package com.alibaba.mtl.log.e;

import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: StringUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class p {
    public static String convertObjectToString(Object o) {
        if (o != null) {
            if (o instanceof String) {
                return ((String) o).toString();
            }
            if (o instanceof Integer) {
                return Constants.MAIN_VERSION_TAG + ((Integer) o).intValue();
            }
            if (o instanceof Long) {
                return Constants.MAIN_VERSION_TAG + ((Long) o).longValue();
            }
            if (o instanceof Double) {
                return Constants.MAIN_VERSION_TAG + ((Double) o).doubleValue();
            }
            if (o instanceof Float) {
                return Constants.MAIN_VERSION_TAG + ((Float) o).floatValue();
            }
            if (o instanceof Short) {
                return Constants.MAIN_VERSION_TAG + ((int) ((Short) o).shortValue());
            }
            if (o instanceof Byte) {
                return Constants.MAIN_VERSION_TAG + ((int) ((Byte) o).byteValue());
            }
            if (o instanceof Boolean) {
                return ((Boolean) o).toString();
            }
            if (o instanceof Character) {
                return ((Character) o).toString();
            }
            return o.toString();
        }
        return Constants.MAIN_VERSION_TAG;
    }

    public static Map<String, String> b(Map<String, String> map) {
        if (map != null) {
            HashMap map2 = new HashMap();
            for (String str : map.keySet()) {
                if (str instanceof String) {
                    String str2 = map.get(str);
                    if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                        try {
                            map2.put(URLEncoder.encode(str, HTTP.UTF_8), URLEncoder.encode(str2, HTTP.UTF_8));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return map2;
        }
        return map;
    }

    public static String d(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        boolean z = true;
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> it = map.keySet().iterator();
        while (true) {
            boolean z2 = z;
            if (it.hasNext()) {
                String next = it.next();
                String strConvertObjectToString = convertObjectToString(map.get(next));
                String strConvertObjectToString2 = convertObjectToString(next);
                if (strConvertObjectToString != null && strConvertObjectToString2 != null) {
                    if (z2) {
                        stringBuffer.append(strConvertObjectToString2 + "=" + strConvertObjectToString);
                        z2 = false;
                    } else {
                        stringBuffer.append(",").append(strConvertObjectToString2 + "=" + strConvertObjectToString);
                    }
                }
                z = z2;
            } else {
                return stringBuffer.toString();
            }
        }
    }
}
