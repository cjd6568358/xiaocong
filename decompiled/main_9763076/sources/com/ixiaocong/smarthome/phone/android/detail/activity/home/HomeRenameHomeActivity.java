package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.content.Intent;
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

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeRenameHomeActivity extends XcBaseActivity implements View.OnClickListener {
    private EditText mEtHomeName;
    private String mHomeId;
    private String mHomeName;
    private ImageView mIvBack;
    private TextView mTvSetting;

    protected int getLayoutId() {
        return R.layout.activity_home_rename;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mEtHomeName = (EditText) $(R.id.et_home_rename_name);
    }

    public void initAdapter() {
        super.initAdapter();
    }

    protected void initData() {
        this.mHomeName = getIntent().getStringExtra("intent_home_name");
        this.mHomeId = getIntent().getStringExtra("intent_home_id");
        if (!TextUtils.isEmpty(this.mHomeName)) {
            this.mEtHomeName.setText(this.mHomeName);
            this.mEtHomeName.setSelection(this.mHomeName.length());
        }
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
    }

    protected void editHomeName(String homeId, final String homeName) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("homeId", homeId);
        params.put(RNMessageModule.NAME, homeName);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("home/update");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeRenameHomeActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeRenameHomeActivity.this.mActivity, "修改成功");
                Intent intent = new Intent();
                intent.putExtra("intent_home_name", homeName);
                HomeRenameHomeActivity.this.setResult(100, intent);
                HomeRenameHomeActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeRenameHomeActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void clickFinish() {
        String homeName = this.mEtHomeName.getText().toString().trim();
        if (TextUtils.isEmpty(homeName)) {
            ToastUtils.showShort(this.mActivity, "家庭名称不能为空");
            return;
        }
        if (homeName.equals(this.mHomeName)) {
            finish();
        } else if (TextUtils.isEmpty(this.mHomeId)) {
            ToastUtils.showShort(this.mActivity, "数据异常,请稍后重试");
        } else {
            editHomeName(this.mHomeId, homeName);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                clickFinish();
                break;
        }
    }
}
