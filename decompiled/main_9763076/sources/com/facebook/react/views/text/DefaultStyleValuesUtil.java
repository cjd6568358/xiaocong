package com.facebook.react.views.text;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class DefaultStyleValuesUtil {
    public static ColorStateList getDefaultTextColorHint(Context context) {
        return getDefaultTextAttribute(context, R.attr.textColorHint);
    }

    public static ColorStateList getDefaultTextColor(Context context) {
        return getDefaultTextAttribute(context, R.attr.textColor);
    }

    public static int getDefaultTextColorHighlight(Context context) {
        return getDefaultTextAttribute(context, R.attr.textColorHighlight).getDefaultColor();
    }

    private static ColorStateList getDefaultTextAttribute(Context context, int attribute) {
        Resources.Theme theme = context.getTheme();
        TypedArray textAppearances = null;
        try {
            textAppearances = theme.obtainStyledAttributes(new int[]{attribute});
            ColorStateList textColor = textAppearances.getColorStateList(0);
            return textColor;
        } finally {
            if (textAppearances != null) {
                textAppearances.recycle();
            }
        }
    }
}
