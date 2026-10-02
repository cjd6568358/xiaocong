package com.youzan.androidsdk.basic.web.plugin;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.Keep;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
@Keep
public interface ChooseFileListener {
    void onChooseFile(Intent intent, int i) throws ActivityNotFoundException;
}
