package com.ixiaocong.smarthome.phone.rn;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceDetailSettingActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceUpdateDetailActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.rn.init.RNCacheViewManager;
import com.ixiaocong.smarthome.phone.rn.pack.RNDeviceControlPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNDeviceInfoPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNDeviceParameterPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNDoorLockModulePackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNInfraredTransmitModulePackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNMessagePackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNOthorControlPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNScenePanelPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNUtilsPackage;
import com.ixiaocong.smarthome.phone.rn.pack.RNVersatileInfraredPackage;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DeviceSdkCheckModel;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.DeviceStatusReceiver;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNDetailActivity extends XcBaseActivity implements View.OnClickListener, DefaultHardwareBackBtnHandler {
    private int mAdmin;
    private Bundle mBundle;
    private String mClientID;
    private String mDeviceId;
    private String mDeviceName;
    private boolean mIsNowDownload;
    private ImageView mIvBack;
    private ImageView mIvSetting;
    private int mProductId;
    private ReactRootView mRRVDetatil;
    private ReactInstanceManager mReactInstanceManager;
    private RelativeLayout mRlStatus;
    private String mSnapshot;
    private int mStatus;
    private TextView mTvHintRed;
    private TextView mTvTitle;
    private boolean isShowUpdatePop = false;
    private DeviceStatusReceiver mDeviceStatusReceiver = new DeviceStatusReceiver() { // from class: com.ixiaocong.smarthome.phone.rn.RNDetailActivity.2
        public void onReceiveDeviceSnapshot(String snapshotDevId, String snapshot, boolean isConnect) {
        }

        public void onReceiveDeviceStatus(String deviceId, int status, boolean isConnect) {
            if (!TextUtils.isEmpty(deviceId) && deviceId.equals(RNDetailActivity.this.mDeviceId)) {
                RNDetailActivity.this.getDeviceStatus(status);
            }
        }
    };

    protected int getLayoutId() {
        return R.layout.activity_detail_rn;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvSetting = (ImageView) $(R.id.right_titlebar_image);
        this.mTvTitle = (TextView) $(R.id.centertxt_titlebar);
        this.mRRVDetatil = (ReactRootView) $(R.id.rrv_detail_rn);
        this.mRlStatus = (RelativeLayout) $(R.id.rl_dev_status_hint);
        this.mTvHintRed = (TextView) $(R.id.right_titlebar_hint_text);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mDeviceName = getIntent().getStringExtra("deviceName");
        this.mProductId = getIntent().getIntExtra("productId", 0);
        this.mAdmin = getIntent().getIntExtra("is_admin", 0);
        this.mStatus = getIntent().getIntExtra("deviceStatus", 0);
        this.mClientID = getIntent().getStringExtra("clientId");
        this.mSnapshot = getIntent().getStringExtra("snapshotMsg");
        this.mIsNowDownload = getIntent().getBooleanExtra("isNowDownload", false);
        this.mBundle = getIntent().getExtras();
        this.mTvTitle.setText(this.mDeviceName);
        if (!RNCacheViewManager.getInstance().isCacheJs(this.mProductId)) {
            XcLogger.e("RNCacheViewManager.isCacheJs", "开始缓存----------------");
        }
        if (!this.mIsNowDownload) {
            initManager();
            RNCacheViewManager.getInstance().removeManager(String.valueOf(this.mProductId));
        } else {
            this.mReactInstanceManager = RNCacheViewManager.getInstance().getReactInstanceManager(String.valueOf(this.mProductId));
            if (this.mReactInstanceManager == null) {
                initManager();
            }
        }
        this.mRRVDetatil.startReactApplication(this.mReactInstanceManager, String.valueOf(this.mProductId), this.mBundle);
        LocalBroadcastManager.getInstance(this.mActivity).registerReceiver(this.mDeviceStatusReceiver, new IntentFilter("ACTION_UPDATE_STATUS"));
        getDeviceStatus(this.mStatus);
    }

    private ReactInstanceManager initManager() {
        String path = this.mActivity.getFilesDir() + "/ixiaocong/js/" + this.mProductId + ".jsbundle";
        this.mReactInstanceManager = ReactInstanceManager.builder().setApplication(getApplication()).setJSBundleFile(path).setJSMainModuleName(String.valueOf(this.mProductId)).setUseDeveloperSupport(false).setInitialLifecycleState(LifecycleState.BEFORE_CREATE).addPackage(new MainReactPackage()).addPackage(new RNDeviceControlPackage()).addPackage(new RNDeviceInfoPackage()).addPackage(new RNMessagePackage()).addPackage(new RNOthorControlPackage()).addPackage(new RNDeviceParameterPackage()).addPackage(new RNDoorLockModulePackage()).addPackage(new RNInfraredTransmitModulePackage()).addPackage(new RNScenePanelPackage()).addPackage(new RNUtilsPackage()).addPackage(new RNVersatileInfraredPackage()).build();
        Intent intent = new Intent();
        intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        intent.putExtra("deviceName", this.mDeviceName);
        intent.putExtra("productId", this.mProductId);
        intent.putExtra("is_admin", this.mAdmin);
        intent.putExtra("clientId", this.mClientID);
        intent.putExtra("deviceStatus", this.mStatus);
        intent.putExtra("imgUrl", "file://" + this.mActivity.getFilesDir() + "/ixiaocong/js/img/drawable-mdpi/");
        intent.putExtra("snapshotMsg", this.mSnapshot);
        intent.putExtra("isNowDownload", false);
        RNCacheViewManager.getInstance().init(this.mActivity, String.valueOf(this.mProductId), intent, this.mReactInstanceManager);
        return this.mReactInstanceManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDeviceStatus(int status) {
        if (status == 1) {
            this.mRlStatus.setVisibility(8);
        } else {
            this.mRlStatus.setVisibility(0);
        }
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mIvSetting.setOnClickListener(this);
    }

    @Override // com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onPause() {
        super.onPause();
        if (this.mReactInstanceManager != null) {
            this.mReactInstanceManager.onHostPause(this);
            XcLogger.e("RNDetailActivity", " onHostPause()");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onDestroy() {
        super.onDestroy();
        if (this.mReactInstanceManager != null) {
            this.mReactInstanceManager.onHostDestroy(this);
            XcLogger.e("RNDetailActivity", " onHostDestroy()");
        }
        LocalBroadcastManager.getInstance(this.mActivity).unregisterReceiver(this.mDeviceStatusReceiver);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onResume() {
        super.onResume();
        if (this.mReactInstanceManager != null) {
            this.mReactInstanceManager.onHostResume(this, this);
        }
        getDeviceSdkVesion(this.mDeviceId);
    }

    public void onBackPressed() {
        if (this.mReactInstanceManager != null) {
            this.mReactInstanceManager.onBackPressed();
            XcLogger.e("RNDetailActivity", " mReactInstanceManager.onBackPressed()");
        } else {
            super.onBackPressed();
            XcLogger.e("RNDetailActivity", " onBackPressed()");
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode != 82 || this.mReactInstanceManager == null) {
            return super.onKeyUp(keyCode, event);
        }
        this.mReactInstanceManager.showDevOptionsDialog();
        return true;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
            case R.id.right_titlebar_image /* 2131296678 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) DeviceDetailSettingActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent.putExtra("deviceName", this.mDeviceName);
                intent.putExtra("productId", this.mProductId);
                intent.putExtra("is_admin", this.mAdmin);
                startActivityForResult(intent, 100);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            String deviceName = data.getStringExtra("deviceName");
            this.mTvTitle.setText(deviceName);
        }
    }

    private void getDeviceSdkVesion(String deviceId) {
        if (!TextUtils.isEmpty(deviceId)) {
            XCHttpSetting httpSetting = new XCHttpSetting();
            HashMap<String, Object> params = new HashMap<>();
            params.put(Constants.FLAG_DEVICE_ID, deviceId);
            httpSetting.setParamsMap(params);
            httpSetting.setPath("device/sdk/check");
            XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.RNDetailActivity.1
                public void onComplete(XCResponseBean var1) {
                    final DeviceSdkCheckModel deviceSdkCheckModel = (DeviceSdkCheckModel) JSON.parseObject(var1.getData(), DeviceSdkCheckModel.class);
                    if (deviceSdkCheckModel.getUpgrade() == 1) {
                        RNDetailActivity.this.mTvHintRed.setVisibility(0);
                    } else {
                        RNDetailActivity.this.mTvHintRed.setVisibility(8);
                    }
                    if (!TextUtils.isEmpty(deviceSdkCheckModel.getUpgradeMsg()) && !RNDetailActivity.this.isShowUpdatePop && deviceSdkCheckModel.getSdkId() > 0) {
                        OperationHintDialog.getInstance().showSelectDialog(RNDetailActivity.this.mActivity, new HintDialogCallback() { // from class: com.ixiaocong.smarthome.phone.rn.RNDetailActivity.1.1
                            @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
                            public void hintDialogListener(boolean isSuccess) {
                                Intent intent = new Intent();
                                intent.setClass(RNDetailActivity.this.mActivity, DeviceUpdateDetailActivity.class);
                                intent.putExtra(Constants.FLAG_DEVICE_ID, RNDetailActivity.this.mDeviceId);
                                intent.putExtra("deviceSdkId", deviceSdkCheckModel.getSdkId());
                                RNDetailActivity.this.mActivity.startActivity(intent);
                            }
                        }, Constants.MAIN_VERSION_TAG, deviceSdkCheckModel.getUpgradeMsg());
                        RNDetailActivity.this.isShowUpdatePop = true;
                    }
                }

                public void onError(XCErrorMessage var1) {
                    XcLogger.e("RNDetailActivity", var1.getErrorMessage());
                }
            });
        }
    }
}
