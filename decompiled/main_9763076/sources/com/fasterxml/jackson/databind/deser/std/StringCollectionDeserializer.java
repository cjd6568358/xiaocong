package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import java.io.IOException;
import java.util.Collection;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonStdImpl
public final class StringCollectionDeserializer extends ContainerDeserializerBase<Collection<String>> implements ContextualDeserializer {
    protected final JavaType _collectionType;
    protected final JsonDeserializer<Object> _delegateDeserializer;
    protected final JsonDeserializer<String> _valueDeserializer;
    protected final ValueInstantiator _valueInstantiator;

    public StringCollectionDeserializer(JavaType collectionType, JsonDeserializer<?> valueDeser, ValueInstantiator valueInstantiator) {
        this(collectionType, valueInstantiator, null, valueDeser);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected StringCollectionDeserializer(JavaType collectionType, ValueInstantiator valueInstantiator, JsonDeserializer<?> delegateDeser, JsonDeserializer<?> jsonDeserializer) {
        super(collectionType.getRawClass());
        this._collectionType = collectionType;
        this._valueDeserializer = jsonDeserializer;
        this._valueInstantiator = valueInstantiator;
        this._delegateDeserializer = delegateDeser;
    }

    protected StringCollectionDeserializer withResolved(JsonDeserializer<?> delegateDeser, JsonDeserializer<?> valueDeser) {
        return (this._valueDeserializer == valueDeser && this._delegateDeserializer == delegateDeser) ? this : new StringCollectionDeserializer(this._collectionType, this._valueInstantiator, delegateDeser, valueDeser);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.fasterxml.jackson.databind.JsonDeserializer] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.fasterxml.jackson.databind.JsonDeserializer] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.fasterxml.jackson.databind.deser.std.StringCollectionDeserializer] */
    @Override // com.fasterxml.jackson.databind.deser.ContextualDeserializer
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        ?? FindContextualValueDeserializer;
        JsonDeserializer<Object> jsonDeserializerFindDeserializer = null;
        if (this._valueInstantiator != null && this._valueInstantiator.getDelegateCreator() != null) {
            jsonDeserializerFindDeserializer = findDeserializer(deserializationContext, this._valueInstantiator.getDelegateType(deserializationContext.getConfig()), beanProperty);
        }
        Object obj = this._valueDeserializer;
        if (obj == null) {
            FindContextualValueDeserializer = deserializationContext.findContextualValueDeserializer(this._collectionType.getContentType(), beanProperty);
        } else if (obj instanceof ContextualDeserializer) {
            FindContextualValueDeserializer = obj;
            FindContextualValueDeserializer = ((ContextualDeserializer) obj).createContextual(deserializationContext, beanProperty);
        }
        FindContextualValueDeserializer = obj;
        boolean zIsDefaultDeserializer = isDefaultDeserializer(FindContextualValueDeserializer);
        ?? r4 = FindContextualValueDeserializer;
        if (zIsDefaultDeserializer) {
            r4 = 0;
        }
        return withResolved(jsonDeserializerFindDeserializer, r4);
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public JavaType getContentType() {
        return this._collectionType.getContentType();
    }

    @Override // com.fasterxml.jackson.databind.deser.std.ContainerDeserializerBase
    public JsonDeserializer<Object> getContentDeserializer() {
        JsonDeserializer<?> deser = this._valueDeserializer;
        return deser;
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Collection<String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        if (this._delegateDeserializer != null) {
            return (Collection) this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
        }
        Collection<String> result = (Collection) this._valueInstantiator.createUsingDefault(ctxt);
        return deserialize(jp, ctxt, result);
    }

    @Override // com.fasterxml.jackson.databind.JsonDeserializer
    public Collection<String> deserialize(JsonParser jp, DeserializationContext ctxt, Collection<String> result) throws IOException {
        if (!jp.isExpectedStartArrayToken()) {
            return handleNonArray(jp, ctxt, result);
        }
        if (this._valueDeserializer != null) {
            return deserializeUsingCustom(jp, ctxt, result, this._valueDeserializer);
        }
        while (true) {
            JsonToken t = jp.nextToken();
            if (t != JsonToken.END_ARRAY) {
                result.add(t == JsonToken.VALUE_NULL ? null : jp.getText());
            } else {
                return result;
            }
        }
    }

    private Collection<String> deserializeUsingCustom(JsonParser jp, DeserializationContext ctxt, Collection<String> result, JsonDeserializer<String> deser) throws IOException {
        String value;
        while (true) {
            JsonToken t = jp.nextToken();
            if (t != JsonToken.END_ARRAY) {
                if (t == JsonToken.VALUE_NULL) {
                    value = null;
                } else {
                    String value2 = deser.deserialize(jp, ctxt);
                    value = value2;
                }
                result.add(value);
            } else {
                return result;
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.std.StdDeserializer, com.fasterxml.jackson.databind.JsonDeserializer
    public Object deserializeWithType(JsonParser jp, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return typeDeserializer.deserializeTypedFromArray(jp, ctxt);
    }

    private final Collection<String> handleNonArray(JsonParser jp, DeserializationContext ctxt, Collection<String> result) throws IOException {
        String value;
        if (!ctxt.isEnabled(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)) {
            throw ctxt.mappingException(this._collectionType.getRawClass());
        }
        JsonDeserializer<String> valueDes = this._valueDeserializer;
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_NULL) {
            value = null;
        } else {
            value = valueDes == null ? jp.getText() : valueDes.deserialize(jp, ctxt);
        }
        result.add(value);
        return result;
    }
}
