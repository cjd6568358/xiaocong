package com.bumptech.glide.load.engine.prefill;

import android.os.Handler;
import android.os.Looper;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BitmapPreFiller {
    private final BitmapPool bitmapPool;
    private final DecodeFormat defaultFormat;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final MemoryCache memoryCache;

    public BitmapPreFiller(MemoryCache memoryCache, BitmapPool bitmapPool, DecodeFormat defaultFormat) {
        this.memoryCache = memoryCache;
        this.bitmapPool = bitmapPool;
        this.defaultFormat = defaultFormat;
    }
}
