package com.ixiaocong.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StringUtils {
    private static final Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private static final Pattern URL = Pattern.compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[一-龥]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static long getRandomInt(int digits) {
        return (long) (((Math.random() * 9.0d) + 1.0d) * Math.pow(10.0d, digits - 1));
    }
}
