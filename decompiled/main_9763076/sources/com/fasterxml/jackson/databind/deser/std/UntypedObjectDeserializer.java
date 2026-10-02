package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public class UntypedObjectDeserializer extends StdDeserializer<Object> {
    private static final Object[] NO_OBJECTS = new Object[0];

    public UntypedObjectDeserializer() {
        super((Class<?>) Object.class);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        switch (jp.getCurrentToken()) {
            case START_OBJECT:
                return mapObject(jp, ctxt);
            case END_OBJECT:
            case END_ARRAY:
            default:
                throw ctxt.mappingException(Object.class);
            case START_ARRAY:
                return mapArray(jp, ctxt);
            case FIELD_NAME:
                return mapObject(jp, ctxt);
            case VALUE_EMBEDDED_OBJECT:
                return jp.getEmbeddedObject();
            case VALUE_STRING:
                return jp.getText();
            case VALUE_NUMBER_INT:
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jp.getBigIntegerValue();
                }
                return jp.getNumberValue();
            case VALUE_NUMBER_FLOAT:
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jp.getDecimalValue();
                }
                return Double.valueOf(jp.getDoubleValue());
            case VALUE_TRUE:
                return Boolean.TRUE;
            case VALUE_FALSE:
                return Boolean.FALSE;
            case VALUE_NULL:
                return null;
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        JsonToken t = jp.getCurrentToken();
        switch (t) {
            case START_OBJECT:
            case START_ARRAY:
            case FIELD_NAME:
                return typeDeserializer.deserializeTypedFromAny(jp, ctxt);
            case END_OBJECT:
            case END_ARRAY:
            default:
                throw ctxt.mappingException(Object.class);
            case VALUE_EMBEDDED_OBJECT:
                return jp.getEmbeddedObject();
            case VALUE_STRING:
                return jp.getText();
            case VALUE_NUMBER_INT:
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS)) {
                    return jp.getBigIntegerValue();
                }
                return Integer.valueOf(jp.getIntValue());
            case VALUE_NUMBER_FLOAT:
                if (ctxt.isEnabled(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)) {
                    return jp.getDecimalValue();
                }
                return Double.valueOf(jp.getDoubleValue());
            case VALUE_TRUE:
                return Boolean.TRUE;
            case VALUE_FALSE:
                return Boolean.FALSE;
            case VALUE_NULL:
                return null;
        }
    }

    protected Object mapArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (ctxt.isEnabled(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY)) {
            return mapArrayToArray(jp, ctxt);
        }
        if (jp.nextToken() == JsonToken.END_ARRAY) {
            return new ArrayList(4);
        }
        ObjectBuffer buffer = ctxt.leaseObjectBuffer();
        Object[] values = buffer.resetAndStart();
        int ptr = 0;
        int totalSize = 0;
        while (true) {
            Object value = deserialize(jp, ctxt);
            totalSize++;
            if (ptr >= values.length) {
                values = buffer.appendCompletedChunk(values);
                ptr = 0;
            }
            int ptr2 = ptr + 1;
            values[ptr] = value;
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                ArrayList<Object> result = new ArrayList<>((totalSize >> 3) + totalSize + 1);
                buffer.completeAndClearBuffer(values, ptr2, result);
                return result;
            }
            ptr = ptr2;
        }
    }

    protected Object mapObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        if (t != JsonToken.FIELD_NAME) {
            return new LinkedHashMap(4);
        }
        String field1 = jp.getText();
        jp.nextToken();
        Object value1 = deserialize(jp, ctxt);
        if (jp.nextToken() != JsonToken.FIELD_NAME) {
            LinkedHashMap<String, Object> result = new LinkedHashMap<>(4);
            result.put(field1, value1);
            return result;
        }
        String field2 = jp.getText();
        jp.nextToken();
        Object value2 = deserialize(jp, ctxt);
        if (jp.nextToken() != JsonToken.FIELD_NAME) {
            LinkedHashMap<String, Object> result2 = new LinkedHashMap<>(4);
            result2.put(field1, value1);
            result2.put(field2, value2);
            return result2;
        }
        LinkedHashMap<String, Object> result3 = new LinkedHashMap<>();
        result3.put(field1, value1);
        result3.put(field2, value2);
        do {
            String fieldName = jp.getText();
            jp.nextToken();
            result3.put(fieldName, deserialize(jp, ctxt));
        } while (jp.nextToken() != JsonToken.END_OBJECT);
        return result3;
    }

    protected Object[] mapArrayToArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (jp.nextToken() == JsonToken.END_ARRAY) {
            return NO_OBJECTS;
        }
        ObjectBuffer buffer = ctxt.leaseObjectBuffer();
        Object[] values = buffer.resetAndStart();
        int ptr = 0;
        while (true) {
            Object value = deserialize(jp, ctxt);
            if (ptr >= values.length) {
                values = buffer.appendCompletedChunk(values);
                ptr = 0;
            }
            int ptr2 = ptr + 1;
            values[ptr] = value;
            if (jp.nextToken() == JsonToken.END_ARRAY) {
                return buffer.completeAndClearBuffer(values, ptr2);
            }
            ptr = ptr2;
        }
    }
}
