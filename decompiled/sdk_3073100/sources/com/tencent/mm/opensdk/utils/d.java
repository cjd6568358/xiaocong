package com.tencent.mm.opensdk.utils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class d {
    public static boolean i(String str) {
        return str == null || str.length() <= 0;
    }

    public static int j(String str) {
        if (str == null) {
            return 0;
        }
        try {
            if (str.length() <= 0) {
                return 0;
            }
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }
}
