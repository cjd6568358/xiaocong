package com.hzy.tvmao.model.db.a;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hzy.tvmao.model.db.bean.ChannelInfo;
import com.kookong.app.data.api.LineupData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: compiled from: LineupDao.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a b;
    private SQLiteDatabase a = com.hzy.tvmao.model.db.a.a().b();

    private a() {
    }

    public static final a a() {
        if (b == null) {
            b = new a();
        }
        return b;
    }

    public synchronized void a(int i, int i2, List<LineupData.Chnnum> list) {
        ArrayList arrayList = new ArrayList();
        for (LineupData.Chnnum chnnum : list) {
            ChannelInfo channelInfo = new ChannelInfo();
            channelInfo.channelId = chnnum.cid;
            channelInfo.name = chnnum.name;
            channelInfo.num = chnnum.num;
            channelInfo.countryId = chnnum.ctrid;
            channelInfo.isHd = chnnum.hd;
            channelInfo.ishidden = chnnum.hidden;
            channelInfo.linupId = i2;
            channelInfo.logo = chnnum.logo;
            channelInfo.llogo = chnnum.llogo;
            channelInfo.sequence = 0;
            channelInfo.type = chnnum.type;
            channelInfo.fee = chnnum.fee;
            channelInfo.pulse = chnnum.pulse;
            arrayList.add(channelInfo);
        }
        a(i, arrayList);
    }

    public void a(int i, List<ChannelInfo> list) {
        this.a.beginTransaction();
        try {
            this.a.execSQL("delete from lineup where device_id=?", new String[]{new StringBuilder(String.valueOf(i)).toString()});
            for (ChannelInfo channelInfo : list) {
                channelInfo.encrypt();
                ContentValues contentValues = new ContentValues();
                contentValues.put("device_id", Integer.valueOf(i));
                contentValues.put("lineup_id", Integer.valueOf(channelInfo.linupId));
                contentValues.put("sequence", Integer.valueOf(channelInfo.sequence));
                contentValues.put("channel_id", Integer.valueOf(channelInfo.channelId));
                contentValues.put("country_id", channelInfo.countryId);
                contentValues.put("pulse", channelInfo.pulse);
                contentValues.put("hd", Short.valueOf(channelInfo.isHd));
                contentValues.put("hidden", Integer.valueOf(channelInfo.ishidden));
                contentValues.put("type", Short.valueOf(channelInfo.type));
                contentValues.put("enc_name", channelInfo.encname);
                contentValues.put("enc_num", channelInfo.encnum);
                contentValues.put("fee", Short.valueOf(channelInfo.fee));
                contentValues.put("logo", channelInfo.logo);
                contentValues.put("llogo", channelInfo.llogo);
                this.a.insert("lineup", null, contentValues);
            }
            this.a.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            this.a.endTransaction();
        }
    }

    public HashMap<ChannelInfo.a, ChannelInfo> a(int i) {
        List<ChannelInfo> listB = b(i);
        if (listB == null) {
            return null;
        }
        HashMap<ChannelInfo.a, ChannelInfo> map = new HashMap<>();
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= listB.size()) {
                return map;
            }
            ChannelInfo channelInfo = listB.get(i3);
            map.put(channelInfo.getKey(), channelInfo);
            i2 = i3 + 1;
        }
    }

    public List<ChannelInfo> b(int i) {
        ArrayList arrayList = new ArrayList();
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = this.a.rawQuery("select * from lineup where device_id=?", new String[]{String.valueOf(i)});
                while (cursorRawQuery != null && cursorRawQuery.moveToNext()) {
                    ChannelInfo channelInfo = new ChannelInfo();
                    channelInfo.deviceId = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("device_id"));
                    channelInfo.fee = cursorRawQuery.getShort(cursorRawQuery.getColumnIndex("fee"));
                    channelInfo.channelId = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("channel_id"));
                    channelInfo.countryId = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("country_id"));
                    channelInfo.pulse = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("pulse"));
                    channelInfo.encname = cursorRawQuery.getBlob(cursorRawQuery.getColumnIndex("enc_name"));
                    channelInfo.logo = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("logo"));
                    channelInfo.llogo = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("llogo"));
                    channelInfo.ishidden = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("hidden"));
                    channelInfo.isHd = cursorRawQuery.getShort(cursorRawQuery.getColumnIndex("hd"));
                    channelInfo.linupId = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("lineup_id"));
                    channelInfo.encnum = cursorRawQuery.getBlob(cursorRawQuery.getColumnIndex("enc_num"));
                    channelInfo.decrypt();
                    arrayList.add(channelInfo);
                }
                if (cursorRawQuery != null) {
                    com.hzy.tvmao.model.db.a.a().a(cursorRawQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursorRawQuery != null) {
                    com.hzy.tvmao.model.db.a.a().a(cursorRawQuery);
                }
            }
            return arrayList;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                com.hzy.tvmao.model.db.a.a().a(cursorRawQuery);
            }
            throw th;
        }
    }

    public List<ChannelInfo> c(int i) {
        ArrayList arrayList = new ArrayList();
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = this.a.rawQuery("select device_id,channel_id,country_id,hd from lineup where device_id=?", new String[]{String.valueOf(i)});
                while (cursorRawQuery != null && cursorRawQuery.moveToNext()) {
                    ChannelInfo channelInfo = new ChannelInfo();
                    channelInfo.deviceId = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("device_id"));
                    channelInfo.channelId = cursorRawQuery.getInt(cursorRawQuery.getColumnIndex("channel_id"));
                    channelInfo.countryId = cursorRawQuery.getString(cursorRawQuery.getColumnIndex("country_id"));
                    channelInfo.isHd = cursorRawQuery.getShort(cursorRawQuery.getColumnIndex("hd"));
                    arrayList.add(channelInfo);
                }
                if (cursorRawQuery != null) {
                    com.hzy.tvmao.model.db.a.a().a(cursorRawQuery);
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (cursorRawQuery != null) {
                    com.hzy.tvmao.model.db.a.a().a(cursorRawQuery);
                }
            }
            return arrayList;
        } catch (Throwable th) {
            if (cursorRawQuery != null) {
                com.hzy.tvmao.model.db.a.a().a(cursorRawQuery);
            }
            throw th;
        }
    }
}
