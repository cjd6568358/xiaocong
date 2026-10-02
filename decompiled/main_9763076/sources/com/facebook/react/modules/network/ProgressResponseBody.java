package com.facebook.react.modules.network;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ProgressResponseBody extends ResponseBody {
    private BufferedSource mBufferedSource;
    private final ProgressListener mProgressListener;
    private final ResponseBody mResponseBody;
    private long mTotalBytesRead = 0;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
        this.mResponseBody = responseBody;
        this.mProgressListener = progressListener;
    }

    public MediaType contentType() {
        return this.mResponseBody.contentType();
    }

    public long contentLength() {
        return this.mResponseBody.contentLength();
    }

    public long totalBytesRead() {
        return this.mTotalBytesRead;
    }

    public BufferedSource source() {
        if (this.mBufferedSource == null) {
            this.mBufferedSource = Okio.buffer(source(this.mResponseBody.source()));
        }
        return this.mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) { // from class: com.facebook.react.modules.network.ProgressResponseBody.1
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                ProgressResponseBody.this.mTotalBytesRead = (bytesRead != -1 ? bytesRead : 0L) + ProgressResponseBody.this.mTotalBytesRead;
                ProgressResponseBody.this.mProgressListener.onProgress(ProgressResponseBody.this.mTotalBytesRead, ProgressResponseBody.this.mResponseBody.contentLength(), bytesRead == -1);
                return bytesRead;
            }
        };
    }
}
