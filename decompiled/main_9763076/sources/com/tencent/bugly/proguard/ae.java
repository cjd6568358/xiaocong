package com.tencent.bugly.proguard;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ae implements ag {
    private String a = null;

    @Override // com.tencent.bugly.proguard.ag
    public final byte[] a(byte[] bArr) throws Exception {
        if (this.a == null || bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(((int) b) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.a.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(this.a.getBytes()));
        byte[] bArrDoFinal = cipher.doFinal(bArr);
        StringBuffer stringBuffer2 = new StringBuffer();
        for (byte b2 : bArrDoFinal) {
            stringBuffer2.append(((int) b2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        }
        return bArrDoFinal;
    }

    @Override // com.tencent.bugly.proguard.ag
    public final byte[] b(byte[] bArr) throws Exception {
        if (this.a == null || bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(((int) b) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.a.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, secretKeySpec, new IvParameterSpec(this.a.getBytes()));
        byte[] bArrDoFinal = cipher.doFinal(bArr);
        StringBuffer stringBuffer2 = new StringBuffer();
        for (byte b2 : bArrDoFinal) {
            stringBuffer2.append(((int) b2) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        }
        return bArrDoFinal;
    }

    @Override // com.tencent.bugly.proguard.ag
    public final void a(String str) {
        if (str != null) {
            for (int length = str.length(); length < 16; length++) {
                str = str + PushConstants.PUSH_TYPE_NOTIFY;
            }
            this.a = str.substring(0, 16);
        }
    }
}
