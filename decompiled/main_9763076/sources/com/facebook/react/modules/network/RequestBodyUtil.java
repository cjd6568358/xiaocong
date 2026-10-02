package com.facebook.react.modules.network;

import android.content.Context;
import android.net.Uri;
import com.facebook.common.logging.FLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Source;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class RequestBodyUtil {
    public static boolean isGzipEncoding(String encodingType) {
        return "gzip".equalsIgnoreCase(encodingType);
    }

    public static InputStream getFileInputStream(Context context, String fileContentUriStr) {
        try {
            Uri fileContentUri = Uri.parse(fileContentUriStr);
            return context.getContentResolver().openInputStream(fileContentUri);
        } catch (Exception e) {
            FLog.e("React", "Could not retrieve file for contentUri " + fileContentUriStr, e);
            return null;
        }
    }

    public static RequestBody createGzip(MediaType mediaType, String body) {
        ByteArrayOutputStream gzipByteArrayOutputStream = new ByteArrayOutputStream();
        try {
            OutputStream gzipOutputStream = new GZIPOutputStream(gzipByteArrayOutputStream);
            gzipOutputStream.write(body.getBytes());
            gzipOutputStream.close();
            return RequestBody.create(mediaType, gzipByteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }

    public static RequestBody create(final MediaType mediaType, final InputStream inputStream) {
        return new RequestBody() { // from class: com.facebook.react.modules.network.RequestBodyUtil.1
            public MediaType contentType() {
                return mediaType;
            }

            public long contentLength() {
                try {
                    return inputStream.available();
                } catch (IOException e) {
                    return 0L;
                }
            }

            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(inputStream);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }

    public static ProgressRequestBody createProgressRequest(RequestBody requestBody, ProgressListener listener) {
        return new ProgressRequestBody(requestBody, listener);
    }

    public static RequestBody getEmptyBody(String method) {
        if (method.equals(HttpPost.METHOD_NAME) || method.equals(HttpPut.METHOD_NAME) || method.equals("PATCH")) {
            return RequestBody.create((MediaType) null, ByteString.EMPTY);
        }
        return null;
    }
}
