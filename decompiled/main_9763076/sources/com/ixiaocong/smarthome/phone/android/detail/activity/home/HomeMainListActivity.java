package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.HomeEidtSelectPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.HomeMainListAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.HomeEidtSelectPopCallback;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.HomeMainListDetailModel;
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
public class HomeMainListActivity extends XcBaseActivity implements View.OnClickListener, HomeEidtSelectPopCallback {
    private List<HomeMainListDetailModel.HomeListBean> homeListBeans;
    private HomeMainListAdapter homeMainListAdapter;
    private boolean isEidt = false;
    private RecyclerView mHomeListView;
    private ImageView mIvBack;
    private TextView mTvSetting;

    protected int getLayoutId() {
        return R.layout.activity_home_main_list;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mHomeListView = (RecyclerView) $(R.id.rv_home_main_list);
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
        this.homeMainListAdapter = new HomeMainListAdapter();
        this.mHomeListView.setAdapter(this.homeMainListAdapter);
    }

    protected void initData() {
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
        this.mHomeListView.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeMainListActivity.1
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                HomeMainListActivity.this.deleteHome(((HomeMainListDetailModel.HomeListBean) HomeMainListActivity.this.homeListBeans.get(position)).getId(), position);
            }
        });
        this.mHomeListView.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeMainListActivity.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                String homeId = ((HomeMainListDetailModel.HomeListBean) HomeMainListActivity.this.homeListBeans.get(position)).getId();
                if (TextUtils.isEmpty(homeId)) {
                    ToastUtils.showShort(HomeMainListActivity.this.mActivity, "家庭信息错误,请重试");
                    return;
                }
                Intent intent = new Intent(HomeMainListActivity.this.mActivity, (Class<?>) HomeDetailActivity.class);
                intent.putExtra("intent_home_id", homeId);
                intent.putExtra("intent_home_name", ((HomeMainListDetailModel.HomeListBean) HomeMainListActivity.this.homeListBeans.get(position)).getName());
                HomeMainListActivity.this.startActivity(intent);
            }
        });
    }

    protected void loadHomeListData() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("home/listDetail");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeMainListActivity.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeMainListDetailModel homeMainListModel = (HomeMainListDetailModel) JSON.parseObject(var1.getData(), HomeMainListDetailModel.class);
                HomeMainListActivity.this.homeListBeans = homeMainListModel.getHomeList();
                HomeMainListActivity.this.homeMainListAdapter.setNewData(HomeMainListActivity.this.homeListBeans);
                HomeMainListActivity.this.homeMainListAdapter.setEdit(false);
                HomeMainListActivity.this.homeMainListAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeMainListActivity.this.mActivity, var1.getErrorMessage());
            }
        });
    }

    protected void deleteHome(String homeId, final int position) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("homeId", homeId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("home/delete");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeMainListActivity.4
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeMainListActivity.this.homeListBeans.remove(position);
                HomeMainListActivity.this.homeMainListAdapter.notifyDataSetChanged();
                ToastUtils.showShort(HomeMainListActivity.this.mActivity, "删除成功");
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeMainListActivity.this.mActivity, var1.getErrorMessage());
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
                    this.homeMainListAdapter.setEdit(false);
                    this.isEidt = false;
                } else {
                    HomeEidtSelectPop.getInstance().showSelectPop(this.mActivity, this.mTvSetting, "新增家庭", "删除家庭", this);
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
        Intent intent = new Intent(this.mActivity, (Class<?>) HomeAddHomeActivity.class);
        startActivity(intent);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HomeEidtSelectPopCallback
    public void OnSecondClick() {
        this.mTvSetting.setText("完成");
        this.homeMainListAdapter.setEdit(true);
        this.isEidt = true;
    }
}
