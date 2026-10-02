package com.tencent.android.tpush.service.channel.a;

import com.tencent.android.tpush.common.Constants;
import java.nio.channels.SocketChannel;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d extends c {
    public d(SocketChannel socketChannel, b bVar, String str, int i) {
        super(socketChannel, bVar, str, i, "http://" + str + (i == 80 ? Constants.MAIN_VERSION_TAG : ":" + i) + "/");
    }

    @Override // com.tencent.android.tpush.service.channel.a.c, com.tencent.android.tpush.service.channel.a.a
    protected boolean a() {
        return super.a();
    }

    @Override // com.tencent.android.tpush.service.channel.a.c, com.tencent.android.tpush.service.channel.a.a
    protected boolean b() {
        if (this.f == null && super.b()) {
            ((com.tencent.android.tpush.service.channel.b.b) this.f).a("X-Online-Host", this.m);
        }
        return this.f != null;
    }
}
