package rx.subscriptions;

import rx.Subscription;
import rx.functions.Action0;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Subscriptions {
    private static final Unsubscribed UNSUBSCRIBED = new Unsubscribed();

    public static Subscription unsubscribed() {
        return UNSUBSCRIBED;
    }

    public static Subscription create(Action0 unsubscribe) {
        return BooleanSubscription.create(unsubscribe);
    }

    static final class Unsubscribed implements Subscription {
        Unsubscribed() {
        }

        @Override // rx.Subscription
        public void unsubscribe() {
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return true;
        }
    }
}
