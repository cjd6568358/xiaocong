package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NumberSerializers {
    protected NumberSerializers() {
    }

    public static void addAll(Map<String, JsonSerializer<?>> allDeserializers) {
        JsonSerializer<?> intS = new IntegerSerializer();
        allDeserializers.put(Integer.class.getName(), intS);
        allDeserializers.put(Integer.TYPE.getName(), intS);
        allDeserializers.put(Long.class.getName(), LongSerializer.instance);
        allDeserializers.put(Long.TYPE.getName(), LongSerializer.instance);
        allDeserializers.put(Byte.class.getName(), IntLikeSerializer.instance);
        allDeserializers.put(Byte.TYPE.getName(), IntLikeSerializer.instance);
        allDeserializers.put(Short.class.getName(), IntLikeSerializer.instance);
        allDeserializers.put(Short.TYPE.getName(), IntLikeSerializer.instance);
        allDeserializers.put(Float.class.getName(), FloatSerializer.instance);
        allDeserializers.put(Float.TYPE.getName(), FloatSerializer.instance);
        allDeserializers.put(Double.class.getName(), DoubleSerializer.instance);
        allDeserializers.put(Double.TYPE.getName(), DoubleSerializer.instance);
    }

    @JacksonStdImpl
    public static final class IntegerSerializer extends NonTypedScalarSerializerBase<Integer> {
        public IntegerSerializer() {
            super(Integer.class);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Integer value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeNumber(value.intValue());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode(SettingsContentProvider.INT_TYPE, true);
        }
    }

    @JacksonStdImpl
    public static final class IntLikeSerializer extends StdScalarSerializer<Number> {
        static final IntLikeSerializer instance = new IntLikeSerializer();

        public IntLikeSerializer() {
            super(Number.class);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Number value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeNumber(value.intValue());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode(SettingsContentProvider.INT_TYPE, true);
        }
    }

    @JacksonStdImpl
    public static final class LongSerializer extends StdScalarSerializer<Long> {
        static final LongSerializer instance = new LongSerializer();

        public LongSerializer() {
            super(Long.class);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Long value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeNumber(value.longValue());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode("number", true);
        }
    }

    @JacksonStdImpl
    public static final class FloatSerializer extends StdScalarSerializer<Float> {
        static final FloatSerializer instance = new FloatSerializer();

        public FloatSerializer() {
            super(Float.class);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Float value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeNumber(value.floatValue());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode("number", true);
        }
    }

    @JacksonStdImpl
    public static final class DoubleSerializer extends NonTypedScalarSerializerBase<Double> {
        static final DoubleSerializer instance = new DoubleSerializer();

        public DoubleSerializer() {
            super(Double.class);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeNumber(value.doubleValue());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode("number", true);
        }
    }

    @JacksonStdImpl
    public static final class NumberSerializer extends StdScalarSerializer<Number> {
        public static final NumberSerializer instance = new NumberSerializer();

        public NumberSerializer() {
            super(Number.class);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Number value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value instanceof BigDecimal) {
                jgen.writeNumber((BigDecimal) value);
                return;
            }
            if (value instanceof BigInteger) {
                jgen.writeNumber((BigInteger) value);
                return;
            }
            if (value instanceof Integer) {
                jgen.writeNumber(value.intValue());
                return;
            }
            if (value instanceof Long) {
                jgen.writeNumber(value.longValue());
                return;
            }
            if (value instanceof Double) {
                jgen.writeNumber(value.doubleValue());
                return;
            }
            if (value instanceof Float) {
                jgen.writeNumber(value.floatValue());
            } else if ((value instanceof Byte) || (value instanceof Short)) {
                jgen.writeNumber(value.intValue());
            } else {
                jgen.writeNumber(value.toString());
            }
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode("number", true);
        }
    }
}
