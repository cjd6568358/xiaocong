package com.huawei.hms.api.internal;

import android.content.Context;
import android.os.Build;
import com.huawei.hms.api.HuaweiApiAvailability;

/* JADX INFO: compiled from: HuaweiMobileServicesUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class e {
    public static int a(Context context) {
        com.huawei.hms.c.a.a(context, "context must not be null.");
        if (Build.VERSION.SDK_INT < 15) {
            return 21;
        }
        com.huawei.hms.c.e eVar = new com.huawei.hms.c.e(context);
        com.huawei.hms.c.e.a aVarA = eVar.a(HuaweiApiAvailability.SERVICES_PACKAGE);
        if (com.huawei.hms.c.e.a.NOT_INSTALLED.equals(aVarA)) {
            return 1;
        }
        if (com.huawei.hms.c.e.a.DISABLED.equals(aVarA)) {
            return 3;
        }
        if (!HuaweiApiAvailability.SERVICES_SIGNATURE.equalsIgnoreCase(eVar.d(HuaweiApiAvailability.SERVICES_PACKAGE))) {
            return 9;
        }
        if (eVar.b(HuaweiApiAvailability.SERVICES_PACKAGE) < 20502300) {
            return 2;
        }
        return 0;
    }
}
