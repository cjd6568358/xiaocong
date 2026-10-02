package com.facebook.react.common;

import android.support.v4.util.Pools;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ClearableSynchronizedPool<T> implements Pools.Pool<T> {
    private final Object[] mPool;
    private int mSize = 0;

    public ClearableSynchronizedPool(int maxSize) {
        this.mPool = new Object[maxSize];
    }

    @Override // android.support.v4.util.Pools.Pool
    public synchronized T acquire() {
        T t = null;
        synchronized (this) {
            if (this.mSize != 0) {
                this.mSize--;
                int i = this.mSize;
                t = (T) this.mPool[i];
                this.mPool[i] = null;
            }
        }
        return t;
    }

    @Override // android.support.v4.util.Pools.Pool
    public synchronized boolean release(T obj) {
        boolean z;
        if (this.mSize == this.mPool.length) {
            z = false;
        } else {
            this.mPool[this.mSize] = obj;
            this.mSize++;
            z = true;
        }
        return z;
    }

    public synchronized void clear() {
        for (int i = 0; i < this.mSize; i++) {
            this.mPool[i] = null;
        }
        this.mSize = 0;
    }
}
