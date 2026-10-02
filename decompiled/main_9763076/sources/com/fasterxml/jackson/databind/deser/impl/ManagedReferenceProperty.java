package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ManagedReferenceProperty extends SettableBeanProperty {
    protected final SettableBeanProperty _backProperty;
    protected final boolean _isContainer;
    protected final SettableBeanProperty _managedProperty;
    protected final String _referenceName;

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public /* bridge */ /* synthetic */ SettableBeanProperty withValueDeserializer(JsonDeserializer x0) {
        return withValueDeserializer((JsonDeserializer<?>) x0);
    }

    public ManagedReferenceProperty(SettableBeanProperty forward, String refName, SettableBeanProperty backward, Annotations contextAnnotations, boolean isContainer) {
        super(forward.getName(), forward.getType(), forward.getValueTypeDeserializer(), contextAnnotations);
        this._referenceName = refName;
        this._managedProperty = forward;
        this._backProperty = backward;
        this._isContainer = isContainer;
    }

    protected ManagedReferenceProperty(ManagedReferenceProperty src, JsonDeserializer<?> deser) {
        super(src, deser);
        this._referenceName = src._referenceName;
        this._isContainer = src._isContainer;
        this._managedProperty = src._managedProperty;
        this._backProperty = src._backProperty;
    }

    protected ManagedReferenceProperty(ManagedReferenceProperty src, String newName) {
        super(src, newName);
        this._referenceName = src._referenceName;
        this._isContainer = src._isContainer;
        this._managedProperty = src._managedProperty;
        this._backProperty = src._backProperty;
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public ManagedReferenceProperty withName(String newName) {
        return new ManagedReferenceProperty(this, newName);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public ManagedReferenceProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new ManagedReferenceProperty(this, deser);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public <A extends Annotation> A getAnnotation(Class<A> cls) {
        return (A) this._managedProperty.getAnnotation(cls);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty, com.fasterxml.jackson.databind.BeanProperty
    public AnnotatedMember getMember() {
        return this._managedProperty.getMember();
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public void deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        set(instance, this._managedProperty.deserialize(jp, ctxt));
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object deserializeSetAndReturn(JsonParser jp, DeserializationContext ctxt, Object instance) throws IOException {
        return setAndReturn(instance, deserialize(jp, ctxt));
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public final void set(Object instance, Object value) throws IOException {
        setAndReturn(instance, value);
    }

    @Override // com.fasterxml.jackson.databind.deser.SettableBeanProperty
    public Object setAndReturn(Object instance, Object value) throws IOException {
        Object result = this._managedProperty.setAndReturn(instance, value);
        if (value != null) {
            if (this._isContainer) {
                if (value instanceof Object[]) {
                    Object[] arr$ = (Object[]) value;
                    for (Object ob : arr$) {
                        if (ob != null) {
                            this._backProperty.set(ob, instance);
                        }
                    }
                } else if (value instanceof Collection) {
                    for (Object ob2 : (Collection) value) {
                        if (ob2 != null) {
                            this._backProperty.set(ob2, instance);
                        }
                    }
                } else if (value instanceof Map) {
                    for (Object ob3 : ((Map) value).values()) {
                        if (ob3 != null) {
                            this._backProperty.set(ob3, instance);
                        }
                    }
                } else {
                    throw new IllegalStateException("Unsupported container type (" + value.getClass().getName() + ") when resolving reference '" + this._referenceName + "'");
                }
            } else {
                this._backProperty.set(value, instance);
            }
        }
        return result;
    }
}
