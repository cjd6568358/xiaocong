package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHReturnType extends SimpleNode {
    public boolean isVoid;

    BSHReturnType(int i) {
        super(i);
    }

    public Class evalReturnType(CallStack callStack, Interpreter interpreter) throws EvalError {
        return this.isVoid ? Void.TYPE : getTypeNode().getType(callStack, interpreter);
    }

    public String getTypeDescriptor(CallStack callStack, Interpreter interpreter, String str) {
        return this.isVoid ? "V" : getTypeNode().getTypeDescriptor(callStack, interpreter, str);
    }

    BSHType getTypeNode() {
        return (BSHType) jjtGetChild(0);
    }
}
