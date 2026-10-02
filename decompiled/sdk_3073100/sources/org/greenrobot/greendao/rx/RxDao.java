package org.greenrobot.greendao.rx;

import java.util.List;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import rx.Observable;
import rx.Scheduler;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@Experimental
public class RxDao<T, K> extends RxBase {
    private final AbstractDao<T, K> dao;

    @Override // org.greenrobot.greendao.rx.RxBase
    @Experimental
    public /* bridge */ /* synthetic */ Scheduler getScheduler() {
        return super.getScheduler();
    }

    @Experimental
    public RxDao(AbstractDao<T, K> dao) {
        this(dao, null);
    }

    @Experimental
    public RxDao(AbstractDao<T, K> dao, Scheduler scheduler) {
        super(scheduler);
        this.dao = dao;
    }

    @Experimental
    public Observable<List<T>> loadAll() {
        return (Observable<List<T>>) wrap(new Callable<List<T>>() { // from class: org.greenrobot.greendao.rx.RxDao.1
            @Override // java.util.concurrent.Callable
            public List<T> call() throws Exception {
                return RxDao.this.dao.loadAll();
            }
        });
    }

    @Experimental
    public Observable<T> load(final K k) {
        return (Observable<T>) wrap(new Callable<T>() { // from class: org.greenrobot.greendao.rx.RxDao.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                return (T) RxDao.this.dao.load(k);
            }
        });
    }

    @Experimental
    public Observable<T> refresh(final T t) {
        return (Observable<T>) wrap(new Callable<T>() { // from class: org.greenrobot.greendao.rx.RxDao.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                RxDao.this.dao.refresh(t);
                return (T) t;
            }
        });
    }

    @Experimental
    public Observable<T> insert(final T t) {
        return (Observable<T>) wrap(new Callable<T>() { // from class: org.greenrobot.greendao.rx.RxDao.4
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                RxDao.this.dao.insert(t);
                return (T) t;
            }
        });
    }

    @Experimental
    public Observable<Iterable<T>> insertInTx(final Iterable<T> iterable) {
        return (Observable<Iterable<T>>) wrap(new Callable<Iterable<T>>() { // from class: org.greenrobot.greendao.rx.RxDao.5
            @Override // java.util.concurrent.Callable
            public Iterable<T> call() throws Exception {
                RxDao.this.dao.insertInTx(iterable);
                return iterable;
            }
        });
    }

    @Experimental
    public Observable<Object[]> insertInTx(final T... entities) {
        return wrap(new Callable<Object[]>() { // from class: org.greenrobot.greendao.rx.RxDao.6
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public Object[] call() throws Exception {
                RxDao.this.dao.insertInTx(entities);
                return entities;
            }
        });
    }

    @Experimental
    public Observable<T> insertOrReplace(final T t) {
        return (Observable<T>) wrap(new Callable<T>() { // from class: org.greenrobot.greendao.rx.RxDao.7
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                RxDao.this.dao.insertOrReplace(t);
                return (T) t;
            }
        });
    }

    @Experimental
    public Observable<Iterable<T>> insertOrReplaceInTx(final Iterable<T> iterable) {
        return (Observable<Iterable<T>>) wrap(new Callable<Iterable<T>>() { // from class: org.greenrobot.greendao.rx.RxDao.8
            @Override // java.util.concurrent.Callable
            public Iterable<T> call() throws Exception {
                RxDao.this.dao.insertOrReplaceInTx(iterable);
                return iterable;
            }
        });
    }

    @Experimental
    public Observable<Object[]> insertOrReplaceInTx(final T... entities) {
        return wrap(new Callable<Object[]>() { // from class: org.greenrobot.greendao.rx.RxDao.9
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public Object[] call() throws Exception {
                RxDao.this.dao.insertOrReplaceInTx(entities);
                return entities;
            }
        });
    }

    @Experimental
    public Observable<T> save(final T t) {
        return (Observable<T>) wrap(new Callable<T>() { // from class: org.greenrobot.greendao.rx.RxDao.10
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                RxDao.this.dao.save(t);
                return (T) t;
            }
        });
    }

    @Experimental
    public Observable<Iterable<T>> saveInTx(final Iterable<T> iterable) {
        return (Observable<Iterable<T>>) wrap(new Callable<Iterable<T>>() { // from class: org.greenrobot.greendao.rx.RxDao.11
            @Override // java.util.concurrent.Callable
            public Iterable<T> call() throws Exception {
                RxDao.this.dao.saveInTx(iterable);
                return iterable;
            }
        });
    }

    @Experimental
    public Observable<Object[]> saveInTx(final T... entities) {
        return wrap(new Callable<Object[]>() { // from class: org.greenrobot.greendao.rx.RxDao.12
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public Object[] call() throws Exception {
                RxDao.this.dao.saveInTx(entities);
                return entities;
            }
        });
    }

    @Experimental
    public Observable<T> update(final T t) {
        return (Observable<T>) wrap(new Callable<T>() { // from class: org.greenrobot.greendao.rx.RxDao.13
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public T call() throws Exception {
                RxDao.this.dao.update(t);
                return (T) t;
            }
        });
    }

    @Experimental
    public Observable<Iterable<T>> updateInTx(final Iterable<T> iterable) {
        return (Observable<Iterable<T>>) wrap(new Callable<Iterable<T>>() { // from class: org.greenrobot.greendao.rx.RxDao.14
            @Override // java.util.concurrent.Callable
            public Iterable<T> call() throws Exception {
                RxDao.this.dao.updateInTx(iterable);
                return iterable;
            }
        });
    }

    @Experimental
    public Observable<Object[]> updateInTx(final T... entities) {
        return wrap(new Callable<Object[]>() { // from class: org.greenrobot.greendao.rx.RxDao.15
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public Object[] call() throws Exception {
                RxDao.this.dao.updateInTx(entities);
                return entities;
            }
        });
    }

    @Experimental
    public Observable<Void> delete(final T entity) {
        return wrap(new Callable<Void>() { // from class: org.greenrobot.greendao.rx.RxDao.16
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                RxDao.this.dao.delete(entity);
                return null;
            }
        });
    }

    @Experimental
    public Observable<Void> deleteByKey(final K key) {
        return wrap(new Callable<Void>() { // from class: org.greenrobot.greendao.rx.RxDao.17
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                RxDao.this.dao.deleteByKey(key);
                return null;
            }
        });
    }

    @Experimental
    public Observable<Void> deleteAll() {
        return wrap(new Callable<Void>() { // from class: org.greenrobot.greendao.rx.RxDao.18
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                RxDao.this.dao.deleteAll();
                return null;
            }
        });
    }

    @Experimental
    public Observable<Void> deleteInTx(final Iterable<T> entities) {
        return wrap(new Callable<Void>() { // from class: org.greenrobot.greendao.rx.RxDao.19
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                RxDao.this.dao.deleteInTx(entities);
                return null;
            }
        });
    }

    @Experimental
    public Observable<Void> deleteInTx(final T... entities) {
        return wrap(new Callable<Void>() { // from class: org.greenrobot.greendao.rx.RxDao.20
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                RxDao.this.dao.deleteInTx(entities);
                return null;
            }
        });
    }

    @Experimental
    public Observable<Void> deleteByKeyInTx(final Iterable<K> keys) {
        return wrap(new Callable<Void>() { // from class: org.greenrobot.greendao.rx.RxDao.21
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                RxDao.this.dao.deleteByKeyInTx(keys);
                return null;
            }
        });
    }

    @Experimental
    public Observable<Void> deleteByKeyInTx(final K... keys) {
        return wrap(new Callable<Void>() { // from class: org.greenrobot.greendao.rx.RxDao.22
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                RxDao.this.dao.deleteByKeyInTx(keys);
                return null;
            }
        });
    }

    @Experimental
    public Observable<Long> count() {
        return wrap(new Callable<Long>() { // from class: org.greenrobot.greendao.rx.RxDao.23
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Long call() throws Exception {
                return Long.valueOf(RxDao.this.dao.count());
            }
        });
    }

    @Experimental
    public AbstractDao<T, K> getDao() {
        return this.dao;
    }
}
