package com.baidu.uaq.agent.android.customtransmission;

import android.support.annotation.Keep;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Keep
public class APMUploadHandler {
    private String uploadName;

    public APMUploadHandler(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getUploadName() {
        return this.uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }
}
