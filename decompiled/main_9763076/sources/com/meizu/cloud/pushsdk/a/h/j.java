package com.meizu.cloud.pushsdk.a.h;

import com.tencent.mm.opensdk.constants.ConstantsAPI;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class j {
    static i a;
    static long b;

    private j() {
    }

    static i a() {
        synchronized (j.class) {
            if (a != null) {
                i iVar = a;
                a = iVar.f;
                iVar.f = null;
                b -= ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLSX;
                return iVar;
            }
            return new i();
        }
    }

    static void a(i iVar) {
        if (iVar.f != null || iVar.g != null) {
            throw new IllegalArgumentException();
        }
        if (!iVar.d) {
            synchronized (j.class) {
                if (b + ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLSX <= 65536) {
                    b += ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLSX;
                    iVar.f = a;
                    iVar.c = 0;
                    iVar.b = 0;
                    a = iVar;
                }
            }
        }
    }
}
