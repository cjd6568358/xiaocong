package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.base.ParserMinimalBase;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.fasterxml.jackson.core.json.JsonWriteContext;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.databind.cfg.DatabindVersion;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TokenBuffer extends JsonGenerator {
    protected static final int DEFAULT_PARSER_FEATURES = JsonParser.Feature.collectDefaults();
    protected int _appendOffset;
    protected boolean _closed;
    protected Segment _first;
    protected Segment _last;
    protected ObjectCodec _objectCodec;
    protected int _generatorFeatures = DEFAULT_PARSER_FEATURES;
    protected JsonWriteContext _writeContext = JsonWriteContext.createRootContext();

    public TokenBuffer(ObjectCodec codec) {
        this._objectCodec = codec;
        Segment segment = new Segment();
        this._last = segment;
        this._first = segment;
        this._appendOffset = 0;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return DatabindVersion.instance.version();
    }

    public JsonParser asParser() {
        return asParser(this._objectCodec);
    }

    public JsonParser asParser(ObjectCodec codec) {
        return new Parser(this._first, codec);
    }

    public JsonParser asParser(JsonParser src) {
        Parser p = new Parser(this._first, src.getCodec());
        p.setLocation(src.getTokenLocation());
        return p;
    }

    public TokenBuffer append(TokenBuffer other) throws IOException {
        JsonParser jp = other.asParser();
        while (jp.nextToken() != null) {
            copyCurrentEvent(jp);
        }
        return this;
    }

    public void serialize(JsonGenerator jgen) throws IOException {
        Segment segment = this._first;
        int ptr = -1;
        while (true) {
            ptr++;
            if (ptr >= 16) {
                ptr = 0;
                segment = segment.next();
                if (segment == null) {
                    return;
                }
            }
            JsonToken t = segment.type(ptr);
            if (t != null) {
                switch (t) {
                    case START_OBJECT:
                        jgen.writeStartObject();
                        break;
                    case END_OBJECT:
                        jgen.writeEndObject();
                        break;
                    case START_ARRAY:
                        jgen.writeStartArray();
                        break;
                    case END_ARRAY:
                        jgen.writeEndArray();
                        break;
                    case FIELD_NAME:
                        Object ob = segment.get(ptr);
                        if (ob instanceof SerializableString) {
                            jgen.writeFieldName((SerializableString) ob);
                        } else {
                            jgen.writeFieldName((String) ob);
                        }
                        break;
                    case VALUE_STRING:
                        Object ob2 = segment.get(ptr);
                        if (ob2 instanceof SerializableString) {
                            jgen.writeString((SerializableString) ob2);
                        } else {
                            jgen.writeString((String) ob2);
                        }
                        break;
                    case VALUE_NUMBER_INT:
                        Number n = (Number) segment.get(ptr);
                        if (n instanceof BigInteger) {
                            jgen.writeNumber((BigInteger) n);
                        } else if (n instanceof Long) {
                            jgen.writeNumber(n.longValue());
                        } else {
                            jgen.writeNumber(n.intValue());
                        }
                        break;
                    case VALUE_NUMBER_FLOAT:
                        Object n2 = segment.get(ptr);
                        if (n2 instanceof BigDecimal) {
                            jgen.writeNumber((BigDecimal) n2);
                        } else if (n2 instanceof Float) {
                            jgen.writeNumber(((Float) n2).floatValue());
                        } else if (n2 instanceof Double) {
                            jgen.writeNumber(((Double) n2).doubleValue());
                        } else if (n2 == null) {
                            jgen.writeNull();
                        } else if (n2 instanceof String) {
                            jgen.writeNumber((String) n2);
                        } else {
                            throw new JsonGenerationException("Unrecognized value type for VALUE_NUMBER_FLOAT: " + n2.getClass().getName() + ", can not serialize");
                        }
                        break;
                    case VALUE_TRUE:
                        jgen.writeBoolean(true);
                        break;
                    case VALUE_FALSE:
                        jgen.writeBoolean(false);
                        break;
                    case VALUE_NULL:
                        jgen.writeNull();
                        break;
                    case VALUE_EMBEDDED_OBJECT:
                        jgen.writeObject(segment.get(ptr));
                        break;
                    default:
                        throw new RuntimeException("Internal error: should never end up through this code path");
                }
            } else {
                return;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[TokenBuffer: ");
        JsonParser jp = asParser();
        int count = 0;
        while (true) {
            try {
                JsonToken t = jp.nextToken();
                if (t == null) {
                    break;
                }
                if (count < 100) {
                    if (count > 0) {
                        sb.append(", ");
                    }
                    sb.append(t.toString());
                    if (t == JsonToken.FIELD_NAME) {
                        sb.append('(');
                        sb.append(jp.getCurrentName());
                        sb.append(')');
                    }
                }
                count++;
            } catch (IOException ioe) {
                throw new IllegalStateException(ioe);
            }
        }
        if (count >= 100) {
            sb.append(" ... (truncated ").append(count - 100).append(" entries)");
        }
        sb.append(']');
        return sb.toString();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator enable(JsonGenerator.Feature f) {
        this._generatorFeatures |= f.getMask();
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator disable(JsonGenerator.Feature f) {
        this._generatorFeatures &= f.getMask() ^ (-1);
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public boolean isEnabled(JsonGenerator.Feature f) {
        return (this._generatorFeatures & f.getMask()) != 0;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator useDefaultPrettyPrinter() {
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator setCodec(ObjectCodec oc) {
        this._objectCodec = oc;
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final JsonWriteContext getOutputContext() {
        return this._writeContext;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void flush() throws IOException {
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this._closed = true;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public boolean isClosed() {
        return this._closed;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeStartArray() throws IOException {
        _append(JsonToken.START_ARRAY);
        this._writeContext = this._writeContext.createChildArrayContext();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeEndArray() throws IOException {
        _append(JsonToken.END_ARRAY);
        JsonWriteContext c = this._writeContext.getParent();
        if (c != null) {
            this._writeContext = c;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeStartObject() throws IOException {
        _append(JsonToken.START_OBJECT);
        this._writeContext = this._writeContext.createChildObjectContext();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeEndObject() throws IOException {
        _append(JsonToken.END_OBJECT);
        JsonWriteContext c = this._writeContext.getParent();
        if (c != null) {
            this._writeContext = c;
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public final void writeFieldName(String name) throws IOException {
        _append(JsonToken.FIELD_NAME, name);
        this._writeContext.writeFieldName(name);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(SerializableString name) throws IOException {
        _append(JsonToken.FIELD_NAME, name);
        this._writeContext.writeFieldName(name.getValue());
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(String text) throws IOException {
        if (text == null) {
            writeNull();
        } else {
            _append(JsonToken.VALUE_STRING, text);
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(char[] text, int offset, int len) throws IOException {
        writeString(new String(text, offset, len));
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(SerializableString text) throws IOException {
        if (text == null) {
            writeNull();
        } else {
            _append(JsonToken.VALUE_STRING, text);
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String text) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String text, int offset, int len) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char[] text, int offset, int len) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char c) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(String text) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(String text, int offset, int len) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(char[] text, int offset, int len) throws IOException {
        _reportUnsupportedOperation();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(int i) throws IOException {
        _append(JsonToken.VALUE_NUMBER_INT, Integer.valueOf(i));
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(long l) throws IOException {
        _append(JsonToken.VALUE_NUMBER_INT, Long.valueOf(l));
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(double d) throws IOException {
        _append(JsonToken.VALUE_NUMBER_FLOAT, Double.valueOf(d));
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(float f) throws IOException {
        _append(JsonToken.VALUE_NUMBER_FLOAT, Float.valueOf(f));
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigDecimal dec) throws IOException {
        if (dec == null) {
            writeNull();
        } else {
            _append(JsonToken.VALUE_NUMBER_FLOAT, dec);
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigInteger v) throws IOException {
        if (v == null) {
            writeNull();
        } else {
            _append(JsonToken.VALUE_NUMBER_INT, v);
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(String encodedValue) throws IOException {
        _append(JsonToken.VALUE_NUMBER_FLOAT, encodedValue);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeBoolean(boolean state) throws IOException {
        _append(state ? JsonToken.VALUE_TRUE : JsonToken.VALUE_FALSE);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNull() throws IOException {
        _append(JsonToken.VALUE_NULL);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeObject(Object value) throws IOException {
        _append(JsonToken.VALUE_EMBEDDED_OBJECT, value);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeTree(TreeNode rootNode) throws IOException {
        _append(JsonToken.VALUE_EMBEDDED_OBJECT, rootNode);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException {
        byte[] copy = new byte[len];
        System.arraycopy(data, offset, copy, 0, len);
        writeObject(copy);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void copyCurrentEvent(JsonParser jp) throws IOException {
        switch (jp.getCurrentToken()) {
            case START_OBJECT:
                writeStartObject();
                return;
            case END_OBJECT:
                writeEndObject();
                return;
            case START_ARRAY:
                writeStartArray();
                return;
            case END_ARRAY:
                writeEndArray();
                return;
            case FIELD_NAME:
                writeFieldName(jp.getCurrentName());
                return;
            case VALUE_STRING:
                if (jp.hasTextCharacters()) {
                    writeString(jp.getTextCharacters(), jp.getTextOffset(), jp.getTextLength());
                    return;
                } else {
                    writeString(jp.getText());
                    return;
                }
            case VALUE_NUMBER_INT:
                switch (jp.getNumberType()) {
                    case INT:
                        writeNumber(jp.getIntValue());
                        return;
                    case BIG_INTEGER:
                        writeNumber(jp.getBigIntegerValue());
                        return;
                    default:
                        writeNumber(jp.getLongValue());
                        return;
                }
            case VALUE_NUMBER_FLOAT:
                switch (jp.getNumberType()) {
                    case BIG_DECIMAL:
                        writeNumber(jp.getDecimalValue());
                        return;
                    case FLOAT:
                        writeNumber(jp.getFloatValue());
                        return;
                    default:
                        writeNumber(jp.getDoubleValue());
                        return;
                }
            case VALUE_TRUE:
                writeBoolean(true);
                return;
            case VALUE_FALSE:
                writeBoolean(false);
                return;
            case VALUE_NULL:
                writeNull();
                return;
            case VALUE_EMBEDDED_OBJECT:
                writeObject(jp.getEmbeddedObject());
                return;
            default:
                throw new RuntimeException("Internal error: should never end up through this code path");
        }
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void copyCurrentStructure(JsonParser jp) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.FIELD_NAME) {
            writeFieldName(jp.getCurrentName());
            t = jp.nextToken();
        }
        switch (t) {
            case START_OBJECT:
                writeStartObject();
                while (jp.nextToken() != JsonToken.END_OBJECT) {
                    copyCurrentStructure(jp);
                }
                writeEndObject();
                break;
            case END_OBJECT:
            default:
                copyCurrentEvent(jp);
                break;
            case START_ARRAY:
                writeStartArray();
                while (jp.nextToken() != JsonToken.END_ARRAY) {
                    copyCurrentStructure(jp);
                }
                writeEndArray();
                break;
        }
    }

    protected final void _append(JsonToken type) {
        Segment next = this._last.append(this._appendOffset, type);
        if (next == null) {
            this._appendOffset++;
        } else {
            this._last = next;
            this._appendOffset = 1;
        }
    }

    protected final void _append(JsonToken type, Object value) {
        Segment next = this._last.append(this._appendOffset, type, value);
        if (next == null) {
            this._appendOffset++;
        } else {
            this._last = next;
            this._appendOffset = 1;
        }
    }

    protected final void _appendRaw(int rawType, Object value) {
        Segment next = this._last.appendRaw(this._appendOffset, rawType, value);
        if (next == null) {
            this._appendOffset++;
        } else {
            this._last = next;
            this._appendOffset = 1;
        }
    }

    protected void _reportUnsupportedOperation() {
        throw new UnsupportedOperationException("Called operation not supported for TokenBuffer");
    }

    protected static final class Parser extends ParserMinimalBase {
        protected transient ByteArrayBuilder _byteBuilder;
        protected boolean _closed;
        protected ObjectCodec _codec;
        protected JsonLocation _location;
        protected JsonReadContext _parsingContext;
        protected Segment _segment;
        protected int _segmentPtr;

        public Parser(Segment firstSeg, ObjectCodec codec) {
            super(0);
            this._location = null;
            this._segment = firstSeg;
            this._segmentPtr = -1;
            this._codec = codec;
            this._parsingContext = JsonReadContext.createRootContext(-1, -1);
        }

        public void setLocation(JsonLocation l) {
            this._location = l;
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public ObjectCodec getCodec() {
            return this._codec;
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public void setCodec(ObjectCodec c) {
            this._codec = c;
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.core.Versioned
        public Version version() {
            return DatabindVersion.instance.version();
        }

        public JsonToken peekNextToken() throws IOException {
            if (this._closed) {
                return null;
            }
            Segment seg = this._segment;
            int ptr = this._segmentPtr + 1;
            if (ptr >= 16) {
                ptr = 0;
                seg = seg == null ? null : seg.next();
            }
            if (seg != null) {
                return seg.type(ptr);
            }
            return null;
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (!this._closed) {
                this._closed = true;
            }
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public JsonToken nextToken() throws IOException {
            if (this._closed || this._segment == null) {
                return null;
            }
            int i = this._segmentPtr + 1;
            this._segmentPtr = i;
            if (i >= 16) {
                this._segmentPtr = 0;
                this._segment = this._segment.next();
                if (this._segment == null) {
                    return null;
                }
            }
            this._currToken = this._segment.type(this._segmentPtr);
            if (this._currToken == JsonToken.FIELD_NAME) {
                Object ob = _currentObject();
                String name = ob instanceof String ? (String) ob : ob.toString();
                this._parsingContext.setCurrentName(name);
            } else if (this._currToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(-1, -1);
            } else if (this._currToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(-1, -1);
            } else if (this._currToken == JsonToken.END_OBJECT || this._currToken == JsonToken.END_ARRAY) {
                this._parsingContext = this._parsingContext.getParent();
                if (this._parsingContext == null) {
                    this._parsingContext = JsonReadContext.createRootContext(-1, -1);
                }
            }
            return this._currToken;
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public boolean isClosed() {
            return this._closed;
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public JsonStreamContext getParsingContext() {
            return this._parsingContext;
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public JsonLocation getTokenLocation() {
            return getCurrentLocation();
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public JsonLocation getCurrentLocation() {
            return this._location == null ? JsonLocation.NA : this._location;
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public String getCurrentName() {
            return this._parsingContext.getCurrentName();
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public void overrideCurrentName(String name) {
            JsonReadContext ctxt = this._parsingContext;
            if (this._currToken == JsonToken.START_OBJECT || this._currToken == JsonToken.START_ARRAY) {
                ctxt = ctxt.getParent();
            }
            ctxt.setCurrentName(name);
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public String getText() {
            if (this._currToken == JsonToken.VALUE_STRING || this._currToken == JsonToken.FIELD_NAME) {
                Object ob = _currentObject();
                if (ob instanceof String) {
                    return (String) ob;
                }
                if (ob != null) {
                    return ob.toString();
                }
                return null;
            }
            if (this._currToken == null) {
                return null;
            }
            switch (this._currToken) {
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    Object ob2 = _currentObject();
                    if (ob2 != null) {
                        return ob2.toString();
                    }
                    return null;
                default:
                    return this._currToken.asString();
            }
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public char[] getTextCharacters() {
            String str = getText();
            if (str == null) {
                return null;
            }
            return str.toCharArray();
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public int getTextLength() {
            String str = getText();
            if (str == null) {
                return 0;
            }
            return str.length();
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public int getTextOffset() {
            return 0;
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public boolean hasTextCharacters() {
            return false;
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public BigInteger getBigIntegerValue() throws IOException {
            Number n = getNumberValue();
            if (n instanceof BigInteger) {
                return (BigInteger) n;
            }
            switch (getNumberType()) {
                case BIG_DECIMAL:
                    return ((BigDecimal) n).toBigInteger();
                default:
                    return BigInteger.valueOf(n.longValue());
            }
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public BigDecimal getDecimalValue() throws IOException {
            Number n = getNumberValue();
            if (n instanceof BigDecimal) {
                return (BigDecimal) n;
            }
            switch (getNumberType()) {
                case INT:
                case LONG:
                    return BigDecimal.valueOf(n.longValue());
                case BIG_INTEGER:
                    return new BigDecimal((BigInteger) n);
                case BIG_DECIMAL:
                case FLOAT:
                default:
                    return BigDecimal.valueOf(n.doubleValue());
            }
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public double getDoubleValue() throws IOException {
            return getNumberValue().doubleValue();
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public float getFloatValue() throws IOException {
            return getNumberValue().floatValue();
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public int getIntValue() throws IOException {
            return this._currToken == JsonToken.VALUE_NUMBER_INT ? ((Number) _currentObject()).intValue() : getNumberValue().intValue();
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public long getLongValue() throws IOException {
            return getNumberValue().longValue();
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public JsonParser.NumberType getNumberType() throws IOException {
            Number n = getNumberValue();
            if (n instanceof Integer) {
                return JsonParser.NumberType.INT;
            }
            if (n instanceof Long) {
                return JsonParser.NumberType.LONG;
            }
            if (n instanceof Double) {
                return JsonParser.NumberType.DOUBLE;
            }
            if (n instanceof BigDecimal) {
                return JsonParser.NumberType.BIG_DECIMAL;
            }
            if (n instanceof Float) {
                return JsonParser.NumberType.FLOAT;
            }
            if (n instanceof BigInteger) {
                return JsonParser.NumberType.BIG_INTEGER;
            }
            return null;
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public final Number getNumberValue() throws IOException {
            _checkIsNumber();
            return (Number) _currentObject();
        }

        @Override // com.fasterxml.jackson.core.JsonParser
        public Object getEmbeddedObject() {
            if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                return _currentObject();
            }
            return null;
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase, com.fasterxml.jackson.core.JsonParser
        public byte[] getBinaryValue(Base64Variant b64variant) throws IOException {
            if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT) {
                Object ob = _currentObject();
                if (ob instanceof byte[]) {
                    return (byte[]) ob;
                }
            }
            if (this._currToken != JsonToken.VALUE_STRING) {
                throw _constructError("Current token (" + this._currToken + ") not VALUE_STRING (or VALUE_EMBEDDED_OBJECT with byte[]), can not access as binary");
            }
            String str = getText();
            if (str == null) {
                return null;
            }
            ByteArrayBuilder builder = this._byteBuilder;
            if (builder == null) {
                builder = new ByteArrayBuilder(100);
                this._byteBuilder = builder;
            } else {
                this._byteBuilder.reset();
            }
            _decodeBase64(str, builder, b64variant);
            return builder.toByteArray();
        }

        protected final Object _currentObject() {
            return this._segment.get(this._segmentPtr);
        }

        protected final void _checkIsNumber() throws JsonParseException {
            if (this._currToken == null || !this._currToken.isNumeric()) {
                throw _constructError("Current token (" + this._currToken + ") not numeric, can not use numeric value accessors");
            }
        }

        @Override // com.fasterxml.jackson.core.base.ParserMinimalBase
        protected void _handleEOF() throws JsonParseException {
            _throwInternal();
        }
    }

    protected static final class Segment {
        public static final int TOKENS_PER_SEGMENT = 16;
        private static final JsonToken[] TOKEN_TYPES_BY_INDEX = new JsonToken[16];
        protected Segment _next;
        protected long _tokenTypes;
        protected final Object[] _tokens = new Object[16];

        static {
            JsonToken[] t = JsonToken.values();
            System.arraycopy(t, 1, TOKEN_TYPES_BY_INDEX, 1, Math.min(15, t.length - 1));
        }

        public JsonToken type(int index) {
            long l = this._tokenTypes;
            if (index > 0) {
                l >>= index << 2;
            }
            int ix = ((int) l) & 15;
            return TOKEN_TYPES_BY_INDEX[ix];
        }

        public int rawType(int index) {
            long l = this._tokenTypes;
            if (index > 0) {
                l >>= index << 2;
            }
            int ix = ((int) l) & 15;
            return ix;
        }

        public Object get(int index) {
            return this._tokens[index];
        }

        public Segment next() {
            return this._next;
        }

        public Segment append(int index, JsonToken tokenType) {
            if (index < 16) {
                set(index, tokenType);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, tokenType);
            return this._next;
        }

        public Segment append(int index, JsonToken tokenType, Object value) {
            if (index < 16) {
                set(index, tokenType, value);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, tokenType, value);
            return this._next;
        }

        public Segment appendRaw(int index, int rawTokenType, Object value) {
            if (index < 16) {
                set(index, rawTokenType, value);
                return null;
            }
            this._next = new Segment();
            this._next.set(0, rawTokenType, value);
            return this._next;
        }

        public void set(int index, JsonToken tokenType) {
            long typeCode = tokenType.ordinal();
            if (index > 0) {
                typeCode <<= index << 2;
            }
            this._tokenTypes |= typeCode;
        }

        public void set(int index, JsonToken tokenType, Object value) {
            this._tokens[index] = value;
            long typeCode = tokenType.ordinal();
            if (index > 0) {
                typeCode <<= index << 2;
            }
            this._tokenTypes |= typeCode;
        }

        private void set(int index, int rawTokenType, Object value) {
            this._tokens[index] = value;
            long typeCode = rawTokenType;
            if (index > 0) {
                typeCode <<= index << 2;
            }
            this._tokenTypes |= typeCode;
        }
    }
}
