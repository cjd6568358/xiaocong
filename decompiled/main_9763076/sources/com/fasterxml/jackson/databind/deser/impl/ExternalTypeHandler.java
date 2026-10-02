package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ExternalTypeHandler {
    private final HashMap<String, Integer> _nameToPropertyIndex;
    private final ExtTypedProperty[] _properties;
    private final TokenBuffer[] _tokens;
    private final String[] _typeIds;

    protected ExternalTypeHandler(ExtTypedProperty[] properties, HashMap<String, Integer> nameToPropertyIndex, String[] typeIds, TokenBuffer[] tokens) {
        this._properties = properties;
        this._nameToPropertyIndex = nameToPropertyIndex;
        this._typeIds = typeIds;
        this._tokens = tokens;
    }

    protected ExternalTypeHandler(ExternalTypeHandler h) {
        this._properties = h._properties;
        this._nameToPropertyIndex = h._nameToPropertyIndex;
        int len = this._properties.length;
        this._typeIds = new String[len];
        this._tokens = new TokenBuffer[len];
    }

    public ExternalTypeHandler start() {
        return new ExternalTypeHandler(this);
    }

    public boolean handleTypePropertyValue(JsonParser jp, DeserializationContext ctxt, String propName, Object bean) throws IOException {
        Integer I = this._nameToPropertyIndex.get(propName);
        if (I == null) {
            return false;
        }
        int index = I.intValue();
        ExtTypedProperty prop = this._properties[index];
        if (!prop.hasTypePropertyName(propName)) {
            return false;
        }
        this._typeIds[index] = jp.getText();
        boolean canDeserialize = (bean == null || this._tokens[index] == null) ? false : true;
        if (canDeserialize) {
            _deserializeAndSet(jp, ctxt, bean, index);
            this._typeIds[index] = null;
            this._tokens[index] = null;
        }
        return true;
    }

    public boolean handlePropertyValue(JsonParser jp, DeserializationContext ctxt, String propName, Object bean) throws IOException {
        boolean canDeserialize = false;
        Integer I = this._nameToPropertyIndex.get(propName);
        if (I == null) {
            return false;
        }
        int index = I.intValue();
        ExtTypedProperty prop = this._properties[index];
        if (prop.hasTypePropertyName(propName)) {
            this._typeIds[index] = jp.getText();
            jp.skipChildren();
            if (bean != null && this._tokens[index] != null) {
                canDeserialize = true;
            }
        } else {
            TokenBuffer tokens = new TokenBuffer(jp.getCodec());
            tokens.copyCurrentStructure(jp);
            this._tokens[index] = tokens;
            if (bean != null && this._typeIds[index] != null) {
                canDeserialize = true;
            }
        }
        if (canDeserialize) {
            _deserializeAndSet(jp, ctxt, bean, index);
            this._typeIds[index] = null;
            this._tokens[index] = null;
        }
        return true;
    }

    public Object complete(JsonParser jp, DeserializationContext ctxt, Object bean) throws IOException {
        int len = this._properties.length;
        for (int i = 0; i < len; i++) {
            if (this._typeIds[i] == null) {
                if (this._tokens[i] != null) {
                    throw ctxt.mappingException("Missing external type id property '" + this._properties[i].getTypePropertyName());
                }
            } else {
                if (this._tokens[i] == null) {
                    SettableBeanProperty prop = this._properties[i].getProperty();
                    throw ctxt.mappingException("Missing property '" + prop.getName() + "' for external type id '" + this._properties[i].getTypePropertyName());
                }
                _deserializeAndSet(jp, ctxt, bean, i);
            }
        }
        return bean;
    }

    public Object complete(JsonParser jp, DeserializationContext ctxt, PropertyValueBuffer buffer, PropertyBasedCreator creator) throws IOException {
        int len = this._properties.length;
        Object[] values = new Object[len];
        for (int i = 0; i < len; i++) {
            if (this._typeIds[i] == null) {
                if (this._tokens[i] != null) {
                    throw ctxt.mappingException("Missing external type id property '" + this._properties[i].getTypePropertyName());
                }
            } else {
                if (this._tokens[i] == null) {
                    throw ctxt.mappingException("Missing property '" + this._properties[i].getProperty().getName() + "' for external type id '" + this._properties[i].getTypePropertyName());
                }
                values[i] = _deserialize(jp, ctxt, i);
            }
        }
        for (int i2 = 0; i2 < len; i2++) {
            SettableBeanProperty prop = this._properties[i2].getProperty();
            if (creator.findCreatorProperty(prop.getName()) != null) {
                buffer.assignParameter(prop.getPropertyIndex(), values[i2]);
            }
        }
        Object bean = creator.build(ctxt, buffer);
        for (int i3 = 0; i3 < len; i3++) {
            SettableBeanProperty prop2 = this._properties[i3].getProperty();
            if (creator.findCreatorProperty(prop2.getName()) == null) {
                prop2.set(bean, values[i3]);
            }
        }
        return bean;
    }

    protected final Object _deserialize(JsonParser jp, DeserializationContext ctxt, int index) throws IOException {
        TokenBuffer merged = new TokenBuffer(jp.getCodec());
        merged.writeStartArray();
        merged.writeString(this._typeIds[index]);
        JsonParser p2 = this._tokens[index].asParser(jp);
        p2.nextToken();
        merged.copyCurrentStructure(p2);
        merged.writeEndArray();
        JsonParser p3 = merged.asParser(jp);
        p3.nextToken();
        return this._properties[index].getProperty().deserialize(p3, ctxt);
    }

    protected final void _deserializeAndSet(JsonParser jp, DeserializationContext ctxt, Object bean, int index) throws IOException {
        TokenBuffer merged = new TokenBuffer(jp.getCodec());
        merged.writeStartArray();
        merged.writeString(this._typeIds[index]);
        JsonParser p2 = this._tokens[index].asParser(jp);
        p2.nextToken();
        merged.copyCurrentStructure(p2);
        merged.writeEndArray();
        JsonParser p3 = merged.asParser(jp);
        p3.nextToken();
        this._properties[index].getProperty().deserializeAndSet(p3, ctxt, bean);
    }

    public static class Builder {
        private final ArrayList<ExtTypedProperty> _properties = new ArrayList<>();
        private final HashMap<String, Integer> _nameToPropertyIndex = new HashMap<>();

        public void addExternal(SettableBeanProperty property, String extPropName) {
            Integer index = Integer.valueOf(this._properties.size());
            this._properties.add(new ExtTypedProperty(property, extPropName));
            this._nameToPropertyIndex.put(property.getName(), index);
            this._nameToPropertyIndex.put(extPropName, index);
        }

        public ExternalTypeHandler build() {
            return new ExternalTypeHandler((ExtTypedProperty[]) this._properties.toArray(new ExtTypedProperty[this._properties.size()]), this._nameToPropertyIndex, null, null);
        }
    }

    private static final class ExtTypedProperty {
        private final SettableBeanProperty _property;
        private final String _typePropertyName;

        public ExtTypedProperty(SettableBeanProperty property, String typePropertyName) {
            this._property = property;
            this._typePropertyName = typePropertyName;
        }

        public boolean hasTypePropertyName(String n) {
            return n.equals(this._typePropertyName);
        }

        public String getTypePropertyName() {
            return this._typePropertyName;
        }

        public SettableBeanProperty getProperty() {
            return this._property;
        }
    }
}
