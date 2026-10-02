package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UnwrappingBeanSerializer extends BeanSerializerBase {
    protected final NameTransformer _nameTransformer;

    public UnwrappingBeanSerializer(BeanSerializerBase src, NameTransformer transformer) {
        super(src, transformer);
        this._nameTransformer = transformer;
    }

    public UnwrappingBeanSerializer(UnwrappingBeanSerializer src, ObjectIdWriter objectIdWriter) {
        super(src, objectIdWriter);
        this._nameTransformer = src._nameTransformer;
    }

    protected UnwrappingBeanSerializer(UnwrappingBeanSerializer src, String[] toIgnore) {
        super(src, toIgnore);
        this._nameTransformer = src._nameTransformer;
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer transformer) {
        return new UnwrappingBeanSerializer(this, transformer);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public boolean isUnwrappingSerializer() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
    public UnwrappingBeanSerializer withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new UnwrappingBeanSerializer(this, objectIdWriter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
    public UnwrappingBeanSerializer withIgnorals(String[] toIgnore) {
        return new UnwrappingBeanSerializer(this, toIgnore);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public final void serialize(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (this._objectIdWriter != null) {
            serializeWithObjectId(bean, jgen, provider);
        } else if (this._propertyFilterId != null) {
            serializeFieldsFiltered(bean, jgen, provider);
        } else {
            serializeFields(bean, jgen, provider);
        }
    }

    private final void serializeWithObjectId(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        ObjectIdWriter w = this._objectIdWriter;
        WritableObjectId oid = provider.findObjectId(bean, w.generator);
        Object id = oid.id;
        if (id != null) {
            oid.serializer.serialize(id, jgen, provider);
            return;
        }
        oid.serializer = w.serializer;
        Object id2 = oid.generator.generateId(bean);
        oid.id = id2;
        jgen.writeStartObject();
        SerializedString name = w.propertyName;
        if (name != null) {
            jgen.writeFieldName(name);
            w.serializer.serialize(id2, jgen, provider);
        }
        if (this._propertyFilterId != null) {
            serializeFieldsFiltered(bean, jgen, provider);
        } else {
            serializeFields(bean, jgen, provider);
        }
        jgen.writeEndObject();
    }

    public String toString() {
        return "UnwrappingBeanSerializer for " + handledType().getName();
    }
}
