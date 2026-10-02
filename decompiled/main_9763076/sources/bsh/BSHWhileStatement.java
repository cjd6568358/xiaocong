package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHWhileStatement extends SimpleNode implements ParserConstants {
    public boolean isDoStatement;

    BSHWhileStatement(int i) {
        super(i);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004d  */
    /* JADX WARN: Code duplicated, block: B:30:0x0024 A[SYNTHETIC] */
    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        SimpleNode simpleNode;
        SimpleNode simpleNode2;
        boolean z;
        int iJjtGetNumChildren = jjtGetNumChildren();
        if (this.isDoStatement) {
            SimpleNode simpleNode3 = (SimpleNode) jjtGetChild(1);
            simpleNode = (SimpleNode) jjtGetChild(0);
            simpleNode2 = simpleNode3;
        } else {
            SimpleNode simpleNode4 = (SimpleNode) jjtGetChild(0);
            if (iJjtGetNumChildren > 1) {
                simpleNode = (SimpleNode) jjtGetChild(1);
                simpleNode2 = simpleNode4;
            } else {
                simpleNode = null;
                simpleNode2 = simpleNode4;
            }
        }
        boolean z2 = this.isDoStatement;
        while (true) {
            if (z2 || BSHIfStatement.evaluateCondition(simpleNode2, callStack, interpreter)) {
                if (simpleNode != null) {
                    Object objEval = simpleNode.eval(callStack, interpreter);
                    if (objEval instanceof ReturnControl) {
                        switch (((ReturnControl) objEval).kind) {
                            case 12:
                                z = true;
                                if (!z) {
                                    z2 = false;
                                }
                                break;
                            case 19:
                                break;
                            case 46:
                                return objEval;
                            default:
                                break;
                        }
                    }
                    z = false;
                    if (!z) {
                        z2 = false;
                    }
                }
            }
        }
        return Primitive.VOID;
    }
}
