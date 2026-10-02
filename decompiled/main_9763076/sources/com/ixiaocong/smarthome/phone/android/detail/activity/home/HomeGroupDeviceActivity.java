package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.HomeAddGroupAdapter;
import com.ixiaocong.smarthome.phone.android.detail.adater.HomeGroupDeviceAdapter;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.HomeDeviceListModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeGroupDeviceActivity extends XcBaseActivity implements View.OnClickListener, HomeAddGroupAdapter.AddGroupListener, HomeGroupDeviceAdapter.GroupDeviceSelectListener {
    private List<HomeDeviceListModel.DeviceListBean> deviceListBeans;
    private HomeGroupDeviceAdapter homeGroupDeviceAdapter;
    private boolean isSelectAll = false;
    private EditText mEtGroupName;
    private String mGroupId;
    private String mHomeId;
    private ImageView mIvBack;
    private RecyclerView mRvDeviceListView;
    private TextView mTvSelectAll;
    private TextView mTvSetting;

    protected int getLayoutId() {
        return R.layout.activity_home_group_device;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mEtGroupName = (EditText) $(R.id.et_home_group_device_name);
        this.mTvSelectAll = (TextView) $(R.id.tv_home_group_device_select_all);
        this.mRvDeviceListView = (RecyclerView) $(R.id.rv_home_group_device);
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
        this.homeGroupDeviceAdapter = new HomeGroupDeviceAdapter(this.mActivity);
        this.mRvDeviceListView.setAdapter(this.homeGroupDeviceAdapter);
    }

    protected void initData() {
        String groupName = getIntent().getStringExtra("intent_group_name");
        if (!TextUtils.isEmpty(groupName)) {
            this.mEtGroupName.setText(groupName);
            this.mEtGroupName.setSelection(groupName.length());
        }
        this.mGroupId = getIntent().getStringExtra("intent_group_id");
        this.mHomeId = getIntent().getStringExtra("intent_home_id");
        this.homeGroupDeviceAdapter.setGroupId(this.mGroupId);
        loadDeviceListData();
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
        this.mTvSelectAll.setOnClickListener(this);
        this.homeGroupDeviceAdapter.setOnAddGroupListener(this);
    }

    protected void addGroup(String groupName, String deviceIds) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put("id", this.mGroupId);
        paramsNoSign.put(RNMessageModule.NAME, groupName);
        params.put("deviceIds", deviceIds);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("group/update");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeGroupDeviceActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeGroupDeviceActivity.this.mActivity, "修改成功");
                HomeGroupDeviceActivity.this.finish();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeGroupDeviceActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void loadDeviceListData() {
        if (TextUtils.isEmpty(this.mHomeId)) {
            ToastUtils.showShort(this.mActivity, "数据加载异常,请稍后重试");
            return;
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("homeId", this.mHomeId);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("home/device/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeGroupDeviceActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeDeviceListModel homeDeviceListModel = (HomeDeviceListModel) JSON.parseObject(var1.getData(), HomeDeviceListModel.class);
                HomeGroupDeviceActivity.this.deviceListBeans = homeDeviceListModel.getDeviceList();
                HomeGroupDeviceActivity.this.homeGroupDeviceAdapter.setNewData(HomeGroupDeviceActivity.this.deviceListBeans);
                HomeGroupDeviceActivity.this.homeGroupDeviceAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeGroupDeviceActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void clickFinish() {
        String homeName = this.mEtGroupName.getText().toString().trim();
        if (TextUtils.isEmpty(homeName)) {
            ToastUtils.showShort(this.mActivity, "分组名称不能为空");
            return;
        }
        String deviceIds = this.homeGroupDeviceAdapter.getSelectItemId();
        if (TextUtils.isEmpty(deviceIds)) {
            deviceIds = Constants.MAIN_VERSION_TAG;
        }
        addGroup(homeName, deviceIds);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                clickFinish();
                XcLogger.i("tag", this.homeGroupDeviceAdapter.getSelectItemId());
                break;
            case R.id.tv_home_group_device_select_all /* 2131296993 */:
                if (this.isSelectAll) {
                    this.homeGroupDeviceAdapter.unSelectAll();
                } else {
                    this.homeGroupDeviceAdapter.selectAll();
                }
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.detail.adater.HomeAddGroupAdapter.AddGroupListener
    public void isSelectAll(Boolean i) {
        if (i.booleanValue()) {
            this.mTvSelectAll.setText("取消");
            this.isSelectAll = true;
        } else {
            this.mTvSelectAll.setText("全选");
            this.isSelectAll = false;
        }
    }
}
