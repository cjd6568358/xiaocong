package com.tencent.mid.api;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a a = null;
    private Context b;
    private SharedPreferences c;
    private String d = "__QQ_MID_STR__";

    private a(Context context) {
        this.b = null;
        this.c = null;
        this.b = context.getApplicationContext();
        this.c = this.b.getSharedPreferences(this.b.getPackageName() + ".mid.world.ro", 0);
    }

    public static a a(Context context) {
        if (a == null) {
            synchronized (a.class) {
                if (a == null) {
                    a = new a(context);
                }
            }
        }
        return a;
    }

    public SharedPreferences a() {
        return this.c;
    }

    public void a(String str) {
        if (str == null || !str.equals(b())) {
            this.c.edit().putString(this.d, str).commit();
        }
    }

    public String b() {
        return this.c.getString(this.d, null);
    }
}
