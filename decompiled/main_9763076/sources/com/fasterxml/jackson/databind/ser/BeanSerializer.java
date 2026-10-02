package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BeanSerializer extends BeanSerializerBase {
    public BeanSerializer(JavaType type, BeanSerializerBuilder builder, BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties) {
        super(type, builder, properties, filteredProperties);
    }

    protected BeanSerializer(BeanSerializerBase src) {
        super(src);
    }

    protected BeanSerializer(BeanSerializerBase src, ObjectIdWriter objectIdWriter) {
        super(src, objectIdWriter);
    }

    protected BeanSerializer(BeanSerializerBase src, String[] toIgnore) {
        super(src, toIgnore);
    }

    public static BeanSerializer createDummy(JavaType forType) {
        return new BeanSerializer(forType, null, NO_PROPS, null);
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer unwrapper) {
        return new UnwrappingBeanSerializer(this, unwrapper);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
    public BeanSerializer withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new BeanSerializer(this, objectIdWriter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase
    public BeanSerializer withIgnorals(String[] toIgnore) {
        return new BeanSerializer(this, toIgnore);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.BeanSerializerBase, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public final void serialize(Object bean, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (this._objectIdWriter != null) {
            serializeWithObjectId(bean, jgen, provider);
            return;
        }
        jgen.writeStartObject();
        if (this._propertyFilterId != null) {
            serializeFieldsFiltered(bean, jgen, provider);
        } else {
            serializeFields(bean, jgen, provider);
        }
        jgen.writeEndObject();
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
        return "BeanSerializer for " + handledType().getName();
    }
}
