package com.bumptech.glide.request;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface Request {
    void begin();

    void clear();

    boolean isCancelled();

    boolean isComplete();

    boolean isResourceSet();

    boolean isRunning();

    void pause();

    void recycle();
}
