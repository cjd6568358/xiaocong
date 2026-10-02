package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.manager.IftttHttpManager;
import com.ixiaocong.smarthome.phone.android.common.utils.StringUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.complete.view.ProgressTextBar;
import com.ixiaocong.smarthome.phone.android.detail.adater.IftttSelectPopPagerAdapter;
import com.ixiaocong.smarthome.phone.android.detail.adater.ScenePopDeviceAdapter;
import com.ixiaocong.smarthome.phone.android.detail.adater.ScenePopParaValueAdapter;
import com.ixiaocong.smarthome.phone.android.detail.adater.ScenePopParameterAdapter;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import com.ixiaocong.smarthome.phone.android.event.callback.IftttOperationCallback;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.ParameterValueModel;
import com.xiaocong.smarthome.httplib.model.ParameterValueNumModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDetailModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDeviceModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneParameterModel;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IftttSelectTriggerPop implements CommonTypeCallback {
    private IftttOperationCallback mCallback;
    private Context mContext;
    private PopupWindow mIftttTriggerPopup;
    private boolean mIsAppend;
    private LinearLayout mLlSbLayout;
    private IftttSelectPopPagerAdapter mPagerAdapter;
    private List<ParameterValueModel> mParaValues;
    private ScenePopDeviceAdapter mPopDeviceAdapter;
    private ScenePopParameterAdapter mPopParaAdapter;
    private ScenePopParaValueAdapter mPopParaValueAdapter;
    private ProgressTextBar mProTextBar;
    private RelativeLayout mRlRvLayout;
    private RecyclerView mRvPopDevice;
    private RecyclerView mRvPopParameter;
    private RecyclerView mRvPopValue;
    private TabLayout mTabCompare;
    private TabLayout mTabSelect;
    private List<String> mTitles;
    private SceneDetailModel.TriggerModel mTriggerModel;
    private TextView mTvCommit;
    private TextView mTvCompareHint;
    private TextView mTvTitle;
    private ParameterValueNumModel mValueNumModel;
    private ViewPager mVpSelect;

    public static IftttSelectTriggerPop getInstance() {
        return IftttSelectTriggerPopHolder.triggerPop;
    }

    public void showPop(Context context, View view, IftttOperationCallback callback, boolean isAppend) {
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_scene_add_device_layout, (ViewGroup) null);
        this.mRvPopDevice = (RecyclerView) popView.findViewById(R.id.rv_scene_pop_device);
        this.mRvPopParameter = (RecyclerView) popView.findViewById(R.id.rv_scene_pop_parameter);
        this.mRvPopValue = (RecyclerView) popView.findViewById(R.id.rv_scene_pop_parameter_value);
        this.mTvCompareHint = (TextView) popView.findViewById(R.id.tv_compare_scene_pop_hint);
        this.mTvTitle = (TextView) popView.findViewById(R.id.tv_select_pop_title);
        this.mTvCommit = (TextView) popView.findViewById(R.id.tv_select_pop_commit);
        this.mTvTitle.setText("请选择触发设备和执行参数");
        this.mRlRvLayout = (RelativeLayout) popView.findViewById(R.id.rl_pop_rv_layout);
        this.mLlSbLayout = (LinearLayout) popView.findViewById(R.id.ll_pop_seekbar_layout);
        this.mTabSelect = (TabLayout) popView.findViewById(R.id.tl_select_scene_pop_layout);
        this.mTabCompare = (TabLayout) popView.findViewById(R.id.tl_compare_scene_pop_layout);
        this.mTitles = new ArrayList();
        this.mTitles.add("请设置");
        this.mVpSelect = (ViewPager) popView.findViewById(R.id.vp_select_scene_pop_layout);
        this.mPagerAdapter = new IftttSelectPopPagerAdapter(this, this.mTitles);
        this.mVpSelect.setAdapter(this.mPagerAdapter);
        this.mTabSelect.setupWithViewPager(this.mVpSelect);
        this.mVpSelect.setCurrentItem(0);
        this.mProTextBar = (ProgressTextBar) popView.findViewById(R.id.tv_pro_pop_sbar_schedule);
        popView.findViewById(R.id.select_outside_pop_view).setOnClickListener(IftttSelectTriggerPop$$Lambda$1.lambdaFactory$(this));
        this.mIftttTriggerPopup = new PopupWindow(popView, -1, -2);
        this.mIftttTriggerPopup.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mIftttTriggerPopup.setBackgroundDrawable(new BitmapDrawable());
        this.mIftttTriggerPopup.setOutsideTouchable(true);
        this.mIftttTriggerPopup.showAtLocation(view, 83, 0, 0);
        this.mCallback = callback;
        this.mIsAppend = isAppend;
        this.mContext = context;
        initAdapter(context);
        initPopListener(context);
        onBack(popView);
        tabItemClick(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPop$0(View v) {
        this.mIftttTriggerPopup.dismiss();
    }

    private void onBack(View popView) {
        popView.setFocusable(true);
        popView.setFocusableInTouchMode(true);
        popView.setOnKeyListener(new View.OnKeyListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                IftttSelectTriggerPop.this.dismiss();
                return true;
            }
        });
    }

    public void triggerManualType(Context context, SceneDetailModel.TriggerModel triggerModel) {
        this.mTriggerModel = triggerModel;
        IftttHttpManager.requestTriggerDevices(context, this.mPopDeviceAdapter);
    }

    public void triggerAotoType(Context context, SceneDetailModel.TriggerModel triggerModel) {
        this.mTriggerModel = triggerModel;
        if (!TextUtils.isEmpty(triggerModel.getParameterValue())) {
            if (triggerModel.getShowType() == 2) {
                initDeviceParameterValue(triggerModel.getTriggerCondition(), triggerModel.getThreshold(), triggerModel.getParameterValue().replace("\\", Constants.MAIN_VERSION_TAG));
            } else {
                this.mRvPopDevice.setVisibility(4);
                this.mRvPopParameter.setVisibility(4);
                this.mRvPopValue.setVisibility(0);
                initDeviceParameterValue(triggerModel.getShowType(), triggerModel.getParameterValue());
            }
        } else {
            this.mRvPopDevice.setVisibility(0);
            this.mRvPopParameter.setVisibility(8);
            this.mRvPopValue.setVisibility(8);
            this.mLlSbLayout.setVisibility(8);
            this.mRlRvLayout.setVisibility(0);
            triggerManualType(context, triggerModel);
        }
        this.mPagerAdapter.setPageTitle(0, triggerModel.getDeviceName());
        this.mPagerAdapter.addPageTitle(triggerModel.getParameterName());
        this.mPagerAdapter.addPageTitle("请设置");
        this.mVpSelect.setCurrentItem(2);
        this.mPagerAdapter.notifyDataSetChanged();
        tabItemClick(1);
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
    private void initAdapter(Context context) {
        this.mPopDeviceAdapter = new ScenePopDeviceAdapter();
        this.mPopParaAdapter = new ScenePopParameterAdapter();
        this.mPopParaValueAdapter = new ScenePopParaValueAdapter();
        this.mRvPopDevice.setLayoutManager(new LinearLayoutManager(context));
        this.mRvPopDevice.setAdapter(this.mPopDeviceAdapter);
        this.mRvPopParameter.setLayoutManager(new LinearLayoutManager(context));
        this.mRvPopParameter.setAdapter(this.mPopParaAdapter);
        this.mRvPopValue.setLayoutManager(new LinearLayoutManager(context));
        this.mRvPopValue.setAdapter(this.mPopParaValueAdapter);
        this.mParaValues = new ArrayList();
    }

    private void initPopListener(final Context context) {
        this.mTabSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop.2
            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                XcLogger.e("mTabSelect", "onTabSelected--" + IftttSelectTriggerPop.this.mTabSelect.getSelectedTabPosition());
                if (tab.getPosition() == 0) {
                    IftttSelectTriggerPop.this.mRvPopDevice.setVisibility(0);
                    IftttSelectTriggerPop.this.mRvPopParameter.setVisibility(8);
                    IftttSelectTriggerPop.this.mRvPopValue.setVisibility(8);
                    IftttSelectTriggerPop.this.mLlSbLayout.setVisibility(8);
                    IftttSelectTriggerPop.this.mRlRvLayout.setVisibility(0);
                    if (IftttSelectTriggerPop.this.mPopDeviceAdapter.getData() == null || IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().size() == 0) {
                        IftttHttpManager.requestTriggerDevices(context, IftttSelectTriggerPop.this.mPopDeviceAdapter);
                        return;
                    }
                    return;
                }
                if (tab.getPosition() == 1 && !TextUtils.isEmpty(tab.getText())) {
                    IftttSelectTriggerPop.this.mRvPopDevice.setVisibility(8);
                    IftttSelectTriggerPop.this.mRvPopParameter.setVisibility(0);
                    IftttSelectTriggerPop.this.mRvPopValue.setVisibility(8);
                    IftttSelectTriggerPop.this.mLlSbLayout.setVisibility(8);
                    IftttSelectTriggerPop.this.mRlRvLayout.setVisibility(0);
                    if (IftttSelectTriggerPop.this.mTriggerModel != null && !TextUtils.isEmpty(IftttSelectTriggerPop.this.mTriggerModel.getDeviceId())) {
                        if (IftttSelectTriggerPop.this.mPopParaAdapter.getData() == null || IftttSelectTriggerPop.this.mPopParaAdapter.getData().size() == 0) {
                            IftttHttpManager.requestTriggerDeviceParamter(context, IftttSelectTriggerPop.this.mPopParaAdapter, IftttSelectTriggerPop.this.mTriggerModel.getDeviceId());
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (tab.getPosition() == 2 && !TextUtils.isEmpty(tab.getText())) {
                    if (IftttSelectTriggerPop.this.mTriggerModel == null || IftttSelectTriggerPop.this.mTriggerModel.getShowType() != 2) {
                        IftttSelectTriggerPop.this.mRvPopDevice.setVisibility(8);
                        IftttSelectTriggerPop.this.mRvPopParameter.setVisibility(8);
                        IftttSelectTriggerPop.this.mRvPopValue.setVisibility(0);
                    } else {
                        IftttSelectTriggerPop.this.mTvCommit.setVisibility(0);
                        IftttSelectTriggerPop.this.mLlSbLayout.setVisibility(0);
                        IftttSelectTriggerPop.this.mRlRvLayout.setVisibility(8);
                    }
                }
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
                XcLogger.w("onTabUnselected", "onTabUnselected--" + tab.getPosition());
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
                XcLogger.w("onTabUnselected", "onTabReselected--" + tab.getPosition());
            }
        });
        this.mRvPopDevice.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop.3
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                if (position != 0 || !((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getDeviceId().equals(PushConstants.PUSH_TYPE_NOTIFY)) {
                    IftttHttpManager.requestTriggerDeviceParamter(context, IftttSelectTriggerPop.this.mPopParaAdapter, ((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getDeviceId());
                    IftttSelectTriggerPop.this.mTriggerModel.setType("auto");
                    IftttSelectTriggerPop.this.mTriggerModel.setDeviceId(((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getDeviceId());
                    IftttSelectTriggerPop.this.mTriggerModel.setDeviceName(((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getDeviceName());
                    IftttSelectTriggerPop.this.mTriggerModel.setTriggerIcon(((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getProductImage());
                    IftttSelectTriggerPop.this.mPagerAdapter.setPageTitle(0, ((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getDeviceName());
                    IftttSelectTriggerPop.this.mPagerAdapter.addPageTitle("请设置");
                    IftttSelectTriggerPop.this.mVpSelect.setCurrentItem(1);
                    return;
                }
                IftttSelectTriggerPop.this.mTriggerModel.setType("manual");
                IftttSelectTriggerPop.this.mTriggerModel.setDeviceId(((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getDeviceId());
                IftttSelectTriggerPop.this.mTriggerModel.setDeviceName(((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getDeviceName());
                IftttSelectTriggerPop.this.mTriggerModel.setTriggerIcon(((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getProductImage());
                IftttSelectTriggerPop.this.mTriggerModel.setTriggerDesc(((SceneDeviceModel) IftttSelectTriggerPop.this.mPopDeviceAdapter.getData().get(position)).getDeviceName());
                if (IftttSelectTriggerPop.this.mIsAppend) {
                    IftttSelectTriggerPop.this.mCallback.updateTriggerCallback(0, IftttSelectTriggerPop.this.mTriggerModel);
                } else {
                    IftttHttpManager.updateTrigger(context, 1, IftttSelectTriggerPop.this.mTriggerModel, IftttSelectTriggerPop.this.mCallback);
                }
                IftttSelectTriggerPop.this.mIftttTriggerPopup.dismiss();
            }
        });
        this.mRvPopParameter.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop.4
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                SceneParameterModel.ParameterModel parameterModel = (SceneParameterModel.ParameterModel) IftttSelectTriggerPop.this.mPopParaAdapter.getItem(position);
                if (parameterModel != null) {
                    if (!TextUtils.isEmpty(parameterModel.getParameterValue())) {
                        String parameterValue = parameterModel.getParameterValue().replace("\\", Constants.MAIN_VERSION_TAG);
                        IftttSelectTriggerPop.this.initDeviceParameterValue(parameterModel.getShowType(), parameterValue);
                        IftttSelectTriggerPop.this.mTriggerModel.setParameterName(parameterModel.getParameterName());
                        IftttSelectTriggerPop.this.mTriggerModel.setParameterType(parameterModel.getParameterType());
                        IftttSelectTriggerPop.this.mTriggerModel.setParameterKey(parameterModel.getParameterKey());
                        IftttSelectTriggerPop.this.mTriggerModel.setParameterValue(parameterModel.getParameterValue());
                        IftttSelectTriggerPop.this.mTriggerModel.setProductParameterId(parameterModel.getProductParameterId());
                        IftttSelectTriggerPop.this.mTriggerModel.setShowType(parameterModel.getShowType());
                        IftttSelectTriggerPop.this.mPagerAdapter.setPageTitle(1, parameterModel.getParameterName());
                        IftttSelectTriggerPop.this.mPagerAdapter.addPageTitle("请设置");
                        IftttSelectTriggerPop.this.mVpSelect.setCurrentItem(2);
                        return;
                    }
                    ToastUtils.showShort(context, "暂未获取到支持设备到参数值");
                }
            }
        });
        this.mRvPopValue.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop.5
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                IftttSelectTriggerPop.this.mTriggerModel.setThreshold(((ParameterValueModel) IftttSelectTriggerPop.this.mPopParaValueAdapter.getData().get(position)).getVal());
                IftttSelectTriggerPop.this.mTriggerModel.setTriggerDesc(IftttSelectTriggerPop.this.mTriggerModel.getDeviceName() + "-" + IftttSelectTriggerPop.this.mTriggerModel.getParameterName() + "-" + ((ParameterValueModel) IftttSelectTriggerPop.this.mPopParaValueAdapter.getData().get(position)).getDesc());
                if (IftttSelectTriggerPop.this.mIsAppend) {
                    IftttSelectTriggerPop.this.mCallback.updateTriggerCallback(0, IftttSelectTriggerPop.this.mTriggerModel);
                } else {
                    IftttHttpManager.updateTrigger(context, 1, IftttSelectTriggerPop.this.mTriggerModel, IftttSelectTriggerPop.this.mCallback);
                }
                IftttSelectTriggerPop.this.mIftttTriggerPopup.dismiss();
            }
        });
        this.mProTextBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && IftttSelectTriggerPop.this.mValueNumModel != null) {
                    if (IftttSelectTriggerPop.this.mValueNumModel.getMin() != 0) {
                        if (IftttSelectTriggerPop.this.mValueNumModel.getMin() >= 0) {
                            if (IftttSelectTriggerPop.this.mValueNumModel.getMin() > 0) {
                                XcLogger.e("progress", Integer.valueOf(((int) (IftttSelectTriggerPop.this.mValueNumModel.getMin() / IftttSelectTriggerPop.this.mValueNumModel.getSize())) + progress));
                                IftttSelectTriggerPop.this.mProTextBar.setProgress(((int) (IftttSelectTriggerPop.this.mValueNumModel.getMin() / IftttSelectTriggerPop.this.mValueNumModel.getSize())) + progress);
                                return;
                            }
                            return;
                        }
                        XcLogger.e("progress", Integer.valueOf(progress - ((int) (IftttSelectTriggerPop.this.mValueNumModel.getMin() / IftttSelectTriggerPop.this.mValueNumModel.getSize()))));
                        IftttSelectTriggerPop.this.mProTextBar.setProgress(progress);
                        return;
                    }
                    XcLogger.e("progress", Double.valueOf(((double) progress) / Math.pow(10.0d, IftttSelectTriggerPop.this.mValueNumModel.getDoubleLength())));
                    IftttSelectTriggerPop.this.mProTextBar.setProgress(progress);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mTvCommit.setOnClickListener(IftttSelectTriggerPop$$Lambda$4.lambdaFactory$(this, context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initPopListener$1(Context context, View v) {
        float threshold;
        if (this.mProTextBar.isMinus()) {
            if (this.mProTextBar.getProgressSize() == 0) {
                threshold = this.mProTextBar.getProgress() + this.mProTextBar.getMinusSize();
            } else {
                double progress = (((double) this.mProTextBar.getProgress()) / Math.pow(10.0d, this.mProTextBar.getProgressSize())) + ((double) this.mProTextBar.getMinusSize());
                threshold = (float) progress;
            }
        } else if (this.mProTextBar.getProgressSize() == 0) {
            threshold = this.mProTextBar.getProgress();
        } else {
            threshold = (float) (((double) this.mProTextBar.getProgress()) / Math.pow(10.0d, this.mProTextBar.getProgressSize()));
        }
        this.mTriggerModel.setThreshold(String.valueOf(threshold));
        String condition = Constants.MAIN_VERSION_TAG;
        if (this.mTabCompare.getSelectedTabPosition() == 0) {
            this.mTriggerModel.setTriggerCondition("lt");
            condition = "小于";
        } else if (this.mTabCompare.getSelectedTabPosition() == 1) {
            this.mTriggerModel.setTriggerCondition("eq");
            condition = "等于";
        } else if (this.mTabCompare.getSelectedTabPosition() == 2) {
            this.mTriggerModel.setTriggerCondition("gt");
            condition = "大于";
        }
        this.mTriggerModel.setTriggerDesc(this.mTriggerModel.getDeviceName() + "-" + this.mTriggerModel.getParameterName() + "-" + condition + this.mTriggerModel.getThreshold() + this.mProTextBar.getUnit());
        if (this.mIsAppend) {
            this.mCallback.updateTriggerCallback(0, this.mTriggerModel);
        } else {
            IftttHttpManager.updateTrigger(context, 1, this.mTriggerModel, this.mCallback);
        }
        this.mIftttTriggerPopup.dismiss();
    }

    public boolean isShowPop() {
        return this.mIftttTriggerPopup != null && this.mIftttTriggerPopup.isShowing();
    }

    public void dismiss() {
        if (this.mIftttTriggerPopup != null && this.mIftttTriggerPopup.isShowing()) {
            this.mIftttTriggerPopup.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDeviceParameterValue(int showType, String parameterValue) {
        if (showType == 2) {
            this.mValueNumModel = (ParameterValueNumModel) new Gson().fromJson(parameterValue, ParameterValueNumModel.class);
            if (this.mValueNumModel != null) {
                if (this.mValueNumModel.getSize() == 0.0f) {
                    ToastUtils.showShort(this.mContext, "获取设备参数选项失败");
                    return;
                }
                this.mRlRvLayout.setVisibility(8);
                this.mTvCommit.setVisibility(0);
                this.mLlSbLayout.setVisibility(0);
                initProgress(this.mValueNumModel.getSize(), this.mValueNumModel.getMin(), this.mValueNumModel.getMax());
                this.mProTextBar.setUnit(this.mValueNumModel.getUnit());
                this.mProTextBar.setProgress(0);
                return;
            }
            return;
        }
        this.mLlSbLayout.setVisibility(8);
        this.mTvCommit.setVisibility(4);
        this.mRlRvLayout.setVisibility(0);
        List valueList = null;
        try {
            valueList = (List) new Gson().fromJson(parameterValue, List.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        this.mParaValues.clear();
        for (int i = 0; i < valueList.size(); i++) {
            Map<String, String> params = (Map) valueList.get(i);
            ParameterValueModel valueModel = new ParameterValueModel();
            valueModel.setVal(params.get("val"));
            valueModel.setDesc(params.get("desc"));
            this.mParaValues.add(valueModel);
        }
        this.mPopParaValueAdapter.setNewData(this.mParaValues);
        this.mPopDeviceAdapter.notifyDataSetChanged();
        this.mTriggerModel.setTriggerCondition("eq");
    }

    private void initProgress(float size, int min, int max) {
        int doubleLength = StringUtils.doublePointLength(String.valueOf(size));
        if (doubleLength == 1) {
            String sizeStr = String.valueOf(size);
            String pointStr = sizeStr.substring(sizeStr.length() - 1, sizeStr.length());
            if (pointStr.equals(PushConstants.PUSH_TYPE_NOTIFY)) {
                this.mProTextBar.setProgressSize(0);
            } else {
                this.mValueNumModel.setDoubleLength(1);
                this.mProTextBar.setProgressSize(1);
            }
        } else {
            this.mValueNumModel.setDoubleLength(doubleLength);
            this.mProTextBar.setProgressSize(doubleLength);
        }
        if (min == 0) {
            this.mProTextBar.setMax((int) (max / size));
            this.mProTextBar.setMinus(false);
        } else if (min < 0) {
            this.mProTextBar.setMax((int) ((max / size) + Math.abs(min / size)));
            this.mProTextBar.setMinus(true);
            this.mProTextBar.setMinusSize(min);
        } else if (min > 0) {
            this.mProTextBar.setMax((int) ((max / size) - (min / size)));
            this.mProTextBar.setMinus(false);
        }
    }

    private void initDeviceParameterValue(String condition, String threshold, String parameterValue) {
        this.mRlRvLayout.setVisibility(8);
        this.mTvCommit.setVisibility(0);
        this.mLlSbLayout.setVisibility(0);
        this.mTriggerModel.setTriggerCondition(condition);
        this.mValueNumModel = (ParameterValueNumModel) new Gson().fromJson(parameterValue, ParameterValueNumModel.class);
        if (this.mValueNumModel != null) {
            initProgress(this.mValueNumModel.getSize(), this.mValueNumModel.getMin(), this.mValueNumModel.getMax());
            this.mProTextBar.setUnit(this.mValueNumModel.getUnit());
            try {
                float progress = (float) (((double) Float.valueOf(threshold).floatValue()) * Math.pow(10.0d, this.mValueNumModel.getDoubleLength()));
                this.mProTextBar.setProgress((int) progress);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (condition.equals("eq")) {
            this.mTabCompare.getTabAt(1).select();
        } else if (condition.equals("gt")) {
            this.mTabCompare.getTabAt(2).select();
        } else if (condition.equals("lt")) {
            this.mTabCompare.getTabAt(0).select();
        }
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    public void resultTypeCalllback(int type) {
        tabItemClick(type);
    }

    private void tabItemClick(int type) {
        TabLayout.Tab tab;
        if (type == 1) {
            for (int i = 0; i < this.mTabSelect.getTabCount() && (tab = this.mTabSelect.getTabAt(i)) != null; i++) {
                try {
                    Field field = tab.getClass().getDeclaredField("mView");
                    field.setAccessible(true);
                    final View view = (View) field.get(tab);
                    if (view != null) {
                        view.setTag(Integer.valueOf(i));
                        view.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectTriggerPop.7
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v) {
                                int position = ((Integer) view.getTag()).intValue();
                                XcLogger.w("onTabUnselected", "OnClickListener--" + position);
                                if (position == 0) {
                                    IftttSelectTriggerPop.this.mPagerAdapter.removePageTitle(2);
                                    IftttSelectTriggerPop.this.mPagerAdapter.removePageTitle(1);
                                } else if (position == 1) {
                                    IftttSelectTriggerPop.this.mPagerAdapter.removePageTitle(2);
                                }
                            }
                        });
                    } else {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class IftttSelectTriggerPopHolder {
        private static final IftttSelectTriggerPop triggerPop = new IftttSelectTriggerPop();
    }
}
