package com.facebook.react.flat;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
interface BitmapUpdateListener {
    void onBitmapReady(Bitmap bitmap);

    void onImageLoadEvent(int i);

    void onSecondaryAttach(Bitmap bitmap);
}
