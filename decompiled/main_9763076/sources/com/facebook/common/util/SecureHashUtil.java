package com.facebook.common.util;

import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SecureHashUtil {
    static final byte[] HEX_CHAR_TABLE = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    public static String makeSHA1HashBase64(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(bytes, 0, bytes.length);
            byte[] sha1hash = md.digest();
            return Base64.encodeToString(sha1hash, 11);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
