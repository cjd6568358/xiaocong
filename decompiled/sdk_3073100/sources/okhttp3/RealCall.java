package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class RealCall implements Call {
    private final OkHttpClient client;
    private boolean executed;
    Request originalRequest;
    private final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;

    protected RealCall(OkHttpClient client, Request originalRequest) {
        this.client = client;
        this.originalRequest = originalRequest;
        this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(client);
    }

    @Override // okhttp3.Call
    public Request request() {
        return this.originalRequest;
    }

    @Override // okhttp3.Call
    public Response execute() throws IOException {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        try {
            this.client.dispatcher().executed(this);
            Response result = getResponseWithInterceptorChain();
            if (result == null) {
                throw new IOException("Canceled");
            }
            this.client.dispatcher().finished(this);
            return result;
        } catch (Throwable th) {
            this.client.dispatcher().finished(this);
            throw th;
        }
    }

    synchronized void setForWebSocket() {
        if (this.executed) {
            throw new IllegalStateException("Already Executed");
        }
        this.retryAndFollowUpInterceptor.setForWebSocket(true);
    }

    @Override // okhttp3.Call
    public void enqueue(Callback responseCallback) {
        synchronized (this) {
            if (this.executed) {
                throw new IllegalStateException("Already Executed");
            }
            this.executed = true;
        }
        this.client.dispatcher().enqueue(new AsyncCall(responseCallback));
    }

    @Override // okhttp3.Call
    public void cancel() {
        this.retryAndFollowUpInterceptor.cancel();
    }

    @Override // okhttp3.Call
    public synchronized boolean isExecuted() {
        return this.executed;
    }

    @Override // okhttp3.Call
    public boolean isCanceled() {
        return this.retryAndFollowUpInterceptor.isCanceled();
    }

    StreamAllocation streamAllocation() {
        return this.retryAndFollowUpInterceptor.streamAllocation();
    }

    final class AsyncCall extends NamedRunnable {
        private final Callback responseCallback;

        private AsyncCall(Callback responseCallback) {
            super("OkHttp %s", RealCall.this.redactedUrl().toString());
            this.responseCallback = responseCallback;
        }

        String host() {
            return RealCall.this.originalRequest.url().host();
        }

        Request request() {
            return RealCall.this.originalRequest;
        }

        RealCall get() {
            return RealCall.this;
        }

        @Override // okhttp3.internal.NamedRunnable
        protected void execute() {
            try {
                Response response = RealCall.this.getResponseWithInterceptorChain();
                if (RealCall.this.retryAndFollowUpInterceptor.isCanceled()) {
                    this.responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
                } else {
                    this.responseCallback.onResponse(RealCall.this, response);
                }
            } catch (IOException e) {
                if (0 != 0) {
                    Platform.get().log(4, "Callback failure for " + RealCall.this.toLoggableString(), e);
                } else {
                    this.responseCallback.onFailure(RealCall.this, e);
                }
            } finally {
                RealCall.this.client.dispatcher().finished(this);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String toLoggableString() {
        String string = this.retryAndFollowUpInterceptor.isCanceled() ? "canceled call" : "call";
        return string + " to " + redactedUrl();
    }

    HttpUrl redactedUrl() {
        return this.originalRequest.url().resolve("/...");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Response getResponseWithInterceptorChain() throws IOException {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(this.client.interceptors());
        interceptors.add(this.retryAndFollowUpInterceptor);
        interceptors.add(new BridgeInterceptor(this.client.cookieJar()));
        interceptors.add(new CacheInterceptor(this.client.internalCache()));
        interceptors.add(new ConnectInterceptor(this.client));
        if (!this.retryAndFollowUpInterceptor.isForWebSocket()) {
            interceptors.addAll(this.client.networkInterceptors());
        }
        interceptors.add(new CallServerInterceptor(this.retryAndFollowUpInterceptor.isForWebSocket()));
        Interceptor.Chain chain = new RealInterceptorChain(interceptors, null, null, null, 0, this.originalRequest);
        return chain.proceed(this.originalRequest);
    }
}
