package com.baidu.mobstat;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Process;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import bsh.ParserConstants;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class de {
    private static String a = null;
    private static String b = null;
    private static String c = null;
    private static final Pattern d = Pattern.compile("\\s*|\t|\r|\n");

    public static String a(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), ParserConstants.LSHIFTASSIGN);
            if (applicationInfo == null) {
                return Constants.MAIN_VERSION_TAG;
            }
            Object obj = null;
            if (applicationInfo.metaData != null) {
                obj = applicationInfo.metaData.get(str);
            }
            if (obj == null) {
                db.a("null,can't find information for key:" + str);
                return Constants.MAIN_VERSION_TAG;
            }
            return obj.toString();
        } catch (Exception e) {
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String a(int i, Context context) {
        try {
            return ct.c(i, a(context).getBytes());
        } catch (Exception e) {
            db.a(e);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String a(Context context) {
        return d.matcher(dg.a(context)).replaceAll(Constants.MAIN_VERSION_TAG);
    }

    public static int b(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            displayMetrics = d(context);
        } catch (Exception e) {
            db.a(e);
        }
        return displayMetrics.widthPixels;
    }

    public static int c(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            displayMetrics = d(context);
        } catch (Exception e) {
            db.a(e);
        }
        return displayMetrics.heightPixels;
    }

    public static DisplayMetrics d(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int e(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            db.b("Get app version code exception");
            return 1;
        }
    }

    public static String f(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            db.b("get app version name exception");
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String g(Context context) {
        String str = String.format("%s_%s_%s", 0, 0, 0);
        try {
            if (cu.e(context, "android.permission.ACCESS_FINE_LOCATION") || cu.e(context, "android.permission.ACCESS_COARSE_LOCATION")) {
                CellLocation cellLocation = ((TelephonyManager) context.getSystemService("phone")).getCellLocation();
                db.a(cellLocation + Constants.MAIN_VERSION_TAG);
                if (cellLocation == null) {
                    return str;
                }
                if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                    return String.format("%s_%s_%s", String.format("%d", Integer.valueOf(gsmCellLocation.getCid())), String.format("%d", Integer.valueOf(gsmCellLocation.getLac())), 0);
                }
                String[] strArrSplit = cellLocation.toString().replace("[", Constants.MAIN_VERSION_TAG).replace("]", Constants.MAIN_VERSION_TAG).split(",");
                return String.format("%s_%s_%s", strArrSplit[0], strArrSplit[3], strArrSplit[4]);
            }
        } catch (Exception e) {
            db.a("Get Location", e);
        }
        return str;
    }

    public static String h(Context context) {
        try {
            if (cu.e(context, "android.permission.ACCESS_FINE_LOCATION")) {
                Location lastKnownLocation = ((LocationManager) context.getSystemService("location")).getLastKnownLocation("gps");
                db.b("location: " + lastKnownLocation);
                if (lastKnownLocation != null) {
                    return String.format("%s_%s_%s", Long.valueOf(lastKnownLocation.getTime()), Double.valueOf(lastKnownLocation.getLongitude()), Double.valueOf(lastKnownLocation.getLatitude()));
                }
            }
        } catch (Exception e) {
            db.b(e);
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static String b(int i, Context context) {
        String strJ = j(context);
        return TextUtils.isEmpty(strJ) ? Constants.MAIN_VERSION_TAG : ct.c(i, strJ.getBytes());
    }

    public static String i(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return j(context);
        }
        return c();
    }

    public static String j(Context context) {
        try {
            if (cu.e(context, "android.permission.ACCESS_WIFI_STATE")) {
                WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                if (connectionInfo != null) {
                    String macAddress = connectionInfo.getMacAddress();
                    if (!TextUtils.isEmpty(macAddress)) {
                        return macAddress;
                    }
                }
            } else {
                db.c("You need the android.Manifest.permission.ACCESS_WIFI_STATE permission. Open AndroidManifest.xml and just before the final </manifest> tag add: android.permission.ACCESS_WIFI_STATE");
            }
        } catch (Exception e) {
            db.b(e);
        }
        return Constants.MAIN_VERSION_TAG;
    }

    @TargetApi(9)
    private static String c() {
        if (Build.VERSION.SDK_INT < 9) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return Constants.MAIN_VERSION_TAG;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (byte b2 : hardwareAddress) {
                        sb.append(String.format("%02x:", Byte.valueOf(b2)));
                    }
                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    return sb.toString();
                }
            }
        } catch (Throwable th) {
            db.b(th);
        }
        return Constants.MAIN_VERSION_TAG;
    }

    private static String a(byte b2) {
        String str = "00" + Integer.toHexString(b2) + ":";
        return str.substring(str.length() - 3);
    }

    public static String c(int i, Context context) {
        String strD = d(i, context);
        String strC = null;
        if (!TextUtils.isEmpty(strD)) {
            strC = ct.c(i, strD.getBytes());
        }
        if (TextUtils.isEmpty(strC)) {
            return Constants.MAIN_VERSION_TAG;
        }
        return strC;
    }

    public static String d(int i, Context context) throws Throwable {
        String strA = a();
        if (TextUtils.isEmpty(strA)) {
            strA = e(i, context);
        }
        if (TextUtils.isEmpty(strA)) {
            return Constants.MAIN_VERSION_TAG;
        }
        return strA;
    }

    @SuppressLint({"NewApi"})
    public static String e(int i, Context context) {
        byte[] hardwareAddress;
        byte[] hardwareAddress2 = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterfaceNextElement = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterfaceNextElement.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isAnyLocalAddress() && (inetAddressNextElement instanceof Inet4Address) && !inetAddressNextElement.isLoopbackAddress()) {
                        if (inetAddressNextElement.isSiteLocalAddress()) {
                            hardwareAddress = networkInterfaceNextElement.getHardwareAddress();
                        } else {
                            if (!inetAddressNextElement.isLinkLocalAddress()) {
                                hardwareAddress2 = networkInterfaceNextElement.getHardwareAddress();
                                break;
                            }
                            hardwareAddress = hardwareAddress2;
                        }
                        hardwareAddress2 = hardwareAddress;
                    }
                }
            }
        } catch (Exception e) {
            db.a(e);
        }
        if (hardwareAddress2 != null) {
            for (byte b2 : hardwareAddress2) {
                stringBuffer.append(a(b2));
            }
            return stringBuffer.substring(0, stringBuffer.length() - 1).replaceAll(":", Constants.MAIN_VERSION_TAG);
        }
        String strB = b(i, context);
        if (strB != null) {
            return strB.replaceAll(":", Constants.MAIN_VERSION_TAG);
        }
        return strB;
    }

    /* JADX WARN: Code duplicated, block: B:47:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static String a() throws Throwable {
        InputStreamReader inputStreamReader;
        Throwable th;
        String strReplaceAll = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            char[] cArr = new char[20];
            inputStreamReader = new InputStreamReader(new FileInputStream("/sys/class/net/eth0/address"));
            while (true) {
                try {
                    try {
                        int i = inputStreamReader.read(cArr);
                        if (i == -1) {
                            break;
                        }
                        if (i != cArr.length || cArr[cArr.length - 1] == '\r') {
                            for (int i2 = 0; i2 < i; i2++) {
                                if (cArr[i2] != '\r') {
                                    stringBuffer.append(cArr[i2]);
                                }
                            }
                        } else {
                            System.out.print(cArr);
                        }
                    } catch (Exception e) {
                        e = e;
                        db.a(e);
                        if (inputStreamReader != null) {
                            try {
                                inputStreamReader.close();
                            } catch (IOException e2) {
                                db.a(e2);
                            }
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e3) {
                            db.a(e3);
                        }
                    }
                    throw th;
                }
            }
            strReplaceAll = stringBuffer.toString().trim().replaceAll(":", Constants.MAIN_VERSION_TAG);
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e4) {
                    db.a(e4);
                }
            }
        } catch (Exception e5) {
            e = e5;
            inputStreamReader = null;
        } catch (Throwable th3) {
            inputStreamReader = null;
            th = th3;
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            throw th;
        }
        return strReplaceAll;
    }

    public static String a(Context context, int i) {
        String strU = u(context);
        return TextUtils.isEmpty(strU) ? Constants.MAIN_VERSION_TAG : ct.c(i, strU.getBytes());
    }

    private static String u(Context context) {
        String name;
        try {
            BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
            return (defaultAdapter == null || (name = defaultAdapter.getName()) == null) ? Constants.MAIN_VERSION_TAG : name;
        } catch (Exception e) {
            db.b(e);
        }
    }

    public static String f(int i, Context context) {
        String strK = k(context);
        return TextUtils.isEmpty(strK) ? Constants.MAIN_VERSION_TAG : ct.c(i, strK.getBytes());
    }

    @SuppressLint({"NewApi"})
    public static String k(Context context) {
        BluetoothAdapter defaultAdapter;
        String address;
        String str = Build.BRAND;
        if ("4.1.1".equals(Build.VERSION.RELEASE) && "TCT".equals(str)) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            return (!cu.e(context, "android.permission.BLUETOOTH") || (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) == null || (address = defaultAdapter.getAddress()) == null) ? Constants.MAIN_VERSION_TAG : address;
        } catch (Exception e) {
            db.b(e);
        }
    }

    public static String l(Context context) {
        String strM = m(context);
        return TextUtils.isEmpty(strM) ? Constants.MAIN_VERSION_TAG : cs.a(strM.getBytes());
    }

    public static String m(Context context) {
        boolean zIsProviderEnabled;
        WifiInfo connectionInfo;
        List<ScanResult> scanResults;
        WifiInfo wifiInfo;
        if (context == null || !cu.e(context, "android.permission.ACCESS_WIFI_STATE")) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            zIsProviderEnabled = cu.e(context, "android.permission.ACCESS_FINE_LOCATION") ? ((LocationManager) context.getSystemService("location")).isProviderEnabled("gps") : false;
        } catch (Exception e) {
            db.a(e);
            zIsProviderEnabled = false;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            connectionInfo = wifiManager.getConnectionInfo();
            try {
                scanResults = wifiManager.getScanResults();
                wifiInfo = connectionInfo;
            } catch (Throwable th) {
                th = th;
                db.a(th);
                scanResults = null;
                wifiInfo = connectionInfo;
            }
        } catch (Throwable th2) {
            th = th2;
            connectionInfo = null;
        }
        if (scanResults != null && scanResults.size() != 0) {
            Collections.sort(scanResults, new df());
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; scanResults != null && i < scanResults.size() && i < 30; i++) {
            try {
                ScanResult scanResult = scanResults.get(i);
                StringBuilder sb = new StringBuilder();
                sb.append(scanResult.BSSID);
                sb.append("|");
                String strReplaceAll = scanResult.SSID.replaceAll("\\|", Constants.MAIN_VERSION_TAG);
                if (strReplaceAll.length() > 30) {
                    strReplaceAll = strReplaceAll.substring(0, 30);
                }
                sb.append(strReplaceAll);
                sb.append("|");
                sb.append(scanResult.level);
                sb.append("|");
                sb.append((wifiInfo == null || !scanResult.BSSID.equals(wifiInfo.getBSSID())) ? 0 : 1);
                jSONArray.put(sb.toString());
            } catch (Exception e2) {
                db.a(e2);
            }
        }
        if (jSONArray.length() == 0) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(System.currentTimeMillis());
            sb2.append("|");
            sb2.append(zIsProviderEnabled ? 1 : 0);
            sb2.append("|");
            sb2.append(h(context));
            jSONObject.put("ap-list", jSONArray);
            jSONObject.put("meta-data", sb2.toString());
            return jSONObject.toString();
        } catch (Exception e3) {
            db.a(e3);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static boolean n(Context context) {
        if (context == null) {
            return false;
        }
        try {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1);
            return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
        } catch (Exception e) {
            db.a(e);
            return false;
        }
    }

    public static String o(Context context) {
        String typeName;
        Exception e;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return Constants.MAIN_VERSION_TAG;
            }
            typeName = activeNetworkInfo.getTypeName();
            try {
                if (!typeName.equals("WIFI") && activeNetworkInfo.getSubtypeName() != null) {
                    return activeNetworkInfo.getSubtypeName();
                }
                return typeName;
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            typeName = Constants.MAIN_VERSION_TAG;
            e = e3;
        }
        db.a(e);
        return typeName;
    }

    public static String p(Context context) {
        return context != null ? context.getPackageName() : Constants.MAIN_VERSION_TAG;
    }

    public static String h(int i, Context context) {
        String strP = p(context);
        if (!TextUtils.isEmpty(strP)) {
            try {
                return ct.c(i, strP.getBytes());
            } catch (Exception e) {
                db.b(e);
            }
        }
        return Constants.MAIN_VERSION_TAG;
    }

    private static String v(Context context) {
        String str;
        String str2 = a;
        if (str2 != null) {
            return str2;
        }
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
            int i = 0;
            while (true) {
                if (runningAppProcesses != null && i < runningAppProcesses.size()) {
                    ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i);
                    if (runningAppProcessInfo == null || runningAppProcessInfo.pid != Process.myPid()) {
                        i++;
                    } else {
                        str = runningAppProcessInfo.processName;
                        break;
                    }
                    str = str2;
                    break;
                }
                str = str2;
                break;
            }
        } catch (Exception e) {
            db.b(e);
        }
        if (str == null) {
            str = Constants.MAIN_VERSION_TAG;
        }
        a = str;
        return str;
    }

    private static String b(Context context, String str) {
        int iLastIndexOf;
        if (str != null && (iLastIndexOf = str.lastIndexOf(58)) > 0 && iLastIndexOf + 1 < str.length()) {
            return str.substring(iLastIndexOf + 1);
        }
        return null;
    }

    private static String c(Context context, String str) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return null;
        }
        String str2 = applicationInfo.processName;
        if (str2 == null || str2.equals(str)) {
            str = null;
        }
        return str;
    }

    public static String q(Context context) {
        String strB = b;
        if (strB == null) {
            String strV = v(context);
            strB = b(context, strV);
            if (TextUtils.isEmpty(strB)) {
                strB = c(context, strV);
            }
            if (strB == null) {
                strB = Constants.MAIN_VERSION_TAG;
            }
            b = strB;
        }
        return strB;
    }

    public static String r(Context context) {
        ServiceInfo[] serviceInfoArr;
        String str = Constants.MAIN_VERSION_TAG;
        String strV = v(context);
        if (strV == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (packageInfo == null || (serviceInfoArr = packageInfo.services) == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        for (ServiceInfo serviceInfo : serviceInfoArr) {
            if (strV.equals(serviceInfo.processName)) {
                str = serviceInfo.name;
                break;
            }
        }
        if (str == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        return str;
    }

    public static boolean s(Context context) {
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        } catch (Exception e) {
            db.b(e);
            return false;
        }
    }

    public static String t(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("m", memoryInfo.availMem);
            jSONObject.put(NotifyType.LIGHTS, memoryInfo.lowMemory);
            jSONObject.put("t", memoryInfo.threshold);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("app_mem", jSONArray);
            jSONObject2.put("meta-data", sb.toString());
            return cs.a(jSONObject2.toString().getBytes());
        } catch (Exception e) {
            db.a(e);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String b() {
        if (c != null) {
            return c;
        }
        String str = Constants.MAIN_VERSION_TAG;
        if (!TextUtils.isEmpty(a("ro.miui.ui.version.name"))) {
            str = "miui";
        } else if (!TextUtils.isEmpty(a("ro.build.version.opporom"))) {
            str = "coloros";
        } else if (!TextUtils.isEmpty(a("ro.build.version.emui"))) {
            str = "emui";
        } else if (!TextUtils.isEmpty(a("ro.vivo.os.version"))) {
            str = "funtouch";
        } else if (!TextUtils.isEmpty(a("ro.smartisan.version"))) {
            str = "smartisan";
        }
        if (TextUtils.isEmpty(str)) {
            String strA = a("ro.build.display.id");
            if (!TextUtils.isEmpty(strA) && strA.contains("Flyme")) {
                str = "flyme";
            }
        }
        c = str;
        return c;
    }

    private static String a(String str) throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        Process processExec;
        String line = null;
        try {
            processExec = Runtime.getRuntime().exec("getprop " + str);
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()), WXMediaMessage.DESCRIPTION_LENGTH_LIMIT);
                try {
                    line = bufferedReader.readLine();
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e) {
                        }
                    }
                    if (processExec != null) {
                        processExec.destroy();
                    }
                } catch (Exception e2) {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e3) {
                        }
                    }
                    if (processExec != null) {
                        processExec.destroy();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e4) {
                        }
                    }
                    if (processExec == null) {
                        throw th;
                    }
                    processExec.destroy();
                    throw th;
                }
            } catch (Exception e5) {
                bufferedReader = null;
            } catch (Throwable th3) {
                bufferedReader = null;
                th = th3;
            }
        } catch (Exception e6) {
            processExec = null;
            bufferedReader = null;
        } catch (Throwable th4) {
            bufferedReader = null;
            th = th4;
            processExec = null;
        }
        return line;
    }
}
