package org.eclipse.paho.client.mqttv3.internal.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.logging.Logger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SSLSocketFactoryFactory {
    private Properties defaultProperties;
    private static final String[] propertyKeys = {"com.ibm.ssl.protocol", "com.ibm.ssl.contextProvider", "com.ibm.ssl.keyStore", "com.ibm.ssl.keyStorePassword", "com.ibm.ssl.keyStoreType", "com.ibm.ssl.keyStoreProvider", "com.ibm.ssl.keyManager", "com.ibm.ssl.trustStore", "com.ibm.ssl.trustStorePassword", "com.ibm.ssl.trustStoreType", "com.ibm.ssl.trustStoreProvider", "com.ibm.ssl.trustManager", "com.ibm.ssl.enabledCipherSuites", "com.ibm.ssl.clientAuthentication"};
    private static final byte[] key = {-99, -89, -39, -128, 5, -72, -119, -100};
    private Logger logger = null;
    private Hashtable configs = new Hashtable();

    private boolean keyValid(String key2) {
        int i = 0;
        while (i < propertyKeys.length && !propertyKeys[i].equals(key2)) {
            i++;
        }
        return i < propertyKeys.length;
    }

    private void checkPropertyKeys(Properties properties) throws IllegalArgumentException {
        Set<String> keys = properties.keySet();
        for (String k : keys) {
            if (!keyValid(k)) {
                throw new IllegalArgumentException(new StringBuffer(String.valueOf(k)).append(" is not a valid IBM SSL property key.").toString());
            }
        }
    }

    public static char[] toChar(byte[] b) {
        if (b == null) {
            return null;
        }
        char[] c = new char[b.length / 2];
        int i = 0;
        int j = 0;
        while (i < b.length) {
            int i2 = i + 1;
            int i3 = b[i] & 255;
            i = i2 + 1;
            c[j] = (char) (i3 + ((b[i2] & 255) << 8));
            j++;
        }
        return c;
    }

    public static byte[] toByte(char[] c) {
        if (c == null) {
            return null;
        }
        byte[] b = new byte[c.length * 2];
        int i = 0;
        for (int j = 0; j < c.length; j++) {
            int i2 = i + 1;
            b[i] = (byte) (c[j] & 255);
            i = i2 + 1;
            b[i2] = (byte) ((c[j] >> '\b') & 255);
        }
        return b;
    }

    public static String obfuscate(char[] password) {
        if (password == null) {
            return null;
        }
        byte[] bytes = toByte(password);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((bytes[i] ^ key[i % key.length]) & 255);
        }
        return new StringBuffer("{xor}").append(new String(SimpleBase64Encoder.encode(bytes))).toString();
    }

    public static char[] deObfuscate(String ePassword) {
        if (ePassword == null) {
            return null;
        }
        try {
            byte[] bytes = SimpleBase64Encoder.decode(ePassword.substring("{xor}".length()));
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) ((bytes[i] ^ key[i % key.length]) & 255);
            }
            return toChar(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] unpackCipherSuites(String ciphers) {
        if (ciphers == null) {
            return null;
        }
        Vector c = new Vector();
        int i = ciphers.indexOf(44);
        int j = 0;
        while (i > -1) {
            c.add(ciphers.substring(j, i));
            j = i + 1;
            i = ciphers.indexOf(44, j);
        }
        c.add(ciphers.substring(j));
        String[] s = new String[c.size()];
        c.toArray(s);
        return s;
    }

    private void convertPassword(Properties p) {
        String pw = p.getProperty("com.ibm.ssl.keyStorePassword");
        if (pw != null && !pw.startsWith("{xor}")) {
            String epw = obfuscate(pw.toCharArray());
            p.put("com.ibm.ssl.keyStorePassword", epw);
        }
        String pw2 = p.getProperty("com.ibm.ssl.trustStorePassword");
        if (pw2 != null && !pw2.startsWith("{xor}")) {
            String epw2 = obfuscate(pw2.toCharArray());
            p.put("com.ibm.ssl.trustStorePassword", epw2);
        }
    }

    public void initialize(Properties props, String configID) throws IllegalArgumentException {
        checkPropertyKeys(props);
        Properties p = new Properties();
        p.putAll(props);
        convertPassword(p);
        if (configID != null) {
            this.configs.put(configID, p);
        } else {
            this.defaultProperties = p;
        }
    }

    private String getProperty(String configID, String ibmKey, String sysProperty) {
        String res = getPropertyFromConfig(configID, ibmKey);
        if (res != null) {
            return res;
        }
        if (sysProperty != null) {
            res = System.getProperty(sysProperty);
        }
        return res;
    }

    private String getPropertyFromConfig(String configID, String ibmKey) {
        String res = null;
        Properties p = null;
        if (configID != null) {
            p = (Properties) this.configs.get(configID);
        }
        if (p != null && (res = p.getProperty(ibmKey)) != null) {
            return res;
        }
        Properties p2 = this.defaultProperties;
        return (p2 == null || (res = p2.getProperty(ibmKey)) == null) ? res : res;
    }

    public String getSSLProtocol(String configID) {
        return getProperty(configID, "com.ibm.ssl.protocol", null);
    }

    public String getJSSEProvider(String configID) {
        return getProperty(configID, "com.ibm.ssl.contextProvider", null);
    }

    public char[] getKeyStorePassword(String configID) {
        String pw = getProperty(configID, "com.ibm.ssl.keyStorePassword", "javax.net.ssl.keyStorePassword");
        if (pw == null) {
            return null;
        }
        if (pw.startsWith("{xor}")) {
            char[] r = deObfuscate(pw);
            return r;
        }
        char[] r2 = pw.toCharArray();
        return r2;
    }

    public String getKeyStoreType(String configID) {
        return getProperty(configID, "com.ibm.ssl.keyStoreType", "javax.net.ssl.keyStoreType");
    }

    public String getKeyStoreProvider(String configID) {
        return getProperty(configID, "com.ibm.ssl.keyStoreProvider", null);
    }

    public String getKeyManager(String configID) {
        return getProperty(configID, "com.ibm.ssl.keyManager", "ssl.KeyManagerFactory.algorithm");
    }

    public String getTrustStore(String configID) {
        return getProperty(configID, "com.ibm.ssl.trustStore", "javax.net.ssl.trustStore");
    }

    public char[] getTrustStorePassword(String configID) {
        String pw = getProperty(configID, "com.ibm.ssl.trustStorePassword", "javax.net.ssl.trustStorePassword");
        if (pw == null) {
            return null;
        }
        if (pw.startsWith("{xor}")) {
            char[] r = deObfuscate(pw);
            return r;
        }
        char[] r2 = pw.toCharArray();
        return r2;
    }

    public String getTrustStoreType(String configID) {
        return getProperty(configID, "com.ibm.ssl.trustStoreType", null);
    }

    public String getTrustStoreProvider(String configID) {
        return getProperty(configID, "com.ibm.ssl.trustStoreProvider", null);
    }

    public String getTrustManager(String configID) {
        return getProperty(configID, "com.ibm.ssl.trustManager", "ssl.TrustManagerFactory.algorithm");
    }

    public String[] getEnabledCipherSuites(String configID) {
        String ciphers = getProperty(configID, "com.ibm.ssl.enabledCipherSuites", null);
        String[] res = unpackCipherSuites(ciphers);
        return res;
    }

    private SSLContext getSSLContext(String configID) throws MqttSecurityException {
        SSLContext ctx;
        TrustManagerFactory trustMgrFact;
        KeyManagerFactory keyMgrFact;
        String protocol = getSSLProtocol(configID);
        if (protocol == null) {
            protocol = "TLS";
        }
        if (this.logger != null) {
            Logger logger = this.logger;
            Object[] objArr = new Object[2];
            objArr[0] = configID != null ? configID : "null (broker defaults)";
            objArr[1] = protocol;
            logger.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12000", objArr);
        }
        String provider = getJSSEProvider(configID);
        try {
            if (provider == null) {
                ctx = SSLContext.getInstance(protocol);
            } else {
                ctx = SSLContext.getInstance(protocol, provider);
            }
            if (this.logger != null) {
                Logger logger2 = this.logger;
                Object[] objArr2 = new Object[2];
                objArr2[0] = configID != null ? configID : "null (broker defaults)";
                objArr2[1] = ctx.getProvider().getName();
                logger2.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12001", objArr2);
            }
            String keyStoreName = getProperty(configID, "com.ibm.ssl.keyStore", null);
            KeyManager[] keyMgr = null;
            if (0 == 0) {
                if (keyStoreName == null) {
                    keyStoreName = getProperty(configID, "com.ibm.ssl.keyStore", "javax.net.ssl.keyStore");
                }
                if (this.logger != null) {
                    Logger logger3 = this.logger;
                    Object[] objArr3 = new Object[2];
                    objArr3[0] = configID != null ? configID : "null (broker defaults)";
                    objArr3[1] = keyStoreName != null ? keyStoreName : "null";
                    logger3.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12004", objArr3);
                }
                char[] keyStorePwd = getKeyStorePassword(configID);
                if (this.logger != null) {
                    Logger logger4 = this.logger;
                    Object[] objArr4 = new Object[2];
                    objArr4[0] = configID != null ? configID : "null (broker defaults)";
                    objArr4[1] = keyStorePwd != null ? obfuscate(keyStorePwd) : "null";
                    logger4.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12005", objArr4);
                }
                String keyStoreType = getKeyStoreType(configID);
                if (keyStoreType == null) {
                    keyStoreType = KeyStore.getDefaultType();
                }
                if (this.logger != null) {
                    Logger logger5 = this.logger;
                    Object[] objArr5 = new Object[2];
                    objArr5[0] = configID != null ? configID : "null (broker defaults)";
                    objArr5[1] = keyStoreType != null ? keyStoreType : "null";
                    logger5.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12006", objArr5);
                }
                String keyMgrAlgo = KeyManagerFactory.getDefaultAlgorithm();
                String keyMgrProvider = getKeyStoreProvider(configID);
                String keyManager = getKeyManager(configID);
                if (keyManager != null) {
                    keyMgrAlgo = keyManager;
                }
                if (keyStoreName != null && keyStoreType != null && keyMgrAlgo != null) {
                    try {
                        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                        keyStore.load(new FileInputStream(keyStoreName), keyStorePwd);
                        if (keyMgrProvider != null) {
                            keyMgrFact = KeyManagerFactory.getInstance(keyMgrAlgo, keyMgrProvider);
                        } else {
                            keyMgrFact = KeyManagerFactory.getInstance(keyMgrAlgo);
                        }
                        if (this.logger != null) {
                            Logger logger6 = this.logger;
                            Object[] objArr6 = new Object[2];
                            objArr6[0] = configID != null ? configID : "null (broker defaults)";
                            if (keyMgrAlgo == null) {
                                keyMgrAlgo = "null";
                            }
                            objArr6[1] = keyMgrAlgo;
                            logger6.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12010", objArr6);
                            Logger logger7 = this.logger;
                            Object[] objArr7 = new Object[2];
                            objArr7[0] = configID != null ? configID : "null (broker defaults)";
                            objArr7[1] = keyMgrFact.getProvider().getName();
                            logger7.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12009", objArr7);
                        }
                        keyMgrFact.init(keyStore, keyStorePwd);
                        keyMgr = keyMgrFact.getKeyManagers();
                    } catch (FileNotFoundException e) {
                        throw new MqttSecurityException(e);
                    } catch (IOException e2) {
                        throw new MqttSecurityException(e2);
                    } catch (KeyStoreException e3) {
                        throw new MqttSecurityException(e3);
                    } catch (UnrecoverableKeyException e4) {
                        throw new MqttSecurityException(e4);
                    } catch (CertificateException e5) {
                        throw new MqttSecurityException(e5);
                    }
                }
            }
            String trustStoreName = getTrustStore(configID);
            if (this.logger != null) {
                Logger logger8 = this.logger;
                Object[] objArr8 = new Object[2];
                objArr8[0] = configID != null ? configID : "null (broker defaults)";
                objArr8[1] = trustStoreName != null ? trustStoreName : "null";
                logger8.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12011", objArr8);
            }
            TrustManager[] trustMgr = null;
            char[] trustStorePwd = getTrustStorePassword(configID);
            if (this.logger != null) {
                Logger logger9 = this.logger;
                Object[] objArr9 = new Object[2];
                objArr9[0] = configID != null ? configID : "null (broker defaults)";
                objArr9[1] = trustStorePwd != null ? obfuscate(trustStorePwd) : "null";
                logger9.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12012", objArr9);
            }
            String trustStoreType = getTrustStoreType(configID);
            if (trustStoreType == null) {
                trustStoreType = KeyStore.getDefaultType();
            }
            if (this.logger != null) {
                Logger logger10 = this.logger;
                Object[] objArr10 = new Object[2];
                objArr10[0] = configID != null ? configID : "null (broker defaults)";
                objArr10[1] = trustStoreType != null ? trustStoreType : "null";
                logger10.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12013", objArr10);
            }
            String trustMgrAlgo = TrustManagerFactory.getDefaultAlgorithm();
            String trustMgrProvider = getTrustStoreProvider(configID);
            String trustManager = getTrustManager(configID);
            if (trustManager != null) {
                trustMgrAlgo = trustManager;
            }
            if (trustStoreName != null && trustStoreType != null && trustMgrAlgo != null) {
                try {
                    KeyStore trustStore = KeyStore.getInstance(trustStoreType);
                    trustStore.load(new FileInputStream(trustStoreName), trustStorePwd);
                    if (trustMgrProvider != null) {
                        trustMgrFact = TrustManagerFactory.getInstance(trustMgrAlgo, trustMgrProvider);
                    } else {
                        trustMgrFact = TrustManagerFactory.getInstance(trustMgrAlgo);
                    }
                    if (this.logger != null) {
                        Logger logger11 = this.logger;
                        Object[] objArr11 = new Object[2];
                        objArr11[0] = configID != null ? configID : "null (broker defaults)";
                        if (trustMgrAlgo == null) {
                            trustMgrAlgo = "null";
                        }
                        objArr11[1] = trustMgrAlgo;
                        logger11.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12017", objArr11);
                        Logger logger12 = this.logger;
                        Object[] objArr12 = new Object[2];
                        if (configID == null) {
                            configID = "null (broker defaults)";
                        }
                        objArr12[0] = configID;
                        objArr12[1] = trustMgrFact.getProvider().getName();
                        logger12.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "getSSLContext", "12016", objArr12);
                    }
                    trustMgrFact.init(trustStore);
                    trustMgr = trustMgrFact.getTrustManagers();
                } catch (FileNotFoundException e6) {
                    throw new MqttSecurityException(e6);
                } catch (IOException e7) {
                    throw new MqttSecurityException(e7);
                } catch (KeyStoreException e8) {
                    throw new MqttSecurityException(e8);
                } catch (CertificateException e9) {
                    throw new MqttSecurityException(e9);
                }
            }
            ctx.init(keyMgr, trustMgr, null);
            return ctx;
        } catch (KeyManagementException e10) {
            throw new MqttSecurityException(e10);
        } catch (NoSuchAlgorithmException e11) {
            throw new MqttSecurityException(e11);
        } catch (NoSuchProviderException e12) {
            throw new MqttSecurityException(e12);
        }
    }

    public SSLSocketFactory createSocketFactory(String configID) throws MqttSecurityException {
        SSLContext ctx = getSSLContext(configID);
        if (this.logger != null) {
            Logger logger = this.logger;
            Object[] objArr = new Object[2];
            objArr[0] = configID != null ? configID : "null (broker defaults)";
            objArr[1] = getEnabledCipherSuites(configID) != null ? getProperty(configID, "com.ibm.ssl.enabledCipherSuites", null) : "null (using platform-enabled cipher suites)";
            logger.fine("org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory", "createSocketFactory", "12020", objArr);
        }
        return ctx.getSocketFactory();
    }
}
