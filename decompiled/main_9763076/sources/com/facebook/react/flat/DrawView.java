package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class DrawView extends AbstractDrawCommand {
    public static final DrawView[] EMPTY_ARRAY = new DrawView[0];
    private final RectF TMP_RECT = new RectF();
    private float mClipRadius;
    private Path mPath;
    final int reactTag;

    public DrawView(int reactTag) {
        this.reactTag = reactTag;
    }

    @Override // com.facebook.react.flat.AbstractDrawCommand, com.facebook.react.flat.DrawCommand
    public void draw(FlatViewGroup parent, Canvas canvas) {
        onPreDraw(parent, canvas);
        if (this.mNeedsClipping || this.mClipRadius > 0.5f) {
            canvas.save(2);
            applyClipping(canvas);
            parent.drawNextChild(canvas);
            canvas.restore();
            return;
        }
        parent.drawNextChild(canvas);
    }

    @Override // com.facebook.react.flat.AbstractDrawCommand
    protected void applyClipping(Canvas canvas) {
        if (this.mClipRadius > 0.5f) {
            canvas.clipPath(this.mPath);
        } else {
            super.applyClipping(canvas);
        }
    }

    @Override // com.facebook.react.flat.AbstractDrawCommand
    protected void onDraw(Canvas canvas) {
    }

    @Override // com.facebook.react.flat.AbstractDrawCommand
    protected void onDebugDraw(FlatViewGroup parent, Canvas canvas) {
        parent.debugDrawNextChild(canvas);
    }
}
