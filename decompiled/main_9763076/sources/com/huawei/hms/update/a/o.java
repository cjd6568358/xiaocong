package com.huawei.hms.update.a;

import android.content.Context;
import com.huawei.hms.api.HuaweiApiAvailability;

/* JADX INFO: compiled from: UpdatePolicy.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class o {
    private final Context a;
    private int b;
    private String c;

    public o(Context context) {
        if (context == null) {
            throw new NullPointerException("context must not be null.");
        }
        this.a = context;
        c();
    }

    public int a() {
        return this.b;
    }

    public String b() {
        return this.c;
    }

    private void c() {
        com.huawei.hms.c.e eVar = new com.huawei.hms.c.e(this.a);
        int iB = eVar.b(HuaweiApiAvailability.SERVICES_PACKAGE);
        String strC = eVar.c(HuaweiApiAvailability.SERVICES_PACKAGE);
        if (iB == 0 || strC.isEmpty() || eVar.a(HuaweiApiAvailability.SERVICES_PACKAGE) == com.huawei.hms.c.e.a.NOT_INSTALLED) {
            this.b = 20101000;
            d();
            return;
        }
        this.b = iB;
        if (strC.endsWith("OVE")) {
            this.c = strC;
            return;
        }
        if (strC.endsWith("EU")) {
            this.c = "2.1.1.0_OVE";
        } else if (iB < 20101302) {
            d();
        } else {
            this.c = strC;
        }
    }

    private void d() {
        if (com.huawei.hms.update.f.a.c(this.a)) {
            this.c = "2.1.1.0";
        } else {
            this.c = "2.1.1.0_OVE";
        }
    }
}
