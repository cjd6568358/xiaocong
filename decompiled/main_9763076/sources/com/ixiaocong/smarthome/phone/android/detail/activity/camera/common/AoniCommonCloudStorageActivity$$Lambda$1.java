package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonCloudStorageActivity$$Lambda$1 implements View.OnClickListener {
    private final AoniCommonCloudStorageActivity arg$1;

    private AoniCommonCloudStorageActivity$$Lambda$1(AoniCommonCloudStorageActivity aoniCommonCloudStorageActivity) {
        this.arg$1 = aoniCommonCloudStorageActivity;
    }

    public static View.OnClickListener lambdaFactory$(AoniCommonCloudStorageActivity aoniCommonCloudStorageActivity) {
        return new AoniCommonCloudStorageActivity$$Lambda$1(aoniCommonCloudStorageActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
