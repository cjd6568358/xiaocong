package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.NoDoubleClickUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.area.AreaSelectActivity;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeDetailActivity extends XcBaseActivity implements View.OnClickListener {
    private String mHomeId;
    private String mHomeName;
    private ImageView mIvBack;
    private LinearLayout mLlDeviceList;
    private LinearLayout mLlHomeDeviceGroup;
    private LinearLayout mLlLocation;
    private LinearLayout mLlRename;
    private TextView mTvHasSomeone;
    private TextView mTvHomeName;
    private TextView mTvLocation;
    private TextView mTvSetting;
    private TextView mTvTilte;

    protected int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.activity_home_detail;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mTvTilte = (TextView) $(R.id.centertxt_titlebar);
        this.mTvHomeName = (TextView) $(R.id.tv_home_detail_home_name);
        this.mTvLocation = (TextView) $(R.id.tv_home_detail_home_location);
        this.mTvHasSomeone = (TextView) $(R.id.tv_home_detail_someone_has);
        this.mLlRename = (LinearLayout) $(R.id.ll_home_detail_home_name);
        this.mLlDeviceList = (LinearLayout) $(R.id.ll_home_detail_device_list);
        this.mLlHomeDeviceGroup = (LinearLayout) $(R.id.ll_home_detail_device_group);
        this.mLlLocation = (LinearLayout) $(R.id.ll_home_detail_home_location);
    }

    public void initAdapter() {
        super.initAdapter();
    }

    protected void initData() {
        this.mHomeId = getIntent().getStringExtra("intent_home_id");
        this.mHomeName = getIntent().getStringExtra("intent_home_name");
        if (!TextUtils.isEmpty(this.mHomeName)) {
            this.mTvTilte.setText(this.mHomeName + Constants.MAIN_VERSION_TAG);
            this.mTvHomeName.setText(Constants.MAIN_VERSION_TAG + this.mHomeName);
        }
        if (!TextUtils.isEmpty(this.mHomeId)) {
            getHomeDetail();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Map<String, String> params) {
        if (params != null) {
            this.mTvLocation.setText("家庭定位: " + params.get("address"));
            updateHome(Constants.MAIN_VERSION_TAG, params.get("adcode"));
        }
    }

    private void updateHome(String address, String adcode) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> noSign = new HashMap<>();
        params.put("homeId", this.mHomeId);
        params.put(RNMessageModule.NAME, this.mHomeName);
        noSign.put("address", address);
        noSign.put("adcode", adcode);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(noSign);
        httpSetting.setPath("home/update");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeDetailActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeDetailActivity.this.mActivity, var1.getMsg());
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeDetailActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
        this.mTvHasSomeone.setOnClickListener(this);
        this.mLlRename.setOnClickListener(this);
        this.mLlLocation.setOnClickListener(this);
        this.mLlDeviceList.setOnClickListener(this);
        this.mLlHomeDeviceGroup.setOnClickListener(this);
    }

    protected void getHomeDetail() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("homeId", this.mHomeId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("home/detail");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeDetailActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                try {
                    JSONObject jsonObj = new JSONObject(var1.getData());
                    String region = jsonObj.optString("region");
                    if (!TextUtils.isEmpty(region)) {
                        HomeDetailActivity.this.mTvLocation.setText("家庭定位: " + region);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeDetailActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.ll_home_detail_device_group /* 2131296590 */:
                intent.setClass(this.mActivity, HomeGroupListActivity.class);
                intent.putExtra("intent_home_id", this.mHomeId);
                startActivity(intent);
                break;
            case R.id.ll_home_detail_device_list /* 2131296591 */:
                if (TextUtils.isEmpty(this.mHomeId)) {
                    ToastUtils.showShort(this.mActivity, "加载设备列表失败,请稍后重试");
                } else {
                    intent.setClass(this.mActivity, HomeDeviceListActivity.class);
                    intent.putExtra("intent_home_id", this.mHomeId);
                    startActivity(intent);
                }
                break;
            case R.id.ll_home_detail_home_location /* 2131296592 */:
                intent.setClass(this.mActivity, AreaSelectActivity.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1002);
                break;
            case R.id.ll_home_detail_home_name /* 2131296593 */:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    intent.setClass(this.mActivity, HomeRenameHomeActivity.class);
                    intent.putExtra("intent_home_name", this.mHomeName);
                    intent.putExtra("intent_home_id", this.mHomeId);
                    startActivityForResult(intent, 100);
                }
                break;
            case R.id.tv_home_detail_someone_has /* 2131296990 */:
                intent.setClass(this.mActivity, HomeHasSomeoneActivity.class);
                intent.putExtra("intent_home_id", this.mHomeId);
                startActivity(intent);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 100) {
                String name = data.getStringExtra("intent_home_name");
                if (!TextUtils.isEmpty(name)) {
                    this.mHomeName = name;
                    this.mTvTilte.setText(name + Constants.MAIN_VERSION_TAG);
                    this.mTvHomeName.setText(Constants.MAIN_VERSION_TAG + name);
                    return;
                }
                return;
            }
            if (requestCode == 1002) {
                String location = data.getStringExtra("spLocationCity");
                this.mTvLocation.setText(location);
            }
        }
    }
}
