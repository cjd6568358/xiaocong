package com.facebook.common.disk;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NoOpDiskTrimmableRegistry implements DiskTrimmableRegistry {
    private static NoOpDiskTrimmableRegistry sInstance = null;

    private NoOpDiskTrimmableRegistry() {
    }

    public static synchronized NoOpDiskTrimmableRegistry getInstance() {
        if (sInstance == null) {
            sInstance = new NoOpDiskTrimmableRegistry();
        }
        return sInstance;
    }

    @Override // com.facebook.common.disk.DiskTrimmableRegistry
    public void registerDiskTrimmable(DiskTrimmable trimmable) {
    }
}
