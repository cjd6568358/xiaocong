package com.alibaba.sdk.android.beacon;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class c {
    private static final char[] a = "0123456789abcdef".toCharArray();

    static String a(Context context) {
        String str = Constants.MAIN_VERSION_TAG;
        if (context == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return str == null ? Constants.MAIN_VERSION_TAG : str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    static String a(String str, String str2) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return a(mac.doFinal(str2.getBytes()));
        } catch (Throwable th) {
            th.printStackTrace();
            return Constants.MAIN_VERSION_TAG;
        }
    }

    private static String a(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & Constants.NETWORK_TYPE_UNCONNECTED;
            cArr[i * 2] = a[i2 >>> 4];
            cArr[(i * 2) + 1] = a[i2 & 15];
        }
        return new String(cArr);
    }
}
