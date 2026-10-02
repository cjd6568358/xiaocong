package com.huawei.hms.update.b;

import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: compiled from: HttpsUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class c {
    public static void a(HttpsURLConnection httpsURLConnection) {
        SocketFactory socketFactoryA = e.a();
        if (socketFactoryA instanceof SSLSocketFactory) {
            httpsURLConnection.setSSLSocketFactory((SSLSocketFactory) socketFactoryA);
        }
    }
}
