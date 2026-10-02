package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ixiaocong.smarthome.phone.R;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.wheel.WheelView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CalendarSelcetDialog extends Dialog {
    private int mBeginYear;
    private Calendar mCalendar;
    private EditDialogCallback mCallback;
    private Context mContext;
    private int mCurYear;
    private String[] mDays;
    private List<String> mDaysList;
    private String[] mMonths;
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private WheelView mWvDay;
    private WheelView mWvMonth;
    private WheelView mWvYear;
    private List<String> mYears;

    public CalendarSelcetDialog(Context context, EditDialogCallback callback, int beginYear) {
        super(context, R.style.PromptDialog);
        this.mMonths = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        this.mDays = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        this.mBeginYear = 0;
        this.mContext = context;
        this.mBeginYear = beginYear;
        this.mCallback = callback;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.dialog_calendar_select_layout);
        setCanceledOnTouchOutside(false);
        initLayout();
    }

    private void initLayout() {
        this.mTvCancel = (TextView) findViewById(R.id.tv_calendar_select_cancel_dialog);
        this.mTvConfirm = (TextView) findViewById(R.id.tv_calendar_select_confirm_dialog);
        this.mWvDay = findViewById(R.id.wv_calendar_select_day_dalog);
        this.mWvYear = findViewById(R.id.wv_calendar_select_year_dialog);
        this.mWvMonth = findViewById(R.id.wv_calendar_select_month_dialog);
        initData();
        addListener();
    }

    private void initData() {
        initYearCalendar();
        this.mWvMonth.setItems(Arrays.asList(this.mMonths), this.mCalendar.get(2));
        initDayCalendar(this.mCalendar.get(5) - 1);
    }

    private void initYearCalendar() {
        this.mCalendar = Calendar.getInstance();
        this.mCurYear = this.mCalendar.get(1);
        this.mYears = new ArrayList();
        if (this.mBeginYear == 0 || this.mBeginYear == this.mCurYear) {
            this.mYears.add(this.mCurYear + Constants.MAIN_VERSION_TAG);
        } else if (this.mBeginYear > 0 && this.mBeginYear < this.mCurYear) {
            for (int i = this.mCurYear - this.mBeginYear; i >= 0; i--) {
                if (i == 0) {
                    this.mYears.add(this.mCurYear + Constants.MAIN_VERSION_TAG);
                } else {
                    this.mYears.add((this.mCurYear - i) + Constants.MAIN_VERSION_TAG);
                }
            }
        }
        if (this.mYears.size() > 0) {
            this.mWvYear.setItems(this.mYears, this.mYears.size() - 1);
        } else {
            ToastUtils.showShort(this.mContext, "获取时间失败");
            dismiss();
        }
    }

    public void initDayCalendar(int currentItem) {
        this.mCalendar.set(1, Integer.valueOf(this.mYears.get(this.mWvYear.getSelectedPosition())).intValue());
        this.mCalendar.set(2, Integer.valueOf(this.mMonths[this.mWvMonth.getSelectedPosition()]).intValue() - 1);
        this.mCalendar.set(5, 1);
        this.mCalendar.roll(5, -1);
        int maxDate = this.mCalendar.get(5);
        this.mDaysList = new ArrayList();
        for (int i = 1; i <= maxDate; i++) {
            if (i < 10) {
                this.mDaysList.add(PushConstants.PUSH_TYPE_NOTIFY + i);
            } else {
                this.mDaysList.add(i + Constants.MAIN_VERSION_TAG);
            }
        }
        this.mWvDay.setItems(this.mDaysList, currentItem);
    }

    private void addListener() {
        this.mTvCancel.setOnClickListener(CalendarSelcetDialog$$Lambda$1.lambdaFactory$(this));
        this.mTvConfirm.setOnClickListener(CalendarSelcetDialog$$Lambda$2.lambdaFactory$(this));
        this.mWvMonth.setOnItemSelectedListener(CalendarSelcetDialog$$Lambda$3.lambdaFactory$(this));
        this.mWvYear.setOnItemSelectedListener(CalendarSelcetDialog$$Lambda$4.lambdaFactory$(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$0(View v) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$1(View v) {
        this.mCallback.editMsgCallback(this.mYears.get(this.mWvYear.getSelectedPosition()) + "-" + this.mMonths[this.mWvMonth.getSelectedPosition()] + "-" + this.mDaysList.get(this.mWvDay.getSelectedPosition()));
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$2(int selectedIndex, String item) {
        initDayCalendar(this.mWvDay.getSelectedPosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addListener$3(int selectedIndex, String item) {
        initDayCalendar(this.mWvDay.getSelectedPosition());
    }
}
