package com.luajava;

import com.tencent.android.tpush.SettingsContentProvider;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class LuaJavaAPI {
    static HashMap<String, Method[]> methodsMap = new HashMap<>();
    static HashMap<String, Method[]> methodCache = new HashMap<>();

    private LuaJavaAPI() {
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0012  */
    public static int objectIndex(int i, Object obj, String str, int i2) throws LuaException {
        int iCheckField;
        int i3 = 3;
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            if (i2 == 0) {
                if (checkMethod(existingState, obj, str) != 0) {
                    i3 = 2;
                } else if ((i2 == 0 && i2 != 1 && i2 != 5) || (iCheckField = checkField(existingState, obj, str)) == 0) {
                    if ((i2 == 0 || i2 == 4) && javaGetter(existingState, obj, str) != 0) {
                        i3 = 4;
                    } else if ((i2 != 0 && i2 != 3) || checkClass(existingState, obj, str) == 0) {
                        i3 = 0;
                    }
                }
            } else {
                i3 = i2 == 0 ? iCheckField : iCheckField;
            }
            throw th;
        }
        return i3;
    }

    public static int callMethod(int i, Object obj, String str) throws LuaException {
        boolean z;
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            Method[] methodArr = methodCache.get(str);
            int top = existingState.getTop();
            Object[] objArr = new Object[top];
            Method method = null;
            for (int i2 = 0; i2 < methodArr.length; i2++) {
                Class<?>[] parameterTypes = methodArr[i2].getParameterTypes();
                if (parameterTypes.length == top) {
                    for (int i3 = 0; i3 < parameterTypes.length; i3++) {
                        try {
                            objArr[i3] = compareTypes(existingState, parameterTypes[i3], i3 + 1);
                        } catch (Exception e) {
                            z = false;
                        }
                    }
                    z = true;
                    if (z) {
                        method = methodArr[i2];
                        break;
                    }
                }
            }
            if (method == null) {
                StringBuilder sb = new StringBuilder();
                for (Method method2 : methodArr) {
                    sb.append(method2.toString());
                    sb.append("\n");
                }
                throw new LuaException("Invalid method call. Invalid Parameters.\n" + sb.toString());
            }
            try {
                if (!Modifier.isPublic(method.getModifiers())) {
                    method.setAccessible(true);
                }
                Object objInvoke = method.invoke(obj, objArr);
                if (objInvoke == null && method.getReturnType().equals(Void.TYPE)) {
                    return 0;
                }
                existingState.pushObjectValue(objInvoke);
                return 1;
            } catch (Exception e2) {
                throw new LuaException(e2);
            }
        }
    }

    public static int objectNewIndex(int i, Object obj, String str) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            if (setFieldValue(existingState, obj, str) != 0) {
                return 1;
            }
            return javaSetter(existingState, obj, str) != 0 ? 1 : 0;
        }
    }

    public static int setFieldValue(LuaState luaState, Object obj, String str) throws LuaException {
        Class<?> cls;
        boolean z;
        synchronized (luaState) {
            if (obj == null) {
                return 0;
            }
            if (obj instanceof Class) {
                cls = (Class) obj;
                z = true;
            } else {
                cls = obj.getClass();
                z = false;
            }
            try {
                Field field = cls.getField(str);
                if (field == null) {
                    return 0;
                }
                if (z && !Modifier.isStatic(field.getModifiers())) {
                    return 0;
                }
                Class<?> type = field.getType();
                try {
                    if (!Modifier.isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    field.set(obj, compareTypes(luaState, type, 3));
                } catch (LuaException e) {
                    argError(luaState, str, 3, type);
                } catch (Exception e2) {
                    throw new LuaException(e2);
                }
                return 1;
            } catch (NoSuchFieldException e3) {
                return 0;
            }
        }
    }

    private static String argError(LuaState luaState, String str, int i, Class cls) throws LuaException {
        throw new LuaException("bad argument to '" + str + "' (" + cls.getName() + " expected, got " + typeName(luaState, 3) + " value)");
    }

    private static String typeName(LuaState luaState, int i) throws LuaException {
        if (luaState.isObject(i)) {
            return luaState.getObjectFromUserdata(i).getClass().getName();
        }
        switch (luaState.type(i)) {
            case 1:
                return SettingsContentProvider.BOOLEAN_TYPE;
            case 2:
            case 7:
                return "userdata";
            case 3:
                return "number";
            case 4:
                return SettingsContentProvider.STRING_TYPE;
            case 5:
                return "table";
            case 6:
                return "function";
            case 8:
                return "thread";
            default:
                return "unkown";
        }
    }

    public static int setArrayValue(int i, Object obj, int i2) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            Class<?> componentType = obj.getClass().getComponentType();
            if (componentType == null) {
                throw new LuaException(String.valueOf(obj.toString()) + " is not a array");
            }
            try {
                Array.set(obj, i2, compareTypes(existingState, componentType, 3));
            } catch (LuaException e) {
                argError(existingState, String.valueOf(obj.getClass().getName()) + " [" + i2 + "]", 3, componentType);
            } catch (Exception e2) {
                throw new LuaException("can not set array value: " + e2.getMessage());
            }
        }
        return 0;
    }

    public static int getArrayValue(int i, Object obj, int i2) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            try {
                existingState.pushObjectValue(Array.get(obj, i2));
            } catch (Exception e) {
                throw new LuaException("can not get array value: " + e.getMessage());
            }
        }
        return 1;
    }

    public static int asTable(int i, Object obj) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            try {
                existingState.newTable();
                if (obj.getClass().isArray()) {
                    int length = Array.getLength(obj);
                    for (int i2 = 0; i2 <= length - 1; i2++) {
                        existingState.pushObjectValue(Array.get(obj, i2));
                        existingState.rawSetI(-2, i2 + 1);
                    }
                } else if (obj instanceof Collection) {
                    Iterator it = ((Collection) obj).iterator();
                    int i3 = 1;
                    while (it.hasNext()) {
                        existingState.pushObjectValue(it.next());
                        existingState.rawSetI(-2, i3);
                        i3++;
                    }
                } else if (obj instanceof Map) {
                    for (Map.Entry entry : ((Map) obj).entrySet()) {
                        existingState.pushObjectValue(entry.getKey());
                        existingState.pushObjectValue(entry.getValue());
                        existingState.setTable(-3);
                    }
                }
                existingState.pushValue(-1);
            } catch (Exception e) {
                throw new LuaException("can not astable: " + e.getMessage());
            }
        }
        return 1;
    }

    public static int newArray(int i, Class<?> cls, int i2) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            try {
                existingState.pushJavaObject(Array.newInstance(cls, i2));
            } catch (Exception e) {
                throw new LuaException("can not create a array: " + e.getMessage());
            }
        }
        return 1;
    }

    public static int newArray(int i, Class<?> cls) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            try {
                int top = existingState.getTop();
                int[] iArr = new int[top - 1];
                for (int i2 = 0; i2 < top - 1; i2++) {
                    iArr[i2] = (int) existingState.toInteger(i2 + 2);
                }
                existingState.pushJavaObject(Array.newInstance(cls, iArr));
            } catch (Exception e) {
                throw new LuaException("can not create a array: " + e.getMessage());
            }
        }
        return 1;
    }

    public static Class javaBindClass(String str) throws LuaException {
        try {
            return Class.forName(str);
        } catch (Exception e) {
            if (str.equals(SettingsContentProvider.BOOLEAN_TYPE)) {
                return Boolean.TYPE;
            }
            if (str.equals("byte")) {
                return Byte.TYPE;
            }
            if (str.equals("char")) {
                return Character.TYPE;
            }
            if (str.equals("short")) {
                return Short.TYPE;
            }
            if (str.equals("int")) {
                return Integer.TYPE;
            }
            if (str.equals(SettingsContentProvider.LONG_TYPE)) {
                return Long.TYPE;
            }
            if (str.equals(SettingsContentProvider.FLOAT_TYPE)) {
                return Float.TYPE;
            }
            if (str.equals("double")) {
                return Double.TYPE;
            }
            throw new LuaException("Class not found: " + str);
        }
    }

    public static int javaNewInstance(int i, String str) throws LuaException {
        int primitive;
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            Class clsJavaBindClass = javaBindClass(str);
            primitive = clsJavaBindClass.isPrimitive() ? toPrimitive(existingState, clsJavaBindClass, -1) : getObjInstance(existingState, clsJavaBindClass);
        }
        return primitive;
    }

    public static int javaNew(int i, Class cls) throws LuaException {
        int primitive;
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            primitive = cls.isPrimitive() ? toPrimitive(existingState, cls, -1) : getObjInstance(existingState, cls);
        }
        return primitive;
    }

    public static int javaCreate(int i, Class cls) throws LuaException {
        int iCreateMap;
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            if (cls.isInterface()) {
                iCreateMap = createProxyObject(existingState, cls);
            } else if (cls.getSuperclass() == AbstractMap.class || cls.getSuperclass() == HashMap.class) {
                iCreateMap = createMap(existingState, cls);
            } else {
                iCreateMap = createArray(existingState, cls);
            }
        }
        return iCreateMap;
    }

    public static int createProxy(int i, String str) throws LuaException {
        int iCreateProxyObject;
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            iCreateProxyObject = createProxyObject(existingState, str);
        }
        return iCreateProxyObject;
    }

    public static int createArray(int i, String str) throws LuaException {
        int iCreateArray;
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            iCreateArray = createArray(existingState, javaBindClass(str));
        }
        return iCreateArray;
    }

    public static int javaLoadLib(int i, String str, String str2) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            try {
                try {
                    Object objInvoke = Class.forName(str).getMethod(str2, LuaState.class).invoke(null, existingState);
                    if (objInvoke == null || !(objInvoke instanceof Integer)) {
                        return 0;
                    }
                    return ((Integer) objInvoke).intValue();
                } catch (Exception e) {
                    throw new LuaException("Error on calling method. Library could not be loaded. " + e.getMessage());
                }
            } catch (ClassNotFoundException e2) {
                throw new LuaException(e2);
            }
        }
    }

    public static int javaToString(int i, Object obj) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            try {
                if (obj == null) {
                    existingState.pushString("null");
                } else {
                    existingState.pushString(obj.toString());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return 1;
    }

    public static int javaEquals(int i, Object obj, Object obj2) throws LuaException {
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            existingState.pushBoolean(obj.equals(obj2));
        }
        return 1;
    }

    public static int javaObjectLength(int i, Object obj) throws LuaException {
        int length;
        LuaState existingState = LuaStateFactory.getExistingState(i);
        synchronized (existingState) {
            try {
                if (obj instanceof CharSequence) {
                    length = ((CharSequence) obj).length();
                } else if (obj instanceof Collection) {
                    length = ((Collection) obj).size();
                } else if (obj instanceof Map) {
                    length = ((Map) obj).size();
                } else {
                    length = Array.getLength(obj);
                }
                existingState.pushInteger(length);
            } catch (Exception e) {
                throw new LuaException(e);
            }
        }
        return 1;
    }

    private static int getObjInstance(LuaState luaState, Class cls) throws LuaException {
        boolean z;
        synchronized (luaState) {
            int top = luaState.getTop();
            Object[] objArr = new Object[top - 1];
            Constructor<?>[] constructors = cls.getConstructors();
            Constructor<?> constructor = null;
            for (int i = 0; i < constructors.length; i++) {
                Class<?>[] parameterTypes = constructors[i].getParameterTypes();
                if (parameterTypes.length == top - 1) {
                    for (int i2 = 0; i2 < parameterTypes.length; i2++) {
                        try {
                            objArr[i2] = compareTypes(luaState, parameterTypes[i2], i2 + 2);
                        } catch (Exception e) {
                            z = false;
                        }
                    }
                    z = true;
                    if (z) {
                        constructor = constructors[i];
                        break;
                    }
                }
            }
            if (constructor == null) {
                StringBuilder sb = new StringBuilder();
                for (Constructor<?> constructor2 : constructors) {
                    sb.append(constructor2.toString());
                    sb.append("\n");
                }
                throw new LuaException("Invalid constructor method call. Invalid Parameters.\n" + sb.toString());
            }
            try {
                Object objNewInstance = constructor.newInstance(objArr);
                if (objNewInstance == null) {
                    throw new LuaException("Couldn't instantiate java Object");
                }
                luaState.pushJavaObject(objNewInstance);
            } catch (Exception e2) {
                throw new LuaException(e2);
            }
        }
        return 1;
    }

    public static int checkField(LuaState luaState, Object obj, String str) throws LuaException {
        Class<?> cls;
        boolean z;
        synchronized (luaState) {
            if (obj instanceof Class) {
                cls = (Class) obj;
                z = true;
            } else {
                cls = obj.getClass();
                z = false;
            }
            try {
                Field field = cls.getField(str);
                if (field == null) {
                    return 0;
                }
                if (z && !Modifier.isStatic(field.getModifiers())) {
                    return 0;
                }
                try {
                    if (!Modifier.isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }
                    luaState.pushObjectValue(field.get(obj));
                    return Modifier.isFinal(field.getModifiers()) ? 5 : 1;
                } catch (Exception e) {
                    throw new LuaException(e);
                }
            } catch (NoSuchFieldException e2) {
                return 0;
            }
        }
    }

    public static int checkMethod(LuaState luaState, Object obj, String str) throws LuaException {
        Class<?> cls;
        boolean z;
        synchronized (luaState) {
            if (obj instanceof Class) {
                cls = (Class) obj;
                z = true;
            } else {
                cls = obj.getClass();
                z = false;
            }
            String name = cls.getName();
            String string = luaState.toString(-1);
            Method[] methodArr = methodCache.get(string);
            if (methodArr == null) {
                Method[] methods = methodsMap.get(name);
                if (methods == null) {
                    methods = cls.getMethods();
                    methodsMap.put(name, methods);
                }
                Method[] methodArr2 = methods;
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < methodArr2.length; i++) {
                    if (methodArr2[i].getName().equals(str) && (!z || Modifier.isStatic(methodArr2[i].getModifiers()))) {
                        arrayList.add(methodArr2[i]);
                    }
                }
                if (arrayList.isEmpty() && z) {
                    Method[] methods2 = cls.getClass().getMethods();
                    for (int i2 = 0; i2 < methods2.length; i2++) {
                        if (methods2[i2].getName().equals(str)) {
                            arrayList.add(methods2[i2]);
                        }
                    }
                }
                methodArr = new Method[arrayList.size()];
                arrayList.toArray(methodArr);
                methodCache.put(string, methodArr);
            }
            return methodArr.length == 0 ? 0 : 2;
        }
    }

    public static int checkClass(LuaState luaState, Object obj, String str) throws LuaException {
        synchronized (luaState) {
            if (!(obj instanceof Class)) {
                return 0;
            }
            Class<?>[] classes = ((Class) obj).getClasses();
            for (int i = 0; i < classes.length; i++) {
                if (classes[i].getSimpleName().equals(str)) {
                    luaState.pushJavaObject(classes[i]);
                    return 3;
                }
            }
            return 0;
        }
    }

    public static int javaGetter(LuaState luaState, Object obj, String str) throws LuaException {
        Class<?> cls;
        boolean z;
        synchronized (luaState) {
            if (obj instanceof Map) {
                luaState.pushObjectValue(((Map) obj).get(str));
                return 1;
            }
            if (obj instanceof Class) {
                cls = (Class) obj;
                z = true;
            } else {
                cls = obj.getClass();
                z = false;
            }
            try {
                Method method = cls.getMethod("get" + str, new Class[0]);
                if (z && !Modifier.isStatic(method.getModifiers())) {
                    return 0;
                }
                try {
                    luaState.pushObjectValue(method.invoke(obj, new Object[0]));
                    return 1;
                } catch (Exception e) {
                    throw new LuaException(e);
                }
            } catch (NoSuchMethodException e2) {
                return 0;
            }
        }
    }

    public static int javaSetter(LuaState luaState, Object obj, String str) throws LuaException {
        Class<?> cls;
        boolean z = true;
        synchronized (luaState) {
            if (obj instanceof Map) {
                ((Map) obj).put(str, luaState.toJavaObject(2));
                return 1;
            }
            if (obj instanceof Class) {
                cls = (Class) obj;
            } else {
                cls = obj.getClass();
                z = false;
            }
            String name = cls.getName();
            Method[] methods = methodsMap.get(name);
            if (methods == null) {
                methods = cls.getMethods();
                methodsMap.put(name, methods);
            }
            if (str.length() > 2 && str.substring(0, 2).equals("on") && luaState.type(-1) == 6) {
                return javaSetListener(luaState, obj, str, methods, z);
            }
            return javaSetMethod(luaState, obj, str, methods, z);
        }
    }

    private static int javaSetListener(LuaState luaState, Object obj, String str, Method[] methodArr, boolean z) throws LuaException {
        synchronized (luaState) {
            String str2 = "setOn" + str.substring(2) + "Listener";
            for (Method method : methodArr) {
                if (method.getName().equals(str2) && (!z || Modifier.isStatic(method.getModifiers()))) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1 && parameterTypes[0].isInterface()) {
                        luaState.newTable();
                        luaState.pushValue(-2);
                        luaState.setField(-2, str);
                        try {
                            method.invoke(obj, luaState.getLuaObject(-1).createProxy(parameterTypes[0]));
                            return 1;
                        } catch (Exception e) {
                            throw new LuaException(e);
                        }
                    }
                }
            }
            return 0;
        }
    }

    private static int javaSetMethod(LuaState luaState, Object obj, String str, Method[] methodArr, boolean z) throws LuaException {
        int i = 0;
        synchronized (luaState) {
            String str2 = "set" + str;
            StringBuilder sb = new StringBuilder();
            for (Method method : methodArr) {
                if (method.getName().equals(str2) && (!z || Modifier.isStatic(method.getModifiers()))) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1) {
                        try {
                            try {
                                method.invoke(obj, compareTypes(luaState, parameterTypes[0], -1));
                                return 1;
                            } catch (Exception e) {
                                throw new LuaException(e);
                            }
                        } catch (LuaException e2) {
                            sb.append(parameterTypes[0]);
                            sb.append("\n");
                        }
                    } else {
                        continue;
                    }
                }
            }
            if (sb.length() > 0) {
                throw new LuaException("Invalid setter " + str + ". Invalid Parameters.\n" + sb.toString() + luaState.typeName(-1));
            }
            return i;
        }
    }

    private static int createProxyObject(LuaState luaState, String str) throws LuaException {
        synchronized (luaState) {
            try {
                luaState.pushJavaObject(luaState.getLuaObject(2).createProxy(str));
            } catch (Exception e) {
                throw new LuaException(e);
            }
        }
        return 1;
    }

    private static int createProxyObject(LuaState luaState, Class cls) throws LuaException {
        synchronized (luaState) {
            luaState.pushJavaObject(createProxyObject(luaState, cls, 2));
        }
        return 1;
    }

    private static Object createProxyObject(LuaState luaState, Class cls, int i) throws LuaException {
        Object objCreateProxy;
        synchronized (luaState) {
            try {
                objCreateProxy = luaState.getLuaObject(i).createProxy(cls);
            } catch (Exception e) {
                throw new LuaException(e);
            }
        }
        return objCreateProxy;
    }

    private static int createArray(LuaState luaState, Class cls) throws LuaException {
        synchronized (luaState) {
            luaState.pushJavaObject(createArray(luaState, cls, 2));
        }
        return 1;
    }

    private static Object createArray(LuaState luaState, Class cls, int i) throws LuaException {
        Object objNewInstance;
        int i2 = 1;
        synchronized (luaState) {
            try {
                int iObjLen = luaState.objLen(i);
                objNewInstance = Array.newInstance((Class<?>) cls, iObjLen);
                if (cls == String.class) {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, luaState.toString(-1));
                        luaState.pop(1);
                        i2++;
                    }
                } else if (cls == Double.TYPE) {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, Double.valueOf(luaState.toNumber(-1)));
                        luaState.pop(1);
                        i2++;
                    }
                } else if (cls == Float.TYPE) {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, Float.valueOf((float) luaState.toNumber(-1)));
                        luaState.pop(1);
                        i2++;
                    }
                } else if (cls == Long.TYPE) {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, Long.valueOf(luaState.toInteger(-1)));
                        luaState.pop(1);
                        i2++;
                    }
                } else if (cls == Integer.TYPE) {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, Integer.valueOf((int) luaState.toInteger(-1)));
                        luaState.pop(1);
                        i2++;
                    }
                } else if (cls == Short.TYPE) {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, Short.valueOf((short) luaState.toInteger(-1)));
                        luaState.pop(1);
                        i2++;
                    }
                } else if (cls == Character.TYPE) {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, Character.valueOf((char) luaState.toInteger(-1)));
                        luaState.pop(1);
                        i2++;
                    }
                } else if (cls == Byte.TYPE) {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, Byte.valueOf((byte) luaState.toInteger(-1)));
                        luaState.pop(1);
                        i2++;
                    }
                } else {
                    while (i2 <= iObjLen) {
                        luaState.pushNumber(i2);
                        luaState.getTable(i);
                        Array.set(objNewInstance, i2 - 1, compareTypes(luaState, cls, -1));
                        luaState.pop(1);
                        i2++;
                    }
                }
            } catch (Exception e) {
                throw new LuaException(e);
            }
        }
        return objNewInstance;
    }

    private static int createMap(LuaState luaState, Class cls) throws LuaException {
        synchronized (luaState) {
            luaState.pushJavaObject(createMap(luaState, cls, 2));
        }
        return 1;
    }

    private static Object createMap(LuaState luaState, Class cls, int i) throws LuaException {
        Map map;
        synchronized (luaState) {
            try {
                map = (Map) cls.newInstance();
                luaState.pushNil();
                while (luaState.next(i) != 0) {
                    map.put(luaState.toJavaObject(-2), luaState.toJavaObject(-1));
                    luaState.pop(1);
                }
            } catch (Exception e) {
                throw new LuaException(e);
            }
        }
        return map;
    }

    private static Object compareTypes(LuaState luaState, Class cls, int i) throws LuaException {
        Object luaObject;
        Number numberConvertLuaNumber;
        boolean z = false;
        boolean z2 = true;
        switch (luaState.type(i)) {
            case 0:
                luaObject = null;
                break;
            case 1:
                if (!cls.isPrimitive() ? cls.isAssignableFrom(Boolean.class) : cls == Boolean.TYPE) {
                    z = true;
                }
                z2 = z;
                luaObject = Boolean.valueOf(luaState.toBoolean(i));
                break;
            case 2:
            default:
                throw new LuaException("Invalid Parameters.");
            case 3:
                if (luaState.isInteger(i)) {
                    numberConvertLuaNumber = LuaState.convertLuaNumber(new Long(luaState.toInteger(i)), (Class<?>) cls);
                } else {
                    numberConvertLuaNumber = LuaState.convertLuaNumber(new Double(luaState.toNumber(i)), (Class<?>) cls);
                }
                if (numberConvertLuaNumber != null) {
                    luaObject = numberConvertLuaNumber;
                } else {
                    z2 = false;
                    luaObject = numberConvertLuaNumber;
                }
                break;
            case 4:
                if (!cls.isAssignableFrom(String.class)) {
                    z2 = false;
                    luaObject = null;
                } else {
                    luaObject = luaState.toString(i);
                }
                break;
            case 5:
                if (cls.isAssignableFrom(LuaObject.class)) {
                    luaObject = luaState.getLuaObject(i);
                } else if (cls.isArray()) {
                    luaObject = createArray(luaState, cls.getComponentType(), i);
                } else if (cls.isInterface()) {
                    luaObject = createProxyObject(luaState, cls, i);
                } else if (cls.getSuperclass() == AbstractMap.class || cls.getSuperclass() == HashMap.class) {
                    luaObject = createMap(luaState, cls, i);
                } else {
                    z2 = false;
                    luaObject = null;
                }
                break;
            case 6:
                if (!cls.isAssignableFrom(LuaObject.class)) {
                    z2 = false;
                    luaObject = null;
                } else {
                    luaObject = luaState.getLuaObject(i);
                }
                break;
            case 7:
                if (luaState.isObject(i)) {
                    Object objectFromUserdata = luaState.getObjectFromUserdata(i);
                    if ((cls.isPrimitive() && Number.class.isAssignableFrom(objectFromUserdata.getClass())) || cls.isAssignableFrom(objectFromUserdata.getClass())) {
                        luaObject = objectFromUserdata;
                    } else {
                        z2 = false;
                        luaObject = null;
                    }
                } else if (!cls.isAssignableFrom(LuaObject.class)) {
                    z2 = false;
                    luaObject = null;
                } else {
                    luaObject = luaState.getLuaObject(i);
                }
                break;
        }
        if (!z2) {
            throw new LuaException("Invalid Parameter.");
        }
        return luaObject;
    }

    private static int toPrimitive(LuaState luaState, Class cls, int i) throws LuaException {
        Object objValueOf = null;
        if (cls == Character.TYPE && luaState.isString(i)) {
            String string = luaState.toString(i);
            if (string.length() == 1) {
                objValueOf = Character.valueOf(string.charAt(0));
            } else {
                objValueOf = string.toCharArray();
            }
        } else {
            if (!luaState.isNumber(i)) {
                throw new LuaException(String.valueOf(luaState.toString(i)) + " is not number");
            }
            if (cls == Double.TYPE) {
                objValueOf = Double.valueOf(luaState.toNumber(i));
            } else if (cls == Float.TYPE) {
                objValueOf = Float.valueOf((float) luaState.toNumber(i));
            } else if (cls == Long.TYPE) {
                objValueOf = Long.valueOf(luaState.toInteger(i));
            } else if (cls == Integer.TYPE) {
                objValueOf = Integer.valueOf((int) luaState.toInteger(i));
            } else if (cls == Short.TYPE) {
                objValueOf = Short.valueOf((short) luaState.toInteger(i));
            } else if (cls == Character.TYPE) {
                objValueOf = Character.valueOf((char) luaState.toInteger(i));
            } else if (cls == Byte.TYPE) {
                objValueOf = Byte.valueOf((byte) luaState.toInteger(i));
            } else if (cls == Boolean.TYPE) {
                objValueOf = Boolean.valueOf(luaState.toBoolean(i));
            }
        }
        luaState.pushJavaObject(objValueOf);
        return 1;
    }
}
