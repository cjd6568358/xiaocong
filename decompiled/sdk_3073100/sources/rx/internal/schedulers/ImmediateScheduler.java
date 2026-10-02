package rx.internal.schedulers;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.BooleanSubscription;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class ImmediateScheduler extends Scheduler {
    public static final ImmediateScheduler INSTANCE = new ImmediateScheduler();

    private ImmediateScheduler() {
    }

    @Override // rx.Scheduler
    public Scheduler.Worker createWorker() {
        return new InnerImmediateScheduler();
    }

    final class InnerImmediateScheduler extends Scheduler.Worker implements Subscription {
        final BooleanSubscription innerSubscription = new BooleanSubscription();

        InnerImmediateScheduler() {
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action) {
            action.call();
            return Subscriptions.unsubscribed();
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            this.innerSubscription.unsubscribe();
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.innerSubscription.isUnsubscribed();
        }
    }
}
