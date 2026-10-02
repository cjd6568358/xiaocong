package com.alibaba.fastjson.serializer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface PropertyPreFilter extends SerializeFilter {
    boolean apply(JSONSerializer jSONSerializer, Object obj, String str);
}
