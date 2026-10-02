package com.xiaocong.smarthome.pickerview;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.FrameLayout;
import com.xiaocong.smarthome.pickerview.builder.TimePickerBuilder;
import com.xiaocong.smarthome.pickerview.listener.OnTimeSelectListener;
import com.xiaocong.smarthome.pickerview.view.TimePickerView;
import com.xiaocong.smarthome.uilib.R;
import java.util.Calendar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PickerViewHelper {
    public static void timePiker(Context context, String title, OnTimeSelectListener listener) {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 1, 1, 0, 0, 0);
        TimePickerView pvTime = new TimePickerBuilder(context, listener).setType(new boolean[]{false, false, false, false, true, true}).isDialog(true).setTitleText(title).setDate(startDate).build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -2, 80);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);
                dialogWindow.setGravity(80);
            }
        }
        pvTime.show();
    }

    public static void showCustomTimePiker(Context context, String title, boolean[] type, Calendar startDate, OnTimeSelectListener listener) {
        if (type.length != 6) {
            type = new boolean[]{false, false, false, true, true, false};
        }
        TimePickerView pvTime = new TimePickerBuilder(context, listener).setType(type).isDialog(true).setTitleText(title).setDate(startDate).build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, -2, 80);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);
                dialogWindow.setGravity(80);
            }
        }
        pvTime.show();
    }
}
