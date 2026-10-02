package com.youzan.systemweb;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.Keep;
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
import com.youzan.spiderman.cache.CacheHandler;
import com.youzan.spiderman.cache.CacheStatistic;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WebChromeClientWrapper extends WebChromeClient {
    private CacheHandler mCacheHandler;
    private WebChromeClient mDelegate;
    private JsInjecter mJsInjecter;

    public WebChromeClientWrapper(JsInjecter jsInjecter) {
        this.mJsInjecter = jsInjecter;
    }

    public void setDelegate(WebChromeClient delegate) {
        this.mDelegate = delegate;
    }

    public void setCacheHandler(CacheHandler cacheHandler) {
        this.mCacheHandler = cacheHandler;
    }

    @Override // android.webkit.WebChromeClient
    public Bitmap getDefaultVideoPoster() {
        return this.mDelegate != null ? this.mDelegate.getDefaultVideoPoster() : super.getDefaultVideoPoster();
    }

    @Override // android.webkit.WebChromeClient
    public View getVideoLoadingProgressView() {
        return this.mDelegate != null ? this.mDelegate.getVideoLoadingProgressView() : super.getVideoLoadingProgressView();
    }

    @Override // android.webkit.WebChromeClient
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        if (this.mDelegate != null) {
            this.mDelegate.getVisitedHistory(callback);
        } else {
            super.getVisitedHistory(callback);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onCloseWindow(WebView window) {
        if (this.mDelegate != null) {
            this.mDelegate.onCloseWindow(window);
        } else {
            super.onCloseWindow(window);
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return this.mDelegate != null ? this.mDelegate.onConsoleMessage(consoleMessage) : super.onConsoleMessage(consoleMessage);
    }

    @Override // android.webkit.WebChromeClient
    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        if (this.mDelegate != null) {
            this.mDelegate.onConsoleMessage(message, lineNumber, sourceID);
        } else {
            super.onConsoleMessage(message, lineNumber, sourceID);
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return this.mDelegate != null ? this.mDelegate.onCreateWindow(view, isDialog, isUserGesture, resultMsg) : super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override // android.webkit.WebChromeClient
    public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota, long estimatedDatabaseSize, long totalQuota, WebStorage.QuotaUpdater quotaUpdater) {
        if (this.mDelegate != null) {
            this.mDelegate.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
        } else {
            super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsHidePrompt() {
        if (this.mDelegate != null) {
            this.mDelegate.onGeolocationPermissionsHidePrompt();
        } else {
            super.onGeolocationPermissionsHidePrompt();
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        if (this.mDelegate != null) {
            this.mDelegate.onGeolocationPermissionsShowPrompt(origin, callback);
        } else {
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onHideCustomView() {
        if (this.mDelegate != null) {
            this.mDelegate.onHideCustomView();
        } else {
            super.onHideCustomView();
        }
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return this.mDelegate != null ? this.mDelegate.onJsAlert(view, url, message, result) : super.onJsAlert(view, url, message, result);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return this.mDelegate != null ? this.mDelegate.onJsBeforeUnload(view, url, message, result) : super.onJsBeforeUnload(view, url, message, result);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return this.mDelegate != null ? this.mDelegate.onJsConfirm(view, url, message, result) : super.onJsConfirm(view, url, message, result);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (this.mJsInjecter.jsPrompt(message, result)) {
            return true;
        }
        if (!CacheStatistic.isStatisticUrl(message)) {
            return this.mDelegate != null ? this.mDelegate.onJsPrompt(view, url, message, defaultValue, result) : super.onJsPrompt(view, url, message, defaultValue, result);
        }
        this.mCacheHandler.parseStatisticTiming(message);
        result.confirm("ok");
        return true;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onJsTimeout() {
        return this.mDelegate != null ? this.mDelegate.onJsTimeout() : super.onJsTimeout();
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(21)
    public void onPermissionRequest(PermissionRequest request) {
        if (this.mDelegate != null) {
            this.mDelegate.onPermissionRequest(request);
        } else {
            super.onPermissionRequest(request);
        }
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(21)
    public void onPermissionRequestCanceled(PermissionRequest request) {
        if (this.mDelegate != null) {
            this.mDelegate.onPermissionRequestCanceled(request);
        } else {
            super.onPermissionRequestCanceled(request);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onProgressChanged(WebView webView, int newProgress) {
        this.mJsInjecter.shouldInjectJs(webView, newProgress);
        if (this.mDelegate != null) {
            this.mDelegate.onProgressChanged(webView, newProgress);
        } else {
            super.onProgressChanged(webView, newProgress);
        }
    }

    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
        if (this.mDelegate != null) {
            this.mDelegate.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        } else {
            super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedIcon(WebView view, Bitmap icon) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedIcon(view, icon);
        } else {
            super.onReceivedIcon(view, icon);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTitle(WebView view, String title) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedTitle(view, title);
        } else {
            super.onReceivedTitle(view, title);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedTouchIconUrl(view, url, precomposed);
        } else {
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onRequestFocus(WebView view) {
        if (this.mDelegate != null) {
            this.mDelegate.onRequestFocus(view);
        } else {
            super.onRequestFocus(view);
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        if (this.mDelegate != null) {
            this.mDelegate.onShowCustomView(view, callback);
        } else {
            super.onShowCustomView(view, callback);
        }
    }

    @Override // android.webkit.WebChromeClient
    @TargetApi(14)
    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
        if (this.mDelegate != null) {
            this.mDelegate.onShowCustomView(view, requestedOrientation, callback);
        } else {
            super.onShowCustomView(view, requestedOrientation, callback);
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        if (this.mDelegate != null) {
            try {
                this.mDelegate.getClass().getDeclaredMethod("openFileChooser", ValueCallback.class, String.class, String.class).invoke(this.mDelegate, uploadMsg, acceptType, capture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        if (this.mDelegate != null) {
            try {
                this.mDelegate.getClass().getDeclaredMethod("openFileChooser", ValueCallback.class, String.class).invoke(this.mDelegate, uploadMsg, acceptType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Keep
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        if (this.mDelegate != null) {
            try {
                this.mDelegate.getClass().getDeclaredMethod("openFileChooser", ValueCallback.class).invoke(this.mDelegate, uploadMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.webkit.WebChromeClient
    @Keep
    @TargetApi(21)
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return this.mDelegate != null ? this.mDelegate.onShowFileChooser(webView, filePathCallback, fileChooserParams) : super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}
