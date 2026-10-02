package com.facebook.imagepipeline.cache;

import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StagingArea {
    private static final Class<?> TAG = StagingArea.class;
    private Map<CacheKey, EncodedImage> mMap = new HashMap();

    private StagingArea() {
    }

    public static StagingArea getInstance() {
        return new StagingArea();
    }

    public synchronized void put(CacheKey key, EncodedImage encodedImage) {
        Preconditions.checkNotNull(key);
        Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
        EncodedImage oldEntry = this.mMap.put(key, EncodedImage.cloneOrNull(encodedImage));
        EncodedImage.closeSafely(oldEntry);
        logStats();
    }

    public void clearAll() {
        List<EncodedImage> old;
        synchronized (this) {
            old = new ArrayList<>(this.mMap.values());
            this.mMap.clear();
        }
        for (int i = 0; i < old.size(); i++) {
            EncodedImage encodedImage = old.get(i);
            if (encodedImage != null) {
                encodedImage.close();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0031 A[Catch: all -> 0x003b, TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0002, B:8:0x001b, B:19:0x0043, B:21:0x0052, B:22:0x005b, B:13:0x0031, B:11:0x0027, B:18:0x003e), top: B:25:0x0002, inners: #0 }] */
    public synchronized boolean remove(CacheKey key, EncodedImage encodedImage) {
        boolean z = false;
        synchronized (this) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(encodedImage);
            Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
            EncodedImage oldValue = this.mMap.get(key);
            if (oldValue != null) {
                CloseableReference<PooledByteBuffer> oldRef = oldValue.getByteBufferRef();
                CloseableReference<PooledByteBuffer> ref = encodedImage.getByteBufferRef();
                if (oldRef == null || ref == null) {
                    CloseableReference.closeSafely(ref);
                    CloseableReference.closeSafely(oldRef);
                    EncodedImage.closeSafely(oldValue);
                } else {
                    try {
                        if (oldRef.get() == ref.get()) {
                            this.mMap.remove(key);
                            CloseableReference.closeSafely(ref);
                            CloseableReference.closeSafely(oldRef);
                            EncodedImage.closeSafely(oldValue);
                            logStats();
                            z = true;
                        } else {
                            CloseableReference.closeSafely(ref);
                            CloseableReference.closeSafely(oldRef);
                            EncodedImage.closeSafely(oldValue);
                        }
                    } catch (Throwable th) {
                        CloseableReference.closeSafely(ref);
                        CloseableReference.closeSafely(oldRef);
                        EncodedImage.closeSafely(oldValue);
                        throw th;
                    }
                }
            }
        }
        return z;
    }

    public synchronized EncodedImage get(CacheKey key) {
        EncodedImage encodedImage;
        Preconditions.checkNotNull(key);
        EncodedImage storedEncodedImage = this.mMap.get(key);
        if (storedEncodedImage != null) {
            try {
                synchronized (storedEncodedImage) {
                    try {
                        if (!EncodedImage.isValid(storedEncodedImage)) {
                            this.mMap.remove(key);
                            FLog.w(TAG, "Found closed reference %d for key %s (%d)", Integer.valueOf(System.identityHashCode(storedEncodedImage)), key.toString(), Integer.valueOf(System.identityHashCode(key)));
                            encodedImage = null;
                        } else {
                            storedEncodedImage = EncodedImage.cloneOrNull(storedEncodedImage);
                            encodedImage = storedEncodedImage;
                        }
                    } catch (Throwable th) {
                        th = th;
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            encodedImage = storedEncodedImage;
        }
        return encodedImage;
    }

    public synchronized boolean containsKey(CacheKey key) {
        boolean z = false;
        synchronized (this) {
            Preconditions.checkNotNull(key);
            if (this.mMap.containsKey(key)) {
                EncodedImage storedEncodedImage = this.mMap.get(key);
                synchronized (storedEncodedImage) {
                    if (EncodedImage.isValid(storedEncodedImage)) {
                        z = true;
                    } else {
                        this.mMap.remove(key);
                        FLog.w(TAG, "Found closed reference %d for key %s (%d)", Integer.valueOf(System.identityHashCode(storedEncodedImage)), key.toString(), Integer.valueOf(System.identityHashCode(key)));
                    }
                }
            }
        }
        return z;
    }

    private synchronized void logStats() {
        FLog.v(TAG, "Count = %d", Integer.valueOf(this.mMap.size()));
    }
}
