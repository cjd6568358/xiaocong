package com.tencent.android.tpush.stat.event;

import com.hzy.tvmao.ir.ac.ACConstants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum EventType {
    PAGE_VIEW(1),
    SESSION_ENV(2),
    ERROR(3),
    CUSTOM(1000),
    ADDITION(1001),
    MONITOR_STAT(1002),
    MTA_GAME_USER(ACConstants.TAG_TEMPERATURE1),
    NETWORK_MONITOR(1004),
    NETWORK_DETECTOR(ACConstants.TAG_WIND_SPEED1),
    LBS(10001);

    private int v;

    EventType(int i) {
        this.v = i;
    }

    public int a() {
        return this.v;
    }
}
