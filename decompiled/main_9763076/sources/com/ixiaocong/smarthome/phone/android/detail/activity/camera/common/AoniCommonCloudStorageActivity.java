package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonCloudStorageActivity extends XcBaseActivity {
    private String mDeviceId;
    private ImageView mIvBack;
    private String mProductId;
    private TextView mTvBuy;
    private TextView mTvBuyHint;
    private TextView mTvComboMsg;
    private TextView mTvComboTitle;

    protected int getLayoutId() {
        return R.layout.activity_aoni_common_cloud_storage;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvBuy = (TextView) $(R.id.tv_aoni_common_cloud_buy);
        this.mTvBuyHint = (TextView) $(R.id.tv_aoni_common_cloud_buy_hint);
        this.mTvComboMsg = (TextView) $(R.id.tv_aoni_common_cloud_combo_msg);
        this.mTvComboTitle = (TextView) $(R.id.tv_aoni_common_cloud_combo_title);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductId = getIntent().getStringExtra("productId");
    }

    protected void onResume() {
        super.onResume();
        loadCloudDetai();
    }

    private void loadCloudDetai() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/getServiceInfo");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonCloudStorageActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                try {
                    JSONObject jsonObj = new JSONObject(var1.getData());
                    int service = jsonObj.optInt("noService");
                    String orderName = jsonObj.optString("orderName");
                    if (TextUtils.isEmpty(orderName) || service != 0) {
                        AoniCommonCloudStorageActivity.this.mTvBuyHint.setVisibility(0);
                        AoniCommonCloudStorageActivity.this.mTvComboMsg.setVisibility(8);
                        AoniCommonCloudStorageActivity.this.mTvComboTitle.setVisibility(8);
                    } else {
                        AoniCommonCloudStorageActivity.this.mTvComboMsg.setText(orderName);
                        AoniCommonCloudStorageActivity.this.mTvBuyHint.setVisibility(8);
                        AoniCommonCloudStorageActivity.this.mTvComboMsg.setVisibility(0);
                        AoniCommonCloudStorageActivity.this.mTvComboTitle.setVisibility(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniCommonCloudStorageActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(AoniCommonCloudStorageActivity$$Lambda$1.lambdaFactory$(this));
        this.mTvBuy.setOnClickListener(AoniCommonCloudStorageActivity$$Lambda$2.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(View v) {
        Intent intent = new Intent(this.mActivity, (Class<?>) AoniCommonBuyComboActivity.class);
        intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        intent.putExtra("productId", this.mProductId);
        startActivity(intent);
    }
}
