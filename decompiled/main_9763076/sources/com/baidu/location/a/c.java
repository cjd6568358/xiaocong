package com.baidu.location.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import bsh.ParserConstants;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.LocationClientOption;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static Method g = null;
    private static Method h = null;
    private static Method i = null;
    private static Method j = null;
    private static Method k = null;
    private static Class<?> l = null;
    String a;
    String b;
    private Context d;
    private TelephonyManager e;
    private WifiManager m;
    private String o;
    private LocationClientOption p;
    private a q;
    private String s;
    private String t;
    private com.baidu.location.b.a f = new com.baidu.location.b.a();
    private C0008c n = null;
    private String r = null;
    b c = new b();

    public interface a {
        void onReceiveLocation(BDLocation bDLocation);
    }

    class b extends com.baidu.location.d.e {
        String a = null;

        b() {
            this.k = new HashMap();
        }

        @Override // com.baidu.location.d.e
        public void a() {
            this.h = com.baidu.location.d.j.c();
            if (c.this.s != null && c.this.t != null) {
                this.a += String.format(Locale.CHINA, "&ki=%s&sn=%s", c.this.s, c.this.t);
            }
            String strEncodeTp4 = Jni.encodeTp4(this.a);
            this.a = null;
            this.k.put("bloc", strEncodeTp4);
            this.k.put("trtm", String.format(Locale.CHINA, "%d", Long.valueOf(System.currentTimeMillis())));
        }

        public void a(String str) {
            this.a = str;
            b(com.baidu.location.d.j.f);
        }

        @Override // com.baidu.location.d.e
        public void a(boolean z) {
            BDLocation bDLocation;
            if (z && this.j != null) {
                try {
                    try {
                        bDLocation = new BDLocation(this.j);
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(63);
                    }
                    if (bDLocation != null && bDLocation.getLocType() == 161) {
                        bDLocation.setCoorType(c.this.p.coorType);
                        bDLocation.setLocationID(Jni.en1(c.this.a + ";" + c.this.b + ";" + bDLocation.getTime()));
                        c.this.q.onReceiveLocation(bDLocation);
                    }
                } catch (Exception e2) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    /* JADX INFO: renamed from: com.baidu.location.a.c$c, reason: collision with other inner class name */
    protected class C0008c {
        public List<ScanResult> a;
        private long c;

        public C0008c(List<ScanResult> list) {
            this.a = null;
            this.c = 0L;
            this.a = list;
            this.c = System.currentTimeMillis();
            c();
        }

        private String b() {
            WifiInfo connectionInfo = c.this.m.getConnectionInfo();
            if (connectionInfo == null) {
                return null;
            }
            try {
                String bssid = connectionInfo.getBSSID();
                String strReplace = bssid != null ? bssid.replace(":", Constants.MAIN_VERSION_TAG) : null;
                if (strReplace == null || strReplace.length() == 12) {
                    return new String(strReplace);
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }

        private void c() {
            boolean z;
            if (a() < 1) {
                return;
            }
            boolean z2 = true;
            for (int size = this.a.size() - 1; size >= 1 && z2; size--) {
                int i = 0;
                z2 = false;
                while (i < size) {
                    if (this.a.get(i).level < this.a.get(i + 1).level) {
                        ScanResult scanResult = this.a.get(i + 1);
                        this.a.set(i + 1, this.a.get(i));
                        this.a.set(i, scanResult);
                        z = true;
                    } else {
                        z = z2;
                    }
                    i++;
                    z2 = z;
                }
            }
        }

        public int a() {
            if (this.a == null) {
                return 0;
            }
            return this.a.size();
        }

        /* JADX WARN: Code duplicated, block: B:66:0x0195  */
        /* JADX WARN: Code duplicated, block: B:68:0x019b A[PHI: r4
  0x019b: PHI (r4v11 long) = (r4v0 long), (r4v2 long) binds: [B:7:0x0017, B:10:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
        public String a(int i) {
            boolean z;
            long j;
            int i2;
            if (a() < 2) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            long jElapsedRealtimeNanos = 0;
            long j2 = 0;
            if (Build.VERSION.SDK_INT >= 19) {
                try {
                    jElapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos() / 1000;
                } catch (Error e) {
                    jElapsedRealtimeNanos = 0;
                }
                if (jElapsedRealtimeNanos > 0) {
                    z = true;
                } else {
                    z = false;
                }
            } else {
                z = false;
            }
            StringBuffer stringBuffer = new StringBuffer(WXMediaMessage.TITLE_LENGTH_LIMIT);
            int size = this.a.size();
            boolean z2 = true;
            int i3 = 0;
            String strB = b();
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (true) {
                if (i6 >= size) {
                    j = j2;
                    break;
                }
                if (this.a.get(i6).level == 0) {
                    i2 = i3;
                    j = j2;
                } else {
                    i4++;
                    if (z2) {
                        stringBuffer.append("&wf=");
                        z2 = false;
                    } else {
                        stringBuffer.append("|");
                    }
                    String strReplace = this.a.get(i6).BSSID.replace(":", Constants.MAIN_VERSION_TAG);
                    stringBuffer.append(strReplace);
                    if (strB != null && strReplace.equals(strB)) {
                        i5 = i4;
                    }
                    int i7 = this.a.get(i6).level;
                    if (i7 < 0) {
                        i7 = -i7;
                    }
                    stringBuffer.append(String.format(Locale.CHINA, ";%d;", Integer.valueOf(i7)));
                    int i8 = i3 + 1;
                    if (z) {
                        try {
                            j = (jElapsedRealtimeNanos - this.a.get(i6).timestamp) / 1000000;
                        } catch (Throwable th) {
                            j = 0;
                        }
                        arrayList.add(Long.valueOf(j));
                        if (j <= j2) {
                            j = j2;
                        }
                    } else {
                        j = j2;
                    }
                    if (i8 > i) {
                        break;
                    }
                    i2 = i8;
                }
                i6++;
                j2 = j;
                i3 = i2;
            }
            if (i5 > 0) {
                stringBuffer.append("&wf_n=");
                stringBuffer.append(i5);
            }
            if (z2) {
                return null;
            }
            if (j > 10 && arrayList.size() > 0 && ((Long) arrayList.get(0)).longValue() > 0) {
                StringBuffer stringBuffer2 = new StringBuffer(ParserConstants.LSHIFTASSIGN);
                stringBuffer2.append("&wf_ut=");
                boolean z3 = true;
                Long l = (Long) arrayList.get(0);
                Iterator it = arrayList.iterator();
                while (true) {
                    boolean z4 = z3;
                    if (!it.hasNext()) {
                        break;
                    }
                    Long l2 = (Long) it.next();
                    if (z4) {
                        z4 = false;
                        stringBuffer2.append(l2.longValue());
                    } else {
                        long jLongValue = l2.longValue() - l.longValue();
                        if (jLongValue != 0) {
                            stringBuffer2.append(Constants.MAIN_VERSION_TAG + jLongValue);
                        }
                    }
                    z3 = z4;
                    stringBuffer2.append("|");
                }
                stringBuffer.append(stringBuffer2.toString());
            }
            return stringBuffer.toString();
        }
    }

    public c(Context context, LocationClientOption locationClientOption, a aVar) {
        String deviceId;
        this.d = null;
        this.e = null;
        this.m = null;
        this.o = null;
        this.s = null;
        this.t = null;
        this.a = null;
        this.b = null;
        this.d = context.getApplicationContext();
        this.p = new LocationClientOption(locationClientOption);
        this.q = aVar;
        this.a = this.d.getPackageName();
        this.b = null;
        try {
            this.e = (TelephonyManager) this.d.getSystemService("phone");
            deviceId = this.e.getDeviceId();
        } catch (Exception e) {
            deviceId = null;
        }
        try {
            this.b = CommonParam.a(this.d);
        } catch (Exception e2) {
            this.b = null;
        }
        if (this.b != null) {
            com.baidu.location.d.j.n = Constants.MAIN_VERSION_TAG + this.b;
            this.o = "&prod=" + this.p.prodName + ":" + this.a + "|&cu=" + this.b + "&coor=" + locationClientOption.getCoorType();
        } else {
            this.o = "&prod=" + this.p.prodName + ":" + this.a + "|&im=" + deviceId + "&coor=" + locationClientOption.getCoorType();
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&fw=");
        stringBuffer.append("7.52");
        stringBuffer.append("&sdk=");
        stringBuffer.append("7.52");
        stringBuffer.append("&lt=1");
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&resid=");
        stringBuffer.append("12");
        if (locationClientOption.getAddrType() != null) {
        }
        if (locationClientOption.getAddrType() != null && locationClientOption.getAddrType().equals("all")) {
            this.o += "&addr=allj";
        }
        if (locationClientOption.isNeedAptag || locationClientOption.isNeedAptagd) {
            this.o += "&sema=";
            if (locationClientOption.isNeedAptag) {
                this.o += "aptag|";
            }
            if (locationClientOption.isNeedAptagd) {
                this.o += "aptagd|";
            }
            this.s = j.b(this.d);
            this.t = j.c(this.d);
        }
        stringBuffer.append("&first=1");
        stringBuffer.append("&os=A");
        stringBuffer.append(Build.VERSION.SDK);
        this.o += stringBuffer.toString();
        this.m = (WifiManager) this.d.getApplicationContext().getSystemService("wifi");
        String strA = a();
        strA = TextUtils.isEmpty(strA) ? strA : strA.replace(":", Constants.MAIN_VERSION_TAG);
        if (!TextUtils.isEmpty(strA) && !strA.equals("020000000000")) {
            this.o += "&mac=" + strA;
        }
        b();
    }

    private int a(int i2) {
        if (i2 == Integer.MAX_VALUE) {
            return -1;
        }
        return i2;
    }

    @SuppressLint({"NewApi"})
    private com.baidu.location.b.a a(CellInfo cellInfo) {
        boolean z = false;
        int i2 = -1;
        int iIntValue = Integer.valueOf(Build.VERSION.SDK_INT).intValue();
        if (iIntValue < 17) {
            return null;
        }
        com.baidu.location.b.a aVar = new com.baidu.location.b.a();
        if (cellInfo instanceof CellInfoGsm) {
            CellIdentityGsm cellIdentity = ((CellInfoGsm) cellInfo).getCellIdentity();
            aVar.c = a(cellIdentity.getMcc());
            aVar.d = a(cellIdentity.getMnc());
            aVar.a = a(cellIdentity.getLac());
            aVar.b = a(cellIdentity.getCid());
            aVar.i = 'g';
            aVar.h = ((CellInfoGsm) cellInfo).getCellSignalStrength().getAsuLevel();
            z = true;
        } else if (cellInfo instanceof CellInfoCdma) {
            CellIdentityCdma cellIdentity2 = ((CellInfoCdma) cellInfo).getCellIdentity();
            aVar.e = cellIdentity2.getLatitude();
            aVar.f = cellIdentity2.getLongitude();
            aVar.d = a(cellIdentity2.getSystemId());
            aVar.a = a(cellIdentity2.getNetworkId());
            aVar.b = a(cellIdentity2.getBasestationId());
            aVar.i = 'c';
            aVar.h = ((CellInfoCdma) cellInfo).getCellSignalStrength().getCdmaDbm();
            if (this.f == null || this.f.c <= 0) {
                try {
                    String networkOperator = this.e.getNetworkOperator();
                    if (networkOperator != null && networkOperator.length() > 0 && networkOperator.length() >= 3) {
                        int iIntValue2 = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                        if (iIntValue2 < 0) {
                            iIntValue2 = -1;
                        }
                        i2 = iIntValue2;
                    }
                } catch (Exception e) {
                }
                if (i2 > 0) {
                    aVar.c = i2;
                }
            } else {
                aVar.c = this.f.c;
            }
            z = true;
        } else if (cellInfo instanceof CellInfoLte) {
            CellIdentityLte cellIdentity3 = ((CellInfoLte) cellInfo).getCellIdentity();
            aVar.c = a(cellIdentity3.getMcc());
            aVar.d = a(cellIdentity3.getMnc());
            aVar.a = a(cellIdentity3.getTac());
            aVar.b = a(cellIdentity3.getCi());
            aVar.i = 'g';
            aVar.h = ((CellInfoLte) cellInfo).getCellSignalStrength().getAsuLevel();
            z = true;
        }
        if (iIntValue >= 18 && !z) {
            try {
                if (cellInfo instanceof CellInfoWcdma) {
                    CellIdentityWcdma cellIdentity4 = ((CellInfoWcdma) cellInfo).getCellIdentity();
                    aVar.c = a(cellIdentity4.getMcc());
                    aVar.d = a(cellIdentity4.getMnc());
                    aVar.a = a(cellIdentity4.getLac());
                    aVar.b = a(cellIdentity4.getCid());
                    aVar.i = 'g';
                    aVar.h = ((CellInfoWcdma) cellInfo).getCellSignalStrength().getAsuLevel();
                }
            } catch (Exception e2) {
            }
        }
        try {
            aVar.g = System.currentTimeMillis() - ((SystemClock.elapsedRealtimeNanos() - cellInfo.getTimeStamp()) / 1000000);
        } catch (Error e3) {
            aVar.g = System.currentTimeMillis();
        }
        return aVar;
    }

    private void a(CellLocation cellLocation) {
        int i2 = 0;
        if (cellLocation == null || this.e == null) {
            return;
        }
        com.baidu.location.b.a aVar = new com.baidu.location.b.a();
        String networkOperator = this.e.getNetworkOperator();
        if (networkOperator != null && networkOperator.length() > 0) {
            try {
                if (networkOperator.length() >= 3) {
                    int iIntValue = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                    if (iIntValue < 0) {
                        iIntValue = this.f.c;
                    }
                    aVar.c = iIntValue;
                }
                String strSubstring = networkOperator.substring(3);
                if (strSubstring != null) {
                    char[] charArray = strSubstring.toCharArray();
                    while (i2 < charArray.length && Character.isDigit(charArray[i2])) {
                        i2++;
                    }
                }
                int iIntValue2 = Integer.valueOf(strSubstring.substring(0, i2)).intValue();
                if (iIntValue2 < 0) {
                    iIntValue2 = this.f.d;
                }
                aVar.d = iIntValue2;
            } catch (Exception e) {
            }
        }
        if (cellLocation instanceof GsmCellLocation) {
            aVar.a = ((GsmCellLocation) cellLocation).getLac();
            aVar.b = ((GsmCellLocation) cellLocation).getCid();
            aVar.i = 'g';
        } else if (cellLocation instanceof CdmaCellLocation) {
            aVar.i = 'c';
            if (l == null) {
                try {
                    l = Class.forName("android.telephony.cdma.CdmaCellLocation");
                    g = l.getMethod("getBaseStationId", new Class[0]);
                    h = l.getMethod("getNetworkId", new Class[0]);
                    i = l.getMethod("getSystemId", new Class[0]);
                    j = l.getMethod("getBaseStationLatitude", new Class[0]);
                    k = l.getMethod("getBaseStationLongitude", new Class[0]);
                } catch (Exception e2) {
                    l = null;
                    return;
                }
            }
            if (l != null && l.isInstance(cellLocation)) {
                try {
                    int iIntValue3 = ((Integer) i.invoke(cellLocation, new Object[0])).intValue();
                    if (iIntValue3 < 0) {
                        iIntValue3 = this.f.d;
                    }
                    aVar.d = iIntValue3;
                    aVar.b = ((Integer) g.invoke(cellLocation, new Object[0])).intValue();
                    aVar.a = ((Integer) h.invoke(cellLocation, new Object[0])).intValue();
                    Object objInvoke = j.invoke(cellLocation, new Object[0]);
                    if (((Integer) objInvoke).intValue() < Integer.MAX_VALUE) {
                        aVar.e = ((Integer) objInvoke).intValue();
                    }
                    Object objInvoke2 = k.invoke(cellLocation, new Object[0]);
                    if (((Integer) objInvoke2).intValue() < Integer.MAX_VALUE) {
                        aVar.f = ((Integer) objInvoke2).intValue();
                    }
                } catch (Exception e3) {
                    return;
                }
            }
        }
        if (aVar.b()) {
            this.f = aVar;
        } else {
            this.f = null;
        }
    }

    private String b(int i2) {
        String strG;
        String strA;
        try {
            com.baidu.location.b.a aVarD = d();
            if (aVarD == null || !aVarD.b()) {
                a(this.e.getCellLocation());
            } else {
                this.f = aVarD;
            }
            strG = (this.f == null || !this.f.b()) ? null : this.f.g();
            try {
                if (!TextUtils.isEmpty(strG) && this.f.j != null) {
                    strG = strG + this.f.j;
                }
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            strG = null;
        }
        try {
            this.n = null;
            this.n = new C0008c(this.m.getScanResults());
            strA = this.n.a(i2);
        } catch (Exception e) {
            strA = null;
        }
        if (strG == null && strA == null) {
            this.r = null;
            return null;
        }
        if (strA != null) {
            strG = strG == null ? strA : strG + strA;
        }
        if (strG == null) {
            return null;
        }
        this.r = strG;
        if (this.o != null) {
            this.r += this.o;
        }
        return strG + this.o;
    }

    @SuppressLint({"NewApi"})
    private com.baidu.location.b.a d() {
        com.baidu.location.b.a aVarA;
        if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 17) {
            return null;
        }
        try {
            List<CellInfo> allCellInfo = this.e.getAllCellInfo();
            if (allCellInfo == null || allCellInfo.size() <= 0) {
                return null;
            }
            com.baidu.location.b.a aVar = null;
            for (CellInfo cellInfo : allCellInfo) {
                if (cellInfo.isRegistered()) {
                    boolean z = aVar != null;
                    aVarA = a(cellInfo);
                    if (aVarA == null) {
                        continue;
                    } else {
                        if (!aVarA.b()) {
                            aVarA = null;
                        } else if (z) {
                            aVar.j = aVarA.h();
                            return aVar;
                        }
                        if (aVar != null) {
                        }
                        aVar = aVarA;
                    }
                }
                aVarA = aVar;
                aVar = aVarA;
            }
            return aVar;
        } catch (Throwable th) {
            return null;
        }
    }

    public String a() {
        try {
            WifiInfo connectionInfo = this.m.getConnectionInfo();
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

    public String b() {
        try {
            return b(15);
        } catch (Exception e) {
            return null;
        }
    }

    public void c() {
        if (this.r != null && 0 == 0) {
            this.c.a(this.r);
        }
    }
}
