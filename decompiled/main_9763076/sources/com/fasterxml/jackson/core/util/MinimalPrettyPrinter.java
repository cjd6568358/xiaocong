package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MinimalPrettyPrinter implements PrettyPrinter {
    public static final String DEFAULT_ROOT_VALUE_SEPARATOR = " ";
    protected String _rootValueSeparator;

    public MinimalPrettyPrinter() {
        this(DEFAULT_ROOT_VALUE_SEPARATOR);
    }

    public MinimalPrettyPrinter(String rootValueSeparator) {
        this._rootValueSeparator = DEFAULT_ROOT_VALUE_SEPARATOR;
        this._rootValueSeparator = rootValueSeparator;
    }

    public void setRootValueSeparator(String sep) {
        this._rootValueSeparator = sep;
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeRootValueSeparator(JsonGenerator jg) throws IOException {
        if (this._rootValueSeparator != null) {
            jg.writeRaw(this._rootValueSeparator);
        }
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeStartObject(JsonGenerator jg) throws IOException {
        jg.writeRaw('{');
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void beforeObjectEntries(JsonGenerator jg) throws IOException {
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeObjectFieldValueSeparator(JsonGenerator jg) throws IOException {
        jg.writeRaw(':');
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeObjectEntrySeparator(JsonGenerator jg) throws IOException {
        jg.writeRaw(',');
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeEndObject(JsonGenerator jg, int nrOfEntries) throws IOException {
        jg.writeRaw('}');
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeStartArray(JsonGenerator jg) throws IOException {
        jg.writeRaw('[');
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void beforeArrayValues(JsonGenerator jg) throws IOException {
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeArrayValueSeparator(JsonGenerator jg) throws IOException {
        jg.writeRaw(',');
    }

    @Override // com.fasterxml.jackson.core.PrettyPrinter
    public void writeEndArray(JsonGenerator jg, int nrOfValues) throws IOException {
        jg.writeRaw(']');
    }
}
