package com.ixiaocong.smarthome.phone.android.detail.activity.device.add;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.AddCategoryAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.ProductCategoryModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceAddCategoryActivity extends XcBaseActivity implements View.OnClickListener {
    private RecyclerView mAddCategoryRv;
    private AddCategoryAdapter mCategoryAdapter;
    private String mDeviceId;
    private String mIsGw;
    private ImageView mLeftBack;
    private List<ProductCategoryModel.ProductModel> mListData;

    protected int getLayoutId() {
        return R.layout.activity_dev_add_category;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mLeftBack = (ImageView) $(R.id.left_titlebar_image);
        this.mAddCategoryRv = (RecyclerView) $(R.id.rv_dev_append_category);
        this.mAddCategoryRv.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mIsGw = getIntent().getStringExtra("isGw");
    }

    protected void onResume() {
        super.onResume();
        if (this.mListData != null) {
            this.mListData.clear();
            this.mCategoryAdapter.notifyDataSetChanged();
        }
        loadCategoryList();
    }

    private void loadCategoryList() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("product/category");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.add.DeviceAddCategoryActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ProductCategoryModel productCategoryModel = (ProductCategoryModel) JSON.parseObject(var1.getData(), ProductCategoryModel.class);
                DeviceAddCategoryActivity.this.mListData = productCategoryModel.getList();
                DeviceAddCategoryActivity.this.mCategoryAdapter.setNewData(DeviceAddCategoryActivity.this.mListData);
                DeviceAddCategoryActivity.this.mCategoryAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceAddCategoryActivity.this.mActivity, var1.getErrorMessage());
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
        this.mCategoryAdapter = new AddCategoryAdapter();
        this.mAddCategoryRv.setAdapter(this.mCategoryAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mLeftBack.setOnClickListener(this);
        this.mAddCategoryRv.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.add.DeviceAddCategoryActivity.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent(DeviceAddCategoryActivity.this.mActivity, (Class<?>) DeviceAddProductActivity.class);
                intent.putExtra("categoryId", ((ProductCategoryModel.ProductModel) DeviceAddCategoryActivity.this.mListData.get(position)).getCategoryId());
                intent.putExtra("categoryCode", ((ProductCategoryModel.ProductModel) DeviceAddCategoryActivity.this.mListData.get(position)).getCategoryCode());
                intent.putExtra("isGw", "isGw");
                intent.putExtra(Constants.FLAG_DEVICE_ID, DeviceAddCategoryActivity.this.mDeviceId);
                DeviceAddCategoryActivity.this.startActivity(intent);
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
