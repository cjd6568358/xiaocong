package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.util.NameTransformer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ThrowableDeserializer extends BeanDeserializer {
    protected static final String PROP_NAME_MESSAGE = "message";

    public ThrowableDeserializer(BeanDeserializer baseDeserializer) {
        super(baseDeserializer);
        this._vanillaProcessing = false;
    }

    protected ThrowableDeserializer(BeanDeserializer src, NameTransformer unwrapper) {
        super(src, unwrapper);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializer, com.fasterxml.jackson.databind.deser.BeanDeserializerBase, com.fasterxml.jackson.databind.JsonDeserializer
    public JsonDeserializer<Object> unwrappingDeserializer(NameTransformer unwrapper) {
        return getClass() != ThrowableDeserializer.class ? this : new ThrowableDeserializer(this, unwrapper);
    }

    @Override // com.fasterxml.jackson.databind.deser.BeanDeserializer
    public Object deserializeFromObject(JsonParser jp, DeserializationContext ctxt) throws Exception {
        Object throwable;
        if (this._propertyBasedCreator != null) {
            return _deserializeUsingPropertyBased(jp, ctxt);
        }
        if (this._delegateDeserializer != null) {
            return this._valueInstantiator.createUsingDelegate(ctxt, this._delegateDeserializer.deserialize(jp, ctxt));
        }
        if (this._beanType.isAbstract()) {
            throw JsonMappingException.from(jp, "Can not instantiate abstract type " + this._beanType + " (need to add/enable type information?)");
        }
        boolean hasStringCreator = this._valueInstantiator.canCreateFromString();
        boolean hasDefaultCtor = this._valueInstantiator.canCreateUsingDefault();
        if (!hasStringCreator && !hasDefaultCtor) {
            throw new JsonMappingException("Can not deserialize Throwable of type " + this._beanType + " without having a default contructor, a single-String-arg constructor; or explicit @JsonCreator");
        }
        Object throwable2 = null;
        Object[] pending = null;
        int pendingIx = 0;
        while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
            String propName = jp.getCurrentName();
            SettableBeanProperty prop = this._beanProperties.find(propName);
            jp.nextToken();
            if (prop != null) {
                if (throwable2 != null) {
                    prop.deserializeAndSet(jp, ctxt, throwable2);
                } else {
                    if (pending == null) {
                        int len = this._beanProperties.size();
                        pending = new Object[len + len];
                    }
                    int pendingIx2 = pendingIx + 1;
                    pending[pendingIx] = prop;
                    pendingIx = pendingIx2 + 1;
                    pending[pendingIx2] = prop.deserialize(jp, ctxt);
                }
            } else if ("message".equals(propName) && hasStringCreator) {
                throwable2 = this._valueInstantiator.createFromString(ctxt, jp.getText());
                if (pending != null) {
                    int len2 = pendingIx;
                    for (int i = 0; i < len2; i += 2) {
                        ((SettableBeanProperty) pending[i]).set(throwable2, pending[i + 1]);
                    }
                    pending = null;
                }
            } else if (this._ignorableProps != null && this._ignorableProps.contains(propName)) {
                jp.skipChildren();
            } else if (this._anySetter != null) {
                this._anySetter.deserializeAndSet(jp, ctxt, throwable2, propName);
            } else {
                handleUnknownProperty(jp, ctxt, throwable2, propName);
            }
            jp.nextToken();
        }
        if (throwable2 == null) {
            if (hasStringCreator) {
                throwable = this._valueInstantiator.createFromString(ctxt, null);
            } else {
                throwable = this._valueInstantiator.createUsingDefault(ctxt);
            }
            if (pending != null) {
                int len3 = pendingIx;
                for (int i2 = 0; i2 < len3; i2 += 2) {
                    ((SettableBeanProperty) pending[i2]).set(throwable, pending[i2 + 1]);
                }
                return throwable;
            }
            return throwable;
        }
        return throwable2;
    }
}
