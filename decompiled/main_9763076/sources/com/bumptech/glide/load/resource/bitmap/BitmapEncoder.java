package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Util;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BitmapEncoder implements ResourceEncoder<Bitmap> {
    private Bitmap.CompressFormat compressFormat;
    private int quality;

    public BitmapEncoder() {
        this(null, 90);
    }

    public BitmapEncoder(Bitmap.CompressFormat compressFormat, int quality) {
        this.compressFormat = compressFormat;
        this.quality = quality;
    }

    @Override // com.bumptech.glide.load.Encoder
    public boolean encode(Resource<Bitmap> resource, OutputStream os) {
        Bitmap bitmap = resource.get();
        long start = LogTime.getLogTime();
        Bitmap.CompressFormat format = getFormat(bitmap);
        bitmap.compress(format, this.quality, os);
        if (Log.isLoggable("BitmapEncoder", 2)) {
            Log.v("BitmapEncoder", "Compressed with type: " + format + " of size " + Util.getBitmapByteSize(bitmap) + " in " + LogTime.getElapsedMillis(start));
            return true;
        }
        return true;
    }

    @Override // com.bumptech.glide.load.Encoder
    public String getId() {
        return "BitmapEncoder.com.bumptech.glide.load.resource.bitmap";
    }

    private Bitmap.CompressFormat getFormat(Bitmap bitmap) {
        if (this.compressFormat != null) {
            return this.compressFormat;
        }
        if (bitmap.hasAlpha()) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.JPEG;
    }
}
