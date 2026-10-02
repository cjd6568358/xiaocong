package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class JsonParser implements Versioned, Closeable {
    private static final int MAX_BYTE_I = 255;
    private static final int MAX_SHORT_I = 32767;
    private static final int MIN_BYTE_I = -128;
    private static final int MIN_SHORT_I = -32768;
    protected int _features;

    public enum NumberType {
        INT,
        LONG,
        BIG_INTEGER,
        FLOAT,
        DOUBLE,
        BIG_DECIMAL
    }

    public abstract void clearCurrentToken();

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public abstract void close() throws IOException;

    public abstract BigInteger getBigIntegerValue() throws IOException;

    public abstract byte[] getBinaryValue(Base64Variant base64Variant) throws IOException;

    public abstract ObjectCodec getCodec();

    public abstract JsonLocation getCurrentLocation();

    public abstract String getCurrentName() throws IOException;

    public abstract JsonToken getCurrentToken();

    public abstract BigDecimal getDecimalValue() throws IOException;

    public abstract double getDoubleValue() throws IOException;

    public abstract Object getEmbeddedObject() throws IOException;

    public abstract float getFloatValue() throws IOException;

    public abstract int getIntValue() throws IOException;

    public abstract JsonToken getLastClearedToken();

    public abstract long getLongValue() throws IOException;

    public abstract NumberType getNumberType() throws IOException;

    public abstract Number getNumberValue() throws IOException;

    public abstract JsonStreamContext getParsingContext();

    public abstract String getText() throws IOException;

    public abstract char[] getTextCharacters() throws IOException;

    public abstract int getTextLength() throws IOException;

    public abstract int getTextOffset() throws IOException;

    public abstract JsonLocation getTokenLocation();

    public abstract boolean hasCurrentToken();

    public abstract boolean hasTextCharacters();

    public abstract boolean isClosed();

    public abstract JsonToken nextToken() throws IOException;

    public abstract JsonToken nextValue() throws IOException;

    public abstract void overrideCurrentName(String str);

    public abstract void setCodec(ObjectCodec objectCodec);

    public abstract JsonParser skipChildren() throws IOException;

    @Override // com.fasterxml.jackson.core.Versioned
    public abstract Version version();

    public enum Feature {
        AUTO_CLOSE_SOURCE(true),
        ALLOW_COMMENTS(false),
        ALLOW_UNQUOTED_FIELD_NAMES(false),
        ALLOW_SINGLE_QUOTES(false),
        ALLOW_UNQUOTED_CONTROL_CHARS(false),
        ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER(false),
        ALLOW_NUMERIC_LEADING_ZEROS(false),
        ALLOW_NON_NUMERIC_NUMBERS(false);

        private final boolean _defaultState;

        public static int collectDefaults() {
            int flags = 0;
            Feature[] arr$ = values();
            for (Feature f : arr$) {
                if (f.enabledByDefault()) {
                    flags |= f.getMask();
                }
            }
            return flags;
        }

        Feature(boolean defaultState) {
            this._defaultState = defaultState;
        }

        public boolean enabledByDefault() {
            return this._defaultState;
        }

        public int getMask() {
            return 1 << ordinal();
        }
    }

    protected JsonParser() {
    }

    protected JsonParser(int features) {
        this._features = features;
    }

    public void setSchema(FormatSchema schema) {
        throw new UnsupportedOperationException("Parser of type " + getClass().getName() + " does not support schema of type '" + schema.getSchemaType() + "'");
    }

    public FormatSchema getSchema() {
        return null;
    }

    public boolean canUseSchema(FormatSchema schema) {
        return false;
    }

    public Object getInputSource() {
        return null;
    }

    public int releaseBuffered(OutputStream out) throws IOException {
        return -1;
    }

    public int releaseBuffered(Writer w) throws IOException {
        return -1;
    }

    public JsonParser enable(Feature f) {
        this._features |= f.getMask();
        return this;
    }

    public JsonParser disable(Feature f) {
        this._features &= f.getMask() ^ (-1);
        return this;
    }

    public JsonParser configure(Feature f, boolean state) {
        if (state) {
            enable(f);
        } else {
            disable(f);
        }
        return this;
    }

    public boolean isEnabled(Feature f) {
        return (this._features & f.getMask()) != 0;
    }

    public boolean nextFieldName(SerializableString str) throws IOException {
        return nextToken() == JsonToken.FIELD_NAME && str.getValue().equals(getCurrentName());
    }

    public String nextTextValue() throws IOException {
        if (nextToken() == JsonToken.VALUE_STRING) {
            return getText();
        }
        return null;
    }

    public int nextIntValue(int defaultValue) throws IOException {
        if (nextToken() != JsonToken.VALUE_NUMBER_INT) {
            return defaultValue;
        }
        int defaultValue2 = getIntValue();
        return defaultValue2;
    }

    public long nextLongValue(long defaultValue) throws IOException {
        if (nextToken() != JsonToken.VALUE_NUMBER_INT) {
            return defaultValue;
        }
        long defaultValue2 = getLongValue();
        return defaultValue2;
    }

    public Boolean nextBooleanValue() throws IOException {
        switch (nextToken()) {
            case VALUE_TRUE:
                return Boolean.TRUE;
            case VALUE_FALSE:
                return Boolean.FALSE;
            default:
                return null;
        }
    }

    public boolean isExpectedStartArrayToken() {
        return getCurrentToken() == JsonToken.START_ARRAY;
    }

    public byte getByteValue() throws IOException {
        int value = getIntValue();
        if (value < MIN_BYTE_I || value > MAX_BYTE_I) {
            throw _constructError("Numeric value (" + getText() + ") out of range of Java byte");
        }
        return (byte) value;
    }

    public short getShortValue() throws IOException {
        int value = getIntValue();
        if (value < MIN_SHORT_I || value > MAX_SHORT_I) {
            throw _constructError("Numeric value (" + getText() + ") out of range of Java short");
        }
        return (short) value;
    }

    public boolean getBooleanValue() throws IOException {
        JsonToken t = getCurrentToken();
        if (t == JsonToken.VALUE_TRUE) {
            return true;
        }
        if (t == JsonToken.VALUE_FALSE) {
            return false;
        }
        throw new JsonParseException("Current token (" + t + ") not of boolean type", getCurrentLocation());
    }

    public byte[] getBinaryValue() throws IOException {
        return getBinaryValue(Base64Variants.getDefaultVariant());
    }

    public int getValueAsInt() throws IOException {
        return getValueAsInt(0);
    }

    public int getValueAsInt(int defaultValue) throws IOException {
        return defaultValue;
    }

    public long getValueAsLong() throws IOException {
        return getValueAsLong(0L);
    }

    public long getValueAsLong(long defaultValue) throws IOException {
        return defaultValue;
    }

    public double getValueAsDouble() throws IOException {
        return getValueAsDouble(0.0d);
    }

    public double getValueAsDouble(double defaultValue) throws IOException {
        return defaultValue;
    }

    public boolean getValueAsBoolean() throws IOException {
        return getValueAsBoolean(false);
    }

    public boolean getValueAsBoolean(boolean defaultValue) throws IOException {
        return defaultValue;
    }

    public <T> T readValueAs(Class<T> cls) throws IOException {
        ObjectCodec codec = getCodec();
        if (codec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
        }
        return (T) codec.readValue(this, cls);
    }

    public <T> T readValueAs(TypeReference<?> typeReference) throws IOException {
        ObjectCodec codec = getCodec();
        if (codec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
        }
        return (T) codec.readValue(this, typeReference);
    }

    public <T> Iterator<T> readValuesAs(Class<T> valueType) throws IOException {
        ObjectCodec codec = getCodec();
        if (codec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
        }
        return codec.readValues(this, valueType);
    }

    public <T> Iterator<T> readValuesAs(TypeReference<?> valueTypeRef) throws IOException {
        ObjectCodec codec = getCodec();
        if (codec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into Java objects");
        }
        return codec.readValues(this, valueTypeRef);
    }

    public <T extends TreeNode> T readValueAsTree() throws IOException {
        ObjectCodec codec = getCodec();
        if (codec == null) {
            throw new IllegalStateException("No ObjectCodec defined for the parser, can not deserialize JSON into JsonNode tree");
        }
        return (T) codec.readTree(this);
    }

    protected JsonParseException _constructError(String msg) {
        return new JsonParseException(msg, getCurrentLocation());
    }
}
