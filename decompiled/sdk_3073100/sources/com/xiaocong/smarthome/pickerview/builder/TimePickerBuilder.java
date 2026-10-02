package com.xiaocong.smarthome.pickerview.builder;

import android.content.Context;
import com.xiaocong.smarthome.pickerview.configure.PickerOptions;
import com.xiaocong.smarthome.pickerview.listener.OnTimeSelectListener;
import com.xiaocong.smarthome.pickerview.view.TimePickerView;
import java.util.Calendar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TimePickerBuilder {
    private PickerOptions mPickerOptions = new PickerOptions(2);

    public TimePickerBuilder(Context context, OnTimeSelectListener listener) {
        this.mPickerOptions.context = context;
        this.mPickerOptions.timeSelectListener = listener;
    }

    public TimePickerBuilder setType(boolean[] type) {
        this.mPickerOptions.type = type;
        return this;
    }

    public TimePickerBuilder isDialog(boolean isDialog) {
        this.mPickerOptions.isDialog = isDialog;
        return this;
    }

    public TimePickerBuilder setTitleText(String textContentTitle) {
        this.mPickerOptions.textContentTitle = textContentTitle;
        return this;
    }

    public TimePickerBuilder setDate(Calendar date) {
        this.mPickerOptions.date = date;
        return this;
    }

    public TimePickerView build() {
        return new TimePickerView(this.mPickerOptions);
    }
}
