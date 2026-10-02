package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHBlock extends SimpleNode {
    public boolean isSynchronized;

    public interface NodeFilter {
        boolean isVisible(SimpleNode simpleNode);
    }

    BSHBlock(int i) {
        super(i);
        this.isSynchronized = false;
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        return eval(callStack, interpreter, false);
    }

    public Object eval(CallStack callStack, Interpreter interpreter, boolean z) throws EvalError {
        Object objEvalBlock;
        Object objEval = this.isSynchronized ? ((SimpleNode) jjtGetChild(0)).eval(callStack, interpreter) : null;
        if (!this.isSynchronized) {
            return evalBlock(callStack, interpreter, z, null);
        }
        synchronized (objEval) {
            objEvalBlock = evalBlock(callStack, interpreter, z, null);
        }
        return objEvalBlock;
    }

    Object evalBlock(CallStack callStack, Interpreter interpreter, boolean z, NodeFilter nodeFilter) throws EvalError {
        NameSpace nameSpace;
        Object objEval;
        Primitive primitive = Primitive.VOID;
        if (z) {
            nameSpace = null;
        } else {
            NameSpace pVar = callStack.top();
            callStack.swap(new BlockNameSpace(pVar));
            nameSpace = pVar;
        }
        int i = this.isSynchronized ? 1 : 0;
        int iJjtGetNumChildren = jjtGetNumChildren();
        for (int i2 = i; i2 < iJjtGetNumChildren; i2++) {
            try {
                SimpleNode simpleNode = (SimpleNode) jjtGetChild(i2);
                if ((nodeFilter == null || nodeFilter.isVisible(simpleNode)) && (simpleNode instanceof BSHClassDeclaration)) {
                    simpleNode.eval(callStack, interpreter);
                }
            } catch (Throwable th) {
                if (!z) {
                    callStack.swap(nameSpace);
                }
                throw th;
            }
        }
        int i3 = i;
        Object obj = primitive;
        int i4 = i3;
        while (true) {
            if (i4 >= iJjtGetNumChildren) {
                objEval = obj;
                break;
            }
            SimpleNode simpleNode2 = (SimpleNode) jjtGetChild(i4);
            if (simpleNode2 instanceof BSHClassDeclaration) {
                objEval = obj;
            } else if (nodeFilter == null || nodeFilter.isVisible(simpleNode2)) {
                objEval = simpleNode2.eval(callStack, interpreter);
                if (objEval instanceof ReturnControl) {
                    break;
                }
            } else {
                objEval = obj;
            }
            i4++;
            obj = objEval;
        }
        if (!z) {
            callStack.swap(nameSpace);
        }
        return objEval;
    }
}
