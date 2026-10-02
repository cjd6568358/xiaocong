package com.ixiaocong.smarthome.phone.android.detail.activity.device.setting;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.DeviceDetailManager;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.MainFragmentActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddStateActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedSelectUserActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.user.EditUserDataActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.DeviceDetailCallBack;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.DevDetailModel;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.switchbutton.SwitchButton;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceDetailSettingActivity extends XcBaseActivity implements View.OnClickListener, DeviceDetailCallBack, HintDialogCallback {
    private Button mBtnDeleteDev;
    private Button mBtnUpdate;
    private String mDeviceId;
    private String mDeviceName;
    private ConstraintLayout mDeviceParameterRenameLayout;
    private ConstraintLayout mDeviceRssiLayout;
    private ConstraintLayout mDeviceSsidLayout;
    private ConstraintLayout mDeviceSwitchRelationLayout;
    private String mDeviceType;
    private ConstraintLayout mDeviceUpdateLayout;
    private int mIsGw;
    private ImageView mIvBack;
    private int mProductId;
    private ConstraintLayout mRenameLayout;
    private ConstraintLayout mResetNetLayout;
    private SwitchButton mSbtnStick;
    private int mSdkId;
    private ConstraintLayout mSharedDeviceLayout;
    private TextView mTvDevName;
    private TextView mTvDeviceNum;
    private TextView mTvProductName;
    private TextView mTvProductNum;
    private TextView mTvRssi;
    private TextView mTvSsid;
    private TextView mTvUpgrade;
    private int mAdmin = -1;
    private boolean isUpgrade = false;

    protected int getLayoutId() {
        return R.layout.activity_detail_device_setting;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvDevName = (TextView) $(R.id.tv_setting_dev_name_value);
        this.mTvUpgrade = (TextView) $(R.id.tv_setting_firm_upgrade_click);
        this.mTvDeviceNum = (TextView) $(R.id.tv_setting_device_num);
        this.mTvProductNum = (TextView) $(R.id.tv_setting_product_num);
        this.mTvProductName = (TextView) $(R.id.tv_setting_product_name);
        this.mBtnDeleteDev = (Button) $(R.id.btn_setting_delete_dev);
        this.mRenameLayout = (ConstraintLayout) $(R.id.cl_setting_dev_name);
        this.mSbtnStick = $(R.id.sbtn_setting_dev_stick);
        this.mResetNetLayout = (ConstraintLayout) $(R.id.cl_setting_reset_net);
        this.mSharedDeviceLayout = (ConstraintLayout) $(R.id.cl_setting_share_device);
        this.mBtnUpdate = (Button) $(R.id.btn_setting_firm_update);
        this.mDeviceParameterRenameLayout = (ConstraintLayout) $(R.id.cl_setting_device_parameter_rename);
        this.mDeviceUpdateLayout = (ConstraintLayout) $(R.id.cl_setting_device_upgrade);
        this.mDeviceSwitchRelationLayout = (ConstraintLayout) $(R.id.cl_setting_device_switch_relation);
        this.mDeviceRssiLayout = (ConstraintLayout) $(R.id.cl_device_setting_rssi);
        this.mDeviceSsidLayout = (ConstraintLayout) $(R.id.cl_device_setting_ssid);
        this.mTvRssi = (TextView) $(R.id.tv_device_setting_rssi);
        this.mTvSsid = (TextView) $(R.id.tv_device_setting_ssid);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mDeviceName = getIntent().getStringExtra("deviceName");
        if (!TextUtils.isEmpty(this.mDeviceId)) {
            this.mTvDevName.setText(this.mDeviceName);
        }
    }

    protected void onResume() {
        super.onResume();
        DeviceDetailManager.requestDevDetail(this.mActivity, this, this.mDeviceId);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvUpgrade.setOnClickListener(this);
        this.mBtnDeleteDev.setOnClickListener(this);
        this.mRenameLayout.setOnClickListener(this);
        this.mResetNetLayout.setOnClickListener(this);
        this.mSharedDeviceLayout.setOnClickListener(this);
        this.mBtnUpdate.setOnClickListener(this);
        this.mDeviceParameterRenameLayout.setOnClickListener(this);
        this.mDeviceSwitchRelationLayout.setOnClickListener(this);
        this.mSbtnStick.setOnCheckedChangeListener(DeviceDetailSettingActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(CompoundButton buttonView, boolean isChecked) {
        XcLogger.i("SystemSettingActivity", "mBtnMsg" + isChecked);
        if (isChecked) {
            deviceSetTop();
        } else {
            deviceCancelTop();
        }
    }

    private void deviceCancelTop() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/top/cancel");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceDetailSettingActivity.1
            public void onComplete(XCResponseBean var1) {
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceDetailSettingActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    private void deviceSetTop() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/top");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceDetailSettingActivity.2
            public void onComplete(XCResponseBean var1) {
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceDetailSettingActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting_delete_dev /* 2131296332 */:
                if (this.mAdmin == 1) {
                    OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "删除设备", "确定删除当前设备吗?");
                } else if (this.mAdmin == 0) {
                    OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "解除分享", "确定解除当前设备的分享关系吗?");
                }
                break;
            case R.id.btn_setting_firm_update /* 2131296333 */:
                if (TextUtils.isEmpty(this.mDeviceId) || this.mSdkId <= 0) {
                    ToastUtils.showShort(this.mActivity, "暂不能升级,请稍后重试!");
                } else {
                    Intent intent = new Intent(this.mActivity, (Class<?>) DeviceUpdateDetailActivity.class);
                    intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                    intent.putExtra("deviceSdkId", this.mSdkId);
                    startActivity(intent);
                }
                break;
            case R.id.cl_setting_dev_name /* 2131296383 */:
                Intent intent2 = new Intent(this.mActivity, (Class<?>) EditUserDataActivity.class);
                intent2.putExtra("deviceName", this.mTvDevName.getText().toString().trim());
                intent2.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent2.putExtra("intentCode", 1002);
                startActivityForResult(intent2, 100);
                break;
            case R.id.cl_setting_device_parameter_rename /* 2131296384 */:
                Intent intent3 = new Intent(this.mActivity, (Class<?>) DeviceParameterRenameActivity.class);
                intent3.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                startActivity(intent3);
                break;
            case R.id.cl_setting_device_switch_relation /* 2131296385 */:
                Intent intent4 = new Intent(this.mActivity, (Class<?>) DeviceSwitchRelationParameterActivity.class);
                intent4.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent4.putExtra("productId", this.mProductId + Constants.MAIN_VERSION_TAG);
                startActivity(intent4);
                break;
            case R.id.cl_setting_reset_net /* 2131296387 */:
                ActivityManagerUtil.getScreenManager().pushActivity(this);
                Intent intent5 = new Intent(this.mActivity, (Class<?>) DeviceAddStateActivity.class);
                intent5.putExtra("productId", String.valueOf(this.mProductId));
                intent5.putExtra("productName", this.mDeviceName);
                intent5.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent5.putExtra("deviceType", this.mDeviceType);
                startActivity(intent5);
                break;
            case R.id.cl_setting_share_device /* 2131296388 */:
                if (TextUtils.isEmpty(this.mDeviceId)) {
                    ToastUtils.showShort(this.mActivity, "分享失败");
                } else {
                    Intent intent6 = new Intent(this.mActivity, (Class<?>) SharedSelectUserActivity.class);
                    intent6.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                    startActivity(intent6);
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.DeviceDetailCallBack
    public void requestDetailSuccess(DevDetailModel responseDetailModel) {
        if (responseDetailModel != null) {
            this.mTvDeviceNum.setText(responseDetailModel.getDevice().getDeviceId());
            this.mTvProductNum.setText(responseDetailModel.getDevice().getProductId());
            String deviceVersion = responseDetailModel.getDevice().getDeviceVersion();
            this.mAdmin = responseDetailModel.getDevice().getIsAdmin();
            this.mProductId = Integer.valueOf(responseDetailModel.getDevice().getProductId()).intValue();
            this.mDeviceName = responseDetailModel.getDevice().getDeviceName();
            int ParameterModifiable = responseDetailModel.getDevice().getParameterModifiable();
            if (TextUtils.isEmpty(this.mDeviceName)) {
                this.mTvDevName.setText(this.mDeviceName);
            }
            if (this.mAdmin == 0) {
                this.mBtnDeleteDev.setText("解除分享");
            } else {
                this.mBtnDeleteDev.setText("删除设备");
            }
            if ("1".equals(responseDetailModel.getDevice().getShowShare())) {
                this.mSharedDeviceLayout.setVisibility(0);
            } else {
                this.mSharedDeviceLayout.setVisibility(8);
            }
            if ("1".equals(responseDetailModel.getDevice().getShowReset())) {
                this.mResetNetLayout.setVisibility(0);
            } else {
                this.mResetNetLayout.setVisibility(8);
            }
            if ("1".equals(responseDetailModel.getDevice().getShowUpdate())) {
                this.mDeviceUpdateLayout.setVisibility(0);
            } else {
                this.mDeviceUpdateLayout.setVisibility(8);
            }
            if (ParameterModifiable == 1) {
                this.mDeviceParameterRenameLayout.setVisibility(0);
            } else {
                this.mDeviceParameterRenameLayout.setVisibility(8);
            }
            if ("1".equals(responseDetailModel.getDevice().getShowSwitchRelation())) {
                this.mDeviceSwitchRelationLayout.setVisibility(0);
            } else {
                this.mDeviceSwitchRelationLayout.setVisibility(8);
            }
            if ("1".equals(responseDetailModel.getDevice().getShowRssi())) {
                if (!TextUtils.isEmpty(responseDetailModel.getDevice().getRssi())) {
                    this.mDeviceRssiLayout.setVisibility(0);
                    this.mTvRssi.setText(responseDetailModel.getDevice().getRssi());
                }
            } else {
                this.mDeviceRssiLayout.setVisibility(8);
            }
            if ("1".equals(responseDetailModel.getDevice().getShowSsid())) {
                if (!TextUtils.isEmpty(responseDetailModel.getDevice().getSsid())) {
                    this.mDeviceSsidLayout.setVisibility(0);
                    this.mTvSsid.setText(responseDetailModel.getDevice().getSsid());
                }
            } else {
                this.mDeviceSsidLayout.setVisibility(8);
            }
            if (this.mAdmin == 1) {
                if (responseDetailModel.getDevice().getUpdate() == 1) {
                    this.mTvUpgrade.setVisibility(8);
                    this.mBtnUpdate.setVisibility(0);
                    this.isUpgrade = true;
                } else {
                    this.mTvUpgrade.setVisibility(0);
                    this.mBtnUpdate.setVisibility(8);
                    if (TextUtils.isEmpty(deviceVersion) || "null".equals(deviceVersion)) {
                        this.mTvUpgrade.setText("已是最新");
                    } else {
                        this.mTvUpgrade.setText(deviceVersion + " 已是最新");
                    }
                    this.isUpgrade = false;
                }
            } else {
                this.mTvUpgrade.setVisibility(0);
                this.mBtnUpdate.setVisibility(8);
                if (TextUtils.isEmpty(deviceVersion) || "null".equals(deviceVersion)) {
                    this.mTvUpgrade.setText("已是最新");
                } else {
                    this.mTvUpgrade.setText(deviceVersion);
                }
            }
            if (responseDetailModel.getDevice().getTop() == 1) {
                this.mSbtnStick.setCheckedImmediatelyNoEvent(true);
            } else {
                this.mSbtnStick.setCheckedImmediatelyNoEvent(false);
            }
            this.mSdkId = responseDetailModel.getDevice().getSdkId();
            this.mIsGw = responseDetailModel.getDevice().getIsGw();
            String productName = responseDetailModel.getDevice().getProductName();
            TextView textView = this.mTvProductName;
            if (TextUtils.isEmpty(productName)) {
                productName = Constants.MAIN_VERSION_TAG;
            }
            textView.setText(productName);
            return;
        }
        ToastUtils.showShort(this.mActivity, "获取设备详情失败,请稍后重试");
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.DeviceDetailCallBack
    public void unbindDeviceResponse(boolean isSuccess) {
        if (isSuccess) {
            startActivity(new Intent(this.mActivity, (Class<?>) MainFragmentActivity.class));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            String deviceName = data.getStringExtra("deviceName");
            this.mTvDevName.setText(deviceName);
            Intent intent = new Intent();
            intent.putExtra("deviceName", deviceName);
            setResult(100, intent);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            if (this.mAdmin == 1) {
                DeviceDetailManager.deleteDeviceHttp(this.mActivity, this, this.mDeviceId, this.mProductId);
            } else {
                DeviceDetailManager.unsharedDeviceHttp(this.mActivity, this, this.mDeviceId);
            }
        }
    }
}
