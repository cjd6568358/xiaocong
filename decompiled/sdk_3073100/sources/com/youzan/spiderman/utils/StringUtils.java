package com.youzan.spiderman.utils;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class StringUtils {
    public static boolean isNotEmpty(CharSequence... args) {
        if (args != null) {
            for (CharSequence text : args) {
                if (isEmpty(text)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static boolean equals(CharSequence text1, CharSequence text2) {
        if (text1 == null || text2 == null) {
            return text1 == null && text2 == null;
        }
        return text1.toString().equals(text2.toString());
    }

    public static boolean isStartWith(String str, String[] prefixes) {
        if (str == null || prefixes == null) {
            return false;
        }
        for (String p : prefixes) {
            if (str.startsWith(p)) {
                return true;
            }
        }
        return false;
    }

    public static String join(List<String> list) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list) {
            if (str != null) {
                stringBuilder.append(',').append(str);
            }
        }
        return stringBuilder.substring(1);
    }
}
