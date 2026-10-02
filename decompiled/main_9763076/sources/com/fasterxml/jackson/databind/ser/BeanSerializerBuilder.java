package com.fasterxml.jackson.databind.ser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BeanSerializerBuilder {
    private static final BeanPropertyWriter[] NO_PROPERTIES = new BeanPropertyWriter[0];
    protected AnyGetterWriter _anyGetter;
    protected final BeanDescription _beanDesc;
    protected Object _filterId;
    protected BeanPropertyWriter[] _filteredProperties;
    protected ObjectIdWriter _objectIdWriter;
    protected List<BeanPropertyWriter> _properties;
    protected AnnotatedMember _typeId;

    public BeanSerializerBuilder(BeanDescription beanDesc) {
        this._beanDesc = beanDesc;
    }

    protected BeanSerializerBuilder(BeanSerializerBuilder src) {
        this._beanDesc = src._beanDesc;
        this._properties = src._properties;
        this._filteredProperties = src._filteredProperties;
        this._anyGetter = src._anyGetter;
        this._filterId = src._filterId;
    }

    public void setProperties(List<BeanPropertyWriter> properties) {
        this._properties = properties;
    }

    public void setFilteredProperties(BeanPropertyWriter[] properties) {
        this._filteredProperties = properties;
    }

    public void setAnyGetter(AnyGetterWriter anyGetter) {
        this._anyGetter = anyGetter;
    }

    public void setFilterId(Object filterId) {
        this._filterId = filterId;
    }

    public void setTypeId(AnnotatedMember idProp) {
        if (this._typeId != null) {
            throw new IllegalArgumentException("Multiple type ids specified with " + this._typeId + " and " + idProp);
        }
        this._typeId = idProp;
    }

    public void setObjectIdWriter(ObjectIdWriter w) {
        this._objectIdWriter = w;
    }

    public BeanDescription getBeanDescription() {
        return this._beanDesc;
    }

    public List<BeanPropertyWriter> getProperties() {
        return this._properties;
    }

    public boolean hasProperties() {
        return this._properties != null && this._properties.size() > 0;
    }

    public BeanPropertyWriter[] getFilteredProperties() {
        return this._filteredProperties;
    }

    public AnyGetterWriter getAnyGetter() {
        return this._anyGetter;
    }

    public Object getFilterId() {
        return this._filterId;
    }

    public AnnotatedMember getTypeId() {
        return this._typeId;
    }

    public ObjectIdWriter getObjectIdWriter() {
        return this._objectIdWriter;
    }

    public JsonSerializer<?> build() {
        BeanPropertyWriter[] properties;
        if (this._properties == null || this._properties.isEmpty()) {
            if (this._anyGetter == null) {
                return null;
            }
            properties = NO_PROPERTIES;
        } else {
            properties = (BeanPropertyWriter[]) this._properties.toArray(new BeanPropertyWriter[this._properties.size()]);
        }
        return new BeanSerializer(this._beanDesc.getType(), this, properties, this._filteredProperties);
    }

    public BeanSerializer createDummy() {
        return BeanSerializer.createDummy(this._beanDesc.getType());
    }
}
