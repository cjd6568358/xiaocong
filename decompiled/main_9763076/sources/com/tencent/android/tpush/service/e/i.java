package com.tencent.android.tpush.service.e;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class i {
    public static JSONArray a(Context context) {
        if (!m.d(context, "android.permission.ACCESS_COARSE_LOCATION")) {
            com.tencent.android.tpush.a.a.c("TLocationManager", "Require the permissionandroid.permission.ACCESS_COARSE_LOCATION");
            return null;
        }
        com.tencent.android.tpush.a.a.c("TLocationManager", "getCellInfo begin");
        JSONArray jSONArray = new JSONArray();
        try {
            ArrayList arrayListC = c(context);
            if (arrayListC != null) {
                Iterator it = arrayListC.iterator();
                while (it.hasNext()) {
                    jSONArray.put(((k) it.next()).a());
                }
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("TLocationManager", "getCellInfo", e);
        }
        return jSONArray;
    }

    public static JSONObject b(Context context) {
        if (!m.d(context, "android.permission.ACCESS_COARSE_LOCATION")) {
            return null;
        }
        try {
            Location lastKnownLocation = ((LocationManager) context.getSystemService("location")).getLastKnownLocation("network");
            if (lastKnownLocation != null) {
                return a(lastKnownLocation);
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.i("TLocationManager", e.getMessage());
        }
        return null;
    }

    public static ArrayList c(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        ArrayList arrayList = new ArrayList();
        k kVar = new k(null);
        int networkType = telephonyManager.getNetworkType();
        int phoneType = telephonyManager.getPhoneType();
        com.tencent.android.tpush.a.a.c("TLocationManager", "getCellIDInfo--> \t\tphoneType = " + phoneType);
        if (phoneType == 2) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
            if (cdmaCellLocation == null) {
                com.tencent.android.tpush.a.a.i("TLocationManager", "CdmaCellLocation is null!!!");
                return null;
            }
            int networkId = cdmaCellLocation.getNetworkId();
            String strSubstring = telephonyManager.getNetworkOperator().substring(0, 3);
            String strValueOf = String.valueOf(cdmaCellLocation.getSystemId());
            kVar.a = cdmaCellLocation.getBaseStationId();
            kVar.b = Integer.valueOf(strSubstring).intValue();
            kVar.c = Integer.valueOf(strValueOf).intValue();
            kVar.d = networkId;
            kVar.e = "cdma";
            kVar.h = networkType;
            arrayList.add(kVar);
            List<CellInfo> allCellInfo = telephonyManager.getAllCellInfo();
            int size = allCellInfo.size();
            for (int i = 0; i < size && i < 3; i++) {
                CellInfo cellInfo = allCellInfo.get(i);
                if (cellInfo instanceof CellInfoCdma) {
                    CellIdentityCdma cellIdentity = ((CellInfoCdma) cellInfo).getCellIdentity();
                    k kVar2 = new k(null);
                    kVar2.a = cellIdentity.getBasestationId();
                    kVar2.b = Integer.valueOf(strSubstring).intValue();
                    kVar2.c = Integer.valueOf(strValueOf).intValue();
                    kVar2.d = networkId;
                    kVar2.f = cellIdentity.getLatitude();
                    kVar2.g = cellIdentity.getLongitude();
                    kVar2.h = networkType;
                    kVar2.e = "cdma";
                    arrayList.add(kVar2);
                } else if (cellInfo instanceof CellInfoWcdma) {
                    CellIdentityWcdma cellIdentity2 = ((CellInfoWcdma) cellInfo).getCellIdentity();
                    k kVar3 = new k(null);
                    kVar3.a = cellIdentity2.getCid();
                    kVar3.b = cellIdentity2.getMcc();
                    kVar3.c = cellIdentity2.getMnc();
                    kVar3.d = cellIdentity2.getLac();
                    kVar3.d = networkId;
                    kVar3.e = "wcdma";
                    kVar3.h = networkType;
                    arrayList.add(kVar3);
                }
            }
        } else {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
            if (gsmCellLocation == null) {
                com.tencent.android.tpush.a.a.i("TLocationManager", "GsmCellLocation is null!!!");
                return null;
            }
            int lac = gsmCellLocation.getLac();
            String strSubstring2 = telephonyManager.getNetworkOperator().substring(0, 3);
            String strSubstring3 = telephonyManager.getNetworkOperator().substring(3, 5);
            kVar.a = gsmCellLocation.getCid();
            kVar.b = Integer.valueOf(strSubstring2).intValue();
            kVar.c = Integer.valueOf(strSubstring3).intValue();
            kVar.d = lac;
            kVar.h = networkType;
            kVar.e = "gsm";
            arrayList.add(kVar);
            List<CellInfo> allCellInfo2 = telephonyManager.getAllCellInfo();
            int size2 = allCellInfo2.size();
            for (int i2 = 0; i2 < size2 && i2 < 3; i2++) {
                CellInfo cellInfo2 = allCellInfo2.get(i2);
                if (cellInfo2 instanceof CellInfoGsm) {
                    CellIdentityGsm cellIdentity3 = ((CellInfoGsm) cellInfo2).getCellIdentity();
                    k kVar4 = new k(null);
                    kVar4.a = cellIdentity3.getCid();
                    kVar4.b = cellIdentity3.getMcc();
                    kVar4.c = cellIdentity3.getMnc();
                    kVar4.d = cellIdentity3.getLac();
                    kVar4.e = "gsm";
                    kVar4.h = networkType;
                    arrayList.add(kVar4);
                } else if (cellInfo2 instanceof CellInfoLte) {
                    CellIdentityLte cellIdentity4 = ((CellInfoLte) cellInfo2).getCellIdentity();
                    k kVar5 = new k(null);
                    kVar5.a = cellIdentity4.getCi();
                    kVar5.b = cellIdentity4.getMcc();
                    kVar5.c = cellIdentity4.getMnc();
                    kVar5.d = cellIdentity4.getTac();
                    kVar5.e = "lte";
                    kVar5.h = networkType;
                    arrayList.add(kVar5);
                }
            }
        }
        return arrayList;
    }

    public static JSONObject d(Context context) {
        if (!m.d(context, "android.permission.ACCESS_FINE_LOCATION")) {
            com.tencent.android.tpush.a.a.c("TLocationManager", "Require the permissionandroid.permission.ACCESS_FINE_LOCATION");
            return null;
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (!a(locationManager)) {
                return null;
            }
            Criteria criteria = new Criteria();
            criteria.setAccuracy(1);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(1);
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
            if (lastKnownLocation != null) {
                return a(lastKnownLocation);
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.i("TLocationManager", "[getGps]" + e.getMessage());
        }
        return null;
    }

    private static JSONObject a(Location location) {
        if (location == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("lat", location.getLatitude());
            jSONObject.put("lng", location.getLongitude());
            jSONObject.put("alt", location.getAltitude());
            jSONObject.put("bear", location.getBearing());
            jSONObject.put("acc", location.getAccuracy());
            jSONObject.put("time", location.getTime());
            jSONObject.put("sp", location.getSpeed());
            jSONObject.put("pvd", location.getProvider());
            return jSONObject;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.i("TLocationManager", "[loactionToJson]" + e.getMessage());
            return null;
        }
    }

    private static boolean a(LocationManager locationManager) {
        return locationManager.isProviderEnabled("gps");
    }

    public static boolean e(Context context) {
        try {
            return ((WifiManager) context.getSystemService("wifi")).isWifiEnabled();
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("TLocationManager", "Wifi Error", e);
            return false;
        }
    }

    public static JSONArray f(Context context) {
        if (!m.d(context, "android.permission.ACCESS_WIFI_STATE")) {
            com.tencent.android.tpush.a.a.c("TLocationManager", "Require the permissionandroid.permission.ACCESS_WIFI_STATE");
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        try {
            Iterator it = h(context).iterator();
            while (it.hasNext()) {
                jSONArray.put(((l) it.next()).a());
            }
            return jSONArray;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("TLocationManager", Constants.MAIN_VERSION_TAG, e);
            return jSONArray;
        }
    }

    private static List h(Context context) {
        if (!e(context)) {
            return new ArrayList();
        }
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        l lVar = null;
        if (connectionInfo != null) {
            lVar = new l(connectionInfo.getBSSID(), connectionInfo.getRssi(), connectionInfo.getSSID());
        }
        ArrayList arrayList = new ArrayList();
        if (lVar != null) {
            arrayList.add(lVar);
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (scanResults != null && scanResults.size() > 0) {
            int i = 0;
            for (ScanResult scanResult : scanResults) {
                i++;
                if (i >= 10) {
                    break;
                }
                l lVar2 = new l(scanResult);
                if (!lVar2.c.equals(lVar.c)) {
                    arrayList.add(lVar2);
                }
            }
        }
        return arrayList;
    }

    public static void g(Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArrayA = a(context);
            if (!m.a(jSONArrayA)) {
                jSONObject.put("cell", jSONArrayA);
            }
            JSONArray jSONArrayF = f(context);
            if (!m.a(jSONArrayF)) {
                jSONObject.put("wflist", jSONArrayF);
            }
            JSONObject jSONObjectD = d(context);
            if (!m.a(jSONObjectD)) {
                jSONObject.put("cgps", jSONObjectD);
            } else {
                JSONObject jSONObjectB = b(context);
                if (!m.a(jSONObjectB)) {
                    jSONObject.put("lasgps", jSONObjectB);
                }
            }
            if (m.a(jSONObject)) {
                com.tencent.android.tpush.a.a.i("TLocationManager", "No location info, return..");
            } else {
                jSONObject.put("fun", "xg");
                com.tencent.android.tpush.common.g.a().a(new j(context, jSONObject));
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("TLocationManager", Constants.MAIN_VERSION_TAG, th);
        }
    }
}
