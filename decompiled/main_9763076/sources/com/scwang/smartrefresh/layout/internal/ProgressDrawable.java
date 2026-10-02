package com.scwang.smartrefresh.layout.internal;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ProgressDrawable extends Drawable implements Animatable {
    private ValueAnimator mValueAnimator;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mProgressDegree = 0;
    private Path mPath = new Path();
    private Paint mPaint = new Paint();

    public ProgressDrawable() {
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(-5592406);
        setupAnimators();
    }

    public void setColor(int color) {
        this.mPaint.setColor(color);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int width = bounds.width();
        int height = bounds.height();
        int r = Math.max(1, width / 20);
        if (this.mWidth != width || this.mHeight != height) {
            this.mPath.reset();
            this.mPath.addCircle(width - r, height / 2, r, Path.Direction.CW);
            this.mPath.addRect(width - (r * 5), (height / 2) - r, width - r, (height / 2) + r, Path.Direction.CW);
            this.mPath.addCircle(width - (r * 5), height / 2, r, Path.Direction.CW);
            this.mWidth = width;
            this.mHeight = height;
        }
        canvas.save();
        canvas.rotate(this.mProgressDegree, width / 2, height / 2);
        for (int i = 0; i < 12; i++) {
            this.mPaint.setAlpha((i + 5) * 17);
            canvas.rotate(30.0f, width / 2, height / 2);
            canvas.drawPath(this.mPath, this.mPaint);
        }
        canvas.restore();
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

    private void setupAnimators() {
        this.mValueAnimator = ValueAnimator.ofInt(30, 3600);
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smartrefresh.layout.internal.ProgressDrawable.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = ((Integer) animation.getAnimatedValue()).intValue();
                ProgressDrawable.this.mProgressDegree = (value / 30) * 30;
                ProgressDrawable.this.invalidateSelf();
            }
        });
        this.mValueAnimator.setDuration(10000L);
        this.mValueAnimator.setInterpolator(new LinearInterpolator());
        this.mValueAnimator.setRepeatCount(-1);
        this.mValueAnimator.setRepeatMode(1);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (!this.mValueAnimator.isRunning()) {
            this.mValueAnimator.start();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        if (this.mValueAnimator.isRunning()) {
            this.mValueAnimator.cancel();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.mValueAnimator.isRunning();
    }
}
