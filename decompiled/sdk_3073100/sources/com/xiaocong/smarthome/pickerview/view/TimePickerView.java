package com.xiaocong.smarthome.pickerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaocong.smarthome.pickerview.configure.PickerOptions;
import com.xiaocong.smarthome.pickerview.listener.ISelectTimeCallback;
import com.xiaocong.smarthome.uilib.R;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TimePickerView extends BasePickerView implements View.OnClickListener {
    private WheelTime wheelTime;

    public TimePickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        this.mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
        if (this.mPickerOptions.customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.pickerview_time, this.contentContainer);
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);
            Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
            Button btnCancel = (Button) findViewById(R.id.btnCancel);
            btnSubmit.setTag("submit");
            btnCancel.setTag("cancel");
            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
            btnSubmit.setText(TextUtils.isEmpty(this.mPickerOptions.textContentConfirm) ? context.getResources().getString(R.string.pickerview_submit) : this.mPickerOptions.textContentConfirm);
            btnCancel.setText(TextUtils.isEmpty(this.mPickerOptions.textContentCancel) ? context.getResources().getString(R.string.pickerview_cancel) : this.mPickerOptions.textContentCancel);
            tvTitle.setText(TextUtils.isEmpty(this.mPickerOptions.textContentTitle) ? "" : this.mPickerOptions.textContentTitle);
            btnSubmit.setTextColor(this.mPickerOptions.textColorConfirm);
            btnCancel.setTextColor(this.mPickerOptions.textColorCancel);
            tvTitle.setTextColor(this.mPickerOptions.textColorTitle);
            rv_top_bar.setBackgroundColor(this.mPickerOptions.bgColorTitle);
            btnSubmit.setTextSize(this.mPickerOptions.textSizeSubmitCancel);
            btnCancel.setTextSize(this.mPickerOptions.textSizeSubmitCancel);
            tvTitle.setTextSize(this.mPickerOptions.textSizeTitle);
        } else {
            this.mPickerOptions.customListener.customLayout(LayoutInflater.from(context).inflate(this.mPickerOptions.layoutRes, this.contentContainer));
        }
        LinearLayout timePickerView = (LinearLayout) findViewById(R.id.timepicker);
        timePickerView.setBackgroundColor(this.mPickerOptions.bgColorWheel);
        initWheelTime(timePickerView);
    }

    private void initWheelTime(LinearLayout timePickerView) {
        this.wheelTime = new WheelTime(timePickerView, this.mPickerOptions.type, this.mPickerOptions.textGravity, this.mPickerOptions.textSizeContent);
        if (this.mPickerOptions.timeSelectChangeListener != null) {
            this.wheelTime.setSelectChangeCallback(new ISelectTimeCallback() { // from class: com.xiaocong.smarthome.pickerview.view.TimePickerView.1
                @Override // com.xiaocong.smarthome.pickerview.listener.ISelectTimeCallback
                public void onTimeSelectChanged() {
                    try {
                        Date date = WheelTime.dateFormat.parse(TimePickerView.this.wheelTime.getTime());
                        TimePickerView.this.mPickerOptions.timeSelectChangeListener.onTimeSelectChanged(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        this.wheelTime.setLunarMode(this.mPickerOptions.isLunarCalendar);
        if (this.mPickerOptions.startYear != 0 && this.mPickerOptions.endYear != 0 && this.mPickerOptions.startYear <= this.mPickerOptions.endYear) {
            setRange();
        }
        if (this.mPickerOptions.startDate == null || this.mPickerOptions.endDate == null) {
            if (this.mPickerOptions.startDate != null) {
                if (this.mPickerOptions.startDate.get(1) < 1900) {
                    throw new IllegalArgumentException("The startDate can not as early as 1900");
                }
                setRangDate();
            } else {
                if (this.mPickerOptions.endDate != null && this.mPickerOptions.endDate.get(1) > 2100) {
                    throw new IllegalArgumentException("The endDate should not be later than 2100");
                }
                setRangDate();
            }
        } else {
            if (this.mPickerOptions.startDate.getTimeInMillis() > this.mPickerOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            }
            setRangDate();
        }
        setTime();
        this.wheelTime.setLabels(this.mPickerOptions.label_year, this.mPickerOptions.label_month, this.mPickerOptions.label_day, this.mPickerOptions.label_hours, this.mPickerOptions.label_minutes, this.mPickerOptions.label_seconds);
        this.wheelTime.setTextXOffset(this.mPickerOptions.x_offset_year, this.mPickerOptions.x_offset_month, this.mPickerOptions.x_offset_day, this.mPickerOptions.x_offset_hours, this.mPickerOptions.x_offset_minutes, this.mPickerOptions.x_offset_seconds);
        setOutSideCancelable(this.mPickerOptions.cancelable);
        this.wheelTime.setCyclic(this.mPickerOptions.cyclic);
        this.wheelTime.setDividerColor(this.mPickerOptions.dividerColor);
        this.wheelTime.setDividerType(this.mPickerOptions.dividerType);
        this.wheelTime.setLineSpacingMultiplier(this.mPickerOptions.lineSpacingMultiplier);
        this.wheelTime.setTextColorOut(this.mPickerOptions.textColorOut);
        this.wheelTime.setTextColorCenter(this.mPickerOptions.textColorCenter);
        this.wheelTime.isCenterLabel(this.mPickerOptions.isCenterLabel);
    }

    private void setRange() {
        this.wheelTime.setStartYear(this.mPickerOptions.startYear);
        this.wheelTime.setEndYear(this.mPickerOptions.endYear);
    }

    private void setRangDate() {
        this.wheelTime.setRangDate(this.mPickerOptions.startDate, this.mPickerOptions.endDate);
        initDefaultSelectedDate();
    }

    private void initDefaultSelectedDate() {
        if (this.mPickerOptions.startDate != null && this.mPickerOptions.endDate != null) {
            if (this.mPickerOptions.date == null || this.mPickerOptions.date.getTimeInMillis() < this.mPickerOptions.startDate.getTimeInMillis() || this.mPickerOptions.date.getTimeInMillis() > this.mPickerOptions.endDate.getTimeInMillis()) {
                this.mPickerOptions.date = this.mPickerOptions.startDate;
                return;
            }
            return;
        }
        if (this.mPickerOptions.startDate != null) {
            this.mPickerOptions.date = this.mPickerOptions.startDate;
        } else if (this.mPickerOptions.endDate != null) {
            this.mPickerOptions.date = this.mPickerOptions.endDate;
        }
    }

    private void setTime() {
        int year;
        int month;
        int day;
        int hours;
        int minute;
        int seconds;
        Calendar calendar = Calendar.getInstance();
        if (this.mPickerOptions.date == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            year = calendar.get(1);
            month = calendar.get(2);
            day = calendar.get(5);
            hours = calendar.get(11);
            minute = calendar.get(12);
            seconds = calendar.get(13);
        } else {
            year = this.mPickerOptions.date.get(1);
            month = this.mPickerOptions.date.get(2);
            day = this.mPickerOptions.date.get(5);
            hours = this.mPickerOptions.date.get(11);
            minute = this.mPickerOptions.date.get(12);
            seconds = this.mPickerOptions.date.get(13);
        }
        this.wheelTime.setPicker(year, month, day, hours, minute, seconds);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals("submit")) {
            returnData();
        }
        dismiss();
    }

    public void returnData() {
        if (this.mPickerOptions.timeSelectListener != null) {
            try {
                Date date = WheelTime.dateFormat.parse(this.wheelTime.getTime());
                this.mPickerOptions.timeSelectListener.onTimeSelect(date, this.clickView);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaocong.smarthome.pickerview.view.BasePickerView
    public boolean isDialog() {
        return this.mPickerOptions.isDialog;
    }
}
