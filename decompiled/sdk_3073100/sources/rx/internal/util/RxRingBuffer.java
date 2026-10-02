package rx.internal.util;

import java.util.Queue;
import rx.Subscription;
import rx.internal.operators.NotificationLite;
import rx.internal.util.unsafe.SpmcArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RxRingBuffer implements Subscription {
    private static final NotificationLite<Object> ON = NotificationLite.instance();
    public static final int SIZE;
    public static final ObjectPool<Queue<Object>> SPMC_POOL;
    public static final ObjectPool<Queue<Object>> SPSC_POOL;
    private final ObjectPool<Queue<Object>> pool;
    private Queue<Object> queue;

    static {
        int defaultSize = 128;
        if (PlatformDependent.isAndroid()) {
            defaultSize = 16;
        }
        String sizeFromProperty = System.getProperty("rx.ring-buffer.size");
        if (sizeFromProperty != null) {
            try {
                defaultSize = Integer.parseInt(sizeFromProperty);
            } catch (NumberFormatException e) {
                System.err.println("Failed to set 'rx.buffer.size' with value " + sizeFromProperty + " => " + e.getMessage());
            }
        }
        SIZE = defaultSize;
        SPSC_POOL = new ObjectPool<Queue<Object>>() { // from class: rx.internal.util.RxRingBuffer.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // rx.internal.util.ObjectPool
            public Queue<Object> createObject() {
                return new SpscArrayQueue(RxRingBuffer.SIZE);
            }
        };
        SPMC_POOL = new ObjectPool<Queue<Object>>() { // from class: rx.internal.util.RxRingBuffer.2
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // rx.internal.util.ObjectPool
            public Queue<Object> createObject() {
                return new SpmcArrayQueue(RxRingBuffer.SIZE);
            }
        };
    }

    public synchronized void release() {
        Queue<Object> q = this.queue;
        ObjectPool<Queue<Object>> p = this.pool;
        if (p != null && q != null) {
            q.clear();
            this.queue = null;
            p.returnObject(q);
        }
    }

    @Override // rx.Subscription
    public void unsubscribe() {
        release();
    }

    @Override // rx.Subscription
    public boolean isUnsubscribed() {
        return this.queue == null;
    }
}
