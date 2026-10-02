package com.alibaba.sdk.android.utils.crashdefend;

import android.util.Log;

/* JADX INFO: compiled from: CrashDefendSDKInfo.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d implements Cloneable {
    public int a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public long f97a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public String f99a;
    public int b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    public long f100b;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    public String f101b;

    /* JADX INFO: renamed from: c, reason: collision with other field name */
    public long f102c;
    public int crashCount;
    public volatile int c = 0;
    public int d = 0;
    public volatile boolean e = false;
    public boolean f = false;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    public SDKMessageCallback f98a = null;

    public Object clone() {
        try {
            return (d) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("CrashSDK", "clone fail:", e);
            return null;
        }
    }
}
