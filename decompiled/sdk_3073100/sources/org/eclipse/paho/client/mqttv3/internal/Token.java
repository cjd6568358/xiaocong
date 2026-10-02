package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Token {
    private static final String CLASS_NAME;
    static Class class$0;
    private static final Logger log;
    private String key;
    private volatile boolean completed = false;
    private boolean pendingComplete = false;
    private boolean sent = false;
    private Object responseLock = new Object();
    private Object sentLock = new Object();
    protected MqttMessage message = null;
    private MqttWireMessage response = null;
    private MqttException exception = null;
    private String[] topics = null;
    private IMqttAsyncClient client = null;
    private IMqttActionListener callback = null;
    private Object userContext = null;
    private int messageID = 0;
    private boolean notified = false;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.internal.Token");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
    }

    public Token(String logContext) {
        log.setResourceName(logContext);
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public MqttException getException() {
        return this.exception;
    }

    public boolean isComplete() {
        return this.completed;
    }

    protected boolean isCompletePending() {
        return this.pendingComplete;
    }

    public void setActionCallback(IMqttActionListener listener) {
        this.callback = listener;
    }

    public IMqttActionListener getActionCallback() {
        return this.callback;
    }

    protected void markComplete(MqttWireMessage msg, MqttException ex) {
        log.fine(CLASS_NAME, "markComplete", "404", new Object[]{getKey(), msg, ex});
        synchronized (this.responseLock) {
            if (msg instanceof MqttAck) {
                this.message = null;
            }
            this.pendingComplete = true;
            this.response = msg;
            this.exception = ex;
        }
    }

    protected void notifyComplete() {
        log.fine(CLASS_NAME, "notifyComplete", "404", new Object[]{getKey(), this.response, this.exception});
        synchronized (this.responseLock) {
            if (this.exception == null && this.pendingComplete) {
                this.completed = true;
                this.pendingComplete = false;
            } else {
                this.pendingComplete = false;
            }
            this.responseLock.notifyAll();
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    public void waitUntilSent() throws MqttException {
        synchronized (this.sentLock) {
            synchronized (this.responseLock) {
                if (this.exception != null) {
                    throw this.exception;
                }
            }
            while (!this.sent) {
                try {
                    log.fine(CLASS_NAME, "waitUntilSent", "409", new Object[]{getKey()});
                    this.sentLock.wait();
                } catch (InterruptedException e) {
                }
            }
            if (!this.sent) {
                if (this.exception == null) {
                    throw ExceptionHelper.createMqttException(6);
                }
                throw this.exception;
            }
        }
    }

    protected void notifySent() {
        log.fine(CLASS_NAME, "notifySent", "403", new Object[]{getKey()});
        synchronized (this.responseLock) {
            this.response = null;
            this.completed = false;
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    protected void setClient(IMqttAsyncClient client) {
        this.client = client;
    }

    public MqttMessage getMessage() {
        return this.message;
    }

    public MqttWireMessage getWireMessage() {
        return this.response;
    }

    public void setMessage(MqttMessage msg) {
        this.message = msg;
    }

    public String[] getTopics() {
        return this.topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public Object getUserContext() {
        return this.userContext;
    }

    public void setUserContext(Object userContext) {
        this.userContext = userContext;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setException(MqttException exception) {
        synchronized (this.responseLock) {
            this.exception = exception;
        }
    }

    public boolean isNotified() {
        return this.notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public String toString() {
        StringBuffer tok = new StringBuffer();
        tok.append("key=").append(getKey());
        tok.append(" ,topics=");
        if (getTopics() != null) {
            for (int i = 0; i < getTopics().length; i++) {
                tok.append(getTopics()[i]).append(", ");
            }
        }
        tok.append(" ,usercontext=").append(getUserContext());
        tok.append(" ,isComplete=").append(isComplete());
        tok.append(" ,isNotified=").append(isNotified());
        tok.append(" ,exception=").append(getException());
        tok.append(" ,actioncallback=").append(getActionCallback());
        return tok.toString();
    }

    public MqttWireMessage getResponse() {
        return this.response;
    }
}
