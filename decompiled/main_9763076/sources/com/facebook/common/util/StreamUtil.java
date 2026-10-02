package com.facebook.common.util;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StreamUtil {
    public static long skip(InputStream inputStream, long bytesCount) throws IOException {
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkArgument(bytesCount >= 0);
        long toSkip = bytesCount;
        while (toSkip > 0) {
            long skipped = inputStream.skip(toSkip);
            if (skipped > 0) {
                toSkip -= skipped;
            } else if (inputStream.read() != -1) {
                toSkip--;
            } else {
                return bytesCount - toSkip;
            }
        }
        return bytesCount;
    }
}
