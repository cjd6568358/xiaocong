package com.ixiaocong.smarthome.phone.android.complete.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;
import com.ixiaocong.smarthome.phone.R;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ProgressTextBar extends SeekBar {
    private boolean isMinus;
    private Bitmap mBackgroundBitmap;
    private float mBgHeight;
    private float mBgWidth;
    private Paint mPaint;
    private int mProgressSize;
    private String mText;
    private float mTextBaseLineY;
    private int mTextColor;
    private int mTextOrientation;
    private float mTextSize;
    private float mTextWidth;
    private String mUint;
    private int minusSize;

    public ProgressTextBar(Context context) {
        this(context, null);
    }

    public ProgressTextBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressTextBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isMinus = false;
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressTextBar, defStyleAttr, 0);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case 0:
                    int bgResId = ta.getResourceId(index, R.drawable.seek_thumb_icon);
                    this.mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), bgResId);
                    this.mBgWidth = this.mBackgroundBitmap.getWidth() + 110;
                    this.mBgHeight = this.mBackgroundBitmap.getHeight();
                    break;
                case 1:
                    this.mTextColor = ta.getColor(index, -1);
                    break;
                case 2:
                    this.mTextOrientation = ta.getInt(index, 1);
                    break;
                case 3:
                    this.mTextSize = ta.getDimension(index, 15.0f);
                    break;
            }
        }
        ta.recycle();
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(this.mTextColor);
        this.mPaint.setTextSize(this.mTextSize);
        if (this.mTextOrientation == 1) {
            setPadding(((int) Math.ceil(this.mBgWidth)) / 2, ((int) Math.ceil(this.mBgHeight)) + 5, ((int) Math.ceil(this.mBgWidth)) / 2, 0);
        } else {
            setPadding(((int) Math.ceil(this.mBgWidth)) / 2, 0, ((int) Math.ceil(this.mBgWidth)) / 2, ((int) Math.ceil(this.mBgHeight)) + 5);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getTextLocation();
        Rect bgRect = getProgressDrawable().getBounds();
        float bgX = (bgRect.width() * getProgress()) / getMax() == 0 ? 1.0f : getMax();
        float bgY = 0.0f;
        if (this.mTextOrientation == 2) {
            bgY = this.mBgHeight + 10.0f;
        }
        float textX = bgX + ((this.mBgWidth - this.mTextWidth) / 2.0f);
        float textY = (float) ((((double) (this.mTextBaseLineY + bgY)) + ((0.16d * ((double) this.mBgHeight)) / 2.0d)) - 10.0d);
        canvas.drawBitmap(this.mBackgroundBitmap, bgX, bgY, this.mPaint);
        canvas.drawText(this.mText, textX, textY, this.mPaint);
    }

    @Override // android.widget.AbsSeekBar, android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        invalidate();
        return super.onTouchEvent(event);
    }

    private void getTextLocation() {
        Paint.FontMetrics fm = this.mPaint.getFontMetrics();
        if (getProgress() == 0) {
            if (isMinus()) {
                this.mText = Constants.MAIN_VERSION_TAG + (getProgress() + getMinusSize()) + getUnit();
            } else {
                this.mText = Constants.MAIN_VERSION_TAG + getProgress() + getUnit();
            }
        } else if (isMinus()) {
            if (getProgressSize() == 0) {
                this.mText = Constants.MAIN_VERSION_TAG + (getProgress() + getMinusSize());
            } else {
                double progress = (((double) getProgress()) / Math.pow(10.0d, getProgressSize())) + ((double) getMinusSize());
                XcLogger.e("progressTextBar", ((float) progress) + "=" + (((double) getProgress()) / Math.pow(10.0d, getProgressSize())) + "+" + getMinusSize());
                this.mText = Constants.MAIN_VERSION_TAG + ((float) progress) + getUnit();
            }
        } else if (getProgressSize() == 0) {
            this.mText = Constants.MAIN_VERSION_TAG + getProgress() + getUnit();
        } else {
            this.mText = Constants.MAIN_VERSION_TAG + (((double) getProgress()) / Math.pow(10.0d, getProgressSize())) + getUnit();
        }
        this.mTextWidth = this.mPaint.measureText(this.mText);
        this.mTextBaseLineY = ((this.mBgHeight / 2.0f) - fm.descent) + ((fm.descent - fm.ascent) / 2.0f);
    }

    public int getProgressSize() {
        return this.mProgressSize;
    }

    public void setProgressSize(int size) {
        this.mProgressSize = size;
    }

    public void setMinus(boolean minus) {
        this.isMinus = minus;
    }

    public boolean isMinus() {
        return this.isMinus;
    }

    public int getMinusSize() {
        return this.minusSize;
    }

    public void setMinusSize(int minusSize) {
        this.minusSize = minusSize;
    }

    public String getUnit() {
        return !TextUtils.isEmpty(this.mUint) ? this.mUint : Constants.MAIN_VERSION_TAG;
    }

    public void setUnit(String uint) {
        this.mUint = uint;
    }
}
