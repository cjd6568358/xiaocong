package com.youzan.androidsdk.tool;

import android.net.Uri;
import android.text.TextUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: UrlParse.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class a {
    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static String m84(String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.clearQuery();
            return builder.build().toString();
        }
        return url;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static Map<String, String> m86(Map<String, String> original, Map<String, String> extra) {
        return m87(original, extra, true);
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static Map<String, String> m87(Map<String, String> original, Map<String, String> extra, boolean cover) {
        if (original == null) {
            original = new LinkedHashMap<>();
        }
        if (cover && extra != null && extra.size() > 0) {
            for (Map.Entry<String, String> entry : extra.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    if (TextUtils.isEmpty(value)) {
                        value = "";
                    }
                    original.put(key, value);
                }
            }
        }
        return original;
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    public static Map<String, String> m88(String url) {
        Uri uri;
        Set<String> keys;
        Map<String, String> params = new LinkedHashMap<>();
        if (!TextUtils.isEmpty(url)) {
            String[] parts = url.split("\\?");
            if (parts.length > 1 && (keys = (uri = Uri.parse(url)).getQueryParameterNames()) != null && keys.size() > 0) {
                for (String key : keys) {
                    String value = uri.getQueryParameter(key);
                    if (!TextUtils.isEmpty(value)) {
                        params.put(key, value);
                    }
                }
            }
        }
        if (params.isEmpty()) {
            return null;
        }
        return params;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static String m85(String url, Map<String, String> param) {
        if (param == null || param.size() == 0) {
            return url;
        }
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        Uri originUri = Uri.parse(url);
        Uri.Builder builder = originUri.buildUpon();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        String uri = builder.build().toString();
        return uri;
    }
}
