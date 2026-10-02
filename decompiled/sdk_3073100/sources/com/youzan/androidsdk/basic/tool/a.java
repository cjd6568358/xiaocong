package com.youzan.androidsdk.basic.tool;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebStorage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: HtmlStorage.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class a {

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private static final String f24 = "KDTSESSIONID";

    /* JADX INFO: renamed from: ʼ, reason: contains not printable characters */
    private static final String f25 = "yz_app_sdk_version";

    /* JADX INFO: renamed from: ʽ, reason: contains not printable characters */
    private static final String f26 = "nobody_sign";

    /* JADX INFO: renamed from: ʾ, reason: contains not printable characters */
    private static final String f27 = "Sat, 31 Dec 2016 23:59:59 GMT";

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final String f28 = "koudaitong.com";

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private static final String f29 = "youzan.com";

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private static final String f30 = "youzan_user_id";

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private static final String f31 = "hide_app_topbar";

    /* JADX INFO: renamed from: ͺ, reason: contains not printable characters */
    private static final String f32 = "Set-Cookie";

    /* JADX INFO: renamed from: ι, reason: contains not printable characters */
    private static final String f33 = ";";

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private static final String f34 = "alipay_installed";

    /* JADX INFO: compiled from: HtmlStorage.java */
    public static class b {
        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static void m17(Context context, String key, String value) {
            if ((TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) && !"null".equalsIgnoreCase(key)) {
                return;
            }
            C0016a.m11(context, (List<com.youzan.androidsdk.basic.tool.b>) C0016a.m13(key, value));
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static void m18(Context context, boolean hide) {
            C0016a.m11(context, (List<com.youzan.androidsdk.basic.tool.b>) C0016a.m13(a.f31, hide ? "1" : "0"));
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static void m16(Context context, String value) {
            C0016a.m11(context, (List<com.youzan.androidsdk.basic.tool.b>) C0016a.m13(a.f24, value));
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static void m15(Context context) {
            C0016a.m11(context, (List<com.youzan.androidsdk.basic.tool.b>) C0016a.m13(a.f34, "1"));
        }

        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        public static void m19(Context context, String value) {
            C0016a.m11(context, (List<com.youzan.androidsdk.basic.tool.b>) C0016a.m13(a.f30, value));
        }

        /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
        public static void m20(Context context, String version) {
            C0016a.m11(context, (List<com.youzan.androidsdk.basic.tool.b>) C0016a.m13(a.f25, version));
        }
    }

    /* JADX INFO: renamed from: com.youzan.androidsdk.basic.tool.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: HtmlStorage.java */
    public static class C0016a {
        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static void m11(Context context, List<com.youzan.androidsdk.basic.tool.b> cookies) {
            if (context != null && cookies != null && cookies.size() > 0) {
                try {
                    CookieSyncManager.createInstance(context);
                    CookieManager manager = CookieManager.getInstance();
                    manager.setAcceptCookie(true);
                    for (com.youzan.androidsdk.basic.tool.b item : cookies) {
                        String httpsHost = "https://." + item.m23();
                        manager.setCookie(httpsHost, item.toString());
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static void m12(Context context, com.youzan.androidsdk.basic.tool.b... cookies) {
            m11(context, (List<com.youzan.androidsdk.basic.tool.b>) Arrays.asList(cookies));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        public static List<com.youzan.androidsdk.basic.tool.b> m13(String key, String value) {
            List<com.youzan.androidsdk.basic.tool.b> cookies = new ArrayList<>(2);
            cookies.add(new com.youzan.androidsdk.basic.tool.b.a().m39(a.f28).m36(key).m38(value).m40());
            cookies.add(new com.youzan.androidsdk.basic.tool.b.a().m39(a.f29).m36(key).m38(value).m40());
            return cookies;
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        static void m10(Context context, String host) {
            try {
                CookieSyncManager.createInstance(context);
                CookieManager manager = CookieManager.getInstance();
                String httpsHost = "https://." + host;
                Set<String> field = m7(manager.getCookie(httpsHost));
                if (field != null) {
                    for (String item : field) {
                        manager.setCookie(httpsHost, item + "=; Expires=" + a.f27);
                    }
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    CookieManager.getInstance().flush();
                } else {
                    CookieSyncManager.getInstance().sync();
                }
            } catch (Throwable th) {
            }
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        private static Set<String> m7(String cookieString) {
            Set<String> field = null;
            if (!TextUtils.isEmpty(cookieString)) {
                String[] cookies = cookieString.split(a.f33);
                field = new HashSet<>(cookies.length);
                for (String item : cookies) {
                    if (item.contains("=")) {
                        field.add(item.split("=", 2)[0].trim());
                    }
                }
            }
            return field;
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static void m9(Context context) {
            m14(context);
            m8();
        }

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static void m8() {
            try {
                WebStorage.getInstance().deleteAllData();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        public static void m14(Context context) {
            m10(context, a.f28);
            m10(context, a.f29);
        }
    }
}
