package rx.internal.operators;

import rx.Observable;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class OnSubscribeMap<T, R> implements Observable.OnSubscribe<R> {
    final Observable<T> source;
    final Func1<? super T, ? extends R> transformer;

    public OnSubscribeMap(Observable<T> source, Func1<? super T, ? extends R> transformer) {
        this.source = source;
        this.transformer = transformer;
    }

    @Override // rx.functions.Action1
    public void call(Subscriber<? super R> o) {
        MapSubscriber<T, R> parent = new MapSubscriber<>(o, this.transformer);
        o.add(parent);
        this.source.unsafeSubscribe(parent);
    }

    static final class MapSubscriber<T, R> extends Subscriber<T> {
        final Subscriber<? super R> actual;
        boolean done;
        final Func1<? super T, ? extends R> mapper;

        public MapSubscriber(Subscriber<? super R> actual, Func1<? super T, ? extends R> mapper) {
            this.actual = actual;
            this.mapper = mapper;
        }

        @Override // rx.Observer
        public void onNext(T t) throws Throwable {
            try {
                R result = this.mapper.call(t);
                this.actual.onNext(result);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                unsubscribe();
                onError(OnErrorThrowable.addValueAsLastCause(ex, t));
            }
        }

        @Override // rx.Observer
        public void onError(Throwable e) {
            if (this.done) {
                RxJavaHooks.onError(e);
            } else {
                this.done = true;
                this.actual.onError(e);
            }
        }

        @Override // rx.Observer
        public void onCompleted() {
            if (!this.done) {
                this.actual.onCompleted();
            }
        }

        @Override // rx.Subscriber
        public void setProducer(Producer p) {
            this.actual.setProducer(p);
        }
    }
}
