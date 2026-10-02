package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BSHPackageDeclaration extends SimpleNode {
    public BSHPackageDeclaration(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        BSHAmbiguousName bSHAmbiguousName = (BSHAmbiguousName) jjtGetChild(0);
        NameSpace pVar = callStack.top();
        pVar.setPackage(bSHAmbiguousName.text);
        pVar.importPackage(bSHAmbiguousName.text);
        return Primitive.VOID;
    }
}
