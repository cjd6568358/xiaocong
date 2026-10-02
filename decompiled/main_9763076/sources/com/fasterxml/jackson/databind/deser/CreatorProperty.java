package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CreatorProperty extends SettableBeanProperty {
    protected final AnnotatedParameter _annotated;
    protected final Object _injectableValueId;

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public /* bridge */ /* synthetic */ SettableBeanProperty withValueDeserializer(JsonDeserializer x0) {
        return withValueDeserializer((JsonDeserializer<?>) x0);
    }

    public CreatorProperty(String name, JavaType type, TypeDeserializer typeDeser, Annotations contextAnnotations, AnnotatedParameter param, int index, Object injectableValueId) {
        super(name, type, typeDeser, contextAnnotations);
        this._annotated = param;
        this._propertyIndex = index;
        this._injectableValueId = injectableValueId;
    }

    protected CreatorProperty(CreatorProperty src, String newName) {
        super(src, newName);
        this._annotated = src._annotated;
        this._injectableValueId = src._injectableValueId;
    }

    protected CreatorProperty(CreatorProperty src, JsonDeserializer<?> deser) {
        super(src, deser);
        this._annotated = src._annotated;
        this._injectableValueId = src._injectableValueId;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public CreatorProperty withName(String newName) {
        return new CreatorProperty(this, newName);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public CreatorProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new CreatorProperty(this, deser);
    }

    public Object findInjectableValue(DeserializationContext context, Object beanInstance) {
        if (this._injectableValueId == null) {
            throw new IllegalStateException("Property '" + getName() + "' (type " + getClass().getName() + ") has no injectable value id configured");
        }
        return context.findInjectableValue(this._injectableValueId, this, beanInstance);
    }

    public void inject(DeserializationContext context, Object beanInstance) throws IOException {
        set(beanInstance, findInjectableValue(context, beanInstance));
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        if (this._annotated == null) {
            return null;
        }
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
    public void set(Object instance, Object value) throws IOException {
        throw new IllegalStateException("Method should never be called on a " + getClass().getName());
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object setAndReturn(Object instance, Object value) throws IOException {
        return instance;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object getInjectableValueId() {
        return this._injectableValueId;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public String toString() {
        return "[creator property, name '" + getName() + "'; inject id '" + this._injectableValueId + "']";
    }
}
