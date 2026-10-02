package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface VisibilityChecker<T extends VisibilityChecker<T>> {
    boolean isCreatorVisible(AnnotatedMember annotatedMember);

    boolean isCreatorVisible(Member member);

    boolean isFieldVisible(AnnotatedField annotatedField);

    boolean isFieldVisible(Field field);

    boolean isGetterVisible(AnnotatedMethod annotatedMethod);

    boolean isGetterVisible(Method method);

    boolean isIsGetterVisible(AnnotatedMethod annotatedMethod);

    boolean isIsGetterVisible(Method method);

    boolean isSetterVisible(AnnotatedMethod annotatedMethod);

    boolean isSetterVisible(Method method);

    T with(JsonAutoDetect.Visibility visibility);

    T with(JsonAutoDetect jsonAutoDetect);

    T withCreatorVisibility(JsonAutoDetect.Visibility visibility);

    T withFieldVisibility(JsonAutoDetect.Visibility visibility);

    T withGetterVisibility(JsonAutoDetect.Visibility visibility);

    T withIsGetterVisibility(JsonAutoDetect.Visibility visibility);

    T withSetterVisibility(JsonAutoDetect.Visibility visibility);

    T withVisibility(PropertyAccessor propertyAccessor, JsonAutoDetect.Visibility visibility);

    @JsonAutoDetect(creatorVisibility = JsonAutoDetect.Visibility.ANY, fieldVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, isGetterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, setterVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Std implements VisibilityChecker<Std> {
        protected static final Std DEFAULT = new Std((JsonAutoDetect) Std.class.getAnnotation(JsonAutoDetect.class));
        protected final JsonAutoDetect.Visibility _creatorMinLevel;
        protected final JsonAutoDetect.Visibility _fieldMinLevel;
        protected final JsonAutoDetect.Visibility _getterMinLevel;
        protected final JsonAutoDetect.Visibility _isGetterMinLevel;
        protected final JsonAutoDetect.Visibility _setterMinLevel;

        public static Std defaultInstance() {
            return DEFAULT;
        }

        public Std(JsonAutoDetect ann) {
            this._getterMinLevel = ann.getterVisibility();
            this._isGetterMinLevel = ann.isGetterVisibility();
            this._setterMinLevel = ann.setterVisibility();
            this._creatorMinLevel = ann.creatorVisibility();
            this._fieldMinLevel = ann.fieldVisibility();
        }

        public Std(JsonAutoDetect.Visibility getter, JsonAutoDetect.Visibility isGetter, JsonAutoDetect.Visibility setter, JsonAutoDetect.Visibility creator, JsonAutoDetect.Visibility field) {
            this._getterMinLevel = getter;
            this._isGetterMinLevel = isGetter;
            this._setterMinLevel = setter;
            this._creatorMinLevel = creator;
            this._fieldMinLevel = field;
        }

        public Std(JsonAutoDetect.Visibility v) {
            if (v == JsonAutoDetect.Visibility.DEFAULT) {
                this._getterMinLevel = DEFAULT._getterMinLevel;
                this._isGetterMinLevel = DEFAULT._isGetterMinLevel;
                this._setterMinLevel = DEFAULT._setterMinLevel;
                this._creatorMinLevel = DEFAULT._creatorMinLevel;
                this._fieldMinLevel = DEFAULT._fieldMinLevel;
                return;
            }
            this._getterMinLevel = v;
            this._isGetterMinLevel = v;
            this._setterMinLevel = v;
            this._creatorMinLevel = v;
            this._fieldMinLevel = v;
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public Std with(JsonAutoDetect ann) {
            if (ann == null) {
                return this;
            }
            return withGetterVisibility(ann.getterVisibility()).withIsGetterVisibility(ann.isGetterVisibility()).withSetterVisibility(ann.setterVisibility()).withCreatorVisibility(ann.creatorVisibility()).withFieldVisibility(ann.fieldVisibility());
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public Std with(JsonAutoDetect.Visibility v) {
            return v == JsonAutoDetect.Visibility.DEFAULT ? DEFAULT : new Std(v);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public Std withVisibility(PropertyAccessor method, JsonAutoDetect.Visibility v) {
            switch (method) {
                case GETTER:
                    return withGetterVisibility(v);
                case SETTER:
                    return withSetterVisibility(v);
                case CREATOR:
                    return withCreatorVisibility(v);
                case FIELD:
                    return withFieldVisibility(v);
                case IS_GETTER:
                    return withIsGetterVisibility(v);
                case ALL:
                    return with(v);
                default:
                    return this;
            }
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public Std withGetterVisibility(JsonAutoDetect.Visibility v) {
            if (v == JsonAutoDetect.Visibility.DEFAULT) {
                v = DEFAULT._getterMinLevel;
            }
            if (this._getterMinLevel == v) {
                return this;
            }
            return new Std(v, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public Std withIsGetterVisibility(JsonAutoDetect.Visibility v) {
            if (v == JsonAutoDetect.Visibility.DEFAULT) {
                v = DEFAULT._isGetterMinLevel;
            }
            if (this._isGetterMinLevel == v) {
                return this;
            }
            return new Std(this._getterMinLevel, v, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public Std withSetterVisibility(JsonAutoDetect.Visibility v) {
            if (v == JsonAutoDetect.Visibility.DEFAULT) {
                v = DEFAULT._setterMinLevel;
            }
            if (this._setterMinLevel == v) {
                return this;
            }
            return new Std(this._getterMinLevel, this._isGetterMinLevel, v, this._creatorMinLevel, this._fieldMinLevel);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public Std withCreatorVisibility(JsonAutoDetect.Visibility v) {
            if (v == JsonAutoDetect.Visibility.DEFAULT) {
                v = DEFAULT._creatorMinLevel;
            }
            if (this._creatorMinLevel == v) {
                return this;
            }
            return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, v, this._fieldMinLevel);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public Std withFieldVisibility(JsonAutoDetect.Visibility v) {
            if (v == JsonAutoDetect.Visibility.DEFAULT) {
                v = DEFAULT._fieldMinLevel;
            }
            return this._fieldMinLevel == v ? this : new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, v);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isCreatorVisible(Member m) {
            return this._creatorMinLevel.isVisible(m);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isCreatorVisible(AnnotatedMember m) {
            return isCreatorVisible(m.getMember());
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isFieldVisible(Field f) {
            return this._fieldMinLevel.isVisible(f);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isFieldVisible(AnnotatedField f) {
            return isFieldVisible(f.getAnnotated());
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isGetterVisible(Method m) {
            return this._getterMinLevel.isVisible(m);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isGetterVisible(AnnotatedMethod m) {
            return isGetterVisible(m.getAnnotated());
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isIsGetterVisible(Method m) {
            return this._isGetterMinLevel.isVisible(m);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isIsGetterVisible(AnnotatedMethod m) {
            return isIsGetterVisible(m.getAnnotated());
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isSetterVisible(Method m) {
            return this._setterMinLevel.isVisible(m);
        }

        @Override // com.fasterxml.jackson.databind.introspect.VisibilityChecker
        public boolean isSetterVisible(AnnotatedMethod m) {
            return isSetterVisible(m.getAnnotated());
        }

        public String toString() {
            return "[Visibility: getter: " + this._getterMinLevel + ", isGetter: " + this._isGetterMinLevel + ", setter: " + this._setterMinLevel + ", creator: " + this._creatorMinLevel + ", field: " + this._fieldMinLevel + "]";
        }
    }
}
