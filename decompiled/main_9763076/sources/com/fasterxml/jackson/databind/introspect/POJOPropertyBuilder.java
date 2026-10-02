package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class POJOPropertyBuilder extends BeanPropertyDefinition implements Comparable<POJOPropertyBuilder> {
    protected final AnnotationIntrospector _annotationIntrospector;
    protected Linked<AnnotatedParameter> _ctorParameters;
    protected Linked<AnnotatedField> _fields;
    protected final boolean _forSerialization;
    protected Linked<AnnotatedMethod> _getters;
    protected final String _internalName;
    protected final String _name;
    protected Linked<AnnotatedMethod> _setters;

    private interface WithMember<T> {
        T withMember(AnnotatedMember annotatedMember);
    }

    public POJOPropertyBuilder(String internalName, AnnotationIntrospector annotationIntrospector, boolean forSerialization) {
        this._internalName = internalName;
        this._name = internalName;
        this._annotationIntrospector = annotationIntrospector;
        this._forSerialization = forSerialization;
    }

    public POJOPropertyBuilder(POJOPropertyBuilder src, String newName) {
        this._internalName = src._internalName;
        this._name = newName;
        this._annotationIntrospector = src._annotationIntrospector;
        this._fields = src._fields;
        this._ctorParameters = src._ctorParameters;
        this._getters = src._getters;
        this._setters = src._setters;
        this._forSerialization = src._forSerialization;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public POJOPropertyBuilder withName(String newName) {
        return new POJOPropertyBuilder(this, newName);
    }

    @Override // java.lang.Comparable
    public int compareTo(POJOPropertyBuilder other) {
        if (this._ctorParameters != null) {
            if (other._ctorParameters == null) {
                return -1;
            }
        } else if (other._ctorParameters != null) {
            return 1;
        }
        return getName().compareTo(other.getName());
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition, com.fasterxml.jackson.databind.util.Named
    public String getName() {
        return this._name;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public String getInternalName() {
        return this._internalName;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isExplicitlyIncluded() {
        return _anyExplicitNames(this._fields) || _anyExplicitNames(this._getters) || _anyExplicitNames(this._setters) || _anyExplicitNames(this._ctorParameters);
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasGetter() {
        return this._getters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasSetter() {
        return this._setters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasField() {
        return this._fields != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean hasConstructorParameter() {
        return this._ctorParameters != null;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean couldSerialize() {
        return (this._getters == null && this._fields == null) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getGetter() {
        if (this._getters == null) {
            return null;
        }
        AnnotatedMethod getter = this._getters.value;
        for (Linked linked = this._getters.next; linked != null; linked = linked.next) {
            AnnotatedMethod nextGetter = (AnnotatedMethod) linked.value;
            Class<?> getterClass = getter.getDeclaringClass();
            Class<?> nextClass = nextGetter.getDeclaringClass();
            if (getterClass != nextClass) {
                if (getterClass.isAssignableFrom(nextClass)) {
                    getter = nextGetter;
                } else if (!nextClass.isAssignableFrom(getterClass)) {
                }
            }
            throw new IllegalArgumentException("Conflicting getter definitions for property \"" + getName() + "\": " + getter.getFullName() + " vs " + nextGetter.getFullName());
        }
        return getter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMethod getSetter() {
        if (this._setters == null) {
            return null;
        }
        AnnotatedMethod setter = this._setters.value;
        for (Linked linked = this._setters.next; linked != null; linked = linked.next) {
            AnnotatedMethod nextSetter = (AnnotatedMethod) linked.value;
            Class<?> setterClass = setter.getDeclaringClass();
            Class<?> nextClass = nextSetter.getDeclaringClass();
            if (setterClass != nextClass) {
                if (setterClass.isAssignableFrom(nextClass)) {
                    setter = nextSetter;
                } else if (!nextClass.isAssignableFrom(setterClass)) {
                }
            }
            throw new IllegalArgumentException("Conflicting setter definitions for property \"" + getName() + "\": " + setter.getFullName() + " vs " + nextSetter.getFullName());
        }
        return setter;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedField getField() {
        if (this._fields == null) {
            return null;
        }
        AnnotatedField field = this._fields.value;
        for (Linked linked = this._fields.next; linked != null; linked = linked.next) {
            AnnotatedField nextField = (AnnotatedField) linked.value;
            Class<?> fieldClass = field.getDeclaringClass();
            Class<?> nextClass = nextField.getDeclaringClass();
            if (fieldClass != nextClass) {
                if (fieldClass.isAssignableFrom(nextClass)) {
                    field = nextField;
                } else if (!nextClass.isAssignableFrom(fieldClass)) {
                }
            }
            throw new IllegalArgumentException("Multiple fields representing property \"" + getName() + "\": " + field.getFullName() + " vs " + nextField.getFullName());
        }
        return field;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedParameter getConstructorParameter() {
        if (this._ctorParameters == null) {
            return null;
        }
        Linked linked = this._ctorParameters;
        while (!(((AnnotatedParameter) linked.value).getOwner() instanceof AnnotatedConstructor)) {
            linked = linked.next;
            if (linked == null) {
                return this._ctorParameters.value;
            }
        }
        return (AnnotatedParameter) linked.value;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMember getAccessor() {
        AnnotatedMember m = getGetter();
        if (m == null) {
            return getField();
        }
        return m;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotatedMember getMutator() {
        AnnotatedMember m = getConstructorParameter();
        if (m == null) {
            AnnotatedMember m2 = getSetter();
            if (m2 == null) {
                return getField();
            }
            return m2;
        }
        return m;
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public Class<?>[] findViews() {
        return (Class[]) fromMemberAnnotation(new WithMember<Class<?>[]>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.1
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public Class<?>[] withMember(AnnotatedMember member) {
                return POJOPropertyBuilder.this._annotationIntrospector.findViews(member);
            }
        });
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public AnnotationIntrospector.ReferenceProperty findReferenceType() {
        return (AnnotationIntrospector.ReferenceProperty) fromMemberAnnotation(new WithMember<AnnotationIntrospector.ReferenceProperty>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public AnnotationIntrospector.ReferenceProperty withMember(AnnotatedMember member) {
                return POJOPropertyBuilder.this._annotationIntrospector.findReferenceType(member);
            }
        });
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isTypeId() {
        Boolean b = (Boolean) fromMemberAnnotation(new WithMember<Boolean>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public Boolean withMember(AnnotatedMember member) {
                return POJOPropertyBuilder.this._annotationIntrospector.isTypeId(member);
            }
        });
        return b != null && b.booleanValue();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public boolean isRequired() {
        Boolean b = (Boolean) fromMemberAnnotation(new WithMember<Boolean>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public Boolean withMember(AnnotatedMember member) {
                return POJOPropertyBuilder.this._annotationIntrospector.hasRequiredMarker(member);
            }
        });
        return b != null && b.booleanValue();
    }

    @Override // com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition
    public ObjectIdInfo findObjectIdInfo() {
        return (ObjectIdInfo) fromMemberAnnotation(new WithMember<ObjectIdInfo>() { // from class: com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder.WithMember
            public ObjectIdInfo withMember(AnnotatedMember member) {
                return POJOPropertyBuilder.this._annotationIntrospector.findObjectIdInfo(member);
            }
        });
    }

    public void addField(AnnotatedField a, String ename, boolean visible, boolean ignored) {
        this._fields = new Linked<>(a, this._fields, ename, visible, ignored);
    }

    public void addCtor(AnnotatedParameter a, String ename, boolean visible, boolean ignored) {
        this._ctorParameters = new Linked<>(a, this._ctorParameters, ename, visible, ignored);
    }

    public void addGetter(AnnotatedMethod a, String ename, boolean visible, boolean ignored) {
        this._getters = new Linked<>(a, this._getters, ename, visible, ignored);
    }

    public void addSetter(AnnotatedMethod a, String ename, boolean visible, boolean ignored) {
        this._setters = new Linked<>(a, this._setters, ename, visible, ignored);
    }

    public void addAll(POJOPropertyBuilder src) {
        this._fields = merge(this._fields, src._fields);
        this._ctorParameters = merge(this._ctorParameters, src._ctorParameters);
        this._getters = merge(this._getters, src._getters);
        this._setters = merge(this._setters, src._setters);
    }

    private static <T> Linked<T> merge(Linked<T> chain1, Linked<T> chain2) {
        if (chain1 == null) {
            return chain2;
        }
        return chain2 == null ? chain1 : chain1.append(chain2);
    }

    public void removeIgnored() {
        this._fields = _removeIgnored(this._fields);
        this._getters = _removeIgnored(this._getters);
        this._setters = _removeIgnored(this._setters);
        this._ctorParameters = _removeIgnored(this._ctorParameters);
    }

    public void removeNonVisible() {
        this._getters = _removeNonVisible(this._getters);
        this._ctorParameters = _removeNonVisible(this._ctorParameters);
        if (this._getters == null) {
            this._fields = _removeNonVisible(this._fields);
            this._setters = _removeNonVisible(this._setters);
        }
    }

    public void trimByVisibility() {
        this._fields = _trimByVisibility(this._fields);
        this._getters = _trimByVisibility(this._getters);
        this._setters = _trimByVisibility(this._setters);
        this._ctorParameters = _trimByVisibility(this._ctorParameters);
    }

    public void mergeAnnotations(boolean forSerialization) {
        if (forSerialization) {
            if (this._getters != null) {
                AnnotationMap ann = _mergeAnnotations(0, this._getters, this._fields, this._ctorParameters, this._setters);
                this._getters = this._getters.withValue(this._getters.value.withAnnotations(ann));
                return;
            } else {
                if (this._fields != null) {
                    AnnotationMap ann2 = _mergeAnnotations(0, this._fields, this._ctorParameters, this._setters);
                    this._fields = this._fields.withValue(this._fields.value.withAnnotations(ann2));
                    return;
                }
                return;
            }
        }
        if (this._ctorParameters != null) {
            AnnotationMap ann3 = _mergeAnnotations(0, this._ctorParameters, this._setters, this._fields, this._getters);
            this._ctorParameters = this._ctorParameters.withValue(this._ctorParameters.value.withAnnotations(ann3));
        } else if (this._setters != null) {
            AnnotationMap ann4 = _mergeAnnotations(0, this._setters, this._fields, this._getters);
            this._setters = this._setters.withValue(this._setters.value.withAnnotations(ann4));
        } else if (this._fields != null) {
            AnnotationMap ann5 = _mergeAnnotations(0, this._fields, this._getters);
            this._fields = this._fields.withValue(this._fields.value.withAnnotations(ann5));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private AnnotationMap _mergeAnnotations(int index, Linked<? extends AnnotatedMember>... nodes) {
        AnnotationMap ann = ((AnnotatedMember) nodes[index].value).getAllAnnotations();
        do {
            index++;
            if (index >= nodes.length) {
                return ann;
            }
        } while (nodes[index] == null);
        return AnnotationMap.merge(ann, _mergeAnnotations(index, nodes));
    }

    private <T> Linked<T> _removeIgnored(Linked<T> node) {
        return node == null ? node : node.withoutIgnored();
    }

    private <T> Linked<T> _removeNonVisible(Linked<T> node) {
        return node == null ? node : node.withoutNonVisible();
    }

    private <T> Linked<T> _trimByVisibility(Linked<T> node) {
        return node == null ? node : node.trimByVisibility();
    }

    private <T> boolean _anyExplicitNames(Linked<T> n) {
        while (n != null) {
            if (n.explicitName == null || n.explicitName.length() <= 0) {
                n = n.next;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean anyVisible() {
        return _anyVisible(this._fields) || _anyVisible(this._getters) || _anyVisible(this._setters) || _anyVisible(this._ctorParameters);
    }

    private <T> boolean _anyVisible(Linked<T> n) {
        while (n != null) {
            if (!n.isVisible) {
                n = n.next;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean anyIgnorals() {
        return _anyIgnorals(this._fields) || _anyIgnorals(this._getters) || _anyIgnorals(this._setters) || _anyIgnorals(this._ctorParameters);
    }

    private <T> boolean _anyIgnorals(Linked<T> n) {
        while (n != null) {
            if (!n.isMarkedIgnored) {
                n = n.next;
            } else {
                return true;
            }
        }
        return false;
    }

    public String findNewName() {
        Linked<? extends AnnotatedMember> renamed = findRenamed(this._fields, null);
        Linked<? extends AnnotatedMember> renamed2 = findRenamed(this._ctorParameters, findRenamed(this._setters, findRenamed(this._getters, renamed)));
        if (renamed2 == null) {
            return null;
        }
        return renamed2.explicitName;
    }

    private Linked<? extends AnnotatedMember> findRenamed(Linked<? extends AnnotatedMember> linked, Linked<? extends AnnotatedMember> linked2) {
        Linked linked3 = linked;
        Linked linked4 = linked2;
        while (linked3 != null) {
            String str = linked3.explicitName;
            if (str != null && !str.equals(this._name)) {
                if (linked4 == null) {
                    linked4 = linked3;
                } else if (!str.equals(linked4.explicitName)) {
                    throw new IllegalStateException("Conflicting property name definitions: '" + linked4.explicitName + "' (for " + linked4.value + ") vs '" + linked3.explicitName + "' (for " + linked3.value + ")");
                }
            }
            linked3 = linked3.next;
            linked4 = linked4;
        }
        return linked4;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Property '").append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
        sb.append("]");
        return sb.toString();
    }

    protected <T> T fromMemberAnnotation(WithMember<T> func) {
        T result = null;
        if (this._annotationIntrospector == null) {
            return null;
        }
        if (this._forSerialization) {
            if (this._getters != null) {
                result = func.withMember(this._getters.value);
            }
        } else {
            if (this._ctorParameters != null) {
                result = func.withMember(this._ctorParameters.value);
            }
            if (result == null && this._setters != null) {
                result = func.withMember(this._setters.value);
            }
        }
        if (result == null && this._fields != null) {
            return func.withMember(this._fields.value);
        }
        return result;
    }

    private static final class Linked<T> {
        public final String explicitName;
        public final boolean isMarkedIgnored;
        public final boolean isVisible;
        public final Linked<T> next;
        public final T value;

        public Linked(T v, Linked<T> n, String explName, boolean visible, boolean ignored) {
            this.value = v;
            this.next = n;
            if (explName == null) {
                this.explicitName = null;
            } else {
                this.explicitName = explName.length() == 0 ? null : explName;
            }
            this.isVisible = visible;
            this.isMarkedIgnored = ignored;
        }

        public Linked<T> withValue(T newValue) {
            return newValue == this.value ? this : new Linked<>(newValue, this.next, this.explicitName, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withNext(Linked<T> newNext) {
            return newNext == this.next ? this : new Linked<>(this.value, newNext, this.explicitName, this.isVisible, this.isMarkedIgnored);
        }

        public Linked<T> withoutIgnored() {
            Linked<T> newNext;
            if (!this.isMarkedIgnored) {
                return (this.next == null || (newNext = this.next.withoutIgnored()) == this.next) ? this : withNext(newNext);
            }
            if (this.next == null) {
                return null;
            }
            return this.next.withoutIgnored();
        }

        public Linked<T> withoutNonVisible() {
            Linked<T> newNext = this.next == null ? null : this.next.withoutNonVisible();
            return this.isVisible ? withNext(newNext) : newNext;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Linked<T> append(Linked<T> appendable) {
            return this.next == null ? withNext(appendable) : withNext(this.next.append(appendable));
        }

        public Linked<T> trimByVisibility() {
            if (this.next == null) {
                return this;
            }
            Linked<T> newNext = this.next.trimByVisibility();
            if (this.explicitName != null) {
                if (newNext.explicitName == null) {
                    return withNext(null);
                }
                return withNext(newNext);
            }
            if (newNext.explicitName == null) {
                if (this.isVisible == newNext.isVisible) {
                    return withNext(newNext);
                }
                return this.isVisible ? withNext(null) : newNext;
            }
            return newNext;
        }

        public String toString() {
            String msg = this.value.toString() + "[visible=" + this.isVisible + "]";
            if (this.next != null) {
                return msg + ", " + this.next.toString();
            }
            return msg;
        }
    }
}
