package org.eclipse.paho.client.mqttv3;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.ConnectActionListener;
import org.eclipse.paho.client.mqttv3.internal.DisconnectedMessageBuffer;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.internal.LocalNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.NetworkModule;
import org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;
import org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketSecureNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MqttAsyncClient implements IMqttAsyncClient {
    private static final String CLASS_NAME;
    static Class class$0;
    private static Object clientLock;
    private static final Logger log;
    private static int reconnectDelay;
    private String clientId;
    protected ClientComms comms;
    private MqttConnectOptions connOpts;
    private MqttCallback mqttCallback;
    private MqttClientPersistence persistence;
    private Timer reconnectTimer;
    private boolean reconnecting = false;
    private String serverURI;
    private Hashtable topics;
    private Object userContext;

    static {
        Class<?> cls = class$0;
        if (cls == null) {
            try {
                cls = Class.forName("org.eclipse.paho.client.mqttv3.MqttAsyncClient");
                class$0 = cls;
            } catch (ClassNotFoundException e) {
                throw new NoClassDefFoundError(e.getMessage());
            }
        }
        CLASS_NAME = cls.getName();
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
        reconnectDelay = 1000;
        clientLock = new Object();
    }

    public MqttAsyncClient(String serverURI, String clientId, MqttClientPersistence persistence, MqttPingSender pingSender) throws MqttException {
        log.setResourceName(clientId);
        if (clientId == null) {
            throw new IllegalArgumentException("Null clientId");
        }
        int clientIdLength = 0;
        int i = 0;
        while (i < clientId.length() - 1) {
            if (Character_isHighSurrogate(clientId.charAt(i))) {
                i++;
            }
            clientIdLength++;
            i++;
        }
        if (clientIdLength > 65535) {
            throw new IllegalArgumentException("ClientId longer than 65535 characters");
        }
        MqttConnectOptions.validateURI(serverURI);
        this.serverURI = serverURI;
        this.clientId = clientId;
        this.persistence = persistence;
        if (this.persistence == null) {
            this.persistence = new MemoryPersistence();
        }
        log.fine(CLASS_NAME, "MqttAsyncClient", "101", new Object[]{clientId, serverURI, persistence});
        this.persistence.open(clientId, serverURI);
        this.comms = new ClientComms(this, this.persistence, pingSender);
        this.persistence.close();
        this.topics = new Hashtable();
    }

    protected static boolean Character_isHighSurrogate(char ch) {
        return ch >= 55296 && ch <= 56319;
    }

    protected NetworkModule[] createNetworkModules(String address, MqttConnectOptions options) throws MqttException {
        String[] array;
        log.fine(CLASS_NAME, "createNetworkModules", "116", new Object[]{address});
        String[] serverURIs = options.getServerURIs();
        if (serverURIs == null || serverURIs.length == 0) {
            array = new String[]{address};
        } else {
            array = serverURIs;
        }
        NetworkModule[] networkModules = new NetworkModule[array.length];
        for (int i = 0; i < array.length; i++) {
            networkModules[i] = createNetworkModule(array[i], options);
        }
        log.fine(CLASS_NAME, "createNetworkModules", "108");
        return networkModules;
    }

    private NetworkModule createNetworkModule(String address, MqttConnectOptions options) throws MqttException {
        String[] enabledCiphers;
        String[] enabledCiphers2;
        log.fine(CLASS_NAME, "createNetworkModule", "115", new Object[]{address});
        SocketFactory factory = options.getSocketFactory();
        int serverURIType = MqttConnectOptions.validateURI(address);
        try {
            URI uri = new URI(address);
            String host = uri.getHost();
            int port = uri.getPort();
            switch (serverURIType) {
                case 0:
                    if (port == -1) {
                        port = 1883;
                    }
                    if (factory == null) {
                        factory = SocketFactory.getDefault();
                    } else if (factory instanceof SSLSocketFactory) {
                        throw ExceptionHelper.createMqttException(32105);
                    }
                    NetworkModule netModule = new TCPNetworkModule(factory, host, port, this.clientId);
                    ((TCPNetworkModule) netModule).setConnectTimeout(options.getConnectionTimeout());
                    return netModule;
                case 1:
                    if (port == -1) {
                        port = 8883;
                    }
                    SSLSocketFactoryFactory factoryFactory = null;
                    if (factory == null) {
                        factoryFactory = new SSLSocketFactoryFactory();
                        Properties sslClientProps = options.getSSLProperties();
                        if (sslClientProps != null) {
                            factoryFactory.initialize(sslClientProps, null);
                        }
                        factory = factoryFactory.createSocketFactory(null);
                    } else if (!(factory instanceof SSLSocketFactory)) {
                        throw ExceptionHelper.createMqttException(32105);
                    }
                    NetworkModule netModule2 = new SSLNetworkModule((SSLSocketFactory) factory, host, port, this.clientId);
                    ((SSLNetworkModule) netModule2).setSSLhandshakeTimeout(options.getConnectionTimeout());
                    if (factoryFactory != null && (enabledCiphers2 = factoryFactory.getEnabledCipherSuites(null)) != null) {
                        ((SSLNetworkModule) netModule2).setEnabledCiphers(enabledCiphers2);
                        return netModule2;
                    }
                    return netModule2;
                case 2:
                    return new LocalNetworkModule(address.substring(8));
                case 3:
                    if (port == -1) {
                        port = 80;
                    }
                    if (factory == null) {
                        factory = SocketFactory.getDefault();
                    } else if (factory instanceof SSLSocketFactory) {
                        throw ExceptionHelper.createMqttException(32105);
                    }
                    NetworkModule netModule3 = new WebSocketNetworkModule(factory, address, host, port, this.clientId);
                    ((WebSocketNetworkModule) netModule3).setConnectTimeout(options.getConnectionTimeout());
                    return netModule3;
                case 4:
                    if (port == -1) {
                        port = 443;
                    }
                    SSLSocketFactoryFactory wSSFactoryFactory = null;
                    if (factory == null) {
                        wSSFactoryFactory = new SSLSocketFactoryFactory();
                        Properties sslClientProps2 = options.getSSLProperties();
                        if (sslClientProps2 != null) {
                            wSSFactoryFactory.initialize(sslClientProps2, null);
                        }
                        factory = wSSFactoryFactory.createSocketFactory(null);
                    } else if (!(factory instanceof SSLSocketFactory)) {
                        throw ExceptionHelper.createMqttException(32105);
                    }
                    NetworkModule netModule4 = new WebSocketSecureNetworkModule((SSLSocketFactory) factory, address, host, port, this.clientId);
                    ((WebSocketSecureNetworkModule) netModule4).setSSLhandshakeTimeout(options.getConnectionTimeout());
                    if (wSSFactoryFactory != null && (enabledCiphers = wSSFactoryFactory.getEnabledCipherSuites(null)) != null) {
                        ((SSLNetworkModule) netModule4).setEnabledCiphers(enabledCiphers);
                        return netModule4;
                    }
                    return netModule4;
                default:
                    log.fine(CLASS_NAME, "createNetworkModule", "119", new Object[]{address});
                    return null;
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(new StringBuffer("Malformed URI: ").append(address).append(", ").append(e.getMessage()).toString());
        }
    }

    public IMqttToken connect(MqttConnectOptions options, Object userContext, IMqttActionListener callback) throws MqttException {
        if (this.comms.isConnected()) {
            throw ExceptionHelper.createMqttException(32100);
        }
        if (this.comms.isConnecting()) {
            throw new MqttException(32110);
        }
        if (this.comms.isDisconnecting()) {
            throw new MqttException(32102);
        }
        if (this.comms.isClosed()) {
            throw new MqttException(32111);
        }
        if (options == null) {
            options = new MqttConnectOptions();
        }
        this.connOpts = options;
        this.userContext = userContext;
        boolean automaticReconnect = options.isAutomaticReconnect();
        Logger logger = log;
        String str = CLASS_NAME;
        Object[] objArr = new Object[8];
        objArr[0] = Boolean.valueOf(options.isCleanSession());
        objArr[1] = new Integer(options.getConnectionTimeout());
        objArr[2] = new Integer(options.getKeepAliveInterval());
        objArr[3] = options.getUserName();
        objArr[4] = options.getPassword() == null ? "[null]" : "[notnull]";
        objArr[5] = options.getWillMessage() == null ? "[null]" : "[notnull]";
        objArr[6] = userContext;
        objArr[7] = callback;
        logger.fine(str, "connect", "103", objArr);
        this.comms.setNetworkModules(createNetworkModules(this.serverURI, options));
        this.comms.setReconnectCallback(new MqttCallbackExtended(this, automaticReconnect) { // from class: org.eclipse.paho.client.mqttv3.MqttAsyncClient.1
            final MqttAsyncClient this$0;
            private final boolean val$automaticReconnect;

            {
                this.this$0 = this;
                this.val$automaticReconnect = automaticReconnect;
            }

            @Override // org.eclipse.paho.client.mqttv3.MqttCallback
            public void messageArrived(String topic, MqttMessage message) throws Exception {
            }

            @Override // org.eclipse.paho.client.mqttv3.MqttCallback
            public void deliveryComplete(IMqttDeliveryToken token) {
            }

            @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
            public void connectComplete(boolean reconnect, String serverURI) {
            }

            @Override // org.eclipse.paho.client.mqttv3.MqttCallback
            public void connectionLost(Throwable cause) {
                if (this.val$automaticReconnect) {
                    this.this$0.comms.setRestingState(true);
                    this.this$0.reconnecting = true;
                    this.this$0.startReconnectCycle();
                }
            }
        });
        MqttToken userToken = new MqttToken(getClientId());
        ConnectActionListener connectActionListener = new ConnectActionListener(this, this.persistence, this.comms, options, userToken, userContext, callback, this.reconnecting);
        userToken.setActionCallback(connectActionListener);
        userToken.setUserContext(this);
        if (this.mqttCallback instanceof MqttCallbackExtended) {
            connectActionListener.setMqttCallbackExtended((MqttCallbackExtended) this.mqttCallback);
        }
        this.comms.setNetworkModuleIndex(0);
        connectActionListener.connect();
        return userToken;
    }

    public IMqttToken disconnect(Object userContext, IMqttActionListener callback) throws MqttException {
        return disconnect(30000L, userContext, callback);
    }

    public IMqttToken disconnect(long quiesceTimeout, Object userContext, IMqttActionListener callback) throws MqttException {
        log.fine(CLASS_NAME, "disconnect", "104", new Object[]{new Long(quiesceTimeout), userContext, callback});
        MqttToken token = new MqttToken(getClientId());
        token.setActionCallback(callback);
        token.setUserContext(userContext);
        MqttDisconnect disconnect = new MqttDisconnect();
        try {
            this.comms.disconnect(disconnect, quiesceTimeout, token);
            log.fine(CLASS_NAME, "disconnect", "108");
            return token;
        } catch (MqttException ex) {
            log.fine(CLASS_NAME, "disconnect", "105", null, ex);
            throw ex;
        }
    }

    public boolean isConnected() {
        return this.comms.isConnected();
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public String getClientId() {
        return this.clientId;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttAsyncClient
    public String getServerURI() {
        return this.serverURI;
    }

    public void setCallback(MqttCallback callback) {
        this.mqttCallback = callback;
        this.comms.setCallback(callback);
    }

    public IMqttDeliveryToken publish(String topic, MqttMessage message, Object userContext, IMqttActionListener callback) throws MqttException {
        log.fine(CLASS_NAME, "publish", "111", new Object[]{topic, userContext, callback});
        MqttTopic.validate(topic, false);
        MqttDeliveryToken token = new MqttDeliveryToken(getClientId());
        token.setActionCallback(callback);
        token.setUserContext(userContext);
        token.setMessage(message);
        token.internalTok.setTopics(new String[]{topic});
        MqttPublish pubMsg = new MqttPublish(topic, message);
        this.comms.sendNoWait(pubMsg, token);
        log.fine(CLASS_NAME, "publish", "112");
        return token;
    }

    public void reconnect() throws MqttException {
        log.fine(CLASS_NAME, "reconnect", "500", new Object[]{this.clientId});
        if (this.comms.isConnected()) {
            throw ExceptionHelper.createMqttException(32100);
        }
        if (this.comms.isConnecting()) {
            throw new MqttException(32110);
        }
        if (this.comms.isDisconnecting()) {
            throw new MqttException(32102);
        }
        if (this.comms.isClosed()) {
            throw new MqttException(32111);
        }
        stopReconnectCycle();
        attemptReconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attemptReconnect() {
        log.fine(CLASS_NAME, "attemptReconnect", "500", new Object[]{this.clientId});
        try {
            connect(this.connOpts, this.userContext, new IMqttActionListener(this) { // from class: org.eclipse.paho.client.mqttv3.MqttAsyncClient.2
                final MqttAsyncClient this$0;

                {
                    this.this$0 = this;
                }

                @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onSuccess(IMqttToken asyncActionToken) {
                    MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, "attemptReconnect", "501", new Object[]{asyncActionToken.getClient().getClientId()});
                    this.this$0.comms.setRestingState(false);
                    this.this$0.stopReconnectCycle();
                }

                @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, "attemptReconnect", "502", new Object[]{asyncActionToken.getClient().getClientId()});
                    if (MqttAsyncClient.reconnectDelay < 128000) {
                        MqttAsyncClient.reconnectDelay *= 2;
                    }
                    this.this$0.rescheduleReconnectCycle(MqttAsyncClient.reconnectDelay);
                }
            });
        } catch (MqttSecurityException ex) {
            log.fine(CLASS_NAME, "attemptReconnect", "804", null, ex);
        } catch (MqttException ex2) {
            log.fine(CLASS_NAME, "attemptReconnect", "804", null, ex2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startReconnectCycle() {
        log.fine(CLASS_NAME, "startReconnectCycle", "503", new Object[]{this.clientId, new Long(reconnectDelay)});
        this.reconnectTimer = new Timer();
        this.reconnectTimer.schedule(new ReconnectTask(this, null), reconnectDelay);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopReconnectCycle() {
        log.fine(CLASS_NAME, "stopReconnectCycle", "504", new Object[]{this.clientId});
        synchronized (clientLock) {
            if (this.connOpts.isAutomaticReconnect()) {
                if (this.reconnectTimer != null) {
                    this.reconnectTimer.cancel();
                    this.reconnectTimer = null;
                }
                reconnectDelay = 1000;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rescheduleReconnectCycle(int delay) {
        log.fine(CLASS_NAME, "rescheduleReconnectCycle", "505", new Object[]{this.clientId, new Long(reconnectDelay)});
        synchronized (clientLock) {
            if (this.connOpts.isAutomaticReconnect()) {
                if (this.reconnectTimer != null) {
                    this.reconnectTimer.schedule(new ReconnectTask(this, null), delay);
                } else {
                    reconnectDelay = delay;
                    startReconnectCycle();
                }
            }
        }
    }

    private class ReconnectTask extends TimerTask {
        final MqttAsyncClient this$0;

        private ReconnectTask(MqttAsyncClient mqttAsyncClient) {
            this.this$0 = mqttAsyncClient;
        }

        ReconnectTask(MqttAsyncClient mqttAsyncClient, ReconnectTask reconnectTask) {
            this(mqttAsyncClient);
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            MqttAsyncClient.log.fine(MqttAsyncClient.CLASS_NAME, "ReconnectTask.run", "506");
            this.this$0.attemptReconnect();
        }
    }

    public void setBufferOpts(DisconnectedBufferOptions bufferOpts) {
        this.comms.setDisconnectedMessageBuffer(new DisconnectedMessageBuffer(bufferOpts));
    }
}
