package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LruBitmapPool implements BitmapPool {
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.ARGB_8888;
    private final Set<Bitmap.Config> allowedConfigs;
    private int currentSize;
    private int evictions;
    private int hits;
    private final int initialMaxSize;
    private int maxSize;
    private int misses;
    private int puts;
    private final LruPoolStrategy strategy;
    private final BitmapTracker tracker;

    private interface BitmapTracker {
        void add(Bitmap bitmap);

        void remove(Bitmap bitmap);
    }

    LruBitmapPool(int maxSize, LruPoolStrategy strategy, Set<Bitmap.Config> allowedConfigs) {
        this.initialMaxSize = maxSize;
        this.maxSize = maxSize;
        this.strategy = strategy;
        this.allowedConfigs = allowedConfigs;
        this.tracker = new NullBitmapTracker();
    }

    public LruBitmapPool(int maxSize) {
        this(maxSize, getDefaultStrategy(), getDefaultAllowedConfigs());
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public synchronized boolean put(Bitmap bitmap) {
        boolean z;
        try {
            if (bitmap == null) {
                throw new NullPointerException("Bitmap must not be null");
            }
            if (!bitmap.isMutable() || this.strategy.getSize(bitmap) > this.maxSize || !this.allowedConfigs.contains(bitmap.getConfig())) {
                if (Log.isLoggable("LruBitmapPool", 2)) {
                    Log.v("LruBitmapPool", "Reject bitmap from pool, bitmap: " + this.strategy.logBitmap(bitmap) + ", is mutable: " + bitmap.isMutable() + ", is allowed config: " + this.allowedConfigs.contains(bitmap.getConfig()));
                }
                z = false;
            } else {
                int size = this.strategy.getSize(bitmap);
                this.strategy.put(bitmap);
                this.tracker.add(bitmap);
                this.puts++;
                this.currentSize += size;
                if (Log.isLoggable("LruBitmapPool", 2)) {
                    Log.v("LruBitmapPool", "Put bitmap in pool=" + this.strategy.logBitmap(bitmap));
                }
                dump();
                evict();
                z = true;
            }
        } catch (Throwable th) {
            throw th;
        }
        return z;
    }

    private void evict() {
        trimToSize(this.maxSize);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public synchronized Bitmap get(int width, int height, Bitmap.Config config) {
        Bitmap result;
        result = getDirty(width, height, config);
        if (result != null) {
            result.eraseColor(0);
        }
        return result;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    @TargetApi(12)
    public synchronized Bitmap getDirty(int width, int height, Bitmap.Config config) {
        Bitmap result;
        result = this.strategy.get(width, height, config != null ? config : DEFAULT_CONFIG);
        if (result == null) {
            if (Log.isLoggable("LruBitmapPool", 3)) {
                Log.d("LruBitmapPool", "Missing bitmap=" + this.strategy.logBitmap(width, height, config));
            }
            this.misses++;
        } else {
            this.hits++;
            this.currentSize -= this.strategy.getSize(result);
            this.tracker.remove(result);
            if (Build.VERSION.SDK_INT >= 12) {
                result.setHasAlpha(true);
            }
        }
        if (Log.isLoggable("LruBitmapPool", 2)) {
            Log.v("LruBitmapPool", "Get bitmap=" + this.strategy.logBitmap(width, height, config));
        }
        dump();
        return result;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public void clearMemory() {
        if (Log.isLoggable("LruBitmapPool", 3)) {
            Log.d("LruBitmapPool", "clearMemory");
        }
        trimToSize(0);
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    @SuppressLint({"InlinedApi"})
    public void trimMemory(int level) {
        if (Log.isLoggable("LruBitmapPool", 3)) {
            Log.d("LruBitmapPool", "trimMemory, level=" + level);
        }
        if (level >= 60) {
            clearMemory();
        } else if (level >= 40) {
            trimToSize(this.maxSize / 2);
        }
    }

    private synchronized void trimToSize(int size) {
        while (this.currentSize > size) {
            Bitmap removed = this.strategy.removeLast();
            if (removed == null) {
                if (Log.isLoggable("LruBitmapPool", 5)) {
                    Log.w("LruBitmapPool", "Size mismatch, resetting");
                    dumpUnchecked();
                }
                this.currentSize = 0;
                break;
            }
            this.tracker.remove(removed);
            this.currentSize -= this.strategy.getSize(removed);
            removed.recycle();
            this.evictions++;
            if (Log.isLoggable("LruBitmapPool", 3)) {
                Log.d("LruBitmapPool", "Evicting bitmap=" + this.strategy.logBitmap(removed));
            }
            dump();
        }
    }

    private void dump() {
        if (Log.isLoggable("LruBitmapPool", 2)) {
            dumpUnchecked();
        }
    }

    private void dumpUnchecked() {
        Log.v("LruBitmapPool", "Hits=" + this.hits + ", misses=" + this.misses + ", puts=" + this.puts + ", evictions=" + this.evictions + ", currentSize=" + this.currentSize + ", maxSize=" + this.maxSize + "\nStrategy=" + this.strategy);
    }

    private static LruPoolStrategy getDefaultStrategy() {
        if (Build.VERSION.SDK_INT >= 19) {
            LruPoolStrategy strategy = new SizeConfigStrategy();
            return strategy;
        }
        LruPoolStrategy strategy2 = new AttributeStrategy();
        return strategy2;
    }

    private static Set<Bitmap.Config> getDefaultAllowedConfigs() {
        Set<Bitmap.Config> configs = new HashSet<>();
        configs.addAll(Arrays.asList(Bitmap.Config.values()));
        if (Build.VERSION.SDK_INT >= 19) {
            configs.add(null);
        }
        return Collections.unmodifiableSet(configs);
    }

    private static class NullBitmapTracker implements BitmapTracker {
        private NullBitmapTracker() {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool.BitmapTracker
        public void add(Bitmap bitmap) {
        }

        @Override // com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool.BitmapTracker
        public void remove(Bitmap bitmap) {
        }
    }
}
