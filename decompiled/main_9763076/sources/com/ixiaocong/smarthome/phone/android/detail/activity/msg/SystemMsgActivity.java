package com.ixiaocong.smarthome.phone.android.detail.activity.msg;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.SystemMsgAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.SystemMsgModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SystemMsgActivity extends XcBaseActivity {
    private ImageView mIvBack;
    private SystemMsgAdapter mMsgAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private TextView mRightTv;
    private SystemMsgModel mSystemMsgModel;
    private RecyclerView mSystemMsgRecycler;
    private int mPageNum = 1;
    private int mPageSize = 15;
    private boolean isRefresh = false;
    private boolean isLoadEnd = false;

    static /* synthetic */ int access$408(SystemMsgActivity x0) {
        int i = x0.mPageNum;
        x0.mPageNum = i + 1;
        return i;
    }

    protected int getLayoutId() {
        return R.layout.activity_system_msg;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mRightTv = (TextView) $(R.id.right_titlebar_text);
        this.mSystemMsgRecycler = (RecyclerView) $(R.id.rv_system_msg);
        this.mRefreshLayout = (SmartRefreshLayout) $(R.id.srl_system_msg_layout);
        this.mSystemMsgRecycler.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        loadDeviceMsg(this.mPageNum, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadDeviceMsg(int pageNum, final boolean isLoadMore) {
        if (!this.isRefresh) {
            HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("page", String.valueOf(pageNum));
        params.put("pageSize", String.valueOf(this.mPageSize));
        httpSetting.setParamsMap(params);
        httpSetting.setNeedSign(false);
        httpSetting.setPath("user/message/find");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SystemMsgActivity.this.mSystemMsgModel = (SystemMsgModel) JSON.parseObject(var1.getData(), SystemMsgModel.class);
                if (SystemMsgActivity.this.mSystemMsgModel.getMessages() != null) {
                    if (isLoadMore) {
                        SystemMsgActivity.this.mMsgAdapter.addData(SystemMsgActivity.this.mSystemMsgModel.getMessages());
                    } else {
                        SystemMsgActivity.this.mMsgAdapter.setNewData(SystemMsgActivity.this.mSystemMsgModel.getMessages());
                    }
                    SystemMsgActivity.this.mMsgAdapter.notifyDataSetChanged();
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SystemMsgActivity.this.mActivity, var1.getErrorMessage());
            }
        });
        if (this.mRefreshLayout != null) {
            if (isLoadMore) {
                this.mRefreshLayout.finishLoadMore();
            } else {
                this.mRefreshLayout.finishRefresh();
            }
        }
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
        this.mMsgAdapter = new SystemMsgAdapter(this.mActivity);
        this.mSystemMsgRecycler.setAdapter(this.mMsgAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(SystemMsgActivity$$Lambda$1.lambdaFactory$(this));
        this.mRightTv.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SystemMsgActivity.this.startActivity(new Intent(SystemMsgActivity.this.mActivity, (Class<?>) SystemMsgSettingActivity.class));
            }
        });
        this.mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.msg.SystemMsgActivity.3
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                SystemMsgActivity.access$408(SystemMsgActivity.this);
                if (SystemMsgActivity.this.mSystemMsgModel != null) {
                    if (SystemMsgActivity.this.mSystemMsgModel.getTotalPage() < SystemMsgActivity.this.mPageNum) {
                        ToastUtils.showShort(SystemMsgActivity.this.mActivity, "我也是有底线的...");
                        SystemMsgActivity.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        SystemMsgActivity.this.loadDeviceMsg(SystemMsgActivity.this.mPageNum, true);
                    }
                }
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                SystemMsgActivity.this.mRefreshLayout.autoLoadMore();
                SystemMsgActivity.this.loadDeviceMsg(1, false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        finish();
    }

    protected void onResume() {
        super.onResume();
    }
}
