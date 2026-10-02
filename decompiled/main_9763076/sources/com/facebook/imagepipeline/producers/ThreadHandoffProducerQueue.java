package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ThreadHandoffProducerQueue {
    private final Executor mExecutor;
    private boolean mQueueing = false;
    private final Deque<Runnable> mRunnableList = new ArrayDeque();

    public ThreadHandoffProducerQueue(Executor executor) {
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
    }

    public synchronized void addToQueueOrExecute(Runnable runnable) {
        if (this.mQueueing) {
            this.mRunnableList.add(runnable);
        } else {
            this.mExecutor.execute(runnable);
        }
    }

    public synchronized void remove(Runnable runnable) {
        this.mRunnableList.remove(runnable);
    }
}
