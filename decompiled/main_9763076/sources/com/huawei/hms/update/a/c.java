package com.huawei.hms.update.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: DownloadRecord.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c {
    private String a;
    private int b;
    private String c;
    private int d;

    c() {
    }

    public void a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.huawei.hms.update.DOWNLOAD_RECORD", 0);
        this.a = sharedPreferences.getString("mUri", Constants.MAIN_VERSION_TAG);
        this.b = sharedPreferences.getInt("mSize", 0);
        this.c = sharedPreferences.getString("mHash", Constants.MAIN_VERSION_TAG);
        this.d = sharedPreferences.getInt("mReceived", 0);
    }

    public void a(String str, int i, String str2) {
        this.a = str;
        this.b = i;
        this.c = str2;
        this.d = 0;
    }

    public void a(Context context, int i) {
        this.d = i;
        b(context);
    }

    private void b(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("com.huawei.hms.update.DOWNLOAD_RECORD", 0).edit();
        editorEdit.putString("mUri", this.a);
        editorEdit.putInt("mSize", this.b);
        editorEdit.putString("mHash", this.c);
        editorEdit.putInt("mReceived", this.d);
        editorEdit.commit();
    }

    public int a() {
        return this.b;
    }

    public int b() {
        return this.d;
    }

    public boolean b(String str, int i, String str2) {
        return str != null && str2 != null && this.a != null && this.a.equals(str) && this.b == i && this.c != null && this.c.equals(str2) && this.d <= this.b;
    }
}
