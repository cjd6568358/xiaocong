package com.fasterxml.jackson.databind.ser.impl;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SimpleFilterProvider extends FilterProvider {
    protected boolean _cfgFailOnUnknownId;
    protected BeanPropertyFilter _defaultFilter;
    protected final Map<String, BeanPropertyFilter> _filtersById;

    public SimpleFilterProvider() {
        this(new HashMap());
    }

    public SimpleFilterProvider(Map<String, BeanPropertyFilter> mapping) {
        this._cfgFailOnUnknownId = true;
        this._filtersById = mapping;
    }

    public SimpleFilterProvider setDefaultFilter(BeanPropertyFilter f) {
        this._defaultFilter = f;
        return this;
    }

    public BeanPropertyFilter getDefaultFilter() {
        return this._defaultFilter;
    }

    public SimpleFilterProvider setFailOnUnknownId(boolean state) {
        this._cfgFailOnUnknownId = state;
        return this;
    }

    public boolean willFailOnUnknownId() {
        return this._cfgFailOnUnknownId;
    }

    public SimpleFilterProvider addFilter(String id, BeanPropertyFilter filter) {
        this._filtersById.put(id, filter);
        return this;
    }

    public BeanPropertyFilter removeFilter(String id) {
        return this._filtersById.remove(id);
    }

    @Override // com.fasterxml.jackson.databind.ser.FilterProvider
    public BeanPropertyFilter findFilter(Object filterId) {
        BeanPropertyFilter f = this._filtersById.get(filterId);
        if (f == null && (f = this._defaultFilter) == null && this._cfgFailOnUnknownId) {
            throw new IllegalArgumentException("No filter configured with id '" + filterId + "' (type " + filterId.getClass().getName() + ")");
        }
        return f;
    }
}
