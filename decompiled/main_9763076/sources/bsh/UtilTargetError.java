package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UtilTargetError extends UtilEvalError {
    public Throwable t;

    public UtilTargetError(String str, Throwable th) {
        super(str);
        this.t = th;
    }

    public UtilTargetError(Throwable th) {
        this(null, th);
    }

    @Override // bsh.UtilEvalError
    public EvalError toEvalError(String str, SimpleNode simpleNode, CallStack callStack) {
        return new TargetError(str == null ? getMessage() : new StringBuffer().append(str).append(": ").append(getMessage()).toString(), this.t, simpleNode, callStack, false);
    }
}
