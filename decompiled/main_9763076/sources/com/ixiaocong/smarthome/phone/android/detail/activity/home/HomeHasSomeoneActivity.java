package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.HomeHasSomeoneAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.HomeHasSomeoneModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HomeHasSomeoneActivity extends XcBaseActivity {
    private HomeHasSomeoneAdapter mAdapter;
    private String mHomeId;
    private ImageView mIvBack;
    private int mPageSize = 50;
    private SmartRefreshLayout mRefresh;
    private RecyclerView mRvSomeone;
    private HomeHasSomeoneModel mSomeoneModel;

    protected int getLayoutId() {
        return R.layout.activity_has_someone;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mRefresh = (SmartRefreshLayout) $(R.id.srl_home_has_someone_layout);
        this.mRvSomeone = (RecyclerView) $(R.id.rv_home_has_someone);
        this.mRvSomeone.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mHomeId = getIntent().getStringExtra("intent_home_id");
        loadData(true);
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
        this.mAdapter = new HomeHasSomeoneAdapter();
        this.mRvSomeone.setAdapter(this.mAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(HomeHasSomeoneActivity$$Lambda$1.lambdaFactory$(this));
        this.mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeHasSomeoneActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (HomeHasSomeoneActivity.this.mSomeoneModel != null && !TextUtils.isEmpty(HomeHasSomeoneActivity.this.mSomeoneModel.getQueryId())) {
                    HomeHasSomeoneActivity.this.loadData(false);
                }
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                HomeHasSomeoneActivity.this.loadData(true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData(final boolean isRefresh) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> noSign = new HashMap<>();
        params.put("homeId", this.mHomeId);
        noSign.put("pageSize", this.mPageSize + Constants.MAIN_VERSION_TAG);
        Object queryId = (isRefresh || this.mSomeoneModel == null) ? Constants.MAIN_VERSION_TAG : this.mSomeoneModel.getQueryId();
        noSign.put("queryId", queryId);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(noSign);
        httpSetting.setPath("home/check/nobody");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.home.HomeHasSomeoneActivity.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HomeHasSomeoneActivity.this.mSomeoneModel = (HomeHasSomeoneModel) JSON.parseObject(var1.getData(), HomeHasSomeoneModel.class);
                if (HomeHasSomeoneActivity.this.mSomeoneModel == null || HomeHasSomeoneActivity.this.mSomeoneModel.getList() == null || HomeHasSomeoneActivity.this.mSomeoneModel.getList().size() == 0) {
                    ToastUtils.showShort(HomeHasSomeoneActivity.this.mActivity, "暂无记录");
                    return;
                }
                if (TextUtils.isEmpty(HomeHasSomeoneActivity.this.mSomeoneModel.getQueryId())) {
                    HomeHasSomeoneActivity.this.mRefresh.finishLoadMoreWithNoMoreData();
                }
                if (isRefresh) {
                    HomeHasSomeoneActivity.this.mAdapter.setNewData(HomeHasSomeoneActivity.this.mSomeoneModel.getList());
                    HomeHasSomeoneActivity.this.mAdapter.notifyDataSetChanged();
                } else {
                    HomeHasSomeoneActivity.this.mAdapter.addData(HomeHasSomeoneActivity.this.mSomeoneModel.getList());
                    HomeHasSomeoneActivity.this.mAdapter.notifyDataSetChanged();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(HomeHasSomeoneActivity.this.mActivity, var1.getErrorMessage());
            }
        });
        if (!isRefresh) {
            this.mRefresh.finishLoadMore();
        } else {
            this.mRefresh.finishRefresh();
        }
    }
}
