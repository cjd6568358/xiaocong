package com.scwang.smartrefresh.layout.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ArrowDrawable extends Drawable {
    private int mWidth = 0;
    private int mHeight = 0;
    private Path mPath = new Path();
    private Paint mPaint = new Paint();

    public ArrowDrawable() {
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(-15614977);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (this.mWidth != width || this.mHeight != height) {
            int lineWidth = (width * 30) / 225;
            this.mPath.reset();
            float vector1 = (float) (((double) lineWidth) * Math.sin(0.7853981633974483d));
            float vector2 = (float) (((double) lineWidth) / Math.sin(0.7853981633974483d));
            this.mPath.moveTo(width / 2, height);
            this.mPath.lineTo(0.0f, height / 2);
            this.mPath.lineTo(vector1, (height / 2) - vector1);
            this.mPath.lineTo((width / 2) - (lineWidth / 2), (height - vector2) - (lineWidth / 2));
            this.mPath.lineTo((width / 2) - (lineWidth / 2), 0.0f);
            this.mPath.lineTo((width / 2) + (lineWidth / 2), 0.0f);
            this.mPath.lineTo((width / 2) + (lineWidth / 2), (height - vector2) - (lineWidth / 2));
            this.mPath.lineTo(width - vector1, (height / 2) - vector1);
            this.mPath.lineTo(width, height / 2);
            this.mPath.close();
            this.mWidth = width;
            this.mHeight = height;
        }
        canvas.drawPath(this.mPath, this.mPaint);
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }
}
