package okhttp3.internal.huc;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Timeout;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
abstract class OutputStreamRequestBody extends RequestBody {
    boolean closed;
    private long expectedContentLength;
    private OutputStream outputStream;
    private Timeout timeout;

    OutputStreamRequestBody() {
    }

    protected void initOutputStream(final BufferedSink sink, final long expectedContentLength) {
        this.timeout = sink.timeout();
        this.expectedContentLength = expectedContentLength;
        this.outputStream = new OutputStream() { // from class: okhttp3.internal.huc.OutputStreamRequestBody.1
            private long bytesReceived;

            @Override // java.io.OutputStream
            public void write(int b) throws IOException {
                write(new byte[]{(byte) b}, 0, 1);
            }

            @Override // java.io.OutputStream
            public void write(byte[] source, int offset, int byteCount) throws IOException {
                if (OutputStreamRequestBody.this.closed) {
                    throw new IOException("closed");
                }
                if (expectedContentLength != -1 && this.bytesReceived + ((long) byteCount) > expectedContentLength) {
                    throw new ProtocolException("expected " + expectedContentLength + " bytes but received " + this.bytesReceived + byteCount);
                }
                this.bytesReceived += (long) byteCount;
                try {
                    sink.write(source, offset, byteCount);
                } catch (InterruptedIOException e) {
                    throw new SocketTimeoutException(e.getMessage());
                }
            }

            @Override // java.io.OutputStream, java.io.Flushable
            public void flush() throws IOException {
                if (!OutputStreamRequestBody.this.closed) {
                    sink.flush();
                }
            }

            @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                OutputStreamRequestBody.this.closed = true;
                if (expectedContentLength != -1 && this.bytesReceived < expectedContentLength) {
                    throw new ProtocolException("expected " + expectedContentLength + " bytes but received " + this.bytesReceived);
                }
                sink.close();
            }
        };
    }

    public final OutputStream outputStream() {
        return this.outputStream;
    }

    public final Timeout timeout() {
        return this.timeout;
    }

    public final boolean isClosed() {
        return this.closed;
    }

    @Override // okhttp3.RequestBody
    public long contentLength() throws IOException {
        return this.expectedContentLength;
    }

    @Override // okhttp3.RequestBody
    public final MediaType contentType() {
        return null;
    }

    public Request prepareToSendRequest(Request request) throws IOException {
        return request;
    }
}
