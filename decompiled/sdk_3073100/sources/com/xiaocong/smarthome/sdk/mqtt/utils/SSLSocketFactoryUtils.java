package com.xiaocong.smarthome.sdk.mqtt.utils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SSLSocketFactoryUtils {
    public static SSLSocketFactory initSSLSocket(InputStream certFileName) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
        X509Certificate caCert = (X509Certificate) cf.generateCertificate(certFileName);
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("certificate", caCert);
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);
        SSLContext context = SSLContext.getInstance("TLSv1.2");
        context.init(null, tmf.getTrustManagers(), null);
        SSLSocketFactory socketFactory = context.getSocketFactory();
        return socketFactory;
    }
}
