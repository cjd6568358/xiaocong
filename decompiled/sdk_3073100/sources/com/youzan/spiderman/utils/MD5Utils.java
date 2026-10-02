package com.youzan.spiderman.utils;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.security.MessageDigest;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MD5Utils {
    public static String getStringMd5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] array = messageDigest.digest(str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET));
            StringBuffer sb = new StringBuffer();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 255) | 256).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            Logger.e("error", e);
            return "";
        }
    }
}
