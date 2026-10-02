package com.facebook.common.util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HashCodeUtil {
    public static int hashCode(Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        return hashCode(o1 == null ? 0 : o1.hashCode(), o2 == null ? 0 : o2.hashCode(), o3 == null ? 0 : o3.hashCode(), o4 == null ? 0 : o4.hashCode(), o5 == null ? 0 : o5.hashCode(), o6 != null ? o6.hashCode() : 0);
    }

    public static int hashCode(int i1, int i2) {
        int acc = i1 + 31;
        return (acc * 31) + i2;
    }

    public static int hashCode(int i1, int i2, int i3, int i4, int i5, int i6) {
        int acc = i1 + 31;
        return (((((((((acc * 31) + i2) * 31) + i3) * 31) + i4) * 31) + i5) * 31) + i6;
    }
}
