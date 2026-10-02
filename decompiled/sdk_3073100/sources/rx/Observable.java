package rx;

import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorFailedException;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.internal.operators.OnSubscribeDefer;
import rx.internal.operators.OnSubscribeLift;
import rx.internal.operators.OnSubscribeMap;
import rx.internal.operators.OnSubscribeSingle;
import rx.internal.operators.OnSubscribeThrow;
import rx.internal.operators.OperatorObserveOn;
import rx.internal.operators.OperatorSubscribeOn;
import rx.internal.operators.OperatorUnsubscribeOn;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.ScalarSynchronousObservable;
import rx.observers.SafeSubscriber;
import rx.plugins.RxJavaHooks;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Observable<T> {
    final OnSubscribe<T> onSubscribe;

    public interface OnSubscribe<T> extends Action1<Subscriber<? super T>> {
    }

    public interface Operator<R, T> extends Func1<Subscriber<? super R>, Subscriber<? super T>> {
    }

    protected Observable(OnSubscribe<T> f) {
        this.onSubscribe = f;
    }

    public static <T> Observable<T> create(OnSubscribe<T> f) {
        return new Observable<>(RxJavaHooks.onCreate(f));
    }

    public final <R> Observable<R> lift(Operator<? extends R, ? super T> operator) {
        return create(new OnSubscribeLift(this.onSubscribe, operator));
    }

    public Single<T> toSingle() {
        return new Single<>(OnSubscribeSingle.create(this));
    }

    public Completable toCompletable() {
        return Completable.fromObservable(this);
    }

    public static <T> Observable<T> defer(Func0<Observable<T>> observableFactory) {
        return create(new OnSubscribeDefer(observableFactory));
    }

    public static <T> Observable<T> error(Throwable exception) {
        return create(new OnSubscribeThrow(exception));
    }

    public static <T> Observable<T> just(T value) {
        return ScalarSynchronousObservable.create(value);
    }

    public final <R> Observable<R> map(Func1<? super T, ? extends R> func) {
        return create(new OnSubscribeMap(this, func));
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        return observeOn(scheduler, RxRingBuffer.SIZE);
    }

    public final Observable<T> observeOn(Scheduler scheduler, int bufferSize) {
        return observeOn(scheduler, false, bufferSize);
    }

    public final Observable<T> observeOn(Scheduler scheduler, boolean z, int i) {
        return this instanceof ScalarSynchronousObservable ? ((ScalarSynchronousObservable) this).scalarScheduleOn(scheduler) : (Observable<T>) lift(new OperatorObserveOn(scheduler, z, i));
    }

    public final Subscription unsafeSubscribe(Subscriber<? super T> subscriber) {
        try {
            subscriber.onStart();
            RxJavaHooks.onObservableStart(this, this.onSubscribe).call(subscriber);
            return RxJavaHooks.onObservableReturn(subscriber);
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            try {
                subscriber.onError(RxJavaHooks.onObservableError(e));
                return Subscriptions.unsubscribed();
            } catch (Throwable e2) {
                Exceptions.throwIfFatal(e2);
                RuntimeException r = new OnErrorFailedException("Error occurred attempting to subscribe [" + e.getMessage() + "] and then again while trying to pass to onError.", e2);
                RxJavaHooks.onObservableError(r);
                throw r;
            }
        }
    }

    public final Subscription subscribe(Subscriber<? super T> subscriber) {
        return subscribe(subscriber, this);
    }

    static <T> Subscription subscribe(Subscriber<? super T> subscriber, Observable<T> observable) throws Throwable {
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber can not be null");
        }
        if (observable.onSubscribe == null) {
            throw new IllegalStateException("onSubscribe function can not be null.");
        }
        subscriber.onStart();
        if (!(subscriber instanceof SafeSubscriber)) {
            subscriber = new SafeSubscriber<>(subscriber);
        }
        try {
            RxJavaHooks.onObservableStart(observable, observable.onSubscribe).call(subscriber);
            return RxJavaHooks.onObservableReturn(subscriber);
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            if (subscriber.isUnsubscribed()) {
                RxJavaHooks.onError(RxJavaHooks.onObservableError(e));
            } else {
                try {
                    subscriber.onError(RxJavaHooks.onObservableError(e));
                } catch (Throwable e2) {
                    Exceptions.throwIfFatal(e2);
                    RuntimeException r = new OnErrorFailedException("Error occurred attempting to subscribe [" + e.getMessage() + "] and then again while trying to pass to onError.", e2);
                    RxJavaHooks.onObservableError(r);
                    throw r;
                }
            }
            return Subscriptions.unsubscribed();
        }
    }

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        return this instanceof ScalarSynchronousObservable ? ((ScalarSynchronousObservable) this).scalarScheduleOn(scheduler) : create(new OperatorSubscribeOn(this, scheduler));
    }

    public final Observable<T> unsubscribeOn(Scheduler scheduler) {
        return (Observable<T>) lift(new OperatorUnsubscribeOn(scheduler));
    }
}
