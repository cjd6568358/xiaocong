package com.fasterxml.jackson.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@JacksonAnnotation
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFormat {
    public static final String DEFAULT_LOCALE = "##default";
    public static final String DEFAULT_TIMEZONE = "##default";

    String locale() default "##default";

    String pattern() default "";

    Shape shape() default Shape.ANY;

    String timezone() default "##default";

    public enum Shape {
        ANY,
        SCALAR,
        ARRAY,
        OBJECT,
        NUMBER,
        NUMBER_FLOAT,
        NUMBER_INT,
        STRING,
        BOOLEAN;

        public boolean isNumeric() {
            return this == NUMBER || this == NUMBER_INT || this == NUMBER_FLOAT;
        }

        public boolean isStructured() {
            return this == OBJECT || this == ARRAY;
        }
    }

    public static class Value {
        private final Locale locale;
        private final String pattern;
        private final Shape shape;
        private final TimeZone timezone;

        public Value(JsonFormat ann) {
            this(ann.pattern(), ann.shape(), ann.locale(), ann.timezone());
        }

        public Value(String p, Shape sh, String localeStr, String tzStr) {
            this.pattern = p;
            this.shape = sh;
            if (localeStr == null || localeStr.length() == 0 || "##default".equals(localeStr)) {
                this.locale = null;
            } else {
                this.locale = new Locale(localeStr);
            }
            if (tzStr == null || tzStr.length() == 0 || "##default".equals(tzStr)) {
                this.timezone = null;
            } else {
                this.timezone = TimeZone.getTimeZone(tzStr);
            }
        }

        public String getPattern() {
            return this.pattern;
        }

        public Shape getShape() {
            return this.shape;
        }

        public Locale getLocale() {
            return this.locale;
        }

        public TimeZone getTimeZone() {
            return this.timezone;
        }
    }
}
