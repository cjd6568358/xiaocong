package com.facebook.imagepipeline.core;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ExecutorSupplier {
    Executor forBackgroundTasks();

    Executor forDecode();

    Executor forLightweightBackgroundTasks();

    Executor forLocalStorageRead();

    Executor forLocalStorageWrite();
}
