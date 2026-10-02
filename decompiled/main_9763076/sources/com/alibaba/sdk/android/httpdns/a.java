package com.alibaba.sdk.android.httpdns;

import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a {
    private static long a;
    private static String sSecretKey;

    static String a(String str, String str2) {
        if (!j.b(str)) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            return j.a(str + "-" + sSecretKey + "-" + str2);
        } catch (NoSuchAlgorithmException e) {
            return Constants.MAIN_VERSION_TAG;
        }
    }

    static boolean a() {
        return !TextUtils.isEmpty(sSecretKey);
    }

    static String getTimestamp() {
        return String.valueOf((System.currentTimeMillis() / 1000) + a + 600);
    }

    static void setAuthCurrentTime(long j) {
        a = j - (System.currentTimeMillis() / 1000);
    }

    static void setSecretKey(String str) {
        sSecretKey = str;
    }
}
