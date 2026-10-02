package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHArguments extends SimpleNode {
    BSHArguments(int i) {
        super(i);
    }

    public Object[] getArguments(CallStack callStack, Interpreter interpreter) throws EvalError {
        Object[] objArr = new Object[jjtGetNumChildren()];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= objArr.length) {
                return objArr;
            }
            objArr[i2] = ((SimpleNode) jjtGetChild(i2)).eval(callStack, interpreter);
            if (objArr[i2] == Primitive.VOID) {
                throw new EvalError(new StringBuffer().append("Undefined argument: ").append(((SimpleNode) jjtGetChild(i2)).getText()).toString(), this, callStack);
            }
            i = i2 + 1;
        }
    }
}
