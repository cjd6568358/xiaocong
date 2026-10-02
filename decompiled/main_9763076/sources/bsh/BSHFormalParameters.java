package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHFormalParameters extends SimpleNode {
    int numArgs;
    private String[] paramNames;
    Class[] paramTypes;
    String[] typeDescriptors;

    BSHFormalParameters(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        if (this.paramTypes != null) {
            return this.paramTypes;
        }
        insureParsed();
        Class[] clsArr = new Class[this.numArgs];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.numArgs) {
                this.paramTypes = clsArr;
                return clsArr;
            }
            clsArr[i2] = (Class) ((BSHFormalParameter) jjtGetChild(i2)).eval(callStack, interpreter);
            i = i2 + 1;
        }
    }

    public String[] getParamNames() {
        insureParsed();
        return this.paramNames;
    }

    public String[] getTypeDescriptors(CallStack callStack, Interpreter interpreter, String str) {
        if (this.typeDescriptors != null) {
            return this.typeDescriptors;
        }
        insureParsed();
        String[] strArr = new String[this.numArgs];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.numArgs) {
                this.typeDescriptors = strArr;
                return strArr;
            }
            strArr[i2] = ((BSHFormalParameter) jjtGetChild(i2)).getTypeDescriptor(callStack, interpreter, str);
            i = i2 + 1;
        }
    }

    void insureParsed() {
        if (this.paramNames != null) {
            return;
        }
        this.numArgs = jjtGetNumChildren();
        String[] strArr = new String[this.numArgs];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.numArgs) {
                this.paramNames = strArr;
                return;
            } else {
                strArr[i2] = ((BSHFormalParameter) jjtGetChild(i2)).name;
                i = i2 + 1;
            }
        }
    }
}
