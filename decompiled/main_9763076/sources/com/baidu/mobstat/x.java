package com.baidu.mobstat;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.android.tpush.common.Constants;
import java.io.Closeable;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
abstract class x implements Closeable {
    private af a;

    public abstract long a(String str, String str2);

    public abstract ArrayList<w> a(int i, int i2);

    public abstract boolean b(long j);

    public x(String str, String str2) {
        ae aeVar = new ae();
        this.a = new af(aeVar, str);
        if (aeVar.getDatabasePath(".confd") != null) {
            a(str2);
        }
    }

    private void a(String str) {
        this.a.a(str);
    }

    public synchronized boolean a() {
        boolean zA;
        try {
            zA = this.a.a();
        } catch (Exception e) {
            bd.b(e);
            zA = false;
        }
        return zA;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        try {
            this.a.close();
        } catch (Exception e) {
            bd.b(e);
        }
    }

    protected int b() {
        return this.a.b();
    }

    protected Cursor a(String str, int i, int i2) {
        return this.a.a(null, null, null, null, null, str + " desc", i2 + ", " + i);
    }

    protected Cursor a(String str, String str2, String str3, int i) {
        return this.a.a(null, str + "=? ", new String[]{str2}, null, null, str3 + " desc", i + Constants.MAIN_VERSION_TAG);
    }

    protected long a(ContentValues contentValues) {
        return this.a.a((String) null, contentValues);
    }

    protected boolean a(long j) {
        return this.a.a("_id=? ", new String[]{new StringBuilder().append(j).append(Constants.MAIN_VERSION_TAG).toString()}) > 0;
    }
}
