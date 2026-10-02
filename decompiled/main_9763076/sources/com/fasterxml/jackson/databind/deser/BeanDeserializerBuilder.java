package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdValueProperty;
import com.fasterxml.jackson.databind.deser.impl.ValueInjector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.util.Annotations;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BeanDeserializerBuilder {
    protected SettableAnyProperty _anySetter;
    protected HashMap<String, SettableBeanProperty> _backRefProperties;
    protected final BeanDescription _beanDesc;
    protected AnnotatedMethod _buildMethod;
    protected JsonPOJOBuilder.Value _builderConfig;
    protected final boolean _defaultViewInclusion;
    protected HashSet<String> _ignorableProps;
    protected boolean _ignoreAllUnknown;
    protected List<ValueInjector> _injectables;
    protected ObjectIdReader _objectIdReader;
    protected final Map<String, SettableBeanProperty> _properties = new LinkedHashMap();
    protected ValueInstantiator _valueInstantiator;

    public BeanDeserializerBuilder(BeanDescription beanDesc, DeserializationConfig config) {
        this._beanDesc = beanDesc;
        this._defaultViewInclusion = config.isEnabled(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

    protected BeanDeserializerBuilder(BeanDeserializerBuilder src) {
        this._beanDesc = src._beanDesc;
        this._anySetter = src._anySetter;
        this._ignoreAllUnknown = src._ignoreAllUnknown;
        this._properties.putAll(src._properties);
        this._backRefProperties = _copy(src._backRefProperties);
        this._ignorableProps = src._ignorableProps;
        this._valueInstantiator = src._valueInstantiator;
        this._defaultViewInclusion = src._defaultViewInclusion;
    }

    private static HashMap<String, SettableBeanProperty> _copy(HashMap<String, SettableBeanProperty> src) {
        if (src == null) {
            return null;
        }
        return new HashMap<>(src);
    }

    public void addOrReplaceProperty(SettableBeanProperty prop, boolean allowOverride) {
        this._properties.put(prop.getName(), prop);
    }

    public void addProperty(SettableBeanProperty prop) {
        SettableBeanProperty old = this._properties.put(prop.getName(), prop);
        if (old != null && old != prop) {
            throw new IllegalArgumentException("Duplicate property '" + prop.getName() + "' for " + this._beanDesc.getType());
        }
    }

    public void addBackReferenceProperty(String referenceName, SettableBeanProperty prop) {
        if (this._backRefProperties == null) {
            this._backRefProperties = new HashMap<>(4);
        }
        this._backRefProperties.put(referenceName, prop);
        if (this._properties != null) {
            this._properties.remove(prop.getName());
        }
    }

    public void addInjectable(String propertyName, JavaType propertyType, Annotations contextAnnotations, AnnotatedMember member, Object valueId) {
        if (this._injectables == null) {
            this._injectables = new ArrayList();
        }
        this._injectables.add(new ValueInjector(propertyName, propertyType, contextAnnotations, member, valueId));
    }

    public void addIgnorable(String propName) {
        if (this._ignorableProps == null) {
            this._ignorableProps = new HashSet<>();
        }
        this._ignorableProps.add(propName);
    }

    public void addCreatorProperty(BeanPropertyDefinition propDef) {
    }

    public void setAnySetter(SettableAnyProperty s) {
        if (this._anySetter != null && s != null) {
            throw new IllegalStateException("_anySetter already set to non-null");
        }
        this._anySetter = s;
    }

    public void setIgnoreUnknownProperties(boolean ignore) {
        this._ignoreAllUnknown = ignore;
    }

    public void setValueInstantiator(ValueInstantiator inst) {
        this._valueInstantiator = inst;
    }

    public void setObjectIdReader(ObjectIdReader r) {
        this._objectIdReader = r;
    }

    public void setPOJOBuilder(AnnotatedMethod buildMethod, JsonPOJOBuilder.Value config) {
        this._buildMethod = buildMethod;
        this._builderConfig = config;
    }

    public Iterator<SettableBeanProperty> getProperties() {
        return this._properties.values().iterator();
    }

    public SettableBeanProperty findProperty(String propertyName) {
        return this._properties.get(propertyName);
    }

    public boolean hasProperty(String propertyName) {
        return findProperty(propertyName) != null;
    }

    public SettableBeanProperty removeProperty(String name) {
        return this._properties.remove(name);
    }

    public SettableAnyProperty getAnySetter() {
        return this._anySetter;
    }

    public ValueInstantiator getValueInstantiator() {
        return this._valueInstantiator;
    }

    public List<ValueInjector> getInjectables() {
        return this._injectables;
    }

    public ObjectIdReader getObjectIdReader() {
        return this._objectIdReader;
    }

    public AnnotatedMethod getBuildMethod() {
        return this._buildMethod;
    }

    public JsonPOJOBuilder.Value getBuilderConfig() {
        return this._builderConfig;
    }

    public BeanDeserializer build() {
        Collection<SettableBeanProperty> props = this._properties.values();
        BeanPropertyMap propertyMap = new BeanPropertyMap(props);
        propertyMap.assignIndexes();
        boolean anyViews = !this._defaultViewInclusion;
        if (!anyViews) {
            for (SettableBeanProperty prop : props) {
                if (prop.hasViews()) {
                    anyViews = true;
                    break;
                }
            }
        }
        if (this._objectIdReader != null) {
            ObjectIdValueProperty prop2 = new ObjectIdValueProperty(this._objectIdReader);
            propertyMap = propertyMap.withProperty(prop2);
        }
        return new BeanDeserializer(this, this._beanDesc, propertyMap, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, anyViews);
    }

    public AbstractDeserializer buildAbstract() {
        return new AbstractDeserializer(this, this._beanDesc, this._backRefProperties);
    }

    public JsonDeserializer<?> buildBuilderBased(JavaType valueType, String expBuildMethodName) {
        if (this._buildMethod == null) {
            throw new IllegalArgumentException("Builder class " + this._beanDesc.getBeanClass().getName() + " does not have build method '" + expBuildMethodName + "()'");
        }
        Class<?> rawBuildType = this._buildMethod.getRawReturnType();
        if (!valueType.getRawClass().isAssignableFrom(rawBuildType)) {
            throw new IllegalArgumentException("Build method '" + this._buildMethod.getFullName() + " has bad return type (" + rawBuildType.getName() + "), not compatible with POJO type (" + valueType.getRawClass().getName() + ")");
        }
        Collection<SettableBeanProperty> props = this._properties.values();
        BeanPropertyMap propertyMap = new BeanPropertyMap(props);
        propertyMap.assignIndexes();
        boolean anyViews = !this._defaultViewInclusion;
        if (!anyViews) {
            for (SettableBeanProperty prop : props) {
                if (prop.hasViews()) {
                    anyViews = true;
                    break;
                }
            }
        }
        if (this._objectIdReader != null) {
            ObjectIdValueProperty prop2 = new ObjectIdValueProperty(this._objectIdReader);
            propertyMap = propertyMap.withProperty(prop2);
        }
        return new BuilderBasedDeserializer(this, this._beanDesc, propertyMap, this._backRefProperties, this._ignorableProps, this._ignoreAllUnknown, anyViews);
    }
}
