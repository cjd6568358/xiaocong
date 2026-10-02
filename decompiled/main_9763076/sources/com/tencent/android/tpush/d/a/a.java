package com.tencent.android.tpush.d.a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends SQLiteOpenHelper {
    public a(Context context) {
        super(context, "xg_message.db", (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        a(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE messagetoshow (_id INTEGER PRIMARY KEY AUTOINCREMENT, msgid INTEGER, message TEXT, time INTEGER, busiid INTEGER, showedtime INTEGER, status INTEGER, UNIQUE (msgid) ON CONFLICT IGNORE);");
    }
}
