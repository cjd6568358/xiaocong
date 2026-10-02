package com.fasterxml.jackson.databind.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonPOJOBuilder {
    String buildMethodName() default "build";

    String withPrefix() default "with";

    public static class Value {
        public final String buildMethodName;
        public final String withPrefix;

        public Value(JsonPOJOBuilder ann) {
            this.buildMethodName = ann.buildMethodName();
            this.withPrefix = ann.withPrefix();
        }
    }
}
