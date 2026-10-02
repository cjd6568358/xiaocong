package org.eclipse.paho.client.mqttv3.internal.wire;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MultiByteInteger {
    private int length;
    private long value;

    public MultiByteInteger(long value, int length) {
        this.value = value;
        this.length = length;
    }

    public long getValue() {
        return this.value;
    }
}
