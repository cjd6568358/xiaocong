package com.facebook.react.common.futures;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SimpleSettableFuture<T> implements Future<T> {
    private Exception mException;
    private final CountDownLatch mReadyLatch = new CountDownLatch(1);
    private T mResult;

    public void set(T result) {
        checkNotSet();
        this.mResult = result;
        this.mReadyLatch.countDown();
    }

    public void setException(Exception exception) {
        checkNotSet();
        this.mException = exception;
        this.mReadyLatch.countDown();
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.mReadyLatch.getCount() == 0;
    }

    @Override // java.util.concurrent.Future
    public T get() throws ExecutionException, InterruptedException {
        this.mReadyLatch.await();
        if (this.mException != null) {
            throw new ExecutionException(this.mException);
        }
        return this.mResult;
    }

    @Override // java.util.concurrent.Future
    public T get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        if (!this.mReadyLatch.await(timeout, unit)) {
            throw new TimeoutException("Timed out waiting for result");
        }
        if (this.mException != null) {
            throw new ExecutionException(this.mException);
        }
        return this.mResult;
    }

    public T getOrThrow() {
        try {
            return get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkNotSet() {
        if (this.mReadyLatch.getCount() == 0) {
            throw new RuntimeException("Result has already been set!");
        }
    }
}
