package com.hzy.tvmao.model.legacy.api;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ServletResult.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class i<T> {
    public int a;
    public String b;
    public String c = Constants.MAIN_VERSION_TAG;
    public byte[] d = null;
    public Object e = null;

    public boolean a() {
        return this.a == 1;
    }

    public static i a(int i, String str) {
        i iVar = new i();
        iVar.a = i;
        if (str == null) {
            str = "网络错误";
        }
        iVar.b = str;
        return iVar;
    }

    public static i b() {
        return a(0, "网络错误");
    }
}
