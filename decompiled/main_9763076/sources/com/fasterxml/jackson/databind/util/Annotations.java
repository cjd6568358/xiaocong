package com.fasterxml.jackson.databind.util;

import java.lang.annotation.Annotation;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Annotations {
    <A extends Annotation> A get(Class<A> cls);

    int size();
}
