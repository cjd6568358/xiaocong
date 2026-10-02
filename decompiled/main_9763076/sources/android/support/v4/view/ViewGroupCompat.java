package android.support.v4.view;

import android.os.Build;
import android.view.ViewGroup;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ViewGroupCompat {
    static final ViewGroupCompatBaseImpl IMPL;

    static class ViewGroupCompatBaseImpl {
        ViewGroupCompatBaseImpl() {
        }
    }

    static class ViewGroupCompatApi18Impl extends ViewGroupCompatBaseImpl {
        ViewGroupCompatApi18Impl() {
        }
    }

    static class ViewGroupCompatApi21Impl extends ViewGroupCompatApi18Impl {
        ViewGroupCompatApi21Impl() {
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new ViewGroupCompatApi21Impl();
        } else if (Build.VERSION.SDK_INT >= 18) {
            IMPL = new ViewGroupCompatApi18Impl();
        } else {
            IMPL = new ViewGroupCompatBaseImpl();
        }
    }

    @Deprecated
    public static void setMotionEventSplittingEnabled(ViewGroup group, boolean split) {
        group.setMotionEventSplittingEnabled(split);
    }
}
