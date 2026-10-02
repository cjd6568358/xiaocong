package com.ixiaocong.smarthome.phone.android.detail.fragment.scene;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttDetailActivity;
import com.ixiaocong.smarthome.phone.android.detail.adater.SceneUserAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseFragment;
import com.xiaocong.smarthome.httplib.model.scene.UserIftttListModel;
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
public class SceneUserFragment extends XcBaseFragment {
    private boolean isOnRefresh = false;
    private SceneUserAdapter mAdapter;
    private List<UserIftttListModel.IftttModel> mListData;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRvUserScene;
    private TextView mTvHint;

    protected int getLayoutId() {
        return R.layout.fragment_scene_user;
    }

    protected void initView() {
        this.mRefreshLayout = (SmartRefreshLayout) $(R.id.srl_scene_user_layout);
        this.mRvUserScene = (RecyclerView) $(R.id.rv_scene_user_list);
        this.mTvHint = (TextView) $(R.id.tv_scene_execute_user_hint);
        this.mRvUserScene.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        loadData();
    }

    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        if (!this.isOnRefresh && getUserVisibleHint()) {
            HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("type", Constants.MAIN_VERSION_TAG);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/list");
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.SceneUserFragment.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                UserIftttListModel listModel = (UserIftttListModel) JSON.parseObject(var1.getData(), UserIftttListModel.class);
                SceneUserFragment.this.mListData = listModel.getSceneList();
                if (SceneUserFragment.this.mListData == null || SceneUserFragment.this.mListData.size() == 0) {
                    SceneUserFragment.this.mTvHint.setVisibility(0);
                    SceneUserFragment.this.mRefreshLayout.setVisibility(8);
                } else {
                    SceneUserFragment.this.mTvHint.setVisibility(8);
                    SceneUserFragment.this.mRefreshLayout.setVisibility(0);
                }
                SceneUserFragment.this.mAdapter.setNewData(SceneUserFragment.this.mListData);
                SceneUserFragment.this.mAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                if (SceneUserFragment.this.getUserVisibleHint()) {
                    ToastUtils.showShort(SceneUserFragment.this.mActivity, var1.getErrorMessage());
                }
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
        if (this.mRefreshLayout != null) {
            this.mRefreshLayout.finishRefresh();
        }
        this.isOnRefresh = false;
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
        this.mAdapter = new SceneUserAdapter(this.mActivity);
        this.mRvUserScene.setAdapter(this.mAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mRvUserScene.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.SceneUserFragment.2
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                Intent intent = new Intent(SceneUserFragment.this.mActivity, (Class<?>) DeviceIftttDetailActivity.class);
                intent.putExtra("triggerId", ((UserIftttListModel.IftttModel) SceneUserFragment.this.mListData.get(position)).getTriggerId());
                intent.putExtra("triggerName", ((UserIftttListModel.IftttModel) SceneUserFragment.this.mListData.get(position)).getTriggerName());
                intent.putExtra("triggerIntro", ((UserIftttListModel.IftttModel) SceneUserFragment.this.mListData.get(position)).getTriggerIntro());
                intent.putExtra("sceneType", ((UserIftttListModel.IftttModel) SceneUserFragment.this.mListData.get(position)).getType());
                SceneUserFragment.this.startActivityForNew(SceneUserFragment.this.mActivity, intent);
            }
        });
        this.mRvUserScene.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.SceneUserFragment.3
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_scene_user_click /* 2131296331 */:
                        IftttHttpManager.executeIfttt(SceneUserFragment.this.mActivity, ((UserIftttListModel.IftttModel) SceneUserFragment.this.mAdapter.getData().get(position)).getTriggerId());
                        break;
                    case R.id.sb_scene_user_switch /* 2131296780 */:
                        if (((UserIftttListModel.IftttModel) SceneUserFragment.this.mAdapter.getData().get(position)).getStatus() == 0) {
                            SceneUserFragment.this.updateTrigger(((UserIftttListModel.IftttModel) SceneUserFragment.this.mAdapter.getData().get(position)).getTriggerId(), ((UserIftttListModel.IftttModel) SceneUserFragment.this.mAdapter.getData().get(position)).getTriggerName(), ((UserIftttListModel.IftttModel) SceneUserFragment.this.mAdapter.getData().get(position)).getTriggerIntro(), 1, position);
                        } else {
                            SceneUserFragment.this.updateTrigger(((UserIftttListModel.IftttModel) SceneUserFragment.this.mAdapter.getData().get(position)).getTriggerId(), ((UserIftttListModel.IftttModel) SceneUserFragment.this.mAdapter.getData().get(position)).getTriggerName(), ((UserIftttListModel.IftttModel) SceneUserFragment.this.mAdapter.getData().get(position)).getTriggerIntro(), 0, position);
                        }
                        break;
                }
            }
        });
        this.mRefreshLayout.setOnRefreshListener(SceneUserFragment$$Lambda$1.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(RefreshLayout refreshLayout) {
        this.isOnRefresh = true;
        loadData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTrigger(String triggerId, String triggerName, String triggerIntro, final int status, final int position) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put("triggerId", triggerId);
        paramsNoSign.put(Constants.FLAG_DEVICE_ID, Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("parameterKey", Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("parameterType", Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("productParameterId", Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("triggerCondition", Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("triggerIcon", Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("triggerName", triggerName);
        paramsNoSign.put("triggerIntro", triggerIntro);
        paramsNoSign.put("threshold", Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("status", status + Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("ifttt/update");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.fragment.scene.SceneUserFragment.4
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ((UserIftttListModel.IftttModel) SceneUserFragment.this.mListData.get(position)).setStatus(status);
                if (status == 1) {
                    ToastUtils.showShort(SceneUserFragment.this.mActivity, "启用成功");
                } else {
                    ToastUtils.showShort(SceneUserFragment.this.mActivity, "禁用成功");
                }
            }

            public void onError(XCErrorMessage var1) {
                if (status == 1) {
                    ToastUtils.showShort(SceneUserFragment.this.mActivity, "启用失败");
                } else {
                    ToastUtils.showShort(SceneUserFragment.this.mActivity, "禁用失败");
                }
                SceneUserFragment.this.mAdapter.notifyItemChanged(position);
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }
}
