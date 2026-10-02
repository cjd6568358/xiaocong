package com.ixiaocong.smarthome.phone.android.detail.fragment.scene;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.WrapContentLinearLayoutManager;
import com.ixiaocong.smarthome.phone.android.detail.adater.SceneExecuteLogAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.scene.SceneExecuteLogModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SceneExecuteLogFragment extends XcBaseFragment {
    private SceneExecuteLogAdapter mLogAdapter;
    private List<SceneExecuteLogModel.LogListModel> mLogList;
    private int mPageNum = 1;
    private int mPageSize = 15;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvExecuteLog;
    private TextView mTvHint;

    static /* synthetic */ int access$008(SceneExecuteLogFragment x0) {
        int i = x0.mPageNum;
        x0.mPageNum = i + 1;
        return i;
    }

    protected int getLayoutId() {
        return R.layout.fragment_scene_executive_log;
    }

    protected void initView() {
        this.mTvHint = (TextView) $(R.id.tv_scene_execute_log_hint);
        this.mRefreshLayout = (SmartRefreshLayout) $(R.id.srl_scene_execute_log);
        this.mRvExecuteLog = (RecyclerView) $(R.id.rv_scene_execute_log);
        this.mRvExecuteLog.setLayoutManager(new WrapContentLinearLayoutManager(this.mActivity, 1, false));
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
    protected void initData() {
        this.mLogList = new ArrayList();
        this.mLogAdapter = new SceneExecuteLogAdapter(this.mActivity);
        this.mRvExecuteLog.setAdapter(this.mLogAdapter);
        loadData(1, false);
    }

    public void addListener() {
        super.addListener();
        this.mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.SceneExecuteLogFragment.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (SceneExecuteLogFragment.this.getUserVisibleHint()) {
                    SceneExecuteLogFragment.access$008(SceneExecuteLogFragment.this);
                    SceneExecuteLogFragment.this.loadData(SceneExecuteLogFragment.this.mPageNum, true);
                }
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(RefreshLayout refreshLayout) {
                SceneExecuteLogFragment.this.loadData(1, false);
            }
        });
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (this.mActivity != null) {
                loadData(1, false);
            }
        } else {
            this.mPageNum = 1;
        }
        XcLogger.e("sceneExecuteLogFragment", isVisibleToUser + "---" + this.mPageNum);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData(int pageNum, final boolean isLoad) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum", pageNum + Constants.MAIN_VERSION_TAG);
        params.put("pageSize", this.mPageSize + Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setNeedSign(false);
        httpSetting.setPath("ifttt/log");
        if (!isLoad && getUserVisibleHint()) {
            HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        }
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.SceneExecuteLogFragment.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SceneExecuteLogModel logModel = (SceneExecuteLogModel) JSON.parseObject(var1.getData(), SceneExecuteLogModel.class);
                if (logModel == null || logModel.getLogList() == null || logModel.getLogList().size() == 0) {
                    if (SceneExecuteLogFragment.this.getUserVisibleHint()) {
                        ToastUtils.showShort(SceneExecuteLogFragment.this.mActivity, "已无更多执行记录");
                    }
                    if (SceneExecuteLogFragment.this.mLogList == null || SceneExecuteLogFragment.this.mLogList.size() != 0) {
                        SceneExecuteLogFragment.this.mTvHint.setVisibility(8);
                    } else {
                        SceneExecuteLogFragment.this.mTvHint.setVisibility(0);
                        SceneExecuteLogFragment.this.mTvHint.setText("无执行记录");
                    }
                    if (isLoad) {
                        SceneExecuteLogFragment.this.mRefreshLayout.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    return;
                }
                if (isLoad) {
                    SceneExecuteLogFragment.this.mLogList.addAll(logModel.getLogList());
                    SceneExecuteLogFragment.this.mLogAdapter.addData(SceneExecuteLogFragment.this.mLogList);
                } else {
                    SceneExecuteLogFragment.this.mLogList.clear();
                    SceneExecuteLogFragment.this.mLogList.addAll(logModel.getLogList());
                    SceneExecuteLogFragment.this.mLogAdapter.setNewData(SceneExecuteLogFragment.this.mLogList);
                }
                SceneExecuteLogFragment.this.mLogAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                if (SceneExecuteLogFragment.this.getUserVisibleHint()) {
                    ToastUtils.showShort(SceneExecuteLogFragment.this.mActivity, var1.getErrorMessage());
                }
                SceneExecuteLogFragment.this.mTvHint.setVisibility(0);
                SceneExecuteLogFragment.this.mTvHint.setText("执行记录加载失败");
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
        if (this.mRefreshLayout != null) {
            if (isLoad) {
                this.mRefreshLayout.finishLoadMore();
            } else {
                this.mRefreshLayout.finishRefresh();
            }
        }
    }
}
