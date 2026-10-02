package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.TabHomeDeviceSelecetPopCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class TabHomeLongClickPop$$Lambda$3 implements View.OnClickListener {
    private final TabHomeLongClickPop arg$1;
    private final TabHomeDeviceSelecetPopCallback arg$2;

    private TabHomeLongClickPop$$Lambda$3(TabHomeLongClickPop tabHomeLongClickPop, TabHomeDeviceSelecetPopCallback tabHomeDeviceSelecetPopCallback) {
        this.arg$1 = tabHomeLongClickPop;
        this.arg$2 = tabHomeDeviceSelecetPopCallback;
    }

    public static View.OnClickListener lambdaFactory$(TabHomeLongClickPop tabHomeLongClickPop, TabHomeDeviceSelecetPopCallback tabHomeDeviceSelecetPopCallback) {
        return new TabHomeLongClickPop$$Lambda$3(tabHomeLongClickPop, tabHomeDeviceSelecetPopCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showSelectPop$2(this.arg$2, view);
    }
}
