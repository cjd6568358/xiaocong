package com.fasterxml.jackson.databind.introspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AnnotatedField extends AnnotatedMember {
    protected final Field _field;

    public AnnotatedField(Field field, AnnotationMap annMap) {
        super(annMap);
        this._field = field;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public AnnotatedField withAnnotations(AnnotationMap ann) {
        return new AnnotatedField(this._field, ann);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Field getAnnotated() {
        return this._field;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int getModifiers() {
        return this._field.getModifiers();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String getName() {
        return this._field.getName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this._annotations.get(cls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Type getGenericType() {
        return this._field.getGenericType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getRawType() {
        return this._field.getType();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Class<?> getDeclaringClass() {
        return this._field.getDeclaringClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Member getMember() {
        return this._field;
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public void setValue(Object pojo, Object value) throws IllegalArgumentException {
        try {
            this._field.set(pojo, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to setValue() for field " + getFullName() + ": " + e.getMessage(), e);
        }
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Object getValue(Object pojo) throws IllegalArgumentException {
        try {
            return this._field.get(pojo);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to getValue() for field " + getFullName() + ": " + e.getMessage(), e);
        }
    }

    public String getFullName() {
        return getDeclaringClass().getName() + "#" + getName();
    }

    public int getAnnotationCount() {
        return this._annotations.size();
    }

    public String toString() {
        return "[field " + getFullName() + "]";
    }
}
