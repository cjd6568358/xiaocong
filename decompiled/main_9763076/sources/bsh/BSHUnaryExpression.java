package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHUnaryExpression extends SimpleNode implements ParserConstants {
    public int kind;
    public boolean postfix;

    BSHUnaryExpression(int i) {
        super(i);
        this.postfix = false;
    }

    private Object lhsUnaryOperation(LHS lhs, boolean z) throws UtilEvalError {
        if (Interpreter.DEBUG) {
            Interpreter.debug("lhsUnaryOperation");
        }
        Object value = lhs.getValue();
        Object objUnaryOperation = unaryOperation(value, this.kind);
        if (!this.postfix) {
            value = objUnaryOperation;
        }
        lhs.assign(objUnaryOperation, z);
        return value;
    }

    private Object primitiveWrapperUnaryOperation(Object obj, int i) throws UtilEvalError {
        Class<?> cls = obj.getClass();
        Object objPromoteToInteger = Primitive.promoteToInteger(obj);
        if (objPromoteToInteger instanceof Boolean) {
            return new Boolean(Primitive.booleanUnaryOperation((Boolean) objPromoteToInteger, i));
        }
        if (!(objPromoteToInteger instanceof Integer)) {
            if (objPromoteToInteger instanceof Long) {
                return new Long(Primitive.longUnaryOperation((Long) objPromoteToInteger, i));
            }
            if (objPromoteToInteger instanceof Float) {
                return new Float(Primitive.floatUnaryOperation((Float) objPromoteToInteger, i));
            }
            if (objPromoteToInteger instanceof Double) {
                return new Double(Primitive.doubleUnaryOperation((Double) objPromoteToInteger, i));
            }
            throw new InterpreterError("An error occurred.  Please call technical support.");
        }
        int iIntUnaryOperation = Primitive.intUnaryOperation((Integer) objPromoteToInteger, i);
        if (i == 100 || i == 101) {
            if (cls == Byte.TYPE) {
                return new Byte((byte) iIntUnaryOperation);
            }
            if (cls == Short.TYPE) {
                return new Short((short) iIntUnaryOperation);
            }
            if (cls == Character.TYPE) {
                return new Character((char) iIntUnaryOperation);
            }
        }
        return new Integer(iIntUnaryOperation);
    }

    private Object unaryOperation(Object obj, int i) throws UtilEvalError {
        if ((obj instanceof Boolean) || (obj instanceof Character) || (obj instanceof Number)) {
            return primitiveWrapperUnaryOperation(obj, i);
        }
        if (obj instanceof Primitive) {
            return Primitive.unaryOperation((Primitive) obj, i);
        }
        throw new UtilEvalError(new StringBuffer().append("Unary operation ").append(ParserConstants.tokenImage[i]).append(" inappropriate for object").toString());
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        SimpleNode simpleNode = (SimpleNode) jjtGetChild(0);
        try {
            return (this.kind == 100 || this.kind == 101) ? lhsUnaryOperation(((BSHPrimaryExpression) simpleNode).toLHS(callStack, interpreter), interpreter.getStrictJava()) : unaryOperation(simpleNode.eval(callStack, interpreter), this.kind);
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }
}
