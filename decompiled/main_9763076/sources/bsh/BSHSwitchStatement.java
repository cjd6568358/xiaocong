package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHSwitchStatement extends SimpleNode implements ParserConstants {
    public BSHSwitchStatement(int i) {
        super(i);
    }

    private boolean primitiveEquals(Object obj, Object obj2, CallStack callStack, SimpleNode simpleNode) throws EvalError {
        if (!(obj instanceof Primitive) && !(obj2 instanceof Primitive)) {
            return obj.equals(obj2);
        }
        try {
            return Primitive.unwrap(Primitive.binaryOperation(obj, obj2, 90)).equals(Boolean.TRUE);
        } catch (UtilEvalError e) {
            throw e.toEvalError(new StringBuffer().append("Switch value: ").append(simpleNode.getText()).append(": ").toString(), this, callStack);
        }
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        int iJjtGetNumChildren = jjtGetNumChildren();
        SimpleNode simpleNode = (SimpleNode) jjtGetChild(0);
        Object objEval = simpleNode.eval(callStack, interpreter);
        ReturnControl returnControl = null;
        if (1 >= iJjtGetNumChildren) {
            throw new EvalError("Empty switch statement.", this, callStack);
        }
        BSHSwitchLabel bSHSwitchLabel = (BSHSwitchLabel) jjtGetChild(1);
        int i = 2;
        while (i < iJjtGetNumChildren && returnControl == null) {
            if (bSHSwitchLabel.isDefault || primitiveEquals(objEval, bSHSwitchLabel.eval(callStack, interpreter), callStack, simpleNode)) {
                while (i < iJjtGetNumChildren) {
                    i++;
                    Node nodeJjtGetChild = jjtGetChild(i);
                    if (!(nodeJjtGetChild instanceof BSHSwitchLabel)) {
                        Object objEval2 = ((SimpleNode) nodeJjtGetChild).eval(callStack, interpreter);
                        if (objEval2 instanceof ReturnControl) {
                            returnControl = (ReturnControl) objEval2;
                            i = i;
                            break;
                        }
                    }
                }
            } else {
                while (i < iJjtGetNumChildren) {
                    int i2 = i + 1;
                    Node nodeJjtGetChild2 = jjtGetChild(i);
                    if (nodeJjtGetChild2 instanceof BSHSwitchLabel) {
                        bSHSwitchLabel = (BSHSwitchLabel) nodeJjtGetChild2;
                        i = i2;
                        break;
                    }
                    i = i2;
                }
            }
        }
        return (returnControl == null || returnControl.kind != 46) ? Primitive.VOID : returnControl;
    }
}
