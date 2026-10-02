package com.meizu.cloud.pushsdk.platform;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a {
    public static SSLSocketFactory a() {
        SSLContext sSLContext;
        NoSuchAlgorithmException e;
        KeyManagementException e2;
        try {
            sSLContext = SSLContext.getInstance("TLS");
            try {
                sSLContext.init(null, new TrustManager[]{new b()}, new SecureRandom());
            } catch (KeyManagementException e3) {
                e2 = e3;
                e2.printStackTrace();
            } catch (NoSuchAlgorithmException e4) {
                e = e4;
                e.printStackTrace();
            }
        } catch (KeyManagementException e5) {
            sSLContext = null;
            e2 = e5;
        } catch (NoSuchAlgorithmException e6) {
            sSLContext = null;
            e = e6;
        }
        return sSLContext.getSocketFactory();
    }

    public static HostnameVerifier b() {
        return new C0035a();
    }

    private static class b implements X509TrustManager {
        private b() {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /* JADX INFO: renamed from: com.meizu.cloud.pushsdk.platform.a$a, reason: collision with other inner class name */
    private static class C0035a implements HostnameVerifier {
        private C0035a() {
        }

        @Override // javax.net.ssl.HostnameVerifier
        public boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    }
}
