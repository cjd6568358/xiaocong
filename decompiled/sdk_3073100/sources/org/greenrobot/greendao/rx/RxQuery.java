package org.greenrobot.greendao.rx;

import java.util.List;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.query.LazyList;
import org.greenrobot.greendao.query.Query;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.Exceptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@Experimental
public class RxQuery<T> extends RxBase {
    private final Query<T> query;

    @Override // org.greenrobot.greendao.rx.RxBase
    @Experimental
    public /* bridge */ /* synthetic */ Scheduler getScheduler() {
        return super.getScheduler();
    }

    public RxQuery(Query<T> query) {
        this.query = query;
    }

    public RxQuery(Query<T> query, Scheduler scheduler) {
        super(scheduler);
        this.query = query;
    }

    @Experimental
    public Observable<List<T>> list() {
        return (Observable<List<T>>) wrap(new Callable<List<T>>() { // from class: org.greenrobot.greendao.rx.RxQuery.1
            @Override // java.util.concurrent.Callable
            public List<T> call() throws Exception {
                return RxQuery.this.query.forCurrentThread().list();
            }
        });
    }

    @Experimental
    public Observable<T> unique() {
        return (Observable<T>) wrap(new Callable<T>() { // from class: org.greenrobot.greendao.rx.RxQuery.2
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                return RxQuery.this.query.forCurrentThread().unique();
            }
        });
    }

    public Observable<T> oneByOne() {
        return (Observable<T>) wrap(Observable.create(new Observable.OnSubscribe<T>() { // from class: org.greenrobot.greendao.rx.RxQuery.3
            @Override // rx.functions.Action1
            public void call(Subscriber<? super T> subscriber) throws Throwable {
                try {
                    LazyList<T> lazyListListLazyUncached = RxQuery.this.query.forCurrentThread().listLazyUncached();
                    try {
                        for (T t : lazyListListLazyUncached) {
                            if (subscriber.isUnsubscribed()) {
                                break;
                            } else {
                                subscriber.onNext(t);
                            }
                        }
                        lazyListListLazyUncached.close();
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    } catch (Throwable th) {
                        lazyListListLazyUncached.close();
                        throw th;
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    subscriber.onError(th2);
                }
            }
        }));
    }
}
