package com.youzan.androidsdk.loader.http.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface HttpAuth {
    public static final int Auth_NONE = 0;
    public static final int Auth_TOKEN = 2;
    public static final int Auth_TOKEN_URL = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AuthType {
    }
}
