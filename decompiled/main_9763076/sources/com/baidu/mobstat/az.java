package com.baidu.mobstat;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.api.MidEntity;
import java.text.SimpleDateFormat;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class az {
    private static az a;
    private Context b;
    private JSONObject c = new JSONObject();
    private long d = 24;
    private long e = 0;
    private long f = 0;
    private long g = 0;
    private long h = 5;
    private long i = 24;
    private long j = 15;
    private long k = 15;
    private long l = 30;
    private long m = 12;
    private long n = 1;
    private long o = 24;
    private String p = Constants.MAIN_VERSION_TAG;
    private String q = Constants.MAIN_VERSION_TAG;

    public static az a(Context context) {
        if (a == null) {
            synchronized (az.class) {
                if (a == null) {
                    a = new az(context);
                }
            }
        }
        return a;
    }

    private az(Context context) throws Throwable {
        this.b = context;
        m();
        j();
        k();
    }

    private void m() throws Throwable {
        String strB = cu.b("backups/system/.timestamp");
        try {
            if (!TextUtils.isEmpty(strB)) {
                this.c = new JSONObject(strB);
            }
        } catch (Exception e) {
        }
    }

    public boolean a() {
        return this.e != 0;
    }

    public boolean b() {
        return this.f != 0;
    }

    public long c() {
        return this.d * 60 * 60 * 1000;
    }

    public long d() {
        return this.o * 60 * 60 * 1000;
    }

    public long e() {
        return this.h * 60 * 1000;
    }

    public long f() {
        return this.i * 60 * 60 * 1000;
    }

    public long g() {
        return this.j * 24 * 60 * 60 * 1000;
    }

    public long h() {
        return this.k * 24 * 60 * 60 * 1000;
    }

    public long i() {
        return this.m * 60 * 60 * 1000;
    }

    public void j() throws Throwable {
        try {
            String str = new String(dc.b(false, cw.a(), cv.a(cu.a(this.b, ".config2").getBytes())));
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                try {
                    this.e = jSONObject.getLong("c");
                } catch (JSONException e) {
                    bd.b(e);
                }
                try {
                    this.h = jSONObject.getLong("d");
                } catch (JSONException e2) {
                    bd.b(e2);
                }
                try {
                    this.i = jSONObject.getLong("e");
                } catch (JSONException e3) {
                    bd.b(e3);
                }
                try {
                    this.j = jSONObject.getLong("i");
                } catch (JSONException e4) {
                    bd.b(e4);
                }
                try {
                    this.d = jSONObject.getLong("f");
                } catch (JSONException e5) {
                    bd.b(e5);
                }
                try {
                    this.o = jSONObject.getLong(NotifyType.SOUND);
                } catch (JSONException e6) {
                    bd.b(e6);
                }
                try {
                    this.k = jSONObject.getLong(PushConstants.URI_PACKAGE_NAME);
                } catch (JSONException e7) {
                    bd.b(e7);
                }
                try {
                    this.l = jSONObject.getLong("at");
                } catch (JSONException e8) {
                    bd.b(e8);
                }
                try {
                    this.m = jSONObject.getLong(AdvanceSetting.ADVANCE_SETTING);
                } catch (JSONException e9) {
                    bd.b(e9);
                }
                try {
                    this.n = jSONObject.getLong("ac");
                } catch (JSONException e10) {
                    bd.b(e10);
                }
                try {
                    this.f = jSONObject.getLong("mc");
                } catch (JSONException e11) {
                    bd.b(e11);
                }
                try {
                    this.g = jSONObject.getLong("lsc");
                } catch (JSONException e12) {
                    bd.b(e12);
                }
            }
        } catch (Exception e13) {
            bd.b(e13);
        }
    }

    public void k() throws Throwable {
        try {
            String str = new String(dc.b(false, cw.a(), cv.a(cu.a(this.b, ".sign").getBytes())));
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                try {
                    this.q = jSONObject.getString("sign");
                } catch (Exception e) {
                    bd.b(e);
                }
                try {
                    this.p = jSONObject.getString(MidEntity.TAG_VER);
                } catch (Exception e2) {
                    bd.b(e2);
                }
            }
        } catch (Exception e3) {
            bd.b(e3);
        }
    }

    public void a(String str) throws Throwable {
        cu.a(this.b, ".config2", str, false);
        j();
    }

    public void b(String str) throws Throwable {
        cu.a(this.b, ".sign", str, false);
        k();
    }

    public String c(String str) {
        return (TextUtils.isEmpty(this.p) || !this.p.equals(str) || TextUtils.isEmpty(this.q)) ? Constants.MAIN_VERSION_TAG : this.q;
    }

    public long a(u uVar) {
        long j = uVar.j;
        try {
            String string = uVar.toString();
            if (this.c.has(string)) {
                j = this.c.getLong(string);
            }
        } catch (Exception e) {
            bd.a(e);
        }
        return b(j);
    }

    public void a(u uVar, long j) throws Throwable {
        uVar.j = j;
        try {
            this.c.put(uVar.toString(), j);
        } catch (Exception e) {
            bd.a(e);
        }
        try {
            cu.a("backups/system/.timestamp", this.c.toString(), false);
        } catch (Exception e2) {
            bd.a(e2);
        }
    }

    public boolean l() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jA = a(u.LAST_SEND);
        long jD = d();
        bd.a("canSend now=" + jCurrentTimeMillis + ";lastSendTime=" + jA + ";sendLogTimeInterval=" + jD);
        return jCurrentTimeMillis - jA > jD || !a(jA);
    }

    public boolean a(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(Long.valueOf(j)).equals(simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
    }

    private long b(long j) {
        if (j - System.currentTimeMillis() > 0) {
            return 0L;
        }
        return j;
    }
}
