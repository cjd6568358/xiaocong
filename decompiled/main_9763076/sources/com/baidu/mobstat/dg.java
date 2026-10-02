package com.baidu.mobstat;

import android.content.Context;
import android.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class dg {
    public static String a(Context context) {
        return e.a(context);
    }

    public static String a(long j) {
        Date date = new Date(j);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return simpleDateFormat.format(date);
    }

    public static boolean a(Class<?> cls, String str) {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String methodName = stackTraceElement.getMethodName();
            if (!TextUtils.isEmpty(methodName) && cls != null && methodName.equals(str)) {
                String className = stackTraceElement.getClassName();
                Class<?> cls2 = null;
                try {
                    cls2 = Class.forName(className);
                } catch (Throwable th) {
                }
                if (cls2 != null && cls.isAssignableFrom(cls2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
