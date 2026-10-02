package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class PooledByteStreams {
    private final ByteArrayPool mByteArrayPool;
    private final int mTempBufSize;

    public PooledByteStreams(ByteArrayPool byteArrayPool) {
        this(byteArrayPool, 16384);
    }

    PooledByteStreams(ByteArrayPool byteArrayPool, int tempBufSize) {
        Preconditions.checkArgument(tempBufSize > 0);
        this.mTempBufSize = tempBufSize;
        this.mByteArrayPool = byteArrayPool;
    }

    public long copy(InputStream from, OutputStream to) throws IOException {
        long count = 0;
        byte[] tmp = this.mByteArrayPool.get(this.mTempBufSize);
        while (true) {
            try {
                int read = from.read(tmp, 0, this.mTempBufSize);
                if (read != -1) {
                    to.write(tmp, 0, read);
                    count += (long) read;
                } else {
                    this.mByteArrayPool.release(tmp);
                    return count;
                }
            } catch (Throwable th) {
                this.mByteArrayPool.release(tmp);
                throw th;
            }
        }
    }
}
