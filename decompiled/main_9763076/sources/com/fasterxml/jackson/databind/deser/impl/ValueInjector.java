package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.Annotations;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ValueInjector extends BeanProperty.Std {
    protected final Object _valueId;

    public ValueInjector(String propertyName, JavaType type, Annotations contextAnnotations, AnnotatedMember mutator, Object valueId) {
        super(propertyName, type, contextAnnotations, mutator);
        this._valueId = valueId;
    }

    public Object findValue(DeserializationContext context, Object beanInstance) {
        return context.findInjectableValue(this._valueId, this, beanInstance);
    }

    public void inject(DeserializationContext context, Object beanInstance) throws IOException {
        this._member.setValue(beanInstance, findValue(context, beanInstance));
    }
}
