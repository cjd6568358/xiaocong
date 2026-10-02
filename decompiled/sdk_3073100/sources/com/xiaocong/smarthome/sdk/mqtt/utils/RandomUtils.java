package com.xiaocong.smarthome.sdk.mqtt.utils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RandomUtils {
    public static long getRandomInt(int digits) {
        return (long) (((Math.random() * 9.0d) + 1.0d) * Math.pow(10.0d, digits - 1));
    }
}
