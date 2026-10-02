package com.scwang.smartrefresh.layout.header;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import org.apache.http.HttpStatus;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BezierRadarHeader extends InternalAbstract implements RefreshHeader {
    protected int mAccentColor;
    protected AnimatorSet mAnimatorSet;
    protected float mDotAlpha;
    protected float mDotFraction;
    protected float mDotRadius;
    protected boolean mEnableHorizontalDrag;
    protected boolean mManualAccentColor;
    protected boolean mManualPrimaryColor;
    protected Paint mPaint;
    protected Path mPath;
    protected int mPrimaryColor;
    protected int mRadarAngle;
    protected float mRadarCircle;
    protected float mRadarRadius;
    protected RectF mRadarRect;
    protected float mRadarScale;
    protected float mRippleRadius;
    protected int mWaveHeight;
    protected int mWaveOffsetX;
    protected boolean mWavePulling;
    protected int mWaveTop;

    public BezierRadarHeader(Context context) {
        this(context, null);
    }

    public BezierRadarHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierRadarHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mEnableHorizontalDrag = false;
        this.mWaveOffsetX = -1;
        this.mRadarAngle = 0;
        this.mRadarRadius = 0.0f;
        this.mRadarCircle = 0.0f;
        this.mRadarScale = 0.0f;
        this.mRadarRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        DensityUtil density = new DensityUtil();
        this.mPath = new Path();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mDotRadius = density.dip2px(7.0f);
        this.mRadarRadius = density.dip2px(20.0f);
        this.mRadarCircle = density.dip2px(7.0f);
        this.mPaint.setStrokeWidth(density.dip2px(3.0f));
        setMinimumHeight(density.dip2px(100.0f));
        if (isInEditMode()) {
            this.mWaveTop = 1000;
            this.mRadarScale = 1.0f;
            this.mRadarAngle = 270;
        } else {
            this.mRadarScale = 0.0f;
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BezierRadarHeader);
        this.mEnableHorizontalDrag = ta.getBoolean(R.styleable.BezierRadarHeader_srlEnableHorizontalDrag, this.mEnableHorizontalDrag);
        setAccentColor(ta.getColor(R.styleable.BezierRadarHeader_srlAccentColor, -1));
        setPrimaryColor(ta.getColor(R.styleable.BezierRadarHeader_srlPrimaryColor, -14540254));
        this.mManualAccentColor = ta.hasValue(R.styleable.BezierRadarHeader_srlAccentColor);
        this.mManualPrimaryColor = ta.hasValue(R.styleable.BezierRadarHeader_srlPrimaryColor);
        ta.recycle();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAnimatorSet != null) {
            this.mAnimatorSet.removeAllListeners();
            this.mAnimatorSet.end();
            this.mAnimatorSet = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        drawWave(canvas, width);
        drawDot(canvas, width, height);
        drawRadar(canvas, width, height);
        drawRipple(canvas, width, height);
        super.dispatchDraw(canvas);
    }

    protected void drawWave(Canvas canvas, int width) {
        this.mPath.reset();
        this.mPath.lineTo(0.0f, this.mWaveTop);
        this.mPath.quadTo(this.mWaveOffsetX >= 0 ? this.mWaveOffsetX : width / 2, this.mWaveTop + this.mWaveHeight, width, this.mWaveTop);
        this.mPath.lineTo(width, 0.0f);
        this.mPaint.setColor(this.mPrimaryColor);
        canvas.drawPath(this.mPath, this.mPaint);
    }

    protected void drawDot(Canvas canvas, int width, int height) {
        if (this.mDotAlpha > 0.0f) {
            this.mPaint.setColor(this.mAccentColor);
            float x = DensityUtil.px2dp(height);
            float wide = (this.mDotFraction * (width / 7)) - (this.mDotFraction > 1.0f ? ((this.mDotFraction - 1.0f) * (width / 7)) / this.mDotFraction : 0.0f);
            float high = height - (this.mDotFraction > 1.0f ? (((this.mDotFraction - 1.0f) * height) / 2.0f) / this.mDotFraction : 0.0f);
            for (int i = 0; i < 7; i++) {
                float index = (1.0f + i) - 4.0f;
                float alpha = 255.0f * (1.0f - (2.0f * (Math.abs(index) / 7.0f)));
                this.mPaint.setAlpha((int) (((double) (this.mDotAlpha * alpha)) * (1.0d - (1.0d / Math.pow((((double) x) / 800.0d) + 1.0d, 15.0d)))));
                float radius = this.mDotRadius * (1.0f - (1.0f / ((x / 10.0f) + 1.0f)));
                canvas.drawCircle(((width / 2) - (radius / 2.0f)) + (wide * index), high / 2.0f, radius, this.mPaint);
            }
            this.mPaint.setAlpha(255);
        }
    }

    protected void drawRadar(Canvas canvas, int width, int height) {
        if (this.mAnimatorSet != null || isInEditMode()) {
            float radius = this.mRadarRadius * this.mRadarScale;
            float circle = this.mRadarCircle * this.mRadarScale;
            this.mPaint.setColor(this.mAccentColor);
            this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(width / 2, height / 2, radius, this.mPaint);
            this.mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(width / 2, height / 2, radius + circle, this.mPaint);
            this.mPaint.setColor((this.mPrimaryColor & 16777215) | 1426063360);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mRadarRect.set((width / 2) - radius, (height / 2) - radius, (width / 2) + radius, (height / 2) + radius);
            canvas.drawArc(this.mRadarRect, 270.0f, this.mRadarAngle, true, this.mPaint);
            float radius2 = radius + circle;
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mRadarRect.set((width / 2) - radius2, (height / 2) - radius2, (width / 2) + radius2, (height / 2) + radius2);
            canvas.drawArc(this.mRadarRect, 270.0f, this.mRadarAngle, false, this.mPaint);
            this.mPaint.setStyle(Paint.Style.FILL);
        }
    }

    protected void drawRipple(Canvas canvas, int width, int height) {
        if (this.mRippleRadius > 0.0f) {
            this.mPaint.setColor(this.mAccentColor);
            canvas.drawCircle(width / 2, height / 2, this.mRippleRadius, this.mPaint);
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onPulling(float percent, int offset, int height, int extendHeight) {
        this.mWavePulling = true;
        this.mWaveTop = Math.min(height, offset);
        this.mWaveHeight = (int) (1.9f * Math.max(0, offset - height));
        this.mDotFraction = percent;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onReleasing(float percent, int offset, int height, int extendHeight) {
        if (this.mWavePulling) {
            onPulling(percent, offset, height, extendHeight);
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
        this.mWaveTop = height;
        this.mWavePulling = false;
        ValueAnimator mRadarAnimator = ValueAnimator.ofInt(0, 360);
        mRadarAnimator.setDuration(720L);
        mRadarAnimator.setRepeatCount(-1);
        mRadarAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRadarAnimator.addUpdateListener(new AnimatorUpdater((byte) 4));
        Interpolator interpolatorDecelerate = new DecelerateInterpolator();
        ValueAnimator animatorDotAlpha = ValueAnimator.ofFloat(1.0f, 0.0f);
        animatorDotAlpha.setInterpolator(interpolatorDecelerate);
        animatorDotAlpha.addUpdateListener(new AnimatorUpdater((byte) 2));
        ValueAnimator animatorRadarScale = ValueAnimator.ofFloat(0.0f, 1.0f);
        animatorDotAlpha.setInterpolator(interpolatorDecelerate);
        animatorRadarScale.addUpdateListener(new AnimatorUpdater((byte) 0));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animatorDotAlpha, animatorRadarScale, mRadarAnimator);
        animatorSet.start();
        ValueAnimator animatorWave = ValueAnimator.ofInt(this.mWaveHeight, 0, -((int) (this.mWaveHeight * 0.8f)), 0, -((int) (this.mWaveHeight * 0.4f)), 0);
        animatorWave.addUpdateListener(new AnimatorUpdater((byte) 1));
        animatorWave.setInterpolator(interpolatorDecelerate);
        animatorWave.setDuration(800L);
        animatorWave.start();
        this.mAnimatorSet = animatorSet;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(RefreshLayout layout, boolean success) {
        if (this.mAnimatorSet != null) {
            this.mAnimatorSet.removeAllListeners();
            this.mAnimatorSet.end();
            this.mAnimatorSet = null;
        }
        int width = getWidth();
        int height = getHeight();
        float bigRadius = (float) Math.sqrt((width * width) + (height * height));
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, bigRadius);
        animator.setDuration(400L);
        animator.addUpdateListener(new AnimatorUpdater((byte) 3));
        animator.start();
        return HttpStatus.SC_BAD_REQUEST;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                this.mDotAlpha = 1.0f;
                this.mRadarScale = 0.0f;
                this.mRippleRadius = 0.0f;
                break;
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(int... colors) {
        if (colors.length > 0 && !this.mManualPrimaryColor) {
            setPrimaryColor(colors[0]);
            this.mManualPrimaryColor = false;
        }
        if (colors.length > 1 && !this.mManualAccentColor) {
            setAccentColor(colors[1]);
            this.mManualAccentColor = false;
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Scale;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public boolean isSupportHorizontalDrag() {
        return this.mEnableHorizontalDrag;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
        this.mWaveOffsetX = offsetX;
        if (Build.VERSION.SDK_INT >= 16) {
            postInvalidateOnAnimation();
        } else {
            invalidate();
        }
    }

    public BezierRadarHeader setPrimaryColor(int color) {
        this.mPrimaryColor = color;
        this.mManualPrimaryColor = true;
        return this;
    }

    public BezierRadarHeader setAccentColor(int color) {
        this.mAccentColor = color;
        this.mManualAccentColor = true;
        return this;
    }

    protected class AnimatorUpdater implements ValueAnimator.AnimatorUpdateListener {
        byte propertyName;

        AnimatorUpdater(byte name) {
            this.propertyName = name;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator animation) {
            if (this.propertyName == 0) {
                BezierRadarHeader.this.mRadarScale = ((Float) animation.getAnimatedValue()).floatValue();
            } else if (1 == this.propertyName) {
                if (BezierRadarHeader.this.mWavePulling) {
                    animation.cancel();
                    return;
                } else {
                    BezierRadarHeader.this.mWaveHeight = ((Integer) animation.getAnimatedValue()).intValue() / 2;
                }
            } else if (2 == this.propertyName) {
                BezierRadarHeader.this.mDotAlpha = ((Float) animation.getAnimatedValue()).floatValue();
            } else if (3 == this.propertyName) {
                BezierRadarHeader.this.mRippleRadius = ((Float) animation.getAnimatedValue()).floatValue();
            } else if (4 == this.propertyName) {
                BezierRadarHeader.this.mRadarAngle = ((Integer) animation.getAnimatedValue()).intValue();
            }
            BezierRadarHeader.this.invalidate();
        }
    }
}
