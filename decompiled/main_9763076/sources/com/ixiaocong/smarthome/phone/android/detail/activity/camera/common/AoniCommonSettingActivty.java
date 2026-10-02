package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonMakeTimePop;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.CameraCommonConfigModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.switchbutton.SwitchButton;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonSettingActivty extends XcBaseActivity implements CommonTypeCallback {
    private CameraCommonConfigModel mConfigModel;
    private String mDeviceId;
    private ImageView mIvBack;
    private LinearLayout mLlMakeTime;
    private String mProductId;
    private SwitchButton mSbtnSetAlarm;
    private SwitchButton mSbtnTranscribe;
    private TextView mTvCameraSwitch;
    private TextView mTvMakeTime;
    private TextView mTvSetStream;
    private TextView mTvSetStreamMsg;

    protected int getLayoutId() {
        return R.layout.activity_aoni_common_setting;
    }

    protected void initView() {
        this.mSbtnSetAlarm = $(R.id.sbtn_aoni_common_set_alarm);
        this.mSbtnTranscribe = $(R.id.sbtn_aoni_common_set_transcribe);
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mLlMakeTime = (LinearLayout) $(R.id.ll_aoni_common_transcribe_time);
        this.mTvMakeTime = (TextView) $(R.id.tv_aoni_common_set_transcribe_time);
        this.mTvCameraSwitch = (TextView) $(R.id.tv_pocket_camera_switch);
        this.mTvSetStream = (TextView) $(R.id.tv_aoni_common_set_transcribe);
        this.mTvSetStreamMsg = (TextView) $(R.id.tv_aoni_common_set_transcribe_msg);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductId = getIntent().getStringExtra("productId");
        if (!TextUtils.isEmpty(this.mDeviceId)) {
            requestConfig();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestConfig() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/config/detail");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonSettingActivty.1
            public void onComplete(XCResponseBean var1) {
                XcLogger.e("getCameraConfig---", var1.getData());
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    AoniCommonSettingActivty.this.mConfigModel = (CameraCommonConfigModel) JSON.parseObject(var1.getData(), CameraCommonConfigModel.class);
                    if (AoniCommonSettingActivty.this.mConfigModel != null) {
                        if (AoniCommonSettingActivty.this.mConfigModel.getSetAlarm() == 0) {
                            AoniCommonSettingActivty.this.mSbtnSetAlarm.setCheckedImmediatelyNoEvent(false);
                            AoniCommonSettingActivty.this.mTvMakeTime.setText("-/-");
                        } else {
                            AoniCommonSettingActivty.this.mSbtnSetAlarm.setCheckedImmediatelyNoEvent(true);
                        }
                        if (AoniCommonSettingActivty.this.mConfigModel.getSetStream() == 1) {
                            AoniCommonSettingActivty.this.mSbtnTranscribe.setCheckedImmediatelyNoEvent(false);
                            AoniCommonSettingActivty.this.mTvSetStreamMsg.setText("关闭后,摄像头持续录制视频");
                            AoniCommonSettingActivty.this.mTvMakeTime.setText("-/-");
                        } else {
                            AoniCommonSettingActivty.this.mSbtnTranscribe.setCheckedImmediatelyNoEvent(true);
                            AoniCommonSettingActivty.this.mTvSetStreamMsg.setText("开启后,摄像头仅移动侦测告警时录制视频");
                            if (AoniCommonSettingActivty.this.mConfigModel.getStreamTime() > 0) {
                                AoniCommonSettingActivty.this.mTvMakeTime.setText(AoniCommonSettingActivty.this.mConfigModel.getStreamTime() + "秒");
                            } else {
                                AoniCommonSettingActivty.this.mTvMakeTime.setText("-/-");
                            }
                        }
                    }
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(AoniCommonSettingActivty.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(AoniCommonSettingActivty$$Lambda$1.lambdaFactory$(this));
        this.mLlMakeTime.setOnClickListener(AoniCommonSettingActivty$$Lambda$2.lambdaFactory$(this));
        this.mSbtnTranscribe.setOnCheckedChangeListener(AoniCommonSettingActivty$$Lambda$3.lambdaFactory$(this));
        this.mSbtnSetAlarm.setOnCheckedChangeListener(AoniCommonSettingActivty$$Lambda$4.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(View v) {
        if (!this.mSbtnSetAlarm.isChecked()) {
            ToastUtils.showShort(this.mActivity, "请先设置移动侦测为开启状态");
            return;
        }
        if (!this.mSbtnTranscribe.isChecked()) {
            ToastUtils.showShort(this.mActivity, "持续录制状态下无需选择该选项");
        } else if (this.mConfigModel != null) {
            CameraCommonMakeTimePop.getInstance().initCommonView(this.mActivity, this.mIvBack, AoniCommonSettingActivty$$Lambda$5.lambdaFactory$(this), this.mConfigModel, this.mDeviceId);
        } else {
            ToastUtils.showShort(this.mActivity, "当前未获取设备参数");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$2(CompoundButton buttonView, boolean isChecked) {
        if (this.mConfigModel != null) {
            if (isChecked) {
                if (!this.mSbtnSetAlarm.isChecked()) {
                    ToastUtils.showShort(this.mActivity, "请先设置移动侦测为开启状态");
                    this.mSbtnTranscribe.setCheckedImmediatelyNoEvent(false);
                    return;
                } else {
                    cameraConfigUpdate(1, 0, this.mConfigModel.getStreamTime(), false);
                    return;
                }
            }
            cameraConfigUpdate(1, 1, this.mConfigModel.getStreamTime(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$3(CompoundButton buttonView, boolean isChecked) {
        if (this.mConfigModel != null) {
            if (isChecked) {
                cameraConfigUpdate(1, this.mConfigModel.getSetStream(), this.mConfigModel.getStreamTime(), true);
            } else {
                cameraConfigUpdate(0, this.mConfigModel.getSetStream(), this.mConfigModel.getStreamTime(), true);
            }
        }
    }

    private void cameraConfigUpdate(final int alarmStatus, final int transctibeStatus, int streamTime, final boolean isAlarm) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("setAlarm", alarmStatus + Constants.MAIN_VERSION_TAG);
        params.put("setStream", transctibeStatus + Constants.MAIN_VERSION_TAG);
        params.put("streamTime", streamTime + Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/config/update");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonSettingActivty.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                if (isAlarm) {
                    if (alarmStatus == 1) {
                        ToastUtils.showShort(AoniCommonSettingActivty.this.mActivity, "已开启移动侦测");
                    } else {
                        ToastUtils.showShort(AoniCommonSettingActivty.this.mActivity, "已关闭移动侦测");
                    }
                } else if (transctibeStatus == 1) {
                    ToastUtils.showShort(AoniCommonSettingActivty.this.mActivity, "已开启持续推流");
                } else {
                    ToastUtils.showShort(AoniCommonSettingActivty.this.mActivity, "已开启告警推流");
                }
                AoniCommonSettingActivty.this.requestConfig();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniCommonSettingActivty.this.mActivity, "功能设置失败");
                AoniCommonSettingActivty.this.requestConfig();
            }
        });
    }

    public void onBackPressed() {
        if (CameraCommonMakeTimePop.getInstance().isShowing()) {
            CameraCommonMakeTimePop.getInstance().dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    public void resultTypeCalllback(int type) {
        this.mTvMakeTime.setText(type + "秒");
        requestConfig();
    }
}
