package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHEnhancedForStatement extends SimpleNode implements ParserConstants {
    String varName;

    BSHEnhancedForStatement(int i) {
        super(i);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00a9  */
    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        Class type;
        boolean z;
        SimpleNode simpleNode = null;
        NameSpace pVar = callStack.top();
        SimpleNode simpleNode2 = (SimpleNode) jjtGetChild(0);
        int iJjtGetNumChildren = jjtGetNumChildren();
        if (simpleNode2 instanceof BSHType) {
            type = ((BSHType) simpleNode2).getType(callStack, interpreter);
            simpleNode2 = (SimpleNode) jjtGetChild(1);
            if (iJjtGetNumChildren > 2) {
                simpleNode = (SimpleNode) jjtGetChild(2);
            }
        } else if (iJjtGetNumChildren > 1) {
            type = null;
            simpleNode = (SimpleNode) jjtGetChild(1);
        } else {
            type = null;
        }
        BlockNameSpace blockNameSpace = new BlockNameSpace(pVar);
        callStack.swap(blockNameSpace);
        Object objEval = simpleNode2.eval(callStack, interpreter);
        if (objEval == Primitive.NULL) {
            throw new EvalError("The collection, array, map, iterator, or enumeration portion of a for statement cannot be null.", this, callStack);
        }
        CollectionManager collectionManager = CollectionManager.getCollectionManager();
        if (!collectionManager.isBshIterable(objEval)) {
            throw new EvalError(new StringBuffer().append("Can't iterate over type: ").append(objEval.getClass()).toString(), this, callStack);
        }
        BshIterator bshIterator = collectionManager.getBshIterator(objEval);
        Object obj = Primitive.VOID;
        do {
            Object obj2 = obj;
            if (bshIterator.hasNext()) {
                if (type != null) {
                    try {
                        blockNameSpace.setTypedVariable(this.varName, type, bshIterator.next(), new Modifiers());
                    } catch (UtilEvalError e) {
                        throw e.toEvalError(new StringBuffer().append("for loop iterator variable:").append(this.varName).toString(), this, callStack);
                    }
                } else {
                    blockNameSpace.setVariable(this.varName, bshIterator.next(), false);
                }
                if (simpleNode != null) {
                    Object objEval2 = simpleNode.eval(callStack, interpreter);
                    if (objEval2 instanceof ReturnControl) {
                        switch (((ReturnControl) objEval2).kind) {
                            case 12:
                                z = true;
                                obj = obj2;
                                break;
                            case 19:
                                z = false;
                                obj = obj2;
                                break;
                            case 46:
                                obj = objEval2;
                                z = true;
                                break;
                            default:
                                z = false;
                                obj = obj2;
                                break;
                        }
                    } else {
                        z = false;
                        obj = obj2;
                    }
                } else {
                    z = false;
                    obj = obj2;
                }
            } else {
                obj = obj2;
            }
            callStack.swap(pVar);
            return obj;
        } while (!z);
        callStack.swap(pVar);
        return obj;
    }
}
