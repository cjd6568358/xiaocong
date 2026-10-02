package com.ixiaocong.smarthome.phone.android.detail.activity.device.setting;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.PromptDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.DeviceParameterRenameAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.DeviceParameterModifiableModel;
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
public class DeviceParameterRenameActivity extends XcBaseActivity implements View.OnClickListener {
    private DeviceParameterRenameAdapter deviceParameterRenameAdapter;
    private String mDeviceId;
    private ImageView mIvBack;
    private RecyclerView mRecyclerView;
    private List<DeviceParameterModifiableModel.ParametersBean> parametersBeans;

    protected int getLayoutId() {
        return R.layout.activity_device_parameter_rename;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mRecyclerView = (RecyclerView) $(R.id.rv_device_parameter_rename_recycler);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        loadParameterList();
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mRecyclerView.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceParameterRenameActivity.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                DeviceParameterRenameActivity.this.renameParSetting(((DeviceParameterModifiableModel.ParametersBean) DeviceParameterRenameActivity.this.parametersBeans.get(position)).getDeviceParameterId(), ((DeviceParameterModifiableModel.ParametersBean) DeviceParameterRenameActivity.this.parametersBeans.get(position)).getParameterName());
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
        this.deviceParameterRenameAdapter = new DeviceParameterRenameAdapter();
        this.mRecyclerView.setAdapter(this.deviceParameterRenameAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadParameterList() {
        if (TextUtils.isEmpty(this.mDeviceId)) {
            ToastUtils.showShort(this.mActivity, "网络错误,请稍后重试!");
            return;
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("parameter/modifiable/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceParameterRenameActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                DeviceParameterModifiableModel deviceParameterModifiableModel = (DeviceParameterModifiableModel) JSON.parseObject(var1.getData(), DeviceParameterModifiableModel.class);
                DeviceParameterRenameActivity.this.parametersBeans = deviceParameterModifiableModel.getParameters();
                DeviceParameterRenameActivity.this.deviceParameterRenameAdapter.setNewData(DeviceParameterRenameActivity.this.parametersBeans);
                DeviceParameterRenameActivity.this.deviceParameterRenameAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceParameterRenameActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void renameParSetting(String parameterId, String parName) {
        PromptDialog renameParDialog = new PromptDialog(this.mActivity, R.style.inputDialog);
        renameParDialog.confirmColor = R.color.master_color;
        renameParDialog.cancelColor = R.color.gray_6;
        renameParDialog.dialogType = 1002;
        renameParDialog.title = "设备参数重命名";
        renameParDialog.editHint = parName;
        renameParDialog.setConfirmListener(DeviceParameterRenameActivity$$Lambda$1.lambdaFactory$(this, renameParDialog, parameterId));
        renameParDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$renameParSetting$0(PromptDialog renameParDialog, String parameterId, View v) {
        String renameParamter = renameParDialog.getEditText().getText().toString().trim();
        if (!TextUtils.isEmpty(renameParamter)) {
            sendParamterRenameHttp(parameterId, renameParamter);
            renameParDialog.dismiss();
        } else {
            ToastUtils.showShort(this.mActivity, "设备参数名称不能为空");
        }
    }

    private void sendParamterRenameHttp(String parameterId, String parameterName) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("deviceParameterId", parameterId);
        params.put("parameterName", parameterName);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("parameter/rename");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.setting.DeviceParameterRenameActivity.3
            public void onComplete(XCResponseBean var1) {
                DeviceParameterRenameActivity.this.loadParameterList();
                ToastUtils.showShort(DeviceParameterRenameActivity.this.mActivity, "更改成功!");
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceParameterRenameActivity.this.mActivity, var1.getErrorMessage());
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
