package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHForStatement extends SimpleNode implements ParserConstants {
    private SimpleNode expression;
    private SimpleNode forInit;
    private SimpleNode forUpdate;
    public boolean hasExpression;
    public boolean hasForInit;
    public boolean hasForUpdate;
    private boolean parsed;
    private SimpleNode statement;

    BSHForStatement(int i) {
        super(i);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0079  */
    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        int i;
        Object obj;
        boolean z;
        if (this.hasForInit) {
            this.forInit = (SimpleNode) jjtGetChild(0);
            i = 1;
        } else {
            i = 0;
        }
        if (this.hasExpression) {
            this.expression = (SimpleNode) jjtGetChild(i);
            i++;
        }
        if (this.hasForUpdate) {
            this.forUpdate = (SimpleNode) jjtGetChild(i);
            i++;
        }
        if (i < jjtGetNumChildren()) {
            this.statement = (SimpleNode) jjtGetChild(i);
        }
        NameSpace pVar = callStack.top();
        callStack.swap(new BlockNameSpace(pVar));
        if (this.hasForInit) {
            this.forInit.eval(callStack, interpreter);
        }
        Object obj2 = Primitive.VOID;
        while (true) {
            obj = obj2;
            if (!this.hasExpression || BSHIfStatement.evaluateCondition(this.expression, callStack, interpreter)) {
                if (this.statement != null) {
                    Object objEval = this.statement.eval(callStack, interpreter);
                    if (objEval instanceof ReturnControl) {
                        switch (((ReturnControl) objEval).kind) {
                            case 12:
                                z = true;
                                obj2 = obj;
                                break;
                            case 19:
                                z = false;
                                obj2 = obj;
                                break;
                            case 46:
                                obj2 = objEval;
                                z = true;
                                break;
                            default:
                                z = false;
                                obj2 = obj;
                                break;
                        }
                    } else {
                        z = false;
                        obj2 = obj;
                    }
                } else {
                    z = false;
                    obj2 = obj;
                }
                if (z) {
                    obj = obj2;
                } else if (this.hasForUpdate) {
                    this.forUpdate.eval(callStack, interpreter);
                }
            }
        }
        callStack.swap(pVar);
        return obj;
    }
}
