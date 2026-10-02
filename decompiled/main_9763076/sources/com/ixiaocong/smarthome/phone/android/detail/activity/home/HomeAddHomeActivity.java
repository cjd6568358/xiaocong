package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
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
public class HomeAddHomeActivity extends XcBaseActivity implements View.OnClickListener {
    private EditText mEtHomeName;
    private ImageView mIvBack;
    private int mStartCode;
    private TextView mTvSetting;

    protected int getLayoutId() {
        return R.layout.activity_home_add_home;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mEtHomeName = (EditText) $(R.id.et_home_add_home);
    }

    public void initAdapter() {
        super.initAdapter();
    }

    protected void initData() {
        this.mStartCode = getIntent().getIntExtra("intentCode", 0);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
    }

    protected void addHome(final String homeName) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(RNMessageModule.NAME, homeName);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("home/add");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeAddHomeActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeAddHomeActivity.this.mActivity, "添加成功");
                if (HomeAddHomeActivity.this.mStartCode == 1003) {
                    try {
                        JSONObject obj = new JSONObject(var1.getData());
                        String homeId = obj.optString("id");
                        Intent intent = new Intent("add.device.success.action");
                        intent.putExtra("intent_home_name", homeName);
                        intent.putExtra("intent_home_id", homeId);
                        LocalBroadcastManager.getInstance(HomeAddHomeActivity.this.mActivity).sendBroadcast(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                HomeAddHomeActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeAddHomeActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void clickFinish() {
        String homeName = this.mEtHomeName.getText().toString().trim();
        if (TextUtils.isEmpty(homeName)) {
            ToastUtils.showShort(this.mActivity, "家庭名称不能为空");
        } else if (homeName.length() < 2 || homeName.length() > 18) {
            ToastUtils.showShort(this.mActivity, "家庭名称长度需要为2-18位");
        } else {
            addHome(homeName);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                clickFinish();
                break;
        }
    }
}
