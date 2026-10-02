package com.baidu.location.a;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import com.baidu.location.Jni;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.api.MidEntity;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.File;
import java.util.HashMap;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private static Object c = new Object();
    private static h d = null;
    private static final String e = com.baidu.location.d.j.h() + "/hst.db";
    private SQLiteDatabase f = null;
    private boolean g = false;
    a a = null;
    a b = null;

    class a extends com.baidu.location.d.e {
        private String b = null;
        private String c = null;
        private boolean d = true;
        private boolean e = false;

        a() {
            this.k = new HashMap();
        }

        @Override // com.baidu.location.d.e
        public void a() {
            this.i = 1;
            this.h = com.baidu.location.d.j.c();
            String strEncodeTp4 = Jni.encodeTp4(this.c);
            this.c = null;
            this.k.put("bloc", strEncodeTp4);
        }

        public void a(String str, String str2) {
            if (h.this.g) {
                return;
            }
            h.this.g = true;
            this.b = str;
            this.c = str2;
            b(com.baidu.location.d.j.f);
        }

        @Override // com.baidu.location.d.e
        public void a(boolean z) {
            if (z && this.j != null) {
                try {
                    String str = this.j;
                    if (this.d) {
                        JSONObject jSONObject = new JSONObject(str);
                        JSONObject jSONObject2 = jSONObject.has("content") ? jSONObject.getJSONObject("content") : null;
                        if (jSONObject2 != null && jSONObject2.has("imo")) {
                            Long lValueOf = Long.valueOf(jSONObject2.getJSONObject("imo").getString(MidEntity.TAG_MAC));
                            int i = jSONObject2.getJSONObject("imo").getInt("mv");
                            if (Jni.encode3(this.b).longValue() == lValueOf.longValue()) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(PushConstants.PUSH_NOTIFICATION_CREATE_TIMES_TAMP, Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
                                contentValues.put("hst", Integer.valueOf(i));
                                try {
                                    if (h.this.f.update("hstdata", contentValues, "id = \"" + lValueOf + "\"", null) <= 0) {
                                        contentValues.put("id", lValueOf);
                                        h.this.f.insert("hstdata", null, contentValues);
                                    }
                                } catch (Exception e) {
                                }
                                Bundle bundle = new Bundle();
                                bundle.putByteArray(MidEntity.TAG_MAC, this.b.getBytes());
                                bundle.putInt("hotspot", i);
                                h.this.a(bundle);
                            }
                        }
                    }
                } catch (Exception e2) {
                }
            } else if (this.d) {
                h.this.f();
            }
            if (this.k != null) {
                this.k.clear();
            }
            h.this.g = false;
        }
    }

    public static h a() {
        h hVar;
        synchronized (c) {
            if (d == null) {
                d = new h();
            }
            hVar = d;
        }
        return hVar;
    }

    private String a(boolean z) {
        com.baidu.location.b.a aVarF = com.baidu.location.b.b.a().f();
        com.baidu.location.b.g gVarO = com.baidu.location.b.h.a().o();
        StringBuffer stringBuffer = new StringBuffer(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
        if (aVarF != null && aVarF.b()) {
            stringBuffer.append(aVarF.g());
        }
        if (gVarO != null && gVarO.a() > 1) {
            stringBuffer.append(gVarO.a(15));
        } else if (com.baidu.location.b.h.a().l() != null) {
            stringBuffer.append(com.baidu.location.b.h.a().l());
        }
        if (z) {
            stringBuffer.append("&imo=1");
        }
        stringBuffer.append(com.baidu.location.d.b.a().a(false));
        stringBuffer.append(com.baidu.location.a.a.a().c());
        return stringBuffer.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bundle bundle) {
        com.baidu.location.a.a.a().a(bundle, HttpStatus.SC_NOT_ACCEPTABLE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        Bundle bundle = new Bundle();
        bundle.putInt("hotspot", -1);
        a(bundle);
    }

    public void a(String str) {
        if (this.g) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.has("content") ? jSONObject.getJSONObject("content") : null;
            if (jSONObject2 == null || !jSONObject2.has("imo")) {
                return;
            }
            Long lValueOf = Long.valueOf(jSONObject2.getJSONObject("imo").getString(MidEntity.TAG_MAC));
            int i = jSONObject2.getJSONObject("imo").getInt("mv");
            ContentValues contentValues = new ContentValues();
            contentValues.put(PushConstants.PUSH_NOTIFICATION_CREATE_TIMES_TAMP, Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
            contentValues.put("hst", Integer.valueOf(i));
            try {
                if (this.f.update("hstdata", contentValues, "id = \"" + lValueOf + "\"", null) <= 0) {
                    contentValues.put("id", lValueOf);
                    this.f.insert("hstdata", null, contentValues);
                }
            } catch (Exception e2) {
            }
        } catch (Exception e3) {
        }
    }

    public void b() {
        try {
            File file = new File(e);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists()) {
                this.f = SQLiteDatabase.openOrCreateDatabase(file, (SQLiteDatabase.CursorFactory) null);
                this.f.execSQL("CREATE TABLE IF NOT EXISTS hstdata(id Long PRIMARY KEY,hst INT,tt INT);");
                this.f.setVersion(1);
            }
        } catch (Exception e2) {
            this.f = null;
        }
    }

    public void c() {
        if (this.f != null) {
            try {
                this.f.close();
            } catch (Exception e2) {
            } finally {
                this.f = null;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0066  */
    public int d() throws Throwable {
        WifiInfo wifiInfoK;
        Cursor cursor;
        Throwable th;
        Cursor cursor2 = null;
        int i = -3;
        if (!this.g) {
            try {
                if (com.baidu.location.b.h.i() && this.f != null && (wifiInfoK = com.baidu.location.b.h.a().k()) != null && wifiInfoK.getBSSID() != null) {
                    try {
                        try {
                            Cursor cursorRawQuery = this.f.rawQuery("select * from hstdata where id = \"" + Jni.encode3(wifiInfoK.getBSSID().replace(":", Constants.MAIN_VERSION_TAG)) + "\";", null);
                            if (cursorRawQuery != null) {
                                try {
                                    if (cursorRawQuery.moveToFirst()) {
                                        i = cursorRawQuery.getInt(1);
                                    } else {
                                        i = -2;
                                    }
                                } catch (Throwable th2) {
                                    cursor = cursorRawQuery;
                                    th = th2;
                                    if (cursor == null) {
                                        throw th;
                                    }
                                    try {
                                        cursor.close();
                                        throw th;
                                    } catch (Exception e2) {
                                        throw th;
                                    }
                                }
                            } else {
                                i = -2;
                            }
                            if (cursorRawQuery != null) {
                                try {
                                    cursorRawQuery.close();
                                } catch (Exception e3) {
                                }
                            }
                        } catch (Throwable th3) {
                            cursor = null;
                            th = th3;
                        }
                    } catch (Exception e4) {
                        if (0 != 0) {
                            try {
                                cursor2.close();
                            } catch (Exception e5) {
                            }
                        }
                    }
                }
            } catch (Exception e6) {
            }
        }
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x00b2  */
    public void e() {
        Cursor cursor;
        Cursor cursor2 = null;
        boolean z = true;
        if (this.g) {
            return;
        }
        try {
            if (!com.baidu.location.b.h.i() || this.f == null) {
                f();
                return;
            }
            WifiInfo wifiInfoK = com.baidu.location.b.h.a().k();
            if (wifiInfoK == null || wifiInfoK.getBSSID() == null) {
                f();
                return;
            }
            String strReplace = wifiInfoK.getBSSID().replace(":", Constants.MAIN_VERSION_TAG);
            boolean z2 = false;
            try {
                try {
                    Cursor cursorRawQuery = this.f.rawQuery("select * from hstdata where id = \"" + Jni.encode3(strReplace) + "\";", null);
                    if (cursorRawQuery != null) {
                        try {
                            if (cursorRawQuery.moveToFirst()) {
                                int i = cursorRawQuery.getInt(1);
                                if ((System.currentTimeMillis() / 1000) - ((long) cursorRawQuery.getInt(2)) <= 259200) {
                                    Bundle bundle = new Bundle();
                                    bundle.putByteArray(MidEntity.TAG_MAC, strReplace.getBytes());
                                    bundle.putInt("hotspot", i);
                                    a(bundle);
                                    z = false;
                                }
                                z2 = z;
                            } else {
                                z2 = true;
                            }
                        } catch (Exception e2) {
                            cursor = cursorRawQuery;
                            if (cursor != null) {
                                try {
                                    cursor.close();
                                } catch (Exception e3) {
                                }
                            }
                        }
                    } else {
                        z2 = true;
                    }
                    if (cursorRawQuery != null) {
                        try {
                            cursorRawQuery.close();
                        } catch (Exception e4) {
                        }
                    }
                } catch (Exception e5) {
                    cursor = null;
                }
                if (z2) {
                    if (this.a == null) {
                        this.a = new a();
                    }
                    if (this.a != null) {
                        this.a.a(strReplace, a(true));
                    }
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    try {
                        cursor2.close();
                    } catch (Exception e6) {
                    }
                }
                throw th;
            }
        } catch (Exception e7) {
        }
    }
}
