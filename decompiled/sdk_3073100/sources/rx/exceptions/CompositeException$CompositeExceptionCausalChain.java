package rx.exceptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class CompositeException$CompositeExceptionCausalChain extends RuntimeException {
    static final String MESSAGE = "Chain of Causes for CompositeException In Order Received =>";
    private static final long serialVersionUID = 3875212506787802066L;

    CompositeException$CompositeExceptionCausalChain() {
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return MESSAGE;
    }
}
