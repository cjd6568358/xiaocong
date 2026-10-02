package com.tencent.android.tpush.common;

import android.content.ContentValues;
import android.content.Context;
import com.tencent.android.tpush.SettingsContentProvider;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class r {
    Context a;
    private ContentValues b;

    private r(Context context) {
        this.b = new ContentValues();
        this.a = context;
    }

    public void a() {
        try {
            this.a.getContentResolver().insert(SettingsContentProvider.getContentUri(this.a, SettingsContentProvider.KEY, "type"), this.b);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("SettingsPreferences", "apply", th);
        }
    }

    public void b() {
        a();
    }

    public r a(String str, String str2) {
        this.b.put(str, str2);
        return this;
    }

    public r a(String str, long j) {
        this.b.put(str, Long.valueOf(j));
        return this;
    }

    public r a(String str, int i) {
        this.b.put(str, Integer.valueOf(i));
        return this;
    }

    public void a(String str) {
        this.b.putNull(str);
    }
}
