package rx.internal.util.unsafe;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class UnsafeAccess {
    private static final boolean DISABLED_BY_USER;
    public static final Unsafe UNSAFE;

    static {
        DISABLED_BY_USER = System.getProperty("rx.unsafe-disable") != null;
        Unsafe u = null;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            u = (Unsafe) field.get(null);
        } catch (Throwable th) {
        }
        UNSAFE = u;
    }

    public static boolean isUnsafeAvailable() {
        return (UNSAFE == null || DISABLED_BY_USER) ? false : true;
    }

    public static long addressOf(Class<?> clazz, String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            return UNSAFE.objectFieldOffset(f);
        } catch (NoSuchFieldException ex) {
            InternalError ie = new InternalError();
            ie.initCause(ex);
            throw ie;
        }
    }
}
