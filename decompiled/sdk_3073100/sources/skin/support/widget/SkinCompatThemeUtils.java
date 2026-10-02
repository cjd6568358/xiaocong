package skin.support.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import skin.support.content.res.SkinCompatResources;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SkinCompatThemeUtils {
    private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<>();
    static final int[] DISABLED_STATE_SET = {-16842910};
    static final int[] FOCUSED_STATE_SET = {R.attr.state_focused};
    static final int[] ACTIVATED_STATE_SET = {R.attr.state_activated};
    static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    static final int[] SELECTED_STATE_SET = {R.attr.state_selected};
    static final int[] NOT_PRESSED_OR_FOCUSED_STATE_SET = {-16842919, -16842908};
    static final int[] EMPTY_STATE_SET = new int[0];
    private static final int[] TEMP_ARRAY = new int[1];
    private static final int[] APPCOMPAT_COLOR_PRIMARY_ATTRS = {android.support.v7.appcompat.R.attr.colorPrimary};
    private static final int[] APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = {android.support.v7.appcompat.R.attr.colorPrimaryDark};
    private static final int[] APPCOMPAT_COLOR_ACCENT_ATTRS = {android.support.v7.appcompat.R.attr.colorAccent};

    public static int getColorPrimaryResId(Context context) {
        return getResId(context, APPCOMPAT_COLOR_PRIMARY_ATTRS);
    }

    public static int getColorPrimaryDarkResId(Context context) {
        return getResId(context, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS);
    }

    public static int getTextColorPrimaryResId(Context context) {
        return getResId(context, new int[]{R.attr.textColorPrimary});
    }

    public static int getStatusBarColorResId(Context context) {
        return getResId(context, new int[]{R.attr.statusBarColor});
    }

    public static int getWindowBackgroundResId(Context context) {
        return getResId(context, new int[]{R.attr.windowBackground});
    }

    private static int getResId(Context context, int[] attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs);
        int resId = a.getResourceId(0, 0);
        a.recycle();
        return resId;
    }

    public static int getThemeAttrColor(Context context, int attr) {
        int color = 0;
        TEMP_ARRAY[0] = attr;
        TypedArray a = context.obtainStyledAttributes((AttributeSet) null, TEMP_ARRAY);
        try {
            int resId = a.getResourceId(0, 0);
            if (resId != 0) {
                color = SkinCompatResources.getColor(context, resId);
            }
            return color;
        } finally {
            a.recycle();
        }
    }

    public static ColorStateList getThemeAttrColorStateList(Context context, int attr) {
        ColorStateList colorStateList = null;
        TEMP_ARRAY[0] = attr;
        TypedArray a = context.obtainStyledAttributes((AttributeSet) null, TEMP_ARRAY);
        try {
            int resId = a.getResourceId(0, 0);
            if (resId != 0) {
                colorStateList = SkinCompatResources.getColorStateList(context, resId);
            }
            return colorStateList;
        } finally {
            a.recycle();
        }
    }

    public static int getDisabledThemeAttrColor(Context context, int attr) {
        ColorStateList csl = getThemeAttrColorStateList(context, attr);
        if (csl != null && csl.isStateful()) {
            return csl.getColorForState(DISABLED_STATE_SET, csl.getDefaultColor());
        }
        TypedValue tv = getTypedValue();
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, tv, true);
        float disabledAlpha = tv.getFloat();
        return getThemeAttrColor(context, attr, disabledAlpha);
    }

    private static TypedValue getTypedValue() {
        TypedValue typedValue = TL_TYPED_VALUE.get();
        if (typedValue == null) {
            TypedValue typedValue2 = new TypedValue();
            TL_TYPED_VALUE.set(typedValue2);
            return typedValue2;
        }
        return typedValue;
    }

    static int getThemeAttrColor(Context context, int attr, float alpha) {
        int color = getThemeAttrColor(context, attr);
        int originalAlpha = Color.alpha(color);
        return ColorUtils.setAlphaComponent(color, Math.round(originalAlpha * alpha));
    }
}
