package android.support.transition;

import android.os.Build;
import android.view.ViewGroup;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ViewGroupUtils {
    private static final ViewGroupUtilsImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new ViewGroupUtilsApi18();
        } else {
            IMPL = new ViewGroupUtilsApi14();
        }
    }

    static ViewGroupOverlayImpl getOverlay(ViewGroup group) {
        return IMPL.getOverlay(group);
    }

    static void suppressLayout(ViewGroup group, boolean suppress) {
        IMPL.suppressLayout(group, suppress);
    }
}
