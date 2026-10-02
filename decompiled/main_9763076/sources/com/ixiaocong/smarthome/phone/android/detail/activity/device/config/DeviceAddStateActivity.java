package com.ixiaocong.smarthome.phone.android.detail.activity.device.config;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import bsh.ParserConstants;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.AppDetailSettingManager;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.common.utils.wifi.WiFi5gManager;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.AddDeviceScanDialog;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SoftApScanPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.xconfig.DeviceAddXConfigActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApScanWifiUtils;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApStage;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.ProductNetConfModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceAddStateActivity extends XcBaseActivity implements View.OnClickListener, CommonTypeCallback, HintDialogCallback, ScanWifiCallback {
    private Button mBtnAppend;
    private Button mBtnError;
    private String mDeviceId;
    private String mDeviceType;
    private ImageView mIvBack;
    private ImageView mIvDevImg;
    private LocationManager mLocationManager;
    private ProductNetConfModel.NetConfModel mNetConfModel;
    private String mProductId;
    private String mProductImg;
    private String mProductName;
    private AddDeviceScanDialog mScanDialog;
    private TextView mTvDevHint;
    private TextView mTvDevName;
    private TextView mTvTitle;

    protected int getLayoutId() {
        return R.layout.activity_dev_add_state;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        getWindow().addFlags(ParserConstants.LSHIFTASSIGN);
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvDevImg = (ImageView) $(R.id.iv_add_state_dev_img);
        this.mTvTitle = (TextView) $(R.id.centertxt_titlebar);
        this.mTvDevName = (TextView) $(R.id.tv_add_state_dev_name);
        this.mTvDevHint = (TextView) $(R.id.tv_add_state_dev_hint_text);
        this.mBtnAppend = (Button) $(R.id.btn_add_state_dev_config);
        this.mBtnError = (Button) $(R.id.btn_add_dev_state_error);
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        this.mProductName = getIntent().getStringExtra("productName");
        this.mProductId = getIntent().getStringExtra("productId");
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mDeviceType = getIntent().getStringExtra("deviceType");
        this.mLocationManager = (LocationManager) getSystemService("location");
        if (!TextUtils.isEmpty(this.mProductName)) {
            this.mTvDevName.setText(this.mProductName);
        }
        this.mScanDialog = new AddDeviceScanDialog(this.mActivity, R.style.AlertDialogStyle, this.mIvBack, 30000L, this);
    }

    protected void onResume() {
        super.onResume();
        loadNetConfig();
        if (SoftApStage.getInstance().isError()) {
            this.mBtnError.setVisibility(0);
        }
    }

    private void loadNetConfig() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", this.mProductId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("product/help");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddStateActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ProductNetConfModel productNetConfModel = (ProductNetConfModel) JSON.parseObject(var1.getData(), ProductNetConfModel.class);
                DeviceAddStateActivity.this.mProductImg = productNetConfModel.getHelp().getImage();
                Glide.with(DeviceAddStateActivity.this.mActivity).load(DeviceAddStateActivity.this.mProductImg).placeholder(R.drawable.default_img_icon).into(DeviceAddStateActivity.this.mIvDevImg);
                DeviceAddStateActivity.this.mTvDevHint.setText(productNetConfModel.getHelp().getIntro());
                DeviceAddStateActivity.this.mNetConfModel = productNetConfModel.getHelp();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceAddStateActivity.this.mActivity, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mBtnAppend.setOnClickListener(this);
        this.mBtnError.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_dev_state_error /* 2131296313 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) XConfigErrorHelpActivity.class);
                intent.putExtra("webViewUrl", this.mNetConfModel == null ? Constants.MAIN_VERSION_TAG : this.mNetConfModel.getFaq());
                startActivity(intent);
                break;
            case R.id.btn_add_state_dev_config /* 2131296317 */:
                if (this.mNetConfModel != null && !TextUtils.isEmpty(this.mNetConfModel.getxConfigKey())) {
                    if ("softap1.0".equals(this.mNetConfModel.getNetconfigCode())) {
                        SoftApScanWifiUtils.startXConfig(getApplicationContext());
                        startScanAp();
                    } else if ("xconfig".equals(this.mNetConfModel.getNetconfigCode())) {
                        Intent intent2 = new Intent();
                        intent2.setClass(this.mActivity, DeviceAddXConfigActivity.class);
                        intent2.putExtra("productName", this.mProductName);
                        intent2.putExtra("productId", this.mProductId);
                        intent2.putExtra("productImg", this.mProductImg);
                        intent2.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                        startActivity(intent2);
                    } else if ("easylink".equals(this.mNetConfModel.getNetconfigCode())) {
                        ToastUtils.showShort(this.mActivity, "暂不支持此配网方式");
                    }
                } else {
                    ToastUtils.showShort(this.mActivity, "获取设备配网参数失败,请稍后重试");
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
        }
    }

    private void showScan() {
        if (this.mScanDialog == null) {
            this.mScanDialog = new AddDeviceScanDialog(this.mActivity, R.style.AlertDialogStyle, this.mIvBack, 30000L, this);
        }
        if (!this.mScanDialog.isShowing()) {
            this.mScanDialog.show();
            this.mScanDialog.startScan();
        }
        SoftApScanWifiUtils.scanDeviceAp(this.mActivity, this, this.mProductId);
    }

    private void startScanAp() {
        if (ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_FINE_LOCATION") != 0 || ActivityCompat.checkSelfPermission(this.mActivity, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            ActivityCompat.requestPermissions(this.mActivity, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 100);
            return;
        }
        if (!this.mLocationManager.isProviderEnabled("gps")) {
            OperationHintDialog.getInstance().showHintDialog(this.mActivity, this, getString(R.string.add_device), getString(R.string.open_gps_hint), getString(R.string.go_open));
        } else if (!WiFi5gManager.isWifiConnect(this.mActivity)) {
            OperationHintDialog.getInstance().showHintDialog(this.mActivity, this, getString(R.string.add_device), getString(R.string.open_wifi_hint), getString(R.string.go_open));
        } else {
            showScan();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == 0) {
                startScanAp();
            } else {
                ToastUtils.showShort(this.mActivity, "请授权App访问位置信息");
                AppDetailSettingManager.getAppDetailSettingIntent(this.mActivity);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            startActivityForResult(intent, 0);
        } else {
            Intent intent2 = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
            startActivityForResult(intent2, 0);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        SoftApScanWifiUtils.stopXConfig(getApplicationContext());
        this.mScanDialog.dismissScan();
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    public void resultTypeCalllback(int type) {
        if (type == 0) {
            this.mBtnError.setVisibility(8);
            SoftApScanWifiUtils.scanDeviceAp(this.mActivity, this, this.mProductId);
            showScan();
        } else if (type == 1) {
            this.mBtnError.setVisibility(0);
            this.mScanDialog.dismissScan();
        } else if (type == 2) {
            runOnUiThread(new Runnable() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddStateActivity.2
                @Override // java.lang.Runnable
                public void run() {
                    if (!DeviceAddStateActivity.this.isFinishing()) {
                        try {
                            SoftApScanWifiUtils.stopScanDeviceAp();
                            SoftApScanPop.getInstance().showSoftApScanPopPopup(DeviceAddStateActivity.this.mActivity, DeviceAddStateActivity$2$$Lambda$1.lambdaFactory$(DeviceAddStateActivity.this), DeviceAddStateActivity.this.mIvBack, "未扫描到设备,您可以重置设备后点击重试", "重试");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback
    public void scanApCallback(String apName) {
        if (!TextUtils.isEmpty(apName)) {
            if (this.mNetConfModel != null && !TextUtils.isEmpty(this.mNetConfModel.getxConfigKey())) {
                this.mScanDialog.dismissScan();
                Intent intent = new Intent();
                intent.setClass(this.mActivity, DeviceAddNetworkActivity.class);
                intent.putExtra("productName", this.mProductName);
                intent.putExtra("productId", this.mProductId);
                intent.putExtra("productImg", this.mProductImg);
                intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent.putExtra("softApName", apName);
                startActivity(intent);
                return;
            }
            ToastUtils.showShort(this.mActivity, "获取设备配网参数失败,请稍后重试");
        }
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback
    public void startScanDevice(String homeBroadAddress) {
    }

    @Override // com.ixiaocong.smarthome.phone.softap.callback.ScanWifiCallback
    public void startConfigNetwork(String apBroadAddress, String checkCode) {
    }
}
