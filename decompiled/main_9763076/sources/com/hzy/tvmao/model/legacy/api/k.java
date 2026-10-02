package com.hzy.tvmao.model.legacy.api;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: StringUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class k {
    public static String a(String str) {
        return str.replace("<p>", "&nbsp;&nbsp;&nbsp;&nbsp;").replace("</p>", "\n").replaceAll("<br[ /]*>", "\n");
    }

    public static String b(String str) {
        return str.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("&amp;", "&").replace("&nbsp;", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).replace("&mdash;", "—").replace("&ldquo;", "“").replace("&rdquo;", "”").replace("&#151;", "—");
    }

    public static String a(CharSequence charSequence) {
        return b(c(a(charSequence.toString())).replaceAll("\n{2,}", "\n"));
    }

    public static String c(String str) {
        return str.replaceAll("</?[^>]+/?>", Constants.MAIN_VERSION_TAG);
    }
}
