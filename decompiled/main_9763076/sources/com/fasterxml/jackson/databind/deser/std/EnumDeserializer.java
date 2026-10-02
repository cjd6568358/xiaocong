package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.io.IOException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class EnumDeserializer extends StdScalarDeserializer<Enum<?>> {
    protected final EnumResolver<?> _resolver;

    public EnumDeserializer(EnumResolver<?> res) {
        super((Class<?>) Enum.class);
        this._resolver = res;
    }

    public static JsonDeserializer<?> deserializerForCreator(DeserializationConfig config, Class<?> enumClass, AnnotatedMethod factory) {
        Class<?> paramClass;
        Class<?> paramClass2 = factory.getRawParameterType(0);
        if (paramClass2 == String.class) {
            paramClass = null;
        } else if (paramClass2 == Integer.TYPE || paramClass2 == Integer.class) {
            paramClass = Integer.class;
        } else if (paramClass2 == Long.TYPE || paramClass2 == Long.class) {
            paramClass = Long.class;
        } else {
            throw new IllegalArgumentException("Parameter #0 type for factory method (" + factory + ") not suitable, must be java.lang.String or int/Integer/long/Long");
        }
        if (config.canOverrideAccessModifiers()) {
            ClassUtil.checkAndFixAccess(factory.getMember());
        }
        return new FactoryBasedDeserializer(enumClass, factory, paramClass);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public boolean isCachable() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Enum<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken curr = jp.getCurrentToken();
        if (curr == JsonToken.VALUE_STRING || curr == JsonToken.FIELD_NAME) {
            String name = jp.getText();
            Enum<?> result = this._resolver.findEnum(name);
            if (result != null || ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                return result;
            }
            throw ctxt.weirdStringException(this._resolver.getEnumClass(), "value not one of declared Enum instance names");
        }
        if (curr == JsonToken.VALUE_NUMBER_INT) {
            if (ctxt.isEnabled(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS)) {
                throw ctxt.mappingException("Not allowed to deserialize Enum value out of JSON number (disable DeserializationConfig.DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS to allow)");
            }
            int index = jp.getIntValue();
            Enum<?> result2 = this._resolver.getEnum(index);
            if (result2 != null || ctxt.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                return result2;
            }
            throw ctxt.weirdNumberException(this._resolver.getEnumClass(), "index value outside legal index range [0.." + this._resolver.lastValidIndex() + "]");
        }
        throw ctxt.mappingException(this._resolver.getEnumClass());
    }

    protected static class FactoryBasedDeserializer extends StdScalarDeserializer<Object> {
        protected final Class<?> _enumClass;
        protected final Method _factory;
        protected final Class<?> _inputType;

        public FactoryBasedDeserializer(Class<?> cls, AnnotatedMethod f, Class<?> inputType) {
            super((Class<?>) Enum.class);
            this._enumClass = cls;
            this._factory = f.getAnnotated();
            this._inputType = inputType;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws Throwable {
            Object objValueOf;
            if (this._inputType == null) {
                objValueOf = jp.getText();
            } else if (this._inputType == Integer.class) {
                objValueOf = Integer.valueOf(jp.getValueAsInt());
            } else if (this._inputType == Long.class) {
                objValueOf = Long.valueOf(jp.getValueAsLong());
            } else {
                throw ctxt.mappingException(this._enumClass);
            }
            try {
                return this._factory.invoke(this._enumClass, objValueOf);
            } catch (Exception e) {
                ClassUtil.unwrapAndThrowAsIAE(e);
                return null;
            }
        }
    }
}
