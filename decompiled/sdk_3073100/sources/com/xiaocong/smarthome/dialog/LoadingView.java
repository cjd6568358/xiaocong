package com.xiaocong.smarthome.dialog;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.xiaocong.smarthome.uilib.R;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class LoadingView extends View {
    private int mAnimateValue;
    private ValueAnimator mAnimator;
    private Paint mPaint;
    private int mPaintColor;
    private int mSize;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.LoadingStyle);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAnimateValue = 0;
        this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaocong.smarthome.dialog.LoadingView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                LoadingView.this.mAnimateValue = ((Integer) animation.getAnimatedValue()).intValue();
                LoadingView.this.invalidate();
            }
        };
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyleAttr, 0);
        this.mSize = array.getDimensionPixelSize(R.styleable.LoadingView_loading_view_size, DisplayHelper.dp2px(context, 32));
        this.mPaintColor = array.getInt(R.styleable.LoadingView_android_color, -1);
        array.recycle();
        initPaint();
    }

    public LoadingView(Context context, int size, int color) {
        super(context);
        this.mAnimateValue = 0;
        this.mUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaocong.smarthome.dialog.LoadingView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                LoadingView.this.mAnimateValue = ((Integer) animation.getAnimatedValue()).intValue();
                LoadingView.this.invalidate();
            }
        };
        this.mSize = size;
        this.mPaintColor = color;
        initPaint();
    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setColor(this.mPaintColor);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setColor(int color) {
        this.mPaintColor = color;
        this.mPaint.setColor(color);
        invalidate();
    }

    public void setSize(int size) {
        this.mSize = size;
        requestLayout();
    }

    public void start() {
        if (this.mAnimator == null) {
            this.mAnimator = ValueAnimator.ofInt(0, 11);
            this.mAnimator.addUpdateListener(this.mUpdateListener);
            this.mAnimator.setDuration(600L);
            this.mAnimator.setRepeatMode(1);
            this.mAnimator.setRepeatCount(-1);
            this.mAnimator.setInterpolator(new LinearInterpolator());
            this.mAnimator.start();
            return;
        }
        if (!this.mAnimator.isStarted()) {
            this.mAnimator.start();
        }
    }

    public void stop() {
        if (this.mAnimator != null) {
            this.mAnimator.removeUpdateListener(this.mUpdateListener);
            this.mAnimator.removeAllUpdateListeners();
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
    }

    private void drawLoading(Canvas canvas, int rotateDegrees) {
        int width = this.mSize / 12;
        int height = this.mSize / 6;
        this.mPaint.setStrokeWidth(width);
        canvas.rotate(rotateDegrees, this.mSize / 2, this.mSize / 2);
        canvas.translate(this.mSize / 2, this.mSize / 2);
        for (int i = 0; i < 12; i++) {
            canvas.rotate(30.0f);
            this.mPaint.setAlpha((int) ((255.0f * (i + 1)) / 12.0f));
            canvas.translate(0.0f, ((-this.mSize) / 2) + (width / 2));
            canvas.drawLine(0.0f, 0.0f, 0.0f, height, this.mPaint);
            canvas.translate(0.0f, (this.mSize / 2) - (width / 2));
        }
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(this.mSize, this.mSize);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null, 31);
        drawLoading(canvas, this.mAnimateValue * 30);
        canvas.restoreToCount(saveCount);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == 0) {
            start();
        } else {
            stop();
        }
    }
}
