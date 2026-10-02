package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapPoolAdapter implements BitmapPool {
    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public boolean put(Bitmap bitmap) {
        return false;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public Bitmap get(int width, int height, Bitmap.Config config) {
        return null;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public Bitmap getDirty(int width, int height, Bitmap.Config config) {
        return null;
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public void clearMemory() {
    }

    @Override // com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
    public void trimMemory(int level) {
    }
}
