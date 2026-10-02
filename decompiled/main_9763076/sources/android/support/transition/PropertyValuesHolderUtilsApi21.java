package android.support.transition;

import android.animation.PropertyValuesHolder;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.Property;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class PropertyValuesHolderUtilsApi21 implements PropertyValuesHolderUtilsImpl {
    PropertyValuesHolderUtilsApi21() {
    }

    @Override // android.support.transition.PropertyValuesHolderUtilsImpl
    public PropertyValuesHolder ofPointF(Property<?, PointF> property, Path path) {
        return PropertyValuesHolder.ofObject(property, (TypeConverter) null, path);
    }
}
