package com.ixiaocong.smarthome.phone.android.detail.activity.msg;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.SystemMsgSettingDeviceAdapter;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.SystemMsgSettingModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.switchbutton.SwitchButton;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SystemMsgSettingActivity extends XcBaseActivity {
    private ImageView mIvBack;
    private SystemMsgSettingDeviceAdapter mMsgAdapter;
    private SwitchButton mSbtnDevice;
    private RecyclerView mSystemMsgRecycler;
    private SystemMsgSettingModel systemMsgSettingModel;

    protected int getLayoutId() {
        return R.layout.activity_system_msg_setting;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mSbtnDevice = $(R.id.btn_system_msg_setting_device);
        this.mSystemMsgRecycler = (RecyclerView) $(R.id.rv_system_msg_setting_device);
        this.mSystemMsgRecycler.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        loadSetting();
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(SystemMsgSettingActivity$$Lambda$1.lambdaFactory$(this));
        this.mSystemMsgRecycler.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgSettingActivity.1
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ll_right_tab_home_sbtn_layout /* 2131296601 */:
                        if (((SystemMsgSettingModel.DevicesBean) SystemMsgSettingActivity.this.systemMsgSettingModel.getDevices().get(position)).getNotification() == 1) {
                            SystemMsgSettingActivity.this.updateDeviceSetting(((SystemMsgSettingModel.DevicesBean) SystemMsgSettingActivity.this.systemMsgSettingModel.getDevices().get(position)).getUserDeviceId() + Constants.MAIN_VERSION_TAG, PushConstants.PUSH_TYPE_NOTIFY);
                        } else {
                            SystemMsgSettingActivity.this.updateDeviceSetting(((SystemMsgSettingModel.DevicesBean) SystemMsgSettingActivity.this.systemMsgSettingModel.getDevices().get(position)).getUserDeviceId() + Constants.MAIN_VERSION_TAG, "1");
                        }
                        break;
                }
            }
        });
        this.mSbtnDevice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgSettingActivity.2
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SystemMsgSettingActivity.this.updateUserSetting("1");
                } else {
                    SystemMsgSettingActivity.this.updateUserSetting(PushConstants.PUSH_TYPE_NOTIFY);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadSetting() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("user/message/config/find");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgSettingActivity.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SystemMsgSettingActivity.this.systemMsgSettingModel = (SystemMsgSettingModel) JSON.parseObject(var1.getData(), SystemMsgSettingModel.class);
                int notification = SystemMsgSettingActivity.this.systemMsgSettingModel.getNotification();
                if (notification == 1) {
                    if (!SystemMsgSettingActivity.this.mSbtnDevice.isChecked()) {
                        SystemMsgSettingActivity.this.mSbtnDevice.setCheckedNoEvent(true);
                    }
                    SystemMsgSettingActivity.this.mSystemMsgRecycler.setVisibility(0);
                } else {
                    if (SystemMsgSettingActivity.this.mSbtnDevice.isChecked()) {
                        SystemMsgSettingActivity.this.mSbtnDevice.setCheckedNoEvent(false);
                    }
                    SystemMsgSettingActivity.this.mSystemMsgRecycler.setVisibility(8);
                }
                SystemMsgSettingActivity.this.mMsgAdapter.setNewData(SystemMsgSettingActivity.this.systemMsgSettingModel.getDevices());
                SystemMsgSettingActivity.this.mMsgAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SystemMsgSettingActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDeviceSetting(String userDeviceId, String notificationStatus) {
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("userDeviceId", userDeviceId);
        params.put("notificationStatus", notificationStatus);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("user/message/device/config/update");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgSettingActivity.4
            public void onComplete(XCResponseBean var1) {
                SystemMsgSettingActivity.this.loadSetting();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SystemMsgSettingActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserSetting(String notificationStatus) {
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("notificationStatus", notificationStatus);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("user/message/config/update");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgSettingActivity.5
            public void onComplete(XCResponseBean var1) {
                SystemMsgSettingActivity.this.loadSetting();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SystemMsgSettingActivity.this.mActivity, var1.getErrorMessage());
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
        this.mMsgAdapter = new SystemMsgSettingDeviceAdapter();
        this.mSystemMsgRecycler.setAdapter(this.mMsgAdapter);
    }

    protected void onResume() {
        super.onResume();
    }
}
