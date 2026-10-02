package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHThrowStatement extends SimpleNode {
    BSHThrowStatement(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        Object objEval = ((SimpleNode) jjtGetChild(0)).eval(callStack, interpreter);
        if (objEval instanceof Exception) {
            throw new TargetError((Exception) objEval, this, callStack);
        }
        throw new EvalError("Expression in 'throw' must be Exception type", this, callStack);
    }
}
