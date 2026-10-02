package okhttp3.internal.connection;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;
import okhttp3.Address;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Route;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.framed.ErrorCode;
import okhttp3.internal.framed.StreamResetException;
import okhttp3.internal.http.Http1xStream;
import okhttp3.internal.http.Http2xStream;
import okhttp3.internal.http.HttpStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class StreamAllocation {
    public final Address address;
    private boolean canceled;
    private RealConnection connection;
    private final ConnectionPool connectionPool;
    private int refusedStreamCount;
    private boolean released;
    private Route route;
    private final RouteSelector routeSelector;
    private HttpStream stream;

    public StreamAllocation(ConnectionPool connectionPool, Address address) {
        this.connectionPool = connectionPool;
        this.address = address;
        this.routeSelector = new RouteSelector(address, routeDatabase());
    }

    public HttpStream newStream(OkHttpClient client, boolean doExtensiveHealthChecks) {
        HttpStream resultStream;
        int connectTimeout = client.connectTimeoutMillis();
        int readTimeout = client.readTimeoutMillis();
        int writeTimeout = client.writeTimeoutMillis();
        boolean connectionRetryEnabled = client.retryOnConnectionFailure();
        try {
            RealConnection resultConnection = findHealthyConnection(connectTimeout, readTimeout, writeTimeout, connectionRetryEnabled, doExtensiveHealthChecks);
            if (resultConnection.framedConnection != null) {
                resultStream = new Http2xStream(client, this, resultConnection.framedConnection);
            } else {
                resultConnection.socket().setSoTimeout(readTimeout);
                resultConnection.source.timeout().timeout(readTimeout, TimeUnit.MILLISECONDS);
                resultConnection.sink.timeout().timeout(writeTimeout, TimeUnit.MILLISECONDS);
                resultStream = new Http1xStream(client, this, resultConnection.source, resultConnection.sink);
            }
            synchronized (this.connectionPool) {
                this.stream = resultStream;
            }
            return resultStream;
        } catch (IOException e) {
            throw new RouteException(e);
        }
    }

    private RealConnection findHealthyConnection(int connectTimeout, int readTimeout, int writeTimeout, boolean connectionRetryEnabled, boolean doExtensiveHealthChecks) throws IOException {
        RealConnection candidate;
        while (true) {
            candidate = findConnection(connectTimeout, readTimeout, writeTimeout, connectionRetryEnabled);
            synchronized (this.connectionPool) {
                if (candidate.successCount != 0) {
                    if (candidate.isHealthy(doExtensiveHealthChecks)) {
                        break;
                    }
                    noNewStreams();
                } else {
                    break;
                }
            }
        }
        return candidate;
    }

    private RealConnection findConnection(int connectTimeout, int readTimeout, int writeTimeout, boolean connectionRetryEnabled) throws IOException {
        synchronized (this.connectionPool) {
            if (this.released) {
                throw new IllegalStateException("released");
            }
            if (this.stream != null) {
                throw new IllegalStateException("stream != null");
            }
            if (this.canceled) {
                throw new IOException("Canceled");
            }
            RealConnection allocatedConnection = this.connection;
            if (allocatedConnection == null || allocatedConnection.noNewStreams) {
                RealConnection pooledConnection = Internal.instance.get(this.connectionPool, this.address, this);
                if (pooledConnection != null) {
                    this.connection = pooledConnection;
                    return pooledConnection;
                }
                Route selectedRoute = this.route;
                if (selectedRoute == null) {
                    selectedRoute = this.routeSelector.next();
                    synchronized (this.connectionPool) {
                        this.route = selectedRoute;
                        this.refusedStreamCount = 0;
                    }
                }
                RealConnection newConnection = new RealConnection(selectedRoute);
                acquire(newConnection);
                synchronized (this.connectionPool) {
                    Internal.instance.put(this.connectionPool, newConnection);
                    this.connection = newConnection;
                    if (this.canceled) {
                        throw new IOException("Canceled");
                    }
                }
                newConnection.connect(connectTimeout, readTimeout, writeTimeout, this.address.connectionSpecs(), connectionRetryEnabled);
                routeDatabase().connected(newConnection.route());
                return newConnection;
            }
            return allocatedConnection;
        }
    }

    public void streamFinished(boolean noNewStreams, HttpStream stream) {
        synchronized (this.connectionPool) {
            if (stream != null) {
                if (stream == this.stream) {
                    if (!noNewStreams) {
                        this.connection.successCount++;
                    }
                }
            }
            throw new IllegalStateException("expected " + this.stream + " but was " + stream);
        }
        deallocate(noNewStreams, false, true);
    }

    public HttpStream stream() {
        HttpStream httpStream;
        synchronized (this.connectionPool) {
            httpStream = this.stream;
        }
        return httpStream;
    }

    private RouteDatabase routeDatabase() {
        return Internal.instance.routeDatabase(this.connectionPool);
    }

    public synchronized RealConnection connection() {
        return this.connection;
    }

    public void release() {
        deallocate(false, true, false);
    }

    public void noNewStreams() {
        deallocate(true, false, false);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0012 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:12:0x0014 A[Catch: all -> 0x005a, TryCatch #0 {, blocks: (B:6:0x0007, B:8:0x000b, B:9:0x000e, B:12:0x0014, B:13:0x0019, B:15:0x001d, B:17:0x0021, B:19:0x0027, B:21:0x0036, B:23:0x004a, B:24:0x004c, B:25:0x004f), top: B:32:0x0007 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0036 A[Catch: all -> 0x005a, TryCatch #0 {, blocks: (B:6:0x0007, B:8:0x000b, B:9:0x000e, B:12:0x0014, B:13:0x0019, B:15:0x001d, B:17:0x0021, B:19:0x0027, B:21:0x0036, B:23:0x004a, B:24:0x004c, B:25:0x004f), top: B:32:0x0007 }] */
    /* JADX WARN: Code duplicated, block: B:23:0x004a A[Catch: all -> 0x005a, TryCatch #0 {, blocks: (B:6:0x0007, B:8:0x000b, B:9:0x000e, B:12:0x0014, B:13:0x0019, B:15:0x001d, B:17:0x0021, B:19:0x0027, B:21:0x0036, B:23:0x004a, B:24:0x004c, B:25:0x004f), top: B:32:0x0007 }] */
    /* JADX WARN: Code duplicated, block: B:8:0x000b A[Catch: all -> 0x005a, TryCatch #0 {, blocks: (B:6:0x0007, B:8:0x000b, B:9:0x000e, B:12:0x0014, B:13:0x0019, B:15:0x001d, B:17:0x0021, B:19:0x0027, B:21:0x0036, B:23:0x004a, B:24:0x004c, B:25:0x004f), top: B:32:0x0007 }] */
    private void deallocate(boolean noNewStreams, boolean released, boolean streamFinished) {
        RealConnection connectionToClose = null;
        synchronized (this.connectionPool) {
            if (streamFinished) {
                this.stream = null;
                if (released) {
                    this.released = true;
                }
                if (this.connection != null) {
                    if (noNewStreams) {
                        this.connection.noNewStreams = true;
                    }
                    if (this.stream == null && (this.released || this.connection.noNewStreams)) {
                        release(this.connection);
                        if (this.connection.allocations.isEmpty()) {
                            this.connection.idleAtNanos = System.nanoTime();
                            if (Internal.instance.connectionBecameIdle(this.connectionPool, this.connection)) {
                                connectionToClose = this.connection;
                            }
                        }
                        this.connection = null;
                    }
                }
            } else {
                if (released) {
                    this.released = true;
                }
                if (this.connection != null) {
                    if (noNewStreams) {
                        this.connection.noNewStreams = true;
                    }
                    if (this.stream == null) {
                        release(this.connection);
                        if (this.connection.allocations.isEmpty()) {
                            this.connection.idleAtNanos = System.nanoTime();
                            if (Internal.instance.connectionBecameIdle(this.connectionPool, this.connection)) {
                                connectionToClose = this.connection;
                            }
                        }
                        this.connection = null;
                    }
                }
            }
            throw th;
        }
        if (connectionToClose != null) {
            Util.closeQuietly(connectionToClose.socket());
        }
    }

    public void cancel() {
        HttpStream streamToCancel;
        RealConnection connectionToCancel;
        synchronized (this.connectionPool) {
            this.canceled = true;
            streamToCancel = this.stream;
            connectionToCancel = this.connection;
        }
        if (streamToCancel != null) {
            streamToCancel.cancel();
        } else if (connectionToCancel != null) {
            connectionToCancel.cancel();
        }
    }

    public void streamFailed(IOException e) {
        boolean noNewStreams = false;
        synchronized (this.connectionPool) {
            if (e instanceof StreamResetException) {
                StreamResetException streamResetException = (StreamResetException) e;
                if (streamResetException.errorCode == ErrorCode.REFUSED_STREAM) {
                    this.refusedStreamCount++;
                }
                if (streamResetException.errorCode != ErrorCode.REFUSED_STREAM || this.refusedStreamCount > 1) {
                    noNewStreams = true;
                    this.route = null;
                }
            } else if (this.connection != null && !this.connection.isMultiplexed()) {
                noNewStreams = true;
                if (this.connection.successCount == 0) {
                    if (this.route != null && e != null) {
                        this.routeSelector.connectFailed(this.route, e);
                    }
                    this.route = null;
                }
            }
        }
        deallocate(noNewStreams, false, true);
    }

    public void acquire(RealConnection connection) {
        connection.allocations.add(new WeakReference(this));
    }

    private void release(RealConnection connection) {
        int size = connection.allocations.size();
        for (int i = 0; i < size; i++) {
            Reference<StreamAllocation> reference = connection.allocations.get(i);
            if (reference.get() == this) {
                connection.allocations.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public boolean hasMoreRoutes() {
        return this.route != null || this.routeSelector.hasNext();
    }

    public String toString() {
        return this.address.toString();
    }
}
