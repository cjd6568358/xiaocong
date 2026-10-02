package com.fasterxml.jackson.databind.deser.impl;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedWithParams;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.tencent.android.tpush.SettingsContentProvider;
import java.lang.reflect.Member;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CreatorCollector {
    final BeanDescription _beanDesc;
    protected AnnotatedWithParams _booleanCreator;
    final boolean _canFixAccess;
    protected AnnotatedConstructor _defaultConstructor;
    protected CreatorProperty[] _delegateArgs;
    protected AnnotatedWithParams _delegateCreator;
    protected AnnotatedWithParams _doubleCreator;
    protected AnnotatedWithParams _intCreator;
    protected AnnotatedWithParams _longCreator;
    protected CreatorProperty[] _propertyBasedArgs = null;
    protected AnnotatedWithParams _propertyBasedCreator;
    protected AnnotatedWithParams _stringCreator;

    public CreatorCollector(BeanDescription beanDesc, boolean canFixAccess) {
        this._beanDesc = beanDesc;
        this._canFixAccess = canFixAccess;
    }

    public ValueInstantiator constructValueInstantiator(DeserializationConfig config) {
        JavaType delegateType;
        StdValueInstantiator inst = new StdValueInstantiator(config, this._beanDesc.getType());
        if (this._delegateCreator == null) {
            delegateType = null;
        } else {
            int ix = 0;
            if (this._delegateArgs != null) {
                int len = this._delegateArgs.length;
                for (int i = 0; i < len; i++) {
                    if (this._delegateArgs[i] == null) {
                        ix = i;
                        break;
                    }
                }
            }
            TypeBindings bindings = this._beanDesc.bindingsForBeanType();
            delegateType = bindings.resolveType(this._delegateCreator.getGenericParameterType(ix));
        }
        inst.configureFromObjectSettings(this._defaultConstructor, this._delegateCreator, delegateType, this._delegateArgs, this._propertyBasedCreator, this._propertyBasedArgs);
        inst.configureFromStringCreator(this._stringCreator);
        inst.configureFromIntCreator(this._intCreator);
        inst.configureFromLongCreator(this._longCreator);
        inst.configureFromDoubleCreator(this._doubleCreator);
        inst.configureFromBooleanCreator(this._booleanCreator);
        return inst;
    }

    public void setDefaultConstructor(AnnotatedConstructor ctor) {
        this._defaultConstructor = ctor;
    }

    public void addStringCreator(AnnotatedWithParams creator) {
        this._stringCreator = verifyNonDup(creator, this._stringCreator, "String");
    }

    public void addIntCreator(AnnotatedWithParams creator) {
        this._intCreator = verifyNonDup(creator, this._intCreator, "int");
    }

    public void addLongCreator(AnnotatedWithParams creator) {
        this._longCreator = verifyNonDup(creator, this._longCreator, SettingsContentProvider.LONG_TYPE);
    }

    public void addDoubleCreator(AnnotatedWithParams creator) {
        this._doubleCreator = verifyNonDup(creator, this._doubleCreator, "double");
    }

    public void addBooleanCreator(AnnotatedWithParams creator) {
        this._booleanCreator = verifyNonDup(creator, this._booleanCreator, SettingsContentProvider.BOOLEAN_TYPE);
    }

    public void addDelegatingCreator(AnnotatedWithParams creator, CreatorProperty[] injectables) {
        this._delegateCreator = verifyNonDup(creator, this._delegateCreator, "delegate");
        this._delegateArgs = injectables;
    }

    public void addPropertyCreator(AnnotatedWithParams creator, CreatorProperty[] properties) {
        Integer old;
        this._propertyBasedCreator = verifyNonDup(creator, this._propertyBasedCreator, "property-based");
        if (properties.length > 1) {
            HashMap<String, Integer> names = new HashMap<>();
            int len = properties.length;
            for (int i = 0; i < len; i++) {
                String name = properties[i].getName();
                if ((name.length() != 0 || properties[i].getInjectableValueId() == null) && (old = names.put(name, Integer.valueOf(i))) != null) {
                    throw new IllegalArgumentException("Duplicate creator property \"" + name + "\" (index " + old + " vs " + i + ")");
                }
            }
        }
        this._propertyBasedArgs = properties;
    }

    protected AnnotatedWithParams verifyNonDup(AnnotatedWithParams newOne, AnnotatedWithParams oldOne, String type) {
        if (oldOne != null && oldOne.getClass() == newOne.getClass()) {
            throw new IllegalArgumentException("Conflicting " + type + " creators: already had " + oldOne + ", encountered " + newOne);
        }
        if (this._canFixAccess) {
            ClassUtil.checkAndFixAccess((Member) newOne.getAnnotated());
        }
        return newOne;
    }
}
