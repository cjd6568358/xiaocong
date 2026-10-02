package com.ixiaocong.smarthome.phone.android.detail.fragment.shared;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.ShareSelectHomeListPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.SharedToMeDeviceAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.ShareSelectHomeListCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
import com.xiaocong.smarthome.httplib.model.HomeListModel;
import com.xiaocong.smarthome.httplib.model.SharedDeviceToMeModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedToMeFragment extends XcBaseFragment implements ShareSelectHomeListCallback {
    private boolean isFirstLoad = false;
    private String mDeviceId;
    private List<HomeListModel.HomeListBean> mFamily;
    private List<SharedDeviceToMeModel.DeviceListBean> mListData;
    private RecyclerView mRvSharedDev;
    private SharedToMeDeviceAdapter mSharedHomeAdapter;
    private TextView mTvHint;

    protected int getLayoutId() {
        return R.layout.fragment_shared_to_me;
    }

    protected void initView() {
        this.mTvHint = (TextView) $(R.id.tv_shared_to_me_hint);
        this.mRvSharedDev = (RecyclerView) $(R.id.rv_shared_device_home);
        this.mRvSharedDev.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        loadHomeList(false, true);
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
        this.mSharedHomeAdapter = new SharedToMeDeviceAdapter(this.mActivity);
        this.mSharedHomeAdapter.setNewData(this.mListData);
        this.mRvSharedDev.setAdapter(this.mSharedHomeAdapter);
    }

    public void onResume() {
        super.onResume();
        loadDevices();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadDevices() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("share/devices/sharedToMine");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.shared.SharedToMeFragment.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SharedDeviceToMeModel sharedDevicesModel = (SharedDeviceToMeModel) JSON.parseObject(var1.getData(), SharedDeviceToMeModel.class);
                SharedToMeFragment.this.mListData = sharedDevicesModel.getDeviceList();
                if (SharedToMeFragment.this.mListData.size() == 0) {
                    SharedToMeFragment.this.mTvHint.setVisibility(0);
                } else {
                    SharedToMeFragment.this.mTvHint.setVisibility(8);
                }
                SharedToMeFragment.this.mSharedHomeAdapter.setNewData(SharedToMeFragment.this.mListData);
                SharedToMeFragment.this.mSharedHomeAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedToMeFragment.this.mActivity, var1.getErrorMessage());
            }
        });
        this.isFirstLoad = true;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isFirstLoad) {
            loadDevices();
        }
    }

    public void addListener() {
        super.addListener();
        this.mRvSharedDev.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.shared.SharedToMeFragment.2
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                SharedToMeFragment.this.mDeviceId = ((SharedDeviceToMeModel.DeviceListBean) SharedToMeFragment.this.mListData.get(position)).getDeviceId();
                if (SharedToMeFragment.this.mFamily != null && SharedToMeFragment.this.mFamily.size() != 0) {
                    ShareSelectHomeListPop.getInstance().showSelectPop(SharedToMeFragment.this.mActivity, SharedToMeFragment.this.mRvSharedDev, SharedToMeFragment.this.mFamily, SharedToMeFragment.this);
                } else {
                    SharedToMeFragment.this.loadHomeList(true, false);
                }
            }
        });
    }

    private void loadAgreeShare(String deviceId, String homeId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        paramsNoSign.put("homeId", homeId);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("share/device/accept");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.shared.SharedToMeFragment.3
            public void onComplete(XCResponseBean var1) {
                SharedToMeFragment.this.loadDevices();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedToMeFragment.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void loadHomeList(boolean isShowDialog, final boolean isFirstLoad) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("home/list");
        if (isShowDialog) {
            HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        }
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.shared.SharedToMeFragment.4
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeListModel homeListModel = (HomeListModel) JSON.parseObject(var1.getData(), HomeListModel.class);
                SharedToMeFragment.this.mFamily = homeListModel.getHomeList();
                if (!isFirstLoad) {
                    ShareSelectHomeListPop.getInstance().showSelectPop(SharedToMeFragment.this.mActivity, SharedToMeFragment.this.mRvSharedDev, SharedToMeFragment.this.mFamily, SharedToMeFragment.this);
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedToMeFragment.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.ShareSelectHomeListCallback
    public void selectHome(int position) {
        loadAgreeShare(this.mDeviceId, this.mFamily.get(position).getId());
    }
}
