package com.facebook.imagepipeline.cache;

import android.app.ActivityManager;
import android.os.Build;
import com.facebook.common.internal.Supplier;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DefaultBitmapMemoryCacheParamsSupplier implements Supplier<MemoryCacheParams> {
    private final ActivityManager mActivityManager;

    public DefaultBitmapMemoryCacheParamsSupplier(ActivityManager activityManager) {
        this.mActivityManager = activityManager;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.facebook.common.internal.Supplier
    public MemoryCacheParams get() {
        return new MemoryCacheParams(getMaxCacheSize(), 256, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private int getMaxCacheSize() {
        int maxMemory = Math.min(this.mActivityManager.getMemoryClass() * 1048576, Integer.MAX_VALUE);
        if (maxMemory < 33554432) {
            return 4194304;
        }
        if (maxMemory < 67108864) {
            return 6291456;
        }
        if (Build.VERSION.SDK_INT < 11) {
            return 8388608;
        }
        return maxMemory / 4;
    }
}
