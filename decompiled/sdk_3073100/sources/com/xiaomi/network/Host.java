package com.xiaomi.network;

import java.net.InetSocketAddress;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Host {
    private String a;
    private int b;

    public Host(String str, int i) {
        this.a = str;
        this.b = i;
    }

    public static Host a(String str, int i) {
        int iLastIndexOf = str.lastIndexOf(":");
        if (iLastIndexOf != -1) {
            String strSubstring = str.substring(0, iLastIndexOf);
            try {
                int i2 = Integer.parseInt(str.substring(iLastIndexOf + 1));
                if (i2 > 0) {
                    i = i2;
                }
                str = strSubstring;
            } catch (NumberFormatException e) {
                str = strSubstring;
            }
        }
        return new Host(str, i);
    }

    public static InetSocketAddress b(String str, int i) {
        Host hostA = a(str, i);
        return new InetSocketAddress(hostA.b(), hostA.a());
    }

    public int a() {
        return this.b;
    }

    public String b() {
        return this.a;
    }

    public String toString() {
        return this.b > 0 ? this.a + ":" + this.b : this.a;
    }
}
