package com.baidu.mobstat;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CooperService {
    private static CooperService a;
    private bu b = new bu();

    static synchronized CooperService a() {
        if (a == null) {
            a = new CooperService();
        }
        return a;
    }

    public bu getHeadObject() {
        return this.b;
    }

    public JSONObject getHeaderExt(Context context) {
        String strL = bj.a().l(context);
        JSONObject jSONObject = new JSONObject();
        if (TextUtils.isEmpty(strL)) {
            return jSONObject;
        }
        try {
            return new JSONObject(strL);
        } catch (JSONException e) {
            return jSONObject;
        }
    }

    private static String a(Context context) {
        String strJ = de.j(context);
        if (!TextUtils.isEmpty(strJ)) {
            return strJ.replaceAll(":", Constants.MAIN_VERSION_TAG);
        }
        return strJ;
    }

    private static String b(Context context) {
        String strI = de.i(context);
        if (!TextUtils.isEmpty(strI)) {
            return strI.replaceAll(":", Constants.MAIN_VERSION_TAG);
        }
        return strI;
    }

    private static String c(Context context) {
        String strK = de.k(context);
        if (!TextUtils.isEmpty(strK)) {
            return strK.replaceAll(":", Constants.MAIN_VERSION_TAG);
        }
        return strK;
    }

    public String getMacAddress(Context context, boolean z) {
        String strReplace = "02:00:00:00:00:00".replace(":", Constants.MAIN_VERSION_TAG);
        if (!z && Build.VERSION.SDK_INT >= 23) {
            return getSecretValue(strReplace);
        }
        if (!TextUtils.isEmpty(this.b.t)) {
            return this.b.t;
        }
        String strI = bj.a().i(context);
        if (!TextUtils.isEmpty(strI)) {
            this.b.t = strI;
            return this.b.t;
        }
        String strA = a(context, z);
        if (!TextUtils.isEmpty(strA) && !strReplace.equals(strA)) {
            this.b.t = getSecretValue(strA);
            bj.a().d(context, this.b.t);
            return this.b.t;
        }
        this.b.t = Constants.MAIN_VERSION_TAG;
        return this.b.t;
    }

    private String a(Context context, boolean z) {
        String strA;
        if (z) {
            strA = b(context);
        } else {
            strA = a(context);
        }
        if (TextUtils.isEmpty(strA)) {
            return Constants.MAIN_VERSION_TAG;
        }
        return strA;
    }

    public String getMacIdForTv(Context context) {
        if (!TextUtils.isEmpty(this.b.u)) {
            return this.b.u;
        }
        String strK = bj.a().k(context);
        if (!TextUtils.isEmpty(strK)) {
            this.b.u = strK;
            return this.b.u;
        }
        String strC = de.c(1, context);
        if (!TextUtils.isEmpty(strC)) {
            this.b.u = strC;
            bj.a().e(context, strC);
            return this.b.u;
        }
        this.b.u = Constants.MAIN_VERSION_TAG;
        return this.b.u;
    }

    public String getCUID(Context context, boolean z) {
        if (this.b.g == null) {
            this.b.g = bj.a().f(context);
            if (this.b.g == null || Constants.MAIN_VERSION_TAG.equalsIgnoreCase(this.b.g)) {
                try {
                    this.b.g = dg.a(context);
                    Matcher matcher = Pattern.compile("\\s*|\t|\r|\n").matcher(this.b.g);
                    this.b.g = matcher.replaceAll(Constants.MAIN_VERSION_TAG);
                    this.b.g = getSecretValue(this.b.g);
                    bj.a().b(context, this.b.g);
                } catch (Exception e) {
                    db.c(e.getMessage());
                }
            }
        }
        if (z) {
            return this.b.g;
        }
        try {
            String str = this.b.g;
            if (!TextUtils.isEmpty(str)) {
                return new String(ct.b(1, cv.a(str.getBytes())));
            }
        } catch (Exception e2) {
            db.a(e2);
        }
        return null;
    }

    public String getDeviceId(TelephonyManager telephonyManager, Context context) {
        String strA = this.b.j;
        if (!TextUtils.isEmpty(strA)) {
            return this.b.j;
        }
        if (bj.a().j(context)) {
            this.b.j = getMacIdForTv(context);
            return this.b.j;
        }
        if (telephonyManager == null) {
            return this.b.j;
        }
        Pattern patternCompile = Pattern.compile("\\s*|\t|\r|\n");
        try {
            String deviceId = telephonyManager.getDeviceId();
            if (deviceId != null) {
                strA = patternCompile.matcher(deviceId).replaceAll(Constants.MAIN_VERSION_TAG);
            }
        } catch (Exception e) {
            db.a(e);
        }
        if (strA == null || strA.equals("000000000000000")) {
            strA = a(context);
        }
        if (de.s(context) && (TextUtils.isEmpty(strA) || strA.equals("000000000000000"))) {
            try {
                strA = c(context);
            } catch (Exception e2) {
                db.a(e2);
            }
        }
        if (TextUtils.isEmpty(strA) || strA.equals("000000000000000")) {
            strA = bj.a().e(context);
        }
        if (TextUtils.isEmpty(strA) || strA.equals("000000000000000")) {
            strA = "hol" + (new Date().getTime() + Constants.MAIN_VERSION_TAG).hashCode() + "mes";
            bj.a().a(context, strA);
        }
        this.b.j = strA;
        this.b.j = getSecretValue(this.b.j);
        return this.b.j;
    }

    public String getAppChannel(Context context) {
        return d(context);
    }

    private String d(Context context) {
        try {
            if (this.b.m == null || this.b.m.equals(Constants.MAIN_VERSION_TAG)) {
                boolean zH = bj.a().h(context);
                if (zH) {
                    this.b.m = bj.a().g(context);
                }
                if (!zH || this.b.m == null || this.b.m.equals(Constants.MAIN_VERSION_TAG)) {
                    this.b.m = de.a(context, "BaiduMobAd_CHANNEL");
                }
            }
        } catch (Exception e) {
            db.a(e);
        }
        return this.b.m;
    }

    public String getAppKey(Context context) {
        if (this.b.f == null) {
            this.b.f = de.a(context, "BaiduMobAd_STAT_ID");
        }
        return this.b.f;
    }

    public int getAppVersionCode(Context context) {
        if (this.b.h == -1) {
            this.b.h = de.e(context);
        }
        return this.b.h;
    }

    public String getAppVersionName(Context context) {
        if (TextUtils.isEmpty(this.b.i)) {
            this.b.i = de.f(context);
        }
        return this.b.i;
    }

    public String getOperator(TelephonyManager telephonyManager) {
        if (TextUtils.isEmpty(this.b.n)) {
            this.b.n = telephonyManager.getNetworkOperator();
        }
        return this.b.n;
    }

    public String getLinkedWay(Context context) {
        if (TextUtils.isEmpty(this.b.s)) {
            this.b.s = de.o(context);
        }
        return this.b.s;
    }

    public String getOSVersion() {
        if (TextUtils.isEmpty(this.b.c)) {
            this.b.c = Integer.toString(Build.VERSION.SDK_INT);
        }
        return this.b.c;
    }

    public String getOSSysVersion() {
        if (TextUtils.isEmpty(this.b.d)) {
            this.b.d = Build.VERSION.RELEASE;
        }
        return this.b.d;
    }

    public String getPhoneModel() {
        if (TextUtils.isEmpty(this.b.o)) {
            this.b.o = Build.MODEL;
        }
        return this.b.o;
    }

    public String getManufacturer() {
        if (TextUtils.isEmpty(this.b.p)) {
            this.b.p = Build.MANUFACTURER;
        }
        return this.b.p;
    }

    public boolean checkGPSLocationSetting(Context context) {
        return "true".equals(de.a(context, "BaiduMobAd_GPS_LOCATION"));
    }

    public boolean checkCellLocationSetting(Context context) {
        return "true".equalsIgnoreCase(de.a(context, "BaiduMobAd_CELL_LOCATION"));
    }

    public String getSecretValue(String str) {
        return ct.c(1, str.getBytes());
    }

    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", Constants.MAIN_VERSION_TAG);
    }

    public boolean isDeviceMacEnabled(Context context) {
        return bj.a().m(context);
    }
}
