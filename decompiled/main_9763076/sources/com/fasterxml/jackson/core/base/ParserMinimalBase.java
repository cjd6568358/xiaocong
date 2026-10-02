package com.fasterxml.jackson.core.base;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.VersionUtil;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ParserMinimalBase extends JsonParser {
    protected static final int INT_APOSTROPHE = 39;
    protected static final int INT_ASTERISK = 42;
    protected static final int INT_BACKSLASH = 92;
    protected static final int INT_COLON = 58;
    protected static final int INT_COMMA = 44;
    protected static final int INT_CR = 13;
    protected static final int INT_LBRACKET = 91;
    protected static final int INT_LCURLY = 123;
    protected static final int INT_LF = 10;
    protected static final int INT_QUOTE = 34;
    protected static final int INT_RBRACKET = 93;
    protected static final int INT_RCURLY = 125;
    protected static final int INT_SLASH = 47;
    protected static final int INT_SPACE = 32;
    protected static final int INT_TAB = 9;
    protected static final int INT_b = 98;
    protected static final int INT_f = 102;
    protected static final int INT_n = 110;
    protected static final int INT_r = 114;
    protected static final int INT_t = 116;
    protected static final int INT_u = 117;
    protected JsonToken _currToken;
    protected JsonToken _lastClearedToken;

    protected abstract void _handleEOF() throws JsonParseException;

    @Override // com.fasterxml.jackson.core.JsonParser, java.io.Closeable, java.lang.AutoCloseable
    public abstract void close() throws IOException;

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract byte[] getBinaryValue(Base64Variant base64Variant) throws IOException;

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract String getCurrentName() throws IOException;

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract JsonStreamContext getParsingContext();

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract String getText() throws IOException;

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract char[] getTextCharacters() throws IOException;

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract int getTextLength() throws IOException;

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract int getTextOffset() throws IOException;

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract boolean hasTextCharacters();

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract boolean isClosed();

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract JsonToken nextToken() throws IOException;

    @Override // com.fasterxml.jackson.core.JsonParser
    public abstract void overrideCurrentName(String str);

    protected ParserMinimalBase() {
    }

    protected ParserMinimalBase(int features) {
        super(features);
    }

    @Override // com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return VersionUtil.versionFor(getClass());
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public JsonToken getCurrentToken() {
        return this._currToken;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public boolean hasCurrentToken() {
        return this._currToken != null;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public JsonToken nextValue() throws IOException {
        JsonToken t = nextToken();
        if (t == JsonToken.FIELD_NAME) {
            return nextToken();
        }
        return t;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public JsonParser skipChildren() throws IOException {
        if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
            int open = 1;
            while (true) {
                JsonToken t = nextToken();
                if (t == null) {
                    _handleEOF();
                } else {
                    switch (t) {
                        case START_OBJECT:
                        case START_ARRAY:
                            open++;
                            break;
                        case END_OBJECT:
                        case END_ARRAY:
                            open--;
                            if (open != 0) {
                            }
                            break;
                    }
                }
            }
        }
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public void clearCurrentToken() {
        if (this._currToken != null) {
            this._lastClearedToken = this._currToken;
            this._currToken = null;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public JsonToken getLastClearedToken() {
        return this._lastClearedToken;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public boolean getValueAsBoolean(boolean defaultValue) throws IOException {
        if (this._currToken == null) {
            return defaultValue;
        }
        switch (this._currToken) {
            case VALUE_NUMBER_INT:
                return getIntValue() != 0;
            case VALUE_TRUE:
                return true;
            case VALUE_FALSE:
            case VALUE_NULL:
                return false;
            case VALUE_EMBEDDED_OBJECT:
                Object value = getEmbeddedObject();
                if (value instanceof Boolean) {
                    return ((Boolean) value).booleanValue();
                }
                break;
            case VALUE_STRING:
                break;
            default:
                return defaultValue;
        }
        String str = getText().trim();
        if ("true".equals(str)) {
            return true;
        }
        return defaultValue;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int getValueAsInt(int defaultValue) throws IOException {
        if (this._currToken != null) {
            switch (this._currToken) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    return getIntValue();
                case VALUE_TRUE:
                    return 1;
                case VALUE_FALSE:
                case VALUE_NULL:
                    return 0;
                case VALUE_EMBEDDED_OBJECT:
                    Object value = getEmbeddedObject();
                    if (value instanceof Number) {
                        return ((Number) value).intValue();
                    }
                    return defaultValue;
                case VALUE_STRING:
                    return NumberInput.parseAsInt(getText(), defaultValue);
                default:
                    return defaultValue;
            }
        }
        return defaultValue;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public long getValueAsLong(long defaultValue) throws IOException {
        if (this._currToken != null) {
            switch (this._currToken) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    return getLongValue();
                case VALUE_TRUE:
                    return 1L;
                case VALUE_FALSE:
                case VALUE_NULL:
                    return 0L;
                case VALUE_EMBEDDED_OBJECT:
                    Object value = getEmbeddedObject();
                    if (value instanceof Number) {
                        return ((Number) value).longValue();
                    }
                    return defaultValue;
                case VALUE_STRING:
                    return NumberInput.parseAsLong(getText(), defaultValue);
                default:
                    return defaultValue;
            }
        }
        return defaultValue;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public double getValueAsDouble(double defaultValue) throws IOException {
        if (this._currToken != null) {
            switch (this._currToken) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    return getDoubleValue();
                case VALUE_TRUE:
                    return 1.0d;
                case VALUE_FALSE:
                case VALUE_NULL:
                    return 0.0d;
                case VALUE_EMBEDDED_OBJECT:
                    Object value = getEmbeddedObject();
                    if (value instanceof Number) {
                        return ((Number) value).doubleValue();
                    }
                    return defaultValue;
                case VALUE_STRING:
                    return NumberInput.parseAsDouble(getText(), defaultValue);
                default:
                    return defaultValue;
            }
        }
        return defaultValue;
    }

    protected void _decodeBase64(String str, ByteArrayBuilder builder, Base64Variant b64variant) throws IOException {
        int ptr;
        char ch;
        int ptr2 = 0;
        int len = str.length();
        while (ptr2 < len) {
            while (true) {
                ptr = ptr2 + 1;
                ch = str.charAt(ptr2);
                if (ptr < len) {
                    if (ch > ' ') {
                        break;
                    } else {
                        ptr2 = ptr;
                    }
                } else {
                    return;
                }
            }
            int bits = b64variant.decodeBase64Char(ch);
            if (bits < 0) {
                _reportInvalidBase64(b64variant, ch, 0, null);
            }
            if (ptr >= len) {
                _reportBase64EOF();
            }
            int ptr3 = ptr + 1;
            char ch2 = str.charAt(ptr);
            int bits2 = b64variant.decodeBase64Char(ch2);
            if (bits2 < 0) {
                _reportInvalidBase64(b64variant, ch2, 1, null);
            }
            int decodedData = (bits << 6) | bits2;
            if (ptr3 >= len) {
                if (!b64variant.usesPadding()) {
                    builder.append(decodedData >> 4);
                    return;
                }
                _reportBase64EOF();
            }
            int ptr4 = ptr3 + 1;
            char ch3 = str.charAt(ptr3);
            int bits3 = b64variant.decodeBase64Char(ch3);
            if (bits3 < 0) {
                if (bits3 != -2) {
                    _reportInvalidBase64(b64variant, ch3, 2, null);
                }
                if (ptr4 >= len) {
                    _reportBase64EOF();
                }
                ptr2 = ptr4 + 1;
                char ch4 = str.charAt(ptr4);
                if (!b64variant.usesPaddingChar(ch4)) {
                    _reportInvalidBase64(b64variant, ch4, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
                }
                builder.append(decodedData >> 4);
            } else {
                int decodedData2 = (decodedData << 6) | bits3;
                if (ptr4 >= len) {
                    if (!b64variant.usesPadding()) {
                        builder.appendTwoBytes(decodedData2 >> 2);
                        return;
                    }
                    _reportBase64EOF();
                }
                ptr2 = ptr4 + 1;
                char ch5 = str.charAt(ptr4);
                int bits4 = b64variant.decodeBase64Char(ch5);
                if (bits4 < 0) {
                    if (bits4 != -2) {
                        _reportInvalidBase64(b64variant, ch5, 3, null);
                    }
                    builder.appendTwoBytes(decodedData2 >> 2);
                } else {
                    builder.appendThreeBytes((decodedData2 << 6) | bits4);
                }
            }
        }
    }

    protected void _reportInvalidBase64(Base64Variant b64variant, char ch, int bindex, String msg) throws JsonParseException {
        String base;
        if (ch <= ' ') {
            base = "Illegal white space character (code 0x" + Integer.toHexString(ch) + ") as character #" + (bindex + 1) + " of 4-char base64 unit: can only used between units";
        } else if (b64variant.usesPaddingChar(ch)) {
            base = "Unexpected padding character ('" + b64variant.getPaddingChar() + "') as character #" + (bindex + 1) + " of 4-char base64 unit: padding only legal as 3rd or 4th character";
        } else if (!Character.isDefined(ch) || Character.isISOControl(ch)) {
            base = "Illegal character (code 0x" + Integer.toHexString(ch) + ") in base64 content";
        } else {
            base = "Illegal character '" + ch + "' (code 0x" + Integer.toHexString(ch) + ") in base64 content";
        }
        if (msg != null) {
            base = base + ": " + msg;
        }
        throw _constructError(base);
    }

    protected void _reportBase64EOF() throws JsonParseException {
        throw _constructError("Unexpected end-of-String in base64 content");
    }

    protected void _reportUnexpectedChar(int ch, String comment) throws JsonParseException {
        String msg = "Unexpected character (" + _getCharDesc(ch) + ")";
        if (comment != null) {
            msg = msg + ": " + comment;
        }
        _reportError(msg);
    }

    protected void _reportInvalidEOF() throws JsonParseException {
        _reportInvalidEOF(" in " + this._currToken);
    }

    protected void _reportInvalidEOF(String msg) throws JsonParseException {
        _reportError("Unexpected end-of-input" + msg);
    }

    protected void _reportInvalidEOFInValue() throws JsonParseException {
        _reportInvalidEOF(" in a value");
    }

    protected void _throwInvalidSpace(int i) throws JsonParseException {
        char c = (char) i;
        String msg = "Illegal character (" + _getCharDesc(c) + "): only regular white space (\\r, \\n, \\t) is allowed between tokens";
        _reportError(msg);
    }

    protected void _throwUnquotedSpace(int i, String ctxtDesc) throws JsonParseException {
        if (!isEnabled(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS) || i >= 32) {
            char c = (char) i;
            String msg = "Illegal unquoted character (" + _getCharDesc(c) + "): has to be escaped using backslash to be included in " + ctxtDesc;
            _reportError(msg);
        }
    }

    protected char _handleUnrecognizedCharacterEscape(char ch) throws JsonProcessingException {
        if (!isEnabled(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER) && (ch != '\'' || !isEnabled(JsonParser.Feature.ALLOW_SINGLE_QUOTES))) {
            _reportError("Unrecognized character escape " + _getCharDesc(ch));
        }
        return ch;
    }

    protected static final String _getCharDesc(int ch) {
        char c = (char) ch;
        if (Character.isISOControl(c)) {
            return "(CTRL-CHAR, code " + ch + ")";
        }
        if (ch > 255) {
            return "'" + c + "' (code " + ch + " / 0x" + Integer.toHexString(ch) + ")";
        }
        return "'" + c + "' (code " + ch + ")";
    }

    protected final void _reportError(String msg) throws JsonParseException {
        throw _constructError(msg);
    }

    protected final void _wrapError(String msg, Throwable t) throws JsonParseException {
        throw _constructError(msg, t);
    }

    protected final void _throwInternal() {
        throw new RuntimeException("Internal error: this code path should never get executed");
    }

    protected final JsonParseException _constructError(String msg, Throwable t) {
        return new JsonParseException(msg, getCurrentLocation(), t);
    }
}
