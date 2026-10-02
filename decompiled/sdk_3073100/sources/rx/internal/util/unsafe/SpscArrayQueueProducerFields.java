package rx.internal.util.unsafe;

/* JADX INFO: compiled from: SpscArrayQueue.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
abstract class SpscArrayQueueProducerFields<E> extends SpscArrayQueueL1Pad<E> {
    protected static final long P_INDEX_OFFSET = UnsafeAccess.addressOf(SpscArrayQueueProducerFields.class, "producerIndex");
    protected long producerIndex;

    public SpscArrayQueueProducerFields(int capacity) {
        super(capacity);
    }
}
