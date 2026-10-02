package com.fasterxml.jackson.databind.cfg;

import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.util.ArrayBuilders;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class SerializerFactoryConfig {
    protected final Serializers[] _additionalKeySerializers;
    protected final Serializers[] _additionalSerializers;
    protected final BeanSerializerModifier[] _modifiers;
    protected static final Serializers[] NO_SERIALIZERS = new Serializers[0];
    protected static final BeanSerializerModifier[] NO_MODIFIERS = new BeanSerializerModifier[0];

    public SerializerFactoryConfig() {
        this(null, null, null);
    }

    protected SerializerFactoryConfig(Serializers[] allAdditionalSerializers, Serializers[] allAdditionalKeySerializers, BeanSerializerModifier[] modifiers) {
        this._additionalSerializers = allAdditionalSerializers == null ? NO_SERIALIZERS : allAdditionalSerializers;
        this._additionalKeySerializers = allAdditionalKeySerializers == null ? NO_SERIALIZERS : allAdditionalKeySerializers;
        this._modifiers = modifiers == null ? NO_MODIFIERS : modifiers;
    }

    public SerializerFactoryConfig withAdditionalSerializers(Serializers additional) {
        if (additional == null) {
            throw new IllegalArgumentException("Can not pass null Serializers");
        }
        Serializers[] all = (Serializers[]) ArrayBuilders.insertInListNoDup(this._additionalSerializers, additional);
        return new SerializerFactoryConfig(all, this._additionalKeySerializers, this._modifiers);
    }

    public SerializerFactoryConfig withAdditionalKeySerializers(Serializers additional) {
        if (additional == null) {
            throw new IllegalArgumentException("Can not pass null Serializers");
        }
        Serializers[] all = (Serializers[]) ArrayBuilders.insertInListNoDup(this._additionalKeySerializers, additional);
        return new SerializerFactoryConfig(this._additionalSerializers, all, this._modifiers);
    }

    public SerializerFactoryConfig withSerializerModifier(BeanSerializerModifier modifier) {
        if (modifier == null) {
            throw new IllegalArgumentException("Can not pass null modifier");
        }
        BeanSerializerModifier[] modifiers = (BeanSerializerModifier[]) ArrayBuilders.insertInListNoDup(this._modifiers, modifier);
        return new SerializerFactoryConfig(this._additionalSerializers, this._additionalKeySerializers, modifiers);
    }

    public boolean hasSerializers() {
        return this._additionalSerializers.length > 0;
    }

    public boolean hasKeySerializers() {
        return this._additionalKeySerializers.length > 0;
    }

    public boolean hasSerializerModifiers() {
        return this._modifiers.length > 0;
    }

    public Iterable<Serializers> serializers() {
        return ArrayBuilders.arrayAsIterable(this._additionalSerializers);
    }

    public Iterable<Serializers> keySerializers() {
        return ArrayBuilders.arrayAsIterable(this._additionalKeySerializers);
    }

    public Iterable<BeanSerializerModifier> serializerModifiers() {
        return ArrayBuilders.arrayAsIterable(this._modifiers);
    }
}
