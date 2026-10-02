package com.hianalytics.android.b.a;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class b {
    public static byte[] a(String str, byte[] bArr) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            int length = bArr.length;
            if (length % blockSize != 0) {
                length += blockSize - (length % blockSize);
            }
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            cipher.init(1, new SecretKeySpec(str.getBytes(HTTP.UTF_8), "AES"), new IvParameterSpec(str.getBytes(HTTP.UTF_8)));
            return cipher.doFinal(bArr2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
