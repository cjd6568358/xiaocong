package com.youzan.systemweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.youzan.jsbridge.util.AsyncExecutor;
import com.youzan.spiderman.cache.CacheHandler;
import com.youzan.spiderman.cache.CacheStatistic;
import com.youzan.spiderman.cache.ResourceResponse;
import com.youzan.spiderman.html.HtmlResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WebViewClientWrapper extends WebViewClient {
    private CacheHandler mCacheHandler;
    private WebViewClient mDelegate;
    private JsInjecter mJsInjecter;

    public WebViewClientWrapper(JsInjecter jsInjecter) {
        this.mJsInjecter = jsInjecter;
    }

    public void setDelegate(WebViewClient delegate) {
        this.mDelegate = delegate;
    }

    public void setCacheHandler(CacheHandler cacheHandler) {
        this.mCacheHandler = cacheHandler;
    }

    @Override // android.webkit.WebViewClient
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (this.mDelegate != null) {
            this.mDelegate.doUpdateVisitedHistory(view, url, isReload);
        } else {
            super.doUpdateVisitedHistory(view, url, isReload);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (this.mDelegate != null) {
            this.mDelegate.onFormResubmission(view, dontResend, resend);
        } else {
            super.onFormResubmission(view, dontResend, resend);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onLoadResource(WebView view, String url) {
        if (this.mDelegate != null) {
            this.mDelegate.onLoadResource(view, url);
        } else {
            super.onLoadResource(view, url);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(23)
    public void onPageCommitVisible(WebView view, String url) {
        if (this.mDelegate != null) {
            this.mDelegate.onPageCommitVisible(view, url);
        } else {
            super.onPageCommitVisible(view, url);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(final WebView view, String url) {
        this.mJsInjecter.injectJsReady(view);
        this.mCacheHandler.tryInjectJs(url, new CacheStatistic.InjectJsCallback() { // from class: com.youzan.systemweb.WebViewClientWrapper.1
            @Override // com.youzan.spiderman.cache.CacheStatistic.InjectJsCallback
            public void onInject(String jsContent) {
                view.loadUrl(jsContent);
            }
        });
        if (this.mDelegate != null) {
            this.mDelegate.onPageFinished(view, url);
        } else {
            super.onPageFinished(view, url);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        this.mCacheHandler.resetStatistic();
        if (this.mDelegate != null) {
            this.mDelegate.onPageStarted(view, url, favicon);
        } else {
            super.onPageStarted(view, url, favicon);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(21)
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedClientCertRequest(view, request);
        } else {
            super.onReceivedClientCertRequest(view, request);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedError(view, errorCode, description, failingUrl);
        } else {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(23)
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedError(view, request, error);
        } else {
            super.onReceivedError(view, request, error);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedHttpAuthRequest(view, handler, host, realm);
        } else {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(23)
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedHttpError(view, request, errorResponse);
        } else {
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(12)
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedLoginRequest(view, realm, account, args);
        } else {
            super.onReceivedLoginRequest(view, realm, account, args);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.mDelegate != null) {
            this.mDelegate.onReceivedSslError(view, handler, error);
        } else {
            super.onReceivedSslError(view, handler, error);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (this.mDelegate != null) {
            this.mDelegate.onScaleChanged(view, oldScale, newScale);
        } else {
            super.onScaleChanged(view, oldScale, newScale);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if (this.mDelegate != null) {
            this.mDelegate.onTooManyRedirects(view, cancelMsg, continueMsg);
        } else {
            super.onTooManyRedirects(view, cancelMsg, continueMsg);
        }
    }

    @Override // android.webkit.WebViewClient
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (this.mDelegate != null) {
            this.mDelegate.onUnhandledKeyEvent(view, event);
        } else {
            super.onUnhandledKeyEvent(view, event);
        }
    }

    @Override // android.webkit.WebViewClient
    @TargetApi(21)
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse response = null;
        if (this.mDelegate != null) {
            response = this.mDelegate.shouldInterceptRequest(view, request);
        }
        if (response == null) {
            final Uri requestUrl = request.getUrl();
            ResourceResponse resource = this.mCacheHandler.shouldInterceptRequest(requestUrl);
            if (resource != null) {
                WebResourceResponse response2 = new WebResourceResponse(resource.getMimeType(), resource.getEncoding(), resource.getInputStream());
                Map<String, String> responseHeader = new HashMap<>();
                responseHeader.put("access-control-allow-origin", "*");
                response2.setResponseHeaders(responseHeader);
                return response2;
            }
            HtmlResponse htmlResponse = this.mCacheHandler.shouldInterceptHtml(requestUrl);
            if (htmlResponse != null) {
                WebResourceResponse response3 = new WebResourceResponse(htmlResponse.getMimeType(), htmlResponse.getEncoding(), htmlResponse.getContentStream());
                response3.setResponseHeaders(htmlResponse.getTransferHeader());
                final List<String> cookies = htmlResponse.getResponseHeader("Set-Cookie".toLowerCase());
                if (cookies != null && !cookies.isEmpty()) {
                    final Context context = view.getContext();
                    AsyncExecutor.getInstance().post(new Runnable() { // from class: com.youzan.systemweb.WebViewClientWrapper.2
                        @Override // java.lang.Runnable
                        public void run() {
                            StorageManager.setCookie(context, requestUrl.toString(), cookies);
                        }
                    });
                    return response3;
                }
                return response3;
            }
            return response;
        }
        return response;
    }

    @Override // android.webkit.WebViewClient
    public WebResourceResponse shouldInterceptRequest(WebView view, final String url) {
        WebResourceResponse response = null;
        if (this.mDelegate != null) {
            response = this.mDelegate.shouldInterceptRequest(view, url);
        }
        if (response == null) {
            ResourceResponse resource = this.mCacheHandler.shouldInterceptRequest(Uri.parse(url));
            if (resource != null) {
                return new WebResourceResponse(resource.getMimeType(), resource.getEncoding(), resource.getInputStream());
            }
            HtmlResponse htmlResponse = this.mCacheHandler.shouldInterceptHtml(url);
            if (htmlResponse != null) {
                response = new WebResourceResponse(htmlResponse.getMimeType(), htmlResponse.getEncoding(), htmlResponse.getContentStream());
                final List<String> cookies = htmlResponse.getResponseHeader("Set-Cookie".toLowerCase());
                if (cookies != null && !cookies.isEmpty()) {
                    final Context context = view.getContext();
                    AsyncExecutor.getInstance().post(new Runnable() { // from class: com.youzan.systemweb.WebViewClientWrapper.3
                        @Override // java.lang.Runnable
                        public void run() {
                            StorageManager.setCookie(context, url, cookies);
                        }
                    });
                }
            }
        }
        return response;
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return this.mDelegate != null ? this.mDelegate.shouldOverrideKeyEvent(view, event) : super.shouldOverrideKeyEvent(view, event);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return this.mDelegate != null ? this.mDelegate.shouldOverrideUrlLoading(view, request) : super.shouldOverrideUrlLoading(view, request);
    }

    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return this.mDelegate != null ? this.mDelegate.shouldOverrideUrlLoading(view, url) : super.shouldOverrideUrlLoading(view, url);
    }
}
