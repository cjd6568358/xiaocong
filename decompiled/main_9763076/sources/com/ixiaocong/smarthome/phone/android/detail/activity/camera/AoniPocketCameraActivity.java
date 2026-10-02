package com.ixiaocong.smarthome.phone.android.detail.activity.camera;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import bsh.ParserConstants;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.TimeZoneUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonAlarmActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonHistoryVideoActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonMoreAlarmLogActiity;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonSettingActivty;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonLive;
import com.ixiaocong.smarthome.phone.android.detail.adater.AoniCommonAlarmLogAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.CameraCommonAlarmListModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniPocketCameraActivity extends XcBaseActivity implements View.OnClickListener {
    private int mAdmin;
    private AoniCommonAlarmLogAdapter mAlarmAdapter;
    private CameraCommonAlarmListModel mAlarmListModel;
    private CheckBox mCbVoice;
    private CameraCommonLive mCommonPlay;
    private String mDeviceId;
    private String mDeviceName;
    private int mDeviceStatus;
    private LinearLayout mLlLogsLayout;
    private String mProductId;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvTodayLog;
    private TextView mTvHistory;
    private TextView mTvMoreLog;
    private TextView mTvTodayLogHint;
    private TextView mTvVoiceSetting;

    protected int getLayoutId() {
        return R.layout.activity_aoni_pocket_camera;
    }

    protected void initView() {
        getWindow().addFlags(ParserConstants.LSHIFTASSIGN);
        this.mTvHistory = (TextView) $(R.id.tv_camera_common_history_video);
        this.mTvVoiceSetting = (TextView) $(R.id.tv_camera_common_setting_video);
        this.mCbVoice = (CheckBox) $(R.id.cb_camera_common_voice);
        this.mCommonPlay = (CameraCommonLive) $(R.id.ccl_camera_pocket_live);
        this.mTvMoreLog = (TextView) $(R.id.tv_aoni_pocket_camera_more_log);
        this.mTvTodayLogHint = (TextView) $(R.id.tv_aoni_pocket_today_log_hint);
        this.mLlLogsLayout = (LinearLayout) $(R.id.ll_aoni_today_logs_layout);
        this.mRefreshLayout = (SmartRefreshLayout) $(R.id.srl_aoni_pocket_today_log);
        this.mRvTodayLog = (RecyclerView) $(R.id.rv_aoni_pocket_today_log);
        this.mRvTodayLog.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mDeviceName = getIntent().getStringExtra("deviceName");
        this.mProductId = getIntent().getStringExtra("productId");
        this.mAdmin = getIntent().getIntExtra("is_admin", 0);
        this.mDeviceStatus = getIntent().getIntExtra("deviceStatus", 0);
        if (!TextUtils.isEmpty(this.mDeviceId)) {
            this.mCommonPlay.initPlay(this.mActivity, this.mDeviceId, this.mProductId, this.mDeviceName, this.mDeviceStatus, this.mAdmin);
        }
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
        this.mAlarmAdapter = new AoniCommonAlarmLogAdapter();
        this.mRvTodayLog.setAdapter(this.mAlarmAdapter);
    }

    protected void onResume() {
        super.onResume();
        getCameraIndex(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getCameraIndex(boolean isRefresh) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/index");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.AoniPocketCameraActivity.1
            public void onComplete(XCResponseBean var1) {
                String liveUrl;
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniPocketCameraActivity.this.mAlarmListModel = (CameraCommonAlarmListModel) JSON.parseObject(var1.getData(), CameraCommonAlarmListModel.class);
                if (AoniPocketCameraActivity.this.mAlarmListModel == null || AoniPocketCameraActivity.this.mAlarmListModel.getList() == null || AoniPocketCameraActivity.this.mAlarmListModel.getList().size() == 0) {
                    AoniPocketCameraActivity.this.mRvTodayLog.setVisibility(8);
                    AoniPocketCameraActivity.this.mTvTodayLogHint.setVisibility(0);
                } else {
                    AoniPocketCameraActivity.this.mRvTodayLog.setVisibility(0);
                    AoniPocketCameraActivity.this.mTvTodayLogHint.setVisibility(8);
                    AoniPocketCameraActivity.this.mAlarmAdapter.setNewData(AoniPocketCameraActivity.this.mAlarmListModel.getList());
                    AoniPocketCameraActivity.this.mAlarmAdapter.notifyDataSetChanged();
                }
                CameraCommonLive cameraCommonLive = AoniPocketCameraActivity.this.mCommonPlay;
                if (AoniPocketCameraActivity.this.mAlarmListModel != null) {
                    liveUrl = AoniPocketCameraActivity.this.mAlarmListModel.getLiveUrl();
                } else {
                    liveUrl = Constants.MAIN_VERSION_TAG;
                }
                cameraCommonLive.initPlayVideo(liveUrl, AoniPocketCameraActivity.this.mAlarmListModel != null ? AoniPocketCameraActivity.this.mAlarmListModel.getSetStream() : 1, AoniPocketCameraActivity.this.mAlarmListModel != null ? AoniPocketCameraActivity.this.mAlarmListModel.getCodeStream() : 1);
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniPocketCameraActivity.this.mActivity, var1.getErrorMessage());
            }
        });
        if (isRefresh) {
            this.mRefreshLayout.finishRefresh();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadAlarmList() {
        long endTime = System.currentTimeMillis();
        long beginTime = TimeZoneUtil.stringToTime(TimeZoneUtil.timeToString(Long.valueOf(endTime), 3), 8).longValue();
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> noSignParams = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("endTime", endTime + Constants.MAIN_VERSION_TAG);
        params.put("beginTime", beginTime + Constants.MAIN_VERSION_TAG);
        noSignParams.put("pageSize", "50");
        noSignParams.put("queryId", this.mAlarmListModel == null ? Constants.MAIN_VERSION_TAG : this.mAlarmListModel.getQueryId());
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(noSignParams);
        httpSetting.setPath("camera/getAlarmList");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.AoniPocketCameraActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniPocketCameraActivity.this.mAlarmListModel = (CameraCommonAlarmListModel) JSON.parseObject(var1.getData(), CameraCommonAlarmListModel.class);
                if (AoniPocketCameraActivity.this.mAlarmListModel == null || AoniPocketCameraActivity.this.mAlarmListModel.getList() == null || AoniPocketCameraActivity.this.mAlarmListModel.getList().size() == 0) {
                    AoniPocketCameraActivity.this.mRvTodayLog.setVisibility(8);
                    AoniPocketCameraActivity.this.mTvTodayLogHint.setVisibility(0);
                } else {
                    AoniPocketCameraActivity.this.mRvTodayLog.setVisibility(0);
                    AoniPocketCameraActivity.this.mTvTodayLogHint.setVisibility(8);
                    AoniPocketCameraActivity.this.mAlarmAdapter.addData(AoniPocketCameraActivity.this.mAlarmListModel.getList());
                    AoniPocketCameraActivity.this.mAlarmAdapter.notifyDataSetChanged();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniPocketCameraActivity.this.mActivity, var1.getErrorMessage());
            }
        });
        this.mRefreshLayout.finishLoadMore();
        XcLogger.e("callbackDate", "endTime=" + endTime + "-beginTime=" + beginTime);
    }

    public void addListener() {
        super.addListener();
        this.mTvHistory.setOnClickListener(this);
        this.mTvVoiceSetting.setOnClickListener(this);
        this.mTvMoreLog.setOnClickListener(this);
        this.mCbVoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.AoniPocketCameraActivity.3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                }
            }
        });
        this.mRvTodayLog.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.AoniPocketCameraActivity.4
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_aoni_common_look_alarm /* 2131296935 */:
                        if (AoniPocketCameraActivity.this.mAlarmListModel != null) {
                            Intent intent = new Intent(AoniPocketCameraActivity.this.mActivity, (Class<?>) AoniCommonAlarmActivity.class);
                            intent.putExtra(Constants.FLAG_DEVICE_ID, AoniPocketCameraActivity.this.mDeviceId);
                            intent.putExtra("productId", AoniPocketCameraActivity.this.mProductId);
                            intent.putExtra("alarm", ((CameraCommonAlarmListModel.CameraAlarmModel) AoniPocketCameraActivity.this.mAlarmAdapter.getData().get(position)).getAlarm());
                            intent.putExtra("beginTime", ((CameraCommonAlarmListModel.CameraAlarmModel) AoniPocketCameraActivity.this.mAlarmAdapter.getData().get(position)).getBeginTime());
                            intent.putExtra("endTime", ((CameraCommonAlarmListModel.CameraAlarmModel) AoniPocketCameraActivity.this.mAlarmAdapter.getData().get(position)).getEndTime());
                            AoniPocketCameraActivity.this.startActivity(intent);
                        }
                        break;
                }
            }
        });
        this.mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.AoniPocketCameraActivity.5
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (AoniPocketCameraActivity.this.mAlarmListModel == null || !TextUtils.isEmpty(AoniPocketCameraActivity.this.mAlarmListModel.getQueryId())) {
                    AoniPocketCameraActivity.this.loadAlarmList();
                } else {
                    AoniPocketCameraActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                AoniPocketCameraActivity.this.getCameraIndex(true);
            }
        });
    }

    protected void onPause() {
        super.onPause();
        this.mCommonPlay.pause();
        XcLogger.e("AoniPocket", "onPause");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            String deviceName = data.getStringExtra("deviceName");
            this.mCommonPlay.setDeviceName(deviceName);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mCommonPlay.onDestory();
    }

    public void onBackPressed() {
        if (this.mCommonPlay.isFullScreen()) {
            this.mCommonPlay.onBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_aoni_pocket_camera_more_log /* 2131296944 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) AoniCommonMoreAlarmLogActiity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent.putExtra("productId", this.mProductId);
                startActivity(intent);
                break;
            case R.id.tv_camera_common_history_video /* 2131296955 */:
                Intent intent2 = new Intent(this.mActivity, (Class<?>) AoniCommonHistoryVideoActivity.class);
                intent2.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent2.putExtra("productId", this.mProductId);
                startActivity(intent2);
                break;
            case R.id.tv_camera_common_setting_video /* 2131296956 */:
                Intent intent3 = new Intent(this.mActivity, (Class<?>) AoniCommonSettingActivty.class);
                intent3.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent3.putExtra("productId", this.mProductId);
                startActivity(intent3);
                break;
        }
    }
}
