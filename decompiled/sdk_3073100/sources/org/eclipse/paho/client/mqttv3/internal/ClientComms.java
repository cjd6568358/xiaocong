package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.BufferedMessage;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ClientComms {
    private static final String CLASS_NAME;
    static Class class$0;
    private static final Logger log;
    private CommsCallback callback;
    private IMqttAsyncClient client;
    private ClientState clientState;
    private MqttConnectOptions conOptions;
    private byte conState;
    private DisconnectedMessageBuffer disconnectedMessageBuffer;
    private int networkModuleIndex;
    private NetworkModule[] networkModules;
    private MqttClientPersistence persistence;
    private MqttPingSender pingSender;
    private CommsReceiver receiver;
    private CommsSender sender;
    private CommsTokenStore tokenStore;
    public static String VERSION = "${project.version}";
    public static String BUILD_LEVEL = "L${build.level}";
    private boolean stoppingComms = false;
    private Object conLock = new Object();
    private boolean closePending = false;
    private boolean resting = false;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.internal.ClientComms");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
    }

    public ClientComms(IMqttAsyncClient client, MqttClientPersistence persistence, MqttPingSender pingSender) throws MqttException {
        this.conState = (byte) 3;
        this.conState = (byte) 3;
        this.client = client;
        this.persistence = persistence;
        this.pingSender = pingSender;
        this.pingSender.init(this);
        this.tokenStore = new CommsTokenStore(getClient().getClientId());
        this.callback = new CommsCallback(this);
        this.clientState = new ClientState(persistence, this.tokenStore, this.callback, this, pingSender);
        this.callback.setClientState(this.clientState);
        log.setResourceName(getClient().getClientId());
    }

    void internalSend(MqttWireMessage message, MqttToken token) throws MqttException {
        log.fine(CLASS_NAME, "internalSend", "200", new Object[]{message.getKey(), message, token});
        if (token.getClient() == null) {
            token.internalTok.setClient(getClient());
            try {
                this.clientState.send(message, token);
                return;
            } catch (MqttException e) {
                if (message instanceof MqttPublish) {
                    this.clientState.undo((MqttPublish) message);
                }
                throw e;
            }
        }
        log.fine(CLASS_NAME, "internalSend", "213", new Object[]{message.getKey(), message, token});
        throw new MqttException(32201);
    }

    public void sendNoWait(MqttWireMessage message, MqttToken token) throws MqttException {
        if (isConnected() || ((!isConnected() && (message instanceof MqttConnect)) || (isDisconnecting() && (message instanceof MqttDisconnect)))) {
            if (this.disconnectedMessageBuffer != null && this.disconnectedMessageBuffer.getMessageCount() != 0) {
                log.fine(CLASS_NAME, "sendNoWait", "507", new Object[]{message.getKey()});
                if (this.disconnectedMessageBuffer.isPersistBuffer()) {
                    this.clientState.persistBufferedMessage(message);
                }
                this.disconnectedMessageBuffer.putMessage(message, token);
                return;
            }
            internalSend(message, token);
            return;
        }
        if (this.disconnectedMessageBuffer != null && isResting()) {
            log.fine(CLASS_NAME, "sendNoWait", "508", new Object[]{message.getKey()});
            if (this.disconnectedMessageBuffer.isPersistBuffer()) {
                this.clientState.persistBufferedMessage(message);
            }
            this.disconnectedMessageBuffer.putMessage(message, token);
            return;
        }
        log.fine(CLASS_NAME, "sendNoWait", "208");
        throw ExceptionHelper.createMqttException(32104);
    }

    public void close() throws MqttException {
        synchronized (this.conLock) {
            if (!isClosed()) {
                if (!isDisconnected()) {
                    log.fine(CLASS_NAME, "close", "224");
                    if (isConnecting()) {
                        throw new MqttException(32110);
                    }
                    if (isConnected()) {
                        throw ExceptionHelper.createMqttException(32100);
                    }
                    if (isDisconnecting()) {
                        this.closePending = true;
                        return;
                    }
                }
                this.conState = (byte) 4;
                this.clientState.close();
                this.clientState = null;
                this.callback = null;
                this.persistence = null;
                this.sender = null;
                this.pingSender = null;
                this.receiver = null;
                this.networkModules = null;
                this.conOptions = null;
                this.tokenStore = null;
            }
        }
    }

    public void connect(MqttConnectOptions options, MqttToken token) throws MqttException {
        synchronized (this.conLock) {
            if (isDisconnected() && !this.closePending) {
                log.fine(CLASS_NAME, "connect", "214");
                this.conState = (byte) 1;
                this.conOptions = options;
                MqttConnect connect = new MqttConnect(this.client.getClientId(), this.conOptions.getMqttVersion(), this.conOptions.isCleanSession(), this.conOptions.getKeepAliveInterval(), this.conOptions.getUserName(), this.conOptions.getPassword(), this.conOptions.getWillMessage(), this.conOptions.getWillDestination());
                this.clientState.setKeepAliveSecs(this.conOptions.getKeepAliveInterval());
                this.clientState.setCleanSession(this.conOptions.isCleanSession());
                this.clientState.setMaxInflight(this.conOptions.getMaxInflight());
                this.tokenStore.open();
                ConnectBG conbg = new ConnectBG(this, this, token, connect);
                conbg.start();
            } else {
                log.fine(CLASS_NAME, "connect", "207", new Object[]{new Byte(this.conState)});
                if (isClosed() || this.closePending) {
                    throw new MqttException(32111);
                }
                if (isConnecting()) {
                    throw new MqttException(32110);
                }
                if (isDisconnecting()) {
                    throw new MqttException(32102);
                }
                throw ExceptionHelper.createMqttException(32100);
            }
        }
    }

    public void connectComplete(MqttConnack cack, MqttException mex) throws MqttException {
        int rc = cack.getReturnCode();
        synchronized (this.conLock) {
            try {
                if (rc == 0) {
                    log.fine(CLASS_NAME, "connectComplete", "215");
                    this.conState = (byte) 0;
                } else {
                    log.fine(CLASS_NAME, "connectComplete", "204", new Object[]{new Integer(rc)});
                    throw mex;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void shutdownConnection(MqttToken token, MqttException reason) {
        NetworkModule networkModule;
        synchronized (this.conLock) {
            if (!this.stoppingComms && !this.closePending && !isClosed()) {
                this.stoppingComms = true;
                log.fine(CLASS_NAME, "shutdownConnection", "216");
                boolean wasConnected = isConnected() || isDisconnecting();
                this.conState = (byte) 2;
                if (token != null && !token.isComplete()) {
                    token.internalTok.setException(reason);
                }
                if (this.callback != null) {
                    this.callback.stop();
                }
                if (this.receiver != null) {
                    this.receiver.stop();
                }
                try {
                    if (this.networkModules != null && (networkModule = this.networkModules[this.networkModuleIndex]) != null) {
                        networkModule.stop();
                    }
                } catch (Exception e) {
                }
                this.tokenStore.quiesce(new MqttException(32102));
                MqttToken endToken = handleOldTokens(token, reason);
                try {
                    this.clientState.disconnected(reason);
                    if (this.clientState.getCleanSession()) {
                        this.callback.removeMessageListeners();
                    }
                } catch (Exception e2) {
                }
                if (this.sender != null) {
                    this.sender.stop();
                }
                if (this.pingSender != null) {
                    this.pingSender.stop();
                }
                try {
                    if (this.disconnectedMessageBuffer == null && this.persistence != null) {
                        this.persistence.close();
                    }
                } catch (Exception e3) {
                }
                synchronized (this.conLock) {
                    log.fine(CLASS_NAME, "shutdownConnection", "217");
                    this.conState = (byte) 3;
                    this.stoppingComms = false;
                }
                if ((endToken != null) & (this.callback != null)) {
                    this.callback.asyncOperationComplete(endToken);
                }
                if (wasConnected && this.callback != null) {
                    this.callback.connectionLost(reason);
                }
                synchronized (this.conLock) {
                    if (this.closePending) {
                        try {
                            close();
                        } catch (Exception e4) {
                        }
                    }
                }
            }
        }
    }

    private MqttToken handleOldTokens(MqttToken token, MqttException reason) {
        log.fine(CLASS_NAME, "handleOldTokens", "222");
        MqttToken tokToNotifyLater = null;
        if (token != null) {
            try {
                if (this.tokenStore.getToken(token.internalTok.getKey()) == null) {
                    this.tokenStore.saveToken(token, token.internalTok.getKey());
                }
            } catch (Exception e) {
            }
        }
        Vector toksToNot = this.clientState.resolveOldTokens(reason);
        Enumeration toksToNotE = toksToNot.elements();
        while (toksToNotE.hasMoreElements()) {
            MqttToken tok = (MqttToken) toksToNotE.nextElement();
            if (tok.internalTok.getKey().equals("Disc") || tok.internalTok.getKey().equals("Con")) {
                tokToNotifyLater = tok;
            } else {
                this.callback.asyncOperationComplete(tok);
            }
        }
        return tokToNotifyLater;
    }

    public void disconnect(MqttDisconnect disconnect, long quiesceTimeout, MqttToken token) throws MqttException {
        synchronized (this.conLock) {
            if (isClosed()) {
                log.fine(CLASS_NAME, "disconnect", "223");
                throw ExceptionHelper.createMqttException(32111);
            }
            if (isDisconnected()) {
                log.fine(CLASS_NAME, "disconnect", "211");
                throw ExceptionHelper.createMqttException(32101);
            }
            if (isDisconnecting()) {
                log.fine(CLASS_NAME, "disconnect", "219");
                throw ExceptionHelper.createMqttException(32102);
            }
            if (Thread.currentThread() == this.callback.getThread()) {
                log.fine(CLASS_NAME, "disconnect", "210");
                throw ExceptionHelper.createMqttException(32107);
            }
            log.fine(CLASS_NAME, "disconnect", "218");
            this.conState = (byte) 2;
            DisconnectBG discbg = new DisconnectBG(this, disconnect, quiesceTimeout, token);
            discbg.start();
        }
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 0;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 1;
        }
        return z;
    }

    public boolean isDisconnected() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 3;
        }
        return z;
    }

    public boolean isDisconnecting() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 2;
        }
        return z;
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this.conLock) {
            z = this.conState == 4;
        }
        return z;
    }

    public boolean isResting() {
        boolean z;
        synchronized (this.conLock) {
            z = this.resting;
        }
        return z;
    }

    public void setCallback(MqttCallback mqttCallback) {
        this.callback.setCallback(mqttCallback);
    }

    public void setReconnectCallback(MqttCallbackExtended callback) {
        this.callback.setReconnectCallback(callback);
    }

    public void setNetworkModuleIndex(int index) {
        this.networkModuleIndex = index;
    }

    public int getNetworkModuleIndex() {
        return this.networkModuleIndex;
    }

    public NetworkModule[] getNetworkModules() {
        return this.networkModules;
    }

    public void setNetworkModules(NetworkModule[] networkModules) {
        this.networkModules = networkModules;
    }

    protected void deliveryComplete(MqttPublish msg) throws MqttPersistenceException {
        this.clientState.deliveryComplete(msg);
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    public long getKeepAlive() {
        return this.clientState.getKeepAlive();
    }

    private class ConnectBG implements Runnable {
        Thread cBg;
        ClientComms clientComms;
        MqttConnect conPacket;
        MqttToken conToken;
        final ClientComms this$0;

        ConnectBG(ClientComms clientComms, ClientComms cc, MqttToken cToken, MqttConnect cPacket) {
            this.this$0 = clientComms;
            this.clientComms = null;
            this.cBg = null;
            this.clientComms = cc;
            this.conToken = cToken;
            this.conPacket = cPacket;
            this.cBg = new Thread(this, new StringBuffer("MQTT Con: ").append(clientComms.getClient().getClientId()).toString());
        }

        void start() {
            this.cBg.start();
        }

        @Override // java.lang.Runnable
        public void run() {
            MqttException mqttEx = null;
            ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "220");
            try {
                MqttDeliveryToken[] toks = this.this$0.tokenStore.getOutstandingDelTokens();
                for (MqttDeliveryToken mqttDeliveryToken : toks) {
                    mqttDeliveryToken.internalTok.setException(null);
                }
                this.this$0.tokenStore.saveToken(this.conToken, this.conPacket);
                NetworkModule networkModule = this.this$0.networkModules[this.this$0.networkModuleIndex];
                networkModule.start();
                this.this$0.receiver = new CommsReceiver(this.clientComms, this.this$0.clientState, this.this$0.tokenStore, networkModule.getInputStream());
                this.this$0.receiver.start(new StringBuffer("MQTT Rec: ").append(this.this$0.getClient().getClientId()).toString());
                this.this$0.sender = new CommsSender(this.clientComms, this.this$0.clientState, this.this$0.tokenStore, networkModule.getOutputStream());
                this.this$0.sender.start(new StringBuffer("MQTT Snd: ").append(this.this$0.getClient().getClientId()).toString());
                this.this$0.callback.start(new StringBuffer("MQTT Call: ").append(this.this$0.getClient().getClientId()).toString());
                this.this$0.internalSend(this.conPacket, this.conToken);
            } catch (MqttException ex) {
                ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "212", null, ex);
                mqttEx = ex;
            } catch (Exception ex2) {
                ClientComms.log.fine(ClientComms.CLASS_NAME, "connectBG:run", "209", null, ex2);
                mqttEx = ExceptionHelper.createMqttException(ex2);
            }
            if (mqttEx != null) {
                this.this$0.shutdownConnection(this.conToken, mqttEx);
            }
        }
    }

    private class DisconnectBG implements Runnable {
        Thread dBg = null;
        MqttDisconnect disconnect;
        long quiesceTimeout;
        final ClientComms this$0;
        MqttToken token;

        DisconnectBG(ClientComms clientComms, MqttDisconnect disconnect, long quiesceTimeout, MqttToken token) {
            this.this$0 = clientComms;
            this.disconnect = disconnect;
            this.quiesceTimeout = quiesceTimeout;
            this.token = token;
        }

        void start() {
            this.dBg = new Thread(this, new StringBuffer("MQTT Disc: ").append(this.this$0.getClient().getClientId()).toString());
            this.dBg.start();
        }

        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // java.lang.Runnable
        public void run() {
            ClientComms.log.fine(ClientComms.CLASS_NAME, "disconnectBG:run", "221");
            this.this$0.clientState.quiesce(this.quiesceTimeout);
            try {
                this.this$0.internalSend(this.disconnect, this.token);
                this.token.internalTok.waitUntilSent();
            } catch (MqttException e) {
            } finally {
                this.token.internalTok.markComplete(null, null);
                this.this$0.shutdownConnection(this.token, null);
            }
        }
    }

    public MqttToken checkForActivity(IMqttActionListener pingCallback) throws Throwable {
        try {
            MqttToken token = this.clientState.checkForActivity(pingCallback);
            return token;
        } catch (MqttException e) {
            handleRunException(e);
            return null;
        } catch (Exception e2) {
            handleRunException(e2);
            return null;
        }
    }

    private void handleRunException(Exception ex) {
        MqttException mex;
        log.fine(CLASS_NAME, "handleRunException", "804", null, ex);
        if (!(ex instanceof MqttException)) {
            mex = new MqttException(32109, ex);
        } else {
            mex = (MqttException) ex;
        }
        shutdownConnection(null, mex);
    }

    public void setRestingState(boolean resting) {
        this.resting = resting;
    }

    public void setDisconnectedMessageBuffer(DisconnectedMessageBuffer disconnectedMessageBuffer) {
        this.disconnectedMessageBuffer = disconnectedMessageBuffer;
    }

    public void notifyReconnect() {
        if (this.disconnectedMessageBuffer != null) {
            log.fine(CLASS_NAME, "notifyReconnect", "509");
            this.disconnectedMessageBuffer.setPublishCallback(new IDisconnectedBufferCallback(this) { // from class: org.eclipse.paho.client.mqttv3.internal.ClientComms.1
                final ClientComms this$0;

                {
                    this.this$0 = this;
                }

                @Override // org.eclipse.paho.client.mqttv3.internal.IDisconnectedBufferCallback
                public void publishBufferedMessage(BufferedMessage bufferedMessage) throws MqttException {
                    if (!this.this$0.isConnected()) {
                        ClientComms.log.fine(ClientComms.CLASS_NAME, "notifyReconnect", "208");
                        throw ExceptionHelper.createMqttException(32104);
                    }
                    while (this.this$0.clientState.getActualInFlight() >= this.this$0.clientState.getMaxInFlight() - 1) {
                        Thread.yield();
                    }
                    ClientComms.log.fine(ClientComms.CLASS_NAME, "notifyReconnect", "510", new Object[]{bufferedMessage.getMessage().getKey()});
                    this.this$0.internalSend(bufferedMessage.getMessage(), bufferedMessage.getToken());
                    this.this$0.clientState.unPersistBufferedMessage(bufferedMessage.getMessage());
                }
            });
            new Thread(this.disconnectedMessageBuffer).start();
        }
    }
}
