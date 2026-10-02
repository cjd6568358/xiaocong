package com.youzan.androidsdk.basic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import com.youzan.androidsdk.event.AbsChooserEvent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class YouzanBrowser$4 extends AbsChooserEvent {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    final /* synthetic */ YouzanBrowser.OnChooseFile f16;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    final /* synthetic */ YouzanBrowser f17;

    YouzanBrowser$4(YouzanBrowser this$0, YouzanBrowser.OnChooseFile onChooseFile) {
        this.f17 = this$0;
        this.f16 = onChooseFile;
    }

    @Override // com.youzan.androidsdk.event.AbsChooserEvent
    public void call(Context context, Intent intent, int requestId) throws ActivityNotFoundException {
        if (this.f16 != null) {
            this.f16.onWebViewChooseFile(intent, requestId);
        }
    }
}
