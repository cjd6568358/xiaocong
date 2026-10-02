package okhttp3.internal.platform;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.internal.tls.BasicCertificateChainCleaner;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.TrustRootIndex;
import okio.Buffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Platform {
    public static final int INFO = 4;
    public static final int WARN = 5;
    private static final Platform PLATFORM = findPlatform();
    private static final Logger logger = Logger.getLogger(OkHttpClient.class.getName());

    public static Platform get() {
        return PLATFORM;
    }

    public String getPrefix() {
        return "OkHttp";
    }

    public X509TrustManager trustManager(SSLSocketFactory sslSocketFactory) {
        try {
            Class<?> sslContextClass = Class.forName("sun.security.ssl.SSLContextImpl");
            Object context = readFieldOrNull(sslSocketFactory, sslContextClass, "context");
            if (context == null) {
                return null;
            }
            return (X509TrustManager) readFieldOrNull(context, X509TrustManager.class, "trustManager");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
    }

    public void afterHandshake(SSLSocket sslSocket) {
    }

    public String getSelectedProtocol(SSLSocket socket) {
        return null;
    }

    public void connectSocket(Socket socket, InetSocketAddress address, int connectTimeout) throws IOException {
        socket.connect(address, connectTimeout);
    }

    public void log(int level, String message, Throwable t) {
        Level logLevel = level == 5 ? Level.WARNING : Level.INFO;
        logger.log(logLevel, message, t);
    }

    public boolean isCleartextTrafficPermitted(String hostname) {
        return true;
    }

    public static List<String> alpnProtocolNames(List<Protocol> protocols) {
        List<String> names = new ArrayList<>(protocols.size());
        int size = protocols.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = protocols.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                names.add(protocol.toString());
            }
        }
        return names;
    }

    public CertificateChainCleaner buildCertificateChainCleaner(X509TrustManager trustManager) {
        return new BasicCertificateChainCleaner(TrustRootIndex.get(trustManager));
    }

    private static Platform findPlatform() {
        Platform android = AndroidPlatform.buildIfSupported();
        if (android == null) {
            Platform jdk9 = Jdk9Platform.buildIfSupported();
            if (jdk9 != null) {
                return jdk9;
            }
            Platform jdkWithJettyBoot = JdkWithJettyBootPlatform.buildIfSupported();
            return jdkWithJettyBoot != null ? jdkWithJettyBoot : new Platform();
        }
        return android;
    }

    static byte[] concatLengthPrefixed(List<Protocol> protocols) {
        Buffer result = new Buffer();
        int size = protocols.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = protocols.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                result.writeByte(protocol.toString().length());
                result.writeUtf8(protocol.toString());
            }
        }
        return result.readByteArray();
    }

    static <T> T readFieldOrNull(Object obj, Class<T> cls, String str) {
        Object fieldOrNull;
        T t = null;
        for (Class<?> superclass = obj.getClass(); superclass != Object.class; superclass = superclass.getSuperclass()) {
            try {
                Field declaredField = superclass.getDeclaredField(str);
                declaredField.setAccessible(true);
                Object obj2 = declaredField.get(obj);
                if (obj2 != null && cls.isInstance(obj2)) {
                    return cls.cast(obj2);
                }
                return t;
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            } catch (NoSuchFieldException e2) {
            }
        }
        return (str.equals("delegate") || (fieldOrNull = readFieldOrNull(obj, Object.class, "delegate")) == null) ? t : (T) readFieldOrNull(fieldOrNull, cls, str);
    }
}
