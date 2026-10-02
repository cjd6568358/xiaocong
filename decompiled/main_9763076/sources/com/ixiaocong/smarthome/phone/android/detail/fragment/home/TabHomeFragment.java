package com.ixiaocong.smarthome.phone.android.detail.fragment.home;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.facebook.react.bridge.UiThreadUtil;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.base.BaseFragment;
import com.ixiaocong.smarthome.phone.android.common.manager.AppDetailSettingManager;
import com.ixiaocong.smarthome.phone.android.common.manager.NotificationManager;
import com.ixiaocong.smarthome.phone.android.common.utils.NoDoubleClickUtils;
import com.ixiaocong.smarthome.phone.android.common.utils.ScreenUtils;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.HomeSelectPop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SelectAddPop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.TabHomeLongClickPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.add.DeviceAddCategoryActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeAddHomeActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeDeviceEditGroupActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.user.EditUserDataActivity;
import com.ixiaocong.smarthome.phone.android.detail.adater.TabHomeDeviceRecyclerAdapter;
import com.ixiaocong.smarthome.phone.android.detail.fragment.home.scene.TabHomeSceneView;
import com.ixiaocong.smarthome.phone.android.detail.fragment.home.weather.TabHomeWeatherView;
import com.ixiaocong.smarthome.phone.android.event.callback.AppBarStateChangeListener;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.HomeSelectFamilyCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.TabHomeDeviceSelecetPopCallback;
import com.ixiaocong.smarthome.phone.android.event.eventbus.LocationEvent;
import com.ixiaocong.smarthome.phone.android.event.eventbus.ThemeEvent;
import com.ixiaocong.smarthome.phone.android.event.receiver.ScanResultReceiver;
import com.ixiaocong.smarthome.phone.android.voice.VoiceDemo;
import com.ixiaocong.smarthome.phone.rn.init.RNCacheViewManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.dialog.LoadingView;
import com.xiaocong.smarthome.httplib.callback.DowloadCallback;
import com.xiaocong.smarthome.httplib.config.AppSpConstans;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DeviceDetailV2Model;
import com.xiaocong.smarthome.httplib.model.DeviceListModel;
import com.xiaocong.smarthome.httplib.model.HomeGroupModel;
import com.xiaocong.smarthome.httplib.model.HomeListModel;
import com.xiaocong.smarthome.httplib.model.MainDevListModel;
import com.xiaocong.smarthome.httplib.model.TabHomeDeviceListModel;
import com.xiaocong.smarthome.httplib.utils.NetworkUtils;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemLongClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.DeviceStatusReceiver;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.sdk.mqtt.helper.MqttObserver;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.util.ACache;
import com.xiaocong.smarthome.zxing.ScanCodeActivity;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpStatus;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TabHomeFragment extends BaseFragment implements View.OnClickListener, CommonPopCallback, HintDialogCallback, HomeSelectFamilyCallback, TabHomeDeviceSelecetPopCallback, OnRefreshListener, DowloadCallback, MqttObserver {
    private AppBarLayout appBarLayout;
    private ACache mCache;
    private String mClientID;
    private LinearLayout mDeviceHintLayout;
    private List<HomeListModel.HomeListBean> mFamily;
    private TextView mHintAddDevice;
    private TabHomeDeviceRecyclerAdapter mHomeDevAdapter;
    private RecyclerView mHomeDevRecycler;
    private List<HomeGroupModel> mHomeGroupList;
    private ImageView mHomeLeftImg;
    private ImageView mHomeRightImg;
    private ImageView mHomeRightMsg;
    private TextView mHomeTitle;
    private List<DeviceListModel> mListData;
    private LoadingView mLoadingView;
    private int mNotifyPosition;
    private TabHomeSceneView mTabScene;
    private TabHomeWeatherView mTabWeather;
    private TabLayout mTablayout;
    private ImageView mTitleBarTextRightView;
    private SmartRefreshLayout mainRefresh;
    private boolean isFirstLoad = true;
    private boolean isControl = false;
    private String mHomeName = "我的家";
    private String currentTab = "all";
    private boolean isOnResume = false;
    private boolean appBarLayoutSateIsShow = true;
    private DeviceStatusReceiver mDeviceStatusReceiver = new DeviceStatusReceiver() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.8
        public void onReceiveDeviceSnapshot(String snapshotDevId, String snapshot, boolean isConnect) {
            if (!TextUtils.isEmpty(snapshotDevId)) {
                if (TabHomeFragment.this.isControl) {
                    if (TabHomeFragment.this.mListData != null && TabHomeFragment.this.mListData.size() > 0) {
                        ((DeviceListModel) TabHomeFragment.this.mListData.get(TabHomeFragment.this.mNotifyPosition)).setSnapshot(snapshot);
                        TabHomeFragment.this.mHomeDevAdapter.notifyItemChanged(TabHomeFragment.this.mNotifyPosition);
                    }
                    XcLogger.i("tabHomeNitify", "mNotifyPosition==" + TabHomeFragment.this.mNotifyPosition);
                    TabHomeFragment.this.isControl = false;
                    return;
                }
                if (TabHomeFragment.this.mListData != null && TabHomeFragment.this.mListData.size() > 0) {
                    for (int i = 0; i < TabHomeFragment.this.mListData.size(); i++) {
                        if (((DeviceListModel) TabHomeFragment.this.mListData.get(i)).getDeviceId().equals(snapshotDevId)) {
                            ((DeviceListModel) TabHomeFragment.this.mListData.get(i)).setSnapshot(snapshot);
                            TabHomeFragment.this.mHomeDevAdapter.notifyItemChanged(i);
                        }
                    }
                }
                XcLogger.i("tabHomeNitify", "notifyDataSetChanged==all");
            }
        }

        public void onReceiveDeviceStatus(String deviceId, int status, boolean isConnect) {
            if (!TextUtils.isEmpty(deviceId) && TabHomeFragment.this.mListData != null && TabHomeFragment.this.mListData.size() > 0) {
                for (int i = 0; i < TabHomeFragment.this.mListData.size(); i++) {
                    if (((DeviceListModel) TabHomeFragment.this.mListData.get(i)).getDeviceId().equals(deviceId)) {
                        ((DeviceListModel) TabHomeFragment.this.mListData.get(i)).setStatus(status);
                        TabHomeFragment.this.mHomeDevAdapter.notifyItemChanged(i);
                    }
                }
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        LocalBroadcastManager.getInstance(this.mActivity).registerReceiver(this.mDeviceStatusReceiver, new IntentFilter("ACTION_UPDATE_STATUS"));
        if (Build.VERSION.SDK_INT >= 19 && !NotificationManager.isNotificationEnabled(getActivity().getApplicationContext())) {
            OperationHintDialog.getInstance().showHintDialog(this.mActivity, this, getString(R.string.notification_title), getString(R.string.notification_msg), getString(R.string.go_open));
        }
        this.mCache = ACache.get(this.mActivity);
    }

    @Override // com.ixiaocong.smarthome.phone.android.base.BaseFragment
    protected int getLayoutId() {
        return R.layout.fragment_home_tab;
    }

    @Override // com.ixiaocong.smarthome.phone.android.base.BaseFragment
    protected void initView() {
        $(R.id.tab_home_statusbar_view).setLayoutParams(new RelativeLayout.LayoutParams(-1, ScreenUtils.getStatusHeight(this.mActivity)));
        this.mHomeLeftImg = (ImageView) $(R.id.iv_left_tab_home_title_image);
        this.mHomeRightImg = (ImageView) $(R.id.iv_right_tab_home_title_image);
        this.mHomeRightMsg = (ImageView) $(R.id.iv_right_tab_home_title_msg);
        this.mTabWeather = (TabHomeWeatherView) $(R.id.tab_home_weather_view);
        this.mTabScene = (TabHomeSceneView) $(R.id.tab_home_scene_view);
        this.mHomeTitle = (TextView) $(R.id.tv_centertxt_tab_home_title);
        this.mDeviceHintLayout = (LinearLayout) $(R.id.rl_tab_home_hint_layout);
        this.mHintAddDevice = (TextView) $(R.id.btn_tab_home_hint_layout_add);
        this.appBarLayout = (AppBarLayout) $(R.id.tab_home_app_bar_layout);
        this.mTablayout = (TabLayout) $(R.id.tab_main_home_layout);
        this.mainRefresh = (SmartRefreshLayout) $(R.id.tab_home_refresh_Layout);
        this.mLoadingView = $(R.id.lv_tab_home_title_loading);
        this.mTitleBarTextRightView = (ImageView) $(R.id.iv_tab_home_title_text_right_icon);
        this.mainRefresh.setOnRefreshListener((OnRefreshListener) this);
        this.mHomeDevRecycler = (RecyclerView) $(R.id.rv_tab_home_device);
        this.mHomeDevRecycler.setLayoutManager(new LinearLayoutManager(this.mActivity));
        checkAppBarLayoutState();
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void initAdapter() {
        super.initAdapter();
        this.mHomeDevAdapter = new TabHomeDeviceRecyclerAdapter(this.mActivity);
        this.mHomeDevRecycler.getItemAnimator().setChangeDuration(0L);
        this.mHomeDevRecycler.setAdapter(this.mHomeDevAdapter);
        loadingCache();
    }

    @Override // com.ixiaocong.smarthome.phone.android.base.BaseFragment
    protected void initData() {
        this.mClientID = AppSpConstans.getInstance().getClientId(this.mActivity);
    }

    public void addListener() {
        super.addListener();
        this.mHomeLeftImg.setOnClickListener(this);
        this.mHomeRightImg.setOnClickListener(this);
        this.mHomeRightMsg.setOnClickListener(this);
        this.mHintAddDevice.setOnClickListener(this);
        this.mHomeTitle.setOnClickListener(this);
        this.mHomeDevRecycler.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                if (!NetworkUtils.isNetworkAvailable(TabHomeFragment.this.mActivity)) {
                    ToastUtils.showShort(TabHomeFragment.this.mActivity, "网络未连接,请联网后重试");
                } else if (!NoDoubleClickUtils.isDoubleClick()) {
                    if (!((DeviceListModel) TabHomeFragment.this.mListData.get(position)).isDownload()) {
                        TabHomeListener.getInstance().deviceItemClick(TabHomeFragment.this.mActivity, position, TabHomeFragment.this.mClientID, TabHomeFragment.this.mListData, TabHomeFragment.this);
                    } else {
                        ToastUtils.showShort(TabHomeFragment.this.mActivity, "正在下载,请稍后……");
                    }
                }
            }
        });
        this.mHomeDevRecycler.addOnItemTouchListener(new OnItemLongClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.2
            public void onSimpleItemLongClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                TabHomeLongClickPop.getInstance().showSelectPop(TabHomeFragment.this.mActivity, view, (DeviceListModel) TabHomeFragment.this.mListData.get(position), TabHomeFragment.this);
            }
        });
        this.mHomeDevRecycler.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.3
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                if (!NoDoubleClickUtils.isDoubleClick() && ((DeviceListModel) TabHomeFragment.this.mListData.get(position)).getControlParameter() != null && view.getId() != 2131296468) {
                    if (!NetworkUtils.isNetworkAvailable(TabHomeFragment.this.mActivity)) {
                        ToastUtils.showShort(TabHomeFragment.this.mActivity, "请检查当前网络连接");
                        return;
                    }
                    if (XCDeviceController.getInstance().XCDeviceControllerStatus()) {
                        TabHomeListener.getInstance().deviceItemChildClick(TabHomeFragment.this.mActivity, view, TabHomeFragment.this.mListData, TabHomeFragment.this.mClientID, position);
                        TabHomeFragment.this.isControl = true;
                        TabHomeFragment.this.mNotifyPosition = position;
                    } else {
                        ToastUtils.showShort(TabHomeFragment.this.mActivity, "正在连稍后重试");
                        XCDeviceController.getInstance().XCDeviceControllerReconnect(TabHomeFragment.this.mActivity);
                    }
                }
            }
        });
        this.mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.4
            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                TabHomeFragment.this.currentTab = (String) tab.getTag();
                if (!"all".equals(TabHomeFragment.this.currentTab)) {
                    TabHomeFragment.this.getGroupDeviceList(TabHomeFragment.this.currentTab, false, true);
                } else {
                    XcLogger.i("onTabSelected", "currentTab" + TabHomeFragment.this.currentTab);
                    TabHomeFragment.this.loadDataList();
                }
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        this.appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.5
            @Override // com.ixiaocong.smarthome.phone.android.event.callback.AppBarStateChangeListener
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                if (state == AppBarStateChangeListener.State.EXPANDED) {
                    XcLogger.i("state", "展开状态");
                    return;
                }
                if (state != AppBarStateChangeListener.State.COLLAPSED) {
                    TabHomeFragment.this.appBarLayoutSateIsShow = true;
                    TabHomeFragment.this.checkAppBarLayoutState();
                    XcLogger.i("state", "中间状态");
                } else {
                    XcLogger.i("state", "折叠状态");
                    TabHomeFragment.this.appBarLayoutSateIsShow = false;
                    TabHomeFragment.this.setTitleBarWhite();
                }
            }
        });
        XCDeviceController.getInstance().XCDeviceControllerDelegate(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAppBarLayoutState() {
        String skinName = (String) SpUtils.getFromLocal(this.mActivity, "ixiaocong_config", "app_theme_style", Constants.MAIN_VERSION_TAG);
        if ("default".equals(skinName)) {
            setTitleBarBack();
            return;
        }
        if ("pink".equals(skinName)) {
            setTitleBarBack();
        } else if ("tinge".equals(skinName)) {
            setTitleBarWhite();
        } else {
            setTitleBarBack();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTitleBarWhite() {
        XcLogger.i("state", "setTitleBarWhite()");
        this.mHomeTitle.setTextColor(this.mActivity.getResources().getColor(R.color.white));
        this.mHomeRightImg.setImageResource(R.drawable.tab_home_append_title_white_icon);
        this.mHomeRightMsg.setImageResource(R.drawable.tab_home_msg_title_white_icon);
        this.mTitleBarTextRightView.setImageResource(R.drawable.arrow_down_icon_triangle_no_skin);
        this.mLoadingView.setColor(this.mActivity.getResources().getColor(R.color.white));
    }

    private void setTitleBarBack() {
        XcLogger.i("state", "setTitleBarBack()");
        this.mHomeTitle.setTextColor(this.mActivity.getResources().getColor(R.color.master_text_color));
        this.mHomeRightImg.setImageResource(R.drawable.tab_home_append_title_icon_pink);
        this.mHomeRightMsg.setImageResource(R.drawable.tab_home_msg_title_icon_pink);
        this.mTitleBarTextRightView.setImageResource(R.drawable.arrow_down_icon_triangle_no_skin_black);
        this.mLoadingView.setColor(this.mActivity.getResources().getColor(R.color.master_text_color));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadDataList() {
        if (this.isFirstLoad) {
            HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("index");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.6
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                try {
                    MainDevListModel mainDevList = (MainDevListModel) JSON.parseObject(var1.getData(), MainDevListModel.class);
                    TabHomeFragment.this.mFamily = mainDevList.getHomes();
                    if (mainDevList.getGroups() != null && !TabHomeFragment.this.equalList(mainDevList.getGroups(), TabHomeFragment.this.mHomeGroupList)) {
                        XcLogger.i("TabHomeFragment", "refresh tab");
                        TabHomeFragment.this.mHomeGroupList = mainDevList.getGroups();
                        TabHomeFragment.this.addTabs(TabHomeFragment.this.mHomeGroupList);
                    }
                    if (mainDevList.getHome() != null && !TextUtils.isEmpty(mainDevList.getHome().getHomeName())) {
                        TabHomeFragment.this.mHomeName = mainDevList.getHome().getHomeName();
                        TabHomeFragment.this.updateMQTT();
                    }
                    if (TabHomeFragment.this.currentTab.equals("all")) {
                        if (mainDevList.devices == null || mainDevList.devices.size() <= 0) {
                            TabHomeFragment.this.mDeviceHintLayout.setVisibility(0);
                        } else {
                            TabHomeFragment.this.mListData = mainDevList.devices;
                            TabHomeFragment.this.mDeviceHintLayout.setVisibility(8);
                        }
                        TabHomeFragment.this.mHomeDevAdapter.setNewData(mainDevList.devices);
                        TabHomeFragment.this.mHomeDevAdapter.notifyDataSetChanged();
                    }
                    TabHomeFragment.this.mTabScene.setSceneData(mainDevList.scenes);
                    if (!TextUtils.isEmpty(mainDevList.getGreetings())) {
                        TabHomeFragment.this.mTabWeather.setHomeGreetings(mainDevList.getGreetings());
                    }
                    TabHomeFragment.this.mTabWeather.setRoomWeather(mainDevList.getWeatherHome());
                    TabHomeFragment.this.isFirstLoad = false;
                    TabHomeFragment.this.mCache.put("homeList", var1.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TabHomeFragment.this.mainRefresh != null) {
                    TabHomeFragment.this.mainRefresh.finishRefresh();
                }
            }

            public void onError(XCErrorMessage var1) {
                if (TabHomeFragment.this.getUserVisibleHint()) {
                    ToastUtils.showShort(TabHomeFragment.this.mActivity, var1.getErrorMessage());
                }
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                if (TabHomeFragment.this.mainRefresh != null) {
                    TabHomeFragment.this.mainRefresh.finishRefresh(false);
                }
                TabHomeFragment.this.isFirstLoad = false;
            }
        });
    }

    private void loadingCache() {
        if (this.isFirstLoad) {
            try {
                String str = this.mCache.getAsString("homeList");
                XcLogger.i("TabHomeFragment", "load cache:" + str);
                if (!TextUtils.isEmpty(str)) {
                    MainDevListModel mainDevList = (MainDevListModel) JSON.parseObject(str, MainDevListModel.class);
                    this.mFamily = mainDevList.getHomes();
                    if (mainDevList.getGroups() != null && !equalList(mainDevList.getGroups(), this.mHomeGroupList)) {
                        this.mHomeGroupList = mainDevList.getGroups();
                        addTabs(this.mHomeGroupList);
                    }
                    if (mainDevList.getHome() != null && !TextUtils.isEmpty(mainDevList.getHome().getHomeName())) {
                        this.mHomeName = mainDevList.getHome().getHomeName();
                        updateMQTT();
                    }
                    if (this.currentTab.equals("all")) {
                        if (mainDevList.devices != null && mainDevList.devices.size() > 0) {
                            this.mListData = mainDevList.devices;
                            this.mDeviceHintLayout.setVisibility(8);
                        } else {
                            this.mDeviceHintLayout.setVisibility(0);
                        }
                        this.mHomeDevAdapter.setNewData(mainDevList.devices);
                        this.mHomeDevAdapter.notifyDataSetChanged();
                    }
                    this.mTabScene.setSceneData(mainDevList.scenes);
                    if (!TextUtils.isEmpty(mainDevList.getGreetings())) {
                        this.mTabWeather.setHomeGreetings(mainDevList.getGreetings());
                    }
                    this.mTabWeather.setRoomWeather(mainDevList.getWeatherHome());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onResume() {
        super.onResume();
        this.isOnResume = true;
        if (NetworkUtils.isNetworkAvailable(this.mActivity)) {
            loadDataList();
            this.mTabWeather.onResume();
            updateMQTT();
            if (!this.currentTab.equals("all")) {
                getGroupDeviceList(this.currentTab, true, false);
                return;
            }
            return;
        }
        this.mHomeTitle.setText(this.mHomeName + "(未连接)");
        ToastUtils.showShort(this.mActivity, "网络未连接,请联网后重试");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LocationEvent event) {
        this.mTabWeather.setLocation(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ThemeEvent event) {
        event.getThemeStyleName();
        XcLogger.i("state", "onEventMainThread()");
        new Thread(new Runnable() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(1000L);
                    UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.7.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (TabHomeFragment.this.appBarLayoutSateIsShow) {
                                TabHomeFragment.this.checkAppBarLayoutState();
                                XcLogger.i("state", "appBarLayoutSateIsShow()");
                            } else {
                                XcLogger.i("state", "appBarLayoutSateNoShow()");
                                TabHomeFragment.this.setTitleBarWhite();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tab_home_hint_layout_add /* 2131296341 */:
                startActivityForNew(this.mActivity, new Intent(this.mActivity, (Class<?>) DeviceAddCategoryActivity.class));
                break;
            case R.id.iv_left_tab_home_title_image /* 2131296505 */:
                startActivityForNew(this.mActivity, new Intent(this.mActivity, (Class<?>) VoiceDemo.class));
                break;
            case R.id.iv_right_tab_home_title_image /* 2131296511 */:
                SelectAddPop.getInstance().showSelectPop(this.mActivity, this.mHomeRightImg, this, true);
                SelectAddPop.getInstance().setName(getString(R.string.scan_title), getString(R.string.add_device));
                SelectAddPop.getInstance().setIconImg(getResources().getDrawable(R.mipmap.scan_pop_icon), getResources().getDrawable(R.mipmap.add_pop_icon));
                break;
            case R.id.iv_right_tab_home_title_msg /* 2131296512 */:
                startActivityForNew(this.mActivity, new Intent(this.mActivity, (Class<?>) SystemMsgActivity.class));
                break;
            case R.id.tv_centertxt_tab_home_title /* 2131296961 */:
                HomeSelectPop.getInstance().showSelectPop(this.mActivity, this.mHomeTitle, this.mFamily, this);
                break;
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(RefreshLayout refreshLayout) {
        if (NetworkUtils.isNetworkAvailable(this.mActivity)) {
            loadDataList();
            this.mTabWeather.onRefresh();
            if (!this.currentTab.equals("all")) {
                getGroupDeviceList(this.currentTab, true, false);
            }
            XcLogger.i("TabHomeFragment", "onRefresh()");
            if (!XCDeviceController.getInstance().XCDeviceControllerStatus()) {
                XCDeviceController.getInstance().XCDeviceControllerReconnect(this.mActivity);
                return;
            }
            return;
        }
        if (this.mainRefresh != null) {
            this.mainRefresh.finishRefresh(false);
        }
        ToastUtils.showShort(this.mActivity, "网络未连接,请联网后重试");
    }

    @Override // com.ixiaocong.smarthome.phone.android.base.BaseFragment
    public void onPause() {
        super.onPause();
        this.isOnResume = false;
        XcLogger.i("TabHomeFragment", "onPause()");
        StatService.onPageEnd(getActivity(), "首页设备列表");
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (this.mDeviceStatusReceiver != null) {
            LocalBroadcastManager.getInstance(this.mActivity).unregisterReceiver(this.mDeviceStatusReceiver);
        }
        XCDeviceController.getInstance().unregistDelegate(this);
    }

    public void downloadSuccessListener(int percent, int position, String deviceId) {
        XcLogger.e("download---position", position + ",isDownLoad" + this.mListData.get(position).isDownload());
        if (!this.mListData.get(position).isDownload() && this.mListData.get(position).getDeviceId().equals(deviceId)) {
            this.mListData.get(position).setDownload(true);
        }
        this.mListData.get(position).setProgress(percent);
        this.mHomeDevAdapter.notifyItemChanged(position);
        XcLogger.e("download---percent", Integer.valueOf(percent));
    }

    public void downloadFinishListener(int position, boolean isUpdate, DeviceDetailV2Model deviceDetailV2Model) {
        this.mListData.get(position).setDownload(false);
        this.mHomeDevAdapter.notifyItemChanged(position);
        if (isUpdate) {
            RNCacheViewManager.getInstance().removeCache(String.valueOf(deviceDetailV2Model.getDeviceInfo().getProductId()));
            if (this.isOnResume && this.mListData.get(position).getDeviceId().equals(deviceDetailV2Model.getDeviceId())) {
                TabHomeListener.getInstance().startActivityForDetail(this.mActivity, deviceDetailV2Model, false);
            }
        }
        XcLogger.e("download---percent", "downloadFinishListener");
    }

    public void dowloadFailureListener(int position) {
        this.mListData.get(position).setDownload(false);
        this.mHomeDevAdapter.notifyItemChanged(position);
        ToastUtils.showShort(this.mActivity, "配置文件下载失败,请稍后重试");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 404) {
            if (grantResults[0] == 0) {
                startActivity(new Intent(this.mActivity, (Class<?>) ScanCodeActivity.class));
                ScanResultReceiver.getInstance().registerReceiver(this.mActivity);
            } else {
                ToastUtils.showShort(this.mActivity, "请授权app访问相机权限");
                AppDetailSettingManager.getAppDetailSettingIntent(this.mActivity);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HomeSelectFamilyCallback
    public void onFamilyItemClick(int position) {
        try {
            homeChange(this.mFamily.get(position).getId(), this.mFamily.get(position).getName());
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HomeSelectFamilyCallback
    public void onAddFamily() {
        startActivity(new Intent(this.mActivity, (Class<?>) HomeAddHomeActivity.class));
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.TabHomeDeviceSelecetPopCallback
    public void editDeviceGroup(String deviceId, String deviceName, String groupId) {
        Intent intent = new Intent(this.mActivity, (Class<?>) HomeDeviceEditGroupActivity.class);
        intent.putExtra(Constants.FLAG_DEVICE_ID, deviceId);
        intent.putExtra("deviceName", deviceName);
        intent.putExtra("intent_group_id", groupId);
        startActivity(intent);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.TabHomeDeviceSelecetPopCallback
    public void deviceTop(String deviceId, int top) {
        if (top == 1) {
            deviceCancelTop(deviceId);
        } else {
            deviceSetTop(deviceId);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.TabHomeDeviceSelecetPopCallback
    public void deviceRename(String deviceId, String deviceName) {
        Intent intent = new Intent(this.mActivity, (Class<?>) EditUserDataActivity.class);
        intent.putExtra("deviceName", deviceName);
        intent.putExtra(Constants.FLAG_DEVICE_ID, deviceId);
        intent.putExtra("intentCode", 1002);
        startActivity(intent);
    }

    private void deviceCancelTop(String deviceId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/top/cancel");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.9
            public void onComplete(XCResponseBean var1) {
                if (!TabHomeFragment.this.currentTab.equals("all")) {
                    TabHomeFragment.this.getGroupDeviceList(TabHomeFragment.this.currentTab, true, false);
                } else {
                    TabHomeFragment.this.loadDataList();
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(TabHomeFragment.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    private void deviceSetTop(String deviceId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/top");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.10
            public void onComplete(XCResponseBean var1) {
                if (!TabHomeFragment.this.currentTab.equals("all")) {
                    TabHomeFragment.this.getGroupDeviceList(TabHomeFragment.this.currentTab, true, false);
                } else {
                    TabHomeFragment.this.loadDataList();
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(TabHomeFragment.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    private void homeChange(String targetHomeId, final String homeName) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("targetHomeId", targetHomeId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("home/move");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.11
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                TabHomeFragment.this.mHomeName = homeName;
                TabHomeFragment.this.updateMQTT();
                TabHomeFragment.this.loadDataList();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(TabHomeFragment.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getGroupDeviceList(String groupId, boolean isRefresh, boolean isShowDialog) {
        if (!isRefresh) {
            this.mHomeDevAdapter.setNewData(null);
            this.mHomeDevAdapter.notifyDataSetChanged();
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("group/device/list");
        if (isShowDialog) {
            HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        }
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeFragment.12
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                TabHomeDeviceListModel tabHomeDeviceListModel = (TabHomeDeviceListModel) JSON.parseObject(var1.getData(), TabHomeDeviceListModel.class);
                if (tabHomeDeviceListModel.devices == null || tabHomeDeviceListModel.devices.size() <= 0) {
                    TabHomeFragment.this.mDeviceHintLayout.setVisibility(0);
                } else {
                    TabHomeFragment.this.mListData = tabHomeDeviceListModel.devices;
                    TabHomeFragment.this.mDeviceHintLayout.setVisibility(8);
                }
                TabHomeFragment.this.mHomeDevAdapter.setNewData(tabHomeDeviceListModel.devices);
                TabHomeFragment.this.mHomeDevAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(TabHomeFragment.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTabs(List<HomeGroupModel> list) {
        this.currentTab = "all";
        this.mTablayout.removeAllTabs();
        this.mTablayout.addTab(this.mTablayout.newTab().setText("全部").setTag("all"));
        if (list != null && list.size() > 0) {
            for (HomeGroupModel i : list) {
                this.mTablayout.addTab(this.mTablayout.newTab().setText(i.getName()).setTag(i.getId()));
            }
        }
    }

    public void updateMQTT() {
        mqttStatus(XCDeviceController.getInstance().XCDeviceControllerStatusInt());
    }

    public boolean equalList(List list1, List list2) {
        return list1 != null && list2 != null && list1.size() == list2.size() && list1.containsAll(list2);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback
    public void commonFirstClick() {
        if (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.CAMERA") != 0) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, HttpStatus.SC_NOT_FOUND);
        } else {
            ScanResultReceiver.getInstance().registerReceiver(this.mActivity);
            startActivity(new Intent(this.mActivity, (Class<?>) ScanCodeActivity.class));
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback
    public void commonSecondClick() {
        startActivity(new Intent(this.mActivity, (Class<?>) DeviceAddCategoryActivity.class));
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        SpUtils.saveToLocal(this.mActivity, "jfkadlj", "jkcien", true);
        Uri packageUri = Uri.parse("package:" + this.mActivity.getPackageName().toString());
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", packageUri);
        this.mActivity.startActivity(intent);
    }

    private void mqttStatus(int status) {
        switch (status) {
            case 0:
                this.mHomeTitle.setText(this.mHomeName);
                this.mLoadingView.setVisibility(0);
                XcLogger.i("XCDeviceControllerStatus", "STATUS_CONNECTING");
                break;
            case 1:
                XcLogger.i("XCDeviceControllerStatus", "STATUS_CONNECTED" + XCDeviceController.getInstance().XCDeviceControllerStatus());
                this.mHomeTitle.setText(this.mHomeName);
                this.mLoadingView.setVisibility(8);
                break;
            case 2:
            default:
                this.mHomeTitle.setText(this.mHomeName + "(未连接)");
                this.mLoadingView.setVisibility(8);
                XcLogger.i("XCDeviceControllerStatus", "default" + status);
                break;
            case 3:
                this.mHomeTitle.setText(this.mHomeName + "(未连接)");
                this.mLoadingView.setVisibility(8);
                XcLogger.i("XCDeviceControllerStatus", "STATUS_DISCONNECTED");
                break;
        }
    }

    public void deviceControllerReceiveMsg(String topic, String msg) {
    }

    public void deviceControllerStatusChanged(int status) {
        mqttStatus(status);
    }
}
