package com.baidu.location.d;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.location.a.m;
import com.tencent.android.tpush.common.Constants;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public String a = null;
    public String b = null;
    public String c = null;
    private boolean i = false;
    private static b h = null;
    public static String d = null;
    public static String e = null;
    public static String f = null;
    public static String g = null;

    private b() {
        if (com.baidu.location.f.getServiceContext() != null) {
            a(com.baidu.location.f.getServiceContext());
        }
    }

    public static b a() {
        if (h == null) {
            h = new b();
        }
        return h;
    }

    public String a(boolean z) {
        return a(z, (String) null);
    }

    public String a(boolean z, String str) {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.52f);
        if (z) {
            if (j.g.equals("all")) {
                stringBuffer.append("&addr=allj");
            }
            if (j.h || j.j || j.k || j.i) {
                stringBuffer.append("&sema=");
                if (j.h) {
                    stringBuffer.append("aptag|");
                }
                if (j.i) {
                    stringBuffer.append("aptagd|");
                }
                if (j.j) {
                    stringBuffer.append("poiregion|");
                }
                if (j.k) {
                    stringBuffer.append("regular");
                }
            }
        }
        if (z) {
            if (str == null) {
                stringBuffer.append("&coor=gcj02");
            } else {
                stringBuffer.append("&coor=");
                stringBuffer.append(str);
            }
        }
        if (this.b == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(this.a);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.b);
            if (this.a != null && !this.a.equals("NULL") && !this.b.contains(new StringBuffer(this.a).reverse().toString())) {
                stringBuffer.append("&Aim=");
                stringBuffer.append(this.a);
            }
        }
        if (this.c != null) {
            stringBuffer.append("&Aid=");
            stringBuffer.append(this.c);
        }
        stringBuffer.append("&fw=");
        stringBuffer.append(com.baidu.location.f.getFrameVersion());
        stringBuffer.append("&lt=1");
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        String strB = j.b();
        if (strB != null) {
            stringBuffer.append("&laip=");
            stringBuffer.append(strB);
        }
        float fD = m.a().d();
        if (fD != 0.0f) {
            stringBuffer.append("&altv=");
            stringBuffer.append(String.format(Locale.US, "%.5f", Float.valueOf(fD)));
        }
        stringBuffer.append("&resid=");
        stringBuffer.append("12");
        stringBuffer.append("&os=A");
        stringBuffer.append(Build.VERSION.SDK);
        if (z) {
            stringBuffer.append("&sv=");
            String strSubstring = Build.VERSION.RELEASE;
            if (strSubstring != null && strSubstring.length() > 6) {
                strSubstring = strSubstring.substring(0, 6);
            }
            stringBuffer.append(strSubstring);
        }
        return stringBuffer.toString();
    }

    public void a(Context context) {
        if (context == null || this.i) {
            return;
        }
        try {
            this.a = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e2) {
            this.a = "NULL";
        }
        try {
            this.b = CommonParam.a(context);
        } catch (Exception e3) {
            this.b = null;
        }
        try {
            this.c = com.baidu.android.bbalbs.common.util.b.b(context);
        } catch (Exception e4) {
            this.c = null;
        }
        try {
            d = context.getPackageName();
        } catch (Exception e5) {
            d = null;
        }
        j.n = Constants.MAIN_VERSION_TAG + this.b;
        this.i = true;
    }

    public void a(String str, String str2) {
        e = str;
        d = str2;
    }

    public String b() {
        return this.b != null ? "v7.52|" + this.b + "|" + Build.MODEL : "v7.52|" + this.a + "|" + Build.MODEL;
    }

    public String c() {
        return d != null ? b() + "|" + d : b();
    }
}
