package com.ixiaocong.smarthome.phone.android.detail.fragment.home.weather;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class TabHomeWeatherView$$Lambda$1 implements View.OnClickListener {
    private final TabHomeWeatherView arg$1;

    private TabHomeWeatherView$$Lambda$1(TabHomeWeatherView tabHomeWeatherView) {
        this.arg$1 = tabHomeWeatherView;
    }

    public static View.OnClickListener lambdaFactory$(TabHomeWeatherView tabHomeWeatherView) {
        return new TabHomeWeatherView$$Lambda$1(tabHomeWeatherView);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
