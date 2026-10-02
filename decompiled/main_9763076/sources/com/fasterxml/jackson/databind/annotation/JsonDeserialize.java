package com.fasterxml.jackson.databind.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.KeyDeserializer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonDeserialize {
    Class<?> as() default NoClass.class;

    Class<?> builder() default NoClass.class;

    Class<?> contentAs() default NoClass.class;

    Class<? extends JsonDeserializer<?>> contentUsing() default JsonDeserializer.None.class;

    Class<?> keyAs() default NoClass.class;

    Class<? extends KeyDeserializer> keyUsing() default KeyDeserializer.None.class;

    Class<? extends JsonDeserializer<?>> using() default JsonDeserializer.None.class;
}
