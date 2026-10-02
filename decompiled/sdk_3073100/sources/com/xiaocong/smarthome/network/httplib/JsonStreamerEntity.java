package com.xiaocong.smarthome.network.httplib;

import android.util.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class JsonStreamerEntity implements HttpEntity {
    private final Header contentEncoding;
    private final ResponseHandlerInterface progressHandler;
    private static final UnsupportedOperationException ERR_UNSUPPORTED = new UnsupportedOperationException("Unsupported operation in this implementation.");
    private static final StringBuilder BUILDER = new StringBuilder(128);
    private static final byte[] JSON_TRUE = "true".getBytes();
    private static final byte[] JSON_FALSE = "false".getBytes();
    private static final byte[] JSON_NULL = "null".getBytes();
    private static final byte[] STREAM_NAME = escape("name");
    private static final byte[] STREAM_TYPE = escape("type");
    private static final byte[] STREAM_CONTENTS = escape("contents");
    private static final byte[] STREAM_ELAPSED = escape("_elapsed");
    private static final Header HEADER_JSON_CONTENT = new BasicHeader("Content-Type", "application/json");
    private static final Header HEADER_GZIP_ENCODING = new BasicHeader("Content-Encoding", AsyncHttpClient.ENCODING_GZIP);
    private final byte[] buffer = new byte[4096];
    private final Map<String, Object> jsonParams = new HashMap();

    public JsonStreamerEntity(ResponseHandlerInterface progressHandler, boolean useGZipCompression) {
        this.progressHandler = progressHandler;
        this.contentEncoding = useGZipCompression ? HEADER_GZIP_ENCODING : null;
    }

    public void addPart(String key, Object value) {
        this.jsonParams.put(key, value);
    }

    @Override // org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isChunked() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public long getContentLength() {
        return -1L;
    }

    @Override // org.apache.http.HttpEntity
    public Header getContentEncoding() {
        return this.contentEncoding;
    }

    @Override // org.apache.http.HttpEntity
    public Header getContentType() {
        return HEADER_JSON_CONTENT;
    }

    @Override // org.apache.http.HttpEntity
    public void consumeContent() throws UnsupportedOperationException, IOException {
    }

    @Override // org.apache.http.HttpEntity
    public InputStream getContent() throws UnsupportedOperationException, IOException {
        throw ERR_UNSUPPORTED;
    }

    @Override // org.apache.http.HttpEntity
    public void writeTo(OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalStateException("Output stream cannot be null.");
        }
        long now = System.currentTimeMillis();
        OutputStream os = this.contentEncoding != null ? new GZIPOutputStream(out, 4096) : out;
        os.write(123);
        Set<String> keys = this.jsonParams.keySet();
        for (String key : keys) {
            Object value = this.jsonParams.get(key);
            if (value != null) {
                os.write(escape(key));
                os.write(58);
                boolean isFileWrapper = value instanceof RequestParams.FileWrapper;
                if (isFileWrapper || (value instanceof RequestParams.StreamWrapper)) {
                    os.write(123);
                    if (isFileWrapper) {
                        writeToFromFile(os, (RequestParams.FileWrapper) value);
                    } else {
                        writeToFromStream(os, (RequestParams.StreamWrapper) value);
                    }
                    os.write(125);
                } else if (value instanceof Boolean) {
                    os.write(((Boolean) value).booleanValue() ? JSON_TRUE : JSON_FALSE);
                } else if (value instanceof Long) {
                    os.write((((Number) value).longValue() + "").getBytes());
                } else if (value instanceof Double) {
                    os.write((((Number) value).doubleValue() + "").getBytes());
                } else if (value instanceof Float) {
                    os.write((((Number) value).floatValue() + "").getBytes());
                } else if (value instanceof Integer) {
                    os.write((((Number) value).intValue() + "").getBytes());
                } else {
                    os.write(value.toString().getBytes());
                }
                os.write(44);
            }
        }
        os.write(STREAM_ELAPSED);
        os.write(58);
        long elapsedTime = System.currentTimeMillis() - now;
        os.write((elapsedTime + "}").getBytes());
        Log.i("JsonStreamerEntity", "Uploaded JSON in " + Math.floor(elapsedTime / 1000) + " seconds");
        os.flush();
        AsyncHttpClient.silentCloseOutputStream(os);
    }

    private void writeToFromStream(OutputStream os, RequestParams.StreamWrapper entry) throws IOException {
        writeMetaData(os, entry.name, entry.contentType);
        Base64OutputStream bos = new Base64OutputStream(os, 18);
        while (true) {
            int bytesRead = entry.inputStream.read(this.buffer);
            if (bytesRead == -1) {
                break;
            } else {
                bos.write(this.buffer, 0, bytesRead);
            }
        }
        AsyncHttpClient.silentCloseOutputStream(bos);
        endMetaData(os);
        if (entry.autoClose) {
            AsyncHttpClient.silentCloseInputStream(entry.inputStream);
        }
    }

    private void writeToFromFile(OutputStream os, RequestParams.FileWrapper wrapper) throws IOException {
        writeMetaData(os, wrapper.file.getName(), wrapper.contentType);
        int bytesWritten = 0;
        int totalSize = (int) wrapper.file.length();
        FileInputStream in = new FileInputStream(wrapper.file);
        Base64OutputStream bos = new Base64OutputStream(os, 18);
        while (true) {
            int bytesRead = in.read(this.buffer);
            if (bytesRead != -1) {
                bos.write(this.buffer, 0, bytesRead);
                bytesWritten += bytesRead;
                this.progressHandler.sendProgressMessage(bytesWritten, totalSize);
            } else {
                AsyncHttpClient.silentCloseOutputStream(bos);
                endMetaData(os);
                AsyncHttpClient.silentCloseInputStream(in);
                return;
            }
        }
    }

    private void writeMetaData(OutputStream os, String name, String contentType) throws IOException {
        os.write(STREAM_NAME);
        os.write(58);
        os.write(escape(name));
        os.write(44);
        os.write(STREAM_TYPE);
        os.write(58);
        os.write(escape(contentType));
        os.write(44);
        os.write(STREAM_CONTENTS);
        os.write(58);
        os.write(34);
    }

    private void endMetaData(OutputStream os) throws IOException {
        os.write(34);
    }

    static byte[] escape(String string) {
        if (string == null) {
            return JSON_NULL;
        }
        BUILDER.append('\"');
        int length = string.length();
        int pos = -1;
        while (true) {
            pos++;
            if (pos < length) {
                char ch = string.charAt(pos);
                switch (ch) {
                    case '\b':
                        BUILDER.append("\\b");
                        break;
                    case '\t':
                        BUILDER.append("\\t");
                        break;
                    case '\n':
                        BUILDER.append("\\n");
                        break;
                    case '\f':
                        BUILDER.append("\\f");
                        break;
                    case '\r':
                        BUILDER.append("\\r");
                        break;
                    case '\"':
                        BUILDER.append("\\\"");
                        break;
                    case '\\':
                        BUILDER.append("\\\\");
                        break;
                    default:
                        if ((ch >= 0 && ch <= 31) || ((ch >= 127 && ch <= 159) || (ch >= 8192 && ch <= 8447))) {
                            String intString = Integer.toHexString(ch);
                            BUILDER.append("\\u");
                            int intLength = 4 - intString.length();
                            for (int zero = 0; zero < intLength; zero++) {
                                BUILDER.append('0');
                            }
                            BUILDER.append(intString.toUpperCase(Locale.US));
                        } else {
                            BUILDER.append(ch);
                        }
                        break;
                }
            } else {
                BUILDER.append('\"');
                try {
                    return BUILDER.toString().getBytes();
                } finally {
                    BUILDER.setLength(0);
                }
            }
        }
    }
}
