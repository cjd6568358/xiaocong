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
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.CommonBottomPop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.CommonBottomSecondPop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop;
import com.ixiaocong.smarthome.phone.android.complete.prompt.popup.SelectAddPop;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.DeviceIftttDetailRelateAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonSecondPopCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.scene.RelateActionModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDetailModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.pickerview.PickerViewHelper;
import com.xiaocong.smarthome.pickerview.listener.OnTimeSelectListener;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceIftttDetailActivity extends XcBaseActivity implements View.OnClickListener, CommonPopCallback, CommonSecondPopCallback, HintDialogCallback, IftttOperationCallback {
    private List<RelateActionModel> mActionListData;
    private LinearLayout mAddRelateLayout;
    private ConstraintLayout mClSetWorkday;
    private EditText mEtSceneName;
    private ImageView mIvBack;
    private ImageView mIvDelIcon;
    private ImageView mIvRight;
    private ImageView mIvTime;
    private ImageView mIvTriggerDeviceIcon;
    private DeviceIftttDetailRelateAdapter mRelateAdapter;
    private RecyclerView mRvRelateIfttt;
    private SceneDetailModel mSceneDetailModel;
    private String mSceneType;
    private String mTriggerId;
    private String mTriggerName;
    private TextView mTvSetting;
    private TextView mTvTime;
    private TextView mTvTimeHint;
    private TextView mTvTitle;
    private TextView mTvTriggerDesc;
    private int mCommonPopType = 1;
    private boolean isEditScene = false;

    protected int getLayoutId() {
        return R.layout.activity_ifttt_device_detail;
    }

    protected void initView() {
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mTvSetting = (TextView) $(R.id.right_titlebar_text);
        this.mIvRight = (ImageView) $(R.id.right_titlebar_image);
        this.mIvTriggerDeviceIcon = (ImageView) $(R.id.iv_ifttt_detail_trigger_device_icon);
        this.mIvDelIcon = (ImageView) $(R.id.iv_ifttt_detail_trigger_device_del_icon);
        this.mTvTriggerDesc = (TextView) $(R.id.tv_ifttt_detail_trigger_device_desc);
        this.mTvTitle = (TextView) $(R.id.centertxt_titlebar);
        this.mTvTimeHint = (TextView) $(R.id.tv_scene_detail_time_hint);
        this.mTvTime = (TextView) $(R.id.tv_scene_detail_time);
        this.mRvRelateIfttt = (RecyclerView) $(R.id.rv_dev_ifttt_relate_list);
        this.mEtSceneName = (EditText) $(R.id.et_scene_detail_name);
        this.mIvTime = (ImageView) $(R.id.iv_scene_detail_time);
        this.mClSetWorkday = (ConstraintLayout) $(R.id.cl_scene_detail_time);
        this.mRvRelateIfttt.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mTriggerId = getIntent().getStringExtra("triggerId");
        this.mTriggerName = getIntent().getStringExtra("triggerName");
        this.mSceneType = getIntent().getStringExtra("sceneType");
        this.mTvTitle.setText(this.mTriggerName);
        this.mEtSceneName.setText(this.mTriggerName);
        loadDetailData();
    }

    private void addRelateFooter() {
        View footerView = View.inflate(this.mActivity, R.layout.scene_relate_footer_layout, null);
        this.mRelateAdapter.addFooterView(footerView);
        this.mAddRelateLayout = (LinearLayout) footerView.findViewById(R.id.ll_add_scene_relate_layout);
        this.mAddRelateLayout.setOnClickListener(this);
        this.mAddRelateLayout.setVisibility(4);
        this.mIvTime.setVisibility(4);
    }

    private void loadDetailData() {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("triggerId", this.mTriggerId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("ifttt/detail");
        HttpLoadingHelper.getInstance().showProcessLoading(this.mActivity);
        XCRequest.getInstance().request(this.mActivity, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttDetailActivity.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                SceneDetailModel detailModel = (SceneDetailModel) JSON.parseObject(var1.getData(), SceneDetailModel.class);
                DeviceIftttDetailActivity.this.mSceneDetailModel = detailModel;
                if (detailModel.getTrigger().getStatus() == -1) {
                    DeviceIftttDetailActivity.this.mIvDelIcon.setVisibility(0);
                    DeviceIftttDetailActivity.this.mTvTriggerDesc.setText(detailModel.getTrigger().getDeviceName());
                } else {
                    DeviceIftttDetailActivity.this.mIvDelIcon.setVisibility(8);
                    DeviceIftttDetailActivity.this.mTvTriggerDesc.setText(detailModel.getTrigger().getTriggerDesc());
                }
                if (detailModel.getTrigger().getType().equals("manual")) {
                    DeviceIftttDetailActivity.this.mTvTimeHint.setVisibility(0);
                    DeviceIftttDetailActivity.this.mTvTime.setText("--/--");
                } else {
                    DeviceIftttDetailActivity.this.mTvTimeHint.setVisibility(4);
                    if (TextUtils.isEmpty(detailModel.getTrigger().getStartWorkTime()) || TextUtils.isEmpty(detailModel.getTrigger().getStopWorkTime())) {
                        DeviceIftttDetailActivity.this.mTvTime.setText("未设置");
                    } else if (detailModel.getTrigger().getStartWorkTime().compareTo(detailModel.getTrigger().getStopWorkTime()) > 0) {
                        DeviceIftttDetailActivity.this.mTvTime.setText(detailModel.getTrigger().getStartWorkTime() + "-次日" + detailModel.getTrigger().getStopWorkTime() + SceneWorkdayUtils.cycleTime(detailModel.getTrigger().getWorkday()));
                    } else if (detailModel.getTrigger().getStartWorkTime().compareTo(detailModel.getTrigger().getStopWorkTime()) < 0) {
                        DeviceIftttDetailActivity.this.mTvTime.setText(detailModel.getTrigger().getStartWorkTime() + "-" + detailModel.getTrigger().getStopWorkTime() + SceneWorkdayUtils.cycleTime(detailModel.getTrigger().getWorkday()));
                    }
                }
                Glide.with(DeviceIftttDetailActivity.this.mActivity).load(detailModel.getTrigger().getTriggerIcon()).placeholder(R.drawable.default_img_icon).into(DeviceIftttDetailActivity.this.mIvTriggerDeviceIcon);
                DeviceIftttDetailActivity.this.mActionListData = detailModel.getTrigger().getActionList();
                DeviceIftttDetailActivity.this.mRelateAdapter.setNewData(DeviceIftttDetailActivity.this.mActionListData);
                DeviceIftttDetailActivity.this.mRelateAdapter.notifyDataSetChanged();
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(DeviceIftttDetailActivity.this.mActivity, var1.getErrorMessage());
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
        super.initAdapter();
        this.mRelateAdapter = new DeviceIftttDetailRelateAdapter(this.mActivity);
        addRelateFooter();
        this.mRvRelateIfttt.setAdapter(this.mRelateAdapter);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvSetting.setOnClickListener(this);
        this.mClSetWorkday.setOnClickListener(this);
        this.mTvTriggerDesc.setOnClickListener(this);
        this.mRvRelateIfttt.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttDetailActivity.2
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_ifttt_edit_del_adapter /* 2131296499 */:
                        IftttHttpManager.deleteRelate(DeviceIftttDetailActivity.this.mActivity, ((RelateActionModel) DeviceIftttDetailActivity.this.mActionListData.get(position)).getActionId(), position, DeviceIftttDetailActivity.this);
                        break;
                    case R.id.rl_ifttt_relate_edit_layout /* 2131296693 */:
                        if (DeviceIftttDetailActivity.this.isEditScene) {
                            if (DeviceIftttDetailActivity.this.mActionListData == null || DeviceIftttDetailActivity.this.mActionListData.size() <= 0) {
                                ToastUtils.showShort(DeviceIftttDetailActivity.this.mActivity, "数据初始化失败,请重试");
                            } else if (((RelateActionModel) DeviceIftttDetailActivity.this.mActionListData.get(position)).getActionType().equals("device")) {
                                IftttSelectRelatePop.getInstance().selectSceneOrDevice(DeviceIftttDetailActivity.this.mActivity, DeviceIftttDetailActivity.this.mIvBack, DeviceIftttDetailActivity.this.mTriggerId, DeviceIftttDetailActivity.this);
                                if (((RelateActionModel) DeviceIftttDetailActivity.this.mActionListData.get(position)).getStatus() == -1) {
                                    IftttSelectRelatePop.getInstance().getRelateDeviceList(DeviceIftttDetailActivity.this.mActivity);
                                } else {
                                    IftttSelectRelatePop.getInstance().getRelateParameterValue(DeviceIftttDetailActivity.this.mActivity, (RelateActionModel) DeviceIftttDetailActivity.this.mActionListData.get(position), position);
                                }
                            } else if (!((RelateActionModel) DeviceIftttDetailActivity.this.mRelateAdapter.getData().get(position)).getActionType().equals("delay")) {
                                IftttSelectRelatePop.getInstance().selectSceneOrDevice(DeviceIftttDetailActivity.this.mActivity, DeviceIftttDetailActivity.this.mIvBack, DeviceIftttDetailActivity.this.mTriggerId, DeviceIftttDetailActivity.this);
                                IftttSelectRelatePop.getInstance().updateRelate(DeviceIftttDetailActivity.this.mActivity, ((RelateActionModel) DeviceIftttDetailActivity.this.mActionListData.get(position)).getActionId(), position);
                            } else {
                                DeviceIftttDetailActivity.this.chooseDelay(true, position);
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
                if (this.isEditScene) {
                    if (this.mSceneDetailModel.getTrigger().getType().equals("manual")) {
                        ToastUtils.showShort(this.mActivity, "手动执行不支持时间段设置");
                    } else {
                        Intent intent = new Intent(this.mActivity, (Class<?>) WorkdayIftttSettingActivity.class);
                        intent.putExtra("workday", this.mSceneDetailModel.getTrigger().getWorkday());
                        intent.putExtra("startTime", this.mSceneDetailModel.getTrigger().getStartWorkTime());
                        intent.putExtra("stopTime", this.mSceneDetailModel.getTrigger().getStopWorkTime());
                        startActivityForResult(intent, 100);
                    }
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                onBackPressed();
                break;
            case R.id.ll_add_scene_relate_layout /* 2131296571 */:
                showRelatePopupwindow();
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                if (this.isEditScene) {
                    if (this.mActionListData == null || this.mActionListData.size() == 0) {
                        ToastUtils.showShort(this.mActivity, "执行动作列表不能为空");
                    } else {
                        String sceneName = this.mEtSceneName.getText().toString().trim();
                        if (TextUtils.isEmpty(sceneName)) {
                            ToastUtils.showShort(this.mActivity, "场景名称不能为空");
                        } else if (this.mSceneDetailModel != null && this.mSceneDetailModel.getTrigger() != null) {
                            this.mSceneDetailModel.getTrigger().setTriggerName(sceneName);
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
                            isEditMsg(false);
                            this.mTvSetting.setText("编辑");
                            this.mAddRelateLayout.setVisibility(4);
                            this.mIvTime.setVisibility(4);
                        }
                    }
                } else {
                    SelectAddPop.getInstance().showSelectPop(this.mActivity, this.mTvSetting, this, false);
                    SelectAddPop.getInstance().setName(getString(R.string.edit_scene), getString(R.string.delete_scene));
                    this.mCommonPopType = 1;
                }
                break;
            case R.id.tv_ifttt_detail_trigger_device_desc /* 2131297001 */:
                SoftInputManager.hidenSoftInputFromWindow(this.mIvBack);
                if (this.isEditScene && this.mSceneDetailModel != null && this.mSceneDetailModel.getTrigger().getSource() != 0) {
                    IftttSelectTriggerPop.getInstance().showPop(this.mActivity, this.mIvBack, this, false);
                    if (this.mSceneDetailModel.getTrigger().getType().equals("manual")) {
                        IftttSelectTriggerPop.getInstance().triggerManualType(this.mActivity, this.mSceneDetailModel.getTrigger());
                    } else if (this.mSceneDetailModel.getTrigger().getStatus() == -1) {
                        IftttSelectTriggerPop.getInstance().triggerManualType(this.mActivity, this.mSceneDetailModel.getTrigger());
                    } else {
                        IftttSelectTriggerPop.getInstance().triggerAotoType(this.mActivity, this.mSceneDetailModel.getTrigger());
                    }
                    break;
                }
                break;
        }
    }

    private void showRelatePopupwindow() {
        if (this.mActionListData != null && this.mActionListData.size() > 0) {
            if (this.mActionListData.get(0).getActionType().equals("device") || this.mActionListData.get(0).getActionType().equals("delay")) {
                CommonBottomSecondPop.getInstance().showCommonBootomPopup(this.mActivity, this, this.mIvBack, "添加时间间隔", "添加执行设备");
                return;
            } else {
                IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, this.mTriggerId, this);
                IftttSelectRelatePop.getInstance().getRelateSceneList(this.mActivity);
                return;
            }
        }
        selectDeviceOrScene();
    }

    private void selectDeviceOrScene() {
        CommonBottomPop.getInstance().showCommonBootomPopup(this.mActivity, this, this.mIvBack, "执行设备", "执行场景");
        CommonBottomPop.getInstance().setCommonFirstColor(getResources().getColor(R.color.master_color));
        CommonBottomPop.getInstance().setCommonSecondColor(getResources().getColor(R.color.master_color));
        this.mCommonPopType = 2;
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            IftttHttpManager.deleteIfttt(this.mActivity, this.mTriggerId);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void deleteRelateCallback(boolean isSuccess, int position) {
        if (isSuccess && this.mActionListData != null && this.mActionListData.size() > 0 && position >= 0 && position < this.mActionListData.size()) {
            this.mActionListData.remove(position);
            this.mRelateAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void updateTriggerCallback(int type, SceneDetailModel.TriggerModel triggerModel) {
        if (type == 1) {
            if (triggerModel != null) {
                if (triggerModel.getType().equals("auto")) {
                    this.mTvTimeHint.setVisibility(8);
                    this.mIvTime.setVisibility(0);
                    if (TextUtils.isEmpty(this.mSceneDetailModel.getTrigger().getStartWorkTime()) && TextUtils.isEmpty(this.mSceneDetailModel.getTrigger().getStopWorkTime())) {
                        this.mTvTime.setText("去设置");
                    }
                } else {
                    this.mTvTimeHint.setVisibility(0);
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
            this.mEtSceneName.setText(triggerModel.getTriggerName());
            this.mTvTitle.setText(triggerModel.getTriggerName());
            isEditMsg(false);
            this.mTvSetting.setText("编辑");
            this.mAddRelateLayout.setVisibility(4);
            this.mIvTime.setVisibility(4);
            return;
        }
        if (type == 3) {
            this.mSceneDetailModel.getTrigger().setTriggerName(this.mTriggerName);
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void addRelateCallback(RelateActionModel actionModel) {
        if (actionModel != null) {
            this.mActionListData.add(actionModel);
            this.mRelateAdapter.setNewData(this.mActionListData);
            this.mRelateAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void updateRelateCallback(int position, RelateActionModel updateRelateModel) {
        if (updateRelateModel != null) {
            this.mActionListData.set(position, updateRelateModel);
            this.mRelateAdapter.setNewData(this.mActionListData);
            this.mRelateAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback
    public void commonFirstClick() {
        if (this.mCommonPopType == 1) {
            this.mTvSetting.setText("保存");
            isEditMsg(true);
            this.mAddRelateLayout.setVisibility(0);
            if (this.mSceneDetailModel.getTrigger().getType().equals("auto")) {
                this.mIvTime.setVisibility(0);
                if (TextUtils.isEmpty(this.mSceneDetailModel.getTrigger().getStartWorkTime()) || TextUtils.isEmpty(this.mSceneDetailModel.getTrigger().getStopWorkTime())) {
                    this.mTvTime.setText("去设置");
                    return;
                }
                return;
            }
            this.mIvTime.setVisibility(4);
            this.mTvTime.setText("--/--");
            return;
        }
        if (this.mCommonPopType == 2) {
            CommonBottomSecondPop.getInstance().showCommonBootomPopup(this.mActivity, this, this.mIvBack, "添加时间间隔", "添加执行设备");
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback
    public void commonSecondClick() {
        if (this.mCommonPopType == 1) {
            OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "删除场景", "确定删除当前场景吗?");
        } else if (this.mCommonPopType == 2) {
            IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, this.mTriggerId, this);
            IftttSelectRelatePop.getInstance().getRelateSceneList(this.mActivity);
        }
    }

    public void onBackPressed() {
        if (IftttSelectRelatePop.getInstance().isShowPop()) {
            IftttSelectRelatePop.getInstance().dismiss();
            return;
        }
        if (IftttSelectTriggerPop.getInstance().isShowPop()) {
            IftttSelectTriggerPop.getInstance().dismiss();
            return;
        }
        if (CommonBottomPop.getInstance().isShow()) {
            CommonBottomPop.getInstance().dismissPop();
            return;
        }
        if (CommonBottomSecondPop.getInstance().isShow()) {
            CommonBottomSecondPop.getInstance().dismissPop();
            return;
        }
        if (this.isEditScene) {
            isEditMsg(false);
            this.mTvSetting.setText("编辑");
            this.mAddRelateLayout.setVisibility(4);
            this.mIvTime.setVisibility(4);
            return;
        }
        if (this.mSceneDetailModel != null && this.mSceneDetailModel.getTrigger().getSource() != 0 && this.mActionListData.size() == 0) {
            OperationHintDialog.getInstance().showSelectDialog(this.mActivity, new HintDialogCallback() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttDetailActivity.3
                @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
                public void hintDialogListener(boolean isSuccess) {
                    if (isSuccess) {
                        DeviceIftttDetailActivity.this.finish();
                    }
                }
            }, "提示", "场景没有执行动作，确定返回吗?");
        } else {
            super.onBackPressed();
        }
    }

    private void isEditMsg(boolean isEdit) {
        if (this.mSceneDetailModel != null && this.mSceneDetailModel.getTrigger().getSource() != 0) {
            this.mEtSceneName.setFocusable(isEdit);
            this.mEtSceneName.setFocusableInTouchMode(isEdit);
            this.mEtSceneName.setEnabled(isEdit);
        }
        this.isEditScene = isEdit;
        this.mRelateAdapter.setShow(isEdit);
        this.mRelateAdapter.notifyDataSetChanged();
        SoftInputManager.hidenSoftInputFromWindow(this.mIvBack);
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

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonSecondPopCallback
    public void commonPopOneClick() {
        chooseDelay(false, 0);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonSecondPopCallback
    public void commonPopTwoClick() {
        IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, this.mTriggerId, this);
        IftttSelectRelatePop.getInstance().getRelateDeviceList(this.mActivity);
    }

    public void chooseDelay(final boolean isUpdate, final int position) {
        PickerViewHelper.timePiker(this.mActivity, Constants.MAIN_VERSION_TAG, new OnTimeSelectListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttDetailActivity.4
            public void onTimeSelect(Date date, View v) {
                String desc;
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                String dateString = format.format(date);
                String min = dateString.substring(0, 2);
                String sed = dateString.substring(3);
                int totalSed = (Integer.valueOf(min).intValue() * 60) + Integer.valueOf(sed).intValue();
                if (Integer.valueOf(min).intValue() == 0) {
                    desc = sed + "秒";
                } else {
                    desc = min + "分" + (Integer.valueOf(sed).intValue() == 0 ? Constants.MAIN_VERSION_TAG : sed + "秒");
                }
                RelateActionModel relateModel = new RelateActionModel();
                relateModel.setTransactionId(Constants.MAIN_VERSION_TAG);
                relateModel.setActionDesc(desc + "后");
                relateModel.setActionIcon(Constants.MAIN_VERSION_TAG);
                relateModel.setActionType("delay");
                relateModel.setActionValue(totalSed + Constants.MAIN_VERSION_TAG);
                XcLogger.i("data", "min:" + min + ",sed:" + sed);
                if (totalSed == 0) {
                    ToastUtils.showShort(DeviceIftttDetailActivity.this.mActivity, "延时不能为0秒,请重新设置");
                } else if (isUpdate) {
                    relateModel.setActionId(((RelateActionModel) DeviceIftttDetailActivity.this.mActionListData.get(position)).getActionId());
                    IftttHttpManager.updateRelate(DeviceIftttDetailActivity.this.mActivity, position, DeviceIftttDetailActivity.this.mTriggerId, relateModel, DeviceIftttDetailActivity.this);
                } else {
                    IftttHttpManager.addRelate(DeviceIftttDetailActivity.this.mActivity, DeviceIftttDetailActivity.this.mTriggerId, relateModel, DeviceIftttDetailActivity.this);
                }
            }
        });
    }
}
