package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.HomeDeviceEditGroupAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.HomeGroupListModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeDeviceEditGroupActivity extends XcBaseActivity implements View.OnClickListener {
    private HomeDeviceEditGroupAdapter homeDeviceEditGroupAdapter;
    private String mDeviceId;
    private String mDeviceName;
    private TextView mEtHomeName;
    private String mGroupId;
    private ImageView mIvBack;
    private RecyclerView mRvDeviceListView;
    private TextView mTvSetting;

    protected int getLayoutId() {
        return R.layout.activity_home_device_edit_group;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mEtHomeName = (TextView) $(R.id.tv_tab_home_device_edit_group_name);
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
        this.homeDeviceEditGroupAdapter = new HomeDeviceEditGroupAdapter();
        this.mRvDeviceListView.setAdapter(this.homeDeviceEditGroupAdapter);
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mDeviceName = getIntent().getStringExtra("deviceName");
        this.mGroupId = getIntent().getStringExtra("intent_group_id");
        if (this.mDeviceName != null && this.mDeviceName.length() > 0) {
            this.mEtHomeName.setText(this.mDeviceName + Constants.MAIN_VERSION_TAG);
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
        params.put("homeId", Constants.MAIN_VERSION_TAG);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("group/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeDeviceEditGroupActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeGroupListModel homeGroupListModel = (HomeGroupListModel) JSON.parseObject(var1.getData(), HomeGroupListModel.class);
                HomeDeviceEditGroupActivity.this.homeDeviceEditGroupAdapter.setNewData(homeGroupListModel.getGroupList());
                HomeDeviceEditGroupActivity.this.homeDeviceEditGroupAdapter.setSelect(HomeDeviceEditGroupActivity.this.mGroupId);
                HomeDeviceEditGroupActivity.this.homeDeviceEditGroupAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeDeviceEditGroupActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void updateGroup(String groupId) {
        if (TextUtils.isEmpty(groupId)) {
            ToastUtils.showShort(this.mActivity, "请选择一个分组");
            return;
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        paramsNoSign.put("deviceName", Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("homeId", Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("groupId", groupId);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("device/update");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeDeviceEditGroupActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeDeviceEditGroupActivity.this.mActivity, "修改成功");
                HomeDeviceEditGroupActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeDeviceEditGroupActivity.this.mActivity, var1.getErrorMessage());
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
                XcLogger.i("tag", this.homeDeviceEditGroupAdapter.getSelectItemId());
                updateGroup(this.homeDeviceEditGroupAdapter.getSelectItemId());
                break;
        }
    }
}
