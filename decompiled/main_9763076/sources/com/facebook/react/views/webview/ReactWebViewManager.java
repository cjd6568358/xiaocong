package com.facebook.react.views.webview;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.ContentSizeChangeEvent;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.webview.events.TopLoadingErrorEvent;
import com.facebook.react.views.webview.events.TopLoadingFinishEvent;
import com.facebook.react.views.webview.events.TopLoadingStartEvent;
import com.facebook.react.views.webview.events.TopMessageEvent;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.sqlcipher.database.SQLiteDatabase;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactWebViewManager extends SimpleViewManager<WebView> {
    private static final String BLANK_URL = "about:blank";
    private static final String BRIDGE_NAME = "__REACT_WEB_VIEW_BRIDGE";
    public static final int COMMAND_GO_BACK = 1;
    public static final int COMMAND_GO_FORWARD = 2;
    public static final int COMMAND_INJECT_JAVASCRIPT = 6;
    public static final int COMMAND_POST_MESSAGE = 5;
    public static final int COMMAND_RELOAD = 3;
    public static final int COMMAND_STOP_LOADING = 4;
    private static final String HTML_ENCODING = "UTF-8";
    private static final String HTML_MIME_TYPE = "text/html; charset=utf-8";
    private static final String HTTP_METHOD_POST = "POST";
    protected static final String REACT_CLASS = "RCTWebView";
    private WebView.PictureListener mPictureListener;
    private WebViewConfig mWebViewConfig;

    protected static class ReactWebViewClient extends WebViewClient {
        private boolean mLastLoadFailed = false;

        protected ReactWebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            if (!this.mLastLoadFailed) {
                ReactWebView reactWebView = (ReactWebView) webView;
                reactWebView.callInjectedJavaScript();
                reactWebView.linkBridge();
                emitFinishEvent(webView, url);
            }
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String url, Bitmap favicon) {
            super.onPageStarted(webView, url, favicon);
            this.mLastLoadFailed = false;
            ReactWebViewManager.dispatchEvent(webView, new TopLoadingStartEvent(webView.getId(), createWebViewEvent(webView, url)));
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("file://")) {
                return false;
            }
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
                view.getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                FLog.w("React", "activity not found to handle uri scheme for: " + url, e);
            }
            return true;
        }

        @Override // android.webkit.WebViewClient
        public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
            super.onReceivedError(webView, errorCode, description, failingUrl);
            this.mLastLoadFailed = true;
            emitFinishEvent(webView, failingUrl);
            WritableMap eventData = createWebViewEvent(webView, failingUrl);
            eventData.putDouble("code", errorCode);
            eventData.putString("description", description);
            ReactWebViewManager.dispatchEvent(webView, new TopLoadingErrorEvent(webView.getId(), eventData));
        }

        @Override // android.webkit.WebViewClient
        public void doUpdateVisitedHistory(WebView webView, String url, boolean isReload) {
            super.doUpdateVisitedHistory(webView, url, isReload);
            ReactWebViewManager.dispatchEvent(webView, new TopLoadingStartEvent(webView.getId(), createWebViewEvent(webView, url)));
        }

        private void emitFinishEvent(WebView webView, String url) {
            ReactWebViewManager.dispatchEvent(webView, new TopLoadingFinishEvent(webView.getId(), createWebViewEvent(webView, url)));
        }

        private WritableMap createWebViewEvent(WebView webView, String url) {
            WritableMap event = Arguments.createMap();
            event.putDouble("target", webView.getId());
            event.putString("url", url);
            event.putBoolean("loading", (this.mLastLoadFailed || webView.getProgress() == 100) ? false : true);
            event.putString("title", webView.getTitle());
            event.putBoolean("canGoBack", webView.canGoBack());
            event.putBoolean("canGoForward", webView.canGoForward());
            return event;
        }
    }

    protected static class ReactWebView extends WebView implements LifecycleEventListener {
        private String injectedJS;
        private boolean messagingEnabled;

        private class ReactWebViewBridge {
            ReactWebView mContext;

            ReactWebViewBridge(ReactWebView c) {
                this.mContext = c;
            }

            @JavascriptInterface
            public void postMessage(String message) {
                this.mContext.onMessage(message);
            }
        }

        public ReactWebView(ThemedReactContext reactContext) {
            super(reactContext);
            this.messagingEnabled = false;
        }

        @Override // com.facebook.react.bridge.LifecycleEventListener
        public void onHostResume() {
        }

        @Override // com.facebook.react.bridge.LifecycleEventListener
        public void onHostPause() {
        }

        @Override // com.facebook.react.bridge.LifecycleEventListener
        public void onHostDestroy() {
            cleanupCallbacksAndDestroy();
        }

        public void setInjectedJavaScript(String js) {
            this.injectedJS = js;
        }

        public void setMessagingEnabled(boolean enabled) {
            if (this.messagingEnabled != enabled) {
                this.messagingEnabled = enabled;
                if (enabled) {
                    addJavascriptInterface(new ReactWebViewBridge(this), ReactWebViewManager.BRIDGE_NAME);
                    linkBridge();
                } else {
                    removeJavascriptInterface(ReactWebViewManager.BRIDGE_NAME);
                }
            }
        }

        public void callInjectedJavaScript() {
            if (getSettings().getJavaScriptEnabled() && this.injectedJS != null && !TextUtils.isEmpty(this.injectedJS)) {
                loadUrl("javascript:(function() {\n" + this.injectedJS + ";\n})();");
            }
        }

        public void linkBridge() {
            if (this.messagingEnabled) {
                loadUrl("javascript:(window.originalPostMessage = window.postMessage,window.postMessage = function(data) {__REACT_WEB_VIEW_BRIDGE.postMessage(String(data));})");
            }
        }

        public void onMessage(String message) {
            ReactWebViewManager.dispatchEvent(this, new TopMessageEvent(getId(), message));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cleanupCallbacksAndDestroy() {
            setWebViewClient(null);
            destroy();
        }
    }

    public ReactWebViewManager() {
        this.mWebViewConfig = new WebViewConfig() { // from class: com.facebook.react.views.webview.ReactWebViewManager.1
            @Override // com.facebook.react.views.webview.WebViewConfig
            public void configWebView(WebView webView) {
            }
        };
    }

    public ReactWebViewManager(WebViewConfig webViewConfig) {
        this.mWebViewConfig = webViewConfig;
    }

    @Override // com.facebook.react.uimanager.ViewManager, com.facebook.react.bridge.NativeModule
    public String getName() {
        return REACT_CLASS;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public WebView createViewInstance(ThemedReactContext reactContext) {
        ReactWebView webView = new ReactWebView(reactContext);
        webView.setWebChromeClient(new WebChromeClient() { // from class: com.facebook.react.views.webview.ReactWebViewManager.2
            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        reactContext.addLifecycleEventListener(webView);
        this.mWebViewConfig.configWebView(webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return webView;
    }

    @ReactProp(name = "javaScriptEnabled")
    public void setJavaScriptEnabled(WebView view, boolean enabled) {
        view.getSettings().setJavaScriptEnabled(enabled);
    }

    @ReactProp(name = "scalesPageToFit")
    public void setScalesPageToFit(WebView view, boolean enabled) {
        view.getSettings().setUseWideViewPort(!enabled);
    }

    @ReactProp(name = "domStorageEnabled")
    public void setDomStorageEnabled(WebView view, boolean enabled) {
        view.getSettings().setDomStorageEnabled(enabled);
    }

    @ReactProp(name = "userAgent")
    public void setUserAgent(WebView view, String userAgent) {
        if (userAgent != null) {
            view.getSettings().setUserAgentString(userAgent);
        }
    }

    @ReactProp(name = "mediaPlaybackRequiresUserAction")
    public void setMediaPlaybackRequiresUserAction(WebView view, boolean requires) {
        view.getSettings().setMediaPlaybackRequiresUserGesture(requires);
    }

    @ReactProp(name = "allowUniversalAccessFromFileURLs")
    public void setAllowUniversalAccessFromFileURLs(WebView view, boolean allow) {
        view.getSettings().setAllowUniversalAccessFromFileURLs(allow);
    }

    @ReactProp(name = "injectedJavaScript")
    public void setInjectedJavaScript(WebView view, String injectedJavaScript) {
        ((ReactWebView) view).setInjectedJavaScript(injectedJavaScript);
    }

    @ReactProp(name = "messagingEnabled")
    public void setMessagingEnabled(WebView view, boolean enabled) {
        ((ReactWebView) view).setMessagingEnabled(enabled);
    }

    @ReactProp(name = "source")
    public void setSource(WebView view, ReadableMap source) {
        if (source != null) {
            if (source.hasKey("html")) {
                String html = source.getString("html");
                if (source.hasKey("baseUrl")) {
                    view.loadDataWithBaseURL(source.getString("baseUrl"), html, HTML_MIME_TYPE, "UTF-8", null);
                    return;
                } else {
                    view.loadData(html, HTML_MIME_TYPE, "UTF-8");
                    return;
                }
            }
            if (source.hasKey("uri")) {
                String url = source.getString("uri");
                String previousUrl = view.getUrl();
                if (previousUrl == null || !previousUrl.equals(url)) {
                    if (source.hasKey(PushConstants.MZ_PUSH_MESSAGE_METHOD)) {
                        String method = source.getString(PushConstants.MZ_PUSH_MESSAGE_METHOD);
                        if (method.equals("POST")) {
                            byte[] postData = null;
                            if (source.hasKey("body")) {
                                String body = source.getString("body");
                                try {
                                    postData = body.getBytes("UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    postData = body.getBytes();
                                }
                            }
                            if (postData == null) {
                                postData = new byte[0];
                            }
                            view.postUrl(url, postData);
                            return;
                        }
                    }
                    HashMap<String, String> headerMap = new HashMap<>();
                    if (source.hasKey("headers")) {
                        ReadableMap headers = source.getMap("headers");
                        ReadableMapKeySetIterator iter = headers.keySetIterator();
                        while (iter.hasNextKey()) {
                            String key = iter.nextKey();
                            if ("user-agent".equals(key.toLowerCase(Locale.ENGLISH))) {
                                if (view.getSettings() != null) {
                                    view.getSettings().setUserAgentString(headers.getString(key));
                                }
                            } else {
                                headerMap.put(key, headers.getString(key));
                            }
                        }
                    }
                    view.loadUrl(url, headerMap);
                    return;
                }
                return;
            }
        }
        view.loadUrl(BLANK_URL);
    }

    @ReactProp(name = "onContentSizeChange")
    public void setOnContentSizeChange(WebView view, boolean sendContentSizeChangeEvents) {
        if (sendContentSizeChangeEvents) {
            view.setPictureListener(getPictureListener());
        } else {
            view.setPictureListener(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.facebook.react.uimanager.ViewManager
    public void addEventEmitters(ThemedReactContext reactContext, WebView view) {
        view.setWebViewClient(new ReactWebViewClient());
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("goBack", 1, "goForward", 2, "reload", 3, "stopLoading", 4, "postMessage", 5, "injectJavaScript", 6);
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void receiveCommand(WebView root, int commandId, ReadableArray args) {
        switch (commandId) {
            case 1:
                root.goBack();
                return;
            case 2:
                root.goForward();
                return;
            case 3:
                root.reload();
                return;
            case 4:
                root.stopLoading();
                return;
            case 5:
                try {
                    JSONObject eventInitDict = new JSONObject();
                    eventInitDict.put("data", args.getString(0));
                    root.loadUrl("javascript:(function () {var event;var data = " + eventInitDict.toString() + ";try {event = new MessageEvent('message', data);} catch (e) {event = document.createEvent('MessageEvent');event.initMessageEvent('message', true, true, data.data, data.origin, data.lastEventId, data.source);}document.dispatchEvent(event);})();");
                    return;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            case 6:
                root.loadUrl("javascript:" + args.getString(0));
                return;
            default:
                return;
        }
    }

    @Override // com.facebook.react.uimanager.ViewManager
    public void onDropViewInstance(WebView webView) {
        super.onDropViewInstance(webView);
        ((ThemedReactContext) webView.getContext()).removeLifecycleEventListener((ReactWebView) webView);
        ((ReactWebView) webView).cleanupCallbacksAndDestroy();
    }

    private WebView.PictureListener getPictureListener() {
        if (this.mPictureListener == null) {
            this.mPictureListener = new WebView.PictureListener() { // from class: com.facebook.react.views.webview.ReactWebViewManager.3
                @Override // android.webkit.WebView.PictureListener
                public void onNewPicture(WebView webView, Picture picture) {
                    ReactWebViewManager.dispatchEvent(webView, new ContentSizeChangeEvent(webView.getId(), webView.getWidth(), webView.getContentHeight()));
                }
            };
        }
        return this.mPictureListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void dispatchEvent(WebView webView, Event event) {
        ReactContext reactContext = (ReactContext) webView.getContext();
        EventDispatcher eventDispatcher = ((UIManagerModule) reactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher();
        eventDispatcher.dispatchEvent(event);
    }
}
