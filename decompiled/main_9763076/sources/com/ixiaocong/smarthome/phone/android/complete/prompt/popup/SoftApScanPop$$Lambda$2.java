package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SoftApScanPop$$Lambda$2 implements View.OnClickListener {
    private final SoftApScanPop arg$1;
    private final String arg$2;
    private final CommonTypeCallback arg$3;

    private SoftApScanPop$$Lambda$2(SoftApScanPop softApScanPop, String str, CommonTypeCallback commonTypeCallback) {
        this.arg$1 = softApScanPop;
        this.arg$2 = str;
        this.arg$3 = commonTypeCallback;
    }

    public static View.OnClickListener lambdaFactory$(SoftApScanPop softApScanPop, String str, CommonTypeCallback commonTypeCallback) {
        return new SoftApScanPop$$Lambda$2(softApScanPop, str, commonTypeCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showSoftApScanPopPopup$1(this.arg$2, this.arg$3, view);
    }
}
