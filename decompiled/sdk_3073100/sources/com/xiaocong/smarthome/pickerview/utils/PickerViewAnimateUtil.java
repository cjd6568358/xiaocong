package com.xiaocong.smarthome.pickerview.utils;

import com.xiaocong.smarthome.uilib.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PickerViewAnimateUtil {
    public static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case 80:
                return isInAnimation ? R.anim.pickerview_slide_in_bottom : R.anim.pickerview_slide_out_bottom;
            default:
                return -1;
        }
    }
}
