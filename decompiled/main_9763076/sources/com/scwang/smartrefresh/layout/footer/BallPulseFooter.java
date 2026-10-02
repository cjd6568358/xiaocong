package com.scwang.smartrefresh.layout.footer;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import bsh.ParserConstants;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BallPulseFooter extends InternalAbstract implements RefreshFooter {
    private int mAnimatingColor;
    private ArrayList<ValueAnimator> mAnimators;
    private float mCircleSpacing;
    private boolean mIsStarted;
    private boolean mManualAnimationColor;
    private boolean mManualNormalColor;
    private int mNormalColor;
    private Paint mPaint;
    private float[] mScaleFloats;
    private SpinnerStyle mSpinnerStyle;
    private Map<ValueAnimator, ValueAnimator.AnimatorUpdateListener> mUpdateListeners;

    public BallPulseFooter(Context context) {
        this(context, null);
    }

    public BallPulseFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallPulseFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSpinnerStyle = SpinnerStyle.Translate;
        this.mNormalColor = -1118482;
        this.mAnimatingColor = -1615546;
        this.mScaleFloats = new float[]{1.0f, 1.0f, 1.0f};
        this.mIsStarted = false;
        this.mUpdateListeners = new HashMap();
        setMinimumHeight(DensityUtil.dp2px(60.0f));
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BallPulseFooter);
        if (ta.hasValue(R.styleable.BallPulseFooter_srlNormalColor)) {
            setNormalColor(ta.getColor(R.styleable.BallPulseFooter_srlNormalColor, 0));
        }
        if (ta.hasValue(R.styleable.BallPulseFooter_srlAnimatingColor)) {
            setAnimatingColor(ta.getColor(R.styleable.BallPulseFooter_srlAnimatingColor, 0));
        }
        this.mSpinnerStyle = SpinnerStyle.values()[ta.getInt(R.styleable.BallPulseFooter_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal())];
        ta.recycle();
        this.mCircleSpacing = DensityUtil.dp2px(4.0f);
        this.mPaint = new Paint();
        this.mPaint.setColor(-1);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setAntiAlias(true);
        this.mAnimators = new ArrayList<>();
        int[] delays = {ParserConstants.STARASSIGN, 240, 360};
        for (int i = 0; i < 3; i++) {
            final int index = i;
            ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.3f, 1.0f);
            animator.setDuration(750L);
            animator.setRepeatCount(-1);
            animator.setStartDelay(delays[i]);
            this.mUpdateListeners.put(animator, new ValueAnimator.AnimatorUpdateListener() { // from class: com.scwang.smartrefresh.layout.footer.BallPulseFooter.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallPulseFooter.this.mScaleFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallPulseFooter.this.postInvalidate();
                }
            });
            this.mAnimators.add(animator);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAnimators != null) {
            for (int i = 0; i < this.mAnimators.size(); i++) {
                this.mAnimators.get(i).cancel();
                this.mAnimators.get(i).removeAllListeners();
                this.mAnimators.get(i).removeAllUpdateListeners();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float radius = (Math.min(width, height) - (this.mCircleSpacing * 2.0f)) / 6.0f;
        float x = (width / 2) - ((radius * 2.0f) + this.mCircleSpacing);
        float y = height / 2;
        for (int i = 0; i < 3; i++) {
            canvas.save();
            float translateX = (radius * 2.0f * i) + x + (this.mCircleSpacing * i);
            canvas.translate(translateX, y);
            canvas.scale(this.mScaleFloats[i], this.mScaleFloats[i]);
            canvas.drawCircle(0.0f, 0.0f, radius, this.mPaint);
            canvas.restore();
        }
        super.dispatchDraw(canvas);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        if (!this.mIsStarted) {
            for (int i = 0; i < this.mAnimators.size(); i++) {
                ValueAnimator animator = this.mAnimators.get(i);
                ValueAnimator.AnimatorUpdateListener updateListener = this.mUpdateListeners.get(animator);
                if (updateListener != null) {
                    animator.addUpdateListener(updateListener);
                }
                animator.start();
            }
            this.mIsStarted = true;
            this.mPaint.setColor(this.mAnimatingColor);
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(RefreshLayout layout, boolean success) {
        if (this.mAnimators != null && this.mIsStarted) {
            this.mIsStarted = false;
            this.mScaleFloats = new float[]{1.0f, 1.0f, 1.0f};
            for (ValueAnimator animator : this.mAnimators) {
                if (animator != null) {
                    animator.removeAllUpdateListeners();
                    animator.end();
                }
            }
        }
        this.mPaint.setColor(this.mNormalColor);
        return 0;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshFooter
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(int... colors) {
        if (!this.mManualAnimationColor && colors.length > 1) {
            setAnimatingColor(colors[0]);
            this.mManualAnimationColor = false;
        }
        if (!this.mManualNormalColor) {
            if (colors.length > 1) {
                setNormalColor(colors[1]);
            } else if (colors.length > 0) {
                setNormalColor(ColorUtils.compositeColors(-1711276033, colors[0]));
            }
            this.mManualNormalColor = false;
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public SpinnerStyle getSpinnerStyle() {
        return this.mSpinnerStyle;
    }

    public BallPulseFooter setNormalColor(int color) {
        this.mNormalColor = color;
        this.mManualNormalColor = true;
        if (!this.mIsStarted) {
            this.mPaint.setColor(color);
        }
        return this;
    }

    public BallPulseFooter setAnimatingColor(int color) {
        this.mAnimatingColor = color;
        this.mManualAnimationColor = true;
        if (this.mIsStarted) {
            this.mPaint.setColor(color);
        }
        return this;
    }
}
