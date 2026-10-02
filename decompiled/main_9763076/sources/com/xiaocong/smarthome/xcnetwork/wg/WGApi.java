package com.xiaocong.smarthome.xcnetwork.wg;

import java.util.Map;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface WGApi {
    @FormUrlEncoded
    @POST
    Observable<String> post2WGAsync(@Url String str, @QueryMap Map<String, String> map, @FieldMap Map<String, Object> map2, @HeaderMap Map<String, Object> map3);
}
