package com.huawei.hms.support.api.entity.push;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.a.a;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TagsResp implements IMessageEntity {

    @a
    private int retCode = 0;

    @a
    private String content = Constants.MAIN_VERSION_TAG;

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int i) {
        this.retCode = i;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }
}
