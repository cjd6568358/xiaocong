package com.hzy.tvmao.interf;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface IRequestResult<T> {
    void onFail(Integer num, String str);

    void onSuccess(String str, T t);
}
