package com.xiaocong.smarthome.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MD5Util {
    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }
}
