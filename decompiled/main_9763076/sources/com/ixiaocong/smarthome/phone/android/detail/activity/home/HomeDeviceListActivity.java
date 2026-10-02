package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.HomeDeviceListAdapter;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.HomeDeviceListModel;
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
public class HomeDeviceListActivity extends XcBaseActivity implements View.OnClickListener {
    private List<HomeDeviceListModel.DeviceListBean> deviceListBeans;
    private HomeDeviceListAdapter homeDeviceListAdapter;
    private String mHomeId;
    private RecyclerView mHomeListView;
    private ImageView mIvBack;

    protected int getLayoutId() {
        return R.layout.activity_home_device_list;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mHomeListView = (RecyclerView) $(R.id.rv_home_device_list);
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
        this.mHomeListView.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.homeDeviceListAdapter = new HomeDeviceListAdapter(this.mActivity);
        this.mHomeListView.setAdapter(this.homeDeviceListAdapter);
    }

    protected void initData() {
        this.mHomeId = getIntent().getStringExtra("intent_home_id");
        loadDeviceListData();
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mHomeListView.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeDeviceListActivity.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
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
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeDeviceListActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeDeviceListModel homeDeviceListModel = (HomeDeviceListModel) JSON.parseObject(var1.getData(), HomeDeviceListModel.class);
                HomeDeviceListActivity.this.deviceListBeans = homeDeviceListModel.getDeviceList();
                HomeDeviceListActivity.this.homeDeviceListAdapter.setNewData(HomeDeviceListActivity.this.deviceListBeans);
                HomeDeviceListActivity.this.homeDeviceListAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeDeviceListActivity.this.mActivity, var1.getErrorMessage());
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
