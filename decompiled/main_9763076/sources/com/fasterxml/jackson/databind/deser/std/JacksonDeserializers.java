package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JacksonDeserializers {
    public static StdDeserializer<?>[] all() {
        return new StdDeserializer[]{new JavaTypeDeserializer(), new TokenBufferDeserializer()};
    }

    public static ValueInstantiator findValueInstantiator(DeserializationConfig config, BeanDescription beanDesc) {
        if (beanDesc.getBeanClass() == JsonLocation.class) {
            return new JsonLocationInstantiator();
        }
        return null;
    }

    public static class JavaTypeDeserializer extends StdScalarDeserializer<JavaType> {
        public JavaTypeDeserializer() {
            super((Class<?>) JavaType.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public JavaType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken curr = jp.getCurrentToken();
            if (curr == JsonToken.VALUE_STRING) {
                String str = jp.getText().trim();
                if (str.length() == 0) {
                    return getEmptyValue();
                }
                return ctxt.getTypeFactory().constructFromCanonical(str);
            }
            if (curr == JsonToken.VALUE_EMBEDDED_OBJECT) {
                return (JavaType) jp.getEmbeddedObject();
            }
            throw ctxt.mappingException(this._valueClass);
        }
    }

    public static class JsonLocationInstantiator extends ValueInstantiator {
        @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
        public String getValueTypeDesc() {
            return JsonLocation.class.getName();
        }

        @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
        public boolean canCreateFromObjectWith() {
            return true;
        }

        @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
        public CreatorProperty[] getFromObjectArguments(DeserializationConfig config) {
            JavaType intType = config.constructType(Integer.TYPE);
            JavaType longType = config.constructType(Long.TYPE);
            return new CreatorProperty[]{new CreatorProperty("sourceRef", config.constructType(Object.class), null, null, null, 0, null), new CreatorProperty("byteOffset", longType, null, null, null, 1, null), new CreatorProperty("charOffset", longType, null, null, null, 2, null), new CreatorProperty("lineNr", intType, null, null, null, 3, null), new CreatorProperty("columnNr", intType, null, null, null, 4, null)};
        }

        @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
        public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) {
            return new JsonLocation(args[0], _long(args[1]), _long(args[2]), _int(args[3]), _int(args[4]));
        }

        private static final long _long(Object o) {
            if (o == null) {
                return 0L;
            }
            return ((Number) o).longValue();
        }

        private static final int _int(Object o) {
            if (o == null) {
                return 0;
            }
            return ((Number) o).intValue();
        }
    }

    @JacksonStdImpl
    public static class TokenBufferDeserializer extends StdScalarDeserializer<TokenBuffer> {
        public TokenBufferDeserializer() {
            super((Class<?>) TokenBuffer.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public TokenBuffer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            TokenBuffer tb = new TokenBuffer(jp.getCodec());
            tb.copyCurrentStructure(jp);
            return tb;
        }
    }
}
