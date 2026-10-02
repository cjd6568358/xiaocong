package rx.exceptions;

import java.io.PrintWriter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class CompositeException$WrappedPrintWriter extends CompositeException$PrintStreamOrWriter {
    private final PrintWriter printWriter;

    CompositeException$WrappedPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    @Override // rx.exceptions.CompositeException$PrintStreamOrWriter
    Object lock() {
        return this.printWriter;
    }

    @Override // rx.exceptions.CompositeException$PrintStreamOrWriter
    void println(Object o) {
        this.printWriter.println(o);
    }
}
