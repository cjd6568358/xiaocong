package com.tencent.android.tpush.common;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class m {
    private static String a(String str) {
        return com.tencent.android.tpush.encrypt.a.a(str);
    }

    public static boolean a(Context context, String str, String str2, boolean z) {
        if (z) {
            try {
                String str3 = (String) com.tencent.android.tpush.service.cache.b.a(str);
                if (str3 != null && str2 != null && str3.equals(str2)) {
                    return true;
                }
                com.tencent.android.tpush.service.cache.b.a(str, str2);
            } catch (Exception e) {
                com.tencent.android.tpush.a.a.c("PushMd5Pref", "putString", e);
                return false;
            }
        }
        n.b(context, a(str), str2);
        return true;
    }

    public static String a(Context context, String str, boolean z) {
        String strA;
        try {
            if (z) {
                strA = (String) com.tencent.android.tpush.service.cache.b.a(str);
                if (strA == null) {
                    strA = n.a(context, a(str), (String) null);
                    com.tencent.android.tpush.service.cache.b.a(str, strA);
                }
            } else {
                strA = n.a(context, a(str), (String) null);
            }
            return strA;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("PushMd5Pref", "getString", e);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static boolean a(Context context, String str, int i) {
        try {
            n.b(context, a(str), i);
            return true;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("PushMd5Pref", "putInt", e);
            return false;
        }
    }

    public static int b(Context context, String str, int i) {
        try {
            return n.a(context, a(str), i);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("PushMd5Pref", "getInt", e);
            return 0;
        }
    }
}
