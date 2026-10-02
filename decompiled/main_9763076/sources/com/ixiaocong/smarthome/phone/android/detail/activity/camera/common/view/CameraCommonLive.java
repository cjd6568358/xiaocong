package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.baiducam.bdplayer.widget.BDCloudVideoView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.utils.CameraControlHttpUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceDetailSettingActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceUpdateDetailActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DeviceSdkCheckModel;
import com.xiaocong.smarthome.httplib.model.LoadLiveUrlModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.DeviceStatusReceiver;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import videocontrollerview.VideoControllerView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CameraCommonLive extends ConstraintLayout implements View.OnClickListener, CommonTypeCallback, VideoControllerView.MediaPlayerControlListener {
    private boolean isFullScreen;
    private Activity mActivity;
    private int mAdmin;
    BroadcastReceiver mBatInfoReceiver;
    private CheckBox mCbScreen;
    private int mCodeStream;
    private Context mContext;
    private String mDeviceId;
    private String mDeviceName;
    private boolean mIsComplete;
    private KeepLiveCountDown mKeepLiveCountDown1;
    private ImageView mLeftBack;
    private OrientationEventListener mOrientationEventListener;
    private String mProductId;
    private ImageView mRightSetting;
    private RelativeLayout mRlStatus;
    private ImageView mScreenBack;
    private int mScreenChange;
    DeviceStatusReceiver mStatusReceiver;
    private TextView mTitleText;
    private TextView mTvCodeStream;
    private TextView mTvHintRed;
    private BDCloudVideoView mVideoView;
    private ViewGroup.LayoutParams mVideolayoutParams;

    public CameraCommonLive(Context context) {
        super(context);
        this.isFullScreen = false;
        this.mStatusReceiver = new AnonymousClass4();
        this.mBatInfoReceiver = new BroadcastReceiver() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonLive.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                XcLogger.e("BatInfoReceiver", action);
                switch (action) {
                    case "android.intent.action.SCREEN_OFF":
                    case "android.intent.action.CLOSE_SYSTEM_DIALOGS":
                        CameraCommonLive.this.mVideoView.pause();
                        break;
                    case "android.intent.action.SCREEN_ON":
                    case "android.intent.action.USER_PRESENT":
                        CameraCommonLive.this.mVideoView.start();
                        break;
                }
            }
        };
        this.mContext = context;
        initCommonPlay();
    }

    public CameraCommonLive(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isFullScreen = false;
        this.mStatusReceiver = new AnonymousClass4();
        this.mBatInfoReceiver = new BroadcastReceiver() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonLive.5
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                XcLogger.e("BatInfoReceiver", action);
                switch (action) {
                    case "android.intent.action.SCREEN_OFF":
                    case "android.intent.action.CLOSE_SYSTEM_DIALOGS":
                        CameraCommonLive.this.mVideoView.pause();
                        break;
                    case "android.intent.action.SCREEN_ON":
                    case "android.intent.action.USER_PRESENT":
                        CameraCommonLive.this.mVideoView.start();
                        break;
                }
            }
        };
        this.mContext = context;
        initCommonPlay();
    }

    private void initCommonPlay() {
        LayoutInflater.from(this.mContext).inflate(R.layout.layout_common_camera_live, this);
        this.mLeftBack = (ImageView) findViewById(R.id.left_titlebar_image);
        this.mRightSetting = (ImageView) findViewById(R.id.right_titlebar_image);
        this.mTitleText = (TextView) findViewById(R.id.centertxt_titlebar);
        this.mTvHintRed = (TextView) findViewById(R.id.right_titlebar_hint_text);
        this.mRlStatus = (RelativeLayout) findViewById(R.id.rl_dev_status_hint);
        this.mScreenBack = (ImageView) findViewById(R.id.iv_common_camera_live_screen_back);
        this.mTvCodeStream = (TextView) findViewById(R.id.tv_common_camera_live_code_stream);
        this.mCbScreen = (CheckBox) findViewById(R.id.cb_common_camera_live_vertical_screen_switch);
        this.mVideoView = (BDCloudVideoView) findViewById(R.id.bdcvv_common_camera_live_videoview);
        this.mVideolayoutParams = this.mVideoView.getLayoutParams();
        this.mVideoView.setLogEnabled(false);
        initControl();
        addListener();
    }

    private void addListener() {
        this.mLeftBack.setOnClickListener(this);
        this.mRightSetting.setOnClickListener(this);
        this.mTvCodeStream.setOnClickListener(this);
        this.mScreenBack.setOnClickListener(this);
        this.mCbScreen.setOnCheckedChangeListener(CameraCommonLive$$Lambda$1.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            this.mActivity.setRequestedOrientation(1);
            setVerticalScreen();
            this.mCbScreen.setChecked(true);
            this.mCbScreen.setVisibility(0);
            return;
        }
        this.mActivity.setRequestedOrientation(0);
        setFullScreen();
        this.mCbScreen.setChecked(false);
        this.mCbScreen.setVisibility(8);
    }

    private void initControl() {
        this.mVideoView.setVideoScalingMode(3);
    }

    public void initPlay(Activity activity, String deviceId, String productId, String deviceName, int deviceStatus, int admin) {
        this.mActivity = activity;
        this.mDeviceId = deviceId;
        this.mDeviceName = deviceName;
        this.mProductId = productId;
        this.mAdmin = admin;
        oreationChange();
        LocalBroadcastManager.getInstance(this.mActivity).registerReceiver(this.mStatusReceiver, new IntentFilter("ACTION_UPDATE_STATUS"));
        getDeviceStatus(deviceStatus, false);
        register(this.mActivity);
        getDeviceSdkVesion(deviceId);
        if (!TextUtils.isEmpty(deviceName)) {
            this.mTitleText.setText(deviceName);
        } else {
            this.mTitleText.setText("摄像头");
        }
    }

    public void initPlayVideo(String liveUrl, int setStream, int codeStream) {
        if (!TextUtils.isEmpty(liveUrl)) {
            setVisibleActionIcon(0);
            if (!this.mVideoView.isPlaying()) {
                this.mVideoView.setVideoPath(liveUrl);
                this.mVideoView.start();
            }
            if (setStream == 0) {
                countDownKeepLive();
            }
            XcLogger.e("loadLiveUrl", liveUrl);
        } else {
            setVisibleActionIcon(8);
        }
        this.mCodeStream = codeStream;
        if (codeStream == 0) {
            this.mTvCodeStream.setText("标清");
        } else {
            this.mTvCodeStream.setText("高清");
        }
    }

    public void onLinePlayVideo(String deviceId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/getLiveStreamUrl");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mContext);
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonLive.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                LoadLiveUrlModel liveUrlModel = (LoadLiveUrlModel) JSON.parseObject(var1.getData(), LoadLiveUrlModel.class);
                if (liveUrlModel == null) {
                    CameraCommonLive.this.setVisibleActionIcon(8);
                    return;
                }
                if (!TextUtils.isEmpty(liveUrlModel.getLiveUrl())) {
                    CameraCommonLive.this.setVisibleActionIcon(0);
                    if (!CameraCommonLive.this.mVideoView.isPlaying()) {
                        CameraCommonLive.this.mVideoView.setVideoPath(liveUrlModel.getLiveUrl());
                        CameraCommonLive.this.mVideoView.start();
                    }
                    if (liveUrlModel.getSetStream() == 0) {
                        CameraCommonLive.this.countDownKeepLive();
                    }
                    XcLogger.e("loadLiveUrl", liveUrlModel.getLiveUrl());
                } else {
                    CameraCommonLive.this.setVisibleActionIcon(8);
                }
                CameraCommonLive.this.mCodeStream = liveUrlModel.getCodeStream();
                if (liveUrlModel.getCodeStream() == 0) {
                    CameraCommonLive.this.mTvCodeStream.setText("标清");
                } else {
                    CameraCommonLive.this.mTvCodeStream.setText("高清");
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(CameraCommonLive.this.mContext, var1.getErrorMessage());
                CameraCommonLive.this.setVisibleActionIcon(8);
            }
        });
    }

    private void getDeviceSdkVesion(String deviceId) {
        if (!TextUtils.isEmpty(deviceId)) {
            XCHttpSetting httpSetting = new XCHttpSetting();
            HashMap<String, Object> params = new HashMap<>();
            params.put(Constants.FLAG_DEVICE_ID, deviceId);
            httpSetting.setParamsMap(params);
            httpSetting.setPath("device/sdk/check");
            XCRequest.getInstance().request(this.mActivity, httpSetting, new AnonymousClass2());
        }
    }

    /* JADX INFO: renamed from: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonLive$2, reason: invalid class name */
    class AnonymousClass2 implements XCDataCallback<XCResponseBean> {
        AnonymousClass2() {
        }

        public void onComplete(XCResponseBean var1) {
            DeviceSdkCheckModel deviceSdkCheckModel = (DeviceSdkCheckModel) JSON.parseObject(var1.getData(), DeviceSdkCheckModel.class);
            if (deviceSdkCheckModel.getUpgrade() == 1) {
                CameraCommonLive.this.mTvHintRed.setVisibility(0);
            } else {
                CameraCommonLive.this.mTvHintRed.setVisibility(8);
            }
            if (!TextUtils.isEmpty(deviceSdkCheckModel.getUpgradeMsg()) && deviceSdkCheckModel.getSdkId() > 0) {
                OperationHintDialog.getInstance().showSelectDialog(CameraCommonLive.this.mActivity, CameraCommonLive$2$$Lambda$1.lambdaFactory$(this, deviceSdkCheckModel), Constants.MAIN_VERSION_TAG, deviceSdkCheckModel.getUpgradeMsg());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onComplete$0(DeviceSdkCheckModel deviceSdkCheckModel, boolean isSuccess) {
            Intent intent = new Intent();
            intent.setClass(CameraCommonLive.this.mActivity, DeviceUpdateDetailActivity.class);
            intent.putExtra(Constants.FLAG_DEVICE_ID, CameraCommonLive.this.mDeviceId);
            intent.putExtra("deviceSdkId", deviceSdkCheckModel.getSdkId());
            CameraCommonLive.this.mActivity.startActivity(intent);
        }

        public void onError(XCErrorMessage var1) {
            XcLogger.e("RNDetailActivity", var1.getErrorMessage());
        }
    }

    private void register(Activity activity) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.USER_PRESENT");
        filter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        activity.registerReceiver(this.mBatInfoReceiver, filter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void countDownKeepLive() {
        this.mKeepLiveCountDown1 = new KeepLiveCountDown(120000L, 1000L);
        this.mKeepLiveCountDown1.start();
        CameraControlHttpUtils.controlSwitch(this.mContext, this.mDeviceId);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XcLogger.e("loadLiveUrl", "onConfigurationChanged: " + isFullScreen());
        if (this.isFullScreen || isFullScreen()) {
        }
        this.isFullScreen = !this.isFullScreen;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDeviceStatus(int status, boolean isReload) {
        if (status == 1) {
            this.mRlStatus.setVisibility(8);
            if (!this.mVideoView.isPlaying() && isReload) {
                onLinePlayVideo(this.mDeviceId);
                return;
            }
            return;
        }
        this.mRlStatus.setVisibility(0);
        this.mVideoView.pause();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVisibleActionIcon(int visible) {
        this.mCbScreen.setVisibility(visible);
        this.mTvCodeStream.setVisibility(visible);
    }

    private void setVerticalScreen() {
        WindowManager.LayoutParams params = this.mActivity.getWindow().getAttributes();
        params.flags &= -1025;
        this.mActivity.getWindow().setAttributes(params);
        this.mActivity.getWindow().clearFlags(WXMediaMessage.TITLE_LENGTH_LIMIT);
        this.mVideoView.setLayoutParams(this.mVideolayoutParams);
        this.mScreenBack.setVisibility(8);
        this.mLeftBack.setVisibility(0);
    }

    private void setFullScreen() {
        WindowManager.LayoutParams params = this.mActivity.getWindow().getAttributes();
        params.flags |= WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
        this.mActivity.getWindow().setAttributes(params);
        this.mActivity.getWindow().addFlags(WXMediaMessage.TITLE_LENGTH_LIMIT);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(-1, -1);
        this.mVideoView.setLayoutParams(layoutParams);
        this.mScreenBack.setVisibility(0);
        this.mLeftBack.setVisibility(8);
    }

    private void oreationChange() {
        this.mOrientationEventListener = new OrientationEventListener(this.mContext) { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonLive.3
            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int orientation) {
                if (orientation != -1) {
                    try {
                        CameraCommonLive.this.mScreenChange = Settings.System.getInt(CameraCommonLive.this.mContext.getContentResolver(), "accelerometer_rotation");
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (CameraCommonLive.this.mScreenChange == 1) {
                        if (orientation > 350 || orientation < 10) {
                            if (CameraCommonLive.this.isFullScreen) {
                                CameraCommonLive.this.mActivity.setRequestedOrientation(1);
                                CameraCommonLive.this.mCbScreen.setChecked(true);
                                return;
                            }
                            return;
                        }
                        if (orientation > 80 && orientation < 100) {
                            if (!CameraCommonLive.this.isFullScreen) {
                                CameraCommonLive.this.mActivity.setRequestedOrientation(8);
                                CameraCommonLive.this.mCbScreen.setChecked(false);
                                return;
                            }
                            return;
                        }
                        if ((orientation <= 170 || orientation >= 190) && orientation > 260 && orientation < 280 && !CameraCommonLive.this.isFullScreen) {
                            CameraCommonLive.this.mActivity.setRequestedOrientation(0);
                            CameraCommonLive.this.mCbScreen.setChecked(false);
                        }
                    }
                }
            }
        };
        this.mOrientationEventListener.enable();
    }

    public void pause() {
        if (this.mVideoView != null) {
            this.mVideoView.pause();
            XcLogger.e("loadLiveUrl", "pause");
        }
    }

    public int getDuration() {
        XcLogger.e("loadLiveUrl", Integer.valueOf(new StringBuilder().append("duration").append(this.mVideoView).toString() != null ? this.mVideoView.getDuration() : 0));
        if (this.mVideoView != null) {
            return this.mVideoView.getDuration();
        }
        return 0;
    }

    public int getCurrentPosition() {
        XcLogger.e("loadLiveUrl", Integer.valueOf(new StringBuilder().append("currentPosition").append(this.mVideoView).toString() != null ? this.mVideoView.getCurrentPosition() : 0));
        if (this.mVideoView != null) {
            return this.mVideoView.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(int position) {
        if (this.mVideoView != null) {
            XcLogger.e("loadLiveUrl", "seekTo---" + position);
            this.mVideoView.seekTo(position);
        }
    }

    public boolean isPlaying() {
        XcLogger.e("loadLiveUrl", Boolean.valueOf(new StringBuilder().append("isPlaying---").append(this.mVideoView).toString() != null && this.mVideoView.isPlaying()));
        return this.mVideoView != null && this.mVideoView.isPlaying();
    }

    public boolean isComplete() {
        XcLogger.e("loadLiveUrl", "isComplete---" + this.mIsComplete);
        return this.mIsComplete;
    }

    public int getBufferPercentage() {
        XcLogger.e("loadLiveUrl", "getBufferPercentage----0");
        return 0;
    }

    public boolean isFullScreen() {
        return this.mActivity.getRequestedOrientation() == 0;
    }

    /* JADX INFO: renamed from: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view.CameraCommonLive$4, reason: invalid class name */
    class AnonymousClass4 extends DeviceStatusReceiver {
        AnonymousClass4() {
        }

        protected void onReceiveDeviceSnapshot(String snapshotDevId, String snapshot, boolean isConnect) {
            XcLogger.i("curtainDetail----", "*********" + snapshot);
            if (!TextUtils.isEmpty(snapshot) && CameraCommonLive.this.mDeviceId.equals(snapshotDevId)) {
                CameraCommonLive.this.mActivity.runOnUiThread(CameraCommonLive$4$$Lambda$1.lambdaFactory$(this, snapshot));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveDeviceSnapshot$0(String snapshot) throws JSONException {
            CameraCommonLive.this.paserSnapshot(snapshot);
        }

        protected void onReceiveDeviceStatus(String deviceId, int status, boolean isConnect) {
            if (!TextUtils.isEmpty(deviceId) && deviceId.equals(CameraCommonLive.this.mDeviceId)) {
                CameraCommonLive.this.mActivity.runOnUiThread(CameraCommonLive$4$$Lambda$2.lambdaFactory$(this, status));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveDeviceStatus$1(int status) {
            CameraCommonLive.this.getDeviceStatus(status, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void paserSnapshot(String snapshot) throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(snapshot);
            this.mCodeStream = jsonObject.optInt("codeStream");
            if (this.mCodeStream == 0) {
                this.mTvCodeStream.setText("标清");
            } else {
                this.mTvCodeStream.setText("高清");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onDestory() {
        if (this.mVideoView != null) {
            this.mVideoView.stopPlayback();
        }
        if (this.mOrientationEventListener != null) {
            this.mOrientationEventListener.disable();
        }
        if (this.mKeepLiveCountDown1 != null) {
            this.mKeepLiveCountDown1.cancel();
        }
        LocalBroadcastManager.getInstance(this.mActivity).unregisterReceiver(this.mStatusReceiver);
        this.mActivity.unregisterReceiver(this.mBatInfoReceiver);
    }

    public void onBack() {
        if (isFullScreen()) {
            this.mActivity.setRequestedOrientation(1);
            this.mCbScreen.setChecked(true);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_common_camera_live_screen_back /* 2131296485 */:
                this.mActivity.setRequestedOrientation(1);
                setVerticalScreen();
                this.mCbScreen.setChecked(true);
                this.mCbScreen.setVisibility(0);
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                this.mActivity.finish();
                break;
            case R.id.right_titlebar_image /* 2131296678 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) DeviceDetailSettingActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent.putExtra("deviceName", this.mDeviceName);
                intent.putExtra("productId", this.mProductId);
                intent.putExtra("is_admin", this.mAdmin);
                this.mActivity.startActivityForResult(intent, 100);
                break;
            case R.id.tv_common_camera_live_code_stream /* 2131296966 */:
                if (this.mCodeStream == 0) {
                    CameraControlHttpUtils.controlCodeStream(this.mContext, CameraCommonLive$$Lambda$2.lambdaFactory$(this), this.mDeviceId, 1);
                } else {
                    CameraControlHttpUtils.controlCodeStream(this.mContext, CameraCommonLive$$Lambda$3.lambdaFactory$(this), this.mDeviceId, 0);
                }
                break;
        }
    }

    public void setDeviceName(String deviceName) {
        if (!TextUtils.isEmpty(deviceName)) {
            this.mTitleText.setText(deviceName);
            this.mDeviceName = deviceName;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    public void resultTypeCalllback(int type) {
        this.mCodeStream = type;
        if (type == 0) {
            this.mTvCodeStream.setText("标清");
        } else {
            this.mTvCodeStream.setText("高清");
        }
    }

    private class KeepLiveCountDown extends CountDownTimer {
        public KeepLiveCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            XcLogger.w("LoadingCountDown", "onReceivePassThroughMessage--getContent = " + millisUntilFinished);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            CameraControlHttpUtils.controlSwitch(CameraCommonLive.this.mContext, CameraCommonLive.this.mDeviceId);
        }
    }
}
