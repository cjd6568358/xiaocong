package com.facebook.common.media;

import android.webkit.MimeTypeMap;
import com.facebook.common.internal.ImmutableMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MediaUtils {
    public static final Map<String, String> ADDITIONAL_ALLOWED_MIME_TYPES = ImmutableMap.of("mkv", "video/x-matroska");

    public static boolean isVideo(String mimeType) {
        return mimeType != null && mimeType.startsWith("video/");
    }

    public static String extractMime(String path) {
        String extension = extractExtension(path);
        if (extension == null) {
            return null;
        }
        String extension2 = extension.toLowerCase(Locale.US);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension2);
        if (mimeType == null) {
            return ADDITIONAL_ALLOWED_MIME_TYPES.get(extension2);
        }
        return mimeType;
    }

    private static String extractExtension(String path) {
        int pos = path.lastIndexOf(46);
        if (pos < 0 || pos == path.length() - 1) {
            return null;
        }
        return path.substring(pos + 1);
    }
}
