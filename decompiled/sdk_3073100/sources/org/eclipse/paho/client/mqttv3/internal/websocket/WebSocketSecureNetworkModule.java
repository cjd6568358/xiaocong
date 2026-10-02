package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WebSocketSecureNetworkModule extends SSLNetworkModule {
    private static final String CLASS_NAME;
    static Class class$0;
    private static final Logger log;
    private String host;
    private ByteArrayOutputStream outputStream;
    private PipedInputStream pipedInputStream;
    private int port;
    private String uri;
    private WebSocketReceiver webSocketReceiver;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketSecureNetworkModule");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
    }

    public WebSocketSecureNetworkModule(SSLSocketFactory factory, String uri, String host, int port, String clientId) {
        super(factory, host, port, clientId);
        this.outputStream = new ByteArrayOutputStream(this) { // from class: org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketSecureNetworkModule.1
            final WebSocketSecureNetworkModule this$0;

            {
                this.this$0 = this;
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public void flush() throws IOException {
                ByteBuffer byteBuffer;
                synchronized (this) {
                    byteBuffer = ByteBuffer.wrap(toByteArray());
                    reset();
                }
                WebSocketFrame frame = new WebSocketFrame((byte) 2, true, byteBuffer.array());
                byte[] rawFrame = frame.encodeFrame();
                this.this$0.getSocketOutputStream().write(rawFrame);
                this.this$0.getSocketOutputStream().flush();
            }
        };
        this.uri = uri;
        this.host = host;
        this.port = port;
        this.pipedInputStream = new PipedInputStream();
        log.setResourceName(clientId);
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule, org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void start() throws MqttException, IOException {
        super.start();
        WebSocketHandshake handshake = new WebSocketHandshake(super.getInputStream(), super.getOutputStream(), this.uri, this.host, this.port);
        handshake.execute();
        this.webSocketReceiver = new WebSocketReceiver(getSocketInputStream(), this.pipedInputStream);
        this.webSocketReceiver.start("WssSocketReceiver");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public OutputStream getSocketOutputStream() throws IOException {
        return super.getOutputStream();
    }

    private InputStream getSocketInputStream() throws IOException {
        return super.getInputStream();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public InputStream getInputStream() throws IOException {
        return this.pipedInputStream;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public OutputStream getOutputStream() throws IOException {
        return this.outputStream;
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public void stop() throws IOException {
        WebSocketFrame frame = new WebSocketFrame((byte) 8, true, "1000".getBytes());
        byte[] rawFrame = frame.encodeFrame();
        getSocketOutputStream().write(rawFrame);
        getSocketOutputStream().flush();
        if (this.webSocketReceiver != null) {
            this.webSocketReceiver.stop();
        }
        super.stop();
    }

    @Override // org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule, org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule, org.eclipse.paho.client.mqttv3.internal.NetworkModule
    public String getServerURI() {
        return new StringBuffer("wss://").append(this.host).append(":").append(this.port).toString();
    }
}
