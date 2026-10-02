package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.FieldInfo;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class BeanContext {
    private final Class<?> beanClass;
    private final FieldInfo fieldInfo;
    private final String format;

    public BeanContext(Class<?> beanClass, FieldInfo fieldInfo) {
        this.beanClass = beanClass;
        this.fieldInfo = fieldInfo;
        this.format = fieldInfo.getFormat();
    }

    public int getFeatures() {
        return this.fieldInfo.serialzeFeatures;
    }

    public boolean isJsonDirect() {
        return this.fieldInfo.jsonDirect;
    }

    public String getFormat() {
        return this.format;
    }
}
