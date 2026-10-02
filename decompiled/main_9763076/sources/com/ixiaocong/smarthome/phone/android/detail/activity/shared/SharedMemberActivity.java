package com.ixiaocong.smarthome.phone.android.detail.activity.shared;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.SharedMemberAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.SharedMemberModel;
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
public class SharedMemberActivity extends XcBaseActivity implements View.OnClickListener {
    private boolean isClick = false;
    private SharedMemberAdapter mAdapter;
    private String mDeviceId;
    private String mDeviceName;
    private ImageView mIvBack;
    private List<SharedMemberModel.MemberModel> mMemberList;
    private RecyclerView mRvMember;
    private TextView mTvSetting;

    protected int getLayoutId() {
        return R.layout.activity_shared_member;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mRvMember = (RecyclerView) $(R.id.rv_shared_member);
        this.mRvMember.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mDeviceId = getIntent().getStringExtra(Constants.FLAG_DEVICE_ID);
        this.mDeviceName = getIntent().getStringExtra("deviceName");
        loadMemberData(this.mDeviceId);
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
        this.mAdapter = new SharedMemberAdapter(this.mActivity);
        this.mRvMember.setAdapter(this.mAdapter);
    }

    private void loadMemberData(String deviceId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("share/device/users");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedMemberActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SharedMemberModel sharedMemberModel = (SharedMemberModel) JSON.parseObject(var1.getData(), SharedMemberModel.class);
                SharedMemberActivity.this.mMemberList = sharedMemberModel.getList();
                SharedMemberActivity.this.mAdapter.setNewData(SharedMemberActivity.this.mMemberList);
                SharedMemberActivity.this.mAdapter.setShow(false);
                SharedMemberActivity.this.mAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedMemberActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
        this.mRvMember.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedMemberActivity.2
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_shared_user_edit_del /* 2131296522 */:
                        SharedMemberActivity.this.cancelSharedUser(((SharedMemberModel.MemberModel) SharedMemberActivity.this.mMemberList.get(position)).getUid(), position);
                        break;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelSharedUser(String uid, final int position) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("uid", String.valueOf(uid));
        params.put(Constants.FLAG_DEVICE_ID, this.mDeviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("share/cancel");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.shared.SharedMemberActivity.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SharedMemberActivity.this.mMemberList.remove(position);
                SharedMemberActivity.this.mAdapter.notifyDataSetChanged();
                ToastUtils.showShort(SharedMemberActivity.this.mActivity, "该设备已取消分享给此用户");
                if (SharedMemberActivity.this.mMemberList.size() == 0) {
                    SharedMemberActivity.this.finish();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SharedMemberActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                if (this.isClick) {
                    this.mTvSetting.setText("编辑");
                    this.mAdapter.setShow(false);
                    this.mAdapter.notifyDataSetChanged();
                    this.isClick = false;
                } else {
                    this.mTvSetting.setText("完成");
                    this.mAdapter.setShow(true);
                    this.mAdapter.notifyDataSetChanged();
                    this.isClick = true;
                }
                break;
        }
    }
}
