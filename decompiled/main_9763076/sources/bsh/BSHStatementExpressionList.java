package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHStatementExpressionList extends SimpleNode {
    BSHStatementExpressionList(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        int iJjtGetNumChildren = jjtGetNumChildren();
        for (int i = 0; i < iJjtGetNumChildren; i++) {
            ((SimpleNode) jjtGetChild(i)).eval(callStack, interpreter);
        }
        return Primitive.VOID;
    }
}
