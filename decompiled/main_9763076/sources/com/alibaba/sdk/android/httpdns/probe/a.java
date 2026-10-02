package com.alibaba.sdk.android.httpdns.probe;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a implements Runnable {
    private f a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private ConcurrentHashMap<String, Long> f73a = new ConcurrentHashMap<>();

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private String[] f74a;
    private String h;
    private long i;
    private int port;

    public a(long j, String str, String[] strArr, int i, f fVar) {
        this.a = null;
        this.i = j;
        this.h = str;
        this.f74a = strArr;
        this.port = i;
        this.a = fVar;
    }

    private c a(String[] strArr) {
        if (this.f74a == null || this.f74a.length == 0 || strArr == null || strArr.length == 0) {
            return null;
        }
        String str = this.f74a[0];
        String str2 = strArr[0];
        return new c(this.h, strArr, str, str2, this.f73a.containsKey(str) ? this.f73a.get(str).longValue() : 2147483647L, this.f73a.containsKey(str2) ? this.f73a.get(str2).longValue() : 2147483647L);
    }

    private String[] a(ConcurrentHashMap<String, Long> concurrentHashMap) {
        if (concurrentHashMap == null) {
            return null;
        }
        String[] strArr = new String[concurrentHashMap.size()];
        Iterator<String> it = concurrentHashMap.keySet().iterator();
        int i = 0;
        while (it.hasNext()) {
            strArr[i] = new String(it.next());
            i++;
        }
        for (int i2 = 0; i2 < strArr.length - 1; i2++) {
            for (int i3 = 0; i3 < (strArr.length - i2) - 1; i3++) {
                if (concurrentHashMap.get(strArr[i3]).longValue() > concurrentHashMap.get(strArr[i3 + 1]).longValue()) {
                    String str = strArr[i3];
                    strArr[i3] = strArr[i3 + 1];
                    strArr[i3 + 1] = str;
                }
            }
        }
        return strArr;
    }

    @Override // java.lang.Runnable
    public void run() {
        String[] strArrA;
        if (this.f74a == null || this.f74a.length == 0) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(this.f74a.length);
        for (int i = 0; i < this.f74a.length; i++) {
            com.alibaba.sdk.android.httpdns.b.a().execute(new g(this.f74a[i], this.port, countDownLatch, this.f73a));
        }
        try {
            countDownLatch.await(10000L, TimeUnit.MILLISECONDS);
            if (this.a == null || (strArrA = a(this.f73a)) == null || strArrA.length == 0) {
                return;
            }
            this.a.a(this.i, a(strArrA));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
