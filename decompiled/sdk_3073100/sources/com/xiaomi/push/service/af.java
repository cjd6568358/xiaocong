package com.xiaomi.push.service;

import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class af implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ boolean b;

    af(List list, boolean z) {
        this.a = list;
        this.b = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zB = ae.b("www.baidu.com:80");
        Iterator it = this.a.iterator();
        while (true) {
            boolean z = zB;
            if (!it.hasNext()) {
                zB = z;
                break;
            }
            zB = z || ae.b((String) it.next());
            if (zB && !this.b) {
                break;
            }
        }
        com.xiaomi.stats.h.a(zB ? 1 : 2);
    }
}
