package com.xiaomi.xmpush.thrift;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public enum k {
    Circle(0),
    Polygon(1);

    private final int c;

    k(int i) {
        this.c = i;
    }

    public static k a(int i) {
        switch (i) {
            case 0:
                return Circle;
            case 1:
                return Polygon;
            default:
                return null;
        }
    }

    public int a() {
        return this.c;
    }
}
