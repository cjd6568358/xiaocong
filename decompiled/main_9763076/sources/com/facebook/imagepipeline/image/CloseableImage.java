package com.facebook.imagepipeline.image;

import com.facebook.common.logging.FLog;
import java.io.Closeable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class CloseableImage implements ImageInfo, Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    public abstract void close();

    public abstract int getSizeInBytes();

    public abstract boolean isClosed();

    public QualityInfo getQualityInfo() {
        return ImmutableQualityInfo.FULL_QUALITY;
    }

    public boolean isStateful() {
        return false;
    }

    protected void finalize() throws Throwable {
        if (!isClosed()) {
            FLog.w("CloseableImage", "finalize: %s %x still open.", getClass().getSimpleName(), Integer.valueOf(System.identityHashCode(this)));
            try {
                close();
            } finally {
                super.finalize();
            }
        }
    }
}
