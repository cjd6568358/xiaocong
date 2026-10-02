package kotlin.jvm.internal;

import java.util.Arrays;
import java.util.List;
import kotlin.KotlinNullPointerException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Intrinsics {
    private Intrinsics() {
    }

    public static void throwNpe() {
        throw ((KotlinNullPointerException) sanitizeStackTrace(new KotlinNullPointerException()));
    }

    public static void checkExpressionValueIsNotNull(Object value, String expression) {
        if (value == null) {
            throw ((IllegalStateException) sanitizeStackTrace(new IllegalStateException(expression + " must not be null")));
        }
    }

    public static void checkParameterIsNotNull(Object value, String paramName) {
        if (value == null) {
            throwParameterIsNullException(paramName);
        }
    }

    private static void throwParameterIsNullException(String paramName) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTraceElements[3];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        IllegalArgumentException exception = new IllegalArgumentException("Parameter specified as non-null is null: method " + className + "." + methodName + ", parameter " + paramName);
        throw ((IllegalArgumentException) sanitizeStackTrace(exception));
    }

    public static boolean areEqual(Object first, Object second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }

    private static <T extends Throwable> T sanitizeStackTrace(T t) {
        return (T) sanitizeStackTrace(t, Intrinsics.class.getName());
    }

    static <T extends Throwable> T sanitizeStackTrace(T throwable, String classNameToDrop) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int lastIntrinsic = -1;
        for (int i = 0; i < size; i++) {
            if (classNameToDrop.equals(stackTrace[i].getClassName())) {
                lastIntrinsic = i;
            }
        }
        List<StackTraceElement> list = Arrays.asList(stackTrace).subList(lastIntrinsic + 1, size);
        throwable.setStackTrace((StackTraceElement[]) list.toArray(new StackTraceElement[list.size()]));
        return throwable;
    }
}
