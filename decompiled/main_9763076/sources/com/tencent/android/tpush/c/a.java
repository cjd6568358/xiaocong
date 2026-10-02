package com.tencent.android.tpush.c;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static final String[] a = {"com.tencent.android.tpush.otherpush.mipush.impl.OtherPushImpl", "com.tencent.android.tpush.otherpush.fcm.impl.OtherPushImpl"};
    private static int b = -2;
    private static String c = null;

    public static boolean a(Context context) {
        if (b == -2) {
            for (int i = 0; i < a.length; i++) {
                try {
                    Class.forName(a[i]);
                    if (a(context, a[i])) {
                        b = i;
                        c = a[i];
                        return true;
                    }
                } catch (ClassNotFoundException e) {
                }
            }
            b = -1;
        }
        return b > -1;
    }

    public static boolean a(Context context, String str) {
        try {
            Class<?> cls = Class.forName(str);
            return ((Boolean) cls.getMethod("checkDevice", Context.class).invoke(cls, context)).booleanValue();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "checkDevice Error for InvocationTargetException: " + cause.getMessage());
            cause.printStackTrace();
            return false;
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.j(Constants.OTHER_PUSH_TAG, "checkDevice Error, are you import otherpush package? " + e2);
            return false;
        }
    }

    public static void b(Context context, String str) {
        a(context, str, "com.tencent.android.tpush.otherpush.fcm.impl.OtherPushImpl");
    }

    private static void a(Context context, String str, String str2) {
        try {
            Class<?> cls = Class.forName(str2);
            cls.getMethod("setAppid", Context.class, String.class).invoke(cls, context, str);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.h(Constants.OTHER_PUSH_TAG, "setAppid Error, are you import otherpush package? " + e);
        }
    }
}
