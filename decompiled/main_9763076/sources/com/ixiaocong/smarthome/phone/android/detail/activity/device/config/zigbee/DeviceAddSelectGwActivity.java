package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.add.DeviceAddCategoryActivity;
import com.ixiaocong.smarthome.phone.android.detail.adater.AddSelectGwAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.SelectGwModel;
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
public class DeviceAddSelectGwActivity extends XcBaseActivity implements HintDialogCallback {
    private AddSelectGwAdapter mAdapter;
    private ImageView mIvBack;
    private List<SelectGwModel.GwModel> mListData;
    private String mModuleId;
    private String mProductId;
    private String mProductName;
    private RecyclerView mRvGw;
    private TextView mTvTitle;

    protected int getLayoutId() {
        return R.layout.activity_dev_add_category;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvTitle = (TextView) $(R.id.centertxt_titlebar);
        this.mRvGw = (RecyclerView) $(R.id.rv_dev_append_category);
        this.mRvGw.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.mTvTitle.setText("选择网关");
        ActivityManagerUtil.getScreenManager().pushActivity(this);
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
    protected void initData() {
        this.mProductName = getIntent().getStringExtra("productName");
        this.mProductId = getIntent().getStringExtra("productId");
        this.mModuleId = getIntent().getStringExtra("moduleId");
        this.mAdapter = new AddSelectGwAdapter();
        this.mRvGw.setAdapter(this.mAdapter);
        loadData();
    }

    private void loadData() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", this.mProductId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/list/gw");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee.DeviceAddSelectGwActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SelectGwModel selectGwModel = (SelectGwModel) JSON.parseObject(var1.getData(), SelectGwModel.class);
                DeviceAddSelectGwActivity.this.mListData = selectGwModel.getDevices();
                if (DeviceAddSelectGwActivity.this.mListData == null || DeviceAddSelectGwActivity.this.mListData.size() == 0) {
                    OperationHintDialog.getInstance().showSelectDialog(DeviceAddSelectGwActivity.this.mActivity, DeviceAddSelectGwActivity.this, "无可用网关", "子设备添加需要先添加网关");
                } else {
                    DeviceAddSelectGwActivity.this.mAdapter.setNewData(selectGwModel.getDevices());
                    DeviceAddSelectGwActivity.this.mAdapter.notifyDataSetChanged();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceAddSelectGwActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(DeviceAddSelectGwActivity$$Lambda$1.lambdaFactory$(this));
        this.mRvGw.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee.DeviceAddSelectGwActivity.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent(DeviceAddSelectGwActivity.this.mActivity, (Class<?>) DeviceAddZigbeeActivity.class);
                intent.putExtra("productId", DeviceAddSelectGwActivity.this.mProductId);
                intent.putExtra("productName", DeviceAddSelectGwActivity.this.mProductName);
                intent.putExtra("moduleId", DeviceAddSelectGwActivity.this.mModuleId);
                intent.putExtra("gateway_id", ((SelectGwModel.GwModel) DeviceAddSelectGwActivity.this.mListData.get(position)).getDeviceId());
                DeviceAddSelectGwActivity.this.startActivity(intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            startActivity(new Intent(this.mActivity, (Class<?>) DeviceAddCategoryActivity.class));
            finish();
        } else {
            finish();
        }
    }
}
