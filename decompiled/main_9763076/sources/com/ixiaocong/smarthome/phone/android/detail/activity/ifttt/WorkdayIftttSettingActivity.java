package com.ixiaocong.smarthome.phone.android.detail.activity.ifttt;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.common.utils.SceneWorkdayUtils;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.adater.WorkdayWeekAdapter;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.base.XcBaseActivity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.WorkdayWeekModel;
import com.xiaocong.smarthome.recycleradapter.base.XcBaseRecyclerAdapter;
import com.xiaocong.smarthome.recycleradapter.base.listener.OnItemChildClickListener;
import com.xiaocong.smarthome.wheel.WheelView;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WorkdayIftttSettingActivity extends XcBaseActivity {
    private boolean isAllWeek = false;
    private CheckBox mCbAllWeek;
    private List<String> mHourData;
    private ImageView mIvBack;
    private List<String> mMinData;
    private RecyclerView mRvWeek;
    private String mStartTime;
    private String mStopTime;
    private TextView mTvMorrow;
    private TextView mTvSave;
    private WorkdayWeekAdapter mWeekAdapter;
    private List<Integer> mWeekData;
    private List<WorkdayWeekModel> mWeekList;
    private String mWorkday;
    private WheelView mWvStartHour;
    private WheelView mWvStartMin;
    private WheelView mWvStopHour;
    private WheelView mWvStopMin;

    protected int getLayoutId() {
        return R.layout.activity_ifttt_workday_setting;
    }

    protected void initView() {
        this.mTvSave = (TextView) $(R.id.right_titlebar_text);
        this.mTvMorrow = (TextView) $(R.id.tv_ifttt_workday_morrow);
        this.mIvBack = (ImageView) $(R.id.left_titlebar_image);
        this.mRvWeek = (RecyclerView) $(R.id.rv_workday_week);
        this.mRvWeek.setLayoutManager(new LinearLayoutManager(this.mActivity));
        this.mWvStartHour = $(R.id.wv_ifttt_start_hour);
        this.mWvStartMin = $(R.id.wv_ifttt_start_min);
        this.mWvStopHour = $(R.id.wv_ifttt_stop_hour);
        this.mWvStopMin = $(R.id.wv_ifttt_stop_min);
    }

    protected void initData() {
        this.mWorkday = getIntent().getStringExtra("workday");
        this.mStartTime = getIntent().getStringExtra("startTime");
        this.mStopTime = getIntent().getStringExtra("stopTime");
        this.mHourData = SceneWorkdayUtils.getHourList();
        this.mMinData = SceneWorkdayUtils.getMinList();
        this.mWeekList = SceneWorkdayUtils.getWeekList(this.mWorkday == null ? "0,1,2,3,4,5,6" : this.mWorkday);
        this.mWeekData = SceneWorkdayUtils.getWeekData(this.mWorkday == null ? "0,1,2,3,4,5,6" : this.mWorkday);
        if (TextUtils.isEmpty(this.mWorkday) || this.mWorkday.split(",").length == 7) {
            this.mCbAllWeek.setChecked(true);
            this.isAllWeek = true;
        } else {
            this.mCbAllWeek.setChecked(false);
        }
        this.mWeekAdapter.setNewData(this.mWeekList);
        this.mWeekAdapter.notifyDataSetChanged();
        initWheelSelect();
    }

    private void initWheelSelect() {
        try {
            this.mWvStartHour.setItems(this.mHourData, SceneWorkdayUtils.getHourIndex(0, this.mStartTime == null ? "00:00" : this.mStartTime));
            this.mWvStartMin.setItems(this.mMinData, SceneWorkdayUtils.getMinIndex(0, this.mStartTime == null ? "00:00" : this.mStartTime));
            this.mWvStopHour.setItems(this.mHourData, SceneWorkdayUtils.getHourIndex(1, this.mStopTime == null ? "23:59" : this.mStopTime));
            this.mWvStopMin.setItems(this.mMinData, SceneWorkdayUtils.getMinIndex(1, this.mStopTime == null ? "23:59" : this.mStopTime));
        } catch (Exception e) {
            e.printStackTrace();
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
        this.mWeekAdapter = new WorkdayWeekAdapter(this.mActivity);
        addHeader();
        this.mRvWeek.setAdapter(this.mWeekAdapter);
    }

    private void addHeader() {
        View headerView = View.inflate(this.mActivity, R.layout.adapter_workday_week, null);
        this.mWeekAdapter.addHeaderView(headerView);
        TextView tvHeaderName = (TextView) headerView.findViewById(R.id.tv_workday_week_name);
        tvHeaderName.setText("每天");
        this.mCbAllWeek = (CheckBox) headerView.findViewById(R.id.cb_workday_week_check);
        if (TextUtils.isEmpty(this.mWorkday)) {
            this.mCbAllWeek.setChecked(true);
        }
        this.mCbAllWeek.setOnCheckedChangeListener(WorkdayIftttSettingActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addHeader$0(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            this.mWeekData.clear();
            this.isAllWeek = true;
            for (int i = 0; i < this.mWeekList.size(); i++) {
                this.mWeekList.get(i).setChecked(true);
                this.mWeekData.add(Integer.valueOf(this.mWeekList.get(i).getWeekValue()));
            }
        } else if (this.isAllWeek) {
            this.mWeekData.clear();
            for (int i2 = 0; i2 < this.mWeekList.size(); i2++) {
                this.mWeekList.get(i2).setChecked(false);
            }
        }
        this.mWeekAdapter.notifyDataSetChanged();
        XcLogger.e("mCheckStates--all", JSON.toJSON(this.mWeekData).toString());
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
    public void addListener() {
        super.addListener();
        this.mIvBack.setOnClickListener(WorkdayIftttSettingActivity$$Lambda$2.lambdaFactory$(this));
        this.mTvSave.setOnClickListener(WorkdayIftttSettingActivity$$Lambda$3.lambdaFactory$(this));
        this.mRvWeek.addOnItemTouchListener(new AnonymousClass1());
        this.mWvStartHour.setOnItemSelectedListener(WorkdayIftttSettingActivity$$Lambda$4.lambdaFactory$(this));
        this.mWvStartMin.setOnItemSelectedListener(WorkdayIftttSettingActivity$$Lambda$5.lambdaFactory$(this));
        this.mWvStopHour.setOnItemSelectedListener(WorkdayIftttSettingActivity$$Lambda$6.lambdaFactory$(this));
        this.mWvStopMin.setOnItemSelectedListener(WorkdayIftttSettingActivity$$Lambda$7.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(View v) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$2(View v) {
        String startDate = this.mWvStartHour.getSelectedItem() + ":" + this.mWvStartMin.getSelectedItem();
        String stopDate = this.mWvStopHour.getSelectedItem() + ":" + this.mWvStopMin.getSelectedItem();
        if (startDate.equals(stopDate)) {
            ToastUtils.showShort(this.mActivity, "开始时间设置与结束时间冲突");
            return;
        }
        Collections.sort(this.mWeekData);
        String weekData = Constants.MAIN_VERSION_TAG;
        for (int i = 0; i < this.mWeekData.size(); i++) {
            if (i == this.mWeekData.size() - 1) {
                weekData = weekData + this.mWeekData.get(i);
            } else {
                weekData = weekData + this.mWeekData.get(i) + ",";
            }
        }
        if (TextUtils.isEmpty(weekData)) {
            ToastUtils.showShort(this.mActivity, "请设置互联执行重复周期");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("startTime", startDate);
        intent.putExtra("stopTime", stopDate);
        intent.putExtra("workday", weekData);
        setResult(100, intent);
        finish();
    }

    /* JADX INFO: renamed from: com.ixiaocong.smarthome.phone.android.detail.activity.ifttt.WorkdayIftttSettingActivity$1, reason: invalid class name */
    class AnonymousClass1 extends OnItemChildClickListener {
        AnonymousClass1() {
        }

        public void onSimpleItemChildClick(XcBaseRecyclerAdapter adapter, View view, int position) {
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_workday_week_check);
            checkBox.setOnCheckedChangeListener(WorkdayIftttSettingActivity$1$$Lambda$1.lambdaFactory$(this, position));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSimpleItemChildClick$0(int position, CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                if (!WorkdayIftttSettingActivity.this.mWeekData.contains(Integer.valueOf(((WorkdayWeekModel) WorkdayIftttSettingActivity.this.mWeekList.get(position)).getWeekValue()))) {
                    WorkdayIftttSettingActivity.this.mWeekData.add(Integer.valueOf(((WorkdayWeekModel) WorkdayIftttSettingActivity.this.mWeekList.get(position)).getWeekValue()));
                }
                if (WorkdayIftttSettingActivity.this.mWeekData.size() == WorkdayIftttSettingActivity.this.mWeekList.size()) {
                    WorkdayIftttSettingActivity.this.isAllWeek = true;
                    WorkdayIftttSettingActivity.this.mCbAllWeek.setChecked(true);
                }
            } else {
                if (WorkdayIftttSettingActivity.this.mWeekData != null && WorkdayIftttSettingActivity.this.mWeekData.size() > 0) {
                    WorkdayIftttSettingActivity.this.mWeekData.remove(WorkdayIftttSettingActivity.this.mWeekData.indexOf(Integer.valueOf(((WorkdayWeekModel) WorkdayIftttSettingActivity.this.mWeekList.get(position)).getWeekValue())));
                }
                if (WorkdayIftttSettingActivity.this.mWeekData.size() != WorkdayIftttSettingActivity.this.mWeekList.size()) {
                    WorkdayIftttSettingActivity.this.isAllWeek = false;
                    WorkdayIftttSettingActivity.this.mCbAllWeek.setChecked(false);
                }
            }
            ((WorkdayWeekModel) WorkdayIftttSettingActivity.this.mWeekList.get(position)).setChecked(isChecked);
            WorkdayIftttSettingActivity.this.mWeekAdapter.notifyDataSetChanged();
            XcLogger.e("mCheckStates", JSON.toJSON(WorkdayIftttSettingActivity.this.mWeekData).toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$3(int selectedIndex, String item) {
        String startTime = item + ":" + this.mWvStartMin.getSelectedItem();
        String stopTime = this.mWvStopHour.getSelectedItem() + ":" + this.mWvStopMin.getSelectedItem();
        XcLogger.e("WheelView", "mWvStartHour: " + startTime + "----" + stopTime);
        if (startTime.compareTo(stopTime) > 0) {
            this.mTvMorrow.setVisibility(0);
        } else {
            this.mTvMorrow.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$4(int selectedIndex, String item) {
        String startTime = this.mWvStartHour.getSelectedItem() + ":" + item;
        String stopTime = this.mWvStopHour.getSelectedItem() + ":" + this.mWvStopMin.getSelectedItem();
        XcLogger.e("WheelView", "mWvStartMin: " + startTime + "----" + stopTime);
        if (startTime.compareTo(stopTime) > 0) {
            this.mTvMorrow.setVisibility(0);
        } else {
            this.mTvMorrow.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$5(int selectedIndex, String item) {
        String startTime = this.mWvStartHour.getSelectedItem() + ":" + this.mWvStartMin.getSelectedItem();
        String stopTime = item + ":" + this.mWvStopMin.getSelectedItem();
        XcLogger.e("WheelView", "mWvStopHour: " + startTime + "----" + stopTime);
        if (startTime.compareTo(stopTime) > 0) {
            this.mTvMorrow.setVisibility(0);
        } else {
            this.mTvMorrow.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$6(int selectedIndex, String item) {
        String startTime = this.mWvStartHour.getSelectedItem() + ":" + this.mWvStartMin.getSelectedItem();
        String stopTime = this.mWvStopHour.getSelectedItem() + ":" + item;
        XcLogger.e("WheelView", "mWvStopMin: " + startTime + "----" + stopTime);
        if (startTime.compareTo(stopTime) > 0) {
            this.mTvMorrow.setVisibility(0);
        } else {
            this.mTvMorrow.setVisibility(4);
        }
    }
}
