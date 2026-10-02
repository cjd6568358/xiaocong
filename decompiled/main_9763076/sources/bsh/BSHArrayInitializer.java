package bsh;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHArrayInitializer extends SimpleNode {
    BSHArrayInitializer(int i) {
        super(i);
    }

    private void throwTypeError(Class cls, Object obj, int i, CallStack callStack) throws EvalError {
        throw new EvalError(new StringBuffer().append("Incompatible type: ").append(obj instanceof Primitive ? ((Primitive) obj).getType().getName() : Reflect.normalizeClassName(obj.getClass())).append(" in initializer of array type: ").append(cls).append(" at position: ").append(i).toString(), this, callStack);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        throw new EvalError("Array initializer has no base type.", this, callStack);
    }

    public Object eval(Class cls, int i, CallStack callStack, Interpreter interpreter) throws EvalError {
        Object objEval;
        Object objUnwrap;
        int iJjtGetNumChildren = jjtGetNumChildren();
        int[] iArr = new int[i];
        iArr[0] = iJjtGetNumChildren;
        Object objNewInstance = Array.newInstance((Class<?>) cls, iArr);
        for (int i2 = 0; i2 < iJjtGetNumChildren; i2++) {
            SimpleNode simpleNode = (SimpleNode) jjtGetChild(i2);
            if (!(simpleNode instanceof BSHArrayInitializer)) {
                objEval = simpleNode.eval(callStack, interpreter);
            } else {
                if (i < 2) {
                    throw new EvalError(new StringBuffer().append("Invalid Location for Intializer, position: ").append(i2).toString(), this, callStack);
                }
                objEval = ((BSHArrayInitializer) simpleNode).eval(cls, i - 1, callStack, interpreter);
            }
            if (objEval == Primitive.VOID) {
                throw new EvalError(new StringBuffer().append("Void in array initializer, position").append(i2).toString(), this, callStack);
            }
            if (i == 1) {
                try {
                    objUnwrap = Primitive.unwrap(Types.castObject(objEval, cls, 0));
                } catch (UtilEvalError e) {
                    throw e.toEvalError("Error in array initializer", this, callStack);
                }
            } else {
                objUnwrap = objEval;
            }
            try {
                Array.set(objNewInstance, i2, objUnwrap);
            } catch (ArrayStoreException e2) {
                Interpreter.debug(new StringBuffer().append("arraystore").append(e2).toString());
                throwTypeError(cls, objEval, i2, callStack);
            } catch (IllegalArgumentException e3) {
                Interpreter.debug(new StringBuffer().append("illegal arg").append(e3).toString());
                throwTypeError(cls, objEval, i2, callStack);
            }
        }
        return objNewInstance;
    }
}
