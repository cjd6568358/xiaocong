package com.xiaomi.smack.packet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class g {
    private String a;

    public g(String str) {
        this.a = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("stream:error (").append(this.a).append(")");
        return sb.toString();
    }
}
