package com.tencent.bugly.proguard;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class aj extends k implements Cloneable {
    private static byte[] d;
    private byte a;
    private String b;
    private byte[] c;

    public aj() {
        this.a = (byte) 0;
        this.b = Constants.MAIN_VERSION_TAG;
        this.c = null;
    }

    public aj(byte b, String str, byte[] bArr) {
        this.a = (byte) 0;
        this.b = Constants.MAIN_VERSION_TAG;
        this.c = null;
        this.a = b;
        this.b = str;
        this.c = bArr;
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(j jVar) {
        jVar.a(this.a, 0);
        jVar.a(this.b, 1);
        if (this.c != null) {
            jVar.a(this.c, 2);
        }
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        this.a = iVar.a(this.a, 0, true);
        this.b = iVar.b(1, true);
        if (d == null) {
            d = new byte[]{0};
        }
        byte[] bArr = d;
        this.c = iVar.c(2, false);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(StringBuilder sb, int i) {
    }
}
