package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import com.baidu.cloud.media.player.IMediaPlayer;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CameraCommonReplay$$Lambda$5 implements IMediaPlayer.OnCompletionListener {
    private final CameraCommonReplay arg$1;

    private CameraCommonReplay$$Lambda$5(CameraCommonReplay cameraCommonReplay) {
        this.arg$1 = cameraCommonReplay;
    }

    public static IMediaPlayer.OnCompletionListener lambdaFactory$(CameraCommonReplay cameraCommonReplay) {
        return new CameraCommonReplay$$Lambda$5(cameraCommonReplay);
    }

    @Override // com.baidu.cloud.media.player.IMediaPlayer.OnCompletionListener
    @LambdaForm.Hidden
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        this.arg$1.lambda$addListener$4(iMediaPlayer);
    }
}
