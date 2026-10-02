package okhttp3.internal.connection;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpStream;
import okhttp3.internal.http.RealInterceptorChain;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class ConnectInterceptor implements Interceptor {
    public final OkHttpClient client;

    public ConnectInterceptor(OkHttpClient client) {
        this.client = client;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        RealInterceptorChain realChain = (RealInterceptorChain) chain;
        Request request = realChain.request();
        StreamAllocation streamAllocation = realChain.streamAllocation();
        boolean doExtensiveHealthChecks = !request.method().equals("GET");
        HttpStream httpStream = streamAllocation.newStream(this.client, doExtensiveHealthChecks);
        RealConnection connection = streamAllocation.connection();
        return realChain.proceed(request, streamAllocation, httpStream, connection);
    }
}
