package rx.internal.operators;

import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class BackpressureUtils {
    public static long getAndAddRequest(AtomicLong requested, long n) {
        long current;
        long next;
        do {
            current = requested.get();
            next = addCap(current, n);
        } while (!requested.compareAndSet(current, next));
        return current;
    }

    public static long addCap(long a, long b) {
        long u = a + b;
        if (u < 0) {
            return Long.MAX_VALUE;
        }
        return u;
    }

    public static long produced(AtomicLong requested, long n) {
        long current;
        long next;
        do {
            current = requested.get();
            if (current == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            next = current - n;
            if (next < 0) {
                throw new IllegalStateException("More produced than requested: " + next);
            }
        } while (!requested.compareAndSet(current, next));
        return next;
    }
}
