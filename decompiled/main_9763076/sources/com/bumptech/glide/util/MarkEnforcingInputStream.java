package com.bumptech.glide.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MarkEnforcingInputStream extends FilterInputStream {
    private int availableBytes;

    public MarkEnforcingInputStream(InputStream in) {
        super(in);
        this.availableBytes = Integer.MIN_VALUE;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void mark(int readlimit) {
        super.mark(readlimit);
        this.availableBytes = readlimit;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (getBytesToRead(1L) == -1) {
            return -1;
        }
        int i = super.read();
        updateAvailableBytesAfterRead(1L);
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        int toRead = (int) getBytesToRead(byteCount);
        if (toRead == -1) {
            return -1;
        }
        int read = super.read(buffer, byteOffset, toRead);
        updateAvailableBytesAfterRead(read);
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public void reset() throws IOException {
        super.reset();
        this.availableBytes = Integer.MIN_VALUE;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long byteCount) throws IOException {
        long toSkip = getBytesToRead(byteCount);
        if (toSkip == -1) {
            return -1L;
        }
        long read = super.skip(toSkip);
        updateAvailableBytesAfterRead(read);
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        return this.availableBytes == Integer.MIN_VALUE ? super.available() : Math.min(this.availableBytes, super.available());
    }

    private long getBytesToRead(long targetByteCount) {
        if (this.availableBytes == 0) {
            return -1L;
        }
        if (this.availableBytes != Integer.MIN_VALUE && targetByteCount > this.availableBytes) {
            return this.availableBytes;
        }
        return targetByteCount;
    }

    private void updateAvailableBytesAfterRead(long bytesRead) {
        if (this.availableBytes != Integer.MIN_VALUE && bytesRead != -1) {
            this.availableBytes = (int) (((long) this.availableBytes) - bytesRead);
        }
    }
}
