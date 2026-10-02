package com.xiaomi.xmpush.thrift;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public enum g {
    INT(1),
    LONG(2),
    STRING(3),
    BOOLEAN(4);

    private final int e;

    g(int i) {
        this.e = i;
    }

    public static g a(int i) {
        switch (i) {
            case 1:
                return INT;
            case 2:
                return LONG;
            case 3:
                return STRING;
            case 4:
                return BOOLEAN;
            default:
                return null;
        }
    }
}
