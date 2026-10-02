package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.HomeEidtSelectPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.HomeGroupListAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.HomeEidtSelectPopCallback;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.HomeGroupListModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeGroupListActivity extends XcBaseActivity implements View.OnClickListener, HomeEidtSelectPopCallback {
    private List<HomeGroupListModel.GroupListBean> groupListBeans;
    private HomeGroupListAdapter homeGroupListAdapter;
    private boolean isEidt = false;
    private RecyclerView mDeviceListView;
    private String mHomeId;
    private ImageView mIvBack;
    private LinearLayout mLlHintText;
    private TextView mTvSetting;

    protected int getLayoutId() {
        return R.layout.activity_home_group_list;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mDeviceListView = (RecyclerView) $(R.id.rv_home_group_list);
        this.mLlHintText = (LinearLayout) $(R.id.ll_home_group_list_hint);
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
        this.mDeviceListView.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.homeGroupListAdapter = new HomeGroupListAdapter();
        this.mDeviceListView.setAdapter(this.homeGroupListAdapter);
    }

    protected void initData() {
        this.mHomeId = getIntent().getStringExtra("intent_home_id");
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
        this.mDeviceListView.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeGroupListActivity.1
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                HomeGroupListActivity.this.deleteGroup(((HomeGroupListModel.GroupListBean) HomeGroupListActivity.this.groupListBeans.get(position)).getId(), position);
            }
        });
        this.mDeviceListView.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeGroupListActivity.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                String groupId = ((HomeGroupListModel.GroupListBean) HomeGroupListActivity.this.groupListBeans.get(position)).getId();
                if (TextUtils.isEmpty(groupId)) {
                    ToastUtils.showShort(HomeGroupListActivity.this.mActivity, "分组信息错误,请重试");
                    return;
                }
                Intent intent = new Intent(HomeGroupListActivity.this.mActivity, (Class<?>) HomeGroupDeviceActivity.class);
                intent.putExtra("intent_group_id", groupId);
                intent.putExtra("intent_home_id", HomeGroupListActivity.this.mHomeId);
                intent.putExtra("intent_group_name", ((HomeGroupListModel.GroupListBean) HomeGroupListActivity.this.groupListBeans.get(position)).getName());
                HomeGroupListActivity.this.startActivity(intent);
            }
        });
    }

    protected void loadHomeListData() {
        if (TextUtils.isEmpty(this.mHomeId)) {
            ToastUtils.showShort(this.mActivity, "分组数据加载失败,请稍后重试");
            return;
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("homeId", this.mHomeId);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("group/list");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeGroupListActivity.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeGroupListModel homeGroupListModel = (HomeGroupListModel) JSON.parseObject(var1.getData(), HomeGroupListModel.class);
                HomeGroupListActivity.this.groupListBeans = homeGroupListModel.getGroupList();
                HomeGroupListActivity.this.homeGroupListAdapter.setNewData(HomeGroupListActivity.this.groupListBeans);
                HomeGroupListActivity.this.homeGroupListAdapter.setEdit(false);
                HomeGroupListActivity.this.homeGroupListAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeGroupListActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void deleteGroup(String groupId, final int position) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("ids", groupId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("group/delete");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeGroupListActivity.4
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeGroupListActivity.this.groupListBeans.remove(position);
                HomeGroupListActivity.this.homeGroupListAdapter.notifyDataSetChanged();
                ToastUtils.showShort(HomeGroupListActivity.this.mActivity, "删除成功");
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeGroupListActivity.this.mActivity, var1.getErrorMessage());
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
                if (this.isEidt) {
                    this.mTvSetting.setText("编辑");
                    this.homeGroupListAdapter.setEdit(false);
                    this.mLlHintText.setVisibility(8);
                    this.isEidt = false;
                } else {
                    HomeEidtSelectPop.getInstance().showSelectPop(this.mActivity, this.mTvSetting, "新增分组", "删除分组", this);
                }
                break;
        }
    }

    protected void onResume() {
        super.onResume();
        loadHomeListData();
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HomeEidtSelectPopCallback
    public void OnFirstClick() {
        if (TextUtils.isEmpty(this.mHomeId)) {
            ToastUtils.showShort(this.mActivity, "加载失败,请重试!");
            return;
        }
        Intent intent = new Intent(this.mActivity, (Class<?>) HomeAddGroupActivity.class);
        intent.putExtra("intent_home_id", this.mHomeId);
        startActivity(intent);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HomeEidtSelectPopCallback
    public void OnSecondClick() {
        this.mTvSetting.setText("完成");
        this.homeGroupListAdapter.setEdit(true);
        this.mLlHintText.setVisibility(0);
        this.isEidt = true;
    }
}
