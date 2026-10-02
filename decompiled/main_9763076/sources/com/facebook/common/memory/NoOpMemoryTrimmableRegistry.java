package com.facebook.common.memory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NoOpMemoryTrimmableRegistry implements MemoryTrimmableRegistry {
    private static NoOpMemoryTrimmableRegistry sInstance = null;

    public static synchronized NoOpMemoryTrimmableRegistry getInstance() {
        if (sInstance == null) {
            sInstance = new NoOpMemoryTrimmableRegistry();
        }
        return sInstance;
    }

    @Override // com.facebook.common.memory.MemoryTrimmableRegistry
    public void registerMemoryTrimmable(MemoryTrimmable trimmable) {
    }
}
