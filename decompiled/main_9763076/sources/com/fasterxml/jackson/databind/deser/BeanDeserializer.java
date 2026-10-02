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
import com.fasterxml.jackson.databind.util.NameTransformer;
import com.fasterxml.jackson.databind.util.TokenBuffer;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BeanDeserializer extends BeanDeserializerBase {
    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public /* bridge */ /* synthetic */ BeanDeserializerBase withIgnorableProperties(HashSet x0) {
        return withIgnorableProperties((HashSet<String>) x0);
    }

    public BeanDeserializer(BeanDeserializerBuilder builder, BeanDescription beanDesc, BeanPropertyMap properties, Map<String, SettableBeanProperty> backRefs, HashSet<String> ignorableProps, boolean ignoreAllUnknown, boolean hasViews) {
        super(builder, beanDesc, properties, backRefs, ignorableProps, ignoreAllUnknown, hasViews);
    }

    protected BeanDeserializer(BeanDeserializerBase src) {
        super(src, src._ignoreAllUnknown);
    }

    protected BeanDeserializer(BeanDeserializerBase src, boolean ignoreAllUnknown) {
        super(src, ignoreAllUnknown);
    }

    protected BeanDeserializer(BeanDeserializerBase src, NameTransformer unwrapper) {
        super(src, unwrapper);
    }

    public BeanDeserializer(BeanDeserializerBase src, ObjectIdReader oir) {
        super(src, oir);
    }

    public BeanDeserializer(BeanDeserializerBase src, HashSet<String> ignorableProps) {
        super(src, ignorableProps);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase, com.fasterxml.jackson.databind.JsonDeserializer
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
        return getClass() != BeanDeserializer.class ? this : new BeanDeserializer(this, unwrapper);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializer withObjectIdReader(ObjectIdReader oir) {
        return new BeanDeserializer(this, oir);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializerBase
    public BeanDeserializer withIgnorableProperties(HashSet<String> ignorableProps) {
        return new BeanDeserializer(this, ignorableProps);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public final Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            JsonToken t2 = jp.nextToken();
            if (this._vanillaProcessing) {
                return vanillaDeserialize(jp, ctxt, t2);
            }
            if (this._objectIdReader != null) {
                return deserializeWithObjectId(jp, ctxt);
            }
            return deserializeFromObject(jp, ctxt);
        }
        switch (t) {
            case VALUE_STRING:
                return deserializeFromString(jp, ctxt);
            case VALUE_NUMBER_INT:
                return deserializeFromNumber(jp, ctxt);
            case VALUE_NUMBER_FLOAT:
                return deserializeFromDouble(jp, ctxt);
            case VALUE_EMBEDDED_OBJECT:
                return jp.getEmbeddedObject();
            case VALUE_TRUE:
            case VALUE_FALSE:
                return deserializeFromBoolean(jp, ctxt);
            case START_ARRAY:
                return deserializeFromArray(jp, ctxt);
            case FIELD_NAME:
            case END_OBJECT:
                if (this._vanillaProcessing) {
                    return vanillaDeserialize(jp, ctxt, t);
                }
                if (this._objectIdReader != null) {
                    return deserializeWithObjectId(jp, ctxt);
                }
                return deserializeFromObject(jp, ctxt);
            default:
                throw ctxt.mappingException(getBeanClass());
        }
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserialize(JsonParser jp, DeserializationContext ctxt, Object bean) throws Exception {
        Class<?> view;
        if (this._injectables != null) {
            injectValues(ctxt, bean);
        }
        if (this._unwrappedPropertyHandler != null) {
            return deserializeWithUnwrapped(jp, ctxt, bean);
        }
        if (this._externalTypeIdHandler != null) {
            return deserializeWithExternalTypeId(jp, ctxt, bean);
        }
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = jp.nextToken();
        }
        if (this._needViewProcesing && (view = ctxt.getActiveView()) != null) {
            return deserializeWithView(jp, ctxt, bean, view);
        }
        while (t == JsonToken.FIELD_NAME) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            if (prop != null) {
                try {
                    prop.deserializeAndSet(jp, ctxt, bean);
                } catch (Exception e) {
                    wrapAndThrow(e, bean, propName, ctxt);
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

    private final Object vanillaDeserialize(JsonParser jp, DeserializationContext ctxt, JsonToken t) throws IOException {
        Object bean = this._valueInstantiator.createUsingDefault(ctxt);
        while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
            String propName = jp.getCurrentName();
            jp.nextToken();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            if (prop != null) {
                try {
                    prop.deserializeAndSet(jp, ctxt, bean);
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
                    prop.deserializeAndSet(jp, ctxt, bean);
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

    protected Object deserializeWithObjectId(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String idPropName = this._objectIdReader.propertyName;
        if (idPropName.equals(jp.getCurrentName())) {
            return deserializeFromObject(jp, ctxt);
        }
        TokenBuffer tmpBuffer = new TokenBuffer(jp.getCodec());
        TokenBuffer mergedBuffer = null;
        while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
            String propName = jp.getCurrentName();
            if (mergedBuffer == null) {
                if (idPropName.equals(propName)) {
                    mergedBuffer = new TokenBuffer(jp.getCodec());
                    mergedBuffer.writeFieldName(propName);
                    jp.nextToken();
                    mergedBuffer.copyCurrentStructure(jp);
                    mergedBuffer.append(tmpBuffer);
                    tmpBuffer = null;
                } else {
                    tmpBuffer.writeFieldName(propName);
                    jp.nextToken();
                    tmpBuffer.copyCurrentStructure(jp);
                }
            } else {
                mergedBuffer.writeFieldName(propName);
                jp.nextToken();
                mergedBuffer.copyCurrentStructure(jp);
            }
            jp.nextToken();
        }
        TokenBuffer buffer = mergedBuffer == null ? tmpBuffer : mergedBuffer;
        buffer.writeEndObject();
        JsonParser mergedParser = buffer.asParser();
        mergedParser.nextToken();
        return deserializeFromObject(mergedParser, ctxt);
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
                        return deserialize(jp, ctxt, bean);
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

    protected Object handlePolymorphic(JsonParser jp, DeserializationContext ctxt, Object bean, TokenBuffer unknownTokens) throws Exception {
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
                        prop.deserializeAndSet(jp, ctxt, bean);
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
                        prop.deserializeAndSet(jp, ctxt, bean);
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
                        prop.deserializeAndSet(jp, ctxt, bean);
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
                if (jp.getCurrentToken().isScalarValue()) {
                    ext.handleTypePropertyValue(jp, ctxt, propName, bean);
                }
                if (activeView != null && !prop.visibleInView(activeView)) {
                    jp.skipChildren();
                } else {
                    try {
                        prop.deserializeAndSet(jp, ctxt, bean);
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
        ExternalTypeHandler ext = this._externalTypeIdHandler.start();
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
                if (ext.handlePropertyValue(jp, ctxt, propName, buffer)) {
                    continue;
                } else {
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
                            if (bean.getClass() != this._beanType.getRawClass()) {
                                throw ctxt.mappingException("Can not create polymorphic instances with unwrapped values");
                            }
                            return ext.complete(jp, ctxt, bean);
                        } catch (Exception e) {
                            wrapAndThrow(e, this._beanType.getRawClass(), propName, ctxt);
                        }
                    } else {
                        continue;
                    }
                }
            } else {
                SettableBeanProperty prop = this._beanProperties.find(propName);
                if (prop != null) {
                    buffer.bufferProperty(prop, prop.deserialize(jp, ctxt));
                } else if (!ext.handlePropertyValue(jp, ctxt, propName, null)) {
                    if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                        jp.skipChildren();
                    } else if (this._anySetter != null) {
                        buffer.bufferAnyProperty(this._anySetter, propName, this._anySetter.deserialize(jp, ctxt));
                    }
                }
            }
            t = jp.nextToken();
        }
        try {
            return ext.complete(jp, ctxt, buffer, creator);
        } catch (Exception e2) {
            wrapInstantiationProblem(e2, ctxt);
            return null;
        }
    }
}
