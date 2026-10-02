package com.facebook.react.bridge;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftAssertions {
    public static void assertUnreachable(String message) {
        throw new AssertionException(message);
    }

    public static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new AssertionException(message);
        }
    }

    public static <T> T assertNotNull(T instance) {
        if (instance == null) {
            throw new AssertionException("Expected object to not be null!");
        }
        return instance;
    }
}
