package com.fasterxml.jackson.databind.jsonschema;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonAnnotation
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonSerializableSchema {
    public static final String NO_VALUE = "##irrelevant";

    String id() default "";

    String schemaItemDefinition() default "##irrelevant";

    String schemaObjectPropertiesDefinition() default "##irrelevant";

    String schemaType() default "any";
}
