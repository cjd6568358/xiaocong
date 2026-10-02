package com.baiducam.bdplayer.widget;

import android.graphics.Bitmap;
import android.view.View;
import com.baidu.cloud.media.player.IMediaPlayer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface IRenderView {

    public interface IRenderCallback {
        void onSurfaceChanged(ISurfaceHolder iSurfaceHolder, int i, int i2, int i3);

        void onSurfaceCreated(ISurfaceHolder iSurfaceHolder, int i, int i2);

        void onSurfaceDestroyed(ISurfaceHolder iSurfaceHolder);
    }

    public interface ISurfaceHolder {
        void bindToMediaPlayer(IMediaPlayer iMediaPlayer);

        IRenderView getRenderView();
    }

    void addRenderCallback(IRenderCallback iRenderCallback);

    Bitmap getBitmap();

    View getView();

    void release();

    void removeRenderCallback(IRenderCallback iRenderCallback);

    void setAspectRatio(int i);

    void setVideoRotation(int i);

    void setVideoSampleAspectRatio(int i, int i2);

    void setVideoSize(int i, int i2);

    boolean shouldWaitForResize();
}
