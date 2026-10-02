package com.hzy.tvmao.model.legacy.api;

import com.hzy.tvmao.utils.DataStoreUtil;
import java.io.Closeable;
import java.io.IOException;

/* JADX INFO: compiled from: Util.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class p {
    private static int a = -1;
    private static int b = -1;

    public static void a(int i) {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        if (Math.abs(i - iCurrentTimeMillis) > 60) {
            if (DataStoreUtil.i().putString("now_time", String.valueOf(i) + "|" + iCurrentTimeMillis)) {
                a = i;
                b = iCurrentTimeMillis;
                return;
            }
            return;
        }
        if (DataStoreUtil.i().remove("now_time")) {
            a = 0;
            b = 0;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }
}
