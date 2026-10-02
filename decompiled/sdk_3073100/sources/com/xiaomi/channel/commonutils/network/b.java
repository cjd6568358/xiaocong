package com.xiaomi.channel.commonutils.network;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    public int a;
    public Map<String, String> b = new HashMap();
    public String c;

    public String a() {
        return this.c;
    }

    public String toString() {
        return String.format("resCode = %1$d, headers = %2$s, response = %3$s", Integer.valueOf(this.a), this.b.toString(), this.c);
    }
}
