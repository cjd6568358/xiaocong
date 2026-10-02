package com.ixiaocong.smarthome.phone.android.common.utils;

import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class StringUtils {
    private static final Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    private static final Pattern URL = Pattern.compile("^(https|http)://.*?$(net|com|.com.cn|org|me|)");
    private static final ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() { // from class: com.ixiaocong.smarthome.phone.android.common.utils.StringUtils.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static final ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() { // from class: com.ixiaocong.smarthome.phone.android.common.utils.StringUtils.2
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static boolean isEmpty(String input) {
        if (input == null || Constants.MAIN_VERSION_TAG.equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[一-龥]");
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static int doublePointLength(String value) {
        if (TextUtils.isEmpty(value) || !value.contains(".")) {
            return 0;
        }
        return (value.length() - value.indexOf(".")) - 1;
    }
}
