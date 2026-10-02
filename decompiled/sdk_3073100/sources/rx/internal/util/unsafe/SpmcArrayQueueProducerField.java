package rx.internal.util.unsafe;

/* JADX INFO: compiled from: SpmcArrayQueue.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
abstract class SpmcArrayQueueProducerField<E> extends SpmcArrayQueueL1Pad<E> {
    protected static final long P_INDEX_OFFSET = UnsafeAccess.addressOf(SpmcArrayQueueProducerField.class, "producerIndex");
    private volatile long producerIndex;

    protected final long lvProducerIndex() {
        return this.producerIndex;
    }

    protected final void soTail(long v) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, v);
    }

    public SpmcArrayQueueProducerField(int capacity) {
        super(capacity);
    }
}
