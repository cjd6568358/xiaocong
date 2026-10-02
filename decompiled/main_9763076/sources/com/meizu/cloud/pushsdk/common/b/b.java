package com.meizu.cloud.pushsdk.common.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static String a = Constants.MAIN_VERSION_TAG;
    private static String b = Constants.MAIN_VERSION_TAG;

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a() {
        e.c cVarA = f.a("ro.target.product");
        if (cVarA.a && !TextUtils.isEmpty((CharSequence) cVarA.b)) {
            c.b("DeviceUtils", "current product is " + ((String) cVarA.b));
            return false;
        }
        c.b("DeviceUtils", "current product is phone");
        return true;
    }

    public static String a(Context context) {
        if (TextUtils.isEmpty(b)) {
            if (!a()) {
                if (TextUtils.isEmpty(b)) {
                    StringBuilder sb = new StringBuilder();
                    String str = Build.SERIAL;
                    c.b("DeviceUtils", "device serial " + str);
                    if (TextUtils.isEmpty(str)) {
                        return null;
                    }
                    sb.append(str);
                    String strB = b(context);
                    c.d("DeviceUtils", "mac address " + strB);
                    if (TextUtils.isEmpty(strB)) {
                        return null;
                    }
                    sb.append(strB.replace(":", Constants.MAIN_VERSION_TAG).toUpperCase());
                    b = sb.toString();
                }
            } else {
                b = h.a(context);
            }
        }
        return b;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x006f  */
    public static String b(Context context) {
        WifiInfo connectionInfo;
        String strA;
        String macAddress = null;
        if (!TextUtils.isEmpty(a)) {
            return a;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                strA = null;
            } else {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    if (activeNetworkInfo.getType() == 1) {
                        strA = a("wlan0");
                    } else if (activeNetworkInfo.getType() == 9) {
                        strA = a("eth0");
                    } else {
                        strA = null;
                    }
                } else {
                    strA = a("wlan0");
                    if (TextUtils.isEmpty(strA)) {
                        strA = a("eth0");
                    }
                }
            }
            macAddress = strA;
        } else {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager != null && (connectionInfo = wifiManager.getConnectionInfo()) != null) {
                macAddress = connectionInfo.getMacAddress();
            }
        }
        a = macAddress;
        return a;
    }

    private static String a(String str) {
        String strTrim = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("/sys/class/net/" + str + "/address");
            Scanner scanner = new Scanner(fileInputStream);
            if (scanner.hasNextLine()) {
                strTrim = scanner.nextLine().trim();
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            c.d("DeviceUtils", "getMacAddressWithIfName File not found Exception");
        } catch (IOException e2) {
            c.d("DeviceUtils", "getMacAddressWithIfName IOException");
        }
        return strTrim;
    }
}
