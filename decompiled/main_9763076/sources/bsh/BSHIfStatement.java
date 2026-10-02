package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHIfStatement extends SimpleNode {
    BSHIfStatement(int i) {
        super(i);
    }

    public static boolean evaluateCondition(SimpleNode simpleNode, CallStack callStack, Interpreter interpreter) throws EvalError {
        Object objEval = simpleNode.eval(callStack, interpreter);
        if (objEval instanceof Primitive) {
            if (objEval == Primitive.VOID) {
                throw new EvalError("Condition evaluates to void type", simpleNode, callStack);
            }
            objEval = ((Primitive) objEval).getValue();
        }
        if (objEval instanceof Boolean) {
            return ((Boolean) objEval).booleanValue();
        }
        throw new EvalError("Condition must evaluate to a Boolean or boolean.", simpleNode, callStack);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        Object objEval;
        if (evaluateCondition((SimpleNode) jjtGetChild(0), callStack, interpreter)) {
            objEval = ((SimpleNode) jjtGetChild(1)).eval(callStack, interpreter);
        } else {
            objEval = jjtGetNumChildren() > 2 ? ((SimpleNode) jjtGetChild(2)).eval(callStack, interpreter) : null;
        }
        return objEval instanceof ReturnControl ? objEval : Primitive.VOID;
    }
}
