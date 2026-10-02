package android.support.v4.view;

import android.os.Build;
import android.view.ViewGroup;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class MarginLayoutParamsCompat {
    public static int getMarginStart(ViewGroup.MarginLayoutParams lp) {
        return Build.VERSION.SDK_INT >= 17 ? lp.getMarginStart() : lp.leftMargin;
    }

    public static int getMarginEnd(ViewGroup.MarginLayoutParams lp) {
        return Build.VERSION.SDK_INT >= 17 ? lp.getMarginEnd() : lp.rightMargin;
    }
}
