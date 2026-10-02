package com.xiaocong.smarthome.xcnetwork.wg;

import java.util.Map;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class WgReqAsyncService<T> extends BaseWgReqService<Observable<T>> {
    private Func1<String, T> resultFunc;

    public WgReqAsyncService(String url) {
        super(url);
    }

    public void setResultFunc(Func1<String, T> resultFunc) {
        this.resultFunc = resultFunc;
    }

    public Observable<T> wgReq(String path, Map<String, String> queryMap, Map<String, Object> bodyMap, Map<String, Object> headMap) {
        if (this.wgApi == null) {
            throw new IllegalStateException("未初始化WGApi");
        }
        return this.wgApi.post2WGAsync(path, queryMap, bodyMap, headMap).map(this.resultFunc).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
