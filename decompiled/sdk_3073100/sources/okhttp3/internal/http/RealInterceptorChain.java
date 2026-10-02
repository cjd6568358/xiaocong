package okhttp3.internal.http;

import java.io.IOException;
import java.util.List;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.connection.StreamAllocation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class RealInterceptorChain implements Interceptor.Chain {
    private int calls;
    private final Connection connection;
    private final HttpStream httpStream;
    private final int index;
    private final List<Interceptor> interceptors;
    private final Request request;
    private final StreamAllocation streamAllocation;

    public RealInterceptorChain(List<Interceptor> interceptors, StreamAllocation streamAllocation, HttpStream httpStream, Connection connection, int index, Request request) {
        this.interceptors = interceptors;
        this.connection = connection;
        this.streamAllocation = streamAllocation;
        this.httpStream = httpStream;
        this.index = index;
        this.request = request;
    }

    @Override // okhttp3.Interceptor.Chain
    public Connection connection() {
        return this.connection;
    }

    public StreamAllocation streamAllocation() {
        return this.streamAllocation;
    }

    public HttpStream httpStream() {
        return this.httpStream;
    }

    @Override // okhttp3.Interceptor.Chain
    public Request request() {
        return this.request;
    }

    @Override // okhttp3.Interceptor.Chain
    public Response proceed(Request request) throws IOException {
        return proceed(request, this.streamAllocation, this.httpStream, this.connection);
    }

    public Response proceed(Request request, StreamAllocation streamAllocation, HttpStream httpStream, Connection connection) throws IOException {
        if (this.index >= this.interceptors.size()) {
            throw new AssertionError();
        }
        this.calls++;
        if (this.httpStream != null && !sameConnection(request.url())) {
            throw new IllegalStateException("network interceptor " + this.interceptors.get(this.index - 1) + " must retain the same host and port");
        }
        if (this.httpStream != null && this.calls > 1) {
            throw new IllegalStateException("network interceptor " + this.interceptors.get(this.index - 1) + " must call proceed() exactly once");
        }
        RealInterceptorChain next = new RealInterceptorChain(this.interceptors, streamAllocation, httpStream, connection, this.index + 1, request);
        Interceptor interceptor = this.interceptors.get(this.index);
        Response response = interceptor.intercept(next);
        if (httpStream != null && this.index + 1 < this.interceptors.size() && next.calls != 1) {
            throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
        }
        if (response == null) {
            throw new NullPointerException("interceptor " + interceptor + " returned null");
        }
        return response;
    }

    private boolean sameConnection(HttpUrl url) {
        return url.host().equals(this.connection.route().address().url().host()) && url.port() == this.connection.route().address().url().port();
    }
}
