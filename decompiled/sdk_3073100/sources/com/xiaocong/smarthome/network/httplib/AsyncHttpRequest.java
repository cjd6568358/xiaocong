package com.xiaocong.smarthome.network.httplib;

import android.util.Log;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AsyncHttpRequest implements Runnable {
    private final AbstractHttpClient client;
    private final HttpContext context;
    private int executionCount;
    private final HttpUriRequest request;
    private final ResponseHandlerInterface responseHandler;
    private boolean isCancelled = false;
    private boolean cancelIsNotified = false;
    private boolean isFinished = false;

    public AsyncHttpRequest(AbstractHttpClient client, HttpContext context, HttpUriRequest request, ResponseHandlerInterface responseHandler) {
        this.client = client;
        this.context = context;
        this.request = request;
        this.responseHandler = responseHandler;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!isCancelled()) {
            if (this.responseHandler != null) {
                this.responseHandler.sendStartMessage();
            }
            if (!isCancelled()) {
                try {
                    makeRequestWithRetries();
                } catch (IOException e) {
                    if (!isCancelled() && this.responseHandler != null) {
                        this.responseHandler.sendFailureMessage(0, null, null, e);
                    } else {
                        Log.e("AsyncHttpRequest", "makeRequestWithRetries returned error, but handler is null", e);
                    }
                }
                if (!isCancelled()) {
                    if (this.responseHandler != null) {
                        this.responseHandler.sendFinishMessage();
                    }
                    this.isFinished = true;
                }
            }
        }
    }

    private void makeRequest() throws IOException {
        if (!isCancelled()) {
            if (this.request.getURI().getScheme() == null) {
                throw new MalformedURLException("No valid URI scheme was provided");
            }
            HttpResponse response = this.client.execute(this.request, this.context);
            if (!isCancelled() && this.responseHandler != null) {
                this.responseHandler.sendResponseMessage(response);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0041 A[Catch: Exception -> 0x00b7, TryCatch #3 {Exception -> 0x00b7, blocks: (B:8:0x002c, B:10:0x0030, B:14:0x0041, B:16:0x0045, B:21:0x006d, B:27:0x0082, B:4:0x000b), top: B:37:0x002c, inners: #4 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x004e  */
    /* JADX WARN: Code duplicated, block: B:43:0x00b9 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    private void makeRequestWithRetries() throws IOException {
        boolean retry = true;
        HttpRequestRetryHandler retryHandler = this.client.getHttpRequestRetryHandler();
        IOException cause = null;
        while (retry) {
            try {
                makeRequest();
                return;
            } catch (UnknownHostException e) {
                try {
                    cause = new IOException("UnknownHostException exception: " + e.getMessage());
                    try {
                        if (this.executionCount > 0) {
                            int i = this.executionCount + 1;
                            this.executionCount = i;
                            if (retryHandler.retryRequest(cause, i, this.context)) {
                                retry = true;
                            } else {
                                retry = false;
                            }
                        } else {
                            retry = false;
                        }
                        if (!retry && this.responseHandler != null) {
                            this.responseHandler.sendRetryMessage(this.executionCount);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Log.e("AsyncHttpRequest", "Unhandled exception origin cause", e);
                        IOException cause2 = new IOException("Unhandled exception: " + e.getMessage());
                        throw cause2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    Log.e("AsyncHttpRequest", "Unhandled exception origin cause", e);
                    IOException cause3 = new IOException("Unhandled exception: " + e.getMessage());
                    throw cause3;
                }
            } catch (IOException e4) {
                if (isCancelled()) {
                    return;
                }
                cause = e4;
                int i2 = this.executionCount + 1;
                this.executionCount = i2;
                retry = retryHandler.retryRequest(cause, i2, this.context);
                if (!retry) {
                }
            } catch (NullPointerException e5) {
                cause = new IOException("NPE in HttpClient: " + e5.getMessage());
                int i3 = this.executionCount + 1;
                this.executionCount = i3;
                retry = retryHandler.retryRequest(cause, i3, this.context);
                if (!retry) {
                }
            }
        }
        throw cause;
    }

    public boolean isCancelled() {
        if (this.isCancelled) {
            sendCancelNotification();
        }
        return this.isCancelled;
    }

    private synchronized void sendCancelNotification() {
        if (!this.isFinished && this.isCancelled && !this.cancelIsNotified) {
            this.cancelIsNotified = true;
            if (this.responseHandler != null) {
                this.responseHandler.sendCancelMessage();
            }
        }
    }

    public boolean isDone() {
        return isCancelled() || this.isFinished;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        this.isCancelled = true;
        if (mayInterruptIfRunning && this.request != null && !this.request.isAborted()) {
            this.request.abort();
        }
        return isCancelled();
    }
}
