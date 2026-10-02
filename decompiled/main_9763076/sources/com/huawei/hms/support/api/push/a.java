package com.huawei.hms.support.api.push;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.entity.push.GetTagsReq;
import com.huawei.hms.support.api.entity.push.PushNaming;
import com.huawei.hms.support.api.entity.push.TagsReq;
import com.huawei.hms.support.api.push.a.a.a.c;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: PushTagManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a {
    a() {
    }

    PendingResult<HandleTagsResult> a(ApiClient apiClient, Map<String, String> map) throws PushException {
        if (map == null) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushTagManager", PushException.EXCEPITON_TAGS_NULL);
            }
            throw new PushException(PushException.EXCEPITON_TAGS_NULL);
        }
        Context context = apiClient.getContext();
        if (com.huawei.hms.support.log.a.a()) {
            com.huawei.hms.support.log.a.a("PushTagManager", "set tags, pkgName:" + context.getPackageName());
        }
        Map<String, String> mapA = a(context, map);
        try {
            JSONArray jSONArray = new JSONArray();
            for (Map.Entry<String, String> entry : mapA.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("tagKey", key);
                jSONObject.put("tagValue", value);
                jSONObject.put("opType", 1);
                if (jSONObject.length() > 0) {
                    jSONArray.put(jSONObject);
                }
            }
            if (jSONArray.length() > 0) {
                if (com.huawei.hms.support.log.a.a()) {
                    com.huawei.hms.support.log.a.a("PushTagManager", "begin to setTags: " + jSONArray.toString());
                }
                return a(apiClient, jSONArray.toString(), 0L, 2);
            }
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushTagManager", "no tag need to upload");
            }
            throw new PushException(PushException.EXCEPITON_TAGS_NULL);
        } catch (Exception e) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushTagManager", "set tags exception," + e.toString());
            }
            throw new PushException(e + PushException.EXCEPITON_SET_TAGS_FAILED);
        }
    }

    PendingResult<HandleTagsResult> a(ApiClient apiClient, List<String> list) throws PushException {
        if (com.huawei.hms.support.log.a.b()) {
            com.huawei.hms.support.log.a.b("PushTagManager", "invoke method: deleteTags");
        }
        try {
            a(list);
            Context context = apiClient.getContext();
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushTagManager", "delete tags, pkgName:" + context.getPackageName());
            }
            JSONArray jSONArrayA = com.huawei.hms.support.api.push.a.a.a.a.a(list, context);
            if (jSONArrayA.length() > 0) {
                if (com.huawei.hms.support.log.a.a()) {
                    com.huawei.hms.support.log.a.a("PushTagManager", "begin to deleTags: " + jSONArrayA.toString());
                }
                return a(apiClient, jSONArrayA.toString(), 0L, 2);
            }
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushTagManager", PushException.EXCEPITON_NO_TAG_NEED_DEL);
            }
            throw new PushException(PushException.EXCEPITON_NO_TAG_NEED_DEL);
        } catch (Exception e) {
            if (com.huawei.hms.support.log.a.c()) {
                com.huawei.hms.support.log.a.c("PushTagManager", "delete tag error: " + e.getMessage());
            }
            throw new PushException(PushException.EXCEPITON_DEL_TAGS_FAILED, e);
        }
    }

    PendingResult<GetTagResult> a(ApiClient apiClient) throws PushException {
        return new GetTagsPendingResultImpl(apiClient, PushNaming.getTags, new GetTagsReq());
    }

    private static Map<String, String> a(Context context, Map<String, String> map) {
        HashMap map2 = new HashMap();
        c cVar = new c(context, "tags_info");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (cVar.c(key)) {
                String strB = cVar.b(key);
                if (!TextUtils.isEmpty(value) && value.equals(strB)) {
                    if (com.huawei.hms.support.log.a.a()) {
                        com.huawei.hms.support.log.a.a("PushTagManager", "tag has reported:" + entry);
                    }
                }
            }
            map2.put(key, value);
        }
        return map2;
    }

    private PendingResult<HandleTagsResult> a(ApiClient apiClient, String str, long j, int i) throws PushException {
        Context context = apiClient.getContext();
        String strA = com.huawei.hms.support.api.push.a.a.a.a(context, "push_client_self_info", "token_info");
        if (TextUtils.isEmpty(strA)) {
            if (com.huawei.hms.support.log.a.c()) {
                com.huawei.hms.support.log.a.c("PushTagManager", "token is null, should register a token first. error code:[907122004]");
            }
            throw new PushException(PushException.EXCEPITON_TOKEN_INVALID);
        }
        TagsReq tagsReq = new TagsReq();
        tagsReq.setContent(str);
        tagsReq.setCycle(j);
        tagsReq.setOperType(1);
        tagsReq.setPlusType(i);
        tagsReq.setToken(strA);
        tagsReq.setPkgName(context.getPackageName());
        tagsReq.setApkVersion(com.huawei.hms.support.api.push.a.a.b(context));
        return new HandleTagPendingResultImpl(apiClient, PushNaming.setTags, tagsReq);
    }

    private void a(List<String> list) {
        if (list == null || list.isEmpty()) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("PushTagManager", "the key list is null");
            }
            throw new PushException(PushException.EXCEPITON_DEL_TAGS_LIST_NULL);
        }
    }
}
