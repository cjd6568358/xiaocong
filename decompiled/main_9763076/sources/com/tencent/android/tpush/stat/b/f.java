package com.tencent.android.tpush.stat.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends h {
    public f(Context context, int i) {
        super(context, i);
    }

    @Override // com.tencent.android.tpush.stat.b.h
    public int a() {
        return 4;
    }

    @Override // com.tencent.android.tpush.stat.b.h
    protected String c() {
        return b(f());
    }

    public String b(String str) {
        String string;
        synchronized (this) {
            if (this.a == null) {
                this.a = com.tencent.android.tpush.stat.a.e.b();
            }
            this.a.b("read mid from sharedPreferences， key=" + str);
            string = PreferenceManager.getDefaultSharedPreferences(this.b).getString(str, null);
        }
        return string;
    }

    @Override // com.tencent.android.tpush.stat.b.h
    protected void a(String str) {
        a(f(), str);
    }

    public void a(String str, String str2) {
        synchronized (this) {
            if (this.a == null) {
                this.a = com.tencent.android.tpush.stat.a.e.b();
            }
            this.a.b("write mid to sharedPreferences " + str);
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.b).edit();
            editorEdit.putString(str, str2);
            editorEdit.commit();
        }
    }

    @Override // com.tencent.android.tpush.stat.b.h
    protected boolean b() {
        return true;
    }
}
