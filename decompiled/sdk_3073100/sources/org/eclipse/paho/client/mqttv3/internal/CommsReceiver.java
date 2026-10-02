package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CommsReceiver implements Runnable {
    private static final String CLASS_NAME;
    static Class class$0;
    private static final Logger log;
    private ClientComms clientComms;
    private ClientState clientState;
    private MqttInputStream in;
    private volatile boolean receiving;
    private CommsTokenStore tokenStore;
    private boolean running = false;
    private Object lifecycle = new Object();
    private Thread recThread = null;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.internal.CommsReceiver");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
    }

    public CommsReceiver(ClientComms clientComms, ClientState clientState, CommsTokenStore tokenStore, InputStream in) {
        this.clientState = null;
        this.clientComms = null;
        this.tokenStore = null;
        this.in = new MqttInputStream(clientState, in);
        this.clientComms = clientComms;
        this.clientState = clientState;
        this.tokenStore = tokenStore;
        log.setResourceName(clientComms.getClient().getClientId());
    }

    public void start(String threadName) {
        log.fine(CLASS_NAME, "start", "855");
        synchronized (this.lifecycle) {
            if (!this.running) {
                this.running = true;
                this.recThread = new Thread(this, threadName);
                this.recThread.start();
            }
        }
    }

    public void stop() {
        synchronized (this.lifecycle) {
            log.fine(CLASS_NAME, "stop", "850");
            if (this.running) {
                this.running = false;
                this.receiving = false;
                if (!Thread.currentThread().equals(this.recThread)) {
                    try {
                        this.recThread.join(1500L);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        this.recThread = null;
        log.fine(CLASS_NAME, "stop", "851");
    }

    @Override // java.lang.Runnable
    public void run() {
        MqttToken token = null;
        while (this.running && this.in != null) {
            try {
                try {
                    log.fine(CLASS_NAME, "run", "852");
                    this.receiving = this.in.available() > 0;
                    MqttWireMessage message = this.in.readMqttWireMessage();
                    this.receiving = false;
                    if (message instanceof MqttAck) {
                        token = this.tokenStore.getToken(message);
                        if (token != null) {
                            synchronized (token) {
                                this.clientState.notifyReceivedAck((MqttAck) message);
                            }
                        } else if ((message instanceof MqttPubRec) || (message instanceof MqttPubComp) || (message instanceof MqttPubAck)) {
                            log.fine(CLASS_NAME, "run", "857");
                        } else {
                            throw new MqttException(6);
                        }
                    } else if (message != null) {
                        this.clientState.notifyReceivedMsg(message);
                    }
                } catch (IOException ioe) {
                    log.fine(CLASS_NAME, "run", "853");
                    this.running = false;
                    if (!this.clientComms.isDisconnecting()) {
                        this.clientComms.shutdownConnection(token, new MqttException(32109, ioe));
                    }
                } catch (MqttException ex) {
                    log.fine(CLASS_NAME, "run", "856", null, ex);
                    this.running = false;
                    this.clientComms.shutdownConnection(token, ex);
                }
                this.receiving = false;
            } catch (Throwable th) {
                this.receiving = false;
                throw th;
            }
        }
        log.fine(CLASS_NAME, "run", "854");
    }
}
