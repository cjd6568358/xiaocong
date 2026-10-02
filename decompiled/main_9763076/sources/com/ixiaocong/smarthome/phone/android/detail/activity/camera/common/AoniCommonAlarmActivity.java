package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.TimeZoneUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonReplay;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.CameraCommonPlayStreamModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonAlarmActivity extends XcBaseActivity implements CommonTypeCallback {
    private CameraCommonReplay mCommonReplay;
    private String mDeviceId;
    private ImageView mIvBack;
    private String mProductId;
    private CameraCommonPlayStreamModel mStreamModel;
    private TextView mTvAlarmCloud;
    private TextView mTvAlarmDate;
    private TextView mTvAlarmName;

    protected int getLayoutId() {
        return R.layout.activity_aoni_common_alarm;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvAlarmDate = (TextView) $(R.id.tv_aoni_common_alarm_detail_date);
        this.mTvAlarmName = (TextView) $(R.id.tv_aoni_common_alarm_detail_name);
        this.mTvAlarmCloud = (TextView) $(R.id.tv_aoni_common_alarm_detail_cloud);
        this.mCommonReplay = (CameraCommonReplay) $(R.id.ccr_common_alarm_replay);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductId = getIntent().getStringExtra("productId");
        String beginTime = getIntent().getStringExtra("beginTime");
        String endTime = getIntent().getStringExtra("endTime");
        String noServiceMsg = getIntent().getStringExtra("noServiceMsg");
        int noService = getIntent().getIntExtra("noService", 0);
        int alarm = getIntent().getIntExtra("alarm", 0);
        this.mCommonReplay.initReplay(this.mActivity, "移动告警");
        this.mCommonReplay.setTypeCallback(AoniCommonAlarmActivity$$Lambda$1.lambdaFactory$(this));
        if (alarm == 0) {
            this.mTvAlarmName.setText("无告警");
        } else {
            this.mTvAlarmName.setText("移动告警");
        }
        this.mTvAlarmDate.setText(TimeZoneUtil.timeToString(Long.valueOf(Long.parseLong(beginTime)), 2));
        if (noService == 1) {
            this.mCommonReplay.setVisibleActionIcon(false);
            ToastUtils.showShort(this.mActivity, noServiceMsg);
        } else {
            loadStreamUrl(beginTime, endTime);
        }
    }

    private void loadStreamUrl(String beginTime, String endTime) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/getPlayStreamUrl");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonAlarmActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniCommonAlarmActivity.this.mStreamModel = (CameraCommonPlayStreamModel) JSON.parseObject(var1.getData(), CameraCommonPlayStreamModel.class);
                if (AoniCommonAlarmActivity.this.mStreamModel == null) {
                    ToastUtils.showShort(AoniCommonAlarmActivity.this.mActivity, "加载失败...");
                    AoniCommonAlarmActivity.this.mCommonReplay.setVisibleActionIcon(false);
                    return;
                }
                if (AoniCommonAlarmActivity.this.mStreamModel.getNoService() == 1) {
                    ToastUtils.showShort(AoniCommonAlarmActivity.this.mActivity, AoniCommonAlarmActivity.this.mStreamModel.getNoServicePrompt());
                    AoniCommonAlarmActivity.this.mCommonReplay.setVisibleActionIcon(false);
                } else if (AoniCommonAlarmActivity.this.mStreamModel.getList() == null || AoniCommonAlarmActivity.this.mStreamModel.getList().size() == 0) {
                    ToastUtils.showShort(AoniCommonAlarmActivity.this.mActivity, "暂未发现告警录像");
                    AoniCommonAlarmActivity.this.mCommonReplay.setVisibleActionIcon(false);
                } else {
                    AoniCommonAlarmActivity.this.mCommonReplay.setVisibleActionIcon(true);
                    AoniCommonAlarmActivity.this.mCommonReplay.replayVideo(((CameraCommonPlayStreamModel.PlayStreamModel) AoniCommonAlarmActivity.this.mStreamModel.getList().get(0)).getPlayUrl(), 0);
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniCommonAlarmActivity.this.mCommonReplay.setVisibleActionIcon(false);
                ToastUtils.showShort(AoniCommonAlarmActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(AoniCommonAlarmActivity$$Lambda$2.lambdaFactory$(this));
        this.mTvAlarmCloud.setOnClickListener(AoniCommonAlarmActivity$$Lambda$3.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(View v) {
        Intent intent = new Intent(this.mActivity, (Class<?>) AoniCommonCloudStorageActivity.class);
        intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        intent.putExtra("productId", this.mProductId);
        startActivity(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        this.mCommonReplay.onDestory();
    }

    public void onBackPressed() {
        if (this.mCommonReplay.isFullScreen()) {
            this.mCommonReplay.onBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    public void resultTypeCalllback(int type) {
        if (this.mStreamModel != null && this.mStreamModel.getList() != null && this.mStreamModel.getList().size() > 0 && type >= 0) {
            if (type < this.mStreamModel.getList().size() - 1) {
                this.mCommonReplay.replayVideo(((CameraCommonPlayStreamModel.PlayStreamModel) this.mStreamModel.getList().get(type + 1)).getPlayUrl(), type + 1);
            } else {
                this.mCommonReplay.pause();
                ToastUtils.showShort(this.mActivity, "播发完毕");
            }
        }
    }
}
