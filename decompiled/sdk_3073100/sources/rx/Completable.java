package rx;

import rx.functions.Action1;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Completable {
    static final Completable COMPLETE = new Completable(new OnSubscribe() { // from class: rx.Completable.1
        @Override // rx.functions.Action1
        public void call(CompletableSubscriber s) {
            s.onSubscribe(Subscriptions.unsubscribed());
            s.onCompleted();
        }
    }, false);
    static final Completable NEVER = new Completable(new OnSubscribe() { // from class: rx.Completable.2
        @Override // rx.functions.Action1
        public void call(CompletableSubscriber s) {
            s.onSubscribe(Subscriptions.unsubscribed());
        }
    }, false);
    private final OnSubscribe onSubscribe;

    public interface OnSubscribe extends Action1<CompletableSubscriber> {
    }

    public interface Operator extends Func1<CompletableSubscriber, CompletableSubscriber> {
    }

    public static Completable create(OnSubscribe onSubscribe) {
        requireNonNull(onSubscribe);
        try {
            return new Completable(onSubscribe);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Throwable ex2) {
            RxJavaHooks.onError(ex2);
            throw toNpe(ex2);
        }
    }

    public static Completable fromObservable(final Observable<?> flowable) {
        requireNonNull(flowable);
        return create(new OnSubscribe() { // from class: rx.Completable.10
            @Override // rx.functions.Action1
            public void call(final CompletableSubscriber cs) {
                Subscriber<Object> subscriber = new Subscriber<Object>() { // from class: rx.Completable.10.1
                    @Override // rx.Observer
                    public void onCompleted() {
                        cs.onCompleted();
                    }

                    @Override // rx.Observer
                    public void onError(Throwable t) {
                        cs.onError(t);
                    }

                    @Override // rx.Observer
                    public void onNext(Object t) {
                    }
                };
                cs.onSubscribe(subscriber);
                flowable.unsafeSubscribe(subscriber);
            }
        });
    }

    static <T> T requireNonNull(T o) {
        if (o == null) {
            throw new NullPointerException();
        }
        return o;
    }

    static NullPointerException toNpe(Throwable ex) {
        NullPointerException npe = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
        npe.initCause(ex);
        return npe;
    }

    protected Completable(OnSubscribe onSubscribe) {
        this.onSubscribe = RxJavaHooks.onCreate(onSubscribe);
    }

    protected Completable(OnSubscribe onSubscribe, boolean useHook) {
        this.onSubscribe = useHook ? RxJavaHooks.onCreate(onSubscribe) : onSubscribe;
    }
}
