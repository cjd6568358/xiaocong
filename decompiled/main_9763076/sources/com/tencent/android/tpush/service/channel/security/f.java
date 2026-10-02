package com.tencent.android.tpush.service.channel.security;

import com.tencent.android.tpush.common.Constants;
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

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    private static RSAPublicKey a = null;

    public static void a(String str) throws Exception {
        try {
            a = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(a.a(str, 0)));
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        } catch (NoSuchAlgorithmException e2) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e3) {
            throw new Exception("公钥非法");
        }
    }

    public static String a(byte[] bArr) throws Exception {
        if (a == null) {
            a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDyMrH3s73WgYu7MnBDurisRILqXwj1enRsuO7lPZCrPIxRd1RpTrv0xoWzKSyl2gwhY+l6/csBqs/Ako70II7wFWP3ugyKroHaWgvPw9M090xowDqBhQjcEfWKMd8A/cimVAlO/1p7kQDH0eTvZvOsv7sLmfTsMe8PkT2t22gZWQIDAQAB");
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
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

    public static byte[] b(String str) {
        if (str == null || str.length() < 2) {
            return new byte[0];
        }
        String lowerCase = str.toLowerCase();
        int length = lowerCase.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) (Integer.parseInt(lowerCase.substring(i * 2, (i * 2) + 2), 16) & 255);
        }
        return bArr;
    }

    public static byte[][] a(byte[] bArr, int i) {
        int length = bArr.length / i;
        int length2 = bArr.length % i;
        int i2 = length2 != 0 ? 1 : 0;
        byte[][] bArr2 = new byte[length + i2][];
        for (int i3 = 0; i3 < length + i2; i3++) {
            byte[] bArr3 = new byte[i];
            if (i3 == (length + i2) - 1 && length2 != 0) {
                System.arraycopy(bArr, i3 * i, bArr3, 0, length2);
            } else {
                System.arraycopy(bArr, i3 * i, bArr3, 0, i);
            }
            bArr2[i3] = bArr3;
        }
        return bArr2;
    }
}
