package com.facebook.imageformat;

import com.facebook.common.internal.Preconditions;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class GifFormatChecker {
    private static final byte[] FRAME_HEADER_START = {0, 33, -7, 4};
    private static final byte[] FRAME_HEADER_END_1 = {0, 44};
    private static final byte[] FRAME_HEADER_END_2 = {0, 33};

    public static boolean isAnimated(InputStream source) {
        byte[] buffer = new byte[10];
        try {
            source.read(buffer, 0, 10);
            int frameHeaders = 0;
            for (int offset = 0; source.read(buffer, offset, 1) > 0; offset = (offset + 1) % buffer.length) {
                if (circularBufferMatchesBytePattern(buffer, offset + 1, FRAME_HEADER_START) && ((circularBufferMatchesBytePattern(buffer, offset + 9, FRAME_HEADER_END_1) || circularBufferMatchesBytePattern(buffer, offset + 9, FRAME_HEADER_END_2)) && (frameHeaders = frameHeaders + 1) > 1)) {
                    return true;
                }
            }
            return false;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    static boolean circularBufferMatchesBytePattern(byte[] byteArray, int offset, byte[] pattern) {
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkNotNull(pattern);
        Preconditions.checkArgument(offset >= 0);
        if (pattern.length > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[(i + offset) % byteArray.length] != pattern[i]) {
                return false;
            }
        }
        return true;
    }
}
