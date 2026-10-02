package com.tencent.wxop.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bc extends SQLiteOpenHelper {
    private String a;
    private Context b;

    public bc(Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 3);
        this.a = "";
        this.b = null;
        this.a = str;
        this.b = context.getApplicationContext();
        if (StatConfig.isDebugEnable()) {
            au.h.i("SQLiteOpenHelper " + this.a);
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x005b  */
    private void a(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Cursor cursorQuery;
        String string = null;
        try {
            cursorQuery = sQLiteDatabase.query("user", null, null, null, null, null, null);
            try {
                try {
                    ContentValues contentValues = new ContentValues();
                    if (cursorQuery.moveToNext()) {
                        string = cursorQuery.getString(0);
                        cursorQuery.getInt(1);
                        cursorQuery.getString(2);
                        cursorQuery.getLong(3);
                        contentValues.put("uid", com.tencent.wxop.stat.common.r.b(string));
                    }
                    if (string != null) {
                        sQLiteDatabase.update("user", contentValues, "uid=?", new String[]{string});
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    au.h.e(th);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x007c  */
    private void b(SQLiteDatabase sQLiteDatabase) throws Throwable {
        Cursor cursorQuery;
        Cursor cursor;
        try {
            cursorQuery = sQLiteDatabase.query("events", null, null, null, null, null, null);
            try {
                ArrayList<bd> arrayList = new ArrayList();
                while (cursorQuery.moveToNext()) {
                    arrayList.add(new bd(cursorQuery.getLong(0), cursorQuery.getString(1), cursorQuery.getInt(2), cursorQuery.getInt(3)));
                }
                ContentValues contentValues = new ContentValues();
                for (bd bdVar : arrayList) {
                    contentValues.put("content", com.tencent.wxop.stat.common.r.b(bdVar.b));
                    sQLiteDatabase.update("events", contentValues, "event_id=?", new String[]{Long.toString(bdVar.a)});
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Throwable th) {
                th = th;
                cursor = cursorQuery;
                try {
                    au.h.e(th);
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursorQuery = cursor;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper, java.lang.AutoCloseable
    public synchronized void close() {
        super.close();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists events(event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, content TEXT, status INTEGER, send_count INTEGER, timestamp LONG)");
        sQLiteDatabase.execSQL("create table if not exists user(uid TEXT PRIMARY KEY, user_type INTEGER, app_ver TEXT, ts INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists config(type INTEGER PRIMARY KEY NOT NULL, content TEXT, md5sum TEXT, version INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
        sQLiteDatabase.execSQL("CREATE INDEX if not exists status_idx ON events(status)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) throws Throwable {
        au.h.debug("upgrade DB from oldVersion " + i + " to newVersion " + i2);
        if (i == 1) {
            sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            a(sQLiteDatabase);
            b(sQLiteDatabase);
        }
        if (i == 2) {
            a(sQLiteDatabase);
            b(sQLiteDatabase);
        }
    }
}
