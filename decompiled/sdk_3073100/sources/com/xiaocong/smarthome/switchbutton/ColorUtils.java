package com.xiaocong.smarthome.switchbutton;

import android.R;
import android.content.res.ColorStateList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ColorUtils {
    static ColorStateList generateThumbColorWithTintColor(int tintColor) {
        int[][] states = {new int[]{-16842910, R.attr.state_checked}, new int[]{-16842910}, new int[]{R.attr.state_pressed, -16842912}, new int[]{R.attr.state_pressed, R.attr.state_checked}, new int[]{R.attr.state_checked}, new int[]{-16842912}};
        int[] colors = {tintColor - (-1442840576), -4539718, tintColor - (-1728053248), tintColor - (-1728053248), (-16777216) | tintColor, -1118482};
        return new ColorStateList(states, colors);
    }

    static ColorStateList generateBackColorWithTintColor(int tintColor) {
        int[][] states = {new int[]{-16842910, R.attr.state_checked}, new int[]{-16842910}, new int[]{R.attr.state_checked, R.attr.state_pressed}, new int[]{-16842912, R.attr.state_pressed}, new int[]{R.attr.state_checked}, new int[]{-16842912}};
        int[] colors = {tintColor - (-520093696), 268435456, tintColor - (-805306368), 536870912, tintColor - (-805306368), 536870912};
        return new ColorStateList(states, colors);
    }
}
