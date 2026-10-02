package com.fasterxml.jackson.databind.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.databind.JsonSerializer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSerialize {

    public enum Inclusion {
        ALWAYS,
        NON_NULL,
        NON_DEFAULT,
        NON_EMPTY
    }

    public enum Typing {
        DYNAMIC,
        STATIC
    }

    Class<?> as() default NoClass.class;

    Class<?> contentAs() default NoClass.class;

    Class<? extends JsonSerializer<?>> contentUsing() default JsonSerializer.None.class;

    @Deprecated
    Inclusion include() default Inclusion.ALWAYS;

    Class<?> keyAs() default NoClass.class;

    Class<? extends JsonSerializer<?>> keyUsing() default JsonSerializer.None.class;

    Typing typing() default Typing.DYNAMIC;

    Class<? extends JsonSerializer<?>> using() default JsonSerializer.None.class;
}
