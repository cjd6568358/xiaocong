package com.alibaba.mtl.appmonitor.f;

/* JADX INFO: compiled from: ParseUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static int a(String str) {
        if (b.c(str)) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }
}
