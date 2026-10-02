package com.facebook.imagepipeline.producers;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Consumer<T> {
    void onCancellation();

    void onFailure(Throwable th);

    void onNewResult(T t, boolean z);

    void onProgressUpdate(float f);
}
