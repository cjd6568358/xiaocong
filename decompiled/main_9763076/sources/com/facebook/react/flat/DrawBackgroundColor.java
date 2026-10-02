package com.facebook.react.flat;

import android.graphics.Canvas;
import android.graphics.Paint;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class DrawBackgroundColor extends AbstractDrawCommand {
    private static final Paint PAINT = new Paint();
    private final int mBackgroundColor;

    DrawBackgroundColor(int backgroundColor) {
        this.mBackgroundColor = backgroundColor;
    }

    @Override // com.facebook.react.flat.AbstractDrawCommand
    public void onDraw(Canvas canvas) {
        PAINT.setColor(this.mBackgroundColor);
        canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), PAINT);
    }
}
