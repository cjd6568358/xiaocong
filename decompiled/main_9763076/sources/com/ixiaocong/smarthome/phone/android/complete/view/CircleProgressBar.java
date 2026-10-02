package com.ixiaocong.smarthome.phone.android.complete.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CircleProgressBar extends View {
    private String mCenterText;
    private final int mCircleLineStrokeWidth;
    private final Context mContext;
    private int mMaxProgress;
    private final Paint mPaint;
    private int mProgress;
    private final RectF mRectF;
    private String mTxtHint2;
    private final int mTxtStrokeWidth;
    private String mUnit;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMaxProgress = 100;
        this.mProgress = 0;
        this.mCircleLineStrokeWidth = 8;
        this.mTxtStrokeWidth = 2;
        this.mContext = context;
        this.mRectF = new RectF();
        this.mPaint = new Paint();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(Color.rgb(233, 233, 233));
        canvas.drawColor(0);
        this.mPaint.setStrokeWidth(8.0f);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mRectF.left = 4.0f;
        this.mRectF.top = 4.0f;
        this.mRectF.right = width - 4;
        this.mRectF.bottom = height - 4;
        canvas.drawArc(this.mRectF, -90.0f, 360.0f, false, this.mPaint);
        this.mPaint.setColor(Color.rgb(34, 34, 34));
        canvas.drawArc(this.mRectF, -90.0f, 360.0f * (this.mProgress / this.mMaxProgress), false, this.mPaint);
        this.mPaint.setStrokeWidth(2.0f);
        String text = this.mCenterText + this.mUnit;
        int textHeight = height / 4;
        this.mPaint.setTextSize(textHeight);
        int textWidth = (int) this.mPaint.measureText(text, 0, text.length());
        this.mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, (width / 2) - (textWidth / 2), (height / 2) + (textHeight / 2), this.mPaint);
        if (!TextUtils.isEmpty(this.mTxtHint2)) {
            this.mPaint.setStrokeWidth(2.0f);
            String text2 = this.mTxtHint2;
            int textHeight2 = height / 8;
            this.mPaint.setTextSize(textHeight2);
            int textWidth2 = (int) this.mPaint.measureText(text2, 0, text2.length());
            this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text2, (width / 2) - (textWidth2 / 2), ((height * 3) / 4) + (textHeight2 / 2), this.mPaint);
        }
    }

    public int getMaxProgress() {
        return this.mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setUnit(String unit) {
        this.mUnit = unit;
    }

    public String getCenterText() {
        return this.mCenterText;
    }

    public void setCenterText(String centerText) {
        this.mCenterText = centerText;
        postInvalidate();
    }

    public String getmTxtHint2() {
        return this.mTxtHint2;
    }

    public void setmTxtHint2(String mTxtHint2) {
        this.mTxtHint2 = mTxtHint2;
        postInvalidate();
    }
}
