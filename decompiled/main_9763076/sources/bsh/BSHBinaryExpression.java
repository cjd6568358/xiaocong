package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHBinaryExpression extends SimpleNode implements ParserConstants {
    static Class class$bsh$Primitive;
    public int kind;

    BSHBinaryExpression(int i) {
        super(i);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private boolean isPrimitiveValue(Object obj) {
        return (!(obj instanceof Primitive) || obj == Primitive.VOID || obj == Primitive.NULL) ? false : true;
    }

    private boolean isWrapper(Object obj) {
        return (obj instanceof Boolean) || (obj instanceof Character) || (obj instanceof Number);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        Class clsClass$;
        Object objEval = ((SimpleNode) jjtGetChild(0)).eval(callStack, interpreter);
        if (this.kind == 35) {
            if (objEval == Primitive.NULL) {
                return new Primitive(false);
            }
            Class type = ((BSHType) jjtGetChild(1)).getType(callStack, interpreter);
            if (!(objEval instanceof Primitive)) {
                return new Primitive(Types.isJavaBaseAssignable(type, objEval.getClass()));
            }
            if (class$bsh$Primitive == null) {
                clsClass$ = class$("bsh.Primitive");
                class$bsh$Primitive = clsClass$;
            } else {
                clsClass$ = class$bsh$Primitive;
            }
            return type == clsClass$ ? new Primitive(true) : new Primitive(false);
        }
        if (this.kind == 98 || this.kind == 99) {
            Object value = isPrimitiveValue(objEval) ? ((Primitive) objEval).getValue() : objEval;
            if ((value instanceof Boolean) && !((Boolean) value).booleanValue()) {
                return new Primitive(false);
            }
        }
        if (this.kind == 96 || this.kind == 97) {
            Object value2 = isPrimitiveValue(objEval) ? ((Primitive) objEval).getValue() : objEval;
            if ((value2 instanceof Boolean) && ((Boolean) value2).booleanValue()) {
                return new Primitive(true);
            }
        }
        boolean zIsWrapper = isWrapper(objEval);
        Object objEval2 = ((SimpleNode) jjtGetChild(1)).eval(callStack, interpreter);
        boolean zIsWrapper2 = isWrapper(objEval2);
        if ((zIsWrapper || isPrimitiveValue(objEval)) && ((zIsWrapper2 || isPrimitiveValue(objEval2)) && !(zIsWrapper && zIsWrapper2 && this.kind == 90))) {
            try {
                return Primitive.binaryOperation(objEval, objEval2, this.kind);
            } catch (UtilEvalError e) {
                throw e.toEvalError(this, callStack);
            }
        }
        switch (this.kind) {
            case 90:
                return new Primitive(objEval == objEval2);
            case 95:
                return new Primitive(objEval != objEval2);
            case 102:
                if ((objEval instanceof String) || (objEval2 instanceof String)) {
                    return new StringBuffer().append(objEval.toString()).append(objEval2.toString()).toString();
                }
                break;
        }
        if ((objEval instanceof Primitive) || (objEval2 instanceof Primitive)) {
            if (objEval == Primitive.VOID || objEval2 == Primitive.VOID) {
                throw new EvalError("illegal use of undefined variable, class, or 'void' literal", this, callStack);
            }
            if (objEval == Primitive.NULL || objEval2 == Primitive.NULL) {
                throw new EvalError("illegal use of null value or 'null' literal", this, callStack);
            }
        }
        throw new EvalError(new StringBuffer().append("Operator: '").append(ParserConstants.tokenImage[this.kind]).append("' inappropriate for objects").toString(), this, callStack);
    }
}
