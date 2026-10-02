package com.xiaocong.smarthome.switchbutton;

import android.R;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SwitchButton extends CompoundButton {
    private static int[] CHECKED_PRESSED_STATE = {R.attr.state_checked, R.attr.state_enabled, R.attr.state_pressed};
    private static int[] UNCHECKED_PRESSED_STATE = {-16842912, R.attr.state_enabled, R.attr.state_pressed};
    private long mAnimationDuration;
    private ColorStateList mBackColor;
    private Drawable mBackDrawable;
    private int mBackHeight;
    private float mBackRadius;
    private RectF mBackRectF;
    private int mBackWidth;
    private boolean mCatch;
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private int mClickTimeout;
    private int mCurrBackColor;
    private int mCurrThumbColor;
    private Drawable mCurrentBackDrawable;
    private boolean mDrawDebugRect;
    private boolean mFadeBack;
    private boolean mIsBackUseDrawable;
    private boolean mIsThumbUseDrawable;
    private float mLastX;
    private int mNextBackColor;
    private Drawable mNextBackDrawable;
    private Layout mOffLayout;
    private int mOffTextColor;
    private Layout mOnLayout;
    private int mOnTextColor;
    private Paint mPaint;
    private RectF mPresentThumbRectF;
    private float mProgress;
    private ObjectAnimator mProgressAnimator;
    private boolean mReady;
    private Paint mRectPaint;
    private boolean mRestoring;
    private RectF mSafeRectF;
    private float mStartX;
    private float mStartY;
    private int mTextAdjust;
    private int mTextExtra;
    private float mTextHeight;
    private CharSequence mTextOff;
    private RectF mTextOffRectF;
    private CharSequence mTextOn;
    private RectF mTextOnRectF;
    private TextPaint mTextPaint;
    private int mTextThumbInset;
    private float mTextWidth;
    private ColorStateList mThumbColor;
    private Drawable mThumbDrawable;
    private int mThumbHeight;
    private RectF mThumbMargin;
    private float mThumbRadius;
    private float mThumbRangeRatio;
    private RectF mThumbRectF;
    private int mThumbWidth;
    private int mTintColor;
    private int mTouchSlop;

    public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDrawDebugRect = false;
        this.mRestoring = false;
        this.mReady = false;
        this.mCatch = false;
        init(attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDrawDebugRect = false;
        this.mRestoring = false;
        this.mReady = false;
        this.mCatch = false;
        init(attrs);
    }

    public SwitchButton(Context context) {
        super(context);
        this.mDrawDebugRect = false;
        this.mRestoring = false;
        this.mReady = false;
        this.mCatch = false;
        init(null);
    }

    private void init(AttributeSet attrs) {
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mPaint = new Paint(1);
        this.mRectPaint = new Paint(1);
        this.mRectPaint.setStyle(Paint.Style.STROKE);
        this.mRectPaint.setStrokeWidth(getResources().getDisplayMetrics().density);
        this.mTextPaint = getPaint();
        this.mThumbRectF = new RectF();
        this.mBackRectF = new RectF();
        this.mSafeRectF = new RectF();
        this.mThumbMargin = new RectF();
        this.mTextOnRectF = new RectF();
        this.mTextOffRectF = new RectF();
        this.mProgressAnimator = ObjectAnimator.ofFloat(this, "progress", 0.0f, 0.0f).setDuration(250L);
        this.mProgressAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mPresentThumbRectF = new RectF();
        Resources res = getResources();
        float density = res.getDisplayMetrics().density;
        Drawable thumbDrawable = null;
        ColorStateList thumbColor = null;
        float margin = density * 2.0f;
        float marginLeft = 0.0f;
        float marginRight = 0.0f;
        float marginTop = 0.0f;
        float marginBottom = 0.0f;
        float thumbWidth = 0.0f;
        float thumbHeight = 0.0f;
        float thumbRadius = -1.0f;
        float backRadius = -1.0f;
        Drawable backDrawable = null;
        ColorStateList backColor = null;
        float thumbRangeRatio = 1.8f;
        int animationDuration = 250;
        boolean fadeBack = true;
        int tintColor = 0;
        String textOn = null;
        String textOff = null;
        int textThumbInset = 0;
        int textExtra = 0;
        int textAdjust = 0;
        TypedArray ta = attrs == null ? null : getContext().obtainStyledAttributes(attrs, com.xiaocong.smarthome.uilib.R.styleable.SwitchButton);
        if (ta != null) {
            thumbDrawable = ta.getDrawable(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbDrawable);
            thumbColor = ta.getColorStateList(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbColor);
            float margin2 = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbMargin, margin);
            marginLeft = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbMarginLeft, margin2);
            marginRight = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbMarginRight, margin2);
            marginTop = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbMarginTop, margin2);
            marginBottom = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbMarginBottom, margin2);
            thumbWidth = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbWidth, 0.0f);
            thumbHeight = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbHeight, 0.0f);
            thumbRadius = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbRadius, -1.0f);
            backRadius = ta.getDimension(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswBackRadius, -1.0f);
            backDrawable = ta.getDrawable(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswBackDrawable);
            backColor = ta.getColorStateList(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswBackColor);
            thumbRangeRatio = ta.getFloat(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswThumbRangeRatio, 1.8f);
            animationDuration = ta.getInteger(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswAnimationDuration, 250);
            fadeBack = ta.getBoolean(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswFadeBack, true);
            tintColor = ta.getColor(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswTintColor, 0);
            textOn = ta.getString(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswTextOn);
            textOff = ta.getString(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswTextOff);
            textThumbInset = ta.getDimensionPixelSize(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswTextThumbInset, 0);
            textExtra = ta.getDimensionPixelSize(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswTextExtra, 0);
            textAdjust = ta.getDimensionPixelSize(com.xiaocong.smarthome.uilib.R.styleable.SwitchButton_kswTextAdjust, 0);
            ta.recycle();
        }
        TypedArray ta2 = attrs == null ? null : getContext().obtainStyledAttributes(attrs, new int[]{R.attr.focusable, R.attr.clickable});
        if (ta2 != null) {
            boolean focusable = ta2.getBoolean(0, true);
            boolean clickable = ta2.getBoolean(1, focusable);
            setFocusable(focusable);
            setClickable(clickable);
            ta2.recycle();
        } else {
            setFocusable(true);
            setClickable(true);
        }
        this.mTextOn = textOn;
        this.mTextOff = textOff;
        this.mTextThumbInset = textThumbInset;
        this.mTextExtra = textExtra;
        this.mTextAdjust = textAdjust;
        this.mThumbDrawable = thumbDrawable;
        this.mThumbColor = thumbColor;
        this.mIsThumbUseDrawable = this.mThumbDrawable != null;
        this.mTintColor = tintColor;
        if (this.mTintColor == 0) {
            TypedValue typedValue = new TypedValue();
            boolean found = getContext().getTheme().resolveAttribute(com.xiaocong.smarthome.uilib.R.attr.colorAccent, typedValue, true);
            if (found) {
                this.mTintColor = typedValue.data;
            } else {
                this.mTintColor = 3309506;
            }
        }
        if (!this.mIsThumbUseDrawable && this.mThumbColor == null) {
            this.mThumbColor = ColorUtils.generateThumbColorWithTintColor(this.mTintColor);
            this.mCurrThumbColor = this.mThumbColor.getDefaultColor();
        }
        this.mThumbWidth = ceil(thumbWidth);
        this.mThumbHeight = ceil(thumbHeight);
        this.mBackDrawable = backDrawable;
        this.mBackColor = backColor;
        this.mIsBackUseDrawable = this.mBackDrawable != null;
        if (!this.mIsBackUseDrawable && this.mBackColor == null) {
            this.mBackColor = ColorUtils.generateBackColorWithTintColor(this.mTintColor);
            this.mCurrBackColor = this.mBackColor.getDefaultColor();
            this.mNextBackColor = this.mBackColor.getColorForState(CHECKED_PRESSED_STATE, this.mCurrBackColor);
        }
        this.mThumbMargin.set(marginLeft, marginTop, marginRight, marginBottom);
        if (this.mThumbMargin.width() >= 0.0f) {
            thumbRangeRatio = Math.max(thumbRangeRatio, 1.0f);
        }
        this.mThumbRangeRatio = thumbRangeRatio;
        this.mThumbRadius = thumbRadius;
        this.mBackRadius = backRadius;
        this.mAnimationDuration = animationDuration;
        this.mFadeBack = fadeBack;
        this.mProgressAnimator.setDuration(this.mAnimationDuration);
        if (isChecked()) {
            setProgress(1.0f);
        }
    }

    private Layout makeLayout(CharSequence text) {
        return new StaticLayout(text, this.mTextPaint, (int) Math.ceil(Layout.getDesiredWidth(text, this.mTextPaint)), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOnLayout == null && !TextUtils.isEmpty(this.mTextOn)) {
            this.mOnLayout = makeLayout(this.mTextOn);
        }
        if (this.mOffLayout == null && !TextUtils.isEmpty(this.mTextOff)) {
            this.mOffLayout = makeLayout(this.mTextOff);
        }
        float onWidth = this.mOnLayout != null ? this.mOnLayout.getWidth() : 0.0f;
        float offWidth = this.mOffLayout != null ? this.mOffLayout.getWidth() : 0.0f;
        if (onWidth != 0.0f || offWidth != 0.0f) {
            this.mTextWidth = Math.max(onWidth, offWidth);
        } else {
            this.mTextWidth = 0.0f;
        }
        float onHeight = this.mOnLayout != null ? this.mOnLayout.getHeight() : 0.0f;
        float offHeight = this.mOffLayout != null ? this.mOffLayout.getHeight() : 0.0f;
        if (onHeight != 0.0f || offHeight != 0.0f) {
            this.mTextHeight = Math.max(onHeight, offHeight);
        } else {
            this.mTextHeight = 0.0f;
        }
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int measuredWidth = widthSize;
        if (this.mThumbWidth == 0 && this.mIsThumbUseDrawable) {
            this.mThumbWidth = this.mThumbDrawable.getIntrinsicWidth();
        }
        int textWidth = ceil(this.mTextWidth);
        if (this.mThumbRangeRatio == 0.0f) {
            this.mThumbRangeRatio = 1.8f;
        }
        if (widthMode == 1073741824) {
            int contentSize = (widthSize - getPaddingLeft()) - getPaddingRight();
            if (this.mThumbWidth != 0) {
                int moveRange = ceil(this.mThumbWidth * this.mThumbRangeRatio);
                int textExtraSpace = (this.mTextExtra + textWidth) - ((moveRange - this.mThumbWidth) + ceil(Math.max(this.mThumbMargin.left, this.mThumbMargin.right)));
                this.mBackWidth = ceil(moveRange + this.mThumbMargin.left + this.mThumbMargin.right + Math.max(textExtraSpace, 0));
                if (this.mBackWidth < 0) {
                    this.mThumbWidth = 0;
                }
                if (moveRange + Math.max(this.mThumbMargin.left, 0.0f) + Math.max(this.mThumbMargin.right, 0.0f) + Math.max(textExtraSpace, 0) > contentSize) {
                    this.mThumbWidth = 0;
                }
            }
            if (this.mThumbWidth == 0) {
                int moveRange2 = ceil((((widthSize - getPaddingLeft()) - getPaddingRight()) - Math.max(this.mThumbMargin.left, 0.0f)) - Math.max(this.mThumbMargin.right, 0.0f));
                if (moveRange2 < 0) {
                    this.mThumbWidth = 0;
                    this.mBackWidth = 0;
                    return measuredWidth;
                }
                this.mThumbWidth = ceil(moveRange2 / this.mThumbRangeRatio);
                this.mBackWidth = ceil(moveRange2 + this.mThumbMargin.left + this.mThumbMargin.right);
                if (this.mBackWidth < 0) {
                    this.mThumbWidth = 0;
                    this.mBackWidth = 0;
                    return measuredWidth;
                }
                int textExtraSpace2 = (this.mTextExtra + textWidth) - ((moveRange2 - this.mThumbWidth) + ceil(Math.max(this.mThumbMargin.left, this.mThumbMargin.right)));
                if (textExtraSpace2 > 0) {
                    this.mThumbWidth -= textExtraSpace2;
                }
                if (this.mThumbWidth < 0) {
                    this.mThumbWidth = 0;
                    this.mBackWidth = 0;
                    return measuredWidth;
                }
            }
        } else {
            if (this.mThumbWidth == 0) {
                this.mThumbWidth = ceil(getResources().getDisplayMetrics().density * 20.0f);
            }
            if (this.mThumbRangeRatio == 0.0f) {
                this.mThumbRangeRatio = 1.8f;
            }
            int moveRange3 = ceil(this.mThumbWidth * this.mThumbRangeRatio);
            int textExtraSpace3 = ceil((this.mTextExtra + textWidth) - (((moveRange3 - this.mThumbWidth) + Math.max(this.mThumbMargin.left, this.mThumbMargin.right)) + this.mTextThumbInset));
            this.mBackWidth = ceil(moveRange3 + this.mThumbMargin.left + this.mThumbMargin.right + Math.max(0, textExtraSpace3));
            if (this.mBackWidth < 0) {
                this.mThumbWidth = 0;
                this.mBackWidth = 0;
                return measuredWidth;
            }
            int contentSize2 = ceil(moveRange3 + Math.max(0.0f, this.mThumbMargin.left) + Math.max(0.0f, this.mThumbMargin.right) + Math.max(0, textExtraSpace3));
            measuredWidth = Math.max(contentSize2, getPaddingLeft() + contentSize2 + getPaddingRight());
        }
        return measuredWidth;
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int measuredHeight = heightSize;
        if (this.mThumbHeight == 0 && this.mIsThumbUseDrawable) {
            this.mThumbHeight = this.mThumbDrawable.getIntrinsicHeight();
        }
        if (heightMode == 1073741824) {
            if (this.mThumbHeight != 0) {
                this.mBackHeight = ceil(this.mThumbHeight + this.mThumbMargin.top + this.mThumbMargin.bottom);
                this.mBackHeight = ceil(Math.max(this.mBackHeight, this.mTextHeight));
                if ((((this.mBackHeight + getPaddingTop()) + getPaddingBottom()) - Math.min(0.0f, this.mThumbMargin.top)) - Math.min(0.0f, this.mThumbMargin.bottom) > heightSize) {
                    this.mThumbHeight = 0;
                }
            }
            if (this.mThumbHeight == 0) {
                this.mBackHeight = ceil(((heightSize - getPaddingTop()) - getPaddingBottom()) + Math.min(0.0f, this.mThumbMargin.top) + Math.min(0.0f, this.mThumbMargin.bottom));
                if (this.mBackHeight < 0) {
                    this.mBackHeight = 0;
                    this.mThumbHeight = 0;
                    return measuredHeight;
                }
                this.mThumbHeight = ceil((this.mBackHeight - this.mThumbMargin.top) - this.mThumbMargin.bottom);
            }
            if (this.mThumbHeight < 0) {
                this.mBackHeight = 0;
                this.mThumbHeight = 0;
                return measuredHeight;
            }
        } else {
            if (this.mThumbHeight == 0) {
                this.mThumbHeight = ceil(getResources().getDisplayMetrics().density * 20.0f);
            }
            this.mBackHeight = ceil(this.mThumbHeight + this.mThumbMargin.top + this.mThumbMargin.bottom);
            if (this.mBackHeight < 0) {
                this.mBackHeight = 0;
                this.mThumbHeight = 0;
                return measuredHeight;
            }
            int textExtraSpace = ceil(this.mTextHeight - this.mBackHeight);
            if (textExtraSpace > 0) {
                this.mBackHeight += textExtraSpace;
                this.mThumbHeight += textExtraSpace;
            }
            int contentSize = Math.max(this.mThumbHeight, this.mBackHeight);
            measuredHeight = Math.max(Math.max(contentSize, getPaddingTop() + contentSize + getPaddingBottom()), getSuggestedMinimumHeight());
        }
        return measuredHeight;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            setup();
        }
    }

    private int ceil(double dimen) {
        return (int) Math.ceil(dimen);
    }

    private void setup() {
        float thumbTop;
        float thumbLeft;
        if (this.mThumbWidth != 0 && this.mThumbHeight != 0 && this.mBackWidth != 0 && this.mBackHeight != 0) {
            if (this.mThumbRadius == -1.0f) {
                this.mThumbRadius = Math.min(this.mThumbWidth, this.mThumbHeight) / 2;
            }
            if (this.mBackRadius == -1.0f) {
                this.mBackRadius = Math.min(this.mBackWidth, this.mBackHeight) / 2;
            }
            int contentWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            int contentHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
            int drawingWidth = ceil((this.mBackWidth - Math.min(0.0f, this.mThumbMargin.left)) - Math.min(0.0f, this.mThumbMargin.right));
            int drawingHeight = ceil((this.mBackHeight - Math.min(0.0f, this.mThumbMargin.top)) - Math.min(0.0f, this.mThumbMargin.bottom));
            if (contentHeight <= drawingHeight) {
                thumbTop = getPaddingTop() + Math.max(0.0f, this.mThumbMargin.top);
            } else {
                thumbTop = getPaddingTop() + Math.max(0.0f, this.mThumbMargin.top) + (((contentHeight - drawingHeight) + 1) / 2);
            }
            if (contentWidth <= this.mBackWidth) {
                thumbLeft = getPaddingLeft() + Math.max(0.0f, this.mThumbMargin.left);
            } else {
                thumbLeft = getPaddingLeft() + Math.max(0.0f, this.mThumbMargin.left) + (((contentWidth - drawingWidth) + 1) / 2);
            }
            this.mThumbRectF.set(thumbLeft, thumbTop, this.mThumbWidth + thumbLeft, this.mThumbHeight + thumbTop);
            float backLeft = this.mThumbRectF.left - this.mThumbMargin.left;
            this.mBackRectF.set(backLeft, this.mThumbRectF.top - this.mThumbMargin.top, this.mBackWidth + backLeft, (this.mThumbRectF.top - this.mThumbMargin.top) + this.mBackHeight);
            this.mSafeRectF.set(this.mThumbRectF.left, 0.0f, (this.mBackRectF.right - this.mThumbMargin.right) - this.mThumbRectF.width(), 0.0f);
            float minBackRadius = Math.min(this.mBackRectF.width(), this.mBackRectF.height()) / 2.0f;
            this.mBackRadius = Math.min(minBackRadius, this.mBackRadius);
            if (this.mBackDrawable != null) {
                this.mBackDrawable.setBounds((int) this.mBackRectF.left, (int) this.mBackRectF.top, ceil(this.mBackRectF.right), ceil(this.mBackRectF.bottom));
            }
            if (this.mOnLayout != null) {
                float onLeft = (this.mBackRectF.left + (((((this.mBackRectF.width() + this.mTextThumbInset) - this.mThumbWidth) - this.mThumbMargin.right) - this.mOnLayout.getWidth()) / 2.0f)) - this.mTextAdjust;
                float onTop = this.mBackRectF.top + ((this.mBackRectF.height() - this.mOnLayout.getHeight()) / 2.0f);
                this.mTextOnRectF.set(onLeft, onTop, this.mOnLayout.getWidth() + onLeft, this.mOnLayout.getHeight() + onTop);
            }
            if (this.mOffLayout != null) {
                float offLeft = ((this.mBackRectF.right - (((((this.mBackRectF.width() + this.mTextThumbInset) - this.mThumbWidth) - this.mThumbMargin.left) - this.mOffLayout.getWidth()) / 2.0f)) - this.mOffLayout.getWidth()) + this.mTextAdjust;
                float offTop = this.mBackRectF.top + ((this.mBackRectF.height() - this.mOffLayout.getHeight()) / 2.0f);
                this.mTextOffRectF.set(offLeft, offTop, this.mOffLayout.getWidth() + offLeft, this.mOffLayout.getHeight() + offTop);
            }
            this.mReady = true;
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        float progress;
        super.onDraw(canvas);
        if (!this.mReady) {
            setup();
        }
        if (this.mReady) {
            if (this.mIsBackUseDrawable) {
                if (this.mFadeBack && this.mCurrentBackDrawable != null && this.mNextBackDrawable != null) {
                    Drawable below = isChecked() ? this.mCurrentBackDrawable : this.mNextBackDrawable;
                    Drawable above = isChecked() ? this.mNextBackDrawable : this.mCurrentBackDrawable;
                    int alpha = (int) (255.0f * getProgress());
                    below.setAlpha(alpha);
                    below.draw(canvas);
                    above.setAlpha(255 - alpha);
                    above.draw(canvas);
                } else {
                    this.mBackDrawable.setAlpha(255);
                    this.mBackDrawable.draw(canvas);
                }
            } else if (this.mFadeBack) {
                int belowColor = isChecked() ? this.mCurrBackColor : this.mNextBackColor;
                int aboveColor = isChecked() ? this.mNextBackColor : this.mCurrBackColor;
                int alpha2 = (int) (255.0f * getProgress());
                int colorAlpha = Color.alpha(belowColor);
                this.mPaint.setARGB((colorAlpha * alpha2) / 255, Color.red(belowColor), Color.green(belowColor), Color.blue(belowColor));
                canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
                int colorAlpha2 = Color.alpha(aboveColor);
                this.mPaint.setARGB((colorAlpha2 * (255 - alpha2)) / 255, Color.red(aboveColor), Color.green(aboveColor), Color.blue(aboveColor));
                canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
                this.mPaint.setAlpha(255);
            } else {
                this.mPaint.setColor(this.mCurrBackColor);
                canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
            }
            Layout switchText = ((double) getProgress()) > 0.5d ? this.mOnLayout : this.mOffLayout;
            RectF textRectF = ((double) getProgress()) > 0.5d ? this.mTextOnRectF : this.mTextOffRectF;
            if (switchText != null && textRectF != null) {
                if (getProgress() >= 0.75d) {
                    progress = (getProgress() * 4.0f) - 3.0f;
                } else {
                    progress = ((double) getProgress()) < 0.25d ? 1.0f - (getProgress() * 4.0f) : 0.0f;
                }
                int alpha3 = (int) (progress * 255.0f);
                int textColor = ((double) getProgress()) > 0.5d ? this.mOnTextColor : this.mOffTextColor;
                int colorAlpha3 = Color.alpha(textColor);
                switchText.getPaint().setARGB((colorAlpha3 * alpha3) / 255, Color.red(textColor), Color.green(textColor), Color.blue(textColor));
                canvas.save();
                canvas.translate(textRectF.left, textRectF.top);
                switchText.draw(canvas);
                canvas.restore();
            }
            this.mPresentThumbRectF.set(this.mThumbRectF);
            this.mPresentThumbRectF.offset(this.mProgress * this.mSafeRectF.width(), 0.0f);
            if (this.mIsThumbUseDrawable) {
                this.mThumbDrawable.setBounds((int) this.mPresentThumbRectF.left, (int) this.mPresentThumbRectF.top, ceil(this.mPresentThumbRectF.right), ceil(this.mPresentThumbRectF.bottom));
                this.mThumbDrawable.draw(canvas);
            } else {
                this.mPaint.setColor(this.mCurrThumbColor);
                canvas.drawRoundRect(this.mPresentThumbRectF, this.mThumbRadius, this.mThumbRadius, this.mPaint);
            }
            if (this.mDrawDebugRect) {
                this.mRectPaint.setColor(Color.parseColor("#AA0000"));
                canvas.drawRect(this.mBackRectF, this.mRectPaint);
                this.mRectPaint.setColor(Color.parseColor("#0000FF"));
                canvas.drawRect(this.mPresentThumbRectF, this.mRectPaint);
                this.mRectPaint.setColor(Color.parseColor("#000000"));
                canvas.drawLine(this.mSafeRectF.left, this.mThumbRectF.top, this.mSafeRectF.right, this.mThumbRectF.top, this.mRectPaint);
                this.mRectPaint.setColor(Color.parseColor("#00CC00"));
                canvas.drawRect(((double) getProgress()) > 0.5d ? this.mTextOnRectF : this.mTextOffRectF, this.mRectPaint);
            }
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (!this.mIsThumbUseDrawable && this.mThumbColor != null) {
            this.mCurrThumbColor = this.mThumbColor.getColorForState(getDrawableState(), this.mCurrThumbColor);
        } else {
            setDrawableState(this.mThumbDrawable);
        }
        int[] nextState = isChecked() ? UNCHECKED_PRESSED_STATE : CHECKED_PRESSED_STATE;
        ColorStateList textColors = getTextColors();
        if (textColors != null) {
            int defaultTextColor = textColors.getDefaultColor();
            this.mOnTextColor = textColors.getColorForState(CHECKED_PRESSED_STATE, defaultTextColor);
            this.mOffTextColor = textColors.getColorForState(UNCHECKED_PRESSED_STATE, defaultTextColor);
        }
        if (!this.mIsBackUseDrawable && this.mBackColor != null) {
            this.mCurrBackColor = this.mBackColor.getColorForState(getDrawableState(), this.mCurrBackColor);
            this.mNextBackColor = this.mBackColor.getColorForState(nextState, this.mCurrBackColor);
            return;
        }
        if ((this.mBackDrawable instanceof StateListDrawable) && this.mFadeBack) {
            this.mBackDrawable.setState(nextState);
            this.mNextBackDrawable = this.mBackDrawable.getCurrent().mutate();
        } else {
            this.mNextBackDrawable = null;
        }
        setDrawableState(this.mBackDrawable);
        if (this.mBackDrawable != null) {
            this.mCurrentBackDrawable = this.mBackDrawable.getCurrent().mutate();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || !isClickable() || !isFocusable() || !this.mReady) {
            return false;
        }
        int action = event.getAction();
        float deltaX = event.getX() - this.mStartX;
        float deltaY = event.getY() - this.mStartY;
        switch (action) {
            case 0:
                this.mStartX = event.getX();
                this.mStartY = event.getY();
                this.mLastX = this.mStartX;
                setPressed(true);
                break;
            case 1:
            case 3:
                this.mCatch = false;
                setPressed(false);
                float time = event.getEventTime() - event.getDownTime();
                if (Math.abs(deltaX) < this.mTouchSlop && Math.abs(deltaY) < this.mTouchSlop && time < this.mClickTimeout) {
                    performClick();
                } else {
                    boolean nextStatus = getStatusBasedOnPos();
                    if (nextStatus != isChecked()) {
                        playSoundEffect(0);
                        setChecked(nextStatus);
                    } else {
                        animateToState(nextStatus);
                    }
                }
                break;
            case 2:
                float x = event.getX();
                setProgress(getProgress() + ((x - this.mLastX) / this.mSafeRectF.width()));
                if (!this.mCatch && (Math.abs(deltaX) > this.mTouchSlop / 2 || Math.abs(deltaY) > this.mTouchSlop / 2)) {
                    if (deltaY == 0.0f || Math.abs(deltaX) > Math.abs(deltaY)) {
                        catchView();
                    } else if (Math.abs(deltaY) > Math.abs(deltaX)) {
                        return false;
                    }
                }
                this.mLastX = x;
                break;
        }
        return true;
    }

    private boolean getStatusBasedOnPos() {
        return getProgress() > 0.5f;
    }

    private float getProgress() {
        return this.mProgress;
    }

    private void setProgress(float progress) {
        float tp = progress;
        if (tp > 1.0f) {
            tp = 1.0f;
        } else if (tp < 0.0f) {
            tp = 0.0f;
        }
        this.mProgress = tp;
        invalidate();
    }

    @Override // android.widget.CompoundButton, android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    protected void animateToState(boolean checked) {
        if (this.mProgressAnimator != null) {
            if (this.mProgressAnimator.isRunning()) {
                this.mProgressAnimator.cancel();
            }
            this.mProgressAnimator.setDuration(this.mAnimationDuration);
            if (checked) {
                this.mProgressAnimator.setFloatValues(this.mProgress, 1.0f);
            } else {
                this.mProgressAnimator.setFloatValues(this.mProgress, 0.0f);
            }
            this.mProgressAnimator.start();
        }
    }

    private void catchView() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        this.mCatch = true;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean checked) {
        if (isChecked() != checked) {
            animateToState(checked);
        }
        if (this.mRestoring) {
            setCheckedImmediatelyNoEvent(checked);
        } else {
            super.setChecked(checked);
        }
    }

    public void setCheckedNoEvent(boolean checked) {
        if (this.mChildOnCheckedChangeListener == null) {
            setChecked(checked);
            return;
        }
        super.setOnCheckedChangeListener(null);
        setChecked(checked);
        super.setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void setCheckedImmediatelyNoEvent(boolean checked) {
        if (this.mChildOnCheckedChangeListener == null) {
            setCheckedImmediately(checked);
            return;
        }
        super.setOnCheckedChangeListener(null);
        setCheckedImmediately(checked);
        super.setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    @Override // android.widget.CompoundButton
    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        super.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mChildOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setCheckedImmediately(boolean checked) {
        super.setChecked(checked);
        if (this.mProgressAnimator != null && this.mProgressAnimator.isRunning()) {
            this.mProgressAnimator.cancel();
        }
        setProgress(checked ? 1.0f : 0.0f);
        invalidate();
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            int[] myDrawableState = getDrawableState();
            drawable.setState(myDrawableState);
            invalidate();
        }
    }

    public void setDrawDebugRect(boolean drawDebugRect) {
        this.mDrawDebugRect = drawDebugRect;
        invalidate();
    }

    public long getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public void setAnimationDuration(long animationDuration) {
        this.mAnimationDuration = animationDuration;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public void setThumbDrawable(Drawable thumbDrawable) {
        this.mThumbDrawable = thumbDrawable;
        this.mIsThumbUseDrawable = this.mThumbDrawable != null;
        refreshDrawableState();
        this.mReady = false;
        requestLayout();
        invalidate();
    }

    public void setThumbDrawableRes(int thumbDrawableRes) {
        setThumbDrawable(ContextCompat.getDrawable(getContext(), thumbDrawableRes));
    }

    public Drawable getBackDrawable() {
        return this.mBackDrawable;
    }

    public void setBackDrawable(Drawable backDrawable) {
        this.mBackDrawable = backDrawable;
        this.mIsBackUseDrawable = this.mBackDrawable != null;
        refreshDrawableState();
        this.mReady = false;
        requestLayout();
        invalidate();
    }

    public void setBackDrawableRes(int backDrawableRes) {
        setBackDrawable(ContextCompat.getDrawable(getContext(), backDrawableRes));
    }

    public ColorStateList getBackColor() {
        return this.mBackColor;
    }

    public void setBackColor(ColorStateList backColor) {
        this.mBackColor = backColor;
        if (this.mBackColor != null) {
            setBackDrawable(null);
        }
        invalidate();
    }

    public void setBackColorRes(int backColorRes) {
        setBackColor(ContextCompat.getColorStateList(getContext(), backColorRes));
    }

    public ColorStateList getThumbColor() {
        return this.mThumbColor;
    }

    public void setThumbColor(ColorStateList thumbColor) {
        this.mThumbColor = thumbColor;
        if (this.mThumbColor != null) {
            setThumbDrawable(null);
        }
        invalidate();
    }

    public void setThumbColorRes(int thumbColorRes) {
        setThumbColor(ContextCompat.getColorStateList(getContext(), thumbColorRes));
    }

    public float getThumbRangeRatio() {
        return this.mThumbRangeRatio;
    }

    public void setThumbRangeRatio(float thumbRangeRatio) {
        this.mThumbRangeRatio = thumbRangeRatio;
        this.mReady = false;
        requestLayout();
    }

    public RectF getThumbMargin() {
        return this.mThumbMargin;
    }

    public void setThumbMargin(RectF thumbMargin) {
        if (thumbMargin == null) {
            setThumbMargin(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            setThumbMargin(thumbMargin.left, thumbMargin.top, thumbMargin.right, thumbMargin.bottom);
        }
    }

    public void setThumbMargin(float left, float top, float right, float bottom) {
        this.mThumbMargin.set(left, top, right, bottom);
        this.mReady = false;
        requestLayout();
    }

    public float getThumbWidth() {
        return this.mThumbWidth;
    }

    public float getThumbHeight() {
        return this.mThumbHeight;
    }

    public float getThumbRadius() {
        return this.mThumbRadius;
    }

    public void setThumbRadius(float thumbRadius) {
        this.mThumbRadius = thumbRadius;
        if (!this.mIsThumbUseDrawable) {
            invalidate();
        }
    }

    public PointF getBackSizeF() {
        return new PointF(this.mBackRectF.width(), this.mBackRectF.height());
    }

    public float getBackRadius() {
        return this.mBackRadius;
    }

    public void setBackRadius(float backRadius) {
        this.mBackRadius = backRadius;
        if (!this.mIsBackUseDrawable) {
            invalidate();
        }
    }

    public void setFadeBack(boolean fadeBack) {
        this.mFadeBack = fadeBack;
    }

    public int getTintColor() {
        return this.mTintColor;
    }

    public void setTintColor(int tintColor) {
        this.mTintColor = tintColor;
        this.mThumbColor = ColorUtils.generateThumbColorWithTintColor(this.mTintColor);
        this.mBackColor = ColorUtils.generateBackColorWithTintColor(this.mTintColor);
        this.mIsBackUseDrawable = false;
        this.mIsThumbUseDrawable = false;
        refreshDrawableState();
        invalidate();
    }

    public void setText(CharSequence onText, CharSequence offText) {
        this.mTextOn = onText;
        this.mTextOff = offText;
        this.mOnLayout = null;
        this.mOffLayout = null;
        this.mReady = false;
        requestLayout();
        invalidate();
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    public void setTextThumbInset(int textThumbInset) {
        this.mTextThumbInset = textThumbInset;
        this.mReady = false;
        requestLayout();
        invalidate();
    }

    public void setTextExtra(int textExtra) {
        this.mTextExtra = textExtra;
        this.mReady = false;
        requestLayout();
        invalidate();
    }

    public void setTextAdjust(int textAdjust) {
        this.mTextAdjust = textAdjust;
        this.mReady = false;
        requestLayout();
        invalidate();
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.onText = this.mTextOn;
        ss.offText = this.mTextOff;
        return ss;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        setText(ss.onText, ss.offText);
        this.mRestoring = true;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mRestoring = false;
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.xiaocong.smarthome.switchbutton.SwitchButton.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        CharSequence offText;
        CharSequence onText;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.onText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
            this.offText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            TextUtils.writeToParcel(this.onText, out, flags);
            TextUtils.writeToParcel(this.offText, out, flags);
        }
    }
}
