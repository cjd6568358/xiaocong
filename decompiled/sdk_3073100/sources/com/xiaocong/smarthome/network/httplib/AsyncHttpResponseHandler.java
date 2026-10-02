package com.xiaocong.smarthome.network.httplib;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.xiaocong.smarthome.network.bean.CommonHttpSetting;
import com.xiaocong.smarthome.network.util.XCHttpLog;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.ByteArrayBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class AsyncHttpResponseHandler implements ResponseHandlerInterface {
    protected static final int BUFFER_SIZE = 4096;
    protected static final int CANCEL_MESSAGE = 6;
    public static final String DEFAULT_CHARSET = "UTF-8";
    protected static final int FAILURE_MESSAGE = 1;
    protected static final int FINISH_MESSAGE = 3;
    private static final String LOG_TAG = "AsyncHttpResponseHandler";
    protected static final int PROGRESS_MESSAGE = 4;
    protected static final int RETRY_MESSAGE = 5;
    protected static final int START_MESSAGE = 2;
    protected static final int SUCCESS_MESSAGE = 0;
    protected CommonHttpSetting mCommonHttpSetting;
    protected WeakReference<Context> mWeakContext;
    private String responseCharset = DEFAULT_CHARSET;
    private Boolean useSynchronousMode = false;
    private URI requestURI = null;
    private Header[] requestHeaders = null;
    private final Handler handler = new ResponderHandler(this);

    public abstract void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th);

    public abstract void onSuccess(int i, Header[] headerArr, byte[] bArr);

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public URI getRequestURI() {
        return this.requestURI;
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public Header[] getRequestHeaders() {
        return this.requestHeaders;
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public void setRequestURI(URI requestURI) {
        this.requestURI = requestURI;
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public void setRequestHeaders(Header[] requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    private static class ResponderHandler extends Handler {
        private final AsyncHttpResponseHandler mResponder;

        ResponderHandler(AsyncHttpResponseHandler mResponder) {
            this.mResponder = mResponder;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            this.mResponder.handleMessage(msg);
        }
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public boolean getUseSynchronousMode() {
        return this.useSynchronousMode.booleanValue();
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public void setUseSynchronousMode(boolean value) {
        this.useSynchronousMode = Boolean.valueOf(value);
    }

    public void setCharset(String charset) {
        this.responseCharset = charset;
    }

    public String getCharset() {
        return this.responseCharset == null ? DEFAULT_CHARSET : this.responseCharset;
    }

    public AsyncHttpResponseHandler() {
        postRunnable(null);
    }

    public CommonHttpSetting getCommonHttpSetting() {
        return this.mCommonHttpSetting;
    }

    public void setCommonHttpSetting(CommonHttpSetting httpSetting) {
        this.mCommonHttpSetting = httpSetting;
    }

    public void onProgress(int bytesWritten, int totalSize) {
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(bytesWritten);
        objArr[1] = Integer.valueOf(totalSize);
        objArr[2] = Integer.valueOf(totalSize > 0 ? (bytesWritten / totalSize) * 100 : -1);
        Log.v(LOG_TAG, String.format("Progress %d from %d (%d%%)", objArr));
    }

    public void onStart() {
    }

    public void onFinish() {
    }

    public void onRetry(int retryNo) {
        Log.d(LOG_TAG, String.format("Request retry no. %d", Integer.valueOf(retryNo)));
    }

    public void onCancel() {
        Log.d(LOG_TAG, "Request got cancelled");
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public final void sendProgressMessage(int bytesWritten, int bytesTotal) {
        sendMessage(obtainMessage(4, new Object[]{Integer.valueOf(bytesWritten), Integer.valueOf(bytesTotal)}));
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public final void sendSuccessMessage(int statusCode, Header[] headers, byte[] responseBytes) {
        sendMessage(obtainMessage(0, new Object[]{Integer.valueOf(statusCode), headers, responseBytes}));
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public final void sendFailureMessage(int statusCode, Header[] headers, byte[] responseBody, Throwable throwable) {
        sendMessage(obtainMessage(1, new Object[]{Integer.valueOf(statusCode), headers, responseBody, throwable}));
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public final void sendStartMessage() {
        sendMessage(obtainMessage(2, null));
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public final void sendFinishMessage() {
        sendMessage(obtainMessage(3, null));
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public final void sendRetryMessage(int retryNo) {
        sendMessage(obtainMessage(5, new Object[]{Integer.valueOf(retryNo)}));
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public final void sendCancelMessage() {
        sendMessage(obtainMessage(6, null));
    }

    public boolean needRemoveCallbck() {
        Context mContext;
        return this.mCommonHttpSetting != null && this.mCommonHttpSetting.isRemoveCallbackWhenActivityDestroy() && this.mWeakContext != null && ((mContext = this.mWeakContext.get()) == null || ((mContext instanceof Activity) && ((Activity) mContext).isFinishing()));
    }

    protected void handleMessage(Message message) {
        if (needRemoveCallbck()) {
            Context simpleName = this.mWeakContext.get();
            StringBuilder sbAppend = new StringBuilder().append("removeCallBack ").append(message.what).append(" ");
            if (simpleName != null) {
                simpleName = simpleName.getClass().getSimpleName();
            }
            XCHttpLog.e(sbAppend.append(simpleName).toString());
        }
        switch (message.what) {
            case 0:
                Object[] response = (Object[]) message.obj;
                if (response != null && response.length >= 3) {
                    onSuccess(((Integer) response[0]).intValue(), (Header[]) response[1], (byte[]) response[2]);
                } else {
                    Log.e(LOG_TAG, "SUCCESS_MESSAGE didn't got enough params");
                }
                break;
            case 1:
                Object[] response2 = (Object[]) message.obj;
                if (response2 != null && response2.length >= 4) {
                    onFailure(((Integer) response2[0]).intValue(), (Header[]) response2[1], (byte[]) response2[2], (Throwable) response2[3]);
                } else {
                    Log.e(LOG_TAG, "FAILURE_MESSAGE didn't got enough params");
                }
                break;
            case 2:
                onStart();
                break;
            case 3:
                onFinish();
                break;
            case 4:
                Object[] response3 = (Object[]) message.obj;
                if (response3 != null && response3.length >= 2) {
                    try {
                        onProgress(((Integer) response3[0]).intValue(), ((Integer) response3[1]).intValue());
                    } catch (Throwable t) {
                        Log.e(LOG_TAG, "custom onProgress contains an error", t);
                        return;
                    }
                } else {
                    Log.e(LOG_TAG, "PROGRESS_MESSAGE didn't got enough params");
                }
                break;
            case 5:
                Object[] response4 = (Object[]) message.obj;
                if (response4 != null && response4.length == 1) {
                    onRetry(((Integer) response4[0]).intValue());
                } else {
                    Log.e(LOG_TAG, "RETRY_MESSAGE didn't get enough params");
                }
                break;
            case 6:
                onCancel();
                break;
        }
    }

    protected void sendMessage(Message msg) {
        if (getUseSynchronousMode()) {
            handleMessage(msg);
        } else if (!Thread.currentThread().isInterrupted()) {
            this.handler.sendMessage(msg);
        }
    }

    protected void postRunnable(Runnable runnable) {
        boolean missingLooper = Looper.myLooper() == null;
        if (runnable != null) {
            if (missingLooper) {
                this.handler.post(runnable);
            } else {
                runnable.run();
            }
        }
    }

    public WeakReference<Context> getWeakContext() {
        return this.mWeakContext;
    }

    public void setWeakContext(WeakReference<Context> weakContext) {
        this.mWeakContext = weakContext;
    }

    protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
        return this.handler.obtainMessage(responseMessageId, responseMessageData);
    }

    @Override // com.xiaocong.smarthome.network.httplib.ResponseHandlerInterface
    public void sendResponseMessage(HttpResponse response) throws IOException {
        if (!Thread.currentThread().isInterrupted()) {
            StatusLine status = response.getStatusLine();
            byte[] responseBody = getResponseData(response.getEntity());
            if (!Thread.currentThread().isInterrupted()) {
                if (status.getStatusCode() >= 300) {
                    sendFailureMessage(status.getStatusCode(), response.getAllHeaders(), responseBody, new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()));
                } else {
                    sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), responseBody);
                }
            }
        }
    }

    byte[] getResponseData(HttpEntity entity) throws IOException {
        InputStream instream;
        int buffersize = BUFFER_SIZE;
        if (entity == null || (instream = entity.getContent()) == null) {
            return null;
        }
        long contentLength = entity.getContentLength();
        if (contentLength > 2147483647L) {
            throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
        }
        if (contentLength > 0) {
            buffersize = (int) contentLength;
        }
        try {
            ByteArrayBuffer buffer = new ByteArrayBuffer(buffersize);
            try {
                byte[] tmp = new byte[BUFFER_SIZE];
                int count = 0;
                while (true) {
                    int l = instream.read(tmp);
                    if (l == -1 || Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    count += l;
                    buffer.append(tmp, 0, l);
                    sendProgressMessage(count, (int) (contentLength <= 0 ? 1L : contentLength));
                }
                AsyncHttpClient.silentCloseInputStream(instream);
                byte[] responseBody = buffer.toByteArray();
                return responseBody;
            } catch (Throwable th) {
                AsyncHttpClient.silentCloseInputStream(instream);
                throw th;
            }
        } catch (OutOfMemoryError e) {
            System.gc();
            throw new IOException("File too large to fit into available memory");
        }
    }
}
