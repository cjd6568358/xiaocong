package okio;

import java.io.IOException;
import java.io.InterruptedIOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AsyncTimeout extends Timeout {
    private static final int TIMEOUT_WRITE_SIZE = 65536;
    private static AsyncTimeout head;
    private boolean inQueue;
    private AsyncTimeout next;
    private long timeoutAt;

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long timeoutNanos = timeoutNanos();
        boolean hasDeadline = hasDeadline();
        if (timeoutNanos != 0 || hasDeadline) {
            this.inQueue = true;
            scheduleTimeout(this, timeoutNanos, hasDeadline);
        }
    }

    private static synchronized void scheduleTimeout(AsyncTimeout node, long timeoutNanos, boolean hasDeadline) {
        if (head == null) {
            head = new AsyncTimeout();
            new Watchdog().start();
        }
        long now = System.nanoTime();
        if (timeoutNanos != 0 && hasDeadline) {
            node.timeoutAt = Math.min(timeoutNanos, node.deadlineNanoTime() - now) + now;
        } else if (timeoutNanos != 0) {
            node.timeoutAt = now + timeoutNanos;
        } else if (hasDeadline) {
            node.timeoutAt = node.deadlineNanoTime();
        } else {
            throw new AssertionError();
        }
        long remainingNanos = node.remainingNanos(now);
        AsyncTimeout prev = head;
        while (prev.next != null && remainingNanos >= prev.next.remainingNanos(now)) {
            prev = prev.next;
        }
        node.next = prev.next;
        prev.next = node;
        if (prev == head) {
            AsyncTimeout.class.notify();
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return cancelScheduledTimeout(this);
    }

    private static synchronized boolean cancelScheduledTimeout(AsyncTimeout node) {
        boolean z;
        for (AsyncTimeout prev = head; prev != null; prev = prev.next) {
            if (prev.next == node) {
                prev.next = node.next;
                node.next = null;
                z = false;
            }
        }
        z = true;
        return z;
    }

    private long remainingNanos(long now) {
        return this.timeoutAt - now;
    }

    protected void timedOut() {
    }

    public final Sink sink(final Sink sink) {
        return new Sink() { // from class: okio.AsyncTimeout.1
            @Override // okio.Sink
            public void write(Buffer source, long byteCount) throws IOException {
                Util.checkOffsetAndCount(source.size, 0L, byteCount);
                while (byteCount > 0) {
                    long toWrite = 0;
                    Segment s = source.head;
                    while (toWrite < 65536) {
                        int segmentSize = source.head.limit - source.head.pos;
                        toWrite += (long) segmentSize;
                        if (toWrite < byteCount) {
                            s = s.next;
                        } else {
                            toWrite = byteCount;
                            break;
                        }
                    }
                    AsyncTimeout.this.enter();
                    try {
                        try {
                            sink.write(source, toWrite);
                            byteCount -= toWrite;
                            AsyncTimeout.this.exit(true);
                        } catch (IOException e) {
                            throw AsyncTimeout.this.exit(e);
                        }
                    } catch (Throwable th) {
                        AsyncTimeout.this.exit(false);
                        throw th;
                    }
                }
            }

            @Override // okio.Sink, java.io.Flushable
            public void flush() throws IOException {
                AsyncTimeout.this.enter();
                try {
                    try {
                        sink.flush();
                        AsyncTimeout.this.exit(true);
                    } catch (IOException e) {
                        throw AsyncTimeout.this.exit(e);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.exit(false);
                    throw th;
                }
            }

            @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                AsyncTimeout.this.enter();
                try {
                    try {
                        sink.close();
                        AsyncTimeout.this.exit(true);
                    } catch (IOException e) {
                        throw AsyncTimeout.this.exit(e);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.exit(false);
                    throw th;
                }
            }

            @Override // okio.Sink
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                return "AsyncTimeout.sink(" + sink + ")";
            }
        };
    }

    public final Source source(final Source source) {
        return new Source() { // from class: okio.AsyncTimeout.2
            @Override // okio.Source
            public long read(Buffer sink, long byteCount) throws IOException {
                AsyncTimeout.this.enter();
                try {
                    try {
                        long result = source.read(sink, byteCount);
                        AsyncTimeout.this.exit(true);
                        return result;
                    } catch (IOException e) {
                        throw AsyncTimeout.this.exit(e);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.exit(false);
                    throw th;
                }
            }

            @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                try {
                    try {
                        source.close();
                        AsyncTimeout.this.exit(true);
                    } catch (IOException e) {
                        throw AsyncTimeout.this.exit(e);
                    }
                } catch (Throwable th) {
                    AsyncTimeout.this.exit(false);
                    throw th;
                }
            }

            @Override // okio.Source
            public Timeout timeout() {
                return AsyncTimeout.this;
            }

            public String toString() {
                return "AsyncTimeout.source(" + source + ")";
            }
        };
    }

    final void exit(boolean throwOnTimeout) throws IOException {
        boolean timedOut = exit();
        if (timedOut && throwOnTimeout) {
            throw newTimeoutException(null);
        }
    }

    final IOException exit(IOException cause) throws IOException {
        return !exit() ? cause : newTimeoutException(cause);
    }

    protected IOException newTimeoutException(IOException cause) {
        InterruptedIOException e = new InterruptedIOException("timeout");
        if (cause != null) {
            e.initCause(cause);
        }
        return e;
    }

    private static final class Watchdog extends Thread {
        public Watchdog() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX INFO: Infinite loop detected, blocks: 11, insns: 0 */
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    AsyncTimeout timedOut = AsyncTimeout.awaitTimeout();
                    if (timedOut != null) {
                        timedOut.timedOut();
                    }
                } catch (InterruptedException e) {
                }
            }
        }
    }

    static synchronized AsyncTimeout awaitTimeout() throws InterruptedException {
        AsyncTimeout node;
        node = head.next;
        if (node == null) {
            AsyncTimeout.class.wait();
            node = null;
        } else {
            long waitNanos = node.remainingNanos(System.nanoTime());
            if (waitNanos > 0) {
                long waitMillis = waitNanos / 1000000;
                AsyncTimeout.class.wait(waitMillis, (int) (waitNanos - (waitMillis * 1000000)));
                node = null;
            } else {
                head.next = node.next;
                node.next = null;
            }
        }
        return node;
    }
}
