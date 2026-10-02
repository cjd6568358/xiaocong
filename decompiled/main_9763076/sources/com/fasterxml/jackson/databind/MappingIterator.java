package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MappingIterator<T> implements Iterator<T> {
    protected static final MappingIterator<?> EMPTY_ITERATOR = new MappingIterator<>(null, null, null, null, false, null);
    protected final boolean _closeParser;
    protected final DeserializationContext _context;
    protected final JsonDeserializer<T> _deserializer;
    protected boolean _hasNextChecked;
    protected JsonParser _parser;
    protected final JavaType _type;
    protected final T _updatedValue;

    protected MappingIterator(JavaType type, JsonParser jp, DeserializationContext ctxt, JsonDeserializer<?> deser) {
        this(type, jp, ctxt, deser, true, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected MappingIterator(JavaType type, JsonParser jp, DeserializationContext ctxt, JsonDeserializer<?> jsonDeserializer, boolean closeParser, Object obj) {
        this._type = type;
        this._parser = jp;
        this._context = ctxt;
        this._deserializer = jsonDeserializer;
        if (jp != null && jp.getCurrentToken() == JsonToken.START_ARRAY) {
            JsonStreamContext sc = jp.getParsingContext();
            if (!sc.inRoot()) {
                jp.clearCurrentToken();
            }
        }
        this._closeParser = closeParser;
        if (obj == 0) {
            this._updatedValue = null;
        } else {
            this._updatedValue = obj;
        }
    }

    protected static <T> MappingIterator<T> emptyIterator() {
        return (MappingIterator<T>) EMPTY_ITERATOR;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        try {
            return hasNextValue();
        } catch (JsonMappingException e) {
            throw new RuntimeJsonMappingException(e.getMessage(), e);
        } catch (IOException e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    @Override // java.util.Iterator
    public T next() {
        try {
            return nextValue();
        } catch (JsonMappingException e) {
            throw new RuntimeJsonMappingException(e.getMessage(), e);
        } catch (IOException e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public boolean hasNextValue() throws IOException {
        if (this._parser == null) {
            return false;
        }
        if (!this._hasNextChecked) {
            JsonToken t = this._parser.getCurrentToken();
            this._hasNextChecked = true;
            if (t == null) {
                JsonToken t2 = this._parser.nextToken();
                if (t2 == null) {
                    JsonParser jp = this._parser;
                    this._parser = null;
                    if (!this._closeParser) {
                        return false;
                    }
                    jp.close();
                    return false;
                }
                if (t2 == JsonToken.END_ARRAY) {
                    return false;
                }
            }
        }
        return true;
    }

    public T nextValue() throws IOException {
        T result;
        if (!this._hasNextChecked && !hasNextValue()) {
            throw new NoSuchElementException();
        }
        if (this._parser == null) {
            throw new NoSuchElementException();
        }
        this._hasNextChecked = false;
        if (this._updatedValue == null) {
            result = this._deserializer.deserialize(this._parser, this._context);
        } else {
            this._deserializer.deserialize(this._parser, this._context, this._updatedValue);
            result = this._updatedValue;
        }
        this._parser.clearCurrentToken();
        return result;
    }
}
