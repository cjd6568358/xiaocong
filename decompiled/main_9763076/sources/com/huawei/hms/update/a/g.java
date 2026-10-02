package com.huawei.hms.update.a;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.io.IOException;

/* JADX INFO: compiled from: OtaUpdateDownload.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class g extends h {
    final /* synthetic */ int a;
    final /* synthetic */ f b;
    private long c;
    private int d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    g(f fVar, File file, int i, int i2) {
        super(file, i);
        this.b = fVar;
        this.a = i2;
        this.c = 0L;
        this.d = this.b.e.b();
    }

    @Override // com.huawei.hms.update.a.h, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        super.write(bArr, i, i2);
        this.d += i2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(jCurrentTimeMillis - this.c) > 1000) {
            this.c = jCurrentTimeMillis;
            a(this.d);
        }
        if (this.d == this.a) {
            a(this.d);
        }
    }

    private void a(int i) {
        this.b.e.a(this.b.a(), i);
        this.b.a(PushConstants.BROADCAST_MESSAGE_ARRIVE, i, this.a);
    }
}
