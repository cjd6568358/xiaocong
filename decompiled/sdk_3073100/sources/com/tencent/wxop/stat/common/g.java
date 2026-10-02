package com.tencent.wxop.stat.common;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class g {
    private static volatile g c = null;
    private int a = 10;
    private int b;
    private Context d;
    private boolean e;

    private g(Context context) {
        this.b = 0;
        this.d = null;
        this.e = false;
        this.d = context.getApplicationContext();
        try {
            this.e = r.a(this.d, "android.permission.WRITE_SETTINGS");
            if (!this.e || Build.VERSION.SDK_INT < 23) {
                return;
            }
            Method declaredMethod = Settings.System.class.getDeclaredMethod("canWrite", Context.class);
            declaredMethod.setAccessible(true);
            this.e = ((Boolean) declaredMethod.invoke(null, this.d)).booleanValue();
        } catch (Throwable th) {
            int i = this.b;
            this.b = i + 1;
            if (i < this.a) {
                th.printStackTrace();
            }
        }
    }

    public static g a(Context context) {
        if (c == null) {
            synchronized (g.class) {
                if (c == null) {
                    c = new g(context);
                }
            }
        }
        return c;
    }

    public String a(String str) {
        try {
            return Settings.System.getString(this.d.getContentResolver(), str);
        } catch (Throwable th) {
            int i = this.b;
            this.b = i + 1;
            if (i < this.a) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public boolean a(String str, String str2) {
        if (this.e) {
            try {
                return Settings.System.putString(this.d.getContentResolver(), str, str2);
            } catch (Throwable th) {
                int i = this.b;
                this.b = i + 1;
                if (i < this.a) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }
}
