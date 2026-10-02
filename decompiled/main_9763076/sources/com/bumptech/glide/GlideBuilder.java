package com.bumptech.glide;

import android.content.Context;
import android.os.Build;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.engine.executor.FifoPriorityThreadPoolExecutor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GlideBuilder {
    private BitmapPool bitmapPool;
    private final Context context;
    private DecodeFormat decodeFormat;
    private DiskCache.Factory diskCacheFactory;
    private ExecutorService diskCacheService;
    private Engine engine;
    private MemoryCache memoryCache;
    private ExecutorService sourceService;

    public GlideBuilder(Context context) {
        this.context = context.getApplicationContext();
    }

    Glide createGlide() {
        if (this.sourceService == null) {
            int cores = Math.max(1, Runtime.getRuntime().availableProcessors());
            this.sourceService = new FifoPriorityThreadPoolExecutor(cores);
        }
        if (this.diskCacheService == null) {
            this.diskCacheService = new FifoPriorityThreadPoolExecutor(1);
        }
        MemorySizeCalculator calculator = new MemorySizeCalculator(this.context);
        if (this.bitmapPool == null) {
            if (Build.VERSION.SDK_INT >= 11) {
                int size = calculator.getBitmapPoolSize();
                this.bitmapPool = new LruBitmapPool(size);
            } else {
                this.bitmapPool = new BitmapPoolAdapter();
            }
        }
        if (this.memoryCache == null) {
            this.memoryCache = new LruResourceCache(calculator.getMemoryCacheSize());
        }
        if (this.diskCacheFactory == null) {
            this.diskCacheFactory = new InternalCacheDiskCacheFactory(this.context);
        }
        if (this.engine == null) {
            this.engine = new Engine(this.memoryCache, this.diskCacheFactory, this.diskCacheService, this.sourceService);
        }
        if (this.decodeFormat == null) {
            this.decodeFormat = DecodeFormat.DEFAULT;
        }
        return new Glide(this.engine, this.memoryCache, this.bitmapPool, this.context, this.decodeFormat);
    }
}
