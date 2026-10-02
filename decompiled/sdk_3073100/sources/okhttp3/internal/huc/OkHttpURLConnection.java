package okhttp3.internal.huc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.SocketPermission;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.Handshake;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.JavaNetHeaders;
import okhttp3.internal.URLFilter;
import okhttp3.internal.Util;
import okhttp3.internal.Version;
import okhttp3.internal.http.HttpDate;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.platform.Platform;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class OkHttpURLConnection extends HttpURLConnection implements Callback {
    Call call;
    private Throwable callFailure;
    OkHttpClient client;
    boolean connectPending;
    private boolean executed;
    private long fixedContentLength;
    Handshake handshake;
    private final Object lock;
    private final NetworkInterceptor networkInterceptor;
    Response networkResponse;
    Proxy proxy;
    private Headers.Builder requestHeaders;
    private Response response;
    private Headers responseHeaders;
    URLFilter urlFilter;
    public static final String SELECTED_PROTOCOL = Platform.get().getPrefix() + "-Selected-Protocol";
    public static final String RESPONSE_SOURCE = Platform.get().getPrefix() + "-Response-Source";
    private static final Set<String> METHODS = new LinkedHashSet(Arrays.asList("OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "PATCH"));

    public OkHttpURLConnection(URL url, OkHttpClient client) {
        super(url);
        this.networkInterceptor = new NetworkInterceptor();
        this.requestHeaders = new Headers.Builder();
        this.fixedContentLength = -1L;
        this.lock = new Object();
        this.connectPending = true;
        this.client = client;
    }

    public OkHttpURLConnection(URL url, OkHttpClient client, URLFilter urlFilter) {
        this(url, client);
        this.urlFilter = urlFilter;
    }

    @Override // java.net.URLConnection
    public void connect() throws IOException {
        if (!this.executed) {
            Call call = buildCall();
            this.executed = true;
            call.enqueue(this);
            synchronized (this.lock) {
                while (this.connectPending && this.response == null && this.callFailure == null) {
                    try {
                        this.lock.wait();
                    } catch (InterruptedException e) {
                        throw new InterruptedIOException();
                    }
                }
                if (this.callFailure != null) {
                    throw propagate(this.callFailure);
                }
            }
        }
    }

    @Override // java.net.HttpURLConnection
    public void disconnect() {
        if (this.call != null) {
            this.networkInterceptor.proceed();
            this.call.cancel();
        }
    }

    @Override // java.net.HttpURLConnection
    public InputStream getErrorStream() {
        try {
            Response response = getResponse();
            if (!HttpHeaders.hasBody(response) || response.code() < 400) {
                return null;
            }
            return response.body().byteStream();
        } catch (IOException e) {
            return null;
        }
    }

    private Headers getHeaders() throws IOException {
        if (this.responseHeaders == null) {
            Response response = getResponse();
            Headers headers = response.headers();
            this.responseHeaders = headers.newBuilder().add(SELECTED_PROTOCOL, response.protocol().toString()).add(RESPONSE_SOURCE, responseSourceHeader(response)).build();
        }
        return this.responseHeaders;
    }

    private static String responseSourceHeader(Response response) {
        if (response.networkResponse() == null) {
            if (response.cacheResponse() == null) {
                return "NONE";
            }
            return "CACHE " + response.code();
        }
        if (response.cacheResponse() == null) {
            return "NETWORK " + response.code();
        }
        return "CONDITIONAL_CACHE " + response.networkResponse().code();
    }

    @Override // java.net.HttpURLConnection, java.net.URLConnection
    public String getHeaderField(int position) {
        try {
            Headers headers = getHeaders();
            if (position < 0 || position >= headers.size()) {
                return null;
            }
            return headers.value(position);
        } catch (IOException e) {
            return null;
        }
    }

    @Override // java.net.URLConnection
    public String getHeaderField(String fieldName) {
        String string;
        try {
            if (fieldName == null) {
                string = StatusLine.get(getResponse()).toString();
            } else {
                string = getHeaders().get(fieldName);
            }
            return string;
        } catch (IOException e) {
            return null;
        }
    }

    @Override // java.net.HttpURLConnection, java.net.URLConnection
    public String getHeaderFieldKey(int position) {
        try {
            Headers headers = getHeaders();
            if (position < 0 || position >= headers.size()) {
                return null;
            }
            return headers.name(position);
        } catch (IOException e) {
            return null;
        }
    }

    @Override // java.net.URLConnection
    public Map<String, List<String>> getHeaderFields() {
        try {
            return JavaNetHeaders.toMultimap(getHeaders(), StatusLine.get(getResponse()).toString());
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }

    @Override // java.net.URLConnection
    public Map<String, List<String>> getRequestProperties() {
        if (this.connected) {
            throw new IllegalStateException("Cannot access request header fields after connection is set");
        }
        return JavaNetHeaders.toMultimap(this.requestHeaders.build(), null);
    }

    @Override // java.net.URLConnection
    public InputStream getInputStream() throws IOException {
        if (!this.doInput) {
            throw new ProtocolException("This protocol does not support input");
        }
        Response response = getResponse();
        if (response.code() >= 400) {
            throw new FileNotFoundException(this.url.toString());
        }
        return response.body().byteStream();
    }

    @Override // java.net.URLConnection
    public OutputStream getOutputStream() throws IOException {
        OutputStreamRequestBody requestBody = (OutputStreamRequestBody) buildCall().request().body();
        if (requestBody == null) {
            throw new ProtocolException("method does not support a request body: " + this.method);
        }
        if (requestBody instanceof StreamedRequestBody) {
            connect();
            this.networkInterceptor.proceed();
        }
        if (requestBody.isClosed()) {
            throw new ProtocolException("cannot write request body after response has been read");
        }
        return requestBody.outputStream();
    }

    @Override // java.net.HttpURLConnection, java.net.URLConnection
    public Permission getPermission() throws IOException {
        int hostPort;
        URL url = getURL();
        String hostname = url.getHost();
        if (url.getPort() != -1) {
            hostPort = url.getPort();
        } else {
            hostPort = HttpUrl.defaultPort(url.getProtocol());
        }
        if (usingProxy()) {
            InetSocketAddress proxyAddress = (InetSocketAddress) this.client.proxy().address();
            hostname = proxyAddress.getHostName();
            hostPort = proxyAddress.getPort();
        }
        return new SocketPermission(hostname + ":" + hostPort, "connect, resolve");
    }

    @Override // java.net.URLConnection
    public String getRequestProperty(String field) {
        if (field == null) {
            return null;
        }
        return this.requestHeaders.get(field);
    }

    @Override // java.net.URLConnection
    public void setConnectTimeout(int timeoutMillis) {
        this.client = this.client.newBuilder().connectTimeout(timeoutMillis, TimeUnit.MILLISECONDS).build();
    }

    @Override // java.net.HttpURLConnection
    public void setInstanceFollowRedirects(boolean followRedirects) {
        this.client = this.client.newBuilder().followRedirects(followRedirects).build();
    }

    @Override // java.net.HttpURLConnection
    public boolean getInstanceFollowRedirects() {
        return this.client.followRedirects();
    }

    @Override // java.net.URLConnection
    public int getConnectTimeout() {
        return this.client.connectTimeoutMillis();
    }

    @Override // java.net.URLConnection
    public void setReadTimeout(int timeoutMillis) {
        this.client = this.client.newBuilder().readTimeout(timeoutMillis, TimeUnit.MILLISECONDS).build();
    }

    @Override // java.net.URLConnection
    public int getReadTimeout() {
        return this.client.readTimeoutMillis();
    }

    private Call buildCall() throws IOException {
        boolean stream = true;
        if (this.call != null) {
            return this.call;
        }
        this.connected = true;
        if (this.doOutput) {
            if (this.method.equals("GET")) {
                this.method = "POST";
            } else if (!HttpMethod.permitsRequestBody(this.method)) {
                throw new ProtocolException(this.method + " does not support writing");
            }
        }
        if (this.requestHeaders.get("User-Agent") == null) {
            this.requestHeaders.add("User-Agent", defaultUserAgent());
        }
        OutputStreamRequestBody requestBody = null;
        if (HttpMethod.permitsRequestBody(this.method)) {
            String contentType = this.requestHeaders.get("Content-Type");
            if (contentType == null) {
                this.requestHeaders.add("Content-Type", "application/x-www-form-urlencoded");
            }
            if (this.fixedContentLength == -1 && this.chunkLength <= 0) {
                stream = false;
            }
            long contentLength = -1;
            String contentLengthString = this.requestHeaders.get("Content-Length");
            if (this.fixedContentLength != -1) {
                contentLength = this.fixedContentLength;
            } else if (contentLengthString != null) {
                contentLength = Long.parseLong(contentLengthString);
            }
            requestBody = stream ? new StreamedRequestBody(contentLength) : new BufferedRequestBody(contentLength);
            requestBody.timeout().timeout(this.client.writeTimeoutMillis(), TimeUnit.MILLISECONDS);
        }
        Request request = new Request.Builder().url(Internal.instance.getHttpUrlChecked(getURL().toString())).headers(this.requestHeaders.build()).method(this.method, requestBody).build();
        if (this.urlFilter != null) {
            this.urlFilter.checkURLPermitted(request.url().url());
        }
        OkHttpClient.Builder clientBuilder = this.client.newBuilder();
        clientBuilder.interceptors().clear();
        clientBuilder.interceptors().add(UnexpectedException.INTERCEPTOR);
        clientBuilder.networkInterceptors().clear();
        clientBuilder.networkInterceptors().add(this.networkInterceptor);
        clientBuilder.dispatcher(new Dispatcher(this.client.dispatcher().executorService()));
        if (!getUseCaches()) {
            clientBuilder.cache(null);
        }
        Call callNewCall = clientBuilder.build().newCall(request);
        this.call = callNewCall;
        return callNewCall;
    }

    private String defaultUserAgent() {
        String agent = System.getProperty("http.agent");
        return agent != null ? Util.toHumanReadableAscii(agent) : Version.userAgent();
    }

    private Response getResponse() throws IOException {
        if (this.response != null) {
            return this.response;
        }
        if (this.networkResponse != null) {
            return this.networkResponse;
        }
        if (this.callFailure != null) {
            throw propagate(this.callFailure);
        }
        Call call = buildCall();
        this.networkInterceptor.proceed();
        OutputStreamRequestBody requestBody = (OutputStreamRequestBody) call.request().body();
        if (requestBody != null) {
            requestBody.outputStream().close();
        }
        if (this.executed) {
            synchronized (this.lock) {
                while (this.response == null && this.callFailure == null) {
                    try {
                        this.lock.wait();
                    } catch (InterruptedException e) {
                        throw new InterruptedIOException();
                    }
                }
            }
        } else {
            this.executed = true;
            try {
                onResponse(call, call.execute());
            } catch (IOException e2) {
                onFailure(call, e2);
            }
        }
        synchronized (this.lock) {
            if (this.callFailure != null) {
                throw propagate(this.callFailure);
            }
            if (this.response != null) {
                return this.response;
            }
            throw new AssertionError();
        }
    }

    @Override // java.net.HttpURLConnection
    public boolean usingProxy() {
        if (this.proxy != null) {
            return true;
        }
        Proxy clientProxy = this.client.proxy();
        return (clientProxy == null || clientProxy.type() == Proxy.Type.DIRECT) ? false : true;
    }

    @Override // java.net.HttpURLConnection
    public String getResponseMessage() throws IOException {
        return getResponse().message();
    }

    @Override // java.net.HttpURLConnection
    public int getResponseCode() throws IOException {
        return getResponse().code();
    }

    @Override // java.net.URLConnection
    public void setRequestProperty(String field, String newValue) {
        if (this.connected) {
            throw new IllegalStateException("Cannot set request property after connection is made");
        }
        if (field == null) {
            throw new NullPointerException("field == null");
        }
        if (newValue == null) {
            Platform.get().log(5, "Ignoring header " + field + " because its value was null.", null);
        } else if ("X-Android-Transports".equals(field) || "X-Android-Protocols".equals(field)) {
            setProtocols(newValue, false);
        } else {
            this.requestHeaders.set(field, newValue);
        }
    }

    @Override // java.net.URLConnection
    public void setIfModifiedSince(long newValue) {
        super.setIfModifiedSince(newValue);
        if (this.ifModifiedSince != 0) {
            this.requestHeaders.set("If-Modified-Since", HttpDate.format(new Date(this.ifModifiedSince)));
        } else {
            this.requestHeaders.removeAll("If-Modified-Since");
        }
    }

    @Override // java.net.URLConnection
    public void addRequestProperty(String field, String value) {
        if (this.connected) {
            throw new IllegalStateException("Cannot add request property after connection is made");
        }
        if (field == null) {
            throw new NullPointerException("field == null");
        }
        if (value == null) {
            Platform.get().log(5, "Ignoring header " + field + " because its value was null.", null);
        } else if ("X-Android-Transports".equals(field) || "X-Android-Protocols".equals(field)) {
            setProtocols(value, true);
        } else {
            this.requestHeaders.add(field, value);
        }
    }

    private void setProtocols(String protocolsString, boolean append) {
        List<Protocol> protocolsList = new ArrayList<>();
        if (append) {
            protocolsList.addAll(this.client.protocols());
        }
        for (String protocol : protocolsString.split(",", -1)) {
            try {
                protocolsList.add(Protocol.get(protocol));
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        this.client = this.client.newBuilder().protocols(protocolsList).build();
    }

    @Override // java.net.HttpURLConnection
    public void setRequestMethod(String method) throws ProtocolException {
        if (!METHODS.contains(method)) {
            throw new ProtocolException("Expected one of " + METHODS + " but was " + method);
        }
        this.method = method;
    }

    @Override // java.net.HttpURLConnection
    public void setFixedLengthStreamingMode(int contentLength) {
        setFixedLengthStreamingMode(contentLength);
    }

    @Override // java.net.HttpURLConnection
    public void setFixedLengthStreamingMode(long contentLength) {
        if (((HttpURLConnection) this).connected) {
            throw new IllegalStateException("Already connected");
        }
        if (this.chunkLength > 0) {
            throw new IllegalStateException("Already in chunked mode");
        }
        if (contentLength < 0) {
            throw new IllegalArgumentException("contentLength < 0");
        }
        this.fixedContentLength = contentLength;
        ((HttpURLConnection) this).fixedContentLength = (int) Math.min(contentLength, 2147483647L);
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException iOException) {
        synchronized (this.lock) {
            boolean z = iOException instanceof UnexpectedException;
            IOException cause = iOException;
            if (z) {
                cause = iOException.getCause();
            }
            this.callFailure = cause;
            this.lock.notifyAll();
        }
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        synchronized (this.lock) {
            this.response = response;
            this.handshake = response.handshake();
            this.url = response.request().url().url();
            this.lock.notifyAll();
        }
    }

    static final class UnexpectedException extends IOException {
        static final Interceptor INTERCEPTOR = new Interceptor() { // from class: okhttp3.internal.huc.OkHttpURLConnection.UnexpectedException.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                try {
                    return chain.proceed(chain.request());
                } catch (Error | RuntimeException e) {
                    throw new UnexpectedException(e);
                }
            }
        };

        public UnexpectedException(Throwable cause) {
            super(cause);
        }
    }

    private static IOException propagate(Throwable throwable) throws Throwable {
        if (throwable instanceof IOException) {
            throw ((IOException) throwable);
        }
        if (throwable instanceof Error) {
            throw ((Error) throwable);
        }
        if (throwable instanceof RuntimeException) {
            throw ((RuntimeException) throwable);
        }
        throw new AssertionError();
    }

    final class NetworkInterceptor implements Interceptor {
        private boolean proceed;

        NetworkInterceptor() {
        }

        public void proceed() {
            synchronized (OkHttpURLConnection.this.lock) {
                this.proceed = true;
                OkHttpURLConnection.this.lock.notifyAll();
            }
        }

        @Override // okhttp3.Interceptor
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            if (OkHttpURLConnection.this.urlFilter != null) {
                OkHttpURLConnection.this.urlFilter.checkURLPermitted(request.url().url());
            }
            synchronized (OkHttpURLConnection.this.lock) {
                OkHttpURLConnection.this.connectPending = false;
                OkHttpURLConnection.this.proxy = chain.connection().route().proxy();
                OkHttpURLConnection.this.handshake = chain.connection().handshake();
                OkHttpURLConnection.this.lock.notifyAll();
                while (!this.proceed) {
                    try {
                        OkHttpURLConnection.this.lock.wait();
                    } catch (InterruptedException e) {
                        throw new InterruptedIOException();
                    }
                }
            }
            if (request.body() instanceof OutputStreamRequestBody) {
                OutputStreamRequestBody requestBody = (OutputStreamRequestBody) request.body();
                request = requestBody.prepareToSendRequest(request);
            }
            Response response = chain.proceed(request);
            synchronized (OkHttpURLConnection.this.lock) {
                OkHttpURLConnection.this.networkResponse = response;
                OkHttpURLConnection.this.url = response.request().url().url();
            }
            return response;
        }
    }
}
