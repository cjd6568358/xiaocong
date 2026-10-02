package com.hzy.tvmao.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/* JADX INFO: compiled from: TimeUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class f extends ThreadLocal<SimpleDateFormat> {
    f() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // java.lang.ThreadLocal
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public SimpleDateFormat initialValue() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    }
}
