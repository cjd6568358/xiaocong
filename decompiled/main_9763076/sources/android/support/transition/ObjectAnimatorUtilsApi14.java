package android.support.transition;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ObjectAnimatorUtilsApi14 implements ObjectAnimatorUtilsImpl {
    ObjectAnimatorUtilsApi14() {
    }

    @Override // android.support.transition.ObjectAnimatorUtilsImpl
    public <T> ObjectAnimator ofPointF(T target, Property<T, PointF> property, Path path) {
        return ObjectAnimator.ofFloat(target, new PathProperty(property, path), 0.0f, 1.0f);
    }
}
