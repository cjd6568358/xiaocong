package com.hzy.tvmao.utils;

import java.text.SimpleDateFormat;

/* JADX INFO: compiled from: TimeUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    public static final ThreadLocal<SimpleDateFormat> a = new f();
    public static final ThreadLocal<SimpleDateFormat> b = new g();
    public static final ThreadLocal<SimpleDateFormat> c = new h();

    public static long a() {
        return System.currentTimeMillis();
    }
}
