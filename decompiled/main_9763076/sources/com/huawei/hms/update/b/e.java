package com.huawei.hms.update.b;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* JADX INFO: compiled from: HttpsUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class e extends SSLSocketFactory {
    private static final Object a = new Object();
    private static SocketFactory c;
    private final SSLContext b = SSLContext.getInstance("TLSv1.2");

    private e() throws NoSuchAlgorithmException, KeyManagementException {
        this.b.init(null, null, null);
    }

    public static SocketFactory a() {
        SocketFactory socketFactory;
        synchronized (a) {
            try {
                if (c == null) {
                    c = new e();
                }
                socketFactory = c;
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                com.huawei.hms.support.log.a.d("TLSSocketFactory", "Failed to new TLSSocketFactory instance." + e.getMessage());
                socketFactory = SSLSocketFactory.getDefault();
            }
        }
        return socketFactory;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        Socket socketCreateSocket = this.b.getSocketFactory().createSocket(socket, str, i, z);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i) throws IOException {
        Socket socketCreateSocket = this.b.getSocketFactory().createSocket(str, i);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        Socket socketCreateSocket = this.b.getSocketFactory().createSocket(str, i, inetAddress, i2);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        Socket socketCreateSocket = this.b.getSocketFactory().createSocket(inetAddress, i);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        Socket socketCreateSocket = this.b.getSocketFactory().createSocket(inetAddress, i, inetAddress2, i2);
        a(socketCreateSocket);
        return socketCreateSocket;
    }

    private void a(Socket socket) {
        if (socket instanceof SSLSocket) {
            b((SSLSocket) socket);
            a((SSLSocket) socket);
        }
    }

    private void b(SSLSocket sSLSocket) {
        sSLSocket.setEnabledProtocols(new String[]{"TLSv1.2"});
    }

    public static void a(SSLSocket sSLSocket) {
        String[] enabledCipherSuites = sSLSocket.getEnabledCipherSuites();
        if (enabledCipherSuites != null && enabledCipherSuites.length != 0) {
            ArrayList arrayList = new ArrayList();
            for (String str : enabledCipherSuites) {
                if (!str.contains("RC2") && !str.contains("RC4") && !str.contains("DES") && !str.contains("MD2") && !str.contains("MD4") && !str.contains("MD5") && !str.contains("ANON") && !str.contains("NULL") && !str.contains("SKIPJACK") && !str.contains("SHA1")) {
                    arrayList.add(str);
                }
            }
            sSLSocket.setEnabledCipherSuites((String[]) arrayList.toArray(new String[arrayList.size()]));
        }
    }
}
