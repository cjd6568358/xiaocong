package com.huawei.hms.support.api.push;

import com.huawei.hms.support.api.client.Result;
import com.huawei.hms.support.api.entity.push.TagsResp;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HandleTagsResult extends Result {
    protected TagsResp resp;

    public void setTagsRes(TagsResp tagsResp) {
        this.resp = tagsResp;
    }

    public TagsResp getTagsRes() {
        return this.resp;
    }
}
