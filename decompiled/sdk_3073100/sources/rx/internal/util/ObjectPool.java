package rx.internal.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import rx.internal.schedulers.GenericScheduledExecutorService;
import rx.internal.schedulers.SchedulerLifecycle;
import rx.internal.util.unsafe.MpmcArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.plugins.RxJavaHooks;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class ObjectPool<T> implements SchedulerLifecycle {
    final int maxSize;
    final int minSize;
    private final AtomicReference<Future<?>> periodicTask;
    Queue<T> pool;
    private final long validationInterval;

    protected abstract T createObject();

    public ObjectPool() {
        this(0, 0, 67L);
    }

    private ObjectPool(int min, int max, long validationInterval) {
        this.minSize = min;
        this.maxSize = max;
        this.validationInterval = validationInterval;
        this.periodicTask = new AtomicReference<>();
        initialize(min);
        start();
    }

    public void returnObject(T object) {
        if (object != null) {
            this.pool.offer(object);
        }
    }

    @Override // rx.internal.schedulers.SchedulerLifecycle
    public void shutdown() {
        Future<?> f = this.periodicTask.getAndSet(null);
        if (f != null) {
            f.cancel(false);
        }
    }

    public void start() {
        while (this.periodicTask.get() == null) {
            ScheduledExecutorService w = GenericScheduledExecutorService.getInstance();
            try {
                Future<?> f = w.scheduleAtFixedRate(new Runnable() { // from class: rx.internal.util.ObjectPool.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int size = ObjectPool.this.pool.size();
                        if (size < ObjectPool.this.minSize) {
                            int i = ObjectPool.this.maxSize - size;
                            for (int i2 = 0; i2 < i; i2++) {
                                ObjectPool.this.pool.add((T) ObjectPool.this.createObject());
                            }
                            return;
                        }
                        if (size > ObjectPool.this.maxSize) {
                            int i3 = size - ObjectPool.this.maxSize;
                            for (int i4 = 0; i4 < i3; i4++) {
                                ObjectPool.this.pool.poll();
                            }
                        }
                    }
                }, this.validationInterval, this.validationInterval, TimeUnit.SECONDS);
                if (!this.periodicTask.compareAndSet(null, f)) {
                    f.cancel(false);
                } else {
                    return;
                }
            } catch (RejectedExecutionException ex) {
                RxJavaHooks.onError(ex);
                return;
            }
        }
    }

    private void initialize(int min) {
        if (UnsafeAccess.isUnsafeAvailable()) {
            this.pool = new MpmcArrayQueue(Math.max(this.maxSize, 1024));
        } else {
            this.pool = new ConcurrentLinkedQueue();
        }
        for (int i = 0; i < min; i++) {
            this.pool.add(createObject());
        }
    }
}
