package org.greenrobot.greendao.identityscope;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface IdentityScope<K, T> {
    void clear();

    boolean detach(K k, T t);

    T get(K k);

    T getNoLock(K k);

    void lock();

    void put(K k, T t);

    void putNoLock(K k, T t);

    void remove(Iterable<K> iterable);

    void remove(K k);

    void reserveRoom(int i);

    void unlock();
}
