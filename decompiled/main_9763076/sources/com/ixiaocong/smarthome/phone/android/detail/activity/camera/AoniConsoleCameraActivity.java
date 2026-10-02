package com.ixiaocong.smarthome.phone.android.detail.activity.camera;

import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bsh.ParserConstants;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonHistoryVideoActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonMoreAlarmLogActiity;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonSettingActivty;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonLive;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.CameraCommonAlarmListModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.helper.ContrlPublishHelper;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniConsoleCameraActivity extends XcBaseActivity implements View.OnClickListener, View.OnTouchListener {
    private int mAdmin;
    private CameraCommonAlarmListModel mAlarmListModel;
    private CheckBox mCbVoice;
    private CameraCommonLive mCommonPlay;
    private RelativeLayout mConsoleEnd;
    private RelativeLayout mConsoleLeft;
    private RelativeLayout mConsoleRight;
    private RelativeLayout mConsoleTop;
    private String mContrlId = "action";
    private String mDeviceId;
    private String mDeviceName;
    private int mDeviceStatus;
    private String mProductId;
    private TextView mTvHistory;
    private TextView mTvLookAlarm;
    private TextView mTvVoiceSetting;

    protected int getLayoutId() {
        return R.layout.activity_aoni_console_camera;
    }

    protected void initView() {
        getWindow().addFlags(ParserConstants.LSHIFTASSIGN);
        this.mTvHistory = (TextView) $(R.id.tv_camera_common_history_video);
        this.mTvVoiceSetting = (TextView) $(R.id.tv_camera_common_setting_video);
        this.mCbVoice = (CheckBox) $(R.id.cb_camera_common_voice);
        this.mCommonPlay = (CameraCommonLive) $(R.id.ccl_camera_console_live);
        this.mTvLookAlarm = (TextView) $(R.id.tv_camera_console_look_alarm);
        this.mConsoleTop = (RelativeLayout) $(R.id.rl_camera_console_control_top);
        this.mConsoleEnd = (RelativeLayout) $(R.id.rl_camera_console_control_end);
        this.mConsoleLeft = (RelativeLayout) $(R.id.rl_camera_console_control_left);
        this.mConsoleRight = (RelativeLayout) $(R.id.rl_camera_console_control_right);
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

    protected void onResume() {
        super.onResume();
        getCameraIndex();
    }

    private void getCameraIndex() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/index");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.AoniConsoleCameraActivity.1
            public void onComplete(XCResponseBean var1) {
                String liveUrl;
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                AoniConsoleCameraActivity.this.mAlarmListModel = (CameraCommonAlarmListModel) JSON.parseObject(var1.getData(), CameraCommonAlarmListModel.class);
                CameraCommonLive cameraCommonLive = AoniConsoleCameraActivity.this.mCommonPlay;
                if (AoniConsoleCameraActivity.this.mAlarmListModel != null) {
                    liveUrl = AoniConsoleCameraActivity.this.mAlarmListModel.getLiveUrl();
                } else {
                    liveUrl = Constants.MAIN_VERSION_TAG;
                }
                cameraCommonLive.initPlayVideo(liveUrl, AoniConsoleCameraActivity.this.mAlarmListModel != null ? AoniConsoleCameraActivity.this.mAlarmListModel.getSetStream() : 1, AoniConsoleCameraActivity.this.mAlarmListModel != null ? AoniConsoleCameraActivity.this.mAlarmListModel.getCodeStream() : 1);
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniConsoleCameraActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mTvHistory.setOnClickListener(this);
        this.mTvVoiceSetting.setOnClickListener(this);
        this.mTvLookAlarm.setOnClickListener(this);
        this.mConsoleTop.setOnTouchListener(this);
        this.mConsoleEnd.setOnTouchListener(this);
        this.mConsoleLeft.setOnTouchListener(this);
        this.mConsoleRight.setOnTouchListener(this);
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
            case R.id.tv_camera_common_history_video /* 2131296955 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) AoniCommonHistoryVideoActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent.putExtra("productId", this.mProductId);
                startActivity(intent);
                break;
            case R.id.tv_camera_common_setting_video /* 2131296956 */:
                Intent intent2 = new Intent(this.mActivity, (Class<?>) AoniCommonSettingActivty.class);
                intent2.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent2.putExtra("productId", this.mProductId);
                startActivity(intent2);
                break;
            case R.id.tv_camera_console_look_alarm /* 2131296957 */:
                Intent intent3 = new Intent(this.mActivity, (Class<?>) AoniCommonMoreAlarmLogActiity.class);
                intent3.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent3.putExtra("productId", this.mProductId);
                startActivity(intent3);
                break;
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                switch (v.getId()) {
                    case R.id.rl_camera_console_control_end /* 2131296685 */:
                        ContrlPublishHelper.contrlPublish(this.mActivity, this.mDeviceId, this.mContrlId, "2");
                        break;
                    case R.id.rl_camera_console_control_left /* 2131296686 */:
                        ContrlPublishHelper.contrlPublish(this.mActivity, this.mDeviceId, this.mContrlId, "3");
                        break;
                    case R.id.rl_camera_console_control_right /* 2131296687 */:
                        ContrlPublishHelper.contrlPublish(this.mActivity, this.mDeviceId, this.mContrlId, "4");
                        break;
                    case R.id.rl_camera_console_control_top /* 2131296688 */:
                        ContrlPublishHelper.contrlPublish(this.mActivity, this.mDeviceId, this.mContrlId, "1");
                        XcLogger.e("AoniConsoleCamera", "control top");
                        break;
                }
                break;
            case 1:
                ContrlPublishHelper.contrlPublish(this.mActivity, this.mDeviceId, this.mContrlId, PushConstants.PUSH_TYPE_NOTIFY);
                XcLogger.e("AoniConsoleCamera", "control cancel");
                break;
        }
        return true;
    }
}
