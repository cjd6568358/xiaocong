package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.type.ClassKey;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RootNameLookup {
    protected LRUMap<ClassKey, SerializedString> _rootNames;

    public SerializedString findRootName(JavaType rootType, MapperConfig<?> config) {
        return findRootName(rootType.getRawClass(), config);
    }

    public synchronized SerializedString findRootName(Class<?> rootType, MapperConfig<?> config) {
        SerializedString name;
        ClassKey key = new ClassKey(rootType);
        if (this._rootNames == null) {
            this._rootNames = new LRUMap<>(20, 200);
        } else {
            name = this._rootNames.get(key);
            if (name == null) {
            }
        }
        BeanDescription beanDesc = config.introspectClassAnnotations(rootType);
        AnnotationIntrospector intr = config.getAnnotationIntrospector();
        AnnotatedClass ac = beanDesc.getClassInfo();
        String nameStr = intr.findRootName(ac);
        if (nameStr == null) {
            nameStr = rootType.getSimpleName();
        }
        name = new SerializedString(nameStr);
        this._rootNames.put(key, name);
        return name;
    }
}
