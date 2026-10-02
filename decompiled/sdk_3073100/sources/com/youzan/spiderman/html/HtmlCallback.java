package com.youzan.spiderman.html;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface HtmlCallback {
    void onFailed();

    void onSuccess(String str, Map<String, List<String>> map, ByteArrayInputStream byteArrayInputStream, String str2);
}
