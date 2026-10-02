package com.xiaocong.smarthome.network.httplib;

import com.xiaocong.smarthome.network.util.XCMyTrustManager;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MySSLSocketFactory extends SSLSocketFactory {
    SSLContext sslContext;

    public MySSLSocketFactory(KeyStore truststore, boolean allTrust) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException, KeyStoreException {
        super(truststore);
        this.sslContext = SSLContext.getInstance("TLS");
        if (allTrust) {
            X509TrustManager tm = new X509TrustManager() { // from class: com.xiaocong.smarthome.network.httplib.MySSLSocketFactory.1
                @Override // javax.net.ssl.X509TrustManager
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override // javax.net.ssl.X509TrustManager
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override // javax.net.ssl.X509TrustManager
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            this.sslContext.init(null, new TrustManager[]{tm}, null);
        } else {
            this.sslContext.init(null, new TrustManager[]{XCMyTrustManager.getInstance(truststore)}, null);
        }
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.LayeredSocketFactory
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        return this.sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    @Override // org.apache.http.conn.ssl.SSLSocketFactory, org.apache.http.conn.scheme.SocketFactory
    public Socket createSocket() throws IOException {
        return this.sslContext.getSocketFactory().createSocket();
    }

    public void fixHttpsURLConnection() {
        HttpsURLConnection.setDefaultSSLSocketFactory(this.sslContext.getSocketFactory());
    }

    public static KeyStore getKeystoreOfCA(InputStream cert) throws Throwable {
        InputStream caInput = null;
        Certificate ca = null;
        try {
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                InputStream caInput2 = new BufferedInputStream(cert);
                try {
                    ca = cf.generateCertificate(caInput2);
                    if (caInput2 != null) {
                        try {
                            caInput2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            caInput = caInput2;
                        }
                    }
                    caInput = caInput2;
                } catch (CertificateException e2) {
                    e1 = e2;
                    caInput = caInput2;
                    e1.printStackTrace();
                    if (caInput != null) {
                        try {
                            caInput.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    caInput = caInput2;
                    if (caInput != null) {
                        try {
                            caInput.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (CertificateException e5) {
            e1 = e5;
        }
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            return keyStore;
        } catch (Exception e6) {
            e6.printStackTrace();
            return keyStore;
        }
    }

    public static KeyStore getKeystore() {
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            return trustStore;
        } catch (Throwable t) {
            t.printStackTrace();
            return trustStore;
        }
    }

    public static SSLSocketFactory getFixedSocketFactory(String url) {
        SSLSocketFactory socketFactory = null;
        try {
            SSLSocketFactory socketFactory2 = new MySSLSocketFactory(null, false);
            try {
                HostnameVerifier hostnameVerifier = SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
                socketFactory2.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
                return socketFactory2;
            } catch (Exception e) {
                e = e;
                socketFactory = socketFactory2;
                e.printStackTrace();
                return socketFactory;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static DefaultHttpClient getNewHttpClient(KeyStore keyStore) {
        try {
            SSLSocketFactory sf = new MySSLSocketFactory(keyStore, false);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, AsyncHttpResponseHandler.DEFAULT_CHARSET);
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }
}
