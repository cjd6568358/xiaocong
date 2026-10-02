package rx.plugins;

import java.util.concurrent.ScheduledExecutorService;
import rx.Completable;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class RxJavaHooks {
    static volatile Func1<Completable.OnSubscribe, Completable.OnSubscribe> onCompletableCreate;
    static volatile Func1<Completable.Operator, Completable.Operator> onCompletableLift;
    static volatile Func2<Completable, Completable.OnSubscribe, Completable.OnSubscribe> onCompletableStart;
    static volatile Func1<Throwable, Throwable> onCompletableSubscribeError;
    static volatile Action1<Throwable> onError;
    static volatile Func0<? extends ScheduledExecutorService> onGenericScheduledExecutorService;
    static volatile Func1<Scheduler, Scheduler> onIOScheduler;
    static volatile Func1<Observable.OnSubscribe, Observable.OnSubscribe> onObservableCreate;
    static volatile Func1<Observable.Operator, Observable.Operator> onObservableLift;
    static volatile Func1<Subscription, Subscription> onObservableReturn;
    static volatile Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe> onObservableStart;
    static volatile Func1<Throwable, Throwable> onObservableSubscribeError;
    static volatile Func1<Action0, Action0> onScheduleAction;
    static volatile Func1<Single.OnSubscribe, Single.OnSubscribe> onSingleCreate;
    static volatile Func1<Observable.Operator, Observable.Operator> onSingleLift;
    static volatile Func1<Subscription, Subscription> onSingleReturn;
    static volatile Func2<Single, Observable.OnSubscribe, Observable.OnSubscribe> onSingleStart;
    static volatile Func1<Throwable, Throwable> onSingleSubscribeError;

    static {
        init();
    }

    static void init() {
        onError = new Action1<Throwable>() { // from class: rx.plugins.RxJavaHooks.1
            @Override // rx.functions.Action1
            public void call(Throwable e) {
                RxJavaPlugins.getInstance().getErrorHandler().handleError(e);
            }
        };
        onObservableStart = new Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe>() { // from class: rx.plugins.RxJavaHooks.2
            @Override // rx.functions.Func2
            public Observable.OnSubscribe call(Observable t1, Observable.OnSubscribe t2) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onSubscribeStart(t1, t2);
            }
        };
        onObservableReturn = new Func1<Subscription, Subscription>() { // from class: rx.plugins.RxJavaHooks.3
            @Override // rx.functions.Func1
            public Subscription call(Subscription f) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onSubscribeReturn(f);
            }
        };
        onSingleStart = new Func2<Single, Observable.OnSubscribe, Observable.OnSubscribe>() { // from class: rx.plugins.RxJavaHooks.4
            @Override // rx.functions.Func2
            public Observable.OnSubscribe call(Single t1, Observable.OnSubscribe t2) {
                return RxJavaPlugins.getInstance().getSingleExecutionHook().onSubscribeStart(t1, t2);
            }
        };
        onSingleReturn = new Func1<Subscription, Subscription>() { // from class: rx.plugins.RxJavaHooks.5
            @Override // rx.functions.Func1
            public Subscription call(Subscription f) {
                return RxJavaPlugins.getInstance().getSingleExecutionHook().onSubscribeReturn(f);
            }
        };
        onCompletableStart = new Func2<Completable, Completable.OnSubscribe, Completable.OnSubscribe>() { // from class: rx.plugins.RxJavaHooks.6
            @Override // rx.functions.Func2
            public Completable.OnSubscribe call(Completable t1, Completable.OnSubscribe t2) {
                return RxJavaPlugins.getInstance().getCompletableExecutionHook().onSubscribeStart(t1, t2);
            }
        };
        onScheduleAction = new Func1<Action0, Action0>() { // from class: rx.plugins.RxJavaHooks.7
            @Override // rx.functions.Func1
            public Action0 call(Action0 a) {
                return RxJavaPlugins.getInstance().getSchedulersHook().onSchedule(a);
            }
        };
        onObservableSubscribeError = new Func1<Throwable, Throwable>() { // from class: rx.plugins.RxJavaHooks.8
            @Override // rx.functions.Func1
            public Throwable call(Throwable t) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onSubscribeError(t);
            }
        };
        onObservableLift = new Func1<Observable.Operator, Observable.Operator>() { // from class: rx.plugins.RxJavaHooks.9
            @Override // rx.functions.Func1
            public Observable.Operator call(Observable.Operator t) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onLift(t);
            }
        };
        onSingleSubscribeError = new Func1<Throwable, Throwable>() { // from class: rx.plugins.RxJavaHooks.10
            @Override // rx.functions.Func1
            public Throwable call(Throwable t) {
                return RxJavaPlugins.getInstance().getSingleExecutionHook().onSubscribeError(t);
            }
        };
        onSingleLift = new Func1<Observable.Operator, Observable.Operator>() { // from class: rx.plugins.RxJavaHooks.11
            @Override // rx.functions.Func1
            public Observable.Operator call(Observable.Operator t) {
                return RxJavaPlugins.getInstance().getSingleExecutionHook().onLift(t);
            }
        };
        onCompletableSubscribeError = new Func1<Throwable, Throwable>() { // from class: rx.plugins.RxJavaHooks.12
            @Override // rx.functions.Func1
            public Throwable call(Throwable t) {
                return RxJavaPlugins.getInstance().getCompletableExecutionHook().onSubscribeError(t);
            }
        };
        onCompletableLift = new Func1<Completable.Operator, Completable.Operator>() { // from class: rx.plugins.RxJavaHooks.13
            @Override // rx.functions.Func1
            public Completable.Operator call(Completable.Operator t) {
                return RxJavaPlugins.getInstance().getCompletableExecutionHook().onLift(t);
            }
        };
        initCreate();
    }

    static void initCreate() {
        onObservableCreate = new Func1<Observable.OnSubscribe, Observable.OnSubscribe>() { // from class: rx.plugins.RxJavaHooks.14
            @Override // rx.functions.Func1
            public Observable.OnSubscribe call(Observable.OnSubscribe f) {
                return RxJavaPlugins.getInstance().getObservableExecutionHook().onCreate(f);
            }
        };
        onSingleCreate = new Func1<Single.OnSubscribe, Single.OnSubscribe>() { // from class: rx.plugins.RxJavaHooks.15
            @Override // rx.functions.Func1
            public Single.OnSubscribe call(Single.OnSubscribe f) {
                return RxJavaPlugins.getInstance().getSingleExecutionHook().onCreate(f);
            }
        };
        onCompletableCreate = new Func1<Completable.OnSubscribe, Completable.OnSubscribe>() { // from class: rx.plugins.RxJavaHooks.16
            @Override // rx.functions.Func1
            public Completable.OnSubscribe call(Completable.OnSubscribe f) {
                return RxJavaPlugins.getInstance().getCompletableExecutionHook().onCreate(f);
            }
        };
    }

    public static void onError(Throwable ex) {
        Action1<Throwable> f = onError;
        if (f != null) {
            try {
                f.call(ex);
                return;
            } catch (Throwable pluginException) {
                System.err.println("The onError handler threw an Exception. It shouldn't. => " + pluginException.getMessage());
                pluginException.printStackTrace();
                signalUncaught(pluginException);
            }
        }
        signalUncaught(ex);
    }

    static void signalUncaught(Throwable ex) {
        Thread current = Thread.currentThread();
        Thread.UncaughtExceptionHandler handler = current.getUncaughtExceptionHandler();
        handler.uncaughtException(current, ex);
    }

    public static <T> Observable.OnSubscribe<T> onCreate(Observable.OnSubscribe<T> onSubscribe) {
        Func1<Observable.OnSubscribe, Observable.OnSubscribe> f = onObservableCreate;
        return f != null ? f.call(onSubscribe) : onSubscribe;
    }

    public static <T> Single.OnSubscribe<T> onCreate(Single.OnSubscribe<T> onSubscribe) {
        Func1<Single.OnSubscribe, Single.OnSubscribe> f = onSingleCreate;
        return f != null ? f.call(onSubscribe) : onSubscribe;
    }

    public static Completable.OnSubscribe onCreate(Completable.OnSubscribe onSubscribe) {
        Func1<Completable.OnSubscribe, Completable.OnSubscribe> f = onCompletableCreate;
        return f != null ? f.call(onSubscribe) : onSubscribe;
    }

    public static Scheduler onIOScheduler(Scheduler scheduler) {
        Func1<Scheduler, Scheduler> f = onIOScheduler;
        return f != null ? f.call(scheduler) : scheduler;
    }

    public static Action0 onScheduledAction(Action0 action) {
        Func1<Action0, Action0> f = onScheduleAction;
        return f != null ? f.call(action) : action;
    }

    public static <T> Observable.OnSubscribe<T> onObservableStart(Observable<T> instance, Observable.OnSubscribe<T> onSubscribe) {
        Func2<Observable, Observable.OnSubscribe, Observable.OnSubscribe> f = onObservableStart;
        return f != null ? f.call(instance, onSubscribe) : onSubscribe;
    }

    public static Subscription onObservableReturn(Subscription subscription) {
        Func1<Subscription, Subscription> f = onObservableReturn;
        return f != null ? f.call(subscription) : subscription;
    }

    public static Throwable onObservableError(Throwable error) {
        Func1<Throwable, Throwable> f = onObservableSubscribeError;
        return f != null ? f.call(error) : error;
    }

    public static <T, R> Observable.Operator<R, T> onObservableLift(Observable.Operator<R, T> operator) {
        Func1<Observable.Operator, Observable.Operator> f = onObservableLift;
        return f != null ? f.call(operator) : operator;
    }

    public static Func0<? extends ScheduledExecutorService> getOnGenericScheduledExecutorService() {
        return onGenericScheduledExecutorService;
    }
}
