package com.xiaocong.smarthome.xcnetwork.dns;

import android.text.TextUtils;
import com.alibaba.sdk.android.httpdns.HttpDnsService;
import com.xiaocong.smarthome.LibApplication;
import com.xiaocong.smarthome.xcnetwork.utils.SPUtil;
import com.xiaocong.smarthome.xcnetwork.utils.XcLogger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import okhttp3.Dns;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class OkHttpDns implements Dns {
    private static final Dns SYSTEM = Dns.SYSTEM;
    private static OkHttpDns instance = null;
    HttpDnsService httpdns = XCDns.getInstance().getHttpdns();

    private OkHttpDns() {
    }

    public static OkHttpDns getInstance() {
        if (instance == null) {
            instance = new OkHttpDns();
        }
        return instance;
    }

    public String getIp(String type, String host) {
        String ip = SPUtil.getInstance(LibApplication.getApplication()).getSP(type);
        if (TextUtils.isEmpty(ip)) {
            ip = this.httpdns.getIpByHostAsync(host);
            XcLogger.e("OkHttpDns", "save ip:" + ip);
            if (ip != null) {
                SPUtil.getInstance(LibApplication.getApplication()).putSP(type, ip);
            }
        }
        return ip;
    }

    @Override // okhttp3.Dns
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        String ip = getIp("dns_ip", hostname);
        XcLogger.e("OkHttpDns", "getIp:" + ip);
        if (ip == null) {
            return Dns.SYSTEM.lookup(hostname);
        }
        List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(ip));
        XcLogger.e("OkHttpDns", "inetAddresses:" + inetAddresses);
        return inetAddresses;
    }
}
