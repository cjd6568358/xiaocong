package com.tencent.mid.util;

import android.util.Base64;
import com.tencent.android.tpush.common.Constants;
import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private static RSAPublicKey a = null;

    public static void a(String str) throws Exception {
        try {
            a = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } catch (NoSuchAlgorithmException e2) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e3) {
            throw new Exception("公钥非法");
        }
    }

    public static byte[] a(byte[] bArr) throws Exception {
        if (a == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1PADDING");
        cipher.init(1, a);
        int length = bArr.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        int i2 = 0;
        while (length - i2 > 0) {
            byte[] bArrDoFinal = length - i2 > 117 ? cipher.doFinal(bArr, i2, 117) : cipher.doFinal(bArr, i2, length - i2);
            byteArrayOutputStream.write(bArrDoFinal, 0, bArrDoFinal.length);
            int i3 = i + 1;
            i2 = i3 * 117;
            i = i3;
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }

    public static byte[][] a(byte[] bArr, int i) {
        int length = bArr.length / i;
        int length2 = bArr.length % i;
        int i2 = length2 != 0 ? 1 : 0;
        byte[][] bArr2 = new byte[length + i2][];
        for (int i3 = 0; i3 < length + i2; i3++) {
            byte[] bArr3 = new byte[i];
            if (i3 != (length + i2) - 1 || length2 == 0) {
                System.arraycopy(bArr, i3 * i, bArr3, 0, i);
            } else {
                System.arraycopy(bArr, i3 * i, bArr3, 0, length2);
            }
            bArr2[i3] = bArr3;
        }
        return bArr2;
    }

    public static String b(byte[] bArr) throws Exception {
        if (a == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1PADDING");
            cipher.init(2, a);
            byte[][] bArrA = a(bArr, a.getModulus().bitLength() / 8);
            int length = bArrA.length;
            String str = Constants.MAIN_VERSION_TAG;
            int i = 0;
            while (i < length) {
                String str2 = str + new String(cipher.doFinal(bArrA[i]), HTTP.UTF_8);
                i++;
                str = str2;
            }
            return str;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (NoSuchAlgorithmException e2) {
            throw new Exception("无此解密算法");
        } catch (BadPaddingException e3) {
            throw new Exception("密文数据已损坏");
        } catch (IllegalBlockSizeException e4) {
            throw new Exception("密文长度非法");
        } catch (NoSuchPaddingException e5) {
            e5.printStackTrace();
            return null;
        }
    }
}
