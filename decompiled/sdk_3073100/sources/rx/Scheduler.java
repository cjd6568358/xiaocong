package rx;

import java.util.concurrent.TimeUnit;
import rx.functions.Action0;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class Scheduler {
    static final long CLOCK_DRIFT_TOLERANCE_NANOS = TimeUnit.MINUTES.toNanos(Long.getLong("rx.scheduler.drift-tolerance", 15).longValue());

    public abstract Worker createWorker();

    public static abstract class Worker implements Subscription {
        public abstract Subscription schedule(Action0 action0);

        public long now() {
            return System.currentTimeMillis();
        }
    }
}
