package bsh;

import java.io.Serializable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class This implements Serializable, Runnable {
    transient Interpreter declaringInterpreter;
    NameSpace namespace;

    protected This(NameSpace nameSpace, Interpreter interpreter) {
        this.namespace = nameSpace;
        this.declaringInterpreter = interpreter;
    }

    public static void bind(This r1, NameSpace nameSpace, Interpreter interpreter) {
        r1.namespace.setParent(nameSpace);
        r1.declaringInterpreter = interpreter;
    }

    static This getThis(NameSpace nameSpace, Interpreter interpreter) {
        Class<?> cls;
        try {
            if (Capabilities.canGenerateInterfaces()) {
                cls = Class.forName("bsh.XThis");
            } else {
                if (!Capabilities.haveSwing()) {
                    return new This(nameSpace, interpreter);
                }
                cls = Class.forName("bsh.JThis");
            }
            return (This) Reflect.constructObject(cls, new Object[]{nameSpace, interpreter});
        } catch (Exception e) {
            throw new InterpreterError(new StringBuffer().append("internal error 1 in This: ").append(e).toString());
        }
    }

    static boolean isExposedThisMethod(String str) {
        return str.equals("getClass") || str.equals("invokeMethod") || str.equals("getInterface") || str.equals("wait") || str.equals("notify") || str.equals("notifyAll");
    }

    public Object getInterface(Class cls) throws UtilEvalError {
        if (cls.isInstance(this)) {
            return this;
        }
        throw new UtilEvalError(new StringBuffer().append("Dynamic proxy mechanism not available. Cannot construct interface type: ").append(cls).toString());
    }

    public Object getInterface(Class[] clsArr) throws UtilEvalError {
        for (int i = 0; i < clsArr.length; i++) {
            if (!clsArr[i].isInstance(this)) {
                throw new UtilEvalError(new StringBuffer().append("Dynamic proxy mechanism not available. Cannot construct interface type: ").append(clsArr[i]).toString());
            }
        }
        return this;
    }

    public NameSpace getNameSpace() {
        return this.namespace;
    }

    public Object invokeMethod(String str, Object[] objArr) throws EvalError {
        return invokeMethod(str, objArr, null, null, null, false);
    }

    public Object invokeMethod(String str, Object[] objArr, Interpreter interpreter, CallStack callStack, SimpleNode simpleNode, boolean z) throws EvalError {
        if (objArr != null) {
            Object[] objArr2 = new Object[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                objArr2[i] = objArr[i] == null ? Primitive.NULL : objArr[i];
            }
            objArr = objArr2;
        }
        if (interpreter == null) {
            interpreter = this.declaringInterpreter;
        }
        if (callStack == null) {
            callStack = new CallStack(this.namespace);
        }
        if (simpleNode == null) {
            simpleNode = SimpleNode.JAVACODE;
        }
        Class[] types = Types.getTypes(objArr);
        BshMethod method = null;
        try {
            method = this.namespace.getMethod(str, types, z);
        } catch (UtilEvalError e) {
        }
        if (method != null) {
            return method.invoke(objArr, interpreter, callStack, simpleNode);
        }
        if (str.equals("toString")) {
            return toString();
        }
        if (str.equals("hashCode")) {
            return new Integer(hashCode());
        }
        if (str.equals("equals")) {
            return new Boolean(this == objArr[0]);
        }
        try {
            method = this.namespace.getMethod("invoke", new Class[]{null, null});
        } catch (UtilEvalError e2) {
        }
        if (method != null) {
            return method.invoke(new Object[]{str, objArr}, interpreter, callStack, simpleNode);
        }
        throw new EvalError(new StringBuffer().append("Method ").append(StringUtil.methodString(str, types)).append(" not found in bsh scripted object: ").append(this.namespace.getName()).toString(), simpleNode, callStack);
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            invokeMethod("run", new Object[0]);
        } catch (EvalError e) {
            this.declaringInterpreter.error(new StringBuffer().append("Exception in runnable:").append(e).toString());
        }
    }

    public String toString() {
        return new StringBuffer().append("'this' reference to Bsh object: ").append(this.namespace).toString();
    }
}
