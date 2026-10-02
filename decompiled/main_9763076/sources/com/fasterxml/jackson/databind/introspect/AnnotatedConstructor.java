package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AnnotatedConstructor extends AnnotatedWithParams {
    protected final Constructor<?> _constructor;

    public AnnotatedConstructor(Constructor<?> constructor, AnnotationMap classAnn, AnnotationMap[] paramAnn) {
        super(classAnn, paramAnn);
        if (constructor == null) {
            throw new IllegalArgumentException("Null constructor not allowed");
        }
        this._constructor = constructor;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public AnnotatedConstructor withAnnotations(AnnotationMap ann) {
        return new AnnotatedConstructor(this._constructor, ann, this._paramAnnotations);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Constructor<?> getAnnotated() {
        return this._constructor;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int getModifiers() {
        return this._constructor.getModifiers();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String getName() {
        return this._constructor.getName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Type getGenericType() {
        return getRawType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getRawType() {
        return this._constructor.getDeclaringClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public JavaType getType(TypeBindings bindings) {
        return getType(bindings, this._constructor.getTypeParameters());
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public int getParameterCount() {
        return this._constructor.getParameterTypes().length;
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public Class<?> getRawParameterType(int index) {
        Class<?>[] types = this._constructor.getParameterTypes();
        if (index >= types.length) {
            return null;
        }
        return types[index];
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public Type getGenericParameterType(int index) {
        Type[] types = this._constructor.getGenericParameterTypes();
        if (index >= types.length) {
            return null;
        }
        return types[index];
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public final Object call() throws Exception {
        return this._constructor.newInstance(new Object[0]);
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public final Object call(Object[] args) throws Exception {
        return this._constructor.newInstance(args);
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedWithParams
    public final Object call1(Object arg) throws Exception {
        return this._constructor.newInstance(arg);
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Class<?> getDeclaringClass() {
        return this._constructor.getDeclaringClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Member getMember() {
        return this._constructor;
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public void setValue(Object pojo, Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor of " + getDeclaringClass().getName());
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Object getValue(Object pojo) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call getValue() on constructor of " + getDeclaringClass().getName());
    }

    public String toString() {
        return "[constructor for " + getName() + ", annotations: " + this._annotations + "]";
    }
}
