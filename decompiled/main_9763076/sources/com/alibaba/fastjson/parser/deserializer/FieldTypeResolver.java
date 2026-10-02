package com.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface FieldTypeResolver extends ParseProcess {
    Type resolve(Object obj, String str);
}
