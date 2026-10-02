package rx.internal.schedulers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class GenericScheduledExecutorService implements SchedulerLifecycle {
    public static final GenericScheduledExecutorService INSTANCE;
    private static final ScheduledExecutorService[] NONE = new ScheduledExecutorService[0];
    private static final ScheduledExecutorService SHUTDOWN = Executors.newScheduledThreadPool(0);
    private static int roundRobin;
    private final AtomicReference<ScheduledExecutorService[]> executor = new AtomicReference<>(NONE);

    static {
        SHUTDOWN.shutdown();
        INSTANCE = new GenericScheduledExecutorService();
    }

    private GenericScheduledExecutorService() {
        start();
    }

    public void start() {
        int count = Runtime.getRuntime().availableProcessors();
        if (count > 4) {
            count /= 2;
        }
        if (count > 8) {
            count = 8;
        }
        ScheduledExecutorService[] execs = new ScheduledExecutorService[count];
        for (int i = 0; i < count; i++) {
            execs[i] = GenericScheduledExecutorServiceFactory.create();
        }
        if (this.executor.compareAndSet(NONE, execs)) {
            for (ScheduledExecutorService exec : execs) {
                if (!NewThreadWorker.tryEnableCancelPolicy(exec) && (exec instanceof ScheduledThreadPoolExecutor)) {
                    NewThreadWorker.registerExecutor((ScheduledThreadPoolExecutor) exec);
                }
            }
            return;
        }
        for (ScheduledExecutorService scheduledExecutorService : execs) {
            scheduledExecutorService.shutdownNow();
        }
    }

    @Override // rx.internal.schedulers.SchedulerLifecycle
    public void shutdown() {
        ScheduledExecutorService[] execs;
        do {
            execs = this.executor.get();
            if (execs == NONE) {
                return;
            }
        } while (!this.executor.compareAndSet(execs, NONE));
        for (ScheduledExecutorService exec : execs) {
            NewThreadWorker.deregisterExecutor(exec);
            exec.shutdownNow();
        }
    }

    public static ScheduledExecutorService getInstance() {
        ScheduledExecutorService[] execs = INSTANCE.executor.get();
        if (execs == NONE) {
            return SHUTDOWN;
        }
        int r = roundRobin + 1;
        if (r >= execs.length) {
            r = 0;
        }
        roundRobin = r;
        return execs[r];
    }
}
