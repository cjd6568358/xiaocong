package com.xiaocong.smarthome.network.httplib;

import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.util.ByteArrayBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class DataAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "DataAsyncHttpResponseHandler";
    protected static final int PROGRESS_DATA_MESSAGE = 6;

    public void onProgressData(byte[] responseBody) {
    }

    public final void sendProgressDataMessage(byte[] responseBytes) {
        sendMessage(obtainMessage(6, new Object[]{responseBytes}));
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    protected void handleMessage(Message message) {
        super.handleMessage(message);
        switch (message.what) {
            case 6:
                Object[] response = (Object[]) message.obj;
                if (response != null && response.length >= 1) {
                    try {
                        onProgressData((byte[]) response[0]);
                    } catch (Throwable t) {
                        Log.e(LOG_TAG, "custom onProgressData contains an error", t);
                        return;
                    }
                } else {
                    Log.e(LOG_TAG, "PROGRESS_DATA_MESSAGE didn't got enough params");
                }
                break;
        }
    }

    @Override // com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler
    byte[] getResponseData(HttpEntity entity) throws IOException {
        InputStream instream;
        if (entity == null || (instream = entity.getContent()) == null) {
            return null;
        }
        long contentLength = entity.getContentLength();
        if (contentLength > 2147483647L) {
            throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
        }
        if (contentLength < 0) {
            contentLength = 4096;
        }
        try {
            ByteArrayBuffer buffer = new ByteArrayBuffer((int) contentLength);
            try {
                byte[] tmp = new byte[4096];
                while (true) {
                    int l = instream.read(tmp);
                    if (l == -1 || Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    buffer.append(tmp, 0, l);
                    sendProgressDataMessage(copyOfRange(tmp, 0, l));
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

    public static byte[] copyOfRange(byte[] original, int start, int end) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NullPointerException {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        int copyLength = Math.min(resultLength, originalLength - start);
        byte[] result = new byte[resultLength];
        System.arraycopy(original, start, result, 0, copyLength);
        return result;
    }
}
