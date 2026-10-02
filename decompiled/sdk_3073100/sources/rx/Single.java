package rx;

import rx.functions.Action1;
import rx.internal.producers.SingleDelayedProducer;
import rx.plugins.RxJavaHooks;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Single<T> {
    final Observable.OnSubscribe<T> onSubscribe;

    public interface OnSubscribe<T> extends Action1<SingleSubscriber<? super T>> {
    }

    protected Single(OnSubscribe<T> f) {
        final OnSubscribe<T> g = RxJavaHooks.onCreate(f);
        this.onSubscribe = new Observable.OnSubscribe<T>() { // from class: rx.Single.1
            @Override // rx.functions.Action1
            public void call(final Subscriber<? super T> child) {
                final SingleDelayedProducer<T> producer = new SingleDelayedProducer<>(child);
                child.setProducer(producer);
                SingleSubscriber<T> ss = new SingleSubscriber<T>() { // from class: rx.Single.1.1
                    @Override // rx.SingleSubscriber
                    public void onSuccess(T value) throws Throwable {
                        producer.setValue(value);
                    }

                    @Override // rx.SingleSubscriber
                    public void onError(Throwable error) {
                        child.onError(error);
                    }
                };
                child.add(ss);
                g.call(ss);
            }
        };
    }
}
