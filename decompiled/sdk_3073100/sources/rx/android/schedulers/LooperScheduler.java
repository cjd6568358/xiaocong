package rx.android.schedulers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.Subscription;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.exceptions.OnErrorNotImplementedException;
import rx.functions.Action0;
import rx.plugins.RxJavaPlugins;
import rx.subscriptions.Subscriptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class LooperScheduler extends Scheduler {
    private final Handler handler;

    LooperScheduler(Looper looper) {
        this.handler = new Handler(looper);
    }

    @Override // rx.Scheduler
    public Scheduler.Worker createWorker() {
        return new HandlerWorker(this.handler);
    }

    static class HandlerWorker extends Scheduler.Worker {
        private final Handler handler;
        private final RxAndroidSchedulersHook hook = RxAndroidPlugins.getInstance().getSchedulersHook();
        private volatile boolean unsubscribed;

        HandlerWorker(Handler handler) {
            this.handler = handler;
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            this.unsubscribed = true;
            this.handler.removeCallbacksAndMessages(this);
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.unsubscribed;
        }

        public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
            if (this.unsubscribed) {
                return Subscriptions.unsubscribed();
            }
            ScheduledAction scheduledAction = new ScheduledAction(this.hook.onSchedule(action), this.handler);
            Message message = Message.obtain(this.handler, scheduledAction);
            message.obj = this;
            this.handler.sendMessageDelayed(message, unit.toMillis(delayTime));
            if (this.unsubscribed) {
                this.handler.removeCallbacks(scheduledAction);
                return Subscriptions.unsubscribed();
            }
            return scheduledAction;
        }

        @Override // rx.Scheduler.Worker
        public Subscription schedule(Action0 action) {
            return schedule(action, 0L, TimeUnit.MILLISECONDS);
        }
    }

    static final class ScheduledAction implements Runnable, Subscription {
        private final Action0 action;
        private final Handler handler;
        private volatile boolean unsubscribed;

        ScheduledAction(Action0 action, Handler handler) {
            this.action = action;
            this.handler = handler;
        }

        @Override // java.lang.Runnable
        public void run() {
            IllegalStateException ie;
            try {
                this.action.call();
            } catch (Throwable e) {
                if (e instanceof OnErrorNotImplementedException) {
                    ie = new IllegalStateException("Exception thrown on Scheduler.Worker thread. Add `onError` handling.", e);
                } else {
                    ie = new IllegalStateException("Fatal Exception thrown on Scheduler.Worker thread.", e);
                }
                RxJavaPlugins.getInstance().getErrorHandler().handleError(ie);
                Thread thread = Thread.currentThread();
                thread.getUncaughtExceptionHandler().uncaughtException(thread, ie);
            }
        }

        @Override // rx.Subscription
        public void unsubscribe() {
            this.unsubscribed = true;
            this.handler.removeCallbacks(this);
        }

        @Override // rx.Subscription
        public boolean isUnsubscribed() {
            return this.unsubscribed;
        }
    }
}
