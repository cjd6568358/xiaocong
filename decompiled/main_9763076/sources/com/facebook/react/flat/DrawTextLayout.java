package com.facebook.react.flat;

import android.graphics.Canvas;
import android.text.Layout;
import com.facebook.fbui.textlayoutbuilder.util.LayoutMeasureUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class DrawTextLayout extends AbstractDrawCommand {
    private Layout mLayout;
    private float mLayoutHeight;
    private float mLayoutWidth;

    DrawTextLayout(Layout layout) {
        setLayout(layout);
    }

    public void setLayout(Layout layout) {
        this.mLayout = layout;
        this.mLayoutWidth = layout.getWidth();
        this.mLayoutHeight = LayoutMeasureUtil.getHeight(layout);
    }

    public float getLayoutWidth() {
        return this.mLayoutWidth;
    }

    public float getLayoutHeight() {
        return this.mLayoutHeight;
    }

    @Override // com.facebook.react.flat.AbstractDrawCommand
    protected void onDraw(Canvas canvas) {
        float left = getLeft();
        float top = getTop();
        canvas.translate(left, top);
        this.mLayout.draw(canvas);
        canvas.translate(-left, -top);
    }
}
