package com.ixiaocong.smarthome.phone.android.detail.activity.device.list;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.add.DeviceAddCategoryActivity;
import com.ixiaocong.smarthome.phone.android.detail.adater.TabHomeDeviceRecyclerAdapter;
import com.ixiaocong.smarthome.phone.android.detail.fragment.home.TabHomeListener;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.config.AppSpConstans;
import com.xiaocong.smarthome.httplib.model.DeviceListModel;
import com.xiaocong.smarthome.httplib.model.MainDevListModel;
import com.xiaocong.smarthome.httplib.utils.NetworkUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceHomeListActivity extends XcBaseActivity implements View.OnClickListener {
    private String mClientID;
    private TabHomeDeviceRecyclerAdapter mHomeAdapter;
    private RecyclerView mHomeDevRv;
    private ImageView mIvBack;
    private ImageView mIvSetting;
    private TextView mTvAddDev;

    protected int getLayoutId() {
        return R.layout.activity_device_home;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvSetting = (ImageView) $(R.id.right_titlebar_image);
        this.mHomeDevRv = (RecyclerView) $(R.id.rv_device_home_acti);
        this.mTvAddDev = (TextView) $(R.id.tv_device_home_add_dev);
        this.mHomeDevRv.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mClientID = AppSpConstans.getInstance().getClientId(this.mActivity);
        loadDevList();
    }

    private void loadDevList() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("device/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.list.DeviceHomeListActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                MainDevListModel mainDevListModel = (MainDevListModel) JSON.parseObject(var1.getData(), MainDevListModel.class);
                DeviceHomeListActivity.this.mHomeAdapter.setNewData(mainDevListModel.devices);
                DeviceHomeListActivity.this.mHomeAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceHomeListActivity.this.mActivity, var1.getErrorMessage());
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
        this.mHomeAdapter = new TabHomeDeviceRecyclerAdapter(this.mActivity);
        this.mHomeDevRv.setAdapter(this.mHomeAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mIvSetting.setOnClickListener(this);
        this.mTvAddDev.setOnClickListener(this);
        this.mHomeDevRv.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.list.DeviceHomeListActivity.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
            }
        });
        this.mHomeDevRv.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.device.list.DeviceHomeListActivity.3
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                if (((DeviceListModel) DeviceHomeListActivity.this.mHomeAdapter.getData().get(position)).getControlParameter() != null) {
                    if (!NetworkUtils.isNetworkAvailable(DeviceHomeListActivity.this.mActivity)) {
                        ToastUtils.showShort(DeviceHomeListActivity.this.mActivity, "请检查当前网络连接");
                    } else if (XCDeviceController.getInstance().XCDeviceControllerStatus()) {
                        TabHomeListener.getInstance().deviceItemChildClick(DeviceHomeListActivity.this.mActivity, view, DeviceHomeListActivity.this.mHomeAdapter.getData(), DeviceHomeListActivity.this.mClientID, position);
                    } else {
                        ToastUtils.showShort(DeviceHomeListActivity.this.mActivity, "正在连接,请稍后重试");
                    }
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.tv_device_home_add_dev /* 2131296972 */:
                startActivity(new Intent(this.mActivity, (Class<?>) DeviceAddCategoryActivity.class));
                break;
        }
    }
}
