package kotlin.jvm.internal;

import java.io.Serializable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Ref {

    public static final class ObjectRef<T> implements Serializable {
        public T element;

        public String toString() {
            return String.valueOf(this.element);
        }
    }
}
