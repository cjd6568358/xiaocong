package kotlin.jvm.internal;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CollectionToArray {
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    public static Object[] toArray(Collection<?> collection) {
        int size = collection.size();
        if (size == 0) {
            return EMPTY_OBJECT_ARRAY;
        }
        Object[] r = new Object[size];
        Iterator<?> it = collection.iterator();
        for (int i = 0; i < size; i++) {
            if (!it.hasNext()) {
                return Arrays.copyOf(r, i);
            }
            r[i] = it.next();
        }
        return it.hasNext() ? finishToArray(r, it) : r;
    }

    public static <T, E> T[] toArray(Collection<E> collection, T[] tArr) {
        int size = collection.size();
        Object[] objArr = tArr.length >= size ? tArr : (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
        Iterator<E> it = collection.iterator();
        for (int i = 0; i < objArr.length; i++) {
            if (!it.hasNext()) {
                if (tArr != objArr) {
                    return (T[]) Arrays.copyOf(objArr, i);
                }
                objArr[i] = null;
                return (T[]) objArr;
            }
            objArr[i] = it.next();
        }
        return it.hasNext() ? (T[]) finishToArray(objArr, it) : (T[]) objArr;
    }

    private static <T> T[] finishToArray(T[] tArr, Iterator<?> it) {
        int length = tArr.length;
        while (it.hasNext()) {
            int length2 = tArr.length;
            if (length == length2) {
                int i = ((length2 / 2) + 1) * 3;
                if (i <= length2) {
                    if (length2 == Integer.MAX_VALUE) {
                        throw new OutOfMemoryError("Required array size too large");
                    }
                    i = Integer.MAX_VALUE;
                }
                tArr = (T[]) Arrays.copyOf(tArr, i);
            }
            tArr[length] = it.next();
            length++;
        }
        return length == tArr.length ? tArr : (T[]) Arrays.copyOf(tArr, length);
    }
}
