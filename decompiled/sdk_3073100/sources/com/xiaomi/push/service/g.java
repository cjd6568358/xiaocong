package com.xiaomi.push.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class g {
    private static volatile g a;
    private f b;

    private g(Context context) {
        this.b = new f(context);
    }

    private synchronized Cursor a(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorRawQuery = null;
        synchronized (this) {
            com.xiaomi.channel.commonutils.misc.k.a(false);
            try {
                cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM geoMessage", null);
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            }
        }
        return cursorRawQuery;
    }

    public static g a(Context context) {
        if (a == null) {
            synchronized (g.class) {
                if (a == null) {
                    a = new g(context);
                }
            }
        }
        return a;
    }

    public synchronized int a(String str) {
        int i = 0;
        synchronized (this) {
            com.xiaomi.channel.commonutils.misc.k.a(false);
            if (!TextUtils.isEmpty(str)) {
                try {
                    SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                    int iDelete = writableDatabase.delete("geoMessage", "message_id = ?", new String[]{str});
                    writableDatabase.close();
                    i = iDelete;
                } catch (Exception e) {
                    com.xiaomi.channel.commonutils.logger.b.d(e.toString());
                }
            }
        }
        return i;
    }

    public synchronized ArrayList<com.xiaomi.push.service.module.b> a() {
        ArrayList<com.xiaomi.push.service.module.b> arrayList;
        com.xiaomi.channel.commonutils.misc.k.a(false);
        try {
            SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
            Cursor cursorA = a(writableDatabase);
            arrayList = new ArrayList<>();
            if (cursorA != null) {
                while (cursorA.moveToNext()) {
                    com.xiaomi.push.service.module.b bVar = new com.xiaomi.push.service.module.b();
                    bVar.a(cursorA.getString(cursorA.getColumnIndex("message_id")));
                    bVar.b(cursorA.getString(cursorA.getColumnIndex("geo_id")));
                    bVar.a(cursorA.getBlob(cursorA.getColumnIndex("content")));
                    bVar.a(cursorA.getInt(cursorA.getColumnIndex("action")));
                    bVar.a(cursorA.getLong(cursorA.getColumnIndex("deadline")));
                    arrayList.add(bVar);
                }
                cursorA.close();
            }
            writableDatabase.close();
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            arrayList = null;
        }
        return arrayList;
    }

    public synchronized boolean a(ArrayList<ContentValues> arrayList) {
        boolean z;
        com.xiaomi.channel.commonutils.misc.k.a(false);
        if (arrayList == null || arrayList.size() <= 0) {
            z = false;
        } else {
            try {
                SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                writableDatabase.beginTransaction();
                Iterator<ContentValues> it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = true;
                        break;
                    }
                    if (-1 == writableDatabase.insert("geoMessage", null, it.next())) {
                        z = false;
                        break;
                    }
                }
                if (z) {
                    writableDatabase.setTransactionSuccessful();
                }
                writableDatabase.endTransaction();
                writableDatabase.close();
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.d(e.toString());
                z = false;
            }
        }
        return z;
    }

    public synchronized int b(String str) {
        int i = 0;
        synchronized (this) {
            com.xiaomi.channel.commonutils.misc.k.a(false);
            if (!TextUtils.isEmpty(str)) {
                try {
                    SQLiteDatabase writableDatabase = this.b.getWritableDatabase();
                    int iDelete = writableDatabase.delete("geoMessage", "geo_id = ?", new String[]{str});
                    writableDatabase.close();
                    i = iDelete;
                } catch (Exception e) {
                    com.xiaomi.channel.commonutils.logger.b.d(e.toString());
                }
            }
        }
        return i;
    }

    public synchronized ArrayList<com.xiaomi.push.service.module.b> c(String str) {
        ArrayList<com.xiaomi.push.service.module.b> arrayList;
        com.xiaomi.channel.commonutils.misc.k.a(false);
        if (TextUtils.isEmpty(str)) {
            arrayList = null;
        } else {
            try {
                ArrayList<com.xiaomi.push.service.module.b> arrayListA = a();
                ArrayList<com.xiaomi.push.service.module.b> arrayList2 = new ArrayList<>();
                for (com.xiaomi.push.service.module.b bVar : arrayListA) {
                    if (TextUtils.equals(bVar.c(), str)) {
                        arrayList2.add(bVar);
                    }
                }
                arrayList = arrayList2;
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.d(e.toString());
                arrayList = null;
            }
        }
        return arrayList;
    }
}
