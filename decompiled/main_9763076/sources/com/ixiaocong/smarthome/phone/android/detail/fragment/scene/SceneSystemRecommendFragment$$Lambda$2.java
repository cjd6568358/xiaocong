package com.ixiaocong.smarthome.phone.android.detail.fragment.scene;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SceneSystemRecommendFragment$$Lambda$2 implements View.OnClickListener {
    private final SceneSystemRecommendFragment arg$1;

    private SceneSystemRecommendFragment$$Lambda$2(SceneSystemRecommendFragment sceneSystemRecommendFragment) {
        this.arg$1 = sceneSystemRecommendFragment;
    }

    public static View.OnClickListener lambdaFactory$(SceneSystemRecommendFragment sceneSystemRecommendFragment) {
        return new SceneSystemRecommendFragment$$Lambda$2(sceneSystemRecommendFragment);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$1(view);
    }
}
