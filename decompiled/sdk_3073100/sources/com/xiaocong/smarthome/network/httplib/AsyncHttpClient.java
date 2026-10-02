package com.xiaocong.smarthome.network.httplib;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AsyncHttpClient {
    public static final int DEFAULT_MAX_CONNECTIONS = 10;
    public static final int DEFAULT_MAX_RETRIES = 5;
    public static final int DEFAULT_RETRY_SLEEP_TIME_MILLIS = 1500;
    public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    public static final int DEFAULT_SOCKET_TIMEOUT = 15000;
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String LOG_TAG = "AsyncHttpClient";
    public static final String VERSION = "1.4.5";
    private static AsyncHttpClient instance = null;
    private static ExecutorService threadPool;
    private final Map<String, String> clientHeaderMap;
    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext;
    private boolean isUrlEncodingEnabled;
    private int maxConnections;
    private final Map<Context, List<RequestHandle>> requestMap;
    private int timeout;

    public static AsyncHttpClient getInstance(String url) {
        return new AsyncHttpClient(url);
    }

    public AsyncHttpClient(String url) {
        this(true, 80, 443, url);
    }

    public AsyncHttpClient(int httpPort) {
        this(false, httpPort, 443);
    }

    public AsyncHttpClient(int httpPort, int httpsPort) {
        this(false, httpPort, httpsPort);
    }

    public AsyncHttpClient(boolean fixNoHttpResponseException, int httpPort, int httpsPort) {
        this(getDefaultSchemeRegistry(fixNoHttpResponseException, httpPort, httpsPort, null));
    }

    public AsyncHttpClient(boolean fixNoHttpResponseException, int httpPort, int httpsPort, String url) {
        this(getDefaultSchemeRegistry(fixNoHttpResponseException, httpPort, httpsPort, url));
    }

    public static SchemeRegistry getDefaultSchemeRegistry(boolean fixNoHttpResponseException, int httpPort, int httpsPort, String url) {
        SSLSocketFactory sslSocketFactory;
        if (fixNoHttpResponseException) {
            Log.d(LOG_TAG, "Beware! Using the fix is insecure, as it doesn't verify SSL certificates.");
        }
        if (httpPort < 1) {
            httpPort = 80;
            Log.d(LOG_TAG, "Invalid HTTP port number specified, defaulting to 80");
        }
        if (httpsPort < 1) {
            httpsPort = 443;
            Log.d(LOG_TAG, "Invalid HTTPS port number specified, defaulting to 443");
        }
        if (fixNoHttpResponseException) {
            sslSocketFactory = MySSLSocketFactory.getFixedSocketFactory(url);
        } else {
            sslSocketFactory = SSLSocketFactory.getSocketFactory();
        }
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), httpPort));
        schemeRegistry.register(new Scheme("https", sslSocketFactory, httpsPort));
        return schemeRegistry;
    }

    public static HttpClient getHttpClient(String filterurl) {
        BasicHttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(httpParams, 15000L);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(10));
        ConnManagerParams.setMaxTotalConnections(httpParams, 10);
        HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);
        HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, DEFAULT_SOCKET_BUFFER_SIZE);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(httpParams, String.format("android-async-http/%s (http://loopj.com/android-async-http)", VERSION));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, getDefaultSchemeRegistry(true, 80, 443, filterurl));
        return new DefaultHttpClient(cm, httpParams);
    }

    public AsyncHttpClient(SchemeRegistry schemeRegistry) {
        this.maxConnections = 10;
        this.timeout = DEFAULT_SOCKET_TIMEOUT;
        this.isUrlEncodingEnabled = true;
        BasicHttpParams httpParams = new BasicHttpParams();
        ConnManagerParams.setTimeout(httpParams, this.timeout);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(this.maxConnections));
        ConnManagerParams.setMaxTotalConnections(httpParams, 10);
        HttpConnectionParams.setSoTimeout(httpParams, this.timeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, this.timeout);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, DEFAULT_SOCKET_BUFFER_SIZE);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(httpParams, String.format("android-async-http/%s (http://loopj.com/android-async-http)", VERSION));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        if (threadPool == null) {
            synchronized (getClass()) {
                if (threadPool == null) {
                    threadPool = Executors.newCachedThreadPool();
                }
            }
        }
        this.requestMap = new WeakHashMap();
        this.clientHeaderMap = new HashMap();
        this.httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        this.httpClient = new DefaultHttpClient(cm, httpParams);
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() { // from class: com.xiaocong.smarthome.network.httplib.AsyncHttpClient.1
            @Override // org.apache.http.HttpRequestInterceptor
            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader(AsyncHttpClient.HEADER_ACCEPT_ENCODING)) {
                    request.addHeader(AsyncHttpClient.HEADER_ACCEPT_ENCODING, AsyncHttpClient.ENCODING_GZIP);
                }
                for (String header : AsyncHttpClient.this.clientHeaderMap.keySet()) {
                    if (request.containsHeader(header)) {
                        Header overwritten = request.getFirstHeader(header);
                        Log.d(AsyncHttpClient.LOG_TAG, String.format("Headers were overwritten! (%s | %s) overwrites (%s | %s)", header, AsyncHttpClient.this.clientHeaderMap.get(header), overwritten.getName(), overwritten.getValue()));
                    }
                    request.addHeader(header, (String) AsyncHttpClient.this.clientHeaderMap.get(header));
                }
            }
        });
        this.httpClient.addResponseInterceptor(new HttpResponseInterceptor() { // from class: com.xiaocong.smarthome.network.httplib.AsyncHttpClient.2
            @Override // org.apache.http.HttpResponseInterceptor
            public void process(HttpResponse response, HttpContext context) {
                Header encoding;
                HttpEntity entity = response.getEntity();
                if (entity != null && (encoding = entity.getContentEncoding()) != null) {
                    for (HeaderElement element : encoding.getElements()) {
                        if (element.getName().equalsIgnoreCase(AsyncHttpClient.ENCODING_GZIP)) {
                            response.setEntity(new InflatingEntity(entity));
                            return;
                        }
                    }
                }
            }
        });
        this.httpClient.addRequestInterceptor(new HttpRequestInterceptor() { // from class: com.xiaocong.smarthome.network.httplib.AsyncHttpClient.3
            @Override // org.apache.http.HttpRequestInterceptor
            public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
                AuthState authState = (AuthState) context.getAttribute("http.auth.target-scope");
                CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute("http.auth.credentials-provider");
                HttpHost targetHost = (HttpHost) context.getAttribute("http.target_host");
                if (authState.getAuthScheme() == null) {
                    AuthScope authScope = new AuthScope(targetHost.getHostName(), targetHost.getPort());
                    Credentials creds = credsProvider.getCredentials(authScope);
                    if (creds != null) {
                        authState.setAuthScheme(new BasicScheme());
                        authState.setCredentials(creds);
                    }
                }
            }
        }, 0);
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(5, DEFAULT_RETRY_SLEEP_TIME_MILLIS));
    }

    public static void allowRetryExceptionClass(Class<?> cls) {
        if (cls != null) {
            RetryHandler.addClassToWhitelist(cls);
        }
    }

    public static void blockRetryExceptionClass(Class<?> cls) {
        if (cls != null) {
            RetryHandler.addClassToBlacklist(cls);
        }
    }

    public Map<String, String> getHeaderMap() {
        return this.clientHeaderMap;
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public HttpContext getHttpContext() {
        return this.httpContext;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.httpContext.setAttribute("http.cookie-store", cookieStore);
    }

    public void setThreadPool(ThreadPoolExecutor threadPool2) {
        threadPool = threadPool2;
    }

    public void setEnableRedirects(final boolean enableRedirects) {
        this.httpClient.setRedirectHandler(new DefaultRedirectHandler() { // from class: com.xiaocong.smarthome.network.httplib.AsyncHttpClient.4
            @Override // org.apache.http.impl.client.DefaultRedirectHandler, org.apache.http.client.RedirectHandler
            public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
                return enableRedirects;
            }
        });
    }

    public void setUserAgent(String userAgent) {
        HttpProtocolParams.setUserAgent(this.httpClient.getParams(), userAgent);
    }

    public int getMaxConnections() {
        return this.maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        if (maxConnections < 1) {
            maxConnections = 10;
        }
        this.maxConnections = maxConnections;
        HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(this.maxConnections));
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout) {
        if (timeout < 1000) {
            timeout = DEFAULT_SOCKET_TIMEOUT;
        }
        this.timeout = timeout;
        HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setTimeout(httpParams, this.timeout);
        HttpConnectionParams.setSoTimeout(httpParams, this.timeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, this.timeout);
    }

    public void setProxy(String hostname, int port) {
        HttpHost proxy = new HttpHost(hostname, port);
        HttpParams httpParams = this.httpClient.getParams();
        httpParams.setParameter("http.route.default-proxy", proxy);
    }

    public void setProxy(String hostname, int port, String username, String password) {
        this.httpClient.getCredentialsProvider().setCredentials(new AuthScope(hostname, port), new UsernamePasswordCredentials(username, password));
        HttpHost proxy = new HttpHost(hostname, port);
        HttpParams httpParams = this.httpClient.getParams();
        httpParams.setParameter("http.route.default-proxy", proxy);
    }

    public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", sslSocketFactory, 443));
    }

    public void setMaxRetriesAndTimeout(int retries, int timeout) {
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(retries, timeout));
    }

    public void addHeader(String header, String value) {
        this.clientHeaderMap.put(header, value);
    }

    public void removeHeader(String header) {
        this.clientHeaderMap.remove(header);
    }

    public void setBasicAuth(String username, String password) {
        setBasicAuth(username, password, false);
    }

    public void setBasicAuth(String username, String password, boolean preemtive) {
        setBasicAuth(username, password, null, preemtive);
    }

    public void setBasicAuth(String username, String password, AuthScope scope) {
        setBasicAuth(username, password, scope, false);
    }

    public void setBasicAuth(String username, String password, AuthScope scope, boolean preemtive) {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        CredentialsProvider credentialsProvider = this.httpClient.getCredentialsProvider();
        if (scope == null) {
            scope = AuthScope.ANY;
        }
        credentialsProvider.setCredentials(scope, credentials);
        setAuthenticationPreemptive(preemtive);
    }

    public void setAuthenticationPreemptive(boolean isPreemtive) {
        if (isPreemtive) {
            this.httpClient.addRequestInterceptor(new PreemtiveAuthorizationHttpRequestInterceptor(), 0);
        } else {
            this.httpClient.removeRequestInterceptorByClass(PreemtiveAuthorizationHttpRequestInterceptor.class);
        }
    }

    public void clearBasicAuth() {
        this.httpClient.getCredentialsProvider().clear();
    }

    public void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        List<RequestHandle> requestList = this.requestMap.get(context);
        if (requestList != null) {
            for (RequestHandle requestHandle : requestList) {
                requestHandle.cancel(mayInterruptIfRunning);
            }
            this.requestMap.remove(context);
        }
    }

    public void cancelAllRequests(boolean mayInterruptIfRunning) {
        for (List<RequestHandle> requestList : this.requestMap.values()) {
            if (requestList != null) {
                for (RequestHandle requestHandle : requestList) {
                    requestHandle.cancel(mayInterruptIfRunning);
                }
            }
        }
        this.requestMap.clear();
    }

    public RequestHandle head(String url, ResponseHandlerInterface responseHandler) {
        return head(null, url, null, responseHandler);
    }

    public RequestHandle head(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return head(null, url, params, responseHandler);
    }

    public RequestHandle head(Context context, String url, ResponseHandlerInterface responseHandler) {
        return head(context, url, null, responseHandler);
    }

    public RequestHandle head(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, new HttpHead(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params)), null, responseHandler, context);
    }

    public RequestHandle head(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpUriRequest request = new HttpHead(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, null, responseHandler, context);
    }

    public RequestHandle get(String url, ResponseHandlerInterface responseHandler) {
        return get(null, url, null, responseHandler);
    }

    public RequestHandle get(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return get(null, url, params, responseHandler);
    }

    public RequestHandle get(Context context, String url, ResponseHandlerInterface responseHandler) {
        return get(context, url, null, responseHandler);
    }

    public RequestHandle get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, new HttpGet(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params)), null, responseHandler, context);
    }

    public RequestHandle get(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpUriRequest request = new HttpGet(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, null, responseHandler, context);
    }

    public RequestHandle post(String url, ResponseHandlerInterface responseHandler) {
        return post(null, url, null, responseHandler);
    }

    public RequestHandle post(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return post(null, url, params, responseHandler);
    }

    public RequestHandle post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return post(context, url, paramsToEntity(params, responseHandler), null, responseHandler);
    }

    public RequestHandle post(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPost(URI.create(url).normalize()), entity), contentType, responseHandler, context);
    }

    public RequestHandle post(Context context, String url, Header[] headers, RequestParams params, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase request = new HttpPost(URI.create(url).normalize());
        if (params != null) {
            request.setEntity(paramsToEntity(params, responseHandler));
        }
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public RequestHandle post(Context context, String url, Header[] headers, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase request = addEntityToRequestBase(new HttpPost(URI.create(url).normalize()), entity);
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public RequestHandle put(String url, ResponseHandlerInterface responseHandler) {
        return put(null, url, null, responseHandler);
    }

    public RequestHandle put(String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return put(null, url, params, responseHandler);
    }

    public RequestHandle put(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        return put(context, url, paramsToEntity(params, responseHandler), null, responseHandler);
    }

    public RequestHandle put(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        return sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPut(URI.create(url).normalize()), entity), contentType, responseHandler, context);
    }

    public RequestHandle put(Context context, String url, Header[] headers, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler) {
        HttpEntityEnclosingRequestBase request = addEntityToRequestBase(new HttpPut(URI.create(url).normalize()), entity);
        if (headers != null) {
            request.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, request, contentType, responseHandler, context);
    }

    public RequestHandle delete(String url, ResponseHandlerInterface responseHandler) {
        return delete(null, url, responseHandler);
    }

    public RequestHandle delete(Context context, String url, ResponseHandlerInterface responseHandler) {
        HttpDelete delete = new HttpDelete(URI.create(url).normalize());
        return sendRequest(this.httpClient, this.httpContext, delete, null, responseHandler, context);
    }

    public RequestHandle delete(Context context, String url, Header[] headers, ResponseHandlerInterface responseHandler) {
        HttpDelete delete = new HttpDelete(URI.create(url).normalize());
        if (headers != null) {
            delete.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, delete, null, responseHandler, context);
    }

    public RequestHandle delete(Context context, String url, Header[] headers, RequestParams params, ResponseHandlerInterface responseHandler) {
        HttpDelete httpDelete = new HttpDelete(getUrlWithQueryString(this.isUrlEncodingEnabled, url, params));
        if (headers != null) {
            httpDelete.setHeaders(headers);
        }
        return sendRequest(this.httpClient, this.httpContext, httpDelete, null, responseHandler, context);
    }

    protected RequestHandle sendRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
        if (contentType != null) {
            uriRequest.setHeader("Content-Type", contentType);
        }
        responseHandler.setRequestHeaders(uriRequest.getAllHeaders());
        responseHandler.setRequestURI(uriRequest.getURI());
        AsyncHttpRequest request = new AsyncHttpRequest(client, httpContext, uriRequest, responseHandler);
        threadPool.submit(request);
        RequestHandle requestHandle = new RequestHandle(request);
        if (context != null) {
            List<RequestHandle> requestList = this.requestMap.get(context);
            if (requestList == null) {
                requestList = new LinkedList<>();
                this.requestMap.put(context, requestList);
            }
            if (responseHandler instanceof RangeFileAsyncHttpResponseHandler) {
                ((RangeFileAsyncHttpResponseHandler) responseHandler).updateRequestHeaders(uriRequest);
            }
            requestList.add(requestHandle);
            Iterator<RequestHandle> iterator = requestList.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().shouldBeGarbageCollected()) {
                    iterator.remove();
                }
            }
        }
        return requestHandle;
    }

    public void setURLEncodingEnabled(boolean enabled) {
        this.isUrlEncodingEnabled = enabled;
    }

    public static String getUrlWithQueryString(boolean shouldEncodeUrl, String url, RequestParams params) {
        if (shouldEncodeUrl) {
            url = url.replace(" ", "%20");
        }
        if (params != null) {
            String paramString = params.getParamString().trim();
            if (!paramString.equals("") && !paramString.equals("?")) {
                return (url + (url.contains("?") ? "&" : "?")) + paramString;
            }
            return url;
        }
        return url;
    }

    public static void silentCloseInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                Log.w(LOG_TAG, "Cannot close input stream", e);
            }
        }
    }

    public static void silentCloseOutputStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                Log.w(LOG_TAG, "Cannot close output stream", e);
            }
        }
    }

    private HttpEntity paramsToEntity(RequestParams params, ResponseHandlerInterface responseHandler) {
        if (params == null) {
            return null;
        }
        try {
            HttpEntity entity = params.getEntity(responseHandler);
            return entity;
        } catch (Throwable t) {
            if (responseHandler != null) {
                responseHandler.sendFailureMessage(0, null, null, t);
                return null;
            }
            t.printStackTrace();
            return null;
        }
    }

    public boolean isUrlEncodingEnabled() {
        return this.isUrlEncodingEnabled;
    }

    private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase requestBase, HttpEntity entity) {
        if (entity != null) {
            requestBase.setEntity(entity);
        }
        return requestBase;
    }

    private static class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
        public InputStream getContent() throws IOException {
            return new GZIPInputStream(this.wrappedEntity.getContent());
        }

        @Override // org.apache.http.entity.HttpEntityWrapper, org.apache.http.HttpEntity
        public long getContentLength() {
            return -1L;
        }
    }
}
