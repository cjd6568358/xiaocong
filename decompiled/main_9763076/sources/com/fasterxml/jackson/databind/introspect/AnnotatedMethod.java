package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AnnotatedMethod extends AnnotatedWithParams {
    protected final Method _method;
    protected Class<?>[] _paramClasses;

    public AnnotatedMethod(Method method, AnnotationMap classAnn, AnnotationMap[] paramAnnotations) {
        super(classAnn, paramAnnotations);
        if (method == null) {
            throw new IllegalArgumentException("Can not construct AnnotatedMethod with null Method");
        }
        this._method = method;
    }

    public AnnotatedMethod withMethod(Method m) {
        return new AnnotatedMethod(m, this._annotations, this._paramAnnotations);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public AnnotatedMethod withAnnotations(AnnotationMap ann) {
        return new AnnotatedMethod(this._method, ann, this._paramAnnotations);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Method getAnnotated() {
        return this._method;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int getModifiers() {
        return this._method.getModifiers();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String getName() {
        return this._method.getName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Type getGenericType() {
        return this._method.getGenericReturnType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getRawType() {
        return this._method.getReturnType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public JavaType getType(TypeBindings bindings) {
        return getType(bindings, this._method.getTypeParameters());
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public final Object call() throws Exception {
        return this._method.invoke(null, new Object[0]);
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public final Object call(Object[] args) throws Exception {
        return this._method.invoke(null, args);
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public final Object call1(Object arg) throws Exception {
        return this._method.invoke(null, arg);
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Class<?> getDeclaringClass() {
        return this._method.getDeclaringClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Method getMember() {
        return this._method;
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public void setValue(Object pojo, Object value) throws IllegalArgumentException {
        try {
            this._method.invoke(pojo, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to setValue() with method " + getFullName() + ": " + e.getMessage(), e);
        } catch (InvocationTargetException e2) {
            throw new IllegalArgumentException("Failed to setValue() with method " + getFullName() + ": " + e2.getMessage(), e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Object getValue(Object pojo) throws IllegalArgumentException {
        try {
            return this._method.invoke(pojo, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to getValue() with method " + getFullName() + ": " + e.getMessage(), e);
        } catch (InvocationTargetException e2) {
            throw new IllegalArgumentException("Failed to getValue() with method " + getFullName() + ": " + e2.getMessage(), e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public int getParameterCount() {
        return getRawParameterTypes().length;
    }

    public String getFullName() {
        return getDeclaringClass().getName() + "#" + getName() + "(" + getParameterCount() + " params)";
    }

    public Class<?>[] getRawParameterTypes() {
        if (this._paramClasses == null) {
            this._paramClasses = this._method.getParameterTypes();
        }
        return this._paramClasses;
    }

    public Type[] getGenericParameterTypes() {
        return this._method.getGenericParameterTypes();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public Class<?> getRawParameterType(int index) {
        Class<?>[] types = getRawParameterTypes();
        if (index >= types.length) {
            return null;
        }
        return types[index];
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public Type getGenericParameterType(int index) {
        Type[] types = this._method.getGenericParameterTypes();
        if (index >= types.length) {
            return null;
        }
        return types[index];
    }

    public Class<?> getRawReturnType() {
        return this._method.getReturnType();
    }

    public Type getGenericReturnType() {
        return this._method.getGenericReturnType();
    }

    public String toString() {
        return "[method " + getFullName() + "]";
    }
}
