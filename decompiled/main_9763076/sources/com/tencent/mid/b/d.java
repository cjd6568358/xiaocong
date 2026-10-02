package com.tencent.mid.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d extends f {
    public d(Context context, int i) {
        super(context, i);
    }

    @Override // com.tencent.mid.b.f
    public int a() {
        return 4;
    }

    @Override // com.tencent.mid.b.f
    protected void a(a aVar) {
        synchronized (this) {
            b.b("write CheckEntity to sharedPreferences:" + aVar.toString());
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.c).edit();
            editorEdit.putString(g(), aVar.toString());
            editorEdit.commit();
        }
    }

    @Override // com.tencent.mid.b.f
    protected void a(String str) {
        a(h(), str);
    }

    public void a(String str, String str2) {
        synchronized (this) {
            b.b("write mid to sharedPreferences");
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.c).edit();
            editorEdit.putString(str, str2);
            editorEdit.commit();
        }
    }

    public String b(String str) {
        String string;
        synchronized (this) {
            b.b("read mid from sharedPreferences， key=" + str);
            string = PreferenceManager.getDefaultSharedPreferences(this.c).getString(str, null);
        }
        return string;
    }

    @Override // com.tencent.mid.b.f
    protected boolean b() {
        return true;
    }

    @Override // com.tencent.mid.b.f
    protected String c() {
        return b(h());
    }

    @Override // com.tencent.mid.b.f
    protected a d() {
        a aVar;
        synchronized (this) {
            aVar = new a(PreferenceManager.getDefaultSharedPreferences(this.c).getString(g(), null));
            b.b("read CheckEntity from sharedPreferences:" + aVar.toString());
        }
        return aVar;
    }
}
