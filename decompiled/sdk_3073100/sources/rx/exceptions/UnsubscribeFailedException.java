package rx.exceptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class UnsubscribeFailedException extends RuntimeException {
    private static final long serialVersionUID = 4594672310593167598L;

    public UnsubscribeFailedException(Throwable throwable) {
        super(throwable == null ? new NullPointerException() : throwable);
    }

    public UnsubscribeFailedException(String message, Throwable throwable) {
        super(message, throwable == null ? new NullPointerException() : throwable);
    }
}
