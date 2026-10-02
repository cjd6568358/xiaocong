package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class PropertyNamingStrategy {
    public static final PropertyNamingStrategy CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES = new LowerCaseWithUnderscoresStrategy();

    public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
        return defaultName;
    }

    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return defaultName;
    }

    public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        return defaultName;
    }

    public String nameForConstructorParameter(MapperConfig<?> config, AnnotatedParameter ctorParam, String defaultName) {
        return defaultName;
    }

    public static abstract class PropertyNamingStrategyBase extends PropertyNamingStrategy {
        public abstract String translate(String str);

        @Override // com.fasterxml.jackson.databind.PropertyNamingStrategy
        public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
            return translate(defaultName);
        }

        @Override // com.fasterxml.jackson.databind.PropertyNamingStrategy
        public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return translate(defaultName);
        }

        @Override // com.fasterxml.jackson.databind.PropertyNamingStrategy
        public String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return translate(defaultName);
        }

        @Override // com.fasterxml.jackson.databind.PropertyNamingStrategy
        public String nameForConstructorParameter(MapperConfig<?> config, AnnotatedParameter ctorParam, String defaultName) {
            return translate(defaultName);
        }
    }

    public static class LowerCaseWithUnderscoresStrategy extends PropertyNamingStrategyBase {
        @Override // com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase
        public String translate(String input) {
            if (input != null) {
                int length = input.length();
                StringBuilder result = new StringBuilder(length * 2);
                int resultLength = 0;
                boolean wasPrevTranslated = false;
                for (int i = 0; i < length; i++) {
                    char c = input.charAt(i);
                    if (i > 0 || c != '_') {
                        if (Character.isUpperCase(c)) {
                            if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != '_') {
                                result.append('_');
                                resultLength++;
                            }
                            c = Character.toLowerCase(c);
                            wasPrevTranslated = true;
                        } else {
                            wasPrevTranslated = false;
                        }
                        result.append(c);
                        resultLength++;
                    }
                }
                return resultLength > 0 ? result.toString() : input;
            }
            return input;
        }
    }
}
