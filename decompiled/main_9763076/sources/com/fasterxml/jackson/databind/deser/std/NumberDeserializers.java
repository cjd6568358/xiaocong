package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NumberDeserializers {
    public static StdDeserializer<?>[] all() {
        return new StdDeserializer[]{new BooleanDeserializer(Boolean.class, null), new ByteDeserializer(Byte.class, null), new ShortDeserializer(Short.class, null), new CharacterDeserializer(Character.class, null), new IntegerDeserializer(Integer.class, null), new LongDeserializer(Long.class, null), new FloatDeserializer(Float.class, null), new DoubleDeserializer(Double.class, null), new BooleanDeserializer(Boolean.TYPE, Boolean.FALSE), new ByteDeserializer(Byte.TYPE, (byte) 0), new ShortDeserializer(Short.TYPE, (short) 0), new CharacterDeserializer(Character.TYPE, (char) 0), new IntegerDeserializer(Integer.TYPE, 0), new LongDeserializer(Long.TYPE, 0L), new FloatDeserializer(Float.TYPE, Float.valueOf(0.0f)), new DoubleDeserializer(Double.TYPE, Double.valueOf(0.0d)), new NumberDeserializer(), new BigDecimalDeserializer(), new BigIntegerDeserializer()};
    }

    protected static abstract class PrimitiveOrWrapperDeserializer<T> extends StdScalarDeserializer<T> {
        final T _nullValue;

        protected PrimitiveOrWrapperDeserializer(Class<T> vc, T nvl) {
            super((Class<?>) vc);
            this._nullValue = nvl;
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public final T getNullValue() {
            return this._nullValue;
        }
    }

    @JacksonStdImpl
    public static final class BooleanDeserializer extends PrimitiveOrWrapperDeserializer<Boolean> {
        public BooleanDeserializer(Class<Boolean> cls, Boolean nvl) {
            super(cls, nvl);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Boolean deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return _parseBoolean(jp, ctxt);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Boolean deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
            return _parseBoolean(jp, ctxt);
        }
    }

    @JacksonStdImpl
    public static final class ByteDeserializer extends PrimitiveOrWrapperDeserializer<Byte> {
        public ByteDeserializer(Class<Byte> cls, Byte nvl) {
            super(cls, nvl);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Byte deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return _parseByte(jp, ctxt);
        }
    }

    @JacksonStdImpl
    public static final class ShortDeserializer extends PrimitiveOrWrapperDeserializer<Short> {
        public ShortDeserializer(Class<Short> cls, Short nvl) {
            super(cls, nvl);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Short deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return _parseShort(jp, ctxt);
        }
    }

    @JacksonStdImpl
    public static final class CharacterDeserializer extends PrimitiveOrWrapperDeserializer<Character> {
        public CharacterDeserializer(Class<Character> cls, Character nvl) {
            super(cls, nvl);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Character deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.VALUE_NUMBER_INT) {
                int value = jp.getIntValue();
                if (value >= 0 && value <= 65535) {
                    return Character.valueOf((char) value);
                }
            } else if (t == JsonToken.VALUE_STRING) {
                String text = jp.getText();
                if (text.length() == 1) {
                    return Character.valueOf(text.charAt(0));
                }
                if (text.length() == 0) {
                    return getEmptyValue();
                }
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }

    @JacksonStdImpl
    public static final class IntegerDeserializer extends PrimitiveOrWrapperDeserializer<Integer> {
        public IntegerDeserializer(Class<Integer> cls, Integer nvl) {
            super(cls, nvl);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Integer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return _parseInteger(jp, ctxt);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Integer deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
            return _parseInteger(jp, ctxt);
        }
    }

    @JacksonStdImpl
    public static final class LongDeserializer extends PrimitiveOrWrapperDeserializer<Long> {
        public LongDeserializer(Class<Long> cls, Long nvl) {
            super(cls, nvl);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Long deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return _parseLong(jp, ctxt);
        }
    }

    @JacksonStdImpl
    public static final class FloatDeserializer extends PrimitiveOrWrapperDeserializer<Float> {
        public FloatDeserializer(Class<Float> cls, Float nvl) {
            super(cls, nvl);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Float deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return _parseFloat(jp, ctxt);
        }
    }

    @JacksonStdImpl
    public static final class DoubleDeserializer extends PrimitiveOrWrapperDeserializer<Double> {
        public DoubleDeserializer(Class<Double> cls, Double nvl) {
            super(cls, nvl);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Double deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            return _parseDouble(jp, ctxt);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Double deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
            return _parseDouble(jp, ctxt);
        }
    }

    @JacksonStdImpl
    public static final class NumberDeserializer extends StdScalarDeserializer<Number> {
        public NumberDeserializer() {
            super((Class<?>) Number.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public Number deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            Number numberValueOf;
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.VALUE_NUMBER_INT) {
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jp.getBigIntegerValue();
                }
                return jp.getNumberValue();
            }
            if (t == JsonToken.VALUE_NUMBER_FLOAT) {
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jp.getDecimalValue();
                }
                return Double.valueOf(jp.getDoubleValue());
            }
            if (t == JsonToken.VALUE_STRING) {
                String text = jp.getText().trim();
                try {
                    if (text.indexOf(46) >= 0) {
                        if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                            numberValueOf = new BigDecimal(text);
                        } else {
                            numberValueOf = new Double(text);
                        }
                    } else if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                        numberValueOf = new BigInteger(text);
                    } else {
                        long value = Long.parseLong(text);
                        if (value <= 2147483647L && value >= -2147483648L) {
                            numberValueOf = Integer.valueOf((int) value);
                        } else {
                            numberValueOf = Long.valueOf(value);
                        }
                    }
                    return numberValueOf;
                } catch (IllegalArgumentException e) {
                    throw ctxt.weirdStringException(this._valueClass, "not a valid number");
                }
            }
            throw ctxt.mappingException(this._valueClass, t);
        }

        @Override // com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer, com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
        public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
            switch (jp.getCurrentToken()) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                case VALUE_STRING:
                    return deserialize(jp, ctxt);
                default:
                    return typeDeserializer.deserializeTypedFromScalar(jp, ctxt);
            }
        }
    }

    @JacksonStdImpl
    public static class BigIntegerDeserializer extends StdScalarDeserializer<BigInteger> {
        public BigIntegerDeserializer() {
            super((Class<?>) BigInteger.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public BigInteger deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.VALUE_NUMBER_INT) {
                switch (jp.getNumberType()) {
                    case INT:
                    case LONG:
                        return BigInteger.valueOf(jp.getLongValue());
                }
            }
            if (t == JsonToken.VALUE_NUMBER_FLOAT) {
                return jp.getDecimalValue().toBigInteger();
            }
            if (t != JsonToken.VALUE_STRING) {
                throw ctxt.mappingException(this._valueClass, t);
            }
            String text = jp.getText().trim();
            if (text.length() == 0) {
                return null;
            }
            try {
                return new BigInteger(text);
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(this._valueClass, "not a valid representation");
            }
        }
    }

    @JacksonStdImpl
    public static class BigDecimalDeserializer extends StdScalarDeserializer<BigDecimal> {
        public BigDecimalDeserializer() {
            super((Class<?>) BigDecimal.class);
        }

        @Override // com.fasterxml.jackson.databind.JsonDeserializer
        public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            JsonToken t = jp.getCurrentToken();
            if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
                return jp.getDecimalValue();
            }
            if (t == JsonToken.VALUE_STRING) {
                String text = jp.getText().trim();
                if (text.length() == 0) {
                    return null;
                }
                try {
                    return new BigDecimal(text);
                } catch (IllegalArgumentException e) {
                    throw ctxt.weirdStringException(this._valueClass, "not a valid representation");
                }
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
    }
}
