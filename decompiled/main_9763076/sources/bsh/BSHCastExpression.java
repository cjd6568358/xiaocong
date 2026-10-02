package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHCastExpression extends SimpleNode {
    public BSHCastExpression(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        callStack.top();
        Class type = ((BSHType) jjtGetChild(0)).getType(callStack, interpreter);
        Object objEval = ((SimpleNode) jjtGetChild(1)).eval(callStack, interpreter);
        objEval.getClass();
        try {
            return Types.castObject(objEval, type, 0);
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }
}
