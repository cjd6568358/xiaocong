package com.tencent.android.tpush.service.channel.c;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import com.tencent.android.tpush.stat.a.h;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    private static volatile f c = null;
    private int a = 10;
    private int b;
    private Context d;
    private boolean e;

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

    public boolean a(String str, int i) {
        if (this.e) {
            try {
                return Settings.System.putInt(this.d.getContentResolver(), str, i);
            } catch (Throwable th) {
                int i2 = this.b;
                this.b = i2 + 1;
                if (i2 < this.a) {
                    th.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean a(String str, long j) {
        if (this.e) {
            try {
                return Settings.System.putLong(this.d.getContentResolver(), str, j);
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

    public boolean a(String str, float f) {
        if (this.e) {
            try {
                return Settings.System.putFloat(this.d.getContentResolver(), str, f);
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

    private f(Context context) {
        this.b = 0;
        this.d = null;
        this.e = false;
        this.d = context.getApplicationContext();
        try {
            this.e = h.a(this.d, "android.permission.WRITE_SETTINGS");
            if (this.e && Build.VERSION.SDK_INT >= 23) {
                Method declaredMethod = Settings.System.class.getDeclaredMethod("canWrite", Context.class);
                declaredMethod.setAccessible(true);
                this.e = ((Boolean) declaredMethod.invoke(null, this.d)).booleanValue();
            }
        } catch (Throwable th) {
            int i = this.b;
            this.b = i + 1;
            if (i < this.a) {
                th.printStackTrace();
            }
        }
    }

    public static f a(Context context) {
        if (c == null) {
            synchronized (f.class) {
                if (c == null) {
                    c = new f(context);
                }
            }
        }
        return c;
    }
}
