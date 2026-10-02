package com.fasterxml.jackson.databind.node;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.cfg.DatabindVersion;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TreeTraversingParser extends ParserMinimalBase {
    protected boolean _closed;
    protected JsonToken _nextToken;
    protected NodeCursor _nodeCursor;
    protected ObjectCodec _objectCodec;
    protected boolean _startContainer;

    public TreeTraversingParser(JsonNode n) {
        this(n, null);
    }

    public TreeTraversingParser(JsonNode n, ObjectCodec codec) {
        super(0);
        this._objectCodec = codec;
        if (n.isArray()) {
            this._nextToken = JsonToken.START_ARRAY;
            this._nodeCursor = new NodeCursor.Array(n, null);
        } else if (n.isObject()) {
            this._nextToken = JsonToken.START_OBJECT;
            this._nodeCursor = new NodeCursor.Object(n, null);
        } else {
            this._nodeCursor = new NodeCursor.RootValue(n, null);
        }
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public void setCodec(ObjectCodec c) {
        this._objectCodec = c;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return DatabindVersion.instance.version();
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this._closed) {
            this._closed = true;
            this._nodeCursor = null;
            this._currToken = null;
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public JsonToken nextToken() throws IOException {
        if (this._nextToken != null) {
            this._currToken = this._nextToken;
            this._nextToken = null;
            return this._currToken;
        }
        if (this._startContainer) {
            this._startContainer = false;
            if (!this._nodeCursor.currentHasChildren()) {
                this._currToken = this._currToken == JsonToken.START_OBJECT ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
                return this._currToken;
            }
            this._nodeCursor = this._nodeCursor.iterateChildren();
            this._currToken = this._nodeCursor.nextToken();
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
                this._startContainer = true;
            }
            return this._currToken;
        }
        if (this._nodeCursor == null) {
            this._closed = true;
            return null;
        }
        this._currToken = this._nodeCursor.nextToken();
        if (this._currToken != null) {
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
                this._startContainer = true;
            }
            return this._currToken;
        }
        this._currToken = this._nodeCursor.endToken();
        this._nodeCursor = this._nodeCursor.getParent();
        return this._currToken;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public JsonParser skipChildren() throws IOException {
        if (this._currToken == JsonToken.START_OBJECT) {
            this._startContainer = false;
            this._currToken = JsonToken.END_OBJECT;
        } else if (this._currToken == JsonToken.START_ARRAY) {
            this._startContainer = false;
            this._currToken = JsonToken.END_ARRAY;
        }
        return this;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public boolean isClosed() {
        return this._closed;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public String getCurrentName() {
        if (this._nodeCursor == null) {
            return null;
        }
        return this._nodeCursor.getCurrentName();
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public void overrideCurrentName(String name) {
        if (this._nodeCursor != null) {
            this._nodeCursor.overrideCurrentName(name);
        }
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public JsonStreamContext getParsingContext() {
        return this._nodeCursor;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public JsonLocation getTokenLocation() {
        return JsonLocation.NA;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public JsonLocation getCurrentLocation() {
        return JsonLocation.NA;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public String getText() {
        if (this._closed) {
            return null;
        }
        switch (this._currToken) {
            case FIELD_NAME:
                return this._nodeCursor.getCurrentName();
            case VALUE_STRING:
                return currentNode().textValue();
            case VALUE_NUMBER_INT:
            case VALUE_NUMBER_FLOAT:
                return String.valueOf(currentNode().numberValue());
            case VALUE_EMBEDDED_OBJECT:
                JsonNode n = currentNode();
                if (n != null && n.isBinary()) {
                    return n.asText();
                }
                break;
        }
        if (this._currToken != null) {
            return this._currToken.asString();
        }
        return null;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public char[] getTextCharacters() throws IOException {
        return getText().toCharArray();
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public int getTextLength() throws IOException {
        return getText().length();
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public int getTextOffset() throws IOException {
        return 0;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public boolean hasTextCharacters() {
        return false;
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public JsonParser.NumberType getNumberType() throws IOException {
        JsonNode n = currentNumericNode();
        if (n == null) {
            return null;
        }
        return n.numberType();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public BigInteger getBigIntegerValue() throws IOException {
        return currentNumericNode().bigIntegerValue();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public BigDecimal getDecimalValue() throws IOException {
        return currentNumericNode().decimalValue();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public double getDoubleValue() throws IOException {
        return currentNumericNode().doubleValue();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public float getFloatValue() throws IOException {
        return (float) currentNumericNode().doubleValue();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public long getLongValue() throws IOException {
        return currentNumericNode().longValue();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public int getIntValue() throws IOException {
        return currentNumericNode().intValue();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public Number getNumberValue() throws IOException {
        return currentNumericNode().numberValue();
    }

    @Override // com.fasterxml.jackson.core.JsonParser
    public Object getEmbeddedObject() {
        JsonNode n;
        if (!this._closed && (n = currentNode()) != null) {
            if (n.isPojo()) {
                return ((POJONode) n).getPojo();
            }
            if (n.isBinary()) {
                return ((BinaryNode) n).binaryValue();
            }
        }
        return null;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
    public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
        JsonNode n = currentNode();
        if (n != null) {
            byte[] data = n.binaryValue();
            if (data == null) {
                if (n.isPojo()) {
                    Object ob = ((POJONode) n).getPojo();
                    if (ob instanceof byte[]) {
                        return (byte[]) ob;
                    }
                }
            } else {
                return data;
            }
        }
        return null;
    }

    protected JsonNode currentNode() {
        if (this._closed || this._nodeCursor == null) {
            return null;
        }
        return this._nodeCursor.currentNode();
    }

    protected JsonNode currentNumericNode() throws JsonParseException {
        JsonNode n = currentNode();
        if (n == null || !n.isNumber()) {
            JsonToken t = n == null ? null : n.asToken();
            throw _constructError("Current token (" + t + ") not numeric, can not use numeric value accessors");
        }
        return n;
    }

    @Override // com.fasterxml.jackson.core.base.ParserMinimalBase
    protected void _handleEOF() throws JsonParseException {
        _throwInternal();
    }
}
