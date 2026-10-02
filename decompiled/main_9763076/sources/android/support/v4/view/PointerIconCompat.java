package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.view.PointerIcon;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class PointerIconCompat {
    private Object mPointerIcon;

    private PointerIconCompat(Object pointerIcon) {
        this.mPointerIcon = pointerIcon;
    }

    public Object getPointerIcon() {
        return this.mPointerIcon;
    }

    public static PointerIconCompat getSystemIcon(Context context, int style) {
        return Build.VERSION.SDK_INT >= 24 ? new PointerIconCompat(PointerIcon.getSystemIcon(context, style)) : new PointerIconCompat(null);
    }
}
