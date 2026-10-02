package com.facebook.infer.annotation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class Assertions {
    public static <T> T assumeNotNull(T object) {
        return object;
    }

    public static <T> T assertNotNull(T object) {
        if (object == null) {
            throw new AssertionError();
        }
        return object;
    }

    public static <T> T assertNotNull(T object, String explanation) {
        if (object == null) {
            throw new AssertionError(explanation);
        }
        return object;
    }

    public static void assertCondition(boolean condition) {
        if (!condition) {
            throw new AssertionError();
        }
    }

    public static void assertCondition(boolean condition, String explanation) {
        if (!condition) {
            throw new AssertionError(explanation);
        }
    }
}
