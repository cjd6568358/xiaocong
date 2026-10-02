package com.baidu.mobstat;

import com.tencent.android.tpush.common.Constants;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ct {
    public static byte[] a(int i, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        int i2 = i - 1;
        if (i2 < 0 || cw.a.length <= i2) {
            return new byte[0];
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(cw.a[i2].getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, secretKeySpec);
        return cipher.doFinal(bArr);
    }

    public static byte[] b(int i, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        int i2 = i - 1;
        if (i2 < 0 || cw.a.length <= i2) {
            return new byte[0];
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(cw.a[i2].getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, secretKeySpec);
        return cipher.doFinal(bArr);
    }

    public static String c(int i, byte[] bArr) {
        try {
            return cv.b(a(i, bArr));
        } catch (Exception e) {
            db.a(e);
            return Constants.MAIN_VERSION_TAG;
        }
    }
}
