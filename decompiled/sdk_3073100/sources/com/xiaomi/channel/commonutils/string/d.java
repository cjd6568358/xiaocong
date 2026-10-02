package com.xiaomi.channel.commonutils.string;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class d {
    public static String a(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length())));
        }
        return stringBuffer.toString();
    }

    public static String a(String str) {
        if (str == null) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(c(str));
            return String.format("%1$032X", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            return str;
        }
    }

    public static String a(Collection<?> collection, String str) {
        if (collection == null) {
            return null;
        }
        return a(collection.iterator(), str);
    }

    public static String a(Iterator<?> it, String str) {
        if (it == null) {
            return null;
        }
        if (!it.hasNext()) {
            return "";
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return next.toString();
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        if (next != null) {
            stringBuffer.append(next);
        }
        while (it.hasNext()) {
            if (str != null) {
                stringBuffer.append(str);
            }
            Object next2 = it.next();
            if (next2 != null) {
                stringBuffer.append(next2);
            }
        }
        return stringBuffer.toString();
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(c(str));
            return String.format("%1$032X", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            return str;
        }
    }

    public static byte[] c(String str) {
        try {
            return str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            return str.getBytes();
        }
    }

    public static boolean d(String str) {
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char cCharAt = str.charAt(i);
                if (cCharAt < 0 || cCharAt > 127) {
                    return false;
                }
            }
        }
        return true;
    }
}
