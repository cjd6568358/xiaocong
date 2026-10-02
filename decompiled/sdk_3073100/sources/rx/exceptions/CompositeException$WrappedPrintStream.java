package rx.exceptions;

import java.io.PrintStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class CompositeException$WrappedPrintStream extends CompositeException$PrintStreamOrWriter {
    private final PrintStream printStream;

    CompositeException$WrappedPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override // rx.exceptions.CompositeException$PrintStreamOrWriter
    Object lock() {
        return this.printStream;
    }

    @Override // rx.exceptions.CompositeException$PrintStreamOrWriter
    void println(Object o) {
        this.printStream.println(o);
    }
}
