package android.support.transition;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.Property;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ObjectAnimatorUtils {
    private static final ObjectAnimatorUtilsImpl IMPL;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            IMPL = new ObjectAnimatorUtilsApi21();
        } else {
            IMPL = new ObjectAnimatorUtilsApi14();
        }
    }

    static <T> ObjectAnimator ofPointF(T target, Property<T, PointF> property, Path path) {
        return IMPL.ofPointF(target, property, path);
    }
}
