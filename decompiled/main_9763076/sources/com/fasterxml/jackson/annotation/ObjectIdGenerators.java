package com.fasterxml.jackson.annotation;

import java.util.UUID;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ObjectIdGenerators {

    public static abstract class None extends ObjectIdGenerator<Object> {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static abstract class Base<T> extends ObjectIdGenerator<T> {
        protected final Class<?> _scope;

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public abstract T generateId(Object obj);

        protected Base(Class<?> scope) {
            this._scope = scope;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public final Class<?> getScope() {
            return this._scope;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public boolean canUseFor(ObjectIdGenerator<?> gen) {
            return gen.getClass() == getClass() && gen.getScope() == this._scope;
        }
    }

    public static abstract class PropertyGenerator extends Base<Object> {
        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public /* bridge */ /* synthetic */ boolean canUseFor(ObjectIdGenerator x0) {
            return super.canUseFor(x0);
        }

        protected PropertyGenerator(Class<?> scope) {
            super(scope);
        }
    }

    public static final class IntSequenceGenerator extends Base<Integer> {
        protected int _nextValue;

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public /* bridge */ /* synthetic */ boolean canUseFor(ObjectIdGenerator x0) {
            return super.canUseFor(x0);
        }

        public IntSequenceGenerator() {
            this(Object.class, -1);
        }

        public IntSequenceGenerator(Class<?> scope, int fv) {
            super(scope);
            this._nextValue = fv;
        }

        protected int initialValue() {
            return 1;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<Integer> forScope(Class<?> scope) {
            return this._scope == scope ? this : new IntSequenceGenerator(scope, this._nextValue);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<Integer> newForSerialization(Object context) {
            return new IntSequenceGenerator(this._scope, initialValue());
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator.IdKey key(Object key) {
            return new ObjectIdGenerator.IdKey(getClass(), this._scope, key);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public Integer generateId(Object forPojo) {
            int id = this._nextValue;
            this._nextValue++;
            return Integer.valueOf(id);
        }
    }

    public static final class UUIDGenerator extends Base<UUID> {
        public UUIDGenerator() {
            this(Object.class);
        }

        private UUIDGenerator(Class<?> scope) {
            super(Object.class);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<UUID> forScope(Class<?> scope) {
            return this;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator<UUID> newForSerialization(Object context) {
            return this;
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public UUID generateId(Object forPojo) {
            return UUID.randomUUID();
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerator
        public ObjectIdGenerator.IdKey key(Object key) {
            return new ObjectIdGenerator.IdKey(getClass(), null, key);
        }

        @Override // com.fasterxml.jackson.annotation.ObjectIdGenerators.Base, com.fasterxml.jackson.annotation.ObjectIdGenerator
        public boolean canUseFor(ObjectIdGenerator<?> gen) {
            return gen.getClass() == getClass();
        }
    }
}
