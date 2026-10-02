package okio;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class Util {
    public static final Charset UTF_8 = Charset.forName(AsyncHttpResponseHandler.DEFAULT_CHARSET);

    public static void checkOffsetAndCount(long size, long offset, long byteCount) {
        if ((offset | byteCount) < 0 || offset > size || size - offset < byteCount) {
            throw new ArrayIndexOutOfBoundsException(String.format("size=%s offset=%s byteCount=%s", Long.valueOf(size), Long.valueOf(offset), Long.valueOf(byteCount)));
        }
    }

    public static short reverseBytesShort(short s) {
        int i = s & 65535;
        int reversed = ((65280 & i) >>> 8) | ((i & 255) << 8);
        return (short) reversed;
    }

    public static int reverseBytesInt(int i) {
        return (((-16777216) & i) >>> 24) | ((16711680 & i) >>> 8) | ((65280 & i) << 8) | ((i & 255) << 24);
    }

    public static void sneakyRethrow(Throwable t) throws Throwable {
        sneakyThrow2(t);
    }

    private static <T extends Throwable> void sneakyThrow2(Throwable t) throws Throwable {
        throw t;
    }

    public static boolean arrayRangeEquals(byte[] a, int aOffset, byte[] b, int bOffset, int byteCount) {
        for (int i = 0; i < byteCount; i++) {
            if (a[i + aOffset] != b[i + bOffset]) {
                return false;
            }
        }
        return true;
    }
}
