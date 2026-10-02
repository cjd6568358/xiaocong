package com.tencent.android.tpush.service.channel.b;

import com.tencent.android.tpush.service.channel.security.TpnsSecurity;
import com.tencent.android.tpush.service.n;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f implements c {
    private long a = Long.MAX_VALUE;
    private boolean b = false;
    protected TpnsSecurity j;

    @Override // com.tencent.android.tpush.service.channel.b.c
    public void a(TpnsSecurity tpnsSecurity) {
        this.j = tpnsSecurity;
    }

    public void c() {
        if (this.a == Long.MAX_VALUE) {
            this.a = System.currentTimeMillis();
        }
    }

    @Override // com.tencent.android.tpush.service.channel.b.c
    public long a() {
        long jCurrentTimeMillis = (this.a + ((long) com.tencent.android.tpush.service.a.a.a(n.f()).b)) - System.currentTimeMillis();
        if (jCurrentTimeMillis < 0) {
            return 0L;
        }
        return jCurrentTimeMillis;
    }

    public synchronized void d() {
        this.b = true;
    }

    @Override // com.tencent.android.tpush.service.channel.b.c
    public synchronized boolean b() {
        return this.b;
    }
}
