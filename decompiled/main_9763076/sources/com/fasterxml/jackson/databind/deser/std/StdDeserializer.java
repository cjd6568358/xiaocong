package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.bugly.Bugly;
import java.io.IOException;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class StdDeserializer<T> extends JsonDeserializer<T> {
    protected final Class<?> _valueClass;

    protected StdDeserializer(Class<?> vc) {
        this._valueClass = vc;
    }

    protected StdDeserializer(JavaType valueType) {
        this._valueClass = valueType == null ? null : valueType.getRawClass();
    }

    public Class<?> getValueClass() {
        return this._valueClass;
    }

    public JavaType getValueType() {
        return null;
    }

    protected boolean isDefaultDeserializer(JsonDeserializer<?> deserializer) {
        return (deserializer == null || deserializer.getClass().getAnnotation(JacksonStdImpl.class) == null) ? false : true;
    }

    protected boolean isDefaultKeyDeserializer(KeyDeserializer keyDeser) {
        return (keyDeser == null || keyDeser.getClass().getAnnotation(JacksonStdImpl.class) == null) ? false : true;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromAny(jp, ctxt);
    }

    protected final boolean _parseBooleanPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (t != JsonToken.VALUE_FALSE && t != JsonToken.VALUE_NULL) {
            if (t == JsonToken.VALUE_NUMBER_INT) {
                if (jp.getNumberType() == JsonParser.NumberType.INT) {
                    return jp.getIntValue() != 0;
                }
                return _parseBooleanFromNumber(jp, ctxt);
            }
            if (t == JsonToken.VALUE_STRING) {
                String text = jp.getText().trim();
                if ("true".equals(text)) {
                    return true;
                }
                if (Bugly.SDK_IS_DEV.equals(text) || text.length() == 0) {
                    return Boolean.FALSE.booleanValue();
                }
                throw ctxt.weirdStringException(this._valueClass, "only \"true\" or \"false\" recognized");
            }
            throw ctxt.mappingException(this._valueClass, t);
        }
        return false;
    }

    protected final Boolean _parseBoolean(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_TRUE) {
            return Boolean.TRUE;
        }
        if (currentToken == JsonToken.VALUE_FALSE) {
            return Boolean.FALSE;
        }
        if (currentToken == JsonToken.VALUE_NUMBER_INT) {
            if (jsonParser.getNumberType() == JsonParser.NumberType.INT) {
                return jsonParser.getIntValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
            }
            return Boolean.valueOf(_parseBooleanFromNumber(jsonParser, deserializationContext));
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Boolean) getNullValue();
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            String strTrim = jsonParser.getText().trim();
            if ("true".equals(strTrim)) {
                return Boolean.TRUE;
            }
            if (Bugly.SDK_IS_DEV.equals(strTrim)) {
                return Boolean.FALSE;
            }
            if (strTrim.length() == 0) {
                return (Boolean) getEmptyValue();
            }
            throw deserializationContext.weirdStringException(this._valueClass, "only \"true\" or \"false\" recognized");
        }
        throw deserializationContext.mappingException(this._valueClass, currentToken);
    }

    protected final boolean _parseBooleanFromNumber(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.getNumberType() == JsonParser.NumberType.LONG) {
            return (jp.getLongValue() == 0 ? Boolean.FALSE : Boolean.TRUE).booleanValue();
        }
        String str = jp.getText();
        if ("0.0".equals(str) || PushConstants.PUSH_TYPE_NOTIFY.equals(str)) {
            return Boolean.FALSE.booleanValue();
        }
        return Boolean.TRUE.booleanValue();
    }

    protected Byte _parseByte(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Byte bValueOf;
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return Byte.valueOf(jsonParser.getByteValue());
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            String strTrim = jsonParser.getText().trim();
            try {
                if (strTrim.length() == 0) {
                    bValueOf = (Byte) getEmptyValue();
                } else {
                    int i = NumberInput.parseInt(strTrim);
                    if (i < -128 || i > 255) {
                        throw deserializationContext.weirdStringException(this._valueClass, "overflow, value can not be represented as 8-bit value");
                    }
                    bValueOf = Byte.valueOf((byte) i);
                }
                return bValueOf;
            } catch (IllegalArgumentException e) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Byte value");
            }
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Byte) getNullValue();
        }
        throw deserializationContext.mappingException(this._valueClass, currentToken);
    }

    protected Short _parseShort(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Short shValueOf;
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return Short.valueOf(jsonParser.getShortValue());
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            String strTrim = jsonParser.getText().trim();
            try {
                if (strTrim.length() == 0) {
                    shValueOf = (Short) getEmptyValue();
                } else {
                    int i = NumberInput.parseInt(strTrim);
                    if (i < -32768 || i > 32767) {
                        throw deserializationContext.weirdStringException(this._valueClass, "overflow, value can not be represented as 16-bit value");
                    }
                    shValueOf = Short.valueOf((short) i);
                }
                return shValueOf;
            } catch (IllegalArgumentException e) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Short value");
            }
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Short) getNullValue();
        }
        throw deserializationContext.mappingException(this._valueClass, currentToken);
    }

    protected final short _parseShortPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        int value = _parseIntPrimitive(jp, ctxt);
        if (value < -32768 || value > 32767) {
            throw ctxt.weirdStringException(this._valueClass, "overflow, value can not be represented as 16-bit value");
        }
        return (short) value;
    }

    protected final int _parseIntPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return jp.getIntValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            try {
                int len = text.length();
                if (len > 9) {
                    long l = Long.parseLong(text);
                    if (l < -2147483648L || l > 2147483647L) {
                        throw ctxt.weirdStringException(this._valueClass, "Overflow: numeric value (" + text + ") out of range of int (-2147483648 - 2147483647)");
                    }
                    return (int) l;
                }
                if (len != 0) {
                    return NumberInput.parseInt(text);
                }
                return 0;
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(this._valueClass, "not a valid int value");
            }
        }
        if (t != JsonToken.VALUE_NULL) {
            throw ctxt.mappingException(this._valueClass, t);
        }
        return 0;
    }

    protected final Integer _parseInteger(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return Integer.valueOf(jsonParser.getIntValue());
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            String strTrim = jsonParser.getText().trim();
            try {
                int length = strTrim.length();
                if (length > 9) {
                    long j = Long.parseLong(strTrim);
                    if (j < -2147483648L || j > 2147483647L) {
                        throw deserializationContext.weirdStringException(this._valueClass, "Overflow: numeric value (" + strTrim + ") out of range of Integer (-2147483648 - 2147483647)");
                    }
                    return Integer.valueOf((int) j);
                }
                if (length == 0) {
                    return (Integer) getEmptyValue();
                }
                return Integer.valueOf(NumberInput.parseInt(strTrim));
            } catch (IllegalArgumentException e) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Integer value");
            }
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Integer) getNullValue();
        }
        throw deserializationContext.mappingException(this._valueClass, currentToken);
    }

    protected final Long _parseLong(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return Long.valueOf(jsonParser.getLongValue());
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            String strTrim = jsonParser.getText().trim();
            if (strTrim.length() == 0) {
                return (Long) getEmptyValue();
            }
            try {
                return Long.valueOf(NumberInput.parseLong(strTrim));
            } catch (IllegalArgumentException e) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Long value");
            }
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Long) getNullValue();
        }
        throw deserializationContext.mappingException(this._valueClass, currentToken);
    }

    protected final long _parseLongPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return jp.getLongValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0) {
                return 0L;
            }
            try {
                return NumberInput.parseLong(text);
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(this._valueClass, "not a valid long value");
            }
        }
        if (t != JsonToken.VALUE_NULL) {
            throw ctxt.mappingException(this._valueClass, t);
        }
        return 0L;
    }

    protected final Float _parseFloat(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return Float.valueOf(jsonParser.getFloatValue());
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            String strTrim = jsonParser.getText().trim();
            if (strTrim.length() == 0) {
                return (Float) getEmptyValue();
            }
            switch (strTrim.charAt(0)) {
                case '-':
                    if ("-Infinity".equals(strTrim) || "-INF".equals(strTrim)) {
                        return Float.valueOf(Float.NEGATIVE_INFINITY);
                    }
                    break;
                case 'I':
                    if ("Infinity".equals(strTrim) || "INF".equals(strTrim)) {
                        return Float.valueOf(Float.POSITIVE_INFINITY);
                    }
                    break;
                case 'N':
                    if ("NaN".equals(strTrim)) {
                        return Float.valueOf(Float.NaN);
                    }
                    break;
            }
            try {
                return Float.valueOf(Float.parseFloat(strTrim));
            } catch (IllegalArgumentException e) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Float value");
            }
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Float) getNullValue();
        }
        throw deserializationContext.mappingException(this._valueClass, currentToken);
    }

    protected final float _parseFloatPrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return jp.getFloatValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0) {
                return 0.0f;
            }
            switch (text.charAt(0)) {
                case '-':
                    if ("-Infinity".equals(text) || "-INF".equals(text)) {
                        return Float.NEGATIVE_INFINITY;
                    }
                    break;
                case 'I':
                    if ("Infinity".equals(text) || "INF".equals(text)) {
                        return Float.POSITIVE_INFINITY;
                    }
                    break;
                case 'N':
                    if ("NaN".equals(text)) {
                        return Float.NaN;
                    }
                    break;
            }
            try {
                return Float.parseFloat(text);
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(this._valueClass, "not a valid float value");
            }
        }
        if (t != JsonToken.VALUE_NULL) {
            throw ctxt.mappingException(this._valueClass, t);
        }
        return 0.0f;
    }

    protected final Double _parseDouble(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_INT || currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
            return Double.valueOf(jsonParser.getDoubleValue());
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            String strTrim = jsonParser.getText().trim();
            if (strTrim.length() == 0) {
                return (Double) getEmptyValue();
            }
            switch (strTrim.charAt(0)) {
                case '-':
                    if ("-Infinity".equals(strTrim) || "-INF".equals(strTrim)) {
                        return Double.valueOf(Double.NEGATIVE_INFINITY);
                    }
                    break;
                case 'I':
                    if ("Infinity".equals(strTrim) || "INF".equals(strTrim)) {
                        return Double.valueOf(Double.POSITIVE_INFINITY);
                    }
                    break;
                case 'N':
                    if ("NaN".equals(strTrim)) {
                        return Double.valueOf(Double.NaN);
                    }
                    break;
            }
            try {
                return Double.valueOf(parseDouble(strTrim));
            } catch (IllegalArgumentException e) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid Double value");
            }
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Double) getNullValue();
        }
        throw deserializationContext.mappingException(this._valueClass, currentToken);
    }

    protected final double _parseDoublePrimitive(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
            return jp.getDoubleValue();
        }
        if (t == JsonToken.VALUE_STRING) {
            String text = jp.getText().trim();
            if (text.length() == 0) {
                return 0.0d;
            }
            switch (text.charAt(0)) {
                case '-':
                    if ("-Infinity".equals(text) || "-INF".equals(text)) {
                        return Double.NEGATIVE_INFINITY;
                    }
                    break;
                case 'I':
                    if ("Infinity".equals(text) || "INF".equals(text)) {
                        return Double.POSITIVE_INFINITY;
                    }
                    break;
                case 'N':
                    if ("NaN".equals(text)) {
                        return Double.NaN;
                    }
                    break;
            }
            try {
                return parseDouble(text);
            } catch (IllegalArgumentException e) {
                throw ctxt.weirdStringException(this._valueClass, "not a valid double value");
            }
        }
        if (t != JsonToken.VALUE_NULL) {
            throw ctxt.mappingException(this._valueClass, t);
        }
        return 0.0d;
    }

    protected Date _parseDate(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Date date;
        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_NUMBER_INT) {
            return new Date(jsonParser.getLongValue());
        }
        if (currentToken == JsonToken.VALUE_NULL) {
            return (Date) getNullValue();
        }
        if (currentToken == JsonToken.VALUE_STRING) {
            try {
                String strTrim = jsonParser.getText().trim();
                if (strTrim.length() == 0) {
                    date = (Date) getEmptyValue();
                } else {
                    date = deserializationContext.parseDate(strTrim);
                }
                return date;
            } catch (IllegalArgumentException e) {
                throw deserializationContext.weirdStringException(this._valueClass, "not a valid representation (error: " + e.getMessage() + ")");
            }
        }
        throw deserializationContext.mappingException(this._valueClass, currentToken);
    }

    protected static final double parseDouble(String numStr) throws NumberFormatException {
        if (NumberInput.NASTY_SMALL_DOUBLE.equals(numStr)) {
            return Double.MIN_VALUE;
        }
        return Double.parseDouble(numStr);
    }

    protected JsonDeserializer<Object> findDeserializer(DeserializationContext ctxt, JavaType type, BeanProperty property) throws JsonMappingException {
        return ctxt.findContextualValueDeserializer(type, property);
    }

    protected void handleUnknownProperty(JsonParser jp, DeserializationContext ctxt, Object instanceOrClass, String propName) throws IOException {
        if (instanceOrClass == null) {
            instanceOrClass = getValueClass();
        }
        if (!ctxt.handleUnknownProperty(jp, this, instanceOrClass, propName)) {
            ctxt.reportUnknownProperty(instanceOrClass, propName, this);
            jp.skipChildren();
        }
    }
}
