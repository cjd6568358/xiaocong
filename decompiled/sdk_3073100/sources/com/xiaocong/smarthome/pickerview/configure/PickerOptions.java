package com.xiaocong.smarthome.pickerview.configure;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ViewGroup;
import com.xiaocong.smarthome.pickerview.listener.CustomListener;
import com.xiaocong.smarthome.pickerview.listener.OnTimeSelectChangeListener;
import com.xiaocong.smarthome.pickerview.listener.OnTimeSelectListener;
import com.xiaocong.smarthome.uilib.R;
import com.xiaocong.smarthome.wheelview.view.WheelView;
import java.util.Calendar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PickerOptions {
    public Context context;
    public CustomListener customListener;
    public Calendar date;
    public ViewGroup decorView;
    public Calendar endDate;
    public int endYear;
    public boolean isDialog;
    public String label_day;
    public String label_hours;
    public String label_minutes;
    public String label_month;
    public String label_seconds;
    public String label_year;
    public int layoutRes;
    public Calendar startDate;
    public int startYear;
    public String textContentCancel;
    public String textContentConfirm;
    public String textContentTitle;
    public OnTimeSelectChangeListener timeSelectChangeListener;
    public OnTimeSelectListener timeSelectListener;
    public int x_offset_day;
    public int x_offset_hours;
    public int x_offset_minutes;
    public int x_offset_month;
    public int x_offset_seconds;
    public int x_offset_year;
    public boolean cyclic1 = false;
    public boolean cyclic2 = false;
    public boolean cyclic3 = false;
    public boolean isRestoreItem = false;
    public boolean[] type = {true, true, true, false, false, false};
    public boolean cyclic = false;
    public boolean isLunarCalendar = false;
    public int textGravity = 17;
    public int textColorConfirm = -14540254;
    public int textColorCancel = -14540254;
    public int textColorTitle = -16777216;
    public int bgColorWheel = -1;
    public int bgColorTitle = -657931;
    public int textSizeSubmitCancel = 17;
    public int textSizeTitle = 18;
    public int textSizeContent = 18;
    public int textColorOut = -5723992;
    public int textColorCenter = -14013910;
    public int dividerColor = -2763307;
    public int backgroundId = -1;
    public float lineSpacingMultiplier = 1.6f;
    public boolean cancelable = true;
    public boolean isCenterLabel = false;
    public Typeface font = Typeface.MONOSPACE;
    public WheelView.DividerType dividerType = WheelView.DividerType.FILL;

    public PickerOptions(int buildType) {
        if (buildType == 1) {
            this.layoutRes = R.layout.pickerview_options;
        } else {
            this.layoutRes = R.layout.pickerview_time;
        }
    }
}
