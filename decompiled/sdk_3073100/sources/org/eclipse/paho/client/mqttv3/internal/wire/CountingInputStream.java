package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CountingInputStream extends InputStream {
    private int counter = 0;
    private InputStream in;

    public CountingInputStream(InputStream in) {
        this.in = in;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.in.read();
        if (i != -1) {
            this.counter++;
        }
        return i;
    }

    public int getCounter() {
        return this.counter;
    }
}
