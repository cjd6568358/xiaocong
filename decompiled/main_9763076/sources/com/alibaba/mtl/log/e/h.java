package com.alibaba.mtl.log.e;

import android.text.TextUtils;
import com.alibaba.mtl.log.model.LogField;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: LogAssemble.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    public static String a(Map<String, String> map) {
        String str;
        StringBuilder sb = new StringBuilder();
        for (LogField logField : LogField.values()) {
            if (logField == LogField.ARGS) {
                break;
            }
            if (map.containsKey(logField.toString())) {
                str = map.get(logField.toString()) + Constants.MAIN_VERSION_TAG;
                map.remove(logField.toString());
            } else {
                str = null;
            }
            sb.append(d(str)).append("||");
        }
        boolean z = true;
        if (map.containsKey(LogField.ARGS.toString())) {
            sb.append(d(map.get(LogField.ARGS.toString()) + Constants.MAIN_VERSION_TAG));
            map.remove(LogField.ARGS.toString());
            z = false;
        }
        Iterator<String> it = map.keySet().iterator();
        while (true) {
            boolean z2 = z;
            if (!it.hasNext()) {
                break;
            }
            String next = it.next();
            String str2 = map.containsKey(next) ? map.get(next) + Constants.MAIN_VERSION_TAG : null;
            if (z2) {
                if ("StackTrace".equals(next)) {
                    sb.append("StackTrace=====>").append(str2);
                } else {
                    sb.append(d(next)).append("=").append(str2);
                }
                z = false;
            } else if ("StackTrace".equals(next)) {
                sb.append(",").append("StackTrace=====>").append(str2);
                z = z2;
            } else {
                sb.append(",").append(d(next)).append("=").append(str2);
                z = z2;
            }
        }
        String string = sb.toString();
        if (!TextUtils.isEmpty(string) && string.endsWith("||")) {
            return string + "-";
        }
        return string;
    }

    public static String b(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        m25a(map);
        return a(map);
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static Map<String, String> m25a(Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        try {
            String strM = b.m();
            if (!TextUtils.isEmpty(strM) && !map.containsKey(LogField.USERNICK.toString())) {
                map.put(LogField.USERNICK.toString(), strM);
            }
            String strJ = b.j();
            if (!TextUtils.isEmpty(strJ) && !map.containsKey(LogField.LL_USERNICK.toString())) {
                map.put(LogField.LL_USERNICK.toString(), strJ);
            }
            String strN = b.n();
            if (!TextUtils.isEmpty(strN) && !map.containsKey(LogField.USERID.toString())) {
                map.put(LogField.USERID.toString(), strN);
            }
            String strK = b.k();
            if (!TextUtils.isEmpty(strK) && !map.containsKey(LogField.LL_USERID.toString())) {
                map.put(LogField.LL_USERID.toString(), strK);
            }
            String strValueOf = String.valueOf(System.currentTimeMillis());
            if (!map.containsKey(LogField.RECORD_TIMESTAMP.toString())) {
                map.put(LogField.RECORD_TIMESTAMP.toString(), strValueOf);
            }
            if (!map.containsKey(LogField.START_SESSION_TIMESTAMP.toString())) {
                map.put(LogField.START_SESSION_TIMESTAMP.toString(), String.valueOf(com.alibaba.mtl.log.a.B));
            }
            Map<String, String> mapA = d.a(com.alibaba.mtl.log.a.getContext());
            if (mapA != null) {
                for (String str : mapA.keySet()) {
                    String str2 = mapA.get(str);
                    if (!TextUtils.isEmpty(str2) && !map.containsKey(str) && !map.containsKey(str)) {
                        map.put(str, str2);
                    }
                }
            }
            String strC = c(map);
            if (!TextUtils.isEmpty(strC) && !map.containsKey(LogField.RESERVES.toString())) {
                map.put(LogField.RESERVES.toString(), strC);
            }
        } catch (Throwable th) {
        }
        return map;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0098  */
    private static String c(Map<String, String> map) {
        String str;
        String wifiAddress = l.getWifiAddress(com.alibaba.mtl.log.a.getContext());
        String str2 = wifiAddress != null ? "_ap=1" + String.format("%s=%s", "_mac", wifiAddress) : "_ap=1";
        if (d.i()) {
            String strQ = d.q();
            if (TextUtils.isEmpty(strQ)) {
                str = str2;
            } else {
                str = str2 + ",_did=" + strQ;
            }
        } else {
            str = str2;
        }
        String str3 = map.get(LogField.APPKEY.toString());
        if (!TextUtils.isEmpty(b.getAppkey()) && !TextUtils.isEmpty(str3) && !b.getAppkey().equalsIgnoreCase(str3)) {
            return str + ",_mak=" + b.getAppkey();
        }
        return str;
    }

    private static String d(String str) {
        if (TextUtils.isEmpty(str)) {
            return "-";
        }
        return str;
    }

    public static String a(String str, String str2, String str3, String str4, String str5, Map<String, String> map, String str6, String str7) {
        HashMap map2 = new HashMap();
        if (map != null) {
            map2.putAll(map);
        }
        if (!TextUtils.isEmpty(str)) {
            map2.put(LogField.PAGE.toString(), str);
        }
        map2.put(LogField.EVENTID.toString(), str2);
        if (!TextUtils.isEmpty(str3)) {
            map2.put(LogField.ARG1.toString(), str3);
        }
        if (!TextUtils.isEmpty(str4)) {
            map2.put(LogField.ARG2.toString(), str4);
        }
        if (!TextUtils.isEmpty(str5)) {
            map2.put(LogField.ARG3.toString(), str5);
        }
        if (!TextUtils.isEmpty(str7)) {
            map2.put(LogField.RECORD_TIMESTAMP.toString(), str7);
        }
        if (!TextUtils.isEmpty(str6)) {
            map2.put(LogField.RESERVE3.toString(), str6);
        }
        return b(map2);
    }
}
