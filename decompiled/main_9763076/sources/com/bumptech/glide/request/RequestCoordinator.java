package com.bumptech.glide.request;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface RequestCoordinator {
    boolean canNotifyStatusChanged(Request request);

    boolean canSetImage(Request request);

    boolean isAnyResourceSet();

    void onRequestSuccess(Request request);
}
