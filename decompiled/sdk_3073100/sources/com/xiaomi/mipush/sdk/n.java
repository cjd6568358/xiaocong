package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class n implements Runnable {
    final /* synthetic */ String[] a;
    final /* synthetic */ Context b;

    n(String[] strArr, Context context) {
        this.a = strArr;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        PackageInfo packageInfo;
        try {
            for (String str : this.a) {
                if (!TextUtils.isEmpty(str) && (packageInfo = this.b.getPackageManager().getPackageInfo(str, 4)) != null) {
                    MiPushClient.awakePushServiceByPackageInfo(this.b, packageInfo);
                }
            }
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
        }
    }
}
