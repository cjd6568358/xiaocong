package com.alibaba.sdk.android.httpdns.probe;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface IPProbeService {

    public enum a {
        NO_PROBING,
        PROBING
    }

    a getProbeStatus(String str);

    void launchIPProbeTask(String str, int i, String[] strArr);

    void setIPListUpdateCallback(b bVar);

    boolean stopIPProbeTask(String str);
}
