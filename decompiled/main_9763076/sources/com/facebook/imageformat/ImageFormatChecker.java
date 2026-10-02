package com.facebook.imageformat;

import com.facebook.common.internal.ByteStreams;
import com.facebook.common.internal.Ints;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.webp.WebpSupportStatus;
import com.qq.taf.jce.JceStruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ImageFormatChecker {
    private static final byte[] JPEG_HEADER = {-1, -40, -1};
    private static final byte[] PNG_HEADER = {-119, 80, 78, 71, JceStruct.SIMPLE_LIST, 10, 26, 10};
    private static final byte[] GIF_HEADER_87A = asciiBytes("GIF87a");
    private static final byte[] GIF_HEADER_89A = asciiBytes("GIF89a");
    private static final byte[] BMP_HEADER = asciiBytes("BM");
    private static final int MAX_HEADER_LENGTH = Ints.max(21, 20, JPEG_HEADER.length, PNG_HEADER.length, 6, BMP_HEADER.length);

    private static ImageFormat doGetImageFormat(byte[] imageHeaderBytes, int headerSize) {
        Preconditions.checkNotNull(imageHeaderBytes);
        if (WebpSupportStatus.isWebpHeader(imageHeaderBytes, 0, headerSize)) {
            return getWebpFormat(imageHeaderBytes, headerSize);
        }
        if (isJpegHeader(imageHeaderBytes, headerSize)) {
            return ImageFormat.JPEG;
        }
        if (isPngHeader(imageHeaderBytes, headerSize)) {
            return ImageFormat.PNG;
        }
        if (isGifHeader(imageHeaderBytes, headerSize)) {
            return ImageFormat.GIF;
        }
        if (isBmpHeader(imageHeaderBytes, headerSize)) {
            return ImageFormat.BMP;
        }
        return ImageFormat.UNKNOWN;
    }

    private static int readHeaderFromStream(InputStream is, byte[] imageHeaderBytes) throws IOException {
        Preconditions.checkNotNull(is);
        Preconditions.checkNotNull(imageHeaderBytes);
        Preconditions.checkArgument(imageHeaderBytes.length >= MAX_HEADER_LENGTH);
        if (is.markSupported()) {
            try {
                is.mark(MAX_HEADER_LENGTH);
                return ByteStreams.read(is, imageHeaderBytes, 0, MAX_HEADER_LENGTH);
            } finally {
                is.reset();
            }
        }
        return ByteStreams.read(is, imageHeaderBytes, 0, MAX_HEADER_LENGTH);
    }

    public static ImageFormat getImageFormat(InputStream is) throws IOException {
        Preconditions.checkNotNull(is);
        byte[] imageHeaderBytes = new byte[MAX_HEADER_LENGTH];
        int headerSize = readHeaderFromStream(is, imageHeaderBytes);
        return doGetImageFormat(imageHeaderBytes, headerSize);
    }

    public static ImageFormat getImageFormat_WrapIOException(InputStream is) {
        try {
            return getImageFormat(is);
        } catch (IOException ioe) {
            throw Throwables.propagate(ioe);
        }
    }

    private static boolean matchBytePattern(byte[] byteArray, int offset, byte[] pattern) {
        Preconditions.checkNotNull(byteArray);
        Preconditions.checkNotNull(pattern);
        Preconditions.checkArgument(offset >= 0);
        if (pattern.length + offset > byteArray.length) {
            return false;
        }
        for (int i = 0; i < pattern.length; i++) {
            if (byteArray[i + offset] != pattern[i]) {
                return false;
            }
        }
        return true;
    }

    private static byte[] asciiBytes(String value) {
        Preconditions.checkNotNull(value);
        try {
            return value.getBytes(HTTP.ASCII);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("ASCII not found!", uee);
        }
    }

    private static ImageFormat getWebpFormat(byte[] imageHeaderBytes, int headerSize) {
        Preconditions.checkArgument(WebpSupportStatus.isWebpHeader(imageHeaderBytes, 0, headerSize));
        if (WebpSupportStatus.isSimpleWebpHeader(imageHeaderBytes, 0)) {
            return ImageFormat.WEBP_SIMPLE;
        }
        if (WebpSupportStatus.isLosslessWebpHeader(imageHeaderBytes, 0)) {
            return ImageFormat.WEBP_LOSSLESS;
        }
        if (WebpSupportStatus.isExtendedWebpHeader(imageHeaderBytes, 0, headerSize)) {
            if (WebpSupportStatus.isAnimatedWebpHeader(imageHeaderBytes, 0)) {
                return ImageFormat.WEBP_ANIMATED;
            }
            if (WebpSupportStatus.isExtendedWebpHeaderWithAlpha(imageHeaderBytes, 0)) {
                return ImageFormat.WEBP_EXTENDED_WITH_ALPHA;
            }
            return ImageFormat.WEBP_EXTENDED;
        }
        return ImageFormat.UNKNOWN;
    }

    private static boolean isJpegHeader(byte[] imageHeaderBytes, int headerSize) {
        return headerSize >= JPEG_HEADER.length && matchBytePattern(imageHeaderBytes, 0, JPEG_HEADER);
    }

    private static boolean isPngHeader(byte[] imageHeaderBytes, int headerSize) {
        return headerSize >= PNG_HEADER.length && matchBytePattern(imageHeaderBytes, 0, PNG_HEADER);
    }

    private static boolean isGifHeader(byte[] imageHeaderBytes, int headerSize) {
        if (headerSize < 6) {
            return false;
        }
        return matchBytePattern(imageHeaderBytes, 0, GIF_HEADER_87A) || matchBytePattern(imageHeaderBytes, 0, GIF_HEADER_89A);
    }

    private static boolean isBmpHeader(byte[] imageHeaderBytes, int headerSize) {
        if (headerSize < BMP_HEADER.length) {
            return false;
        }
        return matchBytePattern(imageHeaderBytes, 0, BMP_HEADER);
    }
}
