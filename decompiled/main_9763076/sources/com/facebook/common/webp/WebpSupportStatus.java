package com.facebook.common.webp;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import java.io.UnsupportedEncodingException;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class WebpSupportStatus {
    private static final byte[] WEBP_NAME_BYTES;
    private static final byte[] WEBP_RIFF_BYTES;
    private static final byte[] WEBP_VP8L_BYTES;
    private static final byte[] WEBP_VP8X_BYTES;
    private static final byte[] WEBP_VP8_BYTES;
    public static final boolean sIsExtendedWebpSupported;
    public static final boolean sIsSimpleWebpSupported;
    public static final boolean sIsWebpSupportRequired;
    public static WebpBitmapFactory sWebpBitmapFactory;
    public static boolean sWebpLibraryPresent;

    static {
        sIsWebpSupportRequired = Build.VERSION.SDK_INT <= 17;
        sIsSimpleWebpSupported = Build.VERSION.SDK_INT >= 14;
        sIsExtendedWebpSupported = isExtendedWebpSupported();
        sWebpBitmapFactory = null;
        sWebpLibraryPresent = false;
        try {
            sWebpBitmapFactory = (WebpBitmapFactory) Class.forName("com.facebook.webpsupport.WebpBitmapFactoryImpl").newInstance();
            sWebpLibraryPresent = true;
        } catch (Throwable th) {
            sWebpLibraryPresent = false;
        }
        WEBP_RIFF_BYTES = asciiBytes("RIFF");
        WEBP_NAME_BYTES = asciiBytes("WEBP");
        WEBP_VP8_BYTES = asciiBytes("VP8 ");
        WEBP_VP8L_BYTES = asciiBytes("VP8L");
        WEBP_VP8X_BYTES = asciiBytes("VP8X");
    }

    private static byte[] asciiBytes(String value) {
        try {
            return value.getBytes(HTTP.ASCII);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("ASCII not found!", uee);
        }
    }

    private static boolean isExtendedWebpSupported() {
        if (Build.VERSION.SDK_INT < 17) {
            return false;
        }
        if (Build.VERSION.SDK_INT == 17) {
            byte[] decodedBytes = Base64.decode("UklGRkoAAABXRUJQVlA4WAoAAAAQAAAAAAAAAAAAQUxQSAwAAAARBxAR/Q9ERP8DAABWUDggGAAAABQBAJ0BKgEAAQAAAP4AAA3AAP7mtQAAAA==", 0);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length, opts);
            if (opts.outHeight != 1 || opts.outWidth != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnimatedWebpHeader(byte[] imageHeaderBytes, int offset) {
        boolean isVp8x = matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES);
        boolean hasAnimationBit = (imageHeaderBytes[offset + 20] & 2) == 2;
        return isVp8x && hasAnimationBit;
    }

    public static boolean isSimpleWebpHeader(byte[] imageHeaderBytes, int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8_BYTES);
    }

    public static boolean isLosslessWebpHeader(byte[] imageHeaderBytes, int offset) {
        return matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8L_BYTES);
    }

    public static boolean isExtendedWebpHeader(byte[] imageHeaderBytes, int offset, int headerSize) {
        return headerSize >= 21 && matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES);
    }

    public static boolean isExtendedWebpHeaderWithAlpha(byte[] imageHeaderBytes, int offset) {
        boolean isVp8x = matchBytePattern(imageHeaderBytes, offset + 12, WEBP_VP8X_BYTES);
        boolean hasAlphaBit = (imageHeaderBytes[offset + 20] & 16) == 16;
        return isVp8x && hasAlphaBit;
    }

    public static boolean isWebpHeader(byte[] imageHeaderBytes, int offset, int headerSize) {
        return headerSize >= 20 && matchBytePattern(imageHeaderBytes, offset, WEBP_RIFF_BYTES) && matchBytePattern(imageHeaderBytes, offset + 8, WEBP_NAME_BYTES);
    }

    private static boolean matchBytePattern(byte[] byteArray, int offset, byte[] pattern) {
        if (pattern == null || byteArray == null || pattern.length + offset > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[i + offset] != pattern[i]) {
                return false;
            }
        }
        return true;
    }
}
