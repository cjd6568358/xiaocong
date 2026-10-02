package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import com.alibaba.sdk.android.utils.AMSConfigUtils;
import com.alibaba.sdk.android.utils.AMSDevReporter;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HttpDns implements HttpDnsService {
    private boolean isExpiredIPEnabled = false;
    private static c hostManager = c.a();
    private static DegradationFilter degradationFilter = null;
    static HttpDns instance = null;
    private static boolean isEnabled = true;
    private static boolean inited = false;
    private static String sAccountId = null;
    private static String sSecretKey = null;
    private static Context sContext = null;

    private HttpDns() {
    }

    private static String getAccountId() {
        if (!TextUtils.isEmpty(sAccountId)) {
            return sAccountId;
        }
        sAccountId = AMSConfigUtils.getAccountId(sContext);
        return sAccountId;
    }

    private String getIpByHost(String str) {
        if (!isEnabled) {
            h.f("HttpDns service turned off");
            return null;
        }
        String[] ipsByHost = getIpsByHost(str);
        if (ipsByHost == null || ipsByHost.length <= 0) {
            return null;
        }
        return ipsByHost[0];
    }

    private String[] getIpsByHost(String str) {
        if (!isEnabled) {
            h.f("HttpDns service turned off");
            return e.d;
        }
        if (!j.b(str)) {
            return e.d;
        }
        if (j.c(str)) {
            return new String[]{str};
        }
        if (degradationFilter != null && degradationFilter.shouldDegradeHttpDNS(str)) {
            return e.d;
        }
        if (s.d()) {
            return getIpsByHostAsync(str);
        }
        d dVarM38a = hostManager.m38a(str);
        if (dVarM38a != null && dVarM38a.m47b() && this.isExpiredIPEnabled) {
            if (!hostManager.m43a(str)) {
                h.d("refresh host async: " + str);
                b.a().submit(new l(str, n.QUERY_HOST));
            }
            return dVarM38a.m46a();
        }
        if (dVarM38a != null && !dVarM38a.m47b()) {
            return dVarM38a.m46a();
        }
        h.d("refresh host sync: " + str);
        try {
            return (String[]) b.a().submit(new l(str, n.QUERY_HOST)).get();
        } catch (Exception e) {
            h.a(e);
            return e.d;
        }
    }

    private static String getSecretKey() {
        if (!TextUtils.isEmpty(sSecretKey)) {
            return sSecretKey;
        }
        sSecretKey = AMSConfigUtils.getHttpdnsSecretKey(sContext);
        return sSecretKey;
    }

    public static synchronized HttpDnsService getService(Context context) {
        if (instance == null && context != null) {
            sContext = context.getApplicationContext();
            com.alibaba.sdk.android.httpdns.c.a.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.3
                @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                public void crashDefendMessage(int i, int i2) {
                    boolean unused = HttpDns.inited = true;
                    if (i > i2) {
                        boolean unused2 = HttpDns.isEnabled = true;
                    } else {
                        h.f("crash limit exceeds, httpdns disabled");
                        boolean unused3 = HttpDns.isEnabled = false;
                    }
                }
            });
            if (!inited) {
                h.f("sdk crash defend not returned");
            }
            if (isEnabled) {
                initHttpDns(sContext, getAccountId(), getSecretKey());
            } else {
                instance = new HttpDns();
            }
        }
        return instance;
    }

    public static synchronized HttpDnsService getService(Context context, String str) {
        if (instance == null && context != null) {
            sContext = context.getApplicationContext();
            setAccountId(str);
            com.alibaba.sdk.android.httpdns.c.a.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.1
                @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                public void crashDefendMessage(int i, int i2) {
                    boolean unused = HttpDns.inited = true;
                    if (i > i2) {
                        boolean unused2 = HttpDns.isEnabled = true;
                    } else {
                        h.f("crash limit exceeds, httpdns disabled");
                        boolean unused3 = HttpDns.isEnabled = false;
                    }
                }
            });
            if (!inited) {
                h.f("sdk crash defend not returned");
            }
            if (isEnabled) {
                initHttpDns(sContext, getAccountId(), getSecretKey());
            } else {
                instance = new HttpDns();
            }
        }
        return instance;
    }

    public static synchronized HttpDnsService getService(Context context, String str, String str2) {
        if (instance == null && context != null) {
            sContext = context.getApplicationContext();
            setAccountId(str);
            setSecretKey(str2);
            com.alibaba.sdk.android.httpdns.c.a.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.2
                @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                public void crashDefendMessage(int i, int i2) {
                    boolean unused = HttpDns.inited = true;
                    if (i > i2) {
                        boolean unused2 = HttpDns.isEnabled = true;
                    } else {
                        h.f("crash limit exceeds, httpdns disabled");
                        boolean unused3 = HttpDns.isEnabled = false;
                    }
                }
            });
            if (!inited) {
                h.f("sdk crash defend not returned");
            }
            if (isEnabled) {
                initHttpDns(sContext, getAccountId(), getSecretKey());
            } else {
                instance = new HttpDns();
            }
        }
        return instance;
    }

    private static void initHttpDns(Context context, String str, String str2) {
        if (instance == null) {
            HashMap map = new HashMap();
            map.put(AMSDevReporter.AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_VERSION.toString(), "1.1.9");
            AMSDevReporter.asyncReport(context, AMSDevReporter.AMSSdkTypeEnum.AMS_HTTPDNS, map);
            k.setContext(context);
            l.setContext(context);
            com.alibaba.sdk.android.httpdns.b.b.init(context);
            com.alibaba.sdk.android.httpdns.b.b.a(context);
            s.init(context);
            e.c(str);
            o.a().init(context);
            if (!TextUtils.isEmpty(str2)) {
                a.setSecretKey(str2);
            }
            reportActive(context, str);
            com.alibaba.sdk.android.httpdns.a.a.a().a(context, str);
            com.alibaba.sdk.android.httpdns.a.a.a().a(com.alibaba.sdk.android.httpdns.c.a.a(context));
            instance = new HttpDns();
        }
    }

    private static void reportActive(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            h.f("report active failed due to missing context or accountid");
        } else {
            com.alibaba.sdk.android.httpdns.c.a.a(context).setAccountId(str);
            com.alibaba.sdk.android.httpdns.c.a.a(context).i();
        }
    }

    private static void reportHttpDnsSuccess(String str, int i) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.a(str, i, com.alibaba.sdk.android.httpdns.c.b.b(), com.alibaba.sdk.android.httpdns.b.b.m31a() ? 1 : 0);
        }
    }

    private static void reportUserGetIP(String str, int i) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.b(str, i, com.alibaba.sdk.android.httpdns.c.b.b(), com.alibaba.sdk.android.httpdns.b.b.m31a() ? 1 : 0);
        }
    }

    private static void setAccountId(String str) {
        sAccountId = str;
    }

    private static void setSecretKey(String str) {
        sSecretKey = str;
    }

    static synchronized void switchDnsService(boolean z) {
        isEnabled = z;
        if (!isEnabled) {
            h.f("httpdns service disabled");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getIpByHostAsync(String str) {
        if (!isEnabled) {
            h.f("HttpDns service turned off");
            return null;
        }
        String[] ipsByHostAsync = getIpsByHostAsync(str);
        if (ipsByHostAsync == null || ipsByHostAsync.length <= 0) {
            return null;
        }
        return ipsByHostAsync[0];
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0056  */
    /* JADX WARN: Code duplicated, block: B:29:0x0081  */
    /* JADX WARN: Code duplicated, block: B:31:0x0087  */
    /* JADX WARN: Code duplicated, block: B:32:0x008d  */
    /* JADX WARN: Code duplicated, block: B:34:0x0091  */
    /* JADX WARN: Code duplicated, block: B:35:0x009d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:36:0x009f  */
    /* JADX WARN: Code duplicated, block: B:37:0x00ab  */
    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String[] getIpsByHostAsync(String str) {
        boolean zM47b;
        if (!isEnabled) {
            h.f("HttpDns service turned off");
            return e.d;
        }
        if (!j.b(str)) {
            return e.d;
        }
        if (j.c(str)) {
            return new String[]{str};
        }
        if (degradationFilter != null && degradationFilter.shouldDegradeHttpDNS(str)) {
            return e.d;
        }
        d dVarM38a = hostManager.m38a(str);
        if (dVarM38a != null) {
            zM47b = dVarM38a.m47b();
            if (zM47b) {
            }
            if (dVarM38a == null) {
                reportUserGetIP(str, 0);
                return e.d;
            }
            if (s.d()) {
                reportUserGetIP(str, 0);
                return e.d;
            }
            if (this.isExpiredIPEnabled) {
                reportHttpDnsSuccess(str, 1);
                reportUserGetIP(str, 1);
                return dVarM38a.m46a();
            }
            if (!zM47b) {
                reportUserGetIP(str, 0);
                return e.d;
            }
            reportHttpDnsSuccess(str, 1);
            reportUserGetIP(str, 1);
            return dVarM38a.m46a();
        }
        zM47b = false;
        if (!hostManager.m43a(str)) {
            if (s.d()) {
                q.a().g(str);
            } else {
                h.d("refresh host async: " + str);
                b.a().submit(new l(str, n.QUERY_HOST));
            }
        }
        if (dVarM38a == null) {
            reportUserGetIP(str, 0);
            return e.d;
        }
        if (s.d()) {
            reportUserGetIP(str, 0);
            return e.d;
        }
        if (this.isExpiredIPEnabled) {
            reportHttpDnsSuccess(str, 1);
            reportUserGetIP(str, 1);
            return dVarM38a.m46a();
        }
        if (!zM47b) {
            reportUserGetIP(str, 0);
            return e.d;
        }
        reportHttpDnsSuccess(str, 1);
        reportUserGetIP(str, 1);
        return dVarM38a.m46a();
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setAuthCurrentTime(long j) {
        if (isEnabled) {
            a.setAuthCurrentTime(j);
        } else {
            h.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setCachedIPEnabled(boolean z) {
        if (!isEnabled) {
            h.f("HttpDns service turned off");
            return;
        }
        com.alibaba.sdk.android.httpdns.b.b.c(z);
        c.a().m40a();
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.c(z ? 1 : 0);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setDegradationFilter(DegradationFilter degradationFilter2) {
        if (isEnabled) {
            degradationFilter = degradationFilter2;
        } else {
            h.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setExpiredIPEnabled(boolean z) {
        if (!isEnabled) {
            h.f("HttpDns service turned off");
            return;
        }
        this.isExpiredIPEnabled = z;
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.d(z ? 1 : 0);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setHTTPSRequestEnabled(boolean z) {
        if (isEnabled) {
            e.setHTTPSRequestEnabled(z);
        } else {
            h.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setIPProbeList(List<IPProbeItem> list) {
        if (isEnabled) {
            e.a(list);
        } else {
            h.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogEnabled(boolean z) {
        h.setLogEnabled(z);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveAfterNetworkChanged(boolean z) {
        if (isEnabled) {
            k.b = z;
        } else {
            h.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveHosts(ArrayList<String> arrayList) {
        if (!isEnabled) {
            h.f("HttpDns service turned off");
            return;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= arrayList.size()) {
                return;
            }
            String str = arrayList.get(i2);
            if (j.b(str) && !hostManager.m43a(str)) {
                b.a().submit(new l(str, n.QUERY_HOST));
            }
            i = i2 + 1;
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setTimeoutInterval(int i) {
        if (isEnabled) {
            e.setTimeoutInterval(i);
        } else {
            h.f("HttpDns service turned off");
        }
    }
}
