package com.alibaba.fastjson.parser.deserializer;

import java.lang.reflect.Type;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ExtraTypeProvider extends ParseProcess {
    Type getExtraType(Object obj, String str);
}
