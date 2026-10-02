package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class GravityCompat {
    public static void apply(int gravity, int w, int h, Rect container, Rect outRect, int layoutDirection) {
        if (Build.VERSION.SDK_INT >= 17) {
            Gravity.apply(gravity, w, h, container, outRect, layoutDirection);
        } else {
            Gravity.apply(gravity, w, h, container, outRect);
        }
    }

    public static int getAbsoluteGravity(int gravity, int layoutDirection) {
        return Build.VERSION.SDK_INT >= 17 ? Gravity.getAbsoluteGravity(gravity, layoutDirection) : (-8388609) & gravity;
    }
}
