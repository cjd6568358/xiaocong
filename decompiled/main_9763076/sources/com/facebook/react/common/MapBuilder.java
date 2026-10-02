package com.facebook.react.common;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MapBuilder {
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> Map<K, V> of() {
        return newHashMap();
    }

    public static <K, V> Map<K, V> of(K k1, V v1) {
        Map<K, V> mapOf = of();
        mapOf.put(k1, v1);
        return mapOf;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        Map<K, V> mapOf = of();
        mapOf.put(k1, v1);
        mapOf.put(k2, v2);
        return mapOf;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> mapOf = of();
        mapOf.put(k1, v1);
        mapOf.put(k2, v2);
        mapOf.put(k3, v3);
        return mapOf;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Map<K, V> mapOf = of();
        mapOf.put(k1, v1);
        mapOf.put(k2, v2);
        mapOf.put(k3, v3);
        mapOf.put(k4, v4);
        return mapOf;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Map<K, V> mapOf = of();
        mapOf.put(k1, v1);
        mapOf.put(k2, v2);
        mapOf.put(k3, v3);
        mapOf.put(k4, v4);
        mapOf.put(k5, v5);
        return mapOf;
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        Map<K, V> mapOf = of();
        mapOf.put(k1, v1);
        mapOf.put(k2, v2);
        mapOf.put(k3, v3);
        mapOf.put(k4, v4);
        mapOf.put(k5, v5);
        mapOf.put(k6, v6);
        return mapOf;
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static final class Builder<K, V> {
        private Map mMap;
        private boolean mUnderConstruction;

        private Builder() {
            this.mMap = MapBuilder.newHashMap();
            this.mUnderConstruction = true;
        }

        public Builder<K, V> put(K k, V v) {
            if (!this.mUnderConstruction) {
                throw new IllegalStateException("Underlying map has already been built");
            }
            this.mMap.put(k, v);
            return this;
        }

        public Map<K, V> build() {
            if (!this.mUnderConstruction) {
                throw new IllegalStateException("Underlying map has already been built");
            }
            this.mUnderConstruction = false;
            return this.mMap;
        }
    }
}
