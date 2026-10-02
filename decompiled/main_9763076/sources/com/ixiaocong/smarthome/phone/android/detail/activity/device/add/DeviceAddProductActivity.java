package com.ixiaocong.smarthome.phone.android.detail.activity.device.add;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddStateActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee.DeviceAddSelectGwActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee.DeviceAddZigbeeActivity;
import com.ixiaocong.smarthome.phone.android.detail.adater.AddListAdapter;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.ProductListModel;
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
public class DeviceAddProductActivity extends XcBaseActivity {
    private AddListAdapter mAdapter;
    private RecyclerView mAddListRv;
    private String mCategoryId;
    private String mDeviceId;
    private String mIsGw;
    private ImageView mIvBack;
    private List<ProductListModel.SubProductModel> mListData;
    private TextView mTitleText;

    protected int getLayoutId() {
        return R.layout.activity_dev_add_category;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTitleText = (TextView) $(R.id.centertxt_titlebar);
        this.mAddListRv = (RecyclerView) $(R.id.rv_dev_append_category);
        this.mAddListRv.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mCategoryId = getIntent().getStringExtra("categoryId");
        this.mIsGw = getIntent().getStringExtra("isGw");
    }

    protected void onResume() {
        super.onResume();
        loadAddDevList(this.mCategoryId);
    }

    private void loadAddDevList(String categoryId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("product/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.add.DeviceAddProductActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ProductListModel productListModel = (ProductListModel) JSON.parseObject(var1.getData(), ProductListModel.class);
                DeviceAddProductActivity.this.mListData = productListModel.getProducts();
                DeviceAddProductActivity.this.mAdapter.setNewData(DeviceAddProductActivity.this.mListData);
                DeviceAddProductActivity.this.mAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceAddProductActivity.this.mActivity, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
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
        this.mAdapter = new AddListAdapter(this.mActivity);
        this.mAddListRv.setAdapter(this.mAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(DeviceAddProductActivity$$Lambda$1.lambdaFactory$(this));
        this.mAddListRv.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.add.DeviceAddProductActivity.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if (((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getDeviceType().equals("zigbee")) {
                    if (TextUtils.isEmpty(DeviceAddProductActivity.this.mDeviceId) || !DeviceAddProductActivity.this.mIsGw.equals("isGw")) {
                        if (((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getIsGw().equals(PushConstants.PUSH_TYPE_NOTIFY)) {
                            intent.setClass(DeviceAddProductActivity.this.mActivity, DeviceAddSelectGwActivity.class);
                            intent.putExtra("moduleId", ((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getModuleId());
                        } else if (((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getIsGw().equals("1")) {
                            intent.setClass(DeviceAddProductActivity.this.mActivity, DeviceAddStateActivity.class);
                            intent.putExtra("deviceType", ((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getDeviceType());
                        }
                    } else {
                        intent.setClass(DeviceAddProductActivity.this.mActivity, DeviceAddZigbeeActivity.class);
                        intent.putExtra("moduleId", ((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getModuleId());
                        intent.putExtra("gateway_id", DeviceAddProductActivity.this.mDeviceId);
                    }
                } else {
                    intent.setClass(DeviceAddProductActivity.this.mActivity, DeviceAddStateActivity.class);
                    intent.putExtra("deviceType", ((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getDeviceType());
                }
                intent.putExtra("productName", ((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getProductName());
                intent.putExtra("productId", ((ProductListModel.SubProductModel) DeviceAddProductActivity.this.mListData.get(position)).getProductId());
                DeviceAddProductActivity.this.startActivity(intent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }
}
