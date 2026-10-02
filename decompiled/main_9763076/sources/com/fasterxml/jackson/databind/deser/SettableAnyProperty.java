package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import java.io.IOException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class SettableAnyProperty {
    protected final BeanProperty _property;
    protected final Method _setter;
    protected final JavaType _type;
    protected JsonDeserializer<Object> _valueDeserializer;

    public SettableAnyProperty(BeanProperty property, AnnotatedMethod setter, JavaType type, JsonDeserializer<Object> valueDeser) {
        this(property, setter.getAnnotated(), type, valueDeser);
    }

    public SettableAnyProperty(BeanProperty property, Method rawSetter, JavaType type, JsonDeserializer<Object> valueDeser) {
        this._property = property;
        this._type = type;
        this._setter = rawSetter;
        this._valueDeserializer = valueDeser;
    }

    public SettableAnyProperty withValueDeserializer(JsonDeserializer<Object> deser) {
        return new SettableAnyProperty(this._property, this._setter, this._type, deser);
    }

    public BeanProperty getProperty() {
        return this._property;
    }

    public boolean hasValueDeserializer() {
        return this._valueDeserializer != null;
    }

    public JavaType getType() {
        return this._type;
    }

    public final void deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object instance, String propName) throws Exception {
        set(instance, propName, deserialize(jp, ctxt));
    }

    public final Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NULL) {
            return null;
        }
        return this._valueDeserializer.deserialize(jp, ctxt);
    }

    public final void set(Object instance, String propName, Object value) throws Exception {
        try {
            this._setter.invoke(instance, propName, value);
        } catch (Exception e) {
            _throwAsIOE(e, propName, value);
        }
    }

    protected void _throwAsIOE(Exception e, String propName, Object value) throws Exception {
        if (e instanceof IllegalArgumentException) {
            String actType = value == null ? "[NULL]" : value.getClass().getName();
            StringBuilder msg = new StringBuilder("Problem deserializing \"any\" property '").append(propName);
            msg.append("' of class " + getClassName() + " (expected type: ").append(this._type);
            msg.append("; actual type: ").append(actType).append(")");
            String origMsg = e.getMessage();
            if (origMsg != null) {
                msg.append(", problem: ").append(origMsg);
            } else {
                msg.append(" (no error message provided)");
            }
            throw new JsonMappingException(msg.toString(), null, e);
        }
        if (e instanceof IOException) {
            throw ((IOException) e);
        }
        if (e instanceof RuntimeException) {
            throw ((RuntimeException) e);
        }
        Throwable t = e;
        while (t.getCause() != null) {
            t = t.getCause();
        }
        throw new JsonMappingException(t.getMessage(), null, t);
    }

    private String getClassName() {
        return this._setter.getDeclaringClass().getName();
    }

    public String toString() {
        return "[any property on class " + getClassName() + "]";
    }
}
