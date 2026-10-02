package com.fasterxml.jackson.databind;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class InjectableValues {
    public abstract Object findInjectableValue(Object obj, DeserializationContext deserializationContext, BeanProperty beanProperty, Object obj2);

    public static class Std extends InjectableValues {
        protected final Map<String, Object> _values;

        public Std() {
            this(new HashMap());
        }

        public Std(Map<String, Object> values) {
            this._values = values;
        }

        public Std addValue(String key, Object value) {
            this._values.put(key, value);
            return this;
        }

        public Std addValue(Class<?> classKey, Object value) {
            this._values.put(classKey.getName(), value);
            return this;
        }

        @Override // com.fasterxml.jackson.databind.InjectableValues
        public Object findInjectableValue(Object valueId, DeserializationContext ctxt, BeanProperty forProperty, Object beanInstance) {
            if (!(valueId instanceof String)) {
                String type = valueId == null ? "[null]" : valueId.getClass().getName();
                throw new IllegalArgumentException("Unrecognized inject value id type (" + type + "), expecting String");
            }
            String key = (String) valueId;
            Object ob = this._values.get(key);
            if (ob == null && !this._values.containsKey(key)) {
                throw new IllegalArgumentException("No injectable id with value '" + key + "' found (for property '" + forProperty.getName() + "')");
            }
            return ob;
        }
    }
}
