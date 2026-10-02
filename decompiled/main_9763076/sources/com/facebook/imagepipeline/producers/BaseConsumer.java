package com.facebook.imagepipeline.producers;

import com.facebook.common.logging.FLog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BaseConsumer<T> implements Consumer<T> {
    private boolean mIsFinished = false;

    protected abstract void onCancellationImpl();

    protected abstract void onFailureImpl(Throwable th);

    protected abstract void onNewResultImpl(T t, boolean z);

    @Override // com.facebook.imagepipeline.producers.Consumer
    public synchronized void onNewResult(T newResult, boolean isLast) {
        if (!this.mIsFinished) {
            this.mIsFinished = isLast;
            try {
                onNewResultImpl(newResult, isLast);
            } catch (Exception e) {
                onUnhandledException(e);
            }
        }
    }

    @Override // com.facebook.imagepipeline.producers.Consumer
    public synchronized void onFailure(Throwable t) {
        if (!this.mIsFinished) {
            this.mIsFinished = true;
            try {
                onFailureImpl(t);
            } catch (Exception e) {
                onUnhandledException(e);
            }
        }
    }

    @Override // com.facebook.imagepipeline.producers.Consumer
    public synchronized void onCancellation() {
        if (!this.mIsFinished) {
            this.mIsFinished = true;
            try {
                onCancellationImpl();
            } catch (Exception e) {
                onUnhandledException(e);
            }
        }
    }

    @Override // com.facebook.imagepipeline.producers.Consumer
    public synchronized void onProgressUpdate(float progress) {
        if (!this.mIsFinished) {
            try {
                onProgressUpdateImpl(progress);
            } catch (Exception e) {
                onUnhandledException(e);
            }
        }
    }

    protected void onProgressUpdateImpl(float progress) {
    }

    protected void onUnhandledException(Exception e) {
        FLog.wtf(getClass(), "unhandled exception", e);
    }
}
