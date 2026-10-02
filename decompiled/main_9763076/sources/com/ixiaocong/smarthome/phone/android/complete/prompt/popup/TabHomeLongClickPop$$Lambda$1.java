package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.TabHomeDeviceSelecetPopCallback;
import com.xiaocong.smarthome.httplib.model.DeviceListModel;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class TabHomeLongClickPop$$Lambda$1 implements View.OnClickListener {
    private final TabHomeLongClickPop arg$1;
    private final TabHomeDeviceSelecetPopCallback arg$2;
    private final DeviceListModel arg$3;

    private TabHomeLongClickPop$$Lambda$1(TabHomeLongClickPop tabHomeLongClickPop, TabHomeDeviceSelecetPopCallback tabHomeDeviceSelecetPopCallback, DeviceListModel deviceListModel) {
        this.arg$1 = tabHomeLongClickPop;
        this.arg$2 = tabHomeDeviceSelecetPopCallback;
        this.arg$3 = deviceListModel;
    }

    public static View.OnClickListener lambdaFactory$(TabHomeLongClickPop tabHomeLongClickPop, TabHomeDeviceSelecetPopCallback tabHomeDeviceSelecetPopCallback, DeviceListModel deviceListModel) {
        return new TabHomeLongClickPop$$Lambda$1(tabHomeLongClickPop, tabHomeDeviceSelecetPopCallback, deviceListModel);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showSelectPop$0(this.arg$2, this.arg$3, view);
    }
}
