package com.facebook.common.executors;

import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class StatefulRunnable<T> implements Runnable {
    protected final AtomicInteger mState = new AtomicInteger(0);

    protected abstract T getResult() throws Exception;

    @Override // java.lang.Runnable
    public final void run() {
        if (this.mState.compareAndSet(0, 1)) {
            try {
                T result = getResult();
                this.mState.set(3);
                try {
                    onSuccess(result);
                } finally {
                    disposeResult(result);
                }
            } catch (Exception e) {
                this.mState.set(4);
                onFailure(e);
            }
        }
    }

    public void cancel() {
        if (this.mState.compareAndSet(0, 2)) {
            onCancellation();
        }
    }

    protected void onSuccess(T result) {
    }

    protected void onFailure(Exception e) {
    }

    protected void onCancellation() {
    }

    protected void disposeResult(T result) {
    }
}
