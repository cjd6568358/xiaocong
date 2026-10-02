package com.xiaomi.push.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.xmpush.thrift.l;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class e {
    private static volatile e b;
    private static String c = "GeoFenceDao.";
    private f a;

    private e(Context context) {
        this.a = new f(context);
    }

    private synchronized Cursor a(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorRawQuery = null;
        synchronized (this) {
            com.xiaomi.channel.commonutils.misc.k.a(false);
            try {
                cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM geofence", null);
            } catch (Exception e) {
            }
        }
        return cursorRawQuery;
    }

    public static e a(Context context) {
        if (b == null) {
            synchronized (e.class) {
                if (b == null) {
                    b = new e(context);
                }
            }
        }
        return b;
    }

    private synchronized com.xiaomi.xmpush.thrift.k a(Cursor cursor) {
        com.xiaomi.xmpush.thrift.k kVar;
        try {
            com.xiaomi.xmpush.thrift.k[] kVarArrValues = com.xiaomi.xmpush.thrift.k.values();
            int length = kVarArrValues.length;
            for (int i = 0; i < length; i++) {
                kVar = kVarArrValues[i];
                if (!TextUtils.equals(cursor.getString(cursor.getColumnIndex("type")), kVar.name())) {
                }
            }
            kVar = null;
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            kVar = null;
        }
        return kVar;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000b A[Catch: all -> 0x0067, TRY_LEAVE, TryCatch #1 {, blocks: (B:5:0x0004, B:11:0x0026, B:12:0x002b, B:13:0x002f, B:15:0x0035, B:17:0x003d, B:22:0x0062, B:20:0x0059, B:7:0x000b), top: B:29:0x0004, inners: #0 }] */
    private synchronized String a(List<l> list) {
        String string;
        if (list == null) {
            com.xiaomi.channel.commonutils.logger.b.a(c + " points unvalidated");
            string = null;
        } else if (list.size() < 3) {
            com.xiaomi.channel.commonutils.logger.b.a(c + " points unvalidated");
            string = null;
        } else {
            JSONArray jSONArray = new JSONArray();
            try {
                for (l lVar : list) {
                    if (lVar != null) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("point_lantitude", lVar.c());
                        jSONObject.put("point_longtitude", lVar.a());
                        jSONArray.put(jSONObject);
                    }
                }
                string = jSONArray.toString();
            } catch (JSONException e) {
                com.xiaomi.channel.commonutils.logger.b.d(e.toString());
                string = null;
            }
        }
        return string;
    }

    private synchronized l b(Cursor cursor) {
        l lVar;
        lVar = new l();
        try {
            lVar.b(Double.parseDouble(cursor.getString(cursor.getColumnIndex("center_lantitude"))));
            lVar.a(Double.parseDouble(cursor.getString(cursor.getColumnIndex("center_longtitude"))));
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            lVar = null;
        }
        return lVar;
    }

    private synchronized ArrayList<l> c(Cursor cursor) {
        ArrayList<l> arrayList;
        ArrayList<l> arrayList2 = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(cursor.getString(cursor.getColumnIndex("polygon_points")));
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= jSONArray.length()) {
                    break;
                }
                l lVar = new l();
                JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                lVar.b(jSONObject.getDouble("point_lantitude"));
                lVar.a(jSONObject.getDouble("point_longtitude"));
                arrayList2.add(lVar);
                i = i2 + 1;
            }
            arrayList = arrayList2;
        } catch (JSONException e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            arrayList = null;
        }
        return arrayList;
    }

    private synchronized com.xiaomi.xmpush.thrift.h d(Cursor cursor) {
        com.xiaomi.xmpush.thrift.h hVarValueOf;
        try {
            hVarValueOf = com.xiaomi.xmpush.thrift.h.valueOf(cursor.getString(cursor.getColumnIndex("coordinate_provider")));
        } catch (IllegalArgumentException e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            hVarValueOf = null;
        }
        return hVarValueOf;
    }

    public synchronized int a(String str, String str2) {
        int i = 0;
        synchronized (this) {
            com.xiaomi.channel.commonutils.misc.k.a(false);
            try {
                if ("Enter".equals(str2) || "Leave".equals(str2) || "Unknown".equals(str2)) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("current_status", str2);
                    SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                    int iUpdate = writableDatabase.update("geofence", contentValues, "id=?", new String[]{str});
                    writableDatabase.close();
                    i = iUpdate;
                }
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            }
        }
        return i;
    }

    public synchronized long a(com.xiaomi.xmpush.thrift.j jVar) {
        long jInsert;
        com.xiaomi.channel.commonutils.misc.k.a(false);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", jVar.a());
            contentValues.put("appId", Long.valueOf(jVar.e()));
            contentValues.put("name", jVar.c());
            contentValues.put("package_name", jVar.g());
            contentValues.put("create_time", Long.valueOf(jVar.i()));
            contentValues.put("type", jVar.k().name());
            contentValues.put("center_longtitude", String.valueOf(jVar.m().a()));
            contentValues.put("center_lantitude", String.valueOf(jVar.m().c()));
            contentValues.put("circle_radius", Double.valueOf(jVar.o()));
            contentValues.put("polygon_point", a(jVar.q()));
            contentValues.put("coordinate_provider", jVar.s().name());
            contentValues.put("current_status", "Unknown");
            SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
            jInsert = writableDatabase.insert("geofence", null, contentValues);
            writableDatabase.close();
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            jInsert = -1;
        }
        return jInsert;
    }

    public synchronized com.xiaomi.xmpush.thrift.j a(String str) {
        com.xiaomi.xmpush.thrift.j next;
        com.xiaomi.channel.commonutils.misc.k.a(false);
        try {
            Iterator<com.xiaomi.xmpush.thrift.j> it = a().iterator();
            while (it.hasNext()) {
                next = it.next();
                if (TextUtils.equals(next.a(), str)) {
                }
            }
            next = null;
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            next = null;
        }
        return next;
    }

    public synchronized ArrayList<com.xiaomi.xmpush.thrift.j> a() {
        ArrayList<com.xiaomi.xmpush.thrift.j> arrayList;
        try {
            com.xiaomi.channel.commonutils.misc.k.a(false);
            try {
                SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                Cursor cursorA = a(writableDatabase);
                arrayList = new ArrayList<>();
                if (cursorA != null) {
                    while (cursorA.moveToNext()) {
                        try {
                            com.xiaomi.xmpush.thrift.j jVar = new com.xiaomi.xmpush.thrift.j();
                            jVar.a(cursorA.getString(cursorA.getColumnIndex("id")));
                            jVar.b(cursorA.getString(cursorA.getColumnIndex("name")));
                            jVar.a(cursorA.getInt(cursorA.getColumnIndex("appId")));
                            jVar.c(cursorA.getString(cursorA.getColumnIndex("package_name")));
                            jVar.b(cursorA.getInt(cursorA.getColumnIndex("create_time")));
                            com.xiaomi.xmpush.thrift.k kVarA = a(cursorA);
                            if (kVarA == null) {
                                com.xiaomi.channel.commonutils.logger.b.c(c + "findAllGeoFencing: geo type null");
                            } else {
                                jVar.a(kVarA);
                                if (TextUtils.equals("Circle", kVarA.name())) {
                                    jVar.a(b(cursorA));
                                    jVar.a(cursorA.getDouble(cursorA.getColumnIndex("circle_radius")));
                                } else if (TextUtils.equals("Polygon", kVarA.name())) {
                                    ArrayList<l> arrayListC = c(cursorA);
                                    if (arrayListC == null || arrayListC.size() < 3) {
                                        com.xiaomi.channel.commonutils.logger.b.c(c + "findAllGeoFencing: geo points null or size<3");
                                    } else {
                                        jVar.a(arrayListC);
                                    }
                                }
                                com.xiaomi.xmpush.thrift.h hVarD = d(cursorA);
                                if (hVarD == null) {
                                    com.xiaomi.channel.commonutils.logger.b.c(c + "findAllGeoFencing: geo Coordinate Provider null ");
                                } else {
                                    jVar.a(hVarD);
                                    arrayList.add(jVar);
                                }
                            }
                        } catch (Exception e) {
                            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
                        }
                    }
                    cursorA.close();
                }
                writableDatabase.close();
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.d(e2.toString());
                arrayList = null;
            }
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    public synchronized ArrayList<com.xiaomi.xmpush.thrift.j> b(String str) {
        ArrayList<com.xiaomi.xmpush.thrift.j> arrayList;
        com.xiaomi.channel.commonutils.misc.k.a(false);
        try {
            ArrayList<com.xiaomi.xmpush.thrift.j> arrayListA = a();
            ArrayList<com.xiaomi.xmpush.thrift.j> arrayList2 = new ArrayList<>();
            for (com.xiaomi.xmpush.thrift.j jVar : arrayListA) {
                if (TextUtils.equals(jVar.g(), str)) {
                    arrayList2.add(jVar);
                }
            }
            arrayList = arrayList2;
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            arrayList = null;
        }
        return arrayList;
    }

    public synchronized String c(String str) {
        String string;
        try {
            com.xiaomi.channel.commonutils.misc.k.a(false);
            try {
                Cursor cursorA = a(this.a.getWritableDatabase());
                if (cursorA != null) {
                    while (cursorA.moveToNext()) {
                        if (TextUtils.equals(cursorA.getString(cursorA.getColumnIndex("id")), str)) {
                            string = cursorA.getString(cursorA.getColumnIndex("current_status"));
                            com.xiaomi.channel.commonutils.logger.b.c(c + "findGeoStatueByGeoId: geo current statue is " + string + " geoId:" + str);
                            cursorA.close();
                        }
                    }
                    cursorA.close();
                    string = "Unknown";
                } else {
                    string = "Unknown";
                }
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.d(e.toString());
                string = "Unknown";
            }
        } catch (Throwable th) {
            throw th;
        }
        return string;
    }

    public synchronized int d(String str) {
        int iDelete;
        com.xiaomi.channel.commonutils.misc.k.a(false);
        try {
            if (a(str) != null) {
                SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                iDelete = writableDatabase.delete("geofence", "id = ?", new String[]{str});
                writableDatabase.close();
            } else {
                iDelete = 0;
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            iDelete = 0;
        }
        return iDelete;
    }

    public synchronized int e(String str) {
        int iDelete;
        com.xiaomi.channel.commonutils.misc.k.a(false);
        try {
            if (TextUtils.isEmpty(str)) {
                iDelete = 0;
            } else {
                SQLiteDatabase writableDatabase = this.a.getWritableDatabase();
                iDelete = writableDatabase.delete("geofence", "package_name = ?", new String[]{str});
                writableDatabase.close();
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.d(e.toString());
            iDelete = 0;
        }
        return iDelete;
    }
}
