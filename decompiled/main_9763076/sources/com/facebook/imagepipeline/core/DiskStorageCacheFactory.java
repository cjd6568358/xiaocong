package com.facebook.imagepipeline.core;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.DiskStorageCache;
import com.facebook.cache.disk.FileCache;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DiskStorageCacheFactory implements FileCacheFactory {
    private DiskStorageFactory mDiskStorageFactory;

    public DiskStorageCacheFactory(DiskStorageFactory diskStorageFactory) {
        this.mDiskStorageFactory = diskStorageFactory;
    }

    public static DiskStorageCache buildDiskStorageCache(DiskCacheConfig diskCacheConfig, DiskStorage diskStorage) {
        DiskStorageCache.Params params = new DiskStorageCache.Params(diskCacheConfig.getMinimumSizeLimit(), diskCacheConfig.getLowDiskSpaceSizeLimit(), diskCacheConfig.getDefaultSizeLimit());
        return new DiskStorageCache(diskStorage, diskCacheConfig.getEntryEvictionComparatorSupplier(), params, diskCacheConfig.getCacheEventListener(), diskCacheConfig.getCacheErrorLogger(), diskCacheConfig.getDiskTrimmableRegistry());
    }

    @Override // com.facebook.imagepipeline.core.FileCacheFactory
    public FileCache get(DiskCacheConfig diskCacheConfig) {
        return buildDiskStorageCache(diskCacheConfig, this.mDiskStorageFactory.get(diskCacheConfig));
    }
}
