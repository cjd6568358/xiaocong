package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.channel.commonutils.android.e;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a {
    private static a a;
    private Context b;
    private C0009a c;

    /* JADX INFO: renamed from: com.xiaomi.mipush.sdk.a$a, reason: collision with other inner class name */
    private class C0009a {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
        public String g;
        public boolean h;
        public boolean i;
        public int j;

        private C0009a() {
            this.h = true;
            this.i = false;
            this.j = 1;
        }

        private String d() {
            return com.xiaomi.channel.commonutils.android.b.a(a.this.b, a.this.b.getPackageName());
        }

        public void a(String str, String str2) {
            this.c = str;
            this.d = str2;
            this.f = e.e(a.this.b);
            this.e = d();
            this.h = true;
            SharedPreferences.Editor editorEdit = a.this.j().edit();
            editorEdit.putString("regId", str);
            editorEdit.putString("regSec", str2);
            editorEdit.putString("devId", this.f);
            editorEdit.putString("vName", d());
            editorEdit.putBoolean("valid", true);
            editorEdit.commit();
        }

        public void a(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.g = str3;
            SharedPreferences.Editor editorEdit = a.this.j().edit();
            editorEdit.putString("appId", this.a);
            editorEdit.putString("appToken", str2);
            editorEdit.putString("regResource", str3);
            editorEdit.commit();
        }

        public void a(boolean z) {
            this.i = z;
        }

        public boolean a() {
            return b(this.a, this.b);
        }

        public void b() {
            a.this.j().edit().clear().commit();
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.f = null;
            this.e = null;
            this.h = false;
            this.i = false;
            this.j = 1;
        }

        public boolean b(String str, String str2) {
            return TextUtils.equals(this.a, str) && TextUtils.equals(this.b, str2) && !TextUtils.isEmpty(this.c) && !TextUtils.isEmpty(this.d) && TextUtils.equals(this.f, e.e(a.this.b));
        }

        public void c() {
            this.h = false;
            a.this.j().edit().putBoolean("valid", this.h).commit();
        }
    }

    private a(Context context) {
        this.b = context;
        o();
    }

    public static a a(Context context) {
        if (a == null) {
            a = new a(context);
        }
        return a;
    }

    private void o() {
        this.c = new C0009a();
        SharedPreferences sharedPreferencesJ = j();
        this.c.a = sharedPreferencesJ.getString("appId", null);
        this.c.b = sharedPreferencesJ.getString("appToken", null);
        this.c.c = sharedPreferencesJ.getString("regId", null);
        this.c.d = sharedPreferencesJ.getString("regSec", null);
        this.c.f = sharedPreferencesJ.getString("devId", null);
        if (!TextUtils.isEmpty(this.c.f) && this.c.f.startsWith("a-")) {
            this.c.f = e.e(this.b);
            sharedPreferencesJ.edit().putString("devId", this.c.f).commit();
        }
        this.c.e = sharedPreferencesJ.getString("vName", null);
        this.c.h = sharedPreferencesJ.getBoolean("valid", true);
        this.c.i = sharedPreferencesJ.getBoolean("paused", false);
        this.c.j = sharedPreferencesJ.getInt("envType", 1);
        this.c.g = sharedPreferencesJ.getString("regResource", null);
    }

    public void a(String str) {
        SharedPreferences.Editor editorEdit = j().edit();
        editorEdit.putString("vName", str);
        editorEdit.commit();
        this.c.e = str;
    }

    public void a(String str, String str2, String str3) {
        this.c.a(str, str2, str3);
    }

    public void a(boolean z) {
        this.c.a(z);
        j().edit().putBoolean("paused", z).commit();
    }

    public void b(String str, String str2) {
        this.c.a(str, str2);
    }

    public boolean b() {
        if (this.c.a()) {
            return true;
        }
        com.xiaomi.channel.commonutils.logger.b.a("Don't send message before initialization succeeded!");
        return false;
    }

    public String c() {
        return this.c.a;
    }

    public String d() {
        return this.c.b;
    }

    public String e() {
        return this.c.c;
    }

    public String f() {
        return this.c.d;
    }

    public void h() {
        this.c.b();
    }

    public boolean i() {
        return this.c.a();
    }

    public SharedPreferences j() {
        return this.b.getSharedPreferences("mipush", 0);
    }

    public void k() {
        this.c.c();
    }

    public boolean l() {
        return this.c.i;
    }

    public int m() {
        return this.c.j;
    }

    public boolean n() {
        return !this.c.h;
    }
}
