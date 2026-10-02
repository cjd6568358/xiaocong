package com.youzan.androidsdk;

import com.youzan.spiderman.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SDKUtil {
    public static <T> List<T> jsonToList(JSONObject o, String key, Class<T> model) throws JSONException {
        Object objNewInstance;
        if (!o.has(key)) {
            return null;
        }
        JSONArray jsonArray = o.optJSONArray(key);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                if (model.getPackage() != null && "java.lang".equals(model.getPackage().getName())) {
                    objNewInstance = jsonArray.get(i);
                } else {
                    objNewInstance = model.getConstructor(JSONObject.class).newInstance(jsonArray.get(i));
                }
                arrayList.add(objNewInstance);
            } catch (Exception e) {
                e.printStackTrace();
                return arrayList;
            }
        }
        return arrayList;
    }

    public static String verifyClientId(String clientId) {
        if (StringUtils.isEmpty(clientId)) {
            throw new IllegalArgumentException("clientId should not be empty");
        }
        String clientId2 = clientId.trim();
        if (clientId2.isEmpty()) {
            throw new IllegalArgumentException("clientId should not be empty");
        }
        if (clientId2.indexOf(32) != -1 || clientId2.indexOf(9) != -1) {
            throw new IllegalArgumentException("clientId should not contain empty characters");
        }
        return clientId2;
    }
}
