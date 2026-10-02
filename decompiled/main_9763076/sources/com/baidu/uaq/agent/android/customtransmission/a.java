package com.baidu.uaq.agent.android.customtransmission;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: BlockData.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private String as;
    private int at;

    public a(String block) {
        this.as = Constants.MAIN_VERSION_TAG;
        this.at = 0;
        this.as = block;
        this.at = 0;
    }

    public String P() {
        return this.as;
    }

    public void Q() {
        this.at++;
    }

    public int getRetryCount() {
        return this.at;
    }

    public String toString() {
        return "BlockData{block='" + this.as + "', retryCount=" + this.at + '}';
    }
}
