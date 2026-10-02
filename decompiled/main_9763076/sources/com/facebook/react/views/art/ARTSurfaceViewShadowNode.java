package com.facebook.react.views.art;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import com.facebook.common.logging.FLog;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ARTSurfaceViewShadowNode extends LayoutShadowNode implements TextureView.SurfaceTextureListener {
    private Surface mSurface;

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtual() {
        return false;
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public boolean isVirtualAnchor() {
        return true;
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void onCollectExtraUpdates(UIViewOperationQueue uiUpdater) {
        super.onCollectExtraUpdates(uiUpdater);
        drawOutput();
        uiUpdater.enqueueUpdateExtraData(getReactTag(), this);
    }

    private void drawOutput() {
        if (this.mSurface == null || !this.mSurface.isValid()) {
            markChildrenUpdatesSeen(this);
            return;
        }
        try {
            Canvas canvas = this.mSurface.lockCanvas(null);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            Paint paint = new Paint();
            for (int i = 0; i < getChildCount(); i++) {
                ARTVirtualNode child = (ARTVirtualNode) getChildAt(i);
                child.draw(canvas, paint, 1.0f);
                child.markUpdateSeen();
            }
            if (this.mSurface != null) {
                this.mSurface.unlockCanvasAndPost(canvas);
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            FLog.e("React", e.getClass().getSimpleName() + " in Surface.unlockCanvasAndPost");
        }
    }

    private void markChildrenUpdatesSeen(ReactShadowNode shadowNode) {
        for (int i = 0; i < shadowNode.getChildCount(); i++) {
            ReactShadowNode child = shadowNode.getChildAt(i);
            child.markUpdateSeen();
            markChildrenUpdatesSeen(child);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.mSurface = new Surface(surface);
        drawOutput();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        surface.release();
        this.mSurface = null;
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }
}
