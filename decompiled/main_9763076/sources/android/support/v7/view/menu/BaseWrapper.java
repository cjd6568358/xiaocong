package android.support.v7.view.menu;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BaseWrapper<T> {
    final T mWrappedObject;

    BaseWrapper(T object) {
        if (object == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.mWrappedObject = object;
    }
}
