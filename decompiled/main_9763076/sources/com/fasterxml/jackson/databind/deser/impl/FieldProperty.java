package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class FieldProperty extends SettableBeanProperty {
    protected final AnnotatedField _annotated;
    protected final Field _field;

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public /* bridge */ /* synthetic */ SettableBeanProperty withValueDeserializer(JsonDeserializer x0) {
        return withValueDeserializer((JsonDeserializer<?>) x0);
    }

    public FieldProperty(BeanPropertyDefinition propDef, JavaType type, TypeDeserializer typeDeser, Annotations contextAnnotations, AnnotatedField field) {
        super(propDef, type, typeDeser, contextAnnotations);
        this._annotated = field;
        this._field = field.getAnnotated();
    }

    protected FieldProperty(FieldProperty src, JsonDeserializer<?> deser) {
        super(src, deser);
        this._annotated = src._annotated;
        this._field = src._field;
    }

    protected FieldProperty(FieldProperty src, String newName) {
        super(src, newName);
        this._annotated = src._annotated;
        this._field = src._field;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public FieldProperty withName(String newName) {
        return new FieldProperty(this, newName);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public FieldProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new FieldProperty(this, deser);
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
    public void deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        set(instance, deserialize(jp, ctxt));
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object deserializeSetAndReturn(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        return setAndReturn(instance, deserialize(jp, ctxt));
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public final void set(Object instance, Object value) throws IOException {
        try {
            this._field.set(instance, value);
        } catch (Exception e) {
            _throwAsIOE(e, value);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object setAndReturn(Object instance, Object value) throws IOException {
        try {
            this._field.set(instance, value);
        } catch (Exception e) {
            _throwAsIOE(e, value);
        }
        return instance;
    }
}
