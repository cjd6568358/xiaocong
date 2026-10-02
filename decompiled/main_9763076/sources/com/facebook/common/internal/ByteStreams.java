package com.facebook.common.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ByteStreams {
    public static long copy(InputStream from, OutputStream to) throws IOException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(to);
        byte[] buf = new byte[4096];
        long total = 0;
        while (true) {
            int r = from.read(buf);
            if (r != -1) {
                to.write(buf, 0, r);
                total += (long) r;
            } else {
                return total;
            }
        }
    }

    public static int read(InputStream in, byte[] b, int off, int len) throws IOException {
        Preconditions.checkNotNull(in);
        Preconditions.checkNotNull(b);
        if (len < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        int total = 0;
        while (total < len) {
            int result = in.read(b, off + total, len - total);
            if (result == -1) {
                break;
            }
            total += result;
        }
        return total;
    }
}
