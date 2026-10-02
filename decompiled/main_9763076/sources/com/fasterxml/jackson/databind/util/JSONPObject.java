package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSONPObject implements JsonSerializable {
    protected final String _function;
    protected final JavaType _serializationType;
    protected final Object _value;

    public JSONPObject(String function, Object value) {
        this(function, value, (JavaType) null);
    }

    public JSONPObject(String function, Object value, JavaType asType) {
        this._function = function;
        this._value = value;
        this._serializationType = asType;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable
    public void serializeWithType(JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        serialize(jgen, provider);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializable
    public void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeRaw(this._function);
        jgen.writeRaw('(');
        if (this._value == null) {
            provider.defaultSerializeNull(jgen);
        } else if (this._serializationType != null) {
            provider.findTypedValueSerializer(this._serializationType, true, (BeanProperty) null).serialize(this._value, jgen, provider);
        } else {
            Class<?> cls = this._value.getClass();
            provider.findTypedValueSerializer(cls, true, (BeanProperty) null).serialize(this._value, jgen, provider);
        }
        jgen.writeRaw(')');
    }

    public String getFunction() {
        return this._function;
    }

    public Object getValue() {
        return this._value;
    }

    public JavaType getSerializationType() {
        return this._serializationType;
    }
}
