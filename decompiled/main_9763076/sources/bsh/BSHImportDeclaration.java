package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHImportDeclaration extends SimpleNode {
    public boolean importPackage;
    public boolean staticImport;
    public boolean superImport;

    BSHImportDeclaration(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        NameSpace pVar = callStack.top();
        if (this.superImport) {
            try {
                pVar.doSuperImport();
            } catch (UtilEvalError e) {
                throw e.toEvalError(this, callStack);
            }
        } else if (!this.staticImport) {
            String str = ((BSHAmbiguousName) jjtGetChild(0)).text;
            if (this.importPackage) {
                pVar.importPackage(str);
            } else {
                pVar.importClass(str);
            }
        } else {
            if (!this.importPackage) {
                throw new EvalError("static field imports not supported yet", this, callStack);
            }
            pVar.importStatic(((BSHAmbiguousName) jjtGetChild(0)).toClass(callStack, interpreter));
        }
        return Primitive.VOID;
    }
}
