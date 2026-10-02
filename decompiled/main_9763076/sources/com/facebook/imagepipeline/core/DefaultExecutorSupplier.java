package com.facebook.imagepipeline.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultExecutorSupplier implements ExecutorSupplier {
    private final Executor mBackgroundExecutor;
    private final Executor mDecodeExecutor;
    private final Executor mIoBoundExecutor;
    private final Executor mLightWeightBackgroundExecutor;

    public DefaultExecutorSupplier(int numCpuBoundThreads) {
        ThreadFactory backgroundPriorityThreadFactory = new PriorityThreadFactory(10);
        this.mIoBoundExecutor = Executors.newFixedThreadPool(2);
        this.mDecodeExecutor = Executors.newFixedThreadPool(numCpuBoundThreads, backgroundPriorityThreadFactory);
        this.mBackgroundExecutor = Executors.newFixedThreadPool(numCpuBoundThreads, backgroundPriorityThreadFactory);
        this.mLightWeightBackgroundExecutor = Executors.newFixedThreadPool(1, backgroundPriorityThreadFactory);
    }

    @Override // com.facebook.imagepipeline.core.ExecutorSupplier
    public Executor forLocalStorageRead() {
        return this.mIoBoundExecutor;
    }

    @Override // com.facebook.imagepipeline.core.ExecutorSupplier
    public Executor forLocalStorageWrite() {
        return this.mIoBoundExecutor;
    }

    @Override // com.facebook.imagepipeline.core.ExecutorSupplier
    public Executor forDecode() {
        return this.mDecodeExecutor;
    }

    @Override // com.facebook.imagepipeline.core.ExecutorSupplier
    public Executor forBackgroundTasks() {
        return this.mBackgroundExecutor;
    }

    @Override // com.facebook.imagepipeline.core.ExecutorSupplier
    public Executor forLightweightBackgroundTasks() {
        return this.mLightWeightBackgroundExecutor;
    }
}
