package com.baidu.mobstat;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ag extends x {
    public ag() {
        super("ap_list3", "Create table if not exists ap_list3(_id Integer primary key AUTOINCREMENT,time VARCHAR(50),content TEXT);");
    }

    @Override // com.baidu.mobstat.x
    public ArrayList<w> a(int i, int i2) {
        Cursor cursorA = a("time", i, i2);
        ArrayList<w> arrayListA = a(cursorA);
        if (cursorA != null) {
            cursorA.close();
        }
        return arrayListA;
    }

    @Override // com.baidu.mobstat.x
    public long a(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("time", str);
        contentValues.put("content", str2);
        return a(contentValues);
    }

    @Override // com.baidu.mobstat.x
    public boolean b(long j) {
        return a(j);
    }

    private ArrayList<w> a(Cursor cursor) {
        ArrayList<w> arrayList = new ArrayList<>();
        if (cursor != null && cursor.getCount() != 0) {
            int columnIndex = cursor.getColumnIndex("_id");
            int columnIndex2 = cursor.getColumnIndex("time");
            int columnIndex3 = cursor.getColumnIndex("content");
            while (cursor.moveToNext()) {
                arrayList.add(new w(cursor.getLong(columnIndex), cursor.getString(columnIndex2), cursor.getString(columnIndex3)));
            }
        }
        return arrayList;
    }
}
