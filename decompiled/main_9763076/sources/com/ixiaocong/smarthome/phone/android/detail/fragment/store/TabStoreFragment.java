package com.ixiaocong.smarthome.phone.android.detail.fragment.store;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.XcApplication;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity;
import com.ixiaocong.smarthome.phone.android.widget.sharedialog.BottomDialog;
import com.ixiaocong.smarthome.phone.android.widget.sharedialog.Item;
import com.ixiaocong.smarthome.phone.android.widget.sharedialog.OnItemClickListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
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
import com.youzan.androidsdk.event.AbsShareEvent;
import com.youzan.androidsdk.event.AbsStateEvent;
import com.youzan.androidsdk.model.goods.GoodsShareModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabStoreFragment extends XcBaseFragment {
    private final String TAG = getClass().getSimpleName();
    private IWXAPI api;
    private ImageView mBackImg;
    private ImageView mHome;
    private ImageView mShareImg;
    private TextView mTitleText;
    private YouzanBrowser mView;
    private RefreshLayout refreshLayout;

    protected int getLayoutId() {
        return R.layout.fragment_store_tab;
    }

    protected void initView() {
        $(R.id.tab_home_statusbar_view).setLayoutParams(new RelativeLayout.LayoutParams(-1, ScreenUtils.getStatusHeight(this.mActivity)));
        this.mBackImg = (ImageView) $(R.id.left_titlebar_image);
        this.mBackImg.setVisibility(4);
        this.mShareImg = (ImageView) $(R.id.right_titlebar_image_share);
        this.mShareImg.setVisibility(4);
        this.mHome = (ImageView) $(R.id.right_titlebar_image_home);
        this.mHome.setVisibility(4);
        this.mTitleText = (TextView) $(R.id.tv_centertxt_tab_home_title);
        this.mTitleText.setText("商城");
        this.mView = (YouzanBrowser) $(R.id.youzan_view);
        this.refreshLayout = (RefreshLayout) $(R.id.refreshLayout);
        this.api = XcApplication.getInstance().registerWX();
    }

    protected void initData() {
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        this.mView.setLayerType(2, null);
        this.mView.subscribe(new AbsAuthEvent() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.1
            public void call(Context context, boolean b) {
                if (b) {
                    TabStoreFragment.this.youzanLogin();
                }
            }
        });
        this.mView.setOnKeyListener(new View.OnKeyListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.2
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != 4 || !TabStoreFragment.this.mView.pageCanGoBack()) {
                    return false;
                }
                TabStoreFragment.this.mView.pageGoBack();
                return true;
            }
        });
        this.mView.subscribe(new AbsStateEvent() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.3
            public void call(Context context) {
            }
        });
        this.mView.subscribe(new AbsShareEvent() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.4
            public void call(Context context, final GoodsShareModel data) {
                BottomDialog bottomDialog = new BottomDialog(TabStoreFragment.this.mActivity);
                bottomDialog.title("分享到").orientation(0).inflateMenu(R.menu.menu_share, new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.4.1
                    @Override // com.ixiaocong.smarthome.phone.android.widget.sharedialog.OnItemClickListener
                    public void click(Item item) {
                        WXWebpageObject webpage = new WXWebpageObject();
                        webpage.webpageUrl = data.getLink();
                        WXMediaMessage msg = new WXMediaMessage(webpage);
                        msg.title = data.getTitle();
                        msg.description = data.getDesc();
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.message = msg;
                        switch (item.getId()) {
                            case R.id.share_collect /* 2131296814 */:
                                req.scene = 2;
                                TabStoreFragment.this.api.sendReq(req);
                                break;
                            case R.id.share_moments /* 2131296815 */:
                                req.scene = 1;
                                TabStoreFragment.this.api.sendReq(req);
                                break;
                            case R.id.share_wechat /* 2131296816 */:
                                req.scene = 0;
                                TabStoreFragment.this.api.sendReq(req);
                                break;
                        }
                    }
                }).show();
            }
        });
        this.mView.setWebViewClient(new WebViewClient() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.5
            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                XcLogger.e(TabStoreFragment.this.TAG, url);
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                TabStoreFragment.this.evaluateJavascript();
                TabStoreFragment.this.refreshLayout.finishRefresh();
                TabStoreFragment.this.mView.setLayerType(0, null);
                super.onPageFinished(view, url);
            }

            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                XcLogger.e(TabStoreFragment.this.TAG, "kaishi" + url);
                TabStoreFragment.this.evaluateJavascript();
                MainFragmentActivity mainFragmentActivity = (MainFragmentActivity) TabStoreFragment.this.getActivity();
                if (url.contains("https://h5.youzan.com/v2/showcase/homepage")) {
                    TabStoreFragment.this.mBackImg.setVisibility(4);
                    TabStoreFragment.this.mHome.setVisibility(8);
                    mainFragmentActivity.showMainTabHost();
                    TabStoreFragment.this.refreshLayout.setEnableRefresh(true);
                } else {
                    TabStoreFragment.this.mBackImg.setVisibility(0);
                    TabStoreFragment.this.mHome.setVisibility(0);
                    mainFragmentActivity.hintMainTabHost();
                    TabStoreFragment.this.refreshLayout.setEnableRefresh(false);
                    TabStoreFragment.this.refreshLayout.setEnableOverScrollDrag(false);
                }
                super.onPageStarted(view, url, favicon);
            }
        });
        loadUrl();
    }

    public void loadUrl() {
        this.mView.loadUrl("https://h5.youzan.com/v2/showcase/homepage?alias=0ST6DqCL92");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void youzanLogin() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("youzan/login");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.6
            public void onComplete(XCResponseBean var1) {
                YouZanLoginModel youZanLoginModel = (YouZanLoginModel) JSON.parseObject(var1.getData(), YouZanLoginModel.class);
                YouzanToken youzanToken = new YouzanToken();
                youzanToken.setAccessToken(youZanLoginModel.getAccess_token());
                youzanToken.setCookieKey(youZanLoginModel.getCookie_key());
                youzanToken.setCookieValue(youZanLoginModel.getCookie_value());
                TabStoreFragment.this.mView.sync(youzanToken);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(TabStoreFragment.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mBackImg.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                TabStoreFragment.this.mView.pageGoBack();
            }
        });
        this.mShareImg.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.8
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                TabStoreFragment.this.mView.sharePage();
            }
        });
        this.mHome.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.9
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                TabStoreFragment.this.loadUrl();
            }
        });
        this.refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.10
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                TabStoreFragment.this.mView.reload();
            }
        });
    }

    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "商城");
    }

    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(), "商城");
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void evaluateJavascript() {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mView.evaluateJavascript("javascript:(function(){var a=document.querySelector('.yz-logo');!!a&&a.remove();console.log('evaluate');})()", new ValueCallback<String>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment.11
                @Override // android.webkit.ValueCallback
                public void onReceiveValue(String value) {
                }
            });
        }
    }
}
