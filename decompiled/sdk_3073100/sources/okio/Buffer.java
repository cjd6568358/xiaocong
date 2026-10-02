package okio;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Buffer implements Cloneable, BufferedSink, BufferedSource {
    private static final byte[] DIGITS = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    Segment head;
    long size;

    public long size() {
        return this.size;
    }

    @Override // okio.BufferedSink, okio.BufferedSource
    public Buffer buffer() {
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer emitCompleteSegments() {
        return this;
    }

    @Override // okio.BufferedSink
    public BufferedSink emit() {
        return this;
    }

    @Override // okio.BufferedSource
    public boolean exhausted() {
        return this.size == 0;
    }

    @Override // okio.BufferedSource
    public void require(long byteCount) throws EOFException {
        if (this.size < byteCount) {
            throw new EOFException();
        }
    }

    @Override // okio.BufferedSource
    public boolean request(long byteCount) {
        return this.size >= byteCount;
    }

    @Override // okio.BufferedSource
    public InputStream inputStream() {
        return new InputStream() { // from class: okio.Buffer.2
            @Override // java.io.InputStream
            public int read() {
                if (Buffer.this.size > 0) {
                    return Buffer.this.readByte() & 255;
                }
                return -1;
            }

            @Override // java.io.InputStream
            public int read(byte[] sink, int offset, int byteCount) {
                return Buffer.this.read(sink, offset, byteCount);
            }

            @Override // java.io.InputStream
            public int available() {
                return (int) Math.min(Buffer.this.size, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            public String toString() {
                return Buffer.this + ".inputStream()";
            }
        };
    }

    public Buffer copyTo(Buffer out, long offset, long byteCount) {
        if (out == null) {
            throw new IllegalArgumentException("out == null");
        }
        Util.checkOffsetAndCount(this.size, offset, byteCount);
        if (byteCount != 0) {
            out.size += byteCount;
            Segment s = this.head;
            while (offset >= s.limit - s.pos) {
                offset -= (long) (s.limit - s.pos);
                s = s.next;
            }
            while (byteCount > 0) {
                Segment copy = new Segment(s);
                copy.pos = (int) (((long) copy.pos) + offset);
                copy.limit = Math.min(copy.pos + ((int) byteCount), copy.limit);
                if (out.head == null) {
                    copy.prev = copy;
                    copy.next = copy;
                    out.head = copy;
                } else {
                    out.head.prev.push(copy);
                }
                byteCount -= (long) (copy.limit - copy.pos);
                offset = 0;
                s = s.next;
            }
        }
        return this;
    }

    public long completeSegmentByteCount() {
        long result = this.size;
        if (result == 0) {
            return 0L;
        }
        Segment tail = this.head.prev;
        if (tail.limit < 8192 && tail.owner) {
            result -= (long) (tail.limit - tail.pos);
        }
        return result;
    }

    @Override // okio.BufferedSource
    public byte readByte() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        byte[] data = segment.data;
        int pos2 = pos + 1;
        byte b = data[pos];
        this.size--;
        if (pos2 == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos2;
        }
        return b;
    }

    public byte getByte(long pos) {
        Util.checkOffsetAndCount(this.size, pos, 1L);
        Segment s = this.head;
        while (true) {
            int segmentByteCount = s.limit - s.pos;
            if (pos < segmentByteCount) {
                return s.data[s.pos + ((int) pos)];
            }
            pos -= (long) segmentByteCount;
            s = s.next;
        }
    }

    @Override // okio.BufferedSource
    public short readShort() {
        if (this.size < 2) {
            throw new IllegalStateException("size < 2: " + this.size);
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 2) {
            int s = ((readByte() & 255) << 8) | (readByte() & 255);
            return (short) s;
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        int i = (data[pos] & 255) << 8;
        int pos3 = pos2 + 1;
        int s2 = i | (data[pos2] & 255);
        this.size -= 2;
        if (pos3 == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
        } else {
            segment.pos = pos3;
        }
        return (short) s2;
    }

    @Override // okio.BufferedSource
    public int readInt() {
        if (this.size < 4) {
            throw new IllegalStateException("size < 4: " + this.size);
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 4) {
            return ((readByte() & 255) << 24) | ((readByte() & 255) << 16) | ((readByte() & 255) << 8) | (readByte() & 255);
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        int i = (data[pos] & 255) << 24;
        int pos3 = pos2 + 1;
        int i2 = i | ((data[pos2] & 255) << 16);
        int pos4 = pos3 + 1;
        int i3 = i2 | ((data[pos3] & 255) << 8);
        int pos5 = pos4 + 1;
        int i4 = i3 | (data[pos4] & 255);
        this.size -= 4;
        if (pos5 == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return i4;
        }
        segment.pos = pos5;
        return i4;
    }

    @Override // okio.BufferedSource
    public long readLong() {
        if (this.size < 8) {
            throw new IllegalStateException("size < 8: " + this.size);
        }
        Segment segment = this.head;
        int pos = segment.pos;
        int limit = segment.limit;
        if (limit - pos < 8) {
            return ((((long) readInt()) & 4294967295L) << 32) | (((long) readInt()) & 4294967295L);
        }
        byte[] data = segment.data;
        int pos2 = pos + 1;
        long j = (((long) data[pos]) & 255) << 56;
        int pos3 = pos2 + 1;
        long j2 = j | ((((long) data[pos2]) & 255) << 48);
        int pos4 = pos3 + 1;
        long j3 = j2 | ((((long) data[pos3]) & 255) << 40);
        int pos5 = pos4 + 1;
        long j4 = j3 | ((((long) data[pos4]) & 255) << 32);
        int pos6 = pos5 + 1;
        long j5 = j4 | ((((long) data[pos5]) & 255) << 24);
        int pos7 = pos6 + 1;
        long j6 = j5 | ((((long) data[pos6]) & 255) << 16);
        int pos8 = pos7 + 1;
        long j7 = j6 | ((((long) data[pos7]) & 255) << 8);
        int pos9 = pos8 + 1;
        long j8 = j7 | (((long) data[pos8]) & 255);
        this.size -= 8;
        if (pos9 == limit) {
            this.head = segment.pop();
            SegmentPool.recycle(segment);
            return j8;
        }
        segment.pos = pos9;
        return j8;
    }

    @Override // okio.BufferedSource
    public short readShortLe() {
        return Util.reverseBytesShort(readShort());
    }

    @Override // okio.BufferedSource
    public int readIntLe() {
        return Util.reverseBytesInt(readInt());
    }

    @Override // okio.BufferedSource
    public long readDecimalLong() {
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        long value = 0;
        int seen = 0;
        boolean negative = false;
        boolean done = false;
        long overflowDigit = -7;
        do {
            Segment segment = this.head;
            byte[] data = segment.data;
            int pos = segment.pos;
            int limit = segment.limit;
            while (pos < limit) {
                byte b = data[pos];
                if (b >= 48 && b <= 57) {
                    int digit = 48 - b;
                    if (value < -922337203685477580L || (value == -922337203685477580L && digit < overflowDigit)) {
                        Buffer buffer = new Buffer().writeDecimalLong(value).writeByte((int) b);
                        if (!negative) {
                            buffer.readByte();
                        }
                        throw new NumberFormatException("Number too large: " + buffer.readUtf8());
                    }
                    value = (value * 10) + ((long) digit);
                } else if (b == 45 && seen == 0) {
                    negative = true;
                    overflowDigit--;
                } else {
                    if (seen == 0) {
                        throw new NumberFormatException("Expected leading [0-9] or '-' character but was 0x" + Integer.toHexString(b));
                    }
                    done = true;
                    break;
                }
                pos++;
                seen++;
            }
            if (pos == limit) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = pos;
            }
            if (done) {
                break;
            }
        } while (this.head != null);
        this.size -= (long) seen;
        return negative ? value : -value;
    }

    @Override // okio.BufferedSource
    public long readHexadecimalUnsignedLong() {
        int digit;
        if (this.size == 0) {
            throw new IllegalStateException("size == 0");
        }
        long value = 0;
        int seen = 0;
        boolean done = false;
        do {
            Segment segment = this.head;
            byte[] data = segment.data;
            int pos = segment.pos;
            int limit = segment.limit;
            while (pos < limit) {
                byte b = data[pos];
                if (b >= 48 && b <= 57) {
                    digit = b - 48;
                } else if (b >= 97 && b <= 102) {
                    digit = (b - 97) + 10;
                } else if (b >= 65 && b <= 70) {
                    digit = (b - 65) + 10;
                } else {
                    if (seen == 0) {
                        throw new NumberFormatException("Expected leading [0-9a-fA-F] character but was 0x" + Integer.toHexString(b));
                    }
                    done = true;
                    break;
                }
                if (((-1152921504606846976L) & value) != 0) {
                    Buffer buffer = new Buffer().writeHexadecimalUnsignedLong(value).writeByte((int) b);
                    throw new NumberFormatException("Number too large: " + buffer.readUtf8());
                }
                value = (value << 4) | ((long) digit);
                pos++;
                seen++;
            }
            if (pos == limit) {
                this.head = segment.pop();
                SegmentPool.recycle(segment);
            } else {
                segment.pos = pos;
            }
            if (done) {
                break;
            }
        } while (this.head != null);
        this.size -= (long) seen;
        return value;
    }

    public ByteString readByteString() {
        return new ByteString(readByteArray());
    }

    @Override // okio.BufferedSource
    public ByteString readByteString(long byteCount) throws EOFException {
        return new ByteString(readByteArray(byteCount));
    }

    @Override // okio.BufferedSource
    public void readFully(Buffer sink, long byteCount) throws EOFException {
        if (this.size < byteCount) {
            sink.write(this, this.size);
            throw new EOFException();
        }
        sink.write(this, byteCount);
    }

    @Override // okio.BufferedSource
    public long readAll(Sink sink) throws IOException {
        long byteCount = this.size;
        if (byteCount > 0) {
            sink.write(this, byteCount);
        }
        return byteCount;
    }

    @Override // okio.BufferedSource
    public String readUtf8() {
        try {
            return readString(this.size, Util.UTF_8);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readUtf8(long byteCount) throws EOFException {
        return readString(byteCount, Util.UTF_8);
    }

    public String readString(Charset charset) {
        try {
            return readString(this.size, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String readString(long byteCount, Charset charset) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, byteCount);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (byteCount > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
        }
        if (byteCount == 0) {
            return "";
        }
        Segment s = this.head;
        if (((long) s.pos) + byteCount > s.limit) {
            return new String(readByteArray(byteCount), charset);
        }
        String str = new String(s.data, s.pos, (int) byteCount, charset);
        s.pos = (int) (((long) s.pos) + byteCount);
        this.size -= byteCount;
        if (s.pos == s.limit) {
            this.head = s.pop();
            SegmentPool.recycle(s);
            return str;
        }
        return str;
    }

    @Override // okio.BufferedSource
    public String readUtf8LineStrict() throws EOFException {
        long newline = indexOf((byte) 10);
        if (newline == -1) {
            Buffer data = new Buffer();
            copyTo(data, 0L, Math.min(32L, this.size));
            throw new EOFException("\\n not found: size=" + size() + " content=" + data.readByteString().hex() + "…");
        }
        return readUtf8Line(newline);
    }

    String readUtf8Line(long newline) throws EOFException {
        if (newline > 0 && getByte(newline - 1) == 13) {
            String result = readUtf8(newline - 1);
            skip(2L);
            return result;
        }
        String result2 = readUtf8(newline);
        skip(1L);
        return result2;
    }

    public int readUtf8CodePoint() throws EOFException {
        int codePoint;
        int byteCount;
        int min;
        if (this.size == 0) {
            throw new EOFException();
        }
        byte b0 = getByte(0L);
        if ((b0 & 128) == 0) {
            codePoint = b0 & 127;
            byteCount = 1;
            min = 0;
        } else if ((b0 & 224) == 192) {
            codePoint = b0 & 31;
            byteCount = 2;
            min = 128;
        } else if ((b0 & 240) == 224) {
            codePoint = b0 & 15;
            byteCount = 3;
            min = 2048;
        } else if ((b0 & 248) == 240) {
            codePoint = b0 & 7;
            byteCount = 4;
            min = 65536;
        } else {
            skip(1L);
            return 65533;
        }
        if (this.size < byteCount) {
            throw new EOFException("size < " + byteCount + ": " + this.size + " (to read code point prefixed 0x" + Integer.toHexString(b0) + ")");
        }
        for (int i = 1; i < byteCount; i++) {
            byte b = getByte(i);
            if ((b & 192) == 128) {
                codePoint = (codePoint << 6) | (b & 63);
            } else {
                skip(i);
                return 65533;
            }
        }
        skip(byteCount);
        if (codePoint > 1114111) {
            return 65533;
        }
        if ((codePoint < 55296 || codePoint > 57343) && codePoint >= min) {
            return codePoint;
        }
        return 65533;
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray() {
        try {
            return readByteArray(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // okio.BufferedSource
    public byte[] readByteArray(long byteCount) throws EOFException {
        Util.checkOffsetAndCount(this.size, 0L, byteCount);
        if (byteCount > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + byteCount);
        }
        byte[] result = new byte[(int) byteCount];
        readFully(result);
        return result;
    }

    @Override // okio.BufferedSource
    public void readFully(byte[] sink) throws EOFException {
        int offset = 0;
        while (offset < sink.length) {
            int read = read(sink, offset, sink.length - offset);
            if (read == -1) {
                throw new EOFException();
            }
            offset += read;
        }
    }

    @Override // okio.BufferedSource
    public int read(byte[] sink, int offset, int byteCount) {
        Util.checkOffsetAndCount(sink.length, offset, byteCount);
        Segment s = this.head;
        if (s == null) {
            return -1;
        }
        int toCopy = Math.min(byteCount, s.limit - s.pos);
        System.arraycopy(s.data, s.pos, sink, offset, toCopy);
        s.pos += toCopy;
        this.size -= (long) toCopy;
        if (s.pos == s.limit) {
            this.head = s.pop();
            SegmentPool.recycle(s);
            return toCopy;
        }
        return toCopy;
    }

    public void clear() {
        try {
            skip(this.size);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // okio.BufferedSource
    public void skip(long byteCount) throws EOFException {
        while (byteCount > 0) {
            if (this.head == null) {
                throw new EOFException();
            }
            int toSkip = (int) Math.min(byteCount, this.head.limit - this.head.pos);
            this.size -= (long) toSkip;
            byteCount -= (long) toSkip;
            this.head.pos += toSkip;
            if (this.head.pos == this.head.limit) {
                Segment toRecycle = this.head;
                this.head = toRecycle.pop();
                SegmentPool.recycle(toRecycle);
            }
        }
    }

    @Override // okio.BufferedSink
    public Buffer write(ByteString byteString) {
        if (byteString == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        byteString.write(this);
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeUtf8(String string) {
        return writeUtf8(string, 0, string.length());
    }

    public Buffer writeUtf8(String string, int beginIndex, int endIndex) {
        if (string == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (beginIndex < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + beginIndex);
        }
        if (endIndex < beginIndex) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + endIndex + " < " + beginIndex);
        }
        if (endIndex > string.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + endIndex + " > " + string.length());
        }
        int i = beginIndex;
        while (true) {
            int i2 = i;
            if (i2 < endIndex) {
                int c = string.charAt(i2);
                if (c < 128) {
                    Segment tail = writableSegment(1);
                    byte[] data = tail.data;
                    int segmentOffset = tail.limit - i2;
                    int runLimit = Math.min(endIndex, 8192 - segmentOffset);
                    data[segmentOffset + i2] = (byte) c;
                    int i3 = i2 + 1;
                    while (i3 < runLimit) {
                        int c2 = string.charAt(i3);
                        if (c2 >= 128) {
                            break;
                        }
                        data[segmentOffset + i3] = (byte) c2;
                        i3++;
                    }
                    int runSize = (i3 + segmentOffset) - tail.limit;
                    tail.limit += runSize;
                    this.size += (long) runSize;
                    i = i3;
                } else if (c < 2048) {
                    writeByte((c >> 6) | 192);
                    writeByte((c & 63) | 128);
                    i = i2 + 1;
                } else if (c < 55296 || c > 57343) {
                    writeByte((c >> 12) | 224);
                    writeByte(((c >> 6) & 63) | 128);
                    writeByte((c & 63) | 128);
                    i = i2 + 1;
                } else {
                    int low = i2 + 1 < endIndex ? string.charAt(i2 + 1) : 0;
                    if (c > 56319 || low < 56320 || low > 57343) {
                        writeByte(63);
                        i = i2 + 1;
                    } else {
                        int codePoint = 65536 + ((((-55297) & c) << 10) | ((-56321) & low));
                        writeByte((codePoint >> 18) | 240);
                        writeByte(((codePoint >> 12) & 63) | 128);
                        writeByte(((codePoint >> 6) & 63) | 128);
                        writeByte((codePoint & 63) | 128);
                        i = i2 + 2;
                    }
                }
            } else {
                return this;
            }
        }
    }

    public Buffer writeUtf8CodePoint(int codePoint) {
        if (codePoint < 128) {
            writeByte(codePoint);
        } else if (codePoint < 2048) {
            writeByte((codePoint >> 6) | 192);
            writeByte((codePoint & 63) | 128);
        } else if (codePoint < 65536) {
            if (codePoint >= 55296 && codePoint <= 57343) {
                throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(codePoint));
            }
            writeByte((codePoint >> 12) | 224);
            writeByte(((codePoint >> 6) & 63) | 128);
            writeByte((codePoint & 63) | 128);
        } else if (codePoint <= 1114111) {
            writeByte((codePoint >> 18) | 240);
            writeByte(((codePoint >> 12) & 63) | 128);
            writeByte(((codePoint >> 6) & 63) | 128);
            writeByte((codePoint & 63) | 128);
        } else {
            throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(codePoint));
        }
        return this;
    }

    public Buffer writeString(String string, Charset charset) {
        return writeString(string, 0, string.length(), charset);
    }

    public Buffer writeString(String string, int beginIndex, int endIndex, Charset charset) {
        if (string == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (beginIndex < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + beginIndex);
        }
        if (endIndex < beginIndex) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + endIndex + " < " + beginIndex);
        }
        if (endIndex > string.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + endIndex + " > " + string.length());
        }
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (charset.equals(Util.UTF_8)) {
            return writeUtf8(string);
        }
        byte[] data = string.substring(beginIndex, endIndex).getBytes(charset);
        return write(data, 0, data.length);
    }

    @Override // okio.BufferedSink
    public Buffer write(byte[] source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        return write(source, 0, source.length);
    }

    @Override // okio.BufferedSink
    public Buffer write(byte[] source, int offset, int byteCount) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        Util.checkOffsetAndCount(source.length, offset, byteCount);
        int limit = offset + byteCount;
        while (offset < limit) {
            Segment tail = writableSegment(1);
            int toCopy = Math.min(limit - offset, 8192 - tail.limit);
            System.arraycopy(source, offset, tail.data, tail.limit, toCopy);
            offset += toCopy;
            tail.limit += toCopy;
        }
        this.size += (long) byteCount;
        return this;
    }

    @Override // okio.BufferedSink
    public long writeAll(Source source) throws IOException {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        long totalBytesRead = 0;
        while (true) {
            long readCount = source.read(this, 8192L);
            if (readCount != -1) {
                totalBytesRead += readCount;
            } else {
                return totalBytesRead;
            }
        }
    }

    @Override // okio.BufferedSink
    public Buffer writeByte(int b) {
        Segment tail = writableSegment(1);
        byte[] bArr = tail.data;
        int i = tail.limit;
        tail.limit = i + 1;
        bArr[i] = (byte) b;
        this.size++;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeShort(int s) {
        Segment tail = writableSegment(2);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((s >>> 8) & 255);
        data[limit2] = (byte) (s & 255);
        tail.limit = limit2 + 1;
        this.size += 2;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeInt(int i) {
        Segment tail = writableSegment(4);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((i >>> 24) & 255);
        int limit3 = limit2 + 1;
        data[limit2] = (byte) ((i >>> 16) & 255);
        int limit4 = limit3 + 1;
        data[limit3] = (byte) ((i >>> 8) & 255);
        data[limit4] = (byte) (i & 255);
        tail.limit = limit4 + 1;
        this.size += 4;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeLong(long v) {
        Segment tail = writableSegment(8);
        byte[] data = tail.data;
        int limit = tail.limit;
        int limit2 = limit + 1;
        data[limit] = (byte) ((v >>> 56) & 255);
        int limit3 = limit2 + 1;
        data[limit2] = (byte) ((v >>> 48) & 255);
        int limit4 = limit3 + 1;
        data[limit3] = (byte) ((v >>> 40) & 255);
        int limit5 = limit4 + 1;
        data[limit4] = (byte) ((v >>> 32) & 255);
        int limit6 = limit5 + 1;
        data[limit5] = (byte) ((v >>> 24) & 255);
        int limit7 = limit6 + 1;
        data[limit6] = (byte) ((v >>> 16) & 255);
        int limit8 = limit7 + 1;
        data[limit7] = (byte) ((v >>> 8) & 255);
        data[limit8] = (byte) (v & 255);
        tail.limit = limit8 + 1;
        this.size += 8;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeDecimalLong(long v) {
        int width;
        if (v == 0) {
            return writeByte(48);
        }
        boolean negative = false;
        if (v < 0) {
            v = -v;
            if (v < 0) {
                return writeUtf8("-9223372036854775808");
            }
            negative = true;
        }
        if (v < 100000000) {
            if (v < 10000) {
                if (v < 100) {
                    width = v < 10 ? 1 : 2;
                } else {
                    width = v < 1000 ? 3 : 4;
                }
            } else if (v < 1000000) {
                width = v < 100000 ? 5 : 6;
            } else {
                width = v < 10000000 ? 7 : 8;
            }
        } else if (v < 1000000000000L) {
            if (v < 10000000000L) {
                width = v < 1000000000 ? 9 : 10;
            } else {
                width = v < 100000000000L ? 11 : 12;
            }
        } else if (v < 1000000000000000L) {
            if (v < 10000000000000L) {
                width = 13;
            } else {
                width = v < 100000000000000L ? 14 : 15;
            }
        } else if (v < 100000000000000000L) {
            width = v < 10000000000000000L ? 16 : 17;
        } else {
            width = v < 1000000000000000000L ? 18 : 19;
        }
        if (negative) {
            width++;
        }
        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        int pos = tail.limit + width;
        while (v != 0) {
            int digit = (int) (v % 10);
            pos--;
            data[pos] = DIGITS[digit];
            v /= 10;
        }
        if (negative) {
            data[pos - 1] = 45;
        }
        tail.limit += width;
        this.size += (long) width;
        return this;
    }

    @Override // okio.BufferedSink
    public Buffer writeHexadecimalUnsignedLong(long v) {
        if (v == 0) {
            return writeByte(48);
        }
        int width = (Long.numberOfTrailingZeros(Long.highestOneBit(v)) / 4) + 1;
        Segment tail = writableSegment(width);
        byte[] data = tail.data;
        int start = tail.limit;
        for (int pos = (tail.limit + width) - 1; pos >= start; pos--) {
            data[pos] = DIGITS[(int) (15 & v)];
            v >>>= 4;
        }
        tail.limit += width;
        this.size += (long) width;
        return this;
    }

    Segment writableSegment(int minimumCapacity) {
        if (minimumCapacity < 1 || minimumCapacity > 8192) {
            throw new IllegalArgumentException();
        }
        if (this.head == null) {
            this.head = SegmentPool.take();
            Segment segment = this.head;
            Segment segment2 = this.head;
            Segment segment3 = this.head;
            segment2.prev = segment3;
            segment.next = segment3;
            return segment3;
        }
        Segment tail = this.head.prev;
        if (tail.limit + minimumCapacity > 8192 || !tail.owner) {
            return tail.push(SegmentPool.take());
        }
        return tail;
    }

    @Override // okio.Sink
    public void write(Buffer source, long byteCount) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (source == this) {
            throw new IllegalArgumentException("source == this");
        }
        Util.checkOffsetAndCount(source.size, 0L, byteCount);
        while (byteCount > 0) {
            if (byteCount < source.head.limit - source.head.pos) {
                Segment tail = this.head != null ? this.head.prev : null;
                if (tail != null && tail.owner) {
                    if ((byteCount + ((long) tail.limit)) - ((long) (tail.shared ? 0 : tail.pos)) <= 8192) {
                        source.head.writeTo(tail, (int) byteCount);
                        source.size -= byteCount;
                        this.size += byteCount;
                        return;
                    }
                }
                source.head = source.head.split((int) byteCount);
            }
            Segment segmentToMove = source.head;
            long movedByteCount = segmentToMove.limit - segmentToMove.pos;
            source.head = segmentToMove.pop();
            if (this.head == null) {
                this.head = segmentToMove;
                Segment segment = this.head;
                Segment segment2 = this.head;
                Segment segment3 = this.head;
                segment2.prev = segment3;
                segment.next = segment3;
            } else {
                this.head.prev.push(segmentToMove).compact();
            }
            source.size -= movedByteCount;
            this.size += movedByteCount;
            byteCount -= movedByteCount;
        }
    }

    @Override // okio.Source
    public long read(Buffer sink, long byteCount) {
        if (sink == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (byteCount < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + byteCount);
        }
        if (this.size == 0) {
            return -1L;
        }
        if (byteCount > this.size) {
            byteCount = this.size;
        }
        sink.write(this, byteCount);
        return byteCount;
    }

    @Override // okio.BufferedSource
    public long indexOf(byte b) {
        return indexOf(b, 0L);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x004e  */
    /* JADX WARN: Code duplicated, block: B:24:0x005a  */
    /* JADX WARN: Code duplicated, block: B:27:0x0065 A[LOOP:2: B:23:0x0058->B:27:0x0065, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:30:0x005e A[SYNTHETIC] */
    public long indexOf(byte b, long fromIndex) {
        long offset;
        byte[] data;
        int pos;
        int limit;
        if (fromIndex < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        Segment s = this.head;
        if (s == null) {
            return -1L;
        }
        if (this.size - fromIndex < fromIndex) {
            offset = this.size;
            while (offset > fromIndex) {
                s = s.prev;
                offset -= (long) (s.limit - s.pos);
            }
        } else {
            offset = 0;
            while (true) {
                long nextOffset = offset + ((long) (s.limit - s.pos));
                if (nextOffset >= fromIndex) {
                    break;
                }
                s = s.next;
                offset = nextOffset;
            }
            while (offset < this.size) {
                data = s.data;
                limit = s.limit;
                for (pos = (int) ((((long) s.pos) + fromIndex) - offset); pos < limit; pos++) {
                    if (data[pos] == b) {
                        return ((long) (pos - s.pos)) + offset;
                    }
                }
                offset += (long) (s.limit - s.pos);
                fromIndex = offset;
                s = s.next;
            }
            return -1L;
        }
        while (offset < this.size) {
            data = s.data;
            limit = s.limit;
            while (pos < limit) {
                if (data[pos] == b) {
                    return ((long) (pos - s.pos)) + offset;
                }
            }
            offset += (long) (s.limit - s.pos);
            fromIndex = offset;
            s = s.next;
        }
        return -1L;
    }

    @Override // okio.BufferedSink, okio.Sink, java.io.Flushable
    public void flush() {
    }

    @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // okio.Sink
    public Timeout timeout() {
        return Timeout.NONE;
    }

    public boolean equals(Object o) {
        int posB;
        int posA;
        if (this == o) {
            return true;
        }
        if (!(o instanceof Buffer)) {
            return false;
        }
        Buffer that = (Buffer) o;
        if (this.size != that.size) {
            return false;
        }
        if (this.size == 0) {
            return true;
        }
        Segment sa = this.head;
        Segment sb = that.head;
        int posA2 = sa.pos;
        int posB2 = sb.pos;
        long pos = 0;
        while (pos < this.size) {
            long count = Math.min(sa.limit - posA2, sb.limit - posB2);
            int i = 0;
            while (true) {
                posB = posB2;
                posA = posA2;
                if (i < count) {
                    posA2 = posA + 1;
                    posB2 = posB + 1;
                    if (sa.data[posA] != sb.data[posB]) {
                        return false;
                    }
                    i++;
                }
            }
            if (posA == sa.limit) {
                sa = sa.next;
                posA2 = sa.pos;
            } else {
                posA2 = posA;
            }
            if (posB == sb.limit) {
                sb = sb.next;
                posB2 = sb.pos;
            } else {
                posB2 = posB;
            }
            pos += count;
        }
        return true;
    }

    public int hashCode() {
        Segment s = this.head;
        if (s == null) {
            return 0;
        }
        int result = 1;
        do {
            int limit = s.limit;
            for (int pos = s.pos; pos < limit; pos++) {
                result = (result * 31) + s.data[pos];
            }
            s = s.next;
        } while (s != this.head);
        return result;
    }

    public String toString() {
        return snapshot().toString();
    }

    public Buffer clone() {
        Buffer result = new Buffer();
        if (this.size != 0) {
            result.head = new Segment(this.head);
            Segment segment = result.head;
            Segment segment2 = result.head;
            Segment segment3 = result.head;
            segment2.prev = segment3;
            segment.next = segment3;
            for (Segment s = this.head.next; s != this.head; s = s.next) {
                result.head.prev.push(new Segment(s));
            }
            result.size = this.size;
        }
        return result;
    }

    public ByteString snapshot() {
        if (this.size > 2147483647L) {
            throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.size);
        }
        return snapshot((int) this.size);
    }

    public ByteString snapshot(int byteCount) {
        return byteCount == 0 ? ByteString.EMPTY : new SegmentedByteString(this, byteCount);
    }
}
