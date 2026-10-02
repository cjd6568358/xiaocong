package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.TabHomeDeviceSelecetPopCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class TabHomeLongClickPop$$Lambda$2 implements View.OnClickListener {
    private final TabHomeLongClickPop arg$1;
    private final Context arg$2;
    private final TabHomeDeviceSelecetPopCallback arg$3;

    private TabHomeLongClickPop$$Lambda$2(TabHomeLongClickPop tabHomeLongClickPop, Context context, TabHomeDeviceSelecetPopCallback tabHomeDeviceSelecetPopCallback) {
        this.arg$1 = tabHomeLongClickPop;
        this.arg$2 = context;
        this.arg$3 = tabHomeDeviceSelecetPopCallback;
    }

    public static View.OnClickListener lambdaFactory$(TabHomeLongClickPop tabHomeLongClickPop, Context context, TabHomeDeviceSelecetPopCallback tabHomeDeviceSelecetPopCallback) {
        return new TabHomeLongClickPop$$Lambda$2(tabHomeLongClickPop, context, tabHomeDeviceSelecetPopCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showSelectPop$1(this.arg$2, this.arg$3, view);
    }
}
