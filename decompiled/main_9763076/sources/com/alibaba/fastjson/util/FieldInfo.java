package com.alibaba.fastjson.util;

import com.alibaba.fastjson.annotation.JSONField;
import com.tencent.android.tpush.common.Constants;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FieldInfo implements Comparable<FieldInfo> {
    public final String[] alternateNames;
    public final Class<?> declaringClass;
    public final Field field;
    public final boolean fieldAccess;
    private final JSONField fieldAnnotation;
    public final Class<?> fieldClass;
    public final boolean fieldTransient;
    public final Type fieldType;
    public final String format;
    public final boolean getOnly;
    public final boolean isEnum;
    public final boolean jsonDirect;
    public final String label;
    public final Method method;
    private final JSONField methodAnnotation;
    public final String name;
    public final char[] name_chars;
    private int ordinal;
    public final int parserFeatures;
    public final int serialzeFeatures;
    public final boolean unwrapped;

    public FieldInfo(String name, Class<?> declaringClass, Class<?> fieldClass, Type fieldType, Field field, int ordinal, int serialzeFeatures, int parserFeatures) {
        this.ordinal = 0;
        this.name = name;
        this.declaringClass = declaringClass;
        this.fieldClass = fieldClass;
        this.fieldType = fieldType;
        this.method = null;
        this.field = field;
        this.ordinal = ordinal;
        this.serialzeFeatures = serialzeFeatures;
        this.parserFeatures = parserFeatures;
        this.isEnum = fieldClass.isEnum();
        if (field != null) {
            int modifiers = field.getModifiers();
            this.fieldAccess = (modifiers & 1) != 0 || this.method == null;
            this.fieldTransient = Modifier.isTransient(modifiers);
        } else {
            this.fieldTransient = false;
            this.fieldAccess = false;
        }
        this.name_chars = genFieldNameChars();
        if (field != null) {
            TypeUtils.setAccessible(field);
        }
        this.label = Constants.MAIN_VERSION_TAG;
        this.fieldAnnotation = null;
        this.methodAnnotation = null;
        this.getOnly = false;
        this.jsonDirect = false;
        this.unwrapped = false;
        this.format = null;
        this.alternateNames = new String[0];
    }

    public FieldInfo(String name, Method method, Field field, Class<?> clazz, Type type, int ordinal, int serialzeFeatures, int parserFeatures, JSONField fieldAnnotation, JSONField methodAnnotation, String label) {
        boolean jsonDirect;
        Class<?> fieldClass;
        Type fieldType;
        this.ordinal = 0;
        if (field != null) {
            String fieldName = field.getName();
            if (fieldName.equals(name)) {
                name = fieldName;
            }
        }
        this.name = name;
        this.method = method;
        this.field = field;
        this.ordinal = ordinal;
        this.serialzeFeatures = serialzeFeatures;
        this.parserFeatures = parserFeatures;
        this.fieldAnnotation = fieldAnnotation;
        this.methodAnnotation = methodAnnotation;
        if (field != null) {
            int modifiers = field.getModifiers();
            this.fieldAccess = (modifiers & 1) != 0 || method == null;
            this.fieldTransient = Modifier.isTransient(modifiers) || TypeUtils.isTransient(method);
        } else {
            this.fieldAccess = false;
            this.fieldTransient = false;
        }
        if (label != null && label.length() > 0) {
            this.label = label;
        } else {
            this.label = Constants.MAIN_VERSION_TAG;
        }
        String format = null;
        JSONField annotation = getAnnotation();
        if (annotation != null) {
            format = annotation.format();
            format = format.trim().length() == 0 ? null : format;
            jsonDirect = annotation.jsonDirect();
            this.unwrapped = annotation.unwrapped();
            this.alternateNames = annotation.alternateNames();
        } else {
            jsonDirect = false;
            this.unwrapped = false;
            this.alternateNames = new String[0];
        }
        this.format = format;
        this.name_chars = genFieldNameChars();
        if (method != null) {
            TypeUtils.setAccessible(method);
        }
        if (field != null) {
            TypeUtils.setAccessible(field);
        }
        boolean getOnly = false;
        if (method != null) {
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 1) {
                fieldClass = types[0];
                fieldType = method.getGenericParameterTypes()[0];
            } else if (types.length == 2 && types[0] == String.class && types[1] == Object.class) {
                fieldClass = types[0];
                fieldType = fieldClass;
            } else {
                fieldClass = method.getReturnType();
                fieldType = method.getGenericReturnType();
                getOnly = true;
            }
            this.declaringClass = method.getDeclaringClass();
        } else {
            fieldClass = field.getType();
            fieldType = field.getGenericType();
            this.declaringClass = field.getDeclaringClass();
            getOnly = Modifier.isFinal(field.getModifiers());
        }
        this.getOnly = getOnly;
        this.jsonDirect = jsonDirect && fieldClass == String.class;
        if (clazz != null && fieldClass == Object.class && (fieldType instanceof TypeVariable)) {
            TypeVariable<?> tv = (TypeVariable) fieldType;
            Type genericFieldType = getInheritGenericType(clazz, type, tv);
            if (genericFieldType != null) {
                this.fieldClass = TypeUtils.getClass(genericFieldType);
                this.fieldType = genericFieldType;
                this.isEnum = fieldClass.isEnum();
                return;
            }
        }
        Type genericFieldType2 = fieldType;
        if (!(fieldType instanceof Class)) {
            genericFieldType2 = getFieldType(clazz, type == null ? clazz : type, fieldType);
            if (genericFieldType2 != fieldType && ((genericFieldType2 instanceof ParameterizedType) || (genericFieldType2 instanceof Class))) {
                fieldClass = TypeUtils.getClass(genericFieldType2);
            }
        }
        this.fieldType = genericFieldType2;
        this.fieldClass = fieldClass;
        this.isEnum = fieldClass.isEnum();
    }

    protected char[] genFieldNameChars() {
        int nameLen = this.name.length();
        char[] name_chars = new char[nameLen + 3];
        this.name.getChars(0, this.name.length(), name_chars, 1);
        name_chars[0] = '\"';
        name_chars[nameLen + 1] = '\"';
        name_chars[nameLen + 2] = ':';
        return name_chars;
    }

    public static Type getFieldType(Class<?> clazz, Type type, Type fieldType) {
        ParameterizedType paramType;
        TypeVariable<?>[] typeVariables;
        if (clazz == null || type == null) {
            return fieldType;
        }
        if (fieldType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) fieldType;
            Type componentType = genericArrayType.getGenericComponentType();
            Type componentTypeX = getFieldType(clazz, type, componentType);
            return componentType != componentTypeX ? Array.newInstance(TypeUtils.getClass(componentTypeX), 0).getClass() : fieldType;
        }
        if (!TypeUtils.isGenericParamType(type)) {
            return fieldType;
        }
        if (fieldType instanceof TypeVariable) {
            ParameterizedType paramType2 = (ParameterizedType) TypeUtils.getGenericParamType(type);
            Class<?> parameterizedClass = TypeUtils.getClass(paramType2);
            TypeVariable<?> typeVar = (TypeVariable) fieldType;
            TypeVariable<?>[] typeVariables2 = parameterizedClass.getTypeParameters();
            for (int i = 0; i < typeVariables2.length; i++) {
                if (typeVariables2[i].getName().equals(typeVar.getName())) {
                    return paramType2.getActualTypeArguments()[i];
                }
            }
        }
        if (fieldType instanceof ParameterizedType) {
            ParameterizedType parameterizedFieldType = (ParameterizedType) fieldType;
            Type[] arguments = parameterizedFieldType.getActualTypeArguments();
            if (type instanceof ParameterizedType) {
                paramType = (ParameterizedType) type;
                typeVariables = clazz.getTypeParameters();
            } else if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                paramType = (ParameterizedType) clazz.getGenericSuperclass();
                typeVariables = clazz.getSuperclass().getTypeParameters();
            } else {
                paramType = parameterizedFieldType;
                typeVariables = type.getClass().getTypeParameters();
            }
            boolean changed = getArgument(arguments, typeVariables, paramType.getActualTypeArguments());
            if (changed) {
                return new ParameterizedTypeImpl(arguments, parameterizedFieldType.getOwnerType(), parameterizedFieldType.getRawType());
            }
        }
        return fieldType;
    }

    private static boolean getArgument(Type[] typeArgs, TypeVariable[] typeVariables, Type[] arguments) {
        if (arguments == null || typeVariables.length == 0) {
            return false;
        }
        boolean changed = false;
        for (int i = 0; i < typeArgs.length; i++) {
            Type typeArg = typeArgs[i];
            if (typeArg instanceof ParameterizedType) {
                ParameterizedType p_typeArg = (ParameterizedType) typeArg;
                Type[] p_typeArg_args = p_typeArg.getActualTypeArguments();
                boolean p_changed = getArgument(p_typeArg_args, typeVariables, arguments);
                if (p_changed) {
                    typeArgs[i] = new ParameterizedTypeImpl(p_typeArg_args, p_typeArg.getOwnerType(), p_typeArg.getRawType());
                    changed = true;
                }
            } else if (typeArg instanceof TypeVariable) {
                for (int j = 0; j < typeVariables.length; j++) {
                    if (typeArg.equals(typeVariables[j])) {
                        typeArgs[i] = arguments[j];
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    private static Type getInheritGenericType(Class<?> clazz, Type type, TypeVariable<?> tv) {
        GenericDeclaration gd = tv.getGenericDeclaration();
        Class<?> class_gd = null;
        if (gd instanceof Class) {
            class_gd = (Class) tv.getGenericDeclaration();
        }
        Type[] arguments = null;
        if (class_gd == clazz) {
            if (type instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) type;
                arguments = ptype.getActualTypeArguments();
            }
        } else {
            for (Class<?> c = clazz; c != null && c != Object.class && c != class_gd; c = c.getSuperclass()) {
                Type superType = c.getGenericSuperclass();
                if (superType instanceof ParameterizedType) {
                    ParameterizedType p_superType = (ParameterizedType) superType;
                    Type[] p_superType_args = p_superType.getActualTypeArguments();
                    getArgument(p_superType_args, c.getTypeParameters(), arguments);
                    arguments = p_superType_args;
                }
            }
        }
        if (arguments == null) {
            return null;
        }
        TypeVariable<?>[] typeVariables = class_gd.getTypeParameters();
        for (int j = 0; j < typeVariables.length; j++) {
            if (tv.equals(typeVariables[j])) {
                Type actualType = arguments[j];
                return actualType;
            }
        }
        return null;
    }

    public String toString() {
        return this.name;
    }

    public Member getMember() {
        return this.method != null ? this.method : this.field;
    }

    protected Class<?> getDeclaredClass() {
        if (this.method != null) {
            return this.method.getDeclaringClass();
        }
        if (this.field != null) {
            return this.field.getDeclaringClass();
        }
        return null;
    }

    @Override // java.lang.Comparable
    public int compareTo(FieldInfo o) {
        if (this.ordinal < o.ordinal) {
            return -1;
        }
        if (this.ordinal > o.ordinal) {
            return 1;
        }
        int result = this.name.compareTo(o.name);
        if (result != 0) {
            return result;
        }
        Class<?> thisDeclaringClass = getDeclaredClass();
        Class<?> otherDeclaringClass = o.getDeclaredClass();
        if (thisDeclaringClass != null && otherDeclaringClass != null && thisDeclaringClass != otherDeclaringClass) {
            if (thisDeclaringClass.isAssignableFrom(otherDeclaringClass)) {
                return -1;
            }
            if (otherDeclaringClass.isAssignableFrom(thisDeclaringClass)) {
                return 1;
            }
        }
        boolean isSampeType = this.field != null && this.field.getType() == this.fieldClass;
        boolean oSameType = o.field != null && o.field.getType() == o.fieldClass;
        if (isSampeType && !oSameType) {
            return 1;
        }
        if (oSameType && !isSampeType) {
            return -1;
        }
        if (o.fieldClass.isPrimitive() && !this.fieldClass.isPrimitive()) {
            return 1;
        }
        if (this.fieldClass.isPrimitive() && !o.fieldClass.isPrimitive()) {
            return -1;
        }
        if (o.fieldClass.getName().startsWith("java.") && !this.fieldClass.getName().startsWith("java.")) {
            return 1;
        }
        if (!this.fieldClass.getName().startsWith("java.") || o.fieldClass.getName().startsWith("java.")) {
            return this.fieldClass.getName().compareTo(o.fieldClass.getName());
        }
        return -1;
    }

    public JSONField getAnnotation() {
        return this.fieldAnnotation != null ? this.fieldAnnotation : this.methodAnnotation;
    }

    public String getFormat() {
        return this.format;
    }

    public Object get(Object javaObject) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            return this.method.invoke(javaObject, new Object[0]);
        }
        return this.field.get(javaObject);
    }

    public void set(Object javaObject, Object value) throws IllegalAccessException, InvocationTargetException {
        if (this.method != null) {
            this.method.invoke(javaObject, value);
        } else {
            this.field.set(javaObject, value);
        }
    }

    public void setAccessible() throws SecurityException {
        if (this.method != null) {
            TypeUtils.setAccessible(this.method);
        } else {
            TypeUtils.setAccessible(this.field);
        }
    }
}
