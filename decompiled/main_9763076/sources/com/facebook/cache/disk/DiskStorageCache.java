package com.facebook.cache.disk;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.common.CacheEventListener;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.MultiCacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.common.disk.DiskTrimmable;
import com.facebook.common.disk.DiskTrimmableRegistry;
import com.facebook.common.logging.FLog;
import com.facebook.common.statfs.StatFsHelper;
import com.facebook.common.time.Clock;
import com.facebook.common.time.SystemClock;
import com.facebook.common.util.SecureHashUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DiskStorageCache implements FileCache, DiskTrimmable {
    private final CacheErrorLogger mCacheErrorLogger;
    private final CacheEventListener mCacheEventListener;
    private long mCacheSizeLimit;
    private final long mCacheSizeLimitMinimum;
    private final Clock mClock;
    private final long mDefaultCacheSizeLimit;
    private final EntryEvictionComparatorSupplier mEntryEvictionComparatorSupplier;
    final Map<CacheKey, String> mIndex;
    private final long mLowDiskSpaceCacheSizeLimit;
    private final DiskStorage mStorage;
    private static final Class<?> TAG = DiskStorageCache.class;
    private static final long FUTURE_TIMESTAMP_THRESHOLD_MS = TimeUnit.HOURS.toMillis(2);
    private static final long FILECACHE_SIZE_UPDATE_PERIOD_MS = TimeUnit.MINUTES.toMillis(30);
    private final Object mLock = new Object();
    private final StatFsHelper mStatFsHelper = StatFsHelper.getInstance();
    private long mCacheSizeLastUpdateTime = -1;
    private final CacheStats mCacheStats = new CacheStats();

    static class CacheStats {
        private boolean mInitialized = false;
        private long mSize = -1;
        private long mCount = -1;

        CacheStats() {
        }

        public synchronized boolean isInitialized() {
            return this.mInitialized;
        }

        public synchronized void reset() {
            this.mInitialized = false;
            this.mCount = -1L;
            this.mSize = -1L;
        }

        public synchronized void set(long size, long count) {
            this.mCount = count;
            this.mSize = size;
            this.mInitialized = true;
        }

        public synchronized void increment(long sizeIncrement, long countIncrement) {
            if (this.mInitialized) {
                this.mSize += sizeIncrement;
                this.mCount += countIncrement;
            }
        }

        public synchronized long getSize() {
            return this.mSize;
        }
    }

    public static class Params {
        public final long mCacheSizeLimitMinimum;
        public final long mDefaultCacheSizeLimit;
        public final long mLowDiskSpaceCacheSizeLimit;

        public Params(long cacheSizeLimitMinimum, long lowDiskSpaceCacheSizeLimit, long defaultCacheSizeLimit) {
            this.mCacheSizeLimitMinimum = cacheSizeLimitMinimum;
            this.mLowDiskSpaceCacheSizeLimit = lowDiskSpaceCacheSizeLimit;
            this.mDefaultCacheSizeLimit = defaultCacheSizeLimit;
        }
    }

    public DiskStorageCache(DiskStorage diskStorage, EntryEvictionComparatorSupplier entryEvictionComparatorSupplier, Params params, CacheEventListener cacheEventListener, CacheErrorLogger cacheErrorLogger, DiskTrimmableRegistry diskTrimmableRegistry) {
        this.mLowDiskSpaceCacheSizeLimit = params.mLowDiskSpaceCacheSizeLimit;
        this.mDefaultCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mCacheSizeLimit = params.mDefaultCacheSizeLimit;
        this.mStorage = diskStorage;
        this.mEntryEvictionComparatorSupplier = entryEvictionComparatorSupplier;
        this.mCacheEventListener = cacheEventListener;
        this.mCacheSizeLimitMinimum = params.mCacheSizeLimitMinimum;
        this.mCacheErrorLogger = cacheErrorLogger;
        if (diskTrimmableRegistry != null) {
            diskTrimmableRegistry.registerDiskTrimmable(this);
        }
        this.mClock = SystemClock.get();
        this.mIndex = new HashMap();
    }

    @Override // com.facebook.cache.disk.FileCache
    public BinaryResource getResource(CacheKey key) {
        BinaryResource resource;
        String resourceId = null;
        SettableCacheEvent cacheEvent = new SettableCacheEvent().setCacheKey(key);
        try {
            synchronized (this.mLock) {
                resource = null;
                try {
                    if (this.mIndex.containsKey(key)) {
                        resourceId = this.mIndex.get(key);
                        cacheEvent.setResourceId(resourceId);
                        resource = this.mStorage.getResource(resourceId, key);
                    } else {
                        List<String> resourceIds = getResourceIds(key);
                        for (int i = 0; i < resourceIds.size(); i++) {
                            resourceId = resourceIds.get(i);
                            cacheEvent.setResourceId(resourceId);
                            resource = this.mStorage.getResource(resourceId, key);
                            if (resource != null) {
                                break;
                            }
                        }
                    }
                    if (resource == null) {
                        this.mCacheEventListener.onMiss(cacheEvent);
                        this.mIndex.remove(key);
                    } else {
                        this.mCacheEventListener.onHit(cacheEvent);
                        this.mIndex.put(key, resourceId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return resource;
        } catch (IOException ioe) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.GENERIC_IO, TAG, "getResource", ioe);
            cacheEvent.setException(ioe);
            this.mCacheEventListener.onReadException(cacheEvent);
            return null;
        }
    }

    private DiskStorage.Inserter startInsert(String resourceId, CacheKey key) throws IOException {
        maybeEvictFilesInCacheDir();
        return this.mStorage.insert(resourceId, key);
    }

    private BinaryResource endInsert(DiskStorage.Inserter inserter, CacheKey key, String resourceId) throws IOException {
        BinaryResource resource;
        synchronized (this.mLock) {
            resource = inserter.commit(key);
            this.mCacheStats.increment(resource.size(), 1L);
            this.mIndex.put(key, resourceId);
        }
        return resource;
    }

    @Override // com.facebook.cache.disk.FileCache
    public BinaryResource insert(CacheKey key, WriterCallback callback) throws IOException {
        String resourceId;
        SettableCacheEvent cacheEvent = new SettableCacheEvent().setCacheKey(key);
        this.mCacheEventListener.onWriteAttempt(cacheEvent);
        synchronized (this.mLock) {
            if (this.mIndex.containsKey(key)) {
                resourceId = this.mIndex.get(key);
            } else {
                resourceId = getFirstResourceId(key);
            }
        }
        cacheEvent.setResourceId(resourceId);
        try {
            DiskStorage.Inserter inserter = startInsert(resourceId, key);
            try {
                inserter.writeData(callback, key);
                BinaryResource resource = endInsert(inserter, key, resourceId);
                cacheEvent.setItemSize(resource.size()).setCacheSize(this.mCacheStats.getSize());
                this.mCacheEventListener.onWriteSuccess(cacheEvent);
                return resource;
            } finally {
                if (!inserter.cleanUp()) {
                    FLog.e(TAG, "Failed to delete temp file");
                }
            }
        } catch (IOException ioe) {
            cacheEvent.setException(ioe);
            this.mCacheEventListener.onWriteException(cacheEvent);
            FLog.e(TAG, "Failed inserting a file into the cache", ioe);
            throw ioe;
        }
    }

    private void maybeEvictFilesInCacheDir() throws IOException {
        synchronized (this.mLock) {
            boolean calculatedRightNow = maybeUpdateFileCacheSize();
            updateFileCacheSizeLimit();
            long cacheSize = this.mCacheStats.getSize();
            if (cacheSize > this.mCacheSizeLimit && !calculatedRightNow) {
                this.mCacheStats.reset();
                maybeUpdateFileCacheSize();
            }
            if (cacheSize > this.mCacheSizeLimit) {
                evictAboveSize((this.mCacheSizeLimit * 9) / 10, CacheEventListener.EvictionReason.CACHE_FULL);
            }
        }
    }

    private void evictAboveSize(long desiredSize, CacheEventListener.EvictionReason reason) throws IOException {
        try {
            Collection<DiskStorage.Entry> entries = getSortedEntries(this.mStorage.getEntries());
            long cacheSizeBeforeClearance = this.mCacheStats.getSize();
            long deleteSize = cacheSizeBeforeClearance - desiredSize;
            int itemCount = 0;
            long sumItemSizes = 0;
            for (DiskStorage.Entry entry : entries) {
                if (sumItemSizes > deleteSize) {
                    break;
                }
                long deletedSize = this.mStorage.remove(entry);
                this.mIndex.values().remove(entry.getId());
                if (deletedSize > 0) {
                    itemCount++;
                    sumItemSizes += deletedSize;
                    this.mCacheEventListener.onEviction(new SettableCacheEvent().setResourceId(entry.getId()).setEvictionReason(reason).setItemSize(deletedSize).setCacheSize(cacheSizeBeforeClearance - sumItemSizes).setCacheLimit(desiredSize));
                }
            }
            this.mCacheStats.increment(-sumItemSizes, -itemCount);
            this.mStorage.purgeUnexpectedResources();
        } catch (IOException ioe) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "evictAboveSize: " + ioe.getMessage(), ioe);
            throw ioe;
        }
    }

    private Collection<DiskStorage.Entry> getSortedEntries(Collection<DiskStorage.Entry> allEntries) {
        long threshold = this.mClock.now() + FUTURE_TIMESTAMP_THRESHOLD_MS;
        ArrayList<DiskStorage.Entry> sortedList = new ArrayList<>(allEntries.size());
        ArrayList<DiskStorage.Entry> listToSort = new ArrayList<>(allEntries.size());
        for (DiskStorage.Entry entry : allEntries) {
            if (entry.getTimestamp() > threshold) {
                sortedList.add(entry);
            } else {
                listToSort.add(entry);
            }
        }
        Collections.sort(listToSort, this.mEntryEvictionComparatorSupplier.get());
        sortedList.addAll(listToSort);
        return sortedList;
    }

    private void updateFileCacheSizeLimit() {
        boolean isAvailableSpaceLowerThanHighLimit = this.mStatFsHelper.testLowDiskSpace(StatFsHelper.StorageType.INTERNAL, this.mDefaultCacheSizeLimit - this.mCacheStats.getSize());
        if (isAvailableSpaceLowerThanHighLimit) {
            this.mCacheSizeLimit = this.mLowDiskSpaceCacheSizeLimit;
        } else {
            this.mCacheSizeLimit = this.mDefaultCacheSizeLimit;
        }
    }

    @Override // com.facebook.cache.disk.FileCache
    public void clearAll() {
        synchronized (this.mLock) {
            try {
                this.mStorage.clearAll();
                this.mIndex.clear();
            } catch (IOException ioe) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.EVICTION, TAG, "clearAll: " + ioe.getMessage(), ioe);
            }
            this.mCacheStats.reset();
        }
    }

    @Override // com.facebook.cache.disk.FileCache
    public boolean hasKeySync(CacheKey key) {
        boolean zContainsKey;
        synchronized (this.mLock) {
            zContainsKey = this.mIndex.containsKey(key);
        }
        return zContainsKey;
    }

    @Override // com.facebook.cache.disk.FileCache
    public boolean hasKey(CacheKey key) {
        boolean retval;
        synchronized (this.mLock) {
            try {
                if (hasKeySync(key)) {
                    retval = true;
                } else {
                    String resourceId = null;
                    retval = false;
                    try {
                        if (this.mIndex.containsKey(key)) {
                            resourceId = this.mIndex.get(key);
                            retval = this.mStorage.contains(resourceId, key);
                        } else {
                            List<String> resourceIds = getResourceIds(key);
                            for (int i = 0; i < resourceIds.size() && !(retval = this.mStorage.contains((resourceId = resourceIds.get(i)), key)); i++) {
                            }
                        }
                        if (retval) {
                            this.mIndex.put(key, resourceId);
                        } else {
                            this.mIndex.remove(key);
                        }
                    } catch (IOException e) {
                        retval = false;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return retval;
    }

    private boolean maybeUpdateFileCacheSize() {
        long now = this.mClock.now();
        if (this.mCacheStats.isInitialized() && this.mCacheSizeLastUpdateTime != -1 && now - this.mCacheSizeLastUpdateTime <= FILECACHE_SIZE_UPDATE_PERIOD_MS) {
            return false;
        }
        calcFileCacheSize();
        this.mCacheSizeLastUpdateTime = now;
        return true;
    }

    private void calcFileCacheSize() {
        long size = 0;
        int count = 0;
        boolean foundFutureTimestamp = false;
        int numFutureFiles = 0;
        int sizeFutureFiles = 0;
        long maxTimeDelta = -1;
        long now = this.mClock.now();
        long timeThreshold = now + FUTURE_TIMESTAMP_THRESHOLD_MS;
        try {
            Collection<DiskStorage.Entry> entries = this.mStorage.getEntries();
            for (DiskStorage.Entry entry : entries) {
                count++;
                size += entry.getSize();
                if (entry.getTimestamp() > timeThreshold) {
                    foundFutureTimestamp = true;
                    numFutureFiles++;
                    sizeFutureFiles = (int) (((long) sizeFutureFiles) + entry.getSize());
                    maxTimeDelta = Math.max(entry.getTimestamp() - now, maxTimeDelta);
                }
            }
            if (foundFutureTimestamp) {
                this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.READ_INVALID_ENTRY, TAG, "Future timestamp found in " + numFutureFiles + " files , with a total size of " + sizeFutureFiles + " bytes, and a maximum time delta of " + maxTimeDelta + "ms", null);
            }
            this.mCacheStats.set(size, count);
        } catch (IOException ioe) {
            this.mCacheErrorLogger.logError(CacheErrorLogger.CacheErrorCategory.GENERIC_IO, TAG, "calcFileCacheSize: " + ioe.getMessage(), ioe);
        }
    }

    private static List<String> getResourceIds(CacheKey key) {
        try {
            if (key instanceof MultiCacheKey) {
                List<CacheKey> keys = ((MultiCacheKey) key).getCacheKeys();
                List<String> ids = new ArrayList<>(keys.size());
                for (int i = 0; i < keys.size(); i++) {
                    ids.add(secureHashKey(keys.get(i)));
                }
                return ids;
            }
            List<String> ids2 = new ArrayList<>(1);
            ids2.add(secureHashKey(key));
            return ids2;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    static String getFirstResourceId(CacheKey key) {
        try {
            if (!(key instanceof MultiCacheKey)) {
                return secureHashKey(key);
            }
            List<CacheKey> keys = ((MultiCacheKey) key).getCacheKeys();
            return secureHashKey(keys.get(0));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String secureHashKey(CacheKey key) throws UnsupportedEncodingException {
        return SecureHashUtil.makeSHA1HashBase64(key.toString().getBytes(HTTP.UTF_8));
    }
}
