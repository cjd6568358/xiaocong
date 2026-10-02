package com.ixiaocong.smarthome.phone.softap.callback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface XConfigSoftApCallback {
    void httpScanDevice(String str, String str2, String str3);

    void xconfigCoapCallback(int i, String str, String str2);

    void xconfigDeviceCallback(String str, String str2);

    void xconfigErrorCallback(int i, String str);
}
