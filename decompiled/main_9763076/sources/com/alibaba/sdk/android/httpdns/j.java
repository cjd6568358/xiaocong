package com.alibaba.sdk.android.httpdns;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j {
    private static Pattern a = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

    static String a(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(str.getBytes());
        byte[] bArrDigest = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : bArrDigest) {
            String hexString = Integer.toHexString(b & Constants.NETWORK_TYPE_UNCONNECTED);
            while (hexString.length() < 2) {
                hexString = PushConstants.PUSH_TYPE_NOTIFY + hexString;
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    static boolean b(String str) {
        if (str == null) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length <= 0 || charArray.length > 255) {
            return false;
        }
        for (char c : charArray) {
            if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && !((c >= '0' && c <= '9') || c == '.' || c == '-'))) {
                return false;
            }
        }
        return true;
    }

    static boolean c(String str) {
        return str != null && str.length() >= 7 && str.length() <= 15 && !str.equals(Constants.MAIN_VERSION_TAG) && a.matcher(str).matches();
    }
}
