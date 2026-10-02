package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class ResponseBody implements Closeable {
    private Reader reader;

    public abstract long contentLength();

    public abstract MediaType contentType();

    public abstract BufferedSource source();

    public final InputStream byteStream() {
        return source().inputStream();
    }

    public final byte[] bytes() throws IOException {
        long contentLength = contentLength();
        if (contentLength > 2147483647L) {
            throw new IOException("Cannot buffer entire body for content length: " + contentLength);
        }
        BufferedSource source = source();
        try {
            byte[] bytes = source.readByteArray();
            Util.closeQuietly(source);
            if (contentLength != -1 && contentLength != bytes.length) {
                throw new IOException("Content-Length and stream length disagree");
            }
            return bytes;
        } catch (Throwable th) {
            Util.closeQuietly(source);
            throw th;
        }
    }

    public final Reader charStream() {
        Reader r = this.reader;
        if (r != null) {
            return r;
        }
        Reader r2 = new InputStreamReader(byteStream(), charset());
        this.reader = r2;
        return r2;
    }

    public final String string() throws IOException {
        return new String(bytes(), charset().name());
    }

    private Charset charset() {
        MediaType contentType = contentType();
        return contentType != null ? contentType.charset(Util.UTF_8) : Util.UTF_8;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Util.closeQuietly(source());
    }

    public static ResponseBody create(MediaType contentType, String content) {
        Charset charset = Util.UTF_8;
        if (contentType != null && (charset = contentType.charset()) == null) {
            charset = Util.UTF_8;
            contentType = MediaType.parse(contentType + "; charset=utf-8");
        }
        Buffer buffer = new Buffer().writeString(content, charset);
        return create(contentType, buffer.size(), buffer);
    }

    public static ResponseBody create(MediaType contentType, byte[] content) {
        Buffer buffer = new Buffer().write(content);
        return create(contentType, content.length, buffer);
    }

    public static ResponseBody create(final MediaType contentType, final long contentLength, final BufferedSource content) {
        if (content == null) {
            throw new NullPointerException("source == null");
        }
        return new ResponseBody() { // from class: okhttp3.ResponseBody.1
            @Override // okhttp3.ResponseBody
            public MediaType contentType() {
                return contentType;
            }

            @Override // okhttp3.ResponseBody
            public long contentLength() {
                return contentLength;
            }

            @Override // okhttp3.ResponseBody
            public BufferedSource source() {
                return content;
            }
        };
    }
}
