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
import com.xiaocong.smarthome.httplib.model.scene.RelateActionModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDeviceModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneParameterModel;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemClickListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class IftttSelectRelatePop implements CommonTypeCallback {
    private IftttOperationCallback mCallback;
    private Context mContext;
    private PopupWindow mIftttSelectDevicePopup;
    private LinearLayout mLlSbLayout;
    private IftttSelectPopPagerAdapter mPagerAdapter;
    private List<ParameterValueModel> mParaValues;
    private ScenePopDeviceAdapter mPopDeviceAdapter;
    private ScenePopParameterAdapter mPopParaAdapter;
    private ScenePopParaValueAdapter mPopParaValueAdapter;
    private int mPositionTag;
    private ProgressTextBar mProTextBar;
    private RelateActionModel mRelateModel;
    private RelativeLayout mRlRvLayout;
    private RecyclerView mRvPopDevice;
    private RecyclerView mRvPopParameter;
    private RecyclerView mRvPopValue;
    private TabLayout mTabCompare;
    private TabLayout mTabSelect;
    private List<String> mTitles;
    private String mTriggerId;
    private TextView mTvCommit;
    private TextView mTvCompareHint;
    private TextView mTvTitle;
    private ParameterValueNumModel mValueNumModel;
    private ViewPager mVpSelect;

    private static class IftttSelectPopHolder {
        public static final IftttSelectRelatePop helperHolder = new IftttSelectRelatePop();
    }

    public static IftttSelectRelatePop getInstance() {
        return IftttSelectPopHolder.helperHolder;
    }

    public void selectSceneOrDevice(Context context, View view, String triggerId, IftttOperationCallback callback) {
        this.mPositionTag = -1;
        View popView = LayoutInflater.from(context).inflate(R.layout.pop_scene_add_device_layout, (ViewGroup) null);
        this.mRvPopDevice = (RecyclerView) popView.findViewById(R.id.rv_scene_pop_device);
        this.mRvPopParameter = (RecyclerView) popView.findViewById(R.id.rv_scene_pop_parameter);
        this.mRvPopValue = (RecyclerView) popView.findViewById(R.id.rv_scene_pop_parameter_value);
        this.mTvTitle = (TextView) popView.findViewById(R.id.tv_select_pop_title);
        this.mTvCommit = (TextView) popView.findViewById(R.id.tv_select_pop_commit);
        this.mTvCompareHint = (TextView) popView.findViewById(R.id.tv_compare_scene_pop_hint);
        this.mRlRvLayout = (RelativeLayout) popView.findViewById(R.id.rl_pop_rv_layout);
        this.mLlSbLayout = (LinearLayout) popView.findViewById(R.id.ll_pop_seekbar_layout);
        this.mProTextBar = (ProgressTextBar) popView.findViewById(R.id.tv_pro_pop_sbar_schedule);
        this.mTabSelect = (TabLayout) popView.findViewById(R.id.tl_select_scene_pop_layout);
        this.mTabCompare = (TabLayout) popView.findViewById(R.id.tl_compare_scene_pop_layout);
        this.mTitles = new ArrayList();
        this.mTitles.add("请设置");
        this.mVpSelect = (ViewPager) popView.findViewById(R.id.vp_select_scene_pop_layout);
        this.mPagerAdapter = new IftttSelectPopPagerAdapter(this, this.mTitles);
        this.mVpSelect.setAdapter(this.mPagerAdapter);
        this.mTabSelect.setupWithViewPager(this.mVpSelect);
        this.mVpSelect.setCurrentItem(0);
        popView.findViewById(R.id.select_outside_pop_view).setOnClickListener(IftttSelectRelatePop$$Lambda$1.lambdaFactory$(this));
        this.mIftttSelectDevicePopup = new PopupWindow(popView, -1, -2);
        this.mIftttSelectDevicePopup.setAnimationStyle(R.style.ActionSheetDialogStyle);
        this.mIftttSelectDevicePopup.setBackgroundDrawable(new BitmapDrawable());
        this.mIftttSelectDevicePopup.setOutsideTouchable(true);
        this.mIftttSelectDevicePopup.showAtLocation(view, 83, 0, 0);
        this.mCallback = callback;
        this.mTriggerId = triggerId;
        this.mContext = context;
        initAdapter(context);
        onBack(popView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectSceneOrDevice$0(View v) {
        this.mIftttSelectDevicePopup.dismiss();
    }

    private void onBack(View popView) {
        popView.setFocusable(true);
        popView.setFocusableInTouchMode(true);
        popView.setOnKeyListener(new View.OnKeyListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop.1
            @Override // android.view.View.OnKeyListener
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                IftttSelectRelatePop.this.dismiss();
                return true;
            }
        });
    }

    public boolean isShowPop() {
        return this.mIftttSelectDevicePopup != null && this.mIftttSelectDevicePopup.isShowing();
    }

    public void dismiss() {
        if (this.mIftttSelectDevicePopup.isShowing()) {
            this.mIftttSelectDevicePopup.dismiss();
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

    private void initPopListener(final Context context, final boolean isDevice) {
        tabItemClick(1);
        this.mTabSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop.2
            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                XcLogger.e("mTabSelect", "onTabSelected--" + IftttSelectRelatePop.this.mTabSelect.getSelectedTabPosition());
                if (tab.getPosition() == 0) {
                    IftttSelectRelatePop.this.mRvPopDevice.setVisibility(0);
                    IftttSelectRelatePop.this.mRvPopParameter.setVisibility(8);
                    IftttSelectRelatePop.this.mRvPopValue.setVisibility(8);
                    IftttSelectRelatePop.this.mLlSbLayout.setVisibility(8);
                    IftttSelectRelatePop.this.mRlRvLayout.setVisibility(0);
                    if (IftttSelectRelatePop.this.mPopDeviceAdapter.getData() == null || IftttSelectRelatePop.this.mPopDeviceAdapter.getData().size() == 0) {
                        IftttHttpManager.requestRelateDevices(context, IftttSelectRelatePop.this.mPopDeviceAdapter);
                        return;
                    }
                    return;
                }
                if (tab.getPosition() == 1) {
                    if (IftttSelectRelatePop.this.mRelateModel != null && !TextUtils.isEmpty(IftttSelectRelatePop.this.mRelateModel.getTransactionId())) {
                        IftttHttpManager.requestRelateDeviceParamter(context, IftttSelectRelatePop.this.mPopParaAdapter, IftttSelectRelatePop.this.mRelateModel.getTransactionId());
                    }
                    IftttSelectRelatePop.this.mRvPopDevice.setVisibility(8);
                    IftttSelectRelatePop.this.mRvPopParameter.setVisibility(0);
                    IftttSelectRelatePop.this.mRvPopValue.setVisibility(8);
                    IftttSelectRelatePop.this.mLlSbLayout.setVisibility(8);
                    IftttSelectRelatePop.this.mRlRvLayout.setVisibility(0);
                    return;
                }
                if (tab.getPosition() == 2) {
                    IftttSelectRelatePop.this.mRvPopDevice.setVisibility(8);
                    IftttSelectRelatePop.this.mRvPopParameter.setVisibility(8);
                    IftttSelectRelatePop.this.mRvPopValue.setVisibility(0);
                }
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        this.mRvPopDevice.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop.3
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                SceneDeviceModel devicesModel = (SceneDeviceModel) IftttSelectRelatePop.this.mPopDeviceAdapter.getData().get(position);
                if (devicesModel != null) {
                    if (isDevice) {
                        IftttSelectRelatePop.this.mRelateModel.setTransactionId(devicesModel.getDeviceId());
                        IftttSelectRelatePop.this.mRelateModel.setDeviceName(devicesModel.getDeviceName());
                        IftttSelectRelatePop.this.mRelateModel.setActionIcon(devicesModel.getProductImage());
                        IftttSelectRelatePop.this.mRelateModel.setActionType("device");
                        IftttSelectRelatePop.this.mPagerAdapter.setPageTitle(0, ((SceneDeviceModel) IftttSelectRelatePop.this.mPopDeviceAdapter.getData().get(position)).getDeviceName());
                        IftttSelectRelatePop.this.mPagerAdapter.addPageTitle("请设置");
                        IftttSelectRelatePop.this.mVpSelect.setCurrentItem(1);
                        return;
                    }
                    IftttSelectRelatePop.this.mRelateModel.setTransactionId(devicesModel.getDeviceId());
                    IftttSelectRelatePop.this.mRelateModel.setActionDesc(devicesModel.getDeviceName());
                    IftttSelectRelatePop.this.mRelateModel.setActionIcon(devicesModel.getProductImage());
                    IftttSelectRelatePop.this.mRelateModel.setActionType("scene");
                    if (TextUtils.isEmpty(IftttSelectRelatePop.this.mTriggerId)) {
                        IftttSelectRelatePop.this.mCallback.addRelateCallback(IftttSelectRelatePop.this.mRelateModel);
                    } else if (IftttSelectRelatePop.this.mTriggerId.equals("createTriggerId")) {
                        if (IftttSelectRelatePop.this.mPositionTag >= 0) {
                            IftttSelectRelatePop.this.mCallback.updateRelateCallback(IftttSelectRelatePop.this.mPositionTag, IftttSelectRelatePop.this.mRelateModel);
                        } else {
                            IftttSelectRelatePop.this.mCallback.addRelateCallback(IftttSelectRelatePop.this.mRelateModel);
                        }
                    } else if (IftttSelectRelatePop.this.mPositionTag >= 0) {
                        IftttHttpManager.updateRelate(context, IftttSelectRelatePop.this.mPositionTag, IftttSelectRelatePop.this.mTriggerId, IftttSelectRelatePop.this.mRelateModel, IftttSelectRelatePop.this.mCallback);
                    } else {
                        IftttHttpManager.addRelate(context, IftttSelectRelatePop.this.mTriggerId, IftttSelectRelatePop.this.mRelateModel, IftttSelectRelatePop.this.mCallback);
                    }
                    IftttSelectRelatePop.this.mIftttSelectDevicePopup.dismiss();
                }
            }
        });
        this.mRvPopParameter.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop.4
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                SceneParameterModel.ParameterModel parameterModel = (SceneParameterModel.ParameterModel) IftttSelectRelatePop.this.mPopParaAdapter.getData().get(position);
                if (parameterModel != null) {
                    if (!TextUtils.isEmpty(parameterModel.getParameterValue())) {
                        String parameterValue = parameterModel.getParameterValue().replace("\\", Constants.MAIN_VERSION_TAG);
                        IftttSelectRelatePop.this.mRelateModel.setParameterKey(parameterModel.getParameterKey());
                        IftttSelectRelatePop.this.mRelateModel.setParameterName(parameterModel.getParameterName());
                        IftttSelectRelatePop.this.mRelateModel.setProductParameterId(parameterModel.getProductParameterId());
                        IftttSelectRelatePop.this.mRelateModel.setParameterType(parameterModel.getParameterType());
                        IftttSelectRelatePop.this.mRelateModel.setParameterValue(parameterValue);
                        IftttSelectRelatePop.this.initDeviceParameterValue(parameterModel.getShowType(), parameterValue);
                        IftttSelectRelatePop.this.mPagerAdapter.setPageTitle(1, parameterModel.getParameterName());
                        IftttSelectRelatePop.this.mPagerAdapter.addPageTitle("请设置");
                        IftttSelectRelatePop.this.mVpSelect.setCurrentItem(2);
                        return;
                    }
                    ToastUtils.showShort(context, "暂未获取到支持设备到参数值");
                }
            }
        });
        this.mRvPopValue.addOnItemTouchListener(new OnItemClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop.5
            public void onSimpleItemClick(XcBaseRecyclerAdapter adapter, View view, int position) {
                ParameterValueModel valueModel = (ParameterValueModel) IftttSelectRelatePop.this.mPopParaValueAdapter.getData().get(position);
                if (valueModel != null) {
                    IftttSelectRelatePop.this.mRelateModel.setActionValue(valueModel.getVal());
                    IftttSelectRelatePop.this.mRelateModel.setActionDesc(IftttSelectRelatePop.this.mRelateModel.getDeviceName() + "-" + IftttSelectRelatePop.this.mRelateModel.getParameterName() + "-" + valueModel.getDesc());
                    if (TextUtils.isEmpty(IftttSelectRelatePop.this.mTriggerId)) {
                        IftttSelectRelatePop.this.mCallback.addRelateCallback(IftttSelectRelatePop.this.mRelateModel);
                    } else if (IftttSelectRelatePop.this.mTriggerId.equals("createTriggerId")) {
                        if (IftttSelectRelatePop.this.mPositionTag >= 0) {
                            IftttSelectRelatePop.this.mCallback.updateRelateCallback(IftttSelectRelatePop.this.mPositionTag, IftttSelectRelatePop.this.mRelateModel);
                        } else {
                            IftttSelectRelatePop.this.mCallback.addRelateCallback(IftttSelectRelatePop.this.mRelateModel);
                        }
                    } else if (IftttSelectRelatePop.this.mPositionTag >= 0) {
                        IftttHttpManager.updateRelate(context, IftttSelectRelatePop.this.mPositionTag, IftttSelectRelatePop.this.mTriggerId, IftttSelectRelatePop.this.mRelateModel, IftttSelectRelatePop.this.mCallback);
                    } else {
                        IftttHttpManager.addRelate(context, IftttSelectRelatePop.this.mTriggerId, IftttSelectRelatePop.this.mRelateModel, IftttSelectRelatePop.this.mCallback);
                    }
                }
                IftttSelectRelatePop.this.mIftttSelectDevicePopup.dismiss();
            }
        });
        this.mProTextBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop.6
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && IftttSelectRelatePop.this.mValueNumModel != null) {
                    if (IftttSelectRelatePop.this.mValueNumModel.getMin() != 0) {
                        if (IftttSelectRelatePop.this.mValueNumModel.getMin() >= 0) {
                            if (IftttSelectRelatePop.this.mValueNumModel.getMin() > 0) {
                                XcLogger.e("progress", Integer.valueOf(((int) (IftttSelectRelatePop.this.mValueNumModel.getMin() / IftttSelectRelatePop.this.mValueNumModel.getSize())) + progress));
                                IftttSelectRelatePop.this.mProTextBar.setProgress(((int) (IftttSelectRelatePop.this.mValueNumModel.getMin() / IftttSelectRelatePop.this.mValueNumModel.getSize())) + progress);
                                return;
                            }
                            return;
                        }
                        XcLogger.e("progress", Integer.valueOf(progress - ((int) (IftttSelectRelatePop.this.mValueNumModel.getMin() / IftttSelectRelatePop.this.mValueNumModel.getSize()))));
                        IftttSelectRelatePop.this.mProTextBar.setProgress(progress);
                        return;
                    }
                    XcLogger.e("progress", Double.valueOf(((double) progress) / Math.pow(10.0d, IftttSelectRelatePop.this.mValueNumModel.getDoubleLength())));
                    IftttSelectRelatePop.this.mProTextBar.setProgress(progress);
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.mTvCommit.setOnClickListener(IftttSelectRelatePop$$Lambda$2.lambdaFactory$(this, context));
        this.mTabCompare.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop.7
            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    IftttSelectRelatePop.this.mTvCompareHint.setGravity(3);
                    IftttSelectRelatePop.this.mTvCompareHint.setText("小于当前数值");
                    IftttSelectRelatePop.this.mRelateModel.setActionType("lt");
                } else if (tab.getPosition() == 1) {
                    IftttSelectRelatePop.this.mTvCompareHint.setGravity(17);
                    IftttSelectRelatePop.this.mTvCompareHint.setText("等于当前数值");
                    IftttSelectRelatePop.this.mRelateModel.setActionType("eq");
                } else if (tab.getPosition() == 2) {
                    IftttSelectRelatePop.this.mTvCompareHint.setGravity(5);
                    IftttSelectRelatePop.this.mTvCompareHint.setText("大于当前数值");
                    IftttSelectRelatePop.this.mTvCompareHint.setPadding(0, 0, 5, 0);
                    IftttSelectRelatePop.this.mRelateModel.setActionType("gt");
                }
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override // android.support.design.widget.TabLayout.OnTabSelectedListener
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
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
        this.mRelateModel.setActionValue(String.valueOf(threshold));
        IftttHttpManager.addRelate(context, this.mTriggerId, this.mRelateModel, this.mCallback);
        this.mIftttSelectDevicePopup.dismiss();
    }

    public void getRelateDeviceList(Context context) {
        this.mTvTitle.setText("请选择响应设备和参数");
        this.mRelateModel = new RelateActionModel();
        initPopListener(context, true);
        IftttHttpManager.requestRelateDevices(context, this.mPopDeviceAdapter);
    }

    public void getRelateSceneList(Context context) {
        this.mTvTitle.setText("请选择响应场景");
        this.mRelateModel = new RelateActionModel();
        this.mPagerAdapter.notifyDataSetChanged();
        initPopListener(context, false);
        IftttHttpManager.requestIftttlList(context, "manual", this.mPopDeviceAdapter, this);
    }

    public void updateRelate(Context context, String actionId, int position) {
        this.mRelateModel = new RelateActionModel();
        this.mPositionTag = position;
        initPopListener(context, false);
        this.mTvTitle.setText("请选择响应场景");
        this.mRelateModel.setActionId(actionId);
        IftttHttpManager.requestIftttlList(context, "manual", this.mPopDeviceAdapter, this);
    }

    public void getRelateParameterValue(Context context, RelateActionModel actionModel, int position) {
        this.mTvTitle.setText("请选择响应设备和参数");
        this.mRelateModel = actionModel;
        this.mPositionTag = position;
        this.mPagerAdapter.setPageTitle(0, actionModel.getDeviceName());
        this.mPagerAdapter.addPageTitle(actionModel.getParameterName());
        this.mPagerAdapter.addPageTitle("请设置");
        this.mVpSelect.setCurrentItem(2);
        this.mPagerAdapter.notifyDataSetChanged();
        initPopListener(context, true);
        initDeviceParameterValue(actionModel.getShowType(), actionModel.getParameterValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initDeviceParameterValue(int showType, String parameterValue) {
        if (showType == 2) {
            this.mRlRvLayout.setVisibility(8);
            this.mValueNumModel = (ParameterValueNumModel) new Gson().fromJson(parameterValue, ParameterValueNumModel.class);
            if (this.mValueNumModel != null) {
                if (this.mValueNumModel.getSize() != 0.0f && this.mValueNumModel.getMin() != 0 && this.mValueNumModel.getMax() != 0) {
                    this.mTvCommit.setVisibility(0);
                    this.mLlSbLayout.setVisibility(0);
                    initProgress(this.mValueNumModel.getSize(), this.mValueNumModel.getMin(), this.mValueNumModel.getMax());
                    return;
                }
                ToastUtils.showShort(this.mContext, "设备参数不能为0");
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
        if (valueList == null || valueList.size() == 0) {
            ToastUtils.showShort(this.mContext, "为获取到设备参数");
            dismiss();
            return;
        }
        for (int i = 0; i < valueList.size(); i++) {
            Map<String, String> params = (Map) valueList.get(i);
            ParameterValueModel valueModel = new ParameterValueModel();
            valueModel.setVal(params.get("val"));
            valueModel.setDesc(params.get("desc"));
            this.mParaValues.add(valueModel);
        }
        this.mPopParaValueAdapter.setNewData(this.mParaValues);
        this.mPopDeviceAdapter.notifyDataSetChanged();
        XcLogger.i("parameterValue---", parameterValue);
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
            return;
        }
        if (min < 0) {
            this.mProTextBar.setMax((int) ((max / size) + Math.abs(min / size)));
            this.mProTextBar.setMinus(true);
            this.mProTextBar.setMinusSize(min);
        } else if (min > 0) {
            this.mProTextBar.setMax((int) ((max / size) - (min / size)));
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
                        view.setOnClickListener(new View.OnClickListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.prompt.popup.IftttSelectRelatePop.8
                            @Override // android.view.View.OnClickListener
                            public void onClick(View v) {
                                int position = ((Integer) view.getTag()).intValue();
                                XcLogger.w("onTabUnselected", "OnClickListener--" + position);
                                if (position == 0) {
                                    IftttSelectRelatePop.this.mPagerAdapter.removePageTitle(2);
                                    IftttSelectRelatePop.this.mPagerAdapter.removePageTitle(1);
                                } else if (position == 1) {
                                    IftttSelectRelatePop.this.mPagerAdapter.removePageTitle(2);
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
}
