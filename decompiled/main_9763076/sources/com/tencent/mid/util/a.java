package com.tencent.mid.util;

import bsh.ParserConstants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static final List<Integer> a = new ArrayList(Arrays.asList(2, 4, 8, 16, 32, 64, Integer.valueOf(ParserConstants.LSHIFTASSIGN), 256, Integer.valueOf(WXMediaMessage.TITLE_LENGTH_LIMIT), Integer.valueOf(WXMediaMessage.DESCRIPTION_LENGTH_LIMIT), 2048));
    private SecretKey b = null;
    private IvParameterSpec c = null;

    private int a(int i, int i2) {
        if (i < i2) {
            i = i2;
        }
        if (a.contains(Integer.valueOf(i))) {
            return i;
        }
        for (Integer num : a) {
            if (num.intValue() > i) {
                return num.intValue();
            }
        }
        return i;
    }

    public static SecretKey a() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(ParserConstants.LSHIFTASSIGN);
            return new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private byte[] a(String str, int i) {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        if (length >= i) {
            return bytes;
        }
        byte[] bArr = new byte[i];
        Arrays.fill(bArr, (byte) 0);
        System.arraycopy(bytes, 0, bArr, 0, length);
        return bArr;
    }

    public static IvParameterSpec d() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return new IvParameterSpec(bArr);
    }

    public void a(String str, String str2) {
        int iA = a(str.length(), str2.length());
        byte[] bArrA = a(str, iA);
        byte[] bArrA2 = a(str2, iA);
        this.b = new SecretKeySpec(bArrA, "AES");
        this.c = new IvParameterSpec(bArrA2);
    }

    public byte[] a(byte[] bArr) throws Exception {
        if (this.b == null) {
            throw new Exception("密钥为空, 请设置");
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(1, this.b, this.c);
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] b() {
        if (this.b != null) {
            return this.b.getEncoded();
        }
        return null;
    }

    public byte[] b(byte[] bArr) throws Exception {
        if (this.b == null) {
            throw new Exception("密钥为空, 请设置");
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(2, this.b, this.c);
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] c() {
        if (this.c != null) {
            return this.c.getIV();
        }
        return null;
    }

    public void e() {
        this.b = a();
        this.c = d();
    }
}
