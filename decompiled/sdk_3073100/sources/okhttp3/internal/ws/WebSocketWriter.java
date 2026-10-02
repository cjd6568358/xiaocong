package okhttp3.internal.ws;

import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import com.youzan.androidsdk.basic.BuildConfig;
import java.io.IOException;
import java.util.Random;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Sink;
import okio.Timeout;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class WebSocketWriter {
    static final /* synthetic */ boolean $assertionsDisabled;
    private boolean activeWriter;
    private final Buffer buffer = new Buffer();
    private final FrameSink frameSink = new FrameSink();
    private final boolean isClient;
    private final byte[] maskBuffer;
    private final byte[] maskKey;
    private final Random random;
    private final BufferedSink sink;
    private boolean writerClosed;

    static {
        $assertionsDisabled = !WebSocketWriter.class.desiredAssertionStatus();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public WebSocketWriter(boolean z, BufferedSink bufferedSink, Random random) {
        if (bufferedSink == null) {
            throw new NullPointerException("sink == null");
        }
        if (random == null) {
            throw new NullPointerException("random == null");
        }
        this.isClient = z;
        this.sink = bufferedSink;
        this.random = random;
        this.maskKey = z ? new byte[4] : null;
        this.maskBuffer = z ? new byte[AsyncHttpClient.DEFAULT_SOCKET_BUFFER_SIZE] : null;
    }

    public void writePing(Buffer payload) throws IOException {
        synchronized (this) {
            writeControlFrameSynchronized(9, payload);
        }
    }

    public void writePong(Buffer payload) throws IOException {
        synchronized (this) {
            writeControlFrameSynchronized(10, payload);
        }
    }

    public void writeClose(int code, String reason) throws IOException {
        Buffer payload = null;
        if (code != 0 || reason != null) {
            if (code != 0) {
                WebSocketProtocol.validateCloseCode(code, true);
            }
            payload = new Buffer();
            payload.writeShort(code);
            if (reason != null) {
                payload.writeUtf8(reason);
            }
        }
        synchronized (this) {
            writeControlFrameSynchronized(8, payload);
            this.writerClosed = true;
        }
    }

    private void writeControlFrameSynchronized(int opcode, Buffer payload) throws IOException {
        if (!$assertionsDisabled && !Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        if (this.writerClosed) {
            throw new IOException("closed");
        }
        int length = 0;
        if (payload != null) {
            length = (int) payload.size();
            if (length > 125) {
                throw new IllegalArgumentException("Payload size must be less than or equal to 125");
            }
        }
        int b0 = opcode | 128;
        this.sink.writeByte(b0);
        int b1 = length;
        if (this.isClient) {
            this.sink.writeByte(b1 | 128);
            this.random.nextBytes(this.maskKey);
            this.sink.write(this.maskKey);
            if (payload != null) {
                writeMaskedSynchronized(payload, length);
            }
        } else {
            this.sink.writeByte(b1);
            if (payload != null) {
                this.sink.writeAll(payload);
            }
        }
        this.sink.emit();
    }

    public Sink newMessageSink(int formatOpcode, long contentLength) {
        if (this.activeWriter) {
            throw new IllegalStateException("Another message writer is active. Did you call close()?");
        }
        this.activeWriter = true;
        this.frameSink.formatOpcode = formatOpcode;
        this.frameSink.contentLength = contentLength;
        this.frameSink.isFirstFrame = true;
        this.frameSink.closed = false;
        return this.frameSink;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeMessageFrameSynchronized(int formatOpcode, long byteCount, boolean isFirstFrame, boolean isFinal) throws IOException {
        if (!$assertionsDisabled && !Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        if (this.writerClosed) {
            throw new IOException("closed");
        }
        int b0 = isFirstFrame ? formatOpcode : 0;
        if (isFinal) {
            b0 |= 128;
        }
        this.sink.writeByte(b0);
        int b1 = 0;
        if (this.isClient) {
            b1 = 0 | 128;
            this.random.nextBytes(this.maskKey);
        }
        if (byteCount <= 125) {
            this.sink.writeByte(b1 | ((int) byteCount));
        } else if (byteCount <= 65535) {
            this.sink.writeByte(b1 | BuildConfig.VERSION_CODE);
            this.sink.writeShort((int) byteCount);
        } else {
            this.sink.writeByte(b1 | 127);
            this.sink.writeLong(byteCount);
        }
        if (this.isClient) {
            this.sink.write(this.maskKey);
            writeMaskedSynchronized(this.buffer, byteCount);
        } else {
            this.sink.write(this.buffer, byteCount);
        }
        this.sink.emit();
    }

    private void writeMaskedSynchronized(BufferedSource source, long byteCount) throws IOException {
        if (!$assertionsDisabled && !Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        long written = 0;
        while (written < byteCount) {
            int toRead = (int) Math.min(byteCount, this.maskBuffer.length);
            int read = source.read(this.maskBuffer, 0, toRead);
            if (read == -1) {
                throw new AssertionError();
            }
            WebSocketProtocol.toggleMask(this.maskBuffer, read, this.maskKey, written);
            this.sink.write(this.maskBuffer, 0, read);
            written += (long) read;
        }
    }

    private final class FrameSink implements Sink {
        private boolean closed;
        private long contentLength;
        private int formatOpcode;
        private boolean isFirstFrame;

        private FrameSink() {
        }

        @Override // okio.Sink
        public void write(Buffer source, long byteCount) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.buffer.write(source, byteCount);
            boolean deferWrite = this.isFirstFrame && this.contentLength != -1 && WebSocketWriter.this.buffer.size() > this.contentLength - 8192;
            long emitCount = WebSocketWriter.this.buffer.completeSegmentByteCount();
            if (emitCount > 0 && !deferWrite) {
                synchronized (WebSocketWriter.this) {
                    WebSocketWriter.this.writeMessageFrameSynchronized(this.formatOpcode, emitCount, this.isFirstFrame, false);
                }
                this.isFirstFrame = false;
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            synchronized (WebSocketWriter.this) {
                WebSocketWriter.this.writeMessageFrameSynchronized(this.formatOpcode, WebSocketWriter.this.buffer.size(), this.isFirstFrame, false);
            }
            this.isFirstFrame = false;
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return WebSocketWriter.this.sink.timeout();
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            synchronized (WebSocketWriter.this) {
                WebSocketWriter.this.writeMessageFrameSynchronized(this.formatOpcode, WebSocketWriter.this.buffer.size(), this.isFirstFrame, true);
            }
            this.closed = true;
            WebSocketWriter.this.activeWriter = false;
        }
    }
}
