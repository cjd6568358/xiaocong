package com.tencent.android.tpush.stat.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    private static e a = null;
    private Context b;
    private SharedPreferences c;
    private String d = "__QQ_MID_STR__";

    private e(Context context) {
        this.b = null;
        this.c = null;
        this.b = context.getApplicationContext();
        this.c = PreferenceManager.getDefaultSharedPreferences(this.b);
    }

    public void a(String str) {
        if (str == null || !str.equals(a())) {
            this.c.edit().putString(this.d, str).commit();
        }
    }

    public String a() {
        return this.c.getString(this.d, null);
    }

    public static e a(Context context) {
        if (a == null) {
            synchronized (e.class) {
                if (a == null) {
                    a = new e(context);
                }
            }
        }
        return a;
    }
}
