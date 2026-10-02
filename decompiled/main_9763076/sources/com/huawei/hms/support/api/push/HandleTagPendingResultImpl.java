package com.huawei.hms.support.api.push;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.support.api.client.ApiClient;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.api.entity.push.TagsResp;
import com.huawei.hms.support.api.push.a.a.a.c;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HandleTagPendingResultImpl extends com.huawei.hms.support.api.a<HandleTagsResult, TagsResp> {
    private ApiClient a;

    public HandleTagPendingResultImpl(ApiClient apiClient, String str, IMessageEntity iMessageEntity) {
        super(apiClient, str, iMessageEntity);
        this.a = apiClient;
    }

    @Override // com.huawei.hms.support.api.a
    public HandleTagsResult onComplete(TagsResp tagsResp) {
        if (com.huawei.hms.support.log.a.a()) {
            com.huawei.hms.support.log.a.a("HandleTagPendingResultImpl", "report tag completely, retcode is:" + tagsResp.getRetCode());
        }
        if (907122001 == tagsResp.getRetCode()) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("HandleTagPendingResultImpl", "report tag success.");
            }
            a(this.a, tagsResp.getContent());
        }
        HandleTagsResult handleTagsResult = new HandleTagsResult();
        handleTagsResult.setStatus(new Status(tagsResp.getRetCode()));
        handleTagsResult.setTagsRes(tagsResp);
        return handleTagsResult;
    }

    private static void a(ApiClient apiClient, String str) {
        if (apiClient == null) {
            if (com.huawei.hms.support.log.a.a()) {
                com.huawei.hms.support.log.a.a("HandleTagPendingResultImpl", "the client is null when adding or deleting tags from file.");
                return;
            }
            return;
        }
        try {
            JSONArray jSONArrayA = com.huawei.hms.support.api.push.a.a.a.a.a(str);
            if (jSONArrayA != null) {
                c cVar = new c(apiClient.getContext(), "tags_info");
                int length = jSONArrayA.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObjectOptJSONObject = jSONArrayA.optJSONObject(i);
                    if (jSONObjectOptJSONObject != null) {
                        String strOptString = jSONObjectOptJSONObject.optString("tagKey");
                        int iOptInt = jSONObjectOptJSONObject.optInt("opType");
                        if (1 == iOptInt) {
                            cVar.a(strOptString, (Object) jSONObjectOptJSONObject.optString("tagValue"));
                        } else if (2 == iOptInt) {
                            cVar.d(strOptString);
                        }
                    }
                }
            }
        } catch (Exception e) {
            if (com.huawei.hms.support.log.a.c()) {
                com.huawei.hms.support.log.a.c("HandleTagPendingResultImpl", "when adding or deleting tags from file excepiton," + e.getMessage());
            }
        }
    }
}
