package com.alibaba.sdk.android.httpdns.b;

import android.text.TextUtils;
import java.text.SimpleDateFormat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static long a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            return Long.valueOf(str).longValue();
        } catch (Exception e) {
            return 0L;
        }
    }

    static String c(String str) {
        try {
            return a.format(Long.valueOf(a(str) * 1000));
        } catch (Exception e) {
            return a.format(Long.valueOf(System.currentTimeMillis()));
        }
    }

    static String d(String str) {
        try {
            return String.valueOf(a.parse(str).getTime() / 1000);
        } catch (Exception e) {
            return String.valueOf(System.currentTimeMillis() / 1000);
        }
    }
}
