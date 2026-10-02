package com.meizu.cloud.pushsdk.b.d;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import com.meizu.cloud.pushsdk.b.f.c;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private SQLiteDatabase b;
    private b c;
    private int f;
    private String a = a.class.getSimpleName();
    private String[] d = {"id", "eventData", "dateCreated"};
    private long e = -1;

    public a(Context context, int i) {
        this.c = b.a(context);
        a();
        this.f = i;
        c.b(this.a, "DB Path: " + this.b.getPath(), new Object[0]);
    }

    public void a(com.meizu.cloud.pushsdk.b.a.a aVar) {
        b(aVar);
    }

    public void a() {
        if (!d()) {
            this.b = this.c.getWritableDatabase();
            this.b.enableWriteAheadLogging();
        }
    }

    public long b(com.meizu.cloud.pushsdk.b.a.a aVar) {
        if (d()) {
            byte[] bArrA = a((Map<String, String>) aVar.a());
            ContentValues contentValues = new ContentValues(2);
            contentValues.put("eventData", bArrA);
            this.e = this.b.insert("events", null, contentValues);
        }
        c.b(this.a, "Added event to database: " + this.e, new Object[0]);
        return this.e;
    }

    public boolean a(long j) {
        int iDelete = -1;
        if (d()) {
            iDelete = this.b.delete("events", "id=" + j, null);
        }
        c.b(this.a, "Removed event from database: " + j, new Object[0]);
        return iDelete == 1;
    }

    private static byte[] a(Map<String, String> map) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(map);
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, String> a(byte[] bArr) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            HashMap map = (HashMap) objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return map;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String, Object>> a(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        if (d()) {
            Cursor cursorQuery = this.b.query("events", this.d, str, null, null, null, str2);
            cursorQuery.moveToFirst();
            while (!cursorQuery.isAfterLast()) {
                HashMap map = new HashMap();
                map.put("id", Long.valueOf(cursorQuery.getLong(0)));
                map.put("eventData", a(cursorQuery.getBlob(1)));
                map.put("dateCreated", cursorQuery.getString(2));
                cursorQuery.moveToNext();
                arrayList.add(map);
            }
            cursorQuery.close();
        }
        return arrayList;
    }

    public long b() {
        return DatabaseUtils.queryNumEntries(this.b, "events");
    }

    public com.meizu.cloud.pushsdk.b.b.b c() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList();
        for (Map<String, Object> map : a(this.f)) {
            com.meizu.cloud.pushsdk.b.a.c cVar = new com.meizu.cloud.pushsdk.b.a.c();
            cVar.a((Map) map.get("eventData"));
            linkedList.add((Long) map.get("id"));
            arrayList.add(cVar);
        }
        return new com.meizu.cloud.pushsdk.b.b.b(arrayList, linkedList);
    }

    public List<Map<String, Object>> a(int i) {
        return a(null, "id ASC LIMIT " + i);
    }

    public boolean d() {
        return this.b != null && this.b.isOpen();
    }
}
