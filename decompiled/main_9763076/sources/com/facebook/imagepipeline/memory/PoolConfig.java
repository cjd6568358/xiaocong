package com.facebook.imagepipeline.memory;

import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PoolConfig {
    private final PoolParams mBitmapPoolParams;
    private final PoolStatsTracker mBitmapPoolStatsTracker;
    private final PoolParams mFlexByteArrayPoolParams;
    private final MemoryTrimmableRegistry mMemoryTrimmableRegistry;
    private final PoolParams mNativeMemoryChunkPoolParams;
    private final PoolStatsTracker mNativeMemoryChunkPoolStatsTracker;
    private final PoolParams mSmallByteArrayPoolParams;
    private final PoolStatsTracker mSmallByteArrayPoolStatsTracker;

    private PoolConfig(Builder builder) {
        PoolParams poolParams;
        PoolStatsTracker noOpPoolStatsTracker;
        PoolParams poolParams2;
        MemoryTrimmableRegistry noOpMemoryTrimmableRegistry;
        PoolParams poolParams3;
        PoolStatsTracker noOpPoolStatsTracker2;
        PoolParams poolParams4;
        PoolStatsTracker noOpPoolStatsTracker3;
        if (builder.mBitmapPoolParams == null) {
            poolParams = DefaultBitmapPoolParams.get();
        } else {
            poolParams = builder.mBitmapPoolParams;
        }
        this.mBitmapPoolParams = poolParams;
        if (builder.mBitmapPoolStatsTracker == null) {
            noOpPoolStatsTracker = NoOpPoolStatsTracker.getInstance();
        } else {
            noOpPoolStatsTracker = builder.mBitmapPoolStatsTracker;
        }
        this.mBitmapPoolStatsTracker = noOpPoolStatsTracker;
        if (builder.mFlexByteArrayPoolParams == null) {
            poolParams2 = DefaultFlexByteArrayPoolParams.get();
        } else {
            poolParams2 = builder.mFlexByteArrayPoolParams;
        }
        this.mFlexByteArrayPoolParams = poolParams2;
        if (builder.mMemoryTrimmableRegistry == null) {
            noOpMemoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();
        } else {
            noOpMemoryTrimmableRegistry = builder.mMemoryTrimmableRegistry;
        }
        this.mMemoryTrimmableRegistry = noOpMemoryTrimmableRegistry;
        if (builder.mNativeMemoryChunkPoolParams == null) {
            poolParams3 = DefaultNativeMemoryChunkPoolParams.get();
        } else {
            poolParams3 = builder.mNativeMemoryChunkPoolParams;
        }
        this.mNativeMemoryChunkPoolParams = poolParams3;
        if (builder.mNativeMemoryChunkPoolStatsTracker == null) {
            noOpPoolStatsTracker2 = NoOpPoolStatsTracker.getInstance();
        } else {
            noOpPoolStatsTracker2 = builder.mNativeMemoryChunkPoolStatsTracker;
        }
        this.mNativeMemoryChunkPoolStatsTracker = noOpPoolStatsTracker2;
        if (builder.mSmallByteArrayPoolParams == null) {
            poolParams4 = DefaultByteArrayPoolParams.get();
        } else {
            poolParams4 = builder.mSmallByteArrayPoolParams;
        }
        this.mSmallByteArrayPoolParams = poolParams4;
        if (builder.mSmallByteArrayPoolStatsTracker == null) {
            noOpPoolStatsTracker3 = NoOpPoolStatsTracker.getInstance();
        } else {
            noOpPoolStatsTracker3 = builder.mSmallByteArrayPoolStatsTracker;
        }
        this.mSmallByteArrayPoolStatsTracker = noOpPoolStatsTracker3;
    }

    public PoolParams getBitmapPoolParams() {
        return this.mBitmapPoolParams;
    }

    public PoolStatsTracker getBitmapPoolStatsTracker() {
        return this.mBitmapPoolStatsTracker;
    }

    public MemoryTrimmableRegistry getMemoryTrimmableRegistry() {
        return this.mMemoryTrimmableRegistry;
    }

    public PoolParams getNativeMemoryChunkPoolParams() {
        return this.mNativeMemoryChunkPoolParams;
    }

    public PoolStatsTracker getNativeMemoryChunkPoolStatsTracker() {
        return this.mNativeMemoryChunkPoolStatsTracker;
    }

    public PoolParams getFlexByteArrayPoolParams() {
        return this.mFlexByteArrayPoolParams;
    }

    public PoolParams getSmallByteArrayPoolParams() {
        return this.mSmallByteArrayPoolParams;
    }

    public PoolStatsTracker getSmallByteArrayPoolStatsTracker() {
        return this.mSmallByteArrayPoolStatsTracker;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private PoolParams mBitmapPoolParams;
        private PoolStatsTracker mBitmapPoolStatsTracker;
        private PoolParams mFlexByteArrayPoolParams;
        private MemoryTrimmableRegistry mMemoryTrimmableRegistry;
        private PoolParams mNativeMemoryChunkPoolParams;
        private PoolStatsTracker mNativeMemoryChunkPoolStatsTracker;
        private PoolParams mSmallByteArrayPoolParams;
        private PoolStatsTracker mSmallByteArrayPoolStatsTracker;

        private Builder() {
        }

        public PoolConfig build() {
            return new PoolConfig(this);
        }
    }
}
