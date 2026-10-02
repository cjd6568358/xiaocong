package com.facebook.react.bridge;

import android.app.Activity;
import android.content.Intent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface ActivityEventListener {
    void onActivityResult(Activity activity, int i, int i2, Intent intent);

    void onNewIntent(Intent intent);
}
