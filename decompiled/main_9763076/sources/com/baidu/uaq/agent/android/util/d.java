package com.baidu.uaq.agent.android.util;

import com.baidu.uaq.agent.android.logging.a;
import com.baidu.uaq.agent.android.logging.b;
import com.tencent.android.tpush.common.Constants;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: compiled from: EncryptUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static final a LOG;
    static final /* synthetic */ boolean cD;

    static {
        cD = !d.class.desiredAssertionStatus();
        LOG = b.bg();
    }

    public static String P(String content) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        if (content == null) {
            LOG.warning("AES warning: content is null");
            return Constants.MAIN_VERSION_TAG;
        }
        byte[] rawKey = Q("587a0fb0c91ae061e66adbf2ec56f0b8");
        byte[] result = a(rawKey, content.getBytes());
        return a(result);
    }

    private static byte[] a(byte[] raw, byte[] clear) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = a(1, raw);
        if (cD || cipher != null) {
            return cipher.doFinal(clear);
        }
        throw new AssertionError();
    }

    private static Cipher a(int mode, byte[] key) {
        try {
            Cipher mCipher = Cipher.getInstance("AES");
            Key keyspec = new SecretKeySpec(key, "AES");
            mCipher.init(mode, keyspec);
            return mCipher;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String a(byte[] buf) {
        if (buf == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        StringBuffer result = new StringBuffer(buf.length * 2);
        for (byte b : buf) {
            a(result, b);
        }
        return result.toString();
    }

    private static byte[] Q(String source) {
        int length = source.length() / 2;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = Integer.valueOf(source.substring(i * 2, (i * 2) + 2), 16).byteValue();
        }
        return result;
    }

    private static void a(StringBuffer sb, byte b) {
        sb.append("0123456789ABCDEF".charAt((b >> 4) & 15)).append("0123456789ABCDEF".charAt(b & 15));
    }
}
