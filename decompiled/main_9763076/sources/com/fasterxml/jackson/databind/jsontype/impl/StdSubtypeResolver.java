package com.fasterxml.jackson.databind.jsontype.impl;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StdSubtypeResolver extends SubtypeResolver {
    protected LinkedHashSet<NamedType> _registeredSubtypes;

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public void registerSubtypes(NamedType... types) {
        if (this._registeredSubtypes == null) {
            this._registeredSubtypes = new LinkedHashSet<>();
        }
        for (NamedType type : types) {
            this._registeredSubtypes.add(type);
        }
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public void registerSubtypes(Class<?>... classes) {
        NamedType[] types = new NamedType[classes.length];
        int len = classes.length;
        for (int i = 0; i < len; i++) {
            types[i] = new NamedType(classes[i]);
        }
        registerSubtypes(types);
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public Collection<NamedType> collectAndResolveSubtypes(AnnotatedMember property, MapperConfig<?> config, AnnotationIntrospector ai) {
        HashMap<NamedType, NamedType> collected = new HashMap<>();
        if (this._registeredSubtypes != null) {
            Class<?> rawBase = property.getRawType();
            for (NamedType subtype : this._registeredSubtypes) {
                if (rawBase.isAssignableFrom(subtype.getType())) {
                    AnnotatedClass curr = AnnotatedClass.constructWithoutSuperTypes(subtype.getType(), ai, config);
                    _collectAndResolve(curr, subtype, config, ai, collected);
                }
            }
        }
        Collection<NamedType> st = ai.findSubtypes(property);
        if (st != null) {
            for (NamedType nt : st) {
                AnnotatedClass ac = AnnotatedClass.constructWithoutSuperTypes(nt.getType(), ai, config);
                _collectAndResolve(ac, nt, config, ai, collected);
            }
        }
        NamedType rootType = new NamedType(property.getRawType(), null);
        AnnotatedClass ac2 = AnnotatedClass.constructWithoutSuperTypes(property.getRawType(), ai, config);
        _collectAndResolve(ac2, rootType, config, ai, collected);
        return new ArrayList(collected.values());
    }

    @Override // com.fasterxml.jackson.databind.jsontype.SubtypeResolver
    public Collection<NamedType> collectAndResolveSubtypes(AnnotatedClass type, MapperConfig<?> config, AnnotationIntrospector ai) {
        HashMap<NamedType, NamedType> subtypes = new HashMap<>();
        if (this._registeredSubtypes != null) {
            Class<?> rawBase = type.getRawType();
            for (NamedType subtype : this._registeredSubtypes) {
                if (rawBase.isAssignableFrom(subtype.getType())) {
                    AnnotatedClass curr = AnnotatedClass.constructWithoutSuperTypes(subtype.getType(), ai, config);
                    _collectAndResolve(curr, subtype, config, ai, subtypes);
                }
            }
        }
        NamedType rootType = new NamedType(type.getRawType(), null);
        _collectAndResolve(type, rootType, config, ai, subtypes);
        return new ArrayList(subtypes.values());
    }

    protected void _collectAndResolve(AnnotatedClass annotatedType, NamedType namedType, MapperConfig<?> config, AnnotationIntrospector ai, HashMap<NamedType, NamedType> collectedSubtypes) {
        String name;
        if (!namedType.hasName() && (name = ai.findTypeName(annotatedType)) != null) {
            namedType = new NamedType(namedType.getType(), name);
        }
        if (collectedSubtypes.containsKey(namedType)) {
            if (namedType.hasName()) {
                NamedType prev = collectedSubtypes.get(namedType);
                if (!prev.hasName()) {
                    collectedSubtypes.put(namedType, namedType);
                    return;
                }
                return;
            }
            return;
        }
        collectedSubtypes.put(namedType, namedType);
        Collection<NamedType> st = ai.findSubtypes(annotatedType);
        if (st != null && !st.isEmpty()) {
            for (NamedType subtype : st) {
                AnnotatedClass subtypeClass = AnnotatedClass.constructWithoutSuperTypes(subtype.getType(), ai, config);
                if (!subtype.hasName()) {
                    subtype = new NamedType(subtype.getType(), ai.findTypeName(subtypeClass));
                }
                _collectAndResolve(subtypeClass, subtype, config, ai, collectedSubtypes);
            }
        }
    }
}
