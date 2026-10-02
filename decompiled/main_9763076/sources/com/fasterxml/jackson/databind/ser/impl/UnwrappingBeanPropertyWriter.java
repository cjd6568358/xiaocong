package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UnwrappingBeanPropertyWriter extends BeanPropertyWriter {
    protected final NameTransformer _nameTransformer;

    public UnwrappingBeanPropertyWriter(BeanPropertyWriter base, NameTransformer unwrapper) {
        super(base);
        this._nameTransformer = unwrapper;
    }

    private UnwrappingBeanPropertyWriter(UnwrappingBeanPropertyWriter base, NameTransformer transformer, SerializedString name) {
        super(base, name);
        this._nameTransformer = transformer;
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    public UnwrappingBeanPropertyWriter rename(NameTransformer transformer) {
        String oldName = this._name.getValue();
        String newName = transformer.transform(oldName);
        return new UnwrappingBeanPropertyWriter(this, NameTransformer.chainedTransformer(transformer, this._nameTransformer), new SerializedString(newName));
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception {
        Class<?> cls;
        PropertySerializerMap map;
        Object value = get(bean);
        if (value != null) {
            JsonSerializer<?> jsonSerializerSerializerFor = this._serializer;
            if (jsonSerializerSerializerFor == null && (jsonSerializerSerializerFor = (map = this._dynamicSerializers).serializerFor((cls = value.getClass()))) == null) {
                jsonSerializerSerializerFor = _findAndAddDynamic(map, cls, prov);
            }
            if (this._suppressableValue != null) {
                if (MARKER_FOR_EMPTY == this._suppressableValue) {
                    if (jsonSerializerSerializerFor.isEmpty(value)) {
                        return;
                    }
                } else if (this._suppressableValue.equals(value)) {
                    return;
                }
            }
            if (value == bean) {
                _handleSelfReference(bean, jsonSerializerSerializerFor);
            }
            if (!jsonSerializerSerializerFor.isUnwrappingSerializer()) {
                jgen.writeFieldName(this._name);
            }
            if (this._typeSerializer == null) {
                jsonSerializerSerializerFor.serialize(value, jgen, prov);
            } else {
                jsonSerializerSerializerFor.serializeWithType(value, jgen, prov, this._typeSerializer);
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    public void assignSerializer(JsonSerializer<Object> ser) {
        super.assignSerializer(ser);
        if (this._serializer != null) {
            NameTransformer t = this._nameTransformer;
            if (this._serializer.isUnwrappingSerializer()) {
                t = NameTransformer.chainedTransformer(t, ((UnwrappingBeanSerializer) this._serializer)._nameTransformer);
            }
            this._serializer = this._serializer.unwrappingSerializer(t);
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.BeanPropertyWriter
    protected JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map, Class<?> type, SerializerProvider provider) throws JsonMappingException {
        JsonSerializer<Object> serializer;
        if (this._nonTrivialBaseType != null) {
            JavaType subtype = provider.constructSpecializedType(this._nonTrivialBaseType, type);
            serializer = provider.findValueSerializer(subtype, this);
        } else {
            serializer = provider.findValueSerializer(type, this);
        }
        NameTransformer t = this._nameTransformer;
        if (serializer.isUnwrappingSerializer()) {
            t = NameTransformer.chainedTransformer(t, ((UnwrappingBeanSerializer) serializer)._nameTransformer);
        }
        JsonSerializer<Object> serializer2 = serializer.unwrappingSerializer(t);
        this._dynamicSerializers = this._dynamicSerializers.newWith(type, serializer2);
        return serializer2;
    }
}
