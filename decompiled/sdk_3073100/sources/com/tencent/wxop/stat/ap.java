package com.tencent.wxop.stat;

import android.content.Context;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class ap implements Runnable {
    private Context a;
    private Map<String, Integer> b;
    private StatSpecifyReportedInfo c;

    public ap(Context context, Map<String, Integer> map, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.a = context;
        this.c = statSpecifyReportedInfo;
        if (map != null) {
            this.b = map;
        }
    }

    private NetworkMonitor a(String str, int i) {
        NetworkMonitor networkMonitor = new NetworkMonitor();
        Socket socket = new Socket();
        int i2 = 0;
        try {
            try {
                networkMonitor.setDomain(str);
                networkMonitor.setPort(i);
                long jCurrentTimeMillis = System.currentTimeMillis();
                InetSocketAddress inetSocketAddress = new InetSocketAddress(str, i);
                socket.connect(inetSocketAddress, 30000);
                networkMonitor.setMillisecondsConsume(System.currentTimeMillis() - jCurrentTimeMillis);
                networkMonitor.setRemoteIp(inetSocketAddress.getAddress().getHostAddress());
                socket.close();
                try {
                    socket.close();
                } catch (Throwable th) {
                    StatServiceImpl.q.e(th);
                }
            } catch (IOException e) {
                i2 = -1;
                StatServiceImpl.q.e((Throwable) e);
                try {
                    socket.close();
                } catch (Throwable th2) {
                    StatServiceImpl.q.e(th2);
                }
            }
            networkMonitor.setStatusCode(i2);
            return networkMonitor;
        } catch (Throwable th3) {
            try {
                socket.close();
            } catch (Throwable th4) {
                StatServiceImpl.q.e(th4);
            }
            throw th3;
        }
    }

    private Map<String, Integer> a() {
        String str;
        HashMap map = new HashMap();
        String strA = StatConfig.a("__MTA_TEST_SPEED__", (String) null);
        if (strA != null && strA.trim().length() != 0) {
            for (String str2 : strA.split(";")) {
                String[] strArrSplit = str2.split(",");
                if (strArrSplit != null && strArrSplit.length == 2 && (str = strArrSplit[0]) != null && str.trim().length() != 0) {
                    try {
                        map.put(str, Integer.valueOf(Integer.valueOf(strArrSplit[1]).intValue()));
                    } catch (NumberFormatException e) {
                        StatServiceImpl.q.e((Throwable) e);
                    }
                }
            }
        }
        return map;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.b == null) {
                this.b = a();
            }
            if (this.b == null || this.b.size() == 0) {
                StatServiceImpl.q.i("empty domain list.");
                return;
            }
            JSONArray jSONArray = new JSONArray();
            for (Map.Entry<String, Integer> entry : this.b.entrySet()) {
                String key = entry.getKey();
                if (key == null || key.length() == 0) {
                    StatServiceImpl.q.w("empty domain name.");
                } else if (entry.getValue() == null) {
                    StatServiceImpl.q.w("port is null for " + key);
                } else {
                    jSONArray.put(a(entry.getKey(), entry.getValue().intValue()).toJSONObject());
                }
            }
            if (jSONArray.length() != 0) {
                com.tencent.wxop.stat.event.i iVar = new com.tencent.wxop.stat.event.i(this.a, StatServiceImpl.a(this.a, false, this.c), this.c);
                iVar.a(jSONArray.toString());
                new aq(iVar).a();
            }
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
        }
    }
}
