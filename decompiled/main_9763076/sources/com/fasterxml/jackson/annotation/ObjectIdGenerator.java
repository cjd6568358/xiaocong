package com.fasterxml.jackson.annotation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class ObjectIdGenerator<T> {
    public abstract boolean canUseFor(ObjectIdGenerator<?> objectIdGenerator);

    public abstract ObjectIdGenerator<T> forScope(Class<?> cls);

    public abstract T generateId(Object obj);

    public abstract Class<?> getScope();

    public abstract IdKey key(Object obj);

    public abstract ObjectIdGenerator<T> newForSerialization(Object obj);

    public static final class IdKey {
        private final int hashCode;
        private final Object key;
        private final Class<?> scope;
        private final Class<?> type;

        public IdKey(Class<?> type, Class<?> scope, Object key) {
            this.type = type;
            this.scope = scope;
            this.key = key;
            int h = key.hashCode() + type.getName().hashCode();
            this.hashCode = scope != null ? h ^ scope.getName().hashCode() : h;
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o != null && o.getClass() == getClass()) {
                IdKey other = (IdKey) o;
                return other.key.equals(this.key) && other.type == this.type && other.scope == this.scope;
            }
            return false;
        }
    }
}
