package com.ixiaocong.smarthome.phone.android.detail.activity.device.setting;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.DeviceSwitchRelationActionParameterAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.SwitchRelationTriggerParameterModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceSwitchRelationActionParameterActivity extends XcBaseActivity implements View.OnClickListener {
    private boolean isParameter = true;
    private DeviceSwitchRelationActionParameterAdapter mAdapter;
    private String mDeviceId;
    private ImageView mIvBack;
    private List<SwitchRelationTriggerParameterModel.ListBean> mList;
    private String mParameterId;
    private String mParameterName;
    private String mProductId;
    private RecyclerView mRvDeviceListView;
    private TextView mTvParameterName;
    private TextView mTvSetting;

    protected int getLayoutId() {
        return R.layout.activity_device_switch_relation_action_parameter;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mTvParameterName = (TextView) $(R.id.tv_device_switch_relation_parameter_name);
        this.mRvDeviceListView = (RecyclerView) $(R.id.rv_tab_home_device_edit_group_recyclerView);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void initAdapter() {
        super.initAdapter();
        this.mRvDeviceListView.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.mAdapter = new DeviceSwitchRelationActionParameterAdapter();
        this.mRvDeviceListView.setAdapter(this.mAdapter);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mParameterName = getIntent().getStringExtra("deviceParameterName");
        this.mProductId = getIntent().getStringExtra("productId");
        this.mParameterId = getIntent().getStringExtra("deviceParameterId");
        if (!TextUtils.isEmpty(this.mParameterName)) {
            this.mTvParameterName.setText(this.mParameterName);
        }
        loadGroupList();
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
    }

    protected void loadGroupList() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", this.mProductId);
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("parameterId", this.mParameterId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("switch/relation/trigger/parameters");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceSwitchRelationActionParameterActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SwitchRelationTriggerParameterModel homeGroupListModel = (SwitchRelationTriggerParameterModel) JSON.parseObject(var1.getData(), SwitchRelationTriggerParameterModel.class);
                DeviceSwitchRelationActionParameterActivity.this.mList = homeGroupListModel.getList();
                if (DeviceSwitchRelationActionParameterActivity.this.mList.size() == 0) {
                    DeviceSwitchRelationActionParameterActivity.this.isParameter = false;
                    ToastUtils.showShort(DeviceSwitchRelationActionParameterActivity.this.mActivity, "没有要配对的设备");
                } else {
                    DeviceSwitchRelationActionParameterActivity.this.isParameter = true;
                }
                DeviceSwitchRelationActionParameterActivity.this.mAdapter.setNewData(DeviceSwitchRelationActionParameterActivity.this.mList);
                DeviceSwitchRelationActionParameterActivity.this.mAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    protected void updateGroup(String position) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("parameterId", this.mParameterId);
        params.put("relationDeviceId", this.mList.get(Integer.valueOf(position).intValue()).getRelationDeviceId());
        params.put("relationParameterId", this.mList.get(Integer.valueOf(position).intValue()).getRelationParameterId());
        httpSetting.setParamsMap(params);
        httpSetting.setPath("switch/relation/add");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceSwitchRelationActionParameterActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceSwitchRelationActionParameterActivity.this.mActivity, "配置成功!");
                DeviceSwitchRelationActionParameterActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceSwitchRelationActionParameterActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void deleteSwitch() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("parameterId", this.mParameterId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("switch/relation/delete");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceSwitchRelationActionParameterActivity.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceSwitchRelationActionParameterActivity.this.mActivity, "解除配对");
                DeviceSwitchRelationActionParameterActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceSwitchRelationActionParameterActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                try {
                    XcLogger.i("tag", this.mAdapter.getSelectItemId());
                    if (TextUtils.isEmpty(this.mAdapter.getSelectItemId())) {
                        if (this.isParameter) {
                            deleteSwitch();
                        } else {
                            ToastUtils.showShort(this.mActivity, "没有要配对的设备");
                        }
                    } else {
                        updateGroup(this.mAdapter.getSelectItemId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                break;
        }
    }
}
