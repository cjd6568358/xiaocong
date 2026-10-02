package com.alibaba.sdk.android.httpdns.probe;

import com.alibaba.sdk.android.httpdns.h;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class g implements Runnable {
    private ConcurrentHashMap<String, Long> a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private CountDownLatch f77a;
    private String k;
    private int port;

    public g(String str, int i, CountDownLatch countDownLatch, ConcurrentHashMap<String, Long> concurrentHashMap) {
        this.f77a = null;
        this.k = str;
        this.port = i;
        this.f77a = countDownLatch;
        this.a = concurrentHashMap;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private long a(String str, int i) throws Throwable {
        Socket socket;
        long j;
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            socket = new Socket();
            try {
                try {
                    socket.connect(new InetSocketAddress(str, i), 5000);
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (socket != null) {
                        try {
                            socket.close();
                            j = jCurrentTimeMillis2;
                        } catch (IOException e) {
                            h.f("socket close failed:" + e.toString());
                            j = jCurrentTimeMillis2;
                        }
                    } else {
                        j = jCurrentTimeMillis2;
                    }
                } catch (IOException e2) {
                    e = e2;
                    h.f("connect failed:" + e.toString());
                    if (socket != null) {
                        try {
                            socket.close();
                            j = 2147483647L;
                        } catch (IOException e3) {
                            h.f("socket close failed:" + e3.toString());
                            j = 2147483647L;
                        }
                    } else {
                        j = 2147483647L;
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e4) {
                        h.f("socket close failed:" + e4.toString());
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            socket = null;
        } catch (Throwable th2) {
            th = th2;
            socket = null;
            if (socket != null) {
                socket.close();
            }
            throw th;
        }
        if (j == 2147483647L) {
            return 2147483647L;
        }
        return j - jCurrentTimeMillis;
    }

    private boolean a(int i) {
        return i >= 1 && i <= 65535;
    }

    @Override // java.lang.Runnable
    public void run() throws Throwable {
        if (this.k == null || !a(this.port)) {
            h.f("invalid params, give up");
        } else {
            long jA = a(this.k, this.port);
            h.d("connect cost for ip:" + this.k + " is " + jA);
            if (this.a != null) {
                this.a.put(this.k, Long.valueOf(jA));
            }
        }
        if (this.f77a != null) {
            this.f77a.countDown();
        }
    }
}
