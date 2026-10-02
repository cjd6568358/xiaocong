package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHPrimaryExpression extends SimpleNode {
    BSHPrimaryExpression(int i) {
        super(i);
    }

    private Object eval(boolean z, CallStack callStack, Interpreter interpreter) throws EvalError {
        Object objEval;
        Object objJjtGetChild = jjtGetChild(0);
        int iJjtGetNumChildren = jjtGetNumChildren();
        for (int i = 1; i < iJjtGetNumChildren; i++) {
            objJjtGetChild = ((BSHPrimarySuffix) jjtGetChild(i)).doSuffix(objJjtGetChild, z, callStack, interpreter);
        }
        if (!(objJjtGetChild instanceof SimpleNode)) {
            objEval = objJjtGetChild;
        } else if (objJjtGetChild instanceof BSHAmbiguousName) {
            objEval = z ? ((BSHAmbiguousName) objJjtGetChild).toLHS(callStack, interpreter) : ((BSHAmbiguousName) objJjtGetChild).toObject(callStack, interpreter);
        } else {
            if (z) {
                throw new EvalError("Can't assign to prefix.", this, callStack);
            }
            objEval = ((SimpleNode) objJjtGetChild).eval(callStack, interpreter);
        }
        if (!(objEval instanceof LHS) || z) {
            return objEval;
        }
        try {
            return ((LHS) objEval).getValue();
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        return eval(false, callStack, interpreter);
    }

    public LHS toLHS(CallStack callStack, Interpreter interpreter) throws EvalError {
        Object objEval = eval(true, callStack, interpreter);
        if (objEval instanceof LHS) {
            return (LHS) objEval;
        }
        throw new EvalError("Can't assign to:", this, callStack);
    }
}
