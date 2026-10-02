package com.baidu.location.a;

import android.os.HandlerThread;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class u {
    private static HandlerThread a = null;

    public static synchronized HandlerThread a() {
        if (a == null) {
            a = new HandlerThread("ServiceStartArguments", 10);
            a.start();
        }
        return a;
    }
}
