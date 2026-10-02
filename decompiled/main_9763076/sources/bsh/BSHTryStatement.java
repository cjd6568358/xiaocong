package bsh;

import java.util.Vector;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHTryStatement extends SimpleNode {
    BSHTryStatement(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        Node nodeJjtGetChild;
        Object objEval;
        Object objEval2;
        int i = 1;
        int i2 = 0;
        TargetError targetError = null;
        BSHBlock bSHBlock = (BSHBlock) jjtGetChild(0);
        Vector vector = new Vector();
        Vector vector2 = new Vector();
        int iJjtGetNumChildren = jjtGetNumChildren();
        while (true) {
            if (i >= iJjtGetNumChildren) {
                nodeJjtGetChild = null;
                break;
            }
            int i3 = i + 1;
            nodeJjtGetChild = jjtGetChild(i);
            if (!(nodeJjtGetChild instanceof BSHFormalParameter)) {
                break;
            }
            vector.addElement(nodeJjtGetChild);
            i = i3 + 1;
            vector2.addElement(jjtGetChild(i3));
        }
        BSHBlock bSHBlock2 = nodeJjtGetChild != null ? (BSHBlock) nodeJjtGetChild : null;
        int iDepth = callStack.depth();
        try {
            objEval = bSHBlock.eval(callStack, interpreter);
            e = null;
        } catch (TargetError e) {
            e = e;
            String string = "Bsh Stack: ";
            while (callStack.depth() > iDepth) {
                string = new StringBuffer().append(string).append("\t").append(callStack.pop()).append("\n").toString();
            }
            objEval = null;
        }
        Throwable target = e != null ? e.getTarget() : null;
        if (target != null) {
            int size = vector.size();
            while (true) {
                if (i2 >= size) {
                    objEval2 = objEval;
                    targetError = e;
                } else {
                    BSHFormalParameter bSHFormalParameter = (BSHFormalParameter) vector.elementAt(i2);
                    bSHFormalParameter.eval(callStack, interpreter);
                    if (bSHFormalParameter.type == null && interpreter.getStrictJava()) {
                        throw new EvalError("(Strict Java) Untyped catch block", this, callStack);
                    }
                    if (bSHFormalParameter.type != null) {
                        try {
                            target = (Throwable) Types.castObject(target, bSHFormalParameter.type, 1);
                        } catch (UtilEvalError e2) {
                            i2++;
                        }
                    }
                    BSHBlock bSHBlock3 = (BSHBlock) vector2.elementAt(i2);
                    NameSpace pVar = callStack.top();
                    BlockNameSpace blockNameSpace = new BlockNameSpace(pVar);
                    try {
                        if (bSHFormalParameter.type == BSHFormalParameter.UNTYPED) {
                            blockNameSpace.setBlockVariable(bSHFormalParameter.name, target);
                        } else {
                            new Modifiers();
                            blockNameSpace.setTypedVariable(bSHFormalParameter.name, bSHFormalParameter.type, target, new Modifiers());
                        }
                        callStack.swap(blockNameSpace);
                        try {
                            objEval2 = bSHBlock3.eval(callStack, interpreter);
                            callStack.swap(pVar);
                        } catch (Throwable th) {
                            callStack.swap(pVar);
                            throw th;
                        }
                    } catch (UtilEvalError e3) {
                        throw new InterpreterError("Unable to set var in catch block namespace.");
                    }
                }
            }
        } else {
            objEval2 = objEval;
            targetError = e;
        }
        if (bSHBlock2 != null) {
            objEval2 = bSHBlock2.eval(callStack, interpreter);
        }
        if (targetError != null) {
            throw targetError;
        }
        return objEval2 instanceof ReturnControl ? objEval2 : Primitive.VOID;
    }
}
