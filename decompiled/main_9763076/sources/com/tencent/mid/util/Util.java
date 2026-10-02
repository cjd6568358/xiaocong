package com.tencent.mid.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import com.tencent.android.tpush.XGPushProvider;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.api.MidConstants;
import com.tencent.mid.api.MidEntity;
import com.tencent.mid.api.MidProvider;
import com.tencent.mid.api.MidService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.HttpHost;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Util {
    private static f a = null;
    public static int errorCount = 0;
    private static Random b = null;
    public static Map<String, MidEntity> lastOtherMidMap = null;
    public static long lastGetOtherMidMapTime = 0;

    private static synchronized Random a() {
        if (b == null) {
            b = new Random();
        }
        return b;
    }

    private static JSONObject a(MidEntity midEntity) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("mid", midEntity.getMid());
        jSONObject.put("ts", midEntity.getTimestamps() / 1000);
        return jSONObject;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<String, MidEntity> b(Context context, int i) {
        MidEntity midEntity;
        HashMap map = new HashMap(4);
        Map<String, ProviderInfo> mapQueryMatchContentProviders = queryMatchContentProviders(context);
        Log.i("MID", ">>>   queryMatchContentProviders size:" + (mapQueryMatchContentProviders != null ? mapQueryMatchContentProviders.size() : 0));
        MidEntity midEntityC = null;
        if (i == 2) {
            midEntityC = com.tencent.mid.b.g.a(context).i();
        } else if (i == 3) {
            midEntityC = com.tencent.mid.b.g.a(context).c();
        }
        if (isMidValid(midEntityC)) {
            map.put(context.getPackageName(), midEntityC);
        }
        if (mapQueryMatchContentProviders == null || mapQueryMatchContentProviders.size() == 0) {
            return map;
        }
        if (lastOtherMidMap != null && !lastOtherMidMap.isEmpty() && Math.abs(System.currentTimeMillis() - lastGetOtherMidMapTime) < 1000) {
            Log.d("MID", ">>> use lastOtherMidMap size:" + lastOtherMidMap.size() + ",content:");
            return lastOtherMidMap;
        }
        for (String str : mapQueryMatchContentProviders.keySet()) {
            try {
                if (!str.equals(context.getPackageName())) {
                    String str2 = getPackageAuth(str) + "/" + i;
                    Log.d("MID", ">>>   read mid from other providrt cmd:" + str2);
                    String type = context.getContentResolver().getType(Uri.parse(str2));
                    Log.d("MID", ">>>   mid cmd:" + str2 + ", return:" + type);
                    if (!isEmpty(type) && (midEntity = MidEntity.parse(type)) != null && midEntity.isMidValid()) {
                        map.put(str, midEntity);
                    }
                }
            } catch (Throwable th) {
                a.f(th);
            }
        }
        lastOtherMidMap = map;
        lastGetOtherMidMapTime = System.currentTimeMillis();
        Log.d("MID", ">>>   appPrivateMidMap size:" + map.size() + ",content:");
        for (Map.Entry entry : map.entrySet()) {
            Log.w("MID", ">>>   pkg:" + ((String) entry.getKey()) + ",midEntity:" + ((MidEntity) entry.getValue()).toString());
        }
        return map;
    }

    public static String bytesToStr(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            stringBuffer.append(((int) b2) + Constants.MAIN_VERSION_TAG);
        }
        return stringBuffer.toString();
    }

    public static boolean checkPermission(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            Log.e("MID", "checkPermission error", th);
            return false;
        }
    }

    public static String decode(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(g.b(Base64.decode(str.getBytes(HTTP.UTF_8), 0)), HTTP.UTF_8).trim().replace("\t", Constants.MAIN_VERSION_TAG).replace("\n", Constants.MAIN_VERSION_TAG).replace("\r", Constants.MAIN_VERSION_TAG);
        } catch (Throwable th) {
            Log.e("MID", "decode error", th);
            return str;
        }
    }

    public static byte[] deocdeGZipContent(byte[] bArr) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length * 2);
        while (true) {
            int i = gZIPInputStream.read(bArr2);
            if (i == -1) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayInputStream.close();
                gZIPInputStream.close();
                byteArrayOutputStream.close();
                return byteArray;
            }
            byteArrayOutputStream.write(bArr2, 0, i);
        }
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(Base64.encode(g.a(str.getBytes(HTTP.UTF_8)), 0), HTTP.UTF_8).trim().replace("\t", Constants.MAIN_VERSION_TAG).replace("\n", Constants.MAIN_VERSION_TAG).replace("\r", Constants.MAIN_VERSION_TAG);
        } catch (Throwable th) {
            Log.e("MID", "encode error", th);
            return str;
        }
    }

    public static boolean equal(MidEntity midEntity, MidEntity midEntity2) {
        if (midEntity == null || midEntity2 == null) {
            return midEntity == null && midEntity2 == null;
        }
        return midEntity.compairTo(midEntity2) == 0;
    }

    public static String getDateString(int i) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.roll(6, i);
            return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        } catch (Throwable th) {
            return "00";
        }
    }

    public static byte[] getHMAC(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "hmacmd5");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            mac.update(str2.getBytes());
            return mac.doFinal();
        } catch (Exception e) {
            a.b(e);
            return null;
        }
    }

    public static String getHttpAddr(Context context) {
        return "http://" + b.a(context).c();
    }

    public static HttpHost getHttpProxy() {
        if (Proxy.getDefaultHost() != null) {
            return new HttpHost(Proxy.getDefaultHost(), Proxy.getDefaultPort());
        }
        return null;
    }

    public static HttpHost getHttpProxy(Context context) {
        NetworkInfo activeNetworkInfo;
        String extraInfo;
        HttpHost httpHost;
        if (context == null) {
            return null;
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0 || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
                httpHost = null;
            } else if ((activeNetworkInfo.getTypeName() != null && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI")) || (extraInfo = activeNetworkInfo.getExtraInfo()) == null) {
                httpHost = null;
            } else {
                if (!extraInfo.equals("cmwap") && !extraInfo.equals("3gwap") && !extraInfo.equals("uniwap")) {
                    if (extraInfo.equals("ctwap")) {
                        httpHost = new HttpHost("10.0.0.200", 80);
                    }
                    return null;
                }
                httpHost = new HttpHost("10.0.0.172", 80);
            }
            return httpHost;
        } catch (Throwable th) {
            a.f(th);
        }
    }

    public static String getImei(Context context) {
        String deviceId;
        try {
            if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                if (deviceId != null) {
                    return deviceId;
                }
            } else {
                a.d("Could not get permission of android.permission.READ_PHONE_STATE");
                deviceId = Constants.MAIN_VERSION_TAG;
            }
        } catch (Throwable th) {
            a.d("get device id error:" + th.toString());
        }
        return deviceId == null ? Constants.MAIN_VERSION_TAG : deviceId;
    }

    public static String getImsi(Context context) {
        String str;
        try {
            if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                String subscriberId = telephonyManager.getSubscriberId();
                if (telephonyManager != null) {
                    return subscriberId;
                }
                str = subscriberId;
            } else {
                a.d("Could not get permission of android.permission.READ_PHONE_STATE");
                str = Constants.MAIN_VERSION_TAG;
            }
        } catch (Throwable th) {
            a.d("get subscriber id error:" + th.toString());
        }
        if (str == null) {
            str = Constants.MAIN_VERSION_TAG;
        }
        return str;
    }

    public static Map<String, ProviderInfo> getLocalXGAppList(Context context) {
        HashMap map = new HashMap();
        for (ProviderInfo providerInfo : context.getPackageManager().queryContentProviders((String) null, 0, 0)) {
            if (providerInfo.name.equals("com.tencent.android.tpush.XGPushProvider") && providerInfo.authority.equals(getProviderAuth(providerInfo.packageName))) {
                map.put(providerInfo.packageName, providerInfo);
                Log.d("MID.XG", providerInfo.authority + "," + providerInfo.packageName + "," + providerInfo.name);
            }
        }
        return map;
    }

    public static synchronized f getLogger() {
        if (a == null) {
            a = new f("MID");
        }
        return a;
    }

    public static Map<String, MidEntity> getMidsByApps(Context context, int i) {
        p pVar = new p(context, i);
        Thread thread = new Thread(pVar);
        thread.start();
        try {
            thread.join(3500L);
        } catch (Throwable th) {
            a.d(th.toString());
        }
        return pVar.a();
    }

    public static MidEntity getNewerMidEntity(MidEntity midEntity, MidEntity midEntity2) {
        if (midEntity != null && midEntity2 != null) {
            return midEntity.compairTo(midEntity2) >= 0 ? midEntity : midEntity2;
        }
        if (midEntity != null) {
            return midEntity;
        }
        if (midEntity2 != null) {
            return midEntity2;
        }
        return null;
    }

    public static String getPackageAuth(String str) {
        return "content://" + getPackageAuthName(str);
    }

    public static String getPackageAuthName(String str) {
        return str + MidConstants.PROVIDER_AUTH_SUFFIX;
    }

    public static String getProviderAuth(String str) {
        return str + XGPushProvider.AUTH_PRIX;
    }

    public static int getRandInt() {
        return a().nextInt(Integer.MAX_VALUE);
    }

    public static JSONArray getSensors(Context context) {
        List<Sensor> sensorList;
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager != null && (sensorList = sensorManager.getSensorList(-1)) != null && sensorList.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= sensorList.size()) {
                        return jSONArray;
                    }
                    Sensor sensor = sensorList.get(i2);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(RNMessageModule.NAME, sensor.getName());
                    jSONObject.put("vendor", sensor.getVendor());
                    jSONArray.put(jSONObject);
                    i = i2 + 1;
                }
            }
        } catch (Throwable th) {
            a.d(th.toString());
        }
        return null;
    }

    public static String getToken(Context context, String str) {
        String str2;
        String type = context.getContentResolver().getType(Uri.parse("content://" + str + XGPushProvider.AUTH_PRIX + "/tokenByMid"));
        if (type != null) {
            try {
                str2 = new String(Base64.decode(type.getBytes(), 0), HTTP.UTF_8);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                str2 = type;
            }
        } else {
            str2 = type;
        }
        Log.i("MID.XG", "get token from pkg:" + str + ", token:" + str2);
        if (str2 == null || str2.trim().length() != 40) {
            return null;
        }
        return str2;
    }

    public static String getWiFiBBSID(Context context) {
        try {
            WifiInfo wifiInfo = getWifiInfo(context);
            if (wifiInfo != null) {
                return wifiInfo.getBSSID();
            }
        } catch (Throwable th) {
            a.d(th.toString());
        }
        return null;
    }

    public static String getWiFiSSID(Context context) {
        try {
            WifiInfo wifiInfo = getWifiInfo(context);
            if (wifiInfo != null) {
                return wifiInfo.getSSID();
            }
        } catch (Throwable th) {
            a.d(th.toString());
        }
        return null;
    }

    public static WifiInfo getWifiInfo(Context context) {
        WifiManager wifiManager;
        if (!checkPermission(context, "android.permission.ACCESS_WIFI_STATE") || (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) == null) {
            return null;
        }
        return wifiManager.getConnectionInfo();
    }

    public static String getWifiMacAddress(Context context) {
        String macAddress = Constants.MAIN_VERSION_TAG;
        if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                if (wifiManager == null) {
                    return Constants.MAIN_VERSION_TAG;
                }
                macAddress = wifiManager.getConnectionInfo().getMacAddress();
            } catch (Exception e) {
                a.d("get wifi address error" + e);
                return Constants.MAIN_VERSION_TAG;
            }
        } else {
            a.d("Could not get permission of android.permission.ACCESS_WIFI_STATE");
        }
        return macAddress == null ? Constants.MAIN_VERSION_TAG : macAddress;
    }

    public static JSONArray getWifiTopN(Context context, int i) {
        List<ScanResult> scanResults;
        try {
            if (!MidService.isEnableReportWifiList()) {
                return null;
            }
            if (checkPermission(context, "android.permission.INTERNET") && checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
                WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
                if (wifiManager != null && (scanResults = wifiManager.getScanResults()) != null && scanResults.size() > 0) {
                    Collections.sort(scanResults, new o());
                    JSONArray jSONArray = new JSONArray();
                    for (int i2 = 0; i2 < scanResults.size() && i2 < i; i2++) {
                        ScanResult scanResult = scanResults.get(i2);
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put(NotificationStyle.BASE_STYLE, scanResult.BSSID);
                        jSONObject.put("ss", scanResult.SSID);
                        jSONArray.put(jSONObject);
                    }
                    return jSONArray;
                }
            } else {
                a.d("can not get the permisson of android.permission.INTERNET");
            }
            return null;
        } catch (Throwable th) {
            a.d(th.toString());
        }
    }

    public static void insertMid2OldProvider(Context context, String str, String str2) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("mid", str2);
            context.getContentResolver().insert(Uri.parse(getPackageAuth(str) + "/11"), contentValues);
        } catch (Throwable th) {
        }
    }

    public static void insertMid2Provider(Context context, String str, String str2) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("mid", str2);
            context.getContentResolver().insert(Uri.parse(getPackageAuth(str) + "/10"), contentValues);
        } catch (Throwable th) {
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isMidValid(MidEntity midEntity) {
        return midEntity != null && isMidValid(midEntity.getMid());
    }

    public static boolean isMidValid(String str) {
        return str != null && str.trim().length() >= 40;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo activeNetworkInfo;
        try {
            if (!checkPermission(context, "android.permission.INTERNET")) {
                return false;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                if (activeNetworkInfo.isConnectedOrConnecting()) {
                    return true;
                }
                Log.w("MID", "Network error is not exist");
                return false;
            }
        } catch (Throwable th) {
            Log.e("MID", "isNetworkAvailable error", th);
        }
        errorCount++;
        if (errorCount <= 5) {
            return true;
        }
        if (errorCount < 10) {
            return false;
        }
        if (errorCount >= 10) {
            errorCount = 0;
        }
        return false;
    }

    public static boolean isStringValid(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static boolean isWifiNet(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetworkInfo == null || activeNetworkInfo.getTypeName() == null || !activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI")) ? false : true;
    }

    public static void jsonPut(JSONObject jSONObject, String str, String str2) throws JSONException {
        if (isStringValid(str2)) {
            jSONObject.put(str, str2);
        }
    }

    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes(HTTP.UTF_8));
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b2 : bArrDigest) {
                stringBuffer.append((int) b2);
            }
            return stringBuffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return str;
        }
    }

    public static Map<String, Integer> queryAllToken(Context context) {
        Map<String, ProviderInfo> localXGAppList = getLocalXGAppList(context);
        HashMap map = new HashMap();
        if (localXGAppList == null || localXGAppList.size() == 0) {
            return map;
        }
        Iterator<String> it = localXGAppList.keySet().iterator();
        while (it.hasNext()) {
            String token = getToken(context, it.next());
            if (isMidValid(token)) {
                Integer num = (Integer) map.get(token);
                if (num == null) {
                    map.put(token, 1);
                } else {
                    map.put(token, Integer.valueOf(num.intValue() + 1));
                }
            }
        }
        return map;
    }

    public static Map<String, ProviderInfo> queryMatchContentProviders(Context context) {
        HashMap map = new HashMap();
        for (ProviderInfo providerInfo : context.getPackageManager().queryContentProviders((String) null, 0, 0)) {
            if (providerInfo.name.equals(MidProvider.class.getName()) && providerInfo.authority.equals(getPackageAuthName(providerInfo.packageName))) {
                map.put(providerInfo.packageName, providerInfo);
            }
        }
        return map;
    }

    public static JSONArray queryMids(Context context, int i) {
        a.h("queryMids, midType=" + i);
        JSONArray jSONArray = new JSONArray();
        Map<String, MidEntity> midsByApps = getMidsByApps(context, i == 2 ? 3 : 2);
        if (midsByApps != null && midsByApps.size() > 0) {
            for (Map.Entry<String, MidEntity> entry : midsByApps.entrySet()) {
                String key = entry.getKey();
                MidEntity value = entry.getValue();
                if (value != null && value.isMidValid()) {
                    try {
                        JSONObject jSONObjectA = a(value);
                        jSONObjectA.put("loc", "priv");
                        if (key.equals(context.getPackageName())) {
                            jSONObjectA.put(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, 1);
                        }
                        jSONObjectA.put("pkg", key);
                        jSONArray.put(jSONObjectA);
                    } catch (Exception e) {
                    }
                }
            }
        }
        MidEntity midEntityD = i == 2 ? com.tencent.mid.b.g.a(context).d() : com.tencent.mid.b.g.a(context).j();
        a.b("settingEntity:" + midEntityD);
        if (midEntityD != null && midEntityD.isMidValid()) {
            try {
                JSONObject jSONObjectA2 = a(midEntityD);
                jSONObjectA2.put("loc", "pub");
                jSONObjectA2.put("lc", "set");
                jSONArray.put(jSONObjectA2);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        MidEntity midEntityE = i == 2 ? com.tencent.mid.b.g.a(context).e() : com.tencent.mid.b.g.a(context).k();
        a.b("sdCardEntity:" + midEntityE);
        if (midEntityE != null && midEntityE.isMidValid()) {
            try {
                JSONObject jSONObjectA3 = a(midEntityE);
                jSONObjectA3.put("loc", "pub");
                jSONObjectA3.put("lc", "sd");
                jSONArray.put(jSONObjectA3);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        return jSONArray;
    }

    public static String selectMaxCountXgAppToken(Context context) {
        String key;
        int iIntValue;
        String str = null;
        Map<String, Integer> mapQueryAllToken = queryAllToken(context);
        if (mapQueryAllToken != null && mapQueryAllToken.size() > 0) {
            int i = 0;
            for (Map.Entry<String, Integer> entry : mapQueryAllToken.entrySet()) {
                if (entry.getValue().intValue() > i) {
                    iIntValue = entry.getValue().intValue();
                    key = entry.getKey();
                } else {
                    key = str;
                    iIntValue = i;
                }
                str = key;
                i = iIntValue;
            }
        }
        return str;
    }

    public static byte[] strToBytes(String str) {
        byte[] bArr = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        return bArr;
    }

    public static void updateIfLocalInvalid(Context context, String str) {
        if (isMidValid(str)) {
            MidEntity midEntity = new MidEntity();
            midEntity.setImei(getImei(context));
            midEntity.setMac(getWifiMacAddress(context));
            midEntity.setMid(str);
            midEntity.setTimestamps(System.currentTimeMillis());
            com.tencent.mid.b.g.a(context).f(midEntity);
        }
    }
}
