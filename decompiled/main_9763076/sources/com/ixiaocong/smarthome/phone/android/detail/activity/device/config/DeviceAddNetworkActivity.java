package com.ixiaocong.smarthome.phone.android.detail.activity.device.config;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import bsh.ParserConstants;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.common.utils.wifi.WiFi5gManager;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.softAp.DeviceAddSoftApActivity;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.utils.SpUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceAddNetworkActivity extends XcBaseActivity implements View.OnClickListener {
    private Button mBtnAppend;
    private CheckBox mCbShowPsw;
    private String mDevImgUrl;
    private String mDeviceId;
    private EditText mEtWifiPsw;
    private ImageView mIvBack;
    private ImageView mIvDevImg;
    private String mProductId;
    private String mProductName;
    private String mScanApName;
    private RelativeLayout mSelectWifiLayout;
    private TextView mTitleText;
    private TextView mTvDevName;
    private TextView mTvHint;
    private TextView mTvWifiName;
    private String mXConfigKey;

    protected int getLayoutId() {
        return R.layout.activity_dev_add_network;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvDevImg = (ImageView) $(R.id.iv_add_wifi_dev_img);
        this.mSelectWifiLayout = (RelativeLayout) $(R.id.rl_add_dev_select_wifi);
        this.mTitleText = (TextView) $(R.id.centertxt_titlebar);
        this.mTvDevName = (TextView) $(R.id.tv_add_wifi_dev_name);
        this.mTvHint = (TextView) $(R.id.tv_add_wifi_dev_hint_text);
        this.mTvWifiName = (TextView) $(R.id.tv_add_dev_wifi_name);
        this.mEtWifiPsw = (EditText) $(R.id.et_add_dev_wifi_psw);
        this.mBtnAppend = (Button) $(R.id.btn_add_wifi_dev_append);
        this.mCbShowPsw = (CheckBox) $(R.id.cb_add_dev_show_wifi_psw);
        setSwipeBackEnable(false);
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        this.mProductName = getIntent().getStringExtra("productName");
        this.mProductId = getIntent().getStringExtra("productId");
        this.mDevImgUrl = getIntent().getStringExtra("productImg");
        this.mXConfigKey = getIntent().getStringExtra("xconfig_key");
        this.mScanApName = getIntent().getStringExtra("softApName");
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        if (!TextUtils.isEmpty(this.mProductName)) {
            this.mTvDevName.setText(this.mProductName);
        }
        if (!TextUtils.isEmpty(this.mDevImgUrl)) {
            Glide.with(this.mActivity).load(this.mDevImgUrl).placeholder(R.drawable.default_img_icon).into(this.mIvDevImg);
        }
        if (!TextUtils.isEmpty(this.mDeviceId)) {
            this.mTitleText.setText("重置联网");
        } else {
            this.mTitleText.setText("添加设备");
        }
    }

    protected void onResume() {
        super.onResume();
        this.mTvWifiName.setText(WiFi5gManager.getWifiName(this.mActivity));
        this.mEtWifiPsw.setText((CharSequence) SpUtils.getFromLocal(this.mActivity, "widnks_pswwwdn", WiFi5gManager.getWifiName(this.mActivity), Constants.MAIN_VERSION_TAG));
        this.mEtWifiPsw.setSelection(this.mEtWifiPsw.length());
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mSelectWifiLayout.setOnClickListener(this);
        this.mBtnAppend.setOnClickListener(this);
        this.mCbShowPsw.setOnCheckedChangeListener(DeviceAddNetworkActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(CompoundButton buttonView, boolean isChecked) {
        InputMethodManager imm = (InputMethodManager) getSystemService("input_method");
        imm.hideSoftInputFromWindow(this.mEtWifiPsw.getWindowToken(), 0);
        this.mEtWifiPsw.setSelection(this.mEtWifiPsw.getText().toString().length());
        if (isChecked) {
            this.mEtWifiPsw.setInputType(ParserConstants.LSHIFTASSIGNX);
        } else {
            this.mEtWifiPsw.setInputType(145);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_wifi_dev_append /* 2131296318 */:
                String ssidPsw = this.mEtWifiPsw.getText().toString();
                String ssidName = this.mTvWifiName.getText().toString();
                if (!WiFi5gManager.isWifiConnect(this.mActivity) || TextUtils.isEmpty(ssidName)) {
                    ToastUtils.showShort(this.mActivity, "该设备的添加需在WiFi网络下");
                } else if (TextUtils.isEmpty(ssidPsw)) {
                    ToastUtils.showShort(this.mActivity, "请输入Wi-Fi密码");
                } else if (ssidPsw.length() < 8 && ssidPsw.length() > 0) {
                    ToastUtils.showShort(this.mActivity, "请输入正确的Wi-Fi密码");
                } else {
                    startConfig(ssidName, ssidPsw);
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.rl_add_dev_select_wifi /* 2131296683 */:
                Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                startActivity(intent);
                break;
        }
    }

    private void startConfig(String ssidName, String ssidPsw) {
        Intent intent = new Intent(this.mActivity, (Class<?>) DeviceAddSoftApActivity.class);
        intent.putExtra("softApName", this.mScanApName);
        intent.putExtra("productId", this.mProductId);
        intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        intent.putExtra("ssid", ssidName);
        intent.putExtra("password", ssidPsw);
        intent.putExtra("xconfig_key", this.mXConfigKey);
        startActivity(intent);
        finish();
    }
}
