package rx.internal.util;

import java.util.concurrent.atomic.AtomicBoolean;
import rx.Observable;
import rx.Producer;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.internal.producers.SingleProducer;
import rx.internal.schedulers.EventLoopsScheduler;
import rx.plugins.RxJavaHooks;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class ScalarSynchronousObservable<T> extends Observable<T> {
    static final boolean STRONG_MODE;
    final T t;

    static {
        String wp = System.getProperty("rx.just.strong-mode", "false");
        STRONG_MODE = Boolean.valueOf(wp).booleanValue();
    }

    static <T> Producer createProducer(Subscriber<? super T> s, T v) {
        return STRONG_MODE ? new SingleProducer(s, v) : new WeakSingleProducer(s, v);
    }

    public static <T> ScalarSynchronousObservable<T> create(T t) {
        return new ScalarSynchronousObservable<>(t);
    }

    protected ScalarSynchronousObservable(T t) {
        super(RxJavaHooks.onCreate(new JustOnSubscribe(t)));
        this.t = t;
    }

    public Observable<T> scalarScheduleOn(final Scheduler scheduler) {
        Func1<Action0, Subscription> onSchedule;
        if (scheduler instanceof EventLoopsScheduler) {
            final EventLoopsScheduler els = (EventLoopsScheduler) scheduler;
            onSchedule = new Func1<Action0, Subscription>() { // from class: rx.internal.util.ScalarSynchronousObservable.1
                @Override // rx.functions.Func1
                public Subscription call(Action0 a) {
                    return els.scheduleDirect(a);
                }
            };
        } else {
            onSchedule = new Func1<Action0, Subscription>() { // from class: rx.internal.util.ScalarSynchronousObservable.2
                @Override // rx.functions.Func1
                public Subscription call(final Action0 a) {
                    final Scheduler.Worker w = scheduler.createWorker();
                    w.schedule(new Action0() { // from class: rx.internal.util.ScalarSynchronousObservable.2.1
                        @Override // rx.functions.Action0
                        public void call() {
                            try {
                                a.call();
                            } finally {
                                w.unsubscribe();
                            }
                        }
                    });
                    return w;
                }
            };
        }
        return create((Observable.OnSubscribe) new ScalarAsyncOnSubscribe(this.t, onSchedule));
    }

    static final class JustOnSubscribe<T> implements Observable.OnSubscribe<T> {
        final T value;

        JustOnSubscribe(T value) {
            this.value = value;
        }

        @Override // rx.functions.Action1
        public void call(Subscriber<? super T> s) {
            s.setProducer(ScalarSynchronousObservable.createProducer(s, this.value));
        }
    }

    static final class ScalarAsyncOnSubscribe<T> implements Observable.OnSubscribe<T> {
        final Func1<Action0, Subscription> onSchedule;
        final T value;

        ScalarAsyncOnSubscribe(T value, Func1<Action0, Subscription> onSchedule) {
            this.value = value;
            this.onSchedule = onSchedule;
        }

        @Override // rx.functions.Action1
        public void call(Subscriber<? super T> s) {
            s.setProducer(new ScalarAsyncProducer(s, this.value, this.onSchedule));
        }
    }

    static final class ScalarAsyncProducer<T> extends AtomicBoolean implements Producer, Action0 {
        private static final long serialVersionUID = -2466317989629281651L;
        final Subscriber<? super T> actual;
        final Func1<Action0, Subscription> onSchedule;
        final T value;

        public ScalarAsyncProducer(Subscriber<? super T> actual, T value, Func1<Action0, Subscription> onSchedule) {
            this.actual = actual;
            this.value = value;
            this.onSchedule = onSchedule;
        }

        @Override // rx.Producer
        public void request(long n) {
            if (n < 0) {
                throw new IllegalArgumentException("n >= 0 required but it was " + n);
            }
            if (n != 0 && compareAndSet(false, true)) {
                this.actual.add(this.onSchedule.call(this));
            }
        }

        @Override // rx.functions.Action0
        public void call() throws Throwable {
            Subscriber<? super T> subscriber = this.actual;
            if (!subscriber.isUnsubscribed()) {
                T t = this.value;
                try {
                    subscriber.onNext(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                    }
                } catch (Throwable th) {
                    Exceptions.throwOrReport(th, subscriber, t);
                }
            }
        }

        @Override // java.util.concurrent.atomic.AtomicBoolean
        public String toString() {
            return "ScalarAsyncProducer[" + this.value + ", " + get() + "]";
        }
    }

    static final class WeakSingleProducer<T> implements Producer {
        final Subscriber<? super T> actual;
        boolean once;
        final T value;

        public WeakSingleProducer(Subscriber<? super T> actual, T value) {
            this.actual = actual;
            this.value = value;
        }

        @Override // rx.Producer
        public void request(long j) throws Throwable {
            if (this.once) {
                return;
            }
            if (j < 0) {
                throw new IllegalStateException("n >= required but it was " + j);
            }
            if (j != 0) {
                this.once = true;
                Subscriber<? super T> subscriber = this.actual;
                if (!subscriber.isUnsubscribed()) {
                    T t = this.value;
                    try {
                        subscriber.onNext(t);
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    } catch (Throwable th) {
                        Exceptions.throwOrReport(th, subscriber, t);
                    }
                }
            }
        }
    }
}
