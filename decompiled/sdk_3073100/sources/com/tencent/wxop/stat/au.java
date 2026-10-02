package com.tencent.wxop.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.tencent.wxop.stat.common.StatLogger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class au {
    private static StatLogger h = com.tencent.wxop.stat.common.l.b();
    private static Context i = null;
    private static au j = null;
    private bc c;
    private bc d;
    private com.tencent.wxop.stat.common.e e;
    private String f;
    private String g;
    private ConcurrentHashMap<com.tencent.wxop.stat.event.e, String> l;
    volatile int a = 0;
    com.tencent.wxop.stat.common.a b = null;
    private int k = 0;
    private boolean m = false;
    private HashMap<String, String> n = new HashMap<>();

    private au(Context context) {
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = "";
        this.g = "";
        this.l = null;
        try {
            this.e = new com.tencent.wxop.stat.common.e();
            i = context.getApplicationContext();
            this.l = new ConcurrentHashMap<>();
            this.f = com.tencent.wxop.stat.common.l.p(context);
            this.g = "pri_" + com.tencent.wxop.stat.common.l.p(context);
            this.c = new bc(i, this.f);
            this.d = new bc(i, this.g);
            a(true);
            a(false);
            f();
            b(i);
            d();
            j();
        } catch (Throwable th) {
            h.e(th);
        }
    }

    public static au a(Context context) {
        if (j == null) {
            synchronized (au.class) {
                if (j == null) {
                    j = new au(context);
                }
            }
        }
        return j;
    }

    private String a(List<bd> list) {
        StringBuilder sb = new StringBuilder(list.size() * 3);
        sb.append("event_id in (");
        int i2 = 0;
        int size = list.size();
        Iterator<bd> it = list.iterator();
        while (true) {
            int i3 = i2;
            if (!it.hasNext()) {
                sb.append(")");
                return sb.toString();
            }
            sb.append(it.next().a);
            if (i3 != size - 1) {
                sb.append(",");
            }
            i2 = i3 + 1;
        }
    }

    private synchronized void a(int i2, boolean z) {
        try {
            if (this.a > 0 && i2 > 0 && !StatServiceImpl.a()) {
                if (StatConfig.isDebugEnable()) {
                    h.i("Load " + this.a + " unsent events");
                }
                ArrayList arrayList = new ArrayList(i2);
                b(arrayList, i2, z);
                if (arrayList.size() > 0) {
                    if (StatConfig.isDebugEnable()) {
                        h.i("Peek " + arrayList.size() + " unsent events.");
                    }
                    a(arrayList, 2, z);
                    i.b(i).b(arrayList, new ba(this, arrayList, z));
                }
            }
        } catch (Throwable th) {
            h.e(th);
        }
    }

    private void a(com.tencent.wxop.stat.event.e eVar, h hVar, boolean z) {
        long jInsert;
        long j2;
        SQLiteDatabase sQLiteDatabaseC = null;
        try {
            try {
                sQLiteDatabaseC = c(z);
                sQLiteDatabaseC.beginTransaction();
                if (!z && this.a > StatConfig.getMaxStoreEventCount()) {
                    h.warn("Too many events stored in db.");
                    this.a -= this.c.getWritableDatabase().delete("events", "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)", null);
                }
                ContentValues contentValues = new ContentValues();
                String strG = eVar.g();
                if (StatConfig.isDebugEnable()) {
                    h.i("insert 1 event, content:" + strG);
                }
                contentValues.put("content", com.tencent.wxop.stat.common.r.b(strG));
                contentValues.put("send_count", "0");
                contentValues.put("status", Integer.toString(1));
                contentValues.put("timestamp", Long.valueOf(eVar.c()));
                jInsert = sQLiteDatabaseC.insert("events", null, contentValues);
                sQLiteDatabaseC.setTransactionSuccessful();
                if (sQLiteDatabaseC != null) {
                    try {
                        sQLiteDatabaseC.endTransaction();
                        j2 = jInsert;
                    } catch (Throwable th) {
                        h.e(th);
                        j2 = jInsert;
                    }
                } else {
                    j2 = jInsert;
                }
            } catch (Throwable th2) {
                jInsert = -1;
                h.e(th2);
                if (sQLiteDatabaseC != null) {
                    try {
                        sQLiteDatabaseC.endTransaction();
                        j2 = -1;
                    } catch (Throwable th3) {
                        h.e(th3);
                        j2 = -1;
                    }
                }
            }
            if (j2 <= 0) {
                h.error("Failed to store event:" + eVar.g());
                return;
            }
            this.a++;
            if (StatConfig.isDebugEnable()) {
                h.d("directStoreEvent insert event to db, event:" + eVar.g());
            }
            if (hVar != null) {
                hVar.a();
            }
        } catch (Throwable th4) {
            if (sQLiteDatabaseC != null) {
                try {
                    sQLiteDatabaseC.endTransaction();
                } catch (Throwable th5) {
                    h.e(th5);
                }
            }
            throw th4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(List<bd> list, int i2, boolean z) {
        SQLiteDatabase sQLiteDatabaseC;
        String str;
        String str2 = null;
        synchronized (this) {
            if (list.size() != 0) {
                try {
                    int iB = b(z);
                    try {
                        sQLiteDatabaseC = c(z);
                        try {
                            if (i2 == 2) {
                                str = "update events set status=" + i2 + ", send_count=send_count+1  where " + a(list);
                            } else {
                                str = "update events set status=" + i2 + " where " + a(list);
                                str2 = this.k % 3 == 0 ? "delete from events where send_count>" + iB : null;
                                this.k++;
                            }
                            if (StatConfig.isDebugEnable()) {
                                h.i("update sql:" + str);
                            }
                            sQLiteDatabaseC.beginTransaction();
                            sQLiteDatabaseC.execSQL(str);
                            if (str2 != null) {
                                h.i("update for delete sql:" + str2);
                                sQLiteDatabaseC.execSQL(str2);
                                f();
                            }
                            sQLiteDatabaseC.setTransactionSuccessful();
                            if (sQLiteDatabaseC != null) {
                                try {
                                    sQLiteDatabaseC.endTransaction();
                                } catch (Throwable th) {
                                    h.e(th);
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            h.e(th);
                            if (sQLiteDatabaseC != null) {
                                try {
                                    sQLiteDatabaseC.endTransaction();
                                } catch (Throwable th3) {
                                    h.e(th3);
                                }
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        sQLiteDatabaseC = null;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(List<bd> list, boolean z) {
        SQLiteDatabase sQLiteDatabaseC = null;
        synchronized (this) {
            if (list.size() != 0) {
                if (StatConfig.isDebugEnable()) {
                    h.i("Delete " + list.size() + " events, important:" + z);
                }
                StringBuilder sb = new StringBuilder(list.size() * 3);
                sb.append("event_id in (");
                int size = list.size();
                Iterator<bd> it = list.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    sb.append(it.next().a);
                    if (i2 != size - 1) {
                        sb.append(",");
                    }
                    i2++;
                }
                sb.append(")");
                try {
                    try {
                        sQLiteDatabaseC = c(z);
                        sQLiteDatabaseC.beginTransaction();
                        int iDelete = sQLiteDatabaseC.delete("events", sb.toString(), null);
                        if (StatConfig.isDebugEnable()) {
                            h.i("delete " + size + " event " + sb.toString() + ", success delete:" + iDelete);
                        }
                        this.a -= iDelete;
                        sQLiteDatabaseC.setTransactionSuccessful();
                        f();
                        if (sQLiteDatabaseC != null) {
                            try {
                                sQLiteDatabaseC.endTransaction();
                            } catch (Throwable th) {
                                h.e(th);
                            }
                        }
                    } catch (Throwable th2) {
                        h.e(th2);
                        if (sQLiteDatabaseC != null) {
                            try {
                                sQLiteDatabaseC.endTransaction();
                            } catch (Throwable th3) {
                                h.e(th3);
                            }
                        }
                    }
                } catch (Throwable th4) {
                    if (sQLiteDatabaseC != null) {
                        try {
                            sQLiteDatabaseC.endTransaction();
                        } catch (Throwable th5) {
                            h.e(th5);
                        }
                    }
                    throw th4;
                }
            }
        }
    }

    private void a(boolean z) {
        SQLiteDatabase sQLiteDatabaseC = null;
        try {
            sQLiteDatabaseC = c(z);
            sQLiteDatabaseC.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("status", (Integer) 1);
            int iUpdate = sQLiteDatabaseC.update("events", contentValues, "status=?", new String[]{Long.toString(2L)});
            if (StatConfig.isDebugEnable()) {
                h.i("update " + iUpdate + " unsent events.");
            }
            sQLiteDatabaseC.setTransactionSuccessful();
        } catch (Throwable th) {
            h.e(th);
        } finally {
            if (sQLiteDatabaseC != null) {
                try {
                    sQLiteDatabaseC.endTransaction();
                } catch (Throwable th2) {
                    h.e(th2);
                }
            }
        }
    }

    private int b(boolean z) {
        return !z ? StatConfig.getMaxSendRetryCount() : StatConfig.getMaxImportantDataSendRetryCount();
    }

    public static au b() {
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2, boolean z) {
        int iG = i2 == -1 ? !z ? g() : h() : i2;
        if (iG > 0) {
            int sendPeriodMinutes = StatConfig.getSendPeriodMinutes() * 60 * StatConfig.getNumEventsCommitPerSec();
            if (iG > sendPeriodMinutes && sendPeriodMinutes > 0) {
                iG = sendPeriodMinutes;
            }
            int iA = StatConfig.a();
            int i3 = iG / iA;
            int i4 = iG % iA;
            if (StatConfig.isDebugEnable()) {
                h.i("sentStoreEventsByDb sendNumbers=" + iG + ",important=" + z + ",maxSendNumPerFor1Period=" + sendPeriodMinutes + ",maxCount=" + i3 + ",restNumbers=" + i4);
            }
            for (int i5 = 0; i5 < i3; i5++) {
                a(iA, z);
            }
            if (i4 > 0) {
                a(i4, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void b(com.tencent.wxop.stat.event.e eVar, h hVar, boolean z, boolean z2) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            if (StatConfig.m <= 0 || z || z2) {
                a(eVar, hVar, z);
            } else if (StatConfig.m > 0) {
                if (StatConfig.isDebugEnable()) {
                    h.i("cacheEventsInMemory.size():" + this.l.size() + ",numEventsCachedInMemory:" + StatConfig.m + ",numStoredEvents:" + this.a);
                    h.i("cache event:" + eVar.g());
                }
                this.l.put(eVar, "");
                if (this.l.size() >= StatConfig.m) {
                    i();
                }
                if (hVar != null) {
                    if (this.l.size() > 0) {
                        i();
                    }
                    hVar.a();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:36:0x00f2 A[Catch: all -> 0x00ff, TRY_ENTER, TRY_LEAVE, TryCatch #7 {all -> 0x00ff, blocks: (B:18:0x009b, B:19:0x009e, B:28:0x00df, B:29:0x00e2, B:36:0x00f2, B:37:0x00f5, B:38:0x00fe), top: B:60:0x0004 }] */
    public synchronized void b(f fVar) {
        Cursor cursorQuery;
        boolean z;
        long jInsert;
        Cursor cursor = null;
        synchronized (this) {
            try {
                try {
                    try {
                        String strA = fVar.a();
                        String strA2 = com.tencent.wxop.stat.common.l.a(strA);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("content", fVar.b.toString());
                        contentValues.put("md5sum", strA2);
                        fVar.c = strA2;
                        contentValues.put("version", Integer.valueOf(fVar.d));
                        cursorQuery = this.c.getReadableDatabase().query("config", null, null, null, null, null, null);
                        while (true) {
                            try {
                                if (!cursorQuery.moveToNext()) {
                                    z = false;
                                    break;
                                } else if (cursorQuery.getInt(0) == fVar.a) {
                                    z = true;
                                    break;
                                }
                            } catch (Throwable th) {
                                th = th;
                                h.e(th);
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                                try {
                                    this.c.getWritableDatabase().endTransaction();
                                } catch (Exception e) {
                                }
                            }
                        }
                        this.c.getWritableDatabase().beginTransaction();
                        if (true == z) {
                            jInsert = this.c.getWritableDatabase().update("config", contentValues, "type=?", new String[]{Integer.toString(fVar.a)});
                        } else {
                            contentValues.put("type", Integer.valueOf(fVar.a));
                            jInsert = this.c.getWritableDatabase().insert("config", null, contentValues);
                        }
                        if (jInsert == -1) {
                            h.e("Failed to store cfg:" + strA);
                        } else {
                            h.d("Sucessed to store cfg:" + strA);
                        }
                        this.c.getWritableDatabase().setTransactionSuccessful();
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        try {
                            this.c.getWritableDatabase().endTransaction();
                        } catch (Exception e2) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (0 != 0) {
                            cursor.close();
                        }
                        try {
                            this.c.getWritableDatabase().endTransaction();
                        } catch (Exception e3) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (0 != 0) {
                        cursor.close();
                    }
                    this.c.getWritableDatabase().endTransaction();
                    throw th;
                }
            } catch (Throwable th4) {
                throw th4;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0095  */
    private void b(List<bd> list, int i2, boolean z) throws Throwable {
        Cursor cursor;
        Cursor cursorQuery;
        try {
            cursorQuery = d(z).query("events", null, "status=?", new String[]{Integer.toString(1)}, null, null, null, Integer.toString(i2));
            while (cursorQuery.moveToNext()) {
                try {
                    long j2 = cursorQuery.getLong(0);
                    String string = cursorQuery.getString(1);
                    if (!StatConfig.g) {
                        string = com.tencent.wxop.stat.common.r.a(string);
                    }
                    int i3 = cursorQuery.getInt(2);
                    int i4 = cursorQuery.getInt(3);
                    bd bdVar = new bd(j2, string, i3, i4);
                    if (StatConfig.isDebugEnable()) {
                        h.i("peek event, id=" + j2 + ",send_count=" + i4 + ",timestamp=" + cursorQuery.getLong(4));
                    }
                    list.add(bdVar);
                } catch (Throwable th) {
                    th = th;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }

    private SQLiteDatabase c(boolean z) {
        return !z ? this.c.getWritableDatabase() : this.d.getWritableDatabase();
    }

    private SQLiteDatabase d(boolean z) {
        return !z ? this.c.getReadableDatabase() : this.d.getReadableDatabase();
    }

    private void f() {
        this.a = g() + h();
    }

    private int g() {
        return (int) DatabaseUtils.queryNumEntries(this.c.getReadableDatabase(), "events");
    }

    private int h() {
        return (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        SQLiteDatabase writableDatabase = null;
        if (this.m) {
            return;
        }
        synchronized (this.l) {
            if (this.l.size() == 0) {
                return;
            }
            this.m = true;
            if (StatConfig.isDebugEnable()) {
                h.i("insert " + this.l.size() + " events ,numEventsCachedInMemory:" + StatConfig.m + ",numStoredEvents:" + this.a);
            }
            try {
                try {
                    writableDatabase = this.c.getWritableDatabase();
                    writableDatabase.beginTransaction();
                    Iterator<Map.Entry<com.tencent.wxop.stat.event.e, String>> it = this.l.entrySet().iterator();
                    while (it.hasNext()) {
                        com.tencent.wxop.stat.event.e key = it.next().getKey();
                        ContentValues contentValues = new ContentValues();
                        String strG = key.g();
                        if (StatConfig.isDebugEnable()) {
                            h.i("insert content:" + strG);
                        }
                        contentValues.put("content", com.tencent.wxop.stat.common.r.b(strG));
                        contentValues.put("send_count", "0");
                        contentValues.put("status", Integer.toString(1));
                        contentValues.put("timestamp", Long.valueOf(key.c()));
                        writableDatabase.insert("events", null, contentValues);
                        it.remove();
                    }
                    writableDatabase.setTransactionSuccessful();
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.endTransaction();
                            f();
                        } catch (Throwable th) {
                            h.e(th);
                        }
                    }
                } catch (Throwable th2) {
                    if (writableDatabase != null) {
                        try {
                            writableDatabase.endTransaction();
                            f();
                        } catch (Throwable th3) {
                            h.e(th3);
                        }
                    }
                    throw th2;
                }
            } catch (Throwable th4) {
                h.e(th4);
                if (writableDatabase != null) {
                    try {
                        writableDatabase.endTransaction();
                        f();
                    } catch (Throwable th5) {
                        h.e(th5);
                    }
                }
            }
            this.m = false;
            if (StatConfig.isDebugEnable()) {
                h.i("after insert, cacheEventsInMemory.size():" + this.l.size() + ",numEventsCachedInMemory:" + StatConfig.m + ",numStoredEvents:" + this.a);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x003f  */
    private void j() throws Throwable {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = this.c.getReadableDatabase().query("keyvalues", null, null, null, null, null, null);
                while (cursorQuery.moveToNext()) {
                    try {
                        this.n.put(cursorQuery.getString(0), cursorQuery.getString(1));
                    } catch (Throwable th) {
                        th = th;
                        h.e(th);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                            return;
                        }
                        return;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0) {
                cursor.close();
            }
            throw th;
        }
    }

    public int a() {
        return this.a;
    }

    void a(int i2) {
        this.e.a(new bb(this, i2));
    }

    void a(com.tencent.wxop.stat.event.e eVar, h hVar, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.a(new ay(this, eVar, hVar, z, z2));
        }
    }

    void a(f fVar) {
        if (fVar == null) {
            return;
        }
        this.e.a(new az(this, fVar));
    }

    void a(List<bd> list, int i2, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.a(new av(this, list, i2, z, z2));
        }
    }

    void a(List<bd> list, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.a(new aw(this, list, z, z2));
        }
    }

    /* JADX WARN: Code duplicated, block: B:104:0x021b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public synchronized com.tencent.wxop.stat.common.a b(Context context) {
        Cursor cursor;
        Cursor cursorQuery;
        com.tencent.wxop.stat.common.a aVar;
        String strB;
        String str;
        String strC;
        if (this.b != null) {
            aVar = this.b;
        } else {
            try {
                this.c.getWritableDatabase().beginTransaction();
                if (StatConfig.isDebugEnable()) {
                    h.i("try to load user info from db.");
                }
                cursorQuery = this.c.getReadableDatabase().query("user", null, null, null, null, null, null, null);
                boolean z = false;
                try {
                    if (cursorQuery.moveToNext()) {
                        String string = cursorQuery.getString(0);
                        String strA = com.tencent.wxop.stat.common.r.a(string);
                        int i2 = cursorQuery.getInt(1);
                        String string2 = cursorQuery.getString(2);
                        long j2 = cursorQuery.getLong(3);
                        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                        int i3 = (i2 == 1 || com.tencent.wxop.stat.common.l.a(j2 * 1000).equals(com.tencent.wxop.stat.common.l.a(1000 * jCurrentTimeMillis))) ? i2 : 1;
                        int i4 = !string2.equals(com.tencent.wxop.stat.common.l.l(context)) ? i3 | 2 : i3;
                        String[] strArrSplit = strA.split(",");
                        boolean z2 = false;
                        if (strArrSplit == null || strArrSplit.length <= 0) {
                            strB = com.tencent.wxop.stat.common.l.b(context);
                            z2 = true;
                            str = strB;
                        } else {
                            String str2 = strArrSplit[0];
                            if (str2 == null || str2.length() < 11) {
                                String strA2 = com.tencent.wxop.stat.common.r.a(context);
                                if (strA2 == null || strA2.length() <= 10) {
                                    strA2 = str2;
                                } else {
                                    z2 = true;
                                }
                                strB = strA;
                                str = strA2;
                            } else {
                                strB = strA;
                                str = str2;
                            }
                        }
                        if (strArrSplit == null || strArrSplit.length < 2) {
                            strC = com.tencent.wxop.stat.common.l.c(context);
                            if (strC != null && strC.length() > 0) {
                                strB = str + "," + strC;
                                z2 = true;
                            }
                        } else {
                            strC = strArrSplit[1];
                            strB = str + "," + strC;
                        }
                        this.b = new com.tencent.wxop.stat.common.a(str, strC, i4);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("uid", com.tencent.wxop.stat.common.r.b(strB));
                        contentValues.put("user_type", Integer.valueOf(i4));
                        contentValues.put("app_ver", com.tencent.wxop.stat.common.l.l(context));
                        contentValues.put("ts", Long.valueOf(jCurrentTimeMillis));
                        if (z2) {
                            this.c.getWritableDatabase().update("user", contentValues, "uid=?", new String[]{string});
                        }
                        if (i4 != i2) {
                            this.c.getWritableDatabase().replace("user", null, contentValues);
                        }
                        z = true;
                    }
                    if (!z) {
                        String strB2 = com.tencent.wxop.stat.common.l.b(context);
                        String strC2 = com.tencent.wxop.stat.common.l.c(context);
                        String str3 = (strC2 == null || strC2.length() <= 0) ? strB2 : strB2 + "," + strC2;
                        long jCurrentTimeMillis2 = System.currentTimeMillis() / 1000;
                        String strL = com.tencent.wxop.stat.common.l.l(context);
                        ContentValues contentValues2 = new ContentValues();
                        contentValues2.put("uid", com.tencent.wxop.stat.common.r.b(str3));
                        contentValues2.put("user_type", (Integer) 0);
                        contentValues2.put("app_ver", strL);
                        contentValues2.put("ts", Long.valueOf(jCurrentTimeMillis2));
                        this.c.getWritableDatabase().insert("user", null, contentValues2);
                        this.b = new com.tencent.wxop.stat.common.a(strB2, strC2, 0);
                    }
                    this.c.getWritableDatabase().setTransactionSuccessful();
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Throwable th) {
                            h.e(th);
                        }
                    }
                    this.c.getWritableDatabase().endTransaction();
                } catch (Throwable th2) {
                    th = th2;
                    cursor = cursorQuery;
                    try {
                        h.e(th);
                        if (cursor != null) {
                            try {
                                cursor.close();
                            } catch (Throwable th3) {
                                h.e(th3);
                            }
                        }
                        this.c.getWritableDatabase().endTransaction();
                    } catch (Throwable th4) {
                        th = th4;
                        cursorQuery = cursor;
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        this.c.getWritableDatabase().endTransaction();
                        throw th;
                    }
                }
            } catch (Throwable th5) {
                th = th5;
                cursor = null;
            }
            aVar = this.b;
        }
        return aVar;
    }

    void c() {
        if (StatConfig.isEnableStatService()) {
            try {
                this.e.a(new ax(this));
            } catch (Throwable th) {
                h.e(th);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x005b  */
    void d() throws Throwable {
        Cursor cursorQuery;
        try {
            cursorQuery = this.c.getReadableDatabase().query("config", null, null, null, null, null, null);
            while (cursorQuery.moveToNext()) {
                try {
                    try {
                        int i2 = cursorQuery.getInt(0);
                        String string = cursorQuery.getString(1);
                        String string2 = cursorQuery.getString(2);
                        int i3 = cursorQuery.getInt(3);
                        f fVar = new f(i2);
                        fVar.a = i2;
                        fVar.b = new JSONObject(string);
                        fVar.c = string2;
                        fVar.d = i3;
                        StatConfig.a(i, fVar);
                    } catch (Throwable th) {
                        th = th;
                        h.e(th);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                            return;
                        }
                        return;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Throwable th3) {
            th = th3;
            cursorQuery = null;
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }
}
