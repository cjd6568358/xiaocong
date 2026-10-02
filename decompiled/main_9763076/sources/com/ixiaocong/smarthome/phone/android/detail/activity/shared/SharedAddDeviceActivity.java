package com.ixiaocong.smarthome.phone.android.detail.activity.shared;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.SharedAddDeviceAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.ShareableDevicesModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SharedAddDeviceActivity extends XcBaseActivity implements View.OnClickListener {
    private boolean isAllChecked = false;
    private SharedAddDeviceAdapter mAppendAdapter;
    private Button mBtnNext;
    private StringBuffer mBuffer;
    private CheckBox mCBox;
    private List<String> mIdList;
    private ImageView mIvBack;
    private List<ShareableDevicesModel.DeviceListBean> mListData;
    private TextView mRightTitle;
    private RelativeLayout mRlRight;
    private RecyclerView mRvAppendShared;

    protected int getLayoutId() {
        return R.layout.activity_shared_append_device;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mRightTitle = (TextView) $(R.id.right_titlebar_text);
        this.mBtnNext = (Button) $(R.id.btn_append_shared_device_next);
        this.mRvAppendShared = (RecyclerView) $(R.id.rv_shared_device_append);
        this.mRlRight = (RelativeLayout) $(R.id.rl_right_titlebar_image);
        this.mRlRight.setVisibility(8);
        this.mRvAppendShared.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ActivityManagerUtil.getScreenManager().pushActivity(this);
    }

    protected void initData() {
        loadSharedDevice();
        this.mBuffer = new StringBuffer();
        this.mIdList = new ArrayList();
    }

    private void loadSharedDevice() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("share/devices/shareable");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedAddDeviceActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ShareableDevicesModel sharedDevicesModel = (ShareableDevicesModel) JSON.parseObject(var1.getData(), ShareableDevicesModel.class);
                if (sharedDevicesModel.getDeviceList().size() <= 0) {
                    SharedAddDeviceActivity.this.mRightTitle.setVisibility(4);
                    SharedAddDeviceActivity.this.mBtnNext.setVisibility(4);
                    ToastUtils.showShort(SharedAddDeviceActivity.this.mActivity, "您目前没有可分享的设备");
                } else {
                    SharedAddDeviceActivity.this.mListData = sharedDevicesModel.getDeviceList();
                    SharedAddDeviceActivity.this.mAppendAdapter.setNewData(SharedAddDeviceActivity.this.mListData);
                    SharedAddDeviceActivity.this.mAppendAdapter.notifyDataSetChanged();
                    SharedAddDeviceActivity.this.mBtnNext.setVisibility(0);
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedAddDeviceActivity.this.mActivity, var1.getErrorMessage());
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
        this.mAppendAdapter = new SharedAddDeviceAdapter(this.mActivity);
        this.mRvAppendShared.setAdapter(this.mAppendAdapter);
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
    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mRightTitle.setOnClickListener(this);
        this.mBtnNext.setOnClickListener(this);
        SparseBooleanArray mCheckStates = new SparseBooleanArray();
        this.mRvAppendShared.addOnItemTouchListener(new AnonymousClass2(mCheckStates));
    }

    /* JADX INFO: renamed from: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedAddDeviceActivity$2, reason: invalid class name */
    class AnonymousClass2 extends OnItemChildClickListener {
        final /* synthetic */ SparseBooleanArray val$mCheckStates;

        AnonymousClass2(SparseBooleanArray sparseBooleanArray) {
            this.val$mCheckStates = sparseBooleanArray;
        }

        public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
            switch (view.getId()) {
                case R.id.cb_shared_append_device_status /* 2131296355 */:
                    SharedAddDeviceActivity.this.mCBox = (CheckBox) view.findViewById(R.id.cb_shared_append_device_status);
                    SharedAddDeviceActivity.this.mCBox.setTag(Integer.valueOf(position));
                    SharedAddDeviceActivity.this.mCBox.setOnCheckedChangeListener(SharedAddDeviceActivity$2$$Lambda$1.lambdaFactory$(this, position, this.val$mCheckStates));
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSimpleItemChildClick$0(int position, SparseBooleanArray mCheckStates, CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                SharedAddDeviceActivity.this.mIdList.add(((ShareableDevicesModel.DeviceListBean) SharedAddDeviceActivity.this.mListData.get(position)).getDeviceId());
                ((ShareableDevicesModel.DeviceListBean) SharedAddDeviceActivity.this.mListData.get(position)).setChecked(true);
                if (SharedAddDeviceActivity.this.mIdList.size() == SharedAddDeviceActivity.this.mListData.size()) {
                    SharedAddDeviceActivity.this.mRightTitle.setText("取消");
                    SharedAddDeviceActivity.this.isAllChecked = true;
                }
                mCheckStates.put(((Integer) buttonView.getTag()).intValue(), true);
            } else {
                SharedAddDeviceActivity.this.mIdList.remove(((ShareableDevicesModel.DeviceListBean) SharedAddDeviceActivity.this.mListData.get(position)).getDeviceId());
                ((ShareableDevicesModel.DeviceListBean) SharedAddDeviceActivity.this.mListData.get(position)).setChecked(false);
                SharedAddDeviceActivity.this.mRightTitle.setText("全选");
                SharedAddDeviceActivity.this.isAllChecked = false;
                mCheckStates.delete(((Integer) buttonView.getTag()).intValue());
            }
            XcLogger.e("SharedAddDeviceActivity", "mIdList.size()--" + SharedAddDeviceActivity.this.mIdList.size());
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_append_shared_device_next /* 2131296321 */:
                XcLogger.e("SharedAddDeviceActivity", "next mIdList.size()--" + this.mIdList.size());
                if (this.mIdList != null) {
                    if (this.mIdList.size() == 0) {
                        ToastUtils.showShort(this.mActivity, "请先选择要分享的设备");
                    } else if (this.mIdList.size() == 1) {
                        Intent intent = new Intent(this.mActivity, (Class<?>) SharedSelectUserActivity.class);
                        intent.putExtra(Constants.FLAG_DEVICE_ID, this.mIdList.get(0));
                        startActivity(intent);
                    } else {
                        this.mBuffer.setLength(0);
                        for (int i = 0; i < this.mIdList.size(); i++) {
                            if (i != this.mIdList.size() - 1) {
                                this.mBuffer.append(this.mIdList.get(i) + ",");
                            } else {
                                this.mBuffer.append(this.mIdList.get(i));
                            }
                        }
                        if (!TextUtils.isEmpty(this.mBuffer.toString())) {
                            Intent intent2 = new Intent(this.mActivity, (Class<?>) SharedSelectUserActivity.class);
                            intent2.putExtra(Constants.FLAG_DEVICE_ID, this.mBuffer.toString());
                            startActivity(intent2);
                        }
                        XcLogger.e("SharedAddDeviceActivity", this.mBuffer.toString());
                    }
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                if (!this.isAllChecked) {
                    this.mRightTitle.setText("取消");
                    this.mAppendAdapter.notifyDataSetChanged();
                    if (this.mListData != null && this.mListData.size() > 0) {
                        this.mIdList.clear();
                        for (int i2 = 0; i2 < this.mListData.size(); i2++) {
                            this.mIdList.add(this.mListData.get(i2).getDeviceId());
                            this.mListData.get(i2).setChecked(true);
                        }
                    } else {
                        ToastUtils.showShort(this.mActivity, "您当前没有可共享的设备,请先前去添加");
                    }
                    this.isAllChecked = true;
                } else {
                    for (int i3 = 0; i3 < this.mListData.size(); i3++) {
                        this.mListData.get(i3).setChecked(false);
                    }
                    this.mAppendAdapter.notifyDataSetChanged();
                    this.mRightTitle.setText("全选");
                    this.isAllChecked = false;
                    this.mIdList.clear();
                }
                break;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getScreenManager().popActivity(this);
    }
}
