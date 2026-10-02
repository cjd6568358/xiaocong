package com.xiaocong.smarthome.network.httplib;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class SimpleMultipartEntity implements HttpEntity {
    private final String boundary;
    private final byte[] boundaryEnd;
    private final byte[] boundaryLine;
    private int bytesWritten;
    private boolean isRepeatable;
    private final ResponseHandlerInterface progressHandler;
    private int totalSize;
    private static final byte[] CR_LF = "\r\n".getBytes();
    private static final byte[] TRANSFER_ENCODING_BINARY = "Content-Transfer-Encoding: binary\r\n".getBytes();
    private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final List<FilePart> fileParts = new ArrayList();
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    public SimpleMultipartEntity(ResponseHandlerInterface progressHandler) {
        StringBuilder buf = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            buf.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        this.boundary = buf.toString();
        this.boundaryLine = ("--" + this.boundary + "\r\n").getBytes();
        this.boundaryEnd = ("--" + this.boundary + "--\r\n").getBytes();
        this.progressHandler = progressHandler;
    }

    public void addPart(String key, String value, String contentType) {
        try {
            this.out.write(this.boundaryLine);
            this.out.write(createContentDisposition(key));
            this.out.write(createContentType(contentType));
            this.out.write(CR_LF);
            this.out.write(value.getBytes());
            this.out.write(CR_LF);
        } catch (IOException e) {
            Log.e("SimpleMultipartEntity", "addPart ByteArrayOutputStream exception", e);
        }
    }

    public void addPart(String key, String value) {
        addPart(key, value, "text/plain; charset=UTF-8");
    }

    public void addPart(String key, File file, String type) {
        this.fileParts.add(new FilePart(key, file, normalizeContentType(type)));
    }

    public void addPart(String key, String streamName, InputStream inputStream, String type) throws IOException {
        this.out.write(this.boundaryLine);
        this.out.write(createContentDisposition(key, streamName));
        this.out.write(createContentType(type));
        this.out.write(TRANSFER_ENCODING_BINARY);
        this.out.write(CR_LF);
        byte[] tmp = new byte[4096];
        while (true) {
            int l = inputStream.read(tmp);
            if (l != -1) {
                this.out.write(tmp, 0, l);
            } else {
                this.out.write(CR_LF);
                this.out.flush();
                AsyncHttpClient.silentCloseOutputStream(this.out);
                return;
            }
        }
    }

    private String normalizeContentType(String type) {
        return type == null ? RequestParams.APPLICATION_OCTET_STREAM : type;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] createContentType(String type) {
        String result = "Content-Type: " + normalizeContentType(type) + "\r\n";
        return result.getBytes();
    }

    private byte[] createContentDisposition(String key) {
        return ("Content-Disposition: form-data; name=\"" + key + "\"\r\n").getBytes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] createContentDisposition(String key, String fileName) {
        return ("Content-Disposition: form-data; name=\"" + key + "\"; filename=\"" + fileName + "\"\r\n").getBytes();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress(int count) {
        this.bytesWritten += count;
        this.progressHandler.sendProgressMessage(this.bytesWritten, this.totalSize);
    }

    private class FilePart {
        public File file;
        public byte[] header;

        public FilePart(String key, File file, String type) {
            this.header = createHeader(key, file.getName(), type);
            this.file = file;
        }

        private byte[] createHeader(String key, String filename, String type) {
            ByteArrayOutputStream headerStream = new ByteArrayOutputStream();
            try {
                headerStream.write(SimpleMultipartEntity.this.boundaryLine);
                headerStream.write(SimpleMultipartEntity.this.createContentDisposition(key, filename));
                headerStream.write(SimpleMultipartEntity.this.createContentType(type));
                headerStream.write(SimpleMultipartEntity.TRANSFER_ENCODING_BINARY);
                headerStream.write(SimpleMultipartEntity.CR_LF);
            } catch (IOException e) {
                Log.e("SimpleMultipartEntity", "createHeader ByteArrayOutputStream exception", e);
            }
            return headerStream.toByteArray();
        }

        public long getTotalLength() {
            long streamLength = this.file.length() + ((long) SimpleMultipartEntity.CR_LF.length);
            return ((long) this.header.length) + streamLength;
        }

        public void writeTo(OutputStream out) throws IOException {
            out.write(this.header);
            SimpleMultipartEntity.this.updateProgress(this.header.length);
            FileInputStream inputStream = new FileInputStream(this.file);
            byte[] tmp = new byte[4096];
            while (true) {
                int bytesRead = inputStream.read(tmp);
                if (bytesRead == -1) {
                    out.write(SimpleMultipartEntity.CR_LF);
                    SimpleMultipartEntity.this.updateProgress(SimpleMultipartEntity.CR_LF.length);
                    out.flush();
                    AsyncHttpClient.silentCloseInputStream(inputStream);
                    return;
                }
                out.write(tmp, 0, bytesRead);
                SimpleMultipartEntity.this.updateProgress(bytesRead);
            }
        }
    }

    @Override // org.apache.http.HttpEntity
    public long getContentLength() {
        long contentLen = this.out.size();
        for (FilePart filePart : this.fileParts) {
            long len = filePart.getTotalLength();
            if (len < 0) {
                return -1L;
            }
            contentLen += len;
        }
        return contentLen + ((long) this.boundaryEnd.length);
    }

    @Override // org.apache.http.HttpEntity
    public Header getContentType() {
        return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.boundary);
    }

    @Override // org.apache.http.HttpEntity
    public boolean isChunked() {
        return false;
    }

    public void setIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isRepeatable() {
        return this.isRepeatable;
    }

    @Override // org.apache.http.HttpEntity
    public boolean isStreaming() {
        return false;
    }

    @Override // org.apache.http.HttpEntity
    public void writeTo(OutputStream outstream) throws IOException {
        this.bytesWritten = 0;
        this.totalSize = (int) getContentLength();
        this.out.writeTo(outstream);
        updateProgress(this.out.size());
        for (FilePart filePart : this.fileParts) {
            filePart.writeTo(outstream);
        }
        outstream.write(this.boundaryEnd);
        updateProgress(this.boundaryEnd.length);
    }

    @Override // org.apache.http.HttpEntity
    public Header getContentEncoding() {
        return null;
    }

    @Override // org.apache.http.HttpEntity
    public void consumeContent() throws UnsupportedOperationException, IOException {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    @Override // org.apache.http.HttpEntity
    public InputStream getContent() throws UnsupportedOperationException, IOException {
        throw new UnsupportedOperationException("getContent() is not supported. Use writeTo() instead.");
    }
}
