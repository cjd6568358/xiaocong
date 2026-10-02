package bsh;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class BSHPrimarySuffix extends SimpleNode {
    public static final int CLASS = 0;
    public static final int INDEX = 1;
    public static final int NAME = 2;
    public static final int PROPERTY = 3;
    public String field;
    Object index;
    public int operation;

    BSHPrimarySuffix(int i) {
        super(i);
    }

    private Object doIndex(Object obj, boolean z, CallStack callStack, Interpreter interpreter) throws ReflectError, EvalError {
        int indexAux = getIndexAux(obj, callStack, interpreter, this);
        if (z) {
            return new LHS(obj, indexAux);
        }
        try {
            return Reflect.getIndex(obj, indexAux);
        } catch (UtilEvalError e) {
            throw e.toEvalError(this, callStack);
        }
    }

    private Object doName(Object obj, boolean z, CallStack callStack, Interpreter interpreter) throws ReflectError, EvalError, InvocationTargetException {
        try {
            if (this.field.equals("length") && obj.getClass().isArray()) {
                if (z) {
                    throw new EvalError("Can't assign array length", this, callStack);
                }
                return new Primitive(Array.getLength(obj));
            }
            if (jjtGetNumChildren() == 0) {
                return z ? Reflect.getLHSObjectField(obj, this.field) : Reflect.getObjectFieldValue(obj, this.field);
            }
            try {
                try {
                    return Reflect.invokeObjectMethod(obj, this.field, ((BSHArguments) jjtGetChild(0)).getArguments(callStack, interpreter), interpreter, callStack, this);
                } catch (InvocationTargetException e) {
                    String string = new StringBuffer().append("Method Invocation ").append(this.field).toString();
                    Throwable targetException = e.getTargetException();
                    boolean zInNativeCode = true;
                    if (targetException instanceof EvalError) {
                        zInNativeCode = targetException instanceof TargetError ? ((TargetError) targetException).inNativeCode() : false;
                    }
                    throw new TargetError(string, targetException, this, callStack, zInNativeCode);
                }
            } catch (ReflectError e2) {
                throw new EvalError(new StringBuffer().append("Error in method invocation: ").append(e2.getMessage()).toString(), this, callStack);
            }
        } catch (UtilEvalError e3) {
            throw e3.toEvalError(this, callStack);
        }
    }

    private Object doProperty(boolean z, Object obj, CallStack callStack, Interpreter interpreter) throws EvalError {
        if (obj == Primitive.VOID) {
            throw new EvalError("Attempt to access property on undefined variable or class name", this, callStack);
        }
        if (obj instanceof Primitive) {
            throw new EvalError("Attempt to access property on a primitive", this, callStack);
        }
        Object objEval = ((SimpleNode) jjtGetChild(0)).eval(callStack, interpreter);
        if (!(objEval instanceof String)) {
            throw new EvalError("Property expression must be a String or identifier.", this, callStack);
        }
        if (z) {
            return new LHS(obj, (String) objEval);
        }
        CollectionManager collectionManager = CollectionManager.getCollectionManager();
        if (collectionManager.isMap(obj)) {
            Object fromMap = collectionManager.getFromMap(obj, objEval);
            return fromMap == null ? Primitive.NULL : fromMap;
        }
        try {
            return Reflect.getObjectProperty(obj, (String) objEval);
        } catch (ReflectError e) {
            throw new EvalError(new StringBuffer().append("No such property: ").append(objEval).toString(), this, callStack);
        } catch (UtilEvalError e2) {
            throw e2.toEvalError(new StringBuffer().append("Property: ").append(objEval).toString(), this, callStack);
        }
    }

    static int getIndexAux(Object obj, CallStack callStack, Interpreter interpreter, SimpleNode simpleNode) throws EvalError {
        if (!obj.getClass().isArray()) {
            throw new EvalError("Not an array", simpleNode, callStack);
        }
        try {
            Object objEval = ((SimpleNode) simpleNode.jjtGetChild(0)).eval(callStack, interpreter);
            if (!(objEval instanceof Primitive)) {
                objEval = Types.castObject(objEval, Integer.TYPE, 1);
            }
            return ((Primitive) objEval).intValue();
        } catch (UtilEvalError e) {
            Interpreter.debug(new StringBuffer().append("doIndex: ").append(e).toString());
            throw e.toEvalError("Arrays may only be indexed by integer types.", simpleNode, callStack);
        }
    }

    public Object doSuffix(Object obj, boolean z, CallStack callStack, Interpreter interpreter) throws EvalError {
        if (this.operation == 0) {
            if (!(obj instanceof BSHType)) {
                throw new EvalError("Attempt to use .class suffix on non class.", this, callStack);
            }
            if (z) {
                throw new EvalError("Can't assign .class", this, callStack);
            }
            callStack.top();
            return ((BSHType) obj).getType(callStack, interpreter);
        }
        if (obj instanceof SimpleNode) {
            obj = obj instanceof BSHAmbiguousName ? ((BSHAmbiguousName) obj).toObject(callStack, interpreter) : ((SimpleNode) obj).eval(callStack, interpreter);
        } else if (obj instanceof LHS) {
            try {
                obj = ((LHS) obj).getValue();
            } catch (UtilEvalError e) {
                throw e.toEvalError(this, callStack);
            }
        }
        try {
            switch (this.operation) {
                case 1:
                    return doIndex(obj, z, callStack, interpreter);
                case 2:
                    return doName(obj, z, callStack, interpreter);
                case 3:
                    return doProperty(z, obj, callStack, interpreter);
                default:
                    throw new InterpreterError("Unknown suffix type");
            }
        } catch (ReflectError e2) {
            throw new EvalError(new StringBuffer().append("reflection error: ").append(e2).toString(), this, callStack);
        } catch (InvocationTargetException e3) {
            throw new TargetError("target exception", e3.getTargetException(), this, callStack, true);
        }
    }
}
