package com.bumptech.glide.load.engine;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum DiskCacheStrategy {
    ALL(true, true),
    NONE(false, false),
    SOURCE(true, false),
    RESULT(false, true);

    private final boolean cacheResult;
    private final boolean cacheSource;

    DiskCacheStrategy(boolean cacheSource, boolean cacheResult) {
        this.cacheSource = cacheSource;
        this.cacheResult = cacheResult;
    }

    public boolean cacheSource() {
        return this.cacheSource;
    }

    public boolean cacheResult() {
        return this.cacheResult;
    }
}
