package com.facebook.react.modules.network;

import android.util.Base64;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ExecutorToken;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.network.OkHttpCallUtil;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.ByteString;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class NetworkingModule extends ReactContextBaseJavaModule {
    private static final int CHUNK_TIMEOUT_NS = 100000000;
    private static final String CONTENT_ENCODING_HEADER_NAME = "content-encoding";
    private static final String CONTENT_TYPE_HEADER_NAME = "content-type";
    private static final int MAX_CHUNK_SIZE_BETWEEN_FLUSHES = 8192;
    protected static final String NAME = "Networking";
    private static final String REQUEST_BODY_KEY_BASE64 = "base64";
    private static final String REQUEST_BODY_KEY_FORMDATA = "formData";
    private static final String REQUEST_BODY_KEY_STRING = "string";
    private static final String REQUEST_BODY_KEY_URI = "uri";
    private static final String USER_AGENT_HEADER_NAME = "user-agent";
    private final OkHttpClient mClient;
    private final ForwardingCookieHandler mCookieHandler;
    private final CookieJarContainer mCookieJarContainer;
    private final String mDefaultUserAgent;
    private final Set<Integer> mRequestIds;
    private boolean mShuttingDown;

    NetworkingModule(ReactApplicationContext reactContext, String defaultUserAgent, OkHttpClient client, List<NetworkInterceptorCreator> networkInterceptorCreators) {
        super(reactContext);
        if (networkInterceptorCreators != null) {
            OkHttpClient.Builder clientBuilder = client.newBuilder();
            for (NetworkInterceptorCreator networkInterceptorCreator : networkInterceptorCreators) {
                clientBuilder.addNetworkInterceptor(networkInterceptorCreator.create());
            }
            client = clientBuilder.build();
        }
        this.mClient = client;
        OkHttpClientProvider.replaceOkHttpClient(client);
        this.mCookieHandler = new ForwardingCookieHandler(reactContext);
        this.mCookieJarContainer = (CookieJarContainer) this.mClient.cookieJar();
        this.mShuttingDown = false;
        this.mDefaultUserAgent = defaultUserAgent;
        this.mRequestIds = new HashSet();
    }

    NetworkingModule(ReactApplicationContext context, String defaultUserAgent, OkHttpClient client) {
        this(context, defaultUserAgent, client, null);
    }

    public NetworkingModule(ReactApplicationContext context) {
        this(context, null, OkHttpClientProvider.getOkHttpClient(), null);
    }

    public NetworkingModule(ReactApplicationContext context, List<NetworkInterceptorCreator> networkInterceptorCreators) {
        this(context, null, OkHttpClientProvider.getOkHttpClient(), networkInterceptorCreators);
    }

    public NetworkingModule(ReactApplicationContext context, String defaultUserAgent) {
        this(context, defaultUserAgent, OkHttpClientProvider.getOkHttpClient(), null);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        this.mCookieJarContainer.setCookieJar(new JavaNetCookieJar(this.mCookieHandler));
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        this.mShuttingDown = true;
        cancelAllRequests();
        this.mCookieHandler.destroy();
        this.mCookieJarContainer.removeCookieJar();
    }

    @ReactMethod
    public void sendRequest(ExecutorToken executorToken, String method, String url, final int requestId, ReadableArray headers, ReadableMap data, final String responseType, final boolean useIncrementalUpdates, int timeout) {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        if (requestId != 0) {
            requestBuilder.tag(Integer.valueOf(requestId));
        }
        final DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter = getEventEmitter(executorToken);
        OkHttpClient.Builder clientBuilder = this.mClient.newBuilder();
        if (useIncrementalUpdates) {
            clientBuilder.addNetworkInterceptor(new Interceptor() { // from class: com.facebook.react.modules.network.NetworkingModule.1
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    ProgressResponseBody responseBody = new ProgressResponseBody(originalResponse.body(), new ProgressListener() { // from class: com.facebook.react.modules.network.NetworkingModule.1.1
                        long last = System.nanoTime();

                        @Override // com.facebook.react.modules.network.ProgressListener
                        public void onProgress(long bytesWritten, long contentLength, boolean done) {
                            long now = System.nanoTime();
                            if ((done || NetworkingModule.shouldDispatch(now, this.last)) && !responseType.equals("text")) {
                                ResponseUtil.onDataReceivedProgress(eventEmitter, requestId, bytesWritten, contentLength);
                                this.last = now;
                            }
                        }
                    });
                    return originalResponse.newBuilder().body(responseBody).build();
                }
            });
        }
        if (timeout != this.mClient.connectTimeoutMillis()) {
            clientBuilder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        }
        OkHttpClient client = clientBuilder.build();
        Headers requestHeaders = extractHeaders(headers, data);
        if (requestHeaders == null) {
            ResponseUtil.onRequestError(eventEmitter, requestId, "Unrecognized headers format", null);
            return;
        }
        String contentType = requestHeaders.get(CONTENT_TYPE_HEADER_NAME);
        String contentEncoding = requestHeaders.get(CONTENT_ENCODING_HEADER_NAME);
        requestBuilder.headers(requestHeaders);
        if (data == null) {
            requestBuilder.method(method, RequestBodyUtil.getEmptyBody(method));
        } else if (data.hasKey("string")) {
            if (contentType == null) {
                ResponseUtil.onRequestError(eventEmitter, requestId, "Payload is set but no content-type header specified", null);
                return;
            }
            String body = data.getString("string");
            MediaType contentMediaType = MediaType.parse(contentType);
            if (RequestBodyUtil.isGzipEncoding(contentEncoding)) {
                RequestBody requestBody = RequestBodyUtil.createGzip(contentMediaType, body);
                if (requestBody == null) {
                    ResponseUtil.onRequestError(eventEmitter, requestId, "Failed to gzip request body", null);
                    return;
                }
                requestBuilder.method(method, requestBody);
            } else {
                requestBuilder.method(method, RequestBody.create(contentMediaType, body));
            }
        } else if (data.hasKey(REQUEST_BODY_KEY_BASE64)) {
            if (contentType == null) {
                ResponseUtil.onRequestError(eventEmitter, requestId, "Payload is set but no content-type header specified", null);
                return;
            } else {
                String base64String = data.getString(REQUEST_BODY_KEY_BASE64);
                requestBuilder.method(method, RequestBody.create(MediaType.parse(contentType), ByteString.decodeBase64(base64String)));
            }
        } else if (data.hasKey(REQUEST_BODY_KEY_URI)) {
            if (contentType == null) {
                ResponseUtil.onRequestError(eventEmitter, requestId, "Payload is set but no content-type header specified", null);
                return;
            }
            String uri = data.getString(REQUEST_BODY_KEY_URI);
            InputStream fileInputStream = RequestBodyUtil.getFileInputStream(getReactApplicationContext(), uri);
            if (fileInputStream == null) {
                ResponseUtil.onRequestError(eventEmitter, requestId, "Could not retrieve file for uri " + uri, null);
                return;
            }
            requestBuilder.method(method, RequestBodyUtil.create(MediaType.parse(contentType), fileInputStream));
        } else if (data.hasKey(REQUEST_BODY_KEY_FORMDATA)) {
            if (contentType == null) {
                contentType = "multipart/form-data";
            }
            ReadableArray parts = data.getArray(REQUEST_BODY_KEY_FORMDATA);
            MultipartBody.Builder multipartBuilder = constructMultipartBody(executorToken, parts, contentType, requestId);
            if (multipartBuilder != null) {
                requestBuilder.method(method, RequestBodyUtil.createProgressRequest(multipartBuilder.build(), new ProgressListener() { // from class: com.facebook.react.modules.network.NetworkingModule.2
                    long last = System.nanoTime();

                    @Override // com.facebook.react.modules.network.ProgressListener
                    public void onProgress(long bytesWritten, long contentLength, boolean done) {
                        long now = System.nanoTime();
                        if (done || NetworkingModule.shouldDispatch(now, this.last)) {
                            ResponseUtil.onDataSend(eventEmitter, requestId, bytesWritten, contentLength);
                            this.last = now;
                        }
                    }
                }));
            } else {
                return;
            }
        } else {
            requestBuilder.method(method, RequestBodyUtil.getEmptyBody(method));
        }
        addRequest(requestId);
        client.newCall(requestBuilder.build()).enqueue(new Callback() { // from class: com.facebook.react.modules.network.NetworkingModule.3
            public void onFailure(Call call, IOException e) {
                if (!NetworkingModule.this.mShuttingDown) {
                    NetworkingModule.this.removeRequest(requestId);
                    ResponseUtil.onRequestError(eventEmitter, requestId, e.getMessage(), e);
                }
            }

            public void onResponse(Call call, Response response) throws IOException {
                if (!NetworkingModule.this.mShuttingDown) {
                    NetworkingModule.this.removeRequest(requestId);
                    ResponseUtil.onResponseReceived(eventEmitter, requestId, response.code(), NetworkingModule.translateHeaders(response.headers()), response.request().url().toString());
                    ResponseBody responseBody = response.body();
                    try {
                        if (useIncrementalUpdates && responseType.equals("text")) {
                            NetworkingModule.this.readWithProgress(eventEmitter, requestId, responseBody);
                            ResponseUtil.onRequestSuccess(eventEmitter, requestId);
                            return;
                        }
                        String responseString = Constants.MAIN_VERSION_TAG;
                        if (responseType.equals("text")) {
                            responseString = responseBody.string();
                        } else if (responseType.equals(NetworkingModule.REQUEST_BODY_KEY_BASE64)) {
                            responseString = Base64.encodeToString(responseBody.bytes(), 2);
                        }
                        ResponseUtil.onDataReceived(eventEmitter, requestId, responseString);
                        ResponseUtil.onRequestSuccess(eventEmitter, requestId);
                    } catch (IOException e) {
                        ResponseUtil.onRequestError(eventEmitter, requestId, e.getMessage(), e);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readWithProgress(DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter, int requestId, ResponseBody responseBody) throws IOException {
        long totalBytesRead = -1;
        long contentLength = -1;
        try {
            ProgressResponseBody progressResponseBody = (ProgressResponseBody) responseBody;
            totalBytesRead = progressResponseBody.totalBytesRead();
            contentLength = progressResponseBody.contentLength();
        } catch (ClassCastException e) {
        }
        Reader reader = responseBody.charStream();
        try {
            char[] buffer = new char[MAX_CHUNK_SIZE_BETWEEN_FLUSHES];
            while (true) {
                int read = reader.read(buffer);
                if (read != -1) {
                    ResponseUtil.onIncrementalDataReceived(eventEmitter, requestId, new String(buffer, 0, read), totalBytesRead, contentLength);
                } else {
                    reader.close();
                    return;
                }
            }
        } catch (Throwable th) {
            reader.close();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean shouldDispatch(long now, long last) {
        return 100000000 + last < now;
    }

    private synchronized void addRequest(int requestId) {
        this.mRequestIds.add(Integer.valueOf(requestId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void removeRequest(int requestId) {
        this.mRequestIds.remove(Integer.valueOf(requestId));
    }

    private synchronized void cancelAllRequests() {
        for (Integer requestId : this.mRequestIds) {
            cancelRequest(requestId.intValue());
        }
        this.mRequestIds.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static WritableMap translateHeaders(Headers headers) {
        WritableMap responseHeaders = Arguments.createMap();
        for (int i = 0; i < headers.size(); i++) {
            String headerName = headers.name(i);
            if (responseHeaders.hasKey(headerName)) {
                responseHeaders.putString(headerName, responseHeaders.getString(headerName) + ", " + headers.value(i));
            } else {
                responseHeaders.putString(headerName, headers.value(i));
            }
        }
        return responseHeaders;
    }

    @ReactMethod
    public void abortRequest(ExecutorToken executorToken, int requestId) {
        cancelRequest(requestId);
        removeRequest(requestId);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.facebook.react.modules.network.NetworkingModule$4] */
    private void cancelRequest(final int requestId) {
        new GuardedAsyncTask<Void, Void>(getReactApplicationContext()) { // from class: com.facebook.react.modules.network.NetworkingModule.4
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.facebook.react.bridge.GuardedAsyncTask
            public void doInBackgroundGuarded(Void... params) {
                OkHttpCallUtil.cancelTag(NetworkingModule.this.mClient, Integer.valueOf(requestId));
            }
        }.execute(new Void[0]);
    }

    @ReactMethod
    public void clearCookies(ExecutorToken executorToken, com.facebook.react.bridge.Callback callback) {
        this.mCookieHandler.clearCookies(callback);
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public boolean supportsWebWorkers() {
        return true;
    }

    private MultipartBody.Builder constructMultipartBody(ExecutorToken ExecutorToken, ReadableArray body, String contentType, int requestId) {
        DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter = getEventEmitter(ExecutorToken);
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        multipartBuilder.setType(MediaType.parse(contentType));
        int size = body.size();
        for (int i = 0; i < size; i++) {
            ReadableMap bodyPart = body.getMap(i);
            ReadableArray headersArray = bodyPart.getArray("headers");
            Headers headers = extractHeaders(headersArray, null);
            if (headers == null) {
                ResponseUtil.onRequestError(eventEmitter, requestId, "Missing or invalid header format for FormData part.", null);
                return null;
            }
            MediaType partContentType = null;
            String partContentTypeStr = headers.get(CONTENT_TYPE_HEADER_NAME);
            if (partContentTypeStr != null) {
                partContentType = MediaType.parse(partContentTypeStr);
                headers = headers.newBuilder().removeAll(CONTENT_TYPE_HEADER_NAME).build();
            }
            if (bodyPart.hasKey("string")) {
                String bodyValue = bodyPart.getString("string");
                multipartBuilder.addPart(headers, RequestBody.create(partContentType, bodyValue));
            } else if (bodyPart.hasKey(REQUEST_BODY_KEY_URI)) {
                if (partContentType == null) {
                    ResponseUtil.onRequestError(eventEmitter, requestId, "Binary FormData part needs a content-type header.", null);
                    return null;
                }
                String fileContentUriStr = bodyPart.getString(REQUEST_BODY_KEY_URI);
                InputStream fileInputStream = RequestBodyUtil.getFileInputStream(getReactApplicationContext(), fileContentUriStr);
                if (fileInputStream == null) {
                    ResponseUtil.onRequestError(eventEmitter, requestId, "Could not retrieve file for uri " + fileContentUriStr, null);
                    return null;
                }
                multipartBuilder.addPart(headers, RequestBodyUtil.create(partContentType, fileInputStream));
            } else {
                ResponseUtil.onRequestError(eventEmitter, requestId, "Unrecognized FormData part.", null);
            }
        }
        return multipartBuilder;
    }

    private Headers extractHeaders(ReadableArray headersArray, ReadableMap requestData) {
        if (headersArray == null) {
            return null;
        }
        Headers.Builder headersBuilder = new Headers.Builder();
        int size = headersArray.size();
        for (int headersIdx = 0; headersIdx < size; headersIdx++) {
            ReadableArray header = headersArray.getArray(headersIdx);
            if (header == null || header.size() != 2) {
                return null;
            }
            String headerName = header.getString(0);
            String headerValue = header.getString(1);
            if (headerName == null || headerValue == null) {
                return null;
            }
            headersBuilder.add(headerName, headerValue);
        }
        if (headersBuilder.get(USER_AGENT_HEADER_NAME) == null && this.mDefaultUserAgent != null) {
            headersBuilder.add(USER_AGENT_HEADER_NAME, this.mDefaultUserAgent);
        }
        boolean isGzipSupported = requestData != null && requestData.hasKey("string");
        if (!isGzipSupported) {
            headersBuilder.removeAll(CONTENT_ENCODING_HEADER_NAME);
        }
        return headersBuilder.build();
    }

    private DeviceEventManagerModule.RCTDeviceEventEmitter getEventEmitter(ExecutorToken ExecutorToken) {
        return (DeviceEventManagerModule.RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(ExecutorToken, DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }
}
