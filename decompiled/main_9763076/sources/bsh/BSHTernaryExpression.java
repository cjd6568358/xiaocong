package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHTernaryExpression extends SimpleNode {
    BSHTernaryExpression(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        return BSHIfStatement.evaluateCondition((SimpleNode) jjtGetChild(0), callStack, interpreter) ? ((SimpleNode) jjtGetChild(1)).eval(callStack, interpreter) : ((SimpleNode) jjtGetChild(2)).eval(callStack, interpreter);
    }
}
