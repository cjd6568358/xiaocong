package com.alibaba.mtl.log.c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.LogFactory;

/* JADX INFO: compiled from: LogSqliteStore.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements com.alibaba.mtl.log.c.a {
    a a;
    String aa = "SELECT * FROM %s ORDER BY %s ASC LIMIT %s";
    String ab = "SELECT count(*) FROM %s";
    String ac = "DELETE FROM log where _id in ( select _id from log  ORDER BY _id ASC LIMIT %d )";

    protected b(Context context) {
        this.a = new a(context);
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000b  */
    /* JADX WARN: Code duplicated, block: B:87:0x00d4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.alibaba.mtl.log.c.a
    /* JADX INFO: renamed from: a */
    public synchronized boolean mo22a(List<com.alibaba.mtl.log.model.a> list) {
        SQLiteDatabase sQLiteDatabase;
        Throwable th;
        boolean z;
        if (list == null) {
            z = true;
        } else if (list.size() == 0) {
            z = true;
        } else {
            SQLiteDatabase sQLiteDatabase2 = null;
            try {
                try {
                    SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                    try {
                        if (writableDatabase != null) {
                            writableDatabase.beginTransaction();
                            int i = 0;
                            while (true) {
                                try {
                                    if (i >= list.size()) {
                                        z = true;
                                        break;
                                    }
                                    com.alibaba.mtl.log.model.a aVar = list.get(i);
                                    if (aVar != null) {
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("eventId", aVar.T);
                                        contentValues.put(LogFactory.PRIORITY_KEY, aVar.U);
                                        contentValues.put("content", aVar.i());
                                        contentValues.put("time", aVar.W);
                                        contentValues.put("_index", aVar.X);
                                        long jInsert = writableDatabase.insert("log", Constants.MAIN_VERSION_TAG, contentValues);
                                        if (jInsert == -1) {
                                            z = false;
                                            break;
                                        }
                                        i.a("UTSqliteLogStore", "[insert] ", aVar.X, " isSuccess:", true, "ret", Long.valueOf(jInsert));
                                    }
                                    i++;
                                } catch (Throwable th2) {
                                    th = th2;
                                    z = true;
                                    sQLiteDatabase = writableDatabase;
                                    try {
                                        i.a("UTSqliteLogStore", "insert error", th);
                                        com.alibaba.mtl.appmonitor.b.b.m16a(th);
                                        if (sQLiteDatabase != null) {
                                            try {
                                                sQLiteDatabase.setTransactionSuccessful();
                                            } catch (Throwable th3) {
                                            }
                                            try {
                                                sQLiteDatabase.endTransaction();
                                            } catch (Throwable th4) {
                                            }
                                        }
                                        this.a.a(sQLiteDatabase);
                                    } catch (Throwable th5) {
                                        th = th5;
                                        sQLiteDatabase2 = sQLiteDatabase;
                                        if (sQLiteDatabase2 != null) {
                                            try {
                                                sQLiteDatabase2.setTransactionSuccessful();
                                            } catch (Throwable th6) {
                                            }
                                            try {
                                                sQLiteDatabase2.endTransaction();
                                            } catch (Throwable th7) {
                                            }
                                        }
                                        this.a.a(sQLiteDatabase2);
                                        throw th;
                                    }
                                }
                            }
                        } else {
                            i.a("UTSqliteLogStore", "db is null");
                            z = false;
                        }
                        if (writableDatabase != null) {
                            try {
                                writableDatabase.setTransactionSuccessful();
                            } catch (Throwable th8) {
                            }
                            try {
                                writableDatabase.endTransaction();
                            } catch (Throwable th9) {
                            }
                        }
                        this.a.a(writableDatabase);
                    } catch (Throwable th10) {
                        sQLiteDatabase = writableDatabase;
                        th = th10;
                        z = false;
                    }
                } catch (Throwable th11) {
                    th = th11;
                    if (sQLiteDatabase2 != null) {
                        sQLiteDatabase2.setTransactionSuccessful();
                        sQLiteDatabase2.endTransaction();
                    }
                    this.a.a(sQLiteDatabase2);
                    throw th;
                }
            } catch (Throwable th12) {
                sQLiteDatabase = null;
                th = th12;
                z = false;
            }
        }
        return z;
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized int a(List<com.alibaba.mtl.log.model.a> list) {
        boolean z;
        int i;
        boolean z2 = true;
        int i2 = 0;
        synchronized (this) {
            if (list != null) {
                if (list.size() != 0) {
                    SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.beginTransaction();
                            int i3 = 0;
                            int i4 = 0;
                            while (i3 < list.size()) {
                                long jDelete = writableDatabase.delete("log", "_id=?", new String[]{list.get(i3).id + Constants.MAIN_VERSION_TAG});
                                if (jDelete <= 0) {
                                    i.a("UTSqliteLogStore", "[delete]  ", Integer.valueOf(list.get(i3).id), " ret:", Long.valueOf(jDelete));
                                    z = false;
                                    i = i4;
                                } else if ("6005".equalsIgnoreCase(list.get(i3).T)) {
                                    z = z2;
                                    i = i4;
                                } else {
                                    boolean z3 = z2;
                                    i = i4 + 1;
                                    z = z3;
                                }
                                i3++;
                                i4 = i;
                                z2 = z;
                            }
                            try {
                                writableDatabase.setTransactionSuccessful();
                            } catch (Throwable th) {
                            }
                            try {
                                writableDatabase.endTransaction();
                            } catch (Throwable th2) {
                            }
                            this.a.a(writableDatabase);
                            i2 = i4;
                        } catch (Throwable th3) {
                            try {
                                writableDatabase.setTransactionSuccessful();
                            } catch (Throwable th4) {
                            }
                            try {
                                writableDatabase.endTransaction();
                            } catch (Throwable th5) {
                            }
                            this.a.a(writableDatabase);
                            throw th3;
                        }
                    } else {
                        i.a("UTSqliteLogStore", "db is null");
                        z2 = false;
                    }
                    i.a("UTSqliteLogStore", "delete ", Integer.valueOf(list.size()), " isSuccess:", Boolean.valueOf(z2));
                }
            }
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.ArrayList<com.alibaba.mtl.log.model.a>] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    @Override // com.alibaba.mtl.log.c.a
    public synchronized ArrayList<com.alibaba.mtl.log.model.a> a(String str, int i) {
        ?? arrayList;
        Object obj = null;
        obj = null;
        cursorRawQuery = null;
        Cursor cursorRawQuery = null;
        synchronized (this) {
            try {
                try {
                    if (i <= 0) {
                        arrayList = (ArrayList) Collections.EMPTY_LIST;
                    } else {
                        arrayList = new ArrayList(i);
                        try {
                            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                            if (writableDatabase != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("SELECT * FROM ").append("log");
                                if (!TextUtils.isEmpty(str)) {
                                    sb.append(" WHERE ").append(str);
                                }
                                sb.append(" ORDER BY ").append("time").append(" ASC ");
                                sb.append(" LIMIT ").append(i + Constants.MAIN_VERSION_TAG);
                                String string = sb.toString();
                                i.a("UTSqliteLogStore", "sql:" + string);
                                try {
                                    cursorRawQuery = writableDatabase.rawQuery(string, null);
                                    while (cursorRawQuery != null && cursorRawQuery.moveToNext()) {
                                        com.alibaba.mtl.log.model.a aVar = new com.alibaba.mtl.log.model.a();
                                        i.a("UTSqliteLogStore", "pos", Integer.valueOf(cursorRawQuery.getPosition()), "count", Integer.valueOf(cursorRawQuery.getCount()));
                                        aVar.id = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("_id"));
                                        aVar.T = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("eventId"));
                                        aVar.U = cursorRawQuery.getString(cursorRawQuery.getColumnIndex(LogFactory.PRIORITY_KEY));
                                        aVar.k(cursorRawQuery.getString(cursorRawQuery.getColumnIndex("content")));
                                        aVar.W = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("time"));
                                        try {
                                            aVar.X = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("_index"));
                                        } catch (Throwable th) {
                                        }
                                        arrayList.add(aVar);
                                    }
                                } catch (Throwable th2) {
                                    i.a("UTSqliteLogStore", "[get]", th2);
                                } finally {
                                    a(cursorRawQuery);
                                    this.a.a(writableDatabase);
                                }
                            } else {
                                Object[] objArr = {"db is null"};
                                i.a("UTSqliteLogStore", objArr);
                                arrayList = arrayList;
                                obj = objArr;
                            }
                        } catch (Throwable th3) {
                        }
                    }
                } catch (Throwable th4) {
                    arrayList = obj;
                }
            } catch (Throwable th5) {
                throw th5;
            }
        }
        return arrayList;
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized int g() {
        Cursor cursorRawQuery = null;
        int i = 0;
        synchronized (this) {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    cursorRawQuery = writableDatabase.rawQuery(String.format(this.ab, "log"), null);
                    if (cursorRawQuery != null) {
                        cursorRawQuery.moveToFirst();
                        i = cursorRawQuery.getInt(0);
                    }
                    a(cursorRawQuery);
                    this.a.a(writableDatabase);
                } catch (Throwable th) {
                    a(cursorRawQuery);
                    this.a.a(writableDatabase);
                    throw th;
                }
            } else {
                i.a("UTSqliteLogStore", "db is null");
            }
        }
        return i;
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized void clear() {
        SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
        if (writableDatabase != null) {
            writableDatabase.delete("log", null, null);
            this.a.a(writableDatabase);
        }
    }

    /* JADX INFO: compiled from: LogSqliteStore.java */
    class a extends SQLiteOpenHelper {
        private SQLiteDatabase a;
        private AtomicInteger e;

        a(Context context) {
            super(context, "ut.db", (SQLiteDatabase.CursorFactory) null, 2);
            this.e = new AtomicInteger();
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onOpen(SQLiteDatabase db) {
            try {
                b.this.a(db.rawQuery("PRAGMA journal_mode=DELETE", null));
            } catch (Throwable th) {
                b.this.a((Cursor) null);
                throw th;
            }
            super.onOpen(db);
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS log (_id INTEGER PRIMARY KEY AUTOINCREMENT, eventId TEXT,priority TEXT, streamId TEXT, time TEXT, content TEXT, _index TEXT )");
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion == 1 && newVersion == 2) {
                try {
                    db.execSQL("ALTER TABLE log ADD COLUMN _index TEXT ");
                } catch (Throwable th) {
                    i.a("UTSqliteLogStore", "DB Upgrade Error", th);
                }
            }
        }

        @Override // android.database.sqlite.SQLiteOpenHelper
        public synchronized SQLiteDatabase getWritableDatabase() {
            try {
                if (this.e.incrementAndGet() == 1) {
                    this.a = super.getWritableDatabase();
                }
            } catch (Throwable th) {
                i.a("TAG", "e", th);
                com.alibaba.mtl.appmonitor.b.b.m16a(th);
            }
            return this.a;
        }

        public synchronized void a(SQLiteDatabase sQLiteDatabase) {
            if (sQLiteDatabase != null) {
                try {
                    if (this.e.decrementAndGet() == 0 && this.a != null) {
                        this.a.close();
                        this.a = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.alibaba.mtl.log.c.a
    public synchronized void c(String str, String str2) {
        SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
        if (writableDatabase != null) {
            try {
                writableDatabase.delete("log", str + " < ?", new String[]{String.valueOf(str2)});
                this.a.a(writableDatabase);
            } catch (Throwable th) {
                this.a.a(writableDatabase);
                throw th;
            }
        } else {
            i.a("UTSqliteLogStore", "db is null");
        }
    }

    @Override // com.alibaba.mtl.log.c.a
    public void e(int i) {
        if (i > 0) {
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    writableDatabase.execSQL(String.format(this.ac, Integer.valueOf(i)));
                    return;
                } catch (Throwable th) {
                    return;
                } finally {
                    this.a.a(writableDatabase);
                }
            }
            i.a("UTSqliteLogStore", "db is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable th) {
            }
        }
    }
}
