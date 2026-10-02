package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class InnerClassProperty extends SettableBeanProperty {
    protected final Constructor<?> _creator;
    protected final SettableBeanProperty _delegate;

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public /* bridge */ /* synthetic */ SettableBeanProperty withValueDeserializer(JsonDeserializer x0) {
        return withValueDeserializer((JsonDeserializer<?>) x0);
    }

    public InnerClassProperty(SettableBeanProperty delegate, Constructor<?> ctor) {
        super(delegate);
        this._delegate = delegate;
        this._creator = ctor;
    }

    protected InnerClassProperty(InnerClassProperty src, JsonDeserializer<?> deser) {
        super(src, deser);
        this._delegate = src._delegate.withValueDeserializer(deser);
        this._creator = src._creator;
    }

    protected InnerClassProperty(InnerClassProperty src, String newName) {
        super(src, newName);
        this._delegate = src._delegate.withName(newName);
        this._creator = src._creator;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public InnerClassProperty withName(String newName) {
        return new InnerClassProperty(this, newName);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public InnerClassProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new InnerClassProperty(this, deser);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this._delegate.getAnnotation(cls);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public AnnotatedMember getMember() {
        return this._delegate.getMember();
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public void deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object bean) throws Throwable {
        Object objNewInstance;
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NULL) {
            objNewInstance = this._nullProvider == null ? null : this._nullProvider.nullValue(ctxt);
        } else if (this._valueTypeDeserializer != null) {
            objNewInstance = this._valueDeserializer.deserializeWithType(jp, ctxt, this._valueTypeDeserializer);
        } else {
            try {
                objNewInstance = this._creator.newInstance(bean);
            } catch (Exception e) {
                ClassUtil.unwrapAndThrowAsIAE(e, "Failed to instantiate class " + this._creator.getDeclaringClass().getName() + ", problem: " + e.getMessage());
                objNewInstance = null;
            }
            this._valueDeserializer.deserialize(jp, ctxt, objNewInstance);
        }
        set(bean, objNewInstance);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object deserializeSetAndReturn(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        return setAndReturn(instance, deserialize(jp, ctxt));
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public final void set(Object instance, Object value) throws IOException {
        this._delegate.set(instance, value);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object setAndReturn(Object instance, Object value) throws IOException {
        return this._delegate.setAndReturn(instance, value);
    }
}
