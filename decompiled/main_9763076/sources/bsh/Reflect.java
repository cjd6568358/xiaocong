package bsh;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Vector;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class Reflect {
    Reflect() {
    }

    private static String accessorName(String str, String str2) {
        return new StringBuffer().append(str).append(String.valueOf(Character.toUpperCase(str2.charAt(0)))).append(str2.substring(1)).toString();
    }

    private static Vector addCandidates(Method[] methodArr, String str, int i, boolean z, Vector vector) {
        for (Method method : methodArr) {
            if (method.getName().equals(str) && method.getParameterTypes().length == i && (!z || isPublic(method))) {
                vector.add(method);
            }
        }
        return vector;
    }

    private static ReflectError cantFindConstructor(Class cls, Class[] clsArr) {
        return clsArr.length == 0 ? new ReflectError(new StringBuffer().append("Can't find default constructor for: ").append(cls).toString()) : new ReflectError(new StringBuffer().append("Can't find constructor: ").append(StringUtil.methodString(cls.getName(), clsArr)).append(" in class: ").append(cls.getName()).toString());
    }

    private static void checkFoundStaticMethod(Method method, boolean z, Class cls) throws UtilEvalError {
        if (method != null && z && !isStatic(method)) {
            throw new UtilEvalError(new StringBuffer().append("Cannot reach instance method: ").append(StringUtil.methodString(method.getName(), method.getParameterTypes())).append(" from static context: ").append(cls.getName()).toString());
        }
    }

    static Object constructObject(Class cls, Object[] objArr) throws ReflectError, InvocationTargetException {
        if (cls.isInterface()) {
            throw new ReflectError(new StringBuffer().append("Can't create instance of an interface: ").append(cls).toString());
        }
        Class[] types = Types.getTypes(objArr);
        Constructor<?>[] declaredConstructors = Capabilities.haveAccessibility() ? cls.getDeclaredConstructors() : cls.getConstructors();
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("Looking for most specific constructor: ").append(cls).toString());
        }
        Constructor constructorFindMostSpecificConstructor = findMostSpecificConstructor(types, declaredConstructors);
        if (constructorFindMostSpecificConstructor == null) {
            throw cantFindConstructor(cls, types);
        }
        if (!isPublic(constructorFindMostSpecificConstructor)) {
            try {
                ReflectManager.RMSetAccessible(constructorFindMostSpecificConstructor);
            } catch (UtilEvalError e) {
            }
        }
        try {
            Object objNewInstance = constructorFindMostSpecificConstructor.newInstance(Primitive.unwrap(objArr));
            if (objNewInstance == null) {
                throw new ReflectError("Couldn't construct the object");
            }
            return objNewInstance;
        } catch (IllegalAccessException e2) {
            throw new ReflectError("We don't have permission to create an instance.Use setAccessibility(true) to enable access.");
        } catch (IllegalArgumentException e3) {
            throw new ReflectError("The number of arguments was wrong");
        } catch (InstantiationException e4) {
            throw new ReflectError(new StringBuffer().append("The class ").append(cls).append(" is abstract ").toString());
        }
    }

    private static Field findAccessibleField(Class cls, String str) throws NoSuchFieldException, UtilEvalError {
        try {
            Field field = cls.getField(str);
            ReflectManager.RMSetAccessible(field);
            return field;
        } catch (NoSuchFieldException e) {
            while (cls != null) {
                try {
                    Field declaredField = cls.getDeclaredField(str);
                    ReflectManager.RMSetAccessible(declaredField);
                    return declaredField;
                } catch (NoSuchFieldException e2) {
                    cls = cls.getSuperclass();
                }
            }
            throw new NoSuchFieldException(str);
        }
    }

    static Constructor findMostSpecificConstructor(Class[] clsArr, Constructor[] constructorArr) {
        int iFindMostSpecificConstructorIndex = findMostSpecificConstructorIndex(clsArr, constructorArr);
        if (iFindMostSpecificConstructorIndex == -1) {
            return null;
        }
        return constructorArr[iFindMostSpecificConstructorIndex];
    }

    static int findMostSpecificConstructorIndex(Class[] clsArr, Constructor[] constructorArr) {
        Class[][] clsArr2 = new Class[constructorArr.length][];
        for (int i = 0; i < clsArr2.length; i++) {
            clsArr2[i] = constructorArr[i].getParameterTypes();
        }
        return findMostSpecificSignature(clsArr, clsArr2);
    }

    static Method findMostSpecificMethod(Class[] clsArr, Method[] methodArr) {
        Class[][] clsArr2 = new Class[methodArr.length][];
        for (int i = 0; i < methodArr.length; i++) {
            clsArr2[i] = methodArr[i].getParameterTypes();
        }
        int iFindMostSpecificSignature = findMostSpecificSignature(clsArr, clsArr2);
        if (iFindMostSpecificSignature == -1) {
            return null;
        }
        return methodArr[iFindMostSpecificSignature];
    }

    static int findMostSpecificSignature(Class[] clsArr, Class[][] clsArr2) {
        for (int i = 1; i <= 4; i++) {
            Class[] clsArr3 = null;
            int i2 = -1;
            for (int i3 = 0; i3 < clsArr2.length; i3++) {
                Class[] clsArr4 = clsArr2[i3];
                if (Types.isSignatureAssignable(clsArr, clsArr4, i) && (clsArr3 == null || Types.isSignatureAssignable(clsArr4, clsArr3, 1))) {
                    i2 = i3;
                    clsArr3 = clsArr4;
                }
            }
            if (clsArr3 != null) {
                return i2;
            }
        }
        return -1;
    }

    private static Method findOverloadedMethod(Class cls, String str, Class[] clsArr, boolean z) {
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("Searching for method: ").append(StringUtil.methodString(str, clsArr)).append(" in '").append(cls.getName()).append("'").toString());
        }
        Method[] candidateMethods = getCandidateMethods(cls, str, clsArr.length, z);
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("Looking for most specific method: ").append(str).toString());
        }
        return findMostSpecificMethod(clsArr, candidateMethods);
    }

    private static Vector gatherMethodsRecursive(Class cls, String str, int i, boolean z, Vector vector) {
        if (vector == null) {
            vector = new Vector();
        }
        if (!z) {
            addCandidates(cls.getDeclaredMethods(), str, i, z, vector);
        } else if (isPublic(cls)) {
            addCandidates(cls.getMethods(), str, i, z, vector);
        }
        for (Class<?> cls2 : cls.getInterfaces()) {
            gatherMethodsRecursive(cls2, str, i, z, vector);
        }
        Class superclass = cls.getSuperclass();
        if (superclass != null) {
            gatherMethodsRecursive(superclass, str, i, z, vector);
        }
        return vector;
    }

    public static Class getArrayBaseType(Class cls) throws ReflectError {
        if (cls.isArray()) {
            return cls.getComponentType();
        }
        throw new ReflectError("The class is not an array.");
    }

    public static int getArrayDimensions(Class cls) {
        if (cls.isArray()) {
            return cls.getName().lastIndexOf(91) + 1;
        }
        return 0;
    }

    static Method[] getCandidateMethods(Class cls, String str, int i, boolean z) {
        Vector vectorGatherMethodsRecursive = gatherMethodsRecursive(cls, str, i, z, null);
        Method[] methodArr = new Method[vectorGatherMethodsRecursive.size()];
        vectorGatherMethodsRecursive.copyInto(methodArr);
        return methodArr;
    }

    private static Object getFieldValue(Class cls, Object obj, String str, boolean z) throws ReflectError, UtilEvalError {
        try {
            Field fieldResolveExpectedJavaField = resolveExpectedJavaField(cls, str, z);
            return Primitive.wrap(fieldResolveExpectedJavaField.get(obj), fieldResolveExpectedJavaField.getType());
        } catch (IllegalAccessException e) {
            throw new ReflectError(new StringBuffer().append("Can't access field: ").append(str).toString());
        } catch (NullPointerException e2) {
            throw new ReflectError(new StringBuffer().append("???").append(str).append(" is not a static field.").toString());
        }
    }

    public static Object getIndex(Object obj, int i) throws ReflectError, UtilTargetError {
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append("getIndex: ").append(obj).append(", index=").append(i).toString());
        }
        try {
            return Primitive.wrap(Array.get(obj, i), obj.getClass().getComponentType());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new UtilTargetError(e);
        } catch (Exception e2) {
            throw new ReflectError(new StringBuffer().append("Array access:").append(e2).toString());
        }
    }

    static LHS getLHSObjectField(Object obj, String str) throws ReflectError, UtilEvalError {
        if (obj instanceof This) {
            return new LHS(((This) obj).namespace, str, false);
        }
        try {
            return new LHS(obj, resolveExpectedJavaField(obj.getClass(), str, false));
        } catch (ReflectError e) {
            if (hasObjectPropertySetter(obj.getClass(), str)) {
                return new LHS(obj, str);
            }
            throw e;
        }
    }

    static LHS getLHSStaticField(Class cls, String str) throws ReflectError, UtilEvalError {
        return new LHS(resolveExpectedJavaField(cls, str, true));
    }

    public static Object getObjectFieldValue(Object obj, String str) throws ReflectError, UtilEvalError {
        if (obj instanceof This) {
            return ((This) obj).namespace.getVariable(str);
        }
        try {
            return getFieldValue(obj.getClass(), obj, str, false);
        } catch (ReflectError e) {
            if (hasObjectPropertyGetter(obj.getClass(), str)) {
                return getObjectProperty(obj, str);
            }
            throw e;
        }
    }

    public static Object getObjectProperty(Object obj, String str) throws ReflectError, UtilEvalError {
        Exception exc;
        Method methodResolveExpectedJavaMethod;
        Method method;
        Method methodResolveExpectedJavaMethod2;
        Exception exc2 = null;
        Object[] objArr = new Object[0];
        Interpreter.debug("property access: ");
        try {
            methodResolveExpectedJavaMethod = resolveExpectedJavaMethod(null, obj.getClass(), obj, accessorName("get", str), objArr, false);
            exc = null;
        } catch (Exception e) {
            exc = e;
            methodResolveExpectedJavaMethod = null;
        }
        if (methodResolveExpectedJavaMethod == null) {
            try {
                methodResolveExpectedJavaMethod2 = resolveExpectedJavaMethod(null, obj.getClass(), obj, accessorName("is", str), objArr, false);
                try {
                    if (methodResolveExpectedJavaMethod2.getReturnType() != Boolean.TYPE) {
                        methodResolveExpectedJavaMethod2 = null;
                    }
                } catch (Exception e2) {
                    method = methodResolveExpectedJavaMethod2;
                    e = e2;
                    exc2 = e;
                    methodResolveExpectedJavaMethod2 = method;
                }
            } catch (Exception e3) {
                e = e3;
                method = methodResolveExpectedJavaMethod;
            }
        } else {
            methodResolveExpectedJavaMethod2 = methodResolveExpectedJavaMethod;
        }
        if (methodResolveExpectedJavaMethod2 == null) {
            throw new ReflectError(new StringBuffer().append("Error in property getter: ").append(exc).append(exc2 != null ? new StringBuffer().append(" : ").append(exc2).toString() : Constants.MAIN_VERSION_TAG).toString());
        }
        try {
            return invokeMethod(methodResolveExpectedJavaMethod2, obj, objArr);
        } catch (InvocationTargetException e4) {
            throw new UtilEvalError(new StringBuffer().append("Property accessor threw exception: ").append(e4.getTargetException()).toString());
        }
    }

    public static Object getStaticFieldValue(Class cls, String str) throws ReflectError, UtilEvalError {
        return getFieldValue(cls, null, str, true);
    }

    public static boolean hasObjectPropertyGetter(Class cls, String str) {
        try {
            cls.getMethod(accessorName("get", str), new Class[0]);
            return true;
        } catch (NoSuchMethodException e) {
            try {
                return cls.getMethod(accessorName("is", str), new Class[0]).getReturnType() == Boolean.TYPE;
            } catch (NoSuchMethodException e2) {
                return false;
            }
        }
    }

    public static boolean hasObjectPropertySetter(Class cls, String str) {
        String strAccessorName = accessorName("set", str);
        for (Method method : cls.getMethods()) {
            if (method.getName().equals(strAccessorName)) {
                return true;
            }
        }
        return false;
    }

    public static Object invokeCompiledCommand(Class cls, Object[] objArr, Interpreter interpreter, CallStack callStack) throws UtilEvalError {
        Object[] objArr2 = new Object[objArr.length + 2];
        objArr2[0] = interpreter;
        objArr2[1] = callStack;
        System.arraycopy(objArr, 0, objArr2, 2, objArr.length);
        try {
            return invokeStaticMethod(interpreter.getClassManager(), cls, "invoke", objArr2);
        } catch (ReflectError e) {
            throw new UtilEvalError(new StringBuffer().append("Error invoking compiled command: ").append(e).toString());
        } catch (InvocationTargetException e2) {
            throw new UtilEvalError(new StringBuffer().append("Error in compiled command: ").append(e2.getTargetException()).toString());
        }
    }

    static Object invokeMethod(Method method, Object obj, Object[] objArr) throws ReflectError, InvocationTargetException {
        if (objArr == null) {
            objArr = new Object[0];
        }
        logInvokeMethod("Invoking method (entry): ", method, objArr);
        Object[] objArr2 = new Object[objArr.length];
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < objArr.length; i++) {
            try {
                objArr2[i] = Types.castObject(objArr[i], parameterTypes[i], 1);
            } catch (UtilEvalError e) {
                throw new InterpreterError(new StringBuffer().append("illegal argument type in method invocation: ").append(e).toString());
            }
        }
        Object[] objArrUnwrap = Primitive.unwrap(objArr2);
        logInvokeMethod("Invoking method (after massaging values): ", method, objArrUnwrap);
        try {
            Object objInvoke = method.invoke(obj, objArrUnwrap);
            if (objInvoke == null) {
                objInvoke = Primitive.NULL;
            }
            return Primitive.wrap(objInvoke, method.getReturnType());
        } catch (IllegalAccessException e2) {
            throw new ReflectError(new StringBuffer().append("Cannot access method ").append(StringUtil.methodString(method.getName(), method.getParameterTypes())).append(" in '").append(method.getDeclaringClass()).append("' :").append(e2).toString());
        }
    }

    public static Object invokeObjectMethod(Object obj, String str, Object[] objArr, Interpreter interpreter, CallStack callStack, SimpleNode simpleNode) throws ReflectError, EvalError, InvocationTargetException {
        BshClassManager classManager;
        if ((obj instanceof This) && !This.isExposedThisMethod(str)) {
            return ((This) obj).invokeMethod(str, objArr, interpreter, callStack, simpleNode, false);
        }
        if (interpreter == null) {
            classManager = null;
        } else {
            try {
                classManager = interpreter.getClassManager();
            } catch (UtilEvalError e) {
                throw e.toEvalError(simpleNode, callStack);
            }
        }
        return invokeMethod(resolveExpectedJavaMethod(classManager, obj.getClass(), obj, str, objArr, false), obj, objArr);
    }

    public static Object invokeStaticMethod(BshClassManager bshClassManager, Class cls, String str, Object[] objArr) throws ReflectError, UtilEvalError, InvocationTargetException {
        Interpreter.debug("invoke static Method");
        return invokeMethod(resolveExpectedJavaMethod(bshClassManager, cls, null, str, objArr, true), null, objArr);
    }

    private static boolean isPublic(Class cls) {
        return Modifier.isPublic(cls.getModifiers());
    }

    private static boolean isPublic(Constructor constructor) {
        return Modifier.isPublic(constructor.getModifiers());
    }

    private static boolean isPublic(Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    private static boolean isStatic(Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    private static void logInvokeMethod(String str, Method method, Object[] objArr) {
        if (Interpreter.DEBUG) {
            Interpreter.debug(new StringBuffer().append(str).append(method).append(" with args:").toString());
            for (int i = 0; i < objArr.length; i++) {
                Interpreter.debug(new StringBuffer().append("args[").append(i).append("] = ").append(objArr[i]).append(" type = ").append(objArr[i].getClass()).toString());
            }
        }
    }

    public static String normalizeClassName(Class cls) {
        if (!cls.isArray()) {
            return cls.getName();
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new StringBuffer().append(getArrayBaseType(cls).getName()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).toString());
            for (int i = 0; i < getArrayDimensions(cls); i++) {
                stringBuffer.append("[]");
            }
        } catch (ReflectError e) {
        }
        return stringBuffer.toString();
    }

    protected static Field resolveExpectedJavaField(Class cls, String str, boolean z) throws ReflectError, UtilEvalError {
        try {
            Field fieldFindAccessibleField = Capabilities.haveAccessibility() ? findAccessibleField(cls, str) : cls.getField(str);
            if (!z || Modifier.isStatic(fieldFindAccessibleField.getModifiers())) {
                return fieldFindAccessibleField;
            }
            throw new UtilEvalError(new StringBuffer().append("Can't reach instance field: ").append(str).append(" from static context: ").append(cls.getName()).toString());
        } catch (NoSuchFieldException e) {
            throw new ReflectError(new StringBuffer().append("No such field: ").append(str).toString());
        } catch (SecurityException e2) {
            throw new UtilTargetError(new StringBuffer().append("Security Exception while searching fields of: ").append(cls).toString(), e2);
        }
    }

    protected static Method resolveExpectedJavaMethod(BshClassManager bshClassManager, Class cls, Object obj, String str, Object[] objArr, boolean z) throws ReflectError, UtilEvalError {
        if (obj == Primitive.NULL) {
            throw new UtilTargetError(new NullPointerException(new StringBuffer().append("Attempt to invoke method ").append(str).append(" on null value").toString()));
        }
        Class[] types = Types.getTypes(objArr);
        Method methodResolveJavaMethod = resolveJavaMethod(bshClassManager, cls, str, types, z);
        if (methodResolveJavaMethod == null) {
            throw new ReflectError(new StringBuffer().append(z ? "Static method " : "Method ").append(StringUtil.methodString(str, types)).append(" not found in class'").append(cls.getName()).append("'").toString());
        }
        return methodResolveJavaMethod;
    }

    protected static Field resolveJavaField(Class cls, String str, boolean z) throws UtilEvalError {
        try {
            return resolveExpectedJavaField(cls, str, z);
        } catch (ReflectError e) {
            return null;
        }
    }

    protected static Method resolveJavaMethod(BshClassManager bshClassManager, Class cls, String str, Class[] clsArr, boolean z) throws UtilEvalError {
        if (cls == null) {
            throw new InterpreterError("null class");
        }
        Method resolvedMethod = null;
        if (bshClassManager == null) {
            Interpreter.debug("resolveJavaMethod UNOPTIMIZED lookup");
        } else {
            resolvedMethod = bshClassManager.getResolvedMethod(cls, str, clsArr, z);
        }
        if (resolvedMethod != null) {
            return resolvedMethod;
        }
        boolean z2 = !Capabilities.haveAccessibility();
        try {
            Method methodFindOverloadedMethod = findOverloadedMethod(cls, str, clsArr, z2);
            checkFoundStaticMethod(methodFindOverloadedMethod, z, cls);
            if (methodFindOverloadedMethod != null && !z2) {
                try {
                    ReflectManager.RMSetAccessible(methodFindOverloadedMethod);
                } catch (UtilEvalError e) {
                }
            }
            if (methodFindOverloadedMethod != null && bshClassManager != null) {
                bshClassManager.cacheResolvedMethod(cls, clsArr, methodFindOverloadedMethod);
            }
            return methodFindOverloadedMethod;
        } catch (SecurityException e2) {
            throw new UtilTargetError(new StringBuffer().append("Security Exception while searching methods of: ").append(cls).toString(), e2);
        }
    }

    public static void setIndex(Object obj, int i, Object obj2) throws ReflectError, UtilTargetError {
        try {
            Array.set(obj, i, Primitive.unwrap(obj2));
        } catch (ArrayStoreException e) {
            throw new UtilTargetError(e);
        } catch (IllegalArgumentException e2) {
            throw new UtilTargetError(new ArrayStoreException(e2.toString()));
        } catch (Exception e3) {
            throw new ReflectError(new StringBuffer().append("Array access:").append(e3).toString());
        }
    }

    public static void setObjectProperty(Object obj, String str, Object obj2) throws ReflectError, UtilEvalError {
        String strAccessorName = accessorName("set", str);
        Object[] objArr = {obj2};
        Interpreter.debug("property access: ");
        try {
            invokeMethod(resolveExpectedJavaMethod(null, obj.getClass(), obj, strAccessorName, objArr, false), obj, objArr);
        } catch (InvocationTargetException e) {
            throw new UtilEvalError(new StringBuffer().append("Property accessor threw exception: ").append(e.getTargetException()).toString());
        }
    }
}
