package okhttp3;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface Authenticator {
    public static final Authenticator NONE = new Authenticator() { // from class: okhttp3.Authenticator.1
        @Override // okhttp3.Authenticator
        public Request authenticate(Route route, Response response) {
            return null;
        }
    };

    Request authenticate(Route route, Response response) throws IOException;
}
