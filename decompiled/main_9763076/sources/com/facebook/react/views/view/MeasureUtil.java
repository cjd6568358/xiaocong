package com.facebook.react.views.view;

import android.view.View;
import com.facebook.yoga.YogaMeasureMode;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MeasureUtil {
    public static int getMeasureSpec(float size, YogaMeasureMode mode) {
        if (mode == YogaMeasureMode.EXACTLY) {
            return View.MeasureSpec.makeMeasureSpec((int) size, 1073741824);
        }
        if (mode == YogaMeasureMode.AT_MOST) {
            return View.MeasureSpec.makeMeasureSpec((int) size, Integer.MIN_VALUE);
        }
        return View.MeasureSpec.makeMeasureSpec(0, 0);
    }
}
