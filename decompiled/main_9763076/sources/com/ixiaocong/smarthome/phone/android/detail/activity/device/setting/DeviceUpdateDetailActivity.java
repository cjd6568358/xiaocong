package com.ixiaocong.smarthome.phone.android.detail.activity.device.setting;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleProgressBar;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DeviceSdkDetailModel;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceUpdateDetailActivity extends XcBaseActivity implements View.OnClickListener, HintDialogCallback {
    private Button mBtnCommit;
    private String mDeviceId;
    private TextView mDeviceSdkDescription;
    private ImageView mIvBack;
    private CircleProgressBar mProgress;
    private int mSdkId;
    private String mSystemDeviceVersion;
    private TextView mTvHintMsg;
    private TextView mTvOwnerDeviceVersion;
    private TextView mTvSystemDeviceVersion;
    private MyCountdown myCountdown;
    private int updateState = 0;
    private int percentage = 2;

    protected int getLayoutId() {
        return R.layout.activity_device_update_detail;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvOwnerDeviceVersion = (TextView) $(R.id.tv_device_update_detail_owner_sdk_version);
        this.mTvSystemDeviceVersion = (TextView) $(R.id.tv_device_update_detail_system_sdk_version);
        this.mDeviceSdkDescription = (TextView) $(R.id.tv_device_update_detail_sdk_description);
        this.mBtnCommit = (Button) $(R.id.btn_device_update_commit);
        this.mTvHintMsg = (TextView) $(R.id.tv_device_update_detail_hint);
        this.mProgress = (CircleProgressBar) $(R.id.cpb_device_update_detail_schedule);
        this.mProgress.setUnit("%");
        this.mProgress.setCenterText(PushConstants.PUSH_TYPE_NOTIFY);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mSdkId = getIntent().getIntExtra("deviceSdkId", 0);
        getDeviceSdkDetail(this.mDeviceId, this.mSdkId);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mBtnCommit.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_device_update_commit /* 2131296324 */:
                if (this.updateState == 0) {
                    startOTA();
                } else if (this.updateState == 2) {
                    finish();
                } else if (this.updateState == 3) {
                    this.percentage = 2;
                    startOTA();
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
        }
    }

    protected void start() {
        this.updateState = 1;
        this.mBtnCommit.setClickable(false);
        this.mBtnCommit.setText("正在升级");
        this.mBtnCommit.setTextColor(getResources().getColor(R.color.press_down_color));
        this.myCountdown = new MyCountdown(300000L, 3000L);
        this.myCountdown.start();
    }

    protected void end() {
        if (this.myCountdown != null) {
            this.myCountdown.cancel();
        }
        this.updateState = 2;
        this.mProgress.setCenterText("100");
        this.mProgress.setProgressNotInUiThread(100);
        this.mTvHintMsg.setVisibility(8);
        this.mBtnCommit.setClickable(true);
        this.mBtnCommit.setText("确定");
        this.mBtnCommit.setTextColor(getResources().getColor(R.color.master_color));
        OperationHintDialog.getInstance().showHintDialog(this.mActivity, this, "提示", "固件升级成功", "确定");
    }

    protected void failed() {
        this.updateState = 3;
        this.mProgress.setCenterText(PushConstants.PUSH_TYPE_NOTIFY);
        this.mProgress.setProgressNotInUiThread(0);
        this.mBtnCommit.setClickable(true);
        ToastUtils.showShort(this.mActivity, "升级失败,请重试!");
        this.mBtnCommit.setText("升级失败,点击重试");
        this.mBtnCommit.setTextColor(getResources().getColor(R.color.master_color));
    }

    private void getDeviceSdkDetail(String deviceId, int sdkId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("sdkId", sdkId + Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/sdk/detail");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceUpdateDetailActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                DeviceSdkDetailModel deviceSdkDetailModel = (DeviceSdkDetailModel) JSON.parseObject(var1.getData(), DeviceSdkDetailModel.class);
                DeviceUpdateDetailActivity.this.mTvOwnerDeviceVersion.setText("您的设备版本：" + deviceSdkDetailModel.getDeviceVersion());
                DeviceUpdateDetailActivity.this.mTvSystemDeviceVersion.setText("系统最新版本：" + deviceSdkDetailModel.getSdkVersion());
                DeviceUpdateDetailActivity.this.mSystemDeviceVersion = deviceSdkDetailModel.getSdkVersion();
                DeviceUpdateDetailActivity.this.mDeviceSdkDescription.setText(deviceSdkDetailModel.getSdkIntro());
                DeviceUpdateDetailActivity.this.updateState = deviceSdkDetailModel.getStatus();
                if (DeviceUpdateDetailActivity.this.updateState == 1) {
                    int schedule = ((Integer) SpUtils.getFromLocal(DeviceUpdateDetailActivity.this.mActivity, "ixiaocong_config", "device_update_schedule", 79)).intValue();
                    DeviceUpdateDetailActivity.this.percentage = ((int) (((long) schedule) + DeviceUpdateDetailActivity.this.getTimeChange().longValue())) - 3;
                    XcLogger.i("DeviceUpdateDetailActivity", "percentage=" + DeviceUpdateDetailActivity.this.percentage);
                    DeviceUpdateDetailActivity.this.start();
                }
                if (deviceSdkDetailModel.getDeviceVersion().equals(deviceSdkDetailModel.getSdkVersion())) {
                    DeviceUpdateDetailActivity.this.end();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                DeviceUpdateDetailActivity.this.failed();
                ToastUtils.showShort(DeviceUpdateDetailActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getSdkVersion() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/detail/mini");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceUpdateDetailActivity.2
            public void onComplete(XCResponseBean var1) {
                try {
                    JSONObject jsonObject = JSON.parseObject(var1.getData());
                    String deviceVersion = jsonObject.getString("deviceVersion");
                    XcLogger.i("DeviceUpdateDetailActivity", "deviceVersion=" + deviceVersion);
                    XcLogger.i("DeviceUpdateDetailActivity", "mSystemDeviceVersion=" + DeviceUpdateDetailActivity.this.mSystemDeviceVersion);
                    if (deviceVersion.equals(DeviceUpdateDetailActivity.this.mSystemDeviceVersion)) {
                        DeviceUpdateDetailActivity.this.mTvOwnerDeviceVersion.setText("您的设备版本：" + deviceVersion);
                        DeviceUpdateDetailActivity.this.end();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onError(XCErrorMessage var1) {
            }
        });
    }

    protected void startOTA() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("sdkId", this.mSdkId + Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/sdk/upgrade");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceUpdateDetailActivity.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceUpdateDetailActivity.this.mActivity, "开始升级...");
                DeviceUpdateDetailActivity.this.start();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceUpdateDetailActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    private Long getTime() {
        long timeStamp = 0;
        try {
            timeStamp = System.currentTimeMillis() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.valueOf(timeStamp);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Long getTimeChange() {
        Long oldTime = (Long) SpUtils.getFromLocal(this.mActivity, "ixiaocong_config", "device_update_time", new Long(0L));
        Long timeChange = Long.valueOf(getTime().longValue() - oldTime.longValue());
        if (timeChange.longValue() < 0) {
            timeChange = 0L;
        }
        XcLogger.i("DeviceUpdateDetailActivity", "getTimeChange=" + timeChange);
        return timeChange;
    }

    protected void onStop() {
        super.onStop();
        if (this.myCountdown != null) {
            this.myCountdown.cancel();
        }
        SpUtils.saveToLocal(this.mActivity, "ixiaocong_config", "device_update_schedule", Integer.valueOf(this.percentage));
        SpUtils.saveToLocal(this.mActivity, "ixiaocong_config", "device_update_time", getTime());
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        finish();
    }

    class MyCountdown extends CountDownTimer {
        public MyCountdown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            DeviceUpdateDetailActivity.this.mProgress.setCenterText((DeviceUpdateDetailActivity.this.percentage > 99 ? 99 : DeviceUpdateDetailActivity.this.percentage) + Constants.MAIN_VERSION_TAG);
            DeviceUpdateDetailActivity.this.mProgress.setProgressNotInUiThread(DeviceUpdateDetailActivity.this.percentage <= 99 ? DeviceUpdateDetailActivity.this.percentage : 99);
            DeviceUpdateDetailActivity.this.percentage += 3;
            XcLogger.i("DeviceUpdateDetailActivity", millisUntilFinished + ";percentage=" + DeviceUpdateDetailActivity.this.percentage);
            DeviceUpdateDetailActivity.this.getSdkVersion();
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            DeviceUpdateDetailActivity.this.failed();
        }
    }
}
