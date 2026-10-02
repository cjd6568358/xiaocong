package com.tencent.android.tpush.common;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class s {
    private static volatile s a = null;
    private boolean b;
    private boolean c;
    private int d = -1;

    private s(Context context) {
        this.b = false;
        this.c = false;
        this.b = j.a();
        this.c = com.tencent.android.tpush.c.a.a(context);
    }

    public static s a(Context context) {
        if (a == null) {
            synchronized (s.class) {
                if (a == null) {
                    a = new s(context);
                }
            }
        }
        return a;
    }

    public boolean a() {
        return this.b;
    }

    public boolean b() {
        if (this.d == -1) {
            String str = Build.MANUFACTURER;
            if (!TextUtils.isEmpty(str)) {
                String lowerCase = str.trim().toLowerCase();
                if ("meizu".equals(lowerCase) || "oppo".equals(lowerCase) || "xiaomi".equals(lowerCase) || "vivo".equals(lowerCase) || "huawei".equals(lowerCase) || this.b) {
                    this.d = 1;
                } else {
                    this.d = 0;
                }
            }
        }
        return this.d == 1;
    }

    public boolean c() {
        return this.c;
    }
}
