package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.util.Provider;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StdJdkSerializers implements Provider<Map.Entry<Class<?>, Object>> {
    @Override // com.fasterxml.jackson.databind.util.Provider
    public Collection<Map.Entry<Class<?>, Object>> provide() {
        HashMap<Class<?>, Object> sers = new HashMap<>();
        ToStringSerializer sls = ToStringSerializer.instance;
        sers.put(URL.class, sls);
        sers.put(URI.class, sls);
        sers.put(Currency.class, sls);
        sers.put(UUID.class, sls);
        sers.put(Pattern.class, sls);
        sers.put(Locale.class, sls);
        sers.put(Locale.class, sls);
        sers.put(AtomicReference.class, AtomicReferenceSerializer.class);
        sers.put(AtomicBoolean.class, AtomicBooleanSerializer.class);
        sers.put(AtomicInteger.class, AtomicIntegerSerializer.class);
        sers.put(AtomicLong.class, AtomicLongSerializer.class);
        sers.put(File.class, FileSerializer.class);
        sers.put(Class.class, ClassSerializer.class);
        sers.put(Void.TYPE, NullSerializer.class);
        return sers.entrySet();
    }

    public static final class AtomicBooleanSerializer extends StdScalarSerializer<AtomicBoolean> {
        public AtomicBooleanSerializer() {
            super(AtomicBoolean.class, false);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(AtomicBoolean value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeBoolean(value.get());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode(SettingsContentProvider.BOOLEAN_TYPE, true);
        }
    }

    public static final class AtomicIntegerSerializer extends StdScalarSerializer<AtomicInteger> {
        public AtomicIntegerSerializer() {
            super(AtomicInteger.class, false);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(AtomicInteger value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeNumber(value.get());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode(SettingsContentProvider.INT_TYPE, true);
        }
    }

    public static final class AtomicLongSerializer extends StdScalarSerializer<AtomicLong> {
        public AtomicLongSerializer() {
            super(AtomicLong.class, false);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(AtomicLong value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeNumber(value.get());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode(SettingsContentProvider.INT_TYPE, true);
        }
    }

    public static final class AtomicReferenceSerializer extends StdSerializer<AtomicReference<?>> {
        public AtomicReferenceSerializer() {
            super(AtomicReference.class, false);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(AtomicReference<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            provider.defaultSerializeValue(value.get(), jgen);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode("any", true);
        }
    }

    public static final class FileSerializer extends StdScalarSerializer<File> {
        public FileSerializer() {
            super(File.class);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(File value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(value.getAbsolutePath());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode(SettingsContentProvider.STRING_TYPE, true);
        }
    }

    public static final class ClassSerializer extends StdScalarSerializer<Class<?>> {
        public ClassSerializer() {
            super(Class.class, false);
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
        public void serialize(Class<?> value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeString(value.getName());
        }

        @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
        public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
            return createSchemaNode(SettingsContentProvider.STRING_TYPE, true);
        }
    }
}
