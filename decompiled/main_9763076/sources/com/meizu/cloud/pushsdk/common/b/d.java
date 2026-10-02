package com.meizu.cloud.pushsdk.common.b;

import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d {
    private static e.c<String> a;

    public static synchronized e.c<String> a() {
        if (a == null) {
            a = new e.c<>();
        }
        if (!a.a || TextUtils.isEmpty(a.b)) {
            a = e.a("android.telephony.MzTelephonyManager").b("getDeviceId").a();
        }
        return a;
    }
}
