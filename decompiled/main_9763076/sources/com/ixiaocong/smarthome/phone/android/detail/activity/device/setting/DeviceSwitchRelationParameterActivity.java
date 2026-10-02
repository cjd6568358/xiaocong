package com.ixiaocong.smarthome.phone.android.detail.activity.device.setting;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.DeviceSwitchRelationParameterListAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.SwitchRelationParametersModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceSwitchRelationParameterActivity extends XcBaseActivity implements View.OnClickListener {
    private DeviceSwitchRelationParameterListAdapter mAdapter;
    private String mDeviceId;
    private ImageView mIvBack;
    private String mProductId;
    private RecyclerView mRecyclerView;
    private List<SwitchRelationParametersModel.ListBean> parametersBeans;

    protected int getLayoutId() {
        return R.layout.activity_device_switch_relation;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mRecyclerView = (RecyclerView) $(R.id.rv_device_switch_relation);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductId = getIntent().getStringExtra("productId");
        loadParameterList();
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mRecyclerView.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceSwitchRelationParameterActivity.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent(DeviceSwitchRelationParameterActivity.this.mActivity, (Class<?>) DeviceSwitchRelationActionParameterActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, DeviceSwitchRelationParameterActivity.this.mDeviceId);
                intent.putExtra("productId", DeviceSwitchRelationParameterActivity.this.mProductId);
                intent.putExtra("deviceParameterId", ((SwitchRelationParametersModel.ListBean) DeviceSwitchRelationParameterActivity.this.parametersBeans.get(position)).getParameterId());
                intent.putExtra("deviceParameterName", ((SwitchRelationParametersModel.ListBean) DeviceSwitchRelationParameterActivity.this.parametersBeans.get(position)).getParameterName());
                DeviceSwitchRelationParameterActivity.this.startActivity(intent);
            }
        });
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
        this.mAdapter = new DeviceSwitchRelationParameterListAdapter();
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    private void loadParameterList() {
        if (TextUtils.isEmpty(this.mDeviceId) || TextUtils.isEmpty(this.mProductId)) {
            ToastUtils.showShort(this.mActivity, "网络错误,请稍后重试!");
            return;
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("productId", this.mProductId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("switch/relation/parameters");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceSwitchRelationParameterActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SwitchRelationParametersModel deviceParameterModifiableModel = (SwitchRelationParametersModel) JSON.parseObject(var1.getData(), SwitchRelationParametersModel.class);
                DeviceSwitchRelationParameterActivity.this.parametersBeans = deviceParameterModifiableModel.getList();
                if (DeviceSwitchRelationParameterActivity.this.parametersBeans.size() == 0) {
                    ToastUtils.showShort(DeviceSwitchRelationParameterActivity.this.mActivity, "参数列表加载失败,请重试!");
                }
                DeviceSwitchRelationParameterActivity.this.mAdapter.setNewData(DeviceSwitchRelationParameterActivity.this.parametersBeans);
                DeviceSwitchRelationParameterActivity.this.mAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
        }
    }
}
