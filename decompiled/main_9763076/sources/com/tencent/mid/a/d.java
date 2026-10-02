package com.tencent.mid.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.LocalServerSocket;
import com.tencent.mid.api.MidCallback;
import com.tencent.mid.api.MidConstants;
import com.tencent.mid.api.MidEntity;
import com.tencent.mid.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static com.tencent.mid.util.f c = Util.getLogger();
    private static d i = null;
    private static Context j = null;
    private com.tencent.mid.util.a d = null;
    private com.tencent.mid.util.a e = null;
    private long f = 0;
    private int g = 0;
    private int h = -1;
    int a = -1;
    LocalServerSocket b = null;

    private d(Context context) {
        try {
            j = context.getApplicationContext();
        } catch (Throwable th) {
            c.f(th);
        }
    }

    static Context a() {
        return j;
    }

    public static synchronized d a(Context context) {
        if (i == null) {
            i = new d(context);
        }
        return i;
    }

    private static void a(String str, long j2, int i2) {
        if (Util.isMidValid(str)) {
            if (!Util.isMidValid(com.tencent.mid.b.g.a(j).b())) {
                i2 = 3;
            }
            c.b("updateNewVersionMidEntity reset:" + i2);
            if (i2 > 0) {
                MidEntity midEntity = new MidEntity();
                midEntity.setMid(str);
                midEntity.setGuid(j2);
                midEntity.setMac(Util.getWifiMacAddress(j));
                midEntity.setImei(Util.getImei(j));
                midEntity.setImsi(Util.getImsi(j));
                midEntity.setTimestamps(System.currentTimeMillis());
                midEntity.setVersion(3);
                c.b("server return new version mid midEntity:" + midEntity.toString());
                switch (i2) {
                    case 1:
                        com.tencent.mid.b.g.a(j).b(midEntity);
                        break;
                    case 2:
                        com.tencent.mid.b.g.a(j).c(midEntity);
                        break;
                    case 3:
                        com.tencent.mid.b.g.a(j).a(midEntity);
                        break;
                    case 4:
                        com.tencent.mid.b.g.a(j).f(midEntity);
                        com.tencent.mid.b.g.a(j).a(midEntity);
                        break;
                    case 8:
                        com.tencent.mid.b.g.a(j).f(midEntity);
                        com.tencent.mid.b.g.a(j).a(midEntity);
                        com.tencent.mid.b.g.a(j).g(midEntity);
                        break;
                }
                com.tencent.mid.b.g.a(j).a(-1, -1);
            }
        }
    }

    private static void a(String str, long j2, int i2, MidCallback midCallback) {
        if (Util.isMidValid(str)) {
            if (!Util.isMidValid(h.d(j))) {
                i2 = 4;
            }
            c.b("updateMidEntity reset:" + i2);
            if (i2 > 0) {
                MidEntity midEntity = new MidEntity();
                midEntity.setMid(str);
                midEntity.setGuid(j2);
                midEntity.setMac(Util.getWifiMacAddress(j));
                midEntity.setImei(Util.getImei(j));
                midEntity.setImsi(Util.getImsi(j));
                midEntity.setTimestamps(System.currentTimeMillis());
                midEntity.setVersion(3);
                c.b("server return new mid midEntity:" + midEntity.toString());
                midCallback.onSuccess(midEntity.toString());
                switch (i2) {
                    case 1:
                        com.tencent.mid.b.g.a(j).d(midEntity);
                        break;
                    case 2:
                        com.tencent.mid.b.g.a(j).e(midEntity);
                        break;
                    case 3:
                        com.tencent.mid.b.g.a(j).f(midEntity);
                        break;
                    case 4:
                        com.tencent.mid.b.g.a(j).f(midEntity);
                        com.tencent.mid.b.g.a(j).g(midEntity);
                        break;
                }
                com.tencent.mid.b.g.a(j).a(-1, -1);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:90:0x0260 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private void b(int i2, f fVar, MidCallback midCallback) throws Throwable {
        b bVar;
        b bVar2 = null;
        c.b(" enter http request, type:" + i2);
        b bVar3 = null;
        try {
            if (e()) {
                c.f("Http request failed too much, please check the network.");
                if (midCallback != null) {
                    midCallback.onFail(MidConstants.ERROR_HTTP_FAILED_TOO_MUCH, "Http request failed too much, please check the network.");
                }
                if (0 != 0) {
                    try {
                        bVar3.a();
                        return;
                    } catch (Throwable th) {
                        th.printStackTrace();
                        return;
                    }
                }
                return;
            }
            com.tencent.mid.util.b bVarA = com.tencent.mid.util.b.a(j);
            bVar = new b(Util.getHttpAddr(j), null);
            try {
                JSONObject jSONObject = new JSONObject();
                fVar.a(jSONObject);
                jSONObject.put("rty", i2);
                if (this.h > 0) {
                    jSONObject.put("seq", this.h);
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("android", jSONObject);
                jSONObject2.put("mid_list", Util.queryMids(j, 1));
                jSONObject2.put("mid_list_new", Util.queryMids(j, 2));
                String string = jSONObject2.toString();
                c.b("jsonBodyStr:" + string);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(string.length());
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                gZIPOutputStream.write(string.getBytes(HTTP.UTF_8));
                gZIPOutputStream.close();
                byteArrayOutputStream.flush();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                com.tencent.mid.util.a aVarA = a(i2);
                byteArrayOutputStream.reset();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                String strF = bVarA.f();
                if (i2 == 1 || i2 == 3) {
                    strF = i2 == 1 ? bVarA.d() : bVarA.e();
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(64);
                    byteArrayOutputStream2.write(aVarA.b());
                    byteArrayOutputStream2.write(aVarA.c());
                    byteArrayOutputStream2.close();
                    byte[] byteArray2 = byteArrayOutputStream2.toByteArray();
                    com.tencent.mid.util.h.a(bVarA.b());
                    byte[] bArrA = com.tencent.mid.util.h.a(byteArray2);
                    dataOutputStream.writeShort(bVarA.a());
                    dataOutputStream.writeShort(bArrA.length);
                    dataOutputStream.write(bArrA);
                }
                dataOutputStream.write(aVarA.a(byteArray));
                dataOutputStream.close();
                byteArrayOutputStream.close();
                e eVarA = bVar.a(strF, byteArrayOutputStream.toByteArray(), "gzip", i2);
                if (eVarA.a() != 200) {
                    String str = "response code invalid:" + eVarA.a();
                    c.d(str);
                    midCallback.onFail(eVarA.a(), str);
                    if (bVar != null) {
                        try {
                            bVar.a();
                            return;
                        } catch (Throwable th2) {
                            th2.printStackTrace();
                            return;
                        }
                    }
                    return;
                }
                JSONObject jSONObjectB = eVarA.b();
                if (jSONObjectB.has("ret_code") || jSONObjectB.has("ret_msg")) {
                    int i3 = jSONObjectB.getInt("ret_code");
                    String str2 = "response code:" + i3 + ",msg:" + jSONObjectB.getString("ret_msg");
                    c.d(str2);
                    if (i3 != 0) {
                        midCallback.onFail(i3, str2);
                        if (bVar != null) {
                            try {
                                bVar.a();
                                return;
                            } catch (Throwable th3) {
                                th3.printStackTrace();
                                return;
                            }
                        }
                        return;
                    }
                }
                if (!jSONObjectB.isNull("seq")) {
                    this.h = jSONObjectB.getInt("seq");
                }
                if (!jSONObjectB.isNull("mid")) {
                    String string2 = jSONObjectB.getString("mid");
                    if (jSONObjectB.has("guid")) {
                        a(string2, jSONObjectB.optLong("guid", 0L), jSONObjectB.optInt("reset", 0), midCallback);
                    }
                }
                int iOptInt = jSONObjectB.optInt("locW", -1);
                if (iOptInt > -1) {
                    com.tencent.mid.util.i.a(j).a("ten.mid.allowCheckAndRewriteLocal.bool", iOptInt);
                }
                a(jSONObjectB.optString(MidConstants.NEW_MID_TAG), jSONObjectB.optLong("guid", 0L), jSONObjectB.optInt("reset_new", 0));
                if (bVar != null) {
                    try {
                        bVar.a();
                    } catch (Throwable th4) {
                        th4.printStackTrace();
                    }
                }
            } catch (Throwable th5) {
                th = th5;
                if (bVar != null) {
                    bVar.a();
                }
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
            bVar = null;
        }
    }

    private void c() {
        this.f = 0L;
        this.g = 0;
    }

    private void d() {
        this.g++;
        this.f = System.currentTimeMillis();
    }

    private boolean e() {
        if (this.g > 3) {
            if (System.currentTimeMillis() - this.f < 1800000) {
                return true;
            }
            c();
        }
        return false;
    }

    private boolean f() {
        try {
            this.b = new LocalServerSocket("com.tencent.teg.mid.sock.lock");
            c.h("open socket mLocalServerSocket:" + this.b);
            return true;
        } catch (IOException e) {
            c.d("socket Name:com.tencent.teg.mid.sock.lock is in use.");
            return false;
        } catch (Throwable th) {
            c.d("something wrong while create LocalServerSocket.");
            return false;
        }
    }

    private void g() {
        if (this.b != null) {
            try {
                this.b.close();
                c.b("close socket  mLocalServerSocket:" + this.b);
                this.b = null;
            } catch (Throwable th) {
            }
        }
    }

    com.tencent.mid.util.a a(int i2) {
        if (i2 == 1) {
            if (this.d == null) {
                this.d = new com.tencent.mid.util.a();
                this.d.e();
            }
            return this.d;
        }
        if (this.e == null) {
            this.e = new com.tencent.mid.util.a();
            this.e.a("key-/.*$!xx", "vec-;*5@)&%(");
        }
        return this.e;
    }

    void a(int i2, f fVar, MidCallback midCallback) {
        if (fVar == null || midCallback == null) {
            if (midCallback != null) {
                midCallback.onFail(MidConstants.ERROR_ARGUMENT, "packet == null || handler == null");
            }
            c.f("packet == null || handler == null || cb == null");
            return;
        }
        if (!Util.isNetworkAvailable(j)) {
            midCallback.onFail(MidConstants.ERROR_NETWORK, "network not available.");
            return;
        }
        int i3 = 0;
        while (!f()) {
            i3++;
            if (i3 >= 10) {
                break;
            } else {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                }
            }
        }
        if (i2 == 1) {
            MidEntity midEntityA = h.a(j);
            if (Util.isMidValid(midEntityA)) {
                midCallback.onSuccess(midEntityA);
                g();
                return;
            }
        }
        if (i2 == 3) {
            MidEntity midEntityA2 = com.tencent.mid.b.g.a(j).a();
            if (Util.isMidValid(midEntityA2)) {
                midCallback.onSuccess(midEntityA2);
                g();
                return;
            }
        }
        if (!b()) {
            g();
        } else {
            b(i2, fVar, midCallback);
            g();
        }
    }

    boolean b() {
        int i2 = this.a;
        this.a = i2 + 1;
        if (i2 > 1000) {
            c.f("send count limit " + this.a);
            return false;
        }
        SharedPreferences sharedPreferencesA = com.tencent.mid.api.a.a(j).a();
        if (sharedPreferencesA != null) {
            String str = "SEND_LIMIT_" + Util.getDateString(0);
            if (this.a == 0) {
                this.a = sharedPreferencesA.getInt(str, 0);
            }
            sharedPreferencesA.edit().putInt(str, this.a);
        }
        return true;
    }
}
