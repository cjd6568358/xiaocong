package com.bumptech.glide.load.engine.cache;

import android.util.Log;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.Key;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DiskLruCacheWrapper implements DiskCache {
    private static DiskLruCacheWrapper wrapper = null;
    private final File directory;
    private DiskLruCache diskLruCache;
    private final int maxSize;
    private final DiskCacheWriteLocker writeLocker = new DiskCacheWriteLocker();
    private final SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();

    public static synchronized DiskCache get(File directory, int maxSize) {
        if (wrapper == null) {
            wrapper = new DiskLruCacheWrapper(directory, maxSize);
        }
        return wrapper;
    }

    protected DiskLruCacheWrapper(File directory, int maxSize) {
        this.directory = directory;
        this.maxSize = maxSize;
    }

    private synchronized DiskLruCache getDiskCache() throws IOException {
        if (this.diskLruCache == null) {
            this.diskLruCache = DiskLruCache.open(this.directory, 1, 1, this.maxSize);
        }
        return this.diskLruCache;
    }

    @Override // com.bumptech.glide.load.engine.cache.DiskCache
    public File get(Key key) {
        String safeKey = this.safeKeyGenerator.getSafeKey(key);
        try {
            DiskLruCache.Value value = getDiskCache().get(safeKey);
            if (value == null) {
                return null;
            }
            File result = value.getFile(0);
            return result;
        } catch (IOException e) {
            if (!Log.isLoggable("DiskLruCacheWrapper", 5)) {
                return null;
            }
            Log.w("DiskLruCacheWrapper", "Unable to get from disk cache", e);
            return null;
        }
    }

    @Override // com.bumptech.glide.load.engine.cache.DiskCache
    public void put(Key key, DiskCache.Writer writer) {
        String safeKey = this.safeKeyGenerator.getSafeKey(key);
        this.writeLocker.acquire(key);
        try {
            try {
                DiskLruCache.Editor editor = getDiskCache().edit(safeKey);
                if (editor != null) {
                    try {
                        File file = editor.getFile(0);
                        if (writer.write(file)) {
                            editor.commit();
                        }
                        editor.abortUnlessCommitted();
                    } catch (Throwable th) {
                        editor.abortUnlessCommitted();
                        throw th;
                    }
                }
                this.writeLocker.release(key);
            } catch (IOException e) {
                if (Log.isLoggable("DiskLruCacheWrapper", 5)) {
                    Log.w("DiskLruCacheWrapper", "Unable to put to disk cache", e);
                }
                this.writeLocker.release(key);
            }
        } catch (Throwable th2) {
            this.writeLocker.release(key);
            throw th2;
        }
    }

    @Override // com.bumptech.glide.load.engine.cache.DiskCache
    public void delete(Key key) {
        String safeKey = this.safeKeyGenerator.getSafeKey(key);
        try {
            getDiskCache().remove(safeKey);
        } catch (IOException e) {
            if (Log.isLoggable("DiskLruCacheWrapper", 5)) {
                Log.w("DiskLruCacheWrapper", "Unable to delete from disk cache", e);
            }
        }
    }
}
