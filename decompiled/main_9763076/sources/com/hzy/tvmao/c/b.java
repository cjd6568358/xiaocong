package com.hzy.tvmao.c;

import com.hzy.tvmao.model.legacy.api.d;
import com.hzy.tvmao.utils.LogUtil;

/* JADX INFO: compiled from: StatUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements Runnable {
    private final /* synthetic */ String a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;

    b(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        LogUtil.d("statistical result is " + d.d(this.a, this.b, this.c).a());
    }
}
