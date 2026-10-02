package bsh;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UtilEvalError extends Exception {
    protected UtilEvalError() {
    }

    public UtilEvalError(String str) {
        super(str);
    }

    public EvalError toEvalError(SimpleNode simpleNode, CallStack callStack) {
        return toEvalError(null, simpleNode, callStack);
    }

    public EvalError toEvalError(String str, SimpleNode simpleNode, CallStack callStack) {
        if (Interpreter.DEBUG) {
            printStackTrace();
        }
        return new EvalError(new StringBuffer().append(str == null ? Constants.MAIN_VERSION_TAG : new StringBuffer().append(str).append(": ").toString()).append(getMessage()).toString(), simpleNode, callStack);
    }
}
