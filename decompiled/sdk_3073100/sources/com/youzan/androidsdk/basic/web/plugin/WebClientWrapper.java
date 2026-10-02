package com.youzan.androidsdk.basic.web.plugin;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.youzan.androidsdk.basic.tool.WebParameter;
import com.youzan.androidsdk.basic.tool.d;
import com.youzan.androidsdk.basic.tool.e;
import com.youzan.androidsdk.tool.AnalyticsUtil;
import java.lang.ref.WeakReference;
import java.util.Stack;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WebClientWrapper extends WebViewClient {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static int f76 = -9;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private static final long f77 = 3000;

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private WeakReference<Activity> f78;

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private String f80;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private WebViewClient f83;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private long f81 = 0;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private final Stack<String> f82 = new Stack<>();

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private boolean f79 = false;

    public WebClientWrapper(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            this.f78 = new WeakReference<>(activity);
        }
    }

    public void setDelegate(WebViewClient delegate) {
        if (!(delegate instanceof WebClientWrapper)) {
            this.f83 = delegate;
        }
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private void m57(String url) {
        if (!TextUtils.isEmpty(url) && !url.equals(getUrl())) {
            if (WebParameter.isYouzanPage(url)) {
                this.f82.push(url);
            } else if (!TextUtils.isEmpty(this.f80)) {
                this.f82.push(this.f80);
                this.f80 = null;
            }
        }
    }

    public String getUrl() {
        if (this.f82.size() > 0) {
            return this.f82.peek();
        }
        return null;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    String m58() {
        if (this.f82.size() > 0) {
            return this.f82.pop();
        }
        return null;
    }

    public boolean pageCanGoBack() {
        return this.f82.size() >= 2;
    }

    public final boolean pageGoBack(WebView webView) {
        if (pageCanGoBack()) {
            String url = popBackUrl();
            if (!TextUtils.isEmpty(url)) {
                webView.loadUrl(url);
                return true;
            }
        }
        return false;
    }

    public final String popBackUrl() {
        if (this.f82.size() < 2) {
            return null;
        }
        this.f82.pop();
        return this.f82.pop();
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    protected final Activity m59() {
        if (this.f78 != null) {
            return this.f78.get();
        }
        return null;
    }

    @Override // android.webkit.WebViewClient
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (this.f83 != null) {
            this.f83.doUpdateVisitedHistory(view, url, isReload);
        } else {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (this.f83 != null) {
            this.f83.onFormResubmission(view, dontResend, resend);
        } else {
            super.onFormResubmission(view, dontResend, resend);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(WebView view, String url) {
        if (this.f83 != null) {
            this.f83.onLoadResource(view, url);
        } else {
            super.onLoadResource(view, url);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(23)
    public void onPageCommitVisible(WebView view, String url) {
        if (this.f83 != null) {
            this.f83.onPageCommitVisible(view, url);
        } else {
            super.onPageCommitVisible(view, url);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView view, String url) {
        if (this.f79) {
            this.f79 = false;
        }
        if (this.f83 != null) {
            this.f83.onPageFinished(view, url);
        }
        AnalyticsUtil.statisticWebviewLoadPage(view.getContext(), url);
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.f79 && this.f82.size() > 0) {
            this.f80 = this.f82.pop();
        }
        m57(url);
        this.f79 = true;
        if (this.f83 != null) {
            this.f83.onPageStarted(view, url, favicon);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(21)
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (this.f83 != null) {
            this.f83.onReceivedClientCertRequest(view, request);
        } else {
            super.onReceivedClientCertRequest(view, request);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (Build.VERSION.SDK_INT < 23 && errorCode == f76) {
            m56(view);
        } else if (this.f83 != null) {
            this.f83.onReceivedError(view, errorCode, description, failingUrl);
        } else {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(23)
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (Build.VERSION.SDK_INT >= 23 && error != null && error.getErrorCode() == f76) {
            m56(view);
        } else if (this.f83 != null) {
            this.f83.onReceivedError(view, request, error);
        } else {
            super.onReceivedError(view, request, error);
        }
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private void m56(WebView view) {
        long now = System.currentTimeMillis();
        if (now - this.f81 > f77) {
            this.f81 = System.currentTimeMillis();
            e.m51(view.getContext());
            view.reload();
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (this.f83 != null) {
            this.f83.onReceivedHttpAuthRequest(view, handler, host, realm);
        } else {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(23)
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (this.f83 != null) {
            this.f83.onReceivedHttpError(view, request, errorResponse);
        } else {
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(12)
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        if (this.f83 != null) {
            this.f83.onReceivedLoginRequest(view, realm, account, args);
        } else {
            super.onReceivedLoginRequest(view, realm, account, args);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.f83 != null) {
            this.f83.onReceivedSslError(view, handler, error);
        } else {
            super.onReceivedSslError(view, handler, error);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (this.f83 != null) {
            this.f83.onScaleChanged(view, oldScale, newScale);
        } else {
            super.onScaleChanged(view, oldScale, newScale);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (this.f83 != null) {
            this.f83.onTooManyRedirects(view, cancelMsg, continueMsg);
        } else {
            super.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (this.f83 != null) {
            this.f83.onUnhandledKeyEvent(view, event);
        } else {
            super.onUnhandledKeyEvent(view, event);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(21)
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (this.f83 != null) {
            return this.f83.shouldInterceptRequest(view, request);
        }
        return null;
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (this.f83 != null) {
            return this.f83.shouldInterceptRequest(view, url);
        }
        return null;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return this.f83 != null ? this.f83.shouldOverrideKeyEvent(view, event) : super.shouldOverrideKeyEvent(view, event);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri uri = request.getUrl();
        Context context = view.getContext();
        WebBackForwardList stack = view.copyBackForwardList();
        if (stack != null && stack.getSize() > 0 && stack.getCurrentItem() != null) {
            String url = uri.toString();
            WebHistoryItem item = stack.getCurrentItem();
            if (url.equals(item.getOriginalUrl()) || url.equals(WebParameter.getKdtUnionUrl(uri))) {
                return false;
            }
        }
        if (d.m50(uri.getScheme())) {
            return d.m49(context, uri);
        }
        if (WebParameter.isBlockHost(uri.getHost())) {
            return false;
        }
        boolean result = this.f83 != null && this.f83.shouldOverrideUrlLoading(view, request);
        return result || d.m46(context, uri);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            WebBackForwardList stack = view.copyBackForwardList();
            if (stack != null && stack.getSize() > 0 && stack.getCurrentItem() != null) {
                WebHistoryItem item = stack.getCurrentItem();
                if (url.equals(item.getOriginalUrl()) || url.equals(WebParameter.getKdtUnionUrl(uri))) {
                    return false;
                }
            }
            Context context = view.getContext();
            if (d.m50(uri.getScheme())) {
                return d.m49(context, uri);
            }
            if (WebParameter.isBlockHost(uri.getHost())) {
                return false;
            }
            boolean result = this.f83 != null && this.f83.shouldOverrideUrlLoading(view, url);
            return result || d.m46(context, uri);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }
}
