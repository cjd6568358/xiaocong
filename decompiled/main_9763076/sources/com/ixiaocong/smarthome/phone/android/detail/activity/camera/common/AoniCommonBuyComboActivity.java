package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.AoniCommonBuyComboAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.CameraCommonBuyComboModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AoniCommonBuyComboActivity extends XcBaseActivity {
    private AoniCommonBuyComboAdapter mComboAdapter;
    private String mDeviceId;
    private ImageView mIvBack;
    private String mProductId;
    private RecyclerView mRvBuyCombo;
    private TextView mTvComboHint;
    private TextView mTvRight;

    protected int getLayoutId() {
        return R.layout.activity_aoni_common_buy_combo;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvRight = (TextView) $(R.id.right_titlebar_text);
        this.mTvComboHint = (TextView) $(R.id.tv_aoni_common_buy_combo_hint);
        this.mRvBuyCombo = (RecyclerView) $(R.id.rv_common_camera_buy_combo);
        this.mRvBuyCombo.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mProductId = getIntent().getStringExtra("productId");
        loagComboList();
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
        this.mComboAdapter = new AoniCommonBuyComboAdapter();
        this.mRvBuyCombo.setAdapter(this.mComboAdapter);
    }

    private void loagComboList() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        params.put("productId", this.mProductId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("camera/pay/template");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonBuyComboActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                CameraCommonBuyComboModel comboModel = (CameraCommonBuyComboModel) JSON.parseObject(var1.getData(), CameraCommonBuyComboModel.class);
                if (comboModel == null || comboModel.getList() == null || comboModel.getList().size() == 0) {
                    AoniCommonBuyComboActivity.this.mTvComboHint.setVisibility(0);
                    AoniCommonBuyComboActivity.this.mRvBuyCombo.setVisibility(8);
                } else {
                    AoniCommonBuyComboActivity.this.mTvComboHint.setVisibility(8);
                    AoniCommonBuyComboActivity.this.mRvBuyCombo.setVisibility(0);
                    AoniCommonBuyComboActivity.this.mComboAdapter.setNewData(comboModel.getList());
                    AoniCommonBuyComboActivity.this.mComboAdapter.notifyDataSetChanged();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(AoniCommonBuyComboActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(AoniCommonBuyComboActivity$$Lambda$1.lambdaFactory$(this));
        this.mTvRight.setOnClickListener(AoniCommonBuyComboActivity$$Lambda$2.lambdaFactory$(this));
        this.mRvBuyCombo.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.AoniCommonBuyComboActivity.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                ToastUtils.showShort(AoniCommonBuyComboActivity.this.mActivity, "暂未开通,敬请期待");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(View v) {
        Intent intent = new Intent(this.mActivity, (Class<?>) AoniCommonBuyHistoryActivity.class);
        intent.putExtra(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        startActivity(intent);
    }
}
