package com.alibaba.sdk.android.httpdns;

import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class q {
    private static q a = null;
    private long g = 0;
    private boolean e = true;
    private String hostName = null;

    private q() {
    }

    public static q a() {
        if (a == null) {
            synchronized (q.class) {
                if (a == null) {
                    a = new q();
                }
            }
        }
        return a;
    }

    private void a(String str, String str2) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.a(str, s.a(n.SNIFF_HOST), str2);
        }
    }

    private boolean c() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.g != 0 && jCurrentTimeMillis - this.g < 30000) {
            return false;
        }
        this.g = jCurrentTimeMillis;
        return true;
    }

    public synchronized void a(boolean z) {
        this.e = z;
    }

    public synchronized void e() {
        this.g = 0L;
    }

    public synchronized void g(String str) {
        String str2 = null;
        boolean z = false;
        synchronized (this) {
            if (str != null) {
                this.hostName = str;
            }
            if (!this.e) {
                str2 = "sniffer is turned off";
            } else if (!c()) {
                str2 = "sniff too often";
            } else if (TextUtils.isEmpty(this.hostName)) {
                str2 = "hostname is null";
            } else {
                z = true;
            }
            if (z) {
                h.d("launch a sniff task");
                l lVar = new l(this.hostName, n.SNIFF_HOST);
                lVar.a(0);
                b.a().submit(lVar);
                a(str, s.a(n.SNIFF_HOST));
                this.hostName = null;
            } else {
                h.d("launch sniffer failed due to " + str2);
            }
        }
    }
}
