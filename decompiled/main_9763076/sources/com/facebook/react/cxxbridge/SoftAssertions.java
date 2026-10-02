package com.facebook.react.cxxbridge;

import com.facebook.react.bridge.AssertionException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SoftAssertions {
    public static void assertCondition(boolean condition, String message) {
        if (!condition) {
            throw new AssertionException(message);
        }
    }
}
