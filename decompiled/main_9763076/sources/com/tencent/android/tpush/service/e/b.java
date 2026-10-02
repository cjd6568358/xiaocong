package com.tencent.android.tpush.service.e;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public static String a = "yyyyMMdd_HHmm";

    public static String a() {
        return new SimpleDateFormat(a).format(new Date());
    }

    public static Date a(String str) {
        try {
            return new SimpleDateFormat(a).parse(str);
        } catch (Exception e) {
            Log.e("XGLogger", "parse filename datetime error - " + str, e);
            return null;
        }
    }

    public static boolean a(Date date, int i) {
        if (date == null) {
            return false;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(date.getTime());
            calendar.add(5, -i);
            return calendar.after(calendar2);
        } catch (Exception e) {
            Log.e("XGLogger", "DateUtils -> isDaysAgo ", e);
            return false;
        }
    }
}
