package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class PropertyValueBuffer {
    private PropertyValue _buffered;
    protected final DeserializationContext _context;
    protected final Object[] _creatorParameters;
    private int _paramsNeeded;
    protected final JsonParser _parser;

    public PropertyValueBuffer(JsonParser jp, DeserializationContext ctxt, int paramCount) {
        this._parser = jp;
        this._context = ctxt;
        this._paramsNeeded = paramCount;
        this._creatorParameters = new Object[paramCount];
    }

    public void inject(SettableBeanProperty[] injectableProperties) {
        int len = injectableProperties.length;
        for (int i = 0; i < len; i++) {
            SettableBeanProperty prop = injectableProperties[i];
            if (prop != null) {
                this._creatorParameters[i] = this._context.findInjectableValue(prop.getInjectableValueId(), prop, null);
            }
        }
    }

    protected final Object[] getParameters(Object[] defaults) {
        Object value;
        if (defaults != null) {
            int len = this._creatorParameters.length;
            for (int i = 0; i < len; i++) {
                if (this._creatorParameters[i] == null && (value = defaults[i]) != null) {
                    this._creatorParameters[i] = value;
                }
            }
        }
        return this._creatorParameters;
    }

    protected PropertyValue buffered() {
        return this._buffered;
    }

    public boolean isComplete() {
        return this._paramsNeeded <= 0;
    }

    public boolean assignParameter(int index, Object value) {
        this._creatorParameters[index] = value;
        int i = this._paramsNeeded - 1;
        this._paramsNeeded = i;
        return i <= 0;
    }

    public void bufferProperty(SettableBeanProperty prop, Object value) {
        this._buffered = new PropertyValue.Regular(this._buffered, value, prop);
    }

    public void bufferAnyProperty(SettableAnyProperty prop, String propName, Object value) {
        this._buffered = new PropertyValue.Any(this._buffered, value, prop, propName);
    }

    public void bufferMapProperty(Object key, Object value) {
        this._buffered = new PropertyValue.Map(this._buffered, value, key);
    }
}
