package org.greenrobot.greendao.rx;

import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.annotation.apihint.Internal;
import rx.Observable;
import rx.Scheduler;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@Internal
class RxBase {
    protected final Scheduler scheduler;

    RxBase() {
        this.scheduler = null;
    }

    @Experimental
    RxBase(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Experimental
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    protected <R> Observable<R> wrap(Callable<R> callable) {
        return wrap(RxUtils.fromCallable(callable));
    }

    protected <R> Observable<R> wrap(Observable<R> observable) {
        if (this.scheduler != null) {
            return observable.subscribeOn(this.scheduler);
        }
        return observable;
    }
}
