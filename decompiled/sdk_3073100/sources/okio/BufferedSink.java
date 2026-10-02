package okio;

import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface BufferedSink extends Sink {
    Buffer buffer();

    BufferedSink emit() throws IOException;

    BufferedSink emitCompleteSegments() throws IOException;

    @Override // okio.Sink, java.io.Flushable
    void flush() throws IOException;

    BufferedSink write(ByteString byteString) throws IOException;

    BufferedSink write(byte[] bArr) throws IOException;

    BufferedSink write(byte[] bArr, int i, int i2) throws IOException;

    long writeAll(Source source) throws IOException;

    BufferedSink writeByte(int i) throws IOException;

    BufferedSink writeDecimalLong(long j) throws IOException;

    BufferedSink writeHexadecimalUnsignedLong(long j) throws IOException;

    BufferedSink writeInt(int i) throws IOException;

    BufferedSink writeLong(long j) throws IOException;

    BufferedSink writeShort(int i) throws IOException;

    BufferedSink writeUtf8(String str) throws IOException;
}
