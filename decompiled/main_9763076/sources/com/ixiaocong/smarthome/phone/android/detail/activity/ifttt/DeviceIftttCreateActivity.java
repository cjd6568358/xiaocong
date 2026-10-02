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
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.DeviceCreateIftttAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonSecondPopCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.scene.RelateActionModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDetailModel;
import com.xiaocong.smarthome.pickerview.PickerViewHelper;
import com.xiaocong.smarthome.pickerview.listener.OnTimeSelectListener;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceIftttCreateActivity extends XcBaseActivity implements View.OnClickListener, CommonPopCallback, CommonSecondPopCallback, HintDialogCallback, IftttOperationCallback {
    private LinearLayout mAddRelateLayout;
    private ConstraintLayout mClSetWorkday;
    private EditText mEtSceneName;
    private ImageView mIvBack;
    private ImageView mIvTiggerIcon;
    private ImageView mIvTime;
    private DeviceCreateIftttAdapter mRelateAdapter;
    private List<RelateActionModel> mRelateData;
    private RecyclerView mRvRelateIfttt;
    private SceneDetailModel.TriggerModel mTriggerModel;
    private TextView mTvCommit;
    private TextView mTvTime;
    private TextView mTvTitle;
    private TextView mTvTriggerDesc;

    protected int getLayoutId() {
        return R.layout.activity_ifttt_device_detail;
    }

    protected void initView() {
        this.mTvCommit = (TextView) $(R.id.right_titlebar_text);
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mIvTiggerIcon = (ImageView) $(R.id.iv_ifttt_detail_trigger_device_icon);
        this.mIvTime = (ImageView) $(R.id.iv_scene_detail_time);
        this.mTvTriggerDesc = (TextView) $(R.id.tv_ifttt_detail_trigger_device_desc);
        this.mTvTitle = (TextView) $(R.id.centertxt_titlebar);
        $(R.id.tv_scene_detail_time_hint).setVisibility(4);
        this.mTvTime = (TextView) $(R.id.tv_scene_detail_time);
        this.mRvRelateIfttt = (RecyclerView) $(R.id.rv_dev_ifttt_relate_list);
        this.mEtSceneName = (EditText) $(R.id.et_scene_detail_name);
        this.mClSetWorkday = (ConstraintLayout) $(R.id.cl_scene_detail_time);
        this.mRvRelateIfttt.setLayoutManager(new LinearLayoutManager(this.mActivity));
    }

    protected void initData() {
        this.mRelateData = new ArrayList();
        this.mTvTitle.setText("新建场景");
        this.mTvCommit.setText(getString(R.string.commit));
        this.mTvTriggerDesc.setText(getString(R.string.go_setting));
        this.mTvTriggerDesc.setTextColor(getResources().getColor(R.color.hint_blue));
        this.mEtSceneName.setFocusable(true);
        this.mEtSceneName.setFocusableInTouchMode(true);
        this.mEtSceneName.setEnabled(true);
        this.mTvTime.setText("请设置生效时间段");
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
        this.mRelateAdapter = new DeviceCreateIftttAdapter(this.mActivity);
        addRelateFooter();
        this.mRvRelateIfttt.setAdapter(this.mRelateAdapter);
    }

    private void addRelateFooter() {
        View footerView = View.inflate(this.mActivity, R.layout.scene_relate_footer_layout, null);
        this.mRelateAdapter.addFooterView(footerView);
        this.mAddRelateLayout = (LinearLayout) footerView.findViewById(R.id.ll_add_scene_relate_layout);
        this.mAddRelateLayout.setOnClickListener(this);
    }

    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(this);
        this.mTvCommit.setOnClickListener(this);
        this.mTvTriggerDesc.setOnClickListener(this);
        this.mClSetWorkday.setOnClickListener(this);
        this.mRvRelateIfttt.addOnItemTouchListener(new OnItemChildClickListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttCreateActivity.1
            public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_ifttt_edit_del_adapter /* 2131296499 */:
                        if (DeviceIftttCreateActivity.this.mRelateAdapter.getData() != null && DeviceIftttCreateActivity.this.mRelateAdapter.getData().size() > 0) {
                            DeviceIftttCreateActivity.this.mRelateAdapter.getData().remove(position);
                            DeviceIftttCreateActivity.this.mRelateAdapter.notifyDataSetChanged();
                            break;
                        }
                        break;
                    case R.id.rl_ifttt_relate_edit_layout /* 2131296693 */:
                        if (((RelateActionModel) DeviceIftttCreateActivity.this.mRelateAdapter.getData().get(position)).getActionType().equals("device")) {
                            IftttSelectRelatePop.getInstance().selectSceneOrDevice(DeviceIftttCreateActivity.this.mActivity, DeviceIftttCreateActivity.this.mIvBack, "createTriggerId", DeviceIftttCreateActivity.this);
                            IftttSelectRelatePop.getInstance().getRelateParameterValue(DeviceIftttCreateActivity.this.mActivity, (RelateActionModel) DeviceIftttCreateActivity.this.mRelateAdapter.getData().get(position), position);
                        } else if (!((RelateActionModel) DeviceIftttCreateActivity.this.mRelateAdapter.getData().get(position)).getActionType().equals("delay")) {
                            IftttSelectRelatePop.getInstance().selectSceneOrDevice(DeviceIftttCreateActivity.this.mActivity, DeviceIftttCreateActivity.this.mIvBack, "createTriggerId", DeviceIftttCreateActivity.this);
                            IftttSelectRelatePop.getInstance().updateRelate(DeviceIftttCreateActivity.this.mActivity, ((RelateActionModel) DeviceIftttCreateActivity.this.mRelateAdapter.getData().get(position)).getActionId(), position);
                        } else {
                            DeviceIftttCreateActivity.this.chooseDelay(true, position);
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
                if (this.mTriggerModel != null) {
                    if (this.mTriggerModel.getType().equals("manual")) {
                        ToastUtils.showShort(this.mActivity, "手动执行不支持时间段设置");
                    } else {
                        Intent intent = new Intent(this.mActivity, (Class<?>) WorkdayIftttSettingActivity.class);
                        intent.putExtra("workday", this.mTriggerModel.getWorkday());
                        intent.putExtra("startTime", this.mTriggerModel.getStartWorkTime());
                        intent.putExtra("stopTime", this.mTriggerModel.getStopWorkTime());
                        startActivityForResult(intent, 100);
                    }
                } else {
                    ToastUtils.showShort(this.mActivity, "请设置响应条件");
                }
                break;
            case R.id.left_titlebar_image /* 2131296560 */:
                if (this.mTriggerModel != null && this.mRelateData != null && this.mRelateData.size() > 0) {
                    OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "取消创建", "确定取消创建当前场景吗?");
                } else {
                    finish();
                }
                break;
            case R.id.ll_add_scene_relate_layout /* 2131296571 */:
                if (this.mTriggerModel != null) {
                    if (this.mRelateData.size() == 0) {
                        CommonBottomPop.getInstance().showCommonBootomPopup(this.mActivity, this, this.mIvBack, "执行设备", "执行场景");
                        CommonBottomPop.getInstance().setCommonFirstColor(getResources().getColor(R.color.master_color));
                        CommonBottomPop.getInstance().setCommonSecondColor(getResources().getColor(R.color.master_color));
                    } else if (this.mRelateData.get(0).getActionType().equals("device") || this.mRelateData.get(0).getActionType().equals("delay")) {
                        CommonBottomSecondPop.getInstance().showCommonBootomPopup(this.mActivity, this, this.mIvBack, "添加时间间隔", "添加执行设备");
                    } else {
                        IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, Constants.MAIN_VERSION_TAG, this);
                        IftttSelectRelatePop.getInstance().getRelateSceneList(this.mActivity);
                    }
                } else {
                    ToastUtils.showShort(this.mActivity, "请先设置触发动作");
                }
                break;
            case R.id.right_titlebar_text /* 2131296682 */:
                String sceneName = this.mEtSceneName.getText().toString().trim();
                boolean isDevice = false;
                if (this.mRelateData != null && this.mRelateData.size() > 0) {
                    if (this.mRelateData.get(0).getActionType().equals("device") || this.mRelateData.get(0).getActionType().equals("delay")) {
                        for (RelateActionModel relateActionModel : this.mRelateData) {
                            if (relateActionModel.getActionType().equals("device")) {
                                isDevice = true;
                            }
                        }
                    } else if (this.mRelateData.get(0).getActionType().equals("scene")) {
                        isDevice = true;
                    }
                }
                if (TextUtils.isEmpty(sceneName)) {
                    ToastUtils.showShort(this.mActivity, "请输入场景名称");
                } else if (this.mRelateData == null || this.mRelateData.size() == 0) {
                    ToastUtils.showShort(this.mActivity, "请设置执行动作");
                } else if (!isDevice) {
                    ToastUtils.showShort(this.mActivity, "执行动作不能全为时间间隔");
                } else if (this.mTriggerModel == null || TextUtils.isEmpty(this.mTriggerModel.getDeviceId())) {
                    ToastUtils.showShort(this.mActivity, "请设置触发条件");
                } else {
                    if (this.mTriggerModel.getType().equals("auto")) {
                        String time = this.mTvTime.getText().toString();
                        if (TextUtils.isEmpty(time) || time.contains("设置")) {
                            this.mTriggerModel.setStartWorkTime("00:00");
                            this.mTriggerModel.setStopWorkTime("23:59");
                            this.mTriggerModel.setWorkday("0,1,2,3,4,5,6");
                        }
                    }
                    this.mTriggerModel.setTriggerName(sceneName);
                    IftttHttpManager.createScene(this.mActivity, this.mTriggerModel, this.mRelateData);
                }
                break;
            case R.id.tv_ifttt_detail_trigger_device_desc /* 2131297001 */:
                SoftInputManager.hidenSoftInputFromWindow(this.mIvBack);
                IftttSelectTriggerPop.getInstance().showPop(this.mActivity, this.mIvBack, this, true);
                if (this.mTriggerModel == null) {
                    IftttSelectTriggerPop.getInstance().triggerManualType(this.mActivity, new SceneDetailModel.TriggerModel());
                } else if (this.mTriggerModel.getType().equals("manual")) {
                    IftttSelectTriggerPop.getInstance().triggerManualType(this.mActivity, this.mTriggerModel);
                } else {
                    IftttSelectTriggerPop.getInstance().triggerAotoType(this.mActivity, this.mTriggerModel);
                }
                break;
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback
    public void commonFirstClick() {
        CommonBottomSecondPop.getInstance().showCommonBootomPopup(this.mActivity, this, this.mIvBack, "添加时间间隔", "添加执行设备");
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback
    public void commonSecondClick() {
        IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, Constants.MAIN_VERSION_TAG, this);
        IftttSelectRelatePop.getInstance().getRelateSceneList(this.mActivity);
        CommonBottomPop.getInstance().dismissPop();
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonSecondPopCallback
    public void commonPopOneClick() {
        chooseDelay(false, 0);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonSecondPopCallback
    public void commonPopTwoClick() {
        IftttSelectRelatePop.getInstance().selectSceneOrDevice(this.mActivity, this.mIvBack, Constants.MAIN_VERSION_TAG, this);
        IftttSelectRelatePop.getInstance().getRelateDeviceList(this.mActivity);
        CommonBottomPop.getInstance().dismissPop();
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
        } else if (this.mTriggerModel != null && this.mRelateData != null && this.mRelateData.size() > 0) {
            OperationHintDialog.getInstance().showSelectDialog(this.mActivity, this, "取消创建", "确定取消创建当前场景吗?");
        } else {
            super.onBackPressed();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    public void hintDialogListener(boolean isSuccess) {
        if (isSuccess) {
            finish();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void deleteRelateCallback(boolean isSuccess, int position) {
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void updateTriggerCallback(int type, SceneDetailModel.TriggerModel triggerModel) {
        if (triggerModel != null) {
            this.mTriggerModel = triggerModel;
            this.mTvTriggerDesc.setText(triggerModel.getTriggerDesc());
            this.mTvTriggerDesc.setTextColor(getResources().getColor(R.color.master_text_color));
            this.mIvTiggerIcon.setVisibility(0);
            Glide.with(this.mActivity).load(triggerModel.getTriggerIcon()).placeholder(R.drawable.default_img_icon).into(this.mIvTiggerIcon);
            if (triggerModel.getType().equals("manual")) {
                this.mIvTime.setVisibility(4);
                this.mTvTime.setText("--/--");
                return;
            }
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
        }
    }

    public void chooseDelay(final boolean isUpdate, final int position) {
        PickerViewHelper.timePiker(this.mActivity, Constants.MAIN_VERSION_TAG, new OnTimeSelectListener() { // from class: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.DeviceIftttCreateActivity.2
            public void onTimeSelect(Date date, View v) {
                String desc;
                SimpleDateFormat format = new SimpleDateFormat("mm:ss");
                String dateString = format.format(date);
                String min = dateString.substring(0, 2);
                String sed = dateString.substring(3);
                if (Integer.valueOf(min).intValue() == 0) {
                    desc = sed + "秒";
                } else {
                    desc = min + "分" + (Integer.valueOf(sed).intValue() == 0 ? Constants.MAIN_VERSION_TAG : sed + "秒");
                }
                int totalSed = (Integer.valueOf(min).intValue() * 60) + Integer.valueOf(sed).intValue();
                RelateActionModel relateModel = new RelateActionModel();
                relateModel.setTransactionId(Constants.MAIN_VERSION_TAG);
                relateModel.setActionDesc(desc + "后");
                relateModel.setActionIcon(Constants.MAIN_VERSION_TAG);
                relateModel.setActionType("delay");
                relateModel.setActionValue(totalSed + Constants.MAIN_VERSION_TAG);
                XcLogger.i("data", "min:" + min + ",sed:" + sed);
                if (totalSed == 0) {
                    ToastUtils.showShort(DeviceIftttCreateActivity.this.mActivity, "延时不能为0秒,请重新设置");
                } else if (isUpdate) {
                    DeviceIftttCreateActivity.this.updateRelateCallback(position, relateModel);
                } else {
                    DeviceIftttCreateActivity.this.addRelateCallback(relateModel);
                }
            }
        });
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void updateRelateCallback(int position, RelateActionModel relateModel) {
        if (relateModel != null) {
            this.mRelateData.set(position, relateModel);
            this.mRelateAdapter.setNewData(this.mRelateData);
            this.mRelateAdapter.setShow(true);
            this.mRelateAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback
    public void addRelateCallback(RelateActionModel actionModel) {
        if (actionModel != null) {
            this.mRelateData.add(actionModel);
            this.mRelateAdapter.setNewData(this.mRelateData);
            this.mRelateAdapter.setShow(true);
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
            if (this.mTriggerModel != null) {
                this.mTriggerModel.setStartWorkTime(startTime);
                this.mTriggerModel.setStopWorkTime(stopTime);
                this.mTriggerModel.setWorkday(workday);
            }
        }
    }
}
