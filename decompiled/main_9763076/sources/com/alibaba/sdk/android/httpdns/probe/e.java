package com.alibaba.sdk.android.httpdns.probe;

import com.alibaba.sdk.android.httpdns.h;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e implements IPProbeService {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private AtomicLong f75a = new AtomicLong(0);

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private ConcurrentHashMap<String, Long> f76b = new ConcurrentHashMap<>();
    private b a = null;
    private f b = new f() { // from class: com.alibaba.sdk.android.httpdns.probe.e.1
        @Override // com.alibaba.sdk.android.httpdns.probe.f
        public void a(long j, c cVar) {
            if (cVar != null) {
                if (!e.this.f76b.containsKey(cVar.getHostName()) || ((Long) e.this.f76b.get(cVar.getHostName())).longValue() != j) {
                    h.d("corresponding tasknumber not exists, drop the result");
                    return;
                }
                if (cVar == null || cVar.a() == null || cVar.h() == null || cVar.i() == null || cVar.getHostName() == null) {
                    return;
                }
                h.e("defultId:" + cVar.h() + ", selectedIp:" + cVar.i() + ", promote:" + (cVar.c() - cVar.d()));
                e.this.a(cVar.getHostName(), cVar.h(), cVar.i(), cVar.c(), cVar.d(), cVar.a().length);
                e.this.a.a(cVar.getHostName(), cVar.a());
                e.this.f76b.remove(cVar.getHostName());
            }
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, String str3, long j, long j2, int i) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.a(str, str2, str3, j, j2, i);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public IPProbeService.a getProbeStatus(String str) {
        return this.f76b.containsKey(str) ? IPProbeService.a.PROBING : IPProbeService.a.NO_PROBING;
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public void launchIPProbeTask(String str, int i, String[] strArr) {
        if (!com.alibaba.sdk.android.httpdns.a.a.a().e()) {
            h.f("ip probe is forbidden");
        } else {
            if (getProbeStatus(str) != IPProbeService.a.NO_PROBING) {
                h.f("already launch the same task, drop the task");
                return;
            }
            long jAddAndGet = this.f75a.addAndGet(1L);
            this.f76b.put(str, Long.valueOf(jAddAndGet));
            com.alibaba.sdk.android.httpdns.b.a().execute(new a(jAddAndGet, str, strArr, i, this.b));
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public void setIPListUpdateCallback(b bVar) {
        this.a = bVar;
    }

    @Override // com.alibaba.sdk.android.httpdns.probe.IPProbeService
    public boolean stopIPProbeTask(String str) {
        if (!this.f76b.containsKey(str)) {
            return false;
        }
        h.d("stop ip probe task for host:" + str);
        this.f76b.remove(str);
        return true;
    }
}
