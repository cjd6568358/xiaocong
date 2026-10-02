package com.ixiaocong.smarthome.phone.android.detail.activity.device.config;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XConfigErrorHelpActivity extends XcBaseActivity {
    private ImageView mIvBack;
    private WebView mWebView;

    protected int getLayoutId() {
        return R.layout.activity_xconfig_error_help;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mWebView = (WebView) $(R.id.wv_xconfig_error);
    }

    protected void initData() {
        String webUrl = getIntent().getStringExtra("webViewUrl");
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setCacheMode(1);
        this.mWebView.getSettings().setUseWideViewPort(true);
        this.mWebView.getSettings().setLoadWithOverviewMode(true);
        this.mWebView.loadUrl(webUrl);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(XConfigErrorHelpActivity$$Lambda$1.lambdaFactory$(this));
        this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.XConfigErrorHelpActivity.1
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress != 100) {
                    HttpLoadingHelper.getInstance().showProcessLoading(XConfigErrorHelpActivity.this.mActivity);
                } else {
                    HttpLoadingHelper.getInstance().dismissProcessLoading();
                }
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.XConfigErrorHelpActivity.2
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                XConfigErrorHelpActivity.this.mWebView.loadUrl(request);
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        if (this.mWebView.canGoBack()) {
            this.mWebView.goBack();
        } else {
            finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.mWebView.canGoBack()) {
                this.mWebView.goBack();
                return true;
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
