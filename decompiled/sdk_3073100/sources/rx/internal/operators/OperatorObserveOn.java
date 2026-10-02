package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.MissingBackpressureException;
import rx.functions.Action0;
import rx.internal.schedulers.ImmediateScheduler;
import rx.internal.schedulers.TrampolineScheduler;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.atomic.SpscAtomicArrayQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.plugins.RxJavaHooks;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class OperatorObserveOn<T> implements Observable.Operator<T, T> {
    private final int bufferSize;
    private final boolean delayError;
    private final Scheduler scheduler;

    public OperatorObserveOn(Scheduler scheduler, boolean delayError, int bufferSize) {
        this.scheduler = scheduler;
        this.delayError = delayError;
        this.bufferSize = bufferSize <= 0 ? RxRingBuffer.SIZE : bufferSize;
    }

    @Override // rx.functions.Func1
    public Subscriber<? super T> call(Subscriber<? super T> child) {
        if (!(this.scheduler instanceof ImmediateScheduler) && !(this.scheduler instanceof TrampolineScheduler)) {
            ObserveOnSubscriber<T> parent = new ObserveOnSubscriber<>(this.scheduler, child, this.delayError, this.bufferSize);
            parent.init();
            return parent;
        }
        return child;
    }

    static final class ObserveOnSubscriber<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super T> child;
        final boolean delayError;
        long emitted;
        Throwable error;
        volatile boolean finished;
        final int limit;
        final Queue<Object> queue;
        final Scheduler.Worker recursiveScheduler;
        final AtomicLong requested = new AtomicLong();
        final AtomicLong counter = new AtomicLong();
        final NotificationLite<T> on = NotificationLite.instance();

        public ObserveOnSubscriber(Scheduler scheduler, Subscriber<? super T> child, boolean delayError, int bufferSize) {
            this.child = child;
            this.recursiveScheduler = scheduler.createWorker();
            this.delayError = delayError;
            int calculatedSize = bufferSize > 0 ? bufferSize : RxRingBuffer.SIZE;
            this.limit = calculatedSize - (calculatedSize >> 2);
            if (UnsafeAccess.isUnsafeAvailable()) {
                this.queue = new SpscArrayQueue(calculatedSize);
            } else {
                this.queue = new SpscAtomicArrayQueue(calculatedSize);
            }
            request(calculatedSize);
        }

        void init() {
            Subscriber<? super T> localChild = this.child;
            localChild.setProducer(new Producer() { // from class: rx.internal.operators.OperatorObserveOn.ObserveOnSubscriber.1
                @Override // rx.Producer
                public void request(long n) {
                    if (n > 0) {
                        BackpressureUtils.getAndAddRequest(ObserveOnSubscriber.this.requested, n);
                        ObserveOnSubscriber.this.schedule();
                    }
                }
            });
            localChild.add(this.recursiveScheduler);
            localChild.add(this);
        }

        @Override // rx.Observer
        public void onNext(T t) {
            if (!isUnsubscribed() && !this.finished) {
                if (!this.queue.offer(this.on.next(t))) {
                    onError(new MissingBackpressureException());
                } else {
                    schedule();
                }
            }
        }

        @Override // rx.Observer
        public void onCompleted() {
            if (!isUnsubscribed() && !this.finished) {
                this.finished = true;
                schedule();
            }
        }

        @Override // rx.Observer
        public void onError(Throwable e) {
            if (isUnsubscribed() || this.finished) {
                RxJavaHooks.onError(e);
                return;
            }
            this.error = e;
            this.finished = true;
            schedule();
        }

        protected void schedule() {
            if (this.counter.getAndIncrement() == 0) {
                this.recursiveScheduler.schedule(this);
            }
        }

        @Override // rx.functions.Action0
        public void call() {
            long jAddAndGet = 1;
            long j = this.emitted;
            Queue<Object> queue = this.queue;
            Subscriber<? super T> subscriber = this.child;
            NotificationLite<T> notificationLite = this.on;
            do {
                long jProduced = this.requested.get();
                while (jProduced != j) {
                    boolean z = this.finished;
                    Object objPoll = queue.poll();
                    boolean z2 = objPoll == null;
                    if (!checkTerminated(z, z2, subscriber, queue)) {
                        if (z2) {
                            break;
                        }
                        subscriber.onNext(notificationLite.getValue(objPoll));
                        j++;
                        if (j == this.limit) {
                            jProduced = BackpressureUtils.produced(this.requested, j);
                            request(j);
                            j = 0;
                        }
                    } else {
                        return;
                    }
                }
                if (jProduced != j || !checkTerminated(this.finished, queue.isEmpty(), subscriber, queue)) {
                    this.emitted = j;
                    jAddAndGet = this.counter.addAndGet(-jAddAndGet);
                } else {
                    return;
                }
            } while (jAddAndGet != 0);
        }

        boolean checkTerminated(boolean done, boolean isEmpty, Subscriber<? super T> a, Queue<Object> q) {
            if (a.isUnsubscribed()) {
                q.clear();
                return true;
            }
            if (done) {
                if (this.delayError) {
                    if (isEmpty) {
                        Throwable e = this.error;
                        try {
                            if (e != null) {
                                a.onError(e);
                            } else {
                                a.onCompleted();
                            }
                            this.recursiveScheduler.unsubscribe();
                        } catch (Throwable th) {
                            this.recursiveScheduler.unsubscribe();
                            throw th;
                        }
                    }
                } else {
                    Throwable e2 = this.error;
                    if (e2 != null) {
                        q.clear();
                        try {
                            a.onError(e2);
                            return true;
                        } finally {
                            this.recursiveScheduler.unsubscribe();
                        }
                    }
                    if (isEmpty) {
                        try {
                            a.onCompleted();
                            return true;
                        } finally {
                            this.recursiveScheduler.unsubscribe();
                        }
                    }
                }
            }
            return false;
        }
    }
}
