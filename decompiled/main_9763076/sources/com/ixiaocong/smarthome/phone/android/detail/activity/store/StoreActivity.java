package com.ixiaocong.smarthome.phone.android.detail.activity.store;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.YouZanLoginModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.basic.YouzanBrowser;
import com.youzan.androidsdk.event.AbsAuthEvent;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StoreActivity extends XcBaseActivity implements View.OnClickListener, OnRefreshListener {
    private final String TAG = getClass().getSimpleName();
    private ImageView mBackImg;
    private String mBuyLink;
    private ImageView mHomeImg;
    private String mPushBuyLink;
    private YouzanBrowser mView;
    private RefreshLayout refreshLayout;

    protected int getLayoutId() {
        return R.layout.activity_store;
    }

    protected void initView() {
        this.mBackImg = (ImageView) $(R.id.left_titlebar_image);
        this.mHomeImg = (ImageView) $(R.id.right_titlebar_image);
        this.mView = (YouzanBrowser) $(R.id.youzan_view);
        this.refreshLayout = (RefreshLayout) $(R.id.refreshLayout);
    }

    public void initAdapter() {
        super.initAdapter();
    }

    protected void initData() {
        this.mBuyLink = getIntent().getStringExtra("BuyingLink");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        this.mView.setLayerType(2, null);
        this.mView.subscribe(new AbsAuthEvent() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.store.StoreActivity.1
            public void call(Context context, boolean b) {
                if (b) {
                    StoreActivity.this.youzanLogin();
                }
            }
        });
        this.mView.setOnKeyListener(new View.OnKeyListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.store.StoreActivity.2
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4 || !StoreActivity.this.mView.pageCanGoBack()) {
                    return false;
                }
                StoreActivity.this.mView.pageGoBack();
                return true;
            }
        });
        this.mView.setWebViewClient(new WebViewClient() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.store.StoreActivity.3
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                XcLogger.e(StoreActivity.this.TAG, url);
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                StoreActivity.this.evaluateJavascript();
                StoreActivity.this.refreshLayout.finishRefresh();
                StoreActivity.this.mView.setLayerType(0, null);
                super.onPageFinished(view, url);
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                XcLogger.e(StoreActivity.this.TAG, "kaishi" + url);
                StoreActivity.this.evaluateJavascript();
                if (url.contains("https://h5.youzan.com/v2/showcase/homepage")) {
                    StoreActivity.this.mHomeImg.setVisibility(8);
                    StoreActivity.this.refreshLayout.setEnableRefresh(true);
                } else {
                    StoreActivity.this.mHomeImg.setVisibility(0);
                    StoreActivity.this.refreshLayout.setEnableRefresh(false);
                    StoreActivity.this.refreshLayout.setEnableOverScrollDrag(false);
                }
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mBackImg.setOnClickListener(this);
        this.mHomeImg.setOnClickListener(this);
        this.refreshLayout.setOnRefreshListener(this);
    }

    public void loadUrl(String url) {
        this.mView.loadUrl(url);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void youzanLogin() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("youzan/login");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.store.StoreActivity.4
            public void onComplete(XCResponseBean var1) {
                YouZanLoginModel youZanLoginModel = (YouZanLoginModel) JSON.parseObject(var1.getData(), YouZanLoginModel.class);
                YouzanToken youzanToken = new YouzanToken();
                youzanToken.setAccessToken(youZanLoginModel.getAccess_token());
                youzanToken.setCookieKey(youZanLoginModel.getCookie_key());
                youzanToken.setCookieValue(youZanLoginModel.getCookie_value());
                StoreActivity.this.mView.sync(youzanToken);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(StoreActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onResume() {
        super.onResume();
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        Log.d("MainFragmentActivity", "XGPushClickedResult:" + click);
        if (click != null) {
            String cusContent = click.getCustomContent();
            if (!TextUtils.isEmpty(cusContent)) {
                try {
                    JSONObject obj = new JSONObject(cusContent);
                    if (!obj.isNull("redirectUrl")) {
                        this.mPushBuyLink = obj.getString("redirectUrl");
                        Log.d("MainFragmentActivity", "get custom value:" + this.mPushBuyLink);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!TextUtils.isEmpty(this.mBuyLink)) {
            loadUrl(this.mBuyLink);
        } else if (!TextUtils.isEmpty(this.mPushBuyLink)) {
            loadUrl(this.mPushBuyLink);
        } else {
            loadUrl("https://h5.youzan.com/v2/showcase/homepage?alias=0ST6DqCL92");
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                if (this.mView.pageCanGoBack()) {
                    this.mView.pageGoBack();
                } else {
                    finish();
                }
                break;
            case R.id.right_titlebar_image /* 2131296678 */:
                loadUrl("https://h5.youzan.com/v2/showcase/homepage?alias=0ST6DqCL92");
                break;
        }
    }

    public void evaluateJavascript() {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mView.evaluateJavascript("javascript:(function(){var a=document.querySelector('.yz-logo');!!a&&a.remove();console.log('evaluate');})()", new ValueCallback<String>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.store.StoreActivity.5
                @Override // android.webkit.ValueCallback
                public void onReceiveValue(String value) {
                }
            });
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        this.mView.reload();
    }
}
