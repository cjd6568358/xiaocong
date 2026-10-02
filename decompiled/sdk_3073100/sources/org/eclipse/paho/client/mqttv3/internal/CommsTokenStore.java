package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CommsTokenStore {
    private static final String CLASS_NAME;
    static Class class$0;
    private static final Logger log;
    private MqttException closedResponse = null;
    private String logContext;
    private Hashtable tokens;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.internal.CommsTokenStore");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
    }

    public CommsTokenStore(String logContext) {
        log.setResourceName(logContext);
        this.tokens = new Hashtable();
        this.logContext = logContext;
        log.fine(CLASS_NAME, "<Init>", "308");
    }

    public MqttToken getToken(MqttWireMessage message) {
        String key = message.getKey();
        return (MqttToken) this.tokens.get(key);
    }

    public MqttToken getToken(String key) {
        return (MqttToken) this.tokens.get(key);
    }

    public MqttToken removeToken(MqttWireMessage message) {
        if (message != null) {
            return removeToken(message.getKey());
        }
        return null;
    }

    public MqttToken removeToken(String key) {
        log.fine(CLASS_NAME, "removeToken", "306", new Object[]{key});
        if (key != null) {
            return (MqttToken) this.tokens.remove(key);
        }
        return null;
    }

    protected MqttDeliveryToken restoreToken(MqttPublish message) {
        MqttDeliveryToken token;
        synchronized (this.tokens) {
            String key = new Integer(message.getMessageId()).toString();
            if (this.tokens.containsKey(key)) {
                token = (MqttDeliveryToken) this.tokens.get(key);
                log.fine(CLASS_NAME, "restoreToken", "302", new Object[]{key, message, token});
            } else {
                token = new MqttDeliveryToken(this.logContext);
                token.internalTok.setKey(key);
                this.tokens.put(key, token);
                log.fine(CLASS_NAME, "restoreToken", "303", new Object[]{key, message, token});
            }
        }
        return token;
    }

    protected void saveToken(MqttToken token, MqttWireMessage message) throws MqttException {
        synchronized (this.tokens) {
            if (this.closedResponse == null) {
                String key = message.getKey();
                log.fine(CLASS_NAME, "saveToken", "300", new Object[]{key, message});
                saveToken(token, key);
            } else {
                throw this.closedResponse;
            }
        }
    }

    protected void saveToken(MqttToken token, String key) {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "saveToken", "307", new Object[]{key, token.toString()});
            token.internalTok.setKey(key);
            this.tokens.put(key, token);
        }
    }

    protected void quiesce(MqttException quiesceResponse) {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "quiesce", "309", new Object[]{quiesceResponse});
            this.closedResponse = quiesceResponse;
        }
    }

    public void open() {
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "open", "310");
            this.closedResponse = null;
        }
    }

    public MqttDeliveryToken[] getOutstandingDelTokens() {
        MqttDeliveryToken[] mqttDeliveryTokenArr;
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "getOutstandingDelTokens", "311");
            Vector list = new Vector();
            Enumeration enumeration = this.tokens.elements();
            while (enumeration.hasMoreElements()) {
                MqttToken token = (MqttToken) enumeration.nextElement();
                if (token != null && (token instanceof MqttDeliveryToken) && !token.internalTok.isNotified()) {
                    list.addElement(token);
                }
            }
            MqttDeliveryToken[] result = new MqttDeliveryToken[list.size()];
            mqttDeliveryTokenArr = (MqttDeliveryToken[]) list.toArray(result);
        }
        return mqttDeliveryTokenArr;
    }

    public Vector getOutstandingTokens() {
        Vector list;
        synchronized (this.tokens) {
            log.fine(CLASS_NAME, "getOutstandingTokens", "312");
            list = new Vector();
            Enumeration enumeration = this.tokens.elements();
            while (enumeration.hasMoreElements()) {
                MqttToken token = (MqttToken) enumeration.nextElement();
                if (token != null) {
                    list.addElement(token);
                }
            }
        }
        return list;
    }

    public void clear() {
        log.fine(CLASS_NAME, "clear", "305", new Object[]{new Integer(this.tokens.size())});
        synchronized (this.tokens) {
            this.tokens.clear();
        }
    }

    public int count() {
        int size;
        synchronized (this.tokens) {
            size = this.tokens.size();
        }
        return size;
    }

    public String toString() {
        String string;
        String lineSep = System.getProperty("line.separator", "\n");
        StringBuffer toks = new StringBuffer();
        synchronized (this.tokens) {
            Enumeration enumeration = this.tokens.elements();
            while (enumeration.hasMoreElements()) {
                MqttToken token = (MqttToken) enumeration.nextElement();
                toks.append(new StringBuffer("{").append(token.internalTok).append("}").append(lineSep).toString());
            }
            string = toks.toString();
        }
        return string;
    }
}
