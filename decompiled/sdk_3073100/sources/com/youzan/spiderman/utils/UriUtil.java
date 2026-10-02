package com.youzan.spiderman.utils;

import android.net.Uri;
import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class UriUtil {
    public static String getUriExtend(Uri uri) {
        int start;
        if (uri != null) {
            String lastSegment = uri.getLastPathSegment();
            if (!TextUtils.isEmpty(lastSegment) && (start = lastSegment.lastIndexOf(".")) >= 0) {
                return lastSegment.substring(start + 1);
            }
        }
        return "";
    }

    public static boolean isScript(String extend) {
        for (String scriptExtends : Stone.SCRIPT_EXTEND_LIST) {
            if (StringUtils.equals(extend, scriptExtends)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isImg(String extend) {
        for (String imgExtends : Stone.IMG_EXTEND_LIST) {
            if (StringUtils.equals(extend, imgExtends)) {
                return true;
            }
        }
        return false;
    }

    public static String buildMimeType(String extend) {
        if (StringUtils.equals(extend, "css")) {
            return "text/css";
        }
        if (StringUtils.equals(extend, "js")) {
            return "application/x-javascript";
        }
        if (StringUtils.equals(extend, "ico")) {
            return "image/x-icon";
        }
        return String.format("image/%s", extend);
    }
}
