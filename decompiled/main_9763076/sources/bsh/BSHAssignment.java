package bsh;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHAssignment extends SimpleNode implements ParserConstants {
    public int operator;

    BSHAssignment(int i) {
        super(i);
    }

    private Object operation(Object obj, Object obj2, int i) throws UtilEvalError {
        if ((obj instanceof String) && obj2 != Primitive.VOID) {
            if (i != 102) {
                throw new UtilEvalError("Use of non + operator with String LHS");
            }
            return new StringBuffer().append((String) obj).append(obj2).toString();
        }
        if ((obj instanceof Primitive) || (obj2 instanceof Primitive)) {
            if (obj == Primitive.VOID || obj2 == Primitive.VOID) {
                throw new UtilEvalError("Illegal use of undefined object or 'void' literal");
            }
            if (obj == Primitive.NULL || obj2 == Primitive.NULL) {
                throw new UtilEvalError("Illegal use of null object or 'null' literal");
            }
        }
        if (((obj instanceof Boolean) || (obj instanceof Character) || (obj instanceof Number) || (obj instanceof Primitive)) && ((obj2 instanceof Boolean) || (obj2 instanceof Character) || (obj2 instanceof Number) || (obj2 instanceof Primitive))) {
            return Primitive.binaryOperation(obj, obj2, i);
        }
        throw new UtilEvalError(new StringBuffer().append("Non primitive value in operator: ").append(obj.getClass()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(ParserConstants.tokenImage[i]).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(obj2.getClass()).toString());
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError, UtilEvalError {
        Object value;
        BSHPrimaryExpression bSHPrimaryExpression = (BSHPrimaryExpression) jjtGetChild(0);
        if (bSHPrimaryExpression == null) {
            throw new InterpreterError("Error, null LHSnode");
        }
        boolean strictJava = interpreter.getStrictJava();
        LHS lhs = bSHPrimaryExpression.toLHS(callStack, interpreter);
        if (lhs == null) {
            throw new InterpreterError("Error, null LHS");
        }
        if (this.operator != 81) {
            try {
                value = lhs.getValue();
            } catch (UtilEvalError e) {
                throw e.toEvalError(this, callStack);
            }
        } else {
            value = null;
        }
        Object objEval = ((SimpleNode) jjtGetChild(1)).eval(callStack, interpreter);
        if (objEval == Primitive.VOID) {
            throw new EvalError("Void assignment.", this, callStack);
        }
        try {
            switch (this.operator) {
                case 81:
                    return lhs.assign(objEval, strictJava);
                case 118:
                    return lhs.assign(operation(value, objEval, 102), strictJava);
                case ParserConstants.MINUSASSIGN /* 119 */:
                    return lhs.assign(operation(value, objEval, 103), strictJava);
                case ParserConstants.STARASSIGN /* 120 */:
                    return lhs.assign(operation(value, objEval, 104), strictJava);
                case ParserConstants.SLASHASSIGN /* 121 */:
                    return lhs.assign(operation(value, objEval, 105), strictJava);
                case ParserConstants.ANDASSIGN /* 122 */:
                case ParserConstants.ANDASSIGNX /* 123 */:
                    return lhs.assign(operation(value, objEval, 106), strictJava);
                case ParserConstants.ORASSIGN /* 124 */:
                case ParserConstants.ORASSIGNX /* 125 */:
                    return lhs.assign(operation(value, objEval, 108), strictJava);
                case ParserConstants.XORASSIGN /* 126 */:
                    return lhs.assign(operation(value, objEval, 110), strictJava);
                case ParserConstants.MODASSIGN /* 127 */:
                    return lhs.assign(operation(value, objEval, 111), strictJava);
                case ParserConstants.LSHIFTASSIGN /* 128 */:
                case ParserConstants.LSHIFTASSIGNX /* 129 */:
                    return lhs.assign(operation(value, objEval, 112), strictJava);
                case ParserConstants.RSIGNEDSHIFTASSIGN /* 130 */:
                case ParserConstants.RSIGNEDSHIFTASSIGNX /* 131 */:
                    return lhs.assign(operation(value, objEval, 114), strictJava);
                case ParserConstants.RUNSIGNEDSHIFTASSIGN /* 132 */:
                case ParserConstants.RUNSIGNEDSHIFTASSIGNX /* 133 */:
                    return lhs.assign(operation(value, objEval, 116), strictJava);
                default:
                    throw new InterpreterError("unimplemented operator in assignment BSH");
            }
        } catch (UtilEvalError e2) {
            throw e2.toEvalError(this, callStack);
        }
    }
}
