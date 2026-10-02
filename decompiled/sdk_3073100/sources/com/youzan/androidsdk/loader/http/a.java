package com.youzan.androidsdk.loader.http;

import android.text.TextUtils;

/* JADX INFO: compiled from: Auth.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class a {
    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    public static String m68(int authType, String method) {
        switch (authType) {
            case 2:
                return C0018a.m69(method);
            default:
                return method;
        }
    }

    /* JADX INFO: renamed from: com.youzan.androidsdk.loader.http.a$a, reason: collision with other inner class name */
    /* JADX INFO: compiled from: Auth.java */
    static class C0018a {
        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        public static String m69(String method) {
            return m70(method);
        }

        /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
        private static String m70(String path) {
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(path)) {
                builder.append("https://open.youzan.com/api/oauthentry");
                builder.append('/');
                builder.append(path);
            }
            return builder.toString();
        }
    }
}
