package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.io.CharTypes;
import com.fasterxml.jackson.core.io.NumberInput;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TextNode extends ValueNode {
    static final TextNode EMPTY_STRING_NODE = new TextNode(Constants.MAIN_VERSION_TAG);
    static final int INT_SPACE = 32;
    final String _value;

    public TextNode(String v) {
        this._value = v;
    }

    public static TextNode valueOf(String v) {
        if (v == null) {
            return null;
        }
        if (v.length() == 0) {
            return EMPTY_STRING_NODE;
        }
        return new TextNode(v);
    }

    @Override // com.fasterxml.jackson.databind.node.ValueNode, com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonNode, com.fasterxml.jackson.core.TreeNode
    public JsonToken asToken() {
        return JsonToken.VALUE_STRING;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean isTextual() {
        return true;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String textValue() {
        return this._value;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x0067  */
    /* JADX WARN: Code duplicated, block: B:31:0x006d  */
    /* JADX WARN: Code duplicated, block: B:34:0x007c  */
    /* JADX WARN: Code duplicated, block: B:38:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:41:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:45:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:52:0x00a3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:53:0x0065 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:54:0x00af A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:55:0x00d2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:56:0x00c6 A[SYNTHETIC] */
    public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
        int ptr;
        char ch;
        int ptr2;
        char ch2;
        int bits;
        char ch3;
        int decodedData;
        char ch4;
        int bits2;
        ByteArrayBuilder builder = new ByteArrayBuilder(100);
        String str = this._value;
        int ptr3 = 0;
        int len = str.length();
        loop0: while (ptr3 < len) {
            while (true) {
                ptr = ptr3 + 1;
                ch = str.charAt(ptr3);
                if (ptr >= len) {
                    break loop0;
                }
                if (ch > ' ') {
                    break;
                }
                ptr3 = ptr;
            }
            int bits3 = b64variant.decodeBase64Char(ch);
            if (bits3 < 0) {
                _reportInvalidBase64(b64variant, ch, 0);
            }
            if (ptr >= len) {
                _reportBase64EOF();
            }
            int ptr4 = ptr + 1;
            char ch5 = str.charAt(ptr);
            int bits4 = b64variant.decodeBase64Char(ch5);
            if (bits4 < 0) {
                _reportInvalidBase64(b64variant, ch5, 1);
            }
            int decodedData2 = (bits3 << 6) | bits4;
            if (ptr4 >= len) {
                if (!b64variant.usesPadding()) {
                    builder.append(decodedData2 >> 4);
                    break;
                }
                _reportBase64EOF();
                ptr2 = ptr4 + 1;
                ch2 = str.charAt(ptr4);
                bits = b64variant.decodeBase64Char(ch2);
                if (bits < 0) {
                    if (bits != -2) {
                        _reportInvalidBase64(b64variant, ch2, 2);
                    }
                    if (ptr2 >= len) {
                        _reportBase64EOF();
                    }
                    ptr3 = ptr2 + 1;
                    ch3 = str.charAt(ptr2);
                    if (!b64variant.usesPaddingChar(ch3)) {
                        _reportInvalidBase64(b64variant, ch3, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
                    }
                    builder.append(decodedData2 >> 4);
                } else {
                    decodedData = (decodedData2 << 6) | bits;
                    if (ptr2 >= len) {
                        if (!b64variant.usesPadding()) {
                            builder.appendTwoBytes(decodedData >> 2);
                            break;
                        }
                        _reportBase64EOF();
                    }
                    ptr3 = ptr2 + 1;
                    ch4 = str.charAt(ptr2);
                    bits2 = b64variant.decodeBase64Char(ch4);
                    if (bits2 < 0) {
                        if (bits2 != -2) {
                            _reportInvalidBase64(b64variant, ch4, 3);
                        }
                        builder.appendTwoBytes(decodedData >> 2);
                    } else {
                        builder.appendThreeBytes((decodedData << 6) | bits2);
                    }
                }
            } else {
                ptr2 = ptr4 + 1;
                ch2 = str.charAt(ptr4);
                bits = b64variant.decodeBase64Char(ch2);
                if (bits < 0) {
                    if (bits != -2) {
                        _reportInvalidBase64(b64variant, ch2, 2);
                    }
                    if (ptr2 >= len) {
                        _reportBase64EOF();
                    }
                    ptr3 = ptr2 + 1;
                    ch3 = str.charAt(ptr2);
                    if (!b64variant.usesPaddingChar(ch3)) {
                        _reportInvalidBase64(b64variant, ch3, 3, "expected padding character '" + b64variant.getPaddingChar() + "'");
                    }
                    builder.append(decodedData2 >> 4);
                } else {
                    decodedData = (decodedData2 << 6) | bits;
                    if (ptr2 >= len) {
                        if (!b64variant.usesPadding()) {
                            builder.appendTwoBytes(decodedData >> 2);
                            break;
                        }
                        _reportBase64EOF();
                    }
                    ptr3 = ptr2 + 1;
                    ch4 = str.charAt(ptr2);
                    bits2 = b64variant.decodeBase64Char(ch4);
                    if (bits2 < 0) {
                        if (bits2 != -2) {
                            _reportInvalidBase64(b64variant, ch4, 3);
                        }
                        builder.appendTwoBytes(decodedData >> 2);
                    } else {
                        builder.appendThreeBytes((decodedData << 6) | bits2);
                    }
                }
            }
        }
        return builder.toByteArray();
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public byte[] binaryValue() throws IOException {
        return getBinaryValue(Base64Variants.getDefaultVariant());
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public String asText() {
        return this._value;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean asBoolean(boolean defaultValue) {
        if (this._value != null && "true".equals(this._value.trim())) {
            return true;
        }
        return defaultValue;
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public int asInt(int defaultValue) {
        return NumberInput.parseAsInt(this._value, defaultValue);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public long asLong(long defaultValue) {
        return NumberInput.parseAsLong(this._value, defaultValue);
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public double asDouble(double defaultValue) {
        return NumberInput.parseAsDouble(this._value, defaultValue);
    }

    @Override // com.fasterxml.jackson.databind.node.BaseJsonNode, com.fasterxml.jackson.databind.JsonSerializable
    public final void serialize(JsonGenerator jg, SerializerProvider provider) throws IOException {
        if (this._value == null) {
            jg.writeNull();
        } else {
            jg.writeString(this._value);
        }
    }

    @Override // com.fasterxml.jackson.databind.JsonNode
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        return ((TextNode) o)._value.equals(this._value);
    }

    public int hashCode() {
        return this._value.hashCode();
    }

    @Override // com.fasterxml.jackson.databind.node.ValueNode, com.fasterxml.jackson.databind.JsonNode
    public String toString() {
        int len = this._value.length();
        StringBuilder sb = new StringBuilder(len + 2 + (len >> 4));
        appendQuoted(sb, this._value);
        return sb.toString();
    }

    protected static void appendQuoted(StringBuilder sb, String content) {
        sb.append('\"');
        CharTypes.appendQuoted(sb, content);
        sb.append('\"');
    }

    protected void _reportInvalidBase64(Base64Variant b64variant, char ch, int bindex) throws JsonParseException {
        _reportInvalidBase64(b64variant, ch, bindex, null);
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
        throw new JsonParseException(base, JsonLocation.NA);
    }

    protected void _reportBase64EOF() throws JsonParseException {
        throw new JsonParseException("Unexpected end-of-String when base64 content", JsonLocation.NA);
    }
}
