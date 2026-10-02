package okhttp3.internal.framed;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okhttp3.Protocol;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class FramedConnection implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
    private static final ExecutorService executor;
    long bytesLeftInWriteWindow;
    final boolean client;
    private final Set<Integer> currentPushRequests;
    final FrameWriter frameWriter;
    private final String hostname;
    private int lastGoodStreamId;
    private final Listener listener;
    private int nextPingId;
    private int nextStreamId;
    Settings okHttpSettings;
    final Settings peerSettings;
    private Map<Integer, Ping> pings;
    final Protocol protocol;
    private final ExecutorService pushExecutor;
    private final PushObserver pushObserver;
    final Reader readerRunnable;
    private boolean receivedInitialPeerSettings;
    private boolean shutdown;
    final Socket socket;
    private final Map<Integer, FramedStream> streams;
    long unacknowledgedBytesRead;
    final Variant variant;

    static {
        $assertionsDisabled = !FramedConnection.class.desiredAssertionStatus();
        executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp FramedConnection", true));
    }

    private FramedConnection(Builder builder) {
        this.streams = new HashMap();
        this.unacknowledgedBytesRead = 0L;
        this.okHttpSettings = new Settings();
        this.peerSettings = new Settings();
        this.receivedInitialPeerSettings = false;
        this.currentPushRequests = new LinkedHashSet();
        this.protocol = builder.protocol;
        this.pushObserver = builder.pushObserver;
        this.client = builder.client;
        this.listener = builder.listener;
        this.nextStreamId = builder.client ? 1 : 2;
        if (builder.client && this.protocol == Protocol.HTTP_2) {
            this.nextStreamId += 2;
        }
        this.nextPingId = builder.client ? 1 : 2;
        if (builder.client) {
            this.okHttpSettings.set(7, 0, OKHTTP_CLIENT_WINDOW_SIZE);
        }
        this.hostname = builder.hostname;
        if (this.protocol == Protocol.HTTP_2) {
            this.variant = new Http2();
            this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory(Util.format("OkHttp %s Push Observer", this.hostname), true));
            this.peerSettings.set(7, 0, 65535);
            this.peerSettings.set(5, 0, 16384);
        } else if (this.protocol == Protocol.SPDY_3) {
            this.variant = new Spdy3();
            this.pushExecutor = null;
        } else {
            throw new AssertionError(this.protocol);
        }
        this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize(65536);
        this.socket = builder.socket;
        this.frameWriter = this.variant.newWriter(builder.sink, this.client);
        this.readerRunnable = new Reader(this.variant.newReader(builder.source, this.client));
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public synchronized int openStreamCount() {
        return this.streams.size();
    }

    synchronized FramedStream getStream(int id) {
        return this.streams.get(Integer.valueOf(id));
    }

    synchronized FramedStream removeStream(int streamId) {
        FramedStream stream;
        stream = this.streams.remove(Integer.valueOf(streamId));
        notifyAll();
        return stream;
    }

    public synchronized int maxConcurrentStreams() {
        return this.peerSettings.getMaxConcurrentStreams(Integer.MAX_VALUE);
    }

    public FramedStream pushStream(int associatedStreamId, List<Header> requestHeaders, boolean out) throws IOException {
        if (this.client) {
            throw new IllegalStateException("Client cannot push requests.");
        }
        if (this.protocol != Protocol.HTTP_2) {
            throw new IllegalStateException("protocol != HTTP_2");
        }
        return newStream(associatedStreamId, requestHeaders, out, false);
    }

    public FramedStream newStream(List<Header> requestHeaders, boolean out, boolean in) throws IOException {
        return newStream(0, requestHeaders, out, in);
    }

    private FramedStream newStream(int associatedStreamId, List<Header> requestHeaders, boolean out, boolean in) throws IOException {
        int streamId;
        FramedStream stream;
        boolean flushHeaders;
        boolean outFinished = !out;
        boolean inFinished = !in;
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                streamId = this.nextStreamId;
                this.nextStreamId += 2;
                stream = new FramedStream(streamId, this, outFinished, inFinished, requestHeaders);
                flushHeaders = !out || this.bytesLeftInWriteWindow == 0 || stream.bytesLeftInWriteWindow == 0;
                if (stream.isOpen()) {
                    this.streams.put(Integer.valueOf(streamId), stream);
                }
            }
            if (associatedStreamId == 0) {
                this.frameWriter.synStream(outFinished, inFinished, streamId, associatedStreamId, requestHeaders);
            } else {
                if (this.client) {
                    throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
                }
                this.frameWriter.pushPromise(associatedStreamId, streamId, requestHeaders);
            }
        }
        if (flushHeaders) {
            this.frameWriter.flush();
        }
        return stream;
    }

    void writeSynReply(int streamId, boolean outFinished, List<Header> alternating) throws IOException {
        this.frameWriter.synReply(outFinished, streamId, alternating);
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x0059 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void writeData(int streamId, boolean outFinished, Buffer buffer, long byteCount) throws IOException {
        int toWrite;
        if (byteCount == 0) {
            this.frameWriter.data(outFinished, streamId, buffer, 0);
            return;
        }
        while (byteCount > 0) {
            synchronized (this) {
                while (this.bytesLeftInWriteWindow <= 0) {
                    try {
                        if (!this.streams.containsKey(Integer.valueOf(streamId))) {
                            throw new IOException("stream closed");
                        }
                        wait();
                    } catch (InterruptedException e) {
                        throw new InterruptedIOException();
                    }
                    throw th;
                }
                toWrite = Math.min((int) Math.min(byteCount, this.bytesLeftInWriteWindow), this.frameWriter.maxDataLength());
                this.bytesLeftInWriteWindow -= (long) toWrite;
            }
            byteCount -= (long) toWrite;
            this.frameWriter.data(outFinished && byteCount == 0, streamId, buffer, toWrite);
        }
    }

    void addBytesToWriteWindow(long delta) {
        this.bytesLeftInWriteWindow += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    void writeSynResetLater(final int streamId, final ErrorCode errorCode) {
        executor.submit(new NamedRunnable("OkHttp %s stream %d", new Object[]{this.hostname, Integer.valueOf(streamId)}) { // from class: okhttp3.internal.framed.FramedConnection.1
            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                try {
                    FramedConnection.this.writeSynReset(streamId, errorCode);
                } catch (IOException e) {
                }
            }
        });
    }

    void writeSynReset(int streamId, ErrorCode statusCode) throws IOException {
        this.frameWriter.rstStream(streamId, statusCode);
    }

    void writeWindowUpdateLater(final int streamId, final long unacknowledgedBytesRead) {
        executor.execute(new NamedRunnable("OkHttp Window Update %s stream %d", new Object[]{this.hostname, Integer.valueOf(streamId)}) { // from class: okhttp3.internal.framed.FramedConnection.2
            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                try {
                    FramedConnection.this.frameWriter.windowUpdate(streamId, unacknowledgedBytesRead);
                } catch (IOException e) {
                }
            }
        });
    }

    public Ping ping() throws IOException {
        int pingId;
        Ping ping = new Ping();
        synchronized (this) {
            if (this.shutdown) {
                throw new IOException("shutdown");
            }
            pingId = this.nextPingId;
            this.nextPingId += 2;
            if (this.pings == null) {
                this.pings = new HashMap();
            }
            this.pings.put(Integer.valueOf(pingId), ping);
        }
        writePing(false, pingId, 1330343787, ping);
        return ping;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writePingLater(final boolean reply, final int payload1, final int payload2, final Ping ping) {
        executor.execute(new NamedRunnable("OkHttp %s ping %08x%08x", new Object[]{this.hostname, Integer.valueOf(payload1), Integer.valueOf(payload2)}) { // from class: okhttp3.internal.framed.FramedConnection.3
            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                try {
                    FramedConnection.this.writePing(reply, payload1, payload2, ping);
                } catch (IOException e) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writePing(boolean reply, int payload1, int payload2, Ping ping) throws IOException {
        synchronized (this.frameWriter) {
            if (ping != null) {
                ping.send();
                this.frameWriter.ping(reply, payload1, payload2);
            } else {
                this.frameWriter.ping(reply, payload1, payload2);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Ping removePing(int id) {
        return this.pings != null ? this.pings.remove(Integer.valueOf(id)) : null;
    }

    public void flush() throws IOException {
        this.frameWriter.flush();
    }

    public void shutdown(ErrorCode statusCode) throws IOException {
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (!this.shutdown) {
                    this.shutdown = true;
                    int lastGoodStreamId = this.lastGoodStreamId;
                    this.frameWriter.goAway(lastGoodStreamId, statusCode, Util.EMPTY_BYTE_ARRAY);
                }
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void close(ErrorCode connectionCode, ErrorCode streamCode) throws IOException {
        if (!$assertionsDisabled && Thread.holdsLock(this)) {
            throw new AssertionError();
        }
        IOException thrown = null;
        try {
            shutdown(connectionCode);
        } catch (IOException e) {
            thrown = e;
        }
        FramedStream[] streamsToClose = null;
        Ping[] pingsToCancel = null;
        synchronized (this) {
            if (!this.streams.isEmpty()) {
                streamsToClose = (FramedStream[]) this.streams.values().toArray(new FramedStream[this.streams.size()]);
                this.streams.clear();
            }
            if (this.pings != null) {
                pingsToCancel = (Ping[]) this.pings.values().toArray(new Ping[this.pings.size()]);
                this.pings = null;
            }
        }
        if (streamsToClose != null) {
            for (FramedStream stream : streamsToClose) {
                try {
                    stream.close(streamCode);
                } catch (IOException e2) {
                    if (thrown != null) {
                        thrown = e2;
                    }
                }
            }
        }
        if (pingsToCancel != null) {
            for (Ping ping : pingsToCancel) {
                ping.cancel();
            }
        }
        try {
            this.frameWriter.close();
        } catch (IOException e3) {
            if (thrown == null) {
                thrown = e3;
            }
        }
        try {
            this.socket.close();
        } catch (IOException e4) {
            thrown = e4;
        }
        if (thrown != null) {
            throw thrown;
        }
    }

    public void start() throws IOException {
        start(true);
    }

    void start(boolean sendConnectionPreface) throws IOException {
        if (sendConnectionPreface) {
            this.frameWriter.connectionPreface();
            this.frameWriter.settings(this.okHttpSettings);
            int windowSize = this.okHttpSettings.getInitialWindowSize(65536);
            if (windowSize != 65536) {
                this.frameWriter.windowUpdate(0, windowSize - 65536);
            }
        }
        new Thread(this.readerRunnable).start();
    }

    public void setSettings(Settings settings) throws IOException {
        synchronized (this.frameWriter) {
            synchronized (this) {
                if (this.shutdown) {
                    throw new IOException("shutdown");
                }
                this.okHttpSettings.merge(settings);
                this.frameWriter.settings(settings);
            }
        }
    }

    public synchronized boolean isShutdown() {
        return this.shutdown;
    }

    public static class Builder {
        private boolean client;
        private String hostname;
        private Listener listener = Listener.REFUSE_INCOMING_STREAMS;
        private Protocol protocol = Protocol.SPDY_3;
        private PushObserver pushObserver = PushObserver.CANCEL;
        private BufferedSink sink;
        private Socket socket;
        private BufferedSource source;

        public Builder(boolean client) {
            this.client = client;
        }

        public Builder socket(Socket socket) throws IOException {
            return socket(socket, ((InetSocketAddress) socket.getRemoteSocketAddress()).getHostName(), Okio.buffer(Okio.source(socket)), Okio.buffer(Okio.sink(socket)));
        }

        public Builder socket(Socket socket, String hostname, BufferedSource source, BufferedSink sink) {
            this.socket = socket;
            this.hostname = hostname;
            this.source = source;
            this.sink = sink;
            return this;
        }

        public Builder listener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder pushObserver(PushObserver pushObserver) {
            this.pushObserver = pushObserver;
            return this;
        }

        public FramedConnection build() throws IOException {
            return new FramedConnection(this);
        }
    }

    class Reader extends NamedRunnable implements FrameReader.Handler {
        final FrameReader frameReader;

        private Reader(FrameReader frameReader) {
            super("OkHttp %s", FramedConnection.this.hostname);
            this.frameReader = frameReader;
        }

        @Override // okhttp3.internal.NamedRunnable
        protected void execute() {
            ErrorCode connectionErrorCode = ErrorCode.INTERNAL_ERROR;
            ErrorCode streamErrorCode = ErrorCode.INTERNAL_ERROR;
            try {
                if (!FramedConnection.this.client) {
                    this.frameReader.readConnectionPreface();
                }
                while (this.frameReader.nextFrame(this)) {
                }
                connectionErrorCode = ErrorCode.NO_ERROR;
                streamErrorCode = ErrorCode.CANCEL;
            } catch (IOException e) {
                connectionErrorCode = ErrorCode.PROTOCOL_ERROR;
                streamErrorCode = ErrorCode.PROTOCOL_ERROR;
            } finally {
                try {
                    FramedConnection.this.close(connectionErrorCode, streamErrorCode);
                } catch (IOException e2) {
                }
                Util.closeQuietly(this.frameReader);
            }
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void data(boolean inFinished, int streamId, BufferedSource source, int length) throws IOException {
            if (FramedConnection.this.pushedStream(streamId)) {
                FramedConnection.this.pushDataLater(streamId, source, length, inFinished);
                return;
            }
            FramedStream dataStream = FramedConnection.this.getStream(streamId);
            if (dataStream == null) {
                FramedConnection.this.writeSynResetLater(streamId, ErrorCode.INVALID_STREAM);
                source.skip(length);
            } else {
                dataStream.receiveData(source, length);
                if (inFinished) {
                    dataStream.receiveFin();
                }
            }
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void headers(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, List<Header> headerBlock, HeadersMode headersMode) {
            if (FramedConnection.this.pushedStream(streamId)) {
                FramedConnection.this.pushHeadersLater(streamId, headerBlock, inFinished);
                return;
            }
            synchronized (FramedConnection.this) {
                if (!FramedConnection.this.shutdown) {
                    FramedStream stream = FramedConnection.this.getStream(streamId);
                    if (stream == null) {
                        if (!headersMode.failIfStreamAbsent()) {
                            if (streamId > FramedConnection.this.lastGoodStreamId) {
                                if (streamId % 2 != FramedConnection.this.nextStreamId % 2) {
                                    final FramedStream newStream = new FramedStream(streamId, FramedConnection.this, outFinished, inFinished, headerBlock);
                                    FramedConnection.this.lastGoodStreamId = streamId;
                                    FramedConnection.this.streams.put(Integer.valueOf(streamId), newStream);
                                    FramedConnection.executor.execute(new NamedRunnable("OkHttp %s stream %d", new Object[]{FramedConnection.this.hostname, Integer.valueOf(streamId)}) { // from class: okhttp3.internal.framed.FramedConnection.Reader.1
                                        @Override // okhttp3.internal.NamedRunnable
                                        public void execute() {
                                            try {
                                                FramedConnection.this.listener.onStream(newStream);
                                            } catch (IOException e) {
                                                Platform.get().log(4, "FramedConnection.Listener failure for " + FramedConnection.this.hostname, e);
                                                try {
                                                    newStream.close(ErrorCode.PROTOCOL_ERROR);
                                                } catch (IOException e2) {
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        } else {
                            FramedConnection.this.writeSynResetLater(streamId, ErrorCode.INVALID_STREAM);
                        }
                    } else if (headersMode.failIfStreamPresent()) {
                        stream.closeLater(ErrorCode.PROTOCOL_ERROR);
                        FramedConnection.this.removeStream(streamId);
                    } else {
                        stream.receiveHeaders(headerBlock, headersMode);
                        if (inFinished) {
                            stream.receiveFin();
                        }
                    }
                }
            }
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void rstStream(int streamId, ErrorCode errorCode) {
            if (FramedConnection.this.pushedStream(streamId)) {
                FramedConnection.this.pushResetLater(streamId, errorCode);
                return;
            }
            FramedStream rstStream = FramedConnection.this.removeStream(streamId);
            if (rstStream != null) {
                rstStream.receiveRstStream(errorCode);
            }
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void settings(boolean clearPrevious, Settings newSettings) {
            long delta = 0;
            FramedStream[] streamsToNotify = null;
            synchronized (FramedConnection.this) {
                int priorWriteWindowSize = FramedConnection.this.peerSettings.getInitialWindowSize(65536);
                if (clearPrevious) {
                    FramedConnection.this.peerSettings.clear();
                }
                FramedConnection.this.peerSettings.merge(newSettings);
                if (FramedConnection.this.getProtocol() == Protocol.HTTP_2) {
                    applyAndAckSettings(newSettings);
                }
                int peerInitialWindowSize = FramedConnection.this.peerSettings.getInitialWindowSize(65536);
                if (peerInitialWindowSize != -1 && peerInitialWindowSize != priorWriteWindowSize) {
                    delta = peerInitialWindowSize - priorWriteWindowSize;
                    if (!FramedConnection.this.receivedInitialPeerSettings) {
                        FramedConnection.this.addBytesToWriteWindow(delta);
                        FramedConnection.this.receivedInitialPeerSettings = true;
                    }
                    if (!FramedConnection.this.streams.isEmpty()) {
                        streamsToNotify = (FramedStream[]) FramedConnection.this.streams.values().toArray(new FramedStream[FramedConnection.this.streams.size()]);
                    }
                }
                FramedConnection.executor.execute(new NamedRunnable("OkHttp %s settings", FramedConnection.this.hostname) { // from class: okhttp3.internal.framed.FramedConnection.Reader.2
                    @Override // okhttp3.internal.NamedRunnable
                    public void execute() {
                        FramedConnection.this.listener.onSettings(FramedConnection.this);
                    }
                });
            }
            if (streamsToNotify != null && delta != 0) {
                for (FramedStream stream : streamsToNotify) {
                    synchronized (stream) {
                        stream.addBytesToWriteWindow(delta);
                    }
                }
            }
        }

        private void applyAndAckSettings(final Settings peerSettings) {
            FramedConnection.executor.execute(new NamedRunnable("OkHttp %s ACK Settings", new Object[]{FramedConnection.this.hostname}) { // from class: okhttp3.internal.framed.FramedConnection.Reader.3
                @Override // okhttp3.internal.NamedRunnable
                public void execute() {
                    try {
                        FramedConnection.this.frameWriter.applyAndAckSettings(peerSettings);
                    } catch (IOException e) {
                    }
                }
            });
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void ackSettings() {
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void ping(boolean reply, int payload1, int payload2) {
            if (reply) {
                Ping ping = FramedConnection.this.removePing(payload1);
                if (ping != null) {
                    ping.receive();
                    return;
                }
                return;
            }
            FramedConnection.this.writePingLater(true, payload1, payload2, null);
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void goAway(int lastGoodStreamId, ErrorCode errorCode, ByteString debugData) {
            FramedStream[] streamsCopy;
            if (debugData.size() > 0) {
            }
            synchronized (FramedConnection.this) {
                streamsCopy = (FramedStream[]) FramedConnection.this.streams.values().toArray(new FramedStream[FramedConnection.this.streams.size()]);
                FramedConnection.this.shutdown = true;
            }
            for (FramedStream framedStream : streamsCopy) {
                if (framedStream.getId() > lastGoodStreamId && framedStream.isLocallyInitiated()) {
                    framedStream.receiveRstStream(ErrorCode.REFUSED_STREAM);
                    FramedConnection.this.removeStream(framedStream.getId());
                }
            }
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void windowUpdate(int streamId, long windowSizeIncrement) {
            if (streamId == 0) {
                synchronized (FramedConnection.this) {
                    FramedConnection.this.bytesLeftInWriteWindow += windowSizeIncrement;
                    FramedConnection.this.notifyAll();
                }
                return;
            }
            FramedStream stream = FramedConnection.this.getStream(streamId);
            if (stream != null) {
                synchronized (stream) {
                    stream.addBytesToWriteWindow(windowSizeIncrement);
                }
            }
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void priority(int streamId, int streamDependency, int weight, boolean exclusive) {
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void pushPromise(int streamId, int promisedStreamId, List<Header> requestHeaders) {
            FramedConnection.this.pushRequestLater(promisedStreamId, requestHeaders);
        }

        @Override // okhttp3.internal.framed.FrameReader.Handler
        public void alternateService(int streamId, String origin, ByteString protocol, String host, int port, long maxAge) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean pushedStream(int streamId) {
        return this.protocol == Protocol.HTTP_2 && streamId != 0 && (streamId & 1) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushRequestLater(final int streamId, final List<Header> requestHeaders) {
        synchronized (this) {
            if (this.currentPushRequests.contains(Integer.valueOf(streamId))) {
                writeSynResetLater(streamId, ErrorCode.PROTOCOL_ERROR);
            } else {
                this.currentPushRequests.add(Integer.valueOf(streamId));
                this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Request[%s]", new Object[]{this.hostname, Integer.valueOf(streamId)}) { // from class: okhttp3.internal.framed.FramedConnection.4
                    @Override // okhttp3.internal.NamedRunnable
                    public void execute() {
                        boolean cancel = FramedConnection.this.pushObserver.onRequest(streamId, requestHeaders);
                        if (cancel) {
                            try {
                                FramedConnection.this.frameWriter.rstStream(streamId, ErrorCode.CANCEL);
                                synchronized (FramedConnection.this) {
                                    FramedConnection.this.currentPushRequests.remove(Integer.valueOf(streamId));
                                }
                            } catch (IOException e) {
                            }
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushHeadersLater(final int streamId, final List<Header> requestHeaders, final boolean inFinished) {
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", new Object[]{this.hostname, Integer.valueOf(streamId)}) { // from class: okhttp3.internal.framed.FramedConnection.5
            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                boolean cancel = FramedConnection.this.pushObserver.onHeaders(streamId, requestHeaders, inFinished);
                if (cancel) {
                    try {
                        FramedConnection.this.frameWriter.rstStream(streamId, ErrorCode.CANCEL);
                    } catch (IOException e) {
                        return;
                    }
                }
                if (cancel || inFinished) {
                    synchronized (FramedConnection.this) {
                        FramedConnection.this.currentPushRequests.remove(Integer.valueOf(streamId));
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushDataLater(final int streamId, BufferedSource source, final int byteCount, final boolean inFinished) throws IOException {
        final Buffer buffer = new Buffer();
        source.require(byteCount);
        source.read(buffer, byteCount);
        if (buffer.size() != byteCount) {
            throw new IOException(buffer.size() + " != " + byteCount);
        }
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Data[%s]", new Object[]{this.hostname, Integer.valueOf(streamId)}) { // from class: okhttp3.internal.framed.FramedConnection.6
            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                try {
                    boolean cancel = FramedConnection.this.pushObserver.onData(streamId, buffer, byteCount, inFinished);
                    if (cancel) {
                        FramedConnection.this.frameWriter.rstStream(streamId, ErrorCode.CANCEL);
                    }
                    if (cancel || inFinished) {
                        synchronized (FramedConnection.this) {
                            try {
                                FramedConnection.this.currentPushRequests.remove(Integer.valueOf(streamId));
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                } catch (IOException e) {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushResetLater(final int streamId, final ErrorCode errorCode) {
        this.pushExecutor.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", new Object[]{this.hostname, Integer.valueOf(streamId)}) { // from class: okhttp3.internal.framed.FramedConnection.7
            @Override // okhttp3.internal.NamedRunnable
            public void execute() {
                FramedConnection.this.pushObserver.onReset(streamId, errorCode);
                synchronized (FramedConnection.this) {
                    FramedConnection.this.currentPushRequests.remove(Integer.valueOf(streamId));
                }
            }
        });
    }

    public static abstract class Listener {
        public static final Listener REFUSE_INCOMING_STREAMS = new Listener() { // from class: okhttp3.internal.framed.FramedConnection.Listener.1
            @Override // okhttp3.internal.framed.FramedConnection.Listener
            public void onStream(FramedStream stream) throws IOException {
                stream.close(ErrorCode.REFUSED_STREAM);
            }
        };

        public abstract void onStream(FramedStream framedStream) throws IOException;

        public void onSettings(FramedConnection connection) {
        }
    }
}
