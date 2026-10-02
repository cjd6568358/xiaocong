package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class AnnotatedWithParams extends AnnotatedMember {
    protected final AnnotationMap[] _paramAnnotations;

    public abstract Object call() throws Exception;

    public abstract Object call(Object[] objArr) throws Exception;

    public abstract Object call1(Object obj) throws Exception;

    public abstract Type getGenericParameterType(int i);

    public abstract int getParameterCount();

    public abstract Class<?> getRawParameterType(int i);

    protected AnnotatedWithParams(AnnotationMap annotations, AnnotationMap[] paramAnnotations) {
        super(annotations);
        this._paramAnnotations = paramAnnotations;
    }

    public final void addOrOverrideParam(int paramIndex, Annotation a) {
        AnnotationMap old = this._paramAnnotations[paramIndex];
        if (old == null) {
            old = new AnnotationMap();
            this._paramAnnotations[paramIndex] = old;
        }
        old.add(a);
    }

    protected AnnotatedParameter replaceParameterAnnotations(int index, AnnotationMap ann) {
        this._paramAnnotations[index] = ann;
        return getParameter(index);
    }

    protected JavaType getType(TypeBindings bindings, TypeVariable<?>[] typeParams) {
        if (typeParams != null && typeParams.length > 0) {
            bindings = bindings.childInstance();
            for (TypeVariable<?> var : typeParams) {
                String name = var.getName();
                bindings._addPlaceholder(name);
                Type lowerBound = var.getBounds()[0];
                JavaType type = lowerBound == null ? TypeFactory.unknownType() : bindings.resolveType(lowerBound);
                bindings.addBinding(var.getName(), type);
            }
        }
        return bindings.resolveType(getGenericType());
    }

    @Override // com.fasterxml.jackson.databind.introspect.Annotated
    public final <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this._annotations.get(cls);
    }

    public final AnnotationMap getParameterAnnotations(int index) {
        if (this._paramAnnotations == null || index < 0 || index > this._paramAnnotations.length) {
            return null;
        }
        return this._paramAnnotations[index];
    }

    public final AnnotatedParameter getParameter(int index) {
        return new AnnotatedParameter(this, getGenericParameterType(index), getParameterAnnotations(index), index);
    }

    public final JavaType resolveParameterType(int index, TypeBindings bindings) {
        return bindings.resolveType(getGenericParameterType(index));
    }

    public final int getAnnotationCount() {
        return this._annotations.size();
    }
}
