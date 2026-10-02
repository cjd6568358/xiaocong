package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.Named;
import java.lang.annotation.Annotation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface BeanProperty extends Named {
    <A extends Annotation> A getAnnotation(Class<A> cls);

    <A extends Annotation> A getContextAnnotation(Class<A> cls);

    AnnotatedMember getMember();

    @Override // com.fasterxml.jackson.databind.util.Named
    String getName();

    JavaType getType();

    public static class Std implements BeanProperty {
        protected final Annotations _contextAnnotations;
        protected final AnnotatedMember _member;
        protected final String _name;
        protected final JavaType _type;

        public Std(String name, JavaType type, Annotations contextAnnotations, AnnotatedMember member) {
            this._name = name;
            this._type = type;
            this._member = member;
            this._contextAnnotations = contextAnnotations;
        }

        public Std withType(JavaType type) {
            return new Std(this._name, type, this._contextAnnotations, this._member);
        }

        @Override // com.fasterxml.jackson.databind.BeanProperty
        public <A extends Annotation> A getAnnotation(Class<A> cls) {
            if (this._member == null) {
                return null;
            }
            return (A) this._member.getAnnotation(cls);
        }

        @Override // com.fasterxml.jackson.databind.BeanProperty
        public <A extends Annotation> A getContextAnnotation(Class<A> cls) {
            if (this._contextAnnotations == null) {
                return null;
            }
            return (A) this._contextAnnotations.get(cls);
        }

        @Override // com.fasterxml.jackson.databind.BeanProperty, com.fasterxml.jackson.databind.util.Named
        public String getName() {
            return this._name;
        }

        @Override // com.fasterxml.jackson.databind.BeanProperty
        public JavaType getType() {
            return this._type;
        }

        @Override // com.fasterxml.jackson.databind.BeanProperty
        public AnnotatedMember getMember() {
            return this._member;
        }
    }
}
