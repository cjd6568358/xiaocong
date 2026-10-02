package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.xconfig;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import bsh.ParserConstants;
import com.google.gson.Gson;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.BindSoftApManager;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleProgressBar;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.softap.model.XConfigDeviceModel;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApStage;
import com.ixiaocong.xconfig.callback.XConfigCallback;
import com.ixiaocong.xconfig.manager.XcSdkManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceAddXConfigActivity extends XcBaseActivity implements View.OnClickListener, HintDialogCallback, XConfigCallback {
    private boolean isConfig = true;
    private CircleProgressBar mAddProgress;
    private Button mBtnCancel;
    private LoadingCountDown mCountDown;
    private String mDeviceId;
    private Animation mInitAnimation;
    private ImageView mIvBack;
    private ImageView mIvConn;
    private ImageView mIvExchange;
    private ImageView mIvInit;
    private ImageView mIvScan;
    private String mProductId;
    private String mProductImg;
    private String mProductName;

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
        $(R.id.tv_softap_scan_hint).setVisibility(4);
        setSwipeBackEnable(false);
        this.mAddProgress.setUnit(NotifyType.SOUND);
        this.mAddProgress.setMaxProgress(89);
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        this.mProductId = getIntent().getStringExtra("productId");
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductName = getIntent().getStringExtra("productName");
        this.mProductImg = getIntent().getStringExtra("productImg");
        initAnimation();
        this.mCountDown = new LoadingCountDown(90000L, 1000L);
        this.mCountDown.start();
        XcSdkManager.getInstance().startConfig(this.mActivity, this, this.mProductId);
    }

    private void initAnimation() {
        this.mInitAnimation = AnimationUtils.loadAnimation(this.mActivity, R.anim.rotate_loading_just_dialog_anim);
        this.mIvInit.setImageResource(R.drawable.add_dev_progress_icon);
        this.mIvInit.setAnimation(this.mInitAnimation);
        this.mInitAnimation.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimation(int type) {
        runOnUiThread(DeviceAddXConfigActivity$$Lambda$1.lambdaFactory$(this, type));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$0(int type) {
        switch (type) {
            case 1:
                this.mIvInit.clearAnimation();
                this.mIvInit.setImageResource(R.drawable.add_dev_check_icon);
                this.mIvConn.setImageResource(R.drawable.add_dev_progress_icon);
                this.mIvConn.setAnimation(this.mInitAnimation);
                break;
            case 2:
                this.mIvConn.clearAnimation();
                this.mIvConn.setImageResource(R.drawable.add_dev_check_icon);
                this.mIvExchange.setImageResource(R.drawable.add_dev_progress_icon);
                this.mIvExchange.setAnimation(this.mInitAnimation);
                break;
            case 3:
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

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        onDismiss();
        finish();
    }

    @Override // com.ixiaocong.xconfig.callback.XConfigCallback
    public void xconfigCallback(final int type, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            final XConfigDeviceModel event = (XConfigDeviceModel) new Gson().fromJson(msg, XConfigDeviceModel.class);
            if (event.getProductId().equals(this.mProductId) && !TextUtils.isEmpty(event.getDeviceId())) {
                runOnUiThread(new Runnable() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.xconfig.DeviceAddXConfigActivity.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DeviceAddXConfigActivity.this.mIvConn.setImageResource(R.drawable.add_dev_check_icon);
                        DeviceAddXConfigActivity.this.mIvExchange.setImageResource(R.drawable.add_dev_check_icon);
                        DeviceAddXConfigActivity.this.mIvScan.setImageResource(R.drawable.add_dev_check_icon);
                        DeviceAddXConfigActivity.this.onDismiss();
                        if (!TextUtils.isEmpty(DeviceAddXConfigActivity.this.mDeviceId)) {
                            if (DeviceAddXConfigActivity.this.mDeviceId.equals(event.getDeviceId())) {
                                DeviceAddXConfigActivity.this.mCountDown.cancel();
                                ToastUtils.showShort(DeviceAddXConfigActivity.this.mActivity, "设备重置入网成功");
                                ActivityManagerUtil.getScreenManager().popAllActivity();
                                DeviceAddXConfigActivity.this.finish();
                                return;
                            }
                            return;
                        }
                        DeviceAddXConfigActivity.this.mCountDown.cancel();
                        BindSoftApManager.querySoftApDeviceInfo(DeviceAddXConfigActivity.this.mActivity, DeviceAddXConfigActivity$1$$Lambda$1.lambdaFactory$(DeviceAddXConfigActivity.this), event.getDeviceId(), DeviceAddXConfigActivity.this.mProductId, event.getMac(), type);
                    }
                });
            }
        }
    }

    private class LoadingCountDown extends CountDownTimer {
        public LoadingCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            int millis = ((int) (millisUntilFinished / 1000)) - 1;
            if (millis < 88 && millis >= 86) {
                DeviceAddXConfigActivity.this.startAnimation(1);
            } else if (millis < 86 && millis >= 84) {
                DeviceAddXConfigActivity.this.startAnimation(2);
            } else if (millis < 84) {
                DeviceAddXConfigActivity.this.startAnimation(3);
            }
            DeviceAddXConfigActivity.this.mAddProgress.setProgressNotInUiThread((int) (90 - (millisUntilFinished / 1000)));
            DeviceAddXConfigActivity.this.mAddProgress.setCenterText(millis >= 0 ? millis + Constants.MAIN_VERSION_TAG : PushConstants.PUSH_TYPE_NOTIFY);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            ToastUtils.showShort(DeviceAddXConfigActivity.this.mActivity, "设备搜索超时,请重新尝试");
            HashMap<String, Object> signParams = new HashMap<>();
            HashMap<String, Object> commonParams = new HashMap<>();
            signParams.put("productId", DeviceAddXConfigActivity.this.mProductId);
            commonParams.put("discoverTime", "90");
            signParams.put("errorCode", 7);
            BindSoftApManager.bindDeviceLog(DeviceAddXConfigActivity.this.mActivity, signParams, commonParams);
            XcSdkManager.getInstance().stopConfig();
            SoftApStage.getInstance().setError(true);
            DeviceAddXConfigActivity.this.onDismiss();
            DeviceAddXConfigActivity.this.isConfig = false;
            DeviceAddXConfigActivity.this.finish();
        }
    }

    private void affirmCancel() {
        OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "设备添加", "确定取消本次设备入网操作吗?");
    }

    public void onBackPressed() {
        if (this.isConfig) {
            affirmCancel();
        } else {
            super.onBackPressed();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDismiss() {
        XcSdkManager.getInstance().stopConfig();
        this.mInitAnimation.cancel();
        this.mCountDown.cancel();
    }
}
