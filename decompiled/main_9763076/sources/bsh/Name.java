package bsh;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class Name implements Serializable {
    private static String FINISHED = null;
    Class asClass;
    private int callstackDepth;
    Class classOfStaticMethod;
    private Object evalBaseObject;
    private String evalName;
    private String lastEvalName;
    public NameSpace namespace;
    String value;

    Name(NameSpace nameSpace, String str) {
        this.value = null;
        this.namespace = nameSpace;
        this.value = str;
    }

    private Object completeRound(String str, String str2, Object obj) {
        if (obj == null) {
            throw new InterpreterError(new StringBuffer().append("lastEvalName = ").append(str).toString());
        }
        this.lastEvalName = str;
        this.evalName = str2;
        this.evalBaseObject = obj;
        return obj;
    }

    private Object consumeNextObjectField(CallStack callStack, Interpreter interpreter, boolean z, boolean z2) throws UtilEvalError {
        Object classIdentifier;
        Object objResolveThisFieldReference;
        if (this.evalBaseObject == null && !isCompound(this.evalName) && !z && (objResolveThisFieldReference = resolveThisFieldReference(callStack, this.namespace, interpreter, this.evalName, false)) != Primitive.VOID) {
            return completeRound(this.evalName, FINISHED, objResolveThisFieldReference);
        }
        String strPrefix = prefix(this.evalName, 1);
        if ((this.evalBaseObject == null || (this.evalBaseObject instanceof This)) && !z) {
            if (Interpreter.DEBUG) {
                Interpreter.debug(new StringBuffer().append("trying to resolve variable: ").append(strPrefix).toString());
            }
            Object objResolveThisFieldReference2 = this.evalBaseObject == null ? resolveThisFieldReference(callStack, this.namespace, interpreter, strPrefix, false) : resolveThisFieldReference(callStack, ((This) this.evalBaseObject).namespace, interpreter, strPrefix, true);
            if (objResolveThisFieldReference2 != Primitive.VOID) {
                if (Interpreter.DEBUG) {
                    Interpreter.debug(new StringBuffer().append("resolved variable: ").append(strPrefix).append(" in namespace: ").append(this.namespace).toString());
                }
                return completeRound(strPrefix, suffix(this.evalName), objResolveThisFieldReference2);
            }
        }
        if (this.evalBaseObject == null) {
            if (Interpreter.DEBUG) {
                Interpreter.debug(new StringBuffer().append("trying class: ").append(this.evalName).toString());
            }
            Class cls = null;
            int i = 1;
            String strPrefix2 = null;
            while (i <= countParts(this.evalName) && (cls = this.namespace.getClass((strPrefix2 = prefix(this.evalName, i)))) == null) {
                i++;
            }
            if (cls != null) {
                return completeRound(strPrefix2, suffix(this.evalName, countParts(this.evalName) - i), new ClassIdentifier(cls));
            }
            if (Interpreter.DEBUG) {
                Interpreter.debug(new StringBuffer().append("not a class, trying var prefix ").append(this.evalName).toString());
            }
        }
        if ((this.evalBaseObject == null || (this.evalBaseObject instanceof This)) && !z && z2) {
            NameSpace nameSpace = this.evalBaseObject == null ? this.namespace : ((This) this.evalBaseObject).namespace;
            This r1 = new NameSpace(nameSpace, new StringBuffer().append("auto: ").append(strPrefix).toString()).getThis(interpreter);
            nameSpace.setVariable(strPrefix, r1, false);
            return completeRound(strPrefix, suffix(this.evalName), r1);
        }
        if (this.evalBaseObject == null) {
            if (isCompound(this.evalName)) {
                throw new UtilEvalError(new StringBuffer().append("Class or variable not found: ").append(this.evalName).toString());
            }
            return completeRound(this.evalName, FINISHED, Primitive.VOID);
        }
        if (this.evalBaseObject == Primitive.NULL) {
            throw new UtilTargetError(new NullPointerException(new StringBuffer().append("Null Pointer while evaluating: ").append(this.value).toString()));
        }
        if (this.evalBaseObject == Primitive.VOID) {
            throw new UtilEvalError(new StringBuffer().append("Undefined variable or class name while evaluating: ").append(this.value).toString());
        }
        if (this.evalBaseObject instanceof Primitive) {
            throw new UtilEvalError(new StringBuffer().append("Can't treat primitive like an object. Error while evaluating: ").append(this.value).toString());
        }
        if (!(this.evalBaseObject instanceof ClassIdentifier)) {
            if (z) {
                throw new UtilEvalError(new StringBuffer().append(this.value).append(" does not resolve to a class name.").toString());
            }
            String strPrefix3 = prefix(this.evalName, 1);
            if (strPrefix3.equals("length") && this.evalBaseObject.getClass().isArray()) {
                return completeRound(strPrefix3, suffix(this.evalName), new Primitive(Array.getLength(this.evalBaseObject)));
            }
            try {
                return completeRound(strPrefix3, suffix(this.evalName), Reflect.getObjectFieldValue(this.evalBaseObject, strPrefix3));
            } catch (ReflectError e) {
                throw new UtilEvalError(new StringBuffer().append("Cannot access field: ").append(strPrefix3).append(", on object: ").append(this.evalBaseObject).toString());
            }
        }
        Class<?> targetClass = ((ClassIdentifier) this.evalBaseObject).getTargetClass();
        String strPrefix4 = prefix(this.evalName, 1);
        if (strPrefix4.equals("this")) {
            for (NameSpace parent = this.namespace; parent != null; parent = parent.getParent()) {
                if (parent.classInstance != null && parent.classInstance.getClass() == targetClass) {
                    return completeRound(strPrefix4, suffix(this.evalName), parent.classInstance);
                }
            }
            throw new UtilEvalError(new StringBuffer().append("Can't find enclosing 'this' instance of class: ").append(targetClass).toString());
        }
        try {
            if (Interpreter.DEBUG) {
                Interpreter.debug(new StringBuffer().append("Name call to getStaticFieldValue, class: ").append(targetClass).append(", field:").append(strPrefix4).toString());
            }
            classIdentifier = Reflect.getStaticFieldValue(targetClass, strPrefix4);
        } catch (ReflectError e2) {
            if (Interpreter.DEBUG) {
                Interpreter.debug(new StringBuffer().append("field reflect error: ").append(e2).toString());
            }
            classIdentifier = null;
        }
        if (classIdentifier == null) {
            Class cls2 = this.namespace.getClass(new StringBuffer().append(targetClass.getName()).append("$").append(strPrefix4).toString());
            if (cls2 != null) {
                classIdentifier = new ClassIdentifier(cls2);
            }
        }
        if (classIdentifier == null) {
            throw new UtilEvalError(new StringBuffer().append("No static field or inner class: ").append(strPrefix4).append(" of ").append(targetClass).toString());
        }
        return completeRound(strPrefix4, suffix(this.evalName), classIdentifier);
    }

    static int countParts(String str) {
        if (str == null) {
            return 0;
        }
        int i = 0;
        int iIndexOf = -1;
        while (true) {
            iIndexOf = str.indexOf(46, iIndexOf + 1);
            if (iIndexOf == -1) {
                return i + 1;
            }
            i++;
        }
    }

    static NameSpace getClassNameSpace(NameSpace nameSpace) {
        if (nameSpace.isClass) {
            return nameSpace;
        }
        if (nameSpace.isMethod && nameSpace.getParent() != null && nameSpace.getParent().isClass) {
            return nameSpace.getParent();
        }
        return null;
    }

    private Object invokeLocalMethod(Interpreter interpreter, Object[] objArr, CallStack callStack, SimpleNode simpleNode) throws EvalError {
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("invokeLocalMethod: ").append(this.value).toString());
        }
        if (interpreter == null) {
            throw new InterpreterError("invokeLocalMethod: interpreter = null");
        }
        String str = this.value;
        Class[] types = Types.getTypes(objArr);
        try {
            BshMethod method = this.namespace.getMethod(str, types);
            if (method != null) {
                return method.invoke(objArr, interpreter, callStack, simpleNode);
            }
            interpreter.getClassManager();
            try {
                Object command = this.namespace.getCommand(str, types, interpreter);
                if (command == null) {
                    try {
                        BshMethod method2 = this.namespace.getMethod("invoke", new Class[]{null, null});
                        if (method2 != null) {
                            return method2.invoke(new Object[]{str, objArr}, interpreter, callStack, simpleNode);
                        }
                        throw new EvalError(new StringBuffer().append("Command not found: ").append(StringUtil.methodString(str, types)).toString(), simpleNode, callStack);
                    } catch (UtilEvalError e) {
                        throw e.toEvalError("Local method invocation", simpleNode, callStack);
                    }
                }
                if (command instanceof BshMethod) {
                    return ((BshMethod) command).invoke(objArr, interpreter, callStack, simpleNode);
                }
                if (!(command instanceof Class)) {
                    throw new InterpreterError("invalid command type");
                }
                try {
                    return Reflect.invokeCompiledCommand((Class) command, objArr, interpreter, callStack);
                } catch (UtilEvalError e2) {
                    throw e2.toEvalError("Error invoking compiled command: ", simpleNode, callStack);
                }
            } catch (UtilEvalError e3) {
                throw e3.toEvalError("Error loading command: ", simpleNode, callStack);
            }
        } catch (UtilEvalError e4) {
            throw e4.toEvalError("Local method invocation", simpleNode, callStack);
        }
    }

    public static boolean isCompound(String str) {
        return str.indexOf(46) != -1;
    }

    static String prefix(String str) {
        if (isCompound(str)) {
            return prefix(str, countParts(str) - 1);
        }
        return null;
    }

    static String prefix(String str, int i) {
        if (i < 1) {
            return null;
        }
        int iIndexOf = -1;
        int i2 = 0;
        do {
            iIndexOf = str.indexOf(46, iIndexOf + 1);
            if (iIndexOf == -1) {
                break;
            }
            i2++;
        } while (i2 < i);
        return iIndexOf != -1 ? str.substring(0, iIndexOf) : str;
    }

    private void reset() {
        this.evalName = this.value;
        this.evalBaseObject = null;
        this.callstackDepth = 0;
    }

    static String suffix(String str) {
        if (isCompound(str)) {
            return suffix(str, countParts(str) - 1);
        }
        return null;
    }

    public static String suffix(String str, int i) {
        if (i < 1) {
            return null;
        }
        int i2 = 0;
        int length = str.length() + 1;
        do {
            length = str.lastIndexOf(46, length - 1);
            if (length == -1) {
                break;
            }
            i2++;
        } while (i2 < i);
        return length != -1 ? str.substring(length + 1) : str;
    }

    public Object invokeMethod(Interpreter interpreter, Object[] objArr, CallStack callStack, SimpleNode simpleNode) throws ReflectError, EvalError, UtilEvalError, InvocationTargetException {
        NameSpace classNameSpace;
        String strSuffix = suffix(this.value, 1);
        BshClassManager classManager = interpreter.getClassManager();
        NameSpace pVar = callStack.top();
        if (this.classOfStaticMethod != null) {
            return Reflect.invokeStaticMethod(classManager, this.classOfStaticMethod, strSuffix, objArr);
        }
        if (!isCompound(this.value)) {
            return invokeLocalMethod(interpreter, objArr, callStack, simpleNode);
        }
        String strPrefix = prefix(this.value);
        if (strPrefix.equals("super") && countParts(this.value) == 2 && (classNameSpace = getClassNameSpace(pVar.getThis(interpreter).getNameSpace())) != null) {
            return ClassGenerator.getClassGenerator().invokeSuperclassMethod(classManager, classNameSpace.getClassInstance(), strSuffix, objArr);
        }
        Name nameResolver = pVar.getNameResolver(strPrefix);
        Object object = nameResolver.toObject(callStack, interpreter);
        if (object == Primitive.VOID) {
            throw new UtilEvalError(new StringBuffer().append("Attempt to resolve method: ").append(strSuffix).append("() on undefined variable or class name: ").append(nameResolver).toString());
        }
        if (!(object instanceof ClassIdentifier)) {
            if (object instanceof Primitive) {
                if (object == Primitive.NULL) {
                    throw new UtilTargetError(new NullPointerException("Null Pointer in Method Invocation"));
                }
                if (Interpreter.DEBUG) {
                    Interpreter.debug("Attempt to access method on primitive... allowing bsh.Primitive to peek through for debugging");
                }
            }
            return Reflect.invokeObjectMethod(object, strSuffix, objArr, interpreter, callStack, simpleNode);
        }
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("invokeMethod: trying static - ").append(nameResolver).toString());
        }
        Class targetClass = ((ClassIdentifier) object).getTargetClass();
        this.classOfStaticMethod = targetClass;
        if (targetClass != null) {
            return Reflect.invokeStaticMethod(classManager, targetClass, strSuffix, objArr);
        }
        throw new UtilEvalError(new StringBuffer().append("invokeMethod: unknown target: ").append(nameResolver).toString());
    }

    Object resolveThisFieldReference(CallStack callStack, NameSpace nameSpace, Interpreter interpreter, String str, boolean z) throws UtilEvalError {
        if (str.equals("this")) {
            if (z) {
                throw new UtilEvalError("Redundant to call .this on This type");
            }
            This r0 = nameSpace.getThis(interpreter);
            NameSpace classNameSpace = getClassNameSpace(r0.getNameSpace());
            if (classNameSpace != null) {
                return isCompound(this.evalName) ? classNameSpace.getThis(interpreter) : classNameSpace.getClassInstance();
            }
            return r0;
        }
        if (str.equals("super")) {
            This r1 = nameSpace.getSuper(interpreter);
            NameSpace nameSpace2 = r1.getNameSpace();
            return (nameSpace2.getParent() == null || !nameSpace2.getParent().isClass) ? r1 : nameSpace2.getParent().getThis(interpreter);
        }
        Object global = str.equals("global") ? nameSpace.getGlobal(interpreter) : null;
        if (global == null && z) {
            if (str.equals("namespace")) {
                global = nameSpace;
            } else if (str.equals("variables")) {
                global = nameSpace.getVariableNames();
            } else if (str.equals("methods")) {
                global = nameSpace.getMethodNames();
            } else if (str.equals("interpreter")) {
                if (!this.lastEvalName.equals("this")) {
                    throw new UtilEvalError("Can only call .interpreter on literal 'this'");
                }
                global = interpreter;
            }
        }
        if (global == null && z && str.equals("caller")) {
            if (!this.lastEvalName.equals("this") && !this.lastEvalName.equals("caller")) {
                throw new UtilEvalError("Can only call .caller on literal 'this' or literal '.caller'");
            }
            if (callStack == null) {
                throw new InterpreterError("no callstack");
            }
            int i = this.callstackDepth + 1;
            this.callstackDepth = i;
            return callStack.get(i).getThis(interpreter);
        }
        if (global == null && z && str.equals("callstack")) {
            if (!this.lastEvalName.equals("this")) {
                throw new UtilEvalError("Can only call .callstack on literal 'this'");
            }
            if (callStack == null) {
                throw new InterpreterError("no callstack");
            }
            global = callStack;
        }
        if (global == null) {
            global = nameSpace.getVariable(str);
        }
        if (global == null) {
            throw new InterpreterError(new StringBuffer().append("null this field ref:").append(str).toString());
        }
        return global;
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0064  */
    public synchronized Class toClass() throws UtilEvalError, ClassNotFoundException {
        Class targetClass;
        Class cls = null;
        Object object = null;
        synchronized (this) {
            if (this.asClass != null) {
                cls = this.asClass;
            } else {
                reset();
                if (this.evalName.equals("var")) {
                    this.asClass = null;
                } else {
                    Class cls2 = this.namespace.getClass(this.evalName);
                    if (cls2 == null) {
                        try {
                            object = toObject(null, null, true);
                        } catch (UtilEvalError e) {
                        }
                        if (object instanceof ClassIdentifier) {
                            targetClass = ((ClassIdentifier) object).getTargetClass();
                        } else {
                            targetClass = cls2;
                        }
                    } else {
                        targetClass = cls2;
                    }
                    if (targetClass == null) {
                        throw new ClassNotFoundException(new StringBuffer().append("Class: ").append(this.value).append(" not found in namespace").toString());
                    }
                    this.asClass = targetClass;
                    cls = this.asClass;
                }
            }
        }
        return cls;
    }

    public synchronized LHS toLHS(CallStack callStack, Interpreter interpreter) throws UtilEvalError {
        LHS lHSStaticField;
        synchronized (this) {
            reset();
            if (isCompound(this.evalName)) {
                Object objConsumeNextObjectField = null;
                while (this.evalName != null && isCompound(this.evalName)) {
                    try {
                        objConsumeNextObjectField = consumeNextObjectField(callStack, interpreter, false, true);
                    } catch (UtilEvalError e) {
                        throw new UtilEvalError(new StringBuffer().append("LHS evaluation: ").append(e.getMessage()).toString());
                    }
                }
                if (this.evalName == null && (objConsumeNextObjectField instanceof ClassIdentifier)) {
                    throw new UtilEvalError(new StringBuffer().append("Can't assign to class: ").append(this.value).toString());
                }
                if (objConsumeNextObjectField == null) {
                    throw new UtilEvalError(new StringBuffer().append("Error in LHS: ").append(this.value).toString());
                }
                if (objConsumeNextObjectField instanceof This) {
                    if (this.evalName.equals("namespace") || this.evalName.equals("variables") || this.evalName.equals("methods") || this.evalName.equals("caller")) {
                        throw new UtilEvalError(new StringBuffer().append("Can't assign to special variable: ").append(this.evalName).toString());
                    }
                    Interpreter.debug("found This reference evaluating LHS");
                    lHSStaticField = new LHS(((This) objConsumeNextObjectField).namespace, this.evalName, this.lastEvalName.equals("super") ? false : true);
                } else {
                    if (this.evalName == null) {
                        throw new InterpreterError("Internal error in lhs...");
                    }
                    try {
                        lHSStaticField = objConsumeNextObjectField instanceof ClassIdentifier ? Reflect.getLHSStaticField(((ClassIdentifier) objConsumeNextObjectField).getTargetClass(), this.evalName) : Reflect.getLHSObjectField(objConsumeNextObjectField, this.evalName);
                    } catch (ReflectError e2) {
                        throw new UtilEvalError(new StringBuffer().append("Field access: ").append(e2).toString());
                    }
                }
            } else {
                if (this.evalName.equals("this")) {
                    throw new UtilEvalError("Can't assign to 'this'.");
                }
                lHSStaticField = new LHS(this.namespace, this.evalName, false);
            }
        }
        return lHSStaticField;
    }

    public Object toObject(CallStack callStack, Interpreter interpreter) throws UtilEvalError {
        return toObject(callStack, interpreter, false);
    }

    public synchronized Object toObject(CallStack callStack, Interpreter interpreter, boolean z) throws UtilEvalError {
        Object objConsumeNextObjectField;
        reset();
        objConsumeNextObjectField = null;
        while (this.evalName != null) {
            objConsumeNextObjectField = consumeNextObjectField(callStack, interpreter, z, false);
        }
        if (objConsumeNextObjectField == null) {
            throw new InterpreterError("null value in toObject()");
        }
        return objConsumeNextObjectField;
    }

    public String toString() {
        return this.value;
    }
}
