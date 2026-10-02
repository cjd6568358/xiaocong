package org.greenrobot.greendao.rx;

import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Internal;
import rx.Observable;
import rx.functions.Func0;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@Internal
class RxUtils {
    RxUtils() {
    }

    @Internal
    static <T> Observable<T> fromCallable(final Callable<T> callable) {
        return Observable.defer(new Func0<Observable<T>>() { // from class: org.greenrobot.greendao.rx.RxUtils.1
            @Override // rx.functions.Func0, java.util.concurrent.Callable
            public Observable<T> call() {
                try {
                    return Observable.just(callable.call());
                } catch (Exception e) {
                    return Observable.error(e);
                }
            }
        });
    }
}
