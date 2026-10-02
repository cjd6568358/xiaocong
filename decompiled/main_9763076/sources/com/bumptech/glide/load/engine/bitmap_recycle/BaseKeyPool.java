package com.bumptech.glide.load.engine.bitmap_recycle;

import com.bumptech.glide.load.engine.bitmap_recycle.Poolable;
import com.bumptech.glide.util.Util;
import java.util.Queue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class BaseKeyPool<T extends Poolable> {
    private final Queue<T> keyPool = Util.createQueue(20);

    protected abstract T create();

    BaseKeyPool() {
    }

    protected T get() {
        T tPoll = this.keyPool.poll();
        if (tPoll == null) {
            return (T) create();
        }
        return tPoll;
    }

    public void offer(T key) {
        if (this.keyPool.size() < 20) {
            this.keyPool.offer(key);
        }
    }
}
