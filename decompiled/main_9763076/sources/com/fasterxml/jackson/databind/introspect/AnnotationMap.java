package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.annotation.Annotation;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AnnotationMap implements Annotations {
    protected HashMap<Class<? extends Annotation>, Annotation> _annotations;

    public AnnotationMap() {
    }

    private AnnotationMap(HashMap<Class<? extends Annotation>, Annotation> a) {
        this._annotations = a;
    }

    @Override // com.fasterxml.jackson.databind.util.Annotations
    public <A extends Annotation> A get(Class<A> cls) {
        if (this._annotations == null) {
            return null;
        }
        return (A) this._annotations.get(cls);
    }

    public static AnnotationMap merge(AnnotationMap primary, AnnotationMap secondary) {
        if (primary == null || primary._annotations == null || primary._annotations.isEmpty()) {
            return secondary;
        }
        if (secondary != null && secondary._annotations != null && !secondary._annotations.isEmpty()) {
            HashMap<Class<? extends Annotation>, Annotation> annotations = new HashMap<>();
            for (Annotation ann : secondary._annotations.values()) {
                annotations.put(ann.annotationType(), ann);
            }
            for (Annotation ann2 : primary._annotations.values()) {
                annotations.put(ann2.annotationType(), ann2);
            }
            return new AnnotationMap(annotations);
        }
        return primary;
    }

    @Override // com.fasterxml.jackson.databind.util.Annotations
    public int size() {
        if (this._annotations == null) {
            return 0;
        }
        return this._annotations.size();
    }

    public void addIfNotPresent(Annotation ann) {
        if (this._annotations == null || !this._annotations.containsKey(ann.annotationType())) {
            _add(ann);
        }
    }

    public void add(Annotation ann) {
        _add(ann);
    }

    public String toString() {
        return this._annotations == null ? "[null]" : this._annotations.toString();
    }

    protected final void _add(Annotation ann) {
        if (this._annotations == null) {
            this._annotations = new HashMap<>();
        }
        this._annotations.put(ann.annotationType(), ann);
    }
}
