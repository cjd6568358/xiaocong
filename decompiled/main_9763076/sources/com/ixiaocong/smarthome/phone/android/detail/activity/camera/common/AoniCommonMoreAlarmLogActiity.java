package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.TimeZoneUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.CalendarSelcetDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.AoniCommonAlarmLogAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
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
public class AoniCommonMoreAlarmLogActiity extends XcBaseActivity implements View.OnClickListener, EditDialogCallback {
    private AoniCommonAlarmLogAdapter mAlarmAdapter;
    private CameraCommonAlarmListModel mAlarmListModel;
    private long mBeginTime;
    private CalendarSelcetDialog mCalendarDialog;
    private LinearLayout mDateLayout;
    private String mDeviceId;
    private long mEndTime;
    private ImageView mIvBack;
    private String mProductId;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvAlarmLog;
    private TextView mTvAlarmLogHint;
    private TextView mTvCloudStorage;
    private TextView mTvSelectDate;

    protected int getLayoutId() {
        return R.layout.activity_aoni_common_more_alarm_log;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSelectDate = (TextView) $(R.id.tv_aoni_common_more_alarm_log_select_date);
        this.mTvCloudStorage = (TextView) $(R.id.tv_aoni_common_more_alarm_log_cloud_storage);
        this.mTvAlarmLogHint = (TextView) $(R.id.tv_aoni_common_more_alarm_log_hint);
        this.mDateLayout = (LinearLayout) $(R.id.ll_aoni_common_more_alarm_log_select_date);
        this.mRefreshLayout = (SmartRefreshLayout) $(R.id.srl_common_camera_more__alarm_log);
        this.mRvAlarmLog = (RecyclerView) $(R.id.rv_common_camera_more_alarm_log);
        this.mRvAlarmLog.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductId = getIntent().getStringExtra("productId");
        if (this.mProductId.equals("381788")) {
            this.mEndTime = System.currentTimeMillis();
            this.mBeginTime = TimeZoneUtil.stringToTime(TimeZoneUtil.timeToString(Long.valueOf(this.mEndTime), 3), 8).longValue();
            this.mTvSelectDate.setText(TimeZoneUtil.timeToString(Long.valueOf(System.currentTimeMillis()), 3));
        } else {
            this.mEndTime = TimeZoneUtil.stringToTime(TimeZoneUtil.timeToString(Long.valueOf(System.currentTimeMillis()), 3), 8).longValue();
            this.mBeginTime = TimeZoneUtil.stringToTime(TimeZoneUtil.getBeforeOneDayF(TimeZoneUtil.timeToString(Long.valueOf(this.mEndTime), 3)), 8).longValue();
            this.mTvSelectDate.setText(TimeZoneUtil.timeToString(Long.valueOf(this.mBeginTime), 3));
        }
        loadAlarmList(false);
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
        this.mRvAlarmLog.setAdapter(this.mAlarmAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadAlarmList(final boolean isRefresh) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> noSignParams = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("beginTime", this.mBeginTime + Constants.MAIN_VERSION_TAG);
        params.put("endTime", this.mEndTime + Constants.MAIN_VERSION_TAG);
        noSignParams.put("pageSize", "50");
        Object queryId = (this.mAlarmListModel == null || this.mAlarmListModel.getQueryId() == null) ? Constants.MAIN_VERSION_TAG : this.mAlarmListModel.getQueryId();
        noSignParams.put("queryId", queryId);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(noSignParams);
        httpSetting.setPath("camera/getAlarmList");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonMoreAlarmLogActiity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniCommonMoreAlarmLogActiity.this.mAlarmListModel = (CameraCommonAlarmListModel) JSON.parseObject(var1.getData(), CameraCommonAlarmListModel.class);
                if (AoniCommonMoreAlarmLogActiity.this.mAlarmListModel == null || AoniCommonMoreAlarmLogActiity.this.mAlarmListModel.getList() == null || AoniCommonMoreAlarmLogActiity.this.mAlarmListModel.getList().size() == 0) {
                    AoniCommonMoreAlarmLogActiity.this.mRvAlarmLog.setVisibility(8);
                    AoniCommonMoreAlarmLogActiity.this.mTvAlarmLogHint.setVisibility(0);
                    ToastUtils.showShort(AoniCommonMoreAlarmLogActiity.this.mActivity, "当日无告警记录...");
                } else {
                    AoniCommonMoreAlarmLogActiity.this.mRvAlarmLog.setVisibility(0);
                    AoniCommonMoreAlarmLogActiity.this.mTvAlarmLogHint.setVisibility(8);
                    if (isRefresh) {
                        AoniCommonMoreAlarmLogActiity.this.mAlarmAdapter.addData(AoniCommonMoreAlarmLogActiity.this.mAlarmListModel.getList());
                    } else {
                        AoniCommonMoreAlarmLogActiity.this.mAlarmAdapter.setNewData(AoniCommonMoreAlarmLogActiity.this.mAlarmListModel.getList());
                    }
                    AoniCommonMoreAlarmLogActiity.this.mAlarmAdapter.notifyDataSetChanged();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniCommonMoreAlarmLogActiity.this.mActivity, var1.getErrorMessage());
            }
        });
        if (isRefresh) {
            this.mRefreshLayout.finishRefresh();
        } else {
            this.mRefreshLayout.finishLoadMore();
        }
        XcLogger.e("callbackDate", "endTime=" + this.mEndTime + "-beginTime=" + this.mBeginTime);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mDateLayout.setOnClickListener(this);
        this.mTvCloudStorage.setOnClickListener(this);
        this.mRvAlarmLog.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonMoreAlarmLogActiity.2
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_aoni_common_look_alarm /* 2131296935 */:
                        if (AoniCommonMoreAlarmLogActiity.this.mAlarmListModel != null) {
                            Intent intent = new Intent(AoniCommonMoreAlarmLogActiity.this.mActivity, (Class<?>) AoniCommonAlarmActivity.class);
                            intent.putExtra(Constants.FLAG_DEVICE_ID, AoniCommonMoreAlarmLogActiity.this.mDeviceId);
                            intent.putExtra("productId", AoniCommonMoreAlarmLogActiity.this.mProductId);
                            intent.putExtra("alarm", ((CameraCommonAlarmListModel.CameraAlarmModel) AoniCommonMoreAlarmLogActiity.this.mAlarmAdapter.getData().get(position)).getAlarm());
                            intent.putExtra("beginTime", ((CameraCommonAlarmListModel.CameraAlarmModel) AoniCommonMoreAlarmLogActiity.this.mAlarmAdapter.getData().get(position)).getBeginTime());
                            intent.putExtra("endTime", ((CameraCommonAlarmListModel.CameraAlarmModel) AoniCommonMoreAlarmLogActiity.this.mAlarmAdapter.getData().get(position)).getEndTime());
                            intent.putExtra("noService", AoniCommonMoreAlarmLogActiity.this.mAlarmListModel.getNoService());
                            intent.putExtra("noServiceMsg", AoniCommonMoreAlarmLogActiity.this.mAlarmListModel.getNoServicePrompt());
                            AoniCommonMoreAlarmLogActiity.this.startActivity(intent);
                        }
                        break;
                }
            }
        });
        this.mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonMoreAlarmLogActiity.3
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (AoniCommonMoreAlarmLogActiity.this.mAlarmListModel == null || !TextUtils.isEmpty(AoniCommonMoreAlarmLogActiity.this.mAlarmListModel.getQueryId())) {
                    AoniCommonMoreAlarmLogActiity.this.loadAlarmList(true);
                } else {
                    AoniCommonMoreAlarmLogActiity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.ll_aoni_common_more_alarm_log_select_date /* 2131296576 */:
                if (this.mCalendarDialog == null) {
                    this.mCalendarDialog = new CalendarSelcetDialog(this.mActivity, AoniCommonMoreAlarmLogActiity$$Lambda$1.lambdaFactory$(this), 2018);
                }
                this.mCalendarDialog.show();
                break;
            case R.id.tv_aoni_common_more_alarm_log_cloud_storage /* 2131296936 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) AoniCommonCloudStorageActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent.putExtra("productId", this.mProductId);
                startActivity(intent);
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback
    public void editMsgCallback(String editMsg) {
        if (!TextUtils.isEmpty(editMsg)) {
            this.mTvSelectDate.setText(editMsg);
            this.mBeginTime = TimeZoneUtil.stringToTime(editMsg, 8).longValue();
            this.mEndTime = TimeZoneUtil.stringToTime(TimeZoneUtil.getAfterOneDayF(TimeZoneUtil.timeToString(Long.valueOf(this.mBeginTime), 3)), 8).longValue();
            loadAlarmList(false);
        }
    }
}
