package rx;

import rx.internal.util.SubscriptionList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class SingleSubscriber<T> implements Subscription {
    private final SubscriptionList cs = new SubscriptionList();

    public abstract void onError(Throwable th);

    public abstract void onSuccess(T t);

    public final void add(Subscription s) {
        this.cs.add(s);
    }

    @Override // rx.Subscription
    public final void unsubscribe() throws Throwable {
        this.cs.unsubscribe();
    }

    @Override // rx.Subscription
    public final boolean isUnsubscribed() {
        return this.cs.isUnsubscribed();
    }
}
