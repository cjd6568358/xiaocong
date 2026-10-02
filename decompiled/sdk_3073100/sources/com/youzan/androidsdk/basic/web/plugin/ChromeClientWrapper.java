package com.youzan.androidsdk.basic.web.plugin;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import com.youzan.androidsdk.event.AbsChooserEvent;
import com.youzan.androidsdk.event.EventAPI;
import com.youzan.androidsdk.event.EventCenter;
import com.youzan.androidsdk.tool.Environment;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class ChromeClientWrapper extends WebChromeClient {

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private static final String f69 = "image/*";

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private ValueCallback<Uri> f70;

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private ValueCallback<Uri[]> f71;

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public final Integer f72 = Integer.valueOf(Environment.generateRequestId());

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private final WebView f73;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private final EventCenter f74;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private WebChromeClient f75;

    public ChromeClientWrapper(WebView webView, EventCenter eventCenter) {
        this.f73 = webView;
        this.f74 = eventCenter;
    }

    public void setDelegate(WebChromeClient delegate) {
        if (!(delegate instanceof ChromeClientWrapper)) {
            this.f75 = delegate;
        }
    }

    @Override // android.webkit.WebChromeClient
    public Bitmap getDefaultVideoPoster() {
        return this.f75 != null ? this.f75.getDefaultVideoPoster() : super.getDefaultVideoPoster();
    }

    @Override // android.webkit.WebChromeClient
    public View getVideoLoadingProgressView() {
        return this.f75 != null ? this.f75.getVideoLoadingProgressView() : super.getVideoLoadingProgressView();
    }

    @Override // android.webkit.WebChromeClient
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        if (this.f75 != null) {
            this.f75.getVisitedHistory(callback);
        } else {
            super.getVisitedHistory(callback);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onCloseWindow(WebView window) {
        if (this.f75 != null) {
            this.f75.onCloseWindow(window);
        } else {
            super.onCloseWindow(window);
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return this.f75 != null ? this.f75.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
    }

    @Override // android.webkit.WebChromeClient
    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        if (this.f75 != null) {
            this.f75.onConsoleMessage(message, lineNumber, sourceID);
        } else {
            super.onConsoleMessage(message, lineNumber, sourceID);
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return this.f75 != null ? this.f75.onCreateWindow(view, isDialog, isUserGesture, resultMsg) : super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override // android.webkit.WebChromeClient
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(5242880L);
        if (this.f75 != null) {
            this.f75.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsHidePrompt() {
        if (this.f75 != null) {
            this.f75.onGeolocationPermissionsHidePrompt();
        } else {
            super.onGeolocationPermissionsHidePrompt();
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
        if (this.f75 != null) {
            this.f75.onGeolocationPermissionsShowPrompt(origin, callback);
        } else {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        if (this.f75 != null) {
            this.f75.onHideCustomView();
        } else {
            super.onHideCustomView();
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return this.f75 != null ? this.f75.onJsAlert(view, url, message, result) : super.onJsAlert(view, url, message, result);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return this.f75 != null ? this.f75.onJsBeforeUnload(view, url, message, result) : super.onJsBeforeUnload(view, url, message, result);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return this.f75 != null ? this.f75.onJsConfirm(view, url, message, result) : super.onJsConfirm(view, url, message, result);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return this.f75 != null ? this.f75.onJsPrompt(view, url, message, defaultValue, result) : super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsTimeout() {
        return this.f75 != null ? this.f75.onJsTimeout() : super.onJsTimeout();
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(21)
    public void onPermissionRequest(PermissionRequest request) {
        if (this.f75 != null) {
            this.f75.onPermissionRequest(request);
        } else {
            super.onPermissionRequest(request);
        }
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(21)
    public void onPermissionRequestCanceled(PermissionRequest request) {
        if (this.f75 != null) {
            this.f75.onPermissionRequestCanceled(request);
        } else {
            super.onPermissionRequestCanceled(request);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView view, int newProgress) {
        if (this.f75 != null) {
            this.f75.onProgressChanged(view, newProgress);
        } else {
            super.onProgressChanged(view, newProgress);
        }
    }

    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
        if (this.f75 != null) {
            this.f75.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        } else {
            super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedIcon(WebView view, Bitmap icon) {
        if (this.f75 != null) {
            this.f75.onReceivedIcon(view, icon);
        } else {
            super.onReceivedIcon(view, icon);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(WebView view, String title) {
        if (this.f75 != null) {
            this.f75.onReceivedTitle(view, title);
        } else {
            super.onReceivedTitle(view, title);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        if (this.f75 != null) {
            this.f75.onReceivedTouchIconUrl(view, url, precomposed);
        } else {
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onRequestFocus(WebView view) {
        if (this.f75 != null) {
            this.f75.onRequestFocus(view);
        } else {
            super.onRequestFocus(view);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        if (this.f75 != null) {
            this.f75.onShowCustomView(view, callback);
        } else {
            super.onShowCustomView(view, callback);
        }
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(14)
    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
        if (this.f75 != null) {
            this.f75.onShowCustomView(view, requestedOrientation, callback);
        } else {
            super.onShowCustomView(view, requestedOrientation, callback);
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        if (!m54(uploadMsg, acceptType) && this.f75 != null) {
            try {
                this.f75.getClass().getDeclaredMethod("openFileChooser", ValueCallback.class, String.class, String.class).invoke(this.f75, uploadMsg, acceptType, capture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (!m54(uploadMsg, acceptType) && this.f75 != null) {
            try {
                this.f75.getClass().getDeclaredMethod("openFileChooser", ValueCallback.class, String.class).invoke(this.f75, uploadMsg, acceptType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        if (!m54(uploadMsg, (String) null) && this.f75 != null) {
            try {
                this.f75.getClass().getDeclaredMethod("openFileChooser", ValueCallback.class).invoke(this.f75, uploadMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.webkit.WebChromeClient
    @Keep
    @TargetApi(21)
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        String[] types = fileChooserParams.getAcceptTypes();
        String type = (types == null || types.length <= 0) ? null : types[0];
        if (!m55(filePathCallback, type) && this.f75 != null) {
            return this.f75.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
        return true;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean m54(ValueCallback<Uri> msg, String acceptType) {
        this.f70 = msg;
        return m53(this.f73.getContext(), acceptType);
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private boolean m55(ValueCallback<Uri[]> msg, String acceptType) {
        this.f71 = msg;
        return m53(this.f73.getContext(), acceptType);
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private boolean m53(Context context, String acceptType) {
        if (TextUtils.isEmpty(acceptType)) {
            acceptType = f69;
        }
        AbsChooserEvent.Meta meta = new AbsChooserEvent.Meta();
        meta.acceptType = acceptType;
        meta.requestId = this.f72.intValue();
        return this.f74.dispatch(context, EventAPI.EVENT_FILE_CHOOSER, meta.toJSON());
    }

    public final void receiveImage(Intent data) {
        try {
            if (this.f70 != null) {
                Uri result = data == null ? null : data.getData();
                this.f70.onReceiveValue(result);
            } else if (this.f71 != null) {
                Uri[] results = data == null ? null : new Uri[]{Uri.parse(data.getDataString())};
                this.f71.onReceiveValue(results);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        this.f71 = null;
        this.f70 = null;
    }
}
