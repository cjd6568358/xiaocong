package com.tencent.android.tpush.encrypt;

import com.tencent.android.tpush.common.Constants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static String a(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return a(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "md5 encrypt:" + str, e);
            return Constants.MAIN_VERSION_TAG;
        } catch (Exception e2) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "md5 encrypt:" + str, e2);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toHexString(b & Constants.NETWORK_TYPE_UNCONNECTED));
        }
        return sb.toString();
    }
}
