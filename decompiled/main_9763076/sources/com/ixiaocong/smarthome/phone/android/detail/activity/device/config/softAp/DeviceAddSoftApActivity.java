package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.softAp;

import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import bsh.ParserConstants;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.BindSoftApManager;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.common.utils.wifi.WifiUtils;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleProgressBar;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback;
import com.ixiaocong.smarthome.phone.softap.callback.XConfigSoftApCallback;
import com.ixiaocong.smarthome.phone.softap.link.XcLinkNetwork;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApCheckUtils;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApManager;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApNetworkId;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApScanWifiUtils;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApStage;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceAddSoftApActivity extends XcBaseActivity implements View.OnClickListener, HintDialogCallback, ScanWifiCallback, XConfigSoftApCallback {
    private CircleProgressBar mAddProgress;
    private Button mBtnCancel;
    private LoadingCountDown mCountDown;
    private String mDeviceId;
    private String mHomeSSID;
    private Animation mInitAnimation;
    private ImageView mIvBack;
    private ImageView mIvConn;
    private ImageView mIvExchange;
    private ImageView mIvInit;
    private ImageView mIvScan;
    private String mPassword;
    private String mProductId;
    private String mSoftApName;
    private TextView mTvScanHint;
    private WifiManager mWifiManager;
    private boolean isBinding = true;
    private int mAnimationType = 4;

    protected int getLayoutId() {
        return R.layout.activity_softap_config_process;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        getWindow().addFlags(ParserConstants.LSHIFTASSIGN);
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvInit = (ImageView) $(R.id.iv_softap_init);
        this.mIvConn = (ImageView) $(R.id.iv_softap_conn);
        this.mIvExchange = (ImageView) $(R.id.iv_softap_exchange);
        this.mIvScan = (ImageView) $(R.id.iv_softap_scan);
        this.mBtnCancel = (Button) $(R.id.btn_add_softap_dev_cancel);
        this.mAddProgress = (CircleProgressBar) $(R.id.cpb_softap_scan_dev);
        this.mTvScanHint = (TextView) $(R.id.tv_softap_scan_hint);
        this.mAddProgress.setUnit(NotifyType.SOUND);
        this.mAddProgress.setMaxProgress(89);
        setSwipeBackEnable(false);
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        this.mProductId = getIntent().getStringExtra("productId");
        this.mSoftApName = getIntent().getStringExtra("softApName");
        this.mHomeSSID = getIntent().getStringExtra("ssid");
        this.mPassword = getIntent().getStringExtra("password");
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mIvInit.setImageResource(R.drawable.add_dev_progress_icon);
        this.mIvInit.setAnimation(this.mInitAnimation);
        this.mInitAnimation = AnimationUtils.loadAnimation(this.mActivity, R.anim.rotate_loading_just_dialog_anim);
        this.mInitAnimation.setInterpolator(new LinearInterpolator());
        this.mInitAnimation.start();
        startConfig();
    }

    private void startConfig() {
        this.mWifiManager = (WifiManager) getApplicationContext().getSystemService("wifi");
        this.mCountDown = new LoadingCountDown(90000L, 1000L);
        this.mCountDown.start();
        SoftApScanWifiUtils.scanAboutWifi(this.mActivity, this, this.mProductId, this.mHomeSSID);
        XcLogger.w("SoftAp", "0---startScan开始扫描设备ap和家庭网络");
    }

    private void startAnimation(int type) {
        runOnUiThread(DeviceAddSoftApActivity$$Lambda$1.lambdaFactory$(this, type));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$0(int type) {
        switch (type) {
            case 1:
                this.mAnimationType = 4;
                this.mIvInit.clearAnimation();
                this.mIvInit.setImageResource(R.drawable.add_dev_check_icon);
                this.mIvConn.setImageResource(R.drawable.add_dev_progress_icon);
                this.mIvConn.setAnimation(this.mInitAnimation);
                break;
            case 2:
                this.mAnimationType = 5;
                this.mIvConn.clearAnimation();
                this.mIvConn.setImageResource(R.drawable.add_dev_check_icon);
                this.mIvExchange.setImageResource(R.drawable.add_dev_progress_icon);
                this.mIvExchange.setAnimation(this.mInitAnimation);
                break;
            case 3:
                this.mAnimationType = 6;
                this.mIvExchange.clearAnimation();
                this.mIvExchange.setImageResource(R.drawable.add_dev_check_icon);
                this.mIvScan.setImageResource(R.drawable.add_dev_progress_icon);
                this.mIvScan.setAnimation(this.mInitAnimation);
                break;
        }
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mBtnCancel.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_softap_dev_cancel /* 2131296316 */:
            case R.id.left_titlebar_image /* 2131296560 */:
                affirmCancel();
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback
    public void scanApCallback(String apName) {
        if (SoftApCheckUtils.verifySSID(apName) && apName.equals(this.mSoftApName)) {
            XcLogger.w("SoftAp", "3---扫描的要连接的ap网络与发现的ap网络一致=" + this.mSoftApName + "//" + apName);
            startAnimation(1);
            SoftApScanWifiUtils.stopScanDeviceAp();
            XcLinkNetwork.linkApNetwork(this.mActivity, this.mWifiManager, apName);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback
    public void startConfigNetwork(String apBroadAddress, String checkCode) {
        XcLogger.w("SoftAp", "9---开始进行设备入网,mSoftApName=" + this.mSoftApName + "//apBroadAddress=" + apBroadAddress + "//checkCode=" + checkCode);
        SoftApManager.getInstance().startSoftAp(this.mActivity, this.mProductId, this.mDeviceId, this);
        SoftApManager.getInstance().sendSoftApTimer(apBroadAddress, this.mHomeSSID, this.mPassword, Constants.MAIN_VERSION_TAG, Constants.MAIN_VERSION_TAG, XCHelp.mClientId, checkCode);
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.XConfigSoftApCallback
    public void xconfigDeviceCallback(String mac, String productId) {
        startAnimation(2);
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback
    public void startScanDevice(String homeBroadAddress) {
        SoftApManager.getInstance().startCoapTimer(homeBroadAddress);
    }

    private void checkConnect(String msg) {
        if (!this.mWifiManager.getConnectionInfo().getSSID().replaceAll("\"", Constants.MAIN_VERSION_TAG).equals(this.mSoftApName) || !WifiUtils.isWifiActive(this.mActivity)) {
            boolean isConnect = this.mWifiManager.enableNetwork(SoftApNetworkId.getInstance().getSoftApId(), true);
            this.mWifiManager.reconnect();
            XcLogger.e("SoftAp", "error---" + msg + "//" + isConnect);
            return;
        }
        SoftApManager.getInstance().reconnectNetAp();
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            XcLinkNetwork.linkHomeNetwork(this.mActivity);
            this.mWifiManager.disableNetwork(SoftApNetworkId.getInstance().getSoftApId());
            HashMap<String, Object> signParams = new HashMap<>();
            HashMap<String, Object> commonParams = new HashMap<>();
            signParams.put("productId", this.mProductId);
            signParams.put("errorCode", 3);
            commonParams.put("discoverTime", SoftApStage.getInstance().getDiscoverTime());
            BindSoftApManager.bindDeviceLog(this.mActivity, signParams, commonParams);
            finish();
            return;
        }
        finish();
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.XConfigSoftApCallback
    public void xconfigCoapCallback(int type, String deviceId, String mac) {
        if (!TextUtils.isEmpty(deviceId)) {
            runOnUiThread(DeviceAddSoftApActivity$$Lambda$2.lambdaFactory$(this, deviceId, mac, type));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$xconfigCoapCallback$1(String deviceId, String mac, int type) {
        this.mAnimationType = 7;
        this.mIvScan.clearAnimation();
        this.mIvScan.setImageResource(R.drawable.add_dev_check_icon);
        this.mInitAnimation.cancel();
        this.mWifiManager.removeNetwork(SoftApNetworkId.getInstance().getSoftApId());
        SpUtils.saveToLocal(this.mActivity, "widnks_pswwwdn", this.mHomeSSID, this.mPassword);
        onDismiss();
        if (!TextUtils.isEmpty(this.mDeviceId)) {
            XcLogger.w("SoftAp", "22---设备重置联网成功");
            ActivityManagerUtil.getScreenManager().popAllActivity();
            ToastUtils.showShort(this.mActivity, "设备重置联网成功");
        } else if (this.isBinding) {
            this.isBinding = false;
            this.mAddProgress.setVisibility(4);
            XcLogger.w("SoftAp", "22---正在执行设备绑定操作,discoverTime=" + SoftApStage.getInstance().getDiscoverTime());
            this.mTvScanHint.setText("正在执行设备绑定操作...");
            BindSoftApManager.querySoftApDeviceInfo(this.mActivity, this, deviceId, this.mProductId, mac, type);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.XConfigSoftApCallback
    public void httpScanDevice(final String checkCode, final String productId, final String mac) {
        XcLogger.w("SoftAp", "20---http轮询发现设备,checkCode=" + checkCode + "//productId=" + productId + "//mac=" + mac);
        startAnimation(3);
        runOnUiThread(new Runnable() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.softAp.DeviceAddSoftApActivity.1
            @Override // java.lang.Runnable
            public void run() {
                BindSoftApManager.findScanDeviceInfo(DeviceAddSoftApActivity.this.mActivity, DeviceAddSoftApActivity.this, checkCode, productId, mac);
            }
        });
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.XConfigSoftApCallback
    public void xconfigErrorCallback(int type, String msg) {
        if (type == 403) {
            XcLogger.e("SoftAp", "error---" + msg + ",请检查是否连接至AP网络");
            checkConnect(msg);
            return;
        }
        if (type == 404) {
            XcLogger.e("SoftAp", "---error 再次尝试连接ao网络----//" + SoftApNetworkId.getInstance().getSoftApId() + "//---//" + msg);
            this.mWifiManager.enableNetwork(SoftApNetworkId.getInstance().getSoftApId(), true);
        } else if (type == 405) {
            if (!TextUtils.isEmpty(this.mDeviceId)) {
                ToastUtils.showShort(this.mActivity, "重置入网失败,请确认设备AP连接情况");
                XcLogger.e("SoftAp", "---error 重置入网失败----//" + msg);
            } else {
                ToastUtils.showShort(this.mActivity, "添加失败,请确认设备AP连接情况");
                XcLogger.e("SoftAp", "---error 添加失败----//" + msg);
            }
            XcLinkNetwork.linkHomeNetwork(this.mActivity);
            finish();
        }
    }

    private void affirmCancel() {
        OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "设备添加", "确定取消本次设备入网操作吗?");
    }

    public void onBackPressed() {
        if (SoftApStage.getInstance().isStartStage()) {
            affirmCancel();
        } else {
            super.onBackPressed();
        }
    }

    private void onDismiss() {
        SoftApManager.getInstance().stop();
        SoftApStage.getInstance().clearStage();
        SoftApScanWifiUtils.stopScanDeviceAp();
        SoftApNetworkId.getInstance().clearId();
        this.mInitAnimation.cancel();
        this.mCountDown.cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class LoadingCountDown extends CountDownTimer {
        public LoadingCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            DeviceAddSoftApActivity.this.runOnUiThread(DeviceAddSoftApActivity$LoadingCountDown$$Lambda$1.lambdaFactory$(this, millisUntilFinished));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTick$0(long millisUntilFinished) {
            int millis = ((int) (millisUntilFinished / 1000)) - 1;
            DeviceAddSoftApActivity.this.mAddProgress.setProgressNotInUiThread((int) (90 - (millisUntilFinished / 1000)));
            DeviceAddSoftApActivity.this.mAddProgress.setCenterText(millis >= 0 ? millis + Constants.MAIN_VERSION_TAG : PushConstants.PUSH_TYPE_NOTIFY);
            SoftApStage.getInstance().setDiscoverTime(String.valueOf(millis));
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            ToastUtils.showShort(DeviceAddSoftApActivity.this.mActivity, "设备搜索超时,请重新尝试");
            HashMap<String, Object> signParams = new HashMap<>();
            HashMap<String, Object> commonParams = new HashMap<>();
            signParams.put("productId", DeviceAddSoftApActivity.this.mProductId);
            signParams.put("errorCode", Integer.valueOf(DeviceAddSoftApActivity.this.mAnimationType));
            commonParams.put("deviceMac", Constants.MAIN_VERSION_TAG);
            commonParams.put(Constants.FLAG_DEVICE_ID, Constants.MAIN_VERSION_TAG);
            commonParams.put("discoverWay", Constants.MAIN_VERSION_TAG);
            commonParams.put("discoverTime", "90");
            BindSoftApManager.bindDeviceLog(DeviceAddSoftApActivity.this.mActivity, signParams, commonParams);
            SoftApStage.getInstance().setError(true);
            DeviceAddSoftApActivity.this.finish();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        onDismiss();
    }
}
