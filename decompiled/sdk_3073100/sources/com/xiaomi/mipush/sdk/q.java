package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.xmpush.thrift.aa;
import com.xiaomi.xmpush.thrift.ab;
import com.xiaomi.xmpush.thrift.ae;
import com.xiaomi.xmpush.thrift.ag;
import com.xiaomi.xmpush.thrift.ai;
import com.xiaomi.xmpush.thrift.aj;
import com.xiaomi.xmpush.thrift.al;
import com.xiaomi.xmpush.thrift.an;
import com.xiaomi.xmpush.thrift.ap;
import com.xiaomi.xmpush.thrift.aq;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class q {
    private static final byte[] a = {100, 23, 84, 114, 72, 0, 4, 97, 73, 97, 2, 52, 84, 102, 18, 32};

    protected static <T extends org.apache.thrift.a<T, ?>> ab a(Context context, T t, com.xiaomi.xmpush.thrift.a aVar) {
        return a(context, t, aVar, !aVar.equals(com.xiaomi.xmpush.thrift.a.Registration), context.getPackageName(), a.a(context).c());
    }

    protected static <T extends org.apache.thrift.a<T, ?>> ab a(Context context, T t, com.xiaomi.xmpush.thrift.a aVar, boolean z, String str, String str2) {
        byte[] bArrA = aq.a(t);
        if (bArrA == null) {
            com.xiaomi.channel.commonutils.logger.b.a("invoke convertThriftObjectToBytes method, return null.");
            return null;
        }
        ab abVar = new ab();
        if (z) {
            String strF = a.a(context).f();
            if (TextUtils.isEmpty(strF)) {
                com.xiaomi.channel.commonutils.logger.b.a("regSecret is empty, return null");
                return null;
            }
            try {
                bArrA = b(com.xiaomi.channel.commonutils.string.a.a(strF), bArrA);
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.d("encryption error. ");
            }
        }
        com.xiaomi.xmpush.thrift.u uVar = new com.xiaomi.xmpush.thrift.u();
        uVar.a = 5L;
        uVar.b = "fakeid";
        abVar.a(uVar);
        abVar.a(ByteBuffer.wrap(bArrA));
        abVar.a(aVar);
        abVar.c(true);
        abVar.b(str);
        abVar.a(z);
        abVar.a(str2);
        return abVar;
    }

    private static Cipher a(byte[] bArr, int i) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(a);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(i, secretKeySpec, ivParameterSpec);
        return cipher;
    }

    public static org.apache.thrift.a a(Context context, ab abVar) {
        byte[] bArrF;
        if (abVar.c()) {
            try {
                bArrF = a(com.xiaomi.channel.commonutils.string.a.a(a.a(context).f()), abVar.f());
            } catch (Exception e) {
                throw new c("the aes decrypt failed.", e);
            }
        } else {
            bArrF = abVar.f();
        }
        org.apache.thrift.a aVarA = a(abVar.a(), abVar.c);
        if (aVarA != null) {
            aq.a(aVarA, bArrF);
        }
        return aVarA;
    }

    private static org.apache.thrift.a a(com.xiaomi.xmpush.thrift.a aVar, boolean z) {
        switch (r.a[aVar.ordinal()]) {
            case 1:
                return new ag();
            case 2:
                return new an();
            case 3:
                return new al();
            case 4:
                return new ap();
            case 5:
                return new aj();
            case 6:
                return new com.xiaomi.xmpush.thrift.w();
            case 7:
                return new aa();
            case 8:
                return new ai();
            case 9:
                if (z) {
                    return new ae();
                }
                com.xiaomi.xmpush.thrift.x xVar = new com.xiaomi.xmpush.thrift.x();
                xVar.a(true);
                return xVar;
            case 10:
                return new aa();
            default:
                return null;
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        return a(bArr, 2).doFinal(bArr2);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        return a(bArr, 1).doFinal(bArr2);
    }
}
