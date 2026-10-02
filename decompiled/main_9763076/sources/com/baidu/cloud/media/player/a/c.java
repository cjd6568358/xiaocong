package com.baidu.cloud.media.player.a;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c {
    private String a;
    private String b;
    private String c;
    private String d;

    public c(Context context) {
        a(context);
    }

    private void a(Context context) {
        try {
            this.b = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.b, 0);
            this.d = packageInfo.versionName;
            this.c = Constants.MAIN_VERSION_TAG + packageInfo.versionCode;
            this.a = (String) packageManager.getApplicationLabel(packageInfo.applicationInfo);
        } catch (Exception e) {
            Log.d("BaseInfo", Constants.MAIN_VERSION_TAG + e.getMessage());
        }
    }
}
