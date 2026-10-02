package com.facebook.react.bridge;

import android.support.v4.util.Pools;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DynamicFromMap implements Dynamic {
    private static final Pools.SimplePool<DynamicFromMap> sPool = new Pools.SimplePool<>(10);
    private ReadableMap mMap;
    private String mName;

    private DynamicFromMap() {
    }

    public static DynamicFromMap create(ReadableMap map, String name) {
        DynamicFromMap dynamic = sPool.acquire();
        if (dynamic == null) {
            dynamic = new DynamicFromMap();
        }
        dynamic.mMap = map;
        dynamic.mName = name;
        return dynamic;
    }

    @Override // com.facebook.react.bridge.Dynamic
    public void recycle() {
        this.mMap = null;
        this.mName = null;
        sPool.release(this);
    }

    @Override // com.facebook.react.bridge.Dynamic
    public boolean isNull() {
        if (this.mMap == null || this.mName == null) {
            throw new IllegalStateException("This dynamic value has been recycled");
        }
        return this.mMap.isNull(this.mName);
    }

    @Override // com.facebook.react.bridge.Dynamic
    public double asDouble() {
        if (this.mMap == null || this.mName == null) {
            throw new IllegalStateException("This dynamic value has been recycled");
        }
        return this.mMap.getDouble(this.mName);
    }

    @Override // com.facebook.react.bridge.Dynamic
    public String asString() {
        if (this.mMap == null || this.mName == null) {
            throw new IllegalStateException("This dynamic value has been recycled");
        }
        return this.mMap.getString(this.mName);
    }

    @Override // com.facebook.react.bridge.Dynamic
    public ReadableType getType() {
        if (this.mMap == null || this.mName == null) {
            throw new IllegalStateException("This dynamic value has been recycled");
        }
        return this.mMap.getType(this.mName);
    }
}
