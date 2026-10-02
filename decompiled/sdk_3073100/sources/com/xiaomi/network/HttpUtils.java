package com.xiaomi.network;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import com.xiaomi.channel.commonutils.network.d;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class HttpUtils {

    public static class DefaultHttpGetProcessor extends HttpProcessor {
        public DefaultHttpGetProcessor() {
            super(1);
        }

        @Override // com.xiaomi.network.HttpProcessor
        public String b(Context context, String str, List<com.xiaomi.channel.commonutils.network.c> list) {
            if (list == null) {
                return d.a(context, new URL(str));
            }
            Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
            for (com.xiaomi.channel.commonutils.network.c cVar : list) {
                builderBuildUpon.appendQueryParameter(cVar.a(), cVar.b());
            }
            return d.a(context, new URL(builderBuildUpon.toString()));
        }
    }

    static int a(int i, int i2) {
        return (((i2 + 243) / 1448) * 132) + 1080 + i + i2;
    }

    static int a(int i, int i2, int i3) {
        return (((i2 + 200) / 1448) * 132) + 1011 + i2 + i + i3;
    }

    private static int a(HttpProcessor httpProcessor, String str, List<com.xiaomi.channel.commonutils.network.c> list, String str2) {
        if (httpProcessor.a() == 1) {
            return a(str.length(), a(str2));
        }
        if (httpProcessor.a() != 2) {
            return -1;
        }
        return a(str.length(), a(list), a(str2));
    }

    static int a(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET).length;
        } catch (UnsupportedEncodingException e) {
            return 0;
        }
    }

    static int a(List<com.xiaomi.channel.commonutils.network.c> list) {
        int length = 0;
        Iterator<com.xiaomi.channel.commonutils.network.c> it = list.iterator();
        while (true) {
            int length2 = length;
            if (!it.hasNext()) {
                return length2 * 2;
            }
            com.xiaomi.channel.commonutils.network.c next = it.next();
            if (!TextUtils.isEmpty(next.a())) {
                length2 += next.a().length();
            }
            length = !TextUtils.isEmpty(next.b()) ? next.b().length() + length2 : length2;
        }
    }

    public static String a(Context context, String str, List<com.xiaomi.channel.commonutils.network.c> list) {
        return a(context, str, list, new DefaultHttpGetProcessor(), true);
    }

    public static String a(Context context, String str, List<com.xiaomi.channel.commonutils.network.c> list, HttpProcessor httpProcessor, boolean z) {
        if (d.d(context)) {
            try {
                ArrayList<String> arrayList = new ArrayList<>();
                Fallback fallbacksByURL = null;
                if (z && (fallbacksByURL = HostManager.getInstance().getFallbacksByURL(str)) != null) {
                    arrayList = fallbacksByURL.a(str);
                }
                if (!arrayList.contains(str)) {
                    arrayList.add(str);
                }
                String strB = null;
                for (String str2 : arrayList) {
                    ArrayList arrayList2 = list != null ? new ArrayList(list) : null;
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    try {
                        if (!httpProcessor.a(context, str2, arrayList2)) {
                            return strB;
                        }
                        strB = httpProcessor.b(context, str2, arrayList2);
                        if (!TextUtils.isEmpty(strB)) {
                            if (fallbacksByURL == null) {
                                return strB;
                            }
                            fallbacksByURL.a(str2, System.currentTimeMillis() - jCurrentTimeMillis, a(httpProcessor, str2, arrayList2, strB));
                            return strB;
                        }
                        if (fallbacksByURL != null) {
                            fallbacksByURL.a(str2, System.currentTimeMillis() - jCurrentTimeMillis, a(httpProcessor, str2, arrayList2, strB), null);
                        }
                    } catch (IOException e) {
                        if (fallbacksByURL != null) {
                            fallbacksByURL.a(str2, System.currentTimeMillis() - jCurrentTimeMillis, a(httpProcessor, str2, arrayList2, strB), e);
                        }
                        e.printStackTrace();
                    }
                    strB = strB;
                }
                return strB;
            } catch (MalformedURLException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
