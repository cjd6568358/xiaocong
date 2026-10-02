package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.util.Named;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class BeanPropertyDefinition implements Named {
    public abstract AnnotatedMember getAccessor();

    public abstract AnnotatedParameter getConstructorParameter();

    public abstract AnnotatedField getField();

    public abstract AnnotatedMethod getGetter();

    public abstract String getInternalName();

    public abstract AnnotatedMember getMutator();

    @Override // com.fasterxml.jackson.databind.util.Named
    public abstract String getName();

    public abstract AnnotatedMethod getSetter();

    public abstract boolean hasConstructorParameter();

    public abstract boolean hasField();

    public abstract boolean hasGetter();

    public abstract boolean hasSetter();

    public abstract boolean isExplicitlyIncluded();

    public abstract BeanPropertyDefinition withName(String str);

    public boolean couldDeserialize() {
        return getMutator() != null;
    }

    public boolean couldSerialize() {
        return getAccessor() != null;
    }

    public Class<?>[] findViews() {
        return null;
    }

    public AnnotationIntrospector.ReferenceProperty findReferenceType() {
        return null;
    }

    public boolean isTypeId() {
        return false;
    }

    public ObjectIdInfo findObjectIdInfo() {
        return null;
    }

    public boolean isRequired() {
        return false;
    }
}
