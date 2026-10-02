package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
interface EngineJobListener {
    void onEngineJobCancelled(EngineJob engineJob, Key key);

    void onEngineJobComplete(Key key, EngineResource<?> engineResource);
}
