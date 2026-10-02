package com.baidu.location.a;

import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class l extends i {
    private double A;
    public i.b f;
    private double z;
    private static l i = null;
    public static boolean h = false;
    final int e = 1000;
    private boolean j = true;
    private String k = null;
    private BDLocation l = null;
    private BDLocation m = null;
    private com.baidu.location.b.g n = null;
    private com.baidu.location.b.a o = null;
    private com.baidu.location.b.g p = null;
    private com.baidu.location.b.a q = null;
    private boolean r = true;
    private volatile boolean s = false;
    private boolean t = false;
    private long u = 0;
    private long v = 0;
    private Address w = null;
    private String x = null;
    private List<Poi> y = null;
    private boolean B = false;
    private long C = 0;
    private long D = 0;
    private a E = null;
    private boolean F = false;
    private boolean G = false;
    private boolean H = true;
    public final Handler g = new i.a();
    private boolean I = false;
    private boolean J = false;
    private b K = null;
    private boolean L = false;
    private int M = 0;
    private long N = 0;
    private boolean O = true;

    private class a implements Runnable {
        final /* synthetic */ l a;

        @Override // java.lang.Runnable
        public void run() {
            if (this.a.F) {
                this.a.F = false;
                if (!this.a.G) {
                }
            }
        }
    }

    private class b implements Runnable {
        private b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (l.this.L) {
                l.this.L = false;
            }
            if (l.this.t) {
                l.this.t = false;
                l.this.h(null);
            }
        }
    }

    private l() {
        this.f = null;
        this.f = new i.b();
    }

    private boolean a(com.baidu.location.b.a aVar) {
        this.b = com.baidu.location.b.b.a().f();
        if (this.b == aVar) {
            return false;
        }
        return this.b == null || aVar == null || !aVar.a(this.b);
    }

    private boolean a(com.baidu.location.b.g gVar) {
        this.a = com.baidu.location.b.h.a().o();
        if (gVar == this.a) {
            return false;
        }
        return this.a == null || gVar == null || !gVar.c(this.a);
    }

    public static synchronized l c() {
        if (i == null) {
            i = new l();
        }
        return i;
    }

    private void c(Message message) {
        boolean z = message.getData().getBoolean("isWaitingLocTag", false);
        if (z) {
            h = true;
        }
        if (z) {
        }
        int iD = com.baidu.location.a.a.a().d(message);
        switch (iD) {
            case 1:
                d(message);
                return;
            case 2:
                g(message);
                return;
            case 3:
                if (com.baidu.location.b.e.a().i()) {
                    e(message);
                    return;
                }
                return;
            default:
                throw new IllegalArgumentException(String.format("this type %d is illegal", Integer.valueOf(iD)));
        }
    }

    private void d(Message message) {
        if (com.baidu.location.b.e.a().i()) {
            e(message);
            n.a().c();
        } else {
            g(message);
            n.a().b();
        }
    }

    private void e(Message message) {
        BDLocation bDLocation = new BDLocation(com.baidu.location.b.e.a().f());
        if (com.baidu.location.d.j.g.equals("all") || com.baidu.location.d.j.h || com.baidu.location.d.j.i) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.A, this.z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            if (fArr[0] < 100.0f) {
                if (this.w != null) {
                    bDLocation.setAddr(this.w);
                }
                if (this.x != null) {
                    bDLocation.setLocationDescribe(this.x);
                }
                if (this.y != null) {
                    bDLocation.setPoiList(this.y);
                }
            } else {
                this.B = true;
                g(null);
            }
        }
        this.l = bDLocation;
        this.m = null;
        com.baidu.location.a.a.a().a(bDLocation);
    }

    private void f(Message message) {
        if (!com.baidu.location.b.h.a().f()) {
            h(message);
            return;
        }
        this.t = true;
        if (this.K == null) {
            this.K = new b();
        }
        if (this.L && this.K != null) {
            this.g.removeCallbacks(this.K);
        }
        this.g.postDelayed(this.K, 3500L);
        this.L = true;
    }

    private void g(Message message) {
        this.M = 0;
        if (!this.r) {
            f(message);
            this.D = SystemClock.uptimeMillis();
            return;
        }
        this.M = 1;
        this.D = SystemClock.uptimeMillis();
        if (com.baidu.location.b.h.a().j()) {
            f(message);
        } else {
            h(message);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h(Message message) {
        long j = 0;
        long jCurrentTimeMillis = System.currentTimeMillis() - this.u;
        if (!this.s || jCurrentTimeMillis > 12000) {
            if (System.currentTimeMillis() - this.u > 0 && System.currentTimeMillis() - this.u < 1000) {
                if (this.l != null) {
                    com.baidu.location.a.a.a().a(this.l);
                }
                k();
                return;
            }
            this.s = true;
            this.j = a(this.o);
            if (!a(this.n) && !this.j && this.l != null && !this.B) {
                if (this.m != null && System.currentTimeMillis() - this.v > 30000) {
                    this.l = this.m;
                    this.m = null;
                }
                if (n.a().d()) {
                    this.l.setDirection(n.a().e());
                }
                if (this.l.getLocType() == 62) {
                    long jCurrentTimeMillis2 = System.currentTimeMillis() - this.N;
                    if (jCurrentTimeMillis2 > 0) {
                        j = jCurrentTimeMillis2;
                    }
                }
                if (this.l.getLocType() == 61 || this.l.getLocType() == 161 || (this.l.getLocType() == 62 && j < 15000)) {
                    com.baidu.location.a.a.a().a(this.l);
                    k();
                    return;
                }
            }
            this.u = System.currentTimeMillis();
            String strA = a((String) null);
            this.J = false;
            if (strA == null) {
                this.J = true;
                this.N = System.currentTimeMillis();
                String[] strArrJ = j();
                long jCurrentTimeMillis3 = System.currentTimeMillis();
                if (jCurrentTimeMillis3 - this.C > 60000) {
                    this.C = jCurrentTimeMillis3;
                }
                String strL = com.baidu.location.b.h.a().l();
                strA = strL != null ? strL + b() + strArrJ[0] : Constants.MAIN_VERSION_TAG + b() + strArrJ[0];
                if (this.b != null && this.b.g() != null) {
                    strA = this.b.g() + strA;
                }
                String strA2 = com.baidu.location.d.b.a().a(true);
                if (strA2 != null) {
                    strA = strA + strA2;
                }
            }
            if (this.k != null) {
                strA = strA + this.k;
                this.k = null;
            }
            this.f.a(strA);
            this.o = this.b;
            this.n = this.a;
            if (this.r) {
                this.r = false;
                if (!com.baidu.location.b.h.i() || message == null || com.baidu.location.a.a.a().e(message) < 1000) {
                }
            }
            if (this.M > 0) {
                if (this.M == 2) {
                    com.baidu.location.b.h.a().f();
                }
                this.M = 0;
            }
        }
    }

    private String[] j() {
        boolean z;
        String[] strArr = {Constants.MAIN_VERSION_TAG, "Location failed beacuse we can not get any loc information!"};
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("&apl=");
        int iA = com.baidu.location.d.j.a(com.baidu.location.f.getServiceContext());
        if (iA == 1) {
            strArr[1] = "Location failed beacuse we can not get any loc information in airplane mode, you can turn it off and try again!!";
        }
        stringBuffer.append(iA);
        String strC = com.baidu.location.d.j.c(com.baidu.location.f.getServiceContext());
        if (strC.contains("0|0|")) {
            strArr[1] = "Location failed beacuse we can not get any loc information without any location permission!";
        }
        stringBuffer.append(strC);
        if (Build.VERSION.SDK_INT >= 23) {
            stringBuffer.append("&loc=");
            int iB = com.baidu.location.d.j.b(com.baidu.location.f.getServiceContext());
            if (iB == 0) {
                strArr[1] = "Location failed beacuse we can not get any loc information with the phone loc mode is off, you can turn it on and try again!";
                z = true;
            } else {
                z = false;
            }
            stringBuffer.append(iB);
        } else {
            z = false;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            stringBuffer.append("&lmd=");
            int iB2 = com.baidu.location.d.j.b(com.baidu.location.f.getServiceContext());
            if (iB2 >= 0) {
                stringBuffer.append(iB2);
            }
        }
        String strG = com.baidu.location.b.b.a().g();
        String strG2 = com.baidu.location.b.h.a().g();
        stringBuffer.append(strG2);
        stringBuffer.append(strG);
        stringBuffer.append(com.baidu.location.d.j.d(com.baidu.location.f.getServiceContext()));
        if (iA == 1) {
            com.baidu.location.a.b.a().a(62, 7, "Location failed beacuse we can not get any loc information in airplane mode, you can turn it off and try again!!");
        } else if (strC.contains("0|0|")) {
            com.baidu.location.a.b.a().a(62, 4, "Location failed beacuse we can not get any loc information without any location permission!");
        } else if (z) {
            com.baidu.location.a.b.a().a(62, 5, "Location failed beacuse we can not get any loc information with the phone loc mode is off, you can turn it on and try again!");
        } else if (strG == null || strG2 == null || !strG.equals("&sim=1") || strG2.equals("&wifio=1")) {
            com.baidu.location.a.b.a().a(62, 9, "Location failed beacuse we can not get any loc information!");
        } else {
            com.baidu.location.a.b.a().a(62, 6, "Location failed beacuse we can not get any loc information , you can insert a sim card or open wifi and try again!");
        }
        strArr[0] = stringBuffer.toString();
        return strArr;
    }

    private void k() {
        this.s = false;
        this.G = false;
        this.H = false;
        this.B = false;
        l();
        if (this.O) {
            this.O = false;
        }
    }

    private void l() {
        if (this.l != null) {
            v.a().c();
        }
    }

    public Address a(BDLocation bDLocation) {
        if (com.baidu.location.d.j.g.equals("all") || com.baidu.location.d.j.h || com.baidu.location.d.j.i) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.A, this.z, bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
            if (fArr[0] >= 100.0f) {
                this.x = null;
                this.y = null;
                this.B = true;
                g(null);
            } else if (this.w != null) {
                return this.w;
            }
        }
        return null;
    }

    @Override // com.baidu.location.a.i
    public void a() {
        if (this.E != null && this.F) {
            this.F = false;
            this.g.removeCallbacks(this.E);
        }
        if (!com.baidu.location.b.e.a().i()) {
            if (this.G) {
                k();
                return;
            }
            if (this.j || this.l == null) {
                BDLocation bDLocation = new BDLocation();
                bDLocation.setLocType(63);
                this.l = null;
                com.baidu.location.a.a.a().a(bDLocation);
            } else {
                com.baidu.location.a.a.a().a(this.l);
            }
            this.m = null;
            k();
            return;
        }
        BDLocation bDLocation2 = new BDLocation(com.baidu.location.b.e.a().f());
        if (com.baidu.location.d.j.g.equals("all") || com.baidu.location.d.j.h || com.baidu.location.d.j.i) {
            float[] fArr = new float[2];
            Location.distanceBetween(this.A, this.z, bDLocation2.getLatitude(), bDLocation2.getLongitude(), fArr);
            if (fArr[0] < 100.0f) {
                if (this.w != null) {
                    bDLocation2.setAddr(this.w);
                }
                if (this.x != null) {
                    bDLocation2.setLocationDescribe(this.x);
                }
                if (this.y != null) {
                    bDLocation2.setPoiList(this.y);
                }
            }
        }
        com.baidu.location.a.a.a().a(bDLocation2);
        k();
    }

    @Override // com.baidu.location.a.i
    public void a(Message message) {
        if (this.E != null && this.F) {
            this.F = false;
            this.g.removeCallbacks(this.E);
        }
        BDLocation bDLocation = (BDLocation) message.obj;
        if (bDLocation != null && bDLocation.getLocType() == 167 && this.J) {
            bDLocation.setLocType(62);
        }
        b(bDLocation);
    }

    public void b(Message message) {
        if (this.I) {
            c(message);
        }
    }

    public void b(BDLocation bDLocation) {
        String strG;
        int iB;
        boolean z = true;
        new BDLocation(bDLocation);
        if (bDLocation.hasAddr()) {
            this.w = bDLocation.getAddress();
            this.z = bDLocation.getLongitude();
            this.A = bDLocation.getLatitude();
        }
        if (bDLocation.getLocationDescribe() != null) {
            this.x = bDLocation.getLocationDescribe();
            this.z = bDLocation.getLongitude();
            this.A = bDLocation.getLatitude();
        }
        if (bDLocation.getPoiList() != null) {
            this.y = bDLocation.getPoiList();
            this.z = bDLocation.getLongitude();
            this.A = bDLocation.getLatitude();
        }
        if (com.baidu.location.b.e.a().i()) {
            BDLocation bDLocation2 = new BDLocation(com.baidu.location.b.e.a().f());
            if (com.baidu.location.d.j.g.equals("all") || com.baidu.location.d.j.h || com.baidu.location.d.j.i) {
                float[] fArr = new float[2];
                Location.distanceBetween(this.A, this.z, bDLocation2.getLatitude(), bDLocation2.getLongitude(), fArr);
                if (fArr[0] < 100.0f) {
                    if (this.w != null) {
                        bDLocation2.setAddr(this.w);
                    }
                    if (this.x != null) {
                        bDLocation2.setLocationDescribe(this.x);
                    }
                    if (this.y != null) {
                        bDLocation2.setPoiList(this.y);
                    }
                }
            }
            com.baidu.location.a.a.a().a(bDLocation2);
            k();
            return;
        }
        if (this.G) {
            float[] fArr2 = new float[2];
            if (this.l != null) {
                Location.distanceBetween(this.l.getLatitude(), this.l.getLongitude(), bDLocation.getLatitude(), bDLocation.getLongitude(), fArr2);
            }
            if (fArr2[0] > 10.0f) {
                this.l = bDLocation;
                if (!this.H) {
                    this.H = false;
                    com.baidu.location.a.a.a().a(bDLocation);
                }
            } else if (bDLocation.getUserIndoorState() > -1) {
                this.l = bDLocation;
                com.baidu.location.a.a.a().a(bDLocation);
            }
            k();
            return;
        }
        if (bDLocation.getLocType() == 167) {
            com.baidu.location.a.b.a().a(167, 8, "NetWork location failed because baidu location service can not caculate the location!");
        } else if (bDLocation.getLocType() == 161) {
            if (Build.VERSION.SDK_INT >= 19 && ((iB = com.baidu.location.d.j.b(com.baidu.location.f.getServiceContext())) == 0 || iB == 2)) {
                com.baidu.location.a.b.a().a(161, 1, "NetWork location successful, open gps will be better!");
            } else if (bDLocation.getRadius() >= 100.0f && bDLocation.getNetworkLocationType() != null && bDLocation.getNetworkLocationType().equals("cl") && (strG = com.baidu.location.b.h.a().g()) != null && !strG.equals("&wifio=1")) {
                com.baidu.location.a.b.a().a(161, 2, "NetWork location successful, open wifi will be better!");
            }
        }
        this.m = null;
        if (bDLocation.getLocType() == 161 && "cl".equals(bDLocation.getNetworkLocationType()) && this.l != null && this.l.getLocType() == 161 && "wf".equals(this.l.getNetworkLocationType()) && System.currentTimeMillis() - this.v < 30000) {
            this.m = bDLocation;
        } else {
            z = false;
        }
        if (z) {
            com.baidu.location.a.a.a().a(this.l);
        } else {
            com.baidu.location.a.a.a().a(bDLocation);
            this.v = System.currentTimeMillis();
        }
        if (!com.baidu.location.d.j.a(bDLocation)) {
            this.l = null;
        } else if (!z) {
            this.l = bDLocation;
        }
        int iA = com.baidu.location.d.j.a(c, "ssid\":\"", "\"");
        if (iA == Integer.MIN_VALUE || this.n == null) {
            this.k = null;
        } else {
            this.k = this.n.c(iA);
        }
        if (com.baidu.location.b.h.i()) {
        }
        k();
    }

    public void c(BDLocation bDLocation) {
        this.l = new BDLocation(bDLocation);
    }

    public void d() {
        this.r = true;
        this.s = false;
        this.I = true;
    }

    public void e() {
        this.s = false;
        this.t = false;
        this.G = false;
        this.H = true;
        i();
        this.I = false;
    }

    public String f() {
        return this.x;
    }

    public List<Poi> g() {
        return this.y;
    }

    public void h() {
        if (this.t) {
            h(null);
            this.t = false;
        }
    }

    public void i() {
        this.l = null;
    }
}
