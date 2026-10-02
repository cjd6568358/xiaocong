package com.huawei.hms.update.e;

import android.app.Activity;
import java.util.HashMap;

/* JADX INFO: compiled from: AbsUpdateWizard.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    abstract void a(b bVar);

    abstract void b(b bVar);

    abstract Activity c();

    protected void a(int i, int i2) {
        Activity activityC = c();
        if (activityC != null && !activityC.isFinishing()) {
            HashMap map = new HashMap();
            map.put("package", activityC.getPackageName());
            map.put("sdk_ver", String.valueOf(20502300));
            map.put("app_id", com.huawei.hms.c.g.a(activityC));
            map.put("trigger_api", com.huawei.hms.update.c.a.b());
            map.put("hms_ver", String.valueOf(com.huawei.hms.update.c.a.a()));
            map.put("update_type", String.valueOf(i2));
            map.put("net_type", String.valueOf(com.huawei.hms.c.d.a(activityC)));
            map.put("result", String.valueOf(i));
            com.huawei.hms.support.b.a.a().a(activityC, "HMS_SDK_UPDATE", map);
        }
    }
}
