package com.facebook.imagepipeline.memory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface PoolStatsTracker {
    void onAlloc(int i);

    void onFree(int i);

    void onHardCapReached();

    void onSoftCapReached();

    void onValueRelease(int i);

    void onValueReuse(int i);

    void setBasePool(BasePool basePool);
}
