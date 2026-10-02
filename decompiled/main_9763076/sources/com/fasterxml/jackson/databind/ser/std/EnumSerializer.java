package com.fasterxml.jackson.databind.ser.std;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.EnumValues;
import com.tencent.android.tpush.SettingsContentProvider;
import java.io.IOException;
import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class EnumSerializer extends StdScalarSerializer<Enum<?>> {
    protected final EnumValues _values;

    public EnumSerializer(EnumValues v) {
        super(Enum.class, false);
        this._values = v;
    }

    public static EnumSerializer construct(Class<Enum<?>> enumClass, SerializationConfig config, BeanDescription beanDesc) {
        AnnotationIntrospector intr = config.getAnnotationIntrospector();
        EnumValues v = config.isEnabled(SerializationFeature.WRITE_ENUMS_USING_TO_STRING) ? EnumValues.constructFromToString(enumClass, intr) : EnumValues.constructFromName(enumClass, intr);
        return new EnumSerializer(v);
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.JsonSerializer
    public final void serialize(Enum<?> en, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (provider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
            jgen.writeNumber(en.ordinal());
        } else {
            jgen.writeString(this._values.serializedValueFor(en));
        }
    }

    @Override // com.fasterxml.jackson.databind.ser.std.StdScalarSerializer, com.fasterxml.jackson.databind.ser.std.StdSerializer, com.fasterxml.jackson.databind.jsonschema.SchemaAware
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) {
        if (provider.isEnabled(SerializationFeature.WRITE_ENUMS_USING_INDEX)) {
            return createSchemaNode(SettingsContentProvider.INT_TYPE, true);
        }
        ObjectNode objectNode = createSchemaNode(SettingsContentProvider.STRING_TYPE, true);
        if (typeHint != null) {
            JavaType type = provider.constructType(typeHint);
            if (type.isEnumType()) {
                ArrayNode enumNode = objectNode.putArray("enum");
                for (SerializedString value : this._values.values()) {
                    enumNode.add(value.getValue());
                }
                return objectNode;
            }
            return objectNode;
        }
        return objectNode;
    }

    public EnumValues getEnumValues() {
        return this._values;
    }
}
