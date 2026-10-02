package com.facebook.common.time;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SystemClock implements Clock {
    private static final SystemClock INSTANCE = new SystemClock();

    private SystemClock() {
    }

    public static SystemClock get() {
        return INSTANCE;
    }

    @Override // com.facebook.common.time.Clock
    public long now() {
        return System.currentTimeMillis();
    }
}
