package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class SetterlessProperty extends SettableBeanProperty {
    protected final AnnotatedMethod _annotated;
    protected final Method _getter;

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public /* bridge */ /* synthetic */ SettableBeanProperty withValueDeserializer(JsonDeserializer x0) {
        return withValueDeserializer((JsonDeserializer<?>) x0);
    }

    public SetterlessProperty(BeanPropertyDefinition propDef, JavaType type, TypeDeserializer typeDeser, Annotations contextAnnotations, AnnotatedMethod method) {
        super(propDef, type, typeDeser, contextAnnotations);
        this._annotated = method;
        this._getter = method.getAnnotated();
    }

    protected SetterlessProperty(SetterlessProperty src, JsonDeserializer<?> deser) {
        super(src, deser);
        this._annotated = src._annotated;
        this._getter = src._getter;
    }

    protected SetterlessProperty(SetterlessProperty src, String newName) {
        super(src, newName);
        this._annotated = src._annotated;
        this._getter = src._getter;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public SetterlessProperty withName(String newName) {
        return new SetterlessProperty(this, newName);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public SetterlessProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new SetterlessProperty(this, deser);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this._annotated.getAnnotation(cls);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public AnnotatedMember getMember() {
        return this._annotated;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public final void deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t != JsonToken.VALUE_NULL) {
            try {
                Object toModify = this._getter.invoke(instance, new Object[0]);
                if (toModify == null) {
                    throw new JsonMappingException("Problem deserializing 'setterless' property '" + getName() + "': get method returned null");
                }
                this._valueDeserializer.deserialize(jp, ctxt, toModify);
            } catch (Exception e) {
                _throwAsIOE(e);
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object deserializeSetAndReturn(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        deserializeAndSet(jp, ctxt, instance);
        return instance;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public final void set(Object instance, Object value) throws IOException {
        throw new UnsupportedOperationException("Should never call 'set' on setterless property");
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object setAndReturn(Object instance, Object value) throws IOException {
        set(instance, value);
        return null;
    }
}
