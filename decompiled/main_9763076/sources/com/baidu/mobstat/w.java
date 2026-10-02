package com.baidu.mobstat;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class w {
    private long a;
    private String b;
    private String c;

    public w(long j, String str, String str2) {
        this.a = -1L;
        str2 = str2 == null ? Constants.MAIN_VERSION_TAG : str2;
        this.a = j;
        this.b = str;
        this.c = str2;
    }

    public long a() {
        return this.a;
    }

    public String b() {
        return this.c;
    }
}
