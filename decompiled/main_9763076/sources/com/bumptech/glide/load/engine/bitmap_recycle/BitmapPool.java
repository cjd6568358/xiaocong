package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface BitmapPool {
    void clearMemory();

    Bitmap get(int i, int i2, Bitmap.Config config);

    Bitmap getDirty(int i, int i2, Bitmap.Config config);

    boolean put(Bitmap bitmap);

    void trimMemory(int i);
}
