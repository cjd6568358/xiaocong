package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.views.art.ARTVirtualNode;
import com.facebook.yoga.YogaUnit;
import com.facebook.yoga.YogaValue;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class FlatARTSurfaceViewShadowNode extends FlatShadowNode implements TextureView.SurfaceTextureListener {
    private boolean mPaddingChanged = false;
    private Surface mSurface;

    FlatARTSurfaceViewShadowNode() {
        forceMountToView();
        forceMountChildrenToView();
    }

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
            Log.e("React", e.getClass().getSimpleName() + " in Surface.unlockCanvasAndPost");
        }
    }

    private void markChildrenUpdatesSeen(ReactShadowNode shadowNode) {
        for (int i = 0; i < shadowNode.getChildCount(); i++) {
            ReactShadowNode child = shadowNode.getChildAt(i);
            child.markUpdateSeen();
            markChildrenUpdatesSeen(child);
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setPadding(int spacingType, float padding) {
        YogaValue current = getStylePadding(spacingType);
        if (current.unit != YogaUnit.PIXEL || current.value != padding) {
            super.setPadding(spacingType, padding);
            this.mPaddingChanged = true;
            markUpdated();
        }
    }

    @Override // com.facebook.react.uimanager.ReactShadowNode
    public void setPaddingPercent(int spacingType, float percent) {
        YogaValue current = getStylePadding(spacingType);
        if (current.unit != YogaUnit.PERCENT || current.value != percent) {
            super.setPadding(spacingType, percent);
            this.mPaddingChanged = true;
            markUpdated();
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
