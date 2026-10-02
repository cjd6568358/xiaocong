package com.alibaba.fastjson.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ParameterizedTypeImpl implements ParameterizedType {
    private final Type[] actualTypeArguments;
    private final Type ownerType;
    private final Type rawType;

    public ParameterizedTypeImpl(Type[] actualTypeArguments, Type ownerType, Type rawType) {
        this.actualTypeArguments = actualTypeArguments;
        this.ownerType = ownerType;
        this.rawType = rawType;
    }

    @Override // java.lang.reflect.ParameterizedType
    public Type[] getActualTypeArguments() {
        return this.actualTypeArguments;
    }

    @Override // java.lang.reflect.ParameterizedType
    public Type getOwnerType() {
        return this.ownerType;
    }

    @Override // java.lang.reflect.ParameterizedType
    public Type getRawType() {
        return this.rawType;
    }

    public boolean equals(Object o) {
        boolean zEquals = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParameterizedTypeImpl that = (ParameterizedTypeImpl) o;
        if (!Arrays.equals(this.actualTypeArguments, that.actualTypeArguments)) {
            return false;
        }
        if (this.ownerType != null) {
            if (!this.ownerType.equals(that.ownerType)) {
                return false;
            }
        } else if (that.ownerType != null) {
            return false;
        }
        if (this.rawType != null) {
            zEquals = this.rawType.equals(that.rawType);
        } else if (that.rawType != null) {
            zEquals = false;
        }
        return zEquals;
    }

    public int hashCode() {
        int result = this.actualTypeArguments != null ? Arrays.hashCode(this.actualTypeArguments) : 0;
        return (((result * 31) + (this.ownerType != null ? this.ownerType.hashCode() : 0)) * 31) + (this.rawType != null ? this.rawType.hashCode() : 0);
    }
}
