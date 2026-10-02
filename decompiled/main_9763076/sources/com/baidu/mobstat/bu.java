package com.baidu.mobstat;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bu {
    static String a = "Android";
    String c;
    String d;
    String i;
    String j;
    int k;
    int l;
    String n;
    String o;
    String p;
    String q;
    String r;
    String s;
    String t;
    String u;
    String v;
    String w;
    String x;
    String y;
    JSONObject z;
    boolean b = false;
    String e = PushConstants.PUSH_TYPE_NOTIFY;
    String f = null;
    String g = null;
    int h = -1;
    String m = null;

    bu() {
    }

    public synchronized void a(Context context, JSONObject jSONObject) {
        a(context);
        if (jSONObject.length() > 10) {
            db.a("header has been installed; header is:" + jSONObject);
        } else {
            b(context, jSONObject);
        }
    }

    public synchronized void a(Context context) {
        if (!this.b) {
            cu.e(context, "android.permission.READ_PHONE_STATE");
            cu.e(context, "android.permission.INTERNET");
            cu.e(context, "android.permission.ACCESS_NETWORK_STATE");
            cu.e(context, "android.permission.WRITE_SETTINGS");
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            this.c = CooperService.a().getOSVersion();
            this.d = CooperService.a().getOSSysVersion();
            this.o = CooperService.a().getPhoneModel();
            this.p = CooperService.a().getManufacturer();
            this.y = CooperService.a().getUUID();
            this.z = CooperService.a().getHeaderExt(context);
            this.j = CooperService.a().getDeviceId(telephonyManager, context);
            this.e = bj.a().j(context) ? "1" : PushConstants.PUSH_TYPE_NOTIFY;
            if (de.s(context)) {
                this.e = "2";
            }
            this.e += "-0";
            try {
                this.t = CooperService.a().getMacAddress(context, CooperService.a().isDeviceMacEnabled(context));
            } catch (Exception e) {
                db.a(e);
            }
            try {
                this.v = de.f(1, context);
            } catch (Exception e2) {
                db.a(e2);
            }
            try {
                this.w = de.a(context, 1);
            } catch (Exception e3) {
                db.a(e3);
            }
            this.g = CooperService.a().getCUID(context, true);
            try {
                this.n = CooperService.a().getOperator(telephonyManager);
            } catch (Exception e4) {
                db.a(e4);
            }
            try {
                this.k = de.b(context);
                this.l = de.c(context);
                if (context.getResources().getConfiguration().orientation == 2) {
                    this.k ^= this.l;
                    this.l = this.k ^ this.l;
                    this.k ^= this.l;
                }
            } catch (Exception e5) {
                db.a(e5);
            }
            this.m = CooperService.a().getAppChannel(context);
            this.f = CooperService.a().getAppKey(context);
            try {
                this.h = CooperService.a().getAppVersionCode(context);
                this.i = CooperService.a().getAppVersionName(context);
            } catch (Exception e6) {
                db.a(e6);
            }
            try {
                if (CooperService.a().checkCellLocationSetting(context)) {
                    this.q = de.g(context);
                } else {
                    this.q = "0_0_0";
                }
            } catch (Exception e7) {
                db.a(e7);
            }
            try {
                if (CooperService.a().checkGPSLocationSetting(context)) {
                    this.r = de.h(context);
                } else {
                    this.r = Constants.MAIN_VERSION_TAG;
                }
            } catch (Exception e8) {
                db.a(e8);
            }
            try {
                this.s = CooperService.a().getLinkedWay(context);
            } catch (Exception e9) {
                db.a(e9);
            }
            this.x = de.b();
            this.b = true;
        }
    }

    public synchronized void b(Context context, JSONObject jSONObject) {
        try {
            jSONObject.put("o", a == null ? Constants.MAIN_VERSION_TAG : a);
            jSONObject.put(TimeDisplaySetting.START_SHOW_TIME, 0);
            jSONObject.put(NotifyType.SOUND, this.c == null ? Constants.MAIN_VERSION_TAG : this.c);
            jSONObject.put("sv", this.d == null ? Constants.MAIN_VERSION_TAG : this.d);
            jSONObject.put("k", this.f == null ? Constants.MAIN_VERSION_TAG : this.f);
            jSONObject.put("pt", this.e == null ? PushConstants.PUSH_TYPE_NOTIFY : this.e);
            jSONObject.put("i", Constants.MAIN_VERSION_TAG);
            jSONObject.put(NotifyType.VIBRATE, "3.7.6.1");
            jSONObject.put("sc", 0);
            jSONObject.put("a", this.h);
            jSONObject.put("n", this.i == null ? Constants.MAIN_VERSION_TAG : this.i);
            jSONObject.put("d", Constants.MAIN_VERSION_TAG);
            jSONObject.put("mc", this.t == null ? Constants.MAIN_VERSION_TAG : this.t);
            jSONObject.put("bm", this.v == null ? Constants.MAIN_VERSION_TAG : this.v);
            jSONObject.put("dd", this.j == null ? Constants.MAIN_VERSION_TAG : this.j);
            jSONObject.put("ii", this.g == null ? Constants.MAIN_VERSION_TAG : this.g);
            jSONObject.put("tg", 1);
            jSONObject.put("w", this.k);
            jSONObject.put("h", this.l);
            jSONObject.put("dn", this.w == null ? Constants.MAIN_VERSION_TAG : this.w);
            jSONObject.put("c", this.m == null ? Constants.MAIN_VERSION_TAG : this.m);
            jSONObject.put("op", this.n == null ? Constants.MAIN_VERSION_TAG : this.n);
            jSONObject.put("m", this.o == null ? Constants.MAIN_VERSION_TAG : this.o);
            jSONObject.put("ma", this.p == null ? Constants.MAIN_VERSION_TAG : this.p);
            jSONObject.put("cl", this.q);
            jSONObject.put("gl", this.r == null ? Constants.MAIN_VERSION_TAG : this.r);
            jSONObject.put(NotifyType.LIGHTS, this.s == null ? Constants.MAIN_VERSION_TAG : this.s);
            jSONObject.put("t", System.currentTimeMillis());
            jSONObject.put("pn", de.h(1, context));
            jSONObject.put("rom", this.x == null ? Constants.MAIN_VERSION_TAG : this.x);
            String strQ = de.q(context);
            jSONObject.put("pl", strQ);
            Object objR = null;
            if (!TextUtils.isEmpty(strQ)) {
                objR = de.r(context);
            }
            if (objR == null) {
                objR = Constants.MAIN_VERSION_TAG;
            }
            jSONObject.put("scl", objR);
            jSONObject.put("sign", this.y == null ? Constants.MAIN_VERSION_TAG : this.y);
            if (this.z != null && this.z.length() != 0) {
                jSONObject.put("ext", this.z);
            }
            db.a("header is: " + jSONObject.toString() + "; len: " + jSONObject.length());
        } catch (JSONException e) {
            db.a("header ini error");
        }
    }
}
