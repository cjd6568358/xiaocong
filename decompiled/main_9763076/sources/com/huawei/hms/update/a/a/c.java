package com.huawei.hms.update.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: UpdateInfo.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    public int a;
    public String b;
    public int c;
    public String d;

    public c() {
        this.a = 0;
        this.b = Constants.MAIN_VERSION_TAG;
        this.c = 0;
        this.d = Constants.MAIN_VERSION_TAG;
    }

    public c(int i, String str, int i2, String str2) {
        this.a = 0;
        this.b = Constants.MAIN_VERSION_TAG;
        this.c = 0;
        this.d = Constants.MAIN_VERSION_TAG;
        this.a = i;
        this.b = str;
        this.c = i2;
        this.d = str2;
    }

    public void a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.huawei.hms.update.UPDATE_INFO", 0);
        this.a = sharedPreferences.getInt("mNewVersionCode", 0);
        this.b = sharedPreferences.getString("mUri", Constants.MAIN_VERSION_TAG);
        this.c = sharedPreferences.getInt("mSize", 0);
        this.d = sharedPreferences.getString("mHash", Constants.MAIN_VERSION_TAG);
    }

    public void b(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("com.huawei.hms.update.UPDATE_INFO", 0).edit();
        editorEdit.putInt("mNewVersionCode", this.a);
        editorEdit.putString("mUri", this.b);
        editorEdit.putInt("mSize", this.c);
        editorEdit.putString("mHash", this.d);
        editorEdit.commit();
    }

    public void c(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("com.huawei.hms.update.UPDATE_INFO", 0).edit();
        editorEdit.clear();
        editorEdit.commit();
    }

    public boolean a() {
        return this.a > 0 && this.c > 0 && this.b != null && !this.b.isEmpty();
    }
}
