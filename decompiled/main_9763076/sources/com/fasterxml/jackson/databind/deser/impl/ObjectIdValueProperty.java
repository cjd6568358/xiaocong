package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ObjectIdValueProperty extends SettableBeanProperty {
    protected final ObjectIdReader _objectIdReader;

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public /* bridge */ /* synthetic */ SettableBeanProperty withValueDeserializer(JsonDeserializer x0) {
        return withValueDeserializer((JsonDeserializer<?>) x0);
    }

    public ObjectIdValueProperty(ObjectIdReader objectIdReader) {
        super(objectIdReader.propertyName, objectIdReader.idType, (TypeDeserializer) null, (Annotations) null);
        this._objectIdReader = objectIdReader;
        this._valueDeserializer = objectIdReader.deserializer;
    }

    protected ObjectIdValueProperty(ObjectIdValueProperty src, JsonDeserializer<?> deser) {
        super(src, deser);
        this._objectIdReader = src._objectIdReader;
    }

    protected ObjectIdValueProperty(ObjectIdValueProperty src, String newName) {
        super(src, newName);
        this._objectIdReader = src._objectIdReader;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public ObjectIdValueProperty withName(String newName) {
        return new ObjectIdValueProperty(this, newName);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public ObjectIdValueProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new ObjectIdValueProperty(this, deser);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public AnnotatedMember getMember() {
        return null;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public void deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        deserializeSetAndReturn(jp, ctxt, instance);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object deserializeSetAndReturn(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        Object id = this._valueDeserializer.deserialize(jp, ctxt);
        ReadableObjectId roid = ctxt.findObjectId(id, this._objectIdReader.generator);
        roid.bindItem(instance);
        SettableBeanProperty idProp = this._objectIdReader.idProperty;
        if (idProp != null) {
            return idProp.setAndReturn(instance, id);
        }
        return instance;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public void set(Object instance, Object value) throws IOException {
        setAndReturn(instance, value);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object setAndReturn(Object instance, Object value) throws IOException {
        SettableBeanProperty idProp = this._objectIdReader.idProperty;
        if (idProp == null) {
            throw new UnsupportedOperationException("Should not call set() on ObjectIdProperty that has no SettableBeanProperty");
        }
        return idProp.setAndReturn(instance, value);
    }
}
