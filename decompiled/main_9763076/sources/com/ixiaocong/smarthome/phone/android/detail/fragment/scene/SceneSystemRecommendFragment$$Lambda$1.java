package com.ixiaocong.smarthome.phone.android.detail.fragment.scene;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SceneSystemRecommendFragment$$Lambda$1 implements OnRefreshListener {
    private final SceneSystemRecommendFragment arg$1;

    private SceneSystemRecommendFragment$$Lambda$1(SceneSystemRecommendFragment sceneSystemRecommendFragment) {
        this.arg$1 = sceneSystemRecommendFragment;
    }

    public static OnRefreshListener lambdaFactory$(SceneSystemRecommendFragment sceneSystemRecommendFragment) {
        return new SceneSystemRecommendFragment$$Lambda$1(sceneSystemRecommendFragment);
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    @LambdaForm.Hidden
    public void onRefresh(RefreshLayout refreshLayout) {
        this.arg$1.lambda$addListener$0(refreshLayout);
    }
}
