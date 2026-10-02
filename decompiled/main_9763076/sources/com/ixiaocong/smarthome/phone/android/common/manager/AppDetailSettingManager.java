package com.ixiaocong.smarthome.phone.android.common.manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import net.sqlcipher.database.SQLiteDatabase;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AppDetailSettingManager {
    public static void getAppDetailSettingIntent(Context context) {
        Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        if (appIntent != null) {
            context.startActivity(appIntent);
            return;
        }
        Intent appIntent2 = context.getPackageManager().getLaunchIntentForPackage("com.oppo.safe");
        if (appIntent2 != null) {
            context.startActivity(appIntent2);
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction("android.intent.action.VIEW");
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }
}
