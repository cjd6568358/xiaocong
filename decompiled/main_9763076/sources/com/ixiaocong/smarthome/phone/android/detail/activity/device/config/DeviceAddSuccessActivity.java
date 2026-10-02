package com.ixiaocong.smarthome.phone.android.detail.activity.device.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SelectDevHomeOrGroupPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.user.EditUserDataActivity;
import com.ixiaocong.smarthome.phone.rn.init.RNCacheViewManager;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceAddSuccessActivity extends XcBaseActivity implements View.OnClickListener, SelectDevHomeOrGroupPop.ResultCallback {
    private Boolean isSelectHome = true;
    private AddSuccessReceiver mAddSuccessReceiver;
    private Button mBtnAppend;
    private String mDevImgUrl;
    private String mDeviceId;
    private String mGroupId;
    private String mHomeId;
    private ImageView mIvBack;
    private ImageView mIvDevImg;
    private ImageView mIvEdit;
    private LinearLayout mLlSelectGroup;
    private LinearLayout mLlSelectHome;
    private String mProductId;
    private String mProductName;
    private TextView mTvDevName;
    private TextView mTvGroupName;
    private TextView mTvHomeName;

    protected int getLayoutId() {
        return R.layout.activity_dev_add_success;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvBack.setVisibility(4);
        this.mIvDevImg = (ImageView) $(R.id.iv_add_dev_success_img);
        this.mIvEdit = (ImageView) $(R.id.iv_add_dev_success_edit_name);
        this.mTvDevName = (TextView) $(R.id.tv_add_dev_success_dev_name);
        this.mTvHomeName = (TextView) $(R.id.tv_ll_add_dev_success_select_home_name);
        this.mTvGroupName = (TextView) $(R.id.tv_ll_add_dev_success_select_group_name);
        this.mBtnAppend = (Button) $(R.id.btn_add_dev_success);
        this.mLlSelectGroup = (LinearLayout) $(R.id.ll_add_dev_success_select_group);
        this.mLlSelectHome = (LinearLayout) $(R.id.ll_add_dev_success_select_home);
    }

    protected void initData() {
        this.mDevImgUrl = getIntent().getStringExtra("productImg");
        this.mProductName = getIntent().getStringExtra("productName");
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        if (!TextUtils.isEmpty(this.mDevImgUrl)) {
            Glide.with(this.mActivity).load(this.mDevImgUrl).asBitmap().into(new SimpleTarget<Bitmap>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddSuccessActivity.1
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(Object obj, GlideAnimation glideAnimation) {
                    onResourceReady((Bitmap) obj, (GlideAnimation<? super Bitmap>) glideAnimation);
                }

                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    DeviceAddSuccessActivity.this.mIvDevImg.setImageBitmap(resource);
                }
            });
        }
        this.mAddSuccessReceiver = new AddSuccessReceiver();
        LocalBroadcastManager.getInstance(this.mActivity).registerReceiver(this.mAddSuccessReceiver, new IntentFilter("add.device.success.action"));
        loadData();
    }

    private void loadData() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/getName");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddSuccessActivity.2
            public void onComplete(XCResponseBean var1) {
                try {
                    JSONObject jsonObj = new JSONObject(var1.getData().toString());
                    String deviceName = jsonObj.optString("deviceName");
                    if (!TextUtils.isEmpty(deviceName)) {
                        DeviceAddSuccessActivity.this.mTvDevName.setText(deviceName);
                    } else if (!TextUtils.isEmpty(DeviceAddSuccessActivity.this.mProductName)) {
                        DeviceAddSuccessActivity.this.mTvDevName.setText(DeviceAddSuccessActivity.this.mProductName);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceAddSuccessActivity.this.mActivity, var1.getErrorMessage());
                if (!TextUtils.isEmpty(DeviceAddSuccessActivity.this.mProductName)) {
                    DeviceAddSuccessActivity.this.mTvDevName.setText(DeviceAddSuccessActivity.this.mProductName);
                }
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mBtnAppend.setOnClickListener(this);
        this.mIvEdit.setOnClickListener(this);
        this.mLlSelectHome.setOnClickListener(this);
        this.mLlSelectGroup.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_dev_success /* 2131296314 */:
                String devName = this.mTvDevName.getText().toString();
                if (TextUtils.isEmpty(devName)) {
                    ToastUtils.showShort(this.mActivity, "设备名称不能为空");
                } else {
                    commitData(devName);
                }
                break;
            case R.id.iv_add_dev_success_edit_name /* 2131296476 */:
                Intent intent = new Intent(this.mActivity, (Class<?>) EditUserDataActivity.class);
                intent.putExtra("deviceName", this.mTvDevName.getText().toString().trim());
                intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
                intent.putExtra("intentCode", 1001);
                startActivityForResult(intent, 100);
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.ll_add_dev_success_select_group /* 2131296568 */:
                this.isSelectHome = false;
                SelectDevHomeOrGroupPop.getInstance().showPop(this.mActivity, this, this.mIvBack, this.isSelectHome.booleanValue(), this.mHomeId);
                break;
            case R.id.ll_add_dev_success_select_home /* 2131296569 */:
                this.isSelectHome = true;
                SelectDevHomeOrGroupPop.getInstance().showPop(this.mActivity, this, this.mIvBack, this.isSelectHome.booleanValue(), this.mHomeId);
                break;
        }
    }

    private void commitData(String devName) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> signParams = new HashMap<>();
        signParams.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        HashMap<String, Object> otherParams = new HashMap<>();
        otherParams.put("deviceName", devName);
        otherParams.put("homeId", this.mHomeId);
        otherParams.put("groupId", this.mGroupId);
        httpSetting.setParamsMap(signParams);
        httpSetting.setParamsMapNoSign(otherParams);
        httpSetting.setPath("device/update");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddSuccessActivity.3
            public void onComplete(XCResponseBean var1) {
                DeviceAddSuccessActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceAddSuccessActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    private void startView() {
        RNCacheViewManager.getInstance().removeCache(this.mProductId);
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this.mActivity).unregisterReceiver(this.mAddSuccessReceiver);
    }

    public void onBackPressed() {
        startView();
        super.onBackPressed();
    }

    @Override // com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SelectDevHomeOrGroupPop.ResultCallback
    public void resultData(String name, String id) {
        if (this.isSelectHome.booleanValue()) {
            this.mHomeId = id;
            this.mTvHomeName.setText(name);
        } else {
            this.mGroupId = id;
            this.mTvGroupName.setText(name);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            String deviceName = data.getStringExtra("deviceName");
            this.mTvDevName.setText(deviceName);
        }
    }

    private class AddSuccessReceiver extends BroadcastReceiver {
        private AddSuccessReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String homeName = intent.getStringExtra("intent_home_name");
            String homeId = intent.getStringExtra("intent_home_id");
            String groupName = intent.getStringExtra("intent_group_name");
            String groupId = intent.getStringExtra("intent_group_id");
            if (!TextUtils.isEmpty(homeName) && !TextUtils.isEmpty(homeId)) {
                DeviceAddSuccessActivity.this.mTvHomeName.setText(homeName);
                DeviceAddSuccessActivity.this.mHomeId = homeId;
            }
            if (!TextUtils.isEmpty(groupName) && !TextUtils.isEmpty(groupName)) {
                DeviceAddSuccessActivity.this.mTvGroupName.setText(groupName);
                DeviceAddSuccessActivity.this.mGroupId = groupId;
            }
        }
    }
}
