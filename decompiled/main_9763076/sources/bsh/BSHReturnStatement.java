package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHReturnStatement extends SimpleNode implements ParserConstants {
    public int kind;

    BSHReturnStatement(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        return new ReturnControl(this.kind, jjtGetNumChildren() > 0 ? ((SimpleNode) jjtGetChild(0)).eval(callStack, interpreter) : Primitive.VOID, this);
    }
}
