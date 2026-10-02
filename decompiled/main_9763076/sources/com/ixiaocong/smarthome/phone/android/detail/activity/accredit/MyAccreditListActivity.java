package com.ixiaocong.smarthome.phone.android.detail.activity.accredit;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.MyAccreditListAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.MyAccreditModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MyAccreditListActivity extends XcBaseActivity implements View.OnClickListener {
    private MyAccreditListAdapter mAccreditAdapter;
    private RecyclerView mAccreditRv;
    private List<MyAccreditModel.AuthoredAppListBean> mListData;
    private TextView mTvHint;

    protected int getLayoutId() {
        return R.layout.activity_my_accredit_list;
    }

    protected void initView() {
        this.mTvHint = (TextView) $(R.id.tv_accredit_list_hint);
        this.mAccreditRv = (RecyclerView) $(R.id.rv_my_accredit);
        this.mAccreditRv.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    public void addListener() {
        super.addListener();
        int[] resIds = {R.id.left_titlebar_image};
        for (int id : resIds) {
            findViewById(id).setOnClickListener(this);
        }
        this.mAccreditRv.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.accredit.MyAccreditListActivity.1
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyAccreditListActivity.this.mActivity, (Class<?>) AccreditDetailActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, ((MyAccreditModel.AuthoredAppListBean) MyAccreditListActivity.this.mListData.get(position)).getAppId());
                intent.putExtra("deviceName", ((MyAccreditModel.AuthoredAppListBean) MyAccreditListActivity.this.mListData.get(position)).getName());
                intent.putExtra("deviceImg", ((MyAccreditModel.AuthoredAppListBean) MyAccreditListActivity.this.mListData.get(position)).getLogo());
                intent.putStringArrayListExtra("devicePermission", (ArrayList) ((MyAccreditModel.AuthoredAppListBean) MyAccreditListActivity.this.mListData.get(position)).getPermissionList());
                MyAccreditListActivity.this.startActivity(intent);
            }
        });
    }

    protected void initData() {
        initAdapter();
    }

    protected void onResume() {
        super.onResume();
        loadMyAccreditList();
    }

    private void loadMyAccreditList() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("authorize/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.accredit.MyAccreditListActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                MyAccreditModel myAccreditModel = (MyAccreditModel) JSON.parseObject(var1.getData(), MyAccreditModel.class);
                MyAccreditListActivity.this.mListData = myAccreditModel.getAuthoredAppList();
                if (MyAccreditListActivity.this.mListData == null || MyAccreditListActivity.this.mListData.size() == 0) {
                    MyAccreditListActivity.this.mTvHint.setVisibility(0);
                    return;
                }
                MyAccreditListActivity.this.mTvHint.setVisibility(8);
                MyAccreditListActivity.this.mAccreditAdapter.setNewData(MyAccreditListActivity.this.mListData);
                MyAccreditListActivity.this.mAccreditAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(MyAccreditListActivity.this.mActivity, var1.getErrorMessage());
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
        this.mAccreditAdapter = new MyAccreditListAdapter();
        this.mAccreditRv.setAdapter(this.mAccreditAdapter);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
        }
    }
}
