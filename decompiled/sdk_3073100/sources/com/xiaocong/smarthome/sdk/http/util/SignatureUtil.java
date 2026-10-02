package com.xiaocong.smarthome.sdk.http.util;

import com.xiaocong.smarthome.util.HexUtil;
import com.xiaocong.smarthome.util.MD5Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SignatureUtil {
    public static String signMD5(String xc_token, String appId, String clientId, String clientKey, long timesTamp, String mUUID, Map<String, Object> bodyParams, boolean isConfig, boolean isSign, String appkey) {
        Map<String, Object> signParams = new HashMap<>();
        if (isSign && bodyParams != null) {
            signParams.putAll(bodyParams);
        }
        signParams.put("xc-timestamp", String.valueOf(timesTamp));
        signParams.put("xc-token", xc_token);
        signParams.put("clientId", clientId);
        signParams.put("appId", appId);
        signParams.put("udid", mUUID);
        List<String> keys = new ArrayList<>(signParams.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        StringBuilder builder = new StringBuilder();
        for (String key : keys) {
            Object val = signParams.get(key) == null ? "" : signParams.get(key);
            builder.append(key + "=" + val);
            builder.append("&");
        }
        String mClient = clientKey;
        if (isConfig) {
            mClient = appkey;
        }
        builder.append(mClient);
        return HexUtil.parseByte2HexStr(MD5Util.md5(builder.toString())).toLowerCase();
    }
}
