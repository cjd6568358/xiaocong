package com.ixiaocong.smarthome.phone.android.detail.fragment.scene;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SceneUserFragment$$Lambda$1 implements OnRefreshListener {
    private final SceneUserFragment arg$1;

    private SceneUserFragment$$Lambda$1(SceneUserFragment sceneUserFragment) {
        this.arg$1 = sceneUserFragment;
    }

    public static OnRefreshListener lambdaFactory$(SceneUserFragment sceneUserFragment) {
        return new SceneUserFragment$$Lambda$1(sceneUserFragment);
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    @LambdaForm.Hidden
    public void onRefresh(RefreshLayout refreshLayout) {
        this.arg$1.lambda$addListener$0(refreshLayout);
    }
}
