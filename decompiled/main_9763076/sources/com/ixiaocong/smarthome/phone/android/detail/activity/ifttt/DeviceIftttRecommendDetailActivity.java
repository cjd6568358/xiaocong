package com.ixiaocong.smarthome.phone.android.detail.activity.ifttt;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager;
import com.ixiaocong.smarthome.phone.android.common.manager.SoftInputManager;
import com.ixiaocong.smarthome.phone.android.common.utils.SceneWorkdayUtils;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.CommonBottomPop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.DeviceIftttDetailRelateAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.model.scene.RelateActionModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDetailModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceIftttRecommendDetailActivity extends XcBaseActivity implements View.OnClickListener, CommonPopCallback, HintDialogCallback, IftttOperationCallback {
    private List<RelateActionModel> mActionListData;
    private LinearLayout mAddRelateLayout;
    private ConstraintLayout mClSetWorkday;
    private int mCommonPopType = 1;
    private EditText mEtSceneName;
    private ImageView mIvBack;
    private ImageView mIvTime;
    private ImageView mIvTriggerDeviceIcon;
    private DeviceIftttDetailRelateAdapter mRelateAdapter;
    private RecyclerView mRvRelateIfttt;
    private SceneDetailModel mSceneDetailModel;
    private String mTriggerId;
    private String mTriggerName;
    private TextView mTvSetting;
    private TextView mTvTime;
    private TextView mTvTitle;
    private TextView mTvTriggerDesc;

    protected int getLayoutId() {
        return R.layout.activity_ifttt_device_detail;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvTime = (ImageView) $(R.id.iv_scene_detail_time);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mIvTriggerDeviceIcon = (ImageView) $(R.id.iv_ifttt_detail_trigger_device_icon);
        this.mTvTriggerDesc = (TextView) $(R.id.tv_ifttt_detail_trigger_device_desc);
        this.mTvTitle = (TextView) $(R.id.centertxt_titlebar);
        $(R.id.tv_scene_detail_time_hint).setVisibility(4);
        this.mTvTime = (TextView) $(R.id.tv_scene_detail_time);
        this.mRvRelateIfttt = (RecyclerView) $(R.id.rv_dev_ifttt_relate_list);
        this.mEtSceneName = (EditText) $(R.id.et_scene_detail_name);
        this.mClSetWorkday = (ConstraintLayout) $(R.id.cl_scene_detail_time);
        this.mRvRelateIfttt.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    private void addRelateFooter() {
        View footerView = View.inflate(this.mActivity, R.layout.scene_relate_footer_layout, null);
        this.mRelateAdapter.addFooterView(footerView);
        this.mAddRelateLayout = (LinearLayout) footerView.findViewById(R.id.ll_add_scene_relate_layout);
        this.mAddRelateLayout.setOnClickListener(this);
    }

    protected void initData() {
        this.mTriggerId = getIntent().getStringExtra("triggerId");
        this.mTriggerName = getIntent().getStringExtra("triggerName");
        this.mTvTitle.setText(this.mTriggerName);
        this.mTvSetting.setText("保存");
        this.mEtSceneName.setText(this.mTriggerName);
        loadData();
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
        this.mRelateAdapter = new DeviceIftttDetailRelateAdapter(this.mActivity);
        addRelateFooter();
        this.mRvRelateIfttt.setAdapter(this.mRelateAdapter);
    }

    private void loadData() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("triggerId", this.mTriggerId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/detailRecommend");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttRecommendDetailActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                DeviceIftttRecommendDetailActivity.this.mSceneDetailModel = (SceneDetailModel) JSON.parseObject(var1.getData(), SceneDetailModel.class);
                if (DeviceIftttRecommendDetailActivity.this.mSceneDetailModel == null || DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger() == null) {
                    ToastUtils.showShort(DeviceIftttRecommendDetailActivity.this.mActivity, "暂未获取到推荐场景详情");
                    return;
                }
                if (DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getType().equals("manual")) {
                    DeviceIftttRecommendDetailActivity.this.mTvTime.setText("--/--");
                    DeviceIftttRecommendDetailActivity.this.mIvTime.setVisibility(4);
                } else {
                    DeviceIftttRecommendDetailActivity.this.mIvTime.setVisibility(0);
                    if (TextUtils.isEmpty(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStartWorkTime()) || TextUtils.isEmpty(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStopWorkTime())) {
                        DeviceIftttRecommendDetailActivity.this.mTvTime.setText("未设置");
                    } else if (DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStartWorkTime().compareTo(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStopWorkTime()) > 0) {
                        DeviceIftttRecommendDetailActivity.this.mTvTime.setText(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStartWorkTime() + "-次日" + DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStopWorkTime() + SceneWorkdayUtils.cycleTime(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getWorkday()));
                    } else if (DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStartWorkTime().compareTo(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStopWorkTime()) < 0) {
                        DeviceIftttRecommendDetailActivity.this.mTvTime.setText(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStartWorkTime() + "-" + DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getStopWorkTime() + SceneWorkdayUtils.cycleTime(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getWorkday()));
                    }
                }
                Glide.with(DeviceIftttRecommendDetailActivity.this.mActivity).load(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getTriggerIcon()).placeholder(R.drawable.default_img_icon).into(DeviceIftttRecommendDetailActivity.this.mIvTriggerDeviceIcon);
                DeviceIftttRecommendDetailActivity.this.mTvTriggerDesc.setText(DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getTriggerDesc());
                DeviceIftttRecommendDetailActivity.this.mActionListData = DeviceIftttRecommendDetailActivity.this.mSceneDetailModel.getTrigger().getActionList();
                DeviceIftttRecommendDetailActivity.this.mRelateAdapter.setNewData(DeviceIftttRecommendDetailActivity.this.mActionListData);
                DeviceIftttRecommendDetailActivity.this.isEditMsg(true);
                DeviceIftttRecommendDetailActivity.this.mRelateAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(DeviceIftttRecommendDetailActivity.this.mActivity, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
        this.mTvTriggerDesc.setOnClickListener(this);
        this.mClSetWorkday.setOnClickListener(this);
        this.mRvRelateIfttt.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttRecommendDetailActivity.2
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_ifttt_edit_del_adapter /* 2131296499 */:
                        if (DeviceIftttRecommendDetailActivity.this.mActionListData == null || DeviceIftttRecommendDetailActivity.this.mActionListData.size() <= 0) {
                            ToastUtils.showShort(DeviceIftttRecommendDetailActivity.this.mActivity, "没有推荐的执行设备");
                        } else {
                            IftttHttpManager.deleteRelate(DeviceIftttRecommendDetailActivity.this.mActivity, ((RelateActionModel) DeviceIftttRecommendDetailActivity.this.mActionListData.get(position)).getActionId(), position, DeviceIftttRecommendDetailActivity.this);
                        }
                        break;
                    case R.id.rl_ifttt_relate_edit_layout /* 2131296693 */:
                        if (DeviceIftttRecommendDetailActivity.this.mActionListData == null || DeviceIftttRecommendDetailActivity.this.mActionListData.size() <= 0) {
                            ToastUtils.showShort(DeviceIftttRecommendDetailActivity.this.mActivity, "没有推荐的执行设备");
                        } else {
                            IftttSelectRelatePop.getInstance().selectSceneOrDevice(DeviceIftttRecommendDetailActivity.this.mActivity, DeviceIftttRecommendDetailActivity.this.mIvBack, DeviceIftttRecommendDetailActivity.this.mTriggerId, DeviceIftttRecommendDetailActivity.this);
                            if (((RelateActionModel) DeviceIftttRecommendDetailActivity.this.mActionListData.get(position)).getActionType().equals("device")) {
                                IftttSelectRelatePop.getInstance().getRelateParameterValue(DeviceIftttRecommendDetailActivity.this.mActivity, (RelateActionModel) DeviceIftttRecommendDetailActivity.this.mActionListData.get(position), position);
                            } else {
                                IftttSelectRelatePop.getInstance().updateRelate(DeviceIftttRecommendDetailActivity.this.mActivity, ((RelateActionModel) DeviceIftttRecommendDetailActivity.this.mActionListData.get(position)).getActionId(), position);
                            }
                        }
                        break;
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_scene_detail_time /* 2131296382 */:
                if (this.mSceneDetailModel != null && this.mSceneDetailModel.getTrigger() != null) {
                    if (this.mSceneDetailModel.getTrigger().getType().equals("manual")) {
                        ToastUtils.showShort(this.mActivity, "手动执行不支持时间段设置");
                    } else {
                        Intent intent = new Intent(this.mActivity, (Class<?>) WorkdayIftttSettingActivity.class);
                        intent.putExtra("workday", this.mSceneDetailModel.getTrigger().getWorkday());
                        intent.putExtra("startTime", this.mSceneDetailModel.getTrigger().getStartWorkTime());
                        intent.putExtra("stopTime", this.mSceneDetailModel.getTrigger().getStopWorkTime());
                        startActivityForResult(intent, 100);
                    }
                } else {
                    ToastUtils.showShort(this.mActivity, "暂未获取到推荐场景详情");
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                finish();
                break;
            case R.id.ll_add_scene_relate_layout /* 2131296571 */:
                showRelatePopupwindow();
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                if (this.mActionListData == null || this.mActionListData.size() == 0) {
                    ToastUtils.showShort(this.mActivity, "执行动作列表不能为空");
                } else {
                    String sceneName = this.mEtSceneName.getText().toString().trim();
                    if (this.mSceneDetailModel != null && this.mSceneDetailModel.getTrigger() != null) {
                        this.mSceneDetailModel.getTrigger().setTriggerName(sceneName);
                        this.mSceneDetailModel.getTrigger().setStatus(1);
                        if (this.mSceneDetailModel.getTrigger().getType().equals("auto")) {
                            String time = this.mTvTime.getText().toString();
                            if (TextUtils.isEmpty(time) || time.contains("设置")) {
                                this.mSceneDetailModel.getTrigger().setStartWorkTime("00:00");
                                this.mSceneDetailModel.getTrigger().setStopWorkTime("23:59");
                                this.mSceneDetailModel.getTrigger().setWorkday("0,1,2,3,4,5,6");
                            }
                        }
                        IftttHttpManager.updateTrigger(this.mActivity, 2, this.mSceneDetailModel.getTrigger(), this);
                    } else {
                        finish();
                    }
                }
                break;
            case R.id.tv_ifttt_detail_trigger_device_desc /* 2131297001 */:
                SoftInputManager.hidenSoftInputFromWindow(this.mIvBack);
                if (this.mSceneDetailModel != null && this.mSceneDetailModel.getTrigger().getSource() != 0) {
                    IftttSelectTriggerPop.getInstance().showPop(this.mActivity, this.mIvBack, this, false);
                    if (this.mSceneDetailModel.getTrigger().getType().equals("manual")) {
                        IftttSelectTriggerPop.getInstance().triggerManualType(this.mActivity, this.mSceneDetailModel.getTrigger());
                    } else {
                        IftttSelectTriggerPop.getInstance().triggerAotoType(this.mActivity, this.mSceneDetailModel.getTrigger());
                    }
                    break;
                }
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void isEditMsg(boolean isEdit) {
        if (this.mSceneDetailModel != null && this.mSceneDetailModel.getTrigger().getSource() != 0) {
            this.mEtSceneName.setFocusable(isEdit);
            this.mEtSceneName.setFocusableInTouchMode(isEdit);
            this.mEtSceneName.setEnabled(isEdit);
        }
        this.mRelateAdapter.setShow(isEdit);
        this.mRelateAdapter.notifyDataSetChanged();
    }

    private void showRelatePopupwindow() {
        if (this.mActionListData != null && this.mActionListData.size() > 0) {
            IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, this.mTriggerId, this);
            if (this.mActionListData.get(0).getActionType().equals("device")) {
                IftttSelectRelatePop.getInstance().getRelateDeviceList(this.mActivity);
                return;
            } else {
                IftttSelectRelatePop.getInstance().getRelateSceneList(this.mActivity);
                return;
            }
        }
        CommonBottomPop.getInstance().showCommonBootomPopup(this.mActivity, this, this.mIvBack, "执行设备", "执行场景");
        CommonBottomPop.getInstance().setCommonFirstColor(getResources().getColor(R.color.master_color));
        CommonBottomPop.getInstance().setCommonSecondColor(getResources().getColor(R.color.master_color));
        this.mCommonPopType = 2;
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback
    public void commonFirstClick() {
        if (this.mCommonPopType == 2) {
            IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, this.mTriggerId, this);
            IftttSelectRelatePop.getInstance().getRelateDeviceList(this.mActivity);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback
    public void commonSecondClick() {
        if (this.mCommonPopType == 2) {
            IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, this.mTriggerId, this);
            IftttSelectRelatePop.getInstance().getRelateSceneList(this.mActivity);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void deleteRelateCallback(boolean isSuccess, int position) {
        if (isSuccess && this.mActionListData != null && this.mActionListData.size() > position) {
            this.mActionListData.remove(position);
            this.mRelateAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void updateTriggerCallback(int type, SceneDetailModel.TriggerModel triggerModel) {
        if (type == 1) {
            if (triggerModel != null) {
                if (triggerModel.getType().equals("auto")) {
                    this.mIvTime.setVisibility(0);
                    String stopTime = triggerModel.getStopWorkTime();
                    String startTime = triggerModel.getStartWorkTime();
                    String workday = triggerModel.getWorkday();
                    if (TextUtils.isEmpty(stopTime) || TextUtils.isEmpty(startTime) || TextUtils.isEmpty(workday)) {
                        this.mTvTime.setText("去设置");
                    } else if (startTime.compareTo(stopTime) > 0) {
                        this.mTvTime.setText(startTime + "-次日" + stopTime + SceneWorkdayUtils.cycleTime(workday));
                    } else if (startTime.compareTo(stopTime) < 0) {
                        this.mTvTime.setText(startTime + "-" + stopTime + SceneWorkdayUtils.cycleTime(workday));
                    }
                } else {
                    this.mTvTime.setText("--/--");
                    this.mIvTime.setVisibility(4);
                }
                Glide.with(this.mActivity).load(triggerModel.getTriggerIcon()).placeholder(R.drawable.default_img_icon).into(this.mIvTriggerDeviceIcon);
                this.mTvTriggerDesc.setText(triggerModel.getTriggerDesc());
                this.mSceneDetailModel.setTrigger(triggerModel);
                return;
            }
            return;
        }
        if (type == 2) {
            finish();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void updateRelateCallback(int position, RelateActionModel relateModel) {
        if (relateModel != null) {
            if (this.mActionListData == null) {
                this.mActionListData = new ArrayList();
            }
            this.mActionListData.set(position, relateModel);
            this.mRelateAdapter.setNewData(this.mActionListData);
            this.mRelateAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void addRelateCallback(RelateActionModel actionModel) {
        if (actionModel != null) {
            if (this.mActionListData == null) {
                this.mActionListData = new ArrayList();
            }
            this.mActionListData.add(actionModel);
            this.mRelateAdapter.setNewData(this.mActionListData);
            this.mRelateAdapter.notifyDataSetChanged();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && requestCode == 100 && data != null) {
            String startTime = data.getStringExtra("startTime");
            String stopTime = data.getStringExtra("stopTime");
            String workday = data.getStringExtra("workday");
            if (startTime.compareTo(stopTime) > 0) {
                this.mTvTime.setText(startTime + "-次日" + stopTime + SceneWorkdayUtils.cycleTime(workday));
            } else if (startTime.compareTo(stopTime) < 0) {
                this.mTvTime.setText(startTime + "-" + stopTime + SceneWorkdayUtils.cycleTime(workday));
            }
            if (this.mSceneDetailModel != null) {
                this.mSceneDetailModel.getTrigger().setStartWorkTime(startTime);
                this.mSceneDetailModel.getTrigger().setStopWorkTime(stopTime);
                this.mSceneDetailModel.getTrigger().setWorkday(workday);
            }
        }
    }

    public void onBackPressed() {
        if (IftttSelectRelatePop.getInstance().isShowPop()) {
            IftttSelectRelatePop.getInstance().dismiss();
            return;
        }
        if (IftttSelectTriggerPop.getInstance().isShowPop()) {
            IftttSelectTriggerPop.getInstance().dismiss();
        } else if (CommonBottomPop.getInstance().isShow()) {
            CommonBottomPop.getInstance().dismissPop();
        } else {
            super.onBackPressed();
        }
    }
}
