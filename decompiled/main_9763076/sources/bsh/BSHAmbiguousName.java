package bsh;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHAmbiguousName extends SimpleNode {
    public String text;

    BSHAmbiguousName(int i) {
        super(i);
    }

    @Override // bsh.SimpleNode
    public Object eval(CallStack callStack, Interpreter interpreter) throws EvalError {
        throw new InterpreterError("Don't know how to eval an ambiguous name!  Use toObject() if you want an object.");
    }

    public Name getName(NameSpace nameSpace) {
        return nameSpace.getNameResolver(this.text);
    }

    public Class toClass(CallStack callStack, Interpreter interpreter) throws EvalError {
        try {
            return getName(callStack.top()).toClass();
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        } catch (ClassNotFoundException e2) {
            throw new EvalError(e2.getMessage(), this, callStack);
        }
    }

    public LHS toLHS(CallStack callStack, Interpreter interpreter) throws EvalError {
        try {
            return getName(callStack.top()).toLHS(callStack, interpreter);
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }

    public Object toObject(CallStack callStack, Interpreter interpreter) throws EvalError {
        return toObject(callStack, interpreter, false);
    }

    Object toObject(CallStack callStack, Interpreter interpreter, boolean z) throws EvalError {
        try {
            return getName(callStack.top()).toObject(callStack, interpreter, z);
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }

    @Override // bsh.SimpleNode
    public String toString() {
        return new StringBuffer().append("AmbigousName: ").append(this.text).toString();
    }
}
