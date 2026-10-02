package okhttp3.internal.ws;

import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.ws.WebSocket;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;
import okio.Timeout;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class WebSocketReader {
    private boolean closed;
    private long frameBytesRead;
    private final FrameCallback frameCallback;
    private long frameLength;
    private final boolean isClient;
    private boolean isControlFrame;
    private boolean isFinalFrame;
    private boolean isMasked;
    private boolean messageClosed;
    private int opcode;
    private final BufferedSource source;
    private final Source framedMessageSource = new FramedMessageSource();
    private final byte[] maskKey = new byte[4];
    private final byte[] maskBuffer = new byte[AsyncHttpClient.DEFAULT_SOCKET_BUFFER_SIZE];

    public interface FrameCallback {
        void onClose(int i, String str);

        void onMessage(ResponseBody responseBody) throws IOException;

        void onPing(Buffer buffer);

        void onPong(Buffer buffer);
    }

    public WebSocketReader(boolean isClient, BufferedSource source, FrameCallback frameCallback) {
        if (source == null) {
            throw new NullPointerException("source == null");
        }
        if (frameCallback == null) {
            throw new NullPointerException("frameCallback == null");
        }
        this.isClient = isClient;
        this.source = source;
        this.frameCallback = frameCallback;
    }

    public void processNextFrame() throws IOException {
        readHeader();
        if (this.isControlFrame) {
            readControlFrame();
        } else {
            readMessageFrame();
        }
    }

    private void readHeader() throws IOException {
        if (this.closed) {
            throw new IOException("closed");
        }
        int b0 = this.source.readByte() & 255;
        this.opcode = b0 & 15;
        this.isFinalFrame = (b0 & 128) != 0;
        this.isControlFrame = (b0 & 8) != 0;
        if (this.isControlFrame && !this.isFinalFrame) {
            throw new ProtocolException("Control frames must be final.");
        }
        boolean reservedFlag1 = (b0 & 64) != 0;
        boolean reservedFlag2 = (b0 & 32) != 0;
        boolean reservedFlag3 = (b0 & 16) != 0;
        if (reservedFlag1 || reservedFlag2 || reservedFlag3) {
            throw new ProtocolException("Reserved flags are unsupported.");
        }
        int b1 = this.source.readByte() & 255;
        this.isMasked = (b1 & 128) != 0;
        if (this.isMasked == this.isClient) {
            throw new ProtocolException("Client-sent frames must be masked. Server sent must not.");
        }
        this.frameLength = b1 & 127;
        if (this.frameLength == 126) {
            this.frameLength = ((long) this.source.readShort()) & 65535;
        } else if (this.frameLength == 127) {
            this.frameLength = this.source.readLong();
            if (this.frameLength < 0) {
                throw new ProtocolException("Frame length 0x" + Long.toHexString(this.frameLength) + " > 0x7FFFFFFFFFFFFFFF");
            }
        }
        this.frameBytesRead = 0L;
        if (this.isControlFrame && this.frameLength > 125) {
            throw new ProtocolException("Control frame must be less than 125B.");
        }
        if (this.isMasked) {
            this.source.readFully(this.maskKey);
        }
    }

    private void readControlFrame() throws IOException {
        Buffer buffer = null;
        if (this.frameBytesRead < this.frameLength) {
            buffer = new Buffer();
            if (this.isClient) {
                this.source.readFully(buffer, this.frameLength);
            } else {
                while (this.frameBytesRead < this.frameLength) {
                    int toRead = (int) Math.min(this.frameLength - this.frameBytesRead, this.maskBuffer.length);
                    int read = this.source.read(this.maskBuffer, 0, toRead);
                    if (read == -1) {
                        throw new EOFException();
                    }
                    WebSocketProtocol.toggleMask(this.maskBuffer, read, this.maskKey, this.frameBytesRead);
                    buffer.write(this.maskBuffer, 0, read);
                    this.frameBytesRead += (long) read;
                }
            }
        }
        switch (this.opcode) {
            case 8:
                int code = 1000;
                String reason = "";
                if (buffer != null) {
                    long bufferSize = buffer.size();
                    if (bufferSize == 1) {
                        throw new ProtocolException("Malformed close payload length of 1.");
                    }
                    if (bufferSize != 0) {
                        code = buffer.readShort();
                        WebSocketProtocol.validateCloseCode(code, false);
                        reason = buffer.readUtf8();
                    }
                }
                this.frameCallback.onClose(code, reason);
                this.closed = true;
                return;
            case 9:
                this.frameCallback.onPing(buffer);
                return;
            case 10:
                this.frameCallback.onPong(buffer);
                return;
            default:
                throw new ProtocolException("Unknown control opcode: " + Integer.toHexString(this.opcode));
        }
    }

    private void readMessageFrame() throws IOException {
        final MediaType type;
        switch (this.opcode) {
            case 1:
                type = WebSocket.TEXT;
                break;
            case 2:
                type = WebSocket.BINARY;
                break;
            default:
                throw new ProtocolException("Unknown opcode: " + Integer.toHexString(this.opcode));
        }
        final BufferedSource source = Okio.buffer(this.framedMessageSource);
        ResponseBody body = new ResponseBody() { // from class: okhttp3.internal.ws.WebSocketReader.1
            @Override // okhttp3.ResponseBody
            public MediaType contentType() {
                return type;
            }

            @Override // okhttp3.ResponseBody
            public long contentLength() {
                return -1L;
            }

            @Override // okhttp3.ResponseBody
            public BufferedSource source() {
                return source;
            }
        };
        this.messageClosed = false;
        this.frameCallback.onMessage(body);
        if (!this.messageClosed) {
            throw new IllegalStateException("Listener failed to call close on message payload.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readUntilNonControlFrame() throws IOException {
        while (!this.closed) {
            readHeader();
            if (this.isControlFrame) {
                readControlFrame();
            } else {
                return;
            }
        }
    }

    private final class FramedMessageSource implements Source {
        private FramedMessageSource() {
        }

        @Override // okio.Source
        public long read(Buffer sink, long byteCount) throws IOException {
            long read;
            if (WebSocketReader.this.closed) {
                throw new IOException("closed");
            }
            if (WebSocketReader.this.messageClosed) {
                throw new IllegalStateException("closed");
            }
            if (WebSocketReader.this.frameBytesRead == WebSocketReader.this.frameLength) {
                if (WebSocketReader.this.isFinalFrame) {
                    return -1L;
                }
                WebSocketReader.this.readUntilNonControlFrame();
                if (WebSocketReader.this.opcode == 0) {
                    if (WebSocketReader.this.isFinalFrame && WebSocketReader.this.frameLength == 0) {
                        return -1L;
                    }
                } else {
                    throw new ProtocolException("Expected continuation opcode. Got: " + Integer.toHexString(WebSocketReader.this.opcode));
                }
            }
            long toRead = Math.min(byteCount, WebSocketReader.this.frameLength - WebSocketReader.this.frameBytesRead);
            if (WebSocketReader.this.isMasked) {
                read = WebSocketReader.this.source.read(WebSocketReader.this.maskBuffer, 0, (int) Math.min(toRead, WebSocketReader.this.maskBuffer.length));
                if (read == -1) {
                    throw new EOFException();
                }
                WebSocketProtocol.toggleMask(WebSocketReader.this.maskBuffer, read, WebSocketReader.this.maskKey, WebSocketReader.this.frameBytesRead);
                sink.write(WebSocketReader.this.maskBuffer, 0, (int) read);
            } else {
                read = WebSocketReader.this.source.read(sink, toRead);
                if (read == -1) {
                    throw new EOFException();
                }
            }
            WebSocketReader.this.frameBytesRead += read;
            return read;
        }

        @Override // okio.Source
        public Timeout timeout() {
            return WebSocketReader.this.source.timeout();
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (!WebSocketReader.this.messageClosed) {
                WebSocketReader.this.messageClosed = true;
                if (!WebSocketReader.this.closed) {
                    WebSocketReader.this.source.skip(WebSocketReader.this.frameLength - WebSocketReader.this.frameBytesRead);
                    while (!WebSocketReader.this.isFinalFrame) {
                        WebSocketReader.this.readUntilNonControlFrame();
                        WebSocketReader.this.source.skip(WebSocketReader.this.frameLength);
                    }
                }
            }
        }
    }
}
