package rx.internal.util.unsafe;

/* JADX INFO: compiled from: SpscArrayQueue.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
abstract class SpscArrayQueueConsumerField<E> extends SpscArrayQueueL2Pad<E> {
    protected static final long C_INDEX_OFFSET = UnsafeAccess.addressOf(SpscArrayQueueConsumerField.class, "consumerIndex");
    protected long consumerIndex;

    public SpscArrayQueueConsumerField(int capacity) {
        super(capacity);
    }
}
