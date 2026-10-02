package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHVariableDeclarator extends SimpleNode {
    public String name;

    BSHVariableDeclarator(int i) {
        super(i);
    }

    public Object eval(BSHType bSHType, CallStack callStack, Interpreter interpreter) throws EvalError {
        Object objEval = null;
        if (jjtGetNumChildren() > 0) {
            SimpleNode simpleNode = (SimpleNode) jjtGetChild(0);
            objEval = (bSHType == null || !(simpleNode instanceof BSHArrayInitializer)) ? simpleNode.eval(callStack, interpreter) : ((BSHArrayInitializer) simpleNode).eval(bSHType.getBaseType(), bSHType.getArrayDims(), callStack, interpreter);
        }
        if (objEval == Primitive.VOID) {
            throw new EvalError("Void initializer.", this, callStack);
        }
        return objEval;
    }

    @Override // bsh.SimpleNode
    public String toString() {
        return new StringBuffer().append("BSHVariableDeclarator ").append(this.name).toString();
    }
}
