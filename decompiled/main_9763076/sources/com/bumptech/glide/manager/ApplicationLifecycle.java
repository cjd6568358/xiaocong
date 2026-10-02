package com.bumptech.glide.manager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ApplicationLifecycle implements Lifecycle {
    ApplicationLifecycle() {
    }

    @Override // com.bumptech.glide.manager.Lifecycle
    public void addListener(LifecycleListener listener) {
        listener.onStart();
    }
}
