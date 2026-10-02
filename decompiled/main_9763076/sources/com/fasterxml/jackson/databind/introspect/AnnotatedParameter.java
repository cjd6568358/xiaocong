package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tencent.android.tpush.common.Constants;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AnnotatedParameter extends AnnotatedMember {
    protected final int _index;
    protected final AnnotatedWithParams _owner;
    protected final Type _type;

    public AnnotatedParameter(AnnotatedWithParams owner, Type type, AnnotationMap annotations, int index) {
        super(annotations);
        this._owner = owner;
        this._type = type;
        this._index = index;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public AnnotatedParameter withAnnotations(AnnotationMap ann) {
        return ann == this._annotations ? this : this._owner.replaceParameterAnnotations(this._index, ann);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public AnnotatedElement getAnnotated() {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public int getModifiers() {
        return this._owner.getModifiers();
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public String getName() {
        return Constants.MAIN_VERSION_TAG;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        if (this._annotations == null) {
            return null;
        }
        return (A) this._annotations.get(cls);
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Type getGenericType() {
        return this._type;
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public Class<?> getRawType() {
        if (this._type instanceof Class) {
            return (Class) this._type;
        }
        JavaType t = TypeFactory.defaultInstance().constructType(this._type);
        return t.getRawClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Class<?> getDeclaringClass() {
        return this._owner.getDeclaringClass();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Member getMember() {
        return this._owner.getMember();
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public void setValue(Object pojo, Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor parameter of " + getDeclaringClass().getName());
    }

    @Override // com.fasterxml.jackson.databind.introspect.AnnotatedMember
    public Object getValue(Object pojo) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call getValue() on constructor parameter of " + getDeclaringClass().getName());
    }

    public Type getParameterType() {
        return this._type;
    }

    public AnnotatedWithParams getOwner() {
        return this._owner;
    }

    public int getIndex() {
        return this._index;
    }

    public String toString() {
        return "[parameter #" + getIndex() + ", annotations: " + this._annotations + "]";
    }
}
