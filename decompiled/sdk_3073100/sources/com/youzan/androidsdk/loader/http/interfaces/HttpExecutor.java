package com.youzan.androidsdk.loader.http.interfaces;

import android.support.annotation.Keep;
import com.youzan.androidsdk.loader.http.Query;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@Keep
public interface HttpExecutor {
    HttpExecutor intercept(HttpInterceptor httpInterceptor) throws NullPointerException;

    <MODEL> HttpCall with(Query<MODEL> query) throws NullPointerException;
}
