package com.fasterxml.jackson.databind.util;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SimpleBeanPropertyDefinition extends BeanPropertyDefinition {
    protected final AnnotatedMember _member;
    protected final String _name;

    public SimpleBeanPropertyDefinition(AnnotatedMember member) {
        this(member, member.getName());
    }

    public SimpleBeanPropertyDefinition(AnnotatedMember member, String name) {
        this._member = member;
        this._name = name;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public SimpleBeanPropertyDefinition withName(String newName) {
        return this._name.equals(newName) ? this : new SimpleBeanPropertyDefinition(this._member, newName);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition, com.fasterxml.jackson.databind.util.Named
    public String getName() {
        return this._name;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public String getInternalName() {
        return getName();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isExplicitlyIncluded() {
        return false;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasGetter() {
        return getGetter() != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasSetter() {
        return getSetter() != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasField() {
        return this._member instanceof AnnotatedField;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasConstructorParameter() {
        return this._member instanceof AnnotatedParameter;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getGetter() {
        if ((this._member instanceof AnnotatedMethod) && ((AnnotatedMethod) this._member).getParameterCount() == 0) {
            return (AnnotatedMethod) this._member;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getSetter() {
        if ((this._member instanceof AnnotatedMethod) && ((AnnotatedMethod) this._member).getParameterCount() == 1) {
            return (AnnotatedMethod) this._member;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedField getField() {
        if (this._member instanceof AnnotatedField) {
            return (AnnotatedField) this._member;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedParameter getConstructorParameter() {
        if (this._member instanceof AnnotatedParameter) {
            return (AnnotatedParameter) this._member;
        }
        return null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMember getAccessor() {
        AnnotatedMember acc = getGetter();
        if (acc == null) {
            return getField();
        }
        return acc;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMember getMutator() {
        AnnotatedMember acc = getConstructorParameter();
        if (acc == null) {
            AnnotatedMember acc2 = getSetter();
            if (acc2 == null) {
                return getField();
            }
            return acc2;
        }
        return acc;
    }
}
