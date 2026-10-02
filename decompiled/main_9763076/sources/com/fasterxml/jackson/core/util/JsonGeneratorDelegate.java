package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JsonGeneratorDelegate extends JsonGenerator {
    protected JsonGenerator delegate;

    public JsonGeneratorDelegate(JsonGenerator d) {
        this.delegate = d;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.delegate.close();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void copyCurrentEvent(JsonParser jp) throws IOException {
        this.delegate.copyCurrentEvent(jp);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void copyCurrentStructure(JsonParser jp) throws IOException {
        this.delegate.copyCurrentStructure(jp);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator disable(JsonGenerator.Feature f) {
        return this.delegate.disable(f);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator enable(JsonGenerator.Feature f) {
        return this.delegate.enable(f);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void flush() throws IOException {
        this.delegate.flush();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public ObjectCodec getCodec() {
        return this.delegate.getCodec();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonStreamContext getOutputContext() {
        return this.delegate.getOutputContext();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void setSchema(FormatSchema schema) {
        this.delegate.setSchema(schema);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public boolean canUseSchema(FormatSchema schema) {
        return this.delegate.canUseSchema(schema);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return this.delegate.version();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public Object getOutputTarget() {
        return this.delegate.getOutputTarget();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public boolean isClosed() {
        return this.delegate.isClosed();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public boolean isEnabled(JsonGenerator.Feature f) {
        return this.delegate.isEnabled(f);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator setCodec(ObjectCodec oc) {
        this.delegate.setCodec(oc);
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public JsonGenerator useDefaultPrettyPrinter() {
        this.delegate.useDefaultPrettyPrinter();
        return this;
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeBinary(Base64Variant b64variant, byte[] data, int offset, int len) throws IOException {
        this.delegate.writeBinary(b64variant, data, offset, len);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeBoolean(boolean state) throws IOException {
        this.delegate.writeBoolean(state);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeEndArray() throws IOException {
        this.delegate.writeEndArray();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeEndObject() throws IOException {
        this.delegate.writeEndObject();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(String name) throws IOException {
        this.delegate.writeFieldName(name);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeFieldName(SerializableString name) throws IOException {
        this.delegate.writeFieldName(name);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNull() throws IOException {
        this.delegate.writeNull();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(int v) throws IOException {
        this.delegate.writeNumber(v);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(long v) throws IOException {
        this.delegate.writeNumber(v);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigInteger v) throws IOException {
        this.delegate.writeNumber(v);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(double v) throws IOException {
        this.delegate.writeNumber(v);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(float v) throws IOException {
        this.delegate.writeNumber(v);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(BigDecimal v) throws IOException {
        this.delegate.writeNumber(v);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeNumber(String encodedValue) throws UnsupportedOperationException, IOException {
        this.delegate.writeNumber(encodedValue);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeObject(Object pojo) throws IOException {
        this.delegate.writeObject(pojo);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String text) throws IOException {
        this.delegate.writeRaw(text);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(String text, int offset, int len) throws IOException {
        this.delegate.writeRaw(text, offset, len);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char[] text, int offset, int len) throws IOException {
        this.delegate.writeRaw(text, offset, len);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRaw(char c) throws IOException {
        this.delegate.writeRaw(c);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(String text) throws IOException {
        this.delegate.writeRawValue(text);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(String text, int offset, int len) throws IOException {
        this.delegate.writeRawValue(text, offset, len);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawValue(char[] text, int offset, int len) throws IOException {
        this.delegate.writeRawValue(text, offset, len);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeStartArray() throws IOException {
        this.delegate.writeStartArray();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeStartObject() throws IOException {
        this.delegate.writeStartObject();
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(String text) throws IOException {
        this.delegate.writeString(text);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(char[] text, int offset, int len) throws IOException {
        this.delegate.writeString(text, offset, len);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeString(SerializableString text) throws IOException {
        this.delegate.writeString(text);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeRawUTF8String(byte[] text, int offset, int length) throws IOException {
        this.delegate.writeRawUTF8String(text, offset, length);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeUTF8String(byte[] text, int offset, int length) throws IOException {
        this.delegate.writeUTF8String(text, offset, length);
    }

    @Override // com.fasterxml.jackson.core.JsonGenerator
    public void writeTree(TreeNode rootNode) throws IOException {
        this.delegate.writeTree(rootNode);
    }
}
