package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHClassDeclaration extends SimpleNode {
    static final String CLASSINITNAME = "_bshClassInit";
    boolean extend;
    boolean isInterface;
    Modifiers modifiers;
    String name;
    int numInterfaces;

    BSHClassDeclaration(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        int i;
        int i2 = 0;
        Class cls = null;
        if (this.extend) {
            cls = ((BSHAmbiguousName) jjtGetChild(0)).toClass(callStack, interpreter);
            i = 1;
        } else {
            i = 0;
        }
        Class[] clsArr = new Class[this.numInterfaces];
        while (i2 < this.numInterfaces) {
            int i3 = i + 1;
            BSHAmbiguousName bSHAmbiguousName = (BSHAmbiguousName) jjtGetChild(i);
            clsArr[i2] = bSHAmbiguousName.toClass(callStack, interpreter);
            if (!clsArr[i2].isInterface()) {
                throw new EvalError(new StringBuffer().append("Type: ").append(bSHAmbiguousName.text).append(" is not an interface!").toString(), this, callStack);
            }
            i2++;
            i = i3;
        }
        try {
            return ClassGenerator.getClassGenerator().generateClass(this.name, this.modifiers, clsArr, cls, i < jjtGetNumChildren() ? (BSHBlock) jjtGetChild(i) : new BSHBlock(25), this.isInterface, callStack, interpreter);
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }

    @Override // bsh.SimpleNode
    public String toString() {
        return new StringBuffer().append("ClassDeclaration: ").append(this.name).toString();
    }
}
