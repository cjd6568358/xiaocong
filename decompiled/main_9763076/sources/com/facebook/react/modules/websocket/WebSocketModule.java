package com.facebook.react.modules.websocket;

import android.util.Base64;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.modules.network.ForwardingCookieHandler;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketCall;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;
import okio.ByteString;
import org.apache.http.HttpHost;
import org.apache.http.cookie.SM;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WebSocketModule extends ReactContextBaseJavaModule {
    private ForwardingCookieHandler mCookieHandler;
    private ReactContext mReactContext;
    private final Map<Integer, WebSocket> mWebSocketConnections;

    public WebSocketModule(ReactApplicationContext context) {
        super(context);
        this.mWebSocketConnections = new HashMap();
        this.mReactContext = context;
        this.mCookieHandler = new ForwardingCookieHandler(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent(String eventName, WritableMap params) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) this.mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(eventName, params);
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "WebSocketModule";
    }

    @ReactMethod
    public void connect(String url, ReadableArray protocols, ReadableMap headers, final int id) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10L, TimeUnit.SECONDS).writeTimeout(10L, TimeUnit.SECONDS).readTimeout(0L, TimeUnit.MINUTES).build();
        Request.Builder builder = new Request.Builder().tag(Integer.valueOf(id)).url(url);
        String cookie = getCookie(url);
        if (cookie != null) {
            builder.addHeader(SM.COOKIE, cookie);
        }
        if (headers != null) {
            ReadableMapKeySetIterator iterator = headers.keySetIterator();
            if (!headers.hasKey("origin")) {
                builder.addHeader("origin", getDefaultOrigin(url));
            }
            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                if (ReadableType.String.equals(headers.getType(key))) {
                    builder.addHeader(key, headers.getString(key));
                } else {
                    FLog.w("React", "Ignoring: requested " + key + ", value not a string");
                }
            }
        } else {
            builder.addHeader("origin", getDefaultOrigin(url));
        }
        if (protocols != null && protocols.size() > 0) {
            StringBuilder protocolsValue = new StringBuilder(Constants.MAIN_VERSION_TAG);
            for (int i = 0; i < protocols.size(); i++) {
                String v = protocols.getString(i).trim();
                if (!v.isEmpty() && !v.contains(",")) {
                    protocolsValue.append(v);
                    protocolsValue.append(",");
                }
            }
            if (protocolsValue.length() > 0) {
                protocolsValue.replace(protocolsValue.length() - 1, protocolsValue.length(), Constants.MAIN_VERSION_TAG);
                builder.addHeader("Sec-WebSocket-Protocol", protocolsValue.toString());
            }
        }
        WebSocketCall.create(client, builder.build()).enqueue(new WebSocketListener() { // from class: com.facebook.react.modules.websocket.WebSocketModule.1
            public void onOpen(WebSocket webSocket, Response response) {
                WebSocketModule.this.mWebSocketConnections.put(Integer.valueOf(id), webSocket);
                WritableMap params = Arguments.createMap();
                params.putInt("id", id);
                WebSocketModule.this.sendEvent("websocketOpen", params);
            }

            public void onClose(int code, String reason) {
                WritableMap params = Arguments.createMap();
                params.putInt("id", id);
                params.putInt("code", code);
                params.putString("reason", reason);
                WebSocketModule.this.sendEvent("websocketClosed", params);
            }

            public void onFailure(IOException e, Response response) {
                WebSocketModule.this.notifyWebSocketFailed(id, e.getMessage());
            }

            public void onPong(Buffer buffer) {
            }

            public void onMessage(ResponseBody response) throws IOException {
                String message;
                try {
                    if (response.contentType() == WebSocket.BINARY) {
                        message = Base64.encodeToString(response.source().readByteArray(), 2);
                    } else {
                        message = response.source().readUtf8();
                    }
                    try {
                        response.source().close();
                    } catch (IOException e) {
                        FLog.e("React", "Could not close BufferedSource for WebSocket id " + id, e);
                    }
                    WritableMap params = Arguments.createMap();
                    params.putInt("id", id);
                    params.putString("data", message);
                    params.putString("type", response.contentType() == WebSocket.BINARY ? "binary" : "text");
                    WebSocketModule.this.sendEvent("websocketMessage", params);
                } catch (IOException e2) {
                    WebSocketModule.this.notifyWebSocketFailed(id, e2.getMessage());
                }
            }
        });
        client.dispatcher().executorService().shutdown();
    }

    @ReactMethod
    public void close(int code, String reason, int id) {
        WebSocket client = this.mWebSocketConnections.get(Integer.valueOf(id));
        if (client != null) {
            try {
                client.close(code, reason);
                this.mWebSocketConnections.remove(Integer.valueOf(id));
            } catch (Exception e) {
                FLog.e("React", "Could not close WebSocket connection for id " + id, e);
            }
        }
    }

    @ReactMethod
    public void send(String message, int id) {
        WebSocket client = this.mWebSocketConnections.get(Integer.valueOf(id));
        if (client == null) {
            throw new RuntimeException("Cannot send a message. Unknown WebSocket id " + id);
        }
        try {
            client.sendMessage(RequestBody.create(WebSocket.TEXT, message));
        } catch (IOException | IllegalStateException e) {
            notifyWebSocketFailed(id, e.getMessage());
        }
    }

    @ReactMethod
    public void sendBinary(String base64String, int id) {
        WebSocket client = this.mWebSocketConnections.get(Integer.valueOf(id));
        if (client == null) {
            throw new RuntimeException("Cannot send a message. Unknown WebSocket id " + id);
        }
        try {
            client.sendMessage(RequestBody.create(WebSocket.BINARY, ByteString.decodeBase64(base64String)));
        } catch (IOException | IllegalStateException e) {
            notifyWebSocketFailed(id, e.getMessage());
        }
    }

    @ReactMethod
    public void ping(int id) {
        WebSocket client = this.mWebSocketConnections.get(Integer.valueOf(id));
        if (client == null) {
            throw new RuntimeException("Cannot send a message. Unknown WebSocket id " + id);
        }
        try {
            Buffer buffer = new Buffer();
            client.sendPing(buffer);
        } catch (IOException | IllegalStateException e) {
            notifyWebSocketFailed(id, e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyWebSocketFailed(int id, String message) {
        WritableMap params = Arguments.createMap();
        params.putInt("id", id);
        params.putString("message", message);
        sendEvent("websocketFailed", params);
    }

    private static String getDefaultOrigin(String uri) {
        try {
            String scheme = Constants.MAIN_VERSION_TAG;
            URI requestURI = new URI(uri);
            if (requestURI.getScheme().equals("wss")) {
                scheme = Constants.MAIN_VERSION_TAG + "https";
            } else if (requestURI.getScheme().equals("ws")) {
                scheme = Constants.MAIN_VERSION_TAG + HttpHost.DEFAULT_SCHEME_NAME;
            }
            if (requestURI.getPort() != -1) {
                String defaultOrigin = String.format("%s://%s:%s", scheme, requestURI.getHost(), Integer.valueOf(requestURI.getPort()));
                return defaultOrigin;
            }
            String defaultOrigin2 = String.format("%s://%s/", scheme, requestURI.getHost());
            return defaultOrigin2;
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Unable to set " + uri + " as default origin header");
        }
    }

    private String getCookie(String uri) {
        try {
            URI origin = new URI(getDefaultOrigin(uri));
            Map<String, List<String>> cookieMap = this.mCookieHandler.get(origin, new HashMap());
            List<String> cookieList = cookieMap.get(SM.COOKIE);
            if (cookieList == null || cookieList.isEmpty()) {
                return null;
            }
            return cookieList.get(0);
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("Unable to get cookie from " + uri);
        }
    }
}
