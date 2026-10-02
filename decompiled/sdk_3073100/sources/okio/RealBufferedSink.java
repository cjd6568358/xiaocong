package okio;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class RealBufferedSink implements BufferedSink {
    public final Buffer buffer = new Buffer();
    boolean closed;
    public final Sink sink;

    RealBufferedSink(Sink sink) {
        if (sink == null) {
            throw new NullPointerException("sink == null");
        }
        this.sink = sink;
    }

    @Override // okio.BufferedSink, okio.BufferedSource
    public Buffer buffer() {
        return this.buffer;
    }

    @Override // okio.Sink
    public void write(Buffer source, long byteCount) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(source, byteCount);
        emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink write(ByteString byteString) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(byteString);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeUtf8(String string) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeUtf8(string);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink write(byte[] source) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(source);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.write(source, offset, byteCount);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long totalBytesRead = 0;
        while (true) {
            long readCount = source.read(this.buffer, 8192L);
            if (readCount != -1) {
                totalBytesRead += readCount;
                emitCompleteSegments();
            } else {
                return totalBytesRead;
            }
        }
    }

    @Override // okio.BufferedSink
    public BufferedSink writeByte(int b) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeByte(b);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeShort(int s) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeShort(s);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeInt(int i) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeInt(i);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeLong(long v) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeLong(v);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeDecimalLong(long v) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeDecimalLong(v);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink writeHexadecimalUnsignedLong(long v) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        this.buffer.writeHexadecimalUnsignedLong(v);
        return emitCompleteSegments();
    }

    @Override // okio.BufferedSink
    public BufferedSink emitCompleteSegments() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        long byteCount = this.buffer.completeSegmentByteCount();
        if (byteCount > 0) {
            this.sink.write(this.buffer, byteCount);
        }
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink emit() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        long byteCount = this.buffer.size();
        if (byteCount > 0) {
            this.sink.write(this.buffer, byteCount);
        }
        return this;
    }

    @Override // okio.BufferedSink, okio.Sink, java.io.Flushable
    public void flush() throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (this.buffer.size > 0) {
            this.sink.write(this.buffer, this.buffer.size);
        }
        this.sink.flush();
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (!this.closed) {
            Throwable thrown = null;
            try {
                if (this.buffer.size > 0) {
                    this.sink.write(this.buffer, this.buffer.size);
                }
            } catch (Throwable e) {
                thrown = e;
            }
            try {
                this.sink.close();
            } catch (Throwable e2) {
                if (thrown == null) {
                    thrown = e2;
                }
            }
            this.closed = true;
            if (thrown != null) {
                Util.sneakyRethrow(thrown);
            }
        }
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return this.sink.timeout();
    }

    public String toString() {
        return "buffer(" + this.sink + ")";
    }
}
