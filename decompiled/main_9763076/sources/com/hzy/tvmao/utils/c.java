package com.hzy.tvmao.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: compiled from: StringUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    public static byte[] a(String str) {
        return Base64.decode(str, 0);
    }

    public static int b(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int[] c(String str) {
        return a(str, ",");
    }

    public static int[] a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] strArrSplit = str.split(str2);
        int[] iArr = new int[strArrSplit.length];
        for (int i = 0; i < strArrSplit.length; i++) {
            iArr[i] = b(strArrSplit[i]);
        }
        return iArr;
    }

    public static String a(String str, int i, char c) {
        if (str.length() < i) {
            StringBuilder sb = new StringBuilder(i);
            for (int length = str.length(); length < i; length++) {
                sb.append(c);
            }
            sb.append(str);
            return sb.toString();
        }
        return str;
    }

    public static byte[] d(String str) {
        String strReplaceAll = str.replaceAll("[^0-9,a-f,A-F]", Constants.MAIN_VERSION_TAG);
        byte[] bArr = new byte[strReplaceAll.length() / 2];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) Integer.parseInt(strReplaceAll.substring(i * 2, (i * 2) + 2), 16);
        }
        return bArr;
    }

    public static String e(String str) {
        byte[] bytes = str.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(bytes);
            return a(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            int i = b & Constants.NETWORK_TYPE_UNCONNECTED;
            if (i < 16) {
                stringBuffer.append(PushConstants.PUSH_TYPE_NOTIFY);
            }
            stringBuffer.append(Integer.toHexString(i));
        }
        return stringBuffer.toString();
    }
}
