package android.support.transition;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
interface ObjectAnimatorUtilsImpl {
    <T> ObjectAnimator ofPointF(T t, Property<T, PointF> property, Path path);
}
