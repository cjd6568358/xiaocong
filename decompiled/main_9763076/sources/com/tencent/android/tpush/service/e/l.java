package com.tencent.android.tpush.service.e;

import android.net.wifi.ScanResult;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class l implements Comparable {
    public final String a;
    public final int b;
    public final String c;

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(l lVar) {
        return lVar.b - this.b;
    }

    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NotificationStyle.BASE_STYLE, this.a);
            jSONObject.put("dBm", this.b);
            jSONObject.put("ss", this.c);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("TLocationManager", Constants.MAIN_VERSION_TAG, e);
        }
        return jSONObject;
    }

    public l(ScanResult scanResult) {
        this.a = scanResult.BSSID;
        this.b = scanResult.level;
        this.c = scanResult.SSID;
    }

    public l(String str, int i, String str2) {
        this.a = str;
        this.b = i;
        this.c = str2;
    }
}
