package rx.exceptions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import rx.Observer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Exceptions {
    public static void throwIfFatal(Throwable t) throws Throwable {
        if (t instanceof OnErrorNotImplementedException) {
            throw ((OnErrorNotImplementedException) t);
        }
        if (t instanceof OnErrorFailedException) {
            throw ((OnErrorFailedException) t);
        }
        if (t instanceof OnCompletedFailedException) {
            throw ((OnCompletedFailedException) t);
        }
        if (t instanceof VirtualMachineError) {
            throw ((VirtualMachineError) t);
        }
        if (t instanceof ThreadDeath) {
            throw ((ThreadDeath) t);
        }
        if (t instanceof LinkageError) {
            throw ((LinkageError) t);
        }
    }

    public static void addCause(Throwable e, Throwable cause) {
        Set<Throwable> seenCauses = new HashSet<>();
        int i = 0;
        while (e.getCause() != null) {
            int i2 = i + 1;
            if (i < 25) {
                e = e.getCause();
                if (seenCauses.contains(e.getCause())) {
                    break;
                }
                seenCauses.add(e.getCause());
                i = i2;
            } else {
                return;
            }
        }
        try {
            e.initCause(cause);
        } catch (Throwable th) {
        }
    }

    public static Throwable getFinalCause(Throwable e) {
        int i = 0;
        while (e.getCause() != null) {
            int i2 = i + 1;
            if (i >= 25) {
                return new RuntimeException("Stack too deep to get final cause");
            }
            e = e.getCause();
            i = i2;
        }
        return e;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: rx.exceptions.CompositeException */
    public static void throwIfAny(List<? extends Throwable> exceptions) throws Throwable {
        if (exceptions != null && !exceptions.isEmpty()) {
            if (exceptions.size() == 1) {
                Throwable t = exceptions.get(0);
                if (t instanceof RuntimeException) {
                    throw ((RuntimeException) t);
                }
                if (t instanceof Error) {
                    throw ((Error) t);
                }
                throw new RuntimeException(t);
            }
            throw new CompositeException(exceptions);
        }
    }

    public static void throwOrReport(Throwable t, Observer<?> o, Object value) throws Throwable {
        throwIfFatal(t);
        o.onError(OnErrorThrowable.addValueAsLastCause(t, value));
    }

    public static void throwOrReport(Throwable t, Observer<?> o) throws Throwable {
        throwIfFatal(t);
        o.onError(t);
    }
}
