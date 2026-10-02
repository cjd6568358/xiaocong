package bsh;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHArrayDimensions extends SimpleNode {
    public Class baseType;
    public int[] definedDimensions;
    public int numDefinedDims;
    public int numUndefinedDims;

    BSHArrayDimensions(int i) {
        super(i);
    }

    public void addDefinedDimension() {
        this.numDefinedDims++;
    }

    public void addUndefinedDimension() {
        this.numUndefinedDims++;
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        SimpleNode simpleNode = (SimpleNode) jjtGetChild(0);
        if (!(simpleNode instanceof BSHArrayInitializer)) {
            this.definedDimensions = new int[this.numDefinedDims];
            for (int i = 0; i < this.numDefinedDims; i++) {
                try {
                    this.definedDimensions[i] = ((Primitive) ((SimpleNode) jjtGetChild(i)).eval(callStack, interpreter)).intValue();
                } catch (Exception e) {
                    throw new EvalError(new StringBuffer().append("Array index: ").append(i).append(" does not evaluate to an integer").toString(), this, callStack);
                }
            }
            return Primitive.VOID;
        }
        if (this.baseType == null) {
            throw new EvalError("Internal Array Eval err:  unknown base type", this, callStack);
        }
        Object objEval = ((BSHArrayInitializer) simpleNode).eval(this.baseType, this.numUndefinedDims, callStack, interpreter);
        int arrayDimensions = Reflect.getArrayDimensions(objEval.getClass());
        this.definedDimensions = new int[arrayDimensions];
        if (this.definedDimensions.length != this.numUndefinedDims) {
            throw new EvalError(new StringBuffer().append("Incompatible initializer. Allocation calls for a ").append(this.numUndefinedDims).append(" dimensional array, but initializer is a ").append(arrayDimensions).append(" dimensional array").toString(), this, callStack);
        }
        Object obj = objEval;
        for (int i2 = 0; i2 < this.definedDimensions.length; i2++) {
            this.definedDimensions[i2] = Array.getLength(obj);
            if (this.definedDimensions[i2] > 0) {
                obj = Array.get(obj, 0);
            }
        }
        return objEval;
    }

    public Object eval(Class cls, CallStack callStack, Interpreter interpreter) throws EvalError {
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("array base type = ").append(cls).toString());
        }
        this.baseType = cls;
        return eval(callStack, interpreter);
    }
}
