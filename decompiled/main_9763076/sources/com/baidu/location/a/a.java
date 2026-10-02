package com.baidu.location.a;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private ArrayList<C0007a> f;
    private static a e = null;
    public static long c = 0;
    private boolean g = false;
    public boolean a = false;
    boolean b = false;
    private BDLocation h = null;
    private BDLocation i = null;
    private BDLocation j = null;
    int d = 0;
    private BDLocation k = null;
    private boolean l = false;
    private boolean m = false;
    private b n = null;

    /* JADX INFO: renamed from: com.baidu.location.a.a$a, reason: collision with other inner class name */
    private class C0007a {
        public String a;
        public Messenger b;
        public LocationClientOption c = new LocationClientOption();
        public int d = 0;
        final /* synthetic */ a e;

        public C0007a(a aVar, Message message) {
            boolean z = true;
            this.e = aVar;
            this.a = null;
            this.b = null;
            this.b = message.replyTo;
            this.a = message.getData().getString(Constants.FLAG_PACK_NAME);
            this.c.prodName = message.getData().getString("prodName");
            com.baidu.location.d.b.a().a(this.c.prodName, this.a);
            this.c.coorType = message.getData().getString("coorType");
            this.c.addrType = message.getData().getString("addrType");
            this.c.enableSimulateGps = message.getData().getBoolean("enableSimulateGps", false);
            com.baidu.location.d.j.l = com.baidu.location.d.j.l || this.c.enableSimulateGps;
            if (!com.baidu.location.d.j.g.equals("all")) {
                com.baidu.location.d.j.g = this.c.addrType;
            }
            this.c.openGps = message.getData().getBoolean("openGPS");
            this.c.scanSpan = message.getData().getInt("scanSpan");
            this.c.timeOut = message.getData().getInt("timeOut");
            this.c.priority = message.getData().getInt(LogFactory.PRIORITY_KEY);
            this.c.location_change_notify = message.getData().getBoolean("location_change_notify");
            this.c.mIsNeedDeviceDirect = message.getData().getBoolean("needDirect", false);
            this.c.isNeedAltitude = message.getData().getBoolean("isneedaltitude", false);
            com.baidu.location.d.j.h = com.baidu.location.d.j.h || message.getData().getBoolean("isneedaptag", false);
            if (!com.baidu.location.d.j.i && !message.getData().getBoolean("isneedaptagd", false)) {
                z = false;
            }
            com.baidu.location.d.j.i = z;
            com.baidu.location.d.j.Q = message.getData().getFloat("autoNotifyLocSensitivity", 0.5f);
            int i = message.getData().getInt("wifitimeout", Integer.MAX_VALUE);
            if (i < com.baidu.location.d.j.ae) {
                com.baidu.location.d.j.ae = i;
            }
            int i2 = message.getData().getInt("autoNotifyMaxInterval", 0);
            if (i2 >= com.baidu.location.d.j.V) {
                com.baidu.location.d.j.V = i2;
            }
            int i3 = message.getData().getInt("autoNotifyMinDistance", 0);
            if (i3 >= com.baidu.location.d.j.X) {
                com.baidu.location.d.j.X = i3;
            }
            int i4 = message.getData().getInt("autoNotifyMinTimeInterval", 0);
            if (i4 >= com.baidu.location.d.j.W) {
                com.baidu.location.d.j.W = i4;
            }
            if (this.c.scanSpan >= 1000) {
            }
            if (this.c.mIsNeedDeviceDirect || this.c.isNeedAltitude) {
                n.a().a(this.c.mIsNeedDeviceDirect);
                n.a().b();
            }
            aVar.b |= this.c.isNeedAltitude;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i) {
            Message messageObtain = Message.obtain((Handler) null, i);
            try {
                if (this.b != null) {
                    this.b.send(messageObtain);
                }
                this.d = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.d++;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(int i, Bundle bundle) {
            Message messageObtain = Message.obtain((Handler) null, i);
            messageObtain.setData(bundle);
            try {
                if (this.b != null) {
                    this.b.send(messageObtain);
                }
                this.d = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.d++;
                }
                e.printStackTrace();
            }
        }

        private void a(int i, String str, BDLocation bDLocation) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(str, bDLocation);
            bundle.setClassLoader(BDLocation.class.getClassLoader());
            Message messageObtain = Message.obtain((Handler) null, i);
            messageObtain.setData(bundle);
            try {
                if (this.b != null) {
                    this.b.send(messageObtain);
                }
                this.d = 0;
            } catch (Exception e) {
                if (e instanceof DeadObjectException) {
                    this.d++;
                }
            }
        }

        public void a() {
            if (this.c.location_change_notify) {
                if (com.baidu.location.d.j.b) {
                    a(54);
                } else {
                    a(55);
                }
            }
        }

        public void a(BDLocation bDLocation) {
            a(bDLocation, 21);
        }

        public void a(BDLocation bDLocation, int i) {
            BDLocation bDLocation2 = new BDLocation(bDLocation);
            if (i == 21) {
                a(27, "locStr", bDLocation2);
            }
            if (this.c.coorType != null && !this.c.coorType.equals("gcj02")) {
                double longitude = bDLocation2.getLongitude();
                double latitude = bDLocation2.getLatitude();
                if (longitude != Double.MIN_VALUE && latitude != Double.MIN_VALUE) {
                    if ((bDLocation2.getCoorType() != null && bDLocation2.getCoorType().equals("gcj02")) || bDLocation2.getCoorType() == null) {
                        double[] dArrCoorEncrypt = Jni.coorEncrypt(longitude, latitude, this.c.coorType);
                        bDLocation2.setLongitude(dArrCoorEncrypt[0]);
                        bDLocation2.setLatitude(dArrCoorEncrypt[1]);
                        bDLocation2.setCoorType(this.c.coorType);
                    } else if (bDLocation2.getCoorType() != null && bDLocation2.getCoorType().equals("wgs84") && !this.c.coorType.equals("bd09ll")) {
                        double[] dArrCoorEncrypt2 = Jni.coorEncrypt(longitude, latitude, "wgs842mc");
                        bDLocation2.setLongitude(dArrCoorEncrypt2[0]);
                        bDLocation2.setLatitude(dArrCoorEncrypt2[1]);
                        bDLocation2.setCoorType("wgs84mc");
                    }
                }
            }
            a(i, "locStr", bDLocation2);
        }
    }

    private class b implements Runnable {
        final /* synthetic */ a a;
        private int b;
        private boolean c;

        @Override // java.lang.Runnable
        public void run() {
            if (this.c) {
                return;
            }
            this.b++;
            this.a.m = false;
        }
    }

    private a() {
        this.f = null;
        this.f = new ArrayList<>();
    }

    private C0007a a(Messenger messenger) {
        if (this.f == null) {
            return null;
        }
        for (C0007a c0007a : this.f) {
            if (c0007a.b.equals(messenger)) {
                return c0007a;
            }
        }
        return null;
    }

    public static a a() {
        if (e == null) {
            e = new a();
        }
        return e;
    }

    private void a(C0007a c0007a) {
        if (c0007a == null) {
            return;
        }
        if (a(c0007a.b) != null) {
            c0007a.a(14);
        } else {
            this.f.add(c0007a);
            c0007a.a(13);
        }
    }

    private void b(String str) {
        Intent intent = new Intent("com.baidu.location.flp.log");
        intent.setPackage("com.baidu.baidulocationdemo");
        intent.putExtra("data", str);
        intent.putExtra("pack", com.baidu.location.d.b.d);
        intent.putExtra("tag", "state");
        com.baidu.location.f.getServiceContext().sendBroadcast(intent);
    }

    private void e() {
        f();
        d();
    }

    private void f() {
        boolean z = false;
        boolean z2 = false;
        for (C0007a c0007a : this.f) {
            if (c0007a.c.openGps) {
                z2 = true;
            }
            z = c0007a.c.location_change_notify ? true : z;
        }
        com.baidu.location.d.j.a = z;
        if (this.g != z2) {
            this.g = z2;
            com.baidu.location.b.e.a().a(this.g);
        }
    }

    public void a(Bundle bundle, int i) {
        Iterator<C0007a> it = this.f.iterator();
        while (it.hasNext()) {
            try {
                C0007a next = it.next();
                next.a(i, bundle);
                if (next.d > 4) {
                    it.remove();
                }
            } catch (Exception e2) {
                return;
            }
        }
    }

    public void a(Message message) {
        if (message == null || message.replyTo == null) {
            return;
        }
        c = System.currentTimeMillis();
        this.a = true;
        com.baidu.location.b.h.a().b();
        a(new C0007a(this, message));
        e();
        if (this.l) {
            b(MessageKey.MSG_ACCEPT_TIME_START);
            this.d = 0;
        }
    }

    public void a(BDLocation bDLocation) {
        b(bDLocation);
    }

    public void a(String str) {
        c(new BDLocation(str));
    }

    public void b() {
        this.f.clear();
        this.h = null;
        e();
    }

    public void b(Message message) {
        C0007a c0007aA = a(message.replyTo);
        if (c0007aA != null) {
            this.f.remove(c0007aA);
        }
        n.a().c();
        e();
        if (this.l) {
            b("stop");
            this.d = 0;
        }
    }

    public void b(BDLocation bDLocation) {
        boolean z = l.h;
        if (z) {
            l.h = false;
        }
        if (com.baidu.location.d.j.V >= 10000 && (bDLocation.getLocType() == 61 || bDLocation.getLocType() == 161 || bDLocation.getLocType() == 66)) {
            if (this.h != null) {
                float[] fArr = new float[1];
                Location.distanceBetween(this.h.getLatitude(), this.h.getLongitude(), bDLocation.getLatitude(), bDLocation.getLongitude(), fArr);
                if (fArr[0] <= com.baidu.location.d.j.X && !z) {
                    return;
                }
                this.h = null;
                this.h = new BDLocation(bDLocation);
            } else {
                this.h = new BDLocation(bDLocation);
            }
        }
        if (bDLocation == null || bDLocation.getLocType() != 161 || j.a().b()) {
            Iterator<C0007a> it = this.f.iterator();
            while (it.hasNext()) {
                try {
                    C0007a next = it.next();
                    next.a(bDLocation);
                    if (next.d > 4) {
                        it.remove();
                    }
                } catch (Exception e2) {
                    return;
                }
            }
            return;
        }
        if (this.i == null) {
            this.i = new BDLocation();
            this.i.setLocType(HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED);
        }
        Iterator<C0007a> it2 = this.f.iterator();
        while (it2.hasNext()) {
            try {
                C0007a next2 = it2.next();
                next2.a(this.i);
                if (next2.d > 4) {
                    it2.remove();
                }
            } catch (Exception e3) {
                return;
            }
        }
    }

    public String c() {
        StringBuffer stringBuffer = new StringBuffer(256);
        if (this.f.isEmpty()) {
            return "&prod=" + com.baidu.location.d.b.e + ":" + com.baidu.location.d.b.d;
        }
        C0007a c0007a = this.f.get(0);
        if (c0007a.c.prodName != null) {
            stringBuffer.append(c0007a.c.prodName);
        }
        if (c0007a.a != null) {
            stringBuffer.append(":");
            stringBuffer.append(c0007a.a);
            stringBuffer.append("|");
        }
        String string = stringBuffer.toString();
        if (string == null || string.equals(Constants.MAIN_VERSION_TAG)) {
            return null;
        }
        return "&prod=" + string;
    }

    public void c(BDLocation bDLocation) {
        Address addressA = l.c().a(bDLocation);
        String strF = l.c().f();
        List<Poi> listG = l.c().g();
        if (addressA != null) {
            bDLocation.setAddr(addressA);
        }
        if (strF != null) {
            bDLocation.setLocationDescribe(strF);
        }
        if (listG != null) {
            bDLocation.setPoiList(listG);
        }
        l.c().c(bDLocation);
        a(bDLocation);
    }

    public boolean c(Message message) {
        boolean z = true;
        C0007a c0007aA = a(message.replyTo);
        if (c0007aA == null) {
            return false;
        }
        int i = c0007aA.c.scanSpan;
        c0007aA.c.scanSpan = message.getData().getInt("scanSpan", c0007aA.c.scanSpan);
        if (c0007aA.c.scanSpan < 1000) {
            n.a().c();
            this.a = false;
        } else {
            this.a = true;
        }
        if (c0007aA.c.scanSpan <= 999 || i >= 1000) {
            z = false;
        } else {
            if (c0007aA.c.mIsNeedDeviceDirect || c0007aA.c.isNeedAltitude) {
                n.a().a(c0007aA.c.mIsNeedDeviceDirect);
                n.a().b();
            }
            this.b |= c0007aA.c.isNeedAltitude;
        }
        c0007aA.c.openGps = message.getData().getBoolean("openGPS", c0007aA.c.openGps);
        String string = message.getData().getString("coorType");
        LocationClientOption locationClientOption = c0007aA.c;
        if (string == null || string.equals(Constants.MAIN_VERSION_TAG)) {
            string = c0007aA.c.coorType;
        }
        locationClientOption.coorType = string;
        String string2 = message.getData().getString("addrType");
        LocationClientOption locationClientOption2 = c0007aA.c;
        if (string2 == null || string2.equals(Constants.MAIN_VERSION_TAG)) {
            string2 = c0007aA.c.addrType;
        }
        locationClientOption2.addrType = string2;
        if (!com.baidu.location.d.j.g.equals(c0007aA.c.addrType)) {
            l.c().i();
        }
        c0007aA.c.timeOut = message.getData().getInt("timeOut", c0007aA.c.timeOut);
        c0007aA.c.location_change_notify = message.getData().getBoolean("location_change_notify", c0007aA.c.location_change_notify);
        c0007aA.c.priority = message.getData().getInt(LogFactory.PRIORITY_KEY, c0007aA.c.priority);
        int i2 = message.getData().getInt("wifitimeout", Integer.MAX_VALUE);
        if (i2 < com.baidu.location.d.j.ae) {
            com.baidu.location.d.j.ae = i2;
        }
        e();
        return z;
    }

    public int d(Message message) {
        C0007a c0007aA;
        if (message == null || message.replyTo == null || (c0007aA = a(message.replyTo)) == null || c0007aA.c == null) {
            return 1;
        }
        return c0007aA.c.priority;
    }

    public void d() {
        Iterator<C0007a> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
    }

    public int e(Message message) {
        C0007a c0007aA;
        if (message == null || message.replyTo == null || (c0007aA = a(message.replyTo)) == null || c0007aA.c == null) {
            return 1000;
        }
        return c0007aA.c.scanSpan;
    }
}
