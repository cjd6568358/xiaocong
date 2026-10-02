package org.greenrobot.greendao.query;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.AbstractQuery;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
abstract class AbstractQueryData<T, Q extends AbstractQuery<T>> {
    final AbstractDao<T, ?> dao;
    final String[] initialValues;
    final Map<Long, WeakReference<Q>> queriesForThreads = new HashMap();
    final String sql;

    protected abstract Q createQuery();

    AbstractQueryData(AbstractDao<T, ?> dao, String sql, String[] initialValues) {
        this.dao = dao;
        this.sql = sql;
        this.initialValues = initialValues;
    }

    Q forCurrentThread(Q q) {
        if (Thread.currentThread() != q.ownerThread) {
            return (Q) forCurrentThread();
        }
        System.arraycopy(this.initialValues, 0, q.parameters, 0, this.initialValues.length);
        return q;
    }

    Q forCurrentThread() {
        Q q;
        long id = Thread.currentThread().getId();
        synchronized (this.queriesForThreads) {
            WeakReference<Q> weakReference = this.queriesForThreads.get(Long.valueOf(id));
            q = weakReference != null ? weakReference.get() : null;
            if (q == null) {
                gc();
                q = (Q) createQuery();
                this.queriesForThreads.put(Long.valueOf(id), new WeakReference<>(q));
            } else {
                System.arraycopy(this.initialValues, 0, q.parameters, 0, this.initialValues.length);
            }
        }
        return q;
    }

    void gc() {
        synchronized (this.queriesForThreads) {
            Iterator<Map.Entry<Long, WeakReference<Q>>> iterator = this.queriesForThreads.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, WeakReference<Q>> entry = iterator.next();
                if (entry.getValue().get() == null) {
                    iterator.remove();
                }
            }
        }
    }
}
