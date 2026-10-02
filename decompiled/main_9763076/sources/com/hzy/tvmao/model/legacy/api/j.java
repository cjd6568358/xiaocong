package com.hzy.tvmao.model.legacy.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.cookie.SM;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: ServletResultParser.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class j {
    public static <T> List<T> a(JSONArray jSONArray) throws Exception {
        int length = jSONArray.length();
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            arrayList.add(jSONArray.get(i));
        }
        return arrayList;
    }

    public static i a(String str, Map<String, String> map, Object obj) throws Exception {
        return a(str, map, obj, false, false);
    }

    public static i a(String str, Map<String, String> map, Object obj, boolean z, boolean z2) throws Exception {
        b bVar = new b(true);
        bVar.a(z);
        bVar.b(z2);
        if (map != null) {
            bVar.a(map);
        }
        Map<String, Object> mapA = bVar.a(str, false);
        if (mapA.get("errno").equals(PushConstants.PUSH_TYPE_NOTIFY)) {
            JSONArray jSONArray = new JSONArray((String) mapA.get("content"));
            i iVar = new i();
            iVar.a = jSONArray.getInt(0);
            iVar.b = jSONArray.getString(1);
            String str2 = (String) mapA.get(SM.COOKIE);
            if (str2 != null) {
                iVar.c = str2;
            }
            if (iVar.a == 2) {
                iVar.e = jSONArray.length() > 2 ? jSONArray.getString(2) : Constants.MAIN_VERSION_TAG;
                if (z2) {
                    iVar.d = (byte[]) mapA.get("encrypt_data");
                }
                return iVar;
            }
            if (iVar.a()) {
                String string = jSONArray.length() > 2 ? jSONArray.getString(2) : Constants.MAIN_VERSION_TAG;
                if (obj == null || obj == JSONObject.class) {
                    iVar.e = string.length() > 0 ? new JSONObject(string) : Constants.MAIN_VERSION_TAG;
                } else if (obj == Object.class) {
                    List listA = a(jSONArray);
                    iVar.e = listA.subList(2, listA.size());
                } else if (obj instanceof Class) {
                    c.a().b().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    iVar.e = c.a().b().readValue(string, (Class) obj);
                } else if (obj instanceof TypeReference) {
                    c.a().b().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    iVar.e = c.a().b().readValue(string, (TypeReference) obj);
                }
                if (z2) {
                    iVar.d = (byte[]) mapA.get("encrypt_data");
                }
            }
            return iVar;
        }
        return i.a(0, (String) mapA.get("content"));
    }
}
