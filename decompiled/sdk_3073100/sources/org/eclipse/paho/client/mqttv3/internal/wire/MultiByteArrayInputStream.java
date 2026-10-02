package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MultiByteArrayInputStream extends InputStream {
    private byte[] bytesA;
    private byte[] bytesB;
    private int lengthA;
    private int lengthB;
    private int offsetA;
    private int offsetB;
    private int pos = 0;

    public MultiByteArrayInputStream(byte[] bytesA, int offsetA, int lengthA, byte[] bytesB, int offsetB, int lengthB) {
        this.bytesA = bytesA;
        this.bytesB = bytesB;
        this.offsetA = offsetA;
        this.offsetB = offsetB;
        this.lengthA = lengthA;
        this.lengthB = lengthB;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i;
        if (this.pos < this.lengthA) {
            i = this.bytesA[this.offsetA + this.pos];
        } else if (this.pos < this.lengthA + this.lengthB) {
            i = this.bytesB[(this.offsetB + this.pos) - this.lengthA];
        } else {
            return -1;
        }
        if (i < 0) {
            i += 256;
        }
        this.pos++;
        return i == true ? 1 : 0;
    }
}
