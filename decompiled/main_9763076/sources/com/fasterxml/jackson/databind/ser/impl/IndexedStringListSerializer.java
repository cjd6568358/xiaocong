package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StaticListSerializerBase;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public final class IndexedStringListSerializer extends StaticListSerializerBase<List<String>> implements ContextualSerializer {
    public static final IndexedStringListSerializer instance = new IndexedStringListSerializer();
    protected final JsonSerializer<String> _serializer;

    protected IndexedStringListSerializer() {
        this(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IndexedStringListSerializer(JsonSerializer<?> jsonSerializer) {
        super(List.class);
        this._serializer = jsonSerializer;
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StaticListSerializerBase
    protected JsonNode contentSchema() {
        return createSchemaNode(SettingsContentProvider.STRING_TYPE, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.fasterxml.jackson.databind.JsonSerializer] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.fasterxml.jackson.databind.JsonSerializer] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r2v0, types: [com.fasterxml.jackson.databind.JsonSerializer<?>, com.fasterxml.jackson.databind.ser.impl.IndexedStringListSerializer] */
    @Override // com.fasterxml.jackson.databind.ser.ContextualSerializer
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        ?? CreateContextual;
        Object obj = this._serializer;
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
        return r0 == this._serializer ? this : new IndexedStringListSerializer(r0);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public void serialize(List<String> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartArray();
        if (this._serializer == null) {
            serializeContents(value, jgen, provider);
        } else {
            serializeUsingCustom(value, jgen, provider);
        }
        jgen.writeEndArray();
    }

    @Override // com.fasterxml.jackson.databind.JsonSerializer
    public void serializeWithType(List<String> value, JsonGenerator jgen, SerializerProvider provider, TypeSerializer typeSer) throws IOException {
        typeSer.writeTypePrefixForArray(value, jgen);
        if (this._serializer == null) {
            serializeContents(value, jgen, provider);
        } else {
            serializeUsingCustom(value, jgen, provider);
        }
        typeSer.writeTypeSuffixForArray(value, jgen);
    }

    private final void serializeContents(List<String> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        int i = 0;
        try {
            int len = value.size();
            while (i < len) {
                String str = value.get(i);
                if (str == null) {
                    provider.defaultSerializeNull(jgen);
                } else {
                    jgen.writeString(str);
                }
                i++;
            }
        } catch (Exception e) {
            wrapAndThrow(provider, e, value, i);
        }
    }

    private final void serializeUsingCustom(List<String> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        int i = 0;
        try {
            int len = value.size();
            JsonSerializer<String> ser = this._serializer;
            i = 0;
            while (i < len) {
                String str = value.get(i);
                if (str == null) {
                    provider.defaultSerializeNull(jgen);
                } else {
                    ser.serialize(str, jgen, provider);
                }
                i++;
            }
        } catch (Exception e) {
            wrapAndThrow(provider, e, value, i);
        }
    }
}
