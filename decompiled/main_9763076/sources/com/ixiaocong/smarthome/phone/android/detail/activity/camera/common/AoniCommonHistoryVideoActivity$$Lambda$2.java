package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonHistoryVideoActivity$$Lambda$2 implements EditDialogCallback {
    private final AoniCommonHistoryVideoActivity arg$1;

    private AoniCommonHistoryVideoActivity$$Lambda$2(AoniCommonHistoryVideoActivity aoniCommonHistoryVideoActivity) {
        this.arg$1 = aoniCommonHistoryVideoActivity;
    }

    public static EditDialogCallback lambdaFactory$(AoniCommonHistoryVideoActivity aoniCommonHistoryVideoActivity) {
        return new AoniCommonHistoryVideoActivity$$Lambda$2(aoniCommonHistoryVideoActivity);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback
    @LambdaForm.Hidden
    public void editMsgCallback(String str) {
        this.arg$1.editMsgCallback(str);
    }
}
