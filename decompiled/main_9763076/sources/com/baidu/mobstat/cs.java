package com.baidu.mobstat;

import android.annotation.SuppressLint;
import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class cs {
    @SuppressLint({"TrulyRandom"})
    public static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(bArr3);
    }

    public static byte[] a() {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(ParserConstants.LSHIFTASSIGN, new SecureRandom());
        return keyGenerator.generateKey().getEncoded();
    }

    public static byte[] b() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return bArr;
    }

    public static String a(byte[] bArr) {
        try {
            return b(a(), b(), bArr);
        } catch (Exception e) {
            db.b(e);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String b(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        try {
            return cv.b(a(bArr, bArr2, cx.a(bArr3))) + "|" + dc.a(bArr) + "|" + dc.a(bArr2);
        } catch (Exception e) {
            db.b(e);
            return Constants.MAIN_VERSION_TAG;
        }
    }
}
