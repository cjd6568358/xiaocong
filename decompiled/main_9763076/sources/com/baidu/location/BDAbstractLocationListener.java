package com.baidu.location;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BDAbstractLocationListener {
    public void onConnectHotSpotMessage(String str, int i) {
    }

    public void onLocDiagnosticMessage(int i, int i2, String str) {
    }

    public abstract void onReceiveLocation(BDLocation bDLocation);
}
