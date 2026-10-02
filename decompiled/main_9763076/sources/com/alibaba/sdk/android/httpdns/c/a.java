package com.alibaba.sdk.android.httpdns.c;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.utils.AlicloudTracker;
import com.alibaba.sdk.android.utils.AlicloudTrackerManager;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static a b = null;
    private AlicloudTracker a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private AlicloudTrackerManager f60a;
    private boolean i = true;

    private a(Context context) {
        this.a = null;
        this.f60a = null;
        if (context == null || !(context.getApplicationContext() instanceof Application)) {
            return;
        }
        this.f60a = AlicloudTrackerManager.getInstance((Application) context.getApplicationContext());
        if (this.f60a != null) {
            this.a = this.f60a.getTracker("httpdns", "1.1.9");
        }
    }

    public static a a() {
        return b;
    }

    public static a a(Context context) {
        if (b == null) {
            synchronized (a.class) {
                if (b == null) {
                    b = new a(context);
                }
            }
        }
        return b;
    }

    public void a(String str, int i, int i2, int i3) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report http dns succes failed due to tacker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || !((i == 0 || i == 1) && ((i2 == 0 || i2 == 1) && (i3 == 0 || i3 == 1)))) {
            Log.e("HttpDns:ReportManager", "report http dns success failed due to invalid params");
            return;
        }
        HashMap map = new HashMap();
        map.put("host", str);
        map.put("success", String.valueOf(i));
        map.put("ipv6", String.valueOf(i2));
        map.put("cacheOpen", String.valueOf(i3));
        this.a.sendCustomHit("perf_getip", map);
    }

    public void a(String str, long j, int i) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report sc request time cost failed due to tacker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || j <= 0 || !(i == 0 || i == 1)) {
            Log.e("HttpDns:ReportManager", "report sc request time cost failed due to invalid params");
            return;
        }
        if (j > 30000) {
            j = 30000;
        }
        HashMap map = new HashMap();
        map.put("scAddr", str);
        map.put("cost", String.valueOf(j));
        map.put("ipv6", String.valueOf(i));
        this.a.sendCustomHit("perf_sc", map);
    }

    public void a(String str, String str2, String str3) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report sniffer failed due to tracker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            Log.e("HttpDns:ReportManager", "report sniffer failed due to missing params");
            return;
        }
        HashMap map = new HashMap();
        map.put("host", str);
        map.put("scAddr", str2);
        map.put("srvAddr", str3);
        this.a.sendCustomHit("biz_sniffer", map);
    }

    public void a(String str, String str2, String str3, int i) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report error sc failed due to tacker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || !(i == 0 || i == 1)) {
            Log.e("HttpDns:ReportManager", "report error sc failed, due to invalid params");
            return;
        }
        HashMap map = new HashMap();
        map.put("scAddr", str);
        map.put("errCode", str2);
        map.put("errMsg", str3);
        map.put("ipv6", String.valueOf(i));
        this.a.sendCustomHit("err_sc", map);
    }

    public void a(String str, String str2, String str3, long j, long j2, int i) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report ip selection failed due to tacker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || i <= 0) {
            Log.e("HttpDns:ReportManager", "report ip selection failed due to invalid params");
            return;
        }
        if (j > 5000) {
            j = 5000;
        }
        if (j2 > 5000) {
            j2 = 5000;
        }
        HashMap map = new HashMap();
        map.put("host", str);
        map.put("defaultIp", str2);
        map.put("selectedIp", str3);
        map.put("defaultIpCost", String.valueOf(j));
        map.put("selectedIpCost", String.valueOf(j2));
        map.put("ipCount", String.valueOf(i));
        this.a.sendCustomHit("perf_ipselection", map);
    }

    public boolean a(SDKMessageCallback sDKMessageCallback) {
        if (this.f60a != null) {
            return this.f60a.registerCrashDefend("httpdns", "1.1.9", 10, 5, sDKMessageCallback);
        }
        return false;
    }

    public void b(String str, int i, int i2, int i3) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report http dns succes failed due to tacker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || !((i == 0 || i == 1) && ((i2 == 0 || i2 == 1) && (i3 == 0 || i3 == 1)))) {
            Log.e("HttpDns:ReportManager", "report http dns success failed due to invalid params");
            return;
        }
        HashMap map = new HashMap();
        map.put("host", str);
        map.put("success", String.valueOf(i));
        map.put("ipv6", String.valueOf(i2));
        map.put("cacheOpen", String.valueOf(i3));
        this.a.sendCustomHit("perf_user_getip", map);
    }

    public void b(String str, long j, int i) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report http dns request time cost failed due to tacker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || j <= 0 || !(i == 0 || i == 1)) {
            Log.e("HttpDns:ReportManager", "report http dns request time cost failed due to invalid param");
            return;
        }
        if (j > 30000) {
            j = 30000;
        }
        HashMap map = new HashMap();
        map.put("srvAddr", str);
        map.put("cost", String.valueOf(j));
        map.put("ipv6", String.valueOf(i));
        this.a.sendCustomHit("perf_srv", map);
    }

    public void b(String str, String str2, String str3) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report local disable failed due to tracker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            Log.e("HttpDns:ReportManager", "report local disable failed due to missing params");
            return;
        }
        HashMap map = new HashMap();
        map.put("host", str);
        map.put("scAddr", str2);
        map.put("srvAddr", str3);
        this.a.sendCustomHit("biz_local_disable", map);
    }

    public void b(String str, String str2, String str3, int i) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report error http dns request failed due to tacker is null");
            return;
        }
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || !(i == 0 || i == 1)) {
            Log.e("HttpDns:ReportManager", "report error http dns request failed, due to invalid params");
            return;
        }
        HashMap map = new HashMap();
        map.put("srvAddr", str);
        map.put("errCode", str2);
        map.put("errMsg", str3);
        map.put("ipv6", String.valueOf(i));
        this.a.sendCustomHit("err_srv", map);
    }

    public void c(int i) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report cache failed due to tracker is null");
            return;
        }
        if (i != 0 && i != 1) {
            Log.e("HttpDns:ReportManager", "report cache failed, due to invalid param enable, enable can only be 0 or 1");
            return;
        }
        HashMap map = new HashMap();
        map.put("enable", String.valueOf(i));
        this.a.sendCustomHit("biz_cache", map);
    }

    public void d(int i) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report set expired ip enabled failed due to tracker is null");
            return;
        }
        if (i != 0 && i != 1) {
            Log.e("HttpDns:ReportManager", "report set expired ip enabled failed, due to invalid param enable, enable can only be 0 or 1");
            return;
        }
        HashMap map = new HashMap();
        map.put("enable", String.valueOf(i));
        this.a.sendCustomHit("biz_expired_ip", map);
    }

    public void d(boolean z) {
        synchronized (a.class) {
            this.i = z;
        }
    }

    public void i() {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
        } else if (this.a != null) {
            this.a.sendCustomHit("biz_active", null);
        } else {
            Log.e("HttpDns:ReportManager", "report sdk start failed due to tracker is null");
        }
    }

    public void j(String str) {
        if (!this.i) {
            Log.e("HttpDns:ReportManager", "report is disabled");
            return;
        }
        if (this.a == null) {
            Log.e("HttpDns:ReportManager", "report uncaught exception failed due to tacker is null");
        } else {
            if (TextUtils.isEmpty(str)) {
                Log.e("HttpDns:ReportManager", "report uncaught exception failed due to exception msg is null");
                return;
            }
            HashMap map = new HashMap();
            map.put("exception", str);
            this.a.sendCustomHit("err_uncaught_exception", map);
        }
    }

    public void setAccountId(String str) {
        if (this.a != null) {
            this.a.setGlobalProperty("accountId", str);
        } else {
            Log.e("HttpDns:ReportManager", "tracker null, set global properties failed");
        }
    }
}
