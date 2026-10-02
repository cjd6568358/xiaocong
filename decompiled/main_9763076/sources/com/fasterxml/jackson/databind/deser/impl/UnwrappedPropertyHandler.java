package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UnwrappedPropertyHandler {
    protected final ArrayList<SettableBeanProperty> _properties = new ArrayList<>();

    public void addProperty(SettableBeanProperty property) {
        this._properties.add(property);
    }

    public void renameAll(NameTransformer transformer) {
        JsonDeserializer<?> jsonDeserializerUnwrappingDeserializer;
        ArrayList<SettableBeanProperty> oldProps = new ArrayList<>(this._properties);
        this._properties.clear();
        for (SettableBeanProperty prop : oldProps) {
            String newName = transformer.transform(prop.getName());
            SettableBeanProperty prop2 = prop.withName(newName);
            JsonDeserializer<?> deser = prop2.getValueDeserializer();
            if (deser != null && (jsonDeserializerUnwrappingDeserializer = deser.unwrappingDeserializer(transformer)) != deser) {
                prop2 = prop2.withValueDeserializer(jsonDeserializerUnwrappingDeserializer);
            }
            this._properties.add(prop2);
        }
    }

    public Object processUnwrapped(JsonParser originalParser, DeserializationContext ctxt, Object bean, TokenBuffer buffered) throws IOException {
        int len = this._properties.size();
        for (int i = 0; i < len; i++) {
            SettableBeanProperty prop = this._properties.get(i);
            JsonParser jp = buffered.asParser();
            jp.nextToken();
            prop.deserializeAndSet(jp, ctxt, bean);
        }
        return bean;
    }
}
