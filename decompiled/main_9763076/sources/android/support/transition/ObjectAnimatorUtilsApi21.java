package android.support.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ObjectAnimatorUtilsApi21 implements ObjectAnimatorUtilsImpl {
    ObjectAnimatorUtilsApi21() {
    }

    @Override // android.support.transition.ObjectAnimatorUtilsImpl
    public <T> ObjectAnimator ofPointF(T target, Property<T, PointF> property, Path path) {
        return ObjectAnimator.ofObject(target, property, (TypeConverter) null, path);
    }
}
