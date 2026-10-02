package okio;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface BufferedSource extends Source {
    Buffer buffer();

    boolean exhausted() throws IOException;

    long indexOf(byte b) throws IOException;

    InputStream inputStream();

    int read(byte[] bArr, int i, int i2) throws IOException;

    long readAll(Sink sink) throws IOException;

    byte readByte() throws IOException;

    byte[] readByteArray() throws IOException;

    byte[] readByteArray(long j) throws IOException;

    ByteString readByteString(long j) throws IOException;

    long readDecimalLong() throws IOException;

    void readFully(Buffer buffer, long j) throws IOException;

    void readFully(byte[] bArr) throws IOException;

    long readHexadecimalUnsignedLong() throws IOException;

    int readInt() throws IOException;

    int readIntLe() throws IOException;

    long readLong() throws IOException;

    short readShort() throws IOException;

    short readShortLe() throws IOException;

    String readUtf8() throws IOException;

    String readUtf8LineStrict() throws IOException;

    boolean request(long j) throws IOException;

    void require(long j) throws IOException;

    void skip(long j) throws IOException;
}
