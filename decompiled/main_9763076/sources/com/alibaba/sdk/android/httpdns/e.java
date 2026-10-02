package com.alibaba.sdk.android.httpdns;

import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static String f61a;

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    static String[] f63b = {"203.107.1.1"};
    static final String[] c = {"203.107.1.97", "203.107.1.100", "httpdns-sc.aliyuncs.com"};
    static final String[] d = new String[0];
    static String b = Constants.UNSTALL_PORT;
    static String PROTOCOL = "http://";
    static int a = 15000;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    static List<IPProbeItem> f62a = null;

    static synchronized void a(List<IPProbeItem> list) {
        f62a = list;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x000d  */
    static synchronized boolean a(String[] strArr) {
        boolean z;
        if (strArr == null) {
            z = false;
        } else if (strArr.length != 0) {
            f63b = strArr;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    static synchronized void c(String str) {
        f61a = str;
    }

    static synchronized void setHTTPSRequestEnabled(boolean z) {
        try {
            if (z) {
                PROTOCOL = "https://";
                b = "443";
            } else {
                PROTOCOL = "http://";
                b = Constants.UNSTALL_PORT;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    static synchronized void setTimeoutInterval(int i) {
        if (i > 0) {
            a = i;
        }
    }
}
