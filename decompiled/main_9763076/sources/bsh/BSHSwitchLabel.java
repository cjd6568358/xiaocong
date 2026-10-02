package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHSwitchLabel extends SimpleNode {
    boolean isDefault;

    public BSHSwitchLabel(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        if (this.isDefault) {
            return null;
        }
        return ((SimpleNode) jjtGetChild(0)).eval(callStack, interpreter);
    }
}
