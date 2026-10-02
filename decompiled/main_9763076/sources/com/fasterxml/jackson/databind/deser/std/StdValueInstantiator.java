package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.tencent.bugly.Bugly;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StdValueInstantiator extends ValueInstantiator {
    protected final boolean _cfgEmptyStringsAsObjects;
    protected CreatorProperty[] _constructorArguments;
    protected AnnotatedWithParams _defaultCreator;
    protected CreatorProperty[] _delegateArguments;
    protected AnnotatedWithParams _delegateCreator;
    protected JavaType _delegateType;
    protected AnnotatedWithParams _fromBooleanCreator;
    protected AnnotatedWithParams _fromDoubleCreator;
    protected AnnotatedWithParams _fromIntCreator;
    protected AnnotatedWithParams _fromLongCreator;
    protected AnnotatedWithParams _fromStringCreator;
    protected final String _valueTypeDesc;
    protected AnnotatedWithParams _withArgsCreator;

    public StdValueInstantiator(DeserializationConfig config, Class<?> valueType) {
        this._cfgEmptyStringsAsObjects = config == null ? false : config.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this._valueTypeDesc = valueType == null ? "UNKNOWN TYPE" : valueType.getName();
    }

    public StdValueInstantiator(DeserializationConfig config, JavaType valueType) {
        this._cfgEmptyStringsAsObjects = config == null ? false : config.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this._valueTypeDesc = valueType == null ? "UNKNOWN TYPE" : valueType.toString();
    }

    protected StdValueInstantiator(StdValueInstantiator src) {
        this._cfgEmptyStringsAsObjects = src._cfgEmptyStringsAsObjects;
        this._valueTypeDesc = src._valueTypeDesc;
        this._defaultCreator = src._defaultCreator;
        this._constructorArguments = src._constructorArguments;
        this._withArgsCreator = src._withArgsCreator;
        this._delegateType = src._delegateType;
        this._delegateCreator = src._delegateCreator;
        this._delegateArguments = src._delegateArguments;
        this._fromStringCreator = src._fromStringCreator;
        this._fromIntCreator = src._fromIntCreator;
        this._fromLongCreator = src._fromLongCreator;
        this._fromDoubleCreator = src._fromDoubleCreator;
        this._fromBooleanCreator = src._fromBooleanCreator;
    }

    public void configureFromObjectSettings(AnnotatedWithParams defaultCreator, AnnotatedWithParams delegateCreator, JavaType delegateType, CreatorProperty[] delegateArgs, AnnotatedWithParams withArgsCreator, CreatorProperty[] constructorArgs) {
        this._defaultCreator = defaultCreator;
        this._delegateCreator = delegateCreator;
        this._delegateType = delegateType;
        this._delegateArguments = delegateArgs;
        this._withArgsCreator = withArgsCreator;
        this._constructorArguments = constructorArgs;
    }

    public void configureFromStringCreator(AnnotatedWithParams creator) {
        this._fromStringCreator = creator;
    }

    public void configureFromIntCreator(AnnotatedWithParams creator) {
        this._fromIntCreator = creator;
    }

    public void configureFromLongCreator(AnnotatedWithParams creator) {
        this._fromLongCreator = creator;
    }

    public void configureFromDoubleCreator(AnnotatedWithParams creator) {
        this._fromDoubleCreator = creator;
    }

    public void configureFromBooleanCreator(AnnotatedWithParams creator) {
        this._fromBooleanCreator = creator;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public String getValueTypeDesc() {
        return this._valueTypeDesc;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateFromString() {
        return this._fromStringCreator != null;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateFromInt() {
        return this._fromIntCreator != null;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateFromLong() {
        return this._fromLongCreator != null;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateFromDouble() {
        return this._fromDoubleCreator != null;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateFromBoolean() {
        return this._fromBooleanCreator != null;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateUsingDefault() {
        return this._defaultCreator != null;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateUsingDelegate() {
        return this._delegateType != null;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public boolean canCreateFromObjectWith() {
        return this._withArgsCreator != null;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public JavaType getDelegateType(DeserializationConfig config) {
        return this._delegateType;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config) {
        return this._constructorArguments;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createUsingDefault(DeserializationContext ctxt) throws IOException {
        if (this._defaultCreator == null) {
            throw new IllegalStateException("No default constructor for " + getValueTypeDesc());
        }
        try {
            return this._defaultCreator.call();
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createFromObjectWith(DeserializationContext ctxt, Object[] args) throws IOException {
        if (this._withArgsCreator == null) {
            throw new IllegalStateException("No with-args constructor for " + getValueTypeDesc());
        }
        try {
            return this._withArgsCreator.call(args);
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createUsingDelegate(DeserializationContext ctxt, Object delegate) throws IOException {
        if (this._delegateCreator == null) {
            throw new IllegalStateException("No delegate constructor for " + getValueTypeDesc());
        }
        try {
            if (this._delegateArguments == null) {
                return this._delegateCreator.call1(delegate);
            }
            int len = this._delegateArguments.length;
            Object[] args = new Object[len];
            for (int i = 0; i < len; i++) {
                CreatorProperty prop = this._delegateArguments[i];
                if (prop == null) {
                    args[i] = delegate;
                } else {
                    args[i] = ctxt.findInjectableValue(prop.getInjectableValueId(), prop, null);
                }
            }
            return this._delegateCreator.call(args);
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createFromString(DeserializationContext ctxt, String value) throws IOException {
        if (this._fromStringCreator != null) {
            try {
                return this._fromStringCreator.call1(value);
            } catch (Exception e) {
                throw wrapException(e);
            } catch (ExceptionInInitializerError e2) {
                throw wrapException(e2);
            }
        }
        return _createFromStringFallbacks(ctxt, value);
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createFromInt(DeserializationContext ctxt, int value) throws IOException {
        try {
            if (this._fromIntCreator != null) {
                return this._fromIntCreator.call1(Integer.valueOf(value));
            }
            if (this._fromLongCreator != null) {
                return this._fromLongCreator.call1(Long.valueOf(value));
            }
            throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON integral number; no single-int-arg constructor/factory method");
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createFromLong(DeserializationContext ctxt, long value) throws IOException {
        try {
            if (this._fromLongCreator != null) {
                return this._fromLongCreator.call1(Long.valueOf(value));
            }
            throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON long integral number; no single-long-arg constructor/factory method");
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createFromDouble(DeserializationContext ctxt, double value) throws IOException {
        try {
            if (this._fromDoubleCreator != null) {
                return this._fromDoubleCreator.call1(Double.valueOf(value));
            }
            throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON floating-point number; no one-double/Double-arg constructor/factory method");
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public Object createFromBoolean(DeserializationContext ctxt, boolean value) throws IOException {
        try {
            if (this._fromBooleanCreator != null) {
                return this._fromBooleanCreator.call1(Boolean.valueOf(value));
            }
            throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON boolean value; no single-boolean/Boolean-arg constructor/factory method");
        } catch (Exception e) {
            throw wrapException(e);
        } catch (ExceptionInInitializerError e2) {
            throw wrapException(e2);
        }
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public AnnotatedWithParams getDelegateCreator() {
        return this._delegateCreator;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public AnnotatedWithParams getDefaultCreator() {
        return this._defaultCreator;
    }

    @Override // com.fasterxml.jackson.databind.deser.ValueInstantiator
    public AnnotatedWithParams getWithArgsCreator() {
        return this._withArgsCreator;
    }

    protected Object _createFromStringFallbacks(DeserializationContext ctxt, String value) throws IOException {
        if (this._fromBooleanCreator != null) {
            String str = value.trim();
            if ("true".equals(str)) {
                return createFromBoolean(ctxt, true);
            }
            if (Bugly.SDK_IS_DEV.equals(str)) {
                return createFromBoolean(ctxt, false);
            }
        }
        if (this._cfgEmptyStringsAsObjects && value.length() == 0) {
            return null;
        }
        throw new JsonMappingException("Can not instantiate value of type " + getValueTypeDesc() + " from JSON String; no single-String constructor/factory method");
    }

    protected JsonMappingException wrapException(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t instanceof JsonMappingException ? (JsonMappingException) t : new JsonMappingException("Instantiation of " + getValueTypeDesc() + " value failed: " + t.getMessage(), t);
    }
}
