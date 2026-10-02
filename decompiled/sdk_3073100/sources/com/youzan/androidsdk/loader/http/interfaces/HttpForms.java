package com.youzan.androidsdk.loader.http.interfaces;

import android.support.annotation.Keep;
import java.io.File;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@Keep
public interface HttpForms extends HttpExecutor {
    HttpForms put(String str, double d);

    HttpForms put(String str, double d, boolean z);

    HttpForms put(String str, float f);

    HttpForms put(String str, float f, boolean z);

    HttpForms put(String str, int i);

    HttpForms put(String str, int i, boolean z);

    HttpForms put(String str, long j);

    HttpForms put(String str, long j, boolean z);

    HttpForms put(String str, File file);

    HttpForms put(String str, File file, boolean z);

    HttpForms put(String str, String str2);

    HttpForms put(String str, String str2, boolean z);

    HttpForms put(String str, short s);

    HttpForms put(String str, short s, boolean z);

    HttpForms put(String str, boolean z);

    HttpForms put(String str, boolean z, boolean z2);

    HttpForms puts(Map<String, String> map);
}
