package com.youzan.spiderman.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RegexUtil {
    public static boolean isMatch(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }
}
