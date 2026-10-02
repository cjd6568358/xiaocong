package com.baidu.location.b;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private WifiManager c = null;
    private a d = null;
    private g e = null;
    private long f = 0;
    private long g = 0;
    private boolean h = false;
    private Handler i = new Handler();
    private long j = 0;
    private long k = 0;
    private static h b = null;
    public static long a = 0;

    /* JADX INFO: Access modifiers changed from: private */
    class a extends BroadcastReceiver {
        private long b;
        private boolean c;

        private a() {
            this.b = 0L;
            this.c = false;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (context == null) {
                return;
            }
            String action = intent.getAction();
            if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                h.a = System.currentTimeMillis() / 1000;
                h.this.i.post(new i(this));
            } else if (action.equals("android.net.wifi.STATE_CHANGE") && ((NetworkInfo) intent.getParcelableExtra("networkInfo")).getState().equals(NetworkInfo.State.CONNECTED) && System.currentTimeMillis() - this.b >= 5000) {
                this.b = System.currentTimeMillis();
                if (this.c) {
                    return;
                }
                this.c = true;
            }
        }
    }

    private h() {
    }

    public static synchronized h a() {
        if (b == null) {
            b = new h();
        }
        return b;
    }

    private String a(long j) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf((int) (j & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 8) & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 16) & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 24) & 255)));
        return stringBuffer.toString();
    }

    public static boolean a(g gVar, g gVar2) {
        boolean zA = a(gVar, gVar2, 0.7f);
        long jCurrentTimeMillis = System.currentTimeMillis() - com.baidu.location.a.a.c;
        if (jCurrentTimeMillis <= 0 || jCurrentTimeMillis >= 30000 || !zA || gVar2.f() - gVar.f() <= 30) {
            return zA;
        }
        return false;
    }

    public static boolean a(g gVar, g gVar2, float f) {
        int i;
        if (gVar == null || gVar2 == null) {
            return false;
        }
        List<ScanResult> list = gVar.a;
        List<ScanResult> list2 = gVar2.a;
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null) {
            return false;
        }
        int size = list.size();
        int size2 = list2.size();
        if (size == 0 && size2 == 0) {
            return true;
        }
        if (size == 0 || size2 == 0) {
            return false;
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < size) {
            String str = list.get(i2).BSSID;
            if (str != null) {
                int i4 = 0;
                while (true) {
                    if (i4 >= size2) {
                        i = i3;
                        break;
                    }
                    if (str.equals(list2.get(i4).BSSID)) {
                        i = i3 + 1;
                        break;
                    }
                    i4++;
                }
            } else {
                i = i3;
            }
            i2++;
            i3 = i;
        }
        return ((float) i3) >= ((float) size) * f;
    }

    public static boolean i() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) com.baidu.location.f.getServiceContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void r() {
        if (this.c == null) {
            return;
        }
        try {
            List<ScanResult> scanResults = this.c.getScanResults();
            if (scanResults != null) {
                g gVar = new g(scanResults, System.currentTimeMillis());
                if (this.e == null || !gVar.a(this.e)) {
                    this.e = gVar;
                }
            }
        } catch (Exception e) {
        }
    }

    public void b() {
        this.j = 0L;
    }

    public synchronized void c() {
        if (!this.h && com.baidu.location.f.isServing) {
            this.c = (WifiManager) com.baidu.location.f.getServiceContext().getApplicationContext().getSystemService("wifi");
            this.d = new a();
            try {
                com.baidu.location.f.getServiceContext().registerReceiver(this.d, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
            } catch (Exception e) {
            }
            this.h = true;
        }
    }

    public synchronized void d() {
        if (this.h) {
            try {
                com.baidu.location.f.getServiceContext().unregisterReceiver(this.d);
                a = 0L;
            } catch (Exception e) {
            }
            this.d = null;
            this.c = null;
            this.h = false;
        }
    }

    public boolean e() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.g > 0 && jCurrentTimeMillis - this.g <= 5000) {
            return false;
        }
        this.g = jCurrentTimeMillis;
        b();
        return f();
    }

    public boolean f() {
        if (this.c == null) {
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.f > 0) {
            if (jCurrentTimeMillis - this.f <= this.j + 5000 || jCurrentTimeMillis - (a * 1000) <= this.j + 5000) {
                return false;
            }
            if (i() && jCurrentTimeMillis - this.f <= 10000 + this.j) {
                return false;
            }
        }
        return h();
    }

    @SuppressLint({"NewApi"})
    public String g() {
        if (this.c == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            return (this.c.isWifiEnabled() || (Build.VERSION.SDK_INT > 17 && this.c.isScanAlwaysAvailable())) ? "&wifio=1" : Constants.MAIN_VERSION_TAG;
        } catch (Exception e) {
            return Constants.MAIN_VERSION_TAG;
        } catch (NoSuchMethodError e2) {
            return Constants.MAIN_VERSION_TAG;
        }
    }

    @SuppressLint({"NewApi"})
    public boolean h() {
        long jCurrentTimeMillis = System.currentTimeMillis() - this.k;
        if (jCurrentTimeMillis >= 0 && jCurrentTimeMillis <= 2000) {
            return false;
        }
        this.k = System.currentTimeMillis();
        try {
            if (!this.c.isWifiEnabled() && (Build.VERSION.SDK_INT <= 17 || !this.c.isScanAlwaysAvailable())) {
                return false;
            }
            this.c.startScan();
            this.f = System.currentTimeMillis();
            return true;
        } catch (Exception e) {
            return false;
        } catch (NoSuchMethodError e2) {
            return false;
        }
    }

    @SuppressLint({"NewApi"})
    public boolean j() {
        g gVar;
        try {
            return (this.c.isWifiEnabled() || (Build.VERSION.SDK_INT > 17 && this.c.isScanAlwaysAvailable())) && !i() && (gVar = new g(this.c.getScanResults(), 0L)) != null && gVar.e();
        } catch (Exception e) {
            return false;
        } catch (NoSuchMethodError e2) {
            return false;
        }
    }

    public WifiInfo k() {
        if (this.c == null) {
            return null;
        }
        try {
            WifiInfo connectionInfo = this.c.getConnectionInfo();
            if (connectionInfo == null || connectionInfo.getBSSID() == null || connectionInfo.getRssi() <= -100) {
                return null;
            }
            String bssid = connectionInfo.getBSSID();
            if (bssid != null) {
                String strReplace = bssid.replace(":", Constants.MAIN_VERSION_TAG);
                if ("000000000000".equals(strReplace) || Constants.MAIN_VERSION_TAG.equals(strReplace)) {
                    return null;
                }
            }
            return connectionInfo;
        } catch (Error e) {
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    public String l() {
        StringBuffer stringBuffer = new StringBuffer();
        WifiInfo wifiInfoK = a().k();
        if (wifiInfoK == null || wifiInfoK.getBSSID() == null) {
            return null;
        }
        String strReplace = wifiInfoK.getBSSID().replace(":", Constants.MAIN_VERSION_TAG);
        int rssi = wifiInfoK.getRssi();
        String strM = a().m();
        if (rssi < 0) {
            rssi = -rssi;
        }
        if (strReplace == null || rssi >= 100) {
            return null;
        }
        stringBuffer.append("&wf=");
        stringBuffer.append(strReplace);
        stringBuffer.append(";");
        stringBuffer.append(Constants.MAIN_VERSION_TAG + rssi + ";");
        String ssid = wifiInfoK.getSSID();
        if (ssid != null && (ssid.contains("&") || ssid.contains(";"))) {
            ssid = ssid.replace("&", "_");
        }
        stringBuffer.append(ssid);
        stringBuffer.append("&wf_n=1");
        if (strM != null) {
            stringBuffer.append("&wf_gw=");
            stringBuffer.append(strM);
        }
        return stringBuffer.toString();
    }

    public String m() {
        DhcpInfo dhcpInfo;
        if (this.c == null || (dhcpInfo = this.c.getDhcpInfo()) == null) {
            return null;
        }
        return a(dhcpInfo.gateway);
    }

    public g n() {
        return (this.e == null || !this.e.i()) ? p() : this.e;
    }

    public g o() {
        return (this.e == null || !this.e.j()) ? p() : this.e;
    }

    public g p() {
        if (this.c != null) {
            try {
                return new g(this.c.getScanResults(), this.f);
            } catch (Exception e) {
            }
        }
        return new g(null, 0L);
    }

    public String q() {
        try {
            WifiInfo connectionInfo = this.c.getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getMacAddress();
            }
            return null;
        } catch (Error e) {
            return null;
        } catch (Exception e2) {
            return null;
        }
    }
}
