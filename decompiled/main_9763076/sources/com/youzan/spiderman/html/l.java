package com.youzan.spiderman.html;

import com.google.gson.annotations.SerializedName;
import com.youzan.spiderman.utils.JsonUtil;
import com.youzan.spiderman.utils.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: HtmlHeader.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class l {

    @SerializedName("header_map")
    private Map<String, List<String>> a = new HashMap();

    public static l a(Map<String, String> map) {
        l htmlHeader = new l();
        Map<String, List<String>> headerMap = new HashMap<>();
        htmlHeader.a = headerMap;
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                List<String> listValue = new ArrayList<>();
                listValue.add(entry.getValue());
                headerMap.put(entry.getKey(), listValue);
            }
        }
        return htmlHeader;
    }

    public static l b(Map<String, List<String>> map) {
        l htmlHeader = new l();
        if (map != null) {
            htmlHeader.a = map;
        } else {
            htmlHeader.a = new HashMap();
        }
        return htmlHeader;
    }

    public static l a(String header) {
        if (header != null) {
            return (l) JsonUtil.fromJson(header, l.class);
        }
        return null;
    }

    public static String a(l htmlHeader) {
        if (htmlHeader != null) {
            return JsonUtil.toJson(htmlHeader);
        }
        return null;
    }

    public Map<String, List<String>> a() {
        return this.a;
    }

    public Map<String, String> b() {
        return c(this.a);
    }

    public static Map<String, String> c(Map<String, List<String>> headerMapList) {
        Map<String, String> headers = new HashMap<>();
        if (headerMapList != null) {
            for (Map.Entry<String, List<String>> entry : headerMapList.entrySet()) {
                List<String> headerValue = entry.getValue();
                if (headerValue != null) {
                    int size = headerValue.size();
                    if (size == 1) {
                        headers.put(entry.getKey(), headerValue.get(0));
                    } else {
                        headers.put(entry.getKey(), StringUtils.join(headerValue));
                    }
                }
            }
        }
        return headers;
    }
}
