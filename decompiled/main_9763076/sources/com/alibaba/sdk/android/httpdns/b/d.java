package com.alibaba.sdk.android.httpdns.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.baidu.cloud.media.player.BDCloudMediaPlayer;
import com.tencent.android.tpush.common.MessageKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d extends SQLiteOpenHelper {
    private static final Object a = new Object();

    d(Context context) {
        super(context, "aliclound_httpdns.db", (SQLiteDatabase.CursorFactory) null, 1);
    }

    private long a(SQLiteDatabase sQLiteDatabase, g gVar) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("host_id", Long.valueOf(gVar.h));
        contentValues.put(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP, gVar.k);
        contentValues.put(MessageKey.MSG_TTL, gVar.l);
        try {
            return sQLiteDatabase.insert(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP, null, contentValues);
        } catch (Exception e) {
            return 0L;
        }
    }

    private List<g> a(long j) throws Throwable {
        SQLiteDatabase sQLiteDatabase;
        Throwable th;
        Cursor cursor;
        SQLiteDatabase writableDatabase;
        Cursor cursor2 = null;
        ArrayList arrayList = new ArrayList();
        try {
            writableDatabase = getWritableDatabase();
            try {
                try {
                    Cursor cursorRawQuery = writableDatabase.rawQuery("SELECT * FROM " + BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP + " WHERE host_id =? ;", new String[]{String.valueOf(j)});
                    if (cursorRawQuery != null) {
                        try {
                            if (cursorRawQuery.getCount() > 0) {
                                cursorRawQuery.moveToFirst();
                                do {
                                    g gVar = new g();
                                    gVar.id = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("id"));
                                    gVar.h = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("host_id"));
                                    gVar.k = cursorRawQuery.getString(cursorRawQuery.getColumnIndex(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP));
                                    gVar.l = cursorRawQuery.getString(cursorRawQuery.getColumnIndex(MessageKey.MSG_TTL));
                                    arrayList.add(gVar);
                                } while (cursorRawQuery.moveToNext());
                            }
                        } catch (Throwable th2) {
                            sQLiteDatabase = writableDatabase;
                            cursor = cursorRawQuery;
                            th = th2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (sQLiteDatabase == null) {
                                throw th;
                            }
                            sQLiteDatabase.close();
                            throw th;
                        }
                    }
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                } catch (Exception e) {
                    if (0 != 0) {
                        cursor2.close();
                    }
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                }
            } catch (Throwable th3) {
                sQLiteDatabase = writableDatabase;
                cursor = null;
                th = th3;
            }
        } catch (Exception e2) {
            writableDatabase = null;
        } catch (Throwable th4) {
            sQLiteDatabase = null;
            th = th4;
            cursor = null;
        }
        return arrayList;
    }

    private List<g> a(e eVar) {
        return a(eVar.id);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private void m32a(long j) throws Throwable {
        SQLiteDatabase sQLiteDatabase;
        Throwable th;
        SQLiteDatabase sQLiteDatabase2 = null;
        try {
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                try {
                    writableDatabase.delete("host", "id = ?", new String[]{String.valueOf(j)});
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                } catch (Throwable th2) {
                    sQLiteDatabase = writableDatabase;
                    th = th2;
                    if (sQLiteDatabase == null) {
                        throw th;
                    }
                    sQLiteDatabase.close();
                    throw th;
                }
            } catch (Throwable th3) {
                sQLiteDatabase = null;
                th = th3;
            }
        } catch (Exception e) {
            if (0 != 0) {
                sQLiteDatabase2.close();
            }
        }
    }

    private void a(g gVar) throws Throwable {
        b(gVar.id);
    }

    private void b(long j) throws Throwable {
        SQLiteDatabase sQLiteDatabase;
        Throwable th;
        SQLiteDatabase sQLiteDatabase2 = null;
        try {
            try {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                try {
                    writableDatabase.delete(BDCloudMediaPlayer.OnNativeInvokeListener.ARG_IP, "id = ?", new String[]{String.valueOf(j)});
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                } catch (Throwable th2) {
                    sQLiteDatabase = writableDatabase;
                    th = th2;
                    if (sQLiteDatabase == null) {
                        throw th;
                    }
                    sQLiteDatabase.close();
                    throw th;
                }
            } catch (Throwable th3) {
                sQLiteDatabase = null;
                th = th3;
            }
        } catch (Exception e) {
            if (0 != 0) {
                sQLiteDatabase2.close();
            }
        }
    }

    private void c(e eVar) throws Throwable {
        m32a(eVar.id);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    long m33a(e eVar) {
        SQLiteDatabase writableDatabase;
        Throwable th;
        SQLiteDatabase sQLiteDatabase = null;
        synchronized (a) {
            b(eVar.i, eVar.h);
            ContentValues contentValues = new ContentValues();
            try {
                writableDatabase = getWritableDatabase();
                try {
                    writableDatabase.beginTransaction();
                    contentValues.put("host", eVar.h);
                    contentValues.put("sp", eVar.i);
                    contentValues.put("time", c.c(eVar.j));
                    long jInsert = writableDatabase.insert("host", null, contentValues);
                    eVar.id = jInsert;
                    if (eVar.a != null) {
                        for (g gVar : eVar.a) {
                            gVar.h = jInsert;
                            gVar.id = a(writableDatabase, gVar);
                        }
                    }
                    writableDatabase.setTransactionSuccessful();
                    if (writableDatabase != null) {
                        writableDatabase.endTransaction();
                        writableDatabase.close();
                    }
                    return jInsert;
                } catch (Exception e) {
                    sQLiteDatabase = writableDatabase;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.endTransaction();
                        sQLiteDatabase.close();
                    }
                    return 0L;
                } catch (Throwable th2) {
                    th = th2;
                    if (writableDatabase != null) {
                        writableDatabase.endTransaction();
                        writableDatabase.close();
                    }
                    throw th;
                }
            } catch (Exception e2) {
            } catch (Throwable th3) {
                writableDatabase = null;
                th = th3;
            }
        }
    }

    e a(String str, String str2) {
        SQLiteDatabase readableDatabase;
        Throwable th;
        Cursor cursorRawQuery;
        SQLiteDatabase sQLiteDatabase;
        e eVar;
        Cursor cursor = null;
        eVar = null;
        e eVar2 = null;
        cursor = null;
        synchronized (a) {
            try {
                readableDatabase = getReadableDatabase();
                try {
                    cursorRawQuery = readableDatabase.rawQuery("SELECT * FROM host WHERE sp =?  AND host =? ;", new String[]{str, str2});
                    if (cursorRawQuery != null) {
                        try {
                            try {
                                if (cursorRawQuery.getCount() > 0) {
                                    cursorRawQuery.moveToFirst();
                                    e eVar3 = new e();
                                    try {
                                        eVar3.id = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("id"));
                                        eVar3.h = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("host"));
                                        eVar3.i = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("sp"));
                                        eVar3.j = c.d(cursorRawQuery.getString(cursorRawQuery.getColumnIndex("time")));
                                        eVar3.a = (ArrayList) a(eVar3);
                                        eVar2 = eVar3;
                                    } catch (Exception e) {
                                        cursor = cursorRawQuery;
                                        sQLiteDatabase = readableDatabase;
                                        eVar = eVar3;
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        if (sQLiteDatabase != null) {
                                            sQLiteDatabase.close();
                                        }
                                    }
                                }
                            } catch (Exception e2) {
                                sQLiteDatabase = readableDatabase;
                                eVar = null;
                                cursor = cursorRawQuery;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                            }
                            if (readableDatabase != null) {
                                readableDatabase.close();
                            }
                            throw th;
                        }
                    }
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    if (readableDatabase != null) {
                        readableDatabase.close();
                        eVar = eVar2;
                    } else {
                        eVar = eVar2;
                    }
                } catch (Exception e3) {
                    sQLiteDatabase = readableDatabase;
                    eVar = null;
                } catch (Throwable th3) {
                    cursorRawQuery = null;
                    th = th3;
                }
            } catch (Exception e4) {
                sQLiteDatabase = null;
                eVar = null;
            } catch (Throwable th4) {
                readableDatabase = null;
                th = th4;
                cursorRawQuery = null;
            }
        }
        return eVar;
    }

    List<e> b() {
        ArrayList arrayList;
        SQLiteDatabase readableDatabase;
        Throwable th;
        Cursor cursorRawQuery;
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor = null;
        synchronized (a) {
            arrayList = new ArrayList();
            try {
                readableDatabase = getReadableDatabase();
                try {
                    cursorRawQuery = readableDatabase.rawQuery("SELECT * FROM host ; ", null);
                    if (cursorRawQuery != null) {
                        try {
                            if (cursorRawQuery.getCount() > 0) {
                                cursorRawQuery.moveToFirst();
                                do {
                                    e eVar = new e();
                                    eVar.id = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("id"));
                                    eVar.h = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("host"));
                                    eVar.i = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("sp"));
                                    eVar.j = c.d(cursorRawQuery.getString(cursorRawQuery.getColumnIndex("time")));
                                    eVar.a = (ArrayList) a(eVar);
                                    arrayList.add(eVar);
                                } while (cursorRawQuery.moveToNext());
                            }
                        } catch (Exception e) {
                            cursor = cursorRawQuery;
                            sQLiteDatabase = readableDatabase;
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursorRawQuery != null) {
                                cursorRawQuery.close();
                            }
                            if (readableDatabase != null) {
                                readableDatabase.close();
                            }
                            throw th;
                        }
                    }
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    if (readableDatabase != null) {
                        readableDatabase.close();
                    }
                } catch (Exception e2) {
                    sQLiteDatabase = readableDatabase;
                } catch (Throwable th3) {
                    cursorRawQuery = null;
                    th = th3;
                }
            } catch (Exception e3) {
                sQLiteDatabase = null;
            } catch (Throwable th4) {
                readableDatabase = null;
                th = th4;
                cursorRawQuery = null;
            }
        }
        return arrayList;
    }

    void b(String str, String str2) {
        synchronized (a) {
            e eVarA = a(str, str2);
            if (eVarA != null) {
                c(eVarA);
                if (eVarA.a != null) {
                    Iterator<g> it = eVarA.a.iterator();
                    while (it.hasNext()) {
                        a(it.next());
                    }
                }
            }
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE host (id INTEGER PRIMARY KEY,host TEXT,sp TEXT,time TEXT);");
            sQLiteDatabase.execSQL("CREATE TABLE ip (id INTEGER PRIMARY KEY,host_id INTEGER,ip TEXT,ttl TEXT);");
        } catch (Exception e) {
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i != i2) {
            try {
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS host;");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ip;");
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                onCreate(sQLiteDatabase);
            } catch (Exception e) {
            }
        }
    }
}
