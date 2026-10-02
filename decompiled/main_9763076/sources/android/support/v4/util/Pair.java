package android.support.v4.util;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair) o;
        return objectsEqual(p.first, this.first) && objectsEqual(p.second, this.second);
    }

    private static boolean objectsEqual(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public int hashCode() {
        return (this.first == null ? 0 : this.first.hashCode()) ^ (this.second != null ? this.second.hashCode() : 0);
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.first) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.valueOf(this.second) + "}";
    }
}
