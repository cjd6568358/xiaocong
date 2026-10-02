package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHTypedVariableDeclaration extends SimpleNode {
    public Modifiers modifiers;

    BSHTypedVariableDeclaration(int i) {
        super(i);
    }

    private BSHType getTypeNode() {
        return (BSHType) jjtGetChild(0);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        try {
            NameSpace pVar = callStack.top();
            BSHType typeNode = getTypeNode();
            Class type = typeNode.getType(callStack, interpreter);
            for (BSHVariableDeclarator bSHVariableDeclarator : getDeclarators()) {
                try {
                    pVar.setTypedVariable(bSHVariableDeclarator.name, type, bSHVariableDeclarator.eval(typeNode, callStack, interpreter), this.modifiers);
                } catch (UtilEvalError e) {
                    throw e.toEvalError(this, callStack);
                }
            }
        } catch (EvalError e2) {
            e2.reThrow("Typed variable declaration");
        }
        return Primitive.VOID;
    }

    Class evalType(CallStack callStack, Interpreter interpreter) throws EvalError {
        return getTypeNode().getType(callStack, interpreter);
    }

    BSHVariableDeclarator[] getDeclarators() {
        int iJjtGetNumChildren = jjtGetNumChildren();
        BSHVariableDeclarator[] bSHVariableDeclaratorArr = new BSHVariableDeclarator[iJjtGetNumChildren - 1];
        for (int i = 1; i < iJjtGetNumChildren; i++) {
            bSHVariableDeclaratorArr[i - 1] = (BSHVariableDeclarator) jjtGetChild(i);
        }
        return bSHVariableDeclaratorArr;
    }

    public String getTypeDescriptor(CallStack callStack, Interpreter interpreter, String str) {
        return getTypeNode().getTypeDescriptor(callStack, interpreter, str);
    }
}
