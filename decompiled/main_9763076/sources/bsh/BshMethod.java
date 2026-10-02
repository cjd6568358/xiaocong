package bsh;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BshMethod implements Serializable {
    private Class[] cparamTypes;
    private Class creturnType;
    NameSpace declaringNameSpace;
    private Method javaMethod;
    private Object javaObject;
    BSHBlock methodBody;
    Modifiers modifiers;
    private String name;
    private int numArgs;
    private String[] paramNames;

    BshMethod(BSHMethodDeclaration bSHMethodDeclaration, NameSpace nameSpace, Modifiers modifiers) {
        this(bSHMethodDeclaration.name, bSHMethodDeclaration.returnType, bSHMethodDeclaration.paramsNode.getParamNames(), bSHMethodDeclaration.paramsNode.paramTypes, bSHMethodDeclaration.blockNode, nameSpace, modifiers);
    }

    BshMethod(String str, Class cls, String[] strArr, Class[] clsArr, BSHBlock bSHBlock, NameSpace nameSpace, Modifiers modifiers) {
        this.name = str;
        this.creturnType = cls;
        this.paramNames = strArr;
        if (strArr != null) {
            this.numArgs = strArr.length;
        }
        this.cparamTypes = clsArr;
        this.methodBody = bSHBlock;
        this.declaringNameSpace = nameSpace;
        this.modifiers = modifiers;
    }

    BshMethod(Method method, Object obj) {
        this(method.getName(), method.getReturnType(), null, method.getParameterTypes(), null, null, null);
        this.javaMethod = method;
        this.javaObject = obj;
    }

    private Object invokeImpl(Object[] objArr, Interpreter interpreter, CallStack callStack, SimpleNode simpleNode, boolean z) throws EvalError {
        NameSpace nameSpace;
        Object obj;
        ReturnControl returnControl;
        Class returnType = getReturnType();
        Class[] parameterTypes = getParameterTypes();
        if (callStack == null) {
            callStack = new CallStack(this.declaringNameSpace);
        }
        if (objArr == null) {
            objArr = new Object[0];
        }
        if (objArr.length != this.numArgs) {
            throw new EvalError(new StringBuffer().append("Wrong number of arguments for local method: ").append(this.name).toString(), simpleNode, callStack);
        }
        if (z) {
            nameSpace = callStack.top();
        } else {
            nameSpace = new NameSpace(this.declaringNameSpace, this.name);
            nameSpace.isMethod = true;
        }
        nameSpace.setNode(simpleNode);
        for (int i = 0; i < this.numArgs; i++) {
            if (parameterTypes[i] != null) {
                try {
                    objArr[i] = Types.castObject(objArr[i], parameterTypes[i], 1);
                    try {
                        nameSpace.setTypedVariable(this.paramNames[i], parameterTypes[i], objArr[i], (Modifiers) null);
                    } catch (UtilEvalError e) {
                        throw e.toEvalError("Typed method parameter assignment", simpleNode, callStack);
                    }
                } catch (UtilEvalError e2) {
                    throw new EvalError(new StringBuffer().append("Invalid argument: `").append(this.paramNames[i]).append("'").append(" for method: ").append(this.name).append(" : ").append(e2.getMessage()).toString(), simpleNode, callStack);
                }
            } else {
                if (objArr[i] == Primitive.VOID) {
                    throw new EvalError(new StringBuffer().append("Undefined variable or class name, parameter: ").append(this.paramNames[i]).append(" to method: ").append(this.name).toString(), simpleNode, callStack);
                }
                try {
                    nameSpace.setLocalVariable(this.paramNames[i], objArr[i], interpreter.getStrictJava());
                } catch (UtilEvalError e3) {
                    throw e3.toEvalError(simpleNode, callStack);
                }
            }
        }
        if (!z) {
            callStack.push(nameSpace);
        }
        Object objEval = this.methodBody.eval(callStack, interpreter, true);
        CallStack callStackCopy = callStack.copy();
        if (!z) {
            callStack.pop();
        }
        if (objEval instanceof ReturnControl) {
            ReturnControl returnControl2 = (ReturnControl) objEval;
            if (returnControl2.kind != 46) {
                throw new EvalError("'continue' or 'break' in method body", returnControl2.returnPoint, callStackCopy);
            }
            Object obj2 = ((ReturnControl) objEval).value;
            if (returnType == Void.TYPE && obj2 != Primitive.VOID) {
                throw new EvalError("Cannot return value from void method", returnControl2.returnPoint, callStackCopy);
            }
            obj = obj2;
            returnControl = returnControl2;
        } else {
            obj = objEval;
            returnControl = null;
        }
        if (returnType == null) {
            return obj;
        }
        if (returnType == Void.TYPE) {
            return Primitive.VOID;
        }
        try {
            return Types.castObject(obj, returnType, 1);
        } catch (UtilEvalError e4) {
            if (returnControl != null) {
                simpleNode = returnControl.returnPoint;
            }
            throw e4.toEvalError(new StringBuffer().append("Incorrect type returned from method: ").append(this.name).append(e4.getMessage()).toString(), simpleNode, callStack);
        }
    }

    public Modifiers getModifiers() {
        return this.modifiers;
    }

    public String getName() {
        return this.name;
    }

    public String[] getParameterNames() {
        return this.paramNames;
    }

    public Class[] getParameterTypes() {
        return this.cparamTypes;
    }

    public Class getReturnType() {
        return this.creturnType;
    }

    public boolean hasModifier(String str) {
        return this.modifiers != null && this.modifiers.hasModifier(str);
    }

    public Object invoke(Object[] objArr, Interpreter interpreter) throws EvalError {
        return invoke(objArr, interpreter, null, null, false);
    }

    public Object invoke(Object[] objArr, Interpreter interpreter, CallStack callStack, SimpleNode simpleNode) throws EvalError {
        return invoke(objArr, interpreter, callStack, simpleNode, false);
    }

    Object invoke(Object[] objArr, Interpreter interpreter, CallStack callStack, SimpleNode simpleNode, boolean z) throws EvalError {
        Object classInstance;
        Object objInvokeImpl;
        if (objArr != null) {
            for (Object obj : objArr) {
                if (obj == null) {
                    throw new Error("HERE!");
                }
            }
        }
        if (this.javaMethod != null) {
            try {
                return Reflect.invokeMethod(this.javaMethod, this.javaObject, objArr);
            } catch (ReflectError e) {
                throw new EvalError(new StringBuffer().append("Error invoking Java method: ").append(e).toString(), simpleNode, callStack);
            } catch (InvocationTargetException e2) {
                throw new TargetError("Exception invoking imported object method.", e2, simpleNode, callStack, true);
            }
        }
        if (this.modifiers == null || !this.modifiers.hasModifier("synchronized")) {
            return invokeImpl(objArr, interpreter, callStack, simpleNode, z);
        }
        if (this.declaringNameSpace.isClass) {
            try {
                classInstance = this.declaringNameSpace.getClassInstance();
            } catch (UtilEvalError e3) {
                throw new InterpreterError("Can't get class instance for synchronized method.");
            }
        } else {
            classInstance = this.declaringNameSpace.getThis(interpreter);
        }
        synchronized (classInstance) {
            objInvokeImpl = invokeImpl(objArr, interpreter, callStack, simpleNode, z);
        }
        return objInvokeImpl;
    }

    public String toString() {
        return new StringBuffer().append("Scripted Method: ").append(StringUtil.methodString(this.name, getParameterTypes())).toString();
    }
}
