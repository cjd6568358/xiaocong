package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonMoreAlarmLogActiity$$Lambda$1 implements EditDialogCallback {
    private final AoniCommonMoreAlarmLogActiity arg$1;

    private AoniCommonMoreAlarmLogActiity$$Lambda$1(AoniCommonMoreAlarmLogActiity aoniCommonMoreAlarmLogActiity) {
        this.arg$1 = aoniCommonMoreAlarmLogActiity;
    }

    public static EditDialogCallback lambdaFactory$(AoniCommonMoreAlarmLogActiity aoniCommonMoreAlarmLogActiity) {
        return new AoniCommonMoreAlarmLogActiity$$Lambda$1(aoniCommonMoreAlarmLogActiity);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback
    @LambdaForm.Hidden
    public void editMsgCallback(String str) {
        this.arg$1.editMsgCallback(str);
    }
}
