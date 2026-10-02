package retrofit2.adapter.rxjava;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.plugins.RxJavaPlugins;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class BodyOnSubscribe<T> implements Observable.OnSubscribe<T> {
    private final Observable.OnSubscribe<Response<T>> upstream;

    BodyOnSubscribe(Observable.OnSubscribe<Response<T>> upstream) {
        this.upstream = upstream;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super T> subscriber) {
        this.upstream.call((Response<T>) new BodySubscriber(subscriber));
    }

    private static class BodySubscriber<R> extends Subscriber<Response<R>> {
        private final Subscriber<? super R> subscriber;
        private boolean subscriberTerminated;

        BodySubscriber(Subscriber<? super R> subscriber) {
            super(subscriber);
            this.subscriber = subscriber;
        }

        @Override // rx.Observer
        public void onNext(Response<R> response) throws Throwable {
            if (response.isSuccessful()) {
                this.subscriber.onNext(response.body());
                return;
            }
            this.subscriberTerminated = true;
            Throwable t = new HttpException(response);
            try {
                this.subscriber.onError(t);
            } catch (Throwable inner) {
                Exceptions.throwIfFatal(inner);
                CompositeException composite = new CompositeException(new Throwable[]{t, inner});
                RxJavaPlugins.getInstance().getErrorHandler().handleError(composite);
            }
        }

        @Override // rx.Observer
        public void onError(Throwable throwable) {
            if (!this.subscriberTerminated) {
                this.subscriber.onError(throwable);
                return;
            }
            Throwable broken = new AssertionError("This should never happen! Report as a Retrofit bug with the full stacktrace.");
            broken.initCause(throwable);
            RxJavaPlugins.getInstance().getErrorHandler().handleError(broken);
        }

        @Override // rx.Observer
        public void onCompleted() {
            if (!this.subscriberTerminated) {
                this.subscriber.onCompleted();
            }
        }
    }
}
