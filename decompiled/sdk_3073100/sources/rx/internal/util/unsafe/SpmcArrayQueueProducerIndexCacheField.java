package rx.internal.util.unsafe;

/* JADX INFO: compiled from: SpmcArrayQueue.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
abstract class SpmcArrayQueueProducerIndexCacheField<E> extends SpmcArrayQueueMidPad<E> {
    private volatile long producerIndexCache;

    public SpmcArrayQueueProducerIndexCacheField(int capacity) {
        super(capacity);
    }

    protected final long lvProducerIndexCache() {
        return this.producerIndexCache;
    }

    protected final void svProducerIndexCache(long v) {
        this.producerIndexCache = v;
    }
}
