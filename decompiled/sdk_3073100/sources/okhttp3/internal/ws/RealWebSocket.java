package okhttp3.internal.ws;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.NamedRunnable;
import okhttp3.ws.WebSocket;
import okhttp3.ws.WebSocketListener;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class RealWebSocket implements WebSocket {
    private static final int CLOSE_PROTOCOL_EXCEPTION = 1002;
    private final AtomicBoolean connectionClosed = new AtomicBoolean();
    private final WebSocketListener listener;
    private final WebSocketReader reader;
    private boolean readerSentClose;
    private final WebSocketWriter writer;
    private volatile boolean writerSentClose;
    private boolean writerWantsClose;

    protected abstract void close() throws IOException;

    public RealWebSocket(boolean isClient, BufferedSource source, BufferedSink sink, Random random, final Executor replyExecutor, final WebSocketListener listener, final String url) {
        this.listener = listener;
        this.writer = new WebSocketWriter(isClient, sink, random);
        this.reader = new WebSocketReader(isClient, source, new WebSocketReader.FrameCallback() { // from class: okhttp3.internal.ws.RealWebSocket.1
            @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
            public void onMessage(ResponseBody message) throws IOException {
                listener.onMessage(message);
            }

            @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
            public void onPing(final Buffer buffer) {
                replyExecutor.execute(new NamedRunnable("OkHttp %s WebSocket Pong Reply", new Object[]{url}) { // from class: okhttp3.internal.ws.RealWebSocket.1.1
                    @Override // okhttp3.internal.NamedRunnable
                    protected void execute() {
                        try {
                            RealWebSocket.this.writer.writePong(buffer);
                        } catch (IOException e) {
                        }
                    }
                });
            }

            @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
            public void onPong(Buffer buffer) {
                listener.onPong(buffer);
            }

            @Override // okhttp3.internal.ws.WebSocketReader.FrameCallback
            public void onClose(final int code, final String reason) {
                RealWebSocket.this.readerSentClose = true;
                replyExecutor.execute(new NamedRunnable("OkHttp %s WebSocket Close Reply", new Object[]{url}) { // from class: okhttp3.internal.ws.RealWebSocket.1.2
                    @Override // okhttp3.internal.NamedRunnable
                    protected void execute() {
                        RealWebSocket.this.peerClose(code, reason);
                    }
                });
            }
        });
    }

    public boolean readMessage() {
        try {
            this.reader.processNextFrame();
            return !this.readerSentClose;
        } catch (IOException e) {
            readerErrorClose(e);
            return false;
        }
    }

    @Override // okhttp3.ws.WebSocket
    public void sendMessage(RequestBody message) throws IOException {
        int formatOpcode;
        if (message == null) {
            throw new NullPointerException("message == null");
        }
        if (this.writerSentClose) {
            throw new IllegalStateException("closed");
        }
        if (this.writerWantsClose) {
            throw new IllegalStateException("must call close()");
        }
        MediaType contentType = message.contentType();
        if (contentType == null) {
            throw new IllegalArgumentException("Message content type was null. Must use WebSocket.TEXT or WebSocket.BINARY.");
        }
        String contentSubtype = contentType.subtype();
        if (WebSocket.TEXT.subtype().equals(contentSubtype)) {
            formatOpcode = 1;
        } else if (WebSocket.BINARY.subtype().equals(contentSubtype)) {
            formatOpcode = 2;
        } else {
            throw new IllegalArgumentException("Unknown message content type: " + contentType.type() + "/" + contentType.subtype() + ". Must use WebSocket.TEXT or WebSocket.BINARY.");
        }
        BufferedSink sink = Okio.buffer(this.writer.newMessageSink(formatOpcode, message.contentLength()));
        try {
            message.writeTo(sink);
            sink.close();
        } catch (IOException e) {
            this.writerWantsClose = true;
            throw e;
        }
    }

    @Override // okhttp3.ws.WebSocket
    public void sendPing(Buffer payload) throws IOException {
        if (this.writerSentClose) {
            throw new IllegalStateException("closed");
        }
        if (this.writerWantsClose) {
            throw new IllegalStateException("must call close()");
        }
        try {
            this.writer.writePing(payload);
        } catch (IOException e) {
            this.writerWantsClose = true;
            throw e;
        }
    }

    public void sendPong(Buffer payload) throws IOException {
        if (this.writerSentClose) {
            throw new IllegalStateException("closed");
        }
        if (this.writerWantsClose) {
            throw new IllegalStateException("must call close()");
        }
        try {
            this.writer.writePong(payload);
        } catch (IOException e) {
            this.writerWantsClose = true;
            throw e;
        }
    }

    @Override // okhttp3.ws.WebSocket
    public void close(int code, String reason) throws IOException {
        if (this.writerSentClose) {
            throw new IllegalStateException("closed");
        }
        this.writerSentClose = true;
        try {
            this.writer.writeClose(code, reason);
        } catch (IOException e) {
            if (this.connectionClosed.compareAndSet(false, true)) {
                try {
                    close();
                } catch (IOException e2) {
                }
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void peerClose(int code, String reason) {
        if (!this.writerSentClose) {
            try {
                this.writer.writeClose(code, reason);
            } catch (IOException e) {
            }
        }
        if (this.connectionClosed.compareAndSet(false, true)) {
            try {
                close();
            } catch (IOException e2) {
            }
        }
        this.listener.onClose(code, reason);
    }

    private void readerErrorClose(IOException e) {
        if (!this.writerSentClose && (e instanceof ProtocolException)) {
            try {
                this.writer.writeClose(CLOSE_PROTOCOL_EXCEPTION, null);
            } catch (IOException e2) {
            }
        }
        if (this.connectionClosed.compareAndSet(false, true)) {
            try {
                close();
            } catch (IOException e3) {
            }
        }
        this.listener.onFailure(e, null);
    }
}
