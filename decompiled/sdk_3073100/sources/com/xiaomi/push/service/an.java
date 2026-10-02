package com.xiaomi.push.service;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import com.xiaomi.network.Fallback;
import com.xiaomi.network.HostFilter;
import com.xiaomi.network.HostManager;
import com.xiaomi.network.HostManagerV2;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class an extends at.a implements HostManager.HostManagerFactory {
    private XMPushService a;
    private long b;

    static class a implements HostManager.HttpGet {
        a() {
        }

        @Override // com.xiaomi.network.HostManager.HttpGet
        public String a(String str) throws IOException {
            Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
            builderBuildUpon.appendQueryParameter("sdkver", String.valueOf(26));
            builderBuildUpon.appendQueryParameter("osver", String.valueOf(Build.VERSION.SDK_INT));
            builderBuildUpon.appendQueryParameter("os", com.xiaomi.smack.util.d.a(Build.MODEL + ":" + Build.VERSION.INCREMENTAL));
            builderBuildUpon.appendQueryParameter("mi", String.valueOf(com.xiaomi.channel.commonutils.android.j.c()));
            String string = builderBuildUpon.toString();
            com.xiaomi.channel.commonutils.logger.b.c("fetch bucket from : " + string);
            URL url = new URL(string);
            int port = url.getPort() == -1 ? 80 : url.getPort();
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                String strA = com.xiaomi.channel.commonutils.network.d.a(com.xiaomi.channel.commonutils.android.j.a(), url);
                com.xiaomi.stats.h.a(url.getHost() + ":" + port, (int) (System.currentTimeMillis() - jCurrentTimeMillis), null);
                return strA;
            } catch (IOException e) {
                com.xiaomi.stats.h.a(url.getHost() + ":" + port, -1, e);
                throw e;
            }
        }
    }

    static class b extends HostManagerV2 {
        protected b(Context context, HostFilter hostFilter, HostManager.HttpGet httpGet, String str) {
            super(context, hostFilter, httpGet, str);
        }

        @Override // com.xiaomi.network.HostManagerV2, com.xiaomi.network.HostManager
        protected String getRemoteFallbackJSON(ArrayList<String> arrayList, String str, String str2) throws IOException {
            try {
                if (com.xiaomi.stats.f.a().c()) {
                    str2 = at.e();
                }
                return super.getRemoteFallbackJSON(arrayList, str, str2);
            } catch (IOException e) {
                com.xiaomi.stats.h.a(0, com.xiaomi.push.thrift.a.GSLB_ERR.a(), 1, null, com.xiaomi.channel.commonutils.network.d.d(this.sAppContext) ? 1 : 0);
                throw e;
            }
        }
    }

    an(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    public static void a(XMPushService xMPushService) {
        an anVar = new an(xMPushService);
        at.a().a(anVar);
        synchronized (HostManager.class) {
            HostManager.setHostManagerFactory(anVar);
            HostManager.init(xMPushService, null, new a(), "0", "push", "2.2");
        }
    }

    @Override // com.xiaomi.network.HostManager.HostManagerFactory
    public HostManager a(Context context, HostFilter hostFilter, HostManager.HttpGet httpGet, String str) {
        return new b(context, hostFilter, httpGet, str);
    }

    @Override // com.xiaomi.push.service.at.a
    public void a(com.xiaomi.push.protobuf.a.C0011a c0011a) {
    }

    @Override // com.xiaomi.push.service.at.a
    public void a(com.xiaomi.push.protobuf.b.C0012b c0012b) {
        Fallback fallbacksByHost;
        boolean z;
        if (c0012b.e() && c0012b.d() && System.currentTimeMillis() - this.b > 3600000) {
            com.xiaomi.channel.commonutils.logger.b.a("fetch bucket :" + c0012b.d());
            this.b = System.currentTimeMillis();
            HostManager hostManager = HostManager.getInstance();
            hostManager.clear();
            hostManager.refreshFallbacks();
            com.xiaomi.smack.a aVarH = this.a.h();
            if (aVarH == null || (fallbacksByHost = hostManager.getFallbacksByHost(aVarH.c().e())) == null) {
                return;
            }
            ArrayList<String> arrayListD = fallbacksByHost.d();
            Iterator<String> it = arrayListD.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = true;
                    break;
                } else if (it.next().equals(aVarH.d())) {
                    z = false;
                    break;
                }
            }
            if (!z || arrayListD.isEmpty()) {
                return;
            }
            com.xiaomi.channel.commonutils.logger.b.a("bucket changed, force reconnect");
            this.a.a(0, (Exception) null);
            this.a.a(false);
        }
    }
}
