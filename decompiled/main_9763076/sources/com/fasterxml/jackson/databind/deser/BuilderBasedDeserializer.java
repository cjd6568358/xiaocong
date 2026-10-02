package com.fasterxml.jackson.databind.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler;
import com.fasterxml.jackson.databind.deser.impl.ObjectIdReader;
import com.fasterxml.jackson.databind.deser.impl.PropertyBasedCreator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BuilderBasedDeserializer extends BeanDeserializerBase {
    protected final AnnotatedMethod _buildMethod;

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public /* bridge */ /* synthetic */ BeanDeserializerBase withIgnorableProperties(HashSet x0) {
        return withIgnorableProperties((HashSet<String>) x0);
    }

    public BuilderBasedDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, BeanPropertyMap properties, Map<String, SettableBeanProperty> backRefs, HashSet<String> ignorableProps, boolean ignoreAllUnknown, boolean hasViews) {
        super(builder, beanDesc, properties, backRefs, ignorableProps, ignoreAllUnknown, hasViews);
        this._buildMethod = builder.getBuildMethod();
        if (this._objectIdReader != null) {
            throw new IllegalArgumentException("Can not use Object Id with Builder-based deserialization (type " + beanDesc.getType() + ")");
        }
    }

    protected BuilderBasedDeserializer(BuilderBasedDeserializer src) {
        this(src, src._ignoreAllUnknown);
    }

    protected BuilderBasedDeserializer(BuilderBasedDeserializer src, boolean ignoreAllUnknown) {
        super(src, ignoreAllUnknown);
        this._buildMethod = src._buildMethod;
    }

    protected BuilderBasedDeserializer(BuilderBasedDeserializer src, NameTransformer unwrapper) {
        super(src, unwrapper);
        this._buildMethod = src._buildMethod;
    }

    public BuilderBasedDeserializer(BuilderBasedDeserializer src, ObjectIdReader oir) {
        super(src, oir);
        this._buildMethod = src._buildMethod;
    }

    public BuilderBasedDeserializer(BuilderBasedDeserializer src, HashSet<String> ignorableProps) {
        super(src, ignorableProps);
        this._buildMethod = src._buildMethod;
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase, com.fasterxml.jackson.databind.JsonDeserializer
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
        return new BuilderBasedDeserializer(this, unwrapper);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BuilderBasedDeserializer withObjectIdReader(ObjectIdReader oir) {
        return new BuilderBasedDeserializer(this, oir);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BuilderBasedDeserializer withIgnorableProperties(HashSet<String> ignorableProps) {
        return new BuilderBasedDeserializer(this, ignorableProps);
    }

    protected final Object finishBuild(DeserializationContext ctxt, Object builder) throws IOException {
        try {
            return this._buildMethod.getMember().invoke(builder, new Object[0]);
        } catch (Exception e) {
            wrapInstantiationProblem(e, ctxt);
            return null;
        }
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public final Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            JsonToken t2 = jp.nextToken();
            if (this._vanillaProcessing) {
                return finishBuild(ctxt, vanillaDeserialize(jp, ctxt, t2));
            }
            Object builder = deserializeFromObject(jp, ctxt);
            return finishBuild(ctxt, builder);
        }
        switch (t) {
            case VALUE_STRING:
                return finishBuild(ctxt, deserializeFromString(jp, ctxt));
            case VALUE_NUMBER_INT:
                return finishBuild(ctxt, deserializeFromNumber(jp, ctxt));
            case VALUE_NUMBER_FLOAT:
                return finishBuild(ctxt, deserializeFromDouble(jp, ctxt));
            case VALUE_EMBEDDED_OBJECT:
                return jp.getEmbeddedObject();
            case VALUE_TRUE:
            case VALUE_FALSE:
                return finishBuild(ctxt, deserializeFromBoolean(jp, ctxt));
            case START_ARRAY:
                return finishBuild(ctxt, deserializeFromArray(jp, ctxt));
            case FIELD_NAME:
            case END_OBJECT:
                return finishBuild(ctxt, deserializeFromObject(jp, ctxt));
            default:
                throw ctxt.mappingException(getBeanClass());
        }
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jp, DeserializationContext ctxt, Object builder) throws IOException {
        return finishBuild(ctxt, _deserialize(jp, ctxt, builder));
    }

    protected final Object _deserialize(JsonParser jp, DeserializationContext ctxt, Object builder) throws Exception {
        Class<?> view;
        if (this._injectables != null) {
            injectValues(ctxt, builder);
        }
        if (this._unwrappedPropertyHandler != null) {
            return deserializeWithUnwrapped(jp, ctxt, builder);
        }
        if (this._externalTypeIdHandler != null) {
            return deserializeWithExternalTypeId(jp, ctxt, builder);
        }
        if (this._needViewProcesing && (view = ctxt.getActiveView()) != null) {
            return deserializeWithView(jp, ctxt, builder, view);
        }
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        while (t == JsonToken.FIELD_NAME) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            if (prop != null) {
                try {
                    builder = prop.deserializeSetAndReturn(jp, ctxt, builder);
                } catch (Exception e) {
                    wrapAndThrow(e, builder, propName, ctxt);
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                jp.skipChildren();
            } else if (this._anySetter != null) {
                this._anySetter.deserializeAndSet(jp, ctxt, builder, propName);
            } else {
                handleUnknownProperty(jp, ctxt, builder, propName);
            }
            t = jp.nextToken();
        }
        return builder;
    }

    private final Object vanillaDeserialize(JsonParser jp, DeserializationContext ctxt, JsonToken t) throws IOException {
        Object bean = this._valueInstantiator.createUsingDefault(ctxt);
        while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            if (prop != null) {
                try {
                    bean = prop.deserializeSetAndReturn(jp, ctxt, bean);
                } catch (Exception e) {
                    wrapAndThrow(e, bean, propName, ctxt);
                }
            } else {
                _vanillaDeserializeHandleUnknown(jp, ctxt, bean, propName);
            }
            jp.nextToken();
        }
        return bean;
    }

    private final void _vanillaDeserializeHandleUnknown(JsonParser jp, DeserializationContext ctxt, Object bean, String propName) throws IOException {
        if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
            jp.skipChildren();
            return;
        }
        if (this._anySetter != null) {
            try {
                this._anySetter.deserializeAndSet(jp, ctxt, bean, propName);
                return;
            } catch (Exception e) {
                wrapAndThrow(e, bean, propName, ctxt);
                return;
            }
        }
        handleUnknownProperty(jp, ctxt, bean, propName);
    }

    public Object deserializeFromObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Class<?> view;
        if (this._nonStandardCreation) {
            if (this._unwrappedPropertyHandler != null) {
                return deserializeWithUnwrapped(jp, ctxt);
            }
            if (this._externalTypeIdHandler != null) {
                return deserializeWithExternalTypeId(jp, ctxt);
            }
            return deserializeFromObjectUsingNonDefault(jp, ctxt);
        }
        Object bean = this._valueInstantiator.createUsingDefault(ctxt);
        if (this._injectables != null) {
            injectValues(ctxt, bean);
        }
        if (this._needViewProcesing && (view = ctxt.getActiveView()) != null) {
            return deserializeWithView(jp, ctxt, bean, view);
        }
        while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            if (prop != null) {
                try {
                    bean = prop.deserializeSetAndReturn(jp, ctxt, bean);
                } catch (Exception e) {
                    wrapAndThrow(e, bean, propName, ctxt);
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                jp.skipChildren();
            } else if (this._anySetter != null) {
                try {
                    this._anySetter.deserializeAndSet(jp, ctxt, bean, propName);
                } catch (Exception e2) {
                    wrapAndThrow(e2, bean, propName, ctxt);
                }
            } else {
                handleUnknownProperty(jp, ctxt, bean, propName);
            }
            jp.nextToken();
        }
        return bean;
    }

    protected Object deserializeFromObjectUsingNonDefault(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
        }
        if (this._propertyBasedCreator != null) {
            return _deserializeUsingPropertyBased(jp, ctxt);
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from(jp, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        throw JsonMappingException.from(jp, "No suitable constructor found for type " + this._beanType + ": can not instantiate from JSON object (need to add/enable type information?)");
    }

    public Object deserializeFromString(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (this._objectIdReader != null) {
            return deserializeFromObjectId(jp, ctxt);
        }
        if (this._delegateDeserializer != null && !this._valueInstantiator.canCreateFromString()) {
            Object bean = this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
            if (this._injectables != null) {
                injectValues(ctxt, bean);
                return bean;
            }
            return bean;
        }
        return this._valueInstantiator.createFromString(ctxt, jp.getText());
    }

    public Object deserializeFromNumber(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (this._objectIdReader != null) {
            return deserializeFromObjectId(jp, ctxt);
        }
        switch (jp.getNumberType()) {
            case INT:
                if (this._delegateDeserializer != null && !this._valueInstantiator.canCreateFromInt()) {
                    Object bean = this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
                    if (this._injectables != null) {
                        injectValues(ctxt, bean);
                        return bean;
                    }
                    return bean;
                }
                return this._valueInstantiator.createFromInt(ctxt, jp.getIntValue());
            case LONG:
                if (this._delegateDeserializer != null && !this._valueInstantiator.canCreateFromInt()) {
                    Object bean2 = this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
                    if (this._injectables != null) {
                        injectValues(ctxt, bean2);
                        return bean2;
                    }
                    return bean2;
                }
                return this._valueInstantiator.createFromLong(ctxt, jp.getLongValue());
            default:
                if (this._delegateDeserializer != null) {
                    Object bean3 = this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
                    if (this._injectables != null) {
                        injectValues(ctxt, bean3);
                        return bean3;
                    }
                    return bean3;
                }
                throw ctxt.instantiationException(getBeanClass(), "no suitable creator method found to deserialize from JSON integer number");
        }
    }

    public Object deserializeFromDouble(JsonParser jp, DeserializationContext ctxt) throws IOException {
        switch (jp.getNumberType()) {
            case FLOAT:
            case DOUBLE:
                if (this._delegateDeserializer != null && !this._valueInstantiator.canCreateFromDouble()) {
                    Object bean = this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
                    if (this._injectables == null) {
                        return bean;
                    }
                    injectValues(ctxt, bean);
                    return bean;
                }
                return this._valueInstantiator.createFromDouble(ctxt, jp.getDoubleValue());
            default:
                if (this._delegateDeserializer != null) {
                    return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
                }
                throw ctxt.instantiationException(getBeanClass(), "no suitable creator method found to deserialize from JSON floating-point number");
        }
    }

    public Object deserializeFromBoolean(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (this._delegateDeserializer != null && !this._valueInstantiator.canCreateFromBoolean()) {
            Object bean = this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
            if (this._injectables != null) {
                injectValues(ctxt, bean);
                return bean;
            }
            return bean;
        }
        boolean value = jp.getCurrentToken() == JsonToken.VALUE_TRUE;
        return this._valueInstantiator.createFromBoolean(ctxt, value);
    }

    public Object deserializeFromArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (this._delegateDeserializer != null) {
            try {
                Object bean = this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
                if (this._injectables != null) {
                    injectValues(ctxt, bean);
                }
                return bean;
            } catch (Exception e) {
                wrapInstantiationProblem(e, ctxt);
            }
        }
        throw ctxt.mappingException(getBeanClass());
    }

    protected final Object _deserializeUsingPropertyBased(JsonParser jp, DeserializationContext ctxt) throws IOException {
        PropertyBasedCreator creator = this._propertyBasedCreator;
        PropertyValueBuffer buffer = creator.startBuilding(jp, ctxt);
        TokenBuffer unknown = null;
        JsonToken t = jp.getCurrentToken();
        while (t == JsonToken.FIELD_NAME) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
            if (creatorProp != null) {
                Object value = creatorProp.deserialize(jp, ctxt);
                if (buffer.assignParameter(creatorProp.getPropertyIndex(), value)) {
                    jp.nextToken();
                    try {
                        Object bean = creator.build(ctxt, buffer);
                        if (bean.getClass() != this._beanType.getRawClass()) {
                            return handlePolymorphic(jp, ctxt, bean, unknown);
                        }
                        if (unknown != null) {
                            bean = handleUnknownProperties(ctxt, bean, unknown);
                        }
                        return _deserialize(jp, ctxt, bean);
                    } catch (Exception e) {
                        wrapAndThrow(e, this._beanType.getRawClass(), propName, ctxt);
                    }
                } else {
                    continue;
                }
            } else {
                SettableBeanProperty prop = this._beanProperties.find(propName);
                if (prop != null) {
                    buffer.bufferProperty(prop, prop.deserialize(jp, ctxt));
                } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                    jp.skipChildren();
                } else if (this._anySetter != null) {
                    buffer.bufferAnyProperty(this._anySetter, propName, this._anySetter.deserialize(jp, ctxt));
                } else {
                    if (unknown == null) {
                        unknown = new TokenBuffer(jp.getCodec());
                    }
                    unknown.writeFieldName(propName);
                    unknown.copyCurrentStructure(jp);
                }
            }
            t = jp.nextToken();
        }
        try {
            Object bean2 = creator.build(ctxt, buffer);
            if (unknown != null) {
                if (bean2.getClass() != this._beanType.getRawClass()) {
                    return handlePolymorphic(null, ctxt, bean2, unknown);
                }
                return handleUnknownProperties(ctxt, bean2, unknown);
            }
            return bean2;
        } catch (Exception e2) {
            wrapInstantiationProblem(e2, ctxt);
            return null;
        }
    }

    protected Object handlePolymorphic(JsonParser jp, DeserializationContext ctxt, Object bean, TokenBuffer unknownTokens) throws IOException {
        JsonDeserializer<Object> subDeser = _findSubclassDeserializer(ctxt, bean, unknownTokens);
        if (subDeser != null) {
            if (unknownTokens != null) {
                unknownTokens.writeEndObject();
                JsonParser p2 = unknownTokens.asParser();
                p2.nextToken();
                bean = subDeser.deserialize(p2, ctxt, bean);
            }
            if (jp != null) {
                bean = subDeser.deserialize(jp, ctxt, bean);
            }
            return bean;
        }
        if (unknownTokens != null) {
            bean = handleUnknownProperties(ctxt, bean, unknownTokens);
        }
        if (jp != null) {
            bean = deserialize(jp, ctxt, bean);
        }
        return bean;
    }

    protected final Object deserializeWithView(JsonParser jp, DeserializationContext ctxt, Object bean, Class<?> activeView) throws Exception {
        JsonToken t = jp.getCurrentToken();
        while (t == JsonToken.FIELD_NAME) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            if (prop != null) {
                if (!prop.visibleInView(activeView)) {
                    jp.skipChildren();
                } else {
                    try {
                        bean = prop.deserializeSetAndReturn(jp, ctxt, bean);
                    } catch (Exception e) {
                        wrapAndThrow(e, bean, propName, ctxt);
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                jp.skipChildren();
            } else if (this._anySetter != null) {
                this._anySetter.deserializeAndSet(jp, ctxt, bean, propName);
            } else {
                handleUnknownProperty(jp, ctxt, bean, propName);
            }
            t = jp.nextToken();
        }
        return bean;
    }

    protected Object deserializeWithUnwrapped(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
        }
        if (this._propertyBasedCreator != null) {
            return deserializeUsingPropertyBasedWithUnwrapped(jp, ctxt);
        }
        TokenBuffer tokens = new TokenBuffer(jp.getCodec());
        tokens.writeStartObject();
        Object bean = this._valueInstantiator.createUsingDefault(ctxt);
        if (this._injectables != null) {
            injectValues(ctxt, bean);
        }
        Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
        while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            if (prop != null) {
                if (activeView != null && !prop.visibleInView(activeView)) {
                    jp.skipChildren();
                } else {
                    try {
                        bean = prop.deserializeSetAndReturn(jp, ctxt, bean);
                    } catch (Exception e) {
                        wrapAndThrow(e, bean, propName, ctxt);
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                jp.skipChildren();
            } else {
                tokens.writeFieldName(propName);
                tokens.copyCurrentStructure(jp);
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jp, ctxt, bean, propName);
                    } catch (Exception e2) {
                        wrapAndThrow(e2, bean, propName, ctxt);
                    }
                }
            }
            jp.nextToken();
        }
        tokens.writeEndObject();
        this._unwrappedPropertyHandler.processUnwrapped(jp, ctxt, bean, tokens);
        return bean;
    }

    protected Object deserializeWithUnwrapped(JsonParser jp, DeserializationContext ctxt, Object bean) throws Exception {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        TokenBuffer tokens = new TokenBuffer(jp.getCodec());
        tokens.writeStartObject();
        Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
        while (t == JsonToken.FIELD_NAME) {
            String propName = jp.getCurrentName();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            jp.nextToken();
            if (prop != null) {
                if (activeView != null && !prop.visibleInView(activeView)) {
                    jp.skipChildren();
                } else {
                    try {
                        bean = prop.deserializeSetAndReturn(jp, ctxt, bean);
                    } catch (Exception e) {
                        wrapAndThrow(e, bean, propName, ctxt);
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                jp.skipChildren();
            } else {
                tokens.writeFieldName(propName);
                tokens.copyCurrentStructure(jp);
                if (this._anySetter != null) {
                    this._anySetter.deserializeAndSet(jp, ctxt, bean, propName);
                }
            }
            t = jp.nextToken();
        }
        tokens.writeEndObject();
        this._unwrappedPropertyHandler.processUnwrapped(jp, ctxt, bean, tokens);
        return bean;
    }

    protected Object deserializeUsingPropertyBasedWithUnwrapped(JsonParser jp, DeserializationContext ctxt) throws IOException {
        PropertyBasedCreator creator = this._propertyBasedCreator;
        PropertyValueBuffer buffer = creator.startBuilding(jp, ctxt);
        TokenBuffer tokens = new TokenBuffer(jp.getCodec());
        tokens.writeStartObject();
        JsonToken t = jp.getCurrentToken();
        while (t == JsonToken.FIELD_NAME) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty creatorProp = creator.findCreatorProperty(propName);
            if (creatorProp != null) {
                Object value = creatorProp.deserialize(jp, ctxt);
                if (buffer.assignParameter(creatorProp.getPropertyIndex(), value)) {
                    JsonToken t2 = jp.nextToken();
                    try {
                        Object bean = creator.build(ctxt, buffer);
                        while (t2 == JsonToken.FIELD_NAME) {
                            jp.nextToken();
                            tokens.copyCurrentStructure(jp);
                            t2 = jp.nextToken();
                        }
                        tokens.writeEndObject();
                        if (bean.getClass() != this._beanType.getRawClass()) {
                            throw ctxt.mappingException("Can not create polymorphic instances with unwrapped values");
                        }
                        return this._unwrappedPropertyHandler.processUnwrapped(jp, ctxt, bean, tokens);
                    } catch (Exception e) {
                        wrapAndThrow(e, this._beanType.getRawClass(), propName, ctxt);
                    }
                } else {
                    continue;
                }
            } else {
                SettableBeanProperty prop = this._beanProperties.find(propName);
                if (prop != null) {
                    buffer.bufferProperty(prop, prop.deserialize(jp, ctxt));
                } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                    jp.skipChildren();
                } else {
                    tokens.writeFieldName(propName);
                    tokens.copyCurrentStructure(jp);
                    if (this._anySetter != null) {
                        buffer.bufferAnyProperty(this._anySetter, propName, this._anySetter.deserialize(jp, ctxt));
                    }
                }
            }
            t = jp.nextToken();
        }
        try {
            return this._unwrappedPropertyHandler.processUnwrapped(jp, ctxt, creator.build(ctxt, buffer), tokens);
        } catch (Exception e2) {
            wrapInstantiationProblem(e2, ctxt);
            return null;
        }
    }

    protected Object deserializeWithExternalTypeId(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return this._propertyBasedCreator != null ? deserializeUsingPropertyBasedWithExternalTypeId(jp, ctxt) : deserializeWithExternalTypeId(jp, ctxt, this._valueInstantiator.createUsingDefault(ctxt));
    }

    protected Object deserializeWithExternalTypeId(JsonParser jp, DeserializationContext ctxt, Object bean) throws IOException {
        Class<?> activeView = this._needViewProcesing ? ctxt.getActiveView() : null;
        ExternalTypeHandler ext = this._externalTypeIdHandler.start();
        while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            if (prop != null) {
                if (activeView != null && !prop.visibleInView(activeView)) {
                    jp.skipChildren();
                } else {
                    try {
                        bean = prop.deserializeSetAndReturn(jp, ctxt, bean);
                    } catch (Exception e) {
                        wrapAndThrow(e, bean, propName, ctxt);
                    }
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                jp.skipChildren();
            } else if (!ext.handlePropertyValue(jp, ctxt, propName, bean)) {
                if (this._anySetter != null) {
                    try {
                        this._anySetter.deserializeAndSet(jp, ctxt, bean, propName);
                    } catch (Exception e2) {
                        wrapAndThrow(e2, bean, propName, ctxt);
                    }
                } else {
                    handleUnknownProperty(jp, ctxt, bean, propName);
                }
            }
            jp.nextToken();
        }
        return ext.complete(jp, ctxt, bean);
    }

    protected Object deserializeUsingPropertyBasedWithExternalTypeId(JsonParser jp, DeserializationContext ctxt) throws IOException {
        throw new IllegalStateException("Deserialization with Builder, External type id, @JsonCreator not yet implemented");
    }
}
