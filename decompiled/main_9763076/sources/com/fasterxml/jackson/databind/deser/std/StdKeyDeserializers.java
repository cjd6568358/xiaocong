package com.fasterxml.jackson.databind.deser.std;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.EnumResolver;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StdKeyDeserializers {
    protected final HashMap<JavaType, KeyDeserializer> _keyDeserializers = new HashMap<>();

    protected StdKeyDeserializers() {
        add(new StdKeyDeserializer.BoolKD());
        add(new StdKeyDeserializer.ByteKD());
        add(new StdKeyDeserializer.CharKD());
        add(new StdKeyDeserializer.ShortKD());
        add(new StdKeyDeserializer.IntKD());
        add(new StdKeyDeserializer.LongKD());
        add(new StdKeyDeserializer.FloatKD());
        add(new StdKeyDeserializer.DoubleKD());
        add(new StdKeyDeserializer.DateKD());
        add(new StdKeyDeserializer.CalendarKD());
        add(new StdKeyDeserializer.UuidKD());
        add(new StdKeyDeserializer.LocaleKD());
    }

    private void add(StdKeyDeserializer kdeser) {
        Class<?> keyClass = kdeser.getKeyClass();
        this._keyDeserializers.put(TypeFactory.defaultInstance().constructType(keyClass), kdeser);
    }

    public static HashMap<JavaType, KeyDeserializer> constructAll() {
        return new StdKeyDeserializers()._keyDeserializers;
    }

    public static KeyDeserializer constructStringKeyDeserializer(DeserializationConfig config, JavaType type) {
        return StdKeyDeserializer.StringKD.forType(type.getRawClass());
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver<?> enumResolver) {
        return new StdKeyDeserializer.EnumKD(enumResolver, null);
    }

    public static KeyDeserializer constructEnumKeyDeserializer(EnumResolver<?> enumResolver, AnnotatedMethod factory) {
        return new StdKeyDeserializer.EnumKD(enumResolver, factory);
    }

    public static KeyDeserializer constructDelegatingKeyDeserializer(DeserializationConfig config, JavaType type, JsonDeserializer<?> deser) {
        return new StdKeyDeserializer.DelegatingKD(type.getRawClass(), deser);
    }

    public static KeyDeserializer findStringBasedKeyDeserializer(DeserializationConfig config, JavaType type) {
        BeanDescription beanDesc = config.introspect(type);
        Constructor<?> ctor = beanDesc.findSingleArgConstructor(String.class);
        if (ctor != null) {
            if (config.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess(ctor);
            }
            return new StdKeyDeserializer.StringCtorKeyDeserializer(ctor);
        }
        Method m = beanDesc.findFactoryMethod(String.class);
        if (m != null) {
            if (config.canOverrideAccessModifiers()) {
                ClassUtil.checkAndFixAccess(m);
            }
            return new StdKeyDeserializer.StringFactoryKeyDeserializer(m);
        }
        return null;
    }
}
