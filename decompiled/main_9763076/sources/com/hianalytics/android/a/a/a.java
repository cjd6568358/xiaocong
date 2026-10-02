package com.hianalytics.android.a.a;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a {
    public static String a(Context context) {
        String strB;
        StringBuffer stringBuffer = new StringBuffer("1.0");
        String strA = com.hianalytics.android.b.a.a.a(context);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            com.hianalytics.android.b.a.a.h();
            return null;
        }
        Configuration configuration = context.getResources().getConfiguration();
        String string = Constants.MAIN_VERSION_TAG;
        if (configuration != null && configuration.locale != null) {
            string = configuration.locale.toString();
        }
        try {
            strB = com.hianalytics.android.b.a.a.b(telephonyManager.getDeviceId());
        } catch (SecurityException e) {
            e.printStackTrace();
            strB = Constants.MAIN_VERSION_TAG;
        }
        String strE = com.hianalytics.android.b.a.a.e(context);
        if (com.hianalytics.android.b.a.a.f(context)) {
            stringBuffer.append(",").append("Android" + Build.VERSION.RELEASE).append(",").append(string).append(",").append(Build.MODEL).append(",").append(Build.DISPLAY).append(",").append(strE).append(",").append(strB).append(",").append(strA).append(",").append(com.hianalytics.android.b.a.a.b(context));
            com.hianalytics.android.b.a.a.h();
        } else {
            stringBuffer.append(",,,,,").append(strE).append(",").append(strB).append(",").append(strA).append(",");
            com.hianalytics.android.b.a.a.h();
        }
        return stringBuffer.toString();
    }
}
