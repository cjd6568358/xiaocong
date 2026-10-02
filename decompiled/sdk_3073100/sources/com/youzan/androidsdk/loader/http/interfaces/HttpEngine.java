package com.youzan.androidsdk.loader.http.interfaces;

import android.content.Context;
import com.youzan.androidsdk.YouzanException;
import com.youzan.androidsdk.loader.http.Query;
import java.io.File;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public interface HttpEngine {
    void cancel();

    <MODEL> void request(Context context, int i, String str, Map<String, String> map, Map<String, File> map2, Map<String, String> map3, Class<MODEL> cls, Query<MODEL> query, boolean z);

    <MODEL> void response(String str, Map<String, List<String>> map, YouzanException youzanException, Query<MODEL> query, Context context, Class<MODEL> cls);
}
