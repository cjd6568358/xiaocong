package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.ClientState;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttOutputStream extends OutputStream {
    private static final String CLASS_NAME;
    static Class class$0;
    private static final Logger log;
    private ClientState clientState;
    private BufferedOutputStream out;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
    }

    public MqttOutputStream(ClientState clientState, OutputStream out) {
        this.clientState = null;
        this.clientState = clientState;
        this.out = new BufferedOutputStream(out);
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.out.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override // java.io.OutputStream
    public void write(byte[] b) throws IOException {
        this.out.write(b);
        this.clientState.notifySentBytes(b.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] b, int off, int len) throws IOException {
        this.out.write(b, off, len);
        this.clientState.notifySentBytes(len);
    }

    @Override // java.io.OutputStream
    public void write(int b) throws IOException {
        this.out.write(b);
    }

    public void write(MqttWireMessage message) throws MqttException, IOException {
        byte[] bytes = message.getHeader();
        byte[] pl = message.getPayload();
        this.out.write(bytes, 0, bytes.length);
        this.clientState.notifySentBytes(bytes.length);
        int offset = 0;
        while (offset < pl.length) {
            int length = Math.min(1024, pl.length - offset);
            this.out.write(pl, offset, length);
            offset += 1024;
            this.clientState.notifySentBytes(length);
        }
        log.fine(CLASS_NAME, "write", "500", new Object[]{message});
    }
}
