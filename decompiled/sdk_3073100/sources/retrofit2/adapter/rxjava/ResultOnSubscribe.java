package retrofit2.adapter.rxjava;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.plugins.RxJavaPlugins;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class ResultOnSubscribe<T> implements Observable.OnSubscribe<Result<T>> {
    private final Observable.OnSubscribe<Response<T>> upstream;

    ResultOnSubscribe(Observable.OnSubscribe<Response<T>> upstream) {
        this.upstream = upstream;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super Result<T>> subscriber) {
        this.upstream.call((Response<T>) new ResultSubscriber(subscriber));
    }

    private static class ResultSubscriber<R> extends Subscriber<Response<R>> {
        private final Subscriber<? super Result<R>> subscriber;

        ResultSubscriber(Subscriber<? super Result<R>> subscriber) {
            super(subscriber);
            this.subscriber = subscriber;
        }

        @Override // rx.Observer
        public void onNext(Response<R> response) {
            this.subscriber.onNext(Result.response(response));
        }

        @Override // rx.Observer
        public void onError(Throwable throwable) throws Throwable {
            try {
                this.subscriber.onNext(Result.error(throwable));
                this.subscriber.onCompleted();
            } catch (Throwable t) {
                try {
                    this.subscriber.onError(t);
                } catch (Throwable inner) {
                    Exceptions.throwIfFatal(inner);
                    CompositeException composite = new CompositeException(new Throwable[]{t, inner});
                    RxJavaPlugins.getInstance().getErrorHandler().handleError(composite);
                }
            }
        }

        @Override // rx.Observer
        public void onCompleted() {
            this.subscriber.onCompleted();
        }
    }
}
