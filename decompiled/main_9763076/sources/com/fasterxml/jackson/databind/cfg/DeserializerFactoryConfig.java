package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.KeyDeserializers;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.util.ArrayBuilders;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeserializerFactoryConfig {
    protected final AbstractTypeResolver[] _abstractTypeResolvers;
    protected final Deserializers[] _additionalDeserializers;
    protected final KeyDeserializers[] _additionalKeyDeserializers;
    protected final BeanDeserializerModifier[] _modifiers;
    protected final ValueInstantiators[] _valueInstantiators;
    protected static final Deserializers[] NO_DESERIALIZERS = new Deserializers[0];
    protected static final KeyDeserializers[] NO_KEY_DESERIALIZERS = new KeyDeserializers[0];
    protected static final BeanDeserializerModifier[] NO_MODIFIERS = new BeanDeserializerModifier[0];
    protected static final AbstractTypeResolver[] NO_ABSTRACT_TYPE_RESOLVERS = new AbstractTypeResolver[0];
    protected static final ValueInstantiators[] NO_VALUE_INSTANTIATORS = new ValueInstantiators[0];

    public DeserializerFactoryConfig() {
        this(null, null, null, null, null);
    }

    protected DeserializerFactoryConfig(Deserializers[] allAdditionalDeserializers, KeyDeserializers[] allAdditionalKeyDeserializers, BeanDeserializerModifier[] modifiers, AbstractTypeResolver[] atr, ValueInstantiators[] vi) {
        this._additionalDeserializers = allAdditionalDeserializers == null ? NO_DESERIALIZERS : allAdditionalDeserializers;
        this._additionalKeyDeserializers = allAdditionalKeyDeserializers == null ? NO_KEY_DESERIALIZERS : allAdditionalKeyDeserializers;
        this._modifiers = modifiers == null ? NO_MODIFIERS : modifiers;
        this._abstractTypeResolvers = atr == null ? NO_ABSTRACT_TYPE_RESOLVERS : atr;
        this._valueInstantiators = vi == null ? NO_VALUE_INSTANTIATORS : vi;
    }

    public DeserializerFactoryConfig withAdditionalDeserializers(Deserializers additional) {
        if (additional == null) {
            throw new IllegalArgumentException("Can not pass null Deserializers");
        }
        Deserializers[] all = (Deserializers[]) ArrayBuilders.insertInListNoDup(this._additionalDeserializers, additional);
        return new DeserializerFactoryConfig(all, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
    }

    public DeserializerFactoryConfig withAdditionalKeyDeserializers(KeyDeserializers additional) {
        if (additional == null) {
            throw new IllegalArgumentException("Can not pass null KeyDeserializers");
        }
        KeyDeserializers[] all = (KeyDeserializers[]) ArrayBuilders.insertInListNoDup(this._additionalKeyDeserializers, additional);
        return new DeserializerFactoryConfig(this._additionalDeserializers, all, this._modifiers, this._abstractTypeResolvers, this._valueInstantiators);
    }

    public DeserializerFactoryConfig withDeserializerModifier(BeanDeserializerModifier modifier) {
        if (modifier == null) {
            throw new IllegalArgumentException("Can not pass null modifier");
        }
        BeanDeserializerModifier[] all = (BeanDeserializerModifier[]) ArrayBuilders.insertInListNoDup(this._modifiers, modifier);
        return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, all, this._abstractTypeResolvers, this._valueInstantiators);
    }

    public DeserializerFactoryConfig withAbstractTypeResolver(AbstractTypeResolver resolver) {
        if (resolver == null) {
            throw new IllegalArgumentException("Can not pass null resolver");
        }
        AbstractTypeResolver[] all = (AbstractTypeResolver[]) ArrayBuilders.insertInListNoDup(this._abstractTypeResolvers, resolver);
        return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, all, this._valueInstantiators);
    }

    public DeserializerFactoryConfig withValueInstantiators(ValueInstantiators instantiators) {
        if (instantiators == null) {
            throw new IllegalArgumentException("Can not pass null resolver");
        }
        ValueInstantiators[] all = (ValueInstantiators[]) ArrayBuilders.insertInListNoDup(this._valueInstantiators, instantiators);
        return new DeserializerFactoryConfig(this._additionalDeserializers, this._additionalKeyDeserializers, this._modifiers, this._abstractTypeResolvers, all);
    }

    public boolean hasDeserializers() {
        return this._additionalDeserializers.length > 0;
    }

    public boolean hasKeyDeserializers() {
        return this._additionalKeyDeserializers.length > 0;
    }

    public boolean hasDeserializerModifiers() {
        return this._modifiers.length > 0;
    }

    public boolean hasAbstractTypeResolvers() {
        return this._abstractTypeResolvers.length > 0;
    }

    public boolean hasValueInstantiators() {
        return this._valueInstantiators.length > 0;
    }

    public Iterable<Deserializers> deserializers() {
        return ArrayBuilders.arrayAsIterable(this._additionalDeserializers);
    }

    public Iterable<KeyDeserializers> keyDeserializers() {
        return ArrayBuilders.arrayAsIterable(this._additionalKeyDeserializers);
    }

    public Iterable<BeanDeserializerModifier> deserializerModifiers() {
        return ArrayBuilders.arrayAsIterable(this._modifiers);
    }

    public Iterable<AbstractTypeResolver> abstractTypeResolvers() {
        return ArrayBuilders.arrayAsIterable(this._abstractTypeResolvers);
    }

    public Iterable<ValueInstantiators> valueInstantiators() {
        return ArrayBuilders.arrayAsIterable(this._valueInstantiators);
    }
}
