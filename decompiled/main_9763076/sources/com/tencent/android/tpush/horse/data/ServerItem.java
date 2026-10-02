package com.tencent.android.tpush.horse.data;

import com.tencent.android.tpush.service.e.m;
import java.io.Serializable;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ServerItem implements Serializable {
    private static final long serialVersionUID = -6609283086276910655L;
    private long serverIpLong;
    private String serverIpStr;
    private int serverPort;
    private int spType;

    public ServerItem(String str, int i, int i2) {
        this.serverIpStr = str;
        this.serverIpLong = m.c(this.serverIpStr);
        this.serverPort = i;
        this.spType = i2;
    }

    public ServerItem(long j, int i, int i2) {
        this.serverIpLong = j;
        this.serverIpStr = m.a(j);
        this.serverPort = i;
        this.spType = i2;
    }

    public String a() {
        return this.serverIpStr;
    }

    public int b() {
        return this.serverPort;
    }

    private boolean a(ServerItem serverItem) {
        return serverItem != null && serverItem.a().equals(this.serverIpStr) && serverItem.b() == this.serverPort && serverItem.c() == this.spType;
    }

    public boolean equals(Object obj) {
        return obj instanceof ServerItem ? a((ServerItem) obj) : super.equals(obj);
    }

    public int hashCode() {
        return ((((((this.serverIpStr != null ? this.serverIpStr.hashCode() : 0) + 31) * 31) + this.serverPort) * 31) + this.spType) & Integer.MAX_VALUE;
    }

    public String toString() {
        return this.serverIpStr + ":" + this.serverPort;
    }

    public int c() {
        return this.spType;
    }
}
