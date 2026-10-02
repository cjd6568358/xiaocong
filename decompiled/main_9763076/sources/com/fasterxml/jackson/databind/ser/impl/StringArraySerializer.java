package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.ArraySerializerBase;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class StringArraySerializer extends ArraySerializerBase<String[]> implements ContextualSerializer {
    private static final JavaType VALUE_TYPE = TypeFactory.defaultInstance().uncheckedSimpleType(String.class);
    public static final StringArraySerializer instance = new StringArraySerializer();
    protected final JsonSerializer<Object> _elementSerializer;

    protected StringArraySerializer() {
        super(String[].class, (BeanProperty) null);
        this._elementSerializer = null;
    }

    public StringArraySerializer(StringArraySerializer src, BeanProperty prop, JsonSerializer<?> ser) {
        super(src, prop);
        this._elementSerializer = ser;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.fasterxml.jackson.databind.JsonSerializer] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.fasterxml.jackson.databind.JsonSerializer] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.fasterxml.jackson.databind.JsonSerializer<?>, com.fasterxml.jackson.databind.ser.impl.StringArraySerializer] */
    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        ?? CreateContextual;
        Object obj = this._elementSerializer;
        if (obj == null) {
            CreateContextual = serializerProvider.findValueSerializer(String.class, beanProperty);
        } else if (obj instanceof ContextualSerializer) {
            CreateContextual = obj;
            CreateContextual = ((ContextualSerializer) obj).createContextual(serializerProvider, beanProperty);
        }
        CreateContextual = obj;
        boolean zIsDefaultSerializer = isDefaultSerializer(CreateContextual);
        ?? r0 = CreateContextual;
        if (zIsDefaultSerializer) {
            r0 = 0;
        }
        return r0 == this._elementSerializer ? this : new StringArraySerializer(this, beanProperty, r0);
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JavaType getContentType() {
        return VALUE_TYPE;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public JsonSerializer<?> getContentSerializer() {
        return this._elementSerializer;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public boolean isEmpty(String[] value) {
        return value == null || value.length == 0;
    }

    @Override // com.fasterxml.jackson.databind.ser.ContainerSerializer
    public boolean hasSingleElement(String[] value) {
        return value.length == 1;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.ArraySerializerBase
    public void serializeContents(String[] value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        int len = value.length;
        if (len != 0) {
            if (this._elementSerializer != null) {
                serializeContentsSlow(value, jgen, provider, this._elementSerializer);
                return;
            }
            for (int i = 0; i < len; i++) {
                String str = value[i];
                if (str == null) {
                    jgen.writeNull();
                } else {
                    jgen.writeString(value[i]);
                }
            }
        }
    }

    private void serializeContentsSlow(String[] value, JsonGenerator jgen, SerializerProvider provider, JsonSerializer<Object> ser) throws IOException {
        int len = value.length;
        for (int i = 0; i < len; i++) {
            String str = value[i];
            if (str == null) {
                provider.defaultSerializeNull(jgen);
            } else {
                ser.serialize(value[i], jgen, provider);
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        ObjectNode o = createSchemaNode("array", true);
        o.put("items", createSchemaNode(SettingsContentProvider.STRING_TYPE));
        return o;
    }
}
