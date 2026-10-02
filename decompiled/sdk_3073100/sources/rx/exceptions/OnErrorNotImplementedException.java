package rx.exceptions;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class OnErrorNotImplementedException extends RuntimeException {
    private static final long serialVersionUID = -6298857009889503852L;

    public OnErrorNotImplementedException(String message, Throwable e) {
        super(message, e == null ? new NullPointerException() : e);
    }

    public OnErrorNotImplementedException(Throwable e) {
        super(e != null ? e.getMessage() : null, e == null ? new NullPointerException() : e);
    }
}
