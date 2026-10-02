package com.huawei.hms.support.api.push.a.a.b;

import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: AES128_CBC.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    public static String a(String str) {
        return TextUtils.isEmpty(str) ? Constants.MAIN_VERSION_TAG : a(str, a());
    }

    public static String a(String str, byte[] bArr) {
        if (TextUtils.isEmpty(str) || bArr == null || bArr.length <= 0) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bArr2 = new byte[16];
            new SecureRandom().nextBytes(bArr2);
            cipher.init(1, secretKeySpec, new IvParameterSpec(bArr2));
            return a(com.huawei.hms.support.api.push.a.a.a.a.a(bArr2), com.huawei.hms.support.api.push.a.a.a.a.a(cipher.doFinal(str.getBytes(HTTP.UTF_8))));
        } catch (UnsupportedEncodingException e) {
            b("UnsupportedEncodingException aes cbc encrypter data error", e);
            return null;
        } catch (IllegalArgumentException e2) {
            b("IllegalArgumentException aes cbc encrypter data error", e2);
            return null;
        } catch (InvalidAlgorithmParameterException e3) {
            b("InvalidAlgorithmParameterException aes cbc encrypter data error", e3);
            return null;
        } catch (InvalidKeyException e4) {
            b("InvalidKeyException aes cbc encrypter data error", e4);
            return null;
        } catch (IllegalBlockSizeException e5) {
            b("IllegalBlockSizeException aes cbc encrypter data error", e5);
            return null;
        } catch (Exception e6) {
            b("aes cbc encrypter data error", e6);
            return null;
        }
    }

    public static String b(String str) {
        return TextUtils.isEmpty(str) ? Constants.MAIN_VERSION_TAG : b(str, a());
    }

    public static String b(String str, byte[] bArr) {
        if (TextUtils.isEmpty(str) || bArr == null || bArr.length <= 0) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            String strC = c(str);
            String strD = d(str);
            if (TextUtils.isEmpty(strC) || TextUtils.isEmpty(strD)) {
                if (com.huawei.hms.support.log.a.b()) {
                    com.huawei.hms.support.log.a.b("AES128_CBC", "ivParameter or encrypedWord is null");
                }
                return Constants.MAIN_VERSION_TAG;
            }
            cipher.init(2, secretKeySpec, new IvParameterSpec(com.huawei.hms.support.api.push.a.a.a.a.b(strC)));
            return new String(cipher.doFinal(com.huawei.hms.support.api.push.a.a.a.a.b(strD)), HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            a("aes cbc decrypter data error", e);
            return Constants.MAIN_VERSION_TAG;
        } catch (NoSuchAlgorithmException e2) {
            a("aes cbc decrypter data error", e2);
            return Constants.MAIN_VERSION_TAG;
        } catch (BadPaddingException e3) {
            a("aes cbc decrypter data error", e3);
            return Constants.MAIN_VERSION_TAG;
        } catch (IllegalBlockSizeException e4) {
            a("aes cbc decrypter data error", e4);
            return Constants.MAIN_VERSION_TAG;
        } catch (NoSuchPaddingException e5) {
            a("aes cbc decrypter data error", e5);
            return Constants.MAIN_VERSION_TAG;
        } catch (Exception e6) {
            a("aes cbc encrypter data error", e6);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    private static byte[] a() {
        byte[] bArrB = com.huawei.hms.support.api.push.a.a.a.a.b(com.huawei.hms.support.api.push.a.a.a.b.a());
        byte[] bArrB2 = com.huawei.hms.support.api.push.a.a.a.a.b(b.a());
        return a(a(a(bArrB, bArrB2), com.huawei.hms.support.api.push.a.a.a.a.b("2A57086C86EF54970C1E6EB37BFC72B1")));
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr.length == 0 || bArr2.length == 0) {
            return new byte[0];
        }
        int length = bArr.length;
        if (length != bArr2.length) {
            return new byte[0];
        }
        byte[] bArr3 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr3[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
        return bArr3;
    }

    private static byte[] a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return new byte[0];
        }
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] >> 2);
        }
        return bArr;
    }

    private static String a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str2.substring(0, 6));
            stringBuffer.append(str.substring(0, 6));
            stringBuffer.append(str2.substring(6, 10));
            stringBuffer.append(str.substring(6, 16));
            stringBuffer.append(str2.substring(10, 16));
            stringBuffer.append(str.substring(16));
            stringBuffer.append(str2.substring(16));
            return stringBuffer.toString();
        } catch (Exception e) {
            if (com.huawei.hms.support.log.a.d()) {
                com.huawei.hms.support.log.a.d("AES128_CBC", e.getMessage());
            }
            return Constants.MAIN_VERSION_TAG;
        }
    }

    private static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str.substring(6, 12));
            stringBuffer.append(str.substring(16, 26));
            stringBuffer.append(str.substring(32, 48));
            return stringBuffer.toString();
        } catch (Exception e) {
            if (com.huawei.hms.support.log.a.d()) {
                com.huawei.hms.support.log.a.d("AES128_CBC", "get iv error:" + e.getMessage());
            }
            return Constants.MAIN_VERSION_TAG;
        }
    }

    private static String d(String str) {
        if (TextUtils.isEmpty(str)) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(str.substring(0, 6));
            stringBuffer.append(str.substring(12, 16));
            stringBuffer.append(str.substring(26, 32));
            stringBuffer.append(str.substring(48));
            return stringBuffer.toString();
        } catch (Exception e) {
            if (com.huawei.hms.support.log.a.d()) {
                com.huawei.hms.support.log.a.d("AES128_CBC", "get encrypt word error:" + e.getMessage());
            }
            return Constants.MAIN_VERSION_TAG;
        }
    }

    private static void a(String str, Exception exc) {
        if (com.huawei.hms.support.log.a.d()) {
            com.huawei.hms.support.log.a.d("AES128_CBC", str + exc.getMessage());
        }
    }

    private static void b(String str, Exception exc) {
        if (com.huawei.hms.support.log.a.d()) {
            com.huawei.hms.support.log.a.d("AES128_CBC", str + exc.getMessage());
        }
    }
}
