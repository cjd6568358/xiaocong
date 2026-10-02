package com.xiaocong.smarthome.network.util;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCMyTrustManager implements X509TrustManager {
    private X509Certificate[] acceptedIssuers;
    private X509TrustManager defaultTrustManager;

    static X509TrustManager findX509TrustManager(TrustManagerFactory tmf) {
        TrustManager[] tms = tmf.getTrustManagers();
        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                return (X509TrustManager) tms[i];
            }
        }
        return null;
    }

    public static XCMyTrustManager getInstance(KeyStore truststore) {
        return new XCMyTrustManager(truststore);
    }

    private XCMyTrustManager(KeyStore truststore) {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(truststore);
            this.defaultTrustManager = findX509TrustManager(tmf);
            if (this.defaultTrustManager == null) {
                throw new IllegalStateException("Couldn't find X509TrustManager");
            }
            this.acceptedIssuers = null;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            this.defaultTrustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException e) {
            System.out.println("-----------------");
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            this.defaultTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException localCertificateException) {
            localCertificateException.printStackTrace();
            System.out.println("-----------------");
            throw localCertificateException;
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public X509Certificate[] getAcceptedIssuers() {
        return this.acceptedIssuers;
    }
}
