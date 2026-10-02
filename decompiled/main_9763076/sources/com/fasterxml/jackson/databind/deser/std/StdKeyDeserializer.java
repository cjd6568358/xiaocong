package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class StdKeyDeserializer extends KeyDeserializer {
    protected final Class<?> _keyClass;

    protected abstract Object _parse(String str, DeserializationContext deserializationContext) throws Exception;

    protected StdKeyDeserializer(Class<?> cls) {
        this._keyClass = cls;
    }

    @Override // com.fasterxml.jackson.databind.KeyDeserializer
    public final Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
        if (key == null) {
            return null;
        }
        try {
            Object result = _parse(key, ctxt);
            if (result == null) {
                if (this._keyClass.isEnum() && ctxt.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                    return null;
                }
                throw ctxt.weirdKeyException(this._keyClass, key, "not a valid representation");
            }
            return result;
        } catch (Exception re) {
            throw ctxt.weirdKeyException(this._keyClass, key, "not a valid representation: " + re.getMessage());
        }
    }

    public Class<?> getKeyClass() {
        return this._keyClass;
    }

    protected int _parseInt(String key) throws IllegalArgumentException {
        return Integer.parseInt(key);
    }

    protected long _parseLong(String key) throws IllegalArgumentException {
        return Long.parseLong(key);
    }

    protected double _parseDouble(String key) throws IllegalArgumentException {
        return NumberInput.parseDouble(key);
    }

    @JacksonStdImpl
    static final class StringKD extends StdKeyDeserializer {
        private static final StringKD sString = new StringKD(String.class);
        private static final StringKD sObject = new StringKD(Object.class);

        private StringKD(Class<?> nominalType) {
            super(nominalType);
        }

        public static StringKD forType(Class<?> nominalType) {
            if (nominalType == String.class) {
                return sString;
            }
            if (nominalType == Object.class) {
                return sObject;
            }
            return new StringKD(nominalType);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public String _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            return key;
        }
    }

    @JacksonStdImpl
    static final class BoolKD extends StdKeyDeserializer {
        BoolKD() {
            super(Boolean.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Boolean _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            if ("true".equals(key)) {
                return Boolean.TRUE;
            }
            if (Bugly.SDK_IS_DEV.equals(key)) {
                return Boolean.FALSE;
            }
            throw ctxt.weirdKeyException(this._keyClass, key, "value not 'true' or 'false'");
        }
    }

    @JacksonStdImpl
    static final class ByteKD extends StdKeyDeserializer {
        ByteKD() {
            super(Byte.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Byte _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            int value = _parseInt(key);
            if (value < -128 || value > 255) {
                throw ctxt.weirdKeyException(this._keyClass, key, "overflow, value can not be represented as 8-bit value");
            }
            return Byte.valueOf((byte) value);
        }
    }

    @JacksonStdImpl
    static final class ShortKD extends StdKeyDeserializer {
        ShortKD() {
            super(Integer.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Short _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            int value = _parseInt(key);
            if (value < -32768 || value > 32767) {
                throw ctxt.weirdKeyException(this._keyClass, key, "overflow, value can not be represented as 16-bit value");
            }
            return Short.valueOf((short) value);
        }
    }

    @JacksonStdImpl
    static final class CharKD extends StdKeyDeserializer {
        CharKD() {
            super(Character.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Character _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            if (key.length() == 1) {
                return Character.valueOf(key.charAt(0));
            }
            throw ctxt.weirdKeyException(this._keyClass, key, "can only convert 1-character Strings");
        }
    }

    @JacksonStdImpl
    static final class IntKD extends StdKeyDeserializer {
        IntKD() {
            super(Integer.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Integer _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            return Integer.valueOf(_parseInt(key));
        }
    }

    @JacksonStdImpl
    static final class LongKD extends StdKeyDeserializer {
        LongKD() {
            super(Long.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Long _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            return Long.valueOf(_parseLong(key));
        }
    }

    @JacksonStdImpl
    static final class DoubleKD extends StdKeyDeserializer {
        DoubleKD() {
            super(Double.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Double _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            return Double.valueOf(_parseDouble(key));
        }
    }

    @JacksonStdImpl
    static final class FloatKD extends StdKeyDeserializer {
        FloatKD() {
            super(Float.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Float _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            return Float.valueOf((float) _parseDouble(key));
        }
    }

    @JacksonStdImpl
    static final class LocaleKD extends StdKeyDeserializer {
        protected JdkDeserializers.LocaleDeserializer _localeDeserializer;

        LocaleKD() {
            super(Locale.class);
            this._localeDeserializer = new JdkDeserializers.LocaleDeserializer();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Locale _parse(String key, DeserializationContext ctxt) throws JsonMappingException {
            try {
                return this._localeDeserializer._deserialize(key, ctxt);
            } catch (IOException e) {
                throw ctxt.weirdKeyException(this._keyClass, key, "unable to parse key as locale");
            }
        }
    }

    static final class DelegatingKD extends KeyDeserializer {
        protected final JsonDeserializer<?> _delegate;
        protected final Class<?> _keyClass;

        protected DelegatingKD(Class<?> cls, JsonDeserializer<?> deser) {
            this._keyClass = cls;
            this._delegate = deser;
        }

        @Override // com.fasterxml.jackson.databind.KeyDeserializer
        public final Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
            if (key == null) {
                return null;
            }
            try {
                Object result = this._delegate.deserialize(ctxt.getParser(), ctxt);
                if (result != null) {
                    return result;
                }
                throw ctxt.weirdKeyException(this._keyClass, key, "not a valid representation");
            } catch (Exception re) {
                throw ctxt.weirdKeyException(this._keyClass, key, "not a valid representation: " + re.getMessage());
            }
        }

        public Class<?> getKeyClass() {
            return this._keyClass;
        }
    }

    @JacksonStdImpl
    static final class EnumKD extends StdKeyDeserializer {
        protected final AnnotatedMethod _factory;
        protected final EnumResolver<?> _resolver;

        protected EnumKD(EnumResolver<?> er, AnnotatedMethod factory) {
            super(er.getEnumClass());
            this._resolver = er;
            this._factory = factory;
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Object _parse(String key, DeserializationContext ctxt) throws Throwable {
            if (this._factory != null) {
                try {
                    return this._factory.call1(key);
                } catch (Exception e) {
                    ClassUtil.unwrapAndThrowAsIAE(e);
                }
            }
            Enum<?> e2 = this._resolver.findEnum(key);
            if (e2 == null && !ctxt.getConfig().isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)) {
                throw ctxt.weirdKeyException(this._keyClass, key, "not one of values for Enum class");
            }
            return e2;
        }
    }

    static final class StringCtorKeyDeserializer extends StdKeyDeserializer {
        protected final Constructor<?> _ctor;

        public StringCtorKeyDeserializer(Constructor<?> ctor) {
            super(ctor.getDeclaringClass());
            this._ctor = ctor;
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Object _parse(String key, DeserializationContext ctxt) throws Exception {
            return this._ctor.newInstance(key);
        }
    }

    static final class StringFactoryKeyDeserializer extends StdKeyDeserializer {
        final Method _factoryMethod;

        public StringFactoryKeyDeserializer(Method fm) {
            super(fm.getDeclaringClass());
            this._factoryMethod = fm;
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Object _parse(String key, DeserializationContext ctxt) throws Exception {
            return this._factoryMethod.invoke(null, key);
        }
    }

    @JacksonStdImpl
    static final class DateKD extends StdKeyDeserializer {
        protected DateKD() {
            super(Date.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Object _parse(String key, DeserializationContext ctxt) throws JsonMappingException, IllegalArgumentException {
            return ctxt.parseDate(key);
        }
    }

    @JacksonStdImpl
    static final class CalendarKD extends StdKeyDeserializer {
        protected CalendarKD() {
            super(Calendar.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Object _parse(String key, DeserializationContext ctxt) throws JsonMappingException, IllegalArgumentException {
            Date date = ctxt.parseDate(key);
            if (date == null) {
                return null;
            }
            return ctxt.constructCalendar(date);
        }
    }

    @JacksonStdImpl
    static final class UuidKD extends StdKeyDeserializer {
        protected UuidKD() {
            super(UUID.class);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer
        public Object _parse(String key, DeserializationContext ctxt) throws JsonMappingException, IllegalArgumentException {
            return UUID.fromString(key);
        }
    }
}
