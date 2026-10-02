package com.ixiaocong.smarthome.phone.android.complete.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SlideButton extends View {
    private final AccelerateInterpolator aInterpolator;
    private float bAnim;
    private float bBottom;
    private float bLeft;
    private float bOff2LeftX;
    private float bOffLeftX;
    private float bOffset;
    private float bOn2LeftX;
    private float bOnLeftX;
    private final Path bPath;
    private float bRadius;
    private final RectF bRectF;
    private float bRight;
    private float bStrokeWidth;
    private float bTop;
    private float bWidth;
    private boolean isOpened;
    private int lastState;
    private OnStateChangedListener listener;
    private int mHeight;
    private int mWidth;
    public int openColor;
    private final Paint paint;
    private float sAnim;
    private float sBottom;
    private float sCenterX;
    private float sCenterY;
    private float sHeight;
    private float sLeft;
    private final Path sPath;
    private float sRight;
    private float sScale;
    private float sTop;
    private float sWidth;
    private RadialGradient shadowGradient;
    private float shadowHeight;
    private int state;

    public interface OnStateChangedListener {
        void toggleToOff(View view);

        void toggleToOn(View view);
    }

    public SlideButton(Context context) {
        this(context, null);
    }

    public SlideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paint = new Paint();
        this.sPath = new Path();
        this.bPath = new Path();
        this.bRectF = new RectF();
        this.aInterpolator = new AccelerateInterpolator(2.0f);
        this.state = 1;
        this.lastState = this.state;
        this.isOpened = false;
        this.listener = new OnStateChangedListener() { // from class: com.ixiaocong.smarthome.phone.android.complete.view.SlideButton.2
            @Override // com.ixiaocong.smarthome.phone.android.complete.view.SlideButton.OnStateChangedListener
            public void toggleToOn(View view) {
                SlideButton.this.toggleSwitch(4);
            }

            @Override // com.ixiaocong.smarthome.phone.android.complete.view.SlideButton.OnStateChangedListener
            public void toggleToOff(View view) {
                SlideButton.this.toggleSwitch(1);
            }
        };
        setLayerType(1, null);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = (int) (widthSize * 0.65f);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        this.sTop = 0.0f;
        this.sLeft = 0.0f;
        this.sRight = this.mWidth;
        this.sBottom = this.mHeight * 0.91f;
        this.sWidth = this.sRight - this.sLeft;
        this.sHeight = this.sBottom - this.sTop;
        this.sCenterX = (this.sRight + this.sLeft) / 2.0f;
        this.sCenterY = (this.sBottom + this.sTop) / 2.0f;
        this.shadowHeight = this.mHeight - this.sBottom;
        this.bTop = 0.0f;
        this.bLeft = 0.0f;
        float f = this.sBottom;
        this.bBottom = f;
        this.bRight = f;
        this.bWidth = this.bRight - this.bLeft;
        float halfHeightOfS = (this.sBottom - this.sTop) / 2.0f;
        this.bRadius = 0.95f * halfHeightOfS;
        this.bOffset = this.bRadius * 0.2f;
        this.bStrokeWidth = (halfHeightOfS - this.bRadius) * 2.0f;
        this.bOnLeftX = this.sWidth - this.bWidth;
        this.bOn2LeftX = this.bOnLeftX - this.bOffset;
        this.bOffLeftX = 0.0f;
        this.bOff2LeftX = 0.0f;
        this.sScale = 1.0f - (this.bStrokeWidth / this.sHeight);
        RectF sRectF = new RectF(this.sLeft, this.sTop, this.sBottom, this.sBottom);
        this.sPath.arcTo(sRectF, 90.0f, 180.0f);
        sRectF.left = this.sRight - this.sBottom;
        sRectF.right = this.sRight;
        this.sPath.arcTo(sRectF, 270.0f, 180.0f);
        this.sPath.close();
        this.bRectF.left = this.bLeft;
        this.bRectF.right = this.bRight;
        this.bRectF.top = this.bTop + (this.bStrokeWidth / 2.0f);
        this.bRectF.bottom = this.bBottom - (this.bStrokeWidth / 2.0f);
        this.shadowGradient = new RadialGradient(this.bWidth / 2.0f, this.bWidth / 2.0f, this.bWidth / 2.0f, -16777216, 0, Shader.TileMode.CLAMP);
    }

    private void calcBPath(float percent) {
        this.bPath.reset();
        this.bRectF.left = this.bLeft + (this.bStrokeWidth / 2.0f);
        this.bRectF.right = this.bRight - (this.bStrokeWidth / 2.0f);
        this.bPath.arcTo(this.bRectF, 90.0f, 180.0f);
        this.bRectF.left = this.bLeft + (this.bOffset * percent) + (this.bStrokeWidth / 2.0f);
        this.bRectF.right = (this.bRight + (this.bOffset * percent)) - (this.bStrokeWidth / 2.0f);
        this.bPath.arcTo(this.bRectF, 270.0f, 180.0f);
        this.bPath.close();
    }

    private float calcBTranslate(float percent) {
        float result = 0.0f;
        int wich = this.state - this.lastState;
        switch (wich) {
            case -3:
                result = this.bOffLeftX + ((this.bOnLeftX - this.bOffLeftX) * percent);
                break;
            case -2:
                if (this.state == 1) {
                    result = this.bOffLeftX + ((this.bOn2LeftX - this.bOffLeftX) * percent);
                } else if (this.state == 2) {
                    result = this.bOff2LeftX + ((this.bOnLeftX - this.bOff2LeftX) * percent);
                }
                break;
            case -1:
                if (this.state == 3) {
                    result = this.bOn2LeftX + ((this.bOnLeftX - this.bOn2LeftX) * percent);
                } else if (this.state == 1) {
                    result = this.bOffLeftX + ((this.bOff2LeftX - this.bOffLeftX) * percent);
                }
                break;
            case 1:
                if (this.state == 2) {
                    result = this.bOff2LeftX - ((this.bOff2LeftX - this.bOffLeftX) * percent);
                } else if (this.state == 4) {
                    result = this.bOnLeftX - ((this.bOnLeftX - this.bOn2LeftX) * percent);
                }
                break;
            case 2:
                if (this.state == 4) {
                    result = this.bOnLeftX - ((this.bOnLeftX - this.bOff2LeftX) * percent);
                } else if (this.state == 4) {
                    result = this.bOn2LeftX - ((this.bOn2LeftX - this.bOffLeftX) * percent);
                }
                break;
            case 3:
                result = this.bOnLeftX - ((this.bOnLeftX - this.bOffLeftX) * percent);
                break;
        }
        return result - this.bOffLeftX;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setAntiAlias(true);
        boolean isOn = this.state == 4 || this.state == 3;
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(isOn ? this.openColor : -1842205);
        canvas.drawPath(this.sPath, this.paint);
        this.sAnim = this.sAnim - 0.1f > 0.0f ? this.sAnim - 0.1f : 0.0f;
        this.bAnim = this.bAnim - 0.1f > 0.0f ? this.bAnim - 0.1f : 0.0f;
        float dsAnim = this.aInterpolator.getInterpolation(this.sAnim);
        float dbAnim = this.aInterpolator.getInterpolation(this.bAnim);
        float scale = this.sScale * (isOn ? dsAnim : 1.0f - dsAnim);
        float f = (this.bOnLeftX + this.bRadius) - this.sCenterX;
        if (isOn) {
            dsAnim = 1.0f - dsAnim;
        }
        float scaleOffset = f * dsAnim;
        canvas.save();
        canvas.scale(scale, scale, this.sCenterX + scaleOffset, this.sCenterY);
        this.paint.setColor(-1);
        canvas.drawPath(this.sPath, this.paint);
        canvas.restore();
        canvas.save();
        canvas.translate(calcBTranslate(dbAnim), this.shadowHeight);
        boolean isState2 = this.state == 3 || this.state == 2;
        if (isState2) {
            dbAnim = 1.0f - dbAnim;
        }
        calcBPath(dbAnim);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(-13421773);
        this.paint.setShader(this.shadowGradient);
        canvas.drawPath(this.bPath, this.paint);
        this.paint.setShader(null);
        canvas.translate(0.0f, -this.shadowHeight);
        canvas.scale(0.98f, 0.98f, this.bWidth / 2.0f, this.bWidth / 2.0f);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(-1);
        canvas.drawPath(this.bPath, this.paint);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(this.bStrokeWidth * 0.5f);
        this.paint.setColor(isOn ? this.openColor : -4210753);
        canvas.drawPath(this.bPath, this.paint);
        canvas.restore();
        this.paint.reset();
        if (this.sAnim > 0.0f || this.bAnim > 0.0f) {
            invalidate();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if ((this.state == 4 || this.state == 1) && this.sAnim * this.bAnim == 0.0f) {
            switch (event.getAction()) {
                case 0:
                    return true;
                case 1:
                    this.lastState = this.state;
                    if (this.state == 1) {
                        refreshState(2);
                    } else if (this.state == 4) {
                        refreshState(3);
                    }
                    this.bAnim = 1.0f;
                    invalidate();
                    if (this.state == 2) {
                        this.listener.toggleToOn(this);
                    } else if (this.state == 3) {
                        this.listener.toggleToOff(this);
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void refreshState(int newState) {
        if (!this.isOpened && newState == 4) {
            this.isOpened = true;
        } else if (this.isOpened && newState == 1) {
            this.isOpened = false;
        }
        this.lastState = this.state;
        this.state = newState;
        postInvalidate();
    }

    public void setOpened(boolean isOpened) {
        refreshState(isOpened ? 4 : 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:16:0x001d A[Catch: all -> 0x002a, TryCatch #0 {, blocks: (B:7:0x0009, B:9:0x000d, B:16:0x001d, B:17:0x0021, B:12:0x0014, B:14:0x0018), top: B:23:0x0009 }] */
    public synchronized void toggleSwitch(int wich) {
        if (wich == 4 || wich == 1) {
            if (wich == 4) {
                if (this.lastState == 1 || this.lastState == 2) {
                    this.sAnim = 1.0f;
                }
                this.bAnim = 1.0f;
                refreshState(wich);
            }
            if (wich == 1 && (this.lastState == 4 || this.lastState == 3)) {
                this.sAnim = 1.0f;
            }
            this.bAnim = 1.0f;
            refreshState(wich);
        }
    }

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("empty listener");
        }
        this.listener = listener;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.isOpened = this.isOpened;
        return ss;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.isOpened = ss.isOpened;
        this.state = this.isOpened ? 4 : 1;
    }

    @SuppressLint({"ParcelCreator"})
    static final class SavedState extends View.BaseSavedState {
        private boolean isOpened;

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.isOpened ? 1 : 0);
        }
    }
}
