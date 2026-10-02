package com.fasterxml.jackson.databind.module;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SimpleModule extends Module {
    protected SimpleAbstractTypeResolver _abstractTypes;
    protected SimpleDeserializers _deserializers;
    protected SimpleKeyDeserializers _keyDeserializers;
    protected SimpleSerializers _keySerializers;
    protected HashMap<Class<?>, Class<?>> _mixins;
    protected final String _name;
    protected SimpleSerializers _serializers;
    protected LinkedHashSet<NamedType> _subtypes;
    protected SimpleValueInstantiators _valueInstantiators;
    protected final Version _version;

    public SimpleModule() {
        this._serializers = null;
        this._deserializers = null;
        this._keySerializers = null;
        this._keyDeserializers = null;
        this._abstractTypes = null;
        this._valueInstantiators = null;
        this._mixins = null;
        this._subtypes = null;
        this._name = "SimpleModule-" + System.identityHashCode(this);
        this._version = Version.unknownVersion();
    }

    public SimpleModule(String name) {
        this(name, Version.unknownVersion());
    }

    public SimpleModule(Version version) {
        this._serializers = null;
        this._deserializers = null;
        this._keySerializers = null;
        this._keyDeserializers = null;
        this._abstractTypes = null;
        this._valueInstantiators = null;
        this._mixins = null;
        this._subtypes = null;
        this._name = version.getArtifactId();
        this._version = version;
    }

    public SimpleModule(String name, Version version) {
        this._serializers = null;
        this._deserializers = null;
        this._keySerializers = null;
        this._keyDeserializers = null;
        this._abstractTypes = null;
        this._valueInstantiators = null;
        this._mixins = null;
        this._subtypes = null;
        this._name = name;
        this._version = version;
    }

    public void setSerializers(SimpleSerializers s) {
        this._serializers = s;
    }

    public void setDeserializers(SimpleDeserializers d) {
        this._deserializers = d;
    }

    public void setKeySerializers(SimpleSerializers ks) {
        this._keySerializers = ks;
    }

    public void setKeyDeserializers(SimpleKeyDeserializers kd) {
        this._keyDeserializers = kd;
    }

    public void setAbstractTypes(SimpleAbstractTypeResolver atr) {
        this._abstractTypes = atr;
    }

    public void setValueInstantiators(SimpleValueInstantiators svi) {
        this._valueInstantiators = svi;
    }

    public SimpleModule addSerializer(JsonSerializer<?> ser) {
        if (this._serializers == null) {
            this._serializers = new SimpleSerializers();
        }
        this._serializers.addSerializer(ser);
        return this;
    }

    public <T> SimpleModule addSerializer(Class<? extends T> type, JsonSerializer<T> ser) {
        if (this._serializers == null) {
            this._serializers = new SimpleSerializers();
        }
        this._serializers.addSerializer(type, ser);
        return this;
    }

    public <T> SimpleModule addKeySerializer(Class<? extends T> type, JsonSerializer<T> ser) {
        if (this._keySerializers == null) {
            this._keySerializers = new SimpleSerializers();
        }
        this._keySerializers.addSerializer(type, ser);
        return this;
    }

    public <T> SimpleModule addDeserializer(Class<T> type, JsonDeserializer<? extends T> deser) {
        if (this._deserializers == null) {
            this._deserializers = new SimpleDeserializers();
        }
        this._deserializers.addDeserializer(type, deser);
        return this;
    }

    public SimpleModule addKeyDeserializer(Class<?> type, KeyDeserializer deser) {
        if (this._keyDeserializers == null) {
            this._keyDeserializers = new SimpleKeyDeserializers();
        }
        this._keyDeserializers.addDeserializer(type, deser);
        return this;
    }

    public <T> SimpleModule addAbstractTypeMapping(Class<T> superType, Class<? extends T> subType) {
        if (this._abstractTypes == null) {
            this._abstractTypes = new SimpleAbstractTypeResolver();
        }
        this._abstractTypes = this._abstractTypes.addMapping(superType, subType);
        return this;
    }

    public SimpleModule addValueInstantiator(Class<?> beanType, ValueInstantiator inst) {
        if (this._valueInstantiators == null) {
            this._valueInstantiators = new SimpleValueInstantiators();
        }
        this._valueInstantiators = this._valueInstantiators.addValueInstantiator(beanType, inst);
        return this;
    }

    public SimpleModule registerSubtypes(Class<?>... subtypes) {
        if (this._subtypes == null) {
            this._subtypes = new LinkedHashSet<>(Math.max(16, subtypes.length));
        }
        for (Class<?> subtype : subtypes) {
            this._subtypes.add(new NamedType(subtype));
        }
        return this;
    }

    public SimpleModule registerSubtypes(NamedType... subtypes) {
        if (this._subtypes == null) {
            this._subtypes = new LinkedHashSet<>(Math.max(16, subtypes.length));
        }
        for (NamedType subtype : subtypes) {
            this._subtypes.add(subtype);
        }
        return this;
    }

    public SimpleModule setMixInAnnotation(Class<?> targetType, Class<?> mixinClass) {
        if (this._mixins == null) {
            this._mixins = new HashMap<>();
        }
        this._mixins.put(targetType, mixinClass);
        return this;
    }

    @Override // com.fasterxml.jackson.databind.Module
    public String getModuleName() {
        return this._name;
    }

    @Override // com.fasterxml.jackson.databind.Module
    public void setupModule(Module.SetupContext context) {
        if (this._serializers != null) {
            context.addSerializers(this._serializers);
        }
        if (this._deserializers != null) {
            context.addDeserializers(this._deserializers);
        }
        if (this._keySerializers != null) {
            context.addKeySerializers(this._keySerializers);
        }
        if (this._keyDeserializers != null) {
            context.addKeyDeserializers(this._keyDeserializers);
        }
        if (this._abstractTypes != null) {
            context.addAbstractTypeResolver(this._abstractTypes);
        }
        if (this._valueInstantiators != null) {
            context.addValueInstantiators(this._valueInstantiators);
        }
        if (this._subtypes != null && this._subtypes.size() > 0) {
            context.registerSubtypes((NamedType[]) this._subtypes.toArray(new NamedType[this._subtypes.size()]));
        }
        if (this._mixins != null) {
            for (Map.Entry<Class<?>, Class<?>> entry : this._mixins.entrySet()) {
                context.setMixInAnnotations(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override // com.fasterxml.jackson.databind.Module, com.fasterxml.jackson.core.Versioned
    public Version version() {
        return this._version;
    }
}
