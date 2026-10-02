package com.bumptech.glide.load.resource.bitmap;

import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RecyclableBufferedInputStream extends FilterInputStream {
    private volatile byte[] buf;
    private int count;
    private int marklimit;
    private int markpos;
    private int pos;

    public RecyclableBufferedInputStream(InputStream in, byte[] buffer) {
        super(in);
        this.markpos = -1;
        if (buffer == null || buffer.length == 0) {
            throw new IllegalArgumentException("buffer is null or empty");
        }
        this.buf = buffer;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int available() throws IOException {
        InputStream localIn;
        localIn = this.in;
        if (this.buf == null || localIn == null) {
            throw streamClosed();
        }
        return (this.count - this.pos) + localIn.available();
    }

    private static IOException streamClosed() throws IOException {
        throw new IOException("BufferedInputStream is closed");
    }

    public synchronized void fixMarkLimit() {
        this.marklimit = this.buf.length;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.buf = null;
        InputStream localIn = this.in;
        this.in = null;
        if (localIn != null) {
            localIn.close();
        }
    }

    private int fillbuf(InputStream localIn, byte[] localBuf) throws IOException {
        if (this.markpos == -1 || this.pos - this.markpos >= this.marklimit) {
            int result = localIn.read(localBuf);
            if (result > 0) {
                this.markpos = -1;
                this.pos = 0;
                this.count = result;
            }
            return result;
        }
        if (this.markpos == 0 && this.marklimit > localBuf.length && this.count == localBuf.length) {
            int newLength = localBuf.length * 2;
            if (newLength > this.marklimit) {
                newLength = this.marklimit;
            }
            if (Log.isLoggable("BufferedIs", 3)) {
                Log.d("BufferedIs", "allocate buffer of length: " + newLength);
            }
            byte[] newbuf = new byte[newLength];
            System.arraycopy(localBuf, 0, newbuf, 0, localBuf.length);
            this.buf = newbuf;
            localBuf = newbuf;
        } else if (this.markpos > 0) {
            System.arraycopy(localBuf, this.markpos, localBuf, 0, localBuf.length - this.markpos);
        }
        this.pos -= this.markpos;
        this.markpos = 0;
        this.count = 0;
        int bytesread = localIn.read(localBuf, this.pos, localBuf.length - this.pos);
        this.count = bytesread <= 0 ? this.pos : this.pos + bytesread;
        return bytesread;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void mark(int readlimit) {
        this.marklimit = Math.max(this.marklimit, readlimit);
        this.markpos = this.pos;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int read() throws IOException {
        int i = -1;
        synchronized (this) {
            byte[] localBuf = this.buf;
            InputStream localIn = this.in;
            if (localBuf == null || localIn == null) {
                throw streamClosed();
            }
            if (this.pos < this.count || fillbuf(localIn, localBuf) != -1) {
                if (localBuf != this.buf && (localBuf = this.buf) == null) {
                    throw streamClosed();
                }
                if (this.count - this.pos > 0) {
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    i = localBuf[i2] & Constants.NETWORK_TYPE_UNCONNECTED;
                }
            }
        }
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:58:0x0092 A[Catch: all -> 0x000b, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0002, B:6:0x0006, B:7:0x000a, B:15:0x0013, B:17:0x0017, B:18:0x001b, B:19:0x001c, B:21:0x0022, B:24:0x002a, B:26:0x0036, B:30:0x0045, B:31:0x0048, B:33:0x004c, B:35:0x004f, B:38:0x0057, B:54:0x0086, B:58:0x0092, B:40:0x005c, B:43:0x0064, B:44:0x0067, B:46:0x006b, B:48:0x006f, B:49:0x0073, B:50:0x0074, B:53:0x007c, B:57:0x008b, B:29:0x003e), top: B:62:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:61:0x009c A[LOOP:0: B:31:0x0048->B:61:0x009c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:66:0x0089 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:67:0x0098 A[SYNTHETIC] */
    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized int read(byte[] buffer, int offset, int byteCount) throws IOException {
        int required;
        int read;
        int i = -1;
        synchronized (this) {
            byte[] localBuf = this.buf;
            if (localBuf == null) {
                throw streamClosed();
            }
            if (byteCount == 0) {
                i = 0;
            } else {
                InputStream localIn = this.in;
                if (localIn == null) {
                    throw streamClosed();
                }
                if (this.pos < this.count) {
                    int copylength = this.count - this.pos >= byteCount ? byteCount : this.count - this.pos;
                    System.arraycopy(localBuf, this.pos, buffer, offset, copylength);
                    this.pos += copylength;
                    if (copylength == byteCount || localIn.available() == 0) {
                        i = copylength;
                    } else {
                        offset += copylength;
                        required = byteCount - copylength;
                    }
                } else {
                    required = byteCount;
                }
                while (true) {
                    if (this.markpos == -1 && required >= localBuf.length) {
                        read = localIn.read(buffer, offset, required);
                        if (read == -1) {
                            if (required == byteCount) {
                                break;
                            }
                            i = byteCount - required;
                            break;
                        }
                        required -= read;
                        if (required == 0) {
                            i = byteCount;
                            break;
                        }
                        if (localIn.available() == 0) {
                            i = byteCount - required;
                            break;
                        }
                        offset += read;
                    } else {
                        if (fillbuf(localIn, localBuf) == -1) {
                            if (required == byteCount) {
                                break;
                            }
                            i = byteCount - required;
                            break;
                        }
                        if (localBuf != this.buf && (localBuf = this.buf) == null) {
                            throw streamClosed();
                        }
                        read = this.count - this.pos >= required ? required : this.count - this.pos;
                        System.arraycopy(localBuf, this.pos, buffer, offset, read);
                        this.pos += read;
                        required -= read;
                        if (required == 0) {
                            i = byteCount;
                            break;
                        }
                        if (localIn.available() == 0) {
                            i = byteCount - required;
                            break;
                        }
                        offset += read;
                    }
                }
            }
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized void reset() throws IOException {
        if (this.buf == null) {
            throw new IOException("Stream is closed");
        }
        if (-1 == this.markpos) {
            throw new InvalidMarkException("Mark has been invalidated");
        }
        this.pos = this.markpos;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public synchronized long skip(long byteCount) throws IOException {
        byte[] localBuf = this.buf;
        InputStream localIn = this.in;
        if (localBuf == null) {
            throw streamClosed();
        }
        if (byteCount < 1) {
            byteCount = 0;
        } else {
            if (localIn == null) {
                throw streamClosed();
            }
            if (this.count - this.pos >= byteCount) {
                this.pos = (int) (((long) this.pos) + byteCount);
            } else {
                long read = this.count - this.pos;
                this.pos = this.count;
                if (this.markpos != -1 && byteCount <= this.marklimit) {
                    if (fillbuf(localIn, localBuf) == -1) {
                        byteCount = read;
                    } else if (this.count - this.pos >= byteCount - read) {
                        this.pos = (int) (((long) this.pos) + (byteCount - read));
                    } else {
                        long read2 = (((long) this.count) + read) - ((long) this.pos);
                        this.pos = this.count;
                        byteCount = read2;
                    }
                } else {
                    byteCount = read + localIn.skip(byteCount - read);
                }
            }
        }
        return byteCount;
    }

    public static class InvalidMarkException extends RuntimeException {
        private static final long serialVersionUID = -4338378848813561757L;

        public InvalidMarkException(String detailMessage) {
            super(detailMessage);
        }
    }
}
