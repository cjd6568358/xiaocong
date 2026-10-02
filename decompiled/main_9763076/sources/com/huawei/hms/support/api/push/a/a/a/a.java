package com.huawei.hms.support.api.push.a.a.a;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.tencent.android.tpush.common.Constants;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: BaseUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static JSONArray a(String str) {
        if (TextUtils.isEmpty(str)) {
            if (!com.huawei.hms.support.log.a.a()) {
                return null;
            }
            com.huawei.hms.support.log.a.a("BaseUtil", "jsonString is null");
            return null;
        }
        try {
            return new JSONArray(str);
        } catch (JSONException e) {
            if (!com.huawei.hms.support.log.a.a()) {
                return null;
            }
            com.huawei.hms.support.log.a.a("BaseUtil", "cast jsonString to jsonArray error");
            return null;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return Constants.MAIN_VERSION_TAG;
        }
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            cArr[i * 2] = a[(b & 240) >> 4];
            cArr[(i * 2) + 1] = a[b & 15];
        }
        return new String(cArr);
    }

    public static byte[] b(String str) {
        byte[] bArr = new byte[str.length() / 2];
        try {
            byte[] bytes = str.getBytes(HTTP.UTF_8);
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = (byte) (((byte) (Byte.decode("0x" + new String(new byte[]{bytes[i * 2]}, HTTP.UTF_8)).byteValue() << 4)) ^ Byte.decode("0x" + new String(new byte[]{bytes[(i * 2) + 1]}, HTTP.UTF_8)).byteValue());
            }
        } catch (UnsupportedEncodingException e) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("BaseUtil", "hexString2ByteArray error" + e.getMessage());
            }
        }
        return bArr;
    }

    public static JSONArray a(List<String> list, Context context) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        c cVar = new c(context, "tags_info");
        for (String str : list) {
            if (cVar.c(str)) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("tagKey", str);
                jSONObject.put("opType", 2);
                if (jSONObject.length() > 0) {
                    jSONArray.put(jSONObject);
                }
            } else if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("BaseUtil", str + " not exist, need not to remove");
            }
        }
        return jSONArray;
    }

    public static void a(ApiClient apiClient, String str) {
        if (apiClient != null) {
            HashMap map = new HashMap();
            map.put("package", apiClient.getPackageName());
            map.put("sdk_ver", String.valueOf(20502300));
            String appID = null;
            SubAppInfo subAppInfo = apiClient.getSubAppInfo();
            if (subAppInfo != null) {
                appID = subAppInfo.getSubAppID();
            }
            if (appID == null) {
                appID = apiClient.getAppID();
            }
            map.put("app_id", appID);
            String[] strArrSplit = str.split("\\.");
            if (strArrSplit.length == 2) {
                map.put("service", strArrSplit[0]);
                map.put("api_name", strArrSplit[1]);
            }
            map.put("result", String.valueOf(0));
            map.put("cost_time", String.valueOf(0));
            com.huawei.hms.support.b.a.a().a(apiClient.getContext(), "HMS_SDK_API_CALL", map);
        }
    }
}
