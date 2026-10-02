package android.support.v4.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AppOpsManagerCompat {
    public static String permissionToOp(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            return AppOpsManager.permissionToOp(permission);
        }
        return null;
    }

    public static int noteProxyOp(Context context, String op, String proxiedPackageName) {
        if (Build.VERSION.SDK_INT < 23) {
            return 1;
        }
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
        return appOpsManager.noteProxyOp(op, proxiedPackageName);
    }
}
