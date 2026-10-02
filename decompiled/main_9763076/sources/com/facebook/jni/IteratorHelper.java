package com.facebook.jni;

import com.facebook.proguard.annotations.DoNotStrip;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class IteratorHelper {

    @DoNotStrip
    private Object mElement;
    private final Iterator mIterator;

    @DoNotStrip
    public IteratorHelper(Iterator iterator) {
        this.mIterator = iterator;
    }

    @DoNotStrip
    public IteratorHelper(Iterable iterable) {
        this.mIterator = iterable.iterator();
    }

    @DoNotStrip
    boolean hasNext() {
        if (this.mIterator.hasNext()) {
            this.mElement = this.mIterator.next();
            return true;
        }
        this.mElement = null;
        return false;
    }
}
