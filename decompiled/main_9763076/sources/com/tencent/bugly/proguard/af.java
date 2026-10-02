package com.tencent.bugly.proguard;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class af implements ag {
    private String a = null;

    @Override // com.tencent.bugly.proguard.ag
    public final byte[] a(byte[] bArr) throws Exception {
        if (this.a == null || bArr == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(this.a.getBytes(HTTP.UTF_8))), new IvParameterSpec(this.a.getBytes(HTTP.UTF_8)));
        return cipher.doFinal(bArr);
    }

    @Override // com.tencent.bugly.proguard.ag
    public final byte[] b(byte[] bArr) throws Exception {
        if (this.a == null || bArr == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(1, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(this.a.getBytes(HTTP.UTF_8))), new IvParameterSpec(this.a.getBytes(HTTP.UTF_8)));
        return cipher.doFinal(bArr);
    }

    @Override // com.tencent.bugly.proguard.ag
    public final void a(String str) {
        if (str != null) {
            this.a = str;
        }
    }
}
