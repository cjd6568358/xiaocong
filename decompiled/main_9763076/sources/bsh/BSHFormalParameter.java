package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHFormalParameter extends SimpleNode {
    public static final Class UNTYPED = null;
    public String name;
    public Class type;

    BSHFormalParameter(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        if (jjtGetNumChildren() > 0) {
            this.type = ((BSHType) jjtGetChild(0)).getType(callStack, interpreter);
        } else {
            this.type = UNTYPED;
        }
        return this.type;
    }

    public String getTypeDescriptor(CallStack callStack, Interpreter interpreter, String str) {
        return jjtGetNumChildren() > 0 ? ((BSHType) jjtGetChild(0)).getTypeDescriptor(callStack, interpreter, str) : "Ljava/lang/Object;";
    }
}
