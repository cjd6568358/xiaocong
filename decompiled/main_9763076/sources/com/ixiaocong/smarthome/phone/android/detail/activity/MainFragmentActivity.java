package com.ixiaocong.smarthome.phone.android.detail.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.baidu.mobstat.StatService;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.LocationManager;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.common.utils.MainActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.ConfigScenePop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment;
import com.ixiaocong.smarthome.phone.android.detail.fragment.scene.TabSceneFragment;
import com.ixiaocong.smarthome.phone.android.detail.fragment.store.TabStoreFragment;
import com.ixiaocong.smarthome.phone.android.detail.fragment.user.TabUserFragment;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.MyTouchListener;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LoginEvent;
import com.ixiaocong.smarthome.phone.android.event.receiver.NetworkReceiver;
import com.tencent.android.tpush.common.Constants;
import com.tencent.bugly.crashreport.CrashReport;
import com.xiaocong.smarthome.httplib.base.XcBaseFragmentActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import skin.support.widget.SkinCompatSupportable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MainFragmentActivity extends XcBaseFragmentActivity implements HintDialogCallback, SkinCompatSupportable {
    private String mAppUpdateInfo;
    private String mAppUpdateUrl;
    private NetworkReceiver mNetworkReceiver;
    private FragmentTabHost mainTabHost;
    private boolean backKeyPressed = false;
    private int[] mImageViewArray = {R.drawable.main_home_selector, R.drawable.main_community_selector, R.drawable.main_store_selector, R.drawable.main_user_selector};
    private String[] mTextviewArray = {"家居", "场景", "商城", "我的"};
    private boolean isStartApp = true;
    private List<MyTouchListener> onTouchListeners = new ArrayList(10);

    protected int getLayoutID() {
        return R.layout.activity_fragment_main;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initControl(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        MainActivityManagerUtil.getScreenManager().pushActivity(this);
        getWindow().addFlags(67108864);
        this.mainTabHost = (FragmentTabHost) findViewById(R.id.tabhost_main);
        this.mainTabHost.setup(this, getSupportFragmentManager(), R.id.main_fragment);
        this.mainTabHost.getTabWidget().setDividerDrawable(R.color.transparent);
        this.mainTabHost.addTab(this.mainTabHost.newTabSpec("home").setIndicator(getTabItemView(0)), TabHomeFragment.class, null);
        this.mainTabHost.addTab(this.mainTabHost.newTabSpec("scene").setIndicator(getTabItemView(1)), TabSceneFragment.class, null);
        this.mainTabHost.addTab(this.mainTabHost.newTabSpec("store").setIndicator(getTabItemView(2)), TabStoreFragment.class, null);
        this.mainTabHost.addTab(this.mainTabHost.newTabSpec("user").setIndicator(getTabItemView(3)), TabUserFragment.class, null);
        int i = getIntent().getIntExtra("index", 0);
        this.mainTabHost.setCurrentTab(i);
        addListener();
        baiduMtj();
        if (this.mNetworkReceiver == null) {
            this.mNetworkReceiver = new NetworkReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        if (this.mNetworkReceiver != null) {
            registerReceiver(this.mNetworkReceiver, filter);
        }
        checkUpdate();
        initData();
    }

    /* JADX WARN: Type inference failed for: r4v17, types: [com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity$1] */
    private void checkUpdate() {
        String appUpdate = XCHelp.getString("app_upgrade", Constants.MAIN_VERSION_TAG);
        if ("1".equals(appUpdate)) {
            String appUpdateMode = XCHelp.getString("app_upgradeMode", Constants.MAIN_VERSION_TAG);
            this.mAppUpdateUrl = XCHelp.getString("app_downloadUrl", Constants.MAIN_VERSION_TAG);
            this.mAppUpdateInfo = XCHelp.getString("app_update_info", Constants.MAIN_VERSION_TAG);
            String showUpdateVersion = XCHelp.getString("app_update_show_version", Constants.MAIN_VERSION_TAG);
            final String updateVersion = XCHelp.getString("app_update_version", Constants.MAIN_VERSION_TAG);
            if (TextUtils.isEmpty(this.mAppUpdateInfo)) {
                this.mAppUpdateInfo = "您是否需要升级APP版本";
            }
            if (!TextUtils.isEmpty(this.mAppUpdateUrl) && "2".equals(appUpdateMode) && !showUpdateVersion.equals(updateVersion)) {
                new Thread() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        try {
                            Thread.sleep(3000L);
                            MainFragmentActivity.this.runOnUiThread(new Runnable() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    OperationHintDialog.getInstance().showSelectDialog(MainFragmentActivity.this.mActivity, MainFragmentActivity.this, "升级提示", MainFragmentActivity.this.mAppUpdateInfo);
                                    XCHelp.putString("app_update_show_version", updateVersion);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }
    }

    private void baiduMtj() {
        StatService.start(this.mActivity);
        StatService.setOn(this.mActivity, 1);
        StatService.setDebugOn(false);
    }

    private void initData() {
        String phone = (String) SpUtils.getFromLocal(this.mActivity, "xiao_cong_jfnda", "xiao_cong_jfnda", Constants.MAIN_VERSION_TAG);
        if (!TextUtils.isEmpty(phone)) {
            CrashReport.setUserId(phone);
        }
    }

    protected void onResume() {
        super.onResume();
        XcLogger.i("MainFragmentActivity", "onResume()");
        if (!this.isStartApp && !XCDeviceController.getInstance().XCDeviceControllerStatus()) {
            XCDeviceController.getInstance().XCDeviceControllerReconnect(this.mActivity);
        }
        this.isStartApp = false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent event) {
        if (event.isLogin()) {
            this.mainTabHost.setCurrentTab(0);
        } else {
            this.mainTabHost.setCurrentTab(1);
        }
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int i = intent.getIntExtra("index", 0);
        this.mainTabHost.setCurrentTab(i);
    }

    private void addListener() {
        this.mainTabHost.setOnTabChangedListener(MainFragmentActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(String tabId) {
        HttpLoadingHelper.getInstance().dismissProcessLoading();
        if (tabId.equals("home")) {
            this.mainTabHost.setVisibility(0);
            return;
        }
        if (tabId.equals("scene")) {
            this.mainTabHost.setVisibility(0);
        } else if (!tabId.equals("store") && tabId.equals("user")) {
            this.mainTabHost.setVisibility(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private View getTabItemView(int index) {
        View view = View.inflate(this, R.layout.tab_main_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_main_img);
        imageView.setBackgroundResource(this.mImageViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_tab_main_name);
        textView.setText(this.mTextviewArray[index]);
        textView.setOnClickListener(null);
        return view;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onDestroy() {
        super.onDestroy();
        MainActivityManagerUtil.getScreenManager().popActivity(this);
        LocationManager.getInstance().locationStop();
        if (this.mNetworkReceiver != null) {
            unregisterReceiver(this.mNetworkReceiver);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            boolean exit = data.getBooleanExtra("exitFlag", false);
            if (exit) {
                this.mainTabHost.setCurrentTab(0);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity$2] */
    public void onBackPressed() {
        if (this.mainTabHost.getCurrentTab() != 0) {
            this.mainTabHost.setCurrentTab(0);
            return;
        }
        if (ConfigScenePop.getInstance().isShow()) {
            ConfigScenePop.getInstance().dismissPop();
            return;
        }
        if (!this.backKeyPressed) {
            this.backKeyPressed = true;
            ToastUtils.showShort(this.mActivity, "再按一次退出程序 ");
            new Thread() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(1500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        MainFragmentActivity.this.backKeyPressed = false;
                    }
                }
            }.start();
        } else {
            XCDeviceController.getInstance().XCDeviceControllerStop(this.mActivity);
            ActivityManagerUtil.getScreenManager().popAllActivity();
            super.onBackPressed();
        }
    }

    public void showMainTabHost() {
        this.mainTabHost.setVisibility(0);
    }

    public void hintMainTabHost() {
        this.mainTabHost.setVisibility(8);
    }

    public void applySkin() {
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : this.onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri url = Uri.parse(this.mAppUpdateUrl);
                intent.setData(url);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showShort(this.mActivity, "升级失败,请稍后重试!");
            }
        }
    }
}
