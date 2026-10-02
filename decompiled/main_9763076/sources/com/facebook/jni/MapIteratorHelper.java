package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class MapIteratorHelper {

    @DoNotStrip
    private final Iterator<Map.Entry> mIterator;

    @DoNotStrip
    private Object mKey;

    @DoNotStrip
    private Object mValue;

    @DoNotStrip
    public MapIteratorHelper(Map map) {
        this.mIterator = map.entrySet().iterator();
    }

    @DoNotStrip
    boolean hasNext() {
        if (this.mIterator.hasNext()) {
            Map.Entry entry = this.mIterator.next();
            this.mKey = entry.getKey();
            this.mValue = entry.getValue();
            return true;
        }
        this.mKey = null;
        this.mValue = null;
        return false;
    }
}
