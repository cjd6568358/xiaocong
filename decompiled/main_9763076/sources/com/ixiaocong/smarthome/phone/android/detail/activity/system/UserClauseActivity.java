package com.ixiaocong.smarthome.phone.android.detail.activity.system;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UserClauseActivity extends XcBaseActivity {
    private WebView mHelpWeb;
    private ImageView mIvBack;
    private TextView mTvTiele;

    protected int getLayoutId() {
        return R.layout.activity_use_app_help;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mHelpWeb = (WebView) $(R.id.wv_use_app_help);
        this.mTvTiele = (TextView) $(R.id.centertxt_titlebar);
        this.mHelpWeb.getSettings().setJavaScriptEnabled(true);
        this.mHelpWeb.getSettings().setCacheMode(1);
        this.mHelpWeb.getSettings().setUseWideViewPort(true);
        this.mHelpWeb.getSettings().setLoadWithOverviewMode(true);
    }

    protected void initData() {
        int type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            this.mTvTiele.setText("用户条款");
            this.mHelpWeb.loadUrl("https://doc.ixiaocong.com/gw/agreement/user.html");
        } else if (type == 2) {
            this.mTvTiele.setText("隐私条款");
            this.mHelpWeb.loadUrl("https://doc.ixiaocong.com/gw/agreement/privacy.html");
        }
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(UserClauseActivity$$Lambda$1.lambdaFactory$(this));
        this.mHelpWeb.setWebChromeClient(new WebChromeClient() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.UserClauseActivity.1
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress != 100) {
                    HttpLoadingHelper.getInstance().showProcessLoading(UserClauseActivity.this.mActivity);
                } else {
                    HttpLoadingHelper.getInstance().dismissProcessLoading();
                }
            }
        });
        this.mHelpWeb.setWebViewClient(new WebViewClient() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.system.UserClauseActivity.2
            @Override // android.webkit.WebViewClient
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                UserClauseActivity.this.mHelpWeb.loadUrl(request);
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
