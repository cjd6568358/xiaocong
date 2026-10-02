package okhttp3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class JavaNetAuthenticator implements Authenticator {
    @Override // okhttp3.Authenticator
    public Request authenticate(Route route, Response response) throws IOException {
        PasswordAuthentication auth;
        List<Challenge> challenges = response.challenges();
        Request request = response.request();
        HttpUrl url = request.url();
        boolean proxyAuthorization = response.code() == 407;
        Proxy proxy = route.proxy();
        int size = challenges.size();
        for (int i = 0; i < size; i++) {
            Challenge challenge = challenges.get(i);
            if ("Basic".equalsIgnoreCase(challenge.scheme())) {
                if (proxyAuthorization) {
                    InetSocketAddress proxyAddress = (InetSocketAddress) proxy.address();
                    auth = java.net.Authenticator.requestPasswordAuthentication(proxyAddress.getHostName(), getConnectToInetAddress(proxy, url), proxyAddress.getPort(), url.scheme(), challenge.realm(), challenge.scheme(), url.url(), java.net.Authenticator.RequestorType.PROXY);
                } else {
                    auth = java.net.Authenticator.requestPasswordAuthentication(url.host(), getConnectToInetAddress(proxy, url), url.port(), url.scheme(), challenge.realm(), challenge.scheme(), url.url(), java.net.Authenticator.RequestorType.SERVER);
                }
                if (auth != null) {
                    String credential = Credentials.basic(auth.getUserName(), new String(auth.getPassword()));
                    return request.newBuilder().header(proxyAuthorization ? "Proxy-Authorization" : "Authorization", credential).build();
                }
            }
        }
        return null;
    }

    private InetAddress getConnectToInetAddress(Proxy proxy, HttpUrl url) throws IOException {
        if (proxy != null && proxy.type() != Proxy.Type.DIRECT) {
            return ((InetSocketAddress) proxy.address()).getAddress();
        }
        return InetAddress.getByName(url.host());
    }
}
