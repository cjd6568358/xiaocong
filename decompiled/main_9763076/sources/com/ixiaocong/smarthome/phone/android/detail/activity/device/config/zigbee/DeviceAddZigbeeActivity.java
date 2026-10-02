package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.BindDeviceManager;
import com.ixiaocong.smarthome.phone.android.common.manager.BindSoftApManager;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.CircleProgressBar;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.XConfigErrorHelpActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.ProductNetConfModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceAddZigbeeActivity extends XcBaseActivity implements HintDialogCallback {
    private boolean isAddZigbee = false;
    private ZigbeeAddCountDown mAddCountDown;
    private CircleProgressBar mAddProgress;
    private Button mBtnAdd;
    private Button mBtnError;
    private String mGwId;
    private ImageView mIvBack;
    private ImageView mIvDevImg;
    private String mModuleId;
    private String mProductId;
    private String mProductImg;
    private String mProductName;
    private TextView mTvDevHint;
    private TextView mTvDevName;
    private TextView mTvTitle;
    private String mWebUrl;
    private ZigbeeAddReceiver mZigbeeReceiver;

    protected int getLayoutId() {
        return R.layout.activity_dev_add_zigbee;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvDevImg = (ImageView) $(R.id.iv_add_zigbee_dev_img);
        this.mTvTitle = (TextView) $(R.id.centertxt_titlebar);
        this.mTvDevName = (TextView) $(R.id.tv_add_zigbee_dev_name);
        this.mTvDevHint = (TextView) $(R.id.tv_add_zigbee_dev_hint_text);
        this.mBtnAdd = (Button) $(R.id.btn_add_zigbee_dev_add);
        this.mBtnError = (Button) $(R.id.btn_add_dev_zigbee_error);
        this.mAddProgress = (CircleProgressBar) $(R.id.cpb_add_zigbee_dev);
        this.mAddProgress.setUnit(NotifyType.SOUND);
        this.mAddProgress.setMaxProgress(89);
        this.mIvBack.setVisibility(4);
        ActivityManagerUtil.getScreenManager().pushActivity(this);
        setSwipeBackEnable(false);
        this.mZigbeeReceiver = new ZigbeeAddReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("device.append.zigbee.action");
        LocalBroadcastManager.getInstance(this.mActivity).registerReceiver(this.mZigbeeReceiver, intentFilter);
        this.mAddCountDown = new ZigbeeAddCountDown(90000L, 1000L);
    }

    protected void initData() {
        this.mProductName = getIntent().getStringExtra("productName");
        this.mProductId = getIntent().getStringExtra("productId");
        this.mModuleId = getIntent().getStringExtra("moduleId");
        this.mGwId = getIntent().getStringExtra("gateway_id");
        if (!TextUtils.isEmpty(this.mProductName)) {
            this.mTvDevName.setText(this.mProductName);
        }
        loadNetConfig();
    }

    private void loadNetConfig() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", this.mProductId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("product/help");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee.DeviceAddZigbeeActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ProductNetConfModel productNetConfModel = (ProductNetConfModel) JSON.parseObject(var1.getData(), ProductNetConfModel.class);
                DeviceAddZigbeeActivity.this.mWebUrl = productNetConfModel.getHelp().getFaq();
                DeviceAddZigbeeActivity.this.mProductImg = productNetConfModel.getHelp().getImage();
                Glide.with(DeviceAddZigbeeActivity.this.mActivity).load(DeviceAddZigbeeActivity.this.mProductImg).placeholder(R.drawable.default_img_icon).into(DeviceAddZigbeeActivity.this.mIvDevImg);
                DeviceAddZigbeeActivity.this.mTvDevHint.setText(productNetConfModel.getHelp().getIntro());
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceAddZigbeeActivity.this.mActivity, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mBtnAdd.setOnClickListener(DeviceAddZigbeeActivity$$Lambda$1.lambdaFactory$(this));
        this.mBtnError.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee.DeviceAddZigbeeActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(DeviceAddZigbeeActivity.this.mActivity, (Class<?>) XConfigErrorHelpActivity.class);
                intent.putExtra("webViewUrl", DeviceAddZigbeeActivity.this.mWebUrl);
                DeviceAddZigbeeActivity.this.startActivity(intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        if (!TextUtils.isEmpty(this.mGwId)) {
            XcLogger.i("appendZibeePublish--", "mGwId:" + this.mGwId + ",mProductId:" + this.mProductId + ",mModuleId:" + this.mModuleId);
            XCDeviceController.getInstance().appendZibeePublish(this.mActivity, this.mGwId, this.mProductId, this.mModuleId);
            this.mBtnAdd.setVisibility(8);
            this.mBtnError.setVisibility(8);
            this.mAddProgress.setVisibility(0);
            this.mTvTitle.setText("设备入网");
            this.isAddZigbee = true;
            this.mAddCountDown.start();
            return;
        }
        ToastUtils.showShort(this.mActivity, "获取网关ID失败");
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mZigbeeReceiver != null) {
            LocalBroadcastManager.getInstance(this.mActivity).unregisterReceiver(this.mZigbeeReceiver);
        }
    }

    public void onBackPressed() {
        if (this.isAddZigbee) {
            ToastUtils.showShort(this.mActivity, "当前设备正在入网,请耐心等待");
        } else {
            super.onBackPressed();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        finish();
    }

    private class ZigbeeAddReceiver extends BroadcastReceiver {
        private ZigbeeAddReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("device.append.zigbee.action")) {
                intent.getStringExtra("value");
                String jsonMsg = intent.getStringExtra("manageMsg");
                XcLogger.i("zigbeeAppendReceiver--", jsonMsg);
                if (!TextUtils.isEmpty(jsonMsg)) {
                    try {
                        JSONObject reciverJson = new JSONObject(jsonMsg);
                        String deviceId = reciverJson.optString(Constants.FLAG_DEVICE_ID);
                        String productId = reciverJson.optString("productId");
                        String senderId = reciverJson.optString("senderId");
                        if (!TextUtils.isEmpty(deviceId) && !TextUtils.isEmpty(productId) && !TextUtils.isEmpty(senderId) && productId.equals(DeviceAddZigbeeActivity.this.mProductId) && senderId.equals(DeviceAddZigbeeActivity.this.mGwId) && DeviceAddZigbeeActivity.this.isAddZigbee) {
                            DeviceAddZigbeeActivity.this.isAddZigbee = false;
                            DeviceAddZigbeeActivity.this.mAddCountDown.cancel();
                            DeviceAddZigbeeActivity.this.queryDeviceInfo(deviceId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ZigbeeAddCountDown extends CountDownTimer {
        public ZigbeeAddCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long millisUntilFinished) {
            DeviceAddZigbeeActivity.this.runOnUiThread(DeviceAddZigbeeActivity$ZigbeeAddCountDown$$Lambda$1.lambdaFactory$(this, millisUntilFinished));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTick$0(long millisUntilFinished) {
            int millis = ((int) (millisUntilFinished / 1000)) - 1;
            DeviceAddZigbeeActivity.this.mAddProgress.setProgressNotInUiThread((int) (90 - (millisUntilFinished / 1000)));
            DeviceAddZigbeeActivity.this.mAddProgress.setCenterText(millis >= 0 ? millis + Constants.MAIN_VERSION_TAG : PushConstants.PUSH_TYPE_NOTIFY);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            ToastUtils.showShort(DeviceAddZigbeeActivity.this.mActivity, "设备搜索超时,请重新尝试");
            HashMap<String, Object> signParams = new HashMap<>();
            HashMap<String, Object> commonParams = new HashMap<>();
            signParams.put("productId", DeviceAddZigbeeActivity.this.mProductId);
            signParams.put("errorCode", 7);
            commonParams.put("discoverTime", "90");
            BindSoftApManager.bindDeviceLog(DeviceAddZigbeeActivity.this.mActivity, signParams, commonParams);
            DeviceAddZigbeeActivity.this.isAddZigbee = false;
            DeviceAddZigbeeActivity.this.mAddProgress.setVisibility(8);
            DeviceAddZigbeeActivity.this.mBtnAdd.setVisibility(0);
            DeviceAddZigbeeActivity.this.mBtnError.setVisibility(0);
            DeviceAddZigbeeActivity.this.mTvTitle.setText("设备配网说明");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void queryDeviceInfo(String deviceId) {
        BindDeviceManager.queryOtherDeviceInfo(this.mActivity, this, deviceId);
    }
}
