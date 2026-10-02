package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.StreamAllocation;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean forWebSocket) {
        this.forWebSocket = forWebSocket;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        HttpStream httpStream = ((RealInterceptorChain) chain).httpStream();
        StreamAllocation streamAllocation = ((RealInterceptorChain) chain).streamAllocation();
        Request request = chain.request();
        long sentRequestMillis = System.currentTimeMillis();
        httpStream.writeRequestHeaders(request);
        if (HttpMethod.permitsRequestBody(request.method()) && request.body() != null) {
            Sink requestBodyOut = httpStream.createRequestBody(request, request.body().contentLength());
            BufferedSink bufferedRequestBody = Okio.buffer(requestBodyOut);
            request.body().writeTo(bufferedRequestBody);
            bufferedRequestBody.close();
        }
        httpStream.finishRequest();
        Response response = httpStream.readResponseHeaders().request(request).handshake(streamAllocation.connection().handshake()).sentRequestAtMillis(sentRequestMillis).receivedResponseAtMillis(System.currentTimeMillis()).build();
        if (!this.forWebSocket || response.code() != 101) {
            response = response.newBuilder().body(httpStream.openResponseBody(response)).build();
        }
        if ("close".equalsIgnoreCase(response.request().header("Connection")) || "close".equalsIgnoreCase(response.header("Connection"))) {
            streamAllocation.noNewStreams();
        }
        int code = response.code();
        if ((code == 204 || code == 205) && response.body().contentLength() > 0) {
            throw new ProtocolException("HTTP " + code + " had non-zero Content-Length: " + response.body().contentLength());
        }
        return response;
    }
}
