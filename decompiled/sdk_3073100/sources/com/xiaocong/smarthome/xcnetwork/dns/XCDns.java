package com.xiaocong.smarthome.xcnetwork.dns;

import android.text.TextUtils;
import com.alibaba.sdk.android.httpdns.HttpDns;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.xiaocong.smarthome.LibApplication;
import com.xiaocong.smarthome.xcnetwork.utils.SPUtil;
import com.xiaocong.smarthome.xcnetwork.utils.XcLogger;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCDns {
    private static XCDns instance = null;
    HttpDnsService httpdns = HttpDns.getService(LibApplication.getApplication(), "192338", "4081a85878c08618044d492668935af7");

    private XCDns() {
        this.httpdns.setPreResolveHosts(new ArrayList(Arrays.asList("gw.ixiaocong.com")));
        this.httpdns.setExpiredIPEnabled(true);
        this.httpdns.setCachedIPEnabled(true);
    }

    public static XCDns getInstance() {
        if (instance == null) {
            instance = new XCDns();
        }
        return instance;
    }

    public HttpDnsService getHttpdns() {
        return this.httpdns;
    }

    public String getIp(String type, String host) {
        String ip = SPUtil.getInstance(LibApplication.getApplication()).getSP(type);
        if (TextUtils.isEmpty(ip)) {
            ip = this.httpdns.getIpByHostAsync(host);
            XcLogger.e("HttpDns", "save ip:" + ip);
            if (ip != null) {
                SPUtil.getInstance(LibApplication.getApplication()).putSP(type, ip);
            }
        }
        return ip;
    }

    public void addPreResolveHosts(String host) {
        this.httpdns.setPreResolveHosts(new ArrayList(Arrays.asList("gw.ixiaocong.com", host)));
    }
}
