package com.ixiaocong.smarthome.phone.android.detail.fragment.scene;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttCreateActivity;
import com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttRecommendDetailActivity;
import com.ixiaocong.smarthome.phone.android.detail.adater.SceneSystemRecommendAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
import com.xiaocong.smarthome.httplib.model.scene.UserIftttListModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SceneSystemRecommendFragment extends XcBaseFragment {
    private SceneSystemRecommendAdapter mAdapter;
    private Button mBtnAddScene;
    private RelativeLayout mHintLayout;
    private List<UserIftttListModel.IftttModel> mListData;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvSceneSys;
    private boolean isOnRefresh = false;
    private boolean isFistrLoad = false;

    protected int getLayoutId() {
        return R.layout.fragment_scene_system_recommend;
    }

    protected void initView() {
        this.mHintLayout = (RelativeLayout) $(R.id.rl_scene_recommend_hint_layout);
        this.mBtnAddScene = (Button) $(R.id.btn_scene_recommend_hint_layout_add);
        this.mRefreshLayout = (SmartRefreshLayout) $(R.id.srl_system_recomm_scene_layout);
        this.mRvSceneSys = (RecyclerView) $(R.id.rv_scene_sys_list);
        this.mRvSceneSys.setLayoutManager(new LinearLayoutManager(this.mActivity));
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
        this.mAdapter = new SceneSystemRecommendAdapter(this.mActivity);
        this.mRvSceneSys.setAdapter(this.mAdapter);
    }

    public void onResume() {
        super.onResume();
        loadData();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && this.isFistrLoad) {
            loadData();
        }
    }

    private void loadData() {
        if (!this.isOnRefresh && getUserVisibleHint()) {
            HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("ifttt/list/recommend");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.SceneSystemRecommendFragment.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                UserIftttListModel userIftttListModel = (UserIftttListModel) JSON.parseObject(var1.getData(), UserIftttListModel.class);
                if (userIftttListModel == null) {
                    SceneSystemRecommendFragment.this.mHintLayout.setVisibility(0);
                    return;
                }
                if (userIftttListModel.getSceneList().size() == 0) {
                    SceneSystemRecommendFragment.this.mHintLayout.setVisibility(0);
                } else {
                    SceneSystemRecommendFragment.this.mHintLayout.setVisibility(8);
                }
                SceneSystemRecommendFragment.this.mListData = userIftttListModel.getSceneList();
                SceneSystemRecommendFragment.this.mAdapter.setNewData(SceneSystemRecommendFragment.this.mListData);
                SceneSystemRecommendFragment.this.mAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(SceneSystemRecommendFragment.this.mActivity, var1.getErrorMessage());
            }
        });
        if (this.mRefreshLayout != null) {
            this.mRefreshLayout.finishRefresh();
        }
        this.isOnRefresh = false;
        this.isFistrLoad = true;
    }

    public void addListener() {
        super.addListener();
        this.mRvSceneSys.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.SceneSystemRecommendFragment.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent(SceneSystemRecommendFragment.this.mActivity, (Class<?>) DeviceIftttRecommendDetailActivity.class);
                intent.putExtra("triggerId", ((UserIftttListModel.IftttModel) SceneSystemRecommendFragment.this.mListData.get(position)).getTriggerId());
                intent.putExtra("triggerName", ((UserIftttListModel.IftttModel) SceneSystemRecommendFragment.this.mListData.get(position)).getTriggerName());
                SceneSystemRecommendFragment.this.startActivityForNew(SceneSystemRecommendFragment.this.mActivity, intent);
            }
        });
        this.mRefreshLayout.setOnRefreshListener(SceneSystemRecommendFragment$$Lambda$1.lambdaFactory$(this));
        this.mBtnAddScene.setOnClickListener(SceneSystemRecommendFragment$$Lambda$2.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(RefreshLayout refreshLayout) {
        this.isOnRefresh = true;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(View v) {
        startActivityForNew(this.mActivity, new Intent(this.mActivity, (Class<?>) DeviceIftttCreateActivity.class));
    }
}
