package com.youzan.spiderman.c;

import android.net.Uri;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/* JADX INFO: compiled from: ApiHelper.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    public static String a(String baseUrl, Map<String, String> params) {
        Uri originUri = Uri.parse(baseUrl);
        Uri.Builder builder = originUri.buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build().toString();
    }

    public static RequestBody a(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }
}
