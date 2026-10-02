package okhttp3;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface Interceptor {

    public interface Chain {
        Connection connection();

        Response proceed(Request request) throws IOException;

        Request request();
    }

    Response intercept(Chain chain) throws IOException;
}
