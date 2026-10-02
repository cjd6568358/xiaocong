package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHMethodDeclaration extends SimpleNode {
    BSHBlock blockNode;
    int firstThrowsClause;
    public Modifiers modifiers;
    public String name;
    int numThrows;
    BSHFormalParameters paramsNode;
    Class returnType;
    BSHReturnType returnTypeNode;

    BSHMethodDeclaration(int i) {
        super(i);
        this.numThrows = 0;
    }

    private void evalNodes(CallStack callStack, Interpreter interpreter) throws EvalError {
        insureNodesParsed();
        int i = this.firstThrowsClause;
        while (true) {
            int i2 = i;
            if (i2 >= this.numThrows + this.firstThrowsClause) {
                break;
            }
            ((BSHAmbiguousName) jjtGetChild(i2)).toClass(callStack, interpreter);
            i = i2 + 1;
        }
        this.paramsNode.eval(callStack, interpreter);
        if (interpreter.getStrictJava()) {
            for (int i3 = 0; i3 < this.paramsNode.paramTypes.length; i3++) {
                if (this.paramsNode.paramTypes[i3] == null) {
                    throw new EvalError(new StringBuffer().append("(Strict Java Mode) Undeclared argument type, parameter: ").append(this.paramsNode.getParamNames()[i3]).append(" in method: ").append(this.name).toString(), this, null);
                }
            }
            if (this.returnType == null) {
                throw new EvalError(new StringBuffer().append("(Strict Java Mode) Undeclared return type for method: ").append(this.name).toString(), this, null);
            }
        }
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        this.returnType = evalReturnType(callStack, interpreter);
        evalNodes(callStack, interpreter);
        NameSpace pVar = callStack.top();
        try {
            pVar.setMethod(this.name, new BshMethod(this, pVar, this.modifiers));
            return Primitive.VOID;
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }

    Class evalReturnType(CallStack callStack, Interpreter interpreter) throws EvalError {
        insureNodesParsed();
        if (this.returnTypeNode != null) {
            return this.returnTypeNode.evalReturnType(callStack, interpreter);
        }
        return null;
    }

    String getReturnTypeDescriptor(CallStack callStack, Interpreter interpreter, String str) {
        insureNodesParsed();
        if (this.returnTypeNode == null) {
            return null;
        }
        return this.returnTypeNode.getTypeDescriptor(callStack, interpreter, str);
    }

    BSHReturnType getReturnTypeNode() {
        insureNodesParsed();
        return this.returnTypeNode;
    }

    synchronized void insureNodesParsed() {
        if (this.paramsNode == null) {
            Node nodeJjtGetChild = jjtGetChild(0);
            this.firstThrowsClause = 1;
            if (nodeJjtGetChild instanceof BSHReturnType) {
                this.returnTypeNode = (BSHReturnType) nodeJjtGetChild;
                this.paramsNode = (BSHFormalParameters) jjtGetChild(1);
                if (jjtGetNumChildren() > this.numThrows + 2) {
                    this.blockNode = (BSHBlock) jjtGetChild(this.numThrows + 2);
                }
                this.firstThrowsClause++;
            } else {
                this.paramsNode = (BSHFormalParameters) jjtGetChild(0);
                this.blockNode = (BSHBlock) jjtGetChild(this.numThrows + 1);
            }
        }
    }

    @Override // bsh.SimpleNode
    public String toString() {
        return new StringBuffer().append("MethodDeclaration: ").append(this.name).toString();
    }
}
