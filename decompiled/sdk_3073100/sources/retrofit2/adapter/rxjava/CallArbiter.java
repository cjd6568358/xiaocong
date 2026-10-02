package retrofit2.adapter.rxjava;

import java.util.concurrent.atomic.AtomicInteger;
import retrofit2.Call;
import retrofit2.Response;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.plugins.RxJavaPlugins;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class CallArbiter<T> extends AtomicInteger implements Producer, Subscription {
    private final Call<T> call;
    private volatile Response<T> response;
    private final Subscriber<? super Response<T>> subscriber;

    CallArbiter(Call<T> call, Subscriber<? super Response<T>> subscriber) {
        super(0);
        this.call = call;
        this.subscriber = subscriber;
    }

    @Override // rx.Subscription
    public void unsubscribe() {
        this.call.cancel();
    }

    @Override // rx.Subscription
    public boolean isUnsubscribed() {
        return this.call.isCanceled();
    }

    @Override // rx.Producer
    public void request(long amount) throws Throwable {
        if (amount == 0) {
            return;
        }
        while (true) {
            int state = get();
            switch (state) {
                case 0:
                    if (compareAndSet(0, 1)) {
                        return;
                    }
                    break;
                case 1:
                case 3:
                    return;
                case 2:
                    if (compareAndSet(2, 3)) {
                        deliverResponse(this.response);
                        return;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown state: " + state);
            }
        }
    }

    void emitResponse(Response<T> response) throws Throwable {
        while (true) {
            int state = get();
            switch (state) {
                case 0:
                    this.response = response;
                    if (compareAndSet(0, 2)) {
                        return;
                    }
                    break;
                case 1:
                    if (compareAndSet(1, 3)) {
                        deliverResponse(response);
                        return;
                    }
                    break;
                case 2:
                case 3:
                    throw new AssertionError();
                default:
                    throw new IllegalStateException("Unknown state: " + state);
            }
        }
    }

    private void deliverResponse(Response<T> response) throws Throwable {
        try {
            if (!isUnsubscribed()) {
                this.subscriber.onNext(response);
            }
            try {
                this.subscriber.onCompleted();
            } catch (Throwable t) {
                Exceptions.throwIfFatal(t);
                RxJavaPlugins.getInstance().getErrorHandler().handleError(t);
            }
        } catch (Throwable t2) {
            Exceptions.throwIfFatal(t2);
            try {
                this.subscriber.onError(t2);
            } catch (Throwable inner) {
                Exceptions.throwIfFatal(inner);
                CompositeException composite = new CompositeException(new Throwable[]{t2, inner});
                RxJavaPlugins.getInstance().getErrorHandler().handleError(composite);
            }
        }
    }

    void emitError(Throwable t) throws Throwable {
        set(3);
        if (!isUnsubscribed()) {
            try {
                this.subscriber.onError(t);
            } catch (Throwable inner) {
                Exceptions.throwIfFatal(inner);
                CompositeException composite = new CompositeException(new Throwable[]{t, inner});
                RxJavaPlugins.getInstance().getErrorHandler().handleError(composite);
            }
        }
    }
}
