package com.facebook.react.bridge;

import android.support.v4.util.Pools;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DynamicFromArray implements Dynamic {
    private static final Pools.SimplePool<DynamicFromArray> sPool = new Pools.SimplePool<>(10);
    private ReadableArray mArray;
    private int mIndex = -1;

    private DynamicFromArray() {
    }

    public static DynamicFromArray create(ReadableArray array, int index) {
        DynamicFromArray dynamic = sPool.acquire();
        if (dynamic == null) {
            dynamic = new DynamicFromArray();
        }
        dynamic.mArray = array;
        dynamic.mIndex = index;
        return dynamic;
    }

    @Override // com.facebook.react.bridge.Dynamic
    public void recycle() {
        this.mArray = null;
        this.mIndex = -1;
        sPool.release(this);
    }

    @Override // com.facebook.react.bridge.Dynamic
    public boolean isNull() {
        if (this.mArray == null) {
            throw new IllegalStateException("This dynamic value has been recycled");
        }
        return this.mArray.isNull(this.mIndex);
    }

    @Override // com.facebook.react.bridge.Dynamic
    public double asDouble() {
        if (this.mArray == null) {
            throw new IllegalStateException("This dynamic value has been recycled");
        }
        return this.mArray.getDouble(this.mIndex);
    }

    @Override // com.facebook.react.bridge.Dynamic
    public String asString() {
        if (this.mArray == null) {
            throw new IllegalStateException("This dynamic value has been recycled");
        }
        return this.mArray.getString(this.mIndex);
    }

    @Override // com.facebook.react.bridge.Dynamic
    public ReadableType getType() {
        if (this.mArray == null) {
            throw new IllegalStateException("This dynamic value has been recycled");
        }
        return this.mArray.getType(this.mIndex);
    }
}
