package com.huawei.hms.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.huawei.hms.activity.BridgeActivity;

/* JADX INFO: compiled from: HuaweiApiAvailabilityImpl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class a extends HuaweiApiAvailability {
    private static final a a = new a();

    private a() {
    }

    public static a a() {
        return a;
    }

    @Override // com.huawei.hms.api.HuaweiApiAvailability
    public int isHuaweiMobileServicesAvailable(Context context) {
        com.huawei.hms.c.a.a(context, "context must not be null.");
        return com.huawei.hms.api.internal.e.a(context);
    }

    @Override // com.huawei.hms.api.HuaweiApiAvailability
    public boolean isUserResolvableError(int i) {
        switch (i) {
            case 1:
            case 2:
            case 6:
                return true;
            case 3:
            case 4:
            case 5:
            default:
                return false;
        }
    }

    @Override // com.huawei.hms.api.HuaweiApiAvailability
    public void resolveError(Activity activity, int i, int i2) {
        com.huawei.hms.c.a.a(activity, "activity must not be null.");
        com.huawei.hms.c.a.a("must be called on ui-thread.");
        com.huawei.hms.support.log.a.b("HuaweiApiAvailabilityImpl", "Enter resolveError, errorCode: " + i);
        switch (i) {
            case 1:
            case 2:
                com.huawei.hms.update.c.a.a(activity, i2);
                break;
            case 6:
                a(activity, com.huawei.hms.api.internal.a.class.getName(), i2);
                break;
        }
    }

    private static void a(Activity activity, String str, int i) {
        Intent intent = new Intent(activity, (Class<?>) BridgeActivity.class);
        intent.putExtra(BridgeActivity.EXTRA_DELEGATE_CLASS_NAME, str);
        activity.startActivityForResult(intent, i);
    }
}
