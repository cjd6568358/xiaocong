package org.greenrobot.eventbus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class Subscription {
    volatile boolean active = true;
    final Object subscriber;
    final SubscriberMethod subscriberMethod;

    Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Subscription)) {
            return false;
        }
        Subscription otherSubscription = (Subscription) other;
        return this.subscriber == otherSubscription.subscriber && this.subscriberMethod.equals(otherSubscription.subscriberMethod);
    }

    public int hashCode() {
        return this.subscriber.hashCode() + this.subscriberMethod.methodString.hashCode();
    }
}
