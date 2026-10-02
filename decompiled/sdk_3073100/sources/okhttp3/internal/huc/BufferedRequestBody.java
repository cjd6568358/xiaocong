package okhttp3.internal.huc;

import java.io.IOException;
import okhttp3.Request;
import okio.Buffer;
import okio.BufferedSink;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class BufferedRequestBody extends OutputStreamRequestBody {
    final Buffer buffer = new Buffer();
    long contentLength = -1;

    BufferedRequestBody(long expectedContentLength) {
        initOutputStream(this.buffer, expectedContentLength);
    }

    @Override // okhttp3.internal.huc.OutputStreamRequestBody, okhttp3.RequestBody
    public long contentLength() throws IOException {
        return this.contentLength;
    }

    @Override // okhttp3.internal.huc.OutputStreamRequestBody
    public Request prepareToSendRequest(Request request) throws IOException {
        if (request.header("Content-Length") == null) {
            outputStream().close();
            this.contentLength = this.buffer.size();
            return request.newBuilder().removeHeader("Transfer-Encoding").header("Content-Length", Long.toString(this.buffer.size())).build();
        }
        return request;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink sink) throws IOException {
        this.buffer.copyTo(sink.buffer(), 0L, this.buffer.size());
    }
}
