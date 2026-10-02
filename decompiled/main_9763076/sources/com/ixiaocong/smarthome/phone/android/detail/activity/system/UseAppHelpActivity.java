package com.ixiaocong.smarthome.phone.android.detail.activity.system;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.config.HttpContent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UseAppHelpActivity extends XcBaseActivity {
    private WebView mHelpWeb;
    private ImageView mIvBack;

    protected int getLayoutId() {
        return R.layout.activity_use_app_help;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mHelpWeb = (WebView) $(R.id.wv_use_app_help);
        this.mHelpWeb.getSettings().setJavaScriptEnabled(true);
        this.mHelpWeb.getSettings().setCacheMode(1);
        this.mHelpWeb.getSettings().setUseWideViewPort(true);
        this.mHelpWeb.getSettings().setLoadWithOverviewMode(true);
    }

    protected void initData() {
        this.mHelpWeb.loadUrl(HttpContent.HTTP_BASE_URL + "/help/index");
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(UseAppHelpActivity$$Lambda$1.lambdaFactory$(this));
        this.mHelpWeb.setWebChromeClient(new WebChromeClient() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.UseAppHelpActivity.1
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView view, int newProgress) {
            }
        });
        this.mHelpWeb.setWebViewClient(new WebViewClient() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.UseAppHelpActivity.2
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                UseAppHelpActivity.this.mHelpWeb.loadUrl(request);
                return true;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        if (this.mHelpWeb.canGoBack()) {
            this.mHelpWeb.goBack();
        } else {
            finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.mHelpWeb.canGoBack()) {
                this.mHelpWeb.goBack();
                return true;
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
