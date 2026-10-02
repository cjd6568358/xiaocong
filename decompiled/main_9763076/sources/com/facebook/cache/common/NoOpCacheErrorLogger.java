package com.facebook.cache.common;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NoOpCacheErrorLogger implements CacheErrorLogger {
    private static NoOpCacheErrorLogger sInstance = null;

    private NoOpCacheErrorLogger() {
    }

    public static synchronized NoOpCacheErrorLogger getInstance() {
        if (sInstance == null) {
            sInstance = new NoOpCacheErrorLogger();
        }
        return sInstance;
    }

    @Override // com.facebook.cache.common.CacheErrorLogger
    public void logError(CacheErrorLogger.CacheErrorCategory category, Class<?> clazz, String message, Throwable throwable) {
    }
}
