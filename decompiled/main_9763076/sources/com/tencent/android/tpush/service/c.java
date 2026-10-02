package com.tencent.android.tpush.service;

import android.content.Context;
import android.content.pm.PackageManager;
import com.tencent.android.tpush.service.cache.CacheManager;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ a c;

    c(a aVar, Context context, String str) {
        this.c = aVar;
        this.a = context;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.a.getPackageManager().getApplicationInfo(this.b, 8192);
        } catch (PackageManager.NameNotFoundException e) {
            com.tencent.android.tpush.a.a.c(a.a, "appRemoveHandler check app:" + this.b + " has been removed.");
            CacheManager.removeRegisterInfos(this.b);
            s.a().a(this.b);
        } catch (Throwable th) {
        }
    }
}
