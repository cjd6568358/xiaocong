package com.alibaba.fastjson.util;

import com.alibaba.fastjson.asm.ClassReader;
import com.alibaba.fastjson.asm.TypeCollector;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ASMUtils {
    public static final String JAVA_VM_NAME = System.getProperty("java.vm.name");
    public static final boolean IS_ANDROID = isAndroid(JAVA_VM_NAME);

    public static boolean isAndroid(String vmName) {
        if (vmName == null) {
            return false;
        }
        String lowerVMName = vmName.toLowerCase();
        return lowerVMName.contains("dalvik") || lowerVMName.contains("lemur");
    }

    public static String desc(Method method) {
        Class<?>[] types = method.getParameterTypes();
        StringBuilder buf = new StringBuilder((types.length + 1) << 4);
        buf.append('(');
        for (Class<?> cls : types) {
            buf.append(desc(cls));
        }
        buf.append(')');
        buf.append(desc(method.getReturnType()));
        return buf.toString();
    }

    public static String desc(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            return getPrimitiveLetter(returnType);
        }
        if (returnType.isArray()) {
            return "[" + desc(returnType.getComponentType());
        }
        return "L" + type(returnType) + ";";
    }

    public static String type(Class<?> parameterType) {
        if (parameterType.isArray()) {
            return "[" + desc(parameterType.getComponentType());
        }
        if (!parameterType.isPrimitive()) {
            String clsName = parameterType.getName();
            return clsName.replace('.', '/');
        }
        return getPrimitiveLetter(parameterType);
    }

    public static String getPrimitiveLetter(Class<?> type) {
        if (Integer.TYPE == type) {
            return "I";
        }
        if (Void.TYPE == type) {
            return "V";
        }
        if (Boolean.TYPE == type) {
            return "Z";
        }
        if (Character.TYPE == type) {
            return "C";
        }
        if (Byte.TYPE == type) {
            return "B";
        }
        if (Short.TYPE == type) {
            return "S";
        }
        if (Float.TYPE == type) {
            return "F";
        }
        if (Long.TYPE == type) {
            return "J";
        }
        if (Double.TYPE == type) {
            return "D";
        }
        throw new IllegalStateException("Type: " + type.getCanonicalName() + " is not a primitive type");
    }

    public static boolean checkName(String name) {
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c < 1 || c > 127 || c == '.') {
                return false;
            }
        }
        return true;
    }

    public static String[] lookupParameterNames(AccessibleObject methodOrCtor) {
        Class<?>[] types;
        Class<?> declaringClass;
        String name;
        String[] parameterNamesForMethod;
        if (IS_ANDROID) {
            return new String[0];
        }
        if (methodOrCtor instanceof Method) {
            Method method = (Method) methodOrCtor;
            types = method.getParameterTypes();
            name = method.getName();
            declaringClass = method.getDeclaringClass();
        } else {
            Constructor<?> constructor = (Constructor) methodOrCtor;
            types = constructor.getParameterTypes();
            declaringClass = constructor.getDeclaringClass();
            name = "<init>";
        }
        if (types.length == 0) {
            return new String[0];
        }
        ClassLoader classLoader = declaringClass.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }
        String className = declaringClass.getName();
        String resourceName = className.replace('.', '/') + ".class";
        InputStream is = classLoader.getResourceAsStream(resourceName);
        try {
            if (is == null) {
                return new String[0];
            }
            ClassReader reader = new ClassReader(is);
            TypeCollector visitor = new TypeCollector(name, types);
            reader.accept(visitor);
            parameterNamesForMethod = visitor.getParameterNamesForMethod();
            return parameterNamesForMethod;
        } catch (IOException e) {
            parameterNamesForMethod = new String[0];
        } finally {
            IOUtils.close(is);
        }
    }
}
