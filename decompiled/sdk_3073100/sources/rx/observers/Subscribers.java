package rx.observers;

import rx.Subscriber;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Subscribers {
    public static <T> Subscriber<T> wrap(final Subscriber<? super T> subscriber) {
        return new Subscriber<T>(subscriber) { // from class: rx.observers.Subscribers.5
            @Override // rx.Observer
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override // rx.Observer
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override // rx.Observer
            public void onNext(T t) {
                subscriber.onNext(t);
            }
        };
    }
}
